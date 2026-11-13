import React, { useCallback, useEffect, useRef, useState } from 'react';
import Layout from '@theme/Layout';
import Link from '@docusaurus/Link';
import Translate, { translate } from '@docusaurus/Translate';
import CodeBlock from '@theme/CodeBlock';
import ThemedImage from '@theme/ThemedImage';
import useBaseUrl from '@docusaurus/useBaseUrl';
import CodeSwatches from '@site/src/components/codeSwatch';
import { ArrowIcon, CheckIcon } from '@site/src/components/pageKit/icons';
import {
  HELLO_JAVA,
  HELLO_KOTLIN,
  THEME_SEED,
  FORM_VIEW,
  CUSTOMER_BEAN,
  SPRING_VIEW,
  ADMIN_VIEW,
  SWING_VIEW,
  WEB_COMPONENT,
  AI_INSTALL,
} from './_code';
import styles from './intro.module.css';
import VideoPlayer from '@site/src/components/VideoPlayer';

const SCREEN_IDS = ['screen-1', 'screen-2', 'screen-3', 'screen-4', 'screen-5', 'screen-6'];


const COMPONENT_TILES = ['Login', 'ComboBox', 'Upload', 'GoogleCharts', 'Slider', 'Terminal'];

const AGENT_SKILLS = [
  'webforj-adding-servlets',
  'webforj-building-forms',
  'webforj-creating-components',
  'webforj-handling-timers-and-async',
  'webforj-localizing-apps',
  'webforj-securing-apps',
  'webforj-styling-apps',
  'webforj-upgrading-versions',
];

// The ends of the six short ticks around the seal, one per screen.
const SEAL_TICKS = [0, 1, 2, 3, 4, 5].map((i) => {
  const angle = ((i * 60 - 90) * Math.PI) / 180;
  const point = (radius) => [100 + radius * Math.cos(angle), 100 + radius * Math.sin(angle)];
  const [x1, y1] = point(78);
  const [x2, y2] = point(90);
  return { x1, y1, x2, y2 };
});

function getScreens() {
  const names = [
    translate({ id: 'page.intro.screens.one', message: 'What webforJ is' }),
    translate({ id: 'page.intro.screens.two', message: 'Components and theming' }),
    translate({ id: 'page.intro.screens.craft', message: 'craftforJ' }),
    translate({ id: 'page.intro.screens.three', message: 'Data, forms and security' }),
    translate({ id: 'page.intro.screens.four', message: 'Your existing code' }),
    translate({ id: 'page.intro.screens.five', message: 'AI tooling and next stops' }),
  ];
  return SCREEN_IDS.map((id, i) => ({ id, name: names[i] }));
}

function getRoadmap() {
  return [
    {
      key: 'today',
      phase: translate({ id: 'page.intro.four.roadmap.today.phase', message: 'Today' }),
      title: translate({
        id: 'page.intro.four.roadmap.today.title',
        message: 'Swing on the desktop',
      }),
      text: translate({
        id: 'page.intro.four.roadmap.today.text',
        message: 'Years of business logic, working every day.',
      }),
    },
    {
      key: 'one',
      phase: translate({ id: 'page.intro.four.roadmap.one.phase', message: 'Step one' }),
      title: translate({
        id: 'page.intro.four.roadmap.one.title',
        message: 'Same app, in the browser',
      }),
      text: translate({
        id: 'page.intro.four.roadmap.one.text',
        message: 'Webswing streams it to the browser with no changes to the source.',
      }),
    },
    {
      key: 'from',
      phase: translate({ id: 'page.intro.four.roadmap.from.phase', message: 'From there' }),
      title: translate({
        id: 'page.intro.four.roadmap.from.title',
        message: 'webforJ, screen by screen',
      }),
      text: translate({
        id: 'page.intro.four.roadmap.from.text',
        message: 'Replace Swing screens with webforJ, one at a time.',
      }),
    },
  ];
}

