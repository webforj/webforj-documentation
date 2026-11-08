// Docs pages that show the contact box, and where.
//
// Only pages where readers make decisions get the box: evaluation, upgrades,
// security, AI tooling, and integrations. Reference pages (components,
// styling, data binding, routing...) stay clean.
//
// Each rule matches doc ids by prefix (the path under docs/, without .md).
// The FIRST matching rule wins, so put specific paths above general ones.
//   path      doc id prefix, for example 'security/' or 'introduction/overview'
//   topic     key of a topic in topics.js, or null for no box on that page
//   position  'top'    = right before the page's first section heading
//             'bottom' = after the page content, before the comments
//
// A single page can opt out with `hide_cta: true` in its front matter.
const DOCS_CTA = [
  // hub pages (only cards, no sections): no box
  { path: 'introduction/overview', topic: null },
  { path: 'architecture/overview', topic: null },
  // how webforJ works: readers are still deciding, so the box comes early
  { path: 'architecture/client-server', topic: 'evaluate', position: 'top' },
  { path: 'introduction/basics', topic: 'evaluate', position: 'bottom' },
  { path: 'architecture/', topic: 'evaluate', position: 'bottom' },
  { path: 'upgrading/', topic: 'release', position: 'bottom' },
  { path: 'security/', topic: 'security', position: 'bottom' },
  { path: 'ai-tooling/', topic: 'ai', position: 'bottom' },
  // modernization: the overview is where teams decide, so the box comes early;
  // the tutorial and setup pages are hands-on, so it waits until the end
  { path: 'integrations/webswing/overview', topic: 'modernization', position: 'top' },
  { path: 'integrations/webswing', topic: 'modernization', position: 'bottom' },
  { path: 'integrations/spring', topic: 'spring', position: 'bottom' },
  { path: 'integrations/', topic: 'integrations', position: 'bottom' },
];

export default DOCS_CTA;
