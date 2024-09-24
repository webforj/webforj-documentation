// @ts-check
// Note: type annotations allow type checking and IDEs autocompletion

const lightCodeTheme = require('prism-react-renderer').themes.github;
const darkCodeTheme = require('prism-react-renderer').themes.dracula;

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'webforJ',
  tagline: 'Develop your Progressive Web App in Java',
  url: 'https://documentation.webforj.com/',
  baseUrl: '/',
  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',
  favicon: 'img/webforj_icon.svg',
  // GitHub pages deployment config.
  // If you aren't using GitHub pages, you don't need these.
  organizationName: 'webforj', // Usually your GitHub org/user name.
  projectName: 'webforj-docs', // Usually your repo name.
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
          "https://github.com/webforj/webforj-docs/tree/website/",
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
          trackingID: 'G-47SBJTFG20',
          anonymizeIP: true
        },
        googleTagManager: {
          containerId: 'GTM-T9Q2WZ5J',
        },
      }),
    ],
  ],
  plugins: [],
  themeConfig: {
      algolia: {
        // The application ID provided by Algolia
        appId: '826LUKOV8E',
  
        // Public API key: it is safe to commit it
        apiKey: 'a69d79113b838bfc8490ffb56cef78f2',
  
        indexName: 'umentation-webforj',
  
        // Optional: see doc section below
        contextualSearch: false,
  
        // // Optional: Specify domains where the navigation should occur through window.location instead on history.push. Useful when our Algolia config crawls multiple documentation sites and we want to navigate with window.location.href to them.
        // externalUrlRegex: 'external\\.com|domain\\.com',
  
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
      announcementBar: {
        id: '24.11',
        content:
          'We are excited to announce webforJ version 24.11 is live! Read more about the changes and features <a href=/blog/whats-new-v24.11>here.</a> ',
        // backgroundColor: 'var(--announcement-background)',
        // textColor: '#ebedf0',
        // backgroundColor: 'var(--chip-background)',
        // textColor: 'var(--chip-text)',
        isCloseable: true,
      },
      image: '/img/webforj.svg',
      navbar: {
        title: '',
        logo: {
          alt: 'webforJ Logo',
          src: 'img/webforj.svg',
          href: 'https://webforj.com/',
          target: '_self'
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
            to: '/docs/category/installation',
            activeBasePath: '/docs/category/installation'
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
          {to: 'blog', label: 'Blog', position: 'left'}, // or position: 'right'
          {
            label: "Report Issue",
            href: 'https://github.com/webforj/webforj/issues/new/choose',
            position: 'right',
          },
          {
            label: "Javadocs",
            href: 'https://javadoc.io/doc/com.webforj',
            position: 'right',
          },
          {
            href: 'https://github.com/webforj',
            position: 'right',
            className: "header-github-link"
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
          // {
            //   title: `Copyright © ${new Date().getFullYear()} BASIS International Ltd. All rights reserved.`,
            //   items:
            //   {
              //     label: 'BASIS International Ltd',
              //     href: 'https://basis.cloud/contact/',
              //   }
              // }
              {
                html: `
                <p>Copyright © <script>document.write(/\d{4}/.exec(Date())[0])</script> <a href='https://basis.cloud/contact/'> BASIS International Ltd.</a> All rights reserved.</p>
                `
              }
            ],
        // copyright: `Copyright © ${new Date().getFullYear()} BASIS International Ltd. All rights reserved.`,
      },
      prism: {
        theme: lightCodeTheme,
        darkTheme: darkCodeTheme,
        additionalLanguages: ['java']
      },
    }
};

module.exports = config;
