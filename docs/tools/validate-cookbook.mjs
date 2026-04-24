#!/usr/bin/env node
/**
 * validate-cookbook.mjs
 *
 * Validates the frontmatter schema of cookbook recipe files.
 * Intended for use in CI on pull requests that touch `docs/cookbook/`.
 *
 * Exit codes:
 *   0 — all validated recipes are valid (or no changed files found)
 *   1 — one or more recipes have schema errors
 *
 * Usage (from the `docs/` directory):
 *   node tools/validate-cookbook.mjs              # validate every recipe
 *   node tools/validate-cookbook.mjs --changed    # validate only git-changed files
 */

import { readFileSync } from 'fs';
import { join, relative, dirname } from 'path';
import { fileURLToPath } from 'url';
import { execSync as exec } from 'child_process';
import fg from 'fast-glob';
import matter from 'gray-matter';

const __dirname = dirname(fileURLToPath(import.meta.url));
const docsRoot   = join(__dirname, '..');
const cookbookDir = join(docsRoot, 'cookbook');

/** Full set of recognised tag values — must match the list in recipe-template.mdx. */
const ALLOWED_TAGS = new Set([
  'css', 'styling', 'routing', 'parameters', 'table', 'forms', 'layout',
  'navigation', 'data-binding', 'validation', 'components', 'dialog',
  'notification', 'grid', 'list', 'tree', 'toolbar', 'upload', 'chart',
  'theme', 'i18n', 'security', 'spring', 'performance', 'testing',
]);

const ALLOWED_DIFFICULTIES = new Set(['beginner', 'intermediate', 'advanced']);

/** Fields that every recipe must provide. */
const REQUIRED_FIELDS = ['title', 'description', 'tags', 'difficulty'];

/** Returns true when the MDX content contains at least one fenced code block. */
function hasFencedCodeBlock(content) {
  return /^\s*```\w/m.test(content);
}

/**
 * Validates a single recipe file and returns its path (relative to docsRoot)
 * together with any error messages.
 *
 * @param {string} filePath - Absolute path to the `.mdx` recipe file.
 * @returns {{ rel: string, errors: string[] }}
 */
function validateRecipe(filePath) {
  const errors = [];
  const raw = readFileSync(filePath, 'utf8');
  const { data: fm, content } = matter(raw);
  const rel = relative(docsRoot, filePath).replace(/\\/g, '/');

  // Required field presence
  for (const field of REQUIRED_FIELDS) {
    if (fm[field] === undefined || fm[field] === null || fm[field] === '') {
      errors.push(`Missing required field: "${field}"`);
    }
  }

  // title — must be a string
  if (fm.title !== undefined && typeof fm.title !== 'string') {
    errors.push('"title" must be a string');
  }

  // description — must be a string, max 160 chars
  if (fm.description !== undefined) {
    if (typeof fm.description !== 'string') {
      errors.push('"description" must be a string');
    } else if (fm.description.length > 160) {
      errors.push(`"description" exceeds 160 characters (${fm.description.length})`);
    }
  }

  // tags — must be an array of known values
  if (fm.tags !== undefined) {
    if (!Array.isArray(fm.tags)) {
      errors.push('"tags" must be an array');
    } else {
      const unknown = fm.tags.filter((t) => !ALLOWED_TAGS.has(t));
      if (unknown.length > 0) {
        errors.push(
          `Unknown tag(s): ${unknown.map((t) => `"${t}"`).join(', ')}. ` +
          `Allowed: ${[...ALLOWED_TAGS].sort().join(', ')}`,
        );
      }
    }
  }

  // difficulty — must be one of the three tiers
  if (fm.difficulty !== undefined && !ALLOWED_DIFFICULTIES.has(fm.difficulty)) {
    errors.push(`"difficulty" must be one of: ${[...ALLOWED_DIFFICULTIES].join(', ')}`);
  }

  // components — optional, but must be an array of strings when present
  if (fm.components !== undefined) {
    if (!Array.isArray(fm.components)) {
      errors.push('"components" must be an array (use [] if not component-specific)');
    } else if (fm.components.some((c) => typeof c !== 'string')) {
      errors.push('"components" entries must be strings');
    }
  }

  // At least one fenced code block is required
  if (!hasFencedCodeBlock(content)) {
    errors.push('No fenced code block found — at least one Java code example is required');
  }

  return { rel, errors };
}

/**
 * Returns the list of cookbook recipe files that were added, copied, or
 * modified in the last commit.  Falls back to an empty array when git is
 * unavailable or no matching files were changed.
 *
 * @returns {Promise<string[]>} Absolute paths to changed recipe files.
 */
async function getChangedFiles() {
  try {
    const output = exec('git diff --name-only --diff-filter=ACM HEAD~1 HEAD', {
      cwd: docsRoot,
      encoding: 'utf8',
    });
    return output
      .split('\n')
      .map((f) => f.trim())
      .filter((f) => f.startsWith('docs/cookbook/') && f.endsWith('.mdx'))
      .filter((f) => !f.endsWith('index.mdx') && !f.endsWith('recipe-template.mdx'))
      .map((f) => join(docsRoot, '..', f));
  } catch {
    return [];
  }
}

async function main() {
  const changedOnly = process.argv.includes('--changed');

  let files;
  if (changedOnly) {
    files = await getChangedFiles();
    if (files.length === 0) {
      console.log('No cookbook recipe files changed — skipping validation.');
      process.exit(0);
    }
  } else {
    files = await fg('**/*.mdx', {
      cwd: cookbookDir,
      ignore: ['index.mdx', 'recipe-template.mdx'],
      absolute: true,
    });
  }

  let hasErrors = false;
  for (const filePath of files.sort()) {
    const { rel, errors } = validateRecipe(filePath);
    if (errors.length > 0) {
      hasErrors = true;
      console.error(`\n❌ ${rel}`);
      errors.forEach((e) => console.error(`   • ${e}`));
    } else {
      console.log(`✅ ${rel}`);
    }
  }

  if (hasErrors) {
    console.error('\nValidation failed. Fix the errors above before merging.');
    process.exit(1);
  } else {
    console.log(`\nAll ${files.length} recipe(s) valid.`);
  }
}

main().catch((err) => {
  console.error('Unexpected error:', err);
  process.exit(1);
});
