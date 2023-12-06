/** @jsxImportSource @emotion/react */

import {React, useState, useEffect, useRef} from 'react'
import { jsx, css } from '@emotion/react';
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import Details from '@theme/Details';
import CodeBlock from '@theme/CodeBlock';
import test1 from '../../../static/img/fold.png';
import test2 from '../../../static/img/expand.png';
import test3 from '../../../static/img/window-maximize.png';
// import useThemeContext from '@theme/hooks/useThemeContext';
import { useColorMode } from '@docusaurus/theme-common';

import DragIndicatorIcon from '@mui/icons-material/DragIndicator';

function CodeToggleButton({ collapse, setCollapse }){

  const buttonWrapperStyles = css`
    display: flex;
    justify-content: end;
    align-items: flex-end;
    background-color: transparent;
    margin-bottom: -10px;
    `
  
  const buttonStyles = css`
    /* position: absolute;
    right: 0; */
    cursor: pointer;
    z-index: 10;
    height: 35px;
    width: 35px;
    border: none;
    background-color: none;
    justify-self: flex-end;
    background-color: transparent;  
    margin-right: 5px;
    margin-bottom: -50px !important;
  `

  const iconStyles = css`
      filter: invert(var(--inversion-percentage));
  `
  
  return(
  <div css={buttonWrapperStyles}>
     <button
      css={buttonStyles}
      onClick={()=>{
        setCollapse(!collapse);
      }}
     >
      {collapse
      ?
      <img css={iconStyles} src={test2} className="icon-tabler-arrow-bar-up"/>
      :
      <img css={iconStyles} src={test1} className="icon-tabler-arrow-bar-down" />
      }
     </button>
  </div> 
  );
}

function OpenNewWindowButton({ url }) {

    const buttonStyles = css`
      position: relative;
      cursor: pointer;
      z-index: 10;
      height: 35px;
      width: 35px;
      border: none;
      background-color: none;
      justify-self: flex-end;
      margin-top: -5px;
      margin-bottom: -20px;
      background-color: transparent;

      
    `;
  
    const iconStyles = css`
      /* filter: invert(var(--inversion-percentage)); */ //UNCOMMENT WHEN DMEOS CHANGE COLOR
      ::before{
        mix-blend-mode: lighten; /* The blend mode determines how the overlay interacts with the image */
        opacity: 0.5;
      }
    `;

    const openNewWindow = () => {
      window.open(url, '_blank'); // '_blank' will open the URL in a new tab
    };

    return (
      <button css={buttonStyles} onClick={openNewWindow}>
        <img css={iconStyles} src={test3}/>
      </button>
    );
}


