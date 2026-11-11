import React, { useEffect, useRef, useState } from 'react';
import Layout from '@theme/Layout';
import Link from '@docusaurus/Link';
import Translate, { translate } from '@docusaurus/Translate';
import Head from '@docusaurus/Head';
import { CONTACT_URL, ISSUES_URL, TEAM_LANGUAGES } from '@site/src/components/connect/links';
import { ArrowIcon } from '@site/src/components/pageKit/icons';
import usePauseOffscreen from '@site/src/components/pageKit/usePauseOffscreen';
import styles from './home.module.css';

function getTitle() {
  return translate({
    id: 'page.home.meta.title',
    message: 'webforJ: the full-stack Java framework for business web apps',
  });
}

function getDescription() {
  return translate({
    id: 'page.home.meta.description',
    message:
      'Build the whole web app in Java, from UI to data, with any backend. AI-ready, with a screen-by-screen path from Swing to the web. Docs, guides and live demos.',
  });
}

// The app as a stack of layers, top to bottom. Each layer is one part of the
// docs. The drawing in the hero and the list further down show the same four.
// Built while rendering so the labels follow the page language.
function getLayers() {
  return [
    {
      key: 'screens',
      name: translate({ id: 'page.home.layers.screens.name', message: 'Screens' }),
      text: translate({
        id: 'page.home.layers.screens.text',
        message: 'Compose the UI from components, lay it out and move between pages.',
      }),
      links: [
        [
          translate({ id: 'page.home.layers.screens.link.components', message: 'Components' }),
          '/docs/components/overview',
        ],
        [
          translate({ id: 'page.home.layers.screens.link.buildingUi', message: 'Building UI' }),
          '/docs/building-ui/overview',
        ],
        [
          translate({ id: 'page.home.layers.screens.link.routing', message: 'Routing' }),
          '/docs/routing/overview',
        ],
        [
          translate({ id: 'page.home.layers.screens.link.styling', message: 'Styling' }),
          '/docs/styling/overview',
        ],
      ],
    },
    {
      key: 'logic',
      name: translate({ id: 'page.home.layers.logic.name', message: 'Logic' }),
      text: translate({
        id: 'page.home.layers.logic.text',
        message: 'Bind forms to your models, protect routes, translate and test.',
      }),
      links: [
        [
          translate({ id: 'page.home.layers.logic.link.dataBinding', message: 'Data binding' }),
          '/docs/data-binding/overview',
        ],
        [
          translate({ id: 'page.home.layers.logic.link.security', message: 'Security' }),
          '/docs/security/overview',
        ],
        [
          translate({ id: 'page.home.layers.logic.link.translation', message: 'Translation' }),
          '/docs/advanced/i18n-localization',
        ],
        [
          translate({ id: 'page.home.layers.logic.link.testing', message: 'Testing' }),
          '/docs/testing/overview',
        ],
      ],
    },
    {
      key: 'data',
      name: translate({ id: 'page.home.layers.data.name', message: 'Data' }),
      text: translate({
        id: 'page.home.layers.data.text',
        message: 'Fill tables from repositories and show the numbers in charts.',
      }),
      links: [
        [
          translate({ id: 'page.home.layers.data.link.repository', message: 'Repository' }),
          '/docs/advanced/repository/overview',
        ],
        [
          translate({ id: 'page.home.layers.data.link.table', message: 'Table' }),
          '/docs/components/table/overview',
        ],
        [
          translate({ id: 'page.home.layers.data.link.charts', message: 'Charts' }),
          '/docs/components/google-charts',
        ],
        [
          translate({
            id: 'page.home.layers.data.link.managingResources',
            message: 'Managing resources',
          }),
          '/docs/managing-resources/overview',
        ],
      ],
    },
    {
      key: 'backend',
      name: translate({ id: 'page.home.layers.backend.name', message: 'Your backend' }),
      text: translate({
        id: 'page.home.layers.backend.text',
        message: 'Run standalone or with Spring Boot, in Java or Kotlin, and ship it.',
      }),
      links: [
        ['Spring Boot', '/docs/integrations/spring/overview'],
        ['Kotlin DSL', '/docs/integrations/kotlin-dsl/overview'],
        [
          translate({
            id: 'page.home.layers.backend.link.configuration',
            message: 'Configuration',
          }),
          '/docs/configuration/overview',
        ],
        [
          translate({ id: 'page.home.layers.backend.link.upgrading', message: 'Upgrading' }),
          '/docs/upgrading/overview',
        ],
      ],
    },
  ];
}

