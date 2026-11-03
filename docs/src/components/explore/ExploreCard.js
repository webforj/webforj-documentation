import React, { useEffect, useState } from 'react';
import clsx from 'clsx';
import Link from '@docusaurus/Link';
import Translate, { translate } from '@docusaurus/Translate';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import { useLocation } from '@docusaurus/router';
import { usePluginData } from '@docusaurus/useGlobalData';
import { FaChevronLeft, FaChevronRight, FaPlay } from 'react-icons/fa6';
import { useCoverSrc } from '@site/src/components/blogCover';
import styles from './ExploreCard.module.css';

const VIDEOS = 3;
const POSTS = 3;

// Words of a page address that say what the page is about, singular and
// lowercase: /docs/components/button becomes ['component', 'button']
const IGNORED = new Set(['doc', 'docs', 'overview', 'introduction', 'the', 'and', 'with', 'for']);

function words(text) {
  return text
    .toLowerCase()
    .split(/[^a-z0-9]+/)
    .map((word) => word.replace(/s$/, ''))
    .filter((word) => word.length > 2 && !IGNORED.has(word));
}

// how many of the page's words a text mentions
function score(pageWords, text) {
  const own = new Set(words(text));
  return pageWords.filter((word) => own.has(word)).length;
}

// a shuffled copy (Fisher-Yates)
function shuffle(items) {
  const copy = [...items];
  for (let i = copy.length - 1; i > 0; i -= 1) {
    const j = Math.floor(Math.random() * (i + 1));
    [copy[i], copy[j]] = [copy[j], copy[i]];
  }
  return copy;
}

// Up to `count` items: the ones that match the page first (best first), then
// the rest in the given order of preference
function pick(pageWords, items, textOf, count, rest) {
  const chosen = items
    .map((item) => ({ item, value: score(pageWords, textOf(item)) }))
    .filter(({ value }) => value > 0)
    .sort((a, b) => b.value - a.value)
    .map(({ item }) => item);
  for (const item of rest) {
    if (chosen.length >= count) break;
    if (!chosen.includes(item)) chosen.push(item);
  }
  return chosen.slice(0, count);
}

// The suggestions for a page, drawn fresh on every page load: videos and
// posts taking turns, each list with its matches for the page first and a
// random draw from the rest
function drawSuggestions(videos, posts, pathname) {
  const pageWords = words(pathname);
  const chosenVideos = pick(pageWords, videos, (video) => video.title, VIDEOS, shuffle(videos));

  const others = posts.filter((post) => post.permalink !== pathname.replace(/\/$/, ''));
  const chosenPosts = pick(
    pageWords,
    others,
    (post) => `${post.title} ${post.tags.join(' ')}`,
    POSTS,
    shuffle(others),
  );

  const items = [];
  for (let i = 0; i < Math.max(chosenVideos.length, chosenPosts.length); i += 1) {
    if (chosenVideos[i]) items.push({ type: 'video', ...chosenVideos[i] });
    if (chosenPosts[i]) items.push({ type: 'post', ...chosenPosts[i] });
  }
  return items;
}

function VideoItem({ video }) {
  const short = video.kind === 'short';
  return (
    <a className={styles.item} href={video.url} target="_blank" rel="noopener noreferrer">
      <span className={clsx(styles.thumb, short ? styles.tall : styles.wide)}>
        <img
          src={video.thumbnail}
          alt=""
          loading="lazy"
          onError={(event) => {
            if (video.fallback && event.currentTarget.src !== video.fallback) {
              event.currentTarget.src = video.fallback;
            }
          }}
        />
        <span className={styles.play} aria-hidden="true">
          <FaPlay />
        </span>
      </span>
      <span className={styles.text}>
        <span className={styles.kind}>
          {short
            ? translate({ id: 'component.exploreCard.short', message: 'Short · YouTube' })
            : translate({ id: 'component.exploreCard.video', message: 'Video · YouTube' })}
        </span>
        <span className={styles.title}>{video.title}</span>
      </span>
    </a>
  );
}

function PostItem({ post }) {
  const cover = useCoverSrc(post.cover);
  const { i18n } = useDocusaurusContext();
  const date = new Date(post.date).toLocaleDateString(i18n.currentLocale, {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
  });
  return (
    <Link className={styles.item} to={post.permalink}>
      <span className={clsx(styles.thumb, styles.wide)}>{cover && <img src={cover} alt="" loading="lazy" />}</span>
      <span className={styles.text}>
        <span className={styles.kind}>
          {translate({ id: 'component.exploreCard.post', message: 'Blog · {date}' }, { date })}
        </span>
        <span className={styles.title}>{post.title}</span>
      </span>
    </Link>
  );
}

// "Explore" under the team card in the docs sidebar: one suggestion at a
// time (a video from the webforJ channel or a recent blog post), drawn at
// random on every page load, with arrows to step through them. Items that
// match the page come first. It keeps one height whatever it shows, and
// stays out of the way on short screens.
export default function ExploreCard() {
  const { pathname } = useLocation();
  const { videos = [], posts = [] } = usePluginData('webforj-explore') ?? {};
  // drawn in the browser, after the page renders: the built page holds the
  // card's place empty, so nothing swaps in front of the reader
  const [items, setItems] = useState(null);
  const [index, setIndex] = useState(0);

  useEffect(() => {
    setItems(drawSuggestions(videos, posts, pathname));
    setIndex(0);
  }, [pathname, videos, posts]);

  if (!videos.length && !posts.length) {
    return null;
  }
  if (!items) {
    return <aside className={clsx(styles.card, styles.pending)} aria-hidden="true" />;
  }
  if (!items.length) {
    return null;
  }
  const current = items[index % items.length];
  const step = (delta) => setIndex((index + delta + items.length) % items.length);

  return (
    <aside
      className={styles.card}
      aria-label={translate({ id: 'component.exploreCard.label', message: 'Explore' })}>
      <div className={styles.head}>
        <p className={styles.heading}>
          <Translate id="component.exploreCard.label">Explore</Translate>
        </p>
        {items.length > 1 && (
          <div className={styles.controls}>
            <button type="button" className={styles.arrow} onClick={() => step(-1)}
              aria-label={translate({ id: 'component.exploreCard.previous', message: 'Previous suggestion' })}>
              <FaChevronLeft aria-hidden="true" />
            </button>
            <span className={styles.count} aria-live="polite">
              {(index % items.length) + 1}/{items.length}
            </span>
            <button type="button" className={styles.arrow} onClick={() => step(1)}
              aria-label={translate({ id: 'component.exploreCard.next', message: 'Next suggestion' })}>
              <FaChevronRight aria-hidden="true" />
            </button>
          </div>
        )}
      </div>
      {current.type === 'video' ? <VideoItem video={current} /> : <PostItem post={current} />}
    </aside>
  );
}
