import React from 'react';
import styles from './MediaPlaceholder.module.css';

const VIDEO_CDN = 'https://cdn.webforj.com/webforj-documentation/video/';

/**
 * Marks a spot in a draft page where a screenshot or a video still has to be
 * produced. Renders a loud block that states what the asset shows, where the
 * file belongs, and the exact markup that replaces the placeholder once the
 * asset exists.
 *
 * Every placeholder is meant to be deleted. A page that still renders one is
 * not finished.
 *
 * Usage:
 *   <MediaPlaceholder type="image" file="inspector/tree-selection.png">
 *     The component tree with a component selected
 *   </MediaPlaceholder>
 *
 *   <MediaPlaceholder type="video" file="craftforj/pick-mode.mp4" length="20s">
 *     Pick mode from the shortcut through to a selection
 *   </MediaPlaceholder>
 *
 * Pages nested below the section root pass the prefix their relative image
 * paths need:
 *   <MediaPlaceholder type="image" file="ai/history.png" prefix="../">…</MediaPlaceholder>
 */
export default function MediaPlaceholder({ type, file, length, prefix = './', children }) {
  const isVideo = type === 'video';
  const description = typeof children === 'string' ? children.trim() : children;

  const location = isVideo
    ? `${VIDEO_CDN}${file}`
    : `docs/craftforj/images/${file}`;

  const alt = typeof description === 'string' ? description.replace(/\.$/, '') : '';
  const snippet = isVideo
    ? `<div class="videos-container">\n  <video controls>\n    <source src="${VIDEO_CDN}${file}" type="video/mp4" />\n  </video>\n</div>`
    : `![${alt}](${prefix}images/${file})`;

  return (
    <aside className={styles.placeholder} aria-label="Media placeholder">
      <div className={styles.header}>
        <span>Media placeholder</span>
        <span className={styles.kind}>{isVideo ? 'Video' : 'Screenshot'}</span>
        <span>{file}</span>
      </div>
      <div className={styles.body}>
        <p className={styles.row}>
          <span className={styles.label}>Shows</span>
          {description}
        </p>
        {length ? (
          <p className={styles.row}>
            <span className={styles.label}>Length</span>
            {length}
          </p>
        ) : null}
        <p className={styles.row}>
          <span className={styles.label}>Goes to</span>
          <code>{location}</code>
        </p>
        <p className={styles.row}>
          <span className={styles.label}>Replace with</span>
          <code className={styles.snippet}>{snippet}</code>
        </p>
      </div>
    </aside>
  );
}
