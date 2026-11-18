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
// canonical and carry no hreflang alternates.
const UNTRANSLATED_SECTIONS = /^(blog|cookbook)(\/|$)/;

function useIsUntranslated() {
  const {
    siteConfig: {baseUrl},
  } = useDocusaurusContext();
  const {pathname} = useLocation();
  const suffix = pathname.startsWith(baseUrl)
    ? pathname.slice(baseUrl.length)
    : pathname.replace(/^\//, '');
  return UNTRANSLATED_SECTIONS.test(suffix);
}

const bcp47ToOpenGraphLocale = (code) => code.replace('-', '_');

function AlternateLangHeaders({untranslated}) {
  const {
    i18n: {currentLocale, defaultLocale, localeConfigs},
  } = useDocusaurusContext();
  const alternatePageUtils = useAlternatePageUtils();
  const currentHtmlLang = localeConfigs[currentLocale].htmlLang;

  if (untranslated) {
    return (
      <Head>
        <meta
          property="og:locale"
          content={bcp47ToOpenGraphLocale(localeConfigs[defaultLocale].htmlLang)}
        />
      </Head>
    );
  }

  return (
    <Head>
      {Object.entries(localeConfigs).map(([locale, {htmlLang}]) => (
        <link
          key={locale}
          rel="alternate"
          href={alternatePageUtils.createUrl({
            locale,
            fullyQualified: true,
          })}
          hrefLang={htmlLang}
        />
      ))}
      <link
        rel="alternate"
        href={alternatePageUtils.createUrl({
          locale: defaultLocale,
          fullyQualified: true,
        })}
        hrefLang="x-default"
      />

      <meta
        property="og:locale"
        content={bcp47ToOpenGraphLocale(currentHtmlLang)}
      />
      {Object.values(localeConfigs)
        .filter((config) => currentHtmlLang !== config.htmlLang)
        .map((config) => (
          <meta
            key={`meta-og-${config.htmlLang}`}
            property="og:locale:alternate"
            content={bcp47ToOpenGraphLocale(config.htmlLang)}
          />
        ))}
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

      <AlternateLangHeaders untranslated={untranslated} />

      <SearchMetadata tag={DEFAULT_SEARCH_TAG} locale={currentLocale} />

      <Head>
        {metadata.map((metadatum, i) => (
          <meta key={i} {...metadatum} />
        ))}
      </Head>
    </>
  );
}
