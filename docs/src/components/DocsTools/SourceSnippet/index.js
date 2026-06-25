import React from 'react';
import { usePluginData } from '@docusaurus/useGlobalData';
import CodeBlock from '@theme/CodeBlock';

export default function SourceSnippet({
  file,
  region,
  language,
  title,
  metastring,
  showLineNumbers = true,
}) {
  const data = usePluginData('webforj-source-snippets');
  const key = `${file}#${region}`;
  const snippet = data?.snippets?.[key];

  if (!snippet) {
    return (
      <CodeBlock language="text">
        {`SourceSnippet not found: ${key}`}
      </CodeBlock>
    );
  }

  return (
    <CodeBlock
      language={language ?? snippet.language}
      title={title ?? snippet.title}
      metastring={metastring}
      showLineNumbers={showLineNumbers}
    >
      {snippet.code}
    </CodeBlock>
  );
}
