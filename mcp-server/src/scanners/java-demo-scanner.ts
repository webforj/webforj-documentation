import { readFileSync } from 'fs';
import { glob } from 'glob';
import { join, basename, relative } from 'path';
import type { DemoMetadata } from '../types.js';

export class JavaDemoScanner {
  constructor(private demoRoot: string) {}

  async scan(): Promise<Map<string, DemoMetadata>> {
    const demos = new Map<string, DemoMetadata>();
    
    // Find all Java view files
    const pattern = join(this.demoRoot, '**/views/**/*View.java');
    const files = await glob(pattern);

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

    return demos;
  }

  private parseJavaDemo(filePath: string): DemoMetadata | null {
    const content = readFileSync(filePath, 'utf-8');
    const fileName = basename(filePath, '.java');
    
    // Extract @Route annotation
    const routeMatch = content.match(/@Route\s*\(\s*"([^"]+)"\s*\)/);
    if (!routeMatch) {
      return null;
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
    
    // Find related files (CSS, other Java files in same package)
    const relativePath = relative(this.demoRoot, filePath);
    const packagePath = relativePath.substring(0, relativePath.lastIndexOf('/'));
    
    return {
      name: fileName,
      route: routeMatch[1],
      title: titleMatch?.[1] || this.humanizeClassName(fileName),
      description,
      sourceFiles: [relativePath],
      highlighted: false
    };
  }

  private humanizeClassName(className: string): string {
    // Remove "View" suffix and add spaces before capitals
    return className
      .replace(/View$/, '')
      .replace(/([A-Z])/g, ' $1')
      .trim();
  }
}