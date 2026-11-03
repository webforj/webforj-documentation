import React from 'react';
import Header from '@theme-original/BlogPostItem/Header';
import Link from '@docusaurus/Link';
import Translate, { translate } from '@docusaurus/Translate';
import { useBlogPost } from '@docusaurus/plugin-content-blog/client';
import BlogShare from '@site/src/components/BlogShare';
import styles from './styles.module.css';

// On a post page, a way back to the blog sits above the title, and the share
// rail stands in the left margin on wide screens
export default function HeaderWrapper(props) {
  const { metadata, isBlogPostPage } = useBlogPost();
  return (
    <>
      {isBlogPostPage && (
        <>
          <Link to="/blog" className={styles.back}>
            <Translate id="component.blogPostHeader.back" description="Link back to the blog list above a post title">
              ← Blog
            </Translate>
          </Link>
          <BlogShare
            title={metadata.title}
            description={metadata.description}
            permalink={metadata.permalink}
            variant="rail"
          />
        </>
      )}
      <Header {...props} />
    </>
  );
}
