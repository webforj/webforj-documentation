import { Server } from '@modelcontextprotocol/sdk/server/index.js';
import { StdioServerTransport } from '@modelcontextprotocol/sdk/server/stdio.js';
import {
  ListResourcesRequestSchema,
  ReadResourceRequestSchema,
  ListToolsRequestSchema,
  CallToolRequestSchema,
  ErrorCode,
  McpError
} from '@modelcontextprotocol/sdk/types.js';
import { glob } from 'glob';
import { readFileSync } from 'fs';
import { watch } from 'chokidar';
import { DocumentationScanner } from './scanners/documentation-scanner.js';
import { JavaDemoScanner } from './scanners/java-demo-scanner.js';
import { JavaDocScanner } from './scanners/javadoc-scanner.js';
import { GitHubRepoScanner } from './scanners/github-repo-scanner.js';
import { TrainingDataScanner } from './scanners/training-data-scanner.js';
import { ComponentAnalyzer } from './analyzers/component-analyzer.js';
import { getConfig, getBaseUrls, type Config } from './config.js';
import { debounce } from './utils/debounce.js';
import { IndexCache } from './cache/index-cache.js';
import { DemoCodeRetriever } from './tools/demo-code-retriever.js';
import { EnhancedSearch } from './search/enhanced-search.js';
import { MarkdownTableParser } from './extractors/markdown-table-parser.js';
import { AntipatternChecker } from './tools/antipattern-checker.js';
import type { MCPIndex } from './types.js';
import { join } from 'path';
import { fileURLToPath } from 'url';
import { dirname } from 'path';

const __dirname = dirname(fileURLToPath(import.meta.url));

export class WebForJMCPServer {
  private server: Server;
  private config: Config;
  private index: MCPIndex | null = null;
  private cache: IndexCache;
  private search: EnhancedSearch;
  private demoRetriever: DemoCodeRetriever;
  private tableParser: MarkdownTableParser;
  private watcher?: any;

  constructor() {
    this.config = getConfig();
    
    // Initialize enhanced components
    this.cache = new IndexCache(this.config.paths.cache, this.config.features.cacheTTL);
    this.search = new EnhancedSearch();
    this.tableParser = new MarkdownTableParser();
    this.demoRetriever = new DemoCodeRetriever(
      join(__dirname, '..', this.config.paths.demoRoot),
      join(__dirname, '..', this.config.paths.resourceRoot)
    );
    
    this.server = new Server(
      {
        name: this.config.server.name,
        version: this.config.server.version,
      },
      {
        capabilities: {
          resources: {},
          tools: {}
        }
      }
    );

    this.setupHandlers();
    
    if (this.config.features.watchFiles) {
      this.setupFileWatcher();
    }
  }

