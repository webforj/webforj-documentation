import React, { useCallback, useEffect, useRef, useState } from 'react';
import Link from '@docusaurus/Link';
import { useLocation } from '@docusaurus/router';
import clsx from 'clsx';
import Translate, { translate } from '@docusaurus/Translate';
import { useBlogListingCount } from './BlogListingCount';
import styles from './BlogFilterBar.module.css';

function getCategories() {
  return [
    {
      label: translate({ id: 'component.blogFilterBar.all', message: 'All' }),
      path: '/blog',
    },
    {
      label: translate({ id: 'component.blogFilterBar.release', message: 'Release' }),
      path: '/blog/tags/release',
    },
    {
      label: translate({ id: 'component.blogFilterBar.ai', message: 'AI' }),
      path: '/blog/tags/ai',
    },
    {
      label: translate({ id: 'component.blogFilterBar.tutorial', message: 'Tutorial' }),
      path: '/blog/tags/tutorial',
    },
    {
      label: translate({ id: 'component.blogFilterBar.components', message: 'Components' }),
      path: '/blog/tags/components',
    },
    {
      label: translate({ id: 'component.blogFilterBar.integrations', message: 'Integrations' }),
      path: '/blog/tags/integrations',
    },
    {
      label: translate({ id: 'component.blogFilterBar.styling', message: 'Styling' }),
      path: '/blog/tags/styling',
    },
    {
      label: translate({ id: 'component.blogFilterBar.insights', message: 'Insights' }),
      path: '/blog/tags/insights',
    },
    {
      label: translate({ id: 'component.blogFilterBar.community', message: 'Community' }),
      path: '/blog/tags/community',
    },
  ];
}

// The command shown in the terminal line above the filter, echoing the
// current listing
function promptCommand(pathname) {
  const path = pathname.replace(/\/+$/, '');
  const tag = path.match(/^\/blog\/tags\/([^/]+)/);
  if (tag) {
    return `ls --tag=${tag[1]}`;
  }
  if (path === '/blog/tags') {
    return 'ls --tags';
  }
  const page = path.match(/^\/blog\/page\/(\d+)/);
  return page ? `ls --latest --page=${page[1]}` : 'ls --latest';
}

// The count printed as the output of the command, e.g. "12 posts"
function listingOutput({ count, unit }) {
  if (unit === 'tag') {
    return count === 1
      ? translate({ id: 'component.blogFilterBar.tags.one', message: '{count} tag' }, { count })
      : translate({ id: 'component.blogFilterBar.tags.other', message: '{count} tags' }, { count });
  }
  return count === 1
    ? translate({ id: 'component.blogFilterBar.posts.one', message: '{count} post' }, { count })
    : translate({ id: 'component.blogFilterBar.posts.other', message: '{count} posts' }, { count });
}

// Must match `blog.postsPerPage` in docusaurus.config.js
const POSTS_PER_PAGE = 12;

export default function BlogFilterBar() {
  const { pathname } = useLocation();
  const listing = useBlogListingCount();
  const categories = getCategories();
  const page = Number(pathname.match(/^\/blog\/page\/(\d+)/)?.[1] ?? 1);

  // on narrow screens the categories scroll sideways; each edge fades only
  // while there is more to scroll in that direction
  const trackRef = useRef(null);
  const [fade, setFade] = useState({ start: false, end: false });
  const updateFade = useCallback(() => {
    const track = trackRef.current;
    if (!track) {
      return;
    }
    const max = track.scrollWidth - track.clientWidth;
    setFade({ start: track.scrollLeft > 1, end: track.scrollLeft < max - 1 });
  }, []);

  useEffect(() => {
    const track = trackRef.current;
    if (!track) {
      return undefined;
    }
    // keep the selected category in view after navigating
    const active = track.querySelector(`.${styles.active}`);
    if (active && track.scrollWidth > track.clientWidth) {
      track.scrollLeft = active.offsetLeft - (track.clientWidth - active.offsetWidth) / 2;
    }
    updateFade();
    const observer = new ResizeObserver(updateFade);
    observer.observe(track);
    return () => observer.disconnect();
  }, [pathname, updateFade]);

  const isActive = (path) => {
    if (path === '/blog') {
      return !pathname.startsWith('/blog/tags/');
    }
    return pathname.startsWith(path);
  };

  return (
    <>
      {/* a command that has already run, with its output, so it reads as a
          log line rather than a field to type into */}
      {/* the issue numbers of the posts below continue across pages */}
      <p
        className={styles.prompt}
        style={{ counterReset: `blog-issue ${(page - 1) * POSTS_PER_PAGE}` }}>
        ~/webforj/blog $ {promptCommand(pathname)}
        {listing && (
          <span className={styles.output}>
            {' '}→ {listingOutput(listing)}
          </span>
        )}
        <span className={styles.caret} aria-hidden="true" />
        {' '}
        <a className={styles.rss} href="/blog/rss.xml" title={translate({
          id: 'component.blogFilterBar.rssTitle',
          message: 'Subscribe to the blog feed',
        })}>
          rss
        </a>
      </p>
      <div className={styles.filterBar}>
        <div
          ref={trackRef}
          onScroll={updateFade}
          className={clsx(styles.track, {
            [styles.fadeStart]: fade.start,
            [styles.fadeEnd]: fade.end,
          })}
        >
          {categories.map(({ label, path }) => (
            <Link
              key={label}
              to={path}
              className={clsx(styles.filterButton, {
                [styles.active]: isActive(path),
              })}
            >
              {label}
            </Link>
          ))}
        </div>
      </div>
    </>
  );
}
