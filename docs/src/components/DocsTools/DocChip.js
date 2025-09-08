/** @jsxImportSource @emotion/react */
import React from 'react';
import { jsx, css } from '@emotion/react';
import { Tooltip, Chip } from '@mui/material';

import BiotechIcon from '@mui/icons-material/Biotech';
import AddTaskIcon from '@mui/icons-material/AddTask';
import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import CodeIcon from '@mui/icons-material/Code';
import ExperimentIcon from '@mui/icons-material/ScienceOutlined';
import DescriptionIcon from '@mui/icons-material/Description';

export default function DocChip( { chip, label, href, exclude, tooltipText, color } ) {

  const mainStyles = css`
    margin-right: 0.5em;
    margin-bottom: 1em;
    background-color: var(--chip-background);
    color: var(--chip-text);
    position: relative;
    z-index: 0;
    top: 0.3rem;
    :hover{
      color: inherit;
      background-color: var(--chip-background-hover);
    }
  `;

  const iconStyles = css`
      color: var(--chip-icon-color) !important;
  `

  let icon;
  switch(chip){
    // A "Shadow DOM" chip
    case 'shadow':
      tooltipText = "This component renders with a shadow DOM, an API built into the browser that facilitates encapsulation.";
      href = "/docs/glossary#shadow-dom";
      label = 'Shadow';
      icon = <FiberSmartRecordIcon css={iconStyles} />;
    break;
    // A "DOM Name" chip
    case 'name':
      tooltipText="The name of this web component as it appears in the DOM.";
        if (!(exclude)){
          const path = "/docs/client-components/";
          const clientPage = label.replace("dwc-", "");
          href = path.concat(clientPage);
        }
      icon = <CodeIcon css={iconStyles} />;
    break;
    // A "Version" chip    
    case 'since':
      tooltipText = `This feature is available for webforJ ${label} and higher.`;
      href = `https://github.com/webforj/webforj/releases/tag/${label}`;
      icon = <AddTaskIcon css={iconStyles} />
    break;
    // A "Scoped" chip
    case 'scoped':
      tooltipText = "This component uses scoped components, an alternative approach to the shadow DOM, a browser API that enables encapsulation. These components scope their styles to avoid leaks or conflicts instead of relying on the native shadow DOM.";
      exclude= 'true';
      label='Scoped';
      icon = <BiotechIcon css={iconStyles} />
    break;
    case 'experimental':
      tooltipText = "This is an experimental feature and may change in future releases.";
      exclude= 'true';
      label='Experimental';
      icon = <ExperimentIcon css={iconStyles}/>
    break;
    default:
      console.warn("Uknown chip type:", chip);
      icon = <DescriptionIcon css={iconStyles} />;
      exclude= 'true';
  }

  return (
    <Tooltip title={tooltipText} arrow css={mainStyles} >
      <Chip className={`doc-chip ${chip.className || ''}`} label={label} component="a" href={ !exclude ? href : null} icon={icon} clickable={!exclude} color={color} target="_blank" />
    </Tooltip>
  )
}
