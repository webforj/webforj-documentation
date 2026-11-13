import React, { useState } from 'react';
import Layout from '@theme/Layout';
import Link from '@docusaurus/Link';
import CodeBlock from '@theme/CodeBlock';
import useBaseUrl from '@docusaurus/useBaseUrl';
import Translate, { translate } from '@docusaurus/Translate';
import { ArrowIcon } from '@site/src/components/pageKit/icons';
import getPanels from './_panels';
import styles from './craftforj.module.css';
import VideoPlayer from '@site/src/components/VideoPlayer';

// Copied from docs/craftforj/getting-started.md
const DEPENDENCY = `<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>\${webforj.version}</version>
</dependency>`;

const PROPERTIES = `webforj.debug = true
webforj.devtools.craftforj.enabled = true`;

// Copied from docs/craftforj/inspector.md, "Removing from source"
const SOURCE_BEFORE = `import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class ContactFormView {
  public ContactFormView(FlexLayout layout) {
    layout.setSpacing("1em");
    Button save = new Button("Save");
    save.setTheme(ButtonTheme.PRIMARY);
    save.onClick(event -> {
      save.setEnabled(false);
    });
    Button cancel = new Button("Cancel");
    layout.add(save, cancel);
  }
}`;

const SOURCE_AFTER = `import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class ContactFormView {
  public ContactFormView(FlexLayout layout) {
    layout.setSpacing("1em");
    Button cancel = new Button("Cancel");
    layout.add(cancel);
  }
}`;

// Data with text lives in functions, called while rendering, so translate()
// sees the page's language.
function getShortcuts() {
  return [
    {
      keys: ['Alt', 'Shift', 'D'],
      text: translate({ id: 'page.craftforj.shortcuts.open', message: 'Open or close' }),
    },
    {
      keys: ['Alt', 'Shift', 'C'],
      text: translate({ id: 'page.craftforj.shortcuts.pick', message: 'Pick a component' }),
    },
    {
      keys: ['Cmd/Ctrl', 'S'],
      text: translate({ id: 'page.craftforj.shortcuts.review', message: 'Review changes' }),
    },
    {
      keys: ['Cmd/Ctrl', 'Z'],
      text: translate({ id: 'page.craftforj.shortcuts.undo', message: 'Undo a change' }),
    },
  ];
}

function getLoop() {
  return [
    {
      title: translate({ id: 'page.craftforj.loop.open.title', message: 'Open' }),
      text: translate({
        id: 'page.craftforj.loop.open.text',
        message:
          'Click the trigger button over your app, or press Alt+Shift+D from anywhere in it.',
      }),
    },
    {
      title: translate({ id: 'page.craftforj.loop.pick.title', message: 'Pick' }),
      text: translate({
        id: 'page.craftforj.loop.pick.text',
        message:
          'Press Alt+Shift+C, hover a component and click it. The tree selects it and the sidebar fills with its properties.',
      }),
    },
    {
      title: translate({ id: 'page.craftforj.loop.change.title', message: 'Change' }),
      text: translate({
        id: 'page.craftforj.loop.change.text',
        message:
          'Edit a property. The running app updates immediately, and your files stay untouched.',
      }),
    },
    {
      title: translate({ id: 'page.craftforj.loop.keep.title', message: 'Keep' }),
      text: translate({
        id: 'page.craftforj.loop.keep.text',
        message: 'Review the change as a diff and apply it to the Java file that created it.',
      }),
    },
  ];
}

function getGuards() {
  return [
    {
      title: translate({
        id: 'page.craftforj.guards.settings.title',
        message: 'Two settings, both required',
      }),
      text: translate({
        id: 'page.craftforj.guards.settings.text',
        message:
          'craftforJ needs webforj.debug and webforj.devtools.craftforj.enabled. Neither does anything on its own.',
      }),
    },
    {
      title: translate({
        id: 'page.craftforj.guards.local.title',
        message: 'Local access by default',
      }),
      text: translate({
        id: 'page.craftforj.guards.local.text',
        message:
          'Only a browser on the machine running the app can reach it, until you name another machine in hosts-allowed.',
      }),
    },
    {
      title: translate({
        id: 'page.craftforj.guards.http.title',
        message: 'No added HTTP surface',
      }),
      text: translate({
        id: 'page.craftforj.guards.http.text',
        message:
          'It adds no endpoint, servlet or filter. It works over the connection your app already has.',
      }),
    },
    {
      title: translate({
        id: 'page.craftforj.guards.compiles.title',
        message: 'Writes only what compiles',
      }),
      text: translate({
        id: 'page.craftforj.guards.compiles.text',
        message:
          'A removal from source is only written if the result still compiles, and files are never deleted.',
      }),
    },
  ];
}

