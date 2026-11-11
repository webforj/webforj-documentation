import React, { useCallback, useEffect, useRef, useState } from 'react';
import Layout from '@theme/Layout';
import Link from '@docusaurus/Link';
import useBaseUrl from '@docusaurus/useBaseUrl';
import CodeBlock from '@theme/CodeBlock';
import Translate, { translate } from '@docusaurus/Translate';
import CodeSwatches from '@site/src/components/codeSwatch';
import { ArrowIcon, ChevronIcon, ExternalIcon } from '@site/src/components/pageKit/icons';
import {
  getPalettes,
  getSurfaces,
  STEPS,
  FONT_SIZES,
  FONT_WEIGHTS,
  SPACES,
  SIZES,
  RADII,
  SHADOWS,
} from './_tokens';
import { getProjects } from '../showcase/_projects';
import styles from './design-kit.module.css';

const KIT_URL = 'https://www.figma.com/community/file/1682060886525639971/dwc-design-kit';

// Copied from the themes page and the design system post.
const SEED_CSS = `:root {
  --dwc-color-primary-seed: #2563eb;
}

html[data-app-theme="dark"] {
  --dwc-color-primary-seed: #a855f7;
}`;

const THEME_JAVA = `@AppTheme("system")
@AppLightTheme("light")
@AppDarkTheme("dark")
class MyApp extends App {
  // app code
}`;

// Every screen of the four showcase apps, in the order of the showcase.
function getSlides() {
  return getProjects().flatMap((project) =>
    project.screens.map((screen) => ({
      slug: project.slug,
      app: project.name,
      phone: project.platform === 'Mobile',
      file: screen.file,
      name: screen.name,
      caption: screen.caption,
      size: project.size,
    })),
  );
}

function getCollections() {
  return [
    {
      id: 'tokens-color',
      label: translate({ id: 'page.designKit.collections.color', message: 'Color' }),
    },
    {
      id: 'tokens-type',
      label: translate({ id: 'page.designKit.collections.type', message: 'Typography' }),
    },
    {
      id: 'tokens-space',
      label: translate({ id: 'page.designKit.collections.space', message: 'Spacing and sizing' }),
    },
    {
      id: 'tokens-radius',
      label: translate({ id: 'page.designKit.collections.radius', message: 'Radius' }),
    },
    {
      id: 'tokens-depth',
      label: translate({ id: 'page.designKit.collections.depth', message: 'Shadows and surfaces' }),
    },
  ];
}

function getHandoff() {
  return [
    {
      design: translate({ id: 'page.designKit.handoff.color.design', message: 'A brand color' }),
      code: '--dwc-color-primary-seed',
      note: translate({
        id: 'page.designKit.handoff.color.note',
        message: 'All 19 steps of the palette are generated from it.',
      }),
    },
    {
      design: translate({ id: 'page.designKit.handoff.type.design', message: 'A type scale' }),
      code: '--dwc-font-size-{size}',
      note: translate({
        id: 'page.designKit.handoff.type.note',
        message: 'Sizes in rem, m is the standard.',
      }),
    },
    {
      design: translate({ id: 'page.designKit.handoff.space.design', message: 'A spacing rhythm' }),
      code: '--dwc-space-{size}',
      note: translate({
        id: 'page.designKit.handoff.space.note',
        message: 'Room between components, in rem.',
      }),
    },
    {
      design: translate({ id: 'page.designKit.handoff.theme.design', message: 'Light and dark' }),
      code: '@AppTheme("system")',
      note: translate({
        id: 'page.designKit.handoff.theme.note',
        message: 'Follows the OS preference.',
      }),
    },
  ];
}

// The three actors when building with AI: the Figma MCP server reads the
// design, the webforJ MCP server checks the components and tokens, and the
// assistant writes the Java.
function getAiSteps() {
  return [
    {
      who: 'Figma MCP server',
      title: translate({ id: 'page.designKit.ai.step1.title', message: 'Reads the design' }),
      text: translate({
        id: 'page.designKit.ai.step1.text',
        message:
          'It hands your assistant the frame you point at: its layout, its components and its variables. The variables in the kit carry the DWC token names, so the design already speaks the language of the code.',
      }),
      sample: 'var(--dwc-space-s)',
    },
    {
      who: 'webforJ MCP server',
      title: translate({
        id: 'page.designKit.ai.step2.title',
        message: 'Checks it against webforJ',
      }),
      text: translate({
        id: 'page.designKit.ai.step2.text',
        message:
          'It answers from the real framework: which component to use, what its styling surface is, and whether every token in the CSS exists.',
      }),
      sample: 'styles_validate_tokens',
    },
    {
      who: translate({ id: 'page.designKit.ai.step3.who', message: 'Your assistant' }),
      title: translate({
        id: 'page.designKit.ai.step3.title',
        message: 'Writes the screen in Java',
      }),
      text: translate({
        id: 'page.designKit.ai.step3.text',
        message:
          'With both connected, it builds the view from webforJ components and the same tokens the designer used, and you review the result against the frame.',
      }),
      sample: 'OrdersView.java',
    },
  ];
}

