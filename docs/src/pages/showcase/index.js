import React, { useCallback, useEffect, useRef, useState } from 'react';
import Layout from '@theme/Layout';
import Link from '@docusaurus/Link';
import Translate, { translate } from '@docusaurus/Translate';
import useBaseUrl from '@docusaurus/useBaseUrl';
import { CONTACT_URL, TEAM_LANGUAGES } from '@site/src/components/connect/links';
import { ArrowIcon } from '@site/src/components/pageKit/icons';
import 'devices.css/dist/devices.min.css';
import { getProjects } from './_projects';
import styles from './showcase.module.css';

const START_URL = 'https://docs.webforj.com/startforj/';
const COMPONENTS_URL = '/docs/components/overview';
const DESKTOP_SIZES = '(min-width: 997px) 800px, 92vw';

// The sections of the page, in order, for the indicator on the left.
function getSections() {
  return [
    {
      id: 'showcase-top',
      label: translate({ id: 'page.showcase.sections.overview', message: 'Overview' }),
    },
    {
      id: 'showcase-apps',
      label: translate({ id: 'page.showcase.sections.apps', message: 'The apps' }),
    },
    ...getProjects().map((project) => ({
      id: project.slug,
      label: project.name,
      colourSection: true,
    })),
    {
      id: 'showcase-business',
      label: translate({ id: 'page.showcase.sections.talk', message: 'Talk to us' }),
      colourSection: true,
    },
    {
      id: 'showcase-start',
      label: translate({ id: 'page.showcase.sections.start', message: 'Get started' }),
      colourSection: true,
    },
  ];
}

// The colours of an app's gradient, read by its tile and its chapter.
function getAppColours(project) {
  return {
    '--app-color': project.colours.base,
    '--app-light': project.colours.light,
    '--app-neighbour': project.colours.neighbour,
    '--app-deep': project.colours.deep,
  };
}

function formatNumber(number) {
  return String(number).padStart(2, '0');
}

// Calls `onStep` with +1 or -1 for the right and left arrow keys.
function handleArrowKeys(event, onStep) {
  if (event.key === 'ArrowRight') {
    event.preventDefault();
    onStep(1);
  } else if (event.key === 'ArrowLeft') {
    event.preventDefault();
    onStep(-1);
  }
}

// Returns a function that gives the image sources and alt text of one
// screen of the project.
function useScreenImage(project) {
  const base = useBaseUrl(`/img/showcase/${project.slug}/`);
  return useCallback(
    (screen) => {
      const full = `${base}${screen.file}.webp`;
      const thumb = `${base}${screen.file}-thumb.webp`;
      return {
        full,
        thumb,
        srcSet: `${thumb} ${project.size.thumb}w, ${full} ${project.size.full}w`,
        alt: translate(
          {
            id: 'page.showcase.screenAlt',
            message: '{screen} screen of the {app} app. {caption}',
          },
          { screen: screen.name, app: project.name, caption: screen.caption },
        ),
      };
    },
    [base, project],
  );
}