function getDocs() {
  return [
    {
      label: translate({ id: 'page.craftforj.docs.overview.label', message: 'Overview' }),
      text: translate({
        id: 'page.craftforj.docs.overview.text',
        message: 'What craftforJ is and what it gives you',
      }),
      to: '/docs/craftforj',
    },
    {
      label: translate({
        id: 'page.craftforj.docs.gettingStarted.label',
        message: 'Getting started',
      }),
      text: translate({
        id: 'page.craftforj.docs.gettingStarted.text',
        message: 'The dependency, the settings and opening it',
      }),
      to: '/docs/craftforj/getting-started',
    },
    {
      label: translate({
        id: 'page.craftforj.docs.inspector.label',
        message: 'Inspecting components',
      }),
      text: translate({
        id: 'page.craftforj.docs.inspector.text',
        message: 'The tree, picking, properties and the source viewer',
      }),
      to: '/docs/craftforj/inspector',
    },
    {
      label: translate({ id: 'page.craftforj.docs.changes.label', message: 'Applying changes' }),
      text: translate({
        id: 'page.craftforj.docs.changes.text',
        message: 'Pending changes, the review, undo and redo',
      }),
      to: '/docs/craftforj/source-changes',
    },
    {
      label: translate({ id: 'page.craftforj.docs.routes.label', message: 'Routes' }),
      text: translate({
        id: 'page.craftforj.docs.routes.text',
        message: 'The routing table, navigation and access rules',
      }),
      to: '/docs/craftforj/routes',
    },
    {
      label: translate({ id: 'page.craftforj.docs.theme.label', message: 'Theme' }),
      text: translate({
        id: 'page.craftforj.docs.theme.text',
        message: 'Design tokens, presets and saving to your stylesheet',
      }),
      to: '/docs/craftforj/theme',
    },
    {
      label: translate({ id: 'page.craftforj.docs.ai.label', message: 'AI assistant' }),
      text: translate({
        id: 'page.craftforj.docs.ai.text',
        message: 'The agent, its approvals and choosing a model',
      }),
      to: '/docs/craftforj/ai',
    },
    {
      label: translate({ id: 'page.craftforj.docs.appInfo.label', message: 'App info' }),
      text: translate({
        id: 'page.craftforj.docs.appInfo.text',
        message: 'Versions, the Java runtime and the project root',
      }),
      to: '/docs/craftforj/app-info',
    },
    {
      label: translate({ id: 'page.craftforj.docs.configuration.label', message: 'Configuration' }),
      text: translate({
        id: 'page.craftforj.docs.configuration.text',
        message: 'Every property, its default and the feature flags',
      }),
      to: '/docs/craftforj/configuration',
    },
    {
      label: translate({ id: 'page.craftforj.docs.security.label', message: 'Security' }),
      text: translate({
        id: 'page.craftforj.docs.security.text',
        message: 'What it can reach and how to confirm it is off',
      }),
      to: '/docs/craftforj/security',
    },
  ];
}

function Keys({ keys }) {
  return (
    <span className={styles.keys}>
      {keys.map((key, i) => (
        <React.Fragment key={key}>
          {i > 0 && (
            <span className={styles.plus} aria-hidden="true">
              +
            </span>
          )}
          <kbd className={styles.kbd}>{key}</kbd>
        </React.Fragment>
      ))}
    </span>
  );
}

// The eyebrow and the two-line title every section opens with. The second
// line is set in the accent colour.
function SectionTitle({ id, eyebrow, title, accent }) {
  return (
    <>
      <p className={styles.eyebrow}>{eyebrow}</p>
      <h2 className={styles.sectionTitle} id={id}>
        {title} <span>{accent}</span>
      </h2>
    </>
  );
}

