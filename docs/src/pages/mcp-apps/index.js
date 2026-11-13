import React, { useRef } from 'react';
import Layout from '@theme/Layout';
import Link from '@docusaurus/Link';
import CodeBlock from '@theme/CodeBlock';
import Translate, { translate } from '@docusaurus/Translate';
import { ArrowIcon, CheckIcon } from '@site/src/components/pageKit/icons';
import PreviewBadge from '@site/src/components/pageKit/PreviewBadge';
import usePauseOffscreen from '@site/src/components/pageKit/usePauseOffscreen';
import {
  ACTION,
  DISPLAY_MODES,
  MCP_APP,
  SEND_MESSAGE,
  getBaseline,
  getDocs,
  getFlow,
  getHostMore,
  getRunWays,
  getStartSteps,
  getVideo,
} from './_content';
import styles from './mcp-apps.module.css';
import VideoPlayer from '@site/src/components/VideoPlayer';

function cx(...classNames) {
  return classNames.filter(Boolean).join(' ');
}

function Code({ title, language, children }) {
  return (
    <CodeBlock language={language} title={title} className={styles.code}>
      {children}
    </CodeBlock>
  );
}

function Hero() {
  const video = getVideo();
  return (
    <header className={styles.hero}>
      <div className={styles.container}>
        <div className={styles.heroHead}>
          <div>
            <div className={styles.eyebrowRow}>
              <p className={styles.eyebrow}>webforJ MCP Apps</p>
              <PreviewBadge className={styles.previewBadge} />
            </div>
            <h1 className={styles.heroTitle}>
              <Translate id="page.mcpApps.hero.title1">Your Java views,</Translate>{' '}
              <span>
                <Translate id="page.mcpApps.hero.title2">inside the AI conversation</Translate>
              </span>
            </h1>
          </div>
          <div className={styles.heroSide}>
            <p className={styles.heroLead}>
              <Translate id="page.mcpApps.hero.lead">
                {
                  'An MCP-capable AI app, also called a host, opens a routed webforJ view inside its conversation. The person and the AI work with the same live UI.'
                }
              </Translate>
            </p>
            <div className={styles.actions}>
              <Link
                className={cx(styles.button, styles.buttonPrimary)}
                to="/docs/integrations/mcp-apps/spring">
                <Translate id="page.mcpApps.hero.primary">Set up with Spring Boot</Translate>
                <ArrowIcon className={styles.icon} />
              </Link>
              <a className={cx(styles.button, styles.buttonGhost)} href="#open">
                <Translate id="page.mcpApps.hero.secondary">See how it works</Translate>
              </a>
            </div>
          </div>
        </div>
        <figure className={styles.videoFrame}>
          <div className={styles.videoBar}>
            <span className={styles.videoDot} aria-hidden="true" />
            <span>
              <Translate id="page.mcpApps.hero.videoBar">
                An inventory view, open in the conversation
              </Translate>
            </span>
          </div>
          <VideoPlayer
            video={video.video}
            width={video.width}
            height={video.height}
            title={video.label}
            muted
            plain
            controlsOnPlay
          />
        </figure>
      </div>
    </header>
  );
}

// The round trip as a flat sequence diagram: the host lane on the left, the
// app lane on the right, one arrow per step. A small dot travels along each
// arrow in turn. It only moves with transform and opacity, pauses while the
// diagram is off screen and is hidden under reduced motion.
function Flow() {
  const flowRef = useRef(null);
  usePauseOffscreen(flowRef, styles.paused);
  return (
    <div ref={flowRef} className={styles.flow}>
      <div className={styles.flowHead} aria-hidden="true">
        <span className={styles.lane}>
          <Translate id="page.mcpApps.flow.hostLane">MCP host</Translate>
          <small>
            <Translate id="page.mcpApps.flow.hostLaneHint">the AI conversation</Translate>
          </small>
        </span>
        <span />
        <span className={styles.lane}>
          <Translate id="page.mcpApps.flow.appLane">webforJ app</Translate>
          <small>
            <Translate id="page.mcpApps.flow.appLaneHint">your routed views</Translate>
          </small>
        </span>
      </div>
      <ol className={styles.flowRows}>
        {getFlow().map((step, index) => (
          <li
            key={step.key}
            className={cx(styles.flowRow, step.to === 'host' && styles.flowBack)}
            style={{ '--step': index }}>
            <div className={styles.flowMid}>
              <p className={styles.flowLabel}>
                <span className={styles.flowWho}>
                  {String(index + 1).padStart(2, '0')} {step.who}
                </span>
                {step.text}
              </p>
              <span className={styles.flowLine} aria-hidden="true">
                <span className={styles.flowDot} />
              </span>
            </div>
          </li>
        ))}
      </ol>
    </div>
  );
}

