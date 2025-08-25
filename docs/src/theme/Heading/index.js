import React, { useState, useEffect } from 'react';
import Heading from '@theme-original/Heading';
import AskMenu from '@site/src/components/DocsTools/AskMenu';
import { useLocation } from '@docusaurus/router';
import { translate } from '@docusaurus/Translate';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import InfoIcon from '@mui/icons-material/Info';
import CloseIcon from '@mui/icons-material/Close';
import styles from './styles.module.css';

export default function HeadingWrapper(props) {
  const { i18n } = useDocusaurusContext();
  const location = useLocation();
  const [showTranslationNotice, setShowTranslationNotice] = useState(false);
  const [isDismissed, setIsDismissed] = useState(false);

  useEffect(() => {
    const dismissed = sessionStorage.getItem('translation-notice-dismissed');
    if (dismissed) {
      setIsDismissed(true);
    }

    if (i18n.currentLocale !== 'en' &&  location.pathname.includes('/docs/')) {
      setShowTranslationNotice(true);
    } else {
      setShowTranslationNotice(false);
    }
  }, [i18n.currentLocale, location.pathname]);

  const handleDismiss = () => {
    setIsDismissed(true);
    sessionStorage.setItem('translation-notice-dismissed', 'true');
  };

  const translationNotice = showTranslationNotice && !isDismissed && props.as === "h1" && (
    <div className={styles.translationNotice}>
      <div className={styles.noticeContent}>
        <InfoIcon className={styles.noticeIcon} />
        <div className={styles.noticeText}>
          {translate({
            id: 'theme.docs.translationNotice',
            message: 'This page has been automatically translated using AI and may contain errors or inaccuracies. For the most accurate and up-to-date information, please refer to the official webforJ documentation. webforJ makes no warranties about the accuracy, reliability, or completeness of these AI translations.',
            description: 'Translation notice for non-English pages'
          })}
          {' '}
          <a href="https://docs.webforj.com" target="_blank" rel="noopener noreferrer">
            docs.webforj.com
          </a>
        </div>
        <button
          className={styles.dismissButton}
          onClick={handleDismiss}
          aria-label="Dismiss translation notice"
        >
          <CloseIcon fontSize="small" />
        </button>
      </div>
    </div>
  );

  const showAskMenu = location.pathname.includes('/docs/');

  return (
    <>
      {props.as === "h1" ? (
        <>
          <div className={styles.headingWrapper}>
            <Heading {...props} className={styles.heading} />
            {showAskMenu && <AskMenu />}
          </div>
          {translationNotice}
        </>
      ) : (
        <Heading {...props} />
      )}
    </>
  );
}
