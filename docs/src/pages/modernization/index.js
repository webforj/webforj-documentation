import React, { useState } from 'react';
import Layout from '@theme/Layout';
import Link from '@docusaurus/Link';
import CodeBlock from '@theme/CodeBlock';
import Translate, { translate } from '@docusaurus/Translate';
import { ArrowIcon } from '@site/src/components/pageKit/icons';
import {
  PHASE_CODE,
  SWING_ANNOUNCES,
  WEB_RECEIVES,
  WEB_REPLIES,
  SWING_UPDATES,
  LIFECYCLE,
  PERFORM,
  START_SCRIPTS,
  DEPENDENCY,
  OPTIONS,
  getPeople,
} from './_content';
import styles from './modernization.module.css';

const WEBSWING_LOGO = 'https://cdn.webforj.com/webforj-documentation/blogs/webswing/webswing.png';

// The columns of the drawn tables and forms. Each is a key of a person and
// of the labels from getMock().
const COLUMNS = ['name', 'team', 'city'];

// Labels of the drawn screens. Called while rendering, so translate() sees the
// page's language.
function getMock() {
  return {
    customerTable: translate({
      id: 'page.modernization.mock.customerTable',
      message: 'Customer Table',
    }),
    file: translate({ id: 'page.modernization.mock.file', message: 'File' }),
    edit: translate({ id: 'page.modernization.mock.edit', message: 'Edit' }),
    view: translate({ id: 'page.modernization.mock.view', message: 'View' }),
    name: translate({ id: 'page.modernization.mock.name', message: 'Name' }),
    team: translate({ id: 'page.modernization.mock.team', message: 'Team' }),
    city: translate({ id: 'page.modernization.mock.city', message: 'City' }),
    customers: translate({ id: 'page.modernization.mock.customers', message: 'Customers' }),
    orders: translate({ id: 'page.modernization.mock.orders', message: 'Orders' }),
    reports: translate({ id: 'page.modernization.mock.reports', message: 'Reports' }),
    editCustomer: translate({
      id: 'page.modernization.mock.editCustomer',
      message: 'Edit customer',
    }),
    cancel: translate({ id: 'page.modernization.mock.cancel', message: 'Cancel' }),
    save: translate({ id: 'page.modernization.mock.save', message: 'Save' }),
    appTab: translate({ id: 'page.modernization.mock.appTab', message: 'webforJ app' }),
    swingStreamed: translate({
      id: 'page.modernization.mock.swingStreamed',
      message: 'Swing app, streamed',
    }),
    webDialog: translate({ id: 'page.modernization.mock.webDialog', message: 'webforJ dialog' }),
    laneSwing: translate({ id: 'page.modernization.mock.laneSwing', message: 'Swing app' }),
    laneWeb: translate({ id: 'page.modernization.mock.laneWeb', message: 'webforJ app' }),
  };
}

// The drawer of the webforJ app while the Swing screens are still embedded.
function getNavHybrid() {
  const mock = getMock();
  return [
    { id: 'customers', label: mock.customers, kind: 'Swing' },
    { id: 'orders', label: mock.orders, kind: 'webforJ' },
    { id: 'reports', label: mock.reports, kind: 'Swing' },
  ];
}

// The drawer once every screen is written in webforJ.
function getNavWeb() {
  const mock = getMock();
  return [
    { id: 'customers', label: mock.customers, kind: 'webforJ' },
    { id: 'orders', label: mock.orders, kind: 'webforJ' },
    { id: 'reports', label: mock.reports, kind: 'webforJ' },
  ];
}

