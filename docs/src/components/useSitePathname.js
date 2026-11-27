import { useLocation } from '@docusaurus/router';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';

// The current path without the locale prefix, so `/es/blog/page/2` reads as
// `/blog/page/2` and path checks behave the same in every language
export default function useSitePathname() {
  const { pathname } = useLocation();
  const { baseUrl } = useDocusaurusContext().siteConfig;
  if (baseUrl !== '/' && pathname.startsWith(baseUrl)) {
    return `/${pathname.slice(baseUrl.length)}`;
  }
  return pathname;
}
