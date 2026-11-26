import dotenv from 'dotenv';
import path from 'path';
import { execFileSync } from 'child_process';

// Load environment variables from docs/.env
dotenv.config({ path: path.resolve(process.cwd(), '.env') });

import { options, whitelist } from './config';
import { loadSiteConfig } from './utils';
import fg from 'fast-glob';
import matter from 'gray-matter';
import fs from 'fs-extra';
import md5 from 'md5';
import { translateBatch, translateDescription, translateTitle, TranslationResult, TranslationTask } from './translate';
import { validatePlaceholders, validateTranslatedMdx } from './validate';

const siteDir = options.project;

// Check for force translate via environment variable or command line
const forceTranslate = process.env.FORCE_TRANSLATE === 'true' ||
                       process.argv.includes('--force-retranslate') ||
                       process.argv.includes('force');

// Check for titles-only mode
const titlesOnly = process.argv.includes('--titles-only') ||
                   process.argv.includes('titles-only');

if (forceTranslate) {
  console.log('Force mode enabled - retranslating all content regardless of changes\n');
}

if (titlesOnly) {
  console.log('Titles-only mode enabled - updating only the front matter of translated files\n');
}

// Track if any translations were made across all locales
let hasChanges = false;

// Per-string hash tracking utilities
interface StringHashes {
  [key: string]: string;  // key -> hash of English content
}

function loadStringHashes(locale: string): StringHashes {
  const hashesPath = path.resolve(siteDir, './i18n', locale, '_hashes.json');
  
  if (!fs.existsSync(hashesPath)) {
    return {};
  }
  
  try {
    const hashesContent = fs.readFileSync(hashesPath, 'utf-8');
    return JSON.parse(hashesContent);
  } catch (error) {
    console.warn(`Failed to load hashes for ${locale}:`, error);
    return {};
  }
}

function saveStringHashes(locale: string, newHashes: StringHashes): void {
  const hashesPath = path.resolve(siteDir, './i18n', locale, '_hashes.json');
  const hashesDir = path.dirname(hashesPath);
  
  try {
    fs.ensureDirSync(hashesDir);
    
    // Load existing hashes and merge with new ones
    const existingHashes = loadStringHashes(locale);
    const mergedHashes = { ...existingHashes, ...newHashes };
    
    const hashesString = JSON.stringify(mergedHashes, null, 2).replace(/\r\n/g, '\n');
    fs.writeFileSync(hashesPath, hashesString);
  } catch (error) {
    console.error(`Failed to save hashes for ${locale}:`, error);
  }
}

// Drops hashes for strings whose translation failed so the next run retries them
function withoutFailedKeys(hashes: StringHashes, results: TranslationResult[]): StringHashes {
  const kept = { ...hashes };
  for (const result of results) {
    if (result.error && result.key) {
      delete kept[result.key];
    }
  }
  return kept;
}

function hashString(content: string): string {
  // Normalize content before hashing for consistency
  const normalized = content.replace(/\r\n/g, '\n').replace(/\r/g, '\n').trim();
  return md5(normalized);
}

async function main() {
  const config = await loadSiteConfig(siteDir);
  if (!config.i18n) {
    throw new Error(
      'Run `docusaurus-i18n` need docusaurus.config.js has i18n config'
    );
  }

  const { defaultLocale, locales } = config.i18n;
  const targetLocales = locales.filter((l) => l !== defaultLocale);

  if (targetLocales.length === 0) {
    throw new Error(
      'Please make sure you have more than one locale exclude default locale'
    );
  }

  for (const locale of targetLocales) {
    console.group(`Translating ${locale}:`);

    if (titlesOnly) {
      // Only translate titles in existing translated files
      await translateDocsTitlesOnly(locale);
    } else {
      // Normal translation flow
      await translateDocs(locale);
      await translateJSON(
        locale,
        path.resolve(
          siteDir,
          './i18n',
          locale,
          './docusaurus-plugin-content-docs/current.json'
        ),
        ['sidebar.documentationSidebar.category.']
      );
      await translateJSON(
        locale,
        path.resolve(
          siteDir,
          './i18n',
          locale,
          './docusaurus-theme-classic/footer.json'
        ),
        ['link.title.', 'link.item.label.']
      );
      await translateJSON(
        locale,
        path.resolve(
          siteDir,
          './i18n',
          locale,
          './docusaurus-theme-classic/navbar.json'
        ),
        ['item.label.']
      );

      // Translate the main code.json file containing all translation strings
      await translateCodeJSON(locale);
    }

    console.groupEnd();
  }

  // Exit based on whether any changes were made
  if (!hasChanges) {
    console.log('\nNo translations needed - all content is up to date');
    process.exit(1); // Exit with code 1 when no changes needed
  }

  console.log('\nTranslation completed with changes');
  process.exit(0); // Exit with code 0 when changes were made
}

