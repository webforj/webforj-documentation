import React, { useEffect, useRef } from 'react';
import { useColorMode } from '@docusaurus/theme-common';

export default function GiscusComments() {
  const ref = useRef(null);
  const { colorMode } = useColorMode();

  // Helper to set the Giscus theme
  const setGiscusTheme = (theme) => {
    const iframe = ref.current?.querySelector('iframe.giscus-frame');
    if (iframe) {
      iframe.contentWindow.postMessage(
        {
          giscus: {
            setConfig: {
              theme: theme,
            },
          },
        },
        'https://giscus.app'
      );
    }
  };

  useEffect(() => {
    // Only add script once
    if (ref.current && !ref.current.hasChildNodes()) {
      const script = document.createElement('script');
      script.src = 'https://giscus.app/client.js';
      script.setAttribute('data-repo', 'webforj/blog-comments');
      script.setAttribute('data-repo-id', 'R_kgDOPITvyA');
      script.setAttribute('data-category', 'Announcements');
      script.setAttribute('data-category-id', 'DIC_kwDOPITvyM4Csl11');
      script.setAttribute('data-mapping', 'title');
      script.setAttribute('data-strict', '0');
      script.setAttribute('data-reactions-enabled', '1');
      script.setAttribute('data-emit-metadata', '0');
      script.setAttribute('data-input-position', 'bottom');
      script.setAttribute('data-theme', colorMode === 'dark' ? 'https://cdn.webforj.com/webforj-documentation/comment-theme-dark.css' : 'https://cdn.webforj.com/webforj-documentation/comment-theme.css');
      script.setAttribute('data-lang', 'en');
      script.crossOrigin = 'anonymous';
      script.async = true;
      ref.current.appendChild(script);
    }
  }, []);

  // Update theme on color mode change
  useEffect(() => {
    setGiscusTheme(colorMode === 'dark' ? 'https://cdn.webforj.com/webforj-documentation/comment-theme-dark.css' : 'https://cdn.webforj.com/webforj-documentation/comment-theme.css');
  }, [colorMode]);

  return <div ref={ref} />;
}
