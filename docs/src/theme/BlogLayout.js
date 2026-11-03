import React, { useEffect, useLayoutEffect } from 'react';
import BlogLayout from '@theme-original/BlogLayout';
import BlogFilterBar from '@site/src/components/BlogFilterBar';
import { useLocation } from '@docusaurus/router';
import { HtmlClassNameProvider } from '@docusaurus/theme-common';
import {
  installBlogViewTransitions,
  blogPageRendered,
} from '@site/src/components/blogViewTransitions';

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
        {showFilterBar && <BlogFilterBar />}
        {children}
      </BlogLayout>
    </HtmlClassNameProvider>
  );
}