// Front matter fields that are translated. Each one stores a hash of its
// English source next to it and is translated again only when that changes.
const TRANSLATED_FIELDS = ['title', 'sidebar_label', 'description'] as const;
type TranslatedField = (typeof TRANSLATED_FIELDS)[number];
const fieldHashKey = (field: TranslatedField) => `_i18n_${field}_hash`;
// Stored instead of a hash when a translation failed, so the next run retries
const PENDING_HASH = 'pending';

function git(args: string[]): string {
  try {
    return execFileSync('git', args, {
      cwd: siteDir,
      encoding: 'utf-8',
      stdio: ['ignore', 'pipe', 'ignore'],
    }).trim();
  } catch {
    return '';
  }
}

let fullHistory: boolean | undefined;

function hasFullHistory(): boolean {
  if (fullHistory === undefined) {
    fullHistory = git(['rev-parse', '--is-shallow-repository']) === 'false';
    if (!fullHistory) {
      console.log('  No full git history available: fields without a stored hash are translated again.');
    }
  }
  return fullHistory;
}

const commitTimes = new Map<string, number>();

function lastCommitTime(args: string[]): number {
  const key = args.join(' ');
  let time = commitTimes.get(key);
  if (time === undefined) {
    time = parseInt(git(['log', '-1', '--format=%ct', ...args]), 10) || 0;
    commitTimes.set(key, time);
  }
  return time;
}

/**
 * Decides whether a translation made before hashes were stored is still
 * current: the translated file must have been written after the last change
 * of that field in the English source. Without full history, or with an
 * uncommitted change of the field, it counts as outdated.
 */
function isCurrentByHistory(field: TranslatedField, sourcePath: string, translatedPath: string): boolean {
  if (!hasFullHistory()) {
    return false;
  }
  const fieldLine = `^${field}:`;
  if (git(['diff', 'HEAD', '--name-only', '-G', fieldLine, '--', sourcePath])) {
    return false;
  }
  const fieldChanged = lastCommitTime(['-G', fieldLine, '--', sourcePath]);
  const translated = lastCommitTime(['--', translatedPath]);
  return fieldChanged > 0 && translated >= fieldChanged;
}

/**
 * Builds the front matter of a translated file. Fields that aren't translated
 * always follow the English source. A translated field keeps its translation
 * while its source is unchanged, and is translated otherwise.
 */
async function buildFrontmatter(
  file: string,
  locale: string,
  sourceFrontmatter: Record<string, any>,
  previousFrontmatter: Record<string, any> | null,
  sourcePath: string,
  translatedPath: string
): Promise<{ frontmatter: Record<string, any>; tokens: number }> {
  const frontmatter: Record<string, any> = { ...sourceFrontmatter };
  let tokens = 0;

  for (const field of TRANSLATED_FIELDS) {
    const source = sourceFrontmatter[field];
    if (typeof source !== 'string' || !source.trim()) {
      continue;
    }

    const hashKey = fieldHashKey(field);
    const hash = md5(source.trim());
    const translated = previousFrontmatter?.[field];
    const storedHash = previousFrontmatter?.[hashKey];
    const hasTranslation = typeof translated === 'string' && translated.trim() !== '';

    if (hasTranslation && !forceTranslate) {
      // Descriptions were never translated before hashes existed, so one
      // without a hash is still English
      const current =
        storedHash === hash ||
        (storedHash === undefined &&
          field !== 'description' &&
          isCurrentByHistory(field, sourcePath, translatedPath));
      if (current) {
        frontmatter[field] = translated;
        frontmatter[hashKey] = hash;
        continue;
      }
    }

    try {
      console.log(`  Translating ${field} for: ${file}`);
      const result = field === 'description'
        ? await translateDescription(source, locale)
        : await translateTitle(source, locale);
      if (!result.translatedText) {
        throw new Error('Empty translation');
      }
      frontmatter[field] = result.translatedText;
      frontmatter[hashKey] = hash;
      tokens += result.usage?.total_tokens ?? 0;
    } catch (error) {
      // Keep what the page had and store no valid hash, so the next run retries
      console.warn(`  Failed to translate ${field} for ${file}: ${error}`);
      if (hasTranslation) {
        frontmatter[field] = translated;
      }
      frontmatter[hashKey] = PENDING_HASH;
      if (process.env.SKIPPED_REPORT) {
        fs.appendFileSync(
          process.env.SKIPPED_REPORT,
          `${locale}/${file} (${field}): ${String(error).replace(/\s+/g, ' ')}\n`
        );
      }
    }
  }

  return { frontmatter, tokens };
}

