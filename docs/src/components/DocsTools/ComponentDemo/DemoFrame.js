/** @jsxImportSource @emotion/react */

import { useState, useEffect, useRef, useCallback } from "react";
import { css } from "@emotion/react";
import { translate } from '@docusaurus/Translate';
import { useColorMode } from "@docusaurus/theme-common";
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import 'devices.css/dist/devices.min.css';

// The phone frame from the devices.css library, by its own class names
const PHONE_CLASS = 'device device-google-pixel-6-pro device-black';


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
export function useIframeSrc(path) {
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

// Narrowest width the resizable frame can be dragged to. 375px is the smallest
// viewport phones ship with, so anything narrower previews a layout no real
// device would show.
const MIN_FRAME_WIDTH = 375;

/**
 * Drag-to-resize hook for the resizable frame variant.
 *
 * Drag bookkeeping (mouse origin, starting width, etc.) lives in refs so
 * 60fps mousemoves don't trigger React renders; only `isResizing` is state
 * because the iframe needs `pointer-events: none` during the drag and the
 * listener-attaching effect needs to react to it.
 *
 * Container width is tracked via ResizeObserver so the min/max clamps stay
 * correct after viewport changes. The frame is clamped to
 * [`minWidth`, container width]; when the container is itself narrower than
 * `minWidth`, the container width becomes the floor so the frame never
 * overflows it.
 */
function useResizable({ minWidth = MIN_FRAME_WIDTH, minRight = 25 } = {}) {
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

      const floor = Math.min(minWidth, containerWidth);
      const proposed = drag.startWidth + dx;
      const nextWidth = Math.max(floor, Math.min(containerWidth, proposed));

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
  }, [isResizing, minWidth, minRight]);

  return { containerRef, elementRef, isResizing, startResizing };
}

// ============================================================
// Styles
// ============================================================

const resizableStageStyles = css`
  display: flex;
  width: 100%;
`;

// A slim handle along the right edge of the preview. Dragging it narrows
// the demo to show how it behaves at smaller widths.
const resizeHandleStyles = css`
  display: flex;
  flex: none;
  align-items: center;
  justify-content: center;
  width: 14px;
  border-left: 1px solid var(--demo-line);
  background-color: var(--dwc-surface-3);
  cursor: ew-resize;

  &::before {
    content: "";
    width: 4px;
    height: 28px;
    border-radius: 999px;
    background-color: var(--ifm-color-emphasis-400);
    transition: background-color 150ms ease-out;
  }

  &:hover::before {
    background-color: var(--ifm-color-primary);
  }

  @media screen and (max-width: 768px) {
    display: none;
  }
`;

// The device variants show the demo inside a real-looking device, drawn by
// the devices.css library (the same frames the showcase page uses). The
// stage has little padding so the device takes the room there is.
const deviceStageStyles = css`
  display: grid;
  place-items: center;
  padding: 24px 20px;
  background-color: var(--dwc-surface-2);

  .device-screen {
    border: 0;
    background: var(--dwc-surface-3);
  }

  /* On a phone-sized viewport the frame is dropped and the demo itself
     takes the full width. */
  @media screen and (max-width: 480px) {
    padding: 0;

    .device,
    .device .device-frame,
    .device .device-screen {
      width: 100%;
      margin: 0;
      padding: 0;
      border-radius: 0;
      box-shadow: none;
    }

    .device > :not(.device-frame) {
      display: none;
    }
  }
`;

// The phone keeps the library's own size: a 376 x 816 screen.
const phoneStageStyles = css`
  @media screen and (max-width: 480px) {
    .device,
    .device .device-frame {
      height: 700px;
    }

    .device .device-screen {
      height: 100%;
    }
  }
`;

// The desktop variant has no frame: the demo fills the window, 600px tall.
const desktopStageStyles = css`
  iframe {
    display: block;
    width: 100%;
    height: 600px;
    border: 0;
    background: var(--dwc-surface-3);
  }
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
    display: block;
    min-height: 100px;
    width: 100%;
    height: ${height || "100%"};
    border: 0;
    pointer-events: ${isResizing ? "none" : "auto"};
  `;

  return (
    <div ref={containerRef} css={resizableStageStyles}>
      <iframe
        title={iframeTitle()}
        onLoad={handleIframeLoad}
        loading="lazy"
        src={iframeSrc}
        css={iframeStyles}
        ref={iframeRef}
      ></iframe>
      <div css={resizeHandleStyles} onMouseDown={startResizing} aria-hidden="true" />
    </div>
  );
}

function DeviceFrame({ path, variant }) {
  const iframeRef = useRef(null);
  const handleIframeLoad = useIframeBehavior(iframeRef);

  const iframeSrc = useIframeSrc(path);
  const isPhone = variant === 'mobile';

  const screen = (
    <iframe
      className={isPhone ? 'device-screen' : undefined}
      title={iframeTitle()}
      onLoad={handleIframeLoad}
      loading="lazy"
      src={iframeSrc}
      ref={iframeRef}
    ></iframe>
  );

  if (!isPhone) {
    return <div css={desktopStageStyles}>{screen}</div>;
  }

  return (
    <div css={[deviceStageStyles, phoneStageStyles]}>
      <div className={PHONE_CLASS}>
        <div className="device-frame">{screen}</div>
        <div className="device-stripe" />
        <div className="device-header" />
        <div className="device-sensors" />
        <div className="device-btns" />
        <div className="device-power" />
      </div>
    </div>
  );
}

/**
 * Renders the preview of a webforJ demo. Three variants:
 *
 * - `'resizable'` (default): the demo at full width with a drag handle on the
 *   right. Honors the `height` prop.
 * - `'mobile'`: a 376x816 preview in a phone frame.
 * - `'desktop'`: a full-width, 600px tall preview with no frame.
 *
 * The device variants ignore `height` (their dimensions are fixed) and have
 * no drag-resize.
 *
 * @param {object} props
 * @param {string} props.path             Path appended to the iframe base URL.
 * @param {string} [props.height]         CSS height (resizable variant only).
 * @param {'resizable'|'mobile'|'desktop'} [props.variant='resizable']
 */
export default function DemoFrame({ path, height, variant = 'resizable' }) {
  if (variant === 'mobile' || variant === 'desktop') {
    return <DeviceFrame path={path} variant={variant} />;
  }
  return <ResizableFrame path={path} height={height} />;
}
