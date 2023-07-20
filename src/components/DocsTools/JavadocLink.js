/**@jsx jsx */

import React, { useState, useEffect } from 'react'
import { jsx, css } from '@emotion/react';
import GithubCache from './GithubCache';

export default function JavadocLink( { type, location, top, children, code, suffix } ) {
  const [url, setUrl] = useState('');

  useEffect(() => {
    const fetchLatestRelease = async () => {
      try {
        const latestTag = await GithubCache.getLatestTag();
        if(!suffix){
          suffix = ""
        }
        setUrl("https://javadoc.io/static/org.dwcj/dwcj-" + type + "/" + latestTag + "/" + location + ".html" + suffix)
      } catch (error) {
        console.error('Error fetching latest release:', error);
      }
    };
    
    fetchLatestRelease();
    console.log(top);
  }, []);

  const mainStyles = css`
    display: inline;

    ${ top && css`
    width: 100%;
    display: flex;
    justify-content: flex-end;
    `}
  `;

  const apiStyles = css`
    color: gray;
  `;


  return (
    <>
    {
    top === 'true' && (
    <div css={mainStyles}>
      <p css={apiStyles}>API:&nbsp;</p>
      <b><a href={url} target="_blank">Java</a></b>
    </div>
    )
    }
  {
    top !== 'true' && !code && (
      <div css={mainStyles}>
        <a href={url} target="_blank">{children}</a>
      </div>
    )
  }
  {
    top !== 'true' && code && (
      <div css={mainStyles}>
        <code>
          <a href={url} target="_blank">{children}</a>
        </code>
      </div>
    )
  }
    </>
  );
}