// Real names from each layer, shown as notes beside the drawing.
const NOTES = {
  screens: 'AppLayout · Table · Dialog',
  logic: 'BindingContext · @RolesAllowed',
  data: 'Repository',
  backend: 'Spring Boot · Kotlin',
};

// The bars of the code drawn on the logic layer: width and indent level.
const CODE_BARS = [
  ['38%', 0],
  ['72%', 1],
  ['58%', 1],
  ['80%', 2],
  ['46%', 2],
  ['64%', 1],
  ['30%', 0],
];

// The GreetingView from the docs, line by line, with its colours set by
// hand so three lines can carry a number. Each part is a token kind and its
// text; an empty kind is plain text.
const CODE = [
  [
    ['a', '@Route('],
    ['s', '"greeting"'],
    ['a', ')'],
  ],
  [
    ['k', 'public class '],
    ['c', 'GreetingView'],
    ['k', ' extends '],
    ['c', 'Composite'],
    ['', '<'],
    ['c', 'Div'],
    ['', '> {'],
  ],
  [
    ['k', '  private final '],
    ['c', 'TextField'],
    ['', ' name = '],
    ['k', 'new '],
    ['c', 'TextField'],
    ['', '('],
    ['s', '"Name"'],
    ['', ');'],
  ],
  [
    ['k', '  private final '],
    ['c', 'Paragraph'],
    ['', ' message = '],
    ['k', 'new '],
    ['c', 'Paragraph'],
    ['', '();'],
  ],
  [
    ['k', '  private final '],
    ['c', 'Button'],
    ['', ' greet ='],
  ],
  [
    ['k', '      new '],
    ['c', 'Button'],
    ['', '('],
    ['s', '"Greet"'],
    ['', ', '],
    ['c', 'ButtonTheme'],
    ['', '.PRIMARY, e -> '],
    ['f', 'greet'],
    ['', '());'],
  ],
  [],
  [
    ['k', '  public '],
    ['c', 'GreetingView'],
    ['', '() {'],
  ],
  [
    ['f', '    getBoundComponent'],
    ['', '().'],
    ['f', 'add'],
    ['', '(name, greet, message);'],
  ],
  [['', '  }']],
  [],
  [
    ['k', '  private void '],
    ['f', 'greet'],
    ['', '() {'],
  ],
  [
    ['', '    message.'],
    ['f', 'setText'],
    ['', '('],
    ['s', '"Hello, "'],
    ['', ' + name.'],
    ['f', 'getValue'],
    ['', '() + '],
    ['s', '"!"'],
    ['', ');'],
  ],
  [['', '  }']],
  [['', '}']],
];

const TOKEN_CLASSES = {
  k: styles.tokenKeyword,
  c: styles.tokenClass,
  s: styles.tokenString,
  f: styles.tokenMethod,
  a: styles.tokenAnnotation,
};

// which line of the code creates which numbered part of the screen
const LINE_MARKS = { 2: 1, 3: 3, 4: 2 };

// The path a newcomer follows, in order.
function getPath() {
  return [
    [
      translate({ id: 'page.home.path.understand.step', message: 'Understand' }),
      translate({ id: 'page.home.path.understand.title', message: 'webforJ in 6 screens' }),
      translate({
        id: 'page.home.path.understand.text',
        message: 'A short guided walk through what webforJ is.',
      }),
      '/intro',
    ],
    [
      translate({ id: 'page.home.path.create.step', message: 'Create' }),
      translate({ id: 'page.home.path.create.title', message: 'Start a project' }),
      translate({
        id: 'page.home.path.create.text',
        message: 'Check the prerequisites, generate a project and run it.',
      }),
      '/docs/introduction/getting-started',
    ],
    [
      translate({ id: 'page.home.path.learn.step', message: 'Learn' }),
      translate({ id: 'page.home.path.learn.title', message: 'Follow the tutorial' }),
      translate({ id: 'page.home.path.learn.text', message: 'Build a working app step by step.' }),
      '/docs/introduction/tutorial/overview',
    ],
    [
      translate({ id: 'page.home.path.build.step', message: 'Build' }),
      translate({ id: 'page.home.path.build.title', message: 'Use the components' }),
      translate({
        id: 'page.home.path.build.text',
        message: 'Every component with its API and live samples.',
      }),
      '/docs/components/overview',
    ],
  ];
}

