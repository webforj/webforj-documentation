import React from 'react';
import Link from '@docusaurus/Link';
import styles from './GettingStarted.module.css';

/**
 * Compact preview strip that sits between the hero and the task grid.
 *
 * Three tight numbered pills sketch the "generate → run → open" flow
 * (Vaadin-style landing hint), with a CTA to the full /quickstart page.
 *
 * Not a full quickstart — just enough shape for a visitor to see what
 * scaffolding a webforJ app looks like without leaving the landing.
 */
export default function PreviewStrip({
  steps = defaultSteps,
  cta = 'Get up and running →',
  to = '/docs/introduction/quickstart',
}) {
  return (
    <section className={styles.previewStrip}>
      <div className={styles.previewSteps}>
        {steps.map((step, i) => (
          <React.Fragment key={step.label}>
            <span className={styles.previewStep}>
              <span className={styles.previewStepNum}>{step.num}</span>
              <span>{step.label}</span>
            </span>
            {i < steps.length - 1 && <span className={styles.previewSeparator}>→</span>}
          </React.Fragment>
        ))}
      </div>
      <Link to={to} className={styles.previewCta}>
        {cta}
      </Link>
    </section>
  );
}

const defaultSteps = [
  { num: '1', label: 'Generate a project' },
  { num: '2', label: 'Run it locally' },
  { num: '3', label: 'Open in browser' },
];
