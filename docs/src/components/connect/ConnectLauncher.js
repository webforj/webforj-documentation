import React, { useEffect, useId, useRef, useState } from 'react';
import clsx from 'clsx';
import { useLocation } from '@docusaurus/router';
import useBaseUrl from '@docusaurus/useBaseUrl';
import Translate, { translate } from '@docusaurus/Translate';
import { FaArrowRight, FaEnvelope, FaGithub, FaWandMagicSparkles, FaXmark } from 'react-icons/fa6';
import ConnectIcons from './ConnectIcons';
import { CONTACT_EMAIL, CONTACT_URL, ISSUES_URL, TEAM_LANGUAGES, followLinksFor } from './links';
import styles from './ConnectLauncher.module.css';

// The greeting arrives like a chat message once the reader has read a while.
// How soon it may come back depends on what they did with the last one, the
// usual frequency capping of chat and popup tools:
// - at most once per visit (browser session)
// - ignored: again on a later visit, at most once a day
// - "Not now" or close: quiet for a week
// - engaged (opened the panel): quiet for a month
const GREETING_KEY = 'webforj-connect-greeting';
const SESSION_KEY = 'webforj-connect-greeting-session';
const GREET_AFTER_MS = 25000;
const GREET_AFTER_SCROLL = 0.4;
const DAY_MS = 24 * 60 * 60 * 1000;
const QUIET_MS = { ignored: DAY_MS, dismissed: 7 * DAY_MS, engaged: 30 * DAY_MS };

function readSession(key) {
  try {
    return window.sessionStorage.getItem(key);
  } catch {
    return null;
  }
}

function writeSession(key, value) {
  try {
    window.sessionStorage.setItem(key, value);
  } catch {
    // storage blocked: nothing else breaks
  }
}

function greetingAllowed() {
  if (readSession(SESSION_KEY)) return false;
  let last = null;
  try {
    last = JSON.parse(readStorage(GREETING_KEY));
  } catch {
    last = null;
  }
  if (!last?.at) return true;
  return Date.now() - last.at >= (QUIET_MS[last.outcome] ?? QUIET_MS.dismissed);
}

function recordGreeting(outcome) {
  writeStorage(GREETING_KEY, JSON.stringify({ at: Date.now(), outcome }));
}

// One path for every reason to reach out. Project talks go to the team by
// email with the subject and page filled in. A docs question opens the docs
// search with Ask AI, which answers on the spot. A bug goes to GitHub.
function getTopics() {
  return [
    {
      key: 'evaluating',
      kind: 'email',
      label: translate({
        id: 'component.connectLauncher.topics.evaluating',
        message: 'Evaluating webforJ for a project',
      }),
      subject: translate({
        id: 'component.connectLauncher.topics.evaluating.subject',
        message: 'Evaluating webforJ for a project',
      }),
    },
    {
      key: 'modernizing',
      kind: 'email',
      label: translate({
        id: 'component.connectLauncher.topics.modernizing',
        message: 'Modernizing an existing app',
      }),
      subject: translate({
        id: 'component.connectLauncher.topics.modernizing.subject',
        message: 'Modernizing an existing app with webforJ',
      }),
    },
    {
      key: 'askAi',
      kind: 'search',
      label: translate({
        id: 'component.connectLauncher.topics.askAi',
        message: 'A question about the docs: ask AI',
      }),
    },
    {
      key: 'bug',
      kind: 'issue',
      label: translate({
        id: 'component.connectLauncher.topics.bug',
        message: 'Found a bug: report it on GitHub',
      }),
    },
  ];
}

const TOPIC_ICONS = { email: FaEnvelope, search: FaWandMagicSparkles, issue: FaGithub };

// Opens the docs search, where Ask AI answers questions from the docs
function openDocsSearch() {
  document.querySelector('.DocSearch-Button')?.click();
}

