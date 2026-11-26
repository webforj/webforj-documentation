import React, { useEffect, useLayoutEffect } from 'react';
import BlogLayout from '@theme-original/BlogLayout';
import BlogFilterBar from '@site/src/components/BlogFilterBar';
import { useLocation } from '@docusaurus/router';
import { HtmlClassNameProvider, PageMetadata } from '@docusaurus/theme-common';
import {
  installBlogViewTransitions,
  blogPageRendered,
} from '@site/src/components/blogViewTransitions';

const BLOG_DESCRIPTION =
  'News, tutorials, and guides from the webforJ team on building and modernizing business web apps in Java.';

// the blog home has no visible title, so its page heading is for screen
// readers and crawlers only
const visuallyHidden = {
  position: 'absolute',
  width: 1,
  height: 1,
  margin: -1,
  padding: 0,
  overflow: 'hidden',
  clip: 'rect(0, 0, 0, 0)',
  whiteSpace: 'nowrap',
  border: 0,
};

export default function BlogLayoutWrapper({ children, ...props }) {
  const { pathname } = useLocation();

  const showFilterBar =
    pathname === '/blog' ||
    /^\/blog\/page\//.test(pathname) ||
    pathname.startsWith('/blog/tags/');

  // only the first page of the blog leads with a featured post; older pages
  // are a plain grid
  const isBlogHome = /^\/blog\/?$/.test(pathname);

  // page transitions inside the blog: set up once, and tell a running
  // transition when the next blog page is on screen
  useEffect(() => {
    installBlogViewTransitions();
  }, []);
  useLayoutEffect(() => {
    blogPageRendered(pathname);
  }, [pathname]);

  return (
    <HtmlClassNameProvider className={isBlogHome ? 'blog-home' : undefined}>
      <BlogLayout {...props}>
        {isBlogHome && <h1 style={visuallyHidden}>webforJ Blog</h1>}
        {showFilterBar && <BlogFilterBar />}
        {children}
        {isBlogHome && <PageMetadata description={BLOG_DESCRIPTION} />}
      </BlogLayout>
    </HtmlClassNameProvider>
  );
}
