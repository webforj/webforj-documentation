import React, { useCallback, useEffect, useRef, useState } from 'react';
import Link from '@docusaurus/Link';
import Translate, { translate } from '@docusaurus/Translate';
import { useLocation } from '@docusaurus/router';
import { usePluginData } from '@docusaurus/useGlobalData';
import { FaChevronLeft, FaChevronRight, FaPlay } from 'react-icons/fa6';
import { useCoverSrc } from '@site/src/components/blogCover';
import styles from './ExploreCard.module.css';

// "/de/docs/security/overview/" -> "/docs/security/overview"
function docsPath(pathname) {
  const at = pathname.indexOf('/docs/');
  return at < 0 ? null : pathname.slice(at).replace(/\/$/, '');
}

// What to show on a docs page: the video and the posts about the same topic
// as the page, chosen when the site builds (plugins/explore.js). A page with
// no matching video or post shows none. Nothing random, so it is the same for
// everyone.
function useExplore() {
  const { pathname } = useLocation();
  const { videos = [], relatedVideo = {}, posts = [], related = {} } = usePluginData('webforj-explore') ?? {};
  const page = docsPath(pathname);
  if (!page) {
    return { video: null, articles: [] };
  }
  const articles = (related[page] ?? []).map((index) => posts[index]).filter(Boolean);
  const video = videos[relatedVideo[page]] ?? null;
  return { video, articles };
}

// One frame for both kinds: a regular video fills it, a Short sits in the
// middle at its upright shape with the same frame, zoomed and darkened, on
// both sides, so it reads as one picture
function VideoThumb({ video }) {
  const short = video.kind === 'short';
  const fallback = (event) => {
    if (video.fallback && event.currentTarget.src !== video.fallback) {
      event.currentTarget.src = video.fallback;
    }
  };
  return (
    <span className={styles.frame}>
      {short && <img className={styles.backdrop} src={video.thumbnail} alt="" aria-hidden="true" loading="lazy" onError={fallback} />}
      <img className={short ? styles.upright : styles.wide} src={video.thumbnail} alt={cleanTitle(video.title || '')} loading="lazy" onError={fallback} />
      <span className={styles.play} aria-hidden="true">
        <FaPlay />
      </span>
      {short && (
        <span className={styles.badge}>
          <Translate id="component.exploreCard.shortBadge">Short</Translate>
        </span>
      )}
    </span>
  );
}

// YouTube titles carry a channel suffix ("... | Tutorial"); the tile shows
// the title alone
function cleanTitle(title) {
  return title.replace(/\s*\|.*$/, '').trim();
}

// one tile of the row: a card with the picture on top, then a quiet label
// (what it is) and the title
function Tile({ href, external, picture, label, title }) {
  const content = (
    <>
      {picture}
      <span className={styles.tileBody}>
        <span className={styles.tileLabel}>{label}</span>
        <span className={styles.tileTitle}>{title}</span>
      </span>
    </>
  );
  return external ? (
    <a className={styles.tile} href={href} target="_blank" rel="noopener noreferrer">
      {content}
    </a>
  ) : (
    <Link className={styles.tile} to={href}>
      {content}
    </Link>
  );
}

function ArticleCard({ post }) {
  const cover = useCoverSrc(post.cover);
  return (
    <Tile
      href={post.permalink}
      picture={<span className={styles.frame}>{cover && <img className={styles.wide} src={cover} alt={post.title} loading="lazy" />}</span>}
      label={translate({ id: 'component.exploreCard.article', message: 'Article' })}
      title={post.title}
    />
  );
}

// End of a docs page: the video and every related post in one row that
// scrolls sideways. An arrow button is active only while there is more to
// scroll that way.
export function ExploreBottom() {
  const { video, articles } = useExplore();
  const trackRef = useRef(null);
  const [more, setMore] = useState({ start: false, end: false });
  const update = useCallback(() => {
    const track = trackRef.current;
    if (!track) return;
    const max = track.scrollWidth - track.clientWidth;
    setMore({ start: track.scrollLeft > 1, end: track.scrollLeft < max - 1 });
  }, []);
  useEffect(() => {
    const track = trackRef.current;
    if (!track) return undefined;
    update();
    const observer = new ResizeObserver(update);
    observer.observe(track);
    return () => observer.disconnect();
  }, [update]);
  // one step is most of the visible width, so a tile is never skipped
  const step = (direction) => {
    const track = trackRef.current;
    track?.scrollBy({ left: direction * track.clientWidth * 0.8, behavior: 'smooth' });
  };

  if (!video && !articles.length) {
    return null;
  }
  return (
    <section className={styles.bottom} aria-labelledby="keep-learning">
      {/* heading row: the label on the left, the arrows on the right, so
          they never sit on top of a picture */}
      <div className={styles.bottomHead}>
        <h2 id="keep-learning" className={styles.bottomHeading}>
          <Translate id="component.exploreCard.keepLearning">Keep learning</Translate>
        </h2>
        {(more.start || more.end) && (
          <div className={styles.arrows}>
            <button
              type="button"
              className={styles.arrow}
              disabled={!more.start}
              onClick={() => step(-1)}
              aria-label={translate({ id: 'component.exploreCard.previous', message: 'Previous' })}>
              <FaChevronLeft />
            </button>
            <button
              type="button"
              className={styles.arrow}
              disabled={!more.end}
              onClick={() => step(1)}
              aria-label={translate({ id: 'component.exploreCard.next', message: 'Next' })}>
              <FaChevronRight />
            </button>
          </div>
        )}
      </div>
      <div className={styles.carousel}>
        <div
          ref={trackRef}
          onScroll={update}
          className={styles.tiles}>
          {video && (
            <Tile
              href={video.url}
              external
              picture={<VideoThumb video={video} />}
              label={
                video.kind === 'short'
                  ? translate({ id: 'component.exploreCard.short', message: 'Short · YouTube' })
                  : translate({ id: 'component.exploreCard.video', message: 'Video · YouTube' })
              }
              title={cleanTitle(video.title)}
            />
          )}
          {articles.map((post) => (
            <ArticleCard key={post.permalink} post={post} />
          ))}
        </div>
      </div>
    </section>
  );
}
