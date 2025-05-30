import { readFileSync, existsSync } from 'fs';
import { join, dirname, basename } from 'path';
import { DemoMetadata } from '../types.js';

export interface DemoSourceCode {
  java: string;
  css?: string;
  resources?: Record<string, string>;
  imports: string[];
  className: string;
  route: string;
}

export class DemoCodeRetriever {
  constructor(private demoRoot: string, private resourceRoot: string) {}

  /**
   * Retrieves complete source code for a demo including Java, CSS, and resources
   */
  async getCompleteDemo(demo: DemoMetadata): Promise<DemoSourceCode> {
    const result: DemoSourceCode = {
      java: '',
      className: demo.className,
      route: demo.route,
      imports: []
    };

    // Read Java source
    if (demo.filePath) {
      const javaPath = join(this.demoRoot, demo.filePath);
      if (existsSync(javaPath)) {
        result.java = readFileSync(javaPath, 'utf-8');
        result.imports = this.extractImports(result.java);
      }
    }

    // Find and read CSS files
    const cssPath = this.findCssFile(demo);
    if (cssPath && existsSync(cssPath)) {
      result.css = readFileSync(cssPath, 'utf-8');
    }

    // Find related resources (images, data files, etc.)
    result.resources = this.findRelatedResources(demo);

    return result;
  }

  /**
   * Extract import statements from Java code
   */
  private extractImports(javaCode: string): string[] {
    const importRegex = /^import\s+([\w.]+);/gm;
    const imports: string[] = [];
    let match;
    
    while ((match = importRegex.exec(javaCode)) !== null) {
      imports.push(match[1]);
    }
    
    return imports;
  }

  /**
   * Find CSS file for a demo based on naming conventions
   */
  private findCssFile(demo: DemoMetadata): string | null {
    if (!demo.filePath) return null;

    // Convert Java path to CSS path
    // e.g., views/button/ButtonView.java -> css/button/button.css
    const parts = demo.filePath.split('/');
    if (parts.length < 2) return null;

    const component = parts[parts.length - 2]; // 'button'
    const viewName = basename(parts[parts.length - 1], '.java'); // 'ButtonView'
    
    // Try multiple CSS file naming patterns
    const patterns = [
      `css/${component}/${viewName.replace('View', '').toLowerCase()}.css`,
      `css/${component}/${component}.css`,
      `css/${component}/${viewName}.css`,
      `css/${viewName.replace('View', '').toLowerCase()}.css`
    ];

    for (const pattern of patterns) {
      const cssPath = join(this.resourceRoot, pattern);
      if (existsSync(cssPath)) {
        return cssPath;
      }
    }

    return null;
  }

  /**
   * Find related resource files (images, data, etc.)
   */
  private findRelatedResources(demo: DemoMetadata): Record<string, string> {
    const resources: Record<string, string> = {};
    
    // Look for component-specific resources
    if (demo.filePath) {
      const component = demo.filePath.split('/')[1];
      
      // Check for data files
      const dataPath = join(this.resourceRoot, 'data', `${component}.json`);
      if (existsSync(dataPath)) {
        resources['data.json'] = readFileSync(dataPath, 'utf-8');
      }
      
      // Check for icon/image files
      const iconPath = join(this.resourceRoot, 'icons', `${component}.png`);
      if (existsSync(iconPath)) {
        resources['icon.png'] = `<binary file: ${iconPath}>`;
      }
    }
    
    return resources;
  }

  /**
   * Generate a minimal runnable example from the demo
   */
  generateMinimalExample(demo: DemoSourceCode): string {
    const imports = demo.imports
      .filter(imp => imp.startsWith('com.webforj'))
      .join('\n');

    return `package com.example;

${imports}
import com.webforj.App;
import com.webforj.annotation.AppMeta;

@AppMeta
public class ${demo.className}Example extends App {
    @Override
    public void run() {
        // Demo code from ${demo.className}
        // View full implementation at: https://docs.webforj.com/demos/${demo.route}
    }
}`;
  }
}