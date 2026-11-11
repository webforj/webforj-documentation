import { useEffect } from 'react';

// Adds `pausedClassName` to the element in `ref` while it is off screen, so
// a looping animation inside it can be stopped with
// `animation-play-state: paused` until it scrolls back into view.
export default function usePauseOffscreen(ref, pausedClassName) {
  useEffect(() => {
    const element = ref.current;
    if (!element || typeof IntersectionObserver === 'undefined') return undefined;
    const observer = new IntersectionObserver(([entry]) => {
      element.classList.toggle(pausedClassName, !entry.isIntersecting);
    });
    observer.observe(element);
    return () => observer.disconnect();
  }, [ref, pausedClassName]);
}
