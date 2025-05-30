#!/usr/bin/env node

/**
 * Script to download Java files from configured GitHub repositories
 * This runs during the Maven build process to cache external examples
 */

import { GitHubRepoScanner } from '../dist/scanners/github-repo-scanner.js';
import { readFileSync } from 'fs';
import { join, dirname } from 'path';
import { fileURLToPath } from 'url';

const __dirname = dirname(fileURLToPath(import.meta.url));

async function downloadRepos() {
  console.log('📦 Downloading GitHub repositories for MCP server...\n');

  try {
    // Load configuration
    const configPath = join(__dirname, '../config/github-repos.json');
    const config = JSON.parse(readFileSync(configPath, 'utf-8'));
    
    if (!config.repositories || config.repositories.length === 0) {
      console.log('No repositories configured to download.');
      return;
    }

    // Initialize scanner
    const scanner = new GitHubRepoScanner(
      config.repositories,
      join(__dirname, '../../target/github-repos-cache')
    );

    // Download and cache repositories
    console.log(`Downloading ${config.repositories.length} repositories...`);
    await scanner.cacheRepositories();

    console.log('\n✅ GitHub repositories downloaded successfully!');
  } catch (error) {
    console.error('❌ Error downloading repositories:', error);
    process.exit(1);
  }
}

// Run if called directly
if (import.meta.url === `file://${process.argv[1]}`) {
  downloadRepos();
}

export { downloadRepos };