  private setupHandlers() {
    // List available resources
    this.server.setRequestHandler(ListResourcesRequestSchema, async () => {
      if (!this.index) {
        await this.buildIndex();
      }

      const resources = [
        {
          uri: 'webforj://components',
          name: 'WebforJ Components',
          description: 'List of all webforJ components with metadata',
          mimeType: 'application/json'
        },
        {
          uri: 'webforj://demos',
          name: 'Component Demos',
          description: 'Interactive demo applications for components',
          mimeType: 'application/json'
        },
        {
          uri: 'webforj://docs',
          name: 'Documentation Pages',
          description: 'All documentation content',
          mimeType: 'application/json'
        },
        {
          uri: 'webforj://antipatterns',
          name: 'webforJ Antipatterns',
          description: 'Common incorrect patterns and their corrections',
          mimeType: 'application/json'
        }
      ];

      // Add individual component resources
      if (this.index) {
        for (const [name, component] of this.index.components) {
          resources.push({
            uri: `webforj://component/${name}`,
            name: `${component.displayName} Component`,
            description: component.description || `Details for ${component.displayName}`,
            mimeType: 'application/json'
          });
        }
      }

      return { resources };
    });

    // Read specific resources
    this.server.setRequestHandler(ReadResourceRequestSchema, async (request) => {
      if (!this.index) {
        await this.buildIndex();
      }

      const uri = request.params.uri;
      const urls = getBaseUrls(this.config);

      if (uri === 'webforj://components') {
        const components = Array.from(this.index!.components.values());
        return {
          contents: [{
            uri,
            mimeType: 'application/json',
            text: JSON.stringify(components, null, 2)
          }]
        };
      }

      if (uri === 'webforj://demos') {
        const demos = Array.from(this.index!.demos.values()).map(demo => ({
          ...demo,
          liveUrl: `${urls.demos}/${demo.route}`
        }));
        return {
          contents: [{
            uri,
            mimeType: 'application/json',
            text: JSON.stringify(demos, null, 2)
          }]
        };
      }

      if (uri === 'webforj://docs') {
        const docs = Array.from(this.index!.docs.entries()).map(([path, doc]) => ({
          path,
          title: doc.title,
          url: `${urls.docs}/${path}`,
          componentRefs: doc.componentRefs
        }));
        return {
          contents: [{
            uri,
            mimeType: 'application/json',
            text: JSON.stringify(docs, null, 2)
          }]
        };
      }
      
      if (uri === 'webforj://antipatterns') {
        const antipatterns = Array.from(this.index!.antipatterns?.values() || []);
        return {
          contents: [{
            uri,
            mimeType: 'application/json',
            text: JSON.stringify(antipatterns, null, 2)
          }]
        };
      }

      if (uri.startsWith('webforj://component/')) {
        const componentName = uri.replace('webforj://component/', '');
        const component = this.index!.components.get(componentName);
        
        if (!component) {
          throw new McpError(ErrorCode.InvalidParams, `Component ${componentName} not found`);
        }

        // Enrich with demo URLs
        const enrichedComponent = {
          ...component,
          demos: component.demos.map(demo => ({
            ...demo,
            liveUrl: `${urls.demos}/${demo.route}`
          }))
        };

        return {
          contents: [{
            uri,
            mimeType: 'application/json',
            text: JSON.stringify(enrichedComponent, null, 2)
          }]
        };
      }

      throw new McpError(ErrorCode.InvalidParams, `Resource ${uri} not found`);
    });

    // List available tools
    this.server.setRequestHandler(ListToolsRequestSchema, async () => {
      return {
        tools: [
          {
            name: 'search_components',
            description: 'Search for webforJ components by name or description',
            inputSchema: {
              type: 'object',
              properties: {
                query: {
                  type: 'string',
                  description: 'Search query'
                }
              },
              required: ['query']
            }
          },
          {
            name: 'get_component_code',
            description: 'Get the source code for a component demo',
            inputSchema: {
              type: 'object',
              properties: {
                route: {
                  type: 'string',
                  description: 'Demo route (e.g., "button")'
                }
              },
              required: ['route']
            }
          },
          {
            name: 'rebuild_index',
            description: 'Rebuild the MCP index by rescanning all files',
            inputSchema: {
              type: 'object',
              properties: {
                clearCache: {
                  type: 'boolean',
                  description: 'Clear cache before rebuilding',
                  default: false
                }
              }
            }
          },
          {
            name: 'search_advanced',
            description: 'Advanced search with filters and options',
            inputSchema: {
              type: 'object',
              properties: {
                query: {
                  type: 'string',
                  description: 'Search query'
                },
                category: {
                  type: 'string',
                  description: 'Filter by category',
                  enum: ['components', 'fields', 'lists', 'dialogs', 'layouts']
                },
                hasDemo: {
                  type: 'boolean',
                  description: 'Filter components that have demos'
                },
                hasJavadoc: {
                  type: 'boolean',
                  description: 'Filter components that have JavaDoc'
                },
                searchIn: {
                  type: 'array',
                  description: 'Where to search',
                  items: {
                    type: 'string',
                    enum: ['name', 'description', 'content', 'code']
                  }
                },
                limit: {
                  type: 'number',
                  description: 'Maximum number of results',
                  default: 50
                }
              },
              required: ['query']
            }
          },
          {
            name: 'get_cache_stats',
            description: 'Get cache statistics',
            inputSchema: {
              type: 'object',
              properties: {}
            }
          },
          {
            name: 'check_antipatterns',
            description: 'Check code for common webforJ antipatterns and get corrections',
            inputSchema: {
              type: 'object',
              properties: {
                code: {
                  type: 'string',
                  description: 'The code to check for antipatterns'
                }
              },
              required: ['code']
            }
          },
          {
            name: 'get_antipattern',
            description: 'Get details about a specific antipattern with correct implementation',
            inputSchema: {
              type: 'object',
              properties: {
                category: {
                  type: 'string',
                  description: 'Antipattern category',
                  enum: ['lifecycle', 'events', 'state', 'routing', 'styling', 'async']
                },
                framework: {
                  type: 'string',
                  description: 'Source framework of confusion',
                  enum: ['React', 'Vue', 'JavaFX', 'DOM-style']
                }
              }
            }
          },
          {
            name: 'get_correct_pattern',
            description: 'Get the correct webforJ pattern for a concept',
            inputSchema: {
              type: 'object',
              properties: {
                concept: {
                  type: 'string',
                  description: 'The concept to get correct pattern for (e.g., "lifecycle", "event handling", "state management")'
                }
              },
              required: ['concept']
            }
          }
        ]
      };
    });

    // Handle tool calls
    this.server.setRequestHandler(CallToolRequestSchema, async (request) => {
      if (!this.index) {
        await this.buildIndex();
      }

      const { name, arguments: args } = request.params;

      if (name === 'search_components') {
        const query = (args as any).query?.toLowerCase();
        if (!query) {
          throw new McpError(ErrorCode.InvalidParams, 'Query parameter is required');
        }
        
        // Use enhanced search for better results
        const results = this.search.search({ query, limit: 20 });
        const componentResults = results
          .filter(r => r.type === 'component')
          .map(r => r.item);
        
        return {
          content: [{
            type: 'text',
            text: JSON.stringify(componentResults, null, 2)
          }]
        };
      }

      if (name === 'get_component_code') {
        const route = (args as any).route;
        if (!route) {
          throw new McpError(ErrorCode.InvalidParams, 'Route parameter is required');
        }
        const demo = this.index!.demos.get(route);
        if (!demo) {
          throw new McpError(ErrorCode.InvalidParams, `Demo ${route} not found`);
        }
        
        // Get complete source code
        const sourceCode = await this.demoRetriever.getCompleteDemo(demo);
        
        const response = {
          ...demo,
          sourceCode: {
            java: sourceCode.java,
            css: sourceCode.css,
            resources: sourceCode.resources,
            imports: sourceCode.imports
          }
        };
        
        return {
          content: [{
            type: 'text',
            text: JSON.stringify(response, null, 2)
          }]
        };
      }

      if (name === 'rebuild_index') {
        const { clearCache } = args as any;
        
        if (clearCache) {
          this.cache.clearCache();
        }
        
        await this.buildIndex();
        return {
          content: [{
            type: 'text',
            text: 'Index rebuilt successfully'
          }]
        };
      }
      
      if (name === 'search_advanced') {
        const options = args as any;
        if (!options.query) {
          throw new McpError(ErrorCode.InvalidParams, 'Query parameter is required');
        }
        
        const results = this.search.search(options);
        
        return {
          content: [{
            type: 'text',
            text: JSON.stringify(results, null, 2)
          }]
        };
      }
      
      if (name === 'get_cache_stats') {
        const stats = this.cache.getCacheStats();
        const categoryStats = this.search.getCategoryStats();
        
        return {
          content: [{
            type: 'text',
            text: JSON.stringify({
              cache: stats,
              categories: categoryStats,
              indexSize: {
                components: this.index!.components.size,
                demos: this.index!.demos.size,
                docs: this.index!.docs.size,
                javadocClasses: this.index!.javadocClasses.size,
                antipatterns: this.index!.antipatterns?.size || 0,
                trainingExamples: this.index!.trainingExamples?.size || 0
              }
            }, null, 2)
          }]
        };
      }
      
      if (name === 'check_antipatterns') {
        const { code } = args as any;
        if (!code) {
          throw new McpError(ErrorCode.InvalidParams, 'Code parameter is required');
        }
        
        const checker = new AntipatternChecker(this.index!.antipatterns || new Map());
        const results = checker.checkCode(code);
        
        return {
          content: [{
            type: 'text',
            text: JSON.stringify(results, null, 2)
          }]
        };
      }
      
      if (name === 'get_antipattern') {
        const { category, framework } = args as any;
        const antipatterns = this.index!.antipatterns || new Map();
        const checker = new AntipatternChecker(antipatterns);
        
        let results;
        if (category) {
          results = checker.getAntipatternsByCategory(category);
        } else if (framework) {
          results = checker.getAntipatternsByFramework(framework);
        } else {
          results = Array.from(antipatterns.values());
        }
        
        return {
          content: [{
            type: 'text',
            text: JSON.stringify(results, null, 2)
          }]
        };
      }
      
      if (name === 'get_correct_pattern') {
        const { concept } = args as any;
        if (!concept) {
          throw new McpError(ErrorCode.InvalidParams, 'Concept parameter is required');
        }
        
        const antipatterns = Array.from(this.index!.antipatterns?.values() || []);
        const relevant = antipatterns.filter(ap => 
          ap.category.toLowerCase().includes(concept.toLowerCase()) ||
          ap.tags.some(tag => tag.toLowerCase().includes(concept.toLowerCase()))
        );
        
        if (relevant.length === 0) {
          return {
            content: [{
              type: 'text',
              text: `No patterns found for concept: ${concept}`
            }]
          };
        }
        
        const response = relevant.map(ap => ({
          category: ap.category,
          explanation: ap.explanation,
          correctPattern: ap.correctCode,
          avoidPatterns: ap.frameworks
        }));
        
        return {
          content: [{
            type: 'text',
            text: JSON.stringify(response, null, 2)
          }]
        };
      }

      throw new McpError(ErrorCode.MethodNotFound, `Tool ${name} not found`);
    });
  }

