import React from 'react';
import { useLocation } from '@docusaurus/router';
import useBaseUrl from '@docusaurus/useBaseUrl';
import Translate, { translate } from '@docusaurus/Translate';
import { FaArrowRight } from 'react-icons/fa6';
import ConnectIcons from './ConnectIcons';
import { CONTACT_URL, followLinksFor } from './links';
import styles from './ConnectCard.module.css';

// A quiet brand-tinted card under the table of contents: the webforJ team,
// an invitation to talk, and the follow links
export default function ConnectCard() {
  const { pathname } = useLocation();
  const icon = useBaseUrl('/img/webforj_icon.svg');
  const isBlog = /^\/blog(\/|$)/.test(pathname);
  return (
    <div className={styles.card}>
      <div className={styles.team}>
        <span className={styles.avatar}>
          <img src={icon} alt="" width="28" height="28" />
          <span className={styles.online} aria-hidden="true" />
        </span>
        <span className={styles.teamName}>
          <Translate id="component.connectCard.teamName">webforJ team</Translate>
        </span>
      </div>
      <p className={styles.title}>{isBlog
          ? translate({ id: 'component.connectCard.title.blog', message: 'Building with webforJ?' })
          : translate({ id: 'component.connectCard.title.docs', message: 'Need a hand?' })}
      </p>
      <p className={styles.text}>
        {isBlog
          ? translate({
              id: 'component.connectCard.text.blog',
              message: 'Talk to the engineers behind it about your project.',
            })
          : translate({
              id: 'component.connectCard.text.docs',
              message: 'Talk to the engineers behind webforJ about your project.',
            })}
      </p>
      <a className={styles.contact} href={CONTACT_URL} target="_blank" rel="noopener noreferrer">
        <Translate id="component.connectCard.contact">Contact us</Translate>{' '}
        <FaArrowRight aria-hidden="true" />
      </a>
      <ConnectIcons links={followLinksFor(pathname)} className={styles.icons} compact />
    </div>
  );
}
