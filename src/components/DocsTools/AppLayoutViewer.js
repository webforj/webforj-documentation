/** @jsxImportSource @emotion/react */

import React from 'react'
import { jsx, css } from '@emotion/react';
import { useColorMode } from '@docusaurus/theme-common';


export default function AppLayoutViewer({url, mobile}) {
    
    const demoStyles = css`
        display: flex;
        flex-direction: column;
        align-items: center;
        margin: 4rem 0;
        /* width: 110%; */
        /* margin-left: -5%; */
    `
    
    const demoPreview = css`
        position: relative;
        width : 100%;
        max-width: ${mobile == 'true' ? '375px' : '100%'};
        height: ${mobile == 'true' ? '700px' : '600px'};
        border-radius: 32px;
        overflow: hidden;
        box-shadow: 0 0 0 10px black, 0 3px 22px black;
        outline: thin solid black;
    `

    const demoContent = css`
        width: 100%;
        height: 100%;
        background: var(--dwc-surface-3);
    `

  return (
    <div css={demoStyles}>
        <div css={demoPreview}>
            <iframe src={url+"&__theme__="+ (useColorMode().colorMode)} css={demoContent} loading='lazy'>

            </iframe>
        </div>
    </div>
  );
}
