#!/usr/bin/env node

const path = require('path');
const { collectSnippets } = require('./extract');

const siteDir = path.resolve(__dirname, '../..');
const repoRoot = path.resolve(siteDir, '..');

try {
  const { snippets, references } = collectSnippets(siteDir, { repoRoot });
  const uniqueCount = Object.keys(snippets).length;
  console.log(
    `Resolved ${references.length} SourceSnippet reference${references.length === 1 ? '' : 's'} ` +
      `across ${uniqueCount} source region${uniqueCount === 1 ? '' : 's'}.`
  );
} catch (error) {
  console.error(error.message);
  process.exitCode = 1;
}