// A section title with its lead paragraph beside it on wide screens.
function SectionHead({ id, eyebrow, title, accent, lead }) {
  return (
    <div className={styles.sectionHead}>
      <div>
        <SectionTitle id={id} eyebrow={eyebrow} title={title} accent={accent} />
      </div>
      <p className={styles.sectionLead}>{lead}</p>
    </div>
  );
}

// One numbered step of the getting started list: its title and text, with
// the code or picture that goes with it in `children`.
function Step({ number, title, text, children }) {
  return (
    <li className={styles.stepItem}>
      <span className={styles.stepNumber}>{number}</span>
      <div className={styles.stepBody}>
        <div>
          <h3 className={styles.stepTitle}>{title}</h3>
          <p className={styles.stepText}>{text}</p>
        </div>
        {children}
      </div>
    </li>
  );
}

function Hero() {
  const shortcuts = getShortcuts();
  return (
    <header className={styles.hero}>
      <div className={styles.container}>
        <div className={styles.heroGrid}>
          <div>
            <p className={styles.eyebrow}>
              <Translate id="page.craftforj.hero.eyebrow">
                {'The visual editor for webforJ'}
              </Translate>
            </p>
            <h1 className={styles.heroTitle}>
              <Translate id="page.craftforj.hero.title1">
                {'See the app your Java built.'}
              </Translate>{' '}
              <span>
                <Translate id="page.craftforj.hero.title2">{'Change it while it runs.'}</Translate>
              </span>
            </h1>
            <p className={styles.heroLead}>
              <Translate id="page.craftforj.hero.lead">
                {
                  'craftforJ runs inside your app in development mode. It shows the component tree your code created, lets you change properties live, and writes the changes you keep back into the Java file that created them.'
                }
              </Translate>
            </p>
            <div className={styles.actions}>
              <Link
                className={`${styles.button} ${styles.buttonPrimary}`}
                to="/docs/craftforj/getting-started">
                <Translate id="page.craftforj.hero.cta">{'Get started'}</Translate>
                <ArrowIcon className={styles.icon} />
              </Link>
              <Link className={`${styles.button} ${styles.buttonGhost}`} to="/docs/craftforj">
                <Translate id="page.craftforj.hero.docs">{'Read the docs'}</Translate>
              </Link>
            </div>
          </div>
          <div className={styles.shortcuts}>
            <p className={styles.shortcutsTitle}>
              <Translate id="page.craftforj.hero.shortcutsTitle">
                {'Four shortcuts cover the loop'}
              </Translate>
            </p>
            <ul>
              {shortcuts.map((shortcut) => (
                <li key={shortcut.text}>
                  <span>{shortcut.text}</span>
                  <Keys keys={shortcut.keys} />
                </li>
              ))}
            </ul>
          </div>
        </div>
        <figure className={styles.heroShot}>
          <VideoPlayer
            video="craftforj/intro"
            width={1920}
            height={1080}
            title={translate({
              id: 'page.craftforj.hero.videoLabel',
              message:
                'craftforJ open over a running webforJ app: inspecting components, editing properties, theming and asking the AI assistant',
            })}
            muted
            plain
            controlsOnPlay
          />
        </figure>
      </div>
    </header>
  );
}

function Loop() {
  const loop = getLoop();
  return (
    <section className={styles.loop} aria-labelledby="cj-loop">
      <div className={styles.container}>
        <SectionHead
          id="cj-loop"
          eyebrow={<Translate id="page.craftforj.loop.eyebrow">{'How it works'}</Translate>}
          title={<Translate id="page.craftforj.loop.title1">{'It reads your app'}</Translate>}
          accent={<Translate id="page.craftforj.loop.title2">{'through webforJ itself'}</Translate>}
          lead={
            <Translate id="page.craftforj.loop.lead">
              {
                'That is why it describes the app in the terms you wrote it in. The tree lists your components, the properties are the ones your components declare, and the routes are the ones your router registered, with the access rules you annotated them with.'
              }
            </Translate>
          }
        />
        <ol className={styles.loopSteps}>
          {loop.map((step, i) => (
            <li key={step.title}>
              <span className={styles.loopNumber}>{String(i + 1).padStart(2, '0')}</span>
              <h3 className={styles.loopTitle}>{step.title}</h3>
              <p className={styles.loopText}>{step.text}</p>
            </li>
          ))}
        </ol>
      </div>
    </section>
  );
}

