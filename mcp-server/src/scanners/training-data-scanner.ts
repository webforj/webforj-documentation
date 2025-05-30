import { glob } from 'glob';
import { readFileSync } from 'fs';
import { relative, join, dirname, basename } from 'path';
import type { TrainingExample, AntipatternExample } from '../types.js';

export class TrainingDataScanner {
  constructor(private basePath: string) {}

  async scan(): Promise<{
    antipatterns: Map<string, AntipatternExample>;
    examples: Map<string, TrainingExample>;
  }> {
    const antipatterns = new Map<string, AntipatternExample>();
    const examples = new Map<string, TrainingExample>();

    try {
      // Scan antipatterns directory
      const antipatternPath = join(this.basePath, 'training-data', 'antipatterns');
      const categories = await this.scanCategories(antipatternPath);
      
      for (const category of categories) {
        // Scan incorrect patterns
        const incorrectFiles = await glob(`${category}/incorrect/*.java`, {
          cwd: antipatternPath,
          absolute: true
        });
        
        // Scan correct patterns
        const correctFiles = await glob(`${category}/correct/*.java`, {
          cwd: antipatternPath,
          absolute: true
        });
        
        // Process pairs of incorrect/correct patterns
        for (const incorrectFile of incorrectFiles) {
          const content = readFileSync(incorrectFile, 'utf-8');
          const name = basename(incorrectFile, '.java');
          const categoryName = category;
          
          // Find corresponding correct file
          const correctFile = correctFiles.find(f => 
            basename(f, '.java').replace('correct-', '') === name.replace('incorrect-', '')
          );
          
          const antipattern: AntipatternExample = {
            id: `antipattern-${categoryName}-${name}`,
            category: categoryName,
            name: name,
            incorrectCode: content,
            correctCode: correctFile ? readFileSync(correctFile, 'utf-8') : '',
            explanation: this.extractExplanation(content),
            frameworks: this.detectFrameworks(content),
            tags: this.extractTags(content)
          };
          
          antipatterns.set(antipattern.id, antipattern);
        }
      }
      
      // Scan general training examples
      const examplesPath = join(this.basePath, 'training-data', 'examples');
      const exampleFiles = await glob('**/*.java', {
        cwd: examplesPath,
        absolute: true
      });
      
      for (const file of exampleFiles) {
        const content = readFileSync(file, 'utf-8');
        const relativePath = relative(examplesPath, file);
        const category = dirname(relativePath);
        
        const example: TrainingExample = {
          id: `example-${relativePath.replace(/[\/\\]/g, '-')}`,
          path: relativePath,
          category: category === '.' ? 'general' : category,
          content: content,
          concepts: this.extractConcepts(content),
          dependencies: this.extractDependencies(content)
        };
        
        examples.set(example.id, example);
      }
      
      console.error(`Scanned ${antipatterns.size} antipatterns and ${examples.size} training examples`);
      
    } catch (error) {
      console.error('Training data scanning failed:', error);
    }
    
    return { antipatterns, examples };
  }
  
  private async scanCategories(basePath: string): Promise<string[]> {
    try {
      const dirs = await glob('*/', {
        cwd: basePath,
        absolute: false
      });
      return dirs.map(d => d.replace(/\/$/, ''));
    } catch {
      return [];
    }
  }
  
  private extractExplanation(content: string): string {
    // Extract explanation from comments
    const match = content.match(/\/\*\*[\s\S]*?\*\//);
    if (match) {
      return match[0]
        .replace(/\/\*\*|\*\//g, '')
        .replace(/\* ?/gm, '')
        .trim();
    }
    return '';
  }
  
  private detectFrameworks(content: string): string[] {
    const frameworks = [];
    if (content.includes('useState') || content.includes('useEffect')) frameworks.push('React');
    if (content.includes('mounted()') || content.includes('v-model')) frameworks.push('Vue');
    if (content.includes('Stage') || content.includes('Scene')) frameworks.push('JavaFX');
    if (content.includes('addEventListener')) frameworks.push('DOM-style');
    return frameworks;
  }
  
  private extractTags(content: string): string[] {
    const tags = [];
    if (content.includes('lifecycle')) tags.push('lifecycle');
    if (content.includes('event')) tags.push('events');
    if (content.includes('route') || content.includes('Route')) tags.push('routing');
    if (content.includes('async') || content.includes('CompletableFuture')) tags.push('async');
    if (content.includes('style') || content.includes('css')) tags.push('styling');
    return tags;
  }
  
  private extractConcepts(content: string): string[] {
    const concepts = [];
    
    // Extract class names
    const classMatches = content.matchAll(/class\s+(\w+)/g);
    for (const match of classMatches) {
      concepts.push(`class:${match[1]}`);
    }
    
    // Extract interfaces
    const interfaceMatches = content.matchAll(/implements\s+([\w,\s]+)/g);
    for (const match of interfaceMatches) {
      const interfaces = match[1].split(',').map(i => i.trim());
      interfaces.forEach(i => concepts.push(`interface:${i}`));
    }
    
    // Extract key methods
    const methodMatches = content.matchAll(/public\s+\w+\s+(\w+)\s*\(/g);
    for (const match of methodMatches) {
      if (!['main', 'run'].includes(match[1])) {
        concepts.push(`method:${match[1]}`);
      }
    }
    
    return concepts;
  }
  
  private extractDependencies(content: string): string[] {
    const deps = [];
    const importMatches = content.matchAll(/import\s+([\w.]+);/g);
    for (const match of importMatches) {
      deps.push(match[1]);
    }
    return deps;
  }
}