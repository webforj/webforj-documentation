import { readFileSync } from 'fs';
import { glob } from 'glob';
import { join, basename, relative, dirname } from 'path';
import type { DemoMetadata } from '../types.js';

export class JavaDemoScanner {
  constructor(private demoRoot: string) {}

  async scan(): Promise<Map<string, DemoMetadata>> {
    const demos = new Map<string, DemoMetadata>();
    
    // Find all Java view files
    const pattern = join(this.demoRoot, '**/views/**/*View.java');
    const files = await glob(pattern);
    console.error(`JavaDemoScanner: Found ${files.length} Java view files`);

    for (const file of files) {
      try {
        const metadata = this.parseJavaDemo(file);
        if (metadata && metadata.route) {
          demos.set(metadata.route, metadata);
        }
      } catch (error) {
        console.error(`Error parsing ${file}:`, error);
      }
    }

    console.error(`JavaDemoScanner: Parsed ${demos.size} demos with routes`);
    return demos;
  }

  private parseJavaDemo(filePath: string): DemoMetadata | null {
    const content = readFileSync(filePath, 'utf-8');
    const fileName = basename(filePath, '.java');
    
    // Check if file has @Route annotation (with or without parameters)
    const hasRoute = /@Route(\s*\(|\s|$)/.test(content);
    if (!hasRoute) {
      return null;
    }
    
    // Extract @Route annotation with explicit value
    const explicitRouteMatch = content.match(/@Route\s*\(\s*"([^"]+)"\s*\)/);
    
    // Generate route based on annotation and class name
    let route: string;
    if (explicitRouteMatch) {
      route = explicitRouteMatch[1];
    } else {
      // When @Route has no parameters, use the class name as the route
      // Convert class name from PascalCase to kebab-case and remove 'View' suffix
      // e.g., ButtonView -> button, ButtonThemesView -> button-themes
      route = this.camelToKebab(fileName.replace(/View$/, ''));
    }
    
    // Extract @FrameTitle annotation
    const titleMatch = content.match(/@FrameTitle\s*\(\s*"([^"]+)"\s*\)/);
    
    // Extract class-level Javadoc
    const javadocMatch = content.match(/\/\*\*\s*\n([^*]|\*(?!\/))*\*\/\s*(?=@|public\s+class)/);
    let description = '';
    if (javadocMatch) {
      description = javadocMatch[0]
        .replace(/\/\*\*|\*\//g, '')
        .replace(/^\s*\*\s?/gm, '')
        .trim();
    }
    
    // Get relative path for source file reference
    const relativePath = relative(this.demoRoot, filePath);
    
    return {
      name: fileName,
      route,
      title: titleMatch?.[1] || this.humanizeClassName(fileName),
      description,
      sourceFiles: [relativePath],
      highlighted: false,
      className: fileName,
      filePath: relativePath
    };
  }

  private humanizeClassName(className: string): string {
    // Remove "View" suffix and add spaces before capitals
    return className
      .replace(/View$/, '')
      .replace(/([A-Z])/g, ' $1')
      .trim();
  }
  
  private camelToKebab(str: string): string {
    return str
      .replace(/([a-z0-9])([A-Z])/g, '$1-$2')
      .toLowerCase();
  }
}