/** @jsxImportSource @emotion/react */

import { useState, useEffect } from "react";
import { css } from "@emotion/react";

import GLOBALS from "../../../../siteConfig";
import DemoFrame from "./DemoFrame";
import CodePanel from "./CodePanel";

// File extension → Prism language identifier. Keeps syntax highlighting honest
// when callers mix Java with CSS, Kotlin, JSON, etc.
const EXT_TO_LANG = {
  java: 'java', kt: 'kotlin', kts: 'kotlin',
  css: 'css', scss: 'scss',
  js: 'javascript', mjs: 'javascript', cjs: 'javascript',
  jsx: 'jsx', ts: 'typescript', tsx: 'tsx',
  json: 'json', xml: 'xml', html: 'html', htm: 'html',
  yml: 'yaml', yaml: 'yaml', md: 'markdown',
  py: 'python', rb: 'ruby', sh: 'bash',
};

function inferLanguage(url) {
  const ext = url.split('?')[0].split('#')[0].split('.').pop().toLowerCase();
  return EXT_TO_LANG[ext] ?? 'text';
}

function basename(url) {
  return new URL(url).pathname.split('/').pop();
}

// Repo-relative paths get the GitHub raw-content base prepended; absolute
// http(s) URLs are an escape hatch and are used as-is.
function resolveUrl(url) {
  if (/^https?:\/\//.test(url)) return url;
  return GLOBALS.RAW_CONTENT_BASE + url.replace(/^\//, '');
}

// Accepts the `files` prop (array of strings or objects) and returns a
// uniform list of { url, label, language, highlight } records, in input order.
function normalizeFiles(files) {
  if (!files) return [];
  return files.map((entry) => {
    const raw = typeof entry === 'string' ? { url: entry } : entry;
    const url = resolveUrl(raw.url);
    return {
      url,
      label: raw.label ?? basename(url),
      language: raw.language ?? inferLanguage(url),
      highlight: raw.highlight,
    };
  });
}

/**
 * Fetches every file in `normalized` and exposes a {url: code} map.
 * Pre-seeds entries so the consumer can render the tab list in input order
 * before any fetch resolves. Aborts in-flight requests on unmount or when
 * the input set changes.
 */
function useRemoteFiles(filesKey, normalized) {
  const [loadedFiles, setLoadedFiles] = useState({});

  useEffect(() => {
    const controller = new AbortController();

    async function loadInto(url) {
      try {
        const response = await fetch(url, { signal: controller.signal });
        if (!response.ok) throw new Error(`${response.status} ${response.statusText}`);
        const text = await response.text();
        setLoadedFiles((prev) => ({ ...prev, [url]: text }));
      } catch (err) {
        if (err.name === 'AbortError') return;
        console.error(`ComponentDemo: failed to load ${url}`, err);
        setLoadedFiles((prev) => ({
          ...prev,
          [url]: `// Failed to load ${url}\n// ${err.message}`,
        }));
      }
    }

    setLoadedFiles((prev) => {
      const seeded = { ...prev };
      normalized.forEach((f) => {
        if (!(f.url in seeded)) seeded[f.url] = '';
      });
      return seeded;
    });

    normalized.forEach((f) => loadInto(f.url));

    return () => controller.abort();
  }, [filesKey]);

  return loadedFiles;
}

const mainStyles = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-bottom: 16px;
  @media screen and (max-width: 768px) {
    width: 100vw;
    margin-left: -1em;
  }
`;

/**
 * Renders an embedded webforJ demo iframe alongside a collapsible code panel
 * showing the source for one or more files (Java, CSS, Kotlin, etc.).
 *
 * @param {object} props
 * @param {string} props.path                  Required. Path appended to the
 *                                             iframe base URL (e.g. "/webforj/qrdemo?").
 * @param {Array<string|object>} [props.files] Source files shown in the code panel,
 *   in array order. Each entry is either:
 *   - a string: a repo-relative path (e.g. "src/main/java/.../View.java") or a full URL.
 *   - an object: `{ url, label?, language?, highlight? }`. `label` defaults to the
 *     file's basename, `language` is inferred from the file extension, `highlight`
 *     is a Docusaurus metastring (e.g. "{3-7}") for line highlighting.
 * @param {string} [props.height]              CSS height for the iframe in
 *                                             `'resizable'` mode (e.g. "175px").
 *                                             Ignored for phone variants.
 * @param {'resizable'|'mobile'|'desktop'} [props.frame='resizable']
 *   Iframe pane variant. `'resizable'` is the standard rectangle with drag-to-resize.
 *   `'mobile'` renders a phone-shaped 375x700 preview, useful for app-layout demos.
 *   `'desktop'` renders a 100%x600 phone-shaped preview for desktop app layouts.
 */
export default function ComponentDemo({ path, files, height, frame = 'resizable' }) {
  const normalized = normalizeFiles(files);
  const filesKey = normalized.map((f) => f.url).join('|');
  const loadedFiles = useRemoteFiles(filesKey, normalized);

  return (
    <div css={mainStyles}>
      <DemoFrame path={path} height={height} variant={frame} />
      {normalized.length > 0 && (
        <CodePanel
          files={normalized}
          loadedFiles={loadedFiles}
          detached={frame !== 'resizable'}
        />
      )}
    </div>
  );
}
