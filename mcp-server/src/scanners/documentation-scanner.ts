import { readFileSync } from 'fs';
import { glob } from 'glob';
import { join, relative } from 'path';
import matter from 'gray-matter';
import { fromMarkdown } from 'mdast-util-from-markdown';
import { mdxjs } from 'micromark-extension-mdxjs';
import { mdxFromMarkdown } from 'mdast-util-mdx';
import type { DocumentMetadata, CodeBlock } from '../types.js';

export class DocumentationScanner {
  constructor(private docsRoot: string) {}

  async scan(): Promise<Map<string, DocumentMetadata>> {
    const documents = new Map<string, DocumentMetadata>();
    
    // Find all markdown/mdx files
    const pattern = join(this.docsRoot, '**/*.{md,mdx}');
    const files = await glob(pattern, { 
      ignore: ['**/node_modules/**', '**/build/**'] 
    });

    for (const file of files) {
      try {
        const metadata = await this.parseDocument(file);
        const key = relative(this.docsRoot, file).replace(/\.(md|mdx)$/, '');
        documents.set(key, metadata);
      } catch (error) {
        console.error(`Error parsing ${file}:`, error);
      }
    }

    return documents;
  }

  private async parseDocument(filePath: string): Promise<DocumentMetadata> {
    const content = readFileSync(filePath, 'utf-8');
    const { data: frontmatter, content: markdownContent } = matter(content);
    
    // Parse MDX/Markdown to AST
    const tree = fromMarkdown(markdownContent, {
      extensions: [mdxjs()],
      mdastExtensions: [mdxFromMarkdown()]
    });

    // Extract code blocks
    const codeBlocks = this.extractCodeBlocks(tree);
    
    // Extract component references (ComponentDemo usage)
    const componentRefs = this.extractComponentRefs(markdownContent);
    
    // Extract title from frontmatter or first heading
    const title = frontmatter.title || this.extractTitle(tree) || 'Untitled';

    return {
      path: filePath,
      title,
      content: markdownContent,
      frontmatter,
      codeBlocks,
      componentRefs
    };
  }

  private extractCodeBlocks(tree: any): CodeBlock[] {
    const blocks: CodeBlock[] = [];
    
    const visit = (node: any) => {
      if (node.type === 'code') {
        blocks.push({
          language: node.lang || 'text',
          code: node.value,
          title: node.meta?.match(/title="([^"]+)"/)?.[1]
        });
      }
      
      if (node.children) {
        node.children.forEach(visit);
      }
    };
    
    visit(tree);
    return blocks;
  }

  private extractComponentRefs(content: string): string[] {
    const refs: string[] = [];
    const componentDemoRegex = /<ComponentDemo\s+path=['"]([^'"]+)['"]/g;
    
    let match;
    while ((match = componentDemoRegex.exec(content)) !== null) {
      refs.push(match[1]);
    }
    
    return refs;
  }

  private extractTitle(tree: any): string | null {
    let title: string | null = null;
    
    const visit = (node: any) => {
      if (!title && node.type === 'heading' && node.depth === 1) {
        title = node.children
          .filter((child: any) => child.type === 'text')
          .map((child: any) => child.value)
          .join('');
      }
      
      if (node.children && !title) {
        node.children.forEach(visit);
      }
    };
    
    visit(tree);
    return title;
  }
}