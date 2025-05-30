import type { AntipatternExample } from '../types.js';

export class AntipatternChecker {
  constructor(private antipatterns: Map<string, AntipatternExample>) {}
  
  checkCode(code: string): {
    detectedPatterns: AntipatternExample[];
    suggestions: string[];
  } {
    const detectedPatterns: AntipatternExample[] = [];
    const suggestions: string[] = [];
    
    // Check each antipattern
    for (const [id, antipattern] of this.antipatterns) {
      // Extract key incorrect patterns from the antipattern
      const incorrectPatterns = this.extractPatterns(antipattern.incorrectCode);
      
      // Check if the provided code contains any of these patterns
      for (const pattern of incorrectPatterns) {
        if (code.includes(pattern)) {
          detectedPatterns.push(antipattern);
          suggestions.push(this.generateSuggestion(antipattern, pattern));
          break; // Only report each antipattern once
        }
      }
    }
    
    return { detectedPatterns, suggestions };
  }
  
  getAntipatternsByCategory(category: string): AntipatternExample[] {
    return Array.from(this.antipatterns.values())
      .filter(ap => ap.category === category);
  }
  
  getAntipatternsByFramework(framework: string): AntipatternExample[] {
    return Array.from(this.antipatterns.values())
      .filter(ap => ap.frameworks.includes(framework));
  }
  
  private extractPatterns(code: string): string[] {
    const patterns: string[] = [];
    
    // Common incorrect patterns
    const patternRegexes = [
      /self\.onAttach/g,
      /self\.onDetach/g,
      /addEventListener\s*\(/g,
      /useState\s*\(/g,
      /useEffect\s*\(/g,
      /mounted\s*\(/g,
      /\.setState\s*\(/g,
      /router\.navigate/g,
      /router\.push/g,
      /styled\s*\(/g,
      /css`/g,
    ];
    
    for (const regex of patternRegexes) {
      const matches = code.match(regex);
      if (matches) {
        patterns.push(...matches);
      }
    }
    
    return patterns;
  }
  
  private generateSuggestion(antipattern: AntipatternExample, pattern: string): string {
    const correctExample = antipattern.correctCode;
    
    // Extract the correct pattern from the correct code
    let suggestion = `Found incorrect pattern: "${pattern}"\\n`;
    suggestion += `Category: ${antipattern.category}\\n`;
    
    if (antipattern.explanation) {
      suggestion += `Issue: ${antipattern.explanation}\\n`;
    }
    
    // Try to find the correct equivalent
    if (pattern.includes('onAttach')) {
      suggestion += 'Use: implements DidEnterObserver and override onDidEnter()';
    } else if (pattern.includes('addEventListener')) {
      suggestion += 'Use: specific event methods like onClick(), onBlur(), etc.';
    } else if (pattern.includes('useState')) {
      suggestion += 'Use: regular Java instance fields for state management';
    } else if (pattern.includes('router.')) {
      suggestion += 'Use: @Route annotation and Link components for navigation';
    }
    
    return suggestion;
  }
}