function getPhases() {
  const mock = getMock();
  const people = getPeople();
  return [
    {
      id: 'phase-1',
      label: translate({
        id: 'page.modernization.phases.p1.label',
        message: 'Your app on the web',
      }),
      title: translate({
        id: 'page.modernization.phases.p1.title',
        message: 'Run the desktop app in the browser',
      }),
      text: translate({
        id: 'page.modernization.phases.p1.text',
        message:
          'Webswing runs your Swing, JavaFX or SWT app on the server and streams its interface to the browser as HTML5 canvas. Mouse and keyboard input go back to the real app. The source code does not change.',
      }),
      file: 'SwingAppView.java',
      code: PHASE_CODE.embed,
      screen: () => (
        <Browser address="localhost:8080/myapp/" tab={mock.customerTable}>
          <div className={styles.canvasStage}>
            <SwingWindow selected={-1} />
          </div>
        </Browser>
      ),
      note: translate({
        id: 'page.modernization.phases.p1.note',
        message:
          'Embed it in a webforJ view with WebswingConnector. The connector connects to the Webswing server when it is added to the page.',
      }),
    },
    {
      id: 'phase-2',
      label: translate({
        id: 'page.modernization.phases.p2.label',
        message: 'New screens in webforJ',
      }),
      title: translate({
        id: 'page.modernization.phases.p2.title',
        message: 'Build new screens around it',
      }),
      text: translate({
        id: 'page.modernization.phases.p2.text',
        message:
          'New screens are written in webforJ and sit next to the embedded app. The two sides send each other actions, so a click in Swing can open a web form and a save on the web updates the Swing table. Users work in old and new screens in the same browser tab.',
      }),
      file: 'CustomerTableView.java',
      code: PHASE_CODE.hybrid,
      screen: () => (
        <Browser address="localhost:8090" tab={mock.appTab}>
          <Shell nav={getNavHybrid()} active="customers">
            <div className={styles.hybrid}>
              <SwingWindow selected={2} tag="WebswingConnector" />
              <div className={styles.channel} aria-hidden="true">
                <span className={styles.msgOut}>
                  select-customer
                  <ArrowIcon className={styles.icon} />
                </span>
                <span className={styles.msgIn}>
                  <ArrowIcon className={styles.icon} direction="left" />
                  update-customer
                </span>
              </div>
              <div className={styles.hybridWeb}>
                <WebForm person={people[2]} />
                <span className={styles.webTag}>webforJ</span>
              </div>
            </div>
          </Shell>
        </Browser>
      ),
      note: translate({
        id: 'page.modernization.phases.p2.note',
        message:
          'Swing announces a double click as select-customer. webforJ opens its own form and sends update-customer back on save.',
      }),
    },
    {
      id: 'phase-3',
      label: translate({ id: 'page.modernization.phases.p3.label', message: 'Switch fully' }),
      title: translate({
        id: 'page.modernization.phases.p3.title',
        message: 'Retire the desktop client',
      }),
      text: translate({
        id: 'page.modernization.phases.p3.text',
        message:
          'Replace the Swing screens one at a time, in the order that matters to your business. When a screen has a webforJ equivalent, delete its actions. After the last one, the Webswing layer and the desktop client go away.',
      }),
      screen: () => (
        <Browser address="localhost:8090" tab={mock.appTab}>
          <Shell nav={getNavWeb()} active="customers">
            <div className={styles.allWeb}>
              <WebTable selected={2} />
              <WebForm person={people[2]} />
            </div>
          </Shell>
        </Browser>
      ),
      endState: [
        {
          term: translate({ id: 'page.modernization.phases.p3.runs.term', message: 'What runs' }),
          desc: translate({
            id: 'page.modernization.phases.p3.runs.desc',
            message: 'One Java app in the browser, every screen written in webforJ.',
          }),
        },
        {
          term: translate({ id: 'page.modernization.phases.p3.goes.term', message: 'What goes' }),
          desc: translate({
            id: 'page.modernization.phases.p3.goes.desc',
            message: 'The Webswing server, the streamed desktop UI and the installed client.',
          }),
        },
        {
          term: translate({ id: 'page.modernization.phases.p3.stays.term', message: 'What stays' }),
          desc: translate({
            id: 'page.modernization.phases.p3.stays.desc',
            message:
              'Your domain classes, services and validation rules, called from the new screens as Java methods.',
          }),
        },
      ],
      note: translate({
        id: 'page.modernization.phases.p3.note',
        message:
          'You can stop after any phase and still have gained something. Phase 2 can run for as long as the budget allows.',
      }),
    },
  ];
}

function getStays() {
  return [
    {
      title: translate({ id: 'page.modernization.stays.team.title', message: 'The team' }),
      text: translate({
        id: 'page.modernization.stays.team.text',
        message:
          'The people who maintain the Swing app today build the web screens in Java. There is no second team and no front-end toolchain to learn.',
      }),
    },
    {
      title: translate({ id: 'page.modernization.stays.code.title', message: 'The code' }),
      text: translate({
        id: 'page.modernization.stays.code.text',
        message:
          'Webswing runs the app as it is. The same code base keeps working as a desktop app and in the browser, so you maintain one app, not two.',
      }),
    },
    {
      title: translate({ id: 'page.modernization.stays.logic.title', message: 'The logic' }),
      text: (
        <Translate
          id="page.modernization.stays.logic.text"
          values={{ code: <code>javax.swing</code> }}>
          {
            'Domain classes, services and validation rules that have nothing to do with {code} are called straight from the new screens. They do not become endpoints first.'
          }
        </Translate>
      ),
    },
    {
      title: translate({
        id: 'page.modernization.stays.toolchain.title',
        message: 'The toolchain',
      }),
      text: translate({
        id: 'page.modernization.stays.toolchain.text',
        message: 'Maven, your IDE, your debugger and your existing tests continue to apply.',
      }),
    },
  ];
}

