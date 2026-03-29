import React from 'react';
import BlogLayout from '@theme-original/BlogLayout';
import BlogFilterBar from '@site/src/components/BlogFilterBar';
import { useLocation } from '@docusaurus/router';

export default function BlogLayoutWrapper({ children, ...props }) {
  const { pathname } = useLocation();

  const showFilterBar =
    pathname === '/blog' ||
    /^\/blog\/page\//.test(pathname) ||
    pathname.startsWith('/blog/tags/');

  return (
    <BlogLayout {...props}>
      {showFilterBar && <BlogFilterBar />}
      {children}
    </BlogLayout>
  );
}