function SharedView() {
  return (
    <section id="flow" className={styles.section} aria-labelledby="mcp-shared">
      <div className={styles.container}>
        <div className={styles.sectionHead}>
          <div>
            <p className={styles.eyebrow}>
              <Translate id="page.mcpApps.shared.eyebrow">One live UI</Translate>
            </p>
            <h2 id="mcp-shared" className={styles.sectionTitle}>
              <Translate id="page.mcpApps.shared.title1">The view stays in your app.</Translate>{' '}
              <span>
                <Translate id="page.mcpApps.shared.title2">Both of them use it.</Translate>
              </span>
            </h2>
          </div>
          <p className={styles.sectionLead}>
            <Translate id="page.mcpApps.shared.lead">
              {
                'The view the host shows is the same routed view a browser shows, with the same components, services, routing and state. The AI works with it through tools, and the person keeps using the rendered components directly.'
              }
            </Translate>
          </p>
        </div>
        <Flow />
      </div>
    </section>
  );
}

function Chapter({ id, number, eyebrow, title, lead, to, linkLabel, children }) {
  return (
    <section id={id} className={styles.chapter} aria-labelledby={`${id}-title`}>
      <div className={styles.container}>
        <div className={styles.chapterHead}>
          <span className={styles.chapterNumber} aria-hidden="true">
            {number}
          </span>
          <div>
            <p className={styles.eyebrow}>{eyebrow}</p>
            <h2 id={`${id}-title`} className={styles.sectionTitle}>
              {title}
            </h2>
          </div>
          <div className={styles.chapterLead}>
            {lead}
            {to && (
              <Link className={styles.textLink} to={to}>
                {linkLabel}
                <ArrowIcon className={styles.icon} />
              </Link>
            )}
          </div>
        </div>
        {children}
      </div>
    </section>
  );
}

