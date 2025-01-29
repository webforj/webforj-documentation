/** @jsxImportSource @emotion/react */

import { React, useState} from 'react'
import { jsx, css } from '@emotion/react';
import { useColorMode } from '@docusaurus/theme-common';
import ComponentDemo, {OpenNewWindowButton} from './ComponentDemo';

export default function AppLayoutViewer({path, mobile, javaE, cssURL}) {
    
  const [buttonVisible, setButtonVisible] = useState(false);

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
    const fadeInButton = css`
      display: flex;
      justify-content: flex-end;
      opacity: 0;
      transition: opacity 0.3s ease-in-out;
      ${buttonVisible && "opacity: 1;"};
      margin: 10px 0 0 0;
      position: absolute;
      top: 10px;
      right: 5px;
    `

  return (
    <div css={demoStyles}>
        <div css={demoPreview}
          onMouseEnter={() => setButtonVisible(true)}
          onMouseLeave={() => setButtonVisible(false)}>
        <div css={fadeInButton}>
                {OpenNewWindowButton({ url: path })}
        </div>
            <iframe src={path+"&__theme__="+ (useColorMode().colorMode)} css={demoContent} loading='lazy'>
            </iframe>
        </div>
        <br/>
        <ComponentDemo frame="hidden" javaE={javaE} cssURL={cssURL}/>
    </div>
  );
}