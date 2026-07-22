import React from 'react';
import clsx from 'clsx';
import styles from './GettingStarted.module.css';

/**
 * Hero for the Getting Started page.
 *
 * Two shapes:
 * - Compact: text-only (pass no `graphic`) — stacked, max-width limited
 * - With graphic: text column on left + graphic column on right, full width
 *
 * `ctas` is a slot for one or more <Link>/<a> button elements.
 */
export default function Hero({ eyebrow, title, subtitle, graphic, ctas }) {
  return (
    <header className={clsx(styles.hero, graphic && styles.heroWithGraphic)}>
      <div className={styles.heroContent}>
        {eyebrow && <p className={styles.heroEyebrow}>{eyebrow}</p>}
        <h1 className={styles.heroTitle}>{title}</h1>
        {subtitle && <p className={styles.heroSubtitle}>{subtitle}</p>}
        {ctas && <div className={styles.heroCtas}>{ctas}</div>}
      </div>
      {graphic && <div className={styles.heroGraphic}>{graphic}</div>}
    </header>
  );
}
