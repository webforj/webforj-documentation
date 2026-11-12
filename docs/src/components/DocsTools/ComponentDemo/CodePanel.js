/** @jsxImportSource @emotion/react */

import { css } from "@emotion/react";

import CodeBlock from "@theme/CodeBlock";

// The code of the selected file, under the preview. A long file scrolls
// inside the pane.
const paneStyles = css`
  max-height: 36rem;
  overflow: auto;
  border-top: 1px solid var(--demo-line);
  background-color: var(--prism-background-color, var(--dwc-surface-3));

  .codeDemoBlock {
    margin: 0;
    border: 0;
    border-radius: 0;
    box-shadow: none;
  }

  .codeDemoBlock pre {
    border-radius: 0;
  }
`;

/**
 * The code of one file, shown under the preview while the code is open.
 *
 * @param {object}  props
 * @param {string}  props.id          Id of the pane, referenced by its tab.
 * @param {string}  props.labelledBy  Id of the tab that labels the pane.
 * @param {boolean} props.open        Whether the pane is shown.
 * @param {object}  props.file        The file record
 *                                    (`{ url, label, language, highlight }`).
 * @param {string}  props.code        The fetched source, empty until loaded.
 */
export default function CodePanel({ id, labelledBy, open, file, code }) {
  return (
    <div id={id} role="tabpanel" aria-labelledby={labelledBy} css={paneStyles} hidden={!open}>
      <CodeBlock
        className="codeDemoBlock"
        language={file.language}
        showLineNumbers
        metastring={file.highlight}
      >
        {code}
      </CodeBlock>
    </div>
  );
}
