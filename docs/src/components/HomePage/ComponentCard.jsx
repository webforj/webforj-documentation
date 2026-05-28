import React from 'react';
import Link from '@docusaurus/Link';
import styles from './ComponentCard.module.css';

/** A small card linking to a component's documentation page. */
export default function ComponentCard({ name, description, path }) {
  return (
    <Link to={path} className={styles.card}>
      <span className={styles.name}>{name}</span>
      <span className={styles.description}>{description}</span>
    </Link>
  );
}