async function translateDocs(locale: string) {
  const docsDir = path.resolve(siteDir, './docs');
  const files = await fg(['**/*.md', '**/*.mdx'], {
    cwd: docsDir,
    onlyFiles: true,
  });

  // Prepare tasks for batch translation
  const tasks: TranslationTask[] = [];
  const fileInfoMap = new Map<string, { 
    filePath: string; 
    targetPath: string; 
    targetDir: string; 
    frontmatter: any; 
    hash: string 
  }>();

  for (const file of files) {
    const filePath = path.resolve(docsDir, file);
    const targetDir = path.resolve(
      siteDir,
      './i18n',
      locale,
      './docusaurus-plugin-content-docs/current',
      path.dirname(file)
    );
    const targetPath = path.resolve(targetDir, path.basename(file));

    const fileContent = fs.readFileSync(filePath, 'utf-8');
    const { data: frontmatter, content } = matter(fileContent);
    // Normalize content before hashing to ensure consistency across platforms
    const normalizedContent = content.replace(/\r\n/g, '\n').replace(/\r/g, '\n');
    const hash = md5(normalizedContent);

    if (!forceTranslate && fs.existsSync(targetPath)) {
      const targetContent = fs.readFileSync(targetPath, 'utf-8');
      const { data: targetFrontmatter } = matter(targetContent);

      if (targetFrontmatter._i18n_hash === hash) {
        // console.log(`Skip: ${file} because not changed`);
        continue;
      }
    }

    fileInfoMap.set(file, { filePath, targetPath, targetDir, frontmatter, hash });
    tasks.push({
      content,
      locale,
      isUIString: false,
      file,
      validate: (translated) => validateTranslatedMdx(content, translated)
    });
  }

  if (tasks.length === 0) {
    console.log('  No files need translation.');
    return;
  }

  console.log(`  Translating ${tasks.length} files with batch size ${options.batchSize}...`);

  // Translate in batches with progress
  const results = await translateBatch(tasks, (completed, total) => {
    process.stdout.write(`\r  Progress: ${completed}/${total} files translated...`);
  });
  process.stdout.write('\n');

  // Process results and translate titles
  let totalTokens = 0;
  let skipped = 0;
  for (const result of results) {
    totalTokens += result.usage?.total_tokens ?? 0;
    if (result.error) {
      // Leave the existing translation (and its stale hash) so the next run retries
      skipped++;
      continue;
    }
    if (result.file) {
      const fileInfo = fileInfoMap.get(result.file);
      if (fileInfo) {
        const previousFrontmatter = fs.existsSync(fileInfo.targetPath)
          ? matter(fs.readFileSync(fileInfo.targetPath, 'utf-8')).data
          : null;
        const { frontmatter: updatedFrontmatter, tokens } = await buildFrontmatter(
          result.file,
          locale,
          fileInfo.frontmatter,
          previousFrontmatter,
          fileInfo.filePath,
          fileInfo.targetPath
        );
        totalTokens += tokens;

        // Add the hash
        updatedFrontmatter._i18n_hash = fileInfo.hash;

        const targetFileContent = matter.stringify(result.translatedText, updatedFrontmatter);

        await fs.ensureDir(fileInfo.targetDir);
        // Ensure LF line endings for consistency across platforms
        const normalizedContent = targetFileContent.replace(/\r\n/g, '\n');
        fs.writeFileSync(fileInfo.targetPath, normalizedContent);
        hasChanges = true;
      }
    }
  }

  console.log(`  Completed translation of ${results.length - skipped} files (${skipped} skipped). Total token usage: ${totalTokens}`);
}

