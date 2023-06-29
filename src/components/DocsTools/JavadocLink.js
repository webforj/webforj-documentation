/**@jsx jsx */

import { React, useState, useEffect } from 'react'
import { jsx, css } from '@emotion/react';
import GithubCache from './GithubCache';

export default function JavadocLink( { type, location } ) {
  const [url, setUrl] = useState('');

  useEffect(() => {
    const fetchLatestRelease = async () => {
      try {
        const latestTag = await GithubCache.getLatestTag();
        setUrl("https://javadoc.io/static/org.dwcj/dwcj-" + type + "/" + latestTag + "/" + location + ".html")
      } catch (error) {
        console.error('Error fetching latest release:', error);
      }
    };
    
    fetchLatestRelease();
  }, []);

  const mainStyles = css`
    width: 100%;
    display: flex;
    justify-content: flex-end;
  `;

  const apiStyles = css`
    color: gray;
  `;


  return (
    <div css={mainStyles}>
      <p css={apiStyles}>API:&nbsp;</p>
      <b><a href={url} target="_blank">Java</a></b>
    </div>
  )
}
