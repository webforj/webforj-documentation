import { readFileSync, existsSync } from 'fs';
import { glob } from 'glob';
import { join, dirname } from 'path';
import { parse } from 'node-html-parser';
import type { ComponentMetadata, PropertyMetadata, MethodMetadata, EventMetadata } from '../types.js';

export interface JavaDocClass {
  name: string;
  fullName: string;
  description: string;
  methods: MethodMetadata[];
  fields: PropertyMetadata[];
  constructors: MethodMetadata[];
  inheritance: string[];
}

export class JavaDocScanner {
  private javadocRoot: string;
  private classCache = new Map<string, JavaDocClass>();

  constructor(javadocRoot: string) {
    this.javadocRoot = javadocRoot;
  }

  async scan(): Promise<Map<string, JavaDocClass>> {
    const classes = new Map<string, JavaDocClass>();
    
    // Find all HTML files in JavaDoc directories
    const patterns = [
      join(this.javadocRoot, '**/*.html'),
    ];
    
    for (const pattern of patterns) {
      const files = await glob(pattern, { 
        ignore: ['**/package-*.html', '**/overview-*.html', '**/help-*.html'] 
      });
      
      for (const file of files) {
        try {
          const classInfo = this.parseClassFile(file);
          if (classInfo) {
            classes.set(classInfo.name, classInfo);
            this.classCache.set(classInfo.fullName, classInfo);
          }
        } catch (error) {
          console.error(`Error parsing JavaDoc file ${file}:`, error);
        }
      }
    }
    
    return classes;
  }

  private parseClassFile(filePath: string): JavaDocClass | null {
    if (!existsSync(filePath)) {
      return null;
    }

    const content = readFileSync(filePath, 'utf-8');
    const doc = parse(content);
    
    // Extract class name from title or heading
    const titleElement = doc.querySelector('title');
    const title = titleElement?.text || '';
    
    // Skip non-class files (package summaries, etc.)
    if (!title.includes('(') || title.includes('package')) {
      return null;
    }
    
    // Parse class name
    const className = this.extractClassName(title);
    if (!className) {
      return null;
    }
    
    // Extract full package name
    const packageElement = doc.querySelector('.package-name');
    const packageName = packageElement?.text?.trim() || '';
    const fullName = packageName ? `${packageName}.${className}` : className;
    
    // Extract class description
    const descriptionElement = doc.querySelector('.class-description .block, .type-signature + .block');
    const description = descriptionElement?.text?.trim() || '';
    
    // Extract inheritance hierarchy
    const inheritance = this.extractInheritance(doc);
    
    // Extract methods
    const methods = this.extractMethods(doc);
    
    // Extract fields (properties)
    const fields = this.extractFields(doc);
    
    // Extract constructors
    const constructors = this.extractConstructors(doc);
    
    return {
      name: className,
      fullName,
      description,
      methods,
      fields,
      constructors,
      inheritance
    };
  }

  private extractClassName(title: string): string | null {
    // Extract class name from JavaDoc title like "Button (webforj 25.01 API)"
    const match = title.match(/^([A-Z][a-zA-Z0-9_]*)/);
    return match ? match[1] : null;
  }

  private extractInheritance(doc: any): string[] {
    const inheritance: string[] = [];
    
    // Look for inheritance information
    const inheritanceSection = doc.querySelector('.inheritance');
    if (inheritanceSection) {
      const links = inheritanceSection.querySelectorAll('a');
      links.forEach((link: any) => {
        const className = link.text?.trim();
        if (className && !inheritance.includes(className)) {
          inheritance.push(className);
        }
      });
    }
    
    return inheritance;
  }

  private extractMethods(doc: any): MethodMetadata[] {
    const methods: MethodMetadata[] = [];
    
    // Find method summary table
    const methodSections = doc.querySelectorAll('.method-summary table tbody tr, .member-summary tbody tr');
    
    methodSections.forEach((row: any) => {
      const nameCell = row.querySelector('.col-second code, .col-first code');
      const descCell = row.querySelector('.col-last .block, .col-last');
      
      if (nameCell) {
        const methodSignature = nameCell.text?.trim();
        const description = descCell?.text?.trim() || '';
        
        if (methodSignature) {
          const method = this.parseMethodSignature(methodSignature, description);
          if (method) {
            methods.push(method);
          }
        }
      }
    });
    
    return methods;
  }

  private extractFields(doc: any): PropertyMetadata[] {
    const fields: PropertyMetadata[] = [];
    
    // Find field summary table
    const fieldSections = doc.querySelectorAll('.field-summary table tbody tr, .member-summary tbody tr');
    
    fieldSections.forEach((row: any) => {
      const typeCell = row.querySelector('.col-first code');
      const nameCell = row.querySelector('.col-second code');
      const descCell = row.querySelector('.col-last .block, .col-last');
      
      if (nameCell && typeCell) {
        const fieldName = nameCell.text?.trim();
        const fieldType = typeCell.text?.trim();
        const description = descCell?.text?.trim() || '';
        
        if (fieldName && fieldType) {
          fields.push({
            name: fieldName,
            type: fieldType,
            description
          });
        }
      }
    });
    
    return fields;
  }

  private extractConstructors(doc: any): MethodMetadata[] {
    const constructors: MethodMetadata[] = [];
    
    // Find constructor summary table
    const constructorSections = doc.querySelectorAll('.constructor-summary table tbody tr');
    
    constructorSections.forEach((row: any) => {
      const nameCell = row.querySelector('.col-first code, .col-second code');
      const descCell = row.querySelector('.col-last .block, .col-last');
      
      if (nameCell) {
        const constructorSignature = nameCell.text?.trim();
        const description = descCell?.text?.trim() || '';
        
        if (constructorSignature) {
          const constructor = this.parseMethodSignature(constructorSignature, description);
          if (constructor) {
            constructors.push(constructor);
          }
        }
      }
    });
    
    return constructors;
  }

  private parseMethodSignature(signature: string, description: string): MethodMetadata | null {
    // Parse method signature like "setText(String text)"
    const methodMatch = signature.match(/(\w+)\s*\((.*?)\)/);
    if (!methodMatch) {
      return null;
    }
    
    const methodName = methodMatch[1];
    const parametersStr = methodMatch[2];
    
    // Parse parameters
    const parameters: string[] = [];
    if (parametersStr.trim()) {
      // Simple parameter parsing - could be enhanced
      const paramParts = parametersStr.split(',');
      paramParts.forEach(param => {
        const cleanParam = param.trim();
        if (cleanParam) {
          parameters.push(cleanParam);
        }
      });
    }
    
    // Extract return type if present
    const returnTypeMatch = signature.match(/^(\w+(?:\[\])?)\s+\w+\(/);
    const returnType = returnTypeMatch ? returnTypeMatch[1] : 'void';
    
    return {
      name: methodName,
      description,
      parameters,
      returnType
    };
  }

  // Helper method to find JavaDoc for a specific component
  findComponentJavaDoc(componentName: string): JavaDocClass | null {
    // Try different variations of the component name
    const variations = [
      componentName,
      componentName.charAt(0).toUpperCase() + componentName.slice(1),
      componentName.replace(/([A-Z])/g, ' $1').trim().split(' ').map(word => 
        word.charAt(0).toUpperCase() + word.slice(1)).join('')
    ];
    
    for (const variation of variations) {
      const found = this.classCache.get(variation);
      if (found) {
        return found;
      }
      
      // Also try searching by class name only
      for (const [fullName, classInfo] of this.classCache) {
        if (classInfo.name === variation) {
          return classInfo;
        }
      }
    }
    
    return null;
  }
}