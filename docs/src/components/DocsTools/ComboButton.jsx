

import React, { useState, useRef, useEffect } from "react";

import ChevronRightIcon from '@mui/icons-material/ChevronRight';

function ComboButton() {
  const [open, setOpen] = useState(false);
  const buttonRef = useRef(null);
  const [dropdownWidth, setDropdownWidth] = useState();

  const currentUrl = typeof window !== "undefined" ? window.location.href : "";
  const docsPath = currentUrl.split("docs/")[1] || "";

  const options = [
    {
      key: "chatgpt",
      label: "Open in ChatGPT",
      href: `https://chat.openai.com/?model=gpt-4&hints=search&prompt=Read%20from%20${encodeURIComponent(currentUrl)}%20so%20I%20can%20ask%20questions%20about%20it`,
    },
    {
      key: "claude",
      label: "Open in Claude",
      href: `https://claude.ai/new?q=Read%20from%20${encodeURIComponent(currentUrl)}`,
    },
    {
      key: "markdown",
      label: "Show Markdown",
      href: `https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/docs/docs/${encodeURIComponent(docsPath)}.md`,
    },
  ];

  useEffect(() => {
    if (open && buttonRef.current) {
      setDropdownWidth(buttonRef.current.offsetWidth);
    }
    if (!open) return;
    const handleClick = (e) => {
      if (buttonRef.current && !buttonRef.current.contains(e.target)) {
        setOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClick);
    return () => document.removeEventListener("mousedown", handleClick);
  }, [open]);

  const btnStyle = {
    padding: "0.3em 0.7em",
    background: "var(--ifm-background-color)",
    border: "1px solid var(--ifm-color-emphasis-300)",
    fontSize: "0.95rem",
    lineHeight: "1.1",
    fontFamily: "inherit",
    boxSizing: "border-box",
    height: "32px",
    color: "var(--ifm-font-color-base)",
  };
  

  return (
    <>
      <style>{`
        .ai-combobutton a::after {
          content: none !important;
        }
        .ai-combobutton {
        margin-bottom: calc(var(--ifm-h1-vertical-rhythm-bottom) * var(--ifm-leading)) !important;
        text-wrap: nowrap;
        max-height: 32px;
        }
        .material-icons {
          font-family: 'Material Icons';
          font-style: normal;
          font-weight: normal;
          font-size: 20px;
          line-height: 1;
          letter-spacing: normal;
          text-transform: none;
          display: inline-block;
          white-space: nowrap;
          direction: ltr;
          -webkit-font-feature-settings: 'liga';
          -webkit-font-smoothing: antialiased;
        }
      `}</style>
      <div className="ai-combobutton"  ref={buttonRef}>
        <div style={{ display: "flex", alignItems: "stretch" }}>
          <a
            href={options[0].href}
            style={{
              ...btnStyle,
              borderRadius: "4px 0 0 4px",
              borderRight: "none",
              textDecoration: "none",
              verticalAlign: "top",
            }}
            target="_blank"
            rel="noopener noreferrer"
          >
            {options[0].label}
          </a>
          <button
            aria-label="Show options"
            onClick={() => setOpen(!open)}
            style={{
              ...btnStyle,
              borderRadius: "0 4px 4px 0",
              borderLeft: "1px solid var(--ifm-color-emphasis-300)",
              cursor: "pointer",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              padding: 0,
              width: 32,
            }}
          >
            <ChevronRightIcon style={{ transform: open ? "rotate(90deg)" : "rotate(0deg)", transition: "transform var(--dwc-transition-medium)" }} />
          </button>
        </div>
        {open && (
          <ul
            style={{
              position: "relative",
              zIndex: "var(--ifm-z-index-fixed)-1",
              background: "var(--ifm-background-color)",
              border: "1px solid var(--ifm-color-emphasis-300)",
              listStyle: "none",
              margin: 0,
              padding: "0.5em 0",
              minWidth: 32,
              boxShadow: "0 2px 8px var(--ifm-color-emphasis-200, rgba(0,0,0,0.08))",
            }}
          >
            {options.map(opt => (
              <li key={opt.key}>
                <button
                  style={{
                    display: "block",
                    width: "100%",
                    padding: "0.5em 1em",
                    background: "none",
                    border: "none",
                    textAlign: "left",
                    cursor: "pointer",
                    color: "var(--ifm-font-color-base)",
                  }}
                  onClick={() => {
                    window.open(opt.href, "_blank", "noopener,noreferrer");
                    setOpen(false);
                  }}
                >
                  {opt.label}
                </button>
              </li>
            ))}
          </ul>
        )}
      </div>
    </>
  );
}

export default ComboButton;