function getLoop() {
  return [
    {
      title: translate({
        id: 'page.modernization.ai.loop.one.title',
        message: 'Give it one Swing screen',
      }),
      text: translate({
        id: 'page.modernization.ai.loop.one.text',
        message: 'Ask for the webforJ equivalent that calls your existing services.',
      }),
    },
    {
      title: translate({
        id: 'page.modernization.ai.loop.two.title',
        message: 'It checks the real API',
      }),
      text: translate({
        id: 'page.modernization.ai.loop.two.text',
        message:
          'The MCP server looks up webforJ documentation and API details on demand, instead of guessing.',
      }),
    },
    {
      title: translate({
        id: 'page.modernization.ai.loop.three.title',
        message: 'You review and test',
      }),
      text: translate({
        id: 'page.modernization.ai.loop.three.text',
        message: 'Compare behavior against the Swing original, then move on to the next screen.',
      }),
    },
  ];
}

function getNext() {
  return [
    {
      title: translate({
        id: 'page.modernization.next.overview.title',
        message: 'Webswing overview',
      }),
      text: translate({
        id: 'page.modernization.next.overview.text',
        message: 'What Webswing solves and how the three layers work together.',
      }),
      to: '/docs/integrations/webswing/overview',
    },
    {
      title: translate({
        id: 'page.modernization.next.setup.title',
        message: 'Setup and configuration',
      }),
      text: translate({
        id: 'page.modernization.next.setup.text',
        message: 'Server install, admin console, CORS and the connector options.',
      }),
      to: '/docs/integrations/webswing/setup',
    },
    {
      title: translate({
        id: 'page.modernization.next.communication.title',
        message: 'Communication',
      }),
      text: translate({
        id: 'page.modernization.next.communication.text',
        message: 'Every action and event between webforJ and the Swing app.',
      }),
      to: '/docs/integrations/webswing/communication',
    },
    {
      title: translate({
        id: 'page.modernization.next.tutorial.title',
        message: 'Modernization tutorial',
      }),
      text: translate({
        id: 'page.modernization.next.tutorial.text',
        message: 'A Swing customer table, embedded and given a webforJ edit dialog.',
      }),
      to: '/docs/integrations/webswing/tutorial',
    },
    {
      title: translate({
        id: 'page.modernization.next.plugin.title',
        message: 'webforJ AI plugin',
      }),
      text: translate({
        id: 'page.modernization.next.plugin.text',
        message: 'The MCP server and Agent Skills for your coding assistant.',
      }),
      to: '/docs/ai-tooling',
    },
    {
      title: translate({ id: 'page.modernization.next.home.title', message: 'Docs home' }),
      text: translate({
        id: 'page.modernization.next.home.text',
        message: 'Everything else in webforJ, from the first project to the components.',
      }),
      to: '/',
    },
  ];
}

// The eyebrow, when there is one, and the two-line title a section opens
// with. The second line is set in the accent colour.
function SectionTitle({ id, eyebrow, title, accent }) {
  return (
    <>
      {eyebrow && <p className={styles.eyebrow}>{eyebrow}</p>}
      <h2 id={id} className={styles.sectionTitle}>
        {title} <span>{accent}</span>
      </h2>
    </>
  );
}

// A dark code window with a title bar that names the file.
function CodeWindow({ file, children, language = 'java' }) {
  return (
    <figure className={styles.codeWindow}>
      <div className={styles.codeBar}>
        <WindowControls className={styles.codeDots} aria-hidden="true" />
        <span>{file}</span>
      </div>
      <CodeBlock language={language} className={styles.code}>
        {children}
      </CodeBlock>
    </figure>
  );
}

// Composed screens. They draw what a visitor sees at each point of the move:
// a browser tab, the desktop app streamed into it, and the webforJ app that
// grows around it.

// The three small window buttons; the class decides how they look.
function WindowControls(props) {
  return (
    <span {...props}>
      <i />
      <i />
      <i />
    </span>
  );
}

// One row of a drawn table, with a cell for each of COLUMNS.
function TableRow({ className, values }) {
  return (
    <div className={className}>
      {COLUMNS.map((column) => (
        <span key={column}>{values[column]}</span>
      ))}
    </div>
  );
}

function Browser({ address, tab, children }) {
  return (
    <div className={styles.browser}>
      <div className={styles.browserBar} aria-hidden="true">
        <WindowControls className={styles.browserDots} />
        <span className={styles.browserAddress}>{address}</span>
        <span className={styles.browserTab}>{tab}</span>
      </div>
      <div className={styles.browserBody}>{children}</div>
    </div>
  );
}

function SwingWindow({ selected, tag }) {
  const mock = getMock();
  return (
    <div className={styles.swing}>
      {tag && <span className={styles.swingTag}>{tag}</span>}
      <div className={styles.swingTitle}>
        <span>{mock.customerTable}</span>
        <WindowControls className={styles.swingButtons} />
      </div>
      <div className={styles.swingMenu}>
        <span>{mock.file}</span>
        <span>{mock.edit}</span>
        <span>{mock.view}</span>
      </div>
      <div className={styles.swingTable}>
        <TableRow className={`${styles.swingRow} ${styles.swingHead}`} values={mock} />
        {getPeople().map((person, i) => (
          <TableRow
            key={person.name}
            className={`${styles.swingRow} ${i === selected ? styles.swingSelected : ''}`}
            values={person}
          />
        ))}
      </div>
    </div>
  );
}

