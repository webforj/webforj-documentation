/**@jsx jsx */
import React from 'react';
import { jsx, css } from '@emotion/react';
import ComponentCard from '../DocsTools/ComponentCard';
// import componentData from '@site/static/card_data.js'



export default function ComponentViewer( {componentData} ) {
  
  const mainStyles = css`
    display : flex;
    flex-direction : row,;
    flex-wrap : wrap;
    gap : 40px;
    margin-right: -300px;
  `

  return (
    <div css={mainStyles}>
      {componentData.map((item) => {
      return(
        <ComponentCard imagePath={item.image} title={item.title} description={item.description} link={item.link}/>
      );
      })}
    </div>
  )
}
