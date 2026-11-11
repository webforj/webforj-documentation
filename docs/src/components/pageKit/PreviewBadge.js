import React from 'react';
import { translate } from '@docusaurus/Translate';
import styles from './PreviewBadge.module.css';

// The small pill beside a page label that marks a feature whose API may
// still change. It takes the colour of the text around it, so the page only
// places it next to its label; pass `className` to set a different colour.
export default function PreviewBadge({ className }) {
  return (
    <span
      className={className ? `${styles.badge} ${className}` : styles.badge}
      title={translate({
        id: 'component.previewBadge.hint',
        message: 'This feature may change in future releases.',
      })}>
      {translate({ id: 'component.previewBadge.label', message: 'Preview' })}
    </span>
  );
}