// What one install of the AI plugin brings, as the docs list it: the MCP
// server's capabilities (ai-tooling/mcp.md) and the Agent Skills
// (ai-tooling/agent-skills.md).
function getMcpCapabilities() {
  return [
    translate({ id: 'page.intro.five.mcp.version', message: 'Target the right webforJ version' }),
    translate({
      id: 'page.intro.five.mcp.knowledge',
      message: 'Look things up in the knowledge base',
    }),
    translate({ id: 'page.intro.five.mcp.scaffold', message: 'Scaffold a new webforJ project' }),
    translate({ id: 'page.intro.five.mcp.theme', message: 'Generate a DWC theme' }),
    translate({ id: 'page.intro.five.mcp.style', message: 'Style DWC components correctly' }),
    translate({ id: 'page.intro.five.mcp.tokens', message: 'Work with DWC design tokens' }),
  ];
}

function getNextStops() {
  return [
    {
      step: translate({ id: 'page.intro.next.create.step', message: 'Create' }),
      title: translate({ id: 'page.intro.next.create.title', message: 'Start a project' }),
      text: translate({
        id: 'page.intro.next.create.text',
        message: 'Generate a project with startforJ or the Maven archetype and run it.',
      }),
      to: '/docs/introduction/getting-started',
    },
    {
      step: translate({ id: 'page.intro.next.learn.step', message: 'Learn' }),
      title: translate({ id: 'page.intro.next.learn.title', message: 'Follow the tutorial' }),
      text: translate({
        id: 'page.intro.next.learn.text',
        message: 'Build a working app step by step, from the first screen to validation.',
      }),
      to: '/docs/introduction/tutorial/overview',
    },
    {
      step: translate({ id: 'page.intro.next.build.step', message: 'Build' }),
      title: translate({ id: 'page.intro.next.build.title', message: 'Components' }),
      text: translate({
        id: 'page.intro.next.build.text',
        message: 'Every component with its API and live samples on the page.',
      }),
      to: '/ui-components',
    },
    {
      step: translate({ id: 'page.intro.next.explore.step', message: 'Explore' }),
      title: translate({ id: 'page.intro.next.explore.title', message: 'Docs home' }),
      text: translate({
        id: 'page.intro.next.explore.text',
        message: 'The full map of the docs, grouped by what you want to do.',
      }),
      to: '/',
    },
  ];
}

function prefersReducedMotion() {
  return (
    typeof window !== 'undefined' && window.matchMedia('(prefers-reduced-motion: reduce)').matches
  );
}

// One screen of the walk: the numbered kicker and the two-line title above
// the copy, with the visual beside it. Even screens put the visual first.
// The opening of the page: what webforJ is, what this page is and how long
// it takes, with the six screens listed as links.
function Opening() {
  const screens = getScreens();
  return (
    <header className={styles.opening} aria-labelledby="intro-title">
      <div className={styles.container}>
        <p className={styles.openingEyebrow}>
          <Translate id="page.intro.opening.eyebrow">Introduction</Translate>
        </p>
        <h1 id="intro-title" className={styles.openingTitle}>
          <Translate id="page.intro.opening.title1">webforJ in six screens.</Translate>{' '}
          <span>
            <Translate id="page.intro.opening.title2">The whole framework, start to finish.</Translate>
          </span>
        </h1>
        <p className={styles.openingLead}>
          <Translate id="page.intro.opening.lead">
            webforJ is the full-stack Java framework for business web apps: you build the whole
            app, screens included, in Java. This tour walks through it in six short screens, from
            your first Java class to the AI tooling. It takes about five minutes.
          </Translate>
        </p>
        <ol className={styles.openingStops}>
          {screens.map((screen, i) => (
            <li key={screen.id}>
              <a href={`#${screen.id}`}>
                <span>{String(i + 1).padStart(2, '0')}</span>
                {screen.name}
              </a>
            </li>
          ))}
        </ol>
      </div>
    </header>
  );
}

