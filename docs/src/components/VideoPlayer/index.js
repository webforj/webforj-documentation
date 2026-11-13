import React, { useEffect, useRef, useState } from 'react';
import clsx from 'clsx';
import styles from './styles.module.css';

const CDN = 'https://cdn.webforj.com/webforj-documentation/video/';

// A video with its cover image and Media Chrome controls (MIT).
//
//   <VideoPlayer video="craftforj/pick-mode" width={1920} height={992} title="..." />
//
// `video` is the path under the CDN video folder, without extension: the
// player loads <video>.mp4 and shows <video>-cover.jpg (the video's first
// frame) as the cover, so the cover appears on iOS before playback. `width`
// and `height` give the frame its real shape, and tall recordings are capped
// so they never fill the whole screen. `cover` takes the URL of a cover made
// by hand, used instead of the first frame.
//
// Landing pages pass `controlsOnPlay`, so only the big play button shows on
// the cover and the control bar appears once the video has started, and
// `plain` when the page already draws a frame around the video. `active`
// pauses the video when its tab or view leaves the screen.
// The server renders a plain <video>; Media Chrome loads in the browser.
export default function VideoPlayer({
  video,
  cover,
  width = 16,
  height = 9,
  title,
  autoPlay = false,
  muted = false,
  controlsOnPlay = false,
  plain = false,
  active = true,
}) {
  const [mc, setMc] = useState(null);
  const [started, setStarted] = useState(autoPlay);
  const ref = useRef(null);

  useEffect(() => {
    let alive = true;
    import('media-chrome/react').then((mod) => alive && setMc(mod));
    return () => {
      alive = false;
    };
  }, []);

  useEffect(() => {
    if (!active) ref.current?.pause();
  }, [active]);

  const src = `${CDN}${video}.mp4`;
  const poster = cover || `${CDN}${video}-cover.jpg`;
  const frameClass = clsx(styles.frame, plain && styles.plain);
  const frameStyle = plain
    ? { aspectRatio: `${width} / ${height}` }
    : {
        aspectRatio: `${width} / ${height}`,
        maxWidth: `min(100%, calc(70vh * ${width} / ${height}))`,
      };

  const media = (extra) => (
    <video
      ref={ref}
      className={styles.video}
      src={src}
      poster={poster}
      playsInline
      preload="metadata"
      autoPlay={autoPlay}
      muted={muted || autoPlay}
      aria-label={title}
      onPlay={() => setStarted(true)}
      {...extra}
    />
  );

  if (!mc) {
    return (
      <div className={frameClass} style={frameStyle}>
        {media({ controls: !controlsOnPlay || started })}
      </div>
    );
  }

  const {
    MediaController,
    MediaControlBar,
    MediaPlayButton,
    MediaTimeRange,
    MediaTimeDisplay,
    MediaMuteButton,
    MediaVolumeRange,
    MediaPlaybackRateButton,
    MediaFullscreenButton,
    MediaLoadingIndicator,
  } = mc;

  return (
    <div className={frameClass} style={frameStyle}>
      <MediaController className={styles.controller}>
        {media({ slot: 'media' })}
        <MediaLoadingIndicator slot="centered-chrome" noAutohide />
        <MediaPlayButton slot="centered-chrome" className={styles.bigPlay} />
        <MediaControlBar className={clsx(styles.bar, controlsOnPlay && !started && styles.barHidden)}>
          <MediaPlayButton />
          <MediaTimeRange />
          <MediaTimeDisplay showDuration />
          <MediaMuteButton />
          <MediaVolumeRange />
          <MediaPlaybackRateButton />
          <MediaFullscreenButton />
        </MediaControlBar>
      </MediaController>
    </div>
  );
}
