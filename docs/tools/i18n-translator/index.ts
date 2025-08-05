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
import { translate, translateBatch, TranslationTask } from './translate';
import os from 'os';

const siteDir = options.project;

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

    console.groupEnd();
  }
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
    const hash = md5(content);

    if (fs.existsSync(targetPath)) {
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

  console.log(`  Translating ${tasks.length} files with batch size ${options.batchSize}...`);

  // Translate in batches with progress
  const results = await translateBatch(tasks, (completed, total) => {
    process.stdout.write(`\r  Progress: ${completed}/${total} files translated...`);
  });
  process.stdout.write('\n');

  // Process results
  let totalTokens = 0;
  for (const result of results) {
    if (result.file) {
      const fileInfo = fileInfoMap.get(result.file);
      if (fileInfo) {
        const targetFileContent = matter.stringify(result.translatedText, {
          ...fileInfo.frontmatter,
          _i18n_hash: fileInfo.hash,
        });

        await fs.ensureDir(fileInfo.targetDir);
        fs.writeFileSync(fileInfo.targetPath, targetFileContent);
        
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

  const jsonContent = fs.readFileSync(jsonPath, 'utf-8');
  const json = JSON.parse(jsonContent);

  // Prepare tasks for batch translation
  const tasks: TranslationTask[] = [];
  const keysToTranslate: string[] = [];

  for (const [key, value] of Object.entries(json)) {
    const shouldTranslate = prefixList.some((prefix) => key.startsWith(prefix));
    if (!shouldTranslate) {
      continue;
    }

    const messageValue = (value as any).message;
    if (typeof messageValue !== 'string') {
      continue;
    }

    if (whitelist.includes(messageValue)) {
      continue;
    }

    keysToTranslate.push(key);
    tasks.push({
      content: messageValue,
      locale,
      isUIString: true,
      key
    });
  }

  if (tasks.length === 0) {
    console.log(`  No need to translate, Skip: ${path.relative(siteDir, jsonPath)}`);
    return;
  }

  console.log(`  Translating ${tasks.length} UI strings...`);

  // Translate in batches
  const results = await translateBatch(tasks);

  // Update JSON with translations
  for (const result of results) {
    if (result.key && json[result.key]) {
      (json[result.key] as any).message = result.translatedText;
    }
  }

  fs.writeFileSync(jsonPath, JSON.stringify(json, null, 2));
  console.log(`  Writed translated file into: ${jsonPath}`);
}

main().catch(console.error);