function Screen({
  index,
  headingLevel: Heading = 'h2',
  kicker,
  title,
  accent,
  visual,
  children,
}) {
  const id = SCREEN_IDS[index - 1];
  const titleId = `${id}-title`;
  return (
    <section id={id} className={styles.screen} aria-labelledby={titleId}>
      <div className={styles.container}>
        <div className={index % 2 === 0 ? `${styles.split} ${styles.splitReverse}` : styles.split}>
          <div className={styles.copy}>
            <p className={styles.kicker}>
              <span className={styles.kickerIndex}>{String(index).padStart(2, '0')}</span>
              <span>{kicker}</span>
            </p>
            <Heading id={titleId} className={styles.statement}>
              {title} <span>{accent}</span>
            </Heading>
            {children}
          </div>
          <div className={styles.visual}>{visual}</div>
        </div>
      </div>
    </section>
  );
}

function Point({ title, children }) {
  return (
    <li>
      <strong>{title}</strong> {children}
    </li>
  );
}

function ReadMore({ to, children }) {
  return (
    <Link className={styles.readMore} to={to}>
      <span>
        <Translate id="page.intro.readMore" values={{ topic: children }}>
          {'Read more: {topic}'}
        </Translate>
      </span>
      <ArrowIcon className={styles.icon} />
    </Link>
  );
}

// The dark window frame around code and video: a bar with three dots, then
// the content.
function Window({ bar, className = '', children }) {
  return (
    <figure className={`${styles.window} ${className}`}>
      <div className={styles.windowBar}>
        <span className={styles.windowDots} aria-hidden="true">
          <i />
          <i />
          <i />
        </span>
        {bar}
      </div>
      {children}
    </figure>
  );
}

// A code window with file tabs. Every panel sits in the same grid cell, so
// the window is as tall as its longest file and never changes height when a
// tab switches.
function CodeWindow({ label, files, footer, className }) {
  const [active, setActive] = useState(0);
  const baseId = label.replace(/\W+/g, '-').toLowerCase();
  const tabbed = files.length > 1;
  return (
    <Window
      className={className}
      bar={
        <>
          {tabbed ? (
            <div className={styles.tabs} role="tablist" aria-label={label}>
              {files.map((file, i) => (
                <button
                  key={file.name}
                  type="button"
                  role="tab"
                  id={`${baseId}-tab-${i}`}
                  aria-selected={i === active}
                  aria-controls={`${baseId}-panel-${i}`}
                  className={`${styles.tab} ${i === active ? styles.tabActive : ''}`}
                  onClick={() => setActive(i)}>
                  {file.tab}
                </button>
              ))}
            </div>
          ) : null}
          <span className={styles.windowName}>{files[active].name}</span>
        </>
      }>
      <div className={styles.panels}>
        {files.map((file, i) => (
          <div
            key={file.name}
            role={tabbed ? 'tabpanel' : undefined}
            id={`${baseId}-panel-${i}`}
            aria-labelledby={tabbed ? `${baseId}-tab-${i}` : undefined}
            className={`${styles.panel} ${i === active ? styles.panelActive : ''}`}
            aria-hidden={i === active ? undefined : true}>
            <CodeSwatches>
              <CodeBlock language={file.lang} className={styles.code}>
                {file.code}
              </CodeBlock>
            </CodeSwatches>
          </div>
        ))}
      </div>
      {footer}
    </Window>
  );
}

// The screen that the HelloView code produces: the field, the button and
// the success toast it shows after a click.
function HelloResult() {
  return (
    <div className={styles.result}>
      <div className={styles.resultBar}>
        <span className={styles.resultAddress}>localhost:8080/hello</span>
        <span>
          <Translate id="page.intro.hello.afterClick">After a click</Translate>
        </span>
      </div>
      <div className={styles.resultBody}>
        <div className={styles.fakeForm} aria-hidden="true">
          <span className={styles.fakeLabel}>What is your name?</span>
          <span className={styles.fakeInput}>Lauren</span>
          <span className={styles.fakeButton}>Say hello</span>
        </div>
        <div className={styles.fakeToast} aria-hidden="true">
          <CheckIcon className={styles.icon} />
          Welcome, Lauren!
        </div>
        <p className={styles.srOnly}>
          <Translate id="page.intro.hello.description">
            {
              'The running view shows a text field labelled What is your name?, a Say hello button and, after a click, a success toast that reads Welcome, Lauren!'
            }
          </Translate>
        </p>
      </div>
    </div>
  );
}

