// The DWC tokens the design kit page draws live. Every name here is a real
// --dwc-* variable from the styling docs (colors, typography, sizing and
// spacing, borders, shadows, surfaces). The page renders each one with
// var() and reads the value the browser resolves, so it never holds a
// copy of a value that could drift from the product.

import { translate } from '@docusaurus/Translate';

export function getPalettes() {
  return [
    {
      name: 'primary',
      note: translate({ id: 'page.designKit.palette.primary', message: 'Your brand color' }),
    },
    {
      name: 'default',
      note: translate({
        id: 'page.designKit.palette.default',
        message: 'Neutral, tinted with the primary hue',
      }),
    },
    {
      name: 'success',
      note: translate({ id: 'page.designKit.palette.success', message: 'Status' }),
    },
    {
      name: 'warning',
      note: translate({ id: 'page.designKit.palette.warning', message: 'Status' }),
    },
    { name: 'danger', note: translate({ id: 'page.designKit.palette.danger', message: 'Status' }) },
    {
      name: 'info',
      note: translate({ id: 'page.designKit.palette.info', message: 'Secondary emphasis' }),
    },
    {
      name: 'gray',
      note: translate({ id: 'page.designKit.palette.gray', message: 'Pure gray, untinted' }),
    },
  ];
}

// 5 (darkest) to 95 (lightest) in steps of 5
export const STEPS = Array.from({ length: 19 }, (_, i) => (i + 1) * 5);

export const FONT_SIZES = ['3xs', '2xs', 'xs', 's', 'm', 'l', 'xl', '2xl', '3xl'];

export const FONT_WEIGHTS = [
  'thin',
  'lighter',
  'light',
  'normal',
  'medium',
  'semibold',
  'bold',
  'bolder',
  'black',
];

export const SPACES = ['3xs', '2xs', 'xs', 's', 'm', 'l', 'xl', '2xl', '3xl'];

export const SIZES = ['3xs', '2xs', 'xs', 's', 'm', 'l', 'xl', '2xl', '3xl'];

export const RADII = ['2xs', 'xs', 's', 'm', 'l', 'xl', '2xl', '3xl', '4xl'];

export const SHADOWS = ['xs', 's', 'm', 'l', 'xl', '2xl'];

export function getSurfaces() {
  return [
    {
      name: '--dwc-surface-1',
      usage: translate({
        id: 'page.designKit.surface.level1',
        message: 'Page and body background',
      }),
    },
    {
      name: '--dwc-surface-2',
      usage: translate({
        id: 'page.designKit.surface.level2',
        message: 'Toolbars, menubars, cards',
      }),
    },
    {
      name: '--dwc-surface-3',
      usage: translate({
        id: 'page.designKit.surface.level3',
        message: 'Windows, menus, popovers, dialogs',
      }),
    },
  ];
}
