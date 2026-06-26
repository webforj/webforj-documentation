#!/usr/bin/env node
/**
 * Compiles full Java snippets from cookbook recipes against the project
 * classpath and reports lightweight behavior warnings for patterns that often
 * compile but fail when copied or rendered.
 *
 * Usage from docs/:
 *   node tools/validate-cookbook-snippets.mjs
 *   node tools/validate-cookbook-snippets.mjs --changed
 *   node tools/validate-cookbook-snippets.mjs --warnings-as-errors
 *   node tools/validate-cookbook-snippets.mjs --keep
 *   node tools/validate-cookbook-snippets.mjs --report
 */

import { execFileSync } from 'child_process';
import {
  existsSync,
  mkdirSync,
  readFileSync,
  rmSync,
  writeFileSync,
} from 'fs';
import { dirname, join, relative, resolve } from 'path';
import { fileURLToPath } from 'url';
import fg from 'fast-glob';
import matter from 'gray-matter';

const __dirname = dirname(fileURLToPath(import.meta.url));
const docsRoot = resolve(__dirname, '..');
const repoRoot = resolve(docsRoot, '..');
const cookbookDir = join(docsRoot, 'cookbook');
const workDir = join(repoRoot, 'target', 'cookbook-snippet-check');
const classesRoot = join(workDir, 'classes');
const sourceRoot = join(workDir, 'sources');
const classpathFile = join(workDir, 'classpath.txt');
const reportFile = join(repoRoot, 'target', 'cookbook-snippet-review.md');

const args = new Set(process.argv.slice(2));
const changedOnly = args.has('--changed');
const keepGenerated = args.has('--keep');
const warningsAsErrors = args.has('--warnings-as-errors');
const verbose = args.has('--verbose');
const createReport = args.has('--report');

const JAVA_FENCE_LANGS = new Set(['java', '']);

function usage() {
  console.log(`Usage: node tools/validate-cookbook-snippets.mjs [options]

Options:
  --changed             Check only cookbook files changed in the working tree
  --warnings-as-errors  Exit non-zero when behavior warnings are found
  --keep                Keep generated sources under target/cookbook-snippet-check
  --report              Write target/cookbook-snippet-review.md
  --verbose             Print skipped Java fragments
  --help                Show this help`);
}

if (args.has('--help')) {
  usage();
  process.exit(0);
}

function normalizePath(filePath) {
  return filePath.replace(/\\/g, '/');
}

function run(command, commandArgs, options = {}) {
  return execFileSync(command, commandArgs, {
    cwd: options.cwd ?? repoRoot,
    encoding: 'utf8',
    stdio: options.stdio ?? 'pipe',
  });
}

function runFirstAvailable(candidates, commandArgs, options = {}) {
  const errors = [];
  for (const candidate of candidates) {
    try {
      return run(candidate, commandArgs, options);
    } catch (error) {
      errors.push(`${candidate}: ${error.message}`);
    }
  }

  throw new Error(
    `Unable to run any of: ${candidates.join(', ')}\n${errors.join('\n')}`,
  );
}

function mavenCandidates() {
  return process.platform === 'win32' ? ['mvn.cmd', 'mvn'] : ['mvn'];
}

function javacCandidates() {
  return process.platform === 'win32' ? ['javac.exe', 'javac'] : ['javac'];
}

function quoteWindowsCommandArg(arg) {
  if (/^[A-Za-z0-9_./:=\\-]+$/.test(arg)) {
    return arg;
  }

  return `"${arg.replace(/"/g, '""')}"`;
}

function runMaven(commandArgs, options = {}) {
  if (process.platform === 'win32') {
    const commandLine = ['mvn.cmd', ...commandArgs]
      .map(quoteWindowsCommandArg)
      .join(' ');
    return run('cmd.exe', ['/d', '/s', '/c', commandLine], options);
  }

  return run('mvn', commandArgs, options);
}

function getChangedCookbookFiles() {
  const tracked = runFirstAvailable(['git'], [
    'diff',
    '--name-only',
    '--diff-filter=ACM',
    'HEAD',
    '--',
    'docs/cookbook',
  ], { cwd: repoRoot })
    .split(/\r?\n/)
    .filter(Boolean);

  const untracked = runFirstAvailable(['git'], [
    'ls-files',
    '--others',
    '--exclude-standard',
    'docs/cookbook',
  ], { cwd: repoRoot })
    .split(/\r?\n/)
    .filter(Boolean);

  return [...new Set([...tracked, ...untracked])]
    .filter((file) => /\.mdx?$/.test(file))
    .filter((file) => !file.endsWith('index.mdx'))
    .filter((file) => !file.endsWith('recipe-template.md'))
    .filter((file) => !file.endsWith('recipe-template.mdx'))
    .map((file) => join(repoRoot, file));
}

