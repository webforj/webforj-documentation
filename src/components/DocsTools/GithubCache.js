const CACHE_DURATION = 60 * 60 * 1000; // 1 hour

let latestTag = null;
let lastFetchTime = null;

async function fetchLatestTag() {
  try {
    const response = await fetch('https://api.github.com/repos/DwcJava/engine/tags');
    const data = await response.json();
    latestTag = data[0].name;
    lastFetchTime = Date.now();
    return latestTag;
  } catch (error) {
    console.error('Error fetching latest tag:', error);
    return null;
  }
}

async function getLatestTag() {
  if (latestTag && lastFetchTime && Date.now() - lastFetchTime < CACHE_DURATION) {
    return latestTag; // Return cached value if it exists and hasn't expired
  }
  return fetchLatestTag(); // Fetch new value and update cache
}

export default {
  getLatestTag,
};
