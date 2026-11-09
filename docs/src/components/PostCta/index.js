import React from 'react';
import { useBlogPost } from '@docusaurus/plugin-content-blog/client';
import { useDoc } from '@docusaurus/plugin-content-docs/client';
import { translate } from '@docusaurus/Translate';
import { FaArrowRight, FaEnvelope } from 'react-icons/fa6';
import { CONTACT_URL, CONTACT_EMAIL } from '@site/src/components/connect/links';
import TOPICS from './topics';
import DOCS_CTA from './docsTopics';
import styles from './styles.module.css';

// startforJ, the project generator (same link as "Start your app" in the navbar)
const START_URL = 'https://docs.webforj.com/startforj/';

// Prefilled text for the "Send a screenshot" email
const SCREENSHOT_NOTE = 'Attached is a screenshot of one screen from our app. It is built with: (Swing, JavaFX, other)';

// The blog post context exists only on blog pages. Outside them useBlogPost
// throws, so this returns null there. The hook is always called once per
// render, so the call order stays stable.
function useBlogPostSafe() {
  try {
    return useBlogPost();
  } catch (e) {
    return null;
  }
}

// Same as useBlogPostSafe, for docs pages.
function useDocSafe() {
  try {
    return useDoc();
  } catch (e) {
    return null;
  }
}

// The docs rule for the current page (topic key and position), or null when
// the page isn't listed in docsTopics.js or opts out with hide_cta.
export function useDocCta() {
  const doc = useDocSafe();
  if (!doc || doc.frontMatter?.hide_cta) return null;
  const id = doc.metadata?.id || '';
  const rule = DOCS_CTA.find((r) => id.startsWith(r.path));
  return rule && rule.topic ? { ...rule, toc: doc.toc } : null;
}

// Blog: the post's tags when this heading is the post's first H2, so the box
// renders once, right after the intro.
// Docs: the topic key when the page's rule says 'top' and this is its first H2.
export function useShowPostCta(as, id) {
  const post = useBlogPostSafe();
  const docCta = useDocCta();
  if (as !== 'h2' || !id) return null;
  if (post && post.isBlogPostPage) {
    const first = (post.toc || []).find((t) => t.level === 2);
    return first && first.id === id ? { tags: post.metadata.tags } : null;
  }
  if (docCta && docCta.position === 'top') {
    const first = (docCta.toc || []).find((t) => t.level === 2);
    return first && first.id === id ? { topicKey: docCta.topic } : null;
  }
  return null;
}

// First topic whose tags match one of the post's tags. Topics live in topics.js.
function pickTopic(tags) {
  const labels = new Set((tags || []).map((t) => (t.label || '').toLowerCase()));
  return TOPICS.find((topic) => topic.tags.some((tag) => labels.has(tag))) || TOPICS[TOPICS.length - 1];
}

// Email link with the subject filled in and the page the reader was on.
export function contactMailto(subject, note = '') {
  const page = typeof window !== 'undefined' ? window.location.href : '';
  const body = `Hi webforJ team,\n\n${note}\n\n(I was reading: ${page})`;
  return `mailto:${CONTACT_EMAIL}?subject=${encodeURIComponent(subject)}&body=${encodeURIComponent(body)}`;
}

export default function PostCta({ tags, topicKey }) {
  const topic = (topicKey && TOPICS.find((t) => t.key === topicKey)) || pickTopic(tags);
  const title = translate({ id: `component.postCta.${topic.key}.title`, message: topic.title });
  const text = translate({ id: `component.postCta.${topic.key}.text`, message: topic.text });

  return (
    <div className={styles.cta} role="complementary" aria-label={title}>
      <div className={styles.copy}>
        <p className={styles.title}>{title}</p>
        <p className={styles.text}>{text}</p>
      </div>
      <div className={styles.actions}>
        {(topic.buttons || ['call', 'email']).map((b, i) => {
          const cls = i === 0 ? styles.primary : styles.secondary;
          if (b === 'start') {
            return (
              <a key={b} className={cls} href={START_URL} target="_blank" rel="noopener noreferrer">
                {translate({ id: 'component.postCta.startApp', message: 'Start your app' })} <FaArrowRight aria-hidden="true" />
              </a>
            );
          }
          if (b === 'screenshot') {
            return (
              <a key={b} className={cls} href={contactMailto(topic.subject, SCREENSHOT_NOTE)}>
                <FaEnvelope aria-hidden="true" /> {translate({ id: 'component.postCta.screenshot', message: 'Send a screenshot' })}
              </a>
            );
          }
          if (b === 'email') {
            return (
              <a key={b} className={cls} href={contactMailto(topic.subject)}>
                <FaEnvelope aria-hidden="true" /> {translate({ id: 'component.postCta.email', message: 'Ask by email' })}
              </a>
            );
          }
          return (
            <a key={b} className={cls} href={CONTACT_URL} target="_blank" rel="noopener noreferrer">
              {translate({ id: 'component.postCta.bookCall', message: 'Book a call' })} <FaArrowRight aria-hidden="true" />
            </a>
          );
        })}
      </div>
    </div>
  );
}
