import React from 'react';
import { useLocation } from '@docusaurus/router';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import { translate } from '@docusaurus/Translate';
import { mergeSearchStrings, useHistorySelector } from '@docusaurus/theme-common';
import { useAlternatePageUtils } from '@docusaurus/theme-common/internal';
import DropdownNavbarItem from '@theme/NavbarItem/DropdownNavbarItem';
import IconLanguage from '@theme/Icon/Language';

import styles from './styles.module.css';

const COOKBOOK_ROUTE_BASE = 'cookbook';

function stripTrailingSlash(path) {
  return path.endsWith('/') ? path.slice(0, -1) : path;
}

function isCookbookPath(pathname, baseUrl) {
  const cookbookPath = `${stripTrailingSlash(baseUrl)}/${COOKBOOK_ROUTE_BASE}`;

  return pathname === cookbookPath || pathname.startsWith(`${cookbookPath}/`);
}

function useLocaleDropdownUtils() {
  const {
    siteConfig,
    i18n: { currentLocale, defaultLocale, localeConfigs },
  } = useDocusaurusContext();
  const { pathname } = useLocation();
  const alternatePageUtils = useAlternatePageUtils();
  const search = useHistorySelector((history) => history.location.search);
  const hash = useHistorySelector((history) => history.location.hash);

  const getLocaleConfig = (locale) => {
    const localeConfig = localeConfigs[locale];
    if (!localeConfig) {
      throw new Error(`No locale config found for locale=${locale}`);
    }
    return localeConfig;
  };

  const cookbookPage = isCookbookPath(
    pathname,
    getLocaleConfig(currentLocale).baseUrl
  );

  const getBaseURLForLocale = (locale) => {
    const targetLocale = cookbookPage ? defaultLocale : locale;
    const localeConfig = getLocaleConfig(targetLocale);
    const isSameDomain = localeConfig.url === siteConfig.url;
    if (isSameDomain) {
      return `pathname://${alternatePageUtils.createUrl({
        locale: targetLocale,
        fullyQualified: false,
      })}`;
    }
    return alternatePageUtils.createUrl({
      locale: targetLocale,
      fullyQualified: true,
    });
  };

  return {
    getURL: (locale, options) => {
      const finalSearch = mergeSearchStrings(
        [search, options.queryString],
        'append'
      );
      return `${getBaseURLForLocale(locale)}${finalSearch}${hash}`;
    },
    getLabel: (locale) => getLocaleConfig(locale).label,
    getLang: (locale) => getLocaleConfig(locale).htmlLang,
  };
}

export default function LocaleDropdownNavbarItem({
  mobile,
  dropdownItemsBefore,
  dropdownItemsAfter,
  queryString,
  ...props
}) {
  const utils = useLocaleDropdownUtils();
  const {
    i18n: { currentLocale, locales },
  } = useDocusaurusContext();

  const localeItems = locales.map((locale) => ({
    label: utils.getLabel(locale),
    lang: utils.getLang(locale),
    to: utils.getURL(locale, { queryString }),
    target: '_self',
    autoAddBaseUrl: false,
    className:
      locale === currentLocale
        ? mobile
          ? 'menu__link--active'
          : 'dropdown__link--active'
        : '',
  }));

  const items = [...dropdownItemsBefore, ...localeItems, ...dropdownItemsAfter];
  const dropdownLabel = mobile
    ? translate({
        message: 'Languages',
        id: 'theme.navbar.mobileLanguageDropdown.label',
        description: 'The label for the mobile language switcher dropdown',
      })
    : utils.getLabel(currentLocale);

  return (
    <DropdownNavbarItem
      {...props}
      mobile={mobile}
      label={
        <>
          <IconLanguage className={styles.iconLanguage} />
          {dropdownLabel}
        </>
      }
      items={items}
    />
  );
}