function getNext() {
  return [
    {
      to: '/docs/styling/overview',
      title: translate({ id: 'page.designKit.next.overview.title', message: 'Styling overview' }),
      text: translate({
        id: 'page.designKit.next.overview.text',
        message: 'The DWC design system and the kit.',
      }),
    },
    {
      to: '/docs/styling/themes',
      title: translate({ id: 'page.designKit.next.themes.title', message: 'Themes' }),
      text: translate({
        id: 'page.designKit.next.themes.text',
        message: 'The light, dark and dark-pure themes, and how to override them.',
      }),
    },
    {
      to: '/docs/styling/colors',
      title: translate({ id: 'page.designKit.next.colors.title', message: 'Colors' }),
      text: translate({
        id: 'page.designKit.next.colors.text',
        message: 'Seven palettes, 19 steps each, from one seed.',
      }),
    },
    {
      to: '/docs/styling/typography',
      title: translate({ id: 'page.designKit.next.typography.title', message: 'Typography' }),
      text: translate({
        id: 'page.designKit.next.typography.text',
        message: 'Font family, sizes, weights and line heights.',
      }),
    },
    {
      to: '/docs/styling/sizing-and-spacing',
      title: translate({
        id: 'page.designKit.next.sizingAndSpacing.title',
        message: 'Sizing and spacing',
      }),
      text: translate({
        id: 'page.designKit.next.sizingAndSpacing.text',
        message: 'The rem scales for size and space.',
      }),
    },
    {
      to: '/docs/styling/borders',
      title: translate({ id: 'page.designKit.next.borders.title', message: 'Borders' }),
      text: translate({
        id: 'page.designKit.next.borders.text',
        message: 'Widths, colors and a radius scale from one seed.',
      }),
    },
    {
      to: '/docs/styling/shadows',
      title: translate({ id: 'page.designKit.next.shadows.title', message: 'Shadows' }),
      text: translate({
        id: 'page.designKit.next.shadows.text',
        message: 'Six steps that adapt to light and dark.',
      }),
    },
    {
      to: '/docs/styling/surfaces',
      title: translate({ id: 'page.designKit.next.surfaces.title', message: 'Surfaces' }),
      text: translate({
        id: 'page.designKit.next.surfaces.text',
        message: 'Three levels for the UI hierarchy.',
      }),
    },
    {
      to: '/docs/styling/css-variables',
      title: translate({ id: 'page.designKit.next.cssVariables.title', message: 'CSS variables' }),
      text: translate({
        id: 'page.designKit.next.cssVariables.text',
        message: 'How tokens are set and overridden.',
      }),
    },
  ];
}

// Shortens long decimals in a resolved value to three places:
// oklch(0.49 0.228191 263.52614) reads as oklch(0.49 0.228 263.526).
function tidy(value) {
  return value
    .replace(/\d+\.\d+/g, (n) => String(Number(Number(n).toFixed(3))))
    .replace(/\s+/g, ' ')
    .trim();
}

function countLayers(shadow) {
  if (!shadow || shadow === 'none') return 0;
  return shadow.split(/,(?![^(]*\))/).length;
}

// Bumps a counter whenever the site theme changes, so live values are read
// again after light and dark switch.
function useThemeTick() {
  const [tick, setTick] = useState(0);
  useEffect(() => {
    const observer = new MutationObserver(() => setTick((count) => count + 1));
    observer.observe(document.documentElement, {
      attributes: true,
      attributeFilter: ['data-theme', 'data-app-theme'],
    });
    return () => observer.disconnect();
  }, []);
  return tick;
}

