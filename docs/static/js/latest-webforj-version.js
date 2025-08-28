function getLatestVer() {
  fetch('https://api.github.com/repos/webforj/webforj/releases/latest')
    .then(response => response.json())
    .then(data => {
      const latestVersion = data.tag_name;
      displayLatestVer(latestVersion, retries = 10);
    })
    .catch(error => console.error(error));
}

function displayLatestVer(latestVersion, retries) {
  const thisVer = document.getElementById("webforj-version-badge");
  if (thisVer) {
    thisVer.innerHTML = latestVersion;
  } else if (retries > 0) {
    setTimeout(() => displayLatestVer(latestVersion, retries - 1), 100);
  } else {
    console.warn("Couldn't find the webforJ version badge");
    return;
  }
}

getLatestVer();
