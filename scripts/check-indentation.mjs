#!/usr/bin/env node
/**
 * check-indentation.mjs
 *
 * Checks that all fenced code blocks in markdown/mdx files use 2-space indentation,
 * not 4-space or tab indentation.
 *
 * A code block is flagged if:
 *   - Any line uses a tab character
 *   - Every indented line has indentation that is a strict multiple of 4 (pure 4-space block)
 *
 * Exit code 0 = all clear
 * Exit code 1 = violations found
 *
 * Usage:
 *   node scripts/check-indentation.mjs
 */

import { readFileSync, readdirSync, statSync } from 'fs';
import { join, resolve, relative, extname, dirname } from 'path';
import { fileURLToPath } from 'url';

const FENCE = '```';
const __dirname = dirname(fileURLToPath(import.meta.url));
const DOCS_DIR = resolve(__dirname, '..', 'docs', 'docs');

/**
 * @param {string} fpath
 * @param {number} blockStart
 * @param {string} lang
 * @param {Array<[number, string]>} lines
 * @returns {Array<object>}
 */
function checkBlock(fpath, blockStart, lang, lines) {
  const violations = [];
  const tabLines = lines.filter(([, l]) => l.startsWith('\t'));
  const indented = lines.filter(([, l]) => l.trim() && l.length - l.trimStart().length > 0);

  for (const [lineno, l] of tabLines) {
    violations.push({ file: fpath, line: lineno, lang, reason: 'tab indentation', content: l.slice(0, 60) });
  }

  if (!indented.length || tabLines.length > 0) return violations;

  const indentLevels = new Set(indented.map(([, l]) => l.length - l.trimStart().length));
  const sortedLevels = [...indentLevels].sort((a, b) => a - b);

  if (sortedLevels.every(n => n % 4 === 0)) {
    violations.push({
      file: fpath,
      line: blockStart,
      lang,
      reason: `4-space indentation (indent levels: ${sortedLevels})`,
      content: null,
    });
  }

  return violations;
}

/**
 * @param {string} fpath
 * @returns {Array<object>}
 */
function checkFile(fpath) {
  const violations = [];
  const lines = readFileSync(fpath, 'utf8').split('\n');

  let inCodeBlock = false;
  let codeBlockLines = [];
  let blockStartLine = 0;
  let lang = '';

  for (const [i, rawLine] of lines.entries()) {
    const lineno = i + 1;
    const stripped = rawLine.replace(/\r$/, '');

    if (stripped.startsWith(FENCE)) {
      if (!inCodeBlock) {
        inCodeBlock = true;
        codeBlockLines = [];
        blockStartLine = lineno + 1;
        lang = stripped.length > 3 ? stripped.slice(3).trim().split(/\s+/)[0] : '';
      } else {
        violations.push(...checkBlock(fpath, blockStartLine, lang, codeBlockLines));
        inCodeBlock = false;
        codeBlockLines = [];
      }
    } else if (inCodeBlock) {
      codeBlockLines.push([lineno, stripped]);
    }
  }

  return violations;
}

/**
 * Recursively collect all .md and .mdx files in a directory, sorted.
 *
 * @param {string} dir
 * @returns {string[]}
 */
function walkDir(dir) {
  const files = [];
  for (const entry of readdirSync(dir).sort()) {
    const full = join(dir, entry);
    if (statSync(full).isDirectory()) {
      files.push(...walkDir(full));
    } else if (extname(entry) === '.md' || extname(entry) === '.mdx') {
      files.push(full);
    }
  }
  return files;
}

const allViolations = [];

for (const fpath of walkDir(DOCS_DIR)) {
  allViolations.push(...checkFile(fpath));
}

if (!allViolations.length) {
  console.log('✅ All code blocks use correct 2-space indentation.');
  process.exit(0);
}

console.error(`❌ Found ${allViolations.length} indentation violation(s):\n`);

let currentFile = null;
for (const v of allViolations) {
  const rel = relative(DOCS_DIR, v.file);
  if (rel !== currentFile) {
    console.error(`  ${rel}`);
    currentFile = rel;
  }
  console.error(`    line ${String(v.line).padStart(4)} (${v.lang || 'no lang'}): ${v.reason}`);
  if (v.content !== null) {
    console.error(`           ${JSON.stringify(v.content)}`);
  }
}

process.exit(1);
