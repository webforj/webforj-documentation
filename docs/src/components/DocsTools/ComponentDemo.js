/** @jsxImportSource @emotion/react */

import { React, useState, useEffect, useRef } from "react";
import { jsx, css } from "@emotion/react";
import PropTypes from "prop-types";

import Tabs from "@theme/Tabs";
import TabItem from "@theme/TabItem";
import CodeBlock from "@theme/CodeBlock";
import test3 from "../../../static/img/window-maximize.png";
import { useColorMode } from "@docusaurus/theme-common";
import GLOBALS from "../../../siteConfig";

import DragIndicatorIcon from "@mui/icons-material/DragIndicator";
import ChevronRightIcon from '@mui/icons-material/ChevronRight';


export function OpenNewWindowButton({ url }) {
  const { colorMode } = useColorMode()
  const buttonStyles = css`
    position: relative;
    cursor: pointer;
    z-index: 10;
    height: 35px;
    width: 35px;
    border: none;
    justify-self: flex-end;
    margin-top: -5px;
    margin-bottom: -20px;
    background-color: transparent;
    margin-right: 12px;
  `;

  const iconStyles = css`
  filter: ${colorMode === "dark" ? "invert(1)" : "none"};
    background-color: #ffffff80;
    border-radius: var(--dwc-border-radius-s);
  `;

  const openNewWindow = () => {
    window.open(url, "_blank"); // '_blank' will open the URL in a new tab
  };

  return (
    <button css={buttonStyles} onClick={openNewWindow}>
      <img css={iconStyles} src={test3} />
    </button>
  );
}

export const isLocalhost = typeof window !== "undefined" &&
window.location.hostname === "localhost" &&
window.location.port === "3000";