function Open() {
  return (
    <Chapter
      id="open"
      number="01"
      eyebrow={<Translate id="page.mcpApps.open.eyebrow">Open a view with input</Translate>}
      title={
        <>
          <Translate id="page.mcpApps.open.title1">A route becomes a tool.</Translate>{' '}
          <span>
            <Translate id="page.mcpApps.open.title2">Its input sets the start.</Translate>
          </span>
        </>
      }
      lead={
        <p>
          <Translate
            id="page.mcpApps.open.lead"
            values={{
              app: <code>@McpApp</code>,
              record: <code>InventoryInput</code>,
              input: <code>@McpAppInput</code>,
            }}>
            {
              'Add {app} to a routed view and it is published as a tool. The description tells the AI when the tool is useful. One object type, such as an {record} record, describes the arguments, and an {input} method applies them after the route renders.'
            }
          </Translate>
        </p>
      }
      to="/docs/integrations/mcp-apps/opening-apps"
      linkLabel={<Translate id="page.mcpApps.open.link">Read about opening input</Translate>}>
      <div className={styles.openRow}>
        <Code language="java" title="InventoryView.java">
          {MCP_APP}
        </Code>
        <div className={styles.fact}>
          <h3 className={styles.factTitle}>
            <Translate id="page.mcpApps.open.namesTitle">Tool names from routes</Translate>
          </h3>
          <p className={styles.factText}>
            <Translate
              id="page.mcpApps.open.namesText"
              values={{
                name: <code>name</code>,
                route: <code>/inventory</code>,
                tool: <code>inventory</code>,
                nestedRoute: <code>/sales/inventory</code>,
                nestedTool: <code>sales_inventory</code>,
                rootTool: <code>app</code>,
              }}>
              {
                'Without {name}, webforJ derives the tool name from the route: {route} becomes {tool}, {nestedRoute} becomes {nestedTool}, and the root route becomes {rootTool}.'
              }
            </Translate>
          </p>
          <h3 className={cx(styles.factTitle, styles.factNext)}>
            <Translate id="page.mcpApps.open.modeTitle">A requested display mode</Translate>
          </h3>
          <p className={styles.factText}>
            <Translate id="page.mcpApps.open.modeText" values={{ mode: <code>displayMode</code> }}>
              {
                '{mode} asks the host how to present the view. The host can choose a different mode.'
              }
            </Translate>
          </p>
          <ul className={styles.chips}>
            {DISPLAY_MODES.map((mode) => (
              <li key={mode}>
                <code className={styles.displayMode}>{mode}</code>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </Chapter>
  );
}

function Change() {
  return (
    <Chapter
      id="change"
      number="02"
      eyebrow={<Translate id="page.mcpApps.change.eyebrow">Actions and updates</Translate>}
      title={
        <>
          <Translate id="page.mcpApps.change.title1">More tools,</Translate>{' '}
          <span>
            <Translate id="page.mcpApps.change.title2">for the view already open</Translate>
          </span>
        </>
      }
      lead={
        <p>
          <Translate id="page.mcpApps.change.lead" values={{ tool: <code>inventory</code> }}>
            {
              'These tools do not open the app. A call goes to the rendered {tool} view in the same MCP session. If that view is not open, the call returns an error that directs the host to call {tool} first.'
            }
          </Translate>
        </p>
      }
      to="/docs/integrations/mcp-apps/actions-updates"
      linkLabel={
        <Translate id="page.mcpApps.change.link">Read about actions and updates</Translate>
      }>
      <div className={styles.pair}>
        <div className={styles.card}>
          <code className={styles.toolName}>inventory_refresh_stock</code>
          <h3 className={styles.cardTitle}>
            <Translate id="page.mcpApps.change.actionTitle">An action is a view method</Translate>
          </h3>
          <p className={styles.cardText}>
            <Translate
              id="page.mcpApps.change.actionText"
              values={{
                annotation: <code>@McpAppAction</code>,
                method: <code>refreshStock</code>,
                tool: <code>inventory_refresh_stock</code>,
              }}>
              {
                'Add {annotation} to a method and it becomes a tool with its own input. The method name {method} produces {tool}.'
              }
            </Translate>
          </p>
          <Code language="java" title="InventoryView.java">
            {ACTION}
          </Code>
        </div>
        <div className={styles.card}>
          <code className={styles.toolName}>inventory_update</code>
          <h3 className={styles.cardTitle}>
            <Translate id="page.mcpApps.change.updateTitle">
              One update tool with the opening input
            </Translate>
          </h3>
          <p className={styles.cardText}>
            <Translate
              id="page.mcpApps.change.updateText"
              values={{
                observer: <code>McpAppUpdateObserver</code>,
                tool: <code>inventory</code>,
              }}>
              {
                'A view that implements {observer} gets one update tool with the same input schema as {tool}. The callback decides how to use the arguments and returns the tool result.'
              }
            </Translate>
          </p>
          <ul className={styles.points}>
            <li>
              <Translate id="page.mcpApps.change.updatePoint1">
                {'webforJ does not apply the values to components automatically.'}
              </Translate>
            </li>
            <li>
              <Translate id="page.mcpApps.change.updatePoint2">
                {'Calling it does not open the route or render another view.'}
              </Translate>
            </li>
            <li>
              <Translate id="page.mcpApps.change.updatePoint3">
                {'The update tool has no UI resource metadata.'}
              </Translate>
            </li>
            <li>
              <Translate id="page.mcpApps.change.updatePoint4">
                {'A view can use both an action and the update tool.'}
              </Translate>
            </li>
          </ul>
        </div>
      </div>
    </Chapter>
  );
}

function Host() {
  return (
    <Chapter
      id="host"
      number="03"
      eyebrow={<Translate id="page.mcpApps.host.eyebrow">Host interaction</Translate>}
      title={
        <>
          <Translate id="page.mcpApps.host.title1">The view talks back</Translate>{' '}
          <span>
            <Translate id="page.mcpApps.host.title2">to the conversation</Translate>
          </span>
        </>
      }
      lead={
        <p>
          <Translate id="page.mcpApps.host.lead" values={{ result: <code>PendingResult</code> }}>
            {
              'The same route also opens in a normal browser, so each host interaction starts by checking that a host is present. Host calls are asynchronous and return a {result}.'
            }
          </Translate>
        </p>
      }
      to="/docs/integrations/mcp-apps/host-interaction"
      linkLabel={<Translate id="page.mcpApps.host.link">Read about host interaction</Translate>}>
      <div className={styles.pair}>
        <div className={styles.card}>
          <h3 className={styles.cardTitle}>
            <Translate id="page.mcpApps.host.messageTitle">
              Continue the conversation from the view
            </Translate>
          </h3>
          <p className={styles.cardText}>
            <Translate
              id="page.mcpApps.host.messageText"
              values={{ call: <code>McpHost.ifPresent</code> }}>
              {
                'The person picks a warehouse and asks the AI to review its stock. The button sends that request as the next user message. {call} runs only when the view is connected to a host.'
              }
            </Translate>
          </p>
          <Code language="java" title="InventoryView.java">
            {SEND_MESSAGE}
          </Code>
        </div>
        <ul className={styles.more}>
          {getHostMore().map((item) => (
            <li key={item.key} className={styles.moreItem}>
              <h3 className={styles.factTitle}>{item.title}</h3>
              <p className={styles.factText}>{item.text}</p>
            </li>
          ))}
        </ul>
      </div>
    </Chapter>
  );
}

function Run() {
  return (
    <Chapter
      id="run"
      number="04"
      eyebrow={
        <Translate id="page.mcpApps.run.eyebrow">Spring Boot or a standard servlet app</Translate>
      }
      title={
        <>
          <Translate id="page.mcpApps.run.title1">Publish with Spring AI,</Translate>{' '}
          <span>
            <Translate id="page.mcpApps.run.title2">or assemble the server yourself</Translate>
          </span>
        </>
      }
      lead={
        <p>
          <Translate id="page.mcpApps.run.lead" values={{ route: <code>/inventory</code> }}>
            {
              'The view still works at {route} in a browser. Publishing adds the MCP tool, the UI resource that opens the route, and the servlet setup the host needs to embed webforJ.'
            }
          </Translate>
        </p>
      }>
      <div className={styles.ways}>
        {getRunWays().map((way) => (
          <div key={way.key} className={styles.card}>
            <span className={styles.tag}>{way.tag}</span>
            <h3 className={styles.cardTitle}>{way.title}</h3>
            <p className={styles.cardText}>{way.text}</p>
            <Link className={styles.textLink} to={way.to}>
              {way.link}
              <ArrowIcon className={styles.icon} />
            </Link>
          </div>
        ))}
      </div>
    </Chapter>
  );
}

function TestCard({ tag, children }) {
  return (
    <div className={styles.testCard}>
      <span className={styles.tag}>{tag}</span>
      {children}
    </div>
  );
}

function Test() {
  return (
    <Chapter
      id="test"
      number="05"
      eyebrow={<Translate id="page.mcpApps.test.eyebrow">Testing</Translate>}
      title={
        <>
          <Translate id="page.mcpApps.test.title1">Connect a host.</Translate>{' '}
          <span>
            <Translate id="page.mcpApps.test.title2">Check the baseline.</Translate>
          </span>
        </>
      }
      lead={
        <p>
          <Translate id="page.mcpApps.test.lead" values={{ tool: <code>inventory</code> }}>
            {
              'webforJ MCP Apps run in any MCP Apps-capable host. The minimal {tool} tool with no input is enough to confirm that a host can discover the tool and render the view.'
            }
          </Translate>
        </p>
      }
      to="/docs/integrations/mcp-apps/testing"
      linkLabel={<Translate id="page.mcpApps.test.link">Read the testing guide</Translate>}>
      <div className={styles.testGrid}>
        <TestCard tag={<Translate id="page.mcpApps.test.remoteTag">Remote hosts</Translate>}>
          <p className={styles.factText}>
            <Translate
              id="page.mcpApps.test.remoteText"
              values={{
                local: <code>localhost</code>,
                origin: <code>webforj.origin</code>,
                path: <code>/mcp</code>,
              }}>
              {
                'A host that connects from outside the development machine cannot reach {local}. Expose the app through a tunnel, set {origin} to the public origin, and give the host that origin with {path} added.'
              }
            </Translate>
          </p>
        </TestCard>
        <TestCard tag={<Translate id="page.mcpApps.test.localTag">Local inspector</Translate>}>
          <p className={styles.factText}>
            <Translate
              id="page.mcpApps.test.localText"
              values={{
                origin: <code>webforj.origin</code>,
                allowed: <code>webforj.mcp.allowed-origins</code>,
              }}>
              {
                "An inspector on the same machine connects to the local endpoint. Set the local {origin} and list the inspector's browser origin in {allowed}."
              }
            </Translate>
          </p>
        </TestCard>
        <TestCard
          tag={
            <Translate id="page.mcpApps.test.baselineTag">The baseline, for each host</Translate>
          }>
          <ul className={styles.checks}>
            {getBaseline().map((item) => (
              <li key={item.key}>
                <CheckIcon className={styles.checkIcon} />
                <span>{item}</span>
              </li>
            ))}
          </ul>
        </TestCard>
      </div>
    </Chapter>
  );
}

function Start() {
  return (
    <section id="start" className={styles.start} aria-labelledby="mcp-start">
      <div className={styles.container}>
        <div className={styles.sectionHead}>
          <div>
            <p className={styles.eyebrow}>
              <Translate id="page.mcpApps.start.eyebrow">How to start</Translate>
            </p>
            <h2 id="mcp-start" className={styles.sectionTitle}>
              <Translate id="page.mcpApps.start.title1">One view,</Translate>{' '}
              <span>
                <Translate id="page.mcpApps.start.title2">open in a host</Translate>
              </span>
            </h2>
          </div>
          <p className={styles.sectionLead}>
            <Translate id="page.mcpApps.start.lead">
              {
                'webforJ is the full-stack Java framework for business web apps: you build the whole app, screens included, in Java. MCP Apps are part of it since webforJ 26.02, and Spring Boot with Spring AI is the primary way to publish one.'
              }
            </Translate>
          </p>
        </div>
        <ol className={styles.steps}>
          {getStartSteps().map((step, index) => (
            <li key={step.key} className={styles.step}>
              <span className={styles.stepNumber}>{index + 1}</span>
              <h3 className={styles.stepTitle}>{step.title}</h3>
              <p className={styles.stepText}>{step.text}</p>
            </li>
          ))}
        </ol>
        <div className={styles.actions}>
          <Link
            className={cx(styles.button, styles.buttonPrimary)}
            to="/docs/integrations/mcp-apps/spring">
            <Translate id="page.mcpApps.start.primary">Set up with Spring Boot</Translate>
            <ArrowIcon className={styles.icon} />
          </Link>
          <Link
            className={cx(styles.button, styles.buttonGhost)}
            to="/docs/integrations/mcp-apps/without-spring">
            <Translate id="page.mcpApps.start.secondary">Set up without Spring</Translate>
          </Link>
        </div>

        <nav
          className={styles.docs}
          aria-label={translate({
            id: 'page.mcpApps.docs.navLabel',
            message: 'MCP Apps documentation',
          })}>
          <ul className={styles.docList}>
            {getDocs().map((doc) => (
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
        </nav>
      </div>
    </section>
  );
}

export default function McpApps() {
  return (
    <Layout
      title={translate({
        id: 'page.mcpApps.meta.title',
        message: 'MCP Apps: open webforJ views inside an AI conversation',
      })}
      description={translate({
        id: 'page.mcpApps.meta.description',
        message:
          'With webforJ MCP Apps, an MCP-capable AI app opens a routed webforJ view inside its conversation, and the person and the AI work with the same live UI.',
      })}>
      <main className={styles.page}>
        <Hero />
        <SharedView />
        <Open />
        <Change />
        <Host />
        <Run />
        <Test />
        <Start />
      </main>
    </Layout>
  );
}
