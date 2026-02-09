/** @jsxImportSource @emotion/react */

import { useState, useEffect, useRef } from "react";
import { css } from "@emotion/react";
import PropTypes from "prop-types";
import { translate } from '@docusaurus/Translate';

import Tabs from "@theme/Tabs";
import TabItem from "@theme/TabItem";
import CodeBlock from "@theme/CodeBlock";
import windowMaximizeIcon from "../../../static/img/window-maximize.png";
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
      <img css={iconStyles} src={windowMaximizeIcon} />
    </button>
  );
}

export const isLocalhost = typeof window !== "undefined" &&
window.location.hostname === "localhost" &&
window.location.port === "3000";

function getLanguageFromExtension(extension) {
  const languageMap = {
    java: "java",
    kt: "kotlin",
    css: "css",
    js: "javascript",
    ts: "typescript",
    json: "json",
    xml: "xml",
    html: "html",
    md: "markdown",
  };
  return languageMap[extension] || "text";
}

/**
 * Derives Kotlin URL from Java URL based on naming convention:
 * - /java/ -> /kotlin/
 * - View.java -> KotlinView.kt
 */
function deriveKotlinUrl(javaUrl) {
  return javaUrl
    .replace('/java/', '/kotlin/')
    .replace('View.java', 'KotlinView.kt');
}

/**
 * Derives Kotlin filename from Java filename
 */
function deriveKotlinFileName(javaFileName) {
  return javaFileName.replace('View.java', 'KotlinView.kt');
}

// Static styles - defined outside component to avoid recreation on each render
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

const languageToggleStyles = css`
  display: flex;
  justify-content: flex-end;

  button {
    padding: 8px 20px;
    border: 1px solid var(--ifm-toc-border-color);
    border-bottom: none;
    background: var(--dwc-surface-3);
    cursor: pointer;
    font-weight: 500;
    font-size: 0.875rem;
    transition: background-color 0.2s, color 0.2s, border-color 0.2s;

    &:first-of-type {
      border-right: none;
    }

    &:hover:not(.active) {
      background-color: var(--ifm-hover-overlay);
    }

    &.active {
      background-color: var(--ifm-color-primary);
      border-color: var(--ifm-color-primary);
      color: white;
    }
  }
`;

/**
 * Custom hook for handling resizable iframe behavior
 * @returns {Object} Resize state, handlers, and refs
 */
function useResizable() {
  const [isResizing, setIsResizing] = useState(false);
  const [initialMouseX, setInitialMouseX] = useState(0);
  const [initialWidth, setInitialWidth] = useState(0);
  const [initialRight, setInitialRight] = useState(25);
  const [newRight, setNewRight] = useState(25);
  const [originalWidth, setOriginalWidth] = useState(0);

  const iframeRef = useRef(null);
  const codeButtonRef = useRef(null);

  const initializeWidth = () => {
    setOriginalWidth(iframeRef.current ? iframeRef.current.offsetWidth : 0);
  };

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

  return {
    isResizing,
    iframeRef,
    codeButtonRef,
    initializeWidth,
    startResizing,
    stopResizing,
    resize,
  };
}

