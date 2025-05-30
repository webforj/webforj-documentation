import { ComponentMetadata, DemoMetadata, DocumentMetadata } from '../types.js';
import Fuse from 'fuse.js';

export interface SearchOptions {
  query: string;
  category?: string;
  hasDemo?: boolean;
  hasJavadoc?: boolean;
  searchIn?: Array<'name' | 'description' | 'content' | 'code'>;
  limit?: number;
}

export interface SearchResult {
  type: 'component' | 'demo' | 'document';
  item: ComponentMetadata | DemoMetadata | DocumentMetadata;
  score: number;
  matches?: Array<{
    key: string;
    value: string;
    indices: Array<[number, number]>;
  }>;
}

export class EnhancedSearch {
  private componentIndex?: Fuse<ComponentMetadata>;
  private demoIndex?: Fuse<DemoMetadata>;
  private docIndex?: Fuse<DocumentMetadata>;
  
  constructor() {}

  /**
   * Initialize search indices
   */
  initialize(
    components: Map<string, ComponentMetadata>,
    demos: Map<string, DemoMetadata>,
    docs: Map<string, DocumentMetadata>
  ): void {
    // Configure Fuse.js options for fuzzy search
    const baseOptions = {
      includeScore: true,
      includeMatches: true,
      threshold: 0.4,
      location: 0,
      distance: 100,
      minMatchCharLength: 2,
    };

    // Component search index
    this.componentIndex = new Fuse(Array.from(components.values()), {
      ...baseOptions,
      keys: [
        { name: 'name', weight: 2 },
        { name: 'displayName', weight: 2 },
        { name: 'description', weight: 1 },
        { name: 'category', weight: 0.5 },
        { name: 'properties.name', weight: 0.3 },
        { name: 'methods.name', weight: 0.3 },
        { name: 'events.name', weight: 0.3 }
      ]
    });

    // Demo search index
    this.demoIndex = new Fuse(Array.from(demos.values()), {
      ...baseOptions,
      keys: [
        { name: 'route', weight: 2 },
        { name: 'title', weight: 2 },
        { name: 'className', weight: 1 },
        { name: 'description', weight: 1 }
      ]
    });

    // Document search index
    this.docIndex = new Fuse(Array.from(docs.values()), {
      ...baseOptions,
      keys: [
        { name: 'title', weight: 2 },
        { name: 'path', weight: 1 },
        { name: 'content', weight: 0.5 },
        { name: 'headings', weight: 0.8 }
      ]
    });
  }

  /**
   * Perform enhanced search across all indices
   */
  search(options: SearchOptions): SearchResult[] {
    const results: SearchResult[] = [];
    const { query, category, hasDemo, hasJavadoc, searchIn, limit = 50 } = options;

    // Search components
    if (!searchIn || searchIn.includes('name') || searchIn.includes('description')) {
      const componentResults = this.componentIndex?.search(query) || [];
      
      for (const result of componentResults) {
        const component = result.item;
        
        // Apply filters
        if (category && component.category !== category) continue;
        if (hasDemo !== undefined && (component.demos.length > 0) !== hasDemo) continue;
        if (hasJavadoc !== undefined && !!component.javadocUrl !== hasJavadoc) continue;
        
        results.push({
          type: 'component',
          item: component,
          score: result.score || 0,
          matches: result.matches as any
        });
      }
    }

    // Search demos if code search is enabled
    if (!searchIn || searchIn.includes('code')) {
      const demoResults = this.demoIndex?.search(query) || [];
      
      for (const result of demoResults) {
        results.push({
          type: 'demo',
          item: result.item,
          score: result.score || 0,
          matches: result.matches as any
        });
      }
    }

    // Search documentation if content search is enabled
    if (!searchIn || searchIn.includes('content')) {
      const docResults = this.docIndex?.search(query) || [];
      
      for (const result of docResults) {
        results.push({
          type: 'document',
          item: result.item,
          score: result.score || 0,
          matches: result.matches as any
        });
      }
    }

    // Sort by relevance score
    results.sort((a, b) => a.score - b.score);

    // Apply limit
    return results.slice(0, limit);
  }

  /**
   * Find similar components based on properties/methods
   */
  findSimilar(component: ComponentMetadata, limit: number = 5): ComponentMetadata[] {
    if (!this.componentIndex) return [];

    // Create a search query based on component features
    const features = [
      ...(component.properties || []).map(p => p.name),
      ...(component.methods || []).map(m => m.name),
      ...(component.events || []).map(e => e.name)
    ].join(' ');

    const results = this.componentIndex.search(features);
    
    return results
      .filter(r => r.item.name !== component.name)
      .slice(0, limit)
      .map(r => r.item);
  }

  /**
   * Get search suggestions based on partial query
   */
  getSuggestions(partialQuery: string, limit: number = 10): string[] {
    const suggestions = new Set<string>();
    
    // Add component names
    const componentResults = this.componentIndex?.search(partialQuery, { limit: 5 }) || [];
    componentResults.forEach(r => {
      suggestions.add(r.item.name);
      suggestions.add(r.item.displayName);
    });

    // Add common search terms
    const commonTerms = [
      'button', 'dialog', 'table', 'field', 'layout',
      'event', 'property', 'method', 'demo', 'example'
    ];
    
    commonTerms
      .filter(term => term.includes(partialQuery.toLowerCase()))
      .forEach(term => suggestions.add(term));

    return Array.from(suggestions).slice(0, limit);
  }

  /**
   * Get category statistics
   */
  getCategoryStats(): Record<string, number> {
    const stats: Record<string, number> = {};
    
    if (this.componentIndex) {
      const components = (this.componentIndex as any)._docs;
      components.forEach((comp: any) => {
        const category = comp.category || 'uncategorized';
        stats[category] = (stats[category] || 0) + 1;
      });
    }
    
    return stats;
  }
}