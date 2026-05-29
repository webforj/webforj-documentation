import React from 'react';
import Link from '@docusaurus/Link';
import { useLocation } from '@docusaurus/router';
import clsx from 'clsx';
import styles from './BlogFilterBar.module.css';

const CATEGORIES = [
  { label: 'All', path: '/blog' },
  { label: 'Release', path: '/blog/tags/release' },
  { label: 'Tutorial', path: '/blog/tags/tutorial' },
  { label: 'Components', path: '/blog/tags/components' },
  { label: 'Integrations', path: '/blog/tags/integrations' },
  { label: 'Styling', path: '/blog/tags/styling' },
  { label: 'Community', path: '/blog/tags/community' },
];

export default function BlogFilterBar() {
  const { pathname } = useLocation();

  const isActive = (path) => {
    if (path === '/blog') {
      return !pathname.startsWith('/blog/tags/');
    }
    return pathname.startsWith(path);
  };

  return (
    <div className={styles.filterBar}>
      {CATEGORIES.map(({ label, path }) => (
        <Link
          key={label}
          to={path}
          className={clsx(styles.filterButton, {
            [styles.active]: isActive(path),
          })}
        >
          {label}
        </Link>
      ))}
    </div>
  );
}