function BasicsScreen() {
  return (
    <Screen
      index={1}
      kicker={<Translate id="page.intro.one.kicker">What webforJ is</Translate>}
      title={<Translate id="page.intro.one.title1">A screen is</Translate>}
      accent={<Translate id="page.intro.one.title2">a Java class.</Translate>}
      visual={
        <CodeWindow
          label={translate({
            id: 'page.intro.one.codeLabel',
            message: 'HelloView in Java or Kotlin',
          })}
          files={[
            { tab: 'Java', name: 'HelloView.java', lang: 'java', code: HELLO_JAVA },
            { tab: 'Kotlin', name: 'HelloView.kt', lang: 'kotlin', code: HELLO_KOTLIN },
          ]}
          footer={<HelloResult />}
        />
      }>
      <p className={styles.lede}>
        <Translate id="page.intro.one.lede">
          {
            'webforJ is the full-stack Java framework for business web apps: you build the whole app, screens included, in Java.'
          }
        </Translate>
      </p>
      <ul className={styles.points}>
        <Point
          title={<Translate id="page.intro.one.route.title">A route makes it a page.</Translate>}>
          <Translate id="page.intro.one.route.text" values={{ code: <code>@Route</code> }}>
            {'Put {code} on a class and it answers at that address in the browser.'}
          </Translate>
        </Point>
        <Point
          title={<Translate id="page.intro.one.fields.title">Components are fields.</Translate>}>
          <Translate id="page.intro.one.fields.text" values={{ code: <code>new</code> }}>
            {'Create them with {code}, react to clicks with a lambda.'}
          </Translate>
        </Point>
        <Point title={<Translate id="page.intro.one.kotlin.title">Java or Kotlin.</Translate>}>
          <Translate id="page.intro.one.kotlin.text">
            The Kotlin DSL builds the same screen with the same components.
          </Translate>
        </Point>
      </ul>
      <ReadMore to="/kotlin-dsl">
        {translate({ id: 'page.intro.one.readMore', message: 'Kotlin DSL' })}
      </ReadMore>
    </Screen>
  );
}

function ComponentTile({ name }) {
  const light = useBaseUrl(`/img/components/light/${name}.webp`);
  const dark = useBaseUrl(`/img/components/dark/${name}.webp`);
  return (
    <figure className={styles.tile}>
      <div className={styles.tileShot}>
        <ThemedImage
          className={styles.tileImage}
          sources={{ light, dark }}
          alt={translate(
            { id: 'page.intro.tile.alt', message: 'The webforJ {name} component' },
            { name },
          )}
          loading="lazy"
          decoding="async"
        />
      </div>
      <figcaption className={styles.tileName}>{name}</figcaption>
    </figure>
  );
}

function ComponentsScreen() {
  return (
    <Screen
      index={2}
      kicker={<Translate id="page.intro.two.kicker">Components and theming</Translate>}
      title={<Translate id="page.intro.two.title1">Components built</Translate>}
      accent={<Translate id="page.intro.two.title2">for business apps.</Translate>}
      visual={
        <div className={styles.mosaic}>
          {COMPONENT_TILES.map((name) => (
            <ComponentTile key={name} name={name} />
          ))}
        </div>
      }>
      <p className={styles.lede}>
        <Translate id="page.intro.two.lede">
          {
            'Fields, lists, dialogs, uploads, charts, trees and layouts come with webforJ. Each one is a Java class you create in your screen.'
          }
        </Translate>
      </p>
      <p className={styles.body}>
        <Translate id="page.intro.two.body">
          {
            'Theming starts from a seed. Set the primary seed and all 19 shades of the palette follow, in every component.'
          }
        </Translate>
      </p>
      <CodeWindow
        className={styles.seedWindow}
        label={translate({ id: 'page.intro.two.codeLabel', message: 'Theme seed' })}
        files={[{ tab: 'CSS', name: 'app.css', lang: 'css', code: THEME_SEED }]}
      />
      <ReadMore to="/ui-components">
        {translate({ id: 'page.intro.two.readMore', message: 'Components' })}
      </ReadMore>
    </Screen>
  );
}

