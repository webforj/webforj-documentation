import React from 'react';
import { FaGithub, FaLinkedin, FaRss, FaYoutube } from 'react-icons/fa6';
import clsx from 'clsx';
import styles from './ConnectIcons.module.css';

const ICONS = { linkedin: FaLinkedin, youtube: FaYoutube, github: FaGithub, rss: FaRss };

// A row of follow links as icons, each named for screen readers and tooltips
export default function ConnectIcons({ links, className, compact = false }) {
  return (
    <ul className={clsx(styles.icons, compact && styles.compact, className)}>
      {links.map(({ id, action, href }) => {
        const Icon = ICONS[id];
        const external = href.startsWith('http');
        return (
          <li key={id}>
            <a
              className={clsx(styles.icon, styles[id])}
              href={href}
              title={action}
              aria-label={action}
              {...(external ? { target: '_blank', rel: 'noopener noreferrer' } : {})}>
              <Icon aria-hidden="true" />
            </a>
          </li>
        );
      })}
    </ul>
  );
}
