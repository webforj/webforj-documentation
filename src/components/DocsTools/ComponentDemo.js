/**@jsx jsx */

import {React, useState} from 'react'
import { jsx, css } from '@emotion/react';
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import Details from '@theme/Details';
import CodeBlock from '@theme/CodeBlock';
import test1 from '../../../static/img/fold.png';
import test2 from '../../../static/img/expand.png';

function CodeToggleButton({ collapse, setCollapse }){

  const buttonWrapperStyles = css`
    /* transition: all var(--ifm-transition-fast) ease; */
    display: flex;
    justify-content: end;
    /* margin-bottom: -85px; */
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
    /* margin-right: 83px; */
    margin-right: 5px;
    margin-bottom: -50px;
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



export default function ComponentDemo({ path, javaC, javaE, cssURL, javaHighlight, height }) {

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
    height: ${height};
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
        src={path}
        css={iframeStyles}>
      </iframe>
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
          <TabItem value='Java' label='Java' default>
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