function Shell({ nav, active, children }) {
  const mock = getMock();
  return (
    <div className={styles.shell}>
      <div className={styles.shellBar}>
        <span className={styles.shellMark} />
        <span className={styles.shellName}>{mock.customers}</span>
      </div>
      <div className={styles.shellMain}>
        <ul className={styles.shellNav}>
          {nav.map((item) => (
            <li key={item.id} className={item.id === active ? styles.shellNavActive : undefined}>
              <span>{item.label}</span>
              <small>{item.kind}</small>
            </li>
          ))}
        </ul>
        <div className={styles.shellContent}>{children}</div>
      </div>
    </div>
  );
}

function WebForm({ person }) {
  const mock = getMock();
  return (
    <div className={styles.webForm}>
      <p className={styles.webFormTitle}>{mock.editCustomer}</p>
      {COLUMNS.map((column) => (
        <label key={column}>
          <span>{mock[column]}</span>
          <b>{person[column]}</b>
        </label>
      ))}
      <div className={styles.webFormActions}>
        <span className={styles.webGhost}>{mock.cancel}</span>
        <span className={styles.webPrimary}>{mock.save}</span>
      </div>
    </div>
  );
}

function WebTable({ selected }) {
  const mock = getMock();
  return (
    <div className={styles.webTable}>
      <TableRow className={`${styles.webRow} ${styles.webHead}`} values={mock} />
      {getPeople().map((person, i) => (
        <TableRow
          key={person.name}
          className={`${styles.webRow} ${i === selected ? styles.webSelected : ''}`}
          values={person}
        />
      ))}
    </div>
  );
}

function HeroScreen() {
  const mock = getMock();
  const people = getPeople();
  return (
    <figure className={styles.heroFigure}>
      <Browser address="localhost:8090" tab={mock.appTab}>
        <Shell nav={getNavHybrid()} active="customers">
          <div className={styles.overlayStage}>
            <SwingWindow selected={2} tag={mock.swingStreamed} />
            <div className={styles.overlay}>
              <WebForm person={people[2]} />
              <span className={styles.webTag}>{mock.webDialog}</span>
            </div>
          </div>
        </Shell>
      </Browser>
      <figcaption className={styles.heroCaption}>
        <Translate id="page.modernization.hero.caption">
          {
            'A double click in the Swing table opens a webforJ dialog instead of the old Swing one. Both run in the same browser tab.'
          }
        </Translate>
      </figcaption>
    </figure>
  );
}

function Hero() {
  return (
    <header className={styles.hero} id="modernization-top">
      <div className={`${styles.container} ${styles.heroGrid}`}>
        <div>
          <p className={styles.eyebrow}>
            <Translate id="page.modernization.hero.eyebrow">{'Swing modernization'}</Translate>
          </p>
          <h1 className={styles.heroTitle}>
            <Translate id="page.modernization.hero.title1">{'Swing to the web,'}</Translate>{' '}
            <span>
              <Translate id="page.modernization.hero.title2">{'one screen at a time'}</Translate>
            </span>
          </h1>
          <p className={styles.heroLead}>
            <Translate id="page.modernization.hero.lead">
              {
                'Your Swing, JavaFX or SWT app runs in a web browser without changes to its source. Then you build new screens in webforJ around it and replace the old ones at your own pace. The Java team and the code stay.'
              }
            </Translate>
          </p>
          <div className={styles.actions}>
            <Link
              className={`${styles.button} ${styles.buttonPrimary}`}
              to="/docs/integrations/webswing/tutorial">
              <Translate id="page.modernization.hero.cta">{'Follow the tutorial'}</Translate>
              <ArrowIcon className={styles.icon} />
            </Link>
            <Link
              className={`${styles.button} ${styles.buttonSecondary}`}
              to="/docs/integrations/webswing/setup">
              <Translate id="page.modernization.hero.setup">{'Set up Webswing'}</Translate>
            </Link>
          </div>
          <dl className={styles.heroFacts}>
            <div>
              <dt>
                <Translate id="page.modernization.hero.fact1">
                  {'Desktop toolkits: Swing, JavaFX and SWT'}
                </Translate>
              </dt>
              <dd>3</dd>
            </div>
            <div>
              <dt>
                <Translate id="page.modernization.hero.fact2">
                  {'Source changes to run in a browser'}
                </Translate>
              </dt>
              <dd>0</dd>
            </div>
          </dl>
        </div>
        <HeroScreen />
      </div>
    </header>
  );
}

