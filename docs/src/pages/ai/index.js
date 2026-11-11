import React, { useRef, useState } from 'react';
import Layout from '@theme/Layout';
import Link from '@docusaurus/Link';
import useBaseUrl from '@docusaurus/useBaseUrl';
import CodeBlock from '@theme/CodeBlock';
import Translate, { translate } from '@docusaurus/Translate';
import { ArrowIcon, CheckIcon } from '@site/src/components/pageKit/icons';
import usePauseOffscreen from '@site/src/components/pageKit/usePauseOffscreen';
import {
  CRAFTFORJ_CONF,
  GREETING_TEST,
  GREETING_VIEW,
  INSTALL_COMMANDS,
  MCP_ONLY,
  TESTBED_DEPENDENCY,
  getBreaks,
  getClients,
  getCompare,
  getDocs,
  getMcpCapabilities,
  getPluginClients,
  getPluginPrompts,
  getSkills,
  getStages,
} from './_content';
import styles from './ai.module.css';

// The prompt character in front of each kind of terminal line. An `ok`
// line shows a check mark instead.
const LINE_PROMPTS = { cmd: '$', slash: '>', note: '#' };

const LINE_CLASSES = { note: styles.lineNote, ok: styles.lineOk };

function cx(...classNames) {
  return classNames.filter(Boolean).join(' ');
}

// A tab list that switches panels with the arrow keys, Home and End. Every
// panel stays in the layout and only the active one is visible, so the
// height never changes. `idPrefix` keeps the tab and panel ids unique.
function useTabs(ids, idPrefix) {
  const [active, setActive] = useState(0);
  const tabRefs = useRef([]);
  const count = ids.length;

  const onKeyDown = (event) => {
    let next = null;
    if (event.key === 'ArrowRight' || event.key === 'ArrowDown') next = (active + 1) % count;
    if (event.key === 'ArrowLeft' || event.key === 'ArrowUp') next = (active - 1 + count) % count;
    if (event.key === 'Home') next = 0;
    if (event.key === 'End') next = count - 1;
    if (next !== null) {
      event.preventDefault();
      setActive(next);
      tabRefs.current[next]?.focus();
    }
  };

  const getTabProps = (index) => ({
    ref: (node) => (tabRefs.current[index] = node),
    type: 'button',
    role: 'tab',
    id: `${idPrefix}-tab-${ids[index]}`,
    'aria-controls': `${idPrefix}-panel-${ids[index]}`,
    'aria-selected': index === active,
    tabIndex: index === active ? 0 : -1,
    onClick: () => setActive(index),
    onKeyDown,
  });

  const getPanelProps = (index) => ({
    id: `${idPrefix}-panel-${ids[index]}`,
    role: 'tabpanel',
    'aria-labelledby': `${idPrefix}-tab-${ids[index]}`,
    'aria-hidden': index !== active,
  });

  return { active, setActive, getTabProps, getPanelProps };
}

function WindowDots() {
  return (
    <span className={styles.dots} aria-hidden="true">
      <i />
      <i />
      <i />
    </span>
  );
}

function WindowBar({ title }) {
  return (
    <div className={styles.windowBar}>
      <WindowDots />
      <span className={styles.windowTitle}>{title}</span>
    </div>
  );
}

function CodeWindow({ title, language, caption, children }) {
  return (
    <figure className={styles.codeWindow}>
      <WindowBar title={title} />
      <CodeBlock language={language} className={styles.code}>
        {children}
      </CodeBlock>
      {caption && <figcaption className={styles.codeCaption}>{caption}</figcaption>}
    </figure>
  );
}

function TerminalLine({ kind, text }) {
  return (
    <div className={cx(styles.line, LINE_CLASSES[kind])}>
      {kind === 'ok' ? (
        <CheckIcon className={styles.lineCheck} />
      ) : (
        <span className={styles.prompt}>{LINE_PROMPTS[kind]}</span>
      )}
      <span className={styles.lineText}>{text}</span>
    </div>
  );
}

