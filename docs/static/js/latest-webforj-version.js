let latestVersion = null;

async function fetchLatestVersion() {
  const cacheKey = "webforj_latest_version";
  const cacheExpiry = 24 * 60 * 60 * 1000; // 24 hours

  const cachedData = localStorage.getItem(cacheKey);
  const cachedTimestamp = localStorage.getItem(`${cacheKey}_timestamp`);

  if (cachedData && cachedTimestamp && (Date.now() - cachedTimestamp) < cacheExpiry) {
    latestVersion = cachedData;
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
    latestVersion = data.tag_name;

    localStorage.setItem(cacheKey, latestVersion);
    localStorage.setItem(`${cacheKey}_timestamp`, Date.now());

  } catch (error) {
    console.error("Failed to fetch the latest version:", error);
    latestVersion = "latest";
  }
}

function updateVersionBadge() {
  const badge = document.getElementById("webforj-version-badge");
  if (badge && latestVersion) {
    badge.innerHTML = `v${latestVersion}`;
  }
}

function tryUpdateBadge(retries = 10) {
  if (retries <= 0) return;
  updateVersionBadge();
  setTimeout(() => tryUpdateBadge(retries - 1), 50);
}

// Fetch version once on initial load
fetchLatestVersion().then(() => {
  tryUpdateBadge();
});

document.addEventListener('DOMContentLoaded', tryUpdateBadge);
window.addEventListener('popstate', tryUpdateBadge);
window.addEventListener('pushstate', tryUpdateBadge);
tryUpdateBadge();
