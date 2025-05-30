import { readFileSync } from 'fs';
import { fileURLToPath } from 'url';
import { dirname, join } from 'path';
import dotenv from 'dotenv';

dotenv.config();

const __dirname = dirname(fileURLToPath(import.meta.url));
const configPath = join(__dirname, '../config/default.json');
const defaultConfig = JSON.parse(readFileSync(configPath, 'utf-8'));

export interface Config {
  server: {
    name: string;
    version: string;
    description: string;
  };
  paths: {
    docsRoot: string;
    blogRoot: string;
    demoRoot: string;
    contextRoot: string;
    cacheFile: string;
    javadocRoot: string;
  };
  scanning: {
    includePatterns: {
      docs: string[];
      demos: string[];
      context: string[];
    };
    excludePatterns: string[];
  };
  production: {
    baseUrl: string;
    demoBaseUrl: string;
  };
  development: {
    baseUrl: string;
    demoBaseUrl: string;
  };
  currentEnv: 'development' | 'production';
}

export function getConfig(): Config {
  const env = process.env.NODE_ENV === 'production' ? 'production' : 'development';
  
  return {
    ...defaultConfig,
    paths: {
      docsRoot: process.env.DOCS_ROOT || defaultConfig.paths.docsRoot,
      blogRoot: process.env.BLOG_ROOT || defaultConfig.paths.blogRoot,
      demoRoot: process.env.DEMO_ROOT || defaultConfig.paths.demoRoot,
      contextRoot: process.env.CONTEXT_ROOT || defaultConfig.paths.contextRoot,
      cacheFile: process.env.CACHE_FILE || defaultConfig.paths.cacheFile,
      javadocRoot: process.env.JAVADOC_ROOT || defaultConfig.paths.javadocRoot,
    },
    production: {
      baseUrl: process.env.PROD_BASE_URL || defaultConfig.production.baseUrl,
      demoBaseUrl: process.env.PROD_DEMO_URL || defaultConfig.production.demoBaseUrl,
    },
    currentEnv: env
  };
}

export function getBaseUrls(config: Config) {
  const env = config.currentEnv;
  return {
    docs: config[env].baseUrl,
    demos: config[env].demoBaseUrl
  };
}