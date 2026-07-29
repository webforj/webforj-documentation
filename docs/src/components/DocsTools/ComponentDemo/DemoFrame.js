/** @jsxImportSource @emotion/react */

import { useState, useEffect, useRef, useCallback } from "react";
import { css } from "@emotion/react";
import { translate } from '@docusaurus/Translate';
import { useColorMode } from "@docusaurus/theme-common";
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';

import openInNewIcon from "../../../../static/img/window-maximize.png";

import DragIndicatorIcon from "@mui/icons-material/DragIndicator";

// True when the docs site is being served by `docusaurus start` on the local
// machine. Drives which iframe base URL we point at (Maven/Jetty in dev,
// production demo host otherwise).
const isLocalhost = typeof window !== "undefined" &&
  window.location.hostname === "localhost" &&
  window.location.port === "3000";

// Writes the current Docusaurus theme onto the iframe's root element so the
// embedded demo follows light/dark mode toggles. Wrapped in try/catch because
// cross-origin iframes throw on contentDocument access.
function applyTheme(iframe, colorMode) {
  try {
    const doc = iframe?.contentDocument || iframe?.contentWindow?.document;
    doc?.documentElement.setAttribute("data-app-theme", colorMode);
  } catch (error) {
    console.error("Failed to apply theme to iframe:", error);
  }
}

// Patches scroll-related methods inside the iframe to prevent DWC components
// from scrolling the parent documentation page. Confirmed culprit is
// scrollIntoViewIfNeeded on DWC-TREE-NODE elements; scrollIntoView and focus
// are patched defensively.
//
// Idempotent — sets a marker on the iframe's window so re-running on iframe
// reload (HMR, internal navigation) doesn't wrap already-wrapped functions
// and stack call layers.
//
// Known limitation: patches apply on iframe load, so a demo that calls focus()
// during initialization (before load fires) can still cause a one-time scroll
// jump. This is also blocked silently when the iframe is cross-origin (e.g.
// `npm start` mode where docs serve on :3000 and demos on :8080).
function applyScrollPatches(iframe) {
  try {
    const win = iframe?.contentWindow;
    if (!win || !win.Element || win.__demoScrollPatched) return;
    win.__demoScrollPatched = true;

    const preserveParentScroll = (originalFn) => function (...args) {
      const x = window.scrollX;
      const y = window.scrollY;
      originalFn.apply(this, args);
      window.scrollTo(x, y);
    };

    win.Element.prototype.scrollIntoView =
      preserveParentScroll(win.Element.prototype.scrollIntoView);

    if (win.Element.prototype.scrollIntoViewIfNeeded) {
      win.Element.prototype.scrollIntoViewIfNeeded =
        preserveParentScroll(win.Element.prototype.scrollIntoViewIfNeeded);
    }

    const originalFocus = win.HTMLElement.prototype.focus;
    win.HTMLElement.prototype.focus = function (options) {
      originalFocus.call(this, { ...options, preventScroll: true });
    };
  } catch (error) {
    // Silently ignore cross-origin errors
  }
}

// Resolves the iframe `src` URL for the current environment. In local dev,
// uses `iframeSrcDev` from customFields (which honors the WEBFORJ_PORT env
// var, default 8080). In production, uses `iframeSrcLive` (empty string, so
// the iframe src becomes a relative path on the docs domain).
function useIframeSrc(path) {
  const { siteConfig } = useDocusaurusContext();
  const { iframeSrcDev, iframeSrcLive } = siteConfig.customFields;
  return (isLocalhost ? iframeSrcDev : iframeSrcLive) + path;
}

// Theme-application + scroll-patches lifecycle for an iframe. Re-applies the
// theme when colorMode changes. Returns an `onLoad` callback to attach to the
// iframe element so patches apply as soon as content is available.
function useIframeBehavior(iframeRef) {
  const { colorMode } = useColorMode();

  useEffect(() => {
    const iframe = iframeRef.current;
    if (!iframe) return;
    applyTheme(iframe, colorMode);
    try {
      if (iframe.contentDocument?.readyState === 'complete') {
        applyScrollPatches(iframe);
      }
    } catch (e) {
      // Silently ignore cross-origin errors
    }
  }, [colorMode]);

  return useCallback(() => {
    const iframe = iframeRef.current;
    if (!iframe) return;
    applyTheme(iframe, colorMode);
    applyScrollPatches(iframe);
  }, [colorMode]);
}

