import React from 'react';
import clsx from 'clsx';
import Translate, { translate } from '@docusaurus/Translate';
import {
  BlueskyIcon,
  BlueskyShareButton,
  EmailIcon,
  EmailShareButton,
  LinkedinLegacyIcon,
  LinkedinShareButton,
  RedditIcon,
  RedditShareButton,
  XIcon,
  XShareButton,
} from 'react-share';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import styles from './BlogShare.module.css';

// The text a share carries: the post's title, a blank line, and its
// description, shortened with an ellipsis to stay within the network's limit
// (the link is added after it by the network)
function shareText(title, description, limit) {
  if (!description) {
    return title;
  }
  const full = `${title}\n\n${description}`;
  return full.length <= limit ? full : `${full.slice(0, limit - 1).trimEnd()}…`;
}

// Share buttons for a blog post, from react-share. The "row" variant ends a
// post; the "rail" variant stands in the left margin of a post on wide
// screens, pinned while it is read. X and Bluesky get the title and
// description as text, Reddit the title, email the title as subject and the
// description as body. LinkedIn takes only the link and builds its preview
// from the page itself.
export default function BlogShare({ title, description, permalink, variant = 'row' }) {
  const { siteConfig } = useDocusaurusContext();
  const url = new URL(permalink, siteConfig.url).href;
  const icon = { size: 32, round: true };

  const buttons = (
    <div className={clsx(styles.share, styles[variant])}>
      <span className={styles.label}>
        <Translate id="component.blogShare.label" description="Label before the share buttons">
          Share
        </Translate>
      </span>
      <LinkedinShareButton url={url} aria-label={translate({ id: 'component.blogShare.linkedin', message: 'Share on LinkedIn' })}>
        {/* the round variant, in LinkedIn's current blue, to match the others */}
        <LinkedinLegacyIcon {...icon} bgStyle={{ fill: '#0A66C2' }} />
      </LinkedinShareButton>
      <XShareButton
        url={url}
        text={shareText(title, description, 220)}
        hashtags={['webforJ', 'Java']}
        aria-label={translate({ id: 'component.blogShare.x', message: 'Share on X' })}>
        <XIcon {...icon} />
      </XShareButton>
      <RedditShareButton url={url} shareTitle={title} aria-label={translate({ id: 'component.blogShare.reddit', message: 'Share on Reddit' })}>
        <RedditIcon {...icon} />
      </RedditShareButton>
      <BlueskyShareButton
        url={url}
        text={shareText(title, description, 220)}
        separator={'\n\n'}
        aria-label={translate({ id: 'component.blogShare.bluesky', message: 'Share on Bluesky' })}>
        <BlueskyIcon {...icon} />
      </BlueskyShareButton>
      <EmailShareButton
        url={url}
        subject={title}
        body={description || title}
        separator={'\n\n'}
        aria-label={translate({ id: 'component.blogShare.email', message: 'Share by email' })}>
        <EmailIcon {...icon} />
      </EmailShareButton>
    </div>
  );

  // the rail runs down the post's left margin and sticks as the post scrolls,
  // starting level with the post like the table of contents
  return variant === 'rail' ? <div className={styles.railTrack}>{buttons}</div> : buttons;
}
