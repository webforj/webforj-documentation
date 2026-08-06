import React from 'react';
import Link from '@docusaurus/Link';
import Illo from './Illo';
import styles from './GettingStarted.module.css';

/**
 * Full-width horizontal spotlight for the "App Basics" landing target.
 *
 * Layout: illustration on the left, kicker + big title + copy + CTA on
 * the right. Sits between the task grid and the community band and gives
 * the fundamentals page a dedicated invitation before the page ends.
 */
export default function AppBasicsBand({
  eyebrow = 'Learn the fundamentals',
  title = 'How a webforJ app is put together',
  description = 'Walk through the Application class, the HomeView, routing annotations, and the layout system — the pieces every webforJ app is built from.',
  cta = 'Read the app basics →',
  to = '/docs/introduction/basics',
  variant = 'wizard',
}) {
  return (
    <section className={styles.appBasics}>
      <div className={styles.appBasicsIllo}>
        <Illo variant={variant} />
      </div>
      <div className={styles.appBasicsContent}>
        {eyebrow && <p className={styles.appBasicsEyebrow}>{eyebrow}</p>}
        <h2 className={styles.appBasicsTitle}>{title}</h2>
        <p className={styles.appBasicsDesc}>{description}</p>
        <div className={styles.appBasicsCtas}>
          <Link to={to} className={styles.appBasicsCta}>
            {cta}
          </Link>
        </div>
      </div>
    </section>
  );
}
