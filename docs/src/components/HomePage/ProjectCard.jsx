import React from 'react';
import Translate from '@docusaurus/Translate';
import OpenInNewIcon from '@mui/icons-material/OpenInNew';
import styles from './ProjectCard.module.css';

/** A card displaying a community project for the "Built with webforJ" home page section. */
export default function ProjectCard({ name, winner, description, tags, demoUrl, sourceUrl }) {
  return (
    <div className={styles.card}>
      <div className={styles.header}>
        <span className={styles.name}>
          {winner && <span className={styles.trophy}>🏆</span>}
          {name}
        </span>
      </div>

      <p className={styles.description}>{description}</p>

      {tags && tags.length > 0 && (
        <div className={styles.tags}>
          {tags.map((tag) => (
            <span key={tag} className={styles.tag}>
              {tag}
            </span>
          ))}
        </div>
      )}

      <div className={styles.links}>
        {demoUrl && (
          <a
            href={demoUrl}
            target="_blank"
            rel="noopener noreferrer"
            className={styles.linkDemo}
          >
            <Translate id="homepage.project.demo">Live Demo</Translate>
            <OpenInNewIcon style={{ fontSize: '0.85em', marginLeft: '0.25em' }} />
          </a>
        )}
        <a
          href={sourceUrl}
          target="_blank"
          rel="noopener noreferrer"
          className={styles.linkSource}
        >
          <Translate id="homepage.project.source">Source</Translate>
          <OpenInNewIcon style={{ fontSize: '0.85em', marginLeft: '0.25em' }} />
        </a>
      </div>
    </div>
  );
}
