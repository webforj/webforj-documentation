import { translate } from '@docusaurus/Translate';

// Where readers can reach and follow webforJ. One place, used by the
// launcher, the side card, the end of posts and the footer.
export const CONTACT_URL = 'https://webforj.com/schedule-discovery/';
export const CONTACT_EMAIL = 'sales@basis.cloud';

// "action" is a getter so the text is translated when read during render,
// not when the module loads
export const FOLLOW = [
  {
    id: 'linkedin',
    label: 'LinkedIn',
    get action() {
      return translate({ id: 'component.connectLinks.followLinkedIn', message: 'Follow on LinkedIn' });
    },
    href: 'https://www.linkedin.com/company/webforj',
  },
  {
    id: 'youtube',
    label: 'YouTube',
    get action() {
      return translate({ id: 'component.connectLinks.subscribeYouTube', message: 'Subscribe on YouTube' });
    },
    href: 'https://www.youtube.com/@webforJ',
  },
  {
    id: 'github',
    label: 'GitHub',
    get action() {
      return translate({ id: 'component.connectLinks.starGitHub', message: 'Star on GitHub' });
    },
    href: 'https://github.com/webforj',
  },
];

export const RSS = {
  id: 'rss',
  label: 'RSS',
  get action() {
    return translate({ id: 'component.connectLinks.subscribeRss', message: 'Subscribe to the blog feed' });
  },
  href: '/blog/rss.xml',
};

// the follow links for a page: the blog adds its feed
export function followLinksFor(pathname) {
  return /^\/blog(\/|$)/.test(pathname) ? [...FOLLOW, RSS] : FOLLOW;
}