// The AI loop in three steps.
function getLoop() {
  return [
    [
      translate({ id: 'page.home.ai.loop.write.title', message: 'Write' }),
      translate({
        id: 'page.home.ai.loop.write.text',
        message:
          'Your assistant reads the real webforJ API through the MCP server and follows Agent Skills for forms, security and styling.',
      }),
    ],
    [
      translate({ id: 'page.home.ai.loop.check.title', message: 'Check' }),
      translate({
        id: 'page.home.ai.loop.check.text',
        message:
          'It runs the view in a plain JUnit test with the TestBed, no browser and no server, and gets a clear pass or fail.',
      }),
    ],
    [
      translate({ id: 'page.home.ai.loop.review.title', message: 'Review' }),
      translate({
        id: 'page.home.ai.loop.review.text',
        message:
          'You review a result that already passed, instead of reading every line of a guess.',
      }),
    ],
  ];
}

// Places with a page of their own. The key picks the colour and the drawing
// of each; the colour stays the same across the site.
function getPlaces() {
  return [
    [
      'components',
      translate({ id: 'page.home.places.components.title', message: 'UI components' }),
      translate({
        id: 'page.home.places.components.text',
        message: 'Tables, forms, dialogs, layouts and more, each with live samples.',
      }),
      '/ui-components',
    ],
    [
      'craft',
      'craftforJ',
      translate({
        id: 'page.home.places.craft.text',
        message:
          'The visual editor: see the component tree of your running app and change it live.',
      }),
      '/craftforj-tool',
    ],
    [
      'swing',
      translate({ id: 'page.home.places.swing.title', message: 'Swing modernization' }),
      translate({
        id: 'page.home.places.swing.text',
        message: 'Run a desktop app in the browser and move it screen by screen.',
      }),
      '/modernization',
    ],
    [
      'ai',
      translate({ id: 'page.home.places.ai.title', message: 'AI tooling' }),
      translate({
        id: 'page.home.places.ai.text',
        message:
          'The AI plugin: the MCP server and Agent Skills for your assistant, in one install.',
      }),
      '/ai',
    ],
    [
      'kit',
      translate({ id: 'page.home.places.kit.title', message: 'Figma design kit' }),
      translate({
        id: 'page.home.places.kit.text',
        message: 'Designers and developers work from the same tokens and components.',
      }),
      '/design-kit',
    ],
    [
      'showcase',
      translate({ id: 'page.home.places.showcase.title', message: 'Showcase' }),
      translate({
        id: 'page.home.places.showcase.text',
        message: 'Four example business apps to step through, screen by screen.',
      }),
      '/showcase',
    ],
  ];
}

