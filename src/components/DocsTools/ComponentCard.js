/**@jsx jsx */
import React from 'react';
import { jsx, css } from '@emotion/react';

export default function ComponentCard({ imagePath, title, description}) {

  const mainStyles = css`
    display: flex;
    flex-direction: column;
    width: 200px;
    margin-top: 15px;
  `

  const imageStyles = css`
    width: 200px;
    height: 150px;
    object-fit: contain;
    margin: 0 0 5px -3px;

    border: 1px solid var(--ifm-color-emphasis-200);
    transition: all var(--ifm-transition-fast) ease;
    transition-property: border,box-shadow;
    border-radius: var(--ifm-card-border-radius);
    background-image: url(${imagePath});

    

    :hover{
      border: 1px var(--ifm-color-primary) solid;
    } 
  `

  const descriptionStyles = css`
    p{
      font-size: .75em;
    }
  `


  return (
    <div css={mainStyles}>
      <div css={imageStyles}>
        {/* <img css={imageStyles}></img> */}
      </div>
      <div css={descriptionStyles}>
        <h5>{title}</h5>
        <p>{description}</p>
      </div>
    </div>
  )
}
