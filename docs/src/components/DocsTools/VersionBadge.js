import React, { useEffect } from 'react';
import { useLocation } from '@docusaurus/router';

export default function VersionBadge() {
  const location = useLocation();

  const getLatestVer = async () => {
    const cacheKey = "webforj_latest_version";
    const cacheExpiry = 24 * 60 * 60 * 1000;

    const cachedData = localStorage.getItem(cacheKey);
    const cachedTimestamp = localStorage.getItem(`${cacheKey}_timestamp`);

    if (cachedData && cachedTimestamp && (Date.now() - cachedTimestamp) < cacheExpiry) {
      displayLatestVer(cachedData, 10);
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

      localStorage.setItem(cacheKey, latestVersion);
      localStorage.setItem(`${cacheKey}_timestamp`, Date.now());

      displayLatestVer(latestVersion, 10);
    } catch (error) {
      console.error("Failed to fetch the latest version:", error);
      displayLatestVer("latest", 10);
    }
  };

  const displayLatestVer = (latestVersion, retries) => {
    const thisVer = document.getElementById("webforj-version-badge");
    if (thisVer) {
      thisVer.innerHTML = `v${latestVersion}`;
    } else if (retries > 0) {
      setTimeout(() => displayLatestVer(latestVersion, retries - 1), 100);
    }
  };

  useEffect(() => {
    getLatestVer();
  }, [location.pathname]); // Re-run when path changes

  return null;
}