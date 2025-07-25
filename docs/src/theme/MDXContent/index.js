import React from 'react';
import MDXContent from '@theme-original/MDXContent';
import GiscusComments from '@site/src/components/DocsTools/GiscusComments';
import ComboButton from '@site/src/components/DocsTools/ComboButton';

export default function MDXContentWrapper(props) {
  const hideComment = props.children.type.metadata.frontMatter.hide_giscus_comments;
  return (
    <>
      <MDXContent {...props} />
      {!hideComment && <GiscusComments />}
      <ComboButton />
    </>
  );
}
