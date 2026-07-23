import React from 'react';
import { Redirect } from 'react-router-dom';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';

export default function Home() {
  const { i18n } = useDocusaurusContext();
  const targetPath = i18n.currentLocale === i18n.defaultLocale
    ? '/docs/introduction/getting-started'
    : `/${i18n.currentLocale}/docs/introduction/getting-started`;

  return <Redirect to={targetPath} />;
}
