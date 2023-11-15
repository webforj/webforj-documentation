/** @jsxImportSource @emotion/react */
import React from 'react';
import { jsx, css } from '@emotion/react';
import { Tooltip, Chip } from '@mui/material';

import BiotechIcon from '@mui/icons-material/Biotech';
import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import CodeIcon from '@mui/icons-material/Code';
import DescriptionIcon from '@mui/icons-material/Description';
import StyleIcon from '@mui/icons-material/Style';

export default function DocChip( { label, href, clickable, iconName, tooltipText, color  } ) {

  const mainStyles = css`
    margin-right: 10px;
    padding-left: 5px;
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
  if(iconName === 'scoped'){
    icon = <BiotechIcon css={iconStyles} />
  } else if(iconName === 'shadow'){
    icon = <FiberSmartRecordIcon css={iconStyles} />
  } else if(iconName === "code"){
    icon = <CodeIcon css={iconStyles} />;
  } else{
    icon = <StyleIcon css={iconStyles} />;
  }

  return (
    <Tooltip title={tooltipText} arrow css={mainStyles}>
      <Chip label={label} component="a" href={clickable ? href : null} icon={icon} clickable={clickable} color={color} target="_blank" />
    </Tooltip>
  )
}