// The install commands for each client. The cursor blinks only while the
// terminal is on screen.
function Terminal() {
  const clients = getClients();
  const { active, getTabProps, getPanelProps } = useTabs(
    clients.map((client) => client.id),
    'term',
  );
  const terminalRef = useRef(null);
  usePauseOffscreen(terminalRef, styles.paused);
  return (
    <figure ref={terminalRef} className={styles.terminal}>
      <div className={styles.terminalTop}>
        <WindowDots />
        <div
          className={styles.terminalTabs}
          role="tablist"
          aria-label={translate({
            id: 'page.ai.terminal.tablistLabel',
            message: 'Install the webforJ AI plugin',
          })}>
          {clients.map((client, index) => (
            <button
              key={client.id}
              {...getTabProps(index)}
              className={cx(styles.terminalTab, index === active && styles.terminalTabActive)}>
              {client.label}
            </button>
          ))}
        </div>
      </div>
      <div className={styles.stack}>
        {clients.map((client, index) => (
          <div
            key={client.id}
            {...getPanelProps(index)}
            className={cx(
              styles.stackItem,
              styles.terminalBody,
              index === active && styles.stackActive,
            )}>
            {client.lines.map((line) => (
              <TerminalLine key={line.text} kind={line.kind} text={line.text} />
            ))}
            {index === active && <span className={styles.cursor} aria-hidden="true" />}
          </div>
        ))}
      </div>
      <figcaption className={styles.terminalCaption}>
        <Translate id="page.ai.terminal.caption">
          The install commands from the docs. One install brings the MCP server and the Agent
          Skills.
        </Translate>
      </figcaption>
    </figure>
  );
}

function Hero() {
  return (
    <header className={styles.hero}>
      <div className={styles.container}>
        <div className={styles.heroGrid}>
          <div>
            <p className={styles.eyebrow}>
              <Translate id="page.ai.hero.eyebrow">AI tooling for webforJ</Translate>
            </p>
            <h1 className={styles.heroTitle}>
              <Translate id="page.ai.hero.title1">Your AI assistant writes the Java.</Translate>{' '}
              <span>
                <Translate id="page.ai.hero.title2">Now it can check it.</Translate>
              </span>
            </h1>
            <p className={styles.heroLead}>
              <Translate id="page.ai.hero.lead">
                AI coding tools are fast, and their output still needs checking. webforJ gives them
                a loop of write, check and review: the real API to read, the right workflow to
                follow, and a test that returns pass or fail without a browser.
              </Translate>
            </p>
            <div className={styles.actions}>
              <Link className={cx(styles.button, styles.buttonPrimary)} to="/docs/ai-tooling">
                <Translate id="page.ai.hero.install">Install the plugin</Translate>
                <ArrowIcon className={styles.icon} />
              </Link>
              <a className={cx(styles.button, styles.buttonGhost)} href="#loop">
                <Translate id="page.ai.hero.step">Step through the loop</Translate>
              </a>
            </div>
            <p className={styles.heroClients}>
              <Translate id="page.ai.hero.clients">
                For Claude Code, GitHub Copilot, Cursor, Gemini and Codex.
              </Translate>
            </p>
          </div>
          <Terminal />
        </div>
      </div>
    </header>
  );
}

function Breaks() {
  return (
    <section className={styles.section} aria-labelledby="ai-breaks">
      <div className={styles.container}>
        <div className={styles.sectionHead}>
          <p className={styles.eyebrow}>
            <Translate id="page.ai.breaks.eyebrow">Why a loop</Translate>
          </p>
          <h2 id="ai-breaks" className={styles.sectionTitle}>
            <Translate id="page.ai.breaks.title">
              Three ways agent-built Java breaks, and the check for each
            </Translate>
          </h2>
          <p className={styles.sectionLead}>
            <Translate id="page.ai.breaks.lead">
              When a coding agent has nothing to look things up in, it writes from memory. Telling
              it to be careful fixes none of these. Each failure needs its own check, and webforJ
              closes each gap with a piece of tooling.
            </Translate>
          </p>
        </div>
        <ol className={styles.breaks}>
          {getBreaks().map((item, index) => (
            <li key={item.piece} className={styles.breakRow}>
              <span className={styles.breakNumber}>0{index + 1}</span>
              <div className={styles.breakFailure}>
                <span className={cx(styles.label, styles.breakLabel)}>
                  <Translate id="page.ai.breaks.without">Without a check</Translate>
                </span>
                <p>{item.failure}</p>
              </div>
              <div className={styles.breakFix}>
                <span className={cx(styles.label, styles.breakLabel)}>{item.piece}</span>
                <p>{item.fix}</p>
                <Link className={styles.textLink} to={item.to}>
                  <Translate id="page.ai.breaks.read">Read the docs</Translate>
                  <ArrowIcon className={styles.icon} />
                </Link>
              </div>
            </li>
          ))}
        </ol>
      </div>
    </section>
  );
}

