import { PropertyMetadata, MethodMetadata, EventMetadata } from '../types.js';

interface TableRow {
  [key: string]: string;
}

export class MarkdownTableParser {
  /**
   * Parse a markdown table into structured data
   */
  parseTable(tableContent: string): TableRow[] {
    const lines = tableContent.trim().split('\n');
    if (lines.length < 2) return [];

    // Extract headers
    const headerLine = lines[0];
    const headers = this.parseTableRow(headerLine);
    
    // Skip separator line (line 1)
    const rows: TableRow[] = [];
    
    for (let i = 2; i < lines.length; i++) {
      const values = this.parseTableRow(lines[i]);
      if (values.length === headers.length) {
        const row: TableRow = {};
        headers.forEach((header, index) => {
          row[header] = values[index];
        });
        rows.push(row);
      }
    }
    
    return rows;
  }

  /**
   * Parse a single table row, handling escaped pipes
   */
  private parseTableRow(line: string): string[] {
    // Remove leading/trailing pipes and split
    const trimmed = line.replace(/^\s*\|\s*/, '').replace(/\s*\|\s*$/, '');
    const cells = trimmed.split(/\s*\|\s*/);
    return cells.map(cell => cell.trim());
  }

  /**
   * Extract properties from a markdown table
   */
  extractProperties(content: string): PropertyMetadata[] {
    const properties: PropertyMetadata[] = [];
    
    // Look for property tables (usually after "## Properties" heading)
    const propertyTableRegex = /##\s*Properties.*?\n\n((?:\|.*\n)+)/gis;
    const matches = content.matchAll(propertyTableRegex);
    
    for (const match of matches) {
      const tableContent = match[1];
      const rows = this.parseTable(tableContent);
      
      for (const row of rows) {
        const prop: PropertyMetadata = {
          name: row['Property'] || row['Name'] || row['Attribute'] || '',
          type: row['Type'] || row['Java Type'] || 'String',
          description: row['Description'] || ''
        };
        
        if (row['Default']) {
          prop.defaultValue = row['Default'];
        }
        
        if (prop.name) {
          properties.push(prop);
        }
      }
    }
    
    return properties;
  }

  /**
   * Extract methods from a markdown table
   */
  extractMethods(content: string): MethodMetadata[] {
    const methods: MethodMetadata[] = [];
    
    // Look for method tables
    const methodTableRegex = /##\s*Methods.*?\n\n((?:\|.*\n)+)/gis;
    const matches = content.matchAll(methodTableRegex);
    
    for (const match of matches) {
      const tableContent = match[1];
      const rows = this.parseTable(tableContent);
      
      for (const row of rows) {
        const method: MethodMetadata = {
          name: row['Method'] || row['Name'] || '',
          description: row['Description'] || ''
        };
        
        if (row['Parameters']) {
          method.parameters = this.parseParameters(row['Parameters']);
        }
        
        if (row['Return Type'] || row['Returns']) {
          method.returnType = row['Return Type'] || row['Returns'];
        }
        
        if (method.name) {
          methods.push(method);
        }
      }
    }
    
    return methods;
  }

  /**
   * Extract events from EventTable components in MDX
   */
  extractEvents(content: string): EventMetadata[] {
    const events: EventMetadata[] = [];
    
    // Look for EventTable components
    const eventTableRegex = /<EventTable\s+events=\{([^}]+)\}/g;
    const matches = content.matchAll(eventTableRegex);
    
    for (const match of matches) {
      try {
        // Parse the events prop (it's usually a JavaScript array)
        const eventsCode = match[1];
        const eventMatches = eventsCode.matchAll(/{\s*name:\s*["']([^"']+)["'],\s*description:\s*["']([^"']+)["'],\s*eventObject:\s*["']([^"']+)["']/g);
        
        for (const eventMatch of eventMatches) {
          events.push({
            name: eventMatch[1],
            description: eventMatch[2],
            payload: eventMatch[3]
          });
        }
      } catch (e) {
        // If parsing fails, try simple regex
        const simpleEventRegex = /on[A-Z]\w+/g;
        const simpleMatches = content.matchAll(simpleEventRegex);
        for (const match of simpleMatches) {
          events.push({
            name: match[0],
            description: `${match[0]} event handler`
          });
        }
      }
    }
    
    // Also look for event tables in markdown
    const eventMarkdownRegex = /##\s*Events.*?\n\n((?:\|.*\n)+)/gis;
    const markdownMatches = content.matchAll(eventMarkdownRegex);
    
    for (const match of markdownMatches) {
      const tableContent = match[1];
      const rows = this.parseTable(tableContent);
      
      for (const row of rows) {
        const event: EventMetadata = {
          name: row['Event'] || row['Name'] || '',
          description: row['Description'] || ''
        };
        
        if (row['Payload'] || row['Event Object']) {
          event.payload = row['Payload'] || row['Event Object'];
        }
        
        if (event.name) {
          events.push(event);
        }
      }
    }
    
    return events;
  }

  /**
   * Parse parameter string into structured format
   */
  private parseParameters(paramString: string): Array<{name: string, type?: string}> {
    const params: Array<{name: string, type?: string}> = [];
    
    // Handle format like "String text, boolean enabled"
    const paramParts = paramString.split(',');
    for (const part of paramParts) {
      const trimmed = part.trim();
      const spaceIndex = trimmed.indexOf(' ');
      if (spaceIndex > -1) {
        params.push({
          type: trimmed.substring(0, spaceIndex),
          name: trimmed.substring(spaceIndex + 1)
        });
      } else {
        params.push({ name: trimmed });
      }
    }
    
    return params;
  }

  /**
   * Extract component metadata from frontmatter and MDX components
   */
  extractComponentMetadata(content: string): {
    title?: string;
    sidebar_label?: string;
    javadocUrl?: string;
    category?: string;
  } {
    const metadata: any = {};
    
    // Extract from ComponentHeader if present
    const headerRegex = /<ComponentHeader\s+([^>]+)>/;
    const headerMatch = content.match(headerRegex);
    
    if (headerMatch) {
      const propsString = headerMatch[1];
      
      // Extract javadocUrl
      const javadocMatch = propsString.match(/javadocUrl=["']([^"']+)["']/);
      if (javadocMatch) {
        metadata.javadocUrl = javadocMatch[1];
      }
      
      // Extract title
      const titleMatch = propsString.match(/title=["']([^"']+)["']/);
      if (titleMatch) {
        metadata.title = titleMatch[1];
      }
    }
    
    return metadata;
  }
}