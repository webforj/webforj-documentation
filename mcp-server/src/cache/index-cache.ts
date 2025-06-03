import { readFileSync, writeFileSync, existsSync, mkdirSync } from 'fs';
import { join, dirname } from 'path';
import { MCPIndex } from '../types.js';
import crypto from 'crypto';

export interface CacheMetadata {
  version: string;
  lastBuilt: string;
  fileHashes: Record<string, string>;
}

export class IndexCache {
  private cacheDir: string;
  private cacheFile: string;
  private metadataFile: string;
  private ttl: number;

  constructor(cacheDir: string = '.mcp-cache', ttl: number = 3600000) {
    this.cacheDir = cacheDir;
    this.cacheFile = join(cacheDir, 'index.json');
    this.metadataFile = join(cacheDir, 'metadata.json');
    this.ttl = ttl; // Default 1 hour
    
    // Ensure cache directory exists
    if (!existsSync(this.cacheDir)) {
      mkdirSync(this.cacheDir, { recursive: true });
    }
  }

  /**
   * Check if cache is valid based on TTL and file changes
   */
  async isCacheValid(filePaths: string[]): Promise<boolean> {
    if (!existsSync(this.cacheFile) || !existsSync(this.metadataFile)) {
      return false;
    }

    try {
      const metadata: CacheMetadata = JSON.parse(
        readFileSync(this.metadataFile, 'utf-8')
      );

      // Check TTL
      const cacheAge = Date.now() - new Date(metadata.lastBuilt).getTime();
      if (cacheAge > this.ttl) {
        console.error('Cache expired (TTL exceeded)');
        return false;
      }

      // Check if any files have changed
      for (const filePath of filePaths) {
        if (!existsSync(filePath)) continue;
        
        const currentHash = await this.hashFile(filePath);
        const cachedHash = metadata.fileHashes[filePath];
        
        if (currentHash !== cachedHash) {
          console.error(`File changed: ${filePath}`);
          return false;
        }
      }

      return true;
    } catch (error) {
      console.error('Error validating cache:', error);
      return false;
    }
  }

  /**
   * Load index from cache
   */
  loadIndex(): MCPIndex | null {
    try {
      if (!existsSync(this.cacheFile)) {
        return null;
      }

      const data = JSON.parse(readFileSync(this.cacheFile, 'utf-8'));
      
      // Convert arrays back to Maps
      const index: MCPIndex = {
        components: new Map(data.components),
        demos: new Map(data.demos),
        docs: new Map(data.docs),
        javadocClasses: new Map(data.javadocClasses)
      };

      console.error('Loaded index from cache');
      return index;
    } catch (error) {
      console.error('Error loading cache:', error);
      return null;
    }
  }

  /**
   * Save index to cache with metadata
   */
  async saveIndex(index: MCPIndex, filePaths: string[]): Promise<void> {
    try {
      // Convert Maps to arrays for JSON serialization
      const serializable = {
        components: Array.from(index.components.entries()),
        demos: Array.from(index.demos.entries()),
        docs: Array.from(index.docs.entries()),
        javadocClasses: Array.from(index.javadocClasses.entries())
      };

      // Generate file hashes
      const fileHashes: Record<string, string> = {};
      for (const filePath of filePaths) {
        if (existsSync(filePath)) {
          fileHashes[filePath] = await this.hashFile(filePath);
        }
      }

      // Save metadata
      const metadata: CacheMetadata = {
        version: '1.0.0',
        lastBuilt: new Date().toISOString(),
        fileHashes
      };

      writeFileSync(this.cacheFile, JSON.stringify(serializable, null, 2));
      writeFileSync(this.metadataFile, JSON.stringify(metadata, null, 2));
      
      console.error('Saved index to cache');
    } catch (error) {
      console.error('Error saving cache:', error);
    }
  }

  /**
   * Clear the cache
   */
  clearCache(): void {
    try {
      if (existsSync(this.cacheFile)) {
        require('fs').unlinkSync(this.cacheFile);
      }
      if (existsSync(this.metadataFile)) {
        require('fs').unlinkSync(this.metadataFile);
      }
      console.error('Cache cleared');
    } catch (error) {
      console.error('Error clearing cache:', error);
    }
  }

  /**
   * Generate hash of file contents
   */
  private async hashFile(filePath: string): Promise<string> {
    const content = readFileSync(filePath, 'utf-8');
    return crypto.createHash('md5').update(content).digest('hex');
  }

  /**
   * Get cache statistics
   */
  getCacheStats(): {
    exists: boolean;
    size?: number;
    age?: number;
    fileCount?: number;
  } {
    if (!existsSync(this.cacheFile) || !existsSync(this.metadataFile)) {
      return { exists: false };
    }

    try {
      const stats = require('fs').statSync(this.cacheFile);
      const metadata: CacheMetadata = JSON.parse(
        readFileSync(this.metadataFile, 'utf-8')
      );
      
      return {
        exists: true,
        size: stats.size,
        age: Date.now() - new Date(metadata.lastBuilt).getTime(),
        fileCount: Object.keys(metadata.fileHashes).length
      };
    } catch (error) {
      return { exists: false };
    }
  }
}