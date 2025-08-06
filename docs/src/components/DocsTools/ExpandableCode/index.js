import React, { useState } from 'react';
import CodeBlock from '@theme/CodeBlock';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import { translate } from '@docusaurus/Translate';
import styles from './styles.module.css';

export default function ExpandableCode({ 
  children, 
  language = 'java', 
  title = '',
  previewLines = 20,
  startLine = 1,
  endLine = null 
}) {
  const [isExpanded, setIsExpanded] = useState(false);
  
  // Split the code into lines
  const lines = children.trim().split('\n');
  
  // Calculate preview range
  const previewStart = startLine - 1; // Convert to 0-based index
  const previewEnd = endLine ? endLine : previewStart + previewLines;
  
  // Get preview content based on specified range
  const previewContent = lines.slice(previewStart, previewEnd).join('\n');
  const fullContent = children;
  
  // Check if we need expand/collapse functionality
  const hasOverflow = endLine ? (endLine < lines.length || startLine > 1) : lines.length > previewLines;
  
  return (
    <div className={styles.expandableCode}>
      <div className={styles.codeWrapper}>
        <div className={`${styles.codeContainer} ${isExpanded ? styles.expanded : styles.collapsed}`}>
          <CodeBlock
            language={language}
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