function Stays() {
  const stays = getStays();
  return (
    <section className={styles.stays} aria-labelledby="modernization-stays">
      <div className={`${styles.container} ${styles.staysGrid}`}>
        <div>
          <SectionTitle
            id="modernization-stays"
            eyebrow={
              <Translate id="page.modernization.stays.eyebrow">{'What stays the same'}</Translate>
            }
            title={<Translate id="page.modernization.stays.title1">{'Only the screens'}</Translate>}
            accent={<Translate id="page.modernization.stays.title2">{'change'}</Translate>}
          />
          <p className={styles.sectionLead}>
            <Translate id="page.modernization.stays.lead">
              {
                'webforJ is the full-stack Java framework for business web apps: you build the whole app, screens included, in Java. So moving a Swing app means moving its screens, and everything under them carries over.'
              }
            </Translate>
          </p>
          <div className={styles.changes}>
            <p className={styles.changesLabel}>
              <Translate id="page.modernization.stays.changesLabel">{'What changes'}</Translate>
            </p>
            <p className={styles.changesText}>
              <Translate id="page.modernization.stays.changesText">
                {
                  'The screens. You move them one at a time, in the order that matters to your business, while the rest of the Swing app keeps running in the same browser tab.'
                }
              </Translate>
            </p>
          </div>
        </div>
        <ol className={styles.staysList}>
          {stays.map((item, i) => (
            <li key={item.title}>
              <span className={styles.staysNumber}>{String(i + 1).padStart(2, '0')}</span>
              <div>
                <h3 className={styles.staysTitle}>{item.title}</h3>
                <p className={styles.staysText}>{item.text}</p>
              </div>
            </li>
          ))}
        </ol>
      </div>
    </section>
  );
}

function Journey() {
  const phases = getPhases();
  const [index, setIndex] = useState(0);
  const showPhase = (next) => setIndex((next + phases.length) % phases.length);
  const onKeyDown = (event) => {
    const step = { ArrowRight: 1, ArrowLeft: -1 }[event.key];
    if (!step) return;
    event.preventDefault();
    showPhase(index + step);
  };
  return (
    <section className={styles.journey} aria-labelledby="modernization-journey" id="journey">
      <div className={styles.container}>
        <div className={styles.introRow}>
          <div>
            <SectionTitle
              id="modernization-journey"
              eyebrow={
                <Translate id="page.modernization.journey.eyebrow">
                  {'The move, in three phases'}
                </Translate>
              }
              title={
                <Translate id="page.modernization.journey.title1">
                  {'Step through the move'}
                </Translate>
              }
              accent={
                <Translate id="page.modernization.journey.title2">
                  {'from desktop to web'}
                </Translate>
              }
            />
          </div>
          <p className={styles.introLead}>
            <Translate id="page.modernization.journey.lead">
              {
                'Each phase ships something users can see. Pick a phase to see the screen at that point and the code that makes it work.'
              }
            </Translate>
          </p>
        </div>
        <div
          className={styles.track}
          role="tablist"
          aria-label={translate({
            id: 'page.modernization.journey.tabsLabel',
            message: 'Modernization phases',
          })}
          onKeyDown={onKeyDown}>
          {phases.map((phase, i) => (
            <button
              key={phase.id}
              type="button"
              role="tab"
              id={`${phase.id}-tab`}
              aria-controls={phase.id}
              aria-selected={i === index}
              tabIndex={i === index ? 0 : -1}
              className={`${styles.stop} ${i === index ? styles.stopActive : ''} ${i < index ? styles.stopDone : ''}`}
              onClick={() => showPhase(i)}>
              <span className={styles.stopMark}>{i + 1}</span>
              <span className={styles.stopText}>
                <span className={styles.stopPhase}>
                  <Translate id="page.modernization.journey.phase" values={{ number: i + 1 }}>
                    {'Phase {number}'}
                  </Translate>
                </span>
                <span className={styles.stopLabel}>{phase.label}</span>
              </span>
            </button>
          ))}
        </div>
        {/* All phases share one grid cell, so the stage keeps the height of
            the tallest phase and nothing below it moves when you step. */}
        <div className={styles.stage}>
          {phases.map((phase, i) => {
            const active = i === index;
            return (
              <div
                key={phase.id}
                id={phase.id}
                role="tabpanel"
                aria-labelledby={`${phase.id}-tab`}
                aria-hidden={!active}
                className={`${styles.panel} ${active ? styles.panelActive : ''}`}>
                <div className={styles.panelScreen}>{phase.screen()}</div>
                <div className={styles.panelCopy}>
                  <h3 className={styles.panelTitle}>{phase.title}</h3>
                  <p className={styles.panelText}>{phase.text}</p>
                  <p className={styles.panelNote}>{phase.note}</p>
                </div>
                <div className={styles.panelSide}>
                  {phase.code ? (
                    <CodeWindow file={phase.file}>{phase.code}</CodeWindow>
                  ) : (
                    <dl className={styles.endState}>
                      {phase.endState.map((row) => (
                        <div key={row.term}>
                          <dt>{row.term}</dt>
                          <dd>{row.desc}</dd>
                        </div>
                      ))}
                    </dl>
                  )}
                </div>
              </div>
            );
          })}
        </div>
        <div className={styles.stepper}>
          <p className={styles.stepperCount} aria-live="polite">
            <Translate
              id="page.modernization.journey.count"
              values={{ number: index + 1, total: phases.length }}>
              {'Phase {number} of {total}'}
            </Translate>
          </p>
          <div className={styles.stepperButtons}>
            <button
              type="button"
              className={styles.stepButton}
              onClick={() => showPhase(index - 1)}
              aria-label={translate({
                id: 'page.modernization.journey.prev',
                message: 'Previous phase',
              })}>
              <ArrowIcon className={styles.icon} direction="left" />
            </button>
            <button
              type="button"
              className={styles.stepButton}
              onClick={() => showPhase(index + 1)}
              aria-label={translate({
                id: 'page.modernization.journey.next',
                message: 'Next phase',
              })}>
              <ArrowIcon className={styles.icon} />
            </button>
          </div>
        </div>
      </div>
    </section>
  );
}

