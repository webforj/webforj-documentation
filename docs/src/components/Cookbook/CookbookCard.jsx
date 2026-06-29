import React from 'react';
import Link from '@docusaurus/Link';
import clsx from 'clsx';
import styles from './CookbookIndex.module.css';

/** Human-readable labels for the three difficulty tiers. */
const DIFFICULTY_LABELS = {
  beginner: 'Beginner',
  intermediate: 'Intermediate',
  advanced: 'Advanced',
};

/**
 * A single recipe card rendered inside the {@link CookbookIndex} grid.
 *
 * @param {object}   props
 * @param {object}   props.recipe           - Recipe entry from cookbook-index.json.
 * @param {string}   props.recipe.url       - Relative URL to the recipe page (e.g. /cookbook/css/inject-inline-css).
 * @param {string}   props.recipe.title     - Short, descriptive recipe title.
 * @param {string}   props.recipe.description - One-sentence summary (≤ 160 chars).
 * @param {string[]} props.recipe.tags      - Topic tags from the allowed tag set.
 * @param {string}   props.recipe.difficulty - One of: beginner | intermediate | advanced.
 */
export default function CookbookCard({ recipe }) {
  const { url, title, description, tags, difficulty } = recipe;

  return (
    <Link to={url} className={styles.card}>
      <div className={styles.cardBody}>
        <h3 className={styles.cardTitle}>{title}</h3>
        {description && <p className={styles.cardDescription}>{description}</p>}
      </div>
      <div className={styles.cardFooter}>
        <div className={styles.tagList}>
          {tags.map((tag) => (
            <span key={tag} className={styles.tag}>
              {tag}
            </span>
          ))}
        </div>
        {difficulty && (
          <span className={clsx(styles.difficulty, styles[difficulty])}>
            {DIFFICULTY_LABELS[difficulty] ?? difficulty}
          </span>
        )}
      </div>
    </Link>
  );
}
