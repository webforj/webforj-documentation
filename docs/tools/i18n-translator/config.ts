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
  .parse();

export const options = program.opts<{
  apiKey?: string;
  organization?: string;
  baseUrl?: string;
  model: string;
  project: string;
}>();

// webforJ-specific terms that should not be translated
export const whitelist = [
  // Brand names
  'webforJ',
  'DWC',
  'startforJ',
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
  'Spring',
  'Docker',
  'JSON',
  'HTML',
  'CSS',
  'JavaScript',
  'TypeScript',
  'JWT',
  'HTTP',
  'HTTPS',
  'REST',
  'SQL',
  'JPA',
  'IDE',
  'JDK',
  'JVM',
  
  // webforJ specific
  'HueCraft',
  'Figma',
  
  // Keep as-is
  'Blog'
];