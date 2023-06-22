/**@jsx jsx */
import React from 'react';
import { jsx, css } from '@emotion/react';

export default function UnderConstruction() {

  const mainStyles = css`
    height: 100%;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
  `

const mainTitle = css`
  text-align: center;
  margin-top: 4rem;
  margin-bottom: 8rem;
  text-shadow: 
  1px 0px 1px #ccc, 0px 1px 1px #eee, 
  2px 1px 1px #ccc, 1px 2px 1px #eee,
  3px 2px 1px #ccc, 2px 3px 1px #eee,
  4px 3px 1px #ccc, 3px 4px 1px #eee,
  5px 4px 1px #ccc, 4px 5px 1px #eee,
  6px 5px 1px #ccc, 5px 6px 1px #eee,
  7px 6px 1px #ccc;

h1{
  font-size: 7rem;
  text-transform: uppercase;
  font-weight: 800;
  color: #555;
}

h2{
  font-size: 4rem;
  font-weight: 300;
  text-transform: uppercase;
}
  `

  return (
    <div css={mainStyles}>
      <div css={mainTitle}>
      <h1>Section</h1>
      <h2>under construction</h2>
      </div>
    </div>
  )
}
