import { program } from 'commander';

program
  .option(
    '--apiKey [key]',
    'OpenAI Key for translation',
    process.env.OPENAI_API_KEY
  )
  .option(
    '--organization [key]',
    'OpenAI organization ID',
    process.env.OPENAI_ORGANIZATION
  )
  .option(
    '--baseUrl [url]',
    'OpenAI base url which can be change for other use'
  )
  .option(
    '--model [model]',
    'OpenAI Model which use for translation',
    'gpt-4o-mini'
  )
  .option(
    '--project [path]',
    'Root path which put docusaurus.config.js',
    process.cwd()
  )
  .option(
    '--batchSize [number]',
    'Number of concurrent translation requests',
    '250'
  )
  .option(
    '--force',
    'Force retranslation of all files, ignoring hash checks',
    false
  )
  .parse();

export const options = program.opts<{
  apiKey?: string;
  organization?: string;
  baseUrl?: string;
  model: string;
  project: string;
  batchSize: string;
  force: boolean;
}>();

import fs from 'fs-extra';
import path from 'path';

// Function to load Vale accept.txt file dynamically
function loadValeWhitelist(): string[] {
  try {
    // Go up from docs/tools/i18n-translator to find .github
    const valeAcceptPath = path.resolve(__dirname, '../../../../.github/.styles/config/vocabularies/webforj/accept.txt');
    
    if (fs.existsSync(valeAcceptPath)) {
      const content = fs.readFileSync(valeAcceptPath, 'utf-8');
      // Split by newlines and filter out empty lines
      const valeTerms = content
        .split('\n')
        .map(line => line.trim())
        .filter(line => line.length > 0);
      
      console.log(`Loaded ${valeTerms.length} terms from Vale accept.txt`);
      return valeTerms;
    } else {
      console.warn('Vale accept.txt not found, using default whitelist');
      return [];
    }
  } catch (error) {
    console.error('Error loading Vale accept.txt:', error);
    return [];
  }
}

// Load Vale whitelist and add additional terms that might not be in Vale
const valeWhitelist = loadValeWhitelist();

// Additional terms that should not be translated (but might not be in Vale)
const additionalWhitelist = [
  // Brand names
  'BASIS',
  'BBj',
  
  // Technical terms
  'API',
  'JavaDocs',
  'GitHub',
  'Discord',
  'Twitter',
  'copyright',
  'Kubernetes',
  'Stack Overflow',
  'Maven',
  'Gradle',
  'Docker',
  'JSON',
  'TypeScript',
  'JWT',
  'HTTP',
  'HTTPS',
  'SQL',
  'IDE',
  'JDK',
  
  // webforJ specific
  'HueCraft',
  'Figma',
  
  // Keep as-is
  'Blog'
];

// Combine Vale whitelist with additional terms (removing duplicates)
export const whitelist = [...new Set([...valeWhitelist, ...additionalWhitelist])];