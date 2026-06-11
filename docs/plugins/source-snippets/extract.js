const fs = require('fs');
const path = require('path');
const fg = require('fast-glob');

const SOURCE_SNIPPET_TAG = /<SourceSnippet\b([\s\S]*?)\/>/g;
const STRING_PROP = /(\w+)\s*=\s*(?:"([^"]*)"|'([^']*)')/g;
const START_MARKER = /^\s*\/\/\s*docs:start\s+([A-Za-z0-9_.:-]+)\s*$/;
const END_MARKER = /^\s*\/\/\s*docs:end\s+([A-Za-z0-9_.:-]+)\s*$/;

const EXT_TO_LANG = {
  java: 'java',
  kt: 'kotlin',
  kts: 'kotlin',
  css: 'css',
  scss: 'scss',
  js: 'javascript',
  mjs: 'javascript',
  cjs: 'javascript',
  jsx: 'jsx',
  ts: 'typescript',
  tsx: 'tsx',
  json: 'json',
  xml: 'xml',
  html: 'html',
  htm: 'html',
  yml: 'yaml',
  yaml: 'yaml',
  md: 'markdown',
};

function inferLanguage(file) {
  const ext = path.extname(file).replace(/^\./, '').toLowerCase();
  return EXT_TO_LANG[ext] ?? 'text';
}

function normalizeSlashes(value) {
  return value.replace(/\\/g, '/');
}

function parseProps(rawProps) {
  const props = {};
  for (const match of rawProps.matchAll(STRING_PROP)) {
    props[match[1]] = match[2] ?? match[3];
  }
  return props;
}

function trimEmptyEdges(lines) {
  let start = 0;
  let end = lines.length;

  while (start < end && lines[start].trim() === '') start += 1;
  while (end > start && lines[end - 1].trim() === '') end -= 1;

  return lines.slice(start, end);
}

function commonIndent(lines) {
  const indents = lines
    .filter((line) => line.trim() !== '')
    .map((line) => line.match(/^[ \t]*/)[0].length);

  return indents.length === 0 ? 0 : Math.min(...indents);
}

function dedent(lines) {
  const trimmed = trimEmptyEdges(lines);
  const indent = commonIndent(trimmed);
  return trimmed.map((line) => line.slice(Math.min(indent, line.length))).join('\n');
}

function findRegion(source, region, sourceFile) {
  const lines = source.split(/\r?\n/);
  let startLine = null;
  let endLine = null;

  for (let index = 0; index < lines.length; index += 1) {
    const start = lines[index].match(START_MARKER);
    const end = lines[index].match(END_MARKER);

    if (start?.[1] === region) {
      if (startLine !== null) {
        throw new Error(`Duplicate start marker for region "${region}" in ${sourceFile}`);
      }
      startLine = index + 1;
    }

    if (end?.[1] === region) {
      endLine = index;
      break;
    }
  }

  if (startLine === null) {
    throw new Error(`Missing docs:start marker for region "${region}" in ${sourceFile}`);
  }

  if (endLine === null) {
    throw new Error(`Missing docs:end marker for region "${region}" in ${sourceFile}`);
  }

  if (endLine < startLine) {
    throw new Error(`docs:end appears before docs:start for region "${region}" in ${sourceFile}`);
  }

  return dedent(lines.slice(startLine, endLine));
}

function scanReferences(siteDir, options = {}) {
  const docsGlobs = options.docsGlobs ?? ['docs/**/*.{md,mdx}', 'blog/**/*.{md,mdx}'];
  const files = fg.sync(docsGlobs, {
    cwd: siteDir,
    absolute: true,
    onlyFiles: true,
    ignore: ['i18n/**', 'build/**', '.docusaurus/**'],
  });

  const references = [];

  for (const docFile of files) {
    const content = fs.readFileSync(docFile, 'utf8');
    for (const match of content.matchAll(SOURCE_SNIPPET_TAG)) {
      const props = parseProps(match[1]);
      if (!props.file || !props.region) {
        throw new Error(
          `SourceSnippet in ${normalizeSlashes(path.relative(siteDir, docFile))} must include string file and region props`
        );
      }

      references.push({
        docFile,
        file: normalizeSlashes(props.file),
        region: props.region,
        language: props.language,
        title: props.title,
      });
    }
  }

  return references;
}

function collectSnippets(siteDir, options = {}) {
  const repoRoot = options.repoRoot ?? path.resolve(siteDir, '..');
  const references = scanReferences(siteDir, options);
  const snippets = {};
  const seen = new Map();

  for (const reference of references) {
    const key = `${reference.file}#${reference.region}`;
    const absoluteSource = path.resolve(repoRoot, reference.file);
    const relativeSource = normalizeSlashes(path.relative(repoRoot, absoluteSource));

    if (relativeSource.startsWith('..')) {
      throw new Error(`SourceSnippet file must stay inside the repository: ${reference.file}`);
    }

    if (!fs.existsSync(absoluteSource)) {
      throw new Error(`SourceSnippet file does not exist: ${reference.file}`);
    }

    const source = fs.readFileSync(absoluteSource, 'utf8');
    const code = findRegion(source, reference.region, reference.file);

    if (!seen.has(key)) {
      seen.set(key, reference);
      snippets[key] = {
        code,
        file: reference.file,
        region: reference.region,
        language: reference.language ?? inferLanguage(reference.file),
        title: reference.title,
      };
    }
  }

  return {
    snippets,
    references,
  };
}

module.exports = {
  collectSnippets,
  findRegion,
  scanReferences,
};
