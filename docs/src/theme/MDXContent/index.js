import React from 'react';
import MDXContent from '@theme-original/MDXContent';
import GiscusComments from '@site/src/components/DocsTools/GiscusComments';
import PostCta, { useDocCta } from '@site/src/components/PostCta';

export default function MDXContentWrapper(props) {
  const hideComment = props.children.type.metadata.frontMatter.hide_giscus_comments;
  // contact box at the end of docs pages listed in PostCta/docsTopics.js
  const docCta = useDocCta();
  return (
    <>
      <MDXContent {...props} />
      {docCta && docCta.position === 'bottom' && <PostCta topicKey={docCta.topic} />}
      {!hideComment && <GiscusComments />}
    </>
  );
}