async function translateDocsTitlesOnly(locale: string) {
  const docsDir = path.resolve(siteDir, './docs');
  const translatedDocsDir = path.resolve(
    siteDir,
    './i18n',
    locale,
    './docusaurus-plugin-content-docs/current'
  );

  // Find all translated files
  const translatedFiles = await fg(['**/*.md', '**/*.mdx'], {
    cwd: translatedDocsDir,
    onlyFiles: true,
  });

  let updatedCount = 0;
  let totalTokens = 0;

  console.log(`  Checking the front matter of ${translatedFiles.length} files...`);

  for (const file of translatedFiles) {
    const translatedPath = path.resolve(translatedDocsDir, file);
    const sourcePath = path.resolve(docsDir, file);

    // Check if source file exists
    if (!fs.existsSync(sourcePath)) {
      console.warn(`  Warning: Source file not found for ${file}, skipping`);
      continue;
    }

    // Read both source and translated files
    const sourceContent = fs.readFileSync(sourcePath, 'utf-8');
    const { data: sourceFrontmatter } = matter(sourceContent);

    const translatedContent = fs.readFileSync(translatedPath, 'utf-8');
    const { data: translatedFrontmatter, content: translatedBody } = matter(translatedContent);

    const { frontmatter, tokens } = await buildFrontmatter(
      file,
      locale,
      sourceFrontmatter,
      translatedFrontmatter,
      sourcePath,
      translatedPath
    );
    totalTokens += tokens;

    // The body is untouched here, so its hash stays as it was
    if (translatedFrontmatter._i18n_hash !== undefined) {
      frontmatter._i18n_hash = translatedFrontmatter._i18n_hash;
    }

    const updatedFileContent = matter.stringify(translatedBody, frontmatter).replace(/\r\n/g, '\n');
    if (updatedFileContent === translatedContent) {
      continue;
    }

    fs.writeFileSync(translatedPath, updatedFileContent);
    updatedCount++;
  }

  if (updatedCount > 0) {
    hasChanges = true;
    console.log(`  Updated the front matter of ${updatedCount} files. Total token usage: ${totalTokens}`);
  } else {
    console.log(`  All front matter is up to date.`);
  }
}

async function translateJSON(
  locale: string,
  jsonPath: string,
  prefixList: string[]
) {
  console.log(`  Checking json....: ${path.relative(siteDir, jsonPath)}`);

  if (!fs.existsSync(jsonPath)) {
    console.log(
      `  Skip translation json config because you need to run \`docusaurus write-translations\` first, locale: ${locale}`
    );
    return;
  }

  // Load the ENGLISH source file - handle both forward and backslashes
  const englishJsonPath = jsonPath
    .replace(`/i18n/${locale}/`, '/i18n/en/')
    .replace(`\\i18n\\${locale}\\`, '\\i18n\\en\\');
  console.log(`  Debug: Looking for English source at: ${englishJsonPath}`);
  if (!fs.existsSync(englishJsonPath)) {
    console.log(`  Warning: English source not found at ${path.relative(siteDir, englishJsonPath)}`);
    console.log(`  Falling back to translating from current content`);
    // Fall back to old behavior if English source doesn't exist
    await translateJSONOldWay(locale, jsonPath, prefixList);
    return;
  }
  console.log(`  Found English source, using per-string hashing...`);

  const englishJson = JSON.parse(fs.readFileSync(englishJsonPath, 'utf-8'));
  const targetJson = JSON.parse(fs.readFileSync(jsonPath, 'utf-8'));
  
  // Load saved hashes for this locale
  const savedHashes = loadStringHashes(locale);
  
  // Prepare tasks for batch translation
  const tasks: TranslationTask[] = [];
  const newHashes: StringHashes = {};

  for (const [key, value] of Object.entries(englishJson)) {
    const shouldTranslate = prefixList.some((prefix) => key.startsWith(prefix));
    if (!shouldTranslate) {
      continue;
    }

    const englishMessage = (value as any).message;
    if (typeof englishMessage !== 'string') {
      continue;
    }

    if (whitelist.includes(englishMessage)) {
      continue;
    }

    // A blank string has nothing to translate, and a key the target file
    // doesn't have has no place to store a translation
    if (!englishMessage.trim() || !targetJson[key]) {
      continue;
    }

    // Hash the English content
    const currentHash = hashString(englishMessage);
    newHashes[key] = currentHash;
    
    // Check if translation is needed
    if (!forceTranslate && savedHashes[key] === currentHash && targetJson[key]) {
      // Hash matches and translation exists, skip
      continue;
    }

    // Need to translate this string
    tasks.push({
      content: englishMessage,
      locale,
      isUIString: true,
      key,
      validate: async (translated) => validatePlaceholders(englishMessage, translated)
    });
  }

  if (tasks.length === 0) {
    console.log(`  All translations up to date, Skip: ${path.relative(siteDir, jsonPath)}`);
    // Still save the hashes to track current state
    saveStringHashes(locale, newHashes);
    return;
  }

  // Mark that we're making changes
  hasChanges = true;

  console.log(`  Translating ${tasks.length} UI strings (${Object.keys(newHashes).length - tasks.length} unchanged)...`);

  // Translate in batches
  const results = await translateBatch(tasks);

  // Update JSON with translations
  for (const result of results) {
    if (!result.error && result.key && targetJson[result.key]) {
      (targetJson[result.key] as any).message = result.translatedText;
    }
  }

  // Save updated translations
  const jsonString = JSON.stringify(targetJson, null, 2).replace(/\r\n/g, '\n');
  fs.writeFileSync(jsonPath, jsonString);
  
  // Save hashes after successful translation
  saveStringHashes(locale, withoutFailedKeys(newHashes, results));
  
  console.log(`  Wrote translated file: ${jsonPath}`);
}