// One small drawing per place, in the same hand: lines in the place colour.
const GLYPHS = {
  components: (
    <>
      <rect x="6" y="8" width="46" height="26" rx="6" />
      <rect x="60" y="8" width="54" height="26" rx="13" />
      <circle cx="101" cy="21" r="8" fill="currentColor" stroke="none" />
      <rect x="6" y="44" width="68" height="28" rx="6" />
      <path d="M16 54h48M16 62h30" />
      <rect x="82" y="44" width="32" height="28" rx="6" fill="currentColor" stroke="none" />
    </>
  ),
  craft: (
    <>
      <rect x="6" y="6" width="108" height="68" rx="8" />
      <path d="M44 6v68" />
      <path d="M14 20h20M20 32h16M20 44h16M14 56h20" />
      <rect x="54" y="18" width="50" height="14" rx="4" fill="currentColor" stroke="none" />
      <path d="M54 44h50M54 56h34" />
    </>
  ),
  swing: (
    <>
      <rect x="6" y="14" width="44" height="52" rx="4" />
      <path d="M6 26h44" />
      <path d="M56 40h14m-5-5 5 5-5 5" />
      <rect x="76" y="8" width="38" height="64" rx="8" />
      <path d="M76 22h38" />
      <rect x="84" y="32" width="22" height="10" rx="3" fill="currentColor" stroke="none" />
      <path d="M84 52h22M84 60h14" />
    </>
  ),
  ai: (
    <>
      <rect x="6" y="8" width="108" height="64" rx="8" />
      <path d="M18 26l8 7-8 7M34 40h18" />
      <path d="M18 56h44" />
      <circle cx="92" cy="50" r="12" fill="currentColor" stroke="none" />
      <path d="M86 50l4 4 8-9" stroke="var(--place-surface)" />
    </>
  ),
  kit: (
    <>
      <rect x="6" y="8" width="24" height="24" rx="6" fill="currentColor" stroke="none" />
      <rect x="36" y="8" width="24" height="24" rx="6" />
      <rect x="66" y="8" width="24" height="24" rx="12" />
      <path d="M100 8v24" />
      <path d="M6 46h108" />
      <path d="M6 58h64M6 70h40" />
    </>
  ),
  showcase: (
    <>
      <rect x="24" y="6" width="90" height="56" rx="6" />
      <rect x="6" y="20" width="90" height="54" rx="6" fill="var(--place-surface)" />
      <path d="M6 32h90" />
      <path d="M16 44h30M16 54h50M16 64h22" />
    </>
  ),
};

function getHelp() {
  return [
    [
      translate({ id: 'page.home.help.cookbook.title', message: 'Cookbook' }),
      translate({ id: 'page.home.help.cookbook.text', message: 'Short recipes for common tasks.' }),
      { to: '/cookbook' },
    ],
    [
      translate({ id: 'page.home.help.blog.title', message: 'Blog' }),
      translate({ id: 'page.home.help.blog.text', message: 'Guides, releases and how-tos.' }),
      { to: '/blog' },
    ],
    [
      'JavaDocs',
      translate({ id: 'page.home.help.javadocs.text', message: 'The full API reference.' }),
      { href: 'https://javadoc.io/doc/com.webforj' },
    ],
    [
      translate({ id: 'page.home.help.issue.title', message: 'Report an issue' }),
      translate({ id: 'page.home.help.issue.text', message: 'Bugs and feature requests.' }),
      { href: ISSUES_URL },
    ],
    [
      translate({ id: 'page.home.help.team.title', message: 'Talk to the team' }),
      translate({ id: 'page.home.help.team.text', message: 'Book a call about your app.' }),
      { href: CONTACT_URL },
    ],
  ];
}

// The search field in the hero opens the site search in the navbar.
function openSearch() {
  const button = document.querySelector('.DocSearch-Button');
  if (button) button.click();
}

function formatIndex(index) {
  return String(index + 1).padStart(2, '0');
}

function SearchIcon() {
  return (
    <svg className={styles.icon} viewBox="0 0 20 20" aria-hidden="true">
      <circle cx="9" cy="9" r="5.5" fill="none" stroke="currentColor" strokeWidth="1.75" />
      <path
        d="m13.5 13.5 3.5 3.5"
        fill="none"
        stroke="currentColor"
        strokeWidth="1.75"
        strokeLinecap="round"
      />
    </svg>
  );
}

// The three dots at the start of a drawn window's title bar.
function WindowDots(props) {
  return (
    <span className={styles.windowDots} {...props}>
      <i />
      <i />
      <i />
    </span>
  );
}

// A number that ties a line of code to the part of the screen it creates.
function Mark({ number, ...props }) {
  return (
    <span className={`${styles.mark} ${styles[`mark${number}`]}`} {...props}>
      {number}
    </span>
  );
}

// One command in the drawn terminal: the prompt, then the command typed in
// one character at a time. `revealClass` makes the prompt appear on cue.
function Command({ text, typeClass, revealClass }) {
  return (
    <span>
      <b className={revealClass && `${styles.show} ${revealClass}`}>$</b>{' '}
      <span className={`${styles.type} ${typeClass}`} style={{ '--chars': text.length }}>
        {text}
      </span>
    </span>
  );
}

