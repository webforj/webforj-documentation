import type { ComponentMetadata, DocumentMetadata, DemoMetadata } from '../types.js';
import type { JavaDocClass } from '../scanners/javadoc-scanner.js';

export class ComponentAnalyzer {
  constructor(
    private documents: Map<string, DocumentMetadata>,
    private demos: Map<string, DemoMetadata>,
    private javadocs: Map<string, JavaDocClass> = new Map()
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
    
    // Try to find corresponding JavaDoc
    const javadocClass = this.findJavaDocForComponent(componentName);
    
    return {
      name: componentName,
      displayName: doc.title,
      description: description || javadocClass?.description,
      category,
      javadocUrl,
      demos: relatedDemos,
      properties: this.mergeProperties(this.extractProperties(content), javadocClass?.fields || []),
      events: this.extractEvents(content),
      methods: this.mergeMethods(this.extractMethods(content), javadocClass?.methods || [])
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

  private findJavaDocForComponent(componentName: string): JavaDocClass | null {
    // Try different variations of component names that might match JavaDoc classes
    const variations = [
      componentName,
      componentName.charAt(0).toUpperCase() + componentName.slice(1),
      this.camelCase(componentName),
      this.pascalCase(componentName)
    ];
    
    for (const variation of variations) {
      const found = this.javadocs.get(variation);
      if (found) {
        return found;
      }
      
      // Also search by class name in full name
      for (const [_, classInfo] of this.javadocs) {
        if (classInfo.name === variation || 
            classInfo.fullName.endsWith('.' + variation) ||
            classInfo.name.toLowerCase() === variation.toLowerCase()) {
          return classInfo;
        }
      }
    }
    
    return null;
  }

  private camelCase(str: string): string {
    return str.replace(/-([a-z])/g, (_, letter) => letter.toUpperCase());
  }

  private pascalCase(str: string): string {
    const camel = this.camelCase(str);
    return camel.charAt(0).toUpperCase() + camel.slice(1);
  }

  private mergeProperties(docProps: any[], javadocFields: any[]): any[] {
    const merged = [...docProps];
    
    // Add JavaDoc fields that aren't already covered by documentation
    for (const field of javadocFields) {
      const exists = merged.some(p => p.name === field.name);
      if (!exists) {
        merged.push({
          name: field.name,
          type: field.type,
          description: field.description || `Field from JavaDoc: ${field.name}`,
          source: 'javadoc'
        });
      }
    }
    
    return merged;
  }

  private mergeMethods(docMethods: any[], javadocMethods: any[]): any[] {
    const merged = [...docMethods];
    
    // Add important JavaDoc methods (setters, getters, event handlers, etc.)
    for (const method of javadocMethods) {
      const exists = merged.some(m => m.name === method.name);
      if (!exists && this.isImportantMethod(method)) {
        merged.push({
          name: method.name,
          description: method.description || `Method from JavaDoc: ${method.name}`,
          parameters: method.parameters || [],
          returnType: method.returnType || 'void',
          source: 'javadoc'
        });
      }
    }
    
    return merged;
  }

  private isImportantMethod(method: any): boolean {
    const name = method.name;
    
    // Include common API methods
    if (name.startsWith('set') || name.startsWith('get') || name.startsWith('is')) {
      return true;
    }
    
    // Include event-related methods
    if (name.includes('Event') || name.includes('Listener') || name.startsWith('on')) {
      return true;
    }
    
    // Include component lifecycle methods
    const lifecycleMethods = ['show', 'hide', 'enable', 'disable', 'focus', 'blur', 'refresh', 'update'];
    if (lifecycleMethods.some(lifecycle => name.toLowerCase().includes(lifecycle))) {
      return true;
    }
    
    return false;
  }
}