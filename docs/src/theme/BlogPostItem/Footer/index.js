import React from 'react';
import Footer from '@theme-original/BlogPostItem/Footer';
import Link from '@docusaurus/Link';
import Translate, { translate } from '@docusaurus/Translate';
import BlogShare from '@site/src/components/BlogShare';
import ConnectIcons from '@site/src/components/connect/ConnectIcons';
import { CONTACT_URL, FOLLOW, RSS } from '@site/src/components/connect/links';
import { contactMailto } from '@site/src/components/PostCta';
import { FaArrowRight, FaEnvelope } from 'react-icons/fa6';
import { useBlogPost } from '@docusaurus/plugin-content-blog/client';
import { useCoverSrc } from '@site/src/components/blogCover';
import styles from './styles.module.css';

function RelatedCard({ post }) {
  const src = useCoverSrc(post.cover);
  return (
    <Link to={post.permalink} className={styles.window}>
      <span className={styles.bar}>
        <span className={styles.dots} aria-hidden="true" />
        <span className={styles.file}>~{post.permalink}.md</span>
      </span>
      {src && <img className={styles.cover} src={src} alt={post.title} loading="lazy" />}
      <span className={styles.body}>
        <span className={styles.title}>{post.title}</span>
        {post.description && <span className={styles.excerpt}>{post.description}</span>}
        <span className={styles.meta}>{post.formattedDate}</span>
      </span>
    </Link>
  );
}

// Posts that share the most tags with this one, computed at build time
// (processBlogPosts in docusaurus.config.js)
function Related({ posts }) {
  if (!posts?.length) {
    return null;
  }
  return (
    <section className={styles.related} aria-labelledby="keep-reading">
      <h2 id="keep-reading" className={styles.label}>
        <Translate id="component.blogPostFooter.keepReading" description="Heading of the related posts section">
          Keep reading
        </Translate>
      </h2>
      <div className={styles.relatedGrid}>
        {posts.map((post) => (
          <RelatedCard key={post.permalink} post={post} />
        ))}
      </div>
    </section>
  );
}

// Following webforJ, right under the share buttons
function Follow() {
  return (
    <div className={styles.follow}>
      <span className={styles.label}>
        <Translate id="component.blogPostFooter.follow" description="Label before the follow icons">
          Follow
        </Translate>
      </span>
      <ConnectIcons links={[...FOLLOW, RSS]} />
    </div>
  );
}

// The invitation to talk, where a reader who finished the post decides
function TalkToUs() {
  return (
    <section className={styles.talk} aria-labelledby="talk-to-us">
      <div>
        <h2 id="talk-to-us" className={styles.talkTitle}>
          <Translate id="component.blogPostFooter.talkTitle" description="Heading of the contact invitation">
            Building something with webforJ?
          </Translate>
        </h2>
        <p className={styles.talkText}>
          <Translate id="component.blogPostFooter.talkText" description="Text of the contact invitation">
            Talk to the team about your project, a migration, or whether webforJ fits.
          </Translate>
        </p>
      </div>
      <div className={styles.talkActions}>
        <a className={styles.talkButton} href={CONTACT_URL} target="_blank" rel="noopener noreferrer">
          <Translate id="component.blogPostFooter.bookCall" description="Button label: book a discovery call">
            Book a call
          </Translate>{' '}
          <FaArrowRight aria-hidden="true" />
        </a>
        <a className={styles.talkEmail} href={contactMailto('Question about webforJ')}>
          <FaEnvelope aria-hidden="true" />{' '}
          <Translate id="component.blogPostFooter.askByEmail" description="Email contact link label">
            Ask by email
          </Translate>
        </a>
      </div>
    </section>
  );
}

export default function FooterWrapper(props) {
  const { metadata, isBlogPostPage } = useBlogPost();
  return (
    <>
      <Footer {...props} />
      {isBlogPostPage && (
        <>
          <BlogShare
            title={metadata.title}
            description={metadata.description}
            permalink={metadata.permalink}
          />
          <Follow />
          <TalkToUs />
          <Related posts={metadata.relatedPosts} />
        </>
      )}
    </>
  );
}
