import React, { useEffect, useRef } from 'react';
import styles from './styles.module.css';

// Wraps code blocks and gives every hex colour written in them a small
// swatch in front of it, the way an editor previews colours. The
// highlighter already marks hex colours (.token.hexcode), so each one only
// needs its own value handed to the stylesheet. The code block rebuilds its
// lines after the page loads and when its content changes, so the values
// are handed over again whenever the lines inside change. The wrapper adds
// no box of its own (display: contents), so it can go around any code block.
export default function CodeSwatches({ children }) {
  const ref = useRef(null);
  useEffect(() => {
    const scope = ref.current;
    if (!scope) return undefined;
    const paint = () => {
      scope.querySelectorAll('.token.hexcode').forEach((token) => {
        const colour = token.textContent.trim();
        if (token.style.getPropertyValue('--swatch') !== colour) {
          token.style.setProperty('--swatch', colour);
        }
      });
    };
    paint();
    const observer = new MutationObserver(paint);
    observer.observe(scope, { childList: true, subtree: true });
    return () => observer.disconnect();
  }, []);
  return (
    <div ref={ref} className={styles.scope}>
      {children}
    </div>
  );
}
