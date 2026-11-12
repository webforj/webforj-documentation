// The navbar's menus, in navbar order. An entry is a plain navbar link
// ({ label, href, position }), or a mega menu ({ type: 'custom-megaMenu',
// position, label, menu }) whose panel content lives in
// src/components/megamenu/menus.js under the name given by `menu`.
//
// Labels here are translated through i18n/<locale>/docusaurus-theme-classic/
// navbar.json; the panel content through code.json. Both are filled by
// `npm run write-translations` and the translation workflow.

module.exports = [
  { type: 'custom-megaMenu', position: 'left', label: 'Framework', menu: 'products' },
  { type: 'custom-megaMenu', position: 'left', label: 'Developers', menu: 'developers' },
  { type: 'custom-megaMenu', position: 'left', label: 'Resources', menu: 'resources' },
  { type: 'custom-megaMenu', position: 'left', label: 'Support', menu: 'support' },
  {
    // a plain link to the showcase page on this site
    label: 'Showcase',
    to: '/showcase',
    position: 'left',
    className: 'navbar-plain-link navbar-showcase-link',
  },
  {
    // a plain link to the pricing page on webforj.com
    label: 'Pricing',
    href: 'https://webforj.com/pricing/',
    position: 'left',
    className: 'navbar-plain-link',
  },
];
