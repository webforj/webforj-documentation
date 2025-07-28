import React from 'react';
import Heading from '@theme-original/Heading';
import AskMenu from '@site/src/components/DocsTools/AskMenu';
import styles from './styles.module.css';

export default function HeadingWrapper(props) {
  return (
    <>
      {props.as === "h1" ? (
        <div className={styles.headingWrapper}>
          <Heading {...props} className={styles.heading} />
          <AskMenu />
        </div>
      ) : (
        <Heading {...props} />
      )}
    </>
  );
}