function Channel() {
  const mock = getMock();
  return (
    <section className={styles.talk} aria-labelledby="modernization-talk">
      <div className={styles.container}>
        <div className={styles.talkHead}>
          <SectionTitle
            id="modernization-talk"
            eyebrow={
              <Translate id="page.modernization.talk.eyebrow">{'Two-way communication'}</Translate>
            }
            title={
              <Translate id="page.modernization.talk.title1">{'Old and new screens'}</Translate>
            }
            accent={
              <Translate id="page.modernization.talk.title2">{'talk to each other'}</Translate>
            }
          />
          <p className={styles.sectionLead}>
            <Translate id="page.modernization.talk.lead">
              {
                'A message is an action: a name, an optional string payload and an optional binary payload. The name is yours to invent. Both ends are plain Java, so nobody writes browser script for the exchange.'
              }
            </Translate>
          </p>
        </div>
        <ol className={styles.trip}>
          <li className={styles.tripRow}>
            <div className={styles.tripLabel}>
              <span className={styles.tripNumber}>1</span>
              <div>
                <h3 className={styles.tripTitle}>
                  <Translate id="page.modernization.talk.announce.title">
                    {'Swing announces'}
                  </Translate>
                </h3>
                <p className={styles.tripText}>
                  <Translate
                    id="page.modernization.talk.announce.text"
                    values={{ action: <code>select-customer</code> }}>
                    {
                      'A double click sends {action} with the row as JSON. On the desktop the same click still opens the Swing dialog.'
                    }
                  </Translate>
                </p>
              </div>
            </div>
            <div className={styles.lanes} aria-hidden="true">
              <span className={styles.laneSwing}>{mock.laneSwing}</span>
              <span className={styles.laneWeb}>{mock.laneWeb}</span>
            </div>
            <div className={styles.tripCode}>
              <CodeWindow file="Application.java (Swing)">{SWING_ANNOUNCES}</CodeWindow>
              <span className={styles.tripArrow} aria-hidden="true">
                <ArrowIcon className={styles.icon} />
              </span>
              <CodeWindow file="CustomerBridgeView.java (webforJ)">{WEB_RECEIVES}</CodeWindow>
            </div>
          </li>
          <li className={styles.tripRow}>
            <div className={styles.tripLabel}>
              <span className={styles.tripNumber}>2</span>
              <div>
                <h3 className={styles.tripTitle}>
                  <Translate id="page.modernization.talk.reply.title">
                    {'webforJ replies'}
                  </Translate>
                </h3>
                <p className={styles.tripText}>
                  <Translate
                    id="page.modernization.talk.reply.text"
                    values={{ action: <code>update-customer</code> }}>
                    {
                      "On save the web form sends {action} back, and the Swing app refreshes its table. Neither side calls into the other one's internals."
                    }
                  </Translate>
                </p>
              </div>
            </div>
            <div className={styles.tripCode}>
              <CodeWindow file="Application.java (Swing)">{SWING_UPDATES}</CodeWindow>
              <span className={styles.tripArrow} aria-hidden="true">
                <ArrowIcon className={styles.icon} direction="left" />
              </span>
              <CodeWindow file="CustomerTableView.java (webforJ)">{WEB_REPLIES}</CodeWindow>
            </div>
          </li>
        </ol>
        <div className={styles.extras}>
          <div>
            <h3 className={styles.extraTitle}>
              <Translate id="page.modernization.talk.send.title">
                {'Send commands into Swing'}
              </Translate>
            </h3>
            <p className={styles.extraText}>
              <Translate id="page.modernization.talk.send.text">
                {
                  'performAction() sends a name alone or a name with data. The Swing app decides what each name means.'
                }
              </Translate>
            </p>
            <CodeWindow
              file={translate({
                id: 'page.modernization.talk.send.file',
                message: 'Sending actions',
              })}>
              {PERFORM}
            </CodeWindow>
          </div>
          <div>
            <h3 className={styles.extraTitle}>
              <Translate id="page.modernization.talk.ready.title">
                {'Know when Swing is ready'}
              </Translate>
            </h3>
            <p className={styles.extraText}>
              <Translate id="page.modernization.talk.ready.text">
                {
                  'onInitialize fires when the connection is ready. onStart fires when the Swing app has fully loaded. Send your first action from onStart.'
                }
              </Translate>
            </p>
            <CodeWindow
              file={translate({
                id: 'page.modernization.talk.ready.file',
                message: 'Lifecycle events',
              })}>
              {LIFECYCLE}
            </CodeWindow>
          </div>
        </div>
      </div>
    </section>
  );
}