function CraftScreen() {
  return (
    <Screen
      index={3}
      kicker={<Translate id="page.intro.craft.kicker">craftforJ</Translate>}
      title={<Translate id="page.intro.craft.title1">See the app your Java built.</Translate>}
      accent={<Translate id="page.intro.craft.title2">Change it while it runs.</Translate>}
      visual={
        <Window bar={<span className={styles.windowName}>craftforJ</span>}>
          <VideoPlayer
            video="craftforj/intro"
            width={1920}
            height={1080}
            title={translate({
              id: 'page.intro.craft.videoLabel',
              message:
                'craftforJ open over a running webforJ app: selecting a component, changing its properties and applying the change to the Java source',
            })}
            muted
            plain
            controlsOnPlay
          />
        </Window>
      }>
      <p className={styles.lede}>
        <Translate id="page.intro.craft.lede">
          {
            'craftforJ is the visual editor that runs inside your app in development mode. It shows the component tree your Java code created, lets you change properties live, and writes the changes you keep back into your Java source.'
          }
        </Translate>
      </p>
      <ul className={styles.points}>
        <Point
          title={<Translate id="page.intro.craft.inspect.title">Inspect components.</Translate>}>
          <Translate id="page.intro.craft.inspect.text">
            Browse the component tree, select a component by clicking it in the page, and change its
            properties while the app runs.
          </Translate>
        </Point>
        <Point
          title={
            <Translate id="page.intro.craft.source.title">Write changes to source.</Translate>
          }>
          <Translate id="page.intro.craft.source.text">
            Review your live edits as a diff and apply them to the Java file they came from.
          </Translate>
        </Point>
        <Point title={<Translate id="page.intro.craft.ai.title">The AI assistant.</Translate>}>
          <Translate id="page.intro.craft.ai.text">
            A coding agent inside the running app writes Java, compiles what it wrote, and applies
            it with your approval.
          </Translate>
        </Point>
      </ul>
      <ReadMore to="/craftforj-tool">craftforJ</ReadMore>
    </Screen>
  );
}

function DataScreen() {
  return (
    <Screen
      index={4}
      kicker={<Translate id="page.intro.three.kicker">Data, forms and security</Translate>}
      title={<Translate id="page.intro.three.title1">Forms, rules and access,</Translate>}
      accent={<Translate id="page.intro.three.title2">next to your backend.</Translate>}
      visual={
        <CodeWindow
          label={translate({
            id: 'page.intro.three.codeLabel',
            message: 'Form, bean, Spring view and guarded route',
          })}
          files={[
            {
              tab: translate({ id: 'page.intro.three.tabForm', message: 'Form' }),
              name: 'CustomerFormView.java',
              lang: 'java',
              code: FORM_VIEW,
            },
            {
              tab: translate({ id: 'page.intro.three.tabBean', message: 'Bean' }),
              name: 'Customer.java',
              lang: 'java',
              code: CUSTOMER_BEAN,
            },
            { tab: 'Spring', name: 'DashboardView.java', lang: 'java', code: SPRING_VIEW },
            {
              tab: translate({ id: 'page.intro.three.tabSecurity', message: 'Security' }),
              name: 'AdminView.java',
              lang: 'java',
              code: ADMIN_VIEW,
            },
          ]}
        />
      }>
      <ul className={styles.points}>
        <Point title={<Translate id="page.intro.three.binding.title">Binding.</Translate>}>
          <Translate
            id="page.intro.three.binding.text"
            values={{ code: <code>BindingContext</code> }}>
            {
              '{code} pairs the form fields with a bean and checks its Jakarta validation rules when you write the form back.'
            }
          </Translate>
        </Point>
        <Point title="Spring Boot.">
          <Translate id="page.intro.three.spring.text">
            Views take your services, repositories and other Spring beans through constructor
            injection.
          </Translate>
        </Point>
        <Point title={<Translate id="page.intro.three.security.title">Security.</Translate>}>
          <Translate
            id="page.intro.three.security.text"
            values={{ code: <code>@RolesAllowed</code> }}>
            {
              'Annotations such as {code} guard a route, and the rules are enforced before any component is rendered.'
            }
          </Translate>
        </Point>
      </ul>
      <ReadMore to="/showcase">
        {translate({ id: 'page.intro.three.readMore', message: 'Showcase' })}
      </ReadMore>
    </Screen>
  );
}