export default function ComponentDemo({
  path,
  javaE,
  urls,
  cssURL,
  javaHighlight,
  height,
  frame,
  tabs,
}) {
  ComponentDemo.propTypes = {
    path: PropTypes.string.isRequired,
    javaE: PropTypes.string,
    urls: PropTypes.arrayOf(PropTypes.string),
    cssURL: PropTypes.string,
    javaHighlight: PropTypes.string,
    height: PropTypes.string,
    frame: PropTypes.string,
    tabs: PropTypes.arrayOf(PropTypes.string),
  };

  const [javaExpand, setJavaExpand] = useState("");
  const [additionalFiles, setAdditionalFiles] = useState({});
  const [cssCode, setCssCode] = useState("");
  const [buttonVisible, setButtonVisible] = useState(false);
  const [fileNames, setFileNames] = useState({});

  const [isResizing, setIsResizing] = useState(false);
  const [initialMouseX, setInitialMouseX] = useState(0);
  const [initialWidth, setInitialWidth] = useState(0);

  const [initialRight, setInitialRight] = useState(25);
  const [newRight, setNewRight] = useState(25);
  const [originalWidth, setOriginalWidth] = useState(0);

  const [showCode, setShowCode] = useState(false);

  const iframeRef = useRef(null);
  const codeButtonRef = useRef(null);
  const { colorMode } = useColorMode()

  useEffect(() => {
    if (javaE) {
      fetch(javaE)
        .then((response) => response.text())
        .then((textString) => {
          setJavaExpand(textString);
          const parsedUrl = new URL(javaE);
          const pathname = parsedUrl.pathname;
          const parts = pathname.split("/");
          const fileName = parts[parts.length - 1];
          setFileNames((prevFileNames) => ({
            ...prevFileNames,
            javaFile: fileName,
          }));
        });
    }
    if (cssURL) {
      const rootPath = isLocalhost
      ? 'https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static'
      : GLOBALS.IFRAME_SRC_LIVE;
      fetch(rootPath + cssURL)
        .then((response) => response.text())
        .then((textString) => {
          setCssCode(textString);
          const parsedUrl = new URL(rootPath + cssURL);
          const pathname = parsedUrl.pathname;
          const parts = pathname.split("/");
          const fileName = parts[parts.length - 1];
          setFileNames((prevFileNames) => ({
            ...prevFileNames,
            cssFile: fileName,
          }));
        });
    }
    if (urls) {
      urls.forEach(fetchAndProcessURL);
    }

    function fetchAndProcessURL(url) {
      const parsedUrl = new URL(url);
      const pathname = parsedUrl.pathname;
      const parts = pathname.split("/");
      const fileName = parts[parts.length - 1];

      fetch(url)
        .then((response) => response.text())
        .then((textString) => {
          setAdditionalFile(fileName, textString);
        });
    }

    function setAdditionalFile(fileName, textString) {
      setAdditionalFiles((prevAdditionalFiles) => ({
        ...prevAdditionalFiles,
        [fileName]: {
          fileName: fileName,
          code: textString,
        },
      }));
    }
    setOriginalWidth(iframeRef.current ? iframeRef.current.offsetWidth : 0);
  }, []);

  useEffect(() => {
    if (!iframeRef.current) return;
  
    const applyThemeToIframe = () => {
      try {
        const iframeDoc = iframeRef.current.contentDocument || iframeRef.current.contentWindow.document;
        if (iframeDoc) {
          iframeDoc.documentElement.setAttribute("data-app-theme", colorMode);
        }
      } catch (error) {
        console.error("Failed to apply theme to iframe:", error);
      }
    };
  
    applyThemeToIframe();
    iframeRef.current.onload = applyThemeToIframe;

  }, [colorMode]);
  

  function renderCodeBlocks(files, codeBlockStyles, javaHighlight) {
    return(
        <CodeBlock
          css={codeBlockStyles}
          className="codeDemoBlock"
          language="java"
          showLineNumbers
          metastring={javaHighlight}
        >
          {files.code}
        </CodeBlock>
    );
  }

  const startResizing = (e) => {
    e.preventDefault();
    setIsResizing(true);
    setInitialMouseX(e.clientX);
    setInitialWidth(iframeRef.current ? iframeRef.current.offsetWidth : 0);
    setInitialRight(newRight);
  };

  const stopResizing = () => {
    setIsResizing(false);
  };

  const resize = (e) => {
    if (isResizing) {
      const dx = e.clientX - initialMouseX;
      if (initialWidth + dx > originalWidth / 3) {
        iframeRef.current.style.width = `${initialWidth + dx}px`;
        codeButtonRef.current.style.right = `${
          initialRight - dx < 25 ? 25 : initialRight - dx
        }px`;
        setNewRight(initialRight - dx < 25 ? 25 : initialRight - dx);
      }
    }
  };

  const mainStyles = css`
    display: flex;
    flex-direction: column;
    width: 100%;
    margin-bottom: 16px;
    @media screen and (max-width: 768px) {
      width: 100vw;
      margin-left: -1em;
    }
  `;

  const demoFrameStyles = css`
    width: 100%;
    border: 1px solid var(--ifm-toc-border-color);
    border-right: none;
    background-color: transparent;
    display: flex;
    position: relative;
  `;

  const iframeStyles = css`
    min-height: 100px;
    height: 100%;
    width: 100%;
    height: ${height || "100%"};
    pointer-events: ${isResizing ? "none" : "auto"};
  `;

  const fadeInButton = css`
    display: flex;
    justify-content: flex-end;
    opacity: 0;
    transition: opacity 0.3s ease-in-out;
    ${buttonVisible && "opacity: 1;"};
    margin: 10px 0 0 0;
    position: absolute;
    right: 25px;
    `;

  const resizeBarStyles = css`
    display: flex;
    align-items: center;
    cursor: ew-resize;
    border-left: 1px solid var(--ifm-toc-border-color);
    border-right: 1px solid var(--ifm-toc-border-color);
    background-color: var(--dwc-surface-3);

    @media screen and (max-width: 768px) {
      display: none;
    }
  `;

  const detailsStyles = css`
    overflow: hidden;
    background-color: var(--dwc-surface-3);
    border: 1px solid var(--ifm-toc-border-color);
    ${frame !== "hidden" && "border-top: none;"}
    margin: ${frame == "hidden"
      ? "40px 0px 0px 0px"
      : "0px"};
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
    transform: rotate(${showCode ? "90": "0)"}deg);
    margin-top: 2px;
  `;

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

  return (
    <div
      css={mainStyles}
      onMouseUp={stopResizing}
      onMouseLeave={stopResizing}
      onMouseMove={resize}
    >
      {frame != "hidden" ? (
        <div
          onMouseEnter={() => {
            setButtonVisible(true);
          }}
          onMouseLeave={() => setButtonVisible(false)}
          css={demoFrameStyles}
        >
          <iframe
            onMouseUp={stopResizing}
            loading="lazy"
            src={(isLocalhost ? GLOBALS.IFRAME_SRC_DEV : GLOBALS.IFRAME_SRC_LIVE) + path}
            css={iframeStyles}
            ref={iframeRef}
            onMouseMove={resize}
          ></iframe>
          <div css={fadeInButton} ref={codeButtonRef}>
            <OpenNewWindowButton url={(isLocalhost ? GLOBALS.IFRAME_SRC_DEV : GLOBALS.IFRAME_SRC_LIVE) + path} />
          </div>
          <div css={resizeBarStyles} onMouseDown={startResizing}>
            <DragIndicatorIcon />
          </div>
        </div>
      ) : null}
      <details
        css={detailsStyles}>
          <summary onClick={() => setShowCode(!showCode)}>
            {showCode ? "Hide Code" : "Show Code"}
            <ChevronRightIcon css={showCodeIconStyles}/>
          </summary>
        {cssURL ? (
          <Tabs css={tabStyles}>
            <TabItem
              value={tabs ? tabs[0] : "Java"}
              label={tabs ? tabs[0] : fileNames.javaFile}
              default
            >
              <CodeBlock
                css={codeBlockStyles}
                className="codeDemoBlock"
                language="java"
                showLineNumbers
                metastring={javaHighlight}
              >
                {javaExpand}
              </CodeBlock>
            </TabItem>
            {Object.keys(additionalFiles).map((fileName, index) => (
              <TabItem key={"tab" + index} value={fileName} label={fileName}>
                {renderCodeBlocks(additionalFiles[fileName], codeBlockStyles, javaHighlight)}
              </TabItem>
            ))}
            <TabItem
              value={tabs ? tabs[1] : "CSS"}
              label={tabs ? tabs[1] : fileNames.cssFile}
            >
              <CodeBlock
                css={codeBlockStyles}
                className="codeDemoBlock"
                language="css"
                showLineNumbers
              >
                {cssCode}
              </CodeBlock>
            </TabItem>
          </Tabs>
        ) : (
          <Tabs css={tabStyles}>
            <TabItem
              value={tabs ? tabs[0] : "Java"}
              label={tabs ? tabs[0] : fileNames.javaFile}
              default
            >
              <CodeBlock
                css={codeBlockStyles}
                className="codeDemoBlock"
                language="java"
                showLineNumbers
                metastring={javaHighlight}
              >
                {javaExpand}
              </CodeBlock>
            </TabItem>
            {Object.keys(additionalFiles).map((fileName, index) => (
              <TabItem key={"tab" + index} value={fileName} label={fileName}>
                {renderCodeBlocks(additionalFiles[fileName], codeBlockStyles, javaHighlight)}
              </TabItem>
            ))}
          </Tabs>
        )}
      </details>
    </div>
  );
}