// A soft two-note chime when the greeting appears, made in the browser (no
// audio file). Browsers only allow sound once the visitor has interacted with
// the page (a click, tap or key press), so it plays only then; otherwise the
// greeting arrives silently with its badge.
function chime() {
  if (!navigator.userActivation?.hasBeenActive) return;
  try {
    const AudioContext = window.AudioContext || window.webkitAudioContext;
    if (!AudioContext) return;
    const context = new AudioContext();
    const now = context.currentTime;
    [
      [880, 0],
      [1318.5, 0.12],
    ].forEach(([frequency, offset]) => {
      const oscillator = context.createOscillator();
      const gain = context.createGain();
      oscillator.type = 'sine';
      oscillator.frequency.value = frequency;
      gain.gain.setValueAtTime(0, now + offset);
      gain.gain.linearRampToValueAtTime(0.06, now + offset + 0.02);
      gain.gain.exponentialRampToValueAtTime(0.0001, now + offset + 0.45);
      oscillator.connect(gain).connect(context.destination);
      oscillator.start(now + offset);
      oscillator.stop(now + offset + 0.5);
    });
    setTimeout(() => context.close(), 1200);
  } catch {
    // no sound available: the greeting and its badge still show
  }
}

function pageTitle() {
  return document.title.replace(/\s*\|\s*webforJ.*$/i, '').trim();
}

function mailto(subject) {
  const body = translate(
    {
      id: 'component.connectLauncher.mailBody',
      message: 'Hi webforJ team,\n\n\n\n(I was reading: {title} - {url})',
    },
    { title: pageTitle(), url: window.location.href },
  );
  return `mailto:${CONTACT_EMAIL}?subject=${encodeURIComponent(subject)}&body=${encodeURIComponent(body)}`;
}

function readStorage(key) {
  try {
    return window.localStorage.getItem(key);
  } catch {
    return null;
  }
}

function writeStorage(key, value) {
  try {
    window.localStorage.setItem(key, value);
  } catch {
    // storage blocked: the greeting may show again, nothing else breaks
  }
}

// What the panel and the greeting say depends on where the reader is
function useContextCopy(pathname) {
  const [title, setTitle] = useState('');
  useEffect(() => setTitle(pageTitle()), [pathname]);

  if (/^\/docs(\/|$)/.test(pathname)) {
    return {
      text: translate({
        id: 'component.connectLauncher.copy.docs.text',
        message:
          'Building something with webforJ, or stuck on a page in the docs? Tell us, and an engineer will help you find the way.',
      }),
      greeting: title
        ? translate(
            {
              id: 'component.connectLauncher.copy.docs.greetingTitled',
              message: 'Questions about {title}? Ask the team.',
            },
            { title },
          )
        : translate({
            id: 'component.connectLauncher.copy.greeting',
            message: 'Questions about webforJ? Ask the team.',
          }),
    };
  }
  if (/^\/blog(\/|$)/.test(pathname)) {
    return {
      text: translate({
        id: 'component.connectLauncher.copy.blog.text',
        message:
          'Building something like this, or weighing webforJ for a project? Tell us about it, and we will help you find the best way to build it.',
      }),
      greeting: translate({
        id: 'component.connectLauncher.copy.blog.greeting',
        message: 'Building something like this? Talk to the team.',
      }),
    };
  }
  return {
    text: translate({
      id: 'component.connectLauncher.copy.default.text',
      message: 'Tell us what you are building, and we will help you find the best way to do it with webforJ.',
    }),
    greeting: translate({
      id: 'component.connectLauncher.copy.greeting',
      message: 'Questions about webforJ? Ask the team.',
    }),
  };
}