function ExistingCodeScreen() {
  return (
    <Screen
      index={5}
      kicker={<Translate id="page.intro.four.kicker">Your existing code comes along</Translate>}
      title={<Translate id="page.intro.four.title1">Bring the code</Translate>}
      accent={<Translate id="page.intro.four.title2">you already have.</Translate>}
      visual={
        <>
          <ol
            className={styles.roadmap}
            aria-label={translate({
              id: 'page.intro.four.roadmapLabel',
              message: 'From Swing to webforJ',
            })}>
            {getRoadmap().map((stage, i) => (
              <li key={stage.key} className={styles.stage}>
                <span className={styles.stagePhase}>
                  <span className={styles.stageNumber}>{i + 1}</span>
                  {stage.phase}
                </span>
                <span className={styles.stageTitle}>{stage.title}</span>
                <span className={styles.stageText}>{stage.text}</span>
              </li>
            ))}
          </ol>
          <CodeWindow
            label={translate({
              id: 'page.intro.four.codeLabel',
              message: 'Swing view and web component',
            })}
            files={[
              {
                tab: translate({ id: 'page.intro.four.tabSwing', message: 'Swing app' }),
                name: 'SwingAppView.java',
                lang: 'java',
                code: SWING_VIEW,
              },
              {
                tab: translate({ id: 'page.intro.four.tabComponent', message: 'Web component' }),
                name: 'RelativeTime.java',
                lang: 'java',
                code: WEB_COMPONENT,
              },
            ]}
          />
        </>
      }>
      <p className={styles.lede}>
        <Translate id="page.intro.four.lede" values={{ code: <code>WebswingConnector</code> }}>
          {
            '{code} puts a running Swing, JavaFX or SWT app inside a webforJ view, so a rewrite never has to happen all at once.'
          }
        </Translate>
      </p>
      <p className={styles.body}>
        <Translate id="page.intro.four.body" values={{ code: <code>ElementComposite</code> }}>
          {
            '{code} wraps a custom HTML element or web component in a Java class, with its properties, attributes and events in Java.'
          }
        </Translate>
      </p>
      <ReadMore to="/modernization">Webswing</ReadMore>
    </Screen>
  );
}