// Keep old way as fallback for when English source doesn't exist
async function translateJSONOldWay(
  locale: string,
  jsonPath: string,
  prefixList: string[]
) {
  const jsonContent = fs.readFileSync(jsonPath, 'utf-8');
  const json = JSON.parse(jsonContent);

  const tasks: TranslationTask[] = [];
  for (const [key, value] of Object.entries(json)) {
    const shouldTranslate = prefixList.some((prefix) => key.startsWith(prefix));
    if (!shouldTranslate) continue;

    const messageValue = (value as any).message;
    if (typeof messageValue !== 'string' || whitelist.includes(messageValue)) continue;

    tasks.push({
      content: messageValue,
      locale,
      isUIString: true,
      key,
      validate: async (translated) => validatePlaceholders(messageValue, translated)
    });
  }

  if (tasks.length === 0) return;

  // Mark that we're making changes
  hasChanges = true;

  console.log(`  Translating ${tasks.length} UI strings...`);
  const results = await translateBatch(tasks);

  for (const result of results) {
    if (!result.error && result.key && json[result.key]) {
      (json[result.key] as any).message = result.translatedText;
    }
  }

  const jsonString = JSON.stringify(json, null, 2).replace(/\r\n/g, '\n');
  fs.writeFileSync(jsonPath, jsonString);
  console.log(`  Wrote translated file: ${jsonPath}`);
}

