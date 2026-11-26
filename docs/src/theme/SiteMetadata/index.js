import React from 'react';
import Head from '@docusaurus/Head';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import {PageMetadata, useThemeConfig} from '@docusaurus/theme-common';
import {
  DEFAULT_SEARCH_TAG,
  useAlternatePageUtils,
} from '@docusaurus/theme-common/internal';
import {useLocation} from '@docusaurus/router';
import {applyTrailingSlash} from '@docusaurus/utils-common';
import SearchMetadata from '@theme/SearchMetadata';

// Sections published in every locale but never translated. Their locale
// copies are English duplicates, so they point at the English page as
// canonical.
const UNTRANSLATED_SECTIONS = /^(blog|cookbook)(\/|$)/;

// Search, archive, tag, author and pagination listings repeat the titles and
// descriptions of the content they list and have nothing of their own to
// rank for. They match the listings the sitemap leaves out.
const LISTING_PAGES =
  /^(search|blog\/archive|blog\/(tags|authors|page)(\/.*)?|cookbook\/tags(\/.*)?)\/?$/;

function usePathSuffix() {
  const {
    siteConfig: {baseUrl},
  } = useDocusaurusContext();
  const {pathname} = useLocation();
  return pathname.startsWith(baseUrl)
    ? pathname.slice(baseUrl.length)
    : pathname.replace(/^\//, '');
}

function useIsUntranslated() {
  return UNTRANSLATED_SECTIONS.test(usePathSuffix());
}

const bcp47ToOpenGraphLocale = (code) => code.replace('-', '_');

// Translations are for visitors of the site, not for search engines: only
// the default locale is indexed, so pages carry no hreflang alternates and
// every other locale is marked noindex, as are the listing pages.
function LocaleHeaders({untranslated}) {
  const {
    i18n: {currentLocale, defaultLocale, localeConfigs},
  } = useDocusaurusContext();
  const ogLocale = untranslated ? defaultLocale : currentLocale;
  const hidden =
    currentLocale !== defaultLocale || LISTING_PAGES.test(usePathSuffix());

  return (
    <Head>
      {hidden && <meta name="robots" content="noindex, follow" />}
      <meta
        property="og:locale"
        content={bcp47ToOpenGraphLocale(localeConfigs[ogLocale].htmlLang)}
      />
    </Head>
  );
}

function useDefaultCanonicalUrl() {
  const {
    siteConfig: {url: siteUrl, baseUrl, trailingSlash},
  } = useDocusaurusContext();
  const {pathname} = useLocation();
  const canonicalPathname = applyTrailingSlash(pathname, {
    trailingSlash,
    baseUrl,
  });
  return siteUrl + canonicalPathname;
}

function CanonicalUrlHeaders({untranslated}) {
  const {
    i18n: {defaultLocale},
  } = useDocusaurusContext();
  const alternatePageUtils = useAlternatePageUtils();
  const defaultCanonicalUrl = useDefaultCanonicalUrl();
  const canonicalUrl = untranslated
    ? alternatePageUtils.createUrl({locale: defaultLocale, fullyQualified: true})
    : defaultCanonicalUrl;
  return (
    <Head>
      <meta property="og:url" content={canonicalUrl} />
      <link rel="canonical" href={canonicalUrl} />
    </Head>
  );
}

export default function SiteMetadata() {
  const {
    i18n: {currentLocale},
  } = useDocusaurusContext();
  const {metadata, image: defaultImage} = useThemeConfig();
  const untranslated = useIsUntranslated();
  return (
    <>
      <Head>
        <meta name="twitter:card" content="summary_large_image" />
        <body />
      </Head>

      {defaultImage && <PageMetadata image={defaultImage} />}

      <CanonicalUrlHeaders untranslated={untranslated} />

      <LocaleHeaders untranslated={untranslated} />

      <SearchMetadata tag={DEFAULT_SEARCH_TAG} locale={currentLocale} />

      <Head>
        {metadata.map((metadatum, i) => (
          <meta key={i} {...metadatum} />
        ))}
      </Head>
    </>
  );
}
