/** @jsxImportSource @emotion/react */
import React from 'react';
import { jsx, css } from '@emotion/react';
import { Tooltip, Chip } from '@mui/material';
import Translate, { translate } from '@docusaurus/Translate';
import BiotechIcon from '@mui/icons-material/Biotech';
import AddTaskIcon from '@mui/icons-material/AddTask';
import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import CodeIcon from '@mui/icons-material/Code';
import ExperimentIcon from '@mui/icons-material/ScienceOutlined';
import DescriptionIcon from '@mui/icons-material/Description';
import HourglassTopIcon from '@mui/icons-material/HourglassTop';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';

export default function DocChip({ chip, label, href, exclude, tooltipText, color }) {

  const { siteConfig } = useDocusaurusContext();
  const webforjVersion = Number(siteConfig.customFields.webforjVersion.split("-")[0]);

  const mainStyles = css`
    margin-right: 0.5em;
    margin-bottom: 1em;
    background-color: var(--chip-background);
    border: 1px solid var(--dwc-color-primary-85);
    color: var(--chip-text);
    position: relative;
    z-index: 0;
    top: 0.3rem;
    :hover,:focus {
      color: inherit;
      background-color: var(--chip-background-hover);
      border: 1px solid var(--dwc-color-primary-55);
    }
  `;

  const iconStyles = css`
      color: var(--chip-icon-color) !important;
  `

  let icon;
  switch (chip) {
    // A "Shadow DOM" chip
    case 'shadow':
      tooltipText = translate({
        id: 'chip.tooltipText.shadow',
        message: 'This component renders with a shadow DOM, an API built into the browser that facilitates encapsulation.',
        description: 'Hover text for components that render with a shadow DOM (document object model)'
      });
      href = "/docs/glossary#shadow-dom";
      label = translate({
        id: 'chip.label.shadow',
        message: 'Shadow',
        description: 'Label to indicate a shadow DOM (document object model)'
      });
      icon = <FiberSmartRecordIcon css={iconStyles} />;
      break;
    // A "DOM Name" chip
    case 'name':
      tooltipText = translate({
        id: 'chip.tooltipText.name',
        message: 'The name of this web component as it appears in the DOM.',
        description: 'Hover text to explain how the web component will appear in the DOM (document object model)'
      });
      if (!(exclude)) {
        const path = "/docs/client-components/";
        const clientPage = label.replace("dwc-", "");
        href = path.concat(clientPage);
      }
      icon = <CodeIcon css={iconStyles} />;
      break;
    // A "Version" chip
    case 'since':
      let versionText = (
        <React.Fragment>
          <Translate
            id='chip.tooltipText.name'
            description='Hover text to explain what version of webforJ you need to use the feature.'
            values={{
              version: label
            }}
          >
            {'This feature is available for webforJ {version} and higher.'}
          </Translate>
        </React.Fragment>
      );
      let snapshotText = translate({
        id: 'chip.tooltipText.webforJ.version.snapshot',
        message: 'This feature will be available in a future release.',
        description: 'Hover text to explain this feature is only available in a snapshot version of webforJ.'
      });
      if (webforjVersion >= Number(label) || isNaN(webforjVersion)) {
        tooltipText = versionText;
        href = `https://github.com/webforj/webforj/releases/tag/${label}`;
        icon = <AddTaskIcon css={iconStyles} />
      } else {
        tooltipText = snapshotText;
        href = `/docs/configuration/snapshots`;
        icon = <HourglassTopIcon css={iconStyles} />
      }
      break;
    // A "Scoped" chip
    case 'scoped':
      tooltipText = translate({
        id: 'chip.tooltipText.scoped',
        message: 'This component uses scoped components, an alternative approach to the shadow DOM, a browser API that enables encapsulation. These components scope their styles to avoid leaks or conflicts instead of relying on the native shadow DOM.',
        description: 'Hover text to explain this component is scoped, an alternative approach to the shadow DOM (document object model).'
      });
      exclude = 'true';
      label = translate({
        id: 'chip.label.scoped',
        message: 'Scoped',
        description: 'Label to show this component uses scoped components.'
      });
      icon = <BiotechIcon css={iconStyles} />
      break;
    case 'experimental':
      tooltipText = translate({
        id: 'chip.tooltipText.experimental',
        message: 'This is an experimental feature and may change in future releases.',
        description: 'Hover text to explain this feature is experimental and subject to change.'
      });
      exclude = 'true';
      label = translate({
        id: 'chip.label.experimental',
        message: 'Experimental',
        description: 'Label to show this component is experimental.'
      });
      icon = <ExperimentIcon css={iconStyles} />
      break;
    default:
      console.warn("Uknown chip type:", chip);
      icon = <DescriptionIcon css={iconStyles} />;
      exclude = 'true';
  }

  return (
    <Tooltip title={tooltipText} arrow css={mainStyles} >
      <Chip className={`doc-chip ${chip.className || ''}`} label={label} component="a" href={!exclude ? href : null} icon={icon} clickable={!exclude} color={color} target="_blank" />
    </Tooltip>
  )
}
