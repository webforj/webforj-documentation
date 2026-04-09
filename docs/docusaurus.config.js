// @ts-check
const codeTheme = require('./src/theme/prism-dwc-theme');

/** Resolves the webforJ version, fetching from GitHub if the build version is a SNAPSHOT. */
async function resolveWebforjVersion() {
  const version = process.env.WEBFORJ_VERSION || 'latest';
  if (!version.toLowerCase().includes('snapshot')) {
    return version;
  }

  try {
    const res = await fetch(
      'https://api.github.com/repos/webforj/webforj/releases/latest',
      { headers: { 'User-Agent': 'webforj-docs' }, signal: AbortSignal.timeout(10000) }
    );
    const { tag_name } = await res.json();
    return tag_name ? tag_name.replace(/^v/, '') : version.replace(/-SNAPSHOT$/i, '');
  } catch {
    return version.replace(/-SNAPSHOT$/i, '');
  }
}

/** @returns {Promise<import('@docusaurus/types').Config>} */
module.exports = async function createConfig() {
  const webforjVersion = await resolveWebforjVersion();

  return {
  title: 'webforJ Documentation',
  tagline: 'webforJ is a robust and flexible web framework that allows you to easily create a modern and engaging user interface using Java.',
  url: 'https://docs.webforj.com/',
  baseUrl: '/',
  onBrokenAnchors: 'throw',
  favicon: 'https://docs.webforj.com/icons/icon.png',
  organizationName: 'webforj',
  projectName: 'webforj-docs',
  trailingSlash: false,
  customFields: {
    webforjVersion: `${webforjVersion}`,
  },
  i18n: {
    defaultLocale: 'en',
    locales: ['en', 'es', 'de', 'fr', 'nl', 'fi', 'zh'],
    localeConfigs: {
      en: {
        label: 'English',
        direction: 'ltr',
        htmlLang: 'en-US',
      },
      es: {
        label: 'Español',
        direction: 'ltr',
        htmlLang: 'es-ES',
      },
      de: {
        label: 'Deutsch',
        direction: 'ltr',
        htmlLang: 'de-DE',
      },
      fr: {
        label: 'Français',
        direction: 'ltr',
        htmlLang: 'fr-FR',
      },
      nl: {
        label: 'Nederlands',
        direction: 'ltr',
        htmlLang: 'nl-NL',
      },
      fi: {
        label: 'Suomi',
        direction: 'ltr',
        htmlLang: 'fi-FI',
      },
      zh: {
        label: '中文',
        direction: 'ltr',
        htmlLang: 'zh-CN',
      },
    },
  },
  scripts: [
    { src: '/js/dwc-theme-switcher.js', async: false },
    { src: '/js/link-decorator.js' },
    { src: '/js/style-startforj.js', defer: true},
    { src: '/js/dwc-doc-components.js', defer: true },
  ],
  headTags: [
    {
      tagName: 'link',
      attributes: {
        rel: 'stylesheet',
        href: 'https://cdn.webforj.com/next/dwc-ui.css',
      },
    },
    {
      tagName: 'link',
      attributes: {
        rel: 'stylesheet',
        href: '/css/dwc-doc-components.css',
      },
    },
    {
      tagName: 'link',
      attributes: {
        rel: 'preconnect',
        href: 'https://fonts.googleapis.com',
      },
    },
    {
      tagName: 'link',
      attributes: {
        rel: 'preconnect',
        href: 'https://fonts.gstatic.com',
        crossorigin: 'anonymous',
      },
    },
    {
      tagName: 'link',
      attributes: {
        rel: 'stylesheet',
        href: 'https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=JetBrains+Mono:wght@400;500;700&display=swap',
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
        blog: {
          postsPerPage: 12
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
          {
            from: '/docs/integrations/spring/dependency-injection',
            to: '/docs/integrations/spring/routing',
          },
          {
            from: '/docs/integrations/spring/setup',
            to: '/docs/integrations/spring/spring-boot',
          },
          {
            from: '/docs/advanced/deploying-additional-servlets',
            to: '/docs/configuration/deploying-additional-servlets',
          },
          {
            from: '/docs/integrations/spring/spring-devtools',
            to: '/docs/configuration/deploy-reload/spring-devtools'
          }
        ],
      },
    ]
  ],
  themes: ['@docusaurus/theme-mermaid'],
  markdown: {
    mermaid: true,
    hooks: {
      onBrokenMarkdownLinks: 'throw',
      onBrokenMarkdownImages: 'throw',
    },
  },
  themeConfig: {
    algolia: {
      appId: '826LUKOV8E',
      apiKey: 'a69d79113b838bfc8490ffb56cef78f2',
      indexName: 'umentation-webforj',
      contextualSearch: true,
      externalUrlRegex: '.*', // disables version filtering
      askAi: {
        assistantId: '0So3Fg39A7WH',
        appId: '826LUKOV8E',
        apiKey: 'a69d79113b838bfc8490ffb56cef78f2',
        indexName: 'umentation-webforj',
      },
    },
    announcementBar: {
      id: `v${webforjVersion}-release`,
      content:
        `We are excited to announce webforJ version ${webforjVersion} is live! Read more about the changes and features&nbsp;<a href=/blog/whats-new-v${webforjVersion}>here.</a>`,
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
          label: `v${webforjVersion}`,
          href: 'https://github.com/webforj/webforj/releases/latest',
          target: '_blank',
          id: 'webforj-version-badge',
          title: 'Latest webforJ release'
        },
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
            },
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
          label: 'Developers',
          position: 'right',
          items: [
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
          position: 'right',
          type: 'html',
          value: `
          <a aria-label="Start your app with startforJ" id="startforj-link" target="_blank" href="https://docs.webforj.com/startforj/" class="navbar__link">
            <div div aria-hidden="true" class="startforj-container">
              <span class="startforj-hover-text">startforJ</span>
              <span class="startforj-idle-text">Start your app</span>
           </div>
          </a>
          `,
        },
        {
          type: 'html',
          position: 'right',
          value: '<div class="separator" aria-hidden></div>',
        },
        {
          type: 'localeDropdown',
          position: 'right',
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
      theme: codeTheme,
      darkTheme: codeTheme,
      additionalLanguages: ['java', 'Ini', 'bash', 'powershell', 'groovy']
    },
  }
};
};
