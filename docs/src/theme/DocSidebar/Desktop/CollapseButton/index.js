import React from 'react';
import { translate } from '@docusaurus/Translate';
import { TbLayoutSidebarLeftCollapse } from 'react-icons/tb';
import styles from './styles.module.css';

// A quiet icon button at the foot of the docs sidebar that hides it
export default function CollapseButton({ onClick }) {
  const label = translate({
    id: 'theme.docs.sidebar.collapseButtonAriaLabel',
    message: 'Collapse sidebar',
    description: 'The title attribute for collapse button of doc sidebar',
  });
  return (
    <div className={styles.bar}>
      <button type="button" className={styles.button} title={label} aria-label={label} onClick={onClick}>
        <TbLayoutSidebarLeftCollapse aria-hidden="true" />
      </button>
    </div>
  );
}