// The video or screenshot of one view. Each holds its box before it loads.
// A video has its own controls and pauses when its tab or its view is no
// longer the one on screen.
function ViewMedia({ view, base, shown }) {
  return (
    <figure className={styles.media} style={{ '--w': view.width, '--h': view.height }}>
      {view.video ? (
        <VideoPlayer
          video={view.video}
          width={view.width}
          height={view.height}
          title={view.alt}
          muted
          plain
          controlsOnPlay
          active={shown}
        />
      ) : (
        <img
          src={`${base}${view.file}`}
          width={view.width}
          height={view.height}
          alt={view.alt}
          loading="lazy"
          decoding="async"
        />
      )}
    </figure>
  );
}

// One tab of the tour. The bar along the top of the frame names the media
// and, when the panel has more than one view, switches between them.
function Panel({ panel, active, base }) {
  const [view, setView] = useState(0);
  return (
    <div
      role="tabpanel"
      id={`cj-panel-${panel.id}`}
      aria-labelledby={`cj-tab-${panel.id}`}
      aria-hidden={!active}
      inert={active ? undefined : ''}
      className={`${styles.panel} ${active ? styles.panelActive : ''}`}>
      <div className={styles.stageFrame}>
        <div className={styles.viewBar}>
          <span className={styles.viewBarLabel}>
            {panel.views[view].video ? (
              <Translate id="page.craftforj.tour.video">{'Video'}</Translate>
            ) : (
              <Translate id="page.craftforj.tour.screenshot">{'Screenshot'}</Translate>
            )}
          </span>
          {panel.views.length > 1 ? (
            <>
              <div
                className={styles.views}
                role="group"
                aria-label={translate(
                  { id: 'page.craftforj.tour.viewsLabel', message: '{tab} views' },
                  { tab: panel.tab },
                )}>
                {panel.views.map((item, i) => (
                  <button
                    key={item.label}
                    type="button"
                    className={`${styles.viewButton} ${i === view ? styles.viewButtonActive : ''}`}
                    aria-pressed={i === view}
                    onClick={() => setView(i)}>
                    {item.label}
                  </button>
                ))}
              </div>
              <span className={styles.viewCount}>
                <Translate
                  id="page.craftforj.tour.count"
                  values={{ count: view + 1, total: panel.views.length }}>
                  {'{count} of {total}'}
                </Translate>
              </span>
            </>
          ) : (
            <span className={styles.viewOnly}>{panel.views[0].label}</span>
          )}
        </div>
        <div className={styles.stage}>
          {panel.views.map((item, i) => (
            <div
              key={item.label}
              className={`${styles.view} ${i === view ? styles.viewActive : ''}`}
              aria-hidden={i !== view}>
              <ViewMedia view={item} base={base} shown={active && i === view} />
            </div>
          ))}
        </div>
      </div>
      <div className={styles.panelBody}>
        <div>
          <h3 className={styles.panelTitle}>{panel.title}</h3>
          <p className={styles.panelText}>{panel.text}</p>
          <div className={styles.panelFoot}>
            <Link className={styles.panelLink} to={panel.doc.to}>
              <Translate id="page.craftforj.tour.docLink" values={{ label: panel.doc.label }}>
                {'{label} docs'}
              </Translate>
              <ArrowIcon className={styles.icon} />
            </Link>
          </div>
        </div>
        <ul className={styles.points}>
          {panel.points.map((point) => (
            <li key={point}>{point}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}

function Tour() {
  const panels = getPanels();
  const [active, setActive] = useState(0);
  const base = useBaseUrl('/img/craftforj/');
  const onKey = (event) => {
    const step = { ArrowRight: 1, ArrowLeft: -1 }[event.key];
    if (!step) return;
    event.preventDefault();
    const next = (active + step + panels.length) % panels.length;
    setActive(next);
    document.getElementById(`cj-tab-${panels[next].id}`)?.focus();
  };
  return (
    <section className={styles.tour} aria-labelledby="cj-tour">
      <div className={styles.container}>
        <div className={styles.tourHead}>
          <SectionTitle
            id="cj-tour"
            eyebrow={
              <Translate id="page.craftforj.tour.eyebrow">{'A tour of the panels'}</Translate>
            }
            title={<Translate id="page.craftforj.tour.title1">{'The panels,'}</Translate>}
            accent={<Translate id="page.craftforj.tour.title2">{'over a real app'}</Translate>}
          />
        </div>
        <div
          className={styles.tabs}
          role="tablist"
          aria-label={translate({
            id: 'page.craftforj.tour.tabsLabel',
            message: 'craftforJ panels',
          })}
          onKeyDown={onKey}>
          {panels.map((panel, i) => (
            <button
              key={panel.id}
              id={`cj-tab-${panel.id}`}
              type="button"
              role="tab"
              aria-selected={i === active}
              aria-controls={`cj-panel-${panel.id}`}
              tabIndex={i === active ? 0 : -1}
              className={`${styles.tab} ${i === active ? styles.tabActive : ''}`}
              onClick={() => setActive(i)}>
              {panel.tab}
            </button>
          ))}
        </div>
        <div className={styles.deck}>
          {panels.map((panel, i) => (
            <Panel key={panel.id} panel={panel} active={i === active} base={base} />
          ))}
        </div>
      </div>
    </section>
  );
}

function Source() {
  const guards = getGuards();
  return (
    <section className={styles.source} aria-labelledby="cj-source">
      <div className={styles.container}>
        <SectionHead
          id="cj-source"
          eyebrow={<Translate id="page.craftforj.source.eyebrow">{'Back to source'}</Translate>}
          title={<Translate id="page.craftforj.source.title1">{'It edits the Java,'}</Translate>}
          accent={<Translate id="page.craftforj.source.title2">{'not a copy of it'}</Translate>}
          lead={
            <Translate id="page.craftforj.source.lead">
              {
                'Take a form with a Save and a Cancel button. Select Save, right-click it in the tree and choose Remove from source. The declaration, the theme, the listener, its place in the add() call and the ButtonTheme import nothing else uses are gone. Cancel stays.'
              }
            </Translate>
          }
        />
        <div className={styles.diff}>
          <div className={styles.diffSide}>
            <p className={styles.diffLabel}>
              <Translate id="page.craftforj.source.before">{'Before'}</Translate>
            </p>
            <CodeBlock language="java" title="ContactFormView.java">
              {SOURCE_BEFORE}
            </CodeBlock>
          </div>
          <div className={styles.diffSide}>
            <p className={styles.diffLabel}>
              <Translate id="page.craftforj.source.after">{'After Remove from source'}</Translate>
            </p>
            <CodeBlock language="java" title="ContactFormView.java">
              {SOURCE_AFTER}
            </CodeBlock>
          </div>
        </div>
        <ul className={styles.guards}>
          {guards.map((guard) => (
            <li key={guard.title}>
              <h3 className={styles.guardTitle}>{guard.title}</h3>
              <p className={styles.guardText}>{guard.text}</p>
            </li>
          ))}
        </ul>
      </div>
    </section>
  );
}

function Start() {
  const dock = useBaseUrl('/img/craftforj/getting-started/docking.png');
  return (
    <section className={styles.start} aria-labelledby="cj-start">
      <div className={styles.container}>
        <SectionHead
          id="cj-start"
          eyebrow={<Translate id="page.craftforj.start.eyebrow">{'Getting started'}</Translate>}
          title={<Translate id="page.craftforj.start.title1">{'Turn it on'}</Translate>}
          accent={<Translate id="page.craftforj.start.title2">{'in three steps'}</Translate>}
          lead={
            <Translate id="page.craftforj.start.lead">
              {
                'craftforJ ships with webforJ, so there is nothing to download separately. Projects created with startforJ or from a webforJ archetype come with it enabled. Run the app and go straight to step three.'
              }
            </Translate>
          }
        />
        <ol className={styles.steps}>
          <Step
            number={1}
            title={
              <Translate id="page.craftforj.start.step1.title">{'Add the dependency'}</Translate>
            }
            text={
              <Translate id="page.craftforj.start.step1.text">
                {'Add webforj-devtools to your project if it is not there already.'}
              </Translate>
            }>
            <CodeBlock language="xml" title="pom.xml">
              {DEPENDENCY}
            </CodeBlock>
          </Step>
          <Step
            number={2}
            title={
              <Translate id="page.craftforj.start.step2.title">
                {'Enable debug mode and craftforJ'}
              </Translate>
            }
            text={
              <Translate id="page.craftforj.start.step2.text">
                {
                  'Set both properties in webforj.conf, or in application.properties for a Spring project. craftforJ only works when both are enabled.'
                }
              </Translate>
            }>
            <CodeBlock language="ini">{PROPERTIES}</CodeBlock>
          </Step>
          <Step
            number={3}
            title={
              <Translate id="page.craftforj.start.step3.title">
                {'Restart, reload and open it'}
              </Translate>
            }
            text={
              <Translate
                id="page.craftforj.start.step3.text"
                values={{ keys: <Keys keys={['Alt', 'Shift', 'D']} /> }}>
                {
                  'Open the app from the machine that runs it. A trigger button appears over your app. Click it, or press {keys} from anywhere in the app. The same shortcut closes craftforJ again.'
                }
              </Translate>
            }>
            <figure className={styles.dockShot}>
              <img
                src={dock}
                width={1910}
                height={938}
                alt={translate({
                  id: 'page.craftforj.start.dockAlt',
                  message: 'craftforJ docked to the right edge of an app page',
                })}
                loading="lazy"
                decoding="async"
              />
              <figcaption className={styles.stepText}>
                <Translate id="page.craftforj.start.dockCaption">
                  {
                    'craftforJ floats over your app by default. Drag it onto an edge to dock it there, or move it into a browser window or tab of its own for a second monitor.'
                  }
                </Translate>
              </figcaption>
            </figure>
          </Step>
        </ol>
      </div>
    </section>
  );
}

function Next() {
  const docs = getDocs();
  return (
    <section className={styles.next} aria-labelledby="cj-next">
      <div className={styles.container}>
        <SectionHead
          id="cj-next"
          eyebrow={<Translate id="page.craftforj.next.eyebrow">{'Where next'}</Translate>}
          title={
            <Translate id="page.craftforj.next.title1">{'Every panel has its own page'}</Translate>
          }
          accent={<Translate id="page.craftforj.next.title2">{'in the docs'}</Translate>}
          lead={
            <Translate id="page.craftforj.next.lead">
              {
                'webforJ is the full-stack Java framework for business web apps: you build the whole app, screens included, in Java. craftforJ ships with it and works on the app you are running.'
              }
            </Translate>
          }
        />
        <ul className={styles.docList}>
          {docs.map((doc) => (
            <li key={doc.to}>
              <Link className={styles.docLink} to={doc.to}>
                <span className={styles.docLabel}>
                  {doc.label}
                  <ArrowIcon className={styles.icon} />
                </span>
                <span className={styles.docText}>{doc.text}</span>
              </Link>
            </li>
          ))}
        </ul>
        <div className={styles.actions}>
          <Link
            className={`${styles.button} ${styles.buttonPrimary}`}
            to="/docs/ai-tooling/craftforj-assistant">
            <Translate id="page.craftforj.next.assistant">{'The craftforJ assistant'}</Translate>
            <ArrowIcon className={styles.icon} />
          </Link>
          <Link className={`${styles.button} ${styles.buttonGhost}`} to="/">
            <Translate id="page.craftforj.next.home">{'Back to the docs home'}</Translate>
          </Link>
        </div>
      </div>
    </section>
  );
}

export default function CraftforjTool() {
  return (
    <Layout
      title={translate({
        id: 'page.craftforj.meta.title',
        message: 'craftforJ: the visual editor for your running webforJ app',
      })}
      description={translate({
        id: 'page.craftforj.meta.description',
        message:
          'craftforJ is the visual editor for webforJ. It shows the component tree of your running webforJ app, lets you change properties live, and writes the changes you keep back into your Java source.',
      })}>
      <main className={styles.page}>
        <Hero />
        <Loop />
        <Tour />
        <Source />
        <Start />
        <Next />
      </main>
    </Layout>
  );
}