export default function ComponentDemo({
  path,
  urls,
  highlight,
  height,
  frame,
}) {
  const [files, setFiles] = useState([]);
  const [buttonVisible, setButtonVisible] = useState(false);
  const [showCode, setShowCode] = useState(false);
  const [showKotlin, setShowKotlinState] = useState(() => {
    if (typeof window !== 'undefined') {
      return localStorage.getItem('preferKotlin') === 'true';
    }
    return false;
  });

  // Wrapper to sync preference across all ComponentDemo instances on the page
  const setShowKotlin = (value) => {
    setShowKotlinState(value);
    if (typeof window !== 'undefined') {
      localStorage.setItem('preferKotlin', value);
      window.dispatchEvent(new CustomEvent('kotlinPreferenceChanged', { detail: value }));
    }
  };

  const { colorMode } = useColorMode();
  const {
    isResizing,
    iframeRef,
    codeButtonRef,
    initializeWidth,
    startResizing,
    stopResizing,
    resize,
  } = useResizable();

  const iframeSrc = (isLocalhost ? GLOBALS.IFRAME_SRC_DEV : GLOBALS.IFRAME_SRC_LIVE) + path;
  const hasAnyKotlin = files.some(f => f.kotlinCode !== null);

  useEffect(() => {
    if (urls && urls.length > 0) {
      Promise.all(
        urls.map(async (url) => {
          // Fetch Java file
          const javaResponse = await fetch(url).catch(() => null);
          if (!javaResponse?.ok) {
            console.error(`Failed to fetch ${url}: ${javaResponse?.status}`);
            return null;
          }
          const code = await javaResponse.text();
          const fileName = url.split("/").pop();
          const extension = fileName.split(".").pop().toLowerCase();

          // Only attempt Kotlin fetch for Java files
          let kotlinCode = null;
          let kotlinFileName = null;
          if (extension === 'java' && fileName.includes('View')) {
            const kotlinUrl = deriveKotlinUrl(url);
            kotlinFileName = deriveKotlinFileName(fileName);
            const kotlinResponse = await fetch(kotlinUrl).catch(() => null);
            if (kotlinResponse?.ok) {
              kotlinCode = await kotlinResponse.text();
            }
          }

          return {
            fileName,
            code,
            language: getLanguageFromExtension(extension),
            kotlinFileName,
            kotlinCode,
          };
        })
      ).then((results) => setFiles(results.filter(Boolean)));
    }
    initializeWidth();
  }, [urls]);

  // Listen for preference changes from other ComponentDemo instances
  useEffect(() => {
    if (typeof window === 'undefined') return;

    const handlePreferenceChange = (e) => {
      setShowKotlinState(e.detail);
    };

    window.addEventListener('kotlinPreferenceChanged', handlePreferenceChange);
    return () => {
      window.removeEventListener('kotlinPreferenceChanged', handlePreferenceChange);
    };
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

  // Dynamic styles - must be inside component as they depend on state/props
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

  const detailsStyles = css`
    overflow: hidden;
    background-color: var(--dwc-surface-3);
    border: 1px solid var(--ifm-toc-border-color);
    ${frame !== "hidden" && "border-top: none;"}
    margin: ${frame === "hidden"
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

  return (
    <div
      css={mainStyles}
      onMouseUp={stopResizing}
      onMouseLeave={stopResizing}
      onMouseMove={resize}
    >
      {hasAnyKotlin && (
        <div css={languageToggleStyles}>
          <button
            className={!showKotlin ? 'active' : ''}
            onClick={() => setShowKotlin(false)}
          >
            Java
          </button>
          <button
            className={showKotlin ? 'active' : ''}
            onClick={() => setShowKotlin(true)}
          >
            Kotlin
          </button>
        </div>
      )}
      {frame !== "hidden" ? (
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
            src={iframeSrc}
            css={iframeStyles}
            ref={iframeRef}
            onMouseMove={resize}
          ></iframe>
          <div css={fadeInButton} ref={codeButtonRef}>
            <OpenNewWindowButton url={iframeSrc} />
          </div>
          <div css={resizeBarStyles} onMouseDown={startResizing}>
            <DragIndicatorIcon />
          </div>
        </div>
      ) : null}
      
      <details
        css={detailsStyles}>
          <summary onClick={() => setShowCode(!showCode)}>
            {showCode
              ? translate({
                  id: 'componentDemo.hideCode',
                  message: 'Hide Code',
                  description: 'Button text to hide the code section'
                })
              : translate({
                  id: 'componentDemo.showCode',
                  message: 'Show Code',
                  description: 'Button text to show the code section'
                })
            }
            <ChevronRightIcon css={showCodeIconStyles}/>
          </summary>
        {files.length > 0 && (
          <Tabs css={tabStyles} defaultValue={files[0].fileName}>
            {files.map((file, index) => {
              const useKotlin = showKotlin && file.kotlinCode !== null;
              const displayFileName = useKotlin ? file.kotlinFileName : file.fileName;
              const displayCode = useKotlin ? file.kotlinCode : file.code;
              const displayLanguage = useKotlin ? 'kotlin' : file.language;

              return (
                <TabItem
                  key={file.fileName}
                  value={file.fileName}
                  label={displayFileName}
                >
                  <CodeBlock
                    css={codeBlockStyles}
                    className="codeDemoBlock"
                    language={displayLanguage}
                    showLineNumbers
                    metastring={index === 0 ? highlight : undefined}
                  >
                    {displayCode}
                  </CodeBlock>
                </TabItem>
              );
            })}
          </Tabs>
        )}
      </details>

    </div>
  );
}

ComponentDemo.propTypes = {
  path: PropTypes.string.isRequired,
  urls: PropTypes.arrayOf(PropTypes.string),
  highlight: PropTypes.string,
  height: PropTypes.string,
  frame: PropTypes.string,
};
