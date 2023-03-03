/**@jsx jsx */

import {React, useState} from 'react'
import { jsx, css } from '@emotion/react';
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import Details from '@theme/Details';
import CodeBlock from '@theme/CodeBlock';

export default function ComponentDemo({ path, javaURL, cssURL, javaHighlight }) {

  const [javaCode, setJavaCode] = useState("");
  const [cssCode, setCssCode] = useState("");

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

  if(javaURL){
    fetch(javaURL)
        .then(response => response.text()) 
        .then(textString => {
            setJavaCode(textString)
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
              language="java"
              showLineNumbers>
              {javaCode}
            </CodeBlock>
          </TabItem>
          <TabItem value='CSS' label='CSS'>
            <CodeBlock
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
                language="java"
                showLineNumbers
                metastring={javaHighlight}
                >
                {javaCode}
              </CodeBlock>
          </TabItem>
        </Tabs>
      }
      </Details>
    </div>
  );
}
