/** @jsxImportSource @emotion/react */

import { React, useState, useEffect, useRef } from 'react'
import { jsx, css } from '@emotion/react';
import { useColorMode } from '@docusaurus/theme-common';
import ComponentDemo, {OpenNewWindowButton, isLocalhost} from './ComponentDemo';
import GLOBALS from "../../../siteConfig";

export default function AppLayoutViewer({path, mobile, javaE, cssURL, urls}) {
    
  const [buttonVisible, setButtonVisible] = useState(false);
  const iframeRef = useRef(null);
  const { colorMode } = useColorMode()
  
  
  useEffect(() => {
      if (!iframeRef.current) return;
    
      const applyThemeToIframe = () => {
        try {
          const iframeDoc = iframeRef.current.contentDocument || iframeRef.current.contentWindow.document;
          if (iframeDoc) {
            iframeDoc.documentElement.setAttribute("data-app-theme", colorMode);
          }
        } catch (error) {
          console.error("Failed to apply theme to iframe:", error);
        }
      };
    
      applyThemeToIframe();
      iframeRef.current.onload = applyThemeToIframe;
  
    }, [colorMode]);

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
                {OpenNewWindowButton({ url: (isLocalhost ? GLOBALS.IFRAME_SRC_DEV : GLOBALS.IFRAME_SRC_LIVE) + path })}
        </div>
            <iframe src={(isLocalhost ? GLOBALS.IFRAME_SRC_DEV : GLOBALS.IFRAME_SRC_LIVE) + path} css={demoContent} loading='lazy' ref={iframeRef}>
            </iframe>
        </div>
        <ComponentDemo frame="hidden" javaE={javaE} cssURL={cssURL} urls={urls}/>
    </div>
  );
}