function PromptCard({ children }) {
  return (
    <div className={styles.promptCard}>
      <span className={cx(styles.label, styles.promptLabel)}>
        <Translate id="page.ai.stages.promptLabel">Your prompt</Translate>
      </span>
      <p className={styles.promptText}>{children}</p>
    </div>
  );
}

function StageWrite() {
  return (
    <div className={styles.stageGrid}>
      <div className={styles.stageText}>
        <PromptCard>
          <Translate id="page.ai.stages.write.prompt">
            Add a webforJ greeting view at /greeting. A name field, a Greet button, and a paragraph
            that says Hello with the name.
          </Translate>
        </PromptCard>
        <h3 className={styles.stageTitle}>
          <Translate id="page.ai.stages.write.title">A view is a Java class with a route</Translate>
        </h3>
        <p>
          <Translate id="page.ai.stages.write.text1">
            Nothing else is needed: no template, no JavaScript, and no REST layer between the screen
            and your code.
          </Translate>
        </p>
        <p>
          <Translate
            id="page.ai.stages.write.text2"
            values={{
              composite: <code>Composite</code>,
              button: <code>Button</code>,
              route: <code>@Route</code>,
            }}>
            {
              'With the MCP server connected, the agent has looked up {composite}, {button} and {route} before it writes them.'
            }
          </Translate>
        </p>
      </div>
      <CodeWindow title="GreetingView.java" language="java">
        {GREETING_VIEW}
      </CodeWindow>
    </div>
  );
}

function StageCheck() {
  return (
    <div className={styles.stageGrid}>
      <div className={styles.stageText}>
        <PromptCard>
          <Translate id="page.ai.stages.check.prompt">
            Write a TestBed test for GreetingView that types a name, clicks Greet, and checks the
            paragraph.
          </Translate>
        </PromptCard>
        <h3 className={styles.stageTitle}>
          <Translate id="page.ai.stages.check.title">
            The test follows the steps of a user
          </Translate>
        </h3>
        <ul className={styles.stageList}>
          <li>
            <Translate
              id="page.ai.stages.check.locator"
              values={{ code: <code>ComponentLocator</code> }}>
              {'{code} finds the field by its label and the button by its text.'}
            </Translate>
          </li>
          <li>
            <Translate
              id="page.ai.stages.check.interaction"
              values={{ code: <code>UserInteraction</code> }}>
              {'{code} types and clicks, so the listener of the button really runs.'}
            </Translate>
          </li>
          <li>
            <Translate id="page.ai.stages.check.verify" values={{ code: <code>verifyThat</code> }}>
              {'{code} checks the text a user would read.'}
            </Translate>
          </li>
        </ul>
      </div>
      <CodeWindow title="GreetingViewTest.java" language="java">
        {GREETING_TEST}
      </CodeWindow>
    </div>
  );
}