/**
 * Drag-to-resize hook for the resizable frame variant.
 *
 * Drag bookkeeping (mouse origin, starting width, etc.) lives in refs so
 * 60fps mousemoves don't trigger React renders; only `isResizing` is state
 * because the iframe needs `pointer-events: none` during the drag and the
 * listener-attaching effect needs to react to it.
 *
 * Container width is tracked via ResizeObserver so the min/max clamps stay
 * correct after viewport changes.
 */
function useResizable({ minWidthFactor = 1 / 3, minRight = 25 } = {}) {
  const containerRef = useRef(null);
  const elementRef = useRef(null);
  const containerWidthRef = useRef(0);
  const dragRef = useRef({ startX: 0, startWidth: 0, startRight: minRight, currentRight: minRight });
  const [isResizing, setIsResizing] = useState(false);

  useEffect(() => {
    if (!containerRef.current) return;
    const observer = new ResizeObserver((entries) => {
      containerWidthRef.current = entries[0].contentRect.width;
    });
    observer.observe(containerRef.current);
    return () => observer.disconnect();
  }, []);

  const startResizing = (e) => {
    e.preventDefault();
    dragRef.current = {
      startX: e.clientX,
      startWidth: elementRef.current?.offsetWidth ?? 0,
      startRight: dragRef.current.currentRight,
      currentRight: dragRef.current.currentRight,
    };
    setIsResizing(true);
  };

  useEffect(() => {
    if (!isResizing) return;

    const onMove = (e) => {
      const drag = dragRef.current;
      const containerWidth = containerWidthRef.current;
      if (!containerWidth) return;
      const dx = e.clientX - drag.startX;

      const minWidth = containerWidth * minWidthFactor;
      const proposed = drag.startWidth + dx;
      const nextWidth = Math.max(minWidth, Math.min(containerWidth, proposed));

      if (elementRef.current) {
        elementRef.current.style.width = `${nextWidth}px`;
      }
    };
    const onUp = () => setIsResizing(false);

    window.addEventListener('mousemove', onMove);
    window.addEventListener('mouseup', onUp);
    return () => {
      window.removeEventListener('mousemove', onMove);
      window.removeEventListener('mouseup', onUp);
    };
  }, [isResizing, minWidthFactor, minRight]);

  return { containerRef, elementRef, isResizing, startResizing };
}

// Small button that opens the demo URL in a new browser tab. Decorative icon
// (alt=""); the button itself carries the accessible label.
function OpenNewWindowButton({ url }) {
  const { colorMode } = useColorMode();

  const buttonStyles = css`
    cursor: pointer;
    z-index: 10;
    height: 35px;
    width: 35px;
    border: none;
    background-color: transparent;
  `;

  const iconStyles = css`
    filter: ${colorMode === "dark" ? "invert(1)" : "none"};
    background-color: #ffffff80;
    border-radius: var(--dwc-border-radius-s);
  `;

  const openNewWindow = () => {
    window.open(url, "_blank", "noopener,noreferrer");
  };

  const openInNewWindowLabel = translate({
    id: 'componentDemo.openInNewWindow',
    message: 'Open demo in new window',
    description: 'Accessible label for the button that opens the demo iframe URL in a new browser tab',
  });

  return (
    <button css={buttonStyles} onClick={openNewWindow} aria-label={openInNewWindowLabel}>
      <img css={iconStyles} src={openInNewIcon} alt="" />
    </button>
  );
}

// ============================================================
// Styles
// ============================================================

const resizableFrameStyles = css`
  width: 100%;
  border: 1px solid var(--ifm-toc-border-color);
  border-right: none;
  background-color: transparent;
  display: flex;
  position: relative;

  &:hover .open-window-button-wrapper {
    opacity: 1;
  }
`;

const fadeInButton = css`
  opacity: 0;
  transition: opacity 0.3s ease-in-out;
  position: absolute;
  position-anchor: --fade-in-anchor;
  position-area: top right;
  transform: translate(-40px, 10px);
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

const phoneOuterStyles = css`
  display: flex;
  flex-direction: column;
  align-items: center;
  /* Top spacing only — the gap below the phone preview is owned by CodePanel's
     detached top margin so the phone + code panel act as one visual unit. */
  margin-top: 4rem;
