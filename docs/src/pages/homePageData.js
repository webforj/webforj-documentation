/**
 * Single source of truth for all curated content on the webforJ docs home page.
 * Update this file when new announcements, projects, or recent docs are added.
 *
 * No React or JSX — plain data only. Content authors only need to edit this file.
 */

export const announcements = [
  {
    date: 'May 2026',
    title: 'webforJ 26.00 — Design System 2.0',
    description:
      'New design tokens, View Transitions API, MCP Server integration, and over 40 component improvements.',
    link: '/docs/upgrading/webforj-26.00',
  },
  {
    date: 'Nov 2025',
    title: 'webforJ 25.11 — Spring AI & MarkdownViewer',
    description:
      'Built-in Spring AI integration, streaming MarkdownViewer, and chat memory support.',
    link: '/docs/upgrading/overview',
  },
  {
    date: 'Oct 2025',
    title: 'webforJ 25.10 — PWA Support',
    description:
      'Installable PWA apps, desktop notifications, and App Badge API — all from pure Java.',
    link: '/docs/upgrading/overview',
  },
];

export const componentShowcase = [
  {
    name: 'Table',
    description: 'High-performance data grid with sorting, filtering, and custom cell renderers.',
    path: '/docs/components/table/overview',
  },
  {
    name: 'Dialog',
    description: 'Accessible modal dialogs with configurable footers and drag-to-move support.',
    path: '/docs/components/dialog',
  },
  {
    name: 'MarkdownViewer',
    description: 'Render and stream Markdown with syntax highlighting — perfect for AI interfaces.',
    path: '/docs/components/markdownviewer',
  },
  {
    name: 'Tree',
    description: 'Hierarchical tree with lazy loading, multi-select, and expand/collapse.',
    path: '/docs/components/tree',
  },
];

export const builtWithProjects = [
  {
    name: 'PingPal',
    winner: true,
    description:
      'A Postman-inspired API testing tool built by Rick van Baalen. Features authentication support, custom headers, formatted responses, and request history.',
    tags: ['API testing', 'developer tools'],
    demoUrl: null,
    sourceUrl: 'https://github.com/webforj/built-with-webforj/tree/main/pingpal',
  },
  {
    name: 'webforj-ghostai',
    winner: false,
    description:
      'An AI chat demo built with Spring AI and webforJ. Features streaming markdown rendering, predictive text input, chat memory, and MCP integration with Google Gemini.',
    tags: ['Spring AI', 'MarkdownViewer', 'streaming'],
    demoUrl: null,
    sourceUrl: 'https://github.com/webforj/built-with-webforj/tree/main/webforj-ghostai',
  },
  {
    name: 'webforj-bookstore',
    winner: false,
    description:
      'A book inventory manager with role-based access control via Spring Security, genre chips, full-text search, and sortable tables with custom renderers.',
    tags: ['Spring Boot', 'Spring Security', 'CRUD'],
    demoUrl: null,
    sourceUrl: 'https://github.com/webforj/built-with-webforj/tree/main/webforj-bookstore',
  },
  {
    name: 'webforj-dashboard',
    winner: false,
    description:
      'A cryptocurrency dashboard showcasing the Google Charts integration with dark/light theme support, portfolio analytics, and a live news feed.',
    tags: ['Google Charts', 'data visualization'],
    demoUrl: 'https://docs.webforj.com/dashboard',
    sourceUrl: 'https://github.com/webforj/built-with-webforj/tree/main/webforj-dashboard',
  },
  {
    name: 'webforj-focustracker',
    winner: false,
    description:
      'A focus timer demonstrating PWA capabilities: installable app support, desktop notifications, and dynamic app icon badges — all from pure Java.',
    tags: ['PWA', 'desktop notifications'],
    demoUrl: null,
    sourceUrl: 'https://github.com/webforj/built-with-webforj/tree/main/webforj-focustracker',
  },
  {
    name: 'webforj-explorer',
    winner: false,
    description:
      'A minimal code viewer mimicking the VSCode layout — file tree on the left, Monaco editor on the right — showing how to integrate third-party components.',
    tags: ['Monaco editor', 'layout system'],
    demoUrl: null,
    sourceUrl: 'https://github.com/webforj/built-with-webforj/tree/main/webforj-explorer',
  },
  {
    name: 'webforj-rest',
    winner: false,
    description:
      'Side-by-side comparison of CollectionRepository and DelegatingRepository patterns for paginated REST data, with a customer management interface.',
    tags: ['REST', 'Spring Boot', 'pagination'],
    demoUrl: null,
    sourceUrl: 'https://github.com/webforj/built-with-webforj/tree/main/webforj-rest',
  },
  {
    name: 'webforj-crud',
    winner: false,
    description:
      'A music artist management system demonstrating Spring Data repositories, JPA entities, validation, and automatic data binding with webforJ.',
    tags: ['Spring Boot', 'JPA', 'CRUD'],
    demoUrl: null,
    sourceUrl: 'https://github.com/webforj/built-with-webforj/tree/main/webforj-crud',
  },
  {
    name: 'webforj-todo',
    winner: false,
    description:
      'A todo app built with Spring Boot and MVC architecture. Features full CRUD, smart filtering, real-time state updates, and H2 database persistence.',
    tags: ['Spring Boot', 'MVC'],
    demoUrl: null,
    sourceUrl: 'https://github.com/webforj/built-with-webforj/tree/main/webforj-todo',
  },
  {
    name: 'webforj-tictactoe',
    winner: false,
    description:
      'A two-player Tic-Tac-Toe game using webforJ namespaces to share game state between running instances — no WebSockets or REST API needed.',
    tags: ['namespaces', 'real-time'],
    demoUrl: 'https://docs.webforj.com/tictactoe/',
    sourceUrl: 'https://github.com/webforj/built-with-webforj/tree/main/webforj-tictactoe',
  },
  {
    name: 'webforj-howdy',
    winner: false,
    description:
      'A demonstration project showcasing routing, Google Charts integration, and a responsive UI — a practical starting point for learning webforJ core concepts.',
    tags: ['routing', 'Google Charts'],
    demoUrl: 'https://docs.webforj.com/howdy/you',
    sourceUrl: 'https://github.com/webforj/built-with-webforj/tree/main/webforj-howdy',
  },
];