// The once-per-visitor greeting: after the reader has scrolled through half
// the page or stayed a while, unless they were greeted or opened the panel
function useGreeting(open, pathname) {
  const [greeting, setGreeting] = useState(false);

  useEffect(() => {
    if (open || !greetingAllowed()) return undefined;
    let shown = false;
    const show = () => {
      if (shown || !greetingAllowed()) return;
      shown = true;
      // counted as ignored until the reader answers, and never twice in a visit
      recordGreeting('ignored');
      writeSession(SESSION_KEY, '1');
      setGreeting(true);
      chime();
    };
    const timer = setTimeout(show, GREET_AFTER_MS);
    const onScroll = () => {
      const max = document.documentElement.scrollHeight - window.innerHeight;
      if (max > 0 && window.scrollY / max >= GREET_AFTER_SCROLL) show();
    };
    window.addEventListener('scroll', onScroll, { passive: true });
    return () => {
      clearTimeout(timer);
      window.removeEventListener('scroll', onScroll);
    };
  }, [open, pathname]);

  // "dismissed" for Not now and close, "engaged" when the panel opens
  const dismiss = (outcome = 'dismissed') => {
    recordGreeting(outcome);
    writeSession(SESSION_KEY, '1');
    setGreeting(false);
  };

  return [greeting, dismiss];
}

