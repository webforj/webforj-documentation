import type { ComponentMetadata, DocumentMetadata, DemoMetadata } from '../types.js';

export class ComponentAnalyzer {
  constructor(
    private documents: Map<string, DocumentMetadata>,
    private demos: Map<string, DemoMetadata>
  ) {}

  analyze(): Map<string, ComponentMetadata> {
    const components = new Map<string, ComponentMetadata>();
    
    // Process component documentation files
    for (const [path, doc] of this.documents) {
      if (path.startsWith('components/') && !path.includes('_category_')) {
        const componentName = this.extractComponentName(path);
        const component = this.analyzeComponentDoc(componentName, path, doc);
        
        if (component) {
          components.set(componentName, component);
        }
      }
    }
    
    // Process client-components documentation (newer format)
    for (const [path, doc] of this.documents) {
      if (path.startsWith('client-components/') && !path.includes('_category_')) {
        const componentName = this.extractComponentName(path);
        const component = this.analyzeComponentDoc(componentName, path, doc);
        
        if (component) {
          components.set(componentName, component);
        }
      }
    }
    
    return components;
  }

  private extractComponentName(path: string): string {
    const parts = path.split('/');
    const fileName = parts[parts.length - 1];
    return fileName.replace(/-/g, '');
  }

  private analyzeComponentDoc(
    componentName: string, 
    path: string, 
    doc: DocumentMetadata
  ): ComponentMetadata | null {
    // Extract metadata from document content
    const content = doc.content;
    
    // Look for component chip to get DWC name
    const nameChipMatch = content.match(/<DocChip\s+chip=['"]name['"]\s+label=['"]([^'"]+)['"]/);
    const dwcName = nameChipMatch?.[1];
    
    // Look for JavadocLink to get the API documentation
    const javadocMatch = content.match(/<JavadocLink[^>]+location=['"]([^'"]+)['"]/);
    const javadocUrl = javadocMatch ? `https://javadoc.io/doc/com.webforj/${javadocMatch[1]}` : undefined;
    
    // Find related demos by matching component references
    const relatedDemos: DemoMetadata[] = [];
    for (const ref of doc.componentRefs) {
      const route = ref.replace('/webforj/', '').replace('?', '');
      const demo = this.demos.get(route);
      if (demo) {
        relatedDemos.push(demo);
      }
    }
    
    // Extract description from first paragraph after title
    const descriptionMatch = content.match(/^#[^\n]+\n\n([^\n]+)/m);
    const description = descriptionMatch?.[1];
    
    // Determine category from path
    const category = path.includes('fields/') ? 'fields' : 
                   path.includes('lists/') ? 'lists' :
                   path.includes('option-dialogs/') ? 'dialogs' :
                   'components';
    
    return {
      name: componentName,
      displayName: doc.title,
      description,
      category,
      javadocUrl,
      demos: relatedDemos,
      properties: this.extractProperties(content),
      events: this.extractEvents(content),
      methods: this.extractMethods(content)
    };
  }

  private extractProperties(content: string): any[] {
    // Look for property tables in the documentation
    // This is a simplified version - in reality, we'd parse the tables more carefully
    const properties: any[] = [];
    
    // Look for common property patterns in documentation
    if (content.includes('setText(')) {
      properties.push({
        name: 'text',
        type: 'String',
        description: 'The text content of the component'
      });
    }
    
    if (content.includes('setEnabled(')) {
      properties.push({
        name: 'enabled',
        type: 'boolean',
        description: 'Whether the component is enabled'
      });
    }
    
    return properties;
  }

  private extractEvents(content: string): any[] {
    const events: any[] = [];
    
    // Look for event patterns
    const eventMatches = content.matchAll(/on[A-Z]\w+/g);
    for (const match of eventMatches) {
      const eventName = match[0];
      events.push({
        name: eventName,
        description: `${eventName} event handler`
      });
    }
    
    return events;
  }

  private extractMethods(content: string): any[] {
    const methods: any[] = [];
    
    // Look for method patterns in code blocks
    const codeBlockRegex = /```java\n([\s\S]*?)```/g;
    let match;
    
    while ((match = codeBlockRegex.exec(content)) !== null) {
      const code = match[1];
      const methodMatches = code.matchAll(/(\w+)\s*\(/g);
      
      for (const methodMatch of methodMatches) {
        const methodName = methodMatch[1];
        if (!methodName.startsWith('get') && !methodName.startsWith('set')) {
          methods.push({
            name: methodName,
            description: `${methodName} method`
          });
        }
      }
    }
    
    return methods;
  }
}