/** @type {{ title: string; path: string; section: string }[]} */
export const recentDocs = [
  { title: 'Tree', path: '/docs/components/tree', section: 'Component' },
  {
    title: 'MarkdownViewer',
    path: '/docs/components/markdownviewer',
    section: 'Component',
  },
  {
    title: 'Refresher',
    path: '/docs/components/refresher',
    section: 'Component',
  },
  {
    title: 'InfiniteScroll',
    path: '/docs/components/infinitescroll',
    section: 'Component',
  },
  { title: 'Avatar', path: '/docs/components/avatar', section: 'Component' },
  {
    title: 'View Transitions',
    path: '/docs/advanced/view-transitions',
    section: 'Advanced',
  },
];

/** @type {typeof builtWithProjects} Three hand-picked projects shown on the homepage. */
export const featuredProjects = [
  {
    name: 'PingPal',
    winner: true,
    description:
      'A Postman-inspired API testing tool built by Rick van Baalen. Features authentication support, custom headers, formatted responses, and request history.',
    tags: ['API testing', 'developer tools'],
    demoUrl: null,
    sourceUrl: 'https://github.com/webforj/built-with-webforj/tree/main/pingpal',
  },
  {
    name: 'webforj-ghostai',
    winner: false,
    description:
      'An AI chat demo built with Spring AI and webforJ. Features streaming markdown rendering, predictive text input, chat memory, and MCP integration with Google Gemini.',
    tags: ['Spring AI', 'MarkdownViewer', 'streaming'],
    demoUrl: null,
    sourceUrl: 'https://github.com/webforj/built-with-webforj/tree/main/webforj-ghostai',
  },
  {
    name: 'webforj-dashboard',
    winner: false,
    description:
      'A cryptocurrency dashboard showcasing the Google Charts integration with dark/light theme support, portfolio analytics, and a live news feed.',
    tags: ['Google Charts', 'data visualization'],
    demoUrl: 'https://docs.webforj.com/dashboard',
    sourceUrl: 'https://github.com/webforj/built-with-webforj/tree/main/webforj-dashboard',
  },
];
