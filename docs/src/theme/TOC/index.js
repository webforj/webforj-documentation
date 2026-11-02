import React from 'react';
import clsx from 'clsx';
import TOC from '@theme-original/TOC';
import ConnectCard from '@site/src/components/connect/ConnectCard';
import ExploreCard from '@site/src/components/explore/ExploreCard';
import styles from './styles.module.css';

// The table of contents, the connect card and the Explore card share one
// sticky column: the cards always stay in view under the contents, which
// scroll on their own when they are long
export default function TOCWrapper({ className, ...props }) {
  return (
    <div className={clsx('webforj-toc-stack', styles.stack)}>
      <TOC {...props} className={clsx(className, styles.toc)} />
      <ConnectCard />
      <ExploreCard />
    </div>
  );
}
