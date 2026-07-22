import React from 'react';
import styles from './GettingStarted.module.css';

/**
 * Quickstart band — tinted section wrapping a vertical stack of numbered steps.
 * Children should be <QuickstartStep> elements.
 */
export function Quickstart({ title = 'Quickstart', subtitle, children }) {
  return (
    <section className={styles.quickstart}>
      <div className={styles.quickstartHeader}>
        <h2 className={styles.quickstartTitle}>{title}</h2>
        {subtitle && <p className={styles.quickstartSubtitle}>{subtitle}</p>}
      </div>
      <div className={styles.steps}>{children}</div>
    </section>
  );
}

/**
 * A single numbered step. `number` is a string so callers control formatting
 * (e.g. "01" vs "1"). Children can be any MDX content (prose, tabs, code).
 */
export function QuickstartStep({ number, title, children }) {
  return (
    <div className={styles.step}>
      <div className={styles.stepNumeral}>{number}</div>
      <div className={styles.stepContent}>
        <h3 className={styles.stepTitle}>{title}</h3>
        <div className={styles.stepBody}>{children}</div>
      </div>
    </div>
  );
}