// Reads one computed property from the element in the ref after the first
// render and after each theme change.
function useResolved(ref, read, tick) {
  const [value, setValue] = useState('');
  useEffect(() => {
    if (!ref.current) return;
    setValue(read(getComputedStyle(ref.current)));
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [tick]);
  return value;
}

function SlideImage({ slide, eager }) {
  const base = useBaseUrl(`/img/showcase/${slide.slug}/`);
  const full = `${base}${slide.file}.webp`;
  const thumb = `${base}${slide.file}-thumb.webp`;
  return (
    <img
      key={full}
      src={full}
      srcSet={`${thumb} ${slide.size.thumb}w, ${full} ${slide.size.full}w`}
      sizes={slide.phone ? '340px' : '(min-width: 1240px) 1136px, 92vw'}
      width={slide.size.width}
      height={slide.size.height}
      alt={translate(
        {
          id: 'page.designKit.carousel.slideAlt',
          message: '{name} screen of the {app} app. {caption}',
        },
        { name: slide.name, app: slide.app, caption: slide.caption },
      )}
      fetchpriority={eager ? 'high' : undefined}
      decoding="async"
      draggable={false}
    />
  );
}

// One screen at a time in a fixed 16:10 window. Phone screens stand in a
// phone frame inside the same window, so the height never changes.
function ScreenCarousel() {
  const [index, setIndex] = useState(0);
  const slides = getSlides();
  const total = slides.length;
  const slide = slides[index];
  const touch = useRef(null);
  const showcaseBase = useBaseUrl('/img/showcase/');

  const step = useCallback((delta) => setIndex((i) => (i + delta + total) % total), [total]);

  // Load the neighbours ahead of time, so stepping does not flash.
  useEffect(() => {
    [slides[(index + 1) % total], slides[(index - 1 + total) % total]].forEach((neighbour) => {
      const img = new Image();
      img.src = `${showcaseBase}${neighbour.slug}/${neighbour.file}${neighbour.phone ? '' : '-thumb'}.webp`;
    });
  }, [index, total, showcaseBase]);

  const onKeyDown = (e) => {
    if (e.key === 'ArrowRight') {
      e.preventDefault();
      step(1);
    } else if (e.key === 'ArrowLeft') {
      e.preventDefault();
      step(-1);
    }
  };

  const onTouchStart = (e) => {
    const point = e.touches[0];
    touch.current = { x: point.clientX, y: point.clientY };
  };

  const onTouchEnd = (e) => {
    if (!touch.current) return;
    const point = e.changedTouches[0];
    const dx = point.clientX - touch.current.x;
    const dy = point.clientY - touch.current.y;
    touch.current = null;
    if (Math.abs(dx) > 40 && Math.abs(dx) > Math.abs(dy)) step(dx < 0 ? 1 : -1);
  };

  return (
    <section
      className={styles.carousel}
      aria-roledescription="carousel"
      aria-label={translate({
        id: 'page.designKit.carousel.label',
        message: 'App screens designed with the kit',
      })}
      tabIndex={0}
      onKeyDown={onKeyDown}>
      <div className={styles.window}>
        <div className={styles.windowBar} aria-hidden="true">
          <span className={styles.dots}>
            <span />
            <span />
            <span />
          </span>
          <span className={styles.windowTitle}>
            {slide.app} / {slide.name}
          </span>
        </div>
        <div
          className={styles.stage}
          onTouchStart={onTouchStart}
          onTouchEnd={onTouchEnd}
          aria-roledescription="slide"
          aria-label={translate(
            { id: 'page.designKit.carousel.position', message: '{n} of {total}' },
            { n: index + 1, total },
          )}>
          {slide.phone ? (
            <span className={styles.phone}>
              <SlideImage slide={slide} eager={index === 0} />
            </span>
          ) : (
            <SlideImage slide={slide} eager={index === 0} />
          )}
        </div>
      </div>
      <div className={styles.carouselBar}>
        <button
          type="button"
          className={styles.stepButton}
          onClick={() => step(-1)}
          aria-label={translate({
            id: 'page.designKit.carousel.previous',
            message: 'Previous screen',
          })}>
          <ChevronIcon className={styles.icon} direction="left" />
        </button>
        <p className={styles.carouselText} aria-live="polite">
          <span className={styles.carouselCount}>
            <Translate id="page.designKit.carousel.count" values={{ n: index + 1, total }}>
              {'{n} of {total}'}
            </Translate>
          </span>
          <span className={styles.carouselName}>
            <strong>{slide.app}</strong>
            <span>{slide.name}</span>
          </span>
        </p>
        <button
          type="button"
          className={styles.stepButton}
          onClick={() => step(1)}
          aria-label={translate({ id: 'page.designKit.carousel.next', message: 'Next screen' })}>
          <ChevronIcon className={styles.icon} />
        </button>
      </div>
    </section>
  );
}

function GroupHead({ title, pattern, text }) {
  return (
    <div className={styles.groupHead}>
      <h3 className={styles.groupTitle}>{title}</h3>
      <code className={styles.groupPattern}>{pattern}</code>
      <p className={styles.groupText}>{text}</p>
    </div>
  );
}

function Swatch({ palette, step, selected, onSelect }) {
  const name = `--dwc-color-${palette}-${step}`;
  return (
    <button
      type="button"
      className={styles.swatch}
      style={{ background: `var(${name})` }}
      aria-label={name}
      aria-pressed={selected}
      title={name}
      onClick={() => onSelect({ palette, step })}
      onMouseEnter={() => onSelect({ palette, step })}
      onFocus={() => onSelect({ palette, step })}
    />
  );
}

function ColorReadout({ palette, step, tick }) {
  const name = `--dwc-color-${palette}-${step}`;
  const onText = `--dwc-color-on-${palette}-text-${step}`;
  const chip = useRef(null);
  const [value, setValue] = useState('');
  useEffect(() => {
    if (chip.current) setValue(tidy(getComputedStyle(chip.current).backgroundColor));
  }, [palette, step, tick]);
  return (
    <div className={styles.readout} aria-live="polite">
      <div
        ref={chip}
        className={styles.readoutChip}
        style={{ background: `var(${name})`, color: `var(${onText})` }}>
        Aa
      </div>
      <dl className={styles.readoutList}>
        <div>
          <dt>
            <Translate id="page.designKit.readout.token">Token</Translate>
          </dt>
          <dd>
            <code>{name}</code>
          </dd>
        </div>
        <div>
          <dt>
            <Translate id="page.designKit.readout.value">Resolved value</Translate>
          </dt>
          <dd>
            <code>{value || ' '}</code>
          </dd>
        </div>
        <div>
          <dt>
            <Translate id="page.designKit.readout.text">Text on it</Translate>
          </dt>
          <dd>
            <code>{onText}</code>
          </dd>
        </div>
      </dl>
    </div>
  );
}

function Colors({ tick }) {
  const palettes = getPalettes();
  const [selected, setSelected] = useState({ palette: 'primary', step: 50 });
  const onSelect = useCallback((next) => setSelected(next), []);
  return (
    <div className={styles.group} id="tokens-color">
      <GroupHead
        title={translate({ id: 'page.designKit.color.title', message: 'Color' })}
        pattern="--dwc-color-{palette}-{step}"
        text={translate({
          id: 'page.designKit.color.text',
          message:
            'Seven palettes of 19 steps, each generated from one seed. Step 5 is always the darkest and step 95 the lightest. Pick a swatch to inspect it.',
        })}
      />
      <ColorReadout palette={selected.palette} step={selected.step} tick={tick} />
      <div className={styles.palettes}>
        <div className={styles.stepScale} aria-hidden="true">
          <span />
          <span className={styles.stepNums}>
            {STEPS.map((step) => (
              <span key={step}>{step}</span>
            ))}
          </span>
        </div>
        {palettes.map((palette) => (
          <div className={styles.paletteRow} key={palette.name}>
            <div className={styles.paletteName}>
              <strong>{palette.name}</strong>
              <span>{palette.note}</span>
            </div>
            <div className={styles.paletteSteps}>
              {STEPS.map((step) => (
                <Swatch
                  key={step}
                  palette={palette.name}
                  step={step}
                  selected={selected.palette === palette.name && selected.step === step}
                  onSelect={onSelect}
                />
              ))}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

function FontSizeRow({ size, tick }) {
  const ref = useRef(null);
  const px = useResolved(ref, (cs) => cs.fontSize, tick);
  return (
    <li className={styles.typeRow}>
      <code className={styles.tokenName}>--dwc-font-size-{size}</code>
      <span
        ref={ref}
        className={styles.typeSample}
        style={{ fontSize: `var(--dwc-font-size-${size})` }}>
        {translate({ id: 'page.designKit.type.sample', message: 'Assign driver' })}
      </span>
      <span className={styles.tokenValue}>{px}</span>
    </li>
  );
}

function WeightCell({ weight, tick }) {
  const ref = useRef(null);
  const value = useResolved(ref, (cs) => cs.fontWeight, tick);
  return (
    <li className={styles.tokenCell}>
      <span
        ref={ref}
        className={styles.weightSample}
        style={{ fontWeight: `var(--dwc-font-weight-${weight})` }}>
        Ag
      </span>
      <code className={styles.tokenName}>{weight}</code>
      <span className={styles.tokenValue}>{value}</span>
    </li>
  );
}

function Typography({ tick }) {
  return (
    <div className={styles.group} id="tokens-type">
      <GroupHead
        title={translate({ id: 'page.designKit.type.title', message: 'Typography' })}
        pattern="--dwc-font-size-{size}"
        text={translate({
          id: 'page.designKit.type.text',
          message:
            'A size scale in rem, where m is the standard size most components use, and a weight scale from thin to black.',
        })}
      />
      <ul className={styles.rowList}>
        {FONT_SIZES.map((size) => (
          <FontSizeRow key={size} size={size} tick={tick} />
        ))}
      </ul>
      <ul className={styles.cellGrid}>
        {FONT_WEIGHTS.map((weight) => (
          <WeightCell key={weight} weight={weight} tick={tick} />
        ))}
      </ul>
    </div>
  );
}

function SpaceRow({ name, tick }) {
  const ref = useRef(null);
  const px = useResolved(ref, (cs) => `${Math.round(parseFloat(cs.width) / 4)}px`, tick);
  return (
    <li className={styles.spaceRow}>
      <code className={styles.tokenName}>{name}</code>
      <span className={styles.spaceTrack}>
        <span ref={ref} className={styles.spaceBar} style={{ width: `calc(var(${name}) * 4)` }} />
      </span>
      <span className={styles.tokenValue}>{px}</span>
    </li>
  );
}

function SizeCell({ size, tick }) {
  const ref = useRef(null);
  const px = useResolved(ref, (cs) => cs.height, tick);
  return (
    <li className={styles.tokenCell}>
      <span className={styles.sizeStage}>
        <span
          ref={ref}
          className={styles.sizeBox}
          style={{ width: `var(--dwc-size-${size})`, height: `var(--dwc-size-${size})` }}
        />
      </span>
      <code className={styles.tokenName}>{size}</code>
      <span className={styles.tokenValue}>{px}</span>
    </li>
  );
}

function Spacing({ tick }) {
  return (
    <div className={styles.group} id="tokens-space">
      <GroupHead
        title={translate({ id: 'page.designKit.space.title', message: 'Spacing and sizing' })}
        pattern="--dwc-space-{size}  --dwc-size-{size}"
        text={translate({
          id: 'page.designKit.space.text',
          message:
            'Space sets the room between components. Size sets the width and height of components, with m the standard for almost all of them. The space bars are drawn at four times their size.',
        })}
      />
      <ul className={styles.rowList}>
        {SPACES.map((space) => (
          <SpaceRow key={space} name={`--dwc-space-${space}`} tick={tick} />
        ))}
      </ul>
      <ul className={styles.cellGrid}>
        {SIZES.map((size) => (
          <SizeCell key={size} size={size} tick={tick} />
        ))}
      </ul>
    </div>
  );
}

function RadiusCell({ size, tick }) {
  const ref = useRef(null);
  const px = useResolved(ref, (cs) => cs.borderTopLeftRadius, tick);
  return (
    <li className={styles.tokenCell}>
      <span
        ref={ref}
        className={styles.radiusBox}
        style={{ borderRadius: `var(--dwc-border-radius-${size})` }}
      />
      <code className={styles.tokenName}>{size}</code>
      <span className={styles.tokenValue}>{px}</span>
    </li>
  );
}

function Radius({ tick }) {
  return (
    <div className={styles.group} id="tokens-radius">
      <GroupHead
        title={translate({ id: 'page.designKit.radius.title', message: 'Radius' })}
        pattern="--dwc-border-radius-{size}"
        text={translate({
          id: 'page.designKit.radius.text',
          message:
            'Every step scales from one seed, --dwc-border-radius-seed. Change the seed and the whole radius system follows.',
        })}
      />
      <ul className={styles.cellGrid}>
        {RADII.map((size) => (
          <RadiusCell key={size} size={size} tick={tick} />
        ))}
      </ul>
    </div>
  );
}

function ShadowCell({ size, tick }) {
  const ref = useRef(null);
  const layers = useResolved(ref, (cs) => countLayers(cs.boxShadow), tick);
  return (
    <li className={styles.shadowCell}>
      <span
        ref={ref}
        className={styles.shadowCard}
        style={{ boxShadow: `var(--dwc-shadow-${size})` }}>
        <code className={styles.tokenName}>{size}</code>
        <span className={styles.tokenValue}>
          {layers === ''
            ? ' '
            : layers === 1
              ? translate(
                  { id: 'page.designKit.shadow.layer', message: '{count} layer' },
                  { count: layers },
                )
              : translate(
                  { id: 'page.designKit.shadow.layers', message: '{count} layers' },
                  { count: layers },
                )}
        </span>
      </span>
    </li>
  );
}

function SurfaceLayer({ surface, depth, tick, children }) {
  const ref = useRef(null);
  const value = useResolved(ref, (cs) => tidy(cs.backgroundColor), tick);
  return (
    <div
      ref={ref}
      className={styles.surface}
      data-depth={depth}
      style={{ background: `var(${surface.name})` }}>
      <div className={styles.surfaceLabel}>
        <code className={styles.tokenName}>{surface.name}</code>
        <span>{surface.usage}</span>
        <span className={styles.tokenValue}>{value}</span>
      </div>
      {children}
    </div>
  );
}

function Depth({ tick }) {
  const surfaces = getSurfaces();
  return (
    <div className={styles.group} id="tokens-depth">
      <GroupHead
        title={translate({ id: 'page.designKit.depth.title', message: 'Shadows and surfaces' })}
        pattern="--dwc-shadow-{size}  --dwc-surface-{level}"
        text={translate({
          id: 'page.designKit.depth.text',
          message:
            'Shadows show what is layered on top. Three surfaces organize the hierarchy, from the page to the dialog. Both adapt to light and dark mode.',
        })}
      />
      <ul className={styles.shadows}>
        {SHADOWS.map((size) => (
          <ShadowCell key={size} size={size} tick={tick} />
        ))}
      </ul>
      <SurfaceLayer surface={surfaces[0]} depth={1} tick={tick}>
        <SurfaceLayer surface={surfaces[1]} depth={2} tick={tick}>
          <SurfaceLayer surface={surfaces[2]} depth={3} tick={tick} />
        </SurfaceLayer>
      </SurfaceLayer>
    </div>
  );
}

function Hero() {
  return (
    <header className={styles.hero} id="design-kit-top">
      <div className={styles.container}>
        <div className={styles.heroText}>
          <div>
            <p className={styles.eyebrow}>
              <Translate id="page.designKit.hero.eyebrow">Figma design kit</Translate>
            </p>
            <h1 className={styles.heroTitle}>
              <Translate id="page.designKit.hero.title1">Design with the same tokens</Translate>{' '}
              <span>
                <Translate id="page.designKit.hero.title2">your app runs on</Translate>
              </span>
            </h1>
          </div>
          <div>
            <p className={styles.heroLead}>
              <Translate id="page.designKit.hero.lead">
                The DWC Design Kit is the official Figma resource for designing webforJ apps, so
                designers and developers work from the same tokens and components.
              </Translate>
            </p>
            <div className={styles.actions}>
              <a
                className={`${styles.button} ${styles.buttonPrimary}`}
                href={KIT_URL}
                target="_blank"
                rel="noopener noreferrer">
                <Translate id="page.designKit.hero.openKit">Open the kit in Figma</Translate>
                <ExternalIcon className={styles.icon} />
              </a>
              <Link
                className={`${styles.button} ${styles.buttonSecondary}`}
                to="/docs/styling/overview">
                <Translate id="page.designKit.hero.styling">Read the styling guide</Translate>
              </Link>
            </div>
          </div>
        </div>
        <ScreenCarousel />
        <dl className={styles.facts}>
          <div>
            <dt>
              <Translate id="page.designKit.facts.components.term">Components</Translate>
            </dt>
            <dd>
              <Translate id="page.designKit.facts.components.text">
                Every DWC component, with its themes, expanses and states
              </Translate>
            </dd>
          </div>
          <div>
            <dt>
              <Translate id="page.designKit.facts.tokens.term">Tokens</Translate>
            </dt>
            <dd>
              <Translate id="page.designKit.facts.tokens.text">
                Palettes, typography, spacing and shadows as Figma variables and styles
              </Translate>
            </dd>
          </div>
          <div>
            <dt>
              <Translate id="page.designKit.facts.modes.term">Modes</Translate>
            </dt>
            <dd>
              <Translate id="page.designKit.facts.modes.text">
                Light and dark, as the app renders them in webforJ 26 and later
              </Translate>
            </dd>
          </div>
        </dl>
      </div>
    </header>
  );
}

function Tokens() {
  const tick = useThemeTick();
  return (
    <section className={styles.tokens} aria-labelledby="design-kit-tokens">
      <div className={styles.container}>
        <div className={styles.sectionHead}>
          <p className={styles.kicker}>
            <Translate id="page.designKit.tokens.kicker">Variables</Translate>
          </p>
          <h2 id="design-kit-tokens" className={styles.sectionTitle}>
            <Translate id="page.designKit.tokens.title1">The values in the kit</Translate>{' '}
            <span>
              <Translate id="page.designKit.tokens.title2">are the values in the code</Translate>
            </span>
          </h2>
          <p className={styles.sectionLead}>
            <Translate id="page.designKit.tokens.lead" values={{ code: <code>--dwc-*</code> }}>
              {
                "The kit holds the DWC tokens as Figma variables. In the app they are CSS custom properties. Everything below is drawn from the {code} variables this page runs on, with the value the browser resolves for each. Switch the site to dark mode and they follow, the way the kit's dark mode does."
              }
            </Translate>
          </p>
        </div>
        <div className={styles.inspector}>
          <nav
            className={styles.collections}
            aria-label={translate({
              id: 'page.designKit.tokens.collectionsLabel',
              message: 'Token collections',
            })}>
            <p className={styles.collectionsTitle}>
              <Translate id="page.designKit.tokens.collections">Collections</Translate>
            </p>
            <ol>
              {getCollections().map((collection) => (
                <li key={collection.id}>
                  <a href={`#${collection.id}`}>{collection.label}</a>
                </li>
              ))}
            </ol>
          </nav>
          <div className={styles.groups}>
            <Colors tick={tick} />
            <Typography tick={tick} />
            <Spacing tick={tick} />
            <Radius tick={tick} />
            <Depth tick={tick} />
          </div>
        </div>
      </div>
    </section>
  );
}

function KitToApp() {
  return (
    <section className={styles.kitToApp} aria-labelledby="design-kit-apps">
      <div className={styles.container}>
        <div className={styles.kitToAppHead}>
          <div>
            <p className={styles.kicker}>
              <Translate id="page.designKit.kitToApp.kicker">From kit to app</Translate>
            </p>
            <h2 id="design-kit-apps" className={styles.sectionTitle}>
              <Translate id="page.designKit.kitToApp.title1">Designed with the kit,</Translate>{' '}
              <span>
                <Translate id="page.designKit.kitToApp.title2">
                  built from webforJ components
                </Translate>
              </span>
            </h2>
          </div>
          <div>
            <p className={styles.kitToAppLead}>
              <Translate id="page.designKit.kitToApp.lead">
                The screens at the top of this page were designed with the kit. A screen designed
                with the kit uses the components and tokens the app renders, so the step from mockup
                to Java code does not translate one visual language into another.
              </Translate>
            </p>
            <Link className={`${styles.button} ${styles.buttonSecondary}`} to="/showcase">
              <Translate id="page.designKit.kitToApp.showcase">Step through the showcase</Translate>
              <ArrowIcon className={styles.icon} />
            </Link>
          </div>
        </div>
      </div>
    </section>
  );
}

function Together() {
  return (
    <section className={styles.together} aria-labelledby="design-kit-together">
      <div className={styles.container}>
        <div className={styles.sectionHead}>
          <p className={styles.kicker}>
            <Translate id="page.designKit.together.kicker">Designer and developer</Translate>
          </p>
          <h2 id="design-kit-together" className={styles.sectionTitle}>
            <Translate id="page.designKit.together.title1">One handoff,</Translate>{' '}
            <span>
              <Translate id="page.designKit.together.title2">mapped once</Translate>
            </span>
          </h2>
          <p className={styles.sectionLead}>
            <Translate id="page.designKit.together.lead">
              Tokens are the contract between design and code. A designer hands over a brand color
              and a type scale from the kit. The developer maps them to a seed and a font size token
              once, and every screen inherits them.
            </Translate>
          </p>
        </div>

        <ol className={styles.handoff}>
          {getHandoff().map((item) => (
            <li key={item.code}>
              <span className={styles.handoffDesign}>
                <span className={styles.overline}>
                  <Translate id="page.designKit.handoff.inFigma">In Figma</Translate>
                </span>
                {item.design}
              </span>
              <svg className={styles.handoffArrow} viewBox="0 0 40 12" aria-hidden="true">
                <path
                  d="M1 6h36m-5-4.5L37 6l-5 4.5"
                  fill="none"
                  stroke="currentColor"
                  strokeWidth="1.5"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                />
              </svg>
              <span className={styles.handoffCode}>
                <span className={styles.overline}>
                  <Translate id="page.designKit.handoff.inCode">In code</Translate>
                </span>
                <code>{item.code}</code>
                <span className={styles.handoffNote}>{item.note}</span>
              </span>
            </li>
          ))}
        </ol>

        <div className={styles.sides}>
          <div className={styles.side}>
            <h3 className={styles.sideTitle}>
              <Translate id="page.designKit.sides.kit.title">In the kit</Translate>
            </h3>
            <ul className={styles.sideList}>
              <li>
                <Translate id="page.designKit.sides.kit.item1">
                  Every DWC component as it looks in webforJ 26 and later, with its themes, expanses
                  and states.
                </Translate>
              </li>
              <li>
                <Translate id="page.designKit.sides.kit.item2">
                  The color palettes, typography, spacing and shadow tokens as Figma variables and
                  styles.
                </Translate>
              </li>
              <li>
                <Translate id="page.designKit.sides.kit.item3">
                  Light and dark mode for the components and the tokens.
                </Translate>
              </li>
              <li>
                <Translate id="page.designKit.sides.kit.item4">
                  Precise spacing and accessible color contrast, for interfaces with predictable
                  component behavior.
                </Translate>
              </li>
            </ul>
          </div>
          <div className={styles.side}>
            <h3 className={styles.sideTitle}>
              <Translate id="page.designKit.sides.app.title">In the app</Translate>
            </h3>
            <p className={styles.sideText}>
              <Translate id="page.designKit.sides.app.seed">
                Set the brand once as a seed. Buttons, links and focus rings pick it up because they
                use the primary palette.
              </Translate>
            </p>
            <div className={styles.code}>
              <CodeSwatches>
                <CodeBlock language="css" title="brand.css">
                  {SEED_CSS}
                </CodeBlock>
              </CodeSwatches>
            </div>
            <p className={styles.sideText}>
              <Translate id="page.designKit.sides.app.theme">
                {
                  "Register a light and a dark theme and let webforJ follow the user's operating system."
                }
              </Translate>
            </p>
            <div className={styles.code}>
              <CodeBlock language="java" title="MyApp.java">
                {THEME_JAVA}
              </CodeBlock>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

function WithAi() {
  return (
    <section className={styles.withAi} aria-labelledby="design-kit-ai">
      <div className={styles.container}>
        <div className={styles.sectionHead}>
          <p className={styles.kicker}>
            <Translate id="page.designKit.ai.kicker">Build with AI</Translate>
          </p>
          <h2 id="design-kit-ai" className={styles.sectionTitle}>
            <Translate id="page.designKit.ai.title1">From the Figma frame</Translate>{' '}
            <span>
              <Translate id="page.designKit.ai.title2">to the Java screen</Translate>
            </span>
          </h2>
          <p className={styles.sectionLead}>
            <Translate id="page.designKit.ai.lead">
              Connect two MCP servers to your coding assistant, the one from Figma and the one from
              webforJ. One reads the design, the other knows the framework, and the assistant works
              between them.
            </Translate>
          </p>
        </div>

        <ol className={styles.aiSteps}>
          {getAiSteps().map((step, index) => (
            <li key={step.who}>
              <span className={styles.aiWho}>
                <span className={styles.aiNumber}>0{index + 1}</span>
                {step.who}
              </span>
              <h3 className={styles.aiTitle}>{step.title}</h3>
              <p className={styles.aiText}>{step.text}</p>
              <code className={styles.aiSample}>{step.sample}</code>
            </li>
          ))}
        </ol>

        <div className={styles.aiPrompt}>
          <span className={styles.overline}>
            <Translate id="page.designKit.ai.promptLabel">A prompt to start from</Translate>
          </span>
          <p>
            <Translate id="page.designKit.ai.prompt">
              Build this Figma frame as a webforJ view. Read the frame with the Figma MCP server,
              pick the components with the webforJ MCP server and validate every --dwc-* token
              before you finish.
            </Translate>
          </p>
        </div>

        <div className={styles.actions}>
          <Link className={`${styles.button} ${styles.buttonPrimary}`} to="/docs/ai-tooling">
            <Translate id="page.designKit.ai.install">Install the AI plugin</Translate>
            <ArrowIcon className={styles.icon} />
          </Link>
          <Link className={`${styles.button} ${styles.buttonSecondary}`} to="/ai">
            <Translate id="page.designKit.ai.tooling">See the AI tooling</Translate>
          </Link>
        </div>
      </div>
    </section>
  );
}

function GetIt() {
  return (
    <section className={styles.closing} aria-labelledby="design-kit-start">
      <div className={styles.container}>
        <div className={styles.closingGrid}>
          <div>
            <p className={styles.kicker}>
              <Translate id="page.designKit.getIt.kicker">Get the kit</Translate>
            </p>
            <h2 id="design-kit-start" className={styles.sectionTitle}>
              <Translate id="page.designKit.getIt.title1">Start your next screen</Translate>{' '}
              <span>
                <Translate id="page.designKit.getIt.title2">in Figma</Translate>
              </span>
            </h2>
            <p className={styles.closingLead}>
              <Translate id="page.designKit.getIt.lead">
                The DWC Design Kit is on Figma Community. webforJ is the full-stack Java framework
                for business web apps: you build the whole app, screens included, in Java.
              </Translate>
            </p>
            <div className={styles.actions}>
              <a
                className={`${styles.button} ${styles.buttonPrimary}`}
                href={KIT_URL}
                target="_blank"
                rel="noopener noreferrer">
                <Translate id="page.designKit.getIt.openKit">Open the kit in Figma</Translate>
                <ExternalIcon className={styles.icon} />
              </a>
              <Link
                className={`${styles.button} ${styles.buttonSecondary}`}
                to="/docs/introduction/getting-started">
                <Translate id="page.designKit.getIt.started">Read getting started</Translate>
              </Link>
            </div>
          </div>
          <ol className={styles.steps}>
            <li>
              <span className={styles.stepNumber}>1</span>
              <div>
                <strong>
                  <Translate id="page.designKit.getIt.step1.title">Open the kit</Translate>
                </strong>
                <span>
                  <Translate id="page.designKit.getIt.step1.text">
                    Find the DWC Design Kit on Figma Community.
                  </Translate>
                </span>
              </div>
            </li>
            <li>
              <span className={styles.stepNumber}>2</span>
              <div>
                <strong>
                  <Translate id="page.designKit.getIt.step2.title">Design the screens</Translate>
                </strong>
                <span>
                  <Translate id="page.designKit.getIt.step2.text">
                    Use its components and variables, and check every screen in light and dark mode.
                  </Translate>
                </span>
              </div>
            </li>
            <li>
              <span className={styles.stepNumber}>3</span>
              <div>
                <strong>
                  <Translate id="page.designKit.getIt.step3.title">Build them in Java</Translate>
                </strong>
                <span>
                  <Translate id="page.designKit.getIt.step3.text">
                    Use the same components in webforJ and set your brand with one seed.
                  </Translate>
                </span>
              </div>
            </li>
          </ol>
        </div>
      </div>
    </section>
  );
}

function WhereNext() {
  return (
    <section className={styles.next} aria-labelledby="design-kit-next">
      <div className={styles.container}>
        <div className={styles.nextHead}>
          <h2 id="design-kit-next" className={styles.nextTitle}>
            <Translate id="page.designKit.next.title">Read the design system</Translate>
          </h2>
          <Link className={styles.homeLink} to="/">
            <Translate id="page.designKit.next.home">Back to the docs home</Translate>
            <ArrowIcon className={styles.icon} />
          </Link>
        </div>
        <ul className={styles.nextGrid}>
          {getNext().map((link) => (
            <li key={link.to}>
              <Link className={styles.nextCard} to={link.to}>
                <strong>{link.title}</strong>
                <span>{link.text}</span>
              </Link>
            </li>
          ))}
        </ul>
      </div>
    </section>
  );
}

export default function DesignKit() {
  return (
    <Layout
      title={translate({
        id: 'page.designKit.meta.title',
        message: 'DWC Design Kit for Figma: design webforJ apps',
      })}
      description={translate({
        id: 'page.designKit.meta.description',
        message:
          'The official Figma kit for webforJ. Every DWC component with its themes, expanses and states, plus the color, typography, spacing and shadow tokens as Figma variables in light and dark mode.',
      })}>
      <main className={styles.page}>
        <Hero />
        <Tokens />
        <KitToApp />
        <Together />
        <WithAi />
        <GetIt />
        <WhereNext />
      </main>
    </Layout>
  );
}
