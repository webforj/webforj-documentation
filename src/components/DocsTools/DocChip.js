/** @jsxImportSource @emotion/react */
import React from 'react';
import { jsx, css } from '@emotion/react';
import { Tooltip, Chip } from '@mui/material';

import BiotechIcon from '@mui/icons-material/Biotech';
import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import CodeIcon from '@mui/icons-material/Code';
import DescriptionIcon from '@mui/icons-material/Description';
import StyleIcon from '@mui/icons-material/Style';

export default function DocChip( { chip, label, href, clickable, iconName, tooltipText, color  } ) {

  const mainStyles = css`
    margin-right: 0.5em;
    margin-bottom: 1em;
    background-color: var(--chip-background);
    color: var(--chip-text);

    :hover{
      color: inherit;
      background-color: var(--chip-background-hover);
    }
  `;

  const iconStyles = css`
      color: var(--chip-icon-color) !important;
  `

  let icon;
  if(chip === 'shadow'){
    // A "Shadow DOM" Chip
    tooltipText = "This component renders with a shadow DOM, an API built into the browser that facilitates encapsulation.";
    clickable= true;
    href = "https://documentation.webforj.com/docs/glossary#shadow-dom";
    label='Shadow';
    iconName = 'shadow';
  } else if (chip === 'name') {
    // A "DOM Name" chip
    tooltipText="The name of this web component as it appears in the DOM.";
    clickable = false;
    iconName = 'code';
  } else if (chip == 'scoped') {
    tooltipText = "This component uses scoped components, an alternative approach to the shadow DOM, a browser API that enables encapsulation. These components scope their styles to avoid leaks or conflicts instead of relying on the native shadow DOM.";
    clickable= false;
    label='Scoped';
    iconName = 'scoped';  
  }

  if (iconName === 'shadow'){
    icon = <FiberSmartRecordIcon css={iconStyles} />;
  } else if (iconName === 'code'){
    icon = <CodeIcon css={iconStyles} />;
  } else if(iconName === 'scoped'){
    icon = <BiotechIcon css={iconStyles} />
  } else{
    icon = <StyleIcon css={iconStyles} />;
  }

  return (
    <Tooltip title={tooltipText} arrow css={mainStyles} >
      <Chip className={`doc-chip ${chip.className || ''}`} label={label} component="a" href={clickable ? href : null} icon={icon} clickable={clickable} color={color} target="_blank" />
    </Tooltip>
  )
}