function AiPort() {
  const loop = getLoop();
  return (
    <section className={styles.ai} aria-labelledby="modernization-ai">
      <div className={`${styles.container} ${styles.aiGrid}`}>
        <div>
          <SectionTitle
            id="modernization-ai"
            eyebrow={
              <Translate id="page.modernization.ai.eyebrow">
                {'Port with an AI assistant'}
              </Translate>
            }
            title={
              <Translate id="page.modernization.ai.title1">{'Let an assistant port it,'}</Translate>
            }
            accent={<Translate id="page.modernization.ai.title2">{'screen by screen'}</Translate>}
          />
          <p className={styles.sectionLead}>
            <Translate id="page.modernization.ai.lead">
              {
                'Moving screen by screen is repetitive work, and that suits an AI coding assistant. The webforJ AI plugin connects it to the webforJ MCP server and to Agent Skills, structured workflows that tell it when to use those tools, in what order, and how to validate the result.'
              }
            </Translate>
          </p>
          <div className={styles.actions}>
            <Link className={`${styles.button} ${styles.buttonPrimary}`} to="/docs/ai-tooling">
              <Translate id="page.modernization.ai.install">{'Install the AI plugin'}</Translate>
              <ArrowIcon className={styles.icon} />
            </Link>
            <Link
              className={`${styles.button} ${styles.buttonSecondary}`}
              to="/docs/ai-tooling/agent-skills">
              <Translate id="page.modernization.ai.skills">{'Read about Agent Skills'}</Translate>
            </Link>
          </div>
        </div>
        <ol className={styles.loop}>
          {loop.map((item, i) => (
            <li key={item.title}>
              <span className={styles.loopMark}>{i + 1}</span>
              <div>
                <p className={styles.loopTitle}>{item.title}</p>
                <p className={styles.loopText}>{item.text}</p>
              </div>
            </li>
          ))}
        </ol>
      </div>
    </section>
  );
}

