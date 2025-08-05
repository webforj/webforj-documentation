import fs from 'fs-extra';
import path from 'path';
import fg from 'fast-glob';
import matter from 'gray-matter';

const siteDir = process.cwd();

async function removeHashes() {
  // Find all translated markdown files
  const translatedDirs = [
    path.resolve(siteDir, './i18n/es/docusaurus-plugin-content-docs/current'),
    path.resolve(siteDir, './i18n/de/docusaurus-plugin-content-docs/current'),
  ];

  let totalFilesProcessed = 0;

  for (const dir of translatedDirs) {
    if (!fs.existsSync(dir)) {
      console.log(`Directory not found, skipping: ${dir}`);
      continue;
    }

    const locale = dir.includes('/es/') ? 'es' : 'de';
    console.log(`\nProcessing ${locale} translations...`);

    const files = await fg(['**/*.md', '**/*.mdx'], {
      cwd: dir,
      onlyFiles: true,
    });

    console.log(`Found ${files.length} files to process`);

    for (const file of files) {
      const filePath = path.resolve(dir, file);
      
      try {
        const fileContent = fs.readFileSync(filePath, 'utf-8');
        const { data: frontmatter, content } = matter(fileContent);

        // Check if _i18n_hash exists
        if (frontmatter._i18n_hash) {
          // Remove the _i18n_hash
          delete frontmatter._i18n_hash;
          
          // Write back the file without the hash
          const newContent = matter.stringify(content, frontmatter);
          fs.writeFileSync(filePath, newContent);
          
          totalFilesProcessed++;
          
          if (totalFilesProcessed % 50 === 0) {
            process.stdout.write(`\r  Processed ${totalFilesProcessed} files...`);
          }
        }
      } catch (error) {
        console.error(`Error processing ${file}: ${error}`);
      }
    }


    console.log(`\n  Completed ${locale}: processed ${files.length} files`);
  }

  console.log(`\n✅ Total files processed: ${totalFilesProcessed}`);
  console.log('All _i18n_hash entries have been removed from translated files.');
  console.log('You can now run the translation script to retranslate with the improved prompts.');
}

removeHashes().catch(console.error);