function AiKit() {
  return (
    <div className={styles.kit}>
      <div>
        <p className={styles.kitTitle}>MCP server</p>
        <ul className={styles.kitList}>
          {getMcpCapabilities().map((item) => (
            <li key={item}>{item}</li>
          ))}
        </ul>
      </div>
      <div>
        <p className={styles.kitTitle}>
          <Translate id="page.intro.five.skillsTitle">Agent Skills</Translate>
        </p>
        <ul className={`${styles.kitList} ${styles.kitSkills}`}>
          {AGENT_SKILLS.map((item) => (
            <li key={item}>{item}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}

// The seal at the end of the tour: a ring with one tick per screen and a
// check in the middle.
function Seal() {
  return (
    <svg className={styles.seal} viewBox="0 0 200 200" aria-hidden="true">
      {SEAL_TICKS.map((tick, i) => (
        <line key={i} className={styles.sealTick} style={{ '--i': i }} {...tick} />
      ))}
      <g className={styles.sealBadge}>
        <circle cx="100" cy="100" r="64" className={styles.sealRing} />
        <circle cx="100" cy="100" r="42" className={styles.sealDisc} />
      </g>
      <path className={styles.sealCheck} d="M82 101l12 12 24-26" />
    </svg>
  );
}

// The closing moment of the tour, a section of its own after the last
// screen. It is fully visible as rendered; once scripts run it hides its
// marks and plays them in once, when the reader has scrolled it to the
// middle of the window.
function TourComplete() {
  const ref = useRef(null);
  useEffect(() => {
    const block = ref.current;
    if (!block || prefersReducedMotion() || typeof IntersectionObserver === 'undefined') {
      return undefined;
    }
    block.classList.add(styles.waiting);
    const observer = new IntersectionObserver(
      ([entry]) => {
        if (!entry.isIntersecting) return;
        block.classList.add(styles.playing);
        block.classList.remove(styles.waiting);
        observer.disconnect();
      },
      // only the middle band of the window counts as "in view"
      { rootMargin: '-40% 0px -40% 0px' },
    );
    observer.observe(block);
    return () => observer.disconnect();
  }, []);

  return (
    <div ref={ref} className={styles.done}>
      <Seal />
      <p className={styles.doneStamp}>
        <Translate id="page.intro.done.stamp">Tour complete</Translate>
      </p>
      <h2 className={styles.doneTitle}>
        <Translate id="page.intro.done.title1">That was the whole stack.</Translate>{' '}
        <span>
          <Translate id="page.intro.done.title2">You are ready to build.</Translate>
        </span>
      </h2>
      <p className={styles.doneText}>
        <Translate id="page.intro.done.text">
          {
            'Consider yourself unofficially certified: you have seen how a webforJ app is put together, from the first screen to the AI tooling that helps you write the next one.'
          }
        </Translate>
      </p>
      <ul className={styles.doneList}>
        {getScreens().map((screen, i) => (
          <li key={screen.id} style={{ '--i': i }}>
            <CheckIcon className={styles.doneCheck} />
            {screen.name}
          </li>
        ))}
      </ul>
    </div>
  );
}

// The end of the page: the closing moment, then where to go next.
function Finale() {
  return (
    <section className={styles.finale}>
      <div className={styles.container}>
        <TourComplete />
        <NextStops />
      </div>
    </section>
  );
}

function NextStops() {
  return (
    <div className={styles.next}>
      <h3 className={styles.nextTitle}>
        <Translate id="page.intro.next.title">Your next stop</Translate>
      </h3>
      <ol className={styles.stops}>
        {getNextStops().map((stop) => (
          <li key={stop.to}>
            <Link className={styles.stop} to={stop.to}>
              <span className={styles.stopStep}>{stop.step}</span>
              <span className={styles.stopTitle}>
                {stop.title}
                <ArrowIcon className={styles.icon} />
              </span>
              <span className={styles.stopText}>{stop.text}</span>
            </Link>
          </li>
        ))}
      </ol>
    </div>
  );
}

function AiScreen() {
  return (
    <Screen
      index={6}
      kicker={<Translate id="page.intro.five.kicker">AI tooling and where next</Translate>}
      title={<Translate id="page.intro.five.title1">Your AI assistant follows</Translate>}
      accent={<Translate id="page.intro.five.title2">webforJ conventions.</Translate>}
      visual={
        <CodeWindow
          className={styles.terminal}
          label={translate({ id: 'page.intro.five.codeLabel', message: 'Install the AI plugin' })}
          files={[
            {
              tab: translate({ id: 'page.intro.five.tabTerminal', message: 'Terminal' }),
              name: translate({ id: 'page.intro.five.nameTerminal', message: 'Terminal' }),
              lang: 'bash',
              code: AI_INSTALL,
            },
          ]}
          footer={<AiKit />}
        />
      }>
      <p className={styles.lede}>
        <Translate id="page.intro.five.lede">
          {
            'One install of the webforJ AI plugin gives your coding assistant the webforJ MCP server and Agent Skills.'
          }
        </Translate>
      </p>
      <p className={styles.body}>
        <Translate id="page.intro.five.body">
          {
            'The server gives it live tools. The skills tell it when to use them, in what order, and how to check the result.'
          }
        </Translate>
      </p>
      <ReadMore to="/ai">
        {translate({ id: 'page.intro.five.readMore', message: 'AI tooling' })}
      </ReadMore>
    </Screen>
  );
}

// The dock rides along at the bottom of the walk. It shows where you are and
// steps to the previous or next screen. Scrolling stays native: the dock only
// follows it and never takes it over.
function Dock({ active, onGo }) {
  const screens = getScreens();
  return (
    <div className={styles.dockWrap}>
      <nav
        className={styles.dock}
        aria-label={translate({ id: 'page.intro.dock.label', message: 'Intro screens' })}>
        <button
          type="button"
          className={styles.dockButton}
          onClick={() => onGo(active - 1)}
          disabled={active === 0}
          aria-label={translate({ id: 'page.intro.dock.previous', message: 'Previous screen' })}>
          <ArrowIcon className={styles.icon} direction="left" />
        </button>
        <div className={styles.dockMiddle}>
          <span className={styles.dockCount} aria-live="polite">
            <Translate
              id="page.intro.dock.count"
              values={{ current: active + 1, total: screens.length }}>
              {'{current} of {total}'}
            </Translate>
            <span className={styles.dockLabel}>{screens[active].name}</span>
          </span>
          <span className={styles.dockBars}>
            {screens.map((screen, i) => (
              <button
                key={screen.id}
                type="button"
                className={`${styles.dockBar} ${i === active ? styles.dockBarActive : ''} ${
                  i < active ? styles.dockBarDone : ''
                }`}
                onClick={() => onGo(i)}
                aria-label={translate(
                  { id: 'page.intro.dock.screen', message: 'Screen {number}: {name}' },
                  { number: i + 1, name: screen.name },
                )}
                aria-current={i === active ? 'step' : undefined}
              />
            ))}
          </span>
        </div>
        <button
          type="button"
          className={styles.dockButton}
          onClick={() => onGo(active + 1)}
          disabled={active === screens.length - 1}
          aria-label={translate({ id: 'page.intro.dock.next', message: 'Next screen' })}>
          <ArrowIcon className={styles.icon} />
        </button>
      </nav>
    </div>
  );
}

export default function Intro() {
  const [active, setActive] = useState(0);
  const activeRef = useRef(0);

  const goTo = useCallback((index) => {
    const target = Math.max(0, Math.min(SCREEN_IDS.length - 1, index));
    const screen = document.getElementById(SCREEN_IDS[target]);
    if (!screen) return;
    screen.scrollIntoView({
      behavior: prefersReducedMotion() ? 'auto' : 'smooth',
      block: 'start',
    });
  }, []);

  // The current screen is the one that crosses the middle of the viewport.
  useEffect(() => {
    const screens = SCREEN_IDS.map((id) => document.getElementById(id)).filter(Boolean);
    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            const index = SCREEN_IDS.indexOf(entry.target.id);
            activeRef.current = index;
            setActive(index);
          }
        });
      },
      { rootMargin: '-50% 0px -50% 0px', threshold: 0 },
    );
    screens.forEach((screen) => observer.observe(screen));
    return () => observer.disconnect();
  }, []);

  // Left and right arrows step through the screens. Up, down, space and
  // page keys keep their usual scrolling.
  useEffect(() => {
    const onKeyDown = (event) => {
      if (event.defaultPrevented || event.altKey || event.ctrlKey || event.metaKey) return;
      if (event.shiftKey || (event.key !== 'ArrowRight' && event.key !== 'ArrowLeft')) return;
      const { target } = event;
      if (target && (target.isContentEditable || /^(INPUT|TEXTAREA|SELECT)$/.test(target.tagName)))
        return;
      if (target && target.closest && target.closest('pre, [role="tablist"]')) return;
      event.preventDefault();
      goTo(activeRef.current + (event.key === 'ArrowRight' ? 1 : -1));
    };
    window.addEventListener('keydown', onKeyDown);
    return () => window.removeEventListener('keydown', onKeyDown);
  }, [goTo]);

  return (
    <Layout
      title={translate({
        id: 'page.intro.meta.title',
        message: 'webforJ in 6 screens: a short introduction',
      })}
      description={translate({
        id: 'page.intro.meta.description',
        message:
          'A six screen walk through webforJ, the full-stack Java framework for business web apps: screens as Java classes, components and theming, the craftforJ visual editor, forms and security, Swing and web components, and AI tooling.',
      })}>
      <main className={styles.page}>
        <Opening />
        <div className={styles.walk}>
          <BasicsScreen />
          <ComponentsScreen />
          <CraftScreen />
          <DataScreen />
          <ExistingCodeScreen />
          <AiScreen />
          <Finale />
          <Dock active={active} onGo={goTo} />
        </div>
      </main>
    </Layout>
  );
}
