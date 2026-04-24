#!/usr/bin/env node
/**
 * generate-cookbook-index.mjs
 *
 * Reads every recipe `.mdx` file under `docs/cookbook/`, parses its
 * frontmatter with gray-matter, and writes the result to
 * `docs/static/cookbook-index.json`.
 *
 * The JSON is fetched at runtime by `CookbookIndex.jsx` to power the
 * client-side search and filter UI on `/cookbook`.
 *
 * Runs automatically via the `prebuild` and `prestart` npm lifecycle hooks
 * defined in `docs/package.json` — no manual invocation needed in most cases.
 *
 * Manual usage (from the `docs/` directory):
 *   node tools/generate-cookbook-index.mjs
 */

import { readFileSync, writeFileSync, mkdirSync } from 'fs';
import { join, relative, dirname } from 'path';
import { fileURLToPath } from 'url';
import fg from 'fast-glob';
import matter from 'gray-matter';

const __dirname = dirname(fileURLToPath(import.meta.url));
const docsRoot   = join(__dirname, '..');
const cookbookDir = join(docsRoot, 'cookbook');
const outputPath  = join(docsRoot, 'static', 'cookbook-index.json');

/**
 * Derives a URL-safe slug from a file path relative to the cookbook directory.
 * Back-slashes (Windows) are normalised to forward slashes.
 *
 * @param {string} filePath - Absolute path to the recipe file.
 * @returns {string} Slug, e.g. `"css/inject-inline-css"`.
 */
function toSlug(filePath) {
  return relative(cookbookDir, filePath)
    .replace(/\\/g, '/')
    .replace(/\.mdx?$/, '');
}

async function main() {
  const files = await fg('**/*.mdx', {
    cwd: cookbookDir,
    ignore: ['index.mdx', 'recipe-template.mdx'],
    absolute: true,
  });

  files.sort();

  const recipes = files.map((filePath) => {
    const raw = readFileSync(filePath, 'utf8');
    const { data: frontmatter } = matter(raw);
    const slug = toSlug(filePath);

    return {
      slug,
      url:         `/cookbook/${slug}`,
      title:       frontmatter.title       ?? '',
      description: frontmatter.description ?? '',
      tags:        frontmatter.tags        ?? [],
      components:  frontmatter.components  ?? [],
      difficulty:  frontmatter.difficulty  ?? '',
    };
  });

  const manifest = {
    generated: new Date().toISOString(),
    count: recipes.length,
    recipes,
  };

  mkdirSync(dirname(outputPath), { recursive: true });
  writeFileSync(outputPath, JSON.stringify(manifest, null, 2), 'utf8');
  console.log(`✅ Cookbook index generated: ${recipes.length} recipe(s) → ${outputPath}`);
}

main().catch((err) => {
  console.error('❌ Failed to generate cookbook index:', err);
  process.exit(1);
});