async function getCookbookFiles() {
  if (changedOnly) {
    return getChangedCookbookFiles();
  }

  return fg('**/*.{md,mdx}', {
    cwd: cookbookDir,
    ignore: ['README.md', 'index.mdx', 'recipe-template.md', 'recipe-template.mdx'],
    absolute: true,
  });
}

function extractFencedBlocks(filePath) {
  const rel = normalizePath(relative(docsRoot, filePath));
  const lines = readFileSync(filePath, 'utf8').split(/\r?\n/);
  const blocks = [];
  let inFence = false;
  let lang = '';
  let startLine = 0;
  let buffer = [];

  for (let index = 0; index < lines.length; index += 1) {
    const line = lines[index];
    const fence = line.match(/^```(\S*)/);

    if (fence && !inFence) {
      inFence = true;
      lang = fence[1] ?? '';
      startLine = index + 1;
      buffer = [];
      continue;
    }

    if (fence && inFence) {
      blocks.push({
        rel,
        filePath,
        lang,
        startLine,
        content: buffer.join('\n'),
      });
      inFence = false;
      lang = '';
      startLine = 0;
      buffer = [];
      continue;
    }

    if (inFence) {
      buffer.push(line);
    }
  }

  return blocks;
}

function findPublicTypeName(code) {
  const match = code.match(
    /\bpublic\s+(?:final\s+|abstract\s+|sealed\s+|non-sealed\s+)?(?:class|interface|enum|record)\s+([A-Za-z_$][\w$]*)/,
  );
  return match?.[1] ?? null;
}

function slugFor(block, index) {
  return `${block.rel.replace(/[^A-Za-z0-9_.-]/g, '_')}_${block.startLine}_${index}`;
}

function refreshClasspath() {
  mkdirSync(workDir, { recursive: true });
  runMaven([
    '-q',
    '-DskipTests',
    'dependency:build-classpath',
    `-Dmdep.outputFile=${classpathFile}`,
  ], { cwd: repoRoot });

  if (!existsSync(classpathFile)) {
    throw new Error(`Maven did not write classpath file: ${classpathFile}`);
  }

  return readFileSync(classpathFile, 'utf8').trim();
}

function compileRecipeSnippets(snippets, classpath) {
  const recipeBlock = snippets[0].block;
  const recipeSlug = recipeBlock.rel.replace(/[^A-Za-z0-9_.-]/g, '_');
  const sourceDir = join(sourceRoot, recipeSlug);
  const outputDir = join(classesRoot, recipeSlug);
  mkdirSync(sourceDir, { recursive: true });
  mkdirSync(outputDir, { recursive: true });

  const sourceFiles = snippets.map((snippet) => {
    const sourceFile = join(sourceDir, `${snippet.typeName}.java`);
    writeFileSync(sourceFile, snippet.block.content, 'utf8');
    return sourceFile;
  });

  try {
    runFirstAvailable(javacCandidates(), [
      '--release',
      '21',
      '-classpath',
      classpath,
      '-d',
      outputDir,
      ...sourceFiles,
    ], { cwd: repoRoot });
    return snippets.map((snippet, index) => ({
      snippet,
      ok: true,
      sourceFile: sourceFiles[index],
      output: '',
    }));
  } catch (error) {
    const output = `${error.stdout ?? ''}${error.stderr ?? ''}`.trim();
    return snippets.map((snippet, index) => ({
      snippet,
      ok: false,
      sourceFile: sourceFiles[index],
      output,
    }));
  }
}

function routeValue(code) {
  const explicit = code.match(/@Route\s*\(\s*(?:value\s*=\s*)?"([^"]*)"/);
  if (explicit) {
    return explicit[1];
  }

  return /@Route\b/.test(code) ? '<default>' : null;
}

function behaviorWarnings(blocks) {
  const warnings = [];
  const routeOwners = new Map();

  for (const block of blocks) {
    const where = `${block.rel}:${block.startLine}`;
    const code = block.content;

    if (block.lang === 'css' && /background-color\s*:\s*yellow\b/i.test(code)) {
      warnings.push({
        rel: block.rel,
        where,
        message: 'Hardcoded yellow background is not theme-aware; prefer DWC color tokens.',
      });
    }

    if (!JAVA_FENCE_LANGS.has(block.lang)) {
      continue;
    }

    if (
      /\bnew\s+Table\s*</.test(code) &&
      !/\.set(?:Min)?Height\s*\(/.test(code) &&
      !/\.setSize\s*\(/.test(code)
    ) {
      warnings.push({
        rel: block.rel,
        where,
        message: 'Table snippet does not set a height or size, which can compile but render as an empty table.',
      });
    }

    if (/App\.getTheme\(\)\.equals\s*\(/.test(code)) {
      warnings.push({
        rel: block.rel,
        where,
        message: 'Theme comparison is not null-safe and treats custom/system themes as light or dark.',
      });
    }

    const route = routeValue(code);
    if (route) {
      const previous = routeOwners.get(route);
      if (previous) {
        warnings.push({
          rel: block.rel,
          where,
          message: `Route "${route}" is also used by ${previous}; snippets compile separately, but copied examples would conflict in one app.`,
        });
      } else {
        routeOwners.set(route, where);
      }
    }
  }

  for (const block of blocks.filter((candidate) => /\.(mdx?)$/.test(candidate.filePath))) {
    const raw = readFileSync(block.filePath, 'utf8');
    if (raw.includes('?tag=css&tag=routing') && raw.includes('getList("tag")')) {
      warnings.push({
        rel: block.rel,
        where: `${block.rel}:1`,
        message: 'Recipe describes repeated query keys with getList(), but ParametersBag keeps one value per key.',
      });
    }
  }

  return uniqueWarnings(warnings);
}

const MANUAL_CHECKS = {
  'cookbook/components/card-with-slots.md': [
    'Create a card with header, body, and footer content and verify the order.',
    'Leave the header or footer empty and verify the empty section is hidden.',
    'Check the card in both light and dark themes.',
  ],
  'cookbook/components/enum-property-on-element-composite.md': [
    'Set every enum value and inspect that the custom element receives the expected lowercase property value.',
    'Read the value back with getVariant() after each update.',
  ],
  'cookbook/components/save-feedback-toast.md': [
    'Run the success path and verify a success toast appears.',
    'Make persist() throw and verify the danger toast contains a useful message.',
  ],
  'cookbook/components/typed-custom-event-from-composite.md': [
    'Click each rating button and verify the emitted event contains the selected value.',
    'Remove the listener registration and verify later clicks no longer invoke it.',
  ],
  'cookbook/css/hide-on-small-screens.md': [
    'At more than 768px, verify the main content and sidebar are both visible.',
    'Below 768px, verify the sidebar disappears without horizontal overflow.',
  ],
  'cookbook/css/inject-inline-css.md': [
    'Verify the class is styled after the component is attached.',
    'Create multiple component instances and confirm only one style element is injected.',
    'Check the result in both light and dark themes.',
  ],
  'cookbook/forms/debounced-email-validation.md': [
    'Type an invalid email and verify validation waits about 500ms after typing stops.',
    'Enter a valid email and then clear the field; verify the invalid state clears.',
    'Navigate away while a debounce is pending and verify no delayed update runs.',
  ],
  'cookbook/forms/dialog-form-with-binding-context.md': [
    'Open the dialog and verify the fields are populated from the Contact bean.',
    'Edit both fields, save, and verify the bean and callback receive the new values.',
    'Cancel and verify the save callback is not invoked.',
  ],
  'cookbook/forms/disable-button-during-save.md': [
    'Use a deliberately slow persist() implementation and verify the disabled state and Saving label are visibly rendered.',
    'Make persist() throw and verify the button is restored by the finally block.',
    'Confirm repeated clicks cannot start overlapping saves.',
  ],
  'cookbook/javascript/call-dom-method.md': [
    'Click the button and verify the footer scrolls smoothly into view.',
    'Check that no browser console error is produced by executeJs or callJsFunction.',
  ],
  'cookbook/layout/centered-layout.md': [
    'Add a visible child and verify it is centered horizontally and vertically.',
    'Resize the parent and verify centering remains stable.',
  ],
  'cookbook/routing/block-navigation-unsaved-changes.md': [
    'Leave without editing and verify navigation proceeds immediately.',
    'Edit the field, reject the dialog, and verify navigation is canceled.',
    'Edit the field, accept the dialog, and verify navigation proceeds.',
  ],
  'cookbook/routing/dynamic-page-title.md': [
    'Open the route with a product ID and verify the browser title includes that ID.',
    'Navigate between IDs and verify the title updates each time.',
  ],
  'cookbook/routing/navigate-programmatically.md': [
    'Click the button and verify the dashboard route loads.',
    'Test the ParametersBag variant with a route that declares the matching parameter.',
  ],
  'cookbook/routing/open-in-new-tab.md': [
    'Click once and verify the report opens in a new tab with the generated route URL.',
    'Verify an unregistered route produces an empty Optional without a JavaScript error.',
  ],
  'cookbook/routing/read-query-parameter.md': [
    'Open the route with and without category and sort parameters and verify the defaults.',
    'Test the documented multi-value format and verify getList() returns every value.',
  ],
  'cookbook/table/boolean-column-renderer.md': [
    'Verify true and false rows render the intended icons and semantic colors.',
    'Add a null value if nulls are allowed and verify the chosen null behavior.',
  ],
  'cookbook/table/empty-state-message.md': [
    'Load with records and verify the table is visible and the empty state is hidden.',
    'Remove all records through the repository and verify the empty state appears.',
    'Add a record again and verify the table returns.',
  ],
  'cookbook/table/persist-column-widths.md': [
    'Verify the table has nonzero width and height and renders both rows.',
    'Resize each column, reload the page, and verify every saved width is restored.',
    'Put an invalid width in LocalStorage and verify it is removed without breaking the table.',
  ],
  'cookbook/theme/app-theme-toggle.md': [
    'Start in light and dark themes and verify the icon, tooltip, and next toggle action.',
    'Start with the system theme and verify the recipe does not silently replace the user preference.',
    'Verify the control has an accessible name that describes the action.',
  ],
};

function fallbackManualChecks(blocks) {
  const checks = [
    'Copy the full snippet into a minimal view and verify it loads without server or browser console errors.',
  ];

  if (blocks.some((block) => block.lang === 'css')) {
    checks.push('Check the visual result in light and dark themes.');
  }
  if (blocks.some((block) => /@Route\b/.test(block.content))) {
    checks.push('Open the route directly and navigate away and back.');
  }

  return checks;
}

function writeReviewReport(files, blocks, snippets, fragments, results, warnings) {
  const recipes = files.map((filePath) => {
    const rel = normalizePath(relative(docsRoot, filePath));
    const raw = readFileSync(filePath, 'utf8');
    const { data } = matter(raw);
    const recipeBlocks = blocks.filter((block) => block.rel === rel);
    const recipeSnippets = snippets.filter((snippet) => snippet.block.rel === rel);
    const recipeFragments = fragments.filter((fragment) => fragment.rel === rel);
    const recipeResults = results.filter((result) => result.snippet.block.rel === rel);
    const recipeWarnings = warnings.filter((warning) => warning.rel === rel);

    return {
      rel,
      title: data.title ?? rel,
      blocks: recipeBlocks,
      snippets: recipeSnippets,
      fragments: recipeFragments,
      results: recipeResults,
      warnings: recipeWarnings,
      checks: MANUAL_CHECKS[rel] ?? fallbackManualChecks(recipeBlocks),
    };
  });

  recipes.sort((left, right) => {
    const warningOrder = right.warnings.length - left.warnings.length;
    return warningOrder || left.rel.localeCompare(right.rel);
  });

  const failures = results.filter((result) => !result.ok);
  const lines = [
    '# Cookbook Snippet Review',
    '',
    `Generated: ${new Date().toISOString()}`,
    '',
    `- Recipes: ${recipes.length}`,
    `- Full Java snippets compiled: ${results.length}`,
    `- Compile failures: ${failures.length}`,
    `- Behavior warnings: ${warnings.length}`,
    `- Java fragments requiring visual review: ${fragments.length}`,
    '',
    'Run again from `docs/` with:',
    '',
    '```powershell',
    'npm run review:cookbook-snippets',
    '```',
    '',
  ];

  if (warnings.length > 0) {
    lines.push('## Priority Review', '');
    for (const warning of warnings) {
      lines.push(`- [ ] [${warning.where}](../docs/${warning.rel}) - ${warning.message}`);
    }
    lines.push('');
  }

  lines.push('## Recipe Checklist', '');
  for (const recipe of recipes) {
    const compileFailures = recipe.results.filter((result) => !result.ok);
    const compileStatus = compileFailures.length > 0
      ? `FAIL (${compileFailures.length})`
      : recipe.results.length > 0
        ? `PASS (${recipe.results.length})`
        : 'NO FULL JAVA SNIPPET';
    const docsPath = `/${recipe.rel.replace(/\.(md|mdx)$/, '')}`;

    lines.push(
      `### ${recipe.title}`,
      '',
      `Source: [${recipe.rel}](../docs/${recipe.rel})`,
      '',
      `Docs path: \`${docsPath}\``,
      '',
      `Compile: **${compileStatus}**`,
      '',
    );

    if (recipe.warnings.length > 0) {
      lines.push('Detected risks:', '');
      for (const warning of recipe.warnings) {
        lines.push(`- [ ] ${warning.message}`);
      }
      lines.push('');
    }

    if (recipe.fragments.length > 0) {
      lines.push(
        `Java fragments skipped by compilation: ${recipe.fragments.map((fragment) => `line ${fragment.startLine}`).join(', ')}`,
        '',
      );
    }

    lines.push('Manual checks:', '');
    for (const check of recipe.checks) {
      lines.push(`- [ ] ${check}`);
    }
    lines.push('');
  }

  mkdirSync(dirname(reportFile), { recursive: true });
  writeFileSync(reportFile, `${lines.join('\n')}\n`, 'utf8');
  return reportFile;
}

function uniqueWarnings(warnings) {
  const seen = new Set();
  return warnings.filter((warning) => {
    const key = `${warning.where}\0${warning.message}`;
    if (seen.has(key)) {
      return false;
    }
    seen.add(key);
    return true;
  });
}

function printCompileFailure(result) {
  console.error(`\nFAIL ${result.snippet.block.rel}:${result.snippet.block.startLine} ${result.snippet.typeName}`);
  console.error(`Generated source: ${result.sourceFile}`);
  console.error(result.output || '(javac produced no output)');
}

async function main() {
  const files = await getCookbookFiles();
  if (files.length === 0) {
    console.log(changedOnly
      ? 'No changed cookbook recipe files found.'
      : 'No cookbook recipe files found.');
    return;
  }

  rmSync(workDir, { recursive: true, force: true });
  mkdirSync(workDir, { recursive: true });

  const blocks = files.flatMap(extractFencedBlocks);
  const javaBlocks = blocks.filter((block) => JAVA_FENCE_LANGS.has(block.lang));
  const snippets = [];
  const fragments = [];

  javaBlocks.forEach((block, index) => {
    const typeName = findPublicTypeName(block.content);
    if (typeName) {
      snippets.push({ block, index, typeName });
    } else {
      fragments.push(block);
    }
  });

  if (snippets.length === 0) {
    console.error('No full Java snippets found to compile.');
    process.exitCode = 1;
    return;
  }

  console.log(`Found ${files.length} recipe file(s), ${snippets.length} full Java snippet(s), ${fragments.length} Java fragment(s).`);
  if (verbose && fragments.length > 0) {
    for (const fragment of fragments) {
      console.log(`SKIP ${fragment.rel}:${fragment.startLine} fragment`);
    }
  }

  const classpath = refreshClasspath();
  const snippetsByRecipe = new Map();
  for (const snippet of snippets) {
    const recipeSnippets = snippetsByRecipe.get(snippet.block.rel) ?? [];
    recipeSnippets.push(snippet);
    snippetsByRecipe.set(snippet.block.rel, recipeSnippets);
  }
  const results = [...snippetsByRecipe.values()].flatMap(
    (recipeSnippets) => compileRecipeSnippets(recipeSnippets, classpath),
  );

  const failures = results.filter((result) => !result.ok);
  for (const result of results.filter((candidate) => candidate.ok)) {
    console.log(`OK ${result.snippet.block.rel}:${result.snippet.block.startLine} ${result.snippet.typeName}`);
  }
  failures.forEach(printCompileFailure);

  const warnings = behaviorWarnings(blocks);
  if (warnings.length > 0) {
    console.log('\nBehavior warnings:');
    for (const warning of warnings) {
      console.log(`WARN ${warning.where} ${warning.message}`);
    }
  }

  console.log(`\nCompiled ${results.length} full Java snippet(s); failures: ${failures.length}; warnings: ${warnings.length}.`);

  if (createReport) {
    const generatedReport = writeReviewReport(
      files,
      blocks,
      snippets,
      fragments,
      results,
      warnings,
    );
    console.log(`Review checklist written to ${generatedReport}`);
  }

  if (!keepGenerated) {
    rmSync(workDir, { recursive: true, force: true });
  } else {
    console.log(`Generated files kept at ${workDir}`);
  }

  if (failures.length > 0 || (warningsAsErrors && warnings.length > 0)) {
    process.exitCode = 1;
  }
}

main().catch((error) => {
  if (!keepGenerated) {
    rmSync(workDir, { recursive: true, force: true });
  }
  console.error(error.stack ?? error.message);
  process.exit(1);
});