  private async buildIndex() {
    // Check cache first
    if (this.config.features.cacheEnabled) {
      const cachedIndex = await this.loadFromCache();
      if (cachedIndex) {
        this.index = cachedIndex;
        return;
      }
    }
    
    console.error('Building MCP index...');
    
    const docsPath = join(__dirname, '..', this.config.paths.docsRoot);
    const demoPath = join(__dirname, '..', this.config.paths.demoRoot);
    const javadocPath = join(__dirname, '..', this.config.paths.javadocRoot);
    
    // Scan documentation with enhanced parser
    const docScanner = new DocumentationScanner(docsPath, this.tableParser);
    const docs = await docScanner.scan();
    
    // Scan Java demos
    const demoScanner = new JavaDemoScanner(demoPath);
    const demos = await demoScanner.scan();
    
    // Scan GitHub repositories (if configured)
    try {
      const githubConfigPath = join(__dirname, '../config/github-repos.json');
      const githubConfig = JSON.parse(readFileSync(githubConfigPath, 'utf-8'));
      
      if (githubConfig.repositories && githubConfig.repositories.length > 0) {
        console.error('Scanning GitHub repositories...');
        const githubScanner = new GitHubRepoScanner(
          githubConfig.repositories,
          join(__dirname, '../../target/github-repos-cache')
        );
        const githubDemos = await githubScanner.scan();
        
        // Merge GitHub demos with local demos
        githubDemos.forEach((demo, key) => {
          demos.set(key, demo);
        });
        
        console.error(`Added ${githubDemos.size} demos from GitHub repositories`);
      }
    } catch (error) {
      console.error('GitHub repository scanning failed (optional):', error);
    }
    
    // Scan JavaDocs (if available)
    let javadocClasses = new Map();
    try {
      const javadocScanner = new JavaDocScanner(javadocPath);
      javadocClasses = await javadocScanner.scan();
      console.error(`Found ${javadocClasses.size} JavaDoc classes`);
    } catch (error) {
      console.error('JavaDoc scanning failed (this is expected if not built yet):', error);
    }
    
    // Scan training data (antipatterns and examples)
    let antipatterns = new Map();
    let trainingExamples = new Map();
    try {
      const trainingScanner = new TrainingDataScanner(join(__dirname, '..'));
      const trainingData = await trainingScanner.scan();
      antipatterns = trainingData.antipatterns;
      trainingExamples = trainingData.examples;
      console.error(`Found ${antipatterns.size} antipatterns and ${trainingExamples.size} training examples`);
    } catch (error) {
      console.error('Training data scanning failed:', error);
    }
    
    // Analyze components from documentation
    const componentAnalyzer = new ComponentAnalyzer(docs, demos, javadocClasses);
    const components = componentAnalyzer.analyze();
    
    this.index = {
      components,
      docs,
      demos,
      javadocClasses,
      antipatterns,
      trainingExamples
    };
    
    // Initialize search index
    this.search.initialize(components, demos, docs);
    
    // Save to cache
    if (this.config.features.cacheEnabled) {
      await this.saveToCache();
    }
    
    console.error(`Index built: ${components.size} components, ${demos.size} demos, ${docs.size} documents, ${javadocClasses.size} javadoc classes`);
  }

