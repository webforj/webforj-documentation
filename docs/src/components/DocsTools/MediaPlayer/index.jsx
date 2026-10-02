import React, {forwardRef} from 'react';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import {
  MediaCaptionsButton,
  MediaControlBar,
  MediaController,
  MediaFullscreenButton,
  MediaLoadingIndicator,
  MediaMuteButton,
  MediaPipButton,
  MediaPlaybackRateButton,
  MediaPlayButton,
  MediaSeekBackwardButton,
  MediaSeekForwardButton,
  MediaTimeDisplay,
  MediaTimeRange,
  MediaVolumeRange,
} from 'media-chrome/react';
import 'media-chrome/lang/de.js';
import 'media-chrome/lang/es.js';
import 'media-chrome/lang/fr.js';
import 'media-chrome/lang/zh-CN.js';

import styles from './styles.module.css';

const MEDIA_CHROME_LOCALES = {
  zh: 'zh-CN',
};

/**
 * A documentation-focused video player built on a native <video> element.
 *
 * The forwarded ref exposes the video element, so a page can use the regular
 * HTMLMediaElement API for custom interactions such as timestamp links or
 * synchronized callouts.
 *
 * Tracks use the native track shape, for example:
 * {kind: 'captions', src: '/captions/demo-en.vtt', srcLang: 'en', label: 'English'}
 */
const MediaPlayer = forwardRef(function MediaPlayer(
  {
    src,
    title = 'Video player',
    caption,
    poster,
    tracks = [],
    playbackRates = [0.5, 0.75, 1, 1.25, 1.5, 2],
    seekOffset = 10,
    aspectRatio = '16 / 9',
    preload = 'metadata',
    className,
    children,
    ...videoProps
  },
  videoRef,
) {
  const {i18n} = useDocusaurusContext();
  const playerLocale = MEDIA_CHROME_LOCALES[i18n.currentLocale] ?? i18n.currentLocale;

  return (
    <figure className={[styles.wrapper, className].filter(Boolean).join(' ')}>
      <MediaController
        className={styles.player}
        style={{'--docs-video-aspect-ratio': aspectRatio}}
        keyboardBackwardSeekOffset={seekOffset}
        keyboardForwardSeekOffset={seekOffset}
        lang={playerLocale}
        aria-label={title}
      >
        <video
          {...videoProps}
          ref={videoRef}
          className={styles.video}
          slot="media"
          src={src}
          poster={poster}
          preload={preload}
          playsInline
          aria-label={title}
        >
          {tracks.map((track, index) => (
            <track
              key={`${track.kind ?? 'track'}-${track.src}-${index}`}
              {...track}
            />
          ))}
          {children}
        </video>

        <MediaLoadingIndicator slot="centered-chrome" noAutohide />

        <MediaControlBar className={styles.controls}>
          <MediaPlayButton />
          <MediaSeekBackwardButton seekOffset={seekOffset} />
          <MediaSeekForwardButton seekOffset={seekOffset} />
          <MediaTimeDisplay showDuration />
          <MediaTimeRange />
          <MediaCaptionsButton />
          <MediaPlaybackRateButton rates={playbackRates} />
          <MediaMuteButton />
          <MediaVolumeRange />
          <MediaPipButton />
          <MediaFullscreenButton />
        </MediaControlBar>
      </MediaController>

      {caption && <figcaption className={styles.caption}>{caption}</figcaption>}
    </figure>
  );
});

export default MediaPlayer;
