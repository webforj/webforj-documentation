import dotenv from 'dotenv';
import path from 'path';

// Load environment variables from docs/.env
dotenv.config({ path: path.resolve(process.cwd(), '.env') });

import { options, whitelist } from './config';
import { loadSiteConfig } from './utils';
import fg from 'fast-glob';
import matter from 'gray-matter';
import fs from 'fs-extra';
import md5 from 'md5';
import { translate, translateBatch, translateTitle, TranslationTask } from './translate';
import os from 'os';

const siteDir = options.project;

// Check for force translate via environment variable or command line
const forceTranslate = process.env.FORCE_TRANSLATE === 'true' ||
                       process.argv.includes('--force-retranslate') ||
                       process.argv.includes('force');

if (forceTranslate) {
  console.log('Force mode enabled - retranslating all content regardless of changes\n');
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
      file
    });
  }

  if (tasks.length === 0) {
    console.log('  No files need translation.');
    return;
  }

  // Mark that we're making changes
  hasChanges = true;

  console.log(`  Translating ${tasks.length} files with batch size ${options.batchSize}...`);

  // Translate in batches with progress
  const results = await translateBatch(tasks, (completed, total) => {
    process.stdout.write(`\r  Progress: ${completed}/${total} files translated...`);
  });
  process.stdout.write('\n');

  // Process results and translate titles
  let totalTokens = 0;
  for (const result of results) {
    if (result.file) {
      const fileInfo = fileInfoMap.get(result.file);
      if (fileInfo) {
        // Start with original frontmatter
        const updatedFrontmatter = { ...fileInfo.frontmatter };

        // Translate the title if it exists
        if (fileInfo.frontmatter.title && typeof fileInfo.frontmatter.title === 'string') {
          try {
            const titleResult = await translateTitle(fileInfo.frontmatter.title, locale);
            updatedFrontmatter.title = titleResult.translatedText;
            if (titleResult.usage?.total_tokens) {
              totalTokens += titleResult.usage.total_tokens;
            }
          } catch (error) {
            console.warn(`  Failed to translate title for ${result.file}: ${error}`);
            // Keep original title on error
          }
        }

        // Add the hash
        updatedFrontmatter._i18n_hash = fileInfo.hash;

        const targetFileContent = matter.stringify(result.translatedText, updatedFrontmatter);

        await fs.ensureDir(fileInfo.targetDir);
        // Ensure LF line endings for consistency across platforms
        const normalizedContent = targetFileContent.replace(/\r\n/g, '\n');
        fs.writeFileSync(fileInfo.targetPath, normalizedContent);

        if (result.usage?.total_tokens) {
          totalTokens += result.usage.total_tokens;
        }
      }
    }
  }

  console.log(`  Completed translation of ${results.length} files. Total token usage: ${totalTokens}`);
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
      key
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
    if (result.key && targetJson[result.key]) {
      (targetJson[result.key] as any).message = result.translatedText;
    }
  }

  // Save updated translations
  const jsonString = JSON.stringify(targetJson, null, 2).replace(/\r\n/g, '\n');
  fs.writeFileSync(jsonPath, jsonString);
  
  // Save hashes after successful translation
  saveStringHashes(locale, newHashes);
  
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
      key
    });
  }

  if (tasks.length === 0) return;

  // Mark that we're making changes
  hasChanges = true;

  console.log(`  Translating ${tasks.length} UI strings...`);
  const results = await translateBatch(tasks);

  for (const result of results) {
    if (result.key && json[result.key]) {
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
      key
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
    if (result.key && targetJson[result.key]) {
      (targetJson[result.key] as any).message = result.translatedText;
    }
  }

  // Save updated translations
  const codeJsonString = JSON.stringify(targetJson, null, 2).replace(/\r\n/g, '\n');
  fs.writeFileSync(jsonPath, codeJsonString);
  
  // Save hashes after successful translation
  saveStringHashes(locale, newHashes);
  
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
      key
    });
  }

  if (tasks.length === 0) return;

  // Mark that we're making changes
  hasChanges = true;

  console.log(`  Translating ${tasks.length} UI strings in code.json...`);
  const results = await translateBatch(tasks);

  for (const result of results) {
    if (result.key && json[result.key]) {
      (json[result.key] as any).message = result.translatedText;
    }
  }

  const codeJsonString = JSON.stringify(json, null, 2).replace(/\r\n/g, '\n');
  fs.writeFileSync(jsonPath, codeJsonString);
  console.log(`  Wrote translated file: ${jsonPath}`);
}

main().catch(console.error);