function StageReview() {
  return (
    <div className={styles.stageGrid}>
      <div className={styles.stageText}>
        <h3 className={styles.stageTitle}>
          <Translate id="page.ai.stages.review.title">The agent reads a pass or a fail</Translate>
        </h3>
        <p>
          <Translate id="page.ai.stages.review.text1" values={{ code: <code>mvn test</code> }}>
            {
              'The agent runs {code} and reads the result. No browser is opened and no server is started.'
            }
          </Translate>
        </p>
        <p>
          <Translate id="page.ai.stages.review.text2">
            If it fails, the message says what differed, and the agent edits and runs again. A
            failure it can read is a failure it can fix.
          </Translate>
        </p>
        <p className={styles.stageStrong}>
          <Translate id="page.ai.stages.review.strong">
            You review a result that already passed instead of a guess.
          </Translate>
        </p>
      </div>
      <div className={styles.result}>
        <div className={styles.resultTerminal}>
          <WindowBar
            title={translate({ id: 'page.ai.stages.review.terminal', message: 'Terminal' })}
          />
          <div className={styles.resultBody}>
            <TerminalLine kind="cmd" text="mvn test" />
          </div>
        </div>
        <div className={styles.resultPass}>
          <CheckIcon className={styles.passIcon} />
          <div>
            <span className={cx(styles.label, styles.resultLabel)}>GreetingViewTest</span>
            <span className={styles.resultName}>shouldGreetTheNameTheUserTyped</span>
            <span className={styles.resultState}>
              <Translate id="page.ai.stages.review.passed">Passed</Translate>
            </span>
          </div>
        </div>
        <div className={styles.resultFail}>
          <span className={cx(styles.label, styles.resultLabel)}>
            <Translate id="page.ai.stages.review.failLabel">
              When a check fails, the message reads like this
            </Translate>
          </span>
          <code className={styles.failMessage}>
            Expected TextField to have value &lt;Grace&gt; but it has &lt;Ada&gt;.
          </code>
          <span className={styles.resultNote}>
            <Translate id="page.ai.stages.review.failNote">
              It names the type of the component, the expected value and the actual one.
            </Translate>
          </span>
        </div>
      </div>
    </div>
  );
}

const STAGE_VIEWS = { write: StageWrite, check: StageCheck, review: StageReview };

function Loop() {
  const stages = getStages();
  const { active, setActive, getTabProps, getPanelProps } = useTabs(
    stages.map((stage) => stage.id),
    'loop',
  );
  return (
    <section id="loop" className={styles.section} aria-labelledby="ai-loop">
      <div className={styles.container}>
        <div className={styles.sectionHead}>
          <p className={styles.eyebrow}>
            <Translate id="page.ai.loop.eyebrow">The loop</Translate>
          </p>
          <h2 id="ai-loop" className={styles.sectionTitle}>
            <Translate id="page.ai.loop.title">Write, check, review</Translate>
          </h2>
          <p className={styles.sectionLead}>
            <Translate id="page.ai.loop.lead">
              The agent writes the view, writes the test, runs it, and reads a pass or a fail. Step
              through one real screen, from the prompt to the passing test.
            </Translate>
          </p>
        </div>

        <div
          className={styles.rail}
          role="tablist"
          aria-label={translate({ id: 'page.ai.loop.railLabel', message: 'Stages of the loop' })}>
          {stages.map((stage, index) => (
            <button
              key={stage.id}
              {...getTabProps(index)}
              className={cx(
                styles.railStep,
                index === active && styles.railStepActive,
                index < active && styles.railStepDone,
              )}>
              <span className={styles.railNumber}>0{index + 1}</span>
              <span className={styles.railLabel}>{stage.label}</span>
              <span className={styles.railShort}>{stage.short}</span>
            </button>
          ))}
          <span className={styles.railReturn} aria-hidden="true">
            <span>
              <Translate id="page.ai.loop.return">If the test fails, back to write</Translate>
            </span>
          </span>
        </div>

        <div className={styles.stagePanel}>
          <div className={styles.stack}>
            {stages.map((stage, index) => {
              const StageView = STAGE_VIEWS[stage.id];
              return (
                <div
                  key={stage.id}
                  {...getPanelProps(index)}
                  className={cx(styles.stackItem, index === active && styles.stackActive)}>
                  <StageView />
                </div>
              );
            })}
          </div>
          <div className={styles.stageNav}>
            <button
              type="button"
              className={styles.navButton}
              disabled={active === 0}
              onClick={() => setActive(active - 1)}>
              <Translate id="page.ai.loop.previous">Previous stage</Translate>
            </button>
            <span className={styles.stageCount}>
              <Translate
                id="page.ai.loop.count"
                values={{ current: active + 1, total: stages.length }}>
                {'Stage {current} of {total}'}
              </Translate>
            </span>
            <button
              type="button"
              className={cx(styles.navButton, styles.navButtonPrimary)}
              disabled={active === stages.length - 1}
              onClick={() => setActive(active + 1)}>
              <Translate id="page.ai.loop.next">Next stage</Translate>
            </button>
          </div>
        </div>
      </div>
    </section>
  );
}

