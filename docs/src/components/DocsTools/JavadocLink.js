/** @jsxImportSource @emotion/react */
import React, { useState, useEffect } from "react";
import { css } from "@emotion/react";
import { Tooltip, Chip } from "@mui/material";
import AutoStoriesIcon from "@mui/icons-material/AutoStories";

export default function JavadocLink({
  type,
  location,
  top,
  children,
  code,
  suffix,
}) {
  const [url, setUrl] = useState("");
  const [hasDocChips, setHasDocChips] = useState(false);

  useEffect(() => {
    // Check if there are any elements with the class 'doc-chip' on the page
    const docChips = document.querySelectorAll(".doc-chip");
    if (docChips.length > 0) {
      setHasDocChips(true);
    } else {
      setHasDocChips(false);
    }
  }, []);

  useEffect(() => {
    async function fetchLatestVersion() {
      const resolvedSuffix = suffix || "";
      const cacheKey = "webforj_latest_version";
      const cacheExpiry = 24 * 60 * 60 * 1000; // 24 hours

      const cachedData = localStorage.getItem(cacheKey);
      const cachedTimestamp = localStorage.getItem(`${cacheKey}_timestamp`);

      if (cachedData && cachedTimestamp && (Date.now() - cachedTimestamp) < cacheExpiry) {
        const latestVersion = cachedData;
        setUrl(
          `https://javadoc.io/doc/com.webforj/webforj-${type}/${latestVersion}/${location}.html${resolvedSuffix}`
        );
        return;
      }

      try {
        const response = await fetch(
          "https://api.github.com/repos/webforj/webforj/releases/latest"
        );
        if (!response.ok) {
          throw new Error(`GitHub API error: ${response.statusText}`);
        }

        const data = await response.json();
        const latestVersion = data.tag_name;
        console.log(latestVersion);

        localStorage.setItem(cacheKey, latestVersion);
        localStorage.setItem(`${cacheKey}_timestamp`, Date.now());

        setUrl(
          `https://javadoc.io/doc/com.webforj/webforj-${type}/${latestVersion}/${location}.html${resolvedSuffix}`
        );
      } catch (error) {
        console.error("Failed to fetch the latest version:", error);
        setUrl(
          `https://javadoc.io/doc/com.webforj/webforj-${type}/latest/${location}.html${resolvedSuffix}`
        );
      }
    }
    fetchLatestVersion();
  }, [type, location, suffix]);

  const mainStyles = css`
    ${(top) =>
      top &&
      css`
        margin-bottom: 1em;
        margin-left: 0.5em;
        float: right;
        @media (max-width: 500px) {
          margin-bottom: 1em;
          float: none;
          margin-left: -0.25em;
        }
      `}
  `;

  // Styling for the div, if no DocChips are on the page
  const divStyles = css`
    width: 100%;
    margin-bottom: 1em;
    display: flex;
    flex-direction: row-reverse;
  `;

  const apiStyles = css`
    background-color: var(--javadoclink-bg);
    color: var(--javadoclink-color);
    padding: 0 0 0 5px;
    :hover {
      background-color: var(--javadoclink-hover-bg);
      color: var(--javadoclink-hover-color);
    }
  `;

  return (
    <>
      {top === "true" && hasDocChips && (
        // If there are DocChips, don't wrap in a div
        <Tooltip title="JavaDoc" arrow css={mainStyles}>
          <Chip
            css={apiStyles}
            label="Java API"
            component="a"
            href={url}
            icon={<AutoStoriesIcon />}
            clickable={true}
            color="primary"
            target="_blank"
          />
        </Tooltip>
      )}
      {top === "true" && !hasDocChips && (
        // If there aren't DocChips, wrap in a div
        <div css={divStyles}>
          <Tooltip title="JavaDoc" arrow>
            <Chip
              css={apiStyles}
              label="Java API"
              component="a"
              href={url}
              icon={<AutoStoriesIcon />}
              clickable={true}
              color="primary"
              target="_blank"
            />
          </Tooltip>
        </div>
      )}
      {top !== "true" && !code && (
        <a href={url} target="_blank">
          {children}
        </a>
      )}
      {top !== "true" && code && (
        <code>
          <a href={url} target="_blank">
            {children}
          </a>
        </code>
      )}
    </>
  );
}