function Start() {
  const [os, setOs] = useState(0);
  return (
    <section className={styles.start} aria-labelledby="modernization-start">
      <div className={styles.container}>
        <div className={styles.startHead}>
          <SectionTitle
            id="modernization-start"
            eyebrow={<Translate id="page.modernization.start.eyebrow">{'How to start'}</Translate>}
            title={<Translate id="page.modernization.start.title1">{'From a JAR file'}</Translate>}
            accent={
              <Translate id="page.modernization.start.title2">
                {'to a page in the browser'}
              </Translate>
            }
          />
          <p className={styles.sectionLead}>
            <Translate id="page.modernization.start.lead">
              {
                'You need a Swing, JavaFX or SWT app packaged as a JAR file, the Webswing server and webforJ 25.10 or later.'
              }
            </Translate>
          </p>
        </div>
        <ol className={styles.steps}>
          <li className={styles.stepCard}>
            <span className={styles.stepNumber}>1</span>
            <h3 className={styles.stepTitle}>
              <Translate id="page.modernization.start.step1.title">
                {'Download and start Webswing'}
              </Translate>
            </h3>
            <p className={styles.stepText}>
              <Translate
                id="page.modernization.start.step1.text"
                values={{ url: <code>http://localhost:8080</code> }}>
                {
                  'Download Webswing, extract the archive, then start the server with the script for your platform. It answers on {url}.'
                }
              </Translate>
            </p>
            <div className={styles.logoPlate}>
              <img
                src={WEBSWING_LOGO}
                width={1901}
                height={380}
                alt="Webswing"
                loading="lazy"
                decoding="async"
              />
            </div>
            <div
              className={styles.osTabs}
              role="tablist"
              aria-label={translate({
                id: 'page.modernization.start.platform',
                message: 'Platform',
              })}>
              {START_SCRIPTS.map((script, i) => (
                <button
                  key={script.os}
                  type="button"
                  role="tab"
                  aria-selected={i === os}
                  className={`${styles.osTab} ${i === os ? styles.osTabActive : ''}`}
                  onClick={() => setOs(i)}>
                  {script.os}
                </button>
              ))}
            </div>
            <CodeBlock language="bash" className={styles.code}>
              {START_SCRIPTS[os].command}
            </CodeBlock>
          </li>
          <li className={styles.stepCard}>
            <span className={styles.stepNumber}>2</span>
            <h3 className={styles.stepTitle}>
              <Translate id="page.modernization.start.step2.title">{'Register your app'}</Translate>
            </h3>
            <p className={styles.stepText}>
              <Translate
                id="page.modernization.start.step2.text"
                values={{
                  admin: <code>http://localhost:8080/admin</code>,
                  url: <code>http://localhost:8080/[app-name]/</code>,
                }}>
                {
                  'In the admin console at {admin}, set the application name, main class, classpath, JVM arguments and home directory. The app is then served at {url}.'
                }
              </Translate>
            </p>
            <dl className={styles.stepFacts}>
              <div>
                <dt>CORS</dt>
                <dd>
                  <Translate
                    id="page.modernization.start.step2.cors"
                    values={{ url: <code>http://localhost:8090</code> }}>
                    {"Add your webforJ app's origin to Allowed Origins, for example {url}."}
                  </Translate>
                </dd>
              </div>
              <div>
                <dt>
                  <Translate id="page.modernization.start.step2.portsTerm">{'Ports'}</Translate>
                </dt>
                <dd>
                  <Translate id="page.modernization.start.step2.ports">
                    {'Both typically use 8080. Change one of them so they do not conflict.'}
                  </Translate>
                </dd>
              </div>
            </dl>
          </li>
          <li className={styles.stepCard}>
            <span className={styles.stepNumber}>3</span>
            <h3 className={styles.stepTitle}>
              <Translate id="page.modernization.start.step3.title">
                {'Add the dependency'}
              </Translate>
            </h3>
            <p className={styles.stepText}>
              <Translate id="page.modernization.start.step3.text">
                {
                  'The webforj-webswing module provides WebswingConnector and the classes around it.'
                }
              </Translate>
            </p>
            <CodeBlock language="xml" className={styles.code}>
              {DEPENDENCY}
            </CodeBlock>
          </li>
          <li className={styles.stepCard}>
            <span className={styles.stepNumber}>4</span>
            <h3 className={styles.stepTitle}>
              <Translate id="page.modernization.start.step4.title">
                {'Embed and configure'}
              </Translate>
            </h3>
            <p className={styles.stepText}>
              <Translate id="page.modernization.start.step4.text">
                {
                  'Add a WebswingConnector to a view, as in phase 1. WebswingOptions changes its behavior, for example hiding the logout button when your webforJ app handles sign in.'
                }
              </Translate>
            </p>
            <CodeBlock language="java" className={styles.code}>
              {OPTIONS}
            </CodeBlock>
          </li>
        </ol>
      </div>
    </section>
  );
}

function Next() {
  const next = getNext();
  return (
    <section className={styles.next} aria-labelledby="modernization-next">
      <div className={styles.container}>
        <div className={styles.introRow}>
          <SectionTitle
            id="modernization-next"
            title={<Translate id="page.modernization.next.title1">{'Pick your'}</Translate>}
            accent={<Translate id="page.modernization.next.title2">{'first screen'}</Translate>}
          />
          <p className={styles.introLead}>
            <Translate id="page.modernization.next.lead">
              {
                'Start with a simple list and edit form. Embed the app, move that one screen to webforJ, and see how it runs next to the rest.'
              }
            </Translate>
          </p>
        </div>
        <ul className={styles.nextList}>
          {next.map((item) => (
            <li key={item.to}>
              <Link className={styles.nextLink} to={item.to}>
                <span className={styles.nextTitle}>
                  {item.title}
                  <ArrowIcon className={styles.icon} />
                </span>
                <span className={styles.nextText}>{item.text}</span>
              </Link>
            </li>
          ))}
        </ul>
      </div>
    </section>
  );
}

export default function Modernization() {
  return (
    <Layout
      title={translate({
        id: 'page.modernization.meta.title',
        message: 'Swing modernization: take your Java desktop app to the web',
      })}
      description={translate({
        id: 'page.modernization.meta.description',
        message:
          'Run your Swing, JavaFX or SWT app in the browser without changing its source, then replace its screens with webforJ one at a time. Same Java team, same code.',
      })}>
      <main className={styles.page}>
        <Hero />
        <Stays />
        <Journey />
        <Channel />
        <AiPort />
        <Start />
        <Next />
      </main>
    </Layout>
  );
}
