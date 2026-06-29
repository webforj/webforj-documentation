import React, { useState } from 'react';
import CodeBlock from '@theme/CodeBlock';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import { translate } from '@docusaurus/Translate';
import styles from './styles.module.css';

function getLanguageFromClassName(className) {
  return className
    ?.split(' ')
    .find((name) => name.startsWith('language-'))
    ?.replace('language-', '');
}

function getCodeElement(node) {
  if (!React.isValidElement(node)) {
    return null;
  }

  if (node.type === 'code') {
    return node;
  }

  return React.Children.toArray(node.props.children)
    .map(getCodeElement)
    .find(Boolean);
}

function getTextContent(node) {
  if (typeof node === 'string') {
    return node;
  }

  if (Array.isArray(node)) {
    return node.map(getTextContent).join('');
  }

  if (React.isValidElement(node)) {
    return getTextContent(node.props.children);
  }

  return '';
}

function trimWrapperBlankLines(code) {
  const lines = code.replace(/\r\n/g, '\n').split('\n');

  while (lines.length > 0 && lines[0].trim() === '') {
    lines.shift();
  }

  while (lines.length > 0 && lines[lines.length - 1].trim() === '') {
    lines.pop();
  }

  return lines.join('\n');
}

export default function ExpandableCode({ 
  children, 
  language = 'java', 
  title = '',
  previewLines = 20,
  startLine = 1,
  endLine = null 
}) {
  const [isExpanded, setIsExpanded] = useState(false);

  const codeElement = React.Children.toArray(children)
    .map(getCodeElement)
    .find(Boolean);
  const code = trimWrapperBlankLines(getTextContent(codeElement ?? children));
  const codeLanguage = getLanguageFromClassName(codeElement?.props.className) ?? language;
  
  // Split the code into lines
  const lines = code.split('\n');
  
  // Calculate preview range
  const previewStart = startLine - 1; // Convert to 0-based index
  const previewEnd = endLine ? endLine : previewStart + previewLines;
  
  // Get preview content based on specified range
  const previewContent = lines.slice(previewStart, previewEnd).join('\n');
  const fullContent = code;
  
  // Check if we need expand/collapse functionality
  const hasOverflow = endLine ? (endLine < lines.length || startLine > 1) : lines.length > previewLines;
  
  return (
    <div className={styles.expandableCode}>
      <div className={styles.codeWrapper}>
        <div className={`${styles.codeContainer} ${isExpanded ? styles.expanded : styles.collapsed}`}>
          <CodeBlock
            language={codeLanguage}
            title={title}
            showLineNumbers
            className={styles.codeBlock}
          >
            {isExpanded ? fullContent : previewContent}
          </CodeBlock>
          
          {!isExpanded && hasOverflow && (
            <div className={styles.gradient} />
          )}
        </div>
        
        {hasOverflow && (
          <button 
            className={styles.expandButton}
            onClick={() => setIsExpanded(!isExpanded)}
            aria-expanded={isExpanded}
            aria-controls="expandable-code-content"
          >
            <span>
              {isExpanded 
                ? translate({
                    id: 'expandableCode.hideCode',
                    message: 'Hide Code',
                    description: 'Button text to hide the expanded code'
                  })
                : translate({
                    id: 'expandableCode.showAllLines',
                    message: 'Show all {count} lines',
                    description: 'Button text to show all lines of code'
                  }, { count: lines.length })
              }
            </span>
            <ChevronRightIcon className={`${styles.chevron} ${isExpanded ? styles.chevronExpanded : ''}`} />
          </button>
        )}
      </div>
    </div>
  );
}
