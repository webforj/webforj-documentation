import React from 'react';
import useBaseUrl from '@docusaurus/useBaseUrl';
import Translate, { translate } from '@docusaurus/Translate';
import { FaArrowRight } from 'react-icons/fa6';
import ConnectIcons from './ConnectIcons';
import { CONTACT_URL, FOLLOW, TEAM_LANGUAGES } from './links';
import styles from './ConnectCard.module.css';

// A compact card above the table of contents, the same on docs and blog
// pages: the webforJ team with its follow links on one line, an invitation
// to talk, the Book a call button and the languages the team speaks. It sits
// on top, so its height is set before the contents and the column never
// shifts.
export default function ConnectCard() {
  const icon = useBaseUrl('/img/webforj_icon.svg');
  return (
    <div className={styles.card}>
      <div className={styles.team}>
        <span className={styles.avatar}>
          <img src={icon} alt="" width="22" height="22" />
          <span className={styles.online} aria-hidden="true" />
        </span>
        <span className={styles.teamName}>
          <Translate id="component.connectCard.teamName">webforJ team</Translate>
        </span>
        <ConnectIcons links={FOLLOW} className={styles.icons} compact />
      </div>
      <p className={styles.title}>
        {translate({ id: 'component.connectCard.title.docs', message: 'Need a hand?' })}
      </p>
      <p className={styles.text}>
        {translate({
          id: 'component.connectCard.text.docs',
          message: 'Talk to the webforJ team about your project.',
        })}
      </p>
      <a className={styles.contact} href={CONTACT_URL} target="_blank" rel="noopener noreferrer">
        <Translate id="component.connectCard.bookCall">Book a call</Translate>{' '}
        <FaArrowRight aria-hidden="true" />
      </a>
      {/* short codes keep it on one line; the full names are in the tooltip
          and for screen readers */}
      <p className={styles.languages} title={TEAM_LANGUAGES.map((l) => l.name).join(', ')}>
        <Translate id="component.connectCard.languages" values={{ languages: TEAM_LANGUAGES.map((l) => l.code).join(' · ') }}>
          {'We speak {languages}'}
        </Translate>
      </p>
    </div>
  );
}
