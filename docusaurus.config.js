// @ts-check
// Note: type annotations allow type checking and IDEs autocompletion

const lightCodeTheme = require('prism-react-renderer').themes.github;
const darkCodeTheme = require('prism-react-renderer').themes.dracula;

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'The Dynamic Web Client for Java',
  tagline: 'Develop your Progressive Web App in Java and deploy it to the BBj application server',
  url: 'https://webforj.github.io/',
  baseUrl: '/',
  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',
  favicon: 'img/logo.png',
  // GitHub pages deployment config.
  // If you aren't using GitHub pages, you don't need these.
  organizationName: 'dwcjava', // Usually your GitHub org/user name.
  projectName: 'dwcjava.github.io', // Usually your repo name.
  trailingSlash: false,
  
  // Even if you don't use internalization, you can use this field to set useful
  // metadata like html lang. For example, if your site is Chinese, you may want
  // to replace "en" with "zh-Hans".
  i18n: {
    defaultLocale: 'en',
    locales: ['en'],
  },

  presets: [
    [
      'classic',
      ({
        docs: {
          sidebarPath: require.resolve('./sidebars.js'),
          // Please change this to your repo.
          // Remove this to remove the "edit this page" links.
          editUrl:
          "https://github.com/DwcJava/dwcjava.github.io/tree/website/",
        },
        // blog: {
        //   showReadingTime: true,
        //   // Please change this to your repo.
        //   // Remove this to remove the "edit this page" links.
        //   // editUrl:
        //   //   'https://github.com/facebook/docusaurus/tree/main/packages/create-docusaurus/templates/shared/',
        // },
        theme: {
          customCss: require.resolve('./src/css/custom.css'),
        },
        gtag: {
          trackingID: 'G-84THEWQM3G',
          anonymizeIP: true
        },
        googleTagManager: {
          containerId: 'GTM-NB7VHG4',
        },
      }),
    ],
  ],
  plugins: [],
  themeConfig:
    ({
      algolia: {
        // The application ID provided by Algolia
        appId: 'RSLUBH6I28',
  
        // Public API key: it is safe to commit it
        apiKey: '9d34325b39e4c9380cd2396f98f772b6',
  
        indexName: 'dwcj',
  
        // Optional: see doc section below
        contextualSearch: true,
  
        // Optional: Specify domains where the navigation should occur through window.location instead on history.push. Useful when our Algolia config crawls multiple documentation sites and we want to navigate with window.location.href to them.
        externalUrlRegex: 'external\\.com|domain\\.com',
  
        // Optional: Replace parts of the item URLs from Algolia. Useful when using the same search index for multiple deployments using a different baseUrl. You can use regexp or string in the `from` param. For example: localhost:3000 vs myCompany.com/docs
        // replaceSearchResultPathname: {
        //   from: '/docs/', // or as RegExp: /\/docs\//
        //   to: '/',
        // },
  
        // Optional: Algolia search parameters
        searchParameters: {},
  
        // Optional: path for search page that enabled by default (`false` to disable it)
        searchPagePath: 'search',
  
        //... other Algolia params
      },
      image: '/img/logo.png',
      navbar: {
        title: 'Dynamic Web Client for Java',
        logo: {
          alt: 'My Site Logo',
          src: 'img/logo.png',
        },
        items: [
          // {
          //   type: 'doc',
          //   docId: 'intro',
          //   position: 'left',
          //   label: 'Documentation'
          // },
          {
            docid: 'intro',
            position: 'left',
            label: 'Installation',
            to: '/docs/installation',
            activeBasePath: '/docs/installation'
          },
          {
            docid: 'started',
            position: 'left',
            label: 'Getting Started',
            to: '/docs/category/getting-started',
            activeBasePath: '/docs/category/getting-started'
          },
          {
            docid: 'components',
            position: 'left',
            label: 'Components',
            to: '/docs/components/home',
            activeBasePath: '/docs/components'
          },
          // {to: '/live_demo', label: 'Live Demo', position: 'left'},
          // {to: '/tutorials/intro', label: 'Tutorials', position: 'left'},
          {
            href: 'https://github.com/webforj',
            label: 'GitHub',
            position: 'right',
          },
        ],
      },
      docs:{
        sidebar:{
          hideable: true
        }
      },
      footer: {
        style: 'dark',
        links: [
          // {
          //   title: 'Docs',
          //   items: [
          //     {
          //       label: 'Tutorial',
          //       to: '/docs/intro',
          //     },
          //   ],
          // },
          // {
          //   title: 'Community',
          //   items: [
          //     {
          //       label: 'Stack Overflow',
          //       href: 'https://stackoverflow.com/questions/tagged/docusaurus',
          //     },
          //     {
          //       label: 'Discord',
          //       href: 'https://discordapp.com/invite/docusaurus',
          //     },
          //     {
          //       label: 'Twitter',
          //       href: 'https://twitter.com/docusaurus',
          //     },
          //   ],
          // },
          // {
          //   title: 'More',
          //   items: [
          //     {
          //       label: 'Blog',
          //       to: '/blog',
          //     },
          //     {
          //       label: 'GitHub',
          //       href: 'https://github.com/facebook/docusaurus',
          //     },
          //   ],
          // },
        ],
        copyright: `Copyright © ${new Date().getFullYear()} BASIS International Ltd. All rights reserved.`,
      },
      prism: {
        theme: lightCodeTheme,
        darkTheme: darkCodeTheme,
        additionalLanguages: ['java']
      },
    })
};

module.exports = config;