function PiecesIndex() {
  return (
    <div className={styles.piecesHead}>
      <div className={styles.container}>
        <p className={styles.eyebrow}>
          <Translate id="page.ai.pieces.eyebrow">The pieces</Translate>
        </p>
        <h2 className={styles.sectionTitle}>
          <Translate id="page.ai.pieces.title">Three pieces behind the loop</Translate>
        </h2>
        {/* The AI plugin bundles the MCP server and the Agent Skills, so those
            two are listed as its parts, not beside it. */}
        <ol className={styles.pieceIndex}>
          <li className={styles.pieceGroup}>
            <a href="#plugin" data-number="01">
              <Translate id="page.ai.pieces.plugin">AI plugin</Translate>
            </a>
            <span className={styles.pieceBundles}>
              <Translate id="page.ai.pieces.bundles">bundles</Translate>
            </span>
            <a href="#mcp" data-number="01a">
              <Translate id="page.ai.pieces.mcp">MCP server</Translate>
            </a>
            <a href="#skills" data-number="01b">
              <Translate id="page.ai.pieces.skills">Agent Skills</Translate>
            </a>
          </li>
          <li>
            <a href="#testbed" data-number="02">
              TestBed
            </a>
          </li>
          <li>
            <a href="#craftforj" data-number="03">
              craftforJ Assistant
            </a>
          </li>
        </ol>
      </div>
    </div>
  );
}

// One chapter per piece: the intro with its docs link on the left and the
// real thing on the right. `primary` gives the link the solid button style.
function Piece({ id, number, name, title, lead, to, linkLabel, primary, children }) {
  return (
    <article id={id} className={styles.piece} aria-labelledby={`${id}-title`}>
      <div className={styles.container}>
        <div className={styles.pieceGrid}>
          <div className={styles.pieceIntro}>
            <p className={styles.eyebrow}>
              <span className={styles.pieceNumber}>{number}</span> {name}
            </p>
            <h2 id={`${id}-title`} className={styles.pieceTitle}>
              {title}
            </h2>
            <div className={styles.pieceLead}>{lead}</div>
            <Link
              className={cx(styles.button, primary ? styles.buttonPrimary : styles.buttonSecondary)}
              to={to}>
              {linkLabel}
              <ArrowIcon className={styles.icon} />
            </Link>
          </div>
          <div className={styles.pieceBody}>{children}</div>
        </div>
      </div>
    </article>
  );
}

function PluginPiece() {
  return (
    <Piece
      id="plugin"
      number="01"
      name={translate({ id: 'page.ai.plugin.name', message: 'webforJ AI plugin' })}
      title={translate({ id: 'page.ai.plugin.title', message: 'One install brings both pieces' })}
      lead={
        <>
          <p>
            <Translate id="page.ai.plugin.lead1">
              The plugin bundles the MCP server and the Agent Skills. Together they turn an
              assistant that guesses at webforJ conventions into one that follows them.
            </Translate>
          </p>
          <p>
            <Translate id="page.ai.plugin.lead2" values={{ name: <strong>webforJ</strong> }}>
              {
                'Mention {name} in your prompts. That is the cue that sends the assistant to the plugin instead of general Java knowledge.'
              }
            </Translate>
          </p>
        </>
      }
      to="/docs/ai-tooling"
      linkLabel={translate({ id: 'page.ai.plugin.link', message: 'Install the plugin' })}>
      <div className={styles.panel}>
        <h3 className={cx(styles.label, styles.panelTitle)}>
          <Translate id="page.ai.plugin.where">Where it installs</Translate>
        </h3>
        <ul className={styles.clientList}>
          {getPluginClients().map((client) => (
            <li key={client.name}>
              <span className={styles.clientName}>{client.name}</span>
              <span className={styles.clientHow}>{client.how}</span>
            </li>
          ))}
        </ul>
      </div>
      <div className={styles.panel}>
        <h3 className={cx(styles.label, styles.panelTitle)}>
          <Translate id="page.ai.plugin.reaches">What a prompt reaches for</Translate>
        </h3>
        <ul className={styles.routeList}>
          {getPluginPrompts().map(([prompt, target]) => (
            <li key={prompt}>
              <span className={styles.routePrompt}>{prompt}</span>
              <span className={styles.routeTarget}>
                <ArrowIcon className={styles.icon} />
                {target}
              </span>
            </li>
          ))}
        </ul>
      </div>
    </Piece>
  );
}

