/** @jsxImportSource @emotion/react */
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
    width: 130%;

    @media only screen and (max-width: 996px) {
      width: 100%;
    }
  `

  return (
    <div css={mainStyles}>
      {componentData.map((item) => {
      return(
        <ComponentCard key={item.title} imagePath={item.image} title={item.title} description={item.description} link={item.link}/>
      );
      })}
    </div>
  )
}
