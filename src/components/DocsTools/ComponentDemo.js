/**@jsx jsx */

import {React, useState} from 'react'
import { jsx, css } from '@emotion/react';
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import Details from '@theme/Details';
import CodeBlock from '@theme/CodeBlock';

function CodeToggleButton({ collapse, setCollapse }){

  const buttonWrapperStyles = css`
    /* transition: all var(--ifm-transition-fast) ease; */
    display: flex;
    justify-content: end;
    margin-bottom: -8px;
    `
  
  const buttonStyles = css`
  opacity: 0;

    position: relative;
    cursor: pointer;
    z-index: 10;
    height: 35px;
    width: 35px;
    border-radius: 5px;
    border: 1px gray;
    background-color: none;
    justify-self: flex-end;
    margin-right: 83px;
    margin-bottom: -50px;

    .icon-tabler-arrow-bar-down{
      opacity: ${collapse ? 1 : 0}

    }

    .icon-tabler-arrow-bar-up{
      opacity: ${collapse ? 0 : 1}
    }
    
  `
  
  return(
    <div css={buttonWrapperStyles}>
     <button
      css={buttonStyles}
      onClick={()=>{
        setCollapse(!collapse);
        console.log(collapse);
      }}
     >
      {collapse
      ?
      <svg xmlns="http://www.w3.org/2000/svg" className="icon icon-tabler icon-tabler-arrow-bar-down" width="24" height="24" viewBox="0 0 24 24" strokeWidth="2" stroke="lightgray" fill="none" strokeLinecap="round" strokeLinejoin="round">
        <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
        <path d="M12 20l0 -10"></path>
        <path d="M12 20l4 -4"></path>
        <path d="M12 20l-4 -4"></path>
        <path d="M4 4l16 0"></path>
      </svg>
      :
      <svg xmlns="http://www.w3.org/2000/svg" className="icon icon-tabler icon-tabler-arrow-bar-up" width="24" height="24" viewBox="0 0 24 24" strokeWidth="2" stroke="lightgray" fill="none" strokeLinecap="round" strokeLinejoin="round">
        <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
        <path d="M12 4l0 10"></path>
        <path d="M12 4l4 4"></path>
        <path d="M12 4l-4 4"></path>
        <path d="M4 20l16 0"></path>
      </svg>
      }
     </button>
    </div>
  );
}



export default function ComponentDemo({ path, javaC, javaE, cssURL, javaHighlight }) {

  const [javaCollapse, setJavaCollapse] = useState("");
  const [javaExpand, setJavaExpand] = useState("");
  const [cssCode, setCssCode] = useState("");
  const [collapsed, setCollapsed] = useState(true);

	const mainStyles = css`
		display: flex;
    flex-direction: column;
    padding: 10px 15px 0 15px;
    background-color: var(--code-display-color);
		width: 100%;
    margin-bottom: 16px;

	`

  const iframeStyles = css`
    min-height: 100px;
    height: 100%;
  `

  const detailsStyles = css`
    border: none;
    box-shadow: none;
    background-color: var(--code-display-color);
    margin-bottom: 0px;
    padding: 10px;

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

  if(javaE){
    fetch(javaE)
        .then(response => response.text()) 
        .then(textString => {
            setJavaExpand(textString)
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
        });
  }

  return (
    <div css={mainStyles}>
      <iframe
        loading="lazy" 
        src={path+'?data-app-theme=\'light\''}
        data-app-theme='light'
        css={iframeStyles}>
      </iframe>
      <Details css={detailsStyles} summary={<summary>Show Code</summary>}>
      {cssURL ? 
        <Tabs>
          <TabItem value='Java' label='Java' default>
            <CodeBlock
              className="codeDemoBlock"
              language="java"
              showLineNumbers>
              {collapsed 
              ? javaExpand
              : javaCollapse
              }
            </CodeBlock>
          </TabItem>
          <TabItem value='CSS' label='CSS'>
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
          <TabItem value='Java' label='Java' default>

          {/* <CodeToggleButton
          collapse={collapsed}
          setCollapse={setCollapsed}
          hover={hover}
          /> */}
            
            <CodeBlock
                className="codeDemoBlock"
                language="java"
                showLineNumbers
                metastring={javaHighlight}
                >
                {collapsed 
                ? javaExpand
                : javaCollapse
                }
              </CodeBlock>
          </TabItem>
        </Tabs>
      }
      </Details>
    </div>
  );
}