// A "Questions? Ask us" button in the corner of every page, opening a small
// panel with the ways to reach and follow webforJ
export default function ConnectLauncher() {
  const { pathname } = useLocation();
  const [open, setOpen] = useState(false);
  // the panel plays its exit animation before it leaves
  const [closing, setClosing] = useState(false);
  const panelId = useId();
  const rootRef = useRef(null);
  const buttonRef = useRef(null);
  const icon = useBaseUrl('/img/webforj_icon.svg');
  const copy = useContextCopy(pathname);
  const [greeting, dismissGreeting] = useGreeting(open, pathname);

  const openPanel = () => {
    dismissGreeting('engaged');
    setClosing(false);
    setOpen(true);
  };

  const close = () => {
    if (window.matchMedia('(prefers-reduced-motion: reduce)').matches) {
      setOpen(false);
      return;
    }
    setClosing(true);
  };

  const finishClose = () => {
    if (closing) {
      setClosing(false);
      setOpen(false);
    }
  };

  // close on navigation, Escape, or a click outside
  useEffect(() => {
    setClosing(false);
    setOpen(false);
  }, [pathname]);
  useEffect(() => {
    if (!open) return undefined;
    const onKey = (event) => {
      if (event.key === 'Escape') {
        close();
        buttonRef.current?.focus();
      }
    };
    const onPointer = (event) => {
      if (!rootRef.current?.contains(event.target)) close();
    };
    document.addEventListener('keydown', onKey);
    document.addEventListener('pointerdown', onPointer);
    return () => {
      document.removeEventListener('keydown', onKey);
      document.removeEventListener('pointerdown', onPointer);
    };
  }, [open, closing]);

  const avatar = (
    <span className={styles.avatar} aria-hidden="true">
      <img src={icon} alt="" width="24" height="24" />
      <span className={styles.online} />
    </span>
  );

  return (
    <div className={styles.root} ref={rootRef}>
      {open && (
        <div
          className={clsx(styles.panel, closing && styles.closing)}
          id={panelId}
          role="dialog"
          aria-label={translate({
            id: 'component.connectLauncher.panelLabel',
            message: 'Talk to the webforJ team',
          })}
          onAnimationEnd={finishClose}>
          <button type="button" className={styles.close} aria-label={translate({ id: 'component.connectLauncher.close', message: 'Close' })}
            onClick={close}>
            <FaXmark aria-hidden="true" />
          </button>
          <div className={styles.hello}>
            {avatar}
            <p className={styles.title}>
              <Translate id="component.connectLauncher.hello">{"Hi, we're the webforJ team 👋"}</Translate>
            </p>
          </div>
          <p className={styles.text}>{copy.text}</p>
          <p className={styles.languages}>
            <Translate id="component.connectLauncher.languages" values={{ languages: TEAM_LANGUAGES.map((l) => l.name).join(' · ') }}>
              {'We speak {languages}'}
            </Translate>
          </p>

          <p className={styles.label}>
            <Translate id="component.connectLauncher.topicsLabel">What can we help with?</Translate>
          </p>
          <ul className={styles.topics}>
            {getTopics().map(({ key, kind, label, subject }) => {
              const Icon = TOPIC_ICONS[kind];
              const content = (
                <>
                  <Icon aria-hidden="true" />
                  <span>{label}</span>
                </>
              );
              if (kind === 'search') {
                return (
                  <li key={key}>
                    <button
                      type="button"
                      className={styles.topic}
                      onClick={() => {
                        close();
                        openDocsSearch();
                      }}>
                      {content}
                    </button>
                  </li>
                );
              }
              if (kind === 'issue') {
                return (
                  <li key={key}>
                    <a className={styles.topic} href={ISSUES_URL} target="_blank" rel="noopener noreferrer">
                      {content}
                    </a>
                  </li>
                );
              }
              return (
                <li key={key}>
                  <a
                    className={styles.topic}
                    href={`mailto:${CONTACT_EMAIL}`}
                    onClick={(event) => {
                      event.currentTarget.href = mailto(subject);
                    }}>
                    {content}
                  </a>
                </li>
              );
            })}
          </ul>

          <a className={styles.primary} href={CONTACT_URL} target="_blank" rel="noopener noreferrer">
            <Translate id="component.connectLauncher.primary">Book a discovery call</Translate>{' '}
            <FaArrowRight aria-hidden="true" />
          </a>
          {/* what the call is, and who it is for, so it is booked for real
              projects and quick questions go to the topics above */}
          <p className={styles.callNote}>
            <strong>
              <Translate id="component.connectLauncher.callNote.strong">No cost, no commitment.</Translate>
            </strong>{' '}
            <Translate id="component.connectLauncher.callNote.text">
              For teams evaluating or building a project with webforJ. For quick questions, pick a topic above.
            </Translate>
          </p>

          <div className={styles.follow}>
            <span className={styles.label}>
              <Translate id="component.connectLauncher.follow">Follow webforJ</Translate>
            </span>
            <ConnectIcons links={followLinksFor(pathname)} compact />
          </div>
        </div>
      )}

      {greeting && !open && (
        // the greeting arrives like a chat message from the team
        <div className={styles.greeting} role="status">
          <button
            type="button"
            className={styles.greetingClose}
            aria-label={translate({ id: 'component.connectLauncher.dismiss', message: 'Dismiss' })}
            onClick={() => dismissGreeting('dismissed')}>
            <FaXmark aria-hidden="true" />
          </button>
          <div className={styles.greetingFrom}>
            {avatar}
            <span className={styles.greetingName}>
              <Translate id="component.connectLauncher.teamName">webforJ team</Translate>
            </span>
          </div>
          <p className={styles.greetingText}>{copy.greeting}</p>
          <div className={styles.greetingReplies}>
            <button type="button" className={styles.replyYes} onClick={openPanel}>
              <Translate id="component.connectLauncher.replyYes">{"Yes, let's talk"}</Translate>
            </button>
            <button type="button" className={styles.replyNo} onClick={() => dismissGreeting('dismissed')}>
              <Translate id="component.connectLauncher.replyNo">Not now</Translate>
            </button>
          </div>
        </div>
      )}

      <button
        type="button"
        ref={buttonRef}
        className={styles.launcher}
        aria-expanded={open && !closing}
        aria-controls={panelId}
        onClick={() => (open && !closing ? close() : openPanel())}>
        {avatar}
        <Translate id="component.connectLauncher.button">Questions? Ask us</Translate>
        {/* an unread greeting is waiting, like a new message */}
        {greeting && !open && (
          <span className={styles.badge} aria-label={translate({
              id: 'component.connectLauncher.newMessage',
              message: '1 new message',
            })}>
            1
          </span>
        )}
      </button>
    </div>
  );
}
