import React from 'react';
import { Redirect } from 'react-router-dom';
import Head from '@docusaurus/Head';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';

// The site root sends readers to getting started. It still carries its own
// title and description, so search results and shared links for
// docs.webforj.com say what webforJ is instead of a bare "Documentation".
const TITLE = 'webforJ: the full-stack Java framework for business web apps';
const DESCRIPTION =
  'Build the whole web app in Java, from UI to data, with any backend. AI-ready, with a screen-by-screen path from Swing to the web. Docs, guides and live demos.';

export default function Home() {
  const { i18n } = useDocusaurusContext();
  const targetPath = i18n.currentLocale === i18n.defaultLocale
    ? '/docs/introduction/getting-started'
    : `/${i18n.currentLocale}/docs/introduction/getting-started`;

  return (
    <>
      <Head>
        <title>{TITLE}</title>
        <meta name="description" content={DESCRIPTION} />
        <meta property="og:title" content={TITLE} />
        <meta property="og:description" content={DESCRIPTION} />
      </Head>
      <Redirect to={targetPath} />
    </>
  );
}