async function translateCodeJSON(locale: string) {
  const jsonPath = path.resolve(siteDir, './i18n', locale, './code.json');
  console.log(`  Checking code.json: ${path.relative(siteDir, jsonPath)}`);

  if (!fs.existsSync(jsonPath)) {
    console.log(
      `  Skip translation of code.json because you need to run \`docusaurus write-translations\` first, locale: ${locale}`
    );
    return;
  }

  // Load the ENGLISH source file
  const englishJsonPath = path.resolve(siteDir, './i18n/en/code.json');
  console.log(`  Debug: Looking for English code.json at: ${englishJsonPath}`);
  if (!fs.existsSync(englishJsonPath)) {
    console.log(`  Warning: English code.json not found at ${path.relative(siteDir, englishJsonPath)}`);
    console.log(`  Falling back to detecting English strings in target file`);
    // Fall back to old behavior if English source doesn't exist
    await translateCodeJSONOldWay(locale, jsonPath);
    return;
  }
  console.log(`  Found English source, using per-string hashing...`);

  const englishJson = JSON.parse(fs.readFileSync(englishJsonPath, 'utf-8'));
  const targetJson = JSON.parse(fs.readFileSync(jsonPath, 'utf-8'));
  
  // Load saved hashes for this locale
  const savedHashes = loadStringHashes(locale);
  
  // Prepare tasks for batch translation
  const tasks: TranslationTask[] = [];
  const newHashes: StringHashes = {};

  for (const [key, value] of Object.entries(englishJson)) {
    const englishMessage = (value as any).message;
    if (typeof englishMessage !== 'string') {
      continue;
    }

    // Skip if in whitelist (technical terms that shouldn't be translated)
    if (whitelist.includes(englishMessage)) {
      continue;
    }

    // A blank string has nothing to translate, and a key the target file
    // doesn't have has no place to store a translation
    if (!englishMessage.trim() || !targetJson[key]) {
      continue;
    }

    // Hash the English content
    const currentHash = hashString(englishMessage);
    newHashes[key] = currentHash;
    
    // Check if translation is needed
    if (!forceTranslate && savedHashes[key] === currentHash && targetJson[key]) {
      // Hash matches and translation exists, skip
      continue;
    }

    // Need to translate this string
    tasks.push({
      content: englishMessage,
      locale,
      isUIString: true,
      key,
      validate: async (translated) => validatePlaceholders(englishMessage, translated)
    });
  }

  if (tasks.length === 0) {
    console.log(`  All translations up to date, Skip: ${path.relative(siteDir, jsonPath)}`);
    // Still save the hashes to track current state
    saveStringHashes(locale, newHashes);
    return;
  }

  // Mark that we're making changes
  hasChanges = true;

  console.log(`  Translating ${tasks.length} UI strings in code.json (${Object.keys(newHashes).length - tasks.length} unchanged)...`);

  // Translate in batches
  const results = await translateBatch(tasks);

  // Update JSON with translations
  for (const result of results) {
    if (!result.error && result.key && targetJson[result.key]) {
      (targetJson[result.key] as any).message = result.translatedText;
    }
  }

  // Save updated translations
  const codeJsonString = JSON.stringify(targetJson, null, 2).replace(/\r\n/g, '\n');
  fs.writeFileSync(jsonPath, codeJsonString);
  
  // Save hashes after successful translation
  saveStringHashes(locale, withoutFailedKeys(newHashes, results));
  
  console.log(`  Wrote translated file: ${jsonPath}`);
}

// Keep old way as fallback for when English source doesn't exist
async function translateCodeJSONOldWay(locale: string, jsonPath: string) {
  const jsonContent = fs.readFileSync(jsonPath, 'utf-8');
  const json = JSON.parse(jsonContent);

  const tasks: TranslationTask[] = [];
  for (const [key, value] of Object.entries(json)) {
    const messageValue = (value as any).message;
    if (typeof messageValue !== 'string') continue;

    // Skip if already translated (not in English)
    const isEnglish = /\b(the|and|or|in|on|at|to|for|of|with|by)\b/i.test(messageValue) ||
                     /^[A-Z][a-z]+ [A-Z][a-z]+/.test(messageValue) ||
                     messageValue === 'Close' ||
                     messageValue === 'Search' ||
                     messageValue === 'Draft' ||
                     messageValue.startsWith('Show ') ||
                     messageValue.startsWith('Hide ') ||
                     messageValue.includes('Navigate to') ||
                     messageValue.includes('After running');
    
    if (!isEnglish || whitelist.includes(messageValue)) continue;

    tasks.push({
      content: messageValue,
      locale,
      isUIString: true,
      key,
      validate: async (translated) => validatePlaceholders(messageValue, translated)
    });
  }

  if (tasks.length === 0) return;

  // Mark that we're making changes
  hasChanges = true;

  console.log(`  Translating ${tasks.length} UI strings in code.json...`);
  const results = await translateBatch(tasks);

  for (const result of results) {
    if (!result.error && result.key && json[result.key]) {
      (json[result.key] as any).message = result.translatedText;
    }
  }

  const codeJsonString = JSON.stringify(json, null, 2).replace(/\r\n/g, '\n');
  fs.writeFileSync(jsonPath, codeJsonString);
  console.log(`  Wrote translated file: ${jsonPath}`);
}

// Exit 2 on a crash so the workflow can tell it apart from "no changes" (exit 1)
main().catch((error) => {
  console.error(error);
  process.exit(2);
});