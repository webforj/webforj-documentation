/** @jsxImportSource @emotion/react */
import React from 'react';
import { jsx, css } from '@emotion/react';
import Link from '@docusaurus/Link';

export default function ComponentCard({ imagePath, title, description, link}) {

  const mainStyles = css`
    display: flex;
    flex-direction: column;
    width: 300px;
    margin-top: 15px;
    padding: 20px;
    border: 1px solid var(--ifm-color-emphasis-200);
    transition: all var(--ifm-transition-fast) ease;
    transition-property: border,box-shadow;
    border-radius: var(--ifm-card-border-radius);
    text-decoration: none;
    color: var(--ifm-font-color-base);    
    
    &:hover{
      border: 1px var(--ifm-color-primary) solid;
      .bar{
        background-color: var(--ifm-color-primary);
      }
      text-decoration: none;
      color: var(--ifm-font-color-base);
    } 
  `

  const imageStyles = css`
    width: 250px;
    height: 150px;
    object-fit: contain;
    margin: 0 0 5px -3px;
  `

    const barStyles = css`
      height: 1px;
      background-color: lightgray;
      margin: 5px 0 15px 0;
      transition: all var(--ifm-transition-fast) ease;
      transition-property: background-color,box-shadow;
    `

  const descriptionStyles = css`
    h5{
      font-size: 1em;
    }
    p{
      font-size: .9em;
      margin: 0px !important;
    }
  `


  return (
    <Link to={link} css={mainStyles}>
      <div css={{display: "flex", justifyContent: "center"}}>
        <img src={imagePath.default} css={imageStyles}></img>
      </div>
      <div css={barStyles} className="bar"></div>
      <div css={descriptionStyles}>
        <h5>{title}</h5>
        <p>{description}</p>
      </div>
    </Link>
  )
}
