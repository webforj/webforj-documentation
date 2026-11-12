import React from 'react';
import MDXContent from '@theme-original/MDXContent';
import GiscusComments from '@site/src/components/DocsTools/GiscusComments';
import PostCta, { useBlogPostSafe, useDocCta } from '@site/src/components/PostCta';
import { ExploreBottom } from '@site/src/components/explore/ExploreCard';

export default function MDXContentWrapper(props) {
  const frontMatter = props.children.type.metadata.frontMatter;
  // comments only on blog posts, never on docs pages
  const post = useBlogPostSafe();
  const showComments = Boolean(post?.isBlogPostPage) && !frontMatter.hide_giscus_comments;
  // contact box at the end of docs pages listed in PostCta/docsTopics.js
  const docCta = useDocCta();
  return (
    <>
      <MDXContent {...props} />
      {/* the contact box comes straight after the last section, before the
          related posts, so it is the first thing a reader meets when done */}
      {docCta && docCta.position === 'bottom' && <PostCta topicKey={docCta.topic} />}
      {/* video and related posts */}
      <ExploreBottom />
      {showComments && <GiscusComments />}
    </>
  );
}
