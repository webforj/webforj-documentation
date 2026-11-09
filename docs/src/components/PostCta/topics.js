// Topics for the box shown after the intro of every blog post.
//
// The box picks the FIRST topic whose `tags` include one of the post's tags,
// so order matters: put the more specific topics higher. Tags are matched by
// their label in blog/tags.yml, lowercased (for example "Spring AI" -> "spring ai").
//
// To add a topic, copy one entry and change:
//   key      short id, also used in the translation ids
//   tags     tag labels (lowercase) that select this topic
//   title    the bold line
//   text     the line under it
//   subject  subject of the "Ask by email" message
//   buttons  the two buttons, in order: 'call' (Book a call), 'start' (Start your
//            app, opens startforJ), 'email' (Ask by email) or 'screenshot' (Send a
//            screenshot: an email asking the reader to attach one screen). Ready-to-talk topics
//            use ['call', 'email'], hands-on topics ['start', 'email'].
//
// Order: release first, then specific topics, then the blog categories
// (components, integrations, tutorial, community). The last entry has no tags
// and is used when nothing else matches.
const TOPICS = [
  // Release notes come first: they usually carry the ai tag too.
  {
    key: 'release',
    tags: ['release'],
    title: 'Planning an upgrade?',
    text: 'Ask the team what this release changes for your app.',
    subject: 'Upgrading webforJ',
  },
  {
    key: 'modernization',
    tags: ['modernization', 'swing', 'webswing'],
    title: 'Moving a desktop Java app to the web?',
    text: "Send us a screenshot of one screen and we'll reply with how it moves to webforJ.",
    subject: 'My app screen: how would it move to webforJ?',
    buttons: ['screenshot', 'call'],
  },
  {
    key: 'ai',
    tags: ['ai'],
    title: 'Using AI to build Java apps?',
    text: 'Ask the team about webforJ AI tooling: the MCP server, Agent Skills, and how they keep generated code correct.',
    subject: 'AI and webforJ',
  },
  {
    key: 'security',
    tags: ['security'],
    title: 'Securing a Java web app?',
    text: 'Walk the team through your login, roles, and access rules.',
    subject: 'Securing an app with webforJ',
  },
  {
    key: 'data',
    tags: ['table', 'data visualization'],
    title: 'Building data-heavy screens?',
    text: 'Show the team your tables, grids, and charts, and get a second opinion.',
    subject: 'Data-heavy screens with webforJ',
    buttons: ['start', 'email'],
  },
  {
    key: 'styling',
    tags: ['styling', 'themes'],
    title: 'Matching webforJ to your brand?',
    text: 'Ask the team about themes, tokens, and your design system.',
    subject: 'Styling and theming with webforJ',
    buttons: ['start', 'email'],
  },
  {
    key: 'spring',
    tags: ['spring', 'spring ai'],
    title: 'Already running Spring Boot?',
    text: 'Ask the team how webforJ fits into your existing Spring app.',
    subject: 'webforJ with Spring Boot',
  },
  // Categories: every post has one, so these catch the rest.
  {
    key: 'components',
    tags: ['components'],
    title: "Need a component that isn't covered here?",
    text: 'Ask the team what fits your screen.',
    subject: 'Components in webforJ',
    buttons: ['start', 'email'],
  },
  {
    key: 'integrations',
    tags: ['integrations'],
    title: 'Connecting webforJ to your stack?',
    text: 'Ask the team about your libraries, services, and build.',
    subject: 'Integrating webforJ',
    buttons: ['start', 'email'],
  },
  {
    key: 'tutorial',
    tags: ['tutorial'],
    title: 'Trying this in your own app?',
    text: "Ask the engineers when something doesn't fit your case.",
    subject: 'Question about a webforJ tutorial',
    buttons: ['start', 'email'],
  },
  {
    key: 'community',
    tags: ['community'],
    title: 'Want to see webforJ on your project?',
    text: 'Book a call and talk it through with the team.',
    subject: 'Talking to the webforJ team',
  },
  // Used by docs pages only (see docsTopics.js), so it has no tags.
  {
    key: 'evaluate',
    tags: [],
    title: 'Evaluating webforJ for a project?',
    text: 'Talk to the team about your app, your team, and whether webforJ fits.',
    subject: 'Evaluating webforJ for a project',
  },
  {
    key: 'build',
    tags: [],
    title: 'Working on something similar?',
    text: 'Ask the engineers behind webforJ. Questions are welcome, big or small.',
    subject: 'Question about webforJ',
  },
];

export default TOPICS;