export default function ComponentDemo({ path, javaC, javaE, cssURL, javaHighlight, height, frame, tabs }) {

  const [javaCollapse, setJavaCollapse] = useState("");
  const [javaExpand, setJavaExpand] = useState("");
  const [cssCode, setCssCode] = useState("");
  const [collapsed, setCollapsed] = useState(!!(javaC && javaE));
  const [buttonVisible, setButtonVisible] = useState(false);
  const [fileNames, setFileNames] = useState({})

  const [isResizing, setIsResizing] = useState(false);
  const [initialMouseX, setInitialMouseX] = useState(0);
  const [initialWidth, setInitialWidth] = useState(0);
  
  const [initialRight, setInitialRight] = useState(25);
  const [newRight, setNewRight] = useState(25);
  const [originalWidth, setOriginalWidth] = useState (0)

  const [showCode, setShowCode] = useState(false)
  
  const iframeRef = useRef(null);
  const codeButtonRef = useRef(null);

  
  useEffect(() => {
    if(javaE){
      fetch(javaE)
          .then(response => response.text()) 
          .then(textString => {
              setJavaExpand(textString)
              const parsedUrl = new URL(javaE);
              const pathname = parsedUrl.pathname;
              const parts = pathname.split('/');
              const fileName = parts[parts.length - 1];
              setFileNames((prevFileNames) => ({ ...prevFileNames, javaFile: fileName }))
          });
    }
    if(javaC){
      fetch(javaC)
          .then(response => response.text()) 
          .then(textString => {
              setJavaCollapse(textString)
          });
    }
    if(cssURL){
      fetch(cssURL)
          .then(response => response.text()) 
          .then(textString => {
              setCssCode(textString)
              const parsedUrl = new URL(cssURL);
              const pathname = parsedUrl.pathname;
              const parts = pathname.split('/');
              const fileName = parts[parts.length - 1];
              setFileNames((prevFileNames) => ({ ...prevFileNames, cssFile: fileName }))
          });
    }
    setOriginalWidth(iframeRef.current ? iframeRef.current.offsetWidth : 0)
  }, []);

  const startResizing = (e) => {
    e.preventDefault();
    setIsResizing(true);
    setInitialMouseX(e.clientX);
    setInitialWidth(iframeRef.current ? iframeRef.current.offsetWidth : 0);
    setInitialRight(newRight)
  };

  const stopResizing = () => {
    setIsResizing(false);
  };

  const resize = (e) => {
    if (isResizing) {
      const dx = e.clientX - initialMouseX;
      if(initialWidth + dx > (originalWidth / 3)){
        iframeRef.current.style.width = `${initialWidth + dx}px`;
        codeButtonRef.current.style.right = `${initialRight - dx < 25 ? 25 : initialRight - dx}px`
        setNewRight(initialRight - dx < 25 ? 25 : initialRight - dx)
      }
      // setInitialMouseX(e.clientX); // Add this line
  }
  };

	const mainStyles = css`
		display: flex;
    flex-direction: column;
		width: 100%;
    margin-bottom: 16px;
    /* padding: ${frame == "hidden" ? "0 15px 0 15px;" : "7px 15px 0 15px;"}; */
    /* box-shadow: var(--ifm-global-shadow-lw); */
    background-color: var(--code-display-color-background);
    `

const demoFrameStyles = css`
    width: 100%;
    /* padding: 1em; */
    border: 1px solid var(--ifm-toc-border-color);
    border-right: none;
    background-color: transparent;
    display: flex;
    position: relative;
  `

  const iframeStyles = css`
    min-height: 100px;
    height: 100%;
    width: 100%;
    height: ${height || '100%'};
    pointer-events: ${isResizing ? 'none' : 'auto'};
  `

  const fadeInButton = css`
    display: flex;
    justify-content: flex-end;
    opacity: 0;
    transition: opacity 0.3s ease-in-out;
    ${buttonVisible && 'opacity: 1;'};
    margin: 10px 0 0 0;
    position: absolute;
    right: 25px;
  `;

  const resizeBarStyles = css`
    display: flex;
    align-items: center;
    cursor: ew-resize;
    border-left: 1px solid var(--ifm-toc-border-color);
    border-right: 1px solid var(--ifm-toc-border-color);
    background-color: var(--ifm-background-color);
  `

  const detailsStyles = css`
    box-shadow: none;
    background-color: var(--ifm-background-color);
    margin: 0px;
    padding: 0px;
    border: ${frame == 'hidden' ? 'none' : '1px solid var(--ifm-toc-border-color)'};
    border-top: none;
    border-radius: 0px;
    position: relative;

    div{
      /* border: none; */
      border-color: var(--ifm-toc-border-color);
      padding: 2px 0px 0px 0px;
      margin: 0px;
    }

    > div:first-of-type{
      border: ${frame == 'hidden' ? '1px solid var(--ifm-toc-border-color)' : 'none'};
      border-top: none;
    }

    summary{
      display: flex;
      width: 100%;
      justify-content: center;
      margin: 10px 0;
      font-weight: bold;
      ::before{
        left: auto;
        margin-left: -100px;
        --docusaurus-details-decoration-color: var(--ifm-color-primary)
      }
    }
    .margin-top--md{
      margin-top: 0px !important;
    }
    ul{
      margin: -4px 0px!important;
    }
    `

  const tabStyles = css`
    /* :first-child{
      margin-left: 1em;
    } */
    li[aria-selected="true"]{
      border-color: var(--ifm-color-primary);
    }
    
    .tabs__item{
      padding: 5px 20px -2px 20px;
      border-radius: 0px;
    }
  `

  const codeBlockStyles = css`
    border-radius: 0px;
    box-shadow: rgba(0, 0, 0, 0.06) 0px 2px 4px 0px inset;
  `




  return (
    <div css={mainStyles} onMouseUp={stopResizing} onMouseLeave={stopResizing} onMouseMove={resize}>
      {frame != "hidden" ? 
      <div 
        onMouseEnter={() => {setButtonVisible(true)}}
        onMouseLeave={() => setButtonVisible(false)}
        css={demoFrameStyles}
      >
        <iframe
          onMouseUp={stopResizing}
          loading="lazy" 
          // src={path+"&theme="+ (useColorMode().colorMode === "dark" ? "dark" : "light")}
          src={path}
          css={iframeStyles}
          ref={iframeRef}
          onMouseMove={resize}
          >   
        </iframe>
        <div css={fadeInButton} ref={codeButtonRef}>
        <OpenNewWindowButton url={path} />
        </div>
        <div css={resizeBarStyles} onMouseDown={startResizing}>
          <DragIndicatorIcon />
        </div>
      </div>
      : null
      }
      <Details css={detailsStyles} summary={<summary onClick={() => setShowCode(!showCode)}>{showCode ? "Hide Code" : "Show Code"}</summary>}>
      {javaC && javaE
      ?
      <CodeToggleButton
      collapse={collapsed}
      setCollapse={setCollapsed}
      />
      : null}
      {cssURL ? 
        <Tabs css={tabStyles}>
          <TabItem value={tabs ? tabs[0] : "Java"} label={tabs ? tabs[0] : fileNames.javaFile} default>
            <CodeBlock
              css={codeBlockStyles}
              className="codeDemoBlock"
              language="java"
              showLineNumbers
              metastring={javaHighlight}
              >
              {collapsed 
              ? javaCollapse
              : javaExpand
              }
            </CodeBlock>
          </TabItem>
          <TabItem value={tabs ? tabs[1] : "CSS"} label={tabs ? tabs[1] : fileNames.cssFile}>
            <CodeBlock
              css={codeBlockStyles}
                className="codeDemoBlock"
              language="css"
              showLineNumbers>
              {cssCode}
            </CodeBlock>
          </TabItem>

        </Tabs>
          :
        <Tabs css={tabStyles}>
          <TabItem value={tabs ? tabs[0] : "Java"} label={tabs ? tabs[0] : fileNames.javaFile} default>
            <CodeBlock
              css={codeBlockStyles}
                className="codeDemoBlock"
                language="java"
                showLineNumbers
                metastring={javaHighlight}
                >
                {collapsed 
                ? javaCollapse
                : javaExpand
                }
              </CodeBlock>
          </TabItem>
        </Tabs>
      }
      </Details>
    </div>
  );
}