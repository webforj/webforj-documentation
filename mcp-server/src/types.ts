export interface ComponentMetadata {
  name: string;
  displayName: string;
  description?: string;
  category?: string;
  javadocUrl?: string;
  demos: DemoMetadata[];
  properties?: PropertyMetadata[];
  events?: EventMetadata[];
  methods?: MethodMetadata[];
}

export interface DemoMetadata {
  name: string;
  route: string;
  title?: string;
  description?: string;
  sourceFiles: string[];
  liveUrl?: string;
  highlighted?: boolean;
}

export interface PropertyMetadata {
  name: string;
  type: string;
  description?: string;
  defaultValue?: string;
}

export interface EventMetadata {
  name: string;
  description?: string;
  payload?: string;
}

export interface MethodMetadata {
  name: string;
  description?: string;
  parameters?: string[];
  returnType?: string;
}

export interface DocumentMetadata {
  path: string;
  title: string;
  content: string;
  frontmatter?: Record<string, any>;
  codeBlocks: CodeBlock[];
  componentRefs: string[];
}

export interface CodeBlock {
  language: string;
  code: string;
  title?: string;
  highlight?: string;
}

export interface MCPIndex {
  components: Map<string, ComponentMetadata>;
  documents: Map<string, DocumentMetadata>;
  demos: Map<string, DemoMetadata>;
  lastBuilt: Date;
  version: string;
}