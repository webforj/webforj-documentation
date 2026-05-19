import React from 'react';
import Link from '@docusaurus/Link';
import styles from './NavCard.module.css';

/** A clickable navigation card for the "Find Your Path" home page section. */
export default function NavCard({ icon, title, description, to }) {
  return (
    <Link to={to} className={styles.card}>
      <span className={styles.icon}>{icon}</span>
      <span className={styles.title}>{title}</span>
      <span className={styles.description}>{description}</span>
    </Link>
  );
}