`;

const phoneInnerStyles = css`
  position: relative;
  width: 100%;
  border-radius: 32px;
  overflow: hidden;
  box-shadow: 0 0 0 10px black, 0 3px 22px black;
  outline: thin solid black;

  &:hover .open-window-button-wrapper {
    opacity: 1;
  }
`;

const phoneMobileSize = css`
  max-width: 375px;
  height: 700px;
`;

const phoneDesktopSize = css`
  max-width: 100%;
  height: 600px;
`;

const phoneIframeStyles = css`
  width: 100%;
  height: 100%;
  background: var(--dwc-surface-3);
  anchor-name: --fade-in-anchor;
`;

const phoneFadeInButton = css`
  display: flex;
  justify-content: flex-end;
  opacity: 0;
  transition: opacity 0.3s ease-in-out;
  position: absolute;
  top: 10px;
  right: 5px;
  z-index: 10;
`;

const iframeTitle = () => translate({
  id: 'componentDemo.iframeTitle',
  message: 'Component demo',
  description: 'Accessible title for the demo iframe',
});

// ============================================================
// Variant components
// ============================================================

function ResizableFrame({ path, height }) {
  const {
    containerRef,
    elementRef: iframeRef,
    isResizing,
    startResizing,
  } = useResizable();
  const handleIframeLoad = useIframeBehavior(iframeRef);

  const iframeSrc = useIframeSrc(path);

  const iframeStyles = css`
    min-height: 100px;
    width: 100%;
    height: ${height || "100%"};
    pointer-events: ${isResizing ? "none" : "auto"};
    anchor-name: --fade-in-anchor;
  `;

  return (
    <div ref={containerRef} css={resizableFrameStyles}>
      <iframe
        title={iframeTitle()}
        onLoad={handleIframeLoad}
        loading="lazy"
        src={iframeSrc}
        css={iframeStyles}
        ref={iframeRef}
      ></iframe>
      <div className="open-window-button-wrapper" css={fadeInButton}>
        <OpenNewWindowButton url={iframeSrc} />
      </div>
      <div css={resizeBarStyles} onMouseDown={startResizing}>
        <DragIndicatorIcon />
      </div>
    </div>
  );
}

function PhoneFrame({ path, variant }) {
  const iframeRef = useRef(null);
  const handleIframeLoad = useIframeBehavior(iframeRef);

  const iframeSrc = useIframeSrc(path);
  const sizeStyles = variant === 'mobile' ? phoneMobileSize : phoneDesktopSize;

  return (
    <div css={phoneOuterStyles}>
      <div css={[phoneInnerStyles, sizeStyles]}>
        <div className="open-window-button-wrapper" css={phoneFadeInButton}>
          <OpenNewWindowButton url={iframeSrc} />
        </div>
        <iframe
          title={iframeTitle()}
          onLoad={handleIframeLoad}
          loading="lazy"
          src={iframeSrc}
          css={phoneIframeStyles}
          ref={iframeRef}
        ></iframe>
      </div>
    </div>
  );
}

/**
 * Renders the iframe pane for a webforJ demo. Three variants:
 *
 * - `'resizable'` (default): standard rectangle with a drag handle on the right.
 *   Honors the `height` prop.
 * - `'mobile'`: phone-shaped 375x700 preview, centered with a margin.
 * - `'desktop'`: phone-shaped 100%x600 preview, centered with a margin.
 *
 * Phone variants ignore `height` (their dimensions are fixed) and have no
 * drag-resize.
 *
 * @param {object} props
 * @param {string} props.path             Path appended to the iframe base URL.
 * @param {string} [props.height]         CSS height (resizable variant only).
 * @param {'resizable'|'mobile'|'desktop'} [props.variant='resizable']
 */
export default function DemoFrame({ path, height, variant = 'resizable' }) {
  if (variant === 'mobile' || variant === 'desktop') {
    return <PhoneFrame path={path} variant={variant} />;
  }
  return <ResizableFrame path={path} height={height} />;
}
