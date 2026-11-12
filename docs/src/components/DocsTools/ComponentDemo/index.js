/** @jsxImportSource @emotion/react */

import { useState, useEffect, useId, useRef } from "react";
import { css } from "@emotion/react";
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import { translate } from '@docusaurus/Translate';

import DemoFrame, { useIframeSrc } from "./DemoFrame";
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
function resolveUrl(url, rawContentBase) {
  if (/^https?:\/\//.test(url)) return url;
  return rawContentBase + url.replace(/^\//, '');
}

// Accepts the `files` prop (array of strings or objects) and returns a
// uniform list of { url, label, language, highlight } records, in input order.
function normalizeFiles(files, rawContentBase) {
  if (!files) return [];
  return files.map((entry) => {
    const raw = typeof entry === 'string' ? { url: entry } : entry;
    const url = resolveUrl(raw.url, rawContentBase);
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

// A demo is one flat box: the live preview, and a strip along the bottom
// that opens the code below it and links to the demo on its own. The
// preview stays in view while its code is read. On phones the box runs
// edge to edge.
const windowStyles = css`
  --demo-line: rgb(0 0 0 / 0.14);

  display: flex;
  flex-direction: column;
  width: 100%;
  margin: 0 0 var(--ifm-leading);
  overflow: hidden;
  border: 1px solid var(--demo-line);
  border-radius: 14px;
  background-color: var(--dwc-surface-3);

  [data-theme="dark"] & {
    --demo-line: rgb(255 255 255 / 0.1);
  }

  @media screen and (max-width: 768px) {
    width: 100vw;
    margin-left: -1em;
    border-right: 0;
    border-left: 0;
    border-radius: 0;
  }
`;

const openLinkStyles = css`
  display: inline-flex;
  flex: none;
  align-self: center;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
  margin-left: auto;
  border-radius: 8px;
  color: var(--ifm-color-emphasis-800);
  transition: color 150ms ease-out;

  &:hover,
  &:focus {
    color: var(--ifm-color-primary);
  }

  &:focus-visible {
    outline: 2px solid var(--ifm-color-primary);
    outline-offset: -2px;
  }

  svg {
    width: 16px;
    height: 16px;
  }
`;

// The strip under the preview. It holds the button that opens the code,
// one tab per source file once open, and the link that opens the demo in a
// new window.
const codeBarStyles = css`
  display: flex;
  align-items: stretch;
  gap: 4px;
  height: 44px;
  padding: 0 6px;
  border-top: 1px solid var(--demo-line);
  background-color: var(--dwc-surface-2);
`;

// Hover changes colour only.
const toggleStyles = css`
  display: inline-flex;
  flex: none;
  align-items: center;
  gap: 8px;
  padding: 0 10px;
  border: 0;
  background-color: transparent;
  color: var(--ifm-font-color-base);
  font-family: inherit;
  font-size: 0.8125rem;
  font-weight: 600;
  line-height: 1;
  white-space: nowrap;
  cursor: pointer;
  transition: color 150ms ease-out;

  &:hover {
    color: var(--ifm-color-primary);
  }

  &:focus-visible {
    outline: 2px solid var(--ifm-color-primary);
    outline-offset: -3px;
  }

  svg {
    width: 16px;
    height: 16px;
    flex: none;
  }
`;

// With many files the row scrolls sideways; the selected tab is brought
// into view when it changes.
const tabListStyles = css`
  display: flex;
  flex: 1;
  gap: 2px;
  min-width: 0;
  padding-left: 8px;
  border-left: 1px solid var(--demo-line);
  overflow-x: auto;
  scrollbar-width: none;

  &::-webkit-scrollbar {
    display: none;
  }
`;

// A file tab: plain text, the selected one marked by its colour and a 2px
// line along the bottom of the strip.
const tabStyles = css`
  flex: none;
  padding: 0 12px;
  border: 0;
  border-bottom: 2px solid transparent;
  border-radius: 0;
  background-color: transparent;
  color: var(--ifm-color-emphasis-800);
  font-family: var(--ifm-font-family-monospace);
  font-size: 0.8125rem;
  font-weight: 500;
  line-height: 1;
  white-space: nowrap;
  cursor: pointer;
  transition: color 150ms ease-out, border-color 150ms ease-out;

  &:hover {
    color: var(--ifm-font-color-base);
  }

  &[aria-selected="true"] {
    border-bottom-color: var(--ifm-color-primary);
    color: var(--ifm-color-primary);
    cursor: default;
  }

  &:focus-visible {
    outline: 2px solid var(--ifm-color-primary);
    outline-offset: -3px;
  }
`;

// Shown at either end of the row while there are more tabs that way
const tabScrollStyles = css`
  display: inline-flex;
  flex: none;
  align-self: center;
  align-items: center;
  justify-content: center;
  width: 26px;
  height: 30px;
  padding: 0;
  border: 0;
  background-color: transparent;
  color: var(--ifm-color-emphasis-800);
  cursor: pointer;
  transition: color 150ms ease-out;

  &:hover {
    color: var(--ifm-color-primary);
  }

  svg {
    width: 16px;
    height: 16px;
  }
`;

const ICON_PATHS = {
  external:
    'M11 4h5v5M16 4l-7 7M14 12v3.5a.5.5 0 0 1-.5.5h-9a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5H8',
  code: 'M7 6 3 10l4 4m6-8 4 4-4 4m-1.5-10-3 12',
  left: 'M12 4.5 6.5 10l5.5 5.5',
  right: 'M8 4.5 13.5 10 8 15.5',
};

function Icon({ name }) {
  return (
    <svg viewBox="0 0 20 20" aria-hidden="true">
      <path
        d={ICON_PATHS[name]}
        fill="none"
        stroke="currentColor"
        strokeWidth="1.75"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
}

// Tracks which ends of a sideways-scrolling row have more to scroll to.
function useScrollEnds(ref, watch) {
  const [more, setMore] = useState({ left: false, right: false });

  useEffect(() => {
    const list = ref.current;
    if (!list) return undefined;
    const update = () => {
      const left = list.scrollLeft > 1;
      const right = list.scrollLeft + list.clientWidth < list.scrollWidth - 1;
      setMore((prev) => (prev.left === left && prev.right === right ? prev : { left, right }));
    };
    update();
    list.addEventListener('scroll', update, { passive: true });
    const observer = new ResizeObserver(update);
    observer.observe(list);
    return () => {
      list.removeEventListener('scroll', update);
      observer.disconnect();
    };
  }, [watch]);

  return more;
}

/**
 * Renders an embedded webforJ demo with its source code.
 *
 * @param {object}  props
 * @param {string}  props.path     Path of the demo, appended to the demo host.
 * @param {Array}   [props.files]  Source files to show: repo-relative paths or
 *                                 `{ url, label, language, highlight }` records.
 * @param {string}  [props.height] CSS height of the preview (resizable only).
 * @param {'resizable'|'mobile'|'desktop'} [props.frame='resizable']
 */
export default function ComponentDemo({ path, files, height, frame = 'resizable' }) {
  const { siteConfig } = useDocusaurusContext();
  const normalized = normalizeFiles(files, siteConfig.customFields.rawContentBase);
  const filesKey = normalized.map((f) => f.url).join('|');
  const loadedFiles = useRemoteFiles(filesKey, normalized);
  const demoUrl = useIframeSrc(path);
  const [open, setOpen] = useState(false);
  const [selected, setSelected] = useState(0);
  const baseId = useId();
  const tabListRef = useRef(null);
  const more = useScrollEnds(tabListRef, `${filesKey}|${open}`);
  const hasCode = normalized.length > 0;
  const current = Math.min(selected, normalized.length - 1);

  const tabId = (index) => `${baseId}-tab-${index}`;
  const panelId = `${baseId}-panel`;

  // keep the selected tab in view when the row of tabs is scrolled
  useEffect(() => {
    const list = tabListRef.current;
    const tab = list?.querySelector('[aria-selected="true"]');
    if (!tab) return;
    const left = tab.offsetLeft - list.offsetLeft;
    if (left < list.scrollLeft) list.scrollLeft = left;
    else if (left + tab.offsetWidth > list.scrollLeft + list.clientWidth) {
      list.scrollLeft = left + tab.offsetWidth - list.clientWidth;
    }
  }, [current, open]);

  // Left and Right move between the tabs, as in any tab list.
  const onKeyDown = (event) => {
    const step = { ArrowRight: 1, ArrowLeft: -1 }[event.key];
    if (!step) return;
    event.preventDefault();
    const next = (current + step + normalized.length) % normalized.length;
    setSelected(next);
    document.getElementById(tabId(next))?.focus();
  };

  const scrollTabs = (direction) => {
    tabListRef.current?.scrollBy({ left: direction * 220, behavior: 'smooth' });
  };

  return (
    <figure css={windowStyles}>
      <DemoFrame path={path} height={height} variant={frame} />
      <div css={codeBarStyles}>
        {hasCode && (
          <>
            <button
              type="button"
              css={toggleStyles}
              aria-expanded={open}
              aria-controls={panelId}
              onClick={() => setOpen((value) => !value)}
            >
              <Icon name="code" />
              {open
                ? translate({
                    id: 'componentDemo.hideCode',
                    message: 'Hide Code',
                    description: 'Button text to hide the code section',
                  })
                : translate({
                    id: 'componentDemo.showCode',
                    message: 'Show Code',
                    description: 'Button text to show the code section',
                  })}
            </button>
            {open && more.left && (
              <button type="button" css={tabScrollStyles} tabIndex={-1} aria-hidden="true" onClick={() => scrollTabs(-1)}>
                <Icon name="left" />
              </button>
            )}
            {open && (
              <div
                ref={tabListRef}
                css={tabListStyles}
                role="tablist"
                aria-label={translate({
                  id: 'componentDemo.tabs',
                  message: 'Source files',
                  description: 'Accessible label for the file tabs of a component demo',
                })}
                onKeyDown={onKeyDown}
              >
                {normalized.map((file, index) => (
                  <button
                    key={file.url}
                    type="button"
                    role="tab"
                    id={tabId(index)}
                    aria-selected={index === current}
                    aria-controls={panelId}
                    tabIndex={index === current ? 0 : -1}
                    css={tabStyles}
                    onClick={() => setSelected(index)}
                  >
                    {file.label}
                  </button>
                ))}
              </div>
            )}
            {open && more.right && (
              <button type="button" css={tabScrollStyles} tabIndex={-1} aria-hidden="true" onClick={() => scrollTabs(1)}>
                <Icon name="right" />
              </button>
            )}
          </>
        )}
        <a
          css={openLinkStyles}
          href={demoUrl}
          target="_blank"
          rel="noopener noreferrer"
          aria-label={translate({
            id: 'componentDemo.openInNewWindow',
            message: 'Open demo in new window',
            description: 'Accessible label for the button that opens the demo iframe URL in a new browser tab',
          })}
        >
          <Icon name="external" />
        </a>
      </div>
      {hasCode && (
        <CodePanel
          id={panelId}
          labelledBy={tabId(current)}
          open={open}
          file={normalized[current]}
          code={loadedFiles[normalized[current].url] ?? ''}
        />
      )}
    </figure>
  );
}
