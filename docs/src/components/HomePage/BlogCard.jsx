import React from 'react';
import Link from '@docusaurus/Link';
import styles from './BlogCard.module.css';

/** A card displaying a blog post preview for the home page. */
export default function BlogCard({ title, slug, description, date, tags, image, author }) {
  const primaryTag = tags?.[0];

  return (
    <article className={styles.card}>
      {image && (
        <div className={styles.imageWrapper}>
          <img src={image} alt={title} className={styles.image} loading="lazy" />
        </div>
      )}
      <div className={styles.content}>
        {primaryTag && <span className={styles.tag}>{primaryTag}</span>}
        <h3 className={styles.title}>
          <Link to={`/blog/${slug}`}>{title}</Link>
        </h3>
        <p className={styles.description}>{description}</p>
        <div className={styles.meta}>
          <span>{author}</span>
          <span className={styles.dot}>·</span>
          <span>{date}</span>
        </div>
      </div>
    </article>
  );
}
