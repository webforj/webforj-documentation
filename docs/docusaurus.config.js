// @ts-check
const lightCodeTheme = require('prism-react-renderer').themes.github;
const darkCodeTheme = require('prism-react-renderer').themes.dracula;

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'webforJ Documentation',
  tagline: 'webforJ is a robust and flexible web framework that allows you to easily create a modern and engaging user interface using Java.',
  url: 'https://docs.webforj.com/',
  baseUrl: '/',
  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',
  favicon: 'img/webforj_icon.svg',
  organizationName: 'webforj',
  projectName: 'webforj-docs',
  trailingSlash: false,
  i18n: {
    defaultLocale: 'en',
    locales: ['en'],
  },
  scripts: [
    { src: '/js/dwc-theme-switcher.js', async: false },
    { src: '/js/link-decorator.js' }
  ],
  headTags: [
    {
      tagName: 'link',
      attributes: {
        rel: 'stylesheet',
        href: 'https://cdn.jsdelivr.net/gh/webforj/dwc-dist@latest/dwc-ui.css',
      },
    },
  ],
  presets: [
    [
      'classic',
      ({
        docs: {
          sidebarPath: require.resolve('./sidebars.js'),
          editUrl:
            'https://github.com/webforj/webforj-documentation/tree/main/docs/',
        },
        theme: {
          customCss: require.resolve('./src/css/custom.scss'),
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
  plugins: [
    'docusaurus-plugin-sass',
    [
      '@docusaurus/plugin-client-redirects',
      {
        redirects: [
          // /docs/oldDoc -> /docs/newDoc
          {
            from: '/docs/installation/docker',
            to: '/docs/introduction/getting-started',
          },
          {
            from: '/docs/intro/getting-started',
            to: '/docs/introduction/getting-started',
          },
          {
            from: '/docs/intro/basics',
            to: '/docs/introduction/basics',
          },
        ],
      },
    ]
  ],
  themeConfig: {
    algolia: {
      appId: '826LUKOV8E',
      apiKey: 'a69d79113b838bfc8490ffb56cef78f2',
      indexName: 'umentation-webforj',
      contextualSearch: false,
    },
    announcementBar: {
      id: '25.02',
      content:
        'We are excited to announce webforJ version 25.02 is live! Read more about the changes and features <a href=/blog/whats-new-v25.02>here.</a> ',
      isCloseable: true,
    },
    image: 'https://docs.webforj.com/img/social-cover.png',
    navbar: {
      style: 'dark',
      logo: {
        alt: 'webforJ Logo',
        src: 'img/webforj.svg',
        href: 'https://webforj.com/',
        target: '_self'
      },
      items: [
        {
          position: 'left',
          label: 'Getting Started',
          to: '/docs/introduction/getting-started',
          activeBasePath: '/docs/introduction/getting-started'
        },
        {
          position: 'left',
          label: 'Components',
          to: '/docs/components/overview',
          activeBasePath: '/docs/components'
        },
        {
          type: 'search',
          position: 'right',
        },
        {
          label: 'Community',
          position: 'right',
          items: [
            {
              label: "Events",
              href: 'https://webforj.com/events/',
              target: '_blank',
              rel: null,
            },
            {
              to: 'blog',
              label: 'Blog'
            }
          ]
        },
        {
          label: 'Developers',
          position: 'right',
          items: [
            {
              label: "startforJ",
              href: 'https://docs.webforj.com/startforj/',
              target: '_blank',
              rel: null,
            },
            {
              label: "DWC HueCraft",
              href: 'https://webforj.github.io/huecraft/',
              target: '_blank',
              rel: null,
            },
            {
              label: "DWC Design Kit",
              href: 'https://www.figma.com/community/file/1144573845612007198/dwc-design-kit',
              target: '_blank',
              rel: null,
            },
            {
              label: "JavaDocs",
              href: 'https://javadoc.io/doc/com.webforj',
              rel: null,
            },
            {
              label: "Client Components",
              to: '/docs/client-components/overview',
              activeBasePath: 'docs/client-components/overview'
            },
            {
              label: "Contribution Guide",
              href: 'https://github.com/webforj/webforj/blob/main/CONTRIBUTING.md',
              rel: null,
            },
          ]
        },
        {
          label: 'Support',
          position: 'right',
          items: [
            {
              label: "Report Issue",
              href: 'https://github.com/webforj/webforj/issues/new/choose',
              target: '_blank',
              rel: null,
            },
            {
              label: "Contact us",
              href: 'https://webforj.com/schedule-discovery/',
              target: '_blank',
              rel: null,
            }
          ]
        },
        {
          type: 'html',
          position: 'right',
          value: '<div class="separator" aria-hidden></div>',
        },
        {
          href: 'https://github.com/webforj',
          position: 'right',
          className: "header-github-link"
        },
      ],
    },
    docs: {
      sidebar: {
        hideable: false,
        autoCollapseCategories: false
      }
    },
    footer: {
      links: [
        {
          html: `
                <p>Copyright © <script>document.write(/\d{4}/.exec(Date())[0])</script> <a href='https://basis.cloud/contact/'> BASIS International Ltd.</a> All rights reserved.</p>
                `
        }
      ],
    },
    prism: {
      theme: lightCodeTheme,
      darkTheme: darkCodeTheme,
      additionalLanguages: ['java', 'Ini', 'bash', 'powershell']
    },
  }
};

module.exports = config;