// What each plane of the stack shows, drawn in bars and blocks.
function PlaneDrawing({ layerKey }) {
  switch (layerKey) {
    case 'screens':
      return (
        <>
          <span className={styles.uiSide}>
            <i />
            <i />
            <i />
            <i />
          </span>
          <span className={styles.uiMain}>
            <span className={styles.uiBar}>
              <i />
              <b />
            </span>
            <span className={styles.uiCards}>
              <i />
              <i />
              <i />
            </span>
            <span className={styles.uiRows}>
              <i />
              <i />
              <i />
            </span>
          </span>
        </>
      );
    case 'logic':
      return (
        <span className={styles.codeLines}>
          {CODE_BARS.map(([width, indent], index) => (
            // eslint-disable-next-line react/no-array-index-key
            <i key={index} style={{ '--line-width': width, '--indent': indent }} />
          ))}
        </span>
      );
    case 'data':
      return (
        <span className={styles.cells}>
          {Array.from({ length: 20 }, (_, index) => (
            <i key={index} />
          ))}
        </span>
      );
    case 'backend':
      return (
        <span className={styles.blocks}>
          <i />
          <i />
          <i />
        </span>
      );
    default:
      return null;
  }
}

function Glyph({ name }) {
  return (
    <svg
      className={styles.glyph}
      viewBox="0 0 120 80"
      fill="none"
      stroke="currentColor"
      strokeWidth={2}
      strokeLinecap="round"
      strokeLinejoin="round"
      aria-hidden="true">
      {GLYPHS[name]}
    </svg>
  );
}

// The drawing of the app as four planes floating above each other, seen at
// an angle. Plain CSS in 3D, so it stays sharp at any size. The planes drift
// slowly and lean toward the pointer, and a click pulls them apart until the
// next click lets them settle again.
function Stack() {
  const layers = getLayers();
  const stackRef = useRef(null);
  const [apart, setApart] = useState(false);
  usePauseOffscreen(stackRef, styles.paused);

  useEffect(() => {
    const stack = stackRef.current;
    if (!stack || window.matchMedia('(prefers-reduced-motion: reduce)').matches) return undefined;
    const clamp = (value) => Math.max(-1, Math.min(1, value)).toFixed(3);
    const onPointerMove = (event) => {
      const box = stack.getBoundingClientRect();
      const x = (event.clientX - (box.left + box.width / 2)) / box.width;
      const y = (event.clientY - (box.top + box.height / 2)) / box.height;
      stack.style.setProperty('--pointer-x', clamp(x));
      stack.style.setProperty('--pointer-y', clamp(y));
    };
    window.addEventListener('pointermove', onPointerMove, { passive: true });
    return () => window.removeEventListener('pointermove', onPointerMove);
  }, []);

  return (
    // eslint-disable-next-line jsx-a11y/click-events-have-key-events, jsx-a11y/no-static-element-interactions
    <div
      className={`${styles.stack} ${apart ? styles.stackApart : ''}`}
      ref={stackRef}
      aria-hidden="true"
      onClick={() => setApart(!apart)}>
      <div className={styles.scene}>
        <span className={styles.floor} />
        {/* the round trip of one click: down through the layers to your
            Java code and the data, then back up to the screen */}
        <span className={styles.signal} />
        {layers.map((layer, index) => (
          <div
            key={layer.key}
            className={`${styles.plane} ${styles[layer.key]}`}
            style={{ '--level': layers.length - 1 - index, '--index': index }}>
            <span className={styles.planeTag}>{layer.name}</span>
            {/* what lives on this layer: a note tied to the corner of its
                own plane, so it moves with it and faces the reader */}
            <span className={styles.note}>{NOTES[layer.key]}</span>
            <PlaneDrawing layerKey={layer.key} />
          </div>
        ))}
      </div>
      <p className={styles.stackCaption}>
        <Translate id="page.home.stack.caption">
          A click on the screen runs your Java code and comes back as an update.
        </Translate>
      </p>
    </div>
  );
}

