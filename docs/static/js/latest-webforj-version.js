async function getLatestVer() {
  const cacheKey = "webforj_latest_version";
  const cacheExpiry = 24 * 60 * 60 * 1000; // 24 hours

  const cachedData = localStorage.getItem(cacheKey);
  const cachedTimestamp = localStorage.getItem(`${cacheKey}_timestamp`);

  if (cachedData && cachedTimestamp && (Date.now() - cachedTimestamp) < cacheExpiry) {
    const latestVersion = cachedData;
    displayLatestVer(latestVersion, retries = 10);
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

    displayLatestVer(latestVersion, retries = 10);

  } catch (error) {
    console.error("Failed to fetch the latest version:", error);
    displayLatestVer(latest, retries = 10);
  }
}

function displayLatestVer(latestVersion, retries) {
  const thisVer = document.getElementById("webforj-version-badge");
  if (thisVer) {
    thisVer.innerHTML = `v${latestVersion}`;
  } else if (retries > 0) {
    setTimeout(() => displayLatestVer(latestVersion, retries - 1), 100);
  } else {
    console.warn("Couldn't find the webforJ version badge");
    return;
  }
}

getLatestVer();