function McpPiece() {
  return (
    <Piece
      id="mcp"
      number="01a"
      name={translate({ id: 'page.ai.mcp.name', message: 'MCP server, part of the AI plugin' })}
      title={translate({
        id: 'page.ai.mcp.title',
        message: 'The assistant reads the real API instead of recalling it',
      })}
      lead={
        <>
          <p>
            <Translate id="page.ai.mcp.lead1">
              Model Context Protocol is an open standard that lets AI assistants call external tools
              on demand. The webforJ MCP server answers from the real framework: docs, design
              tokens, component styling and project scaffolding.
            </Translate>
          </p>
          <p>
            <Translate id="page.ai.mcp.lead2">
              Validation catches typos and invented tokens before they ship as silently failing CSS.
            </Translate>
          </p>
        </>
      }
      to="/docs/ai-tooling/mcp"
      linkLabel={translate({ id: 'page.ai.mcp.link', message: 'See what the server can do' })}>
      <ol className={styles.capabilities}>
        {getMcpCapabilities().map((capability) => (
          <li key={capability.title} className={styles.capability}>
            <h3 className={styles.capabilityTitle}>{capability.title}</h3>
            <p className={styles.capabilityText}>{capability.text}</p>
            {capability.prompt && (
              <code className={styles.capabilityPrompt}>{capability.prompt}</code>
            )}
          </li>
        ))}
      </ol>
      <CodeWindow
        title={translate({
          id: 'page.ai.mcp.onlyTitle',
          message: 'Only the MCP server, in Claude Code',
        })}
        language="bash"
        caption={translate({
          id: 'page.ai.mcp.onlyCaption',
          message:
            'The plugin is the recommended install. This command connects the server alone, at https://mcp.webforj.com/mcp.',
        })}>
        {MCP_ONLY}
      </CodeWindow>
    </Piece>
  );
}

function SkillsPiece() {
  return (
    <Piece
      id="skills"
      number="01b"
      name={translate({
        id: 'page.ai.skills.name',
        message: 'Agent Skills, part of the AI plugin',
      })}
      title={translate({
        id: 'page.ai.skills.title',
        message: 'The right workflow for the task in front of it',
      })}
      lead={
        <>
          <p>
            <Translate id="page.ai.skills.lead1">
              The MCP server makes accurate information available. A skill tells the assistant when
              to look something up, which approach fits, and what order to do things in.
            </Translate>
          </p>
          <p>
            <Translate id="page.ai.skills.lead2">
              Skills follow the open Agent Skills standard. The assistant loads one automatically
              when your prompt matches it.
            </Translate>
          </p>
        </>
      }
      to="/docs/ai-tooling/agent-skills"
      linkLabel={translate({ id: 'page.ai.skills.link', message: 'Browse the skills' })}>
      <ul className={styles.skills}>
        {getSkills().map((skill) => (
          <li key={skill.name} className={styles.skill}>
            <code className={styles.skillName}>{skill.name}</code>
            <p className={styles.skillText}>{skill.text}</p>
            <span className={styles.skillPrompt}>{skill.prompt}</span>
          </li>
        ))}
      </ul>
    </Piece>
  );
}

function TestPart({ verb, api, children }) {
  return (
    <li className={styles.testPart}>
      <span className={styles.testVerb}>{verb}</span>
      <code>{api}</code>
      <p>{children}</p>
    </li>
  );
}

