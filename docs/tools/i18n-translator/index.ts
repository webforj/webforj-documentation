import { options, whitelist } from './config';
import { loadSiteConfig } from './utils';
import path from 'path';
import fg from 'fast-glob';
import matter from 'gray-matter';
import fs from 'fs-extra';
import md5 from 'md5';
import { translate } from './translate';
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
      ['sidebar.tutorialSidebar.category.']
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

    console.log(`  Translating.... ${file}`);

    const { translatedText, usage } = await translate(content, locale);

    const targetFileContent = matter.stringify(translatedText, {
      ...frontmatter,
      _i18n_hash: hash,
    });

    await fs.ensureDir(targetDir);
    fs.writeFileSync(targetPath, targetFileContent);

    console.log(
      `  Writed translated file into: ${targetPath} , token usage: ${usage?.total_tokens}`
    );
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

  const jsonContent = fs.readFileSync(jsonPath, 'utf-8');
  const json = JSON.parse(jsonContent);

  let hasTranslation = false;
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

    const { translatedText } = await translate(messageValue, locale);
    (value as any).message = translatedText;
    hasTranslation = true;
  }

  if (hasTranslation) {
    fs.writeFileSync(jsonPath, JSON.stringify(json, null, 2));
    console.log(`  Writed translated file into: ${jsonPath}`);
  } else {
    console.log(`  No need to translate, Skip: ${path.relative(siteDir, jsonPath)}`);
  }
}

main().catch(console.error);