  private async loadFromCache(): Promise<MCPIndex | null> {
    // Get all files that would be scanned
    const docFiles = await glob('**/*.{md,mdx}', { 
      cwd: join(__dirname, '..', this.config.paths.docsRoot) 
    });
    const demoFiles = await glob('**/*View.java', { 
      cwd: join(__dirname, '..', this.config.paths.demoRoot) 
    });
    
    const allFiles = [
      ...docFiles.map(f => join(__dirname, '..', this.config.paths.docsRoot, f)),
      ...demoFiles.map(f => join(__dirname, '..', this.config.paths.demoRoot, f))
    ];
    
    // Check if cache is valid
    if (await this.cache.isCacheValid(allFiles)) {
      const cached = this.cache.loadIndex();
      if (cached) {
        console.error('Loaded index from cache');
        // Re-initialize search with cached data
        this.search.initialize(cached.components, cached.demos, cached.docs);
        return cached;
      }
    }
    
    return null;
  }
  
  private async saveToCache(): Promise<void> {
    if (!this.index) return;
    
    // Get all files that were scanned
    const docFiles = await glob('**/*.{md,mdx}', { 
      cwd: join(__dirname, '..', this.config.paths.docsRoot) 
    });
    const demoFiles = await glob('**/*View.java', { 
      cwd: join(__dirname, '..', this.config.paths.demoRoot) 
    });
    
    const allFiles = [
      ...docFiles.map(f => join(__dirname, '..', this.config.paths.docsRoot, f)),
      ...demoFiles.map(f => join(__dirname, '..', this.config.paths.demoRoot, f))
    ];
    
    await this.cache.saveIndex(this.index, allFiles);
  }
  
  private setupFileWatcher(): void {
    const docsPath = join(__dirname, '..', this.config.paths.docsRoot);
    const demoPath = join(__dirname, '..', this.config.paths.demoRoot);
    const resourcePath = join(__dirname, '..', this.config.paths.resourceRoot);
    
    const watchPaths = [
      `${docsPath}/**/*.{md,mdx}`,
      `${demoPath}/**/*.java`,
      `${resourcePath}/**/*.css`
    ];
    
    this.watcher = watch(watchPaths, {
      ignored: /node_modules/,
      persistent: true,
      ignoreInitial: true
    });
    
    const rebuildDebounced = debounce(async () => {
      console.error('Files changed, rebuilding index...');
      this.cache.clearCache();
      await this.buildIndex();
    }, 1000);
    
    this.watcher
      .on('add', rebuildDebounced)
      .on('change', rebuildDebounced)
      .on('unlink', rebuildDebounced);
    
    console.error('File watcher initialized');
  }

  async start() {
    const transport = new StdioServerTransport();
    await this.server.connect(transport);
    console.error('WebforJ MCP Server started');
  }
}