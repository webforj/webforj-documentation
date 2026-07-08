import React, { useEffect } from 'react';
import { useLocation } from '@docusaurus/router';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';

const DEFAULT_LOCALE = 'en';
const COOKBOOK_ROUTE_BASE = 'cookbook';

function stripTrailingSlash(path) {
  return path.endsWith('/') ? path.slice(0, -1) : path;
}

function buildCookbookPath(baseUrl) {
  const basePath = stripTrailingSlash(baseUrl);

  return `${basePath}/${COOKBOOK_ROUTE_BASE}`;
}

function getCookbookRedirectPath(pathname, currentLocale, localeConfigs) {
  if (currentLocale === DEFAULT_LOCALE) {
    return null;
  }

  const sourceCookbookPath = buildCookbookPath(
    localeConfigs[currentLocale]?.baseUrl ?? `/${currentLocale}/`
  );
  if (
    pathname !== sourceCookbookPath &&
    !pathname.startsWith(`${sourceCookbookPath}/`)
  ) {
    return null;
  }

  const targetCookbookPath = buildCookbookPath(
    localeConfigs[DEFAULT_LOCALE]?.baseUrl ?? '/'
  );

  return `${targetCookbookPath}${pathname.slice(sourceCookbookPath.length)}`;
}

export default function Root({ children }) {
  const location = useLocation();
  const {
    i18n: { currentLocale, localeConfigs },
  } = useDocusaurusContext();

  useEffect(() => {
    const englishPath = getCookbookRedirectPath(
      location.pathname,
      currentLocale,
      localeConfigs
    );
    if (!englishPath) {
      return;
    }

    const currentUrl = `${location.pathname}${location.search}${location.hash}`;
    const targetUrl = `${englishPath}${location.search}${location.hash}`;

    if (targetUrl !== currentUrl) {
      window.location.replace(targetUrl);
    }
  }, [
    currentLocale,
    localeConfigs,
    location.hash,
    location.pathname,
    location.search,
  ]);

  return <>{children}</>;
}