function TestbedPiece() {
  return (
    <Piece
      id="testbed"
      number="02"
      name="TestBed"
      title={translate({
        id: 'page.ai.testbed.title',
        message: 'A clear pass or fail, with no browser and no server',
      })}
      lead={
        <>
          <p>
            <Translate id="page.ai.testbed.lead1">
              A view runs inside a plain JUnit test. Components attach, fire their events, and
              navigate exactly as they do in a running app.
            </Translate>
          </p>
          <p>
            <Translate
              id="page.ai.testbed.lead2"
              values={{ module: <code>webforj-spring-testbed</code> }}>
              {
                'Every interaction finishes before the next line of the test runs, so nothing waits and nothing flakes. Spring Boot apps use the {module} module.'
              }
            </Translate>
          </p>
        </>
      }
      to="/docs/testing/testbed/overview"
      linkLabel={translate({ id: 'page.ai.testbed.link', message: 'Read the TestBed guide' })}
      primary>
      <ol className={styles.testParts}>
        <TestPart
          verb={<Translate id="page.ai.testbed.find.verb">Find</Translate>}
          api="ComponentLocator">
          <Translate id="page.ai.testbed.find.text">
            Describes a component the way a user picks it out, by its type and what it shows.
          </Translate>
        </TestPart>
        <TestPart
          verb={<Translate id="page.ai.testbed.act.verb">Act</Translate>}
          api="UserInteraction">
          <Translate id="page.ai.testbed.act.text">
            Makes only the gestures a user could make. Clicking a disabled button fails the test.
          </Translate>
        </TestPart>
        <TestPart
          verb={<Translate id="page.ai.testbed.check.verb">Check</Translate>}
          api="verifyThat">
          <Translate id="page.ai.testbed.check.text">
            Checks what the user would see, and a failure names the component and both values.
          </Translate>
        </TestPart>
      </ol>
      <CodeWindow
        title="pom.xml"
        language="xml"
        caption={translate({
          id: 'page.ai.testbed.caption',
          message:
            'The version comes from the webforJ BOM. A project created from an archetype already imports it.',
        })}>
        {TESTBED_DEPENDENCY}
      </CodeWindow>
    </Piece>
  );
}

function CraftforjPiece() {
  const screenshotUrl = useBaseUrl('/img/craftforj/ai/approval-prompt.png');
  return (
    <Piece
      id="craftforj"
      number="03"
      name="craftforJ Assistant"
      title={translate({
        id: 'page.ai.craftforj.title',
        message: 'A coding agent inside the running app',
      })}
      lead={
        <>
          <p>
            <Translate id="page.ai.craftforj.lead1">
              It writes Java freely, compiles each edit before you see it, and applies it to the
              running app. Applying a change restarts the app, and the agent picks its plan up again
              once it is back.
            </Translate>
          </p>
          <p>
            <Translate id="page.ai.craftforj.lead2">
              It ships with webforJ, so there is nothing to install. craftforJ ships no model of its
              own: add an API key for a supported provider or point it at a model running locally
              with Ollama.
            </Translate>
          </p>
        </>
      }
      to="/docs/ai-tooling/craftforj-assistant"
      linkLabel={translate({ id: 'page.ai.craftforj.link', message: 'Meet the assistant' })}>
      <div className={styles.compare}>
        <div className={styles.compareHead} aria-hidden="true">
          <span />
          <span>
            <Translate id="page.ai.craftforj.colPlugin">AI plugin</Translate>
          </span>
          <span>craftforJ Assistant</span>
        </div>
        <dl className={styles.compareRows}>
          {getCompare().map(([label, plugin, assistant]) => (
            <div key={label} className={styles.compareRow}>
              <dt className={styles.label}>{label}</dt>
              <dd>
                <span className={styles.compareMobile}>
                  <Translate id="page.ai.craftforj.mobilePlugin">AI plugin</Translate>
                </span>
                {plugin}
              </dd>
              <dd className={styles.compareStrong}>
                <span className={styles.compareMobile}>craftforJ Assistant</span>
                {assistant}
              </dd>
            </div>
          ))}
        </dl>
      </div>
      <div className={styles.craftRow}>
        <figure className={styles.screenshot}>
          <img
            src={screenshotUrl}
            width={638}
            height={177}
            loading="lazy"
            alt={translate({
              id: 'page.ai.craftforj.shotAlt',
              message:
                'The craftforJ Assistant asking before it acts, inline in the transcript, with Apply, Allow for chat and Dismiss.',
            })}
          />
          <figcaption>
            <Translate id="page.ai.craftforj.shotCaption">
              The assistant asking before it acts, inline in the transcript.
            </Translate>
          </figcaption>
        </figure>
        <CodeWindow
          title="webforj.conf"
          language="ini"
          caption={
            <Translate
              id="page.ai.craftforj.confCaption"
              values={{
                alt: <kbd>Alt</kbd>,
                shift: <kbd>Shift</kbd>,
                d: <kbd>D</kbd>,
              }}>
              {
                'craftforJ runs only in development. Open it with {alt} + {shift} + {d} and switch to the AI Assistant tab.'
              }
            </Translate>
          }>
          {CRAFTFORJ_CONF}
        </CodeWindow>
      </div>
    </Piece>
  );
}

