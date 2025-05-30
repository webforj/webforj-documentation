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
  className: string;
  filePath?: string;
  repository?: {
    owner: string;
    repo: string;
    branch: string;
    url: string;
  };
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
  parameters?: Array<{name: string, type?: string}>;
  returnType?: string;
}

export interface DocumentMetadata {
  path: string;
  title: string;
  content: string;
  frontmatter?: Record<string, any>;
  codeBlocks: CodeBlock[];
  componentRefs: string[];
  headings?: string[];
  extractedData?: {
    properties: PropertyMetadata[];
    methods: MethodMetadata[];
    events: EventMetadata[];
  };
}

export interface CodeBlock {
  language: string;
  code: string;
  title?: string;
  highlight?: string;
}

export interface MCPIndex {
  components: Map<string, ComponentMetadata>;
  docs: Map<string, DocumentMetadata>;
  demos: Map<string, DemoMetadata>;
  javadocClasses: Map<string, JavaDocClass>;
  antipatterns?: Map<string, AntipatternExample>;
  trainingExamples?: Map<string, TrainingExample>;
}

export interface JavaDocClass {
  name: string;
  qualifiedName: string;
  packageName: string;
  description?: string;
  methods: JavaDocMethod[];
  fields: JavaDocField[];
  constructors: JavaDocConstructor[];
  extends?: string;
  implements?: string[];
}

export interface JavaDocMethod {
  name: string;
  signature: string;
  description?: string;
  parameters: Array<{name: string, type: string, description?: string}>;
  returnType: string;
  returnDescription?: string;
  modifiers: string[];
}

export interface JavaDocField {
  name: string;
  type: string;
  description?: string;
  modifiers: string[];
}

export interface JavaDocConstructor {
  signature: string;
  description?: string;
  parameters: Array<{name: string, type: string, description?: string}>;
  modifiers: string[];
}

export interface AntipatternExample {
  id: string;
  category: string;
  name: string;
  incorrectCode: string;
  correctCode: string;
  explanation: string;
  frameworks: string[];
  tags: string[];
}

export interface TrainingExample {
  id: string;
  path: string;
  category: string;
  content: string;
  concepts: string[];
  dependencies: string[];
}