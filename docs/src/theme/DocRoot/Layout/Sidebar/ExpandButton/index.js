import React from 'react';
import { translate } from '@docusaurus/Translate';
import { TbLayoutSidebarLeftExpand } from 'react-icons/tb';
import styles from './styles.module.css';

// With the sidebar hidden, its thin column brings it back: the whole column
// is the button, quiet until hovered, with the icon near the top
export default function DocRootLayoutSidebarExpandButton({ toggleSidebar }) {
  const label = translate({
    id: 'theme.docs.sidebar.expandButtonAriaLabel',
    message: 'Expand sidebar',
    description: 'The ARIA label and title attribute for expand button of doc sidebar',
  });
  return (
    <button type="button" className={styles.column} title={label} aria-label={label} onClick={toggleSidebar}>
      <span className={styles.icon}>
        <TbLayoutSidebarLeftExpand aria-hidden="true" />
      </span>
    </button>
  );
}
