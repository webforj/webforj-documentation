/**
 * MDX Cleanser - Strips interactive MDX components while preserving prose content
 * This allows the documentation scanner to parse files that would otherwise fail
 * due to MDX/JSX syntax issues.
 */

export class MDXCleanser {
  /**
   * Cleanses MDX content by removing or replacing problematic components
   * while preserving the prose content and extracting component references.
   */
  static cleanse(content: string): {
    cleansedContent: string;
    extractedComponents: Array<{
      type: string;
      props: Record<string, string>;
      originalText: string;
    }>;
  } {
    const extractedComponents: Array<{
      type: string;
      props: Record<string, string>;
      originalText: string;
    }> = [];

    // Pattern to match self-closing MDX components like <ComponentDemo ... />
    const selfClosingComponentPattern = /<(\w+)\s+([^>]*?)\/>/g;
    
    // Pattern to match paired MDX components like <DocChip>content</DocChip>
    const pairedComponentPattern = /<(\w+)(\s+[^>]*?)?>(.*?)<\/\1>/gs;
    
    // Pattern to match import statements
    const importPattern = /^import\s+.*?;?\s*$/gm;
    
    // Pattern to match HTML comments that cause MDX issues
    const htmlCommentPattern = /<!--.*?-->/gs;

    let cleansedContent = content;

    // Extract and replace self-closing components
    cleansedContent = cleansedContent.replace(selfClosingComponentPattern, (match, componentName, propsString) => {
      const props = this.parseProps(propsString);
      extractedComponents.push({
        type: componentName,
        props,
        originalText: match
      });

      // Special handling for ComponentDemo - preserve as a reference
      if (componentName === 'ComponentDemo') {
        const path = props.path || '';
        const javaFile = props.javaE || '';
        return `\n[Component Demo: ${path}]\n`;
      }
      
      // For other components, just remove them
      return '';
    });

    // Extract and replace paired components
    cleansedContent = cleansedContent.replace(pairedComponentPattern, (match, componentName, propsString, content) => {
      const props = this.parseProps(propsString || '');
      extractedComponents.push({
        type: componentName,
        props,
        originalText: match
      });

      // For DocChip and similar metadata components, preserve the label
      if (componentName === 'DocChip' && props.label) {
        return `[${props.label}]`;
      }

      // For JavadocLink, preserve as a reference
      if (componentName === 'JavadocLink' && props.location) {
        return `[JavaDoc: ${props.location}]`;
      }

      // Keep the content for most paired components
      return content.trim();
    });

    // Remove import statements (they're not needed for prose extraction)
    cleansedContent = cleansedContent.replace(importPattern, '');

    // Remove HTML comments
    cleansedContent = cleansedContent.replace(htmlCommentPattern, '');

    // Clean up excessive newlines
    cleansedContent = cleansedContent.replace(/\n{3,}/g, '\n\n');

    return {
      cleansedContent: cleansedContent.trim(),
      extractedComponents
    };
  }

  /**
   * Parses props string into key-value pairs
   */
  private static parseProps(propsString: string): Record<string, string> {
    const props: Record<string, string> = {};
    
    // Match prop patterns like key="value" or key='value' or key={value}
    const propPattern = /(\w+)=(?:"([^"]*)"|'([^']*)'|{([^}]*)})/g;
    
    let match;
    while ((match = propPattern.exec(propsString)) !== null) {
      const key = match[1];
      const value = match[2] || match[3] || match[4] || '';
      props[key] = value;
    }

    return props;
  }

  /**
   * Extracts ComponentDemo references to preserve component linking
   */
  static extractComponentDemos(content: string): string[] {
    const demos: string[] = [];
    const pattern = /<ComponentDemo\s+path=['"]([^'"]+)['"].*?\/>/g;
    
    let match;
    while ((match = pattern.exec(content)) !== null) {
      demos.push(match[1]);
    }

    return demos;
  }
}