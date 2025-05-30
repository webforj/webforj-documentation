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
import { DocumentationScanner } from './scanners/documentation-scanner.js';
import { JavaDemoScanner } from './scanners/java-demo-scanner.js';
import { JavaDocScanner } from './scanners/javadoc-scanner.js';
import { ComponentAnalyzer } from './analyzers/component-analyzer.js';
import { getConfig, getBaseUrls, type Config } from './config.js';
import type { MCPIndex } from './types.js';
import { join } from 'path';
import { fileURLToPath } from 'url';
import { dirname } from 'path';

const __dirname = dirname(fileURLToPath(import.meta.url));

export class WebForJMCPServer {
  private server: Server;
  private config: Config;
  private index: MCPIndex | null = null;

  constructor() {
    this.config = getConfig();
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
        const docs = Array.from(this.index!.documents.entries()).map(([path, doc]) => ({
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
              properties: {}
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
        const results = Array.from(this.index!.components.values()).filter(
          comp => 
            comp.name.toLowerCase().includes(query) ||
            comp.displayName.toLowerCase().includes(query) ||
            (comp.description && comp.description.toLowerCase().includes(query))
        );
        
        return {
          content: [{
            type: 'text',
            text: JSON.stringify(results, null, 2)
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
        
        // In a real implementation, we would read the actual source files
        // For now, return the metadata
        return {
          content: [{
            type: 'text',
            text: JSON.stringify(demo, null, 2)
          }]
        };
      }

      if (name === 'rebuild_index') {
        await this.buildIndex();
        return {
          content: [{
            type: 'text',
            text: 'Index rebuilt successfully'
          }]
        };
      }

      throw new McpError(ErrorCode.MethodNotFound, `Tool ${name} not found`);
    });
  }

  private async buildIndex() {
    console.error('Building MCP index...');
    
    const docsPath = join(__dirname, '..', this.config.paths.docsRoot);
    const demoPath = join(__dirname, '..', this.config.paths.demoRoot);
    const javadocPath = join(__dirname, '..', this.config.paths.javadocRoot);
    
    // Scan documentation
    const docScanner = new DocumentationScanner(docsPath);
    const documents = await docScanner.scan();
    
    // Scan Java demos
    const demoScanner = new JavaDemoScanner(demoPath);
    const demos = await demoScanner.scan();
    
    // Scan JavaDocs (if available)
    let javadocs = new Map();
    try {
      const javadocScanner = new JavaDocScanner(javadocPath);
      javadocs = await javadocScanner.scan();
      console.error(`Found ${javadocs.size} JavaDoc classes`);
    } catch (error) {
      console.error('JavaDoc scanning failed (this is expected if not built yet):', error);
    }
    
    // Analyze components from documentation
    const componentAnalyzer = new ComponentAnalyzer(documents, demos, javadocs);
    const components = componentAnalyzer.analyze();
    
    this.index = {
      components,
      documents,
      demos,
      lastBuilt: new Date(),
      version: '1.0.0'
    };
    
    console.error(`Index built: ${components.size} components, ${demos.size} demos, ${documents.size} documents, ${javadocs.size} javadoc classes`);
  }

  async start() {
    const transport = new StdioServerTransport();
    await this.server.connect(transport);
    console.error('WebforJ MCP Server started');
  }
}