function Hero() {
  return (
    <header className={styles.hero}>
      <div className={`${styles.container} ${styles.heroGrid}`}>
        <div>
          <p className={styles.eyebrow}>
            <Translate id="page.home.hero.eyebrow">webforJ documentation</Translate>
          </p>
          <h1 className={styles.heroTitle}>
            <Translate id="page.home.hero.title1">The whole app.</Translate>
            <span>
              <Translate id="page.home.hero.title2">In Java.</Translate>
            </span>
          </h1>
          <p className={styles.heroLead}>
            <Translate id="page.home.hero.lead">
              {
                'webforJ is the full-stack Java framework for business web apps: you build the whole app, screens included, in Java. These docs take you from the first project to production.'
              }
            </Translate>
          </p>
          <div className={styles.heroActions}>
            <Link className={`${styles.button} ${styles.buttonPrimary}`} to="/intro">
              <Translate id="page.home.hero.tour">Take the 5-minute tour</Translate>
              <ArrowIcon className={styles.icon} />
            </Link>
            <button type="button" className={styles.search} onClick={openSearch}>
              <SearchIcon />
              <span>
                <Translate id="page.home.hero.search">Search or ask AI</Translate>
              </span>
              <kbd>⌘K</kbd>
            </button>
          </div>
        </div>
        <Stack />
      </div>
    </header>
  );
}