function ExpandIcon() {
  return (
    <svg className={styles.icon} viewBox="0 0 20 20" aria-hidden="true">
      <path
        d="M12 3.5h4.5V8M8 16.5H3.5V12M16.5 3.5 11 9M3.5 16.5 9 11"
        fill="none"
        stroke="currentColor"
        strokeWidth="1.75"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
}

function CloseIcon() {
  return (
    <svg className={styles.icon} viewBox="0 0 20 20" aria-hidden="true">
      <path
        d="M5 5l10 10M15 5 5 15"
        fill="none"
        stroke="currentColor"
        strokeWidth="1.75"
        strokeLinecap="round"
      />
    </svg>
  );
}

// The page scrolls normally. The scrollama library watches the sections and
// tells the dots on the left which one is in view. A click on a dot scrolls
// to its section.
function SectionIndicator() {
  const sections = getSections();
  const [active, setActive] = useState(0);
  const navRef = useRef(null);

  useEffect(() => {
    let scroller;
    let cancelled = false;
    import('scrollama').then(({ default: scrollama }) => {
      if (cancelled) return;
      scroller = scrollama();
      scroller
        .setup({ step: '[data-showcase-step]', offset: 0.4 })
        .onStepEnter(({ index }) => setActive(index));
    });
    const onResize = () => scroller && scroller.resize();
    window.addEventListener('resize', onResize);
    // scrollama re-creates its size observers when a section changes size.
    // The browser reports that as a harmless "ResizeObserver loop" notice,
    // which the dev server would show as a full screen error.
    const quietLoopNotice = (event) => {
      if (event.message && event.message.startsWith('ResizeObserver loop')) {
        event.stopImmediatePropagation();
      }
    };
    window.addEventListener('error', quietLoopNotice, true);
    // hide the dots while the footer is on screen, so they never sit on it
    const footer = document.querySelector('footer');
    const nav = navRef.current;
    let footerObserver;
    if (footer && nav && 'IntersectionObserver' in window) {
      footerObserver = new IntersectionObserver(([entry]) => {
        nav.classList.toggle(styles.indicatorHidden, entry.isIntersecting);
      });
      footerObserver.observe(footer);
    }
    return () => {
      cancelled = true;
      window.removeEventListener('resize', onResize);
      window.removeEventListener('error', quietLoopNotice, true);
      if (scroller) scroller.destroy();
      if (footerObserver) footerObserver.disconnect();
    };
  }, []);

  const scrollToSection = (event, index) => {
    event.preventDefault();
    const step = document.querySelectorAll('[data-showcase-step]')[index];
    if (step) step.scrollIntoView({ behavior: 'smooth', block: 'start' });
  };

  return (
    <nav
      ref={navRef}
      className={`${styles.indicator} ${sections[active].colourSection ? styles.indicatorOverColourSection : ''}`}
      aria-label={translate({
        id: 'page.showcase.sections.label',
        message: 'Sections on this page',
      })}>
      <ol>
        {sections.map((section, index) => (
          <li key={section.id}>
            <a
              href={`#${section.id}`}
              onClick={(event) => scrollToSection(event, index)}
              className={index === active ? styles.indicatorActive : undefined}
              aria-current={index === active ? 'true' : undefined}>
              <span className={styles.indicatorDot} aria-hidden="true" />
              <span className={styles.indicatorLabel}>{section.label}</span>
            </a>
          </li>
        ))}
      </ol>
    </nav>
  );
}

// The phone in the hero, in a device frame from the devices.css library,
// drawn in CSS at real proportions. The status row sits over the screen's
// own status area.
function HeroPhone({ src, alt }) {
  return (
    <div className={styles.heroPhone}>
      <div className="device device-google-pixel-6-pro device-black">
        <div className="device-frame">
          <img
            className="device-screen"
            src={`${src}.webp`}
            width={780}
            height={1684}
            alt={alt}
            decoding="async"
          />
          <div className={styles.statusBar} aria-hidden="true">
            <span>10:24</span>
            <span className={styles.statusIcons}>
              <svg viewBox="0 0 18 12" width="18" height="12">
                <rect x="0" y="8" width="3" height="4" rx="1" />
                <rect x="5" y="5.5" width="3" height="6.5" rx="1" />
                <rect x="10" y="3" width="3" height="9" rx="1" />
                <rect x="15" y="0" width="3" height="12" rx="1" />
              </svg>
              <svg viewBox="0 0 16 12" width="16" height="12">
                <path d="M8 2.2c2.4 0 4.6.9 6.3 2.5l1.2-1.3C13.4 1.3 10.8.2 8 .2S2.6 1.3.5 3.4l1.2 1.3C3.4 3.1 5.6 2.2 8 2.2Z" />
                <path d="M8 5.7c1.5 0 2.8.5 3.9 1.5l1.2-1.3C11.7 4.6 9.9 3.9 8 3.9S4.3 4.6 2.9 5.9l1.2 1.3c1.1-1 2.4-1.5 3.9-1.5Z" />
                <path d="M8 9.1c.6 0 1.1.2 1.6.6L8 11.5 6.4 9.7c.5-.4 1-.6 1.6-.6Z" />
              </svg>
              <svg viewBox="0 0 27 12" width="27" height="12">
                <rect
                  x="0.5"
                  y="0.5"
                  width="23"
                  height="11"
                  rx="3"
                  fill="none"
                  stroke="currentColor"
                  strokeOpacity="0.4"
                />
                <rect x="2" y="2" width="16" height="8" rx="1.8" />
                <path d="M25 4v4c.8-.3 1.3-1.1 1.3-2S25.8 4.3 25 4Z" opacity="0.45" />
              </svg>
            </span>
          </div>
        </div>
        <div className="device-stripe" />
        <div className="device-header" />
        <div className="device-sensors" />
        <div className="device-btns" />
        <div className="device-power" />
      </div>
    </div>
  );
}

function Hero() {
  const projects = getProjects();
  const totalScreens = projects.reduce((sum, project) => sum + project.screens.length, 0);
  const desktopImage = useBaseUrl('/img/showcase/freight-dispatch/02-live-map');
  const phoneImage = useBaseUrl('/img/showcase/site-inspection/02-checklist-phone');
  return (
    <header className={styles.hero} id="showcase-top">
      <div className={styles.container}>
        <div className={styles.heroTop}>
          <div className={styles.heroText}>
            <p className={styles.eyebrow}>
              <Translate id="page.showcase.hero.eyebrow">webforJ showcase</Translate>
            </p>
            <h1 className={styles.heroTitle}>
              <Translate id="page.showcase.hero.title">What you can build with webforJ</Translate>
            </h1>
            <p className={styles.heroLead}>
              <Translate id="page.showcase.hero.lead">
                {
                  'Dispatch boards, field inspections, front desks and finance approvals. Business apps like these are built in Java with webforJ components: tables, forms, dialogs and live updates.'
                }
              </Translate>
            </p>
            <div className={styles.actions}>
              <a className={`${styles.button} ${styles.buttonPrimary}`} href={START_URL}>
                <Translate id="page.showcase.hero.start">Start a project</Translate>
                <ArrowIcon className={styles.icon} />
              </a>
              <Link className={`${styles.button} ${styles.buttonSecondary}`} to={COMPONENTS_URL}>
                <Translate id="page.showcase.hero.components">Explore the components</Translate>
              </Link>
            </div>
          </div>
          <dl className={styles.facts}>
            <div>
              <dt>
                <Translate id="page.showcase.hero.factApps">Apps</Translate>
              </dt>
              <dd>{projects.length}</dd>
            </div>
            <div>
              <dt>
                <Translate id="page.showcase.hero.factScreens">Screens to step through</Translate>
              </dt>
              <dd>{totalScreens}</dd>
            </div>
            <div>
              <dt>
                <Translate id="page.showcase.hero.factPlatforms">
                  Platforms, desktop and mobile
                </Translate>
              </dt>
              <dd>2</dd>
            </div>
          </dl>
        </div>
        <div className={styles.heroVisual}>
          <img
            className={styles.heroDesktop}
            src={`${desktopImage}.webp`}
            srcSet={`${desktopImage}-thumb.webp 800w, ${desktopImage}.webp 2880w`}
            sizes="(min-width: 1240px) 1100px, 92vw"
            width={1440}
            height={900}
            alt={translate({
              id: 'page.showcase.hero.desktopAlt',
              message:
                'Live map screen of the Freight Dispatch app, with every truck on a road map and a list of shipments sorted by arrival time.',
            })}
            fetchpriority="high"
          />
          <HeroPhone
            src={phoneImage}
            alt={translate({
              id: 'page.showcase.hero.phoneAlt',
              message:
                'Checklist screen of the Site Inspection app on a phone, with pass, fail and not applicable buttons for each check.',
            })}
          />
        </div>
      </div>
    </header>
  );
}

function Gallery() {
  const projects = getProjects();
  return (
    <section className={styles.gallery} aria-labelledby="showcase-apps">
      <div className={styles.container}>
        <div className={styles.galleryHead}>
          <h2 id="showcase-apps" className={styles.galleryTitle}>
            <Translate id="page.showcase.gallery.title">The apps</Translate>
          </h2>
          <p className={styles.galleryLead}>
            <Translate id="page.showcase.gallery.lead">
              {'Four business apps, each one a full set of screens. Pick one and step through it.'}
            </Translate>
          </p>
        </div>
        <ul className={styles.tiles}>
          {projects.map((project, index) => (
            <li key={project.slug}>
              <article className={styles.tile} style={getAppColours(project)}>
                <p className={styles.tileMeta}>
                  <span>{formatNumber(index + 1)}</span>
                  <span>
                    <Translate
                      id="page.showcase.gallery.meta"
                      values={{ platform: project.platformLabel, count: project.screens.length }}>
                      {'{platform} · {count} screens'}
                    </Translate>
                  </span>
                </p>
                <h3 className={styles.tileTitle}>{project.name}</h3>
                <p className={styles.tileText}>{project.useCase}</p>
                <a className={styles.tileAction} href={`#${project.slug}`}>
                  <Translate id="page.showcase.gallery.see">See the screens</Translate>
                  <ArrowIcon className={styles.icon} />
                </a>
              </article>
            </li>
          ))}
        </ul>
      </div>
    </section>
  );
}

function Lightbox({ project, index, onChange, onClose, open }) {
  const dialogRef = useRef(null);
  const getImage = useScreenImage(project);
  const screen = project.screens[index];
  const image = getImage(screen);
  const count = project.screens.length;
  const step = (offset) => onChange((index + offset + count) % count);

  useEffect(() => {
    const dialog = dialogRef.current;
    if (!dialog) return;
    if (open && !dialog.open) dialog.showModal();
    if (!open && dialog.open) dialog.close();
  }, [open]);

  return (
    <dialog
      ref={dialogRef}
      className={`${styles.lightbox} ${project.platform === 'Mobile' ? styles.lightboxMobile : ''}`}
      aria-label={translate(
        { id: 'page.showcase.lightbox.label', message: '{name} screens, full size' },
        { name: project.name },
      )}
      onClose={onClose}
      onKeyDown={(event) => handleArrowKeys(event, step)}
      onClick={(event) => {
        if (event.target === dialogRef.current) onClose();
      }}>
      <div className={styles.lightboxBar}>
        <p className={styles.lightboxTitle} aria-live="polite">
          <span>
            {index + 1} / {count}
          </span>
          {screen.name}
        </p>
        <div className={styles.lightboxControls}>
          <button
            type="button"
            className={styles.iconButton}
            onClick={() => step(-1)}
            aria-label={translate({
              id: 'page.showcase.lightbox.previous',
              message: 'Previous screen',
            })}>
            <ArrowIcon className={styles.icon} direction="left" />
          </button>
          <button
            type="button"
            className={styles.iconButton}
            onClick={() => step(1)}
            aria-label={translate({ id: 'page.showcase.lightbox.next', message: 'Next screen' })}>
            <ArrowIcon className={styles.icon} />
          </button>
          <button
            type="button"
            className={styles.iconButton}
            onClick={onClose}
            aria-label={translate({
              id: 'page.showcase.lightbox.close',
              message: 'Close full size view',
            })}>
            <CloseIcon />
          </button>
        </div>
      </div>
      <div className={styles.lightboxStage}>
        {open && (
          <img
            key={image.full}
            src={image.full}
            width={project.size.full}
            height={Math.round((project.size.full * project.size.height) / project.size.width)}
            alt={image.alt}
          />
        )}
      </div>
    </dialog>
  );
}

// The previous, next and full size buttons of a chapter's view.
function StepButtons({ screenName, onStep, onOpenFull }) {
  return (
    <>
      <button
        type="button"
        className={styles.stepButton}
        onClick={() => onStep(-1)}
        aria-label={translate({ id: 'page.showcase.step.previous', message: 'Previous screen' })}>
        <ArrowIcon className={styles.icon} direction="left" />
      </button>
      <button
        type="button"
        className={styles.stepButton}
        onClick={() => onStep(1)}
        aria-label={translate({ id: 'page.showcase.step.next', message: 'Next screen' })}>
        <ArrowIcon className={styles.icon} />
      </button>
      <button
        type="button"
        className={`${styles.stepButton} ${styles.stepButtonWide}`}
        onClick={onOpenFull}
        aria-label={translate(
          { id: 'page.showcase.step.openFull', message: 'Open {name} full size' },
          { name: screenName },
        )}>
        <ExpandIcon />
        <span>
          <Translate id="page.showcase.step.fullSize">Full size</Translate>
        </span>
      </button>
    </>
  );
}

// One app: its intro and screen list on the left, the current screen on the
// right, and the webforJ features it shows below.
function Project({ project, number }) {
  const [index, setIndex] = useState(0);
  const [open, setOpen] = useState(false);
  const touchStart = useRef(null);
  const opener = useRef(null);
  const getImage = useScreenImage(project);
  const count = project.screens.length;
  const screen = project.screens[index];
  const image = getImage(screen);
  const isMobile = project.platform === 'Mobile';
  const headingId = `${project.slug}-title`;

  // The screen that is leaving and the direction of travel, so the view can
  // slide the new screen in over the old one.
  const [leaving, setLeaving] = useState(null);
  const [direction, setDirection] = useState(1);
  const goTo = useCallback(
    (next) => {
      const target = ((next % count) + count) % count;
      if (target === index) return;
      setDirection(next > index ? 1 : -1);
      setLeaving(index);
      setIndex(target);
    },
    [count, index],
  );
  const step = (offset) => goTo(index + offset);
  const moved = leaving !== null;

  // Warm the cache for the neighbours so stepping through feels instant.
  useEffect(() => {
    if (typeof window === 'undefined') return;
    [index + 1, index - 1].forEach((neighbour) => {
      const preload = new window.Image();
      preload.srcset = getImage(project.screens[(neighbour + count) % count]).srcSet;
      preload.sizes = isMobile ? '390px' : DESKTOP_SIZES;
    });
  }, [index, count, project, getImage, isMobile]);

  const onTouchStart = (event) => {
    const touch = event.touches[0];
    touchStart.current = { x: touch.clientX, y: touch.clientY };
  };

  const onTouchEnd = (event) => {
    if (!touchStart.current) return;
    const touch = event.changedTouches[0];
    const dx = touch.clientX - touchStart.current.x;
    const dy = touch.clientY - touchStart.current.y;
    touchStart.current = null;
    if (Math.abs(dx) > 48 && Math.abs(dx) > Math.abs(dy) * 1.5) {
      step(dx < 0 ? 1 : -1);
    }
  };

  const openFull = (event) => {
    opener.current = event.currentTarget;
    setOpen(true);
  };

  const close = () => {
    setOpen(false);
    opener.current?.focus();
  };

  const stepButtons = <StepButtons screenName={screen.name} onStep={step} onOpenFull={openFull} />;

  return (
    <section
      id={project.slug}
      className={styles.chapter}
      style={getAppColours(project)}
      aria-labelledby={headingId}>
      {/* Arrow keys step through screens from any control in the chapter. */}
      {/* eslint-disable-next-line jsx-a11y/no-static-element-interactions */}
      <div className={styles.container} onKeyDown={(event) => handleArrowKeys(event, step)}>
        <div className={styles.chapterGrid}>
          <div className={styles.rail}>
            <p className={styles.chapterKicker}>
              <span>{formatNumber(number)}</span>
              <Translate
                id="page.showcase.chapter.kicker"
                values={{ platform: project.platformLabel, count }}>
                {'{platform} app · {count} screens'}
              </Translate>
            </p>
            <h2 id={headingId} className={`${styles.sectionTitle} ${styles.chapterTitle}`}>
              {project.name}
            </h2>
            <p className={styles.chapterText}>{project.description}</p>

            <ol
              className={styles.screens}
              aria-label={translate(
                { id: 'page.showcase.chapter.screensLabel', message: '{name} screens' },
                { name: project.name },
              )}>
              {project.screens.map((item, itemIndex) => (
                <li key={item.file}>
                  <button
                    type="button"
                    className={styles.screenTab}
                    onClick={() => goTo(itemIndex)}
                    aria-current={itemIndex === index ? 'true' : undefined}>
                    <span className={styles.screenNumber}>{itemIndex + 1}</span>
                    <span>{item.name}</span>
                  </button>
                  {itemIndex === index && (
                    <p className={styles.screenCaption} aria-live="polite">
                      {item.caption}
                    </p>
                  )}
                </li>
              ))}
            </ol>
          </div>

          <div
            className={styles.viewer}
            style={{ '--direction': direction }}
            role="group"
            aria-roledescription="carousel"
            aria-label={translate(
              { id: 'page.showcase.chapter.carouselLabel', message: '{name} screens' },
              { name: project.name },
            )}
            onTouchStart={onTouchStart}
            onTouchEnd={onTouchEnd}>
            {isMobile ? (
              <>
                <div className={styles.phones}>
                  {[-1, 0, 1].map((offset) => {
                    const shownIndex = (index + offset + count) % count;
                    const shown = project.screens[shownIndex];
                    const shownImage = getImage(shown);
                    const active = offset === 0;
                    return (
                      <button
                        key={offset}
                        type="button"
                        className={`${styles.phoneShot} ${active ? styles.phoneShotActive : ''}`}
                        onClick={active ? openFull : () => goTo(shownIndex)}
                        aria-label={
                          active
                            ? translate(
                                {
                                  id: 'page.showcase.chapter.phoneOpen',
                                  message: 'Open {name} full size',
                                },
                                { name: shown.name },
                              )
                            : translate(
                                { id: 'page.showcase.chapter.phoneShow', message: 'Show {name}' },
                                { name: shown.name },
                              )
                        }>
                        <img
                          key={shownImage.full}
                          className={moved ? styles.shotIn : undefined}
                          src={shownImage.full}
                          width={project.size.width}
                          height={project.size.height}
                          alt={shownImage.alt}
                          loading="lazy"
                          decoding="async"
                        />
                      </button>
                    );
                  })}
                </div>
                <div className={styles.phoneControls}>{stepButtons}</div>
              </>
            ) : (
              <div className={styles.window}>
                <div className={styles.windowBar}>
                  <span className={styles.windowDots} aria-hidden="true">
                    <i />
                    <i />
                    <i />
                  </span>
                  <p className={styles.windowTitle}>
                    {project.name}
                    <span>{screen.name}</span>
                  </p>
                  <div className={styles.windowControls}>{stepButtons}</div>
                </div>
                <button
                  type="button"
                  className={styles.windowShot}
                  onClick={openFull}
                  aria-label={translate(
                    { id: 'page.showcase.chapter.windowOpen', message: 'Open {name} full size' },
                    { name: screen.name },
                  )}>
                  {moved && (
                    <img
                      key={`out-${project.screens[leaving].file}`}
                      className={styles.shotOut}
                      src={getImage(project.screens[leaving]).thumb}
                      srcSet={getImage(project.screens[leaving]).srcSet}
                      sizes={DESKTOP_SIZES}
                      width={project.size.width}
                      height={project.size.height}
                      alt={getImage(project.screens[leaving]).alt}
                      aria-hidden="true"
                    />
                  )}
                  <img
                    key={image.full}
                    className={moved ? styles.shotIn : undefined}
                    src={image.thumb}
                    srcSet={image.srcSet}
                    sizes={DESKTOP_SIZES}
                    width={project.size.width}
                    height={project.size.height}
                    alt={image.alt}
                    loading={number === 1 ? 'eager' : 'lazy'}
                    decoding="async"
                  />
                </button>
              </div>
            )}
          </div>
        </div>

        <div className={styles.features}>
          <h3 className={styles.featuresTitle}>
            <Translate id="page.showcase.chapter.shows">What it shows in webforJ</Translate>
          </h3>
          <ul className={styles.featureList}>
            {project.features.map((feature) => (
              <li key={feature.title}>
                <div className={styles.feature}>
                  <span className={styles.featureName}>{feature.title}</span>
                  <span className={styles.featureText}>{feature.text}</span>
                </div>
              </li>
            ))}
          </ul>
        </div>
      </div>

      <Lightbox project={project} index={index} onChange={goTo} onClose={close} open={open} />
    </section>
  );
}

function BusinessCall() {
  return (
    <section className={styles.business} aria-labelledby="showcase-business">
      <div className={styles.container}>
        <div className={styles.businessPanel}>
          <div>
            <h2 id="showcase-business" className={`${styles.sectionTitle} ${styles.businessTitle}`}>
              <Translate id="page.showcase.business.title">
                Planning a business app with webforJ?
              </Translate>
            </h2>
            <p className={styles.businessText}>
              <Translate id="page.showcase.business.text">
                {
                  'Book a call with the team that builds webforJ. Bring your app, your backend and your timeline, and we will show you how webforJ fits, from the first screen to LTS+ support.'
                }
              </Translate>
            </p>
          </div>
          <div className={styles.businessSide}>
            <div className={styles.actions}>
              <a
                className={`${styles.button} ${styles.buttonOnBrand}`}
                href={CONTACT_URL}
                target="_blank"
                rel="noopener noreferrer">
                <Translate id="page.showcase.business.book">Book a call</Translate>
                <ArrowIcon className={styles.icon} />
              </a>
              <a
                className={`${styles.button} ${styles.buttonOnBrandGhost}`}
                href="https://webforj.com/pricing/"
                target="_blank"
                rel="noopener noreferrer">
                <Translate id="page.showcase.business.pricing">See pricing</Translate>
              </a>
            </div>
            <p className={styles.businessLanguages}>
              <Translate
                id="page.showcase.business.languages"
                values={{ languages: TEAM_LANGUAGES.map((language) => language.name).join(', ') }}>
                {'We speak {languages}'}
              </Translate>
            </p>
          </div>
        </div>
      </div>
    </section>
  );
}

function Closing() {
  return (
    <section className={styles.closing} aria-labelledby="showcase-start">
      <div className={`${styles.container} ${styles.closingInner}`}>
        <div>
          <h2 id="showcase-start" className={`${styles.sectionTitle} ${styles.closingTitle}`}>
            <Translate id="page.showcase.closing.title">
              Build your first screen with startforJ
            </Translate>
          </h2>
          <p className={styles.closingText}>
            <Translate id="page.showcase.closing.text">
              {
                'Name your app, pick a theme color and an archetype, then download a project with the layout and dependencies in place. The Table, Dialog, Drawer and Upload from these screens are ready to use.'
              }
            </Translate>
          </p>
        </div>
        <div className={styles.actions}>
          <a className={`${styles.button} ${styles.buttonOnBrand}`} href={START_URL}>
            <Translate id="page.showcase.closing.open">Open startforJ</Translate>
            <ArrowIcon className={styles.icon} />
          </a>
          <Link
            className={`${styles.button} ${styles.buttonOnBrandGhost}`}
            to="/docs/introduction/getting-started">
            <Translate id="page.showcase.closing.read">Read getting started</Translate>
          </Link>
        </div>
      </div>
      <div className={styles.container}>
        <p className={styles.footnote}>
          <Translate id="page.showcase.closing.footnote">
            The apps on this page are examples that show what you can build with webforJ components.
          </Translate>
        </p>
      </div>
    </section>
  );
}

export default function Showcase() {
  const projects = getProjects();
  const sections = [
    <Hero key="hero" />,
    <Gallery key="gallery" />,
    ...projects.map((project, index) => (
      <Project key={project.slug} project={project} number={index + 1} />
    )),
    <BusinessCall key="business" />,
    <Closing key="closing" />,
  ];
  return (
    <Layout
      title={translate({
        id: 'page.showcase.meta.title',
        message: 'What you can build with webforJ: Java business app examples',
      })}
      description={translate({
        id: 'page.showcase.meta.description',
        message:
          'Example business apps for dispatch, field inspection, clinic reception and invoice approval, made from webforJ components. Step through every screen and the webforJ features behind it.',
      })}>
      <main className={styles.page}>
        <SectionIndicator />
        {sections.map((section) => (
          <div key={section.key} data-showcase-step className={styles.step}>
            {section}
          </div>
        ))}
      </main>
    </Layout>
  );
}
