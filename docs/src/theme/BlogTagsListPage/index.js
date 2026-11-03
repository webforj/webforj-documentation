import React from 'react';
import {
  PageMetadata,
  HtmlClassNameProvider,
  ThemeClassNames,
  translateTagsPageTitle,
} from '@docusaurus/theme-common';
import BlogLayout from '@theme/BlogLayout';
import SearchMetadata from '@theme/SearchMetadata';
import Heading from '@theme/Heading';
import Link from '@docusaurus/Link';
import Translate, { translate } from '@docusaurus/Translate';
import { BlogListingCountProvider } from '@site/src/components/BlogListingCount';
import styles from './styles.module.css';

function TagCard({ tag }) {
  return (
    <Link href={tag.permalink} className={styles.tagCard}>
      <span className={styles.tagLabel}>{tag.label}</span>
      <span className={styles.tagCount}>{tag.count}</span>
    </Link>
  );
}

export default function BlogTagsListPage({ tags, sidebar }) {
  const title = translateTagsPageTitle();
  const sortedTags = [...tags].sort((a, b) => b.count - a.count);

  return (
    <HtmlClassNameProvider
      className={`${ThemeClassNames.wrapper.blogPages} ${ThemeClassNames.page.blogTagsListPage}`}>
      <PageMetadata title={title} />
      <SearchMetadata tag="blog_tags_list" />
      <BlogListingCountProvider value={{ count: tags.length, unit: 'tag' }}>
      <BlogLayout sidebar={sidebar}>
        <Heading as="h1">{title}</Heading>
        <p className={styles.subtitle}>
          {tags.length === 1 ? (
            <Translate
              id="component.blogTagsListPage.subtitle.one"
              values={{ count: tags.length }}>
              {'Browse all {count} topic covered in the webforJ blog.'}
            </Translate>
          ) : (
            <Translate
              id="component.blogTagsListPage.subtitle.other"
              values={{ count: tags.length }}>
              {'Browse all {count} topics covered in the webforJ blog.'}
            </Translate>
          )}
        </p>
        <div className={styles.tagsGrid}>
          {sortedTags.map((tag) => (
            <TagCard key={tag.permalink} tag={tag} />
          ))}
        </div>
      </BlogLayout>
      </BlogListingCountProvider>
    </HtmlClassNameProvider>
  );
}
