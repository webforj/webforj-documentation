/**
 * Single source of truth for all curated content on the webforJ docs home page.
 * Update this file when new blog posts, projects, or recent docs are added.
 *
 * No React or JSX — plain data only. Content authors only need to edit this file.
 */

export const recentPosts = [
  {
    title: 'A year of webforJ, in one video',
    slug: 'webforj-2025-highlights',
    description:
      'A short walkthrough of the biggest features from the 25.x cycle and the Design System 2.0 refresh in 26.00.',
    date: 'May 7, 2026',
    tags: ['community', 'release'],
    image:
      'https://cdn.webforj.com/webforj-documentation/blogs/webforj-v26.00/video-cover.jpg',
    author: 'Matthew Hawkins',
  },
  {
    title: 'Building a Full-Featured Demo with webforJ and Spring Boot',
    slug: 'webforj-bookstore',
    description:
      'A walkthrough of the webforJ Bookstore — a full-stack inventory manager with live filtering, data binding, and Spring Security.',
    date: 'April 30, 2026',
    tags: ['showcase'],
    image:
      'https://cdn.webforj.com/webforj-documentation/blogs/2026-04-24-webforj-bookstore/screenshots/webforj-bookstore-cover.png',
    author: 'Eric Handtke',
  },
  {
    title: 'Animated Transitions, No JavaScript Required',
    slug: 'view-transitions',
    description:
      "How webforJ's Transitions API brings the browser's View Transition API to Java, with zero JavaScript required.",
    date: 'April 13, 2026',
    tags: ['tutorial'],
    image:
      'https://cdn.webforj.com/webforj-documentation/blogs/2026-04-03-view-transitions/blog-view-transitions-cover.png',
    author: 'Lauren Alamo',
  },
];

export const builtWithProjects = [
  {
    name: 'PingPal',
    winner: true,
    description:
      'A Postman-inspired REST API tester built entirely in Java — no JavaScript, no build tools required.',
    tags: ['REST client', 'Hackathon Winner'],
    demoUrl: 'https://www.pingpal.dev/',
    sourceUrl: 'https://github.com/hyyan/pingpal',
  },
  {
    name: 'webforj-bookstore',
    winner: false,
    description:
      'A full-stack book inventory manager with live table filtering, column renderers, and Spring Security auth.',
    tags: ['Spring Boot', 'Spring Security', 'data binding'],
    demoUrl: null,
    sourceUrl: 'https://github.com/webforj/webforj-bookstore',
  },
  {
    name: 'webforj-ghostai',
    winner: false,
    description:
      'An AI-powered chat interface using MarkdownViewer and Spring AI for real-time streaming responses.',
    tags: ['Spring AI', 'MarkdownViewer', 'streaming'],
    demoUrl: null,
    sourceUrl: 'https://github.com/webforj/webforj-ghostai',
  },
  {
    name: 'webforj-dashboard',
    winner: false,
    description:
      'A live cryptocurrency dashboard with real-time price charts powered by the Google Charts integration.',
    tags: ['Google Charts', 'data visualization', 'real-time'],
    demoUrl: 'https://docs.webforj.com/dashboard/',
    sourceUrl: 'https://github.com/webforj/webforj-dashboard',
  },
  {
    name: 'webforj-crud',
    winner: false,
    description:
      'A Spring Boot + JPA CRUD application showcasing form binding, validation, and dialog-based editing.',
    tags: ['Spring Boot', 'JPA', 'CRUD'],
    demoUrl: null,
    sourceUrl: 'https://github.com/webforj/webforj-crud',
  },
  {
    name: 'webforj-focustracker',
    winner: false,
    description:
      'A PWA-enabled Pomodoro focus timer with desktop notifications built on the webforJ component library.',
    tags: ['PWA', 'desktop notifications'],
    demoUrl: 'https://docs.webforj.com/focustracker/',
    sourceUrl: 'https://github.com/webforj/webforj-focustracker',
  },
  {
    name: 'webforj-todo',
    winner: false,
    description:
      'A clean MVC todo application demonstrating Spring Boot integration and first-class routing patterns.',
    tags: ['MVC', 'Spring Boot', 'routing'],
    demoUrl: 'https://docs.webforj.com/todo/',
    sourceUrl: 'https://github.com/webforj/webforj-todo',
  },
  {
    name: 'startforJ',
    winner: false,
    description:
      'An in-browser project scaffolder that generates new webforJ applications from configurable templates.',
    tags: ['scaffolding', 'developer tooling'],
    demoUrl: 'https://docs.webforj.com/startforj/',
    sourceUrl: 'https://github.com/webforj/startforJ',
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
