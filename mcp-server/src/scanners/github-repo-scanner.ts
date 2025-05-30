import { Octokit } from '@octokit/rest';
import { readFileSync, writeFileSync, mkdirSync } from 'fs';
import { join, dirname, basename } from 'path';
import { glob } from 'glob';
import type { DemoMetadata } from '../types.js';

export interface GitHubRepoConfig {
  owner: string;
  repo: string;
  branch?: string;
  paths?: string[];  // Specific paths to scan, defaults to all Java files
  description?: string;
}

export class GitHubRepoScanner {
  private octokit: Octokit;
  private cacheDir: string;

  constructor(
    private config: GitHubRepoConfig[],
    cacheDir: string = './cache/github-repos'
  ) {
    this.octokit = new Octokit({
      auth: process.env.GITHUB_TOKEN // Optional, for higher rate limits
    });
    this.cacheDir = cacheDir;
  }

  async scan(): Promise<Map<string, DemoMetadata>> {
    const allDemos = new Map<string, DemoMetadata>();

    for (const repo of this.config) {
      console.error(`GitHubRepoScanner: Scanning ${repo.owner}/${repo.repo}...`);
      try {
        const demos = await this.scanRepository(repo);
        demos.forEach((demo, key) => {
          // Prefix keys with repo name to avoid conflicts
          const prefixedKey = `${repo.repo}/${key}`;
          allDemos.set(prefixedKey, demo);
        });
      } catch (error) {
        console.error(`Error scanning repository ${repo.owner}/${repo.repo}:`, error);
      }
    }

    console.error(`GitHubRepoScanner: Found ${allDemos.size} demos from ${this.config.length} repositories`);
    return allDemos;
  }

  private async scanRepository(repo: GitHubRepoConfig): Promise<Map<string, DemoMetadata>> {
    const demos = new Map<string, DemoMetadata>();
    const branch = repo.branch || 'main';

    // Get repository tree
    const { data: tree } = await this.octokit.git.getTree({
      owner: repo.owner,
      repo: repo.repo,
      tree_sha: branch,
      recursive: 'true'
    });

    // Filter for Java files
    const javaFiles = tree.tree.filter(item => 
      item.type === 'blob' && 
      item.path?.endsWith('.java') &&
      (repo.paths ? repo.paths.some(p => item.path?.includes(p)) : true)
    );

    console.error(`GitHubRepoScanner: Found ${javaFiles.length} Java files in ${repo.repo}`);

    // Process each Java file
    for (const file of javaFiles) {
      if (!file.path || !file.sha) continue;

      try {
        // Get file content
        const { data: blob } = await this.octokit.git.getBlob({
          owner: repo.owner,
          repo: repo.repo,
          file_sha: file.sha
        });

        // Decode base64 content
        const content = Buffer.from(blob.content, 'base64').toString('utf-8');
        
        // Cache the file locally for reference
        const localPath = join(this.cacheDir, repo.repo, file.path);
        mkdirSync(dirname(localPath), { recursive: true });
        writeFileSync(localPath, content);

        // Parse the Java file for metadata
        const metadata = this.parseJavaFile(file.path, content, repo);
        if (metadata) {
          const key = this.generateKey(file.path);
          demos.set(key, metadata);
        }
      } catch (error) {
        console.error(`Error processing ${file.path}:`, error);
      }
    }

    return demos;
  }

  private parseJavaFile(filePath: string, content: string, repo: GitHubRepoConfig): DemoMetadata | null {
    const fileName = basename(filePath, '.java');
    
    // Look for @Route annotation or main application classes
    const hasRoute = /@Route/.test(content);
    const isMainApp = /public\s+class\s+\w+\s+extends\s+\w*Application/.test(content);
    
    if (!hasRoute && !isMainApp) {
      return null;
    }

    // Extract class-level Javadoc
    const javadocMatch = content.match(/\/\*\*\s*\n([^*]|\*(?!\/))*\*\/\s*(?=@|public\s+class)/);
    let description = repo.description || '';
    if (javadocMatch) {
      description = javadocMatch[0]
        .replace(/\/\*\*|\*\//g, '')
        .replace(/^\s*\*\s?/gm, '')
        .trim();
    }

    // Extract route if present
    const routeMatch = content.match(/@Route\s*\(\s*"([^"]+)"\s*\)/);
    const route = routeMatch ? routeMatch[1] : this.generateKey(filePath);

    return {
      name: fileName,
      route,
      title: `${repo.repo} - ${fileName}`,
      description,
      sourceFiles: [filePath],
      highlighted: false,
      className: fileName,
      filePath,
      repository: {
        owner: repo.owner,
        repo: repo.repo,
        branch: repo.branch || 'main',
        url: `https://github.com/${repo.owner}/${repo.repo}/blob/${repo.branch || 'main'}/${filePath}`
      }
    };
  }

  private generateKey(filePath: string): string {
    // Convert file path to a key
    // e.g., src/main/java/com/example/App.java -> example/app
    const parts = filePath.split('/');
    const fileName = basename(filePath, '.java');
    return fileName.toLowerCase().replace(/view$/, '');
  }

  // Method to download and cache repository files for offline access
  async cacheRepositories(): Promise<void> {
    for (const repo of this.config) {
      console.log(`Caching ${repo.owner}/${repo.repo}...`);
      await this.scanRepository(repo); // This already caches files
    }
  }
}