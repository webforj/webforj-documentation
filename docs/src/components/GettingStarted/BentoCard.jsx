import React from 'react';
import Link from '@docusaurus/Link';
import Illo from './Illo';
import styles from './GettingStarted.module.css';

/**
 * Large card used in the two-card row (startforJ + Built with webforJ).
 * External links open in a new tab; internal links use Docusaurus Link.
 */
export default function BentoCard({ variant, title, description, cta, to, external = false }) {
  const linkProps = external
    ? { href: to, target: '_blank', rel: 'noopener noreferrer' }
    : { to };
  const Component = external ? 'a' : Link;

  return (
    <Component {...linkProps} className={styles.bento}>
      <div className={styles.bentoIllo}>
        <Illo variant={variant} />
      </div>
      <h3 className={styles.bentoTitle}>{title}</h3>
      <p className={styles.bentoDesc}>{description}</p>
      <span className={styles.bentoCta}>{cta}</span>
    </Component>
  );
}
