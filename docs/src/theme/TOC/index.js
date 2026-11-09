import React, { useLayoutEffect, useRef } from 'react';
import clsx from 'clsx';
import TOC from '@theme-original/TOC';
import ConnectCard from '@site/src/components/connect/ConnectCard';
import styles from './styles.module.css';

// space kept free under the column for the floating "Questions?" launcher
const LAUNCHER_SPACE = 88;

// The connect card and the table of contents share one sticky column. The
// contents never scroll on their own. When the column is taller than the
// screen, it moves with the page until its end is in view and stays there,
// so nothing is cut off. Only the sticky offset changes, never the content.
export default function TOCWrapper({ className, ...props }) {
  const stackRef = useRef(null);

  useLayoutEffect(() => {
    const stack = stackRef.current;
    if (!stack) return undefined;
    const place = () => {
      stack.style.top = '';
      const top = parseFloat(getComputedStyle(stack).top) || 0;
      const overflow = stack.offsetHeight - (window.innerHeight - top - LAUNCHER_SPACE);
      if (overflow > 0) stack.style.top = `${top - overflow}px`;
    };
    place();
    document.fonts?.ready.then(place);
    window.addEventListener('resize', place);
    return () => {
      window.removeEventListener('resize', place);
      stack.style.top = '';
    };
  }, []);

  return (
    <div ref={stackRef} className={clsx('webforj-toc-stack', styles.stack)}>
      <ConnectCard />
      <TOC {...props} className={clsx(className, styles.toc)} />
    </div>
  );
}