function StartStep({ number, title, children }) {
  return (
    <li className={styles.stepItem}>
      <span className={styles.stepNumber}>{number}</span>
      <div className={styles.stepBody}>
        <h3>{title}</h3>
        {children}
      </div>
    </li>
  );
}

function Start() {
  return (
    <section className={styles.section} aria-labelledby="ai-start">
      <div className={styles.container}>
        <div className={styles.startGrid}>
          <div>
            <p className={styles.eyebrow}>
              <Translate id="page.ai.start.eyebrow">How to start</Translate>
            </p>
            <h2 id="ai-start" className={styles.sectionTitle}>
              <Translate id="page.ai.start.title">Ask for one screen with a test</Translate>
            </h2>
            <p className={styles.sectionLead}>
              <Translate id="page.ai.start.lead">
                Pick a screen you would otherwise build by hand this week. webforJ is the full-stack
                Java framework for business web apps: you build the whole app, screens included, in
                Java.
              </Translate>
            </p>
          </div>
          <ol className={styles.steps}>
            <StartStep
              number={1}
              title={<Translate id="page.ai.start.step1">Install the plugin</Translate>}>
              <CodeBlock language="bash" className={cx(styles.code, styles.stepCode)}>
                {INSTALL_COMMANDS}
              </CodeBlock>
            </StartStep>
            <StartStep
              number={2}
              title={
                <Translate id="page.ai.start.step2.title">
                  Scaffold or open a webforJ project
                </Translate>
              }>
              <p>
                <Translate id="page.ai.start.step2.text">
                  Ask the MCP server for a project, or open one you already have.
                </Translate>
              </p>
            </StartStep>
            <StartStep
              number={3}
              title={
                <Translate id="page.ai.start.step3.title">
                  Ask for the view and its TestBed test in one prompt
                </Translate>
              }>
              <p>
                <Translate id="page.ai.start.step3.text">
                  Describe the behavior you care about, so the test asserts it.
                </Translate>
              </p>
            </StartStep>
            <StartStep
              number={4}
              title={
                <Translate id="page.ai.start.step4" values={{ code: <code>mvn test</code> }}>
                  {'Run {code} and read the result'}
                </Translate>
              }
            />
          </ol>
        </div>

        <nav
          className={styles.docs}
          aria-label={translate({
            id: 'page.ai.start.docsLabel',
            message: 'AI tooling documentation',
          })}>
          <ul className={styles.docsList}>
            {getDocs().map(([title, text, to]) => (
              <li key={to}>
                <Link className={styles.docLink} to={to}>
                  <span className={styles.docTitle}>
                    {title}
                    <ArrowIcon className={styles.icon} />
                  </span>
                  <span className={styles.docText}>{text}</span>
                </Link>
              </li>
            ))}
          </ul>
          <Link className={styles.homeLink} to="/">
            <Translate id="page.ai.start.home">Back to the docs home</Translate>
            <ArrowIcon className={styles.icon} />
          </Link>
        </nav>
      </div>
    </section>
  );
}

export default function AiTooling() {
  return (
    <Layout
      title={translate({
        id: 'page.ai.meta.title',
        message: 'AI tooling for webforJ: an AI coding loop for Java web apps',
      })}
      description={translate({
        id: 'page.ai.meta.description',
        message:
          'The webforJ AI plugin with its MCP server and Agent Skills, the TestBed and the craftforJ Assistant give AI coding tools a loop of write, check and review for Java web apps.',
      })}>
      <main className={styles.page}>
        <Hero />
        <Breaks />
        <Loop />
        <PiecesIndex />
        <PluginPiece />
        <McpPiece />
        <SkillsPiece />
        <TestbedPiece />
        <CraftforjPiece />
        <Start />
      </main>
    </Layout>
  );
}
