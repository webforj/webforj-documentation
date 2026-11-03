import React from 'react';
import BlogTagsPostsPage from '@theme-original/BlogTagsPostsPage';
import { BlogListingCountProvider } from '@site/src/components/BlogListingCount';

export default function BlogTagsPostsPageWrapper(props) {
  return (
    <BlogListingCountProvider value={{ count: props.tag.count, unit: 'post' }}>
      <BlogTagsPostsPage {...props} />
    </BlogListingCountProvider>
  );
}
