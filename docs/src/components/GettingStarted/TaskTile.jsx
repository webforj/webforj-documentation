import React from 'react';
import Link from '@docusaurus/Link';
import Illo from './Illo';
import styles from './GettingStarted.module.css';

/**
 * A single task tile — illustration + title + short description.
 * Wraps everything in a Docusaurus Link so the whole card is clickable.
 */
export default function TaskTile({ variant, title, description, to }) {
  return (
    <Link to={to} className={styles.tile}>
      <div className={styles.illoWrap}>
        <Illo variant={variant} />
      </div>
      <h3 className={styles.tileTitle}>{title}</h3>
      <p className={styles.tileDesc}>{description}</p>
    </Link>
  );
}
