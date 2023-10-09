/**@jsx jsx */

import {React, useState, useEffect} from 'react'
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

function CodeToggleButton({ collapse, setCollapse }){

  const buttonWrapperStyles = css`
    display: flex;
    justify-content: end;
    margin-bottom: -30px;
    background-color: transparent;
    `
  
  const buttonStyles = css`
    position: relative;
    cursor: pointer;
    z-index: 10;
    height: 35px;
    width: 35px;
    border: none;
    background-color: none;
    justify-self: flex-end;
    margin-right: 5px;
    background-color: transparent;  
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
      /* margin-right: 5px; */
      margin-top: -5px;
      margin-bottom: -20px;
      background-color: transparent;

      
    `;
  
    const iconStyles = css`
      filter: invert(var(--inversion-percentage));
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
  const [collapsed, setCollapsed] = useState(javaC && javaE ? true : false);
  const [buttonVisible, setButtonVisible] = useState(false);

  const [fileNames, setFileNames] = useState({})

  
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
  }, []);


	const mainStyles = css`
		display: flex;
    flex-direction: column;
    background-color: var(--code-display-color);
		width: 100%;
    margin-bottom: 16px;
    padding: ${frame == "hidden" ? "0 15px 0 15px;" : "7px 15px 0 15px;"};
    box-shadow: var(--ifm-global-shadow-lw);
    `

  const iframeStyles = css`
    min-height: 100px;
    height: 100%;
    width: 100%;
    height: ${height};
  `

  const detailsStyles = css`
    border: none;
    box-shadow: none;
    background-color: var(--code-display-color);
    margin-bottom: 0px;
    padding: 10px;

    .tabs{
      margin-top: 20px;
    }

    summary{
      display: flex;
      width: 100%;
      justify-content: center;
      margin: 0;
      font-weight: bold;
      ::before{
        left: auto;
        margin-left: -100px;
      }
    }

  `

  const testStyles = css`
  width: 100%;
  `

  const fadeInButton = css`
  display: flex;
  justify-content: flex-end;
    opacity: 0;
    transition: opacity 0.3s ease-in-out;
    ${buttonVisible && 'opacity: 1;'};
    
  `;





  return (
    <div css={mainStyles}>
      {frame != "hidden" ? 
      <div 
        onMouseEnter={() => {setButtonVisible(true)}}
        onMouseLeave={() => setButtonVisible(false)}
        css={testStyles}
      >
        <div css={fadeInButton}>
        <OpenNewWindowButton url={path} />
        </div>
        <iframe
          loading="lazy" 
          src={path+"&theme="+ (useColorMode().colorMode === "dark" ? "dark" : "light")}
          css={iframeStyles}>
        </iframe>
      </div>
      : null
      }
      <Details css={detailsStyles} summary={<summary>Show Code</summary>}>
      {javaC && javaE
      ?
      <CodeToggleButton
      collapse={collapsed}
      setCollapse={setCollapsed}
      />
      : null}
      {cssURL ? 
        <Tabs>
          <TabItem value={tabs ? tabs[0] : "Java"} label={tabs ? tabs[0] : fileNames.javaFile} default>
            <CodeBlock
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
                className="codeDemoBlock"
              language="css"
              showLineNumbers>
              {cssCode}
            </CodeBlock>
          </TabItem>

        </Tabs>
          :
        <Tabs>
          <TabItem value={tabs ? tabs[0] : "Java"} label={tabs ? tabs[0] : fileNames.javaFile} default>
            <CodeBlock
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