// The Java class on the left and a drawing of the screen it becomes on the
// right. The numbers tie three lines of code to the three parts they make.
function CodeToScreen() {
  return (
    <section className={styles.codeScreenSection} aria-labelledby="home-live">
      <div className={styles.container}>
        <div className={styles.codeScreenHead}>
          <h2 id="home-live" className={styles.bigTitle}>
            <Translate id="page.home.live.title1">Write Java.</Translate>
            <span>
              <Translate id="page.home.live.title2">Get a screen.</Translate>
            </span>
          </h2>
          <p className={styles.codeScreenNote}>
            <Translate id="page.home.live.note">
              {
                'A complete screen is a Java class with a route. No template, no JavaScript and no REST layer between the screen and your code.'
              }
            </Translate>
          </p>
        </div>
        <div className={styles.codeScreen}>
          <figure className={styles.codeWindow}>
            <figcaption className={styles.windowBar}>
              <WindowDots aria-hidden="true" />
              GreetingView.java
            </figcaption>
            <pre className={styles.codeText}>
              <code>
                {CODE.map((line, lineIndex) => (
                  // eslint-disable-next-line react/no-array-index-key
                  <span key={lineIndex} className={styles.codeLine}>
                    <span className={styles.lineNo} aria-hidden="true">
                      {lineIndex + 1}
                    </span>
                    <span className={styles.lineText}>
                      {line.map(([kind, text], partIndex) => (
                        // eslint-disable-next-line react/no-array-index-key
                        <span key={partIndex} className={TOKEN_CLASSES[kind]}>
                          {text}
                        </span>
                      ))}
                    </span>
                    {LINE_MARKS[lineIndex] && (
                      <Mark number={LINE_MARKS[lineIndex]} aria-hidden="true" />
                    )}
                  </span>
                ))}
              </code>
            </pre>
          </figure>
          <div className={styles.screenWindow} aria-hidden="true">
            <p className={styles.windowBar}>
              <WindowDots />
              <span className={styles.address}>localhost:8080/greeting</span>
            </p>
            <div className={styles.drawn}>
              <div className={styles.drawnRow}>
                <Mark number={1} />
                <div className={styles.drawnField}>
                  <span className={styles.drawnLabel}>Name</span>
                  <span className={styles.drawnInput}>Ada</span>
                </div>
              </div>
              <div className={styles.drawnRow}>
                <Mark number={2} />
                <span className={styles.drawnButton}>Greet</span>
              </div>
              <div className={styles.drawnRow}>
                <Mark number={3} />
                <span className={styles.drawnMessage}>Hello, Ada!</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

function Path() {
  const path = getPath();
  return (
    <section className={styles.pathSection} aria-labelledby="home-path">
      <div className={styles.container}>
        <h2 id="home-path" className={styles.bigTitle}>
          <Translate id="page.home.path.title1">New here?</Translate>
          <span>
            <Translate id="page.home.path.title2">Follow the path.</Translate>
          </span>
        </h2>
        <ol className={styles.path}>
          {path.map(([step, title, text, to], index) => (
            <li key={to}>
              <Link className={styles.stop} to={to}>
                <span className={styles.stopNumber} aria-hidden="true">
                  {index + 1}
                </span>
                <span className={styles.stopStep}>{step}</span>
                <span className={styles.stopTitle}>{title}</span>
                <span className={styles.stopText}>{text}</span>
                <span className={styles.stopGo} aria-hidden="true">
                  <ArrowIcon className={styles.icon} />
                </span>
              </Link>
            </li>
          ))}
        </ol>
      </div>
    </section>
  );
}

// The same four layers as the drawing, as the map of the docs.
function Layers() {
  const layers = getLayers();
  return (
    <section className={styles.layersSection} aria-labelledby="home-layers">
      <div className={styles.container}>
        <h2 id="home-layers" className={styles.bigTitle}>
          <Translate id="page.home.layers.title1">One stack.</Translate>
          <span>
            <Translate id="page.home.layers.title2">One language.</Translate>
          </span>
        </h2>
        <ol className={styles.layers}>
          {layers.map((layer, index) => (
            <li key={layer.key} className={`${styles.layer} ${styles[layer.key]}`}>
              <span className={styles.layerIndex}>{formatIndex(index)}</span>
              <div className={styles.layerBody}>
                <h3 className={styles.layerName}>{layer.name}</h3>
                <p className={styles.layerText}>{layer.text}</p>
              </div>
              <ul className={styles.layerLinks}>
                {layer.links.map(([label, to]) => (
                  <li key={to}>
                    <Link to={to}>
                      {label}
                      <ArrowIcon className={styles.icon} />
                    </Link>
                  </li>
                ))}
              </ul>
            </li>
          ))}
        </ol>
      </div>
    </section>
  );
}

// AI ready from day one: the loop in three steps, and the install commands
// from the docs in a drawn terminal.
function AiReady() {
  const loop = getLoop();
  const terminalRef = useRef(null);
  usePauseOffscreen(terminalRef, styles.paused);
  return (
    <section className={styles.aiSection} aria-labelledby="home-ai">
      <div className={styles.container}>
        <h2 id="home-ai" className={styles.bigTitle}>
          <Translate id="page.home.ai.title1">AI ready.</Translate>
          <span>
            <Translate id="page.home.ai.title2">From day one.</Translate>
          </span>
        </h2>
        <div className={styles.aiGrid}>
          <div className={styles.aiPanel}>
            <p className={styles.aiLead}>
              <Translate id="page.home.ai.lead">
                {
                  'One install gives Claude Code, Copilot, Cursor, Gemini and Codex the webforJ MCP server and the Agent Skills.'
                }
              </Translate>
            </p>
            <Link className={`${styles.button} ${styles.buttonPrimary}`} to="/ai">
              <Translate id="page.home.ai.cta">See the AI tooling</Translate>
              <ArrowIcon className={styles.icon} />
            </Link>
          </div>
          <div className={styles.terminal} ref={terminalRef} aria-hidden="true">
            <p className={styles.windowBar}>
              <WindowDots />
              <Translate id="page.home.ai.terminal">Terminal</Translate>
            </p>
            <div className={styles.terminalBody}>
              <Command
                text="claude plugin marketplace add webforj/webforj-ai"
                typeClass={styles.type1}
              />
              <Command
                text="claude plugin install webforj@webforj-ai"
                typeClass={styles.type2}
                revealClass={styles.at2}
              />
              <Command text="mvn test" typeClass={styles.type3} revealClass={styles.at3} />
              <span className={`${styles.fail} ${styles.show} ${styles.at4}`}>
                <i>FAIL</i> Expected TextField to have value &lt;Grace&gt; but it has &lt;Ada&gt;.
              </span>
              <span className={`${styles.dim} ${styles.show} ${styles.at5}`}>
                #{' '}
                <Translate id="page.home.ai.terminalComment">
                  the assistant reads the message and fixes the view
                </Translate>
              </span>
              <Command text="mvn test" typeClass={styles.type4} revealClass={styles.at6} />
              <span className={`${styles.ok} ${styles.show} ${styles.at7}`}>
                <i>PASS</i> Tests run: 1, Failures: 0, Errors: 0
                <span className={styles.caret} />
              </span>
            </div>
          </div>
        </div>
        <ol className={styles.loop}>
          {loop.map(([title, text], index) => (
            <li key={title}>
              <span className={styles.loopMark}>{formatIndex(index)}</span>
              <h3 className={styles.loopTitle}>{title}</h3>
              <p className={styles.loopText}>{text}</p>
            </li>
          ))}
        </ol>
      </div>
    </section>
  );
}

function Places() {
  const places = getPlaces();
  return (
    <section className={styles.placesSection} aria-labelledby="home-places">
      <div className={styles.container}>
        <h2 id="home-places" className={styles.bigTitle}>
          <Translate id="page.home.places.title1">Places</Translate>
          <span>
            <Translate id="page.home.places.title2">worth a visit.</Translate>
          </span>
        </h2>
        <ul className={styles.places}>
          {places.map(([key, title, text, to]) => (
            <li key={to}>
              <Link className={`${styles.place} ${styles[key]}`} to={to}>
                <Glyph name={key} />
                <span className={styles.placeTitle}>{title}</span>
                <span className={styles.placeText}>{text}</span>
                <span className={styles.placeGo} aria-hidden="true">
                  <ArrowIcon className={styles.icon} />
                </span>
              </Link>
            </li>
          ))}
        </ul>
      </div>
    </section>
  );
}

function Help() {
  const help = getHelp();
  return (
    <section className={styles.helpSection} aria-labelledby="home-help">
      <div className={styles.container}>
        <h2 id="home-help" className={styles.bigTitle}>
          <Translate id="page.home.help.title">Still looking?</Translate>
        </h2>
        <ul className={styles.help}>
          {help.map(([title, text, linkProps]) => (
            <li key={title}>
              <Link className={styles.helpLink} {...linkProps}>
                <span className={styles.helpTitle}>
                  {title}
                  <ArrowIcon className={styles.icon} />
                </span>
                <span className={styles.helpText}>{text}</span>
              </Link>
            </li>
          ))}
        </ul>
        <p className={styles.builtBy}>
          <Translate id="page.home.help.behind">
            {
              'webforJ is built by BASIS International. BASIS software and software tools have been used to develop business solutions for both large international corporations and SMEs for almost 40 years, with 20+ years of delivering technology built on Java.'
            }
          </Translate>
        </p>
      </div>
    </section>
  );
}

// The closing banner: a call with the team. One flat block in the brand
// blue, the only solid colour block on the page.
function Talk() {
  return (
    <section className={styles.talkSection} aria-labelledby="home-talk">
      <div className={styles.container}>
        <div className={styles.talk}>
          <div>
            <h2 id="home-talk" className={styles.talkTitle}>
              <Translate id="page.home.talk.title">Planning a business app with webforJ?</Translate>
            </h2>
            <p className={styles.talkText}>
              <Translate id="page.home.talk.text">
                {
                  'Book a call with the team that builds webforJ. Bring your app, your backend and your timeline, and we will show you how webforJ fits, from the first screen to LTS+ support.'
                }
              </Translate>
            </p>
          </div>
          <div>
            <div className={styles.talkActions}>
              <a
                className={`${styles.talkButton} ${styles.talkPrimary}`}
                href={CONTACT_URL}
                target="_blank"
                rel="noopener noreferrer">
                <Translate id="page.home.talk.book">Book a call</Translate>
                <ArrowIcon className={styles.icon} />
              </a>
              <a
                className={`${styles.talkButton} ${styles.talkGhost}`}
                href="https://webforj.com/pricing/"
                target="_blank"
                rel="noopener noreferrer">
                <Translate id="page.home.talk.pricing">See pricing</Translate>
              </a>
            </div>
            <p className={styles.talkLanguages}>
              <Translate
                id="page.home.talk.languages"
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

export default function Home() {
  const title = getTitle();
  return (
    <Layout title={title} description={getDescription()}>
      {/* the home page title stands alone, without the site name after it */}
      <Head>
        <title>{title}</title>
        <meta property="og:title" content={title} />
      </Head>
      <main className={styles.page}>
        <Hero />
        <CodeToScreen />
        <Path />
        <Layers />
        <AiReady />
        <Places />
        <Help />
        <Talk />
      </main>
    </Layout>
  );
}
