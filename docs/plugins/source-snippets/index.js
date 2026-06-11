const path = require('path');
const { collectSnippets } = require('./extract');

module.exports = function sourceSnippetsPlugin(context, options = {}) {
  const repoRoot = path.resolve(context.siteDir, '..');

  return {
    name: 'webforj-source-snippets',

    getPathsToWatch() {
      return [
        path.join(context.siteDir, 'docs', '**', '*.{md,mdx}'),
        path.join(context.siteDir, 'blog', '**', '*.{md,mdx}'),
        path.join(repoRoot, 'src', 'main', 'java', '**', '*.java'),
      ];
    },

    async loadContent() {
      return collectSnippets(context.siteDir, {
        ...options,
        repoRoot,
      });
    },

    async contentLoaded({ content, actions }) {
      actions.setGlobalData({
        snippets: content.snippets,
      });
    },
  };
};
