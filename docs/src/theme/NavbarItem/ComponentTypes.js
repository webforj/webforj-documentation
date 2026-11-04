import ComponentTypes from '@theme-original/NavbarItem/ComponentTypes';
import MegaMenu from '@site/src/components/megamenu/MegaMenu';

// Adds the mega menu as a navbar item type: { type: 'custom-megaMenu' }
export default {
  ...ComponentTypes,
  'custom-megaMenu': MegaMenu,
};
