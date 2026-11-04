import React from 'react';
import BlogListPage from '@theme-original/BlogListPage';
import { BlogListingCountProvider } from '@site/src/components/BlogListingCount';

export default function BlogListPageWrapper(props) {
  return (
    <BlogListingCountProvider value={{ count: props.metadata.totalCount, unit: 'post' }}>
      <BlogListPage {...props} />
    </BlogListingCountProvider>
  );
}
