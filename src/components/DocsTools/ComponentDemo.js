/**@jsx jsx */

import React from 'react'
import { jsx, css } from '@emotion/react';
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import Details from '@theme/Details';
import CodeBlock from '@theme/CodeBlock';

export default function ComponentDemo({ path, showCSS, javaCode, cssCode }) {

	const mainStyles = css`
		display: flex;
    flex-direction: column;
    padding: 10px 15px 0 15px;
    background-color: #F8FAFC;
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
    background-color: #F8FAFC;
    margin-bottom: 0px;
    padding: 10px;

    summary{
      display: flex;
      width: 100%;
      justify-content: center;
      margin: 0;
      ::before{
        left: auto;
        margin-left: -100px;
      }
    }

  `

  var code = fetch('../../static/demos/button-demos/test.java')

  return (
    <div css={mainStyles}>
      <iframe
        loading="lazy" 
        src={path}
        css={iframeStyles}>
      </iframe>
      <Details css={detailsStyles} summary={<summary>Show Code</summary>}>
      {cssCode ? 
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
                showLineNumbers>
                {javaCode}
              </CodeBlock>
          </TabItem>
        </Tabs>
      }
      </Details>
    </div>
  );
}
