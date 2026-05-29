/** @jsxImportSource @emotion/react */

import { useState } from "react";
import { css } from "@emotion/react";
import { translate } from '@docusaurus/Translate';

import Tabs from "@theme/Tabs";
import TabItem from "@theme/TabItem";
import CodeBlock from "@theme/CodeBlock";

import ChevronRightIcon from '@mui/icons-material/ChevronRight';

const tabStyles = css`
  li[aria-selected="true"] {
    border-color: var(--ifm-color-primary);
  }

  .tabs__item {
    padding: 5px 20px -2px 20px;
    border-radius: 0px;
  }
`;

const codeBlockStyles = css`
  border-radius: 0px;
  box-shadow: rgba(0, 0, 0, 0.06) 0px 2px 4px 0px inset;
`;

/**
 * Collapsible code panel that renders a tab per file, with each tab body
 * showing the fetched source. Owns the open/closed state via the native
 * `<details>` element's `onToggle` event so keyboard activation stays in sync.
 *
 * @param {object}  props
 * @param {Array}   props.files         Normalized file records from the parent
 *                                       (`{ url, label, language, highlight }`).
 * @param {object}  props.loadedFiles   Map of resolved url → fetched code string.
 *                                       Pre-seeded with empty strings so tabs
 *                                       render in input order before fetches resolve.
 * @param {boolean} [props.detached]    If true, the panel renders standalone
 *                                       (top border + 40px top margin). If false
 *                                       (default), it sits visually attached
 *                                       beneath a frame above (no top border).
 */
export default function CodePanel({ files, loadedFiles, detached = false }) {
  const [showCode, setShowCode] = useState(false);

  const detailsStyles = css`
    overflow: hidden;
    background-color: var(--dwc-surface-3);
    border: 1px solid var(--ifm-toc-border-color);
    ${detached ? "" : "border-top: none;"}
    margin: ${detached ? "40px 0px 0px 0px" : "0px"};
    padding: 0px;
    position: relative;

    div {
      border-color: var(--ifm-toc-border-color);
      padding: 2px 0px 0px 0px;
      margin: 0px;
    }

    summary {
      cursor: pointer;
      border-bottom: ${showCode ? "1px solid var(--ifm-toc-border-color)" : "none"};
      display: flex;
      justify-content: center;
      padding: 10px 0;
      font-weight: bold;
    }

    &::details-content {
      block-size: auto;
      block-size: ${showCode ? "calc-size(auto, size)" : "0"};
      transition:
        block-size var(--dwc-transition-slow),
        content-visibility var(--dwc-transition-slow);
      transition-behavior: allow-discrete;
    }

    .margin-top--md {
      margin-top: 0px !important;
    }
    ul {
      margin: -4px 0px !important;
    }
  `;

  const showCodeIconStyles = css`
    transition: transform var(--dwc-transition-medium);
    transform: rotate(${showCode ? "90" : "0"}deg);
    margin-top: 2px;
  `;

  return (
    <details css={detailsStyles} onToggle={(e) => setShowCode(e.currentTarget.open)}>
      <summary>
        {showCode
          ? translate({
              id: 'componentDemo.hideCode',
              message: 'Hide Code',
              description: 'Button text to hide the code section',
            })
          : translate({
              id: 'componentDemo.showCode',
              message: 'Show Code',
              description: 'Button text to show the code section',
            })
        }
        <ChevronRightIcon css={showCodeIconStyles} />
      </summary>
      <Tabs css={tabStyles}>
        {files.map((f, index) => (
          <TabItem
            key={f.url}
            value={f.url}
            label={f.label}
            default={index === 0 ? true : undefined}
          >
            <CodeBlock
              css={codeBlockStyles}
              className="codeDemoBlock"
              language={f.language}
              showLineNumbers
              metastring={f.highlight}
            >
              {loadedFiles[f.url] ?? ''}
            </CodeBlock>
          </TabItem>
        ))}
      </Tabs>
    </details>
  );
}
