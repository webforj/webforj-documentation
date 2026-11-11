import React from 'react';
import Layout from '@theme/Layout';
import Link from '@docusaurus/Link';
import CodeBlock from '@theme/CodeBlock';
import Translate, { translate } from '@docusaurus/Translate';
import { ArrowIcon } from '@site/src/components/pageKit/icons';
import PreviewBadge from '@site/src/components/pageKit/PreviewBadge';
import {
  CONFIG_BLOCK,
  EXTEND_FUNCTION,
  HELLO_JAVA,
  HELLO_KOTLIN,
  NAMES,
  getConcepts,
  getSteps,
} from './_content';
import styles from './kotlin-dsl.module.css';

const SETUP_DOC = '/docs/integrations/kotlin-dsl/overview#setup';
const USING_DOC = '/docs/integrations/kotlin-dsl/using-the-dsl';

// The two calls to action: setting up the DSL and the guide to using it.
function Actions({ setupLabel, usingLabel }) {
  return (
    <div className={styles.actions}>
      <Link className={`${styles.button} ${styles.buttonPrimary}`} to={SETUP_DOC}>
        {setupLabel}
        <ArrowIcon className={styles.icon} />
      </Link>
      <Link className={`${styles.button} ${styles.buttonGhost}`} to={USING_DOC}>
        {usingLabel}
      </Link>
    </div>
  );
}

// The eyebrow and two-line title of a section, with its lead beside them.
function SectionHead({ titleId, eyebrow, title, accent, lead }) {
  return (
    <div className={styles.head}>
      <div>
        <p className={styles.eyebrow}>{eyebrow}</p>
        <h2 className={styles.sectionTitle} id={titleId}>
          {title} <span>{accent}</span>
        </h2>
      </div>
      <p className={styles.sectionLead}>{lead}</p>
    </div>
  );
}

function Hero() {
  return (
    <header className={styles.hero}>
      <div className={styles.container}>
        <div className={styles.heroGrid}>
          <div>
            <div className={styles.eyebrowRow}>
              <p className={styles.eyebrow}>
                <Translate id="page.kotlinDsl.hero.eyebrow">Kotlin DSL for webforJ</Translate>
              </p>
              <PreviewBadge className={styles.previewBadge} />
            </div>
            <h1 className={styles.heroTitle}>
              <Translate id="page.kotlinDsl.hero.title1">Write it in Kotlin.</Translate>{' '}
              <span>
                <Translate id="page.kotlinDsl.hero.title2">Keep your Java.</Translate>
              </span>
            </h1>
            <p className={styles.heroLead}>
              <Translate id="page.kotlinDsl.hero.lead">
                The Kotlin DSL builds webforJ UIs with concise, type-safe syntax. Instead of
                imperative calls, you write declarative code that reads like a description of the
                screen, and it compiles in the same project as your Java.
              </Translate>
            </p>
            <Actions
              setupLabel={<Translate id="page.kotlinDsl.hero.add">Add it to a project</Translate>}
              usingLabel={<Translate id="page.kotlinDsl.hero.using">Using the DSL</Translate>}
            />
          </div>
          <div className={styles.names}>
            <p className={styles.namesTitle}>
              <Translate id="page.kotlinDsl.names.title">
                The class you know, as a builder
              </Translate>
            </p>
            <ul className={styles.namesList}>
              {NAMES.map((name) => (
                <li key={name.java}>
                  <code className={styles.nameJava}>{name.java}</code>
                  <span className={styles.nameArrow} aria-hidden="true">
                    <ArrowIcon className={styles.icon} />
                  </span>
                  <span className={styles.visuallyHidden}>
                    <Translate id="page.kotlinDsl.names.becomes">becomes</Translate>
                  </span>
                  <code className={styles.nameKotlin}>{name.kotlin}</code>
                </li>
              ))}
            </ul>
            <p className={styles.namesNote}>
              <Translate id="page.kotlinDsl.names.note">
                Buttons, fields, layouts, dialogs, drawers, lists and HTML elements each have one,
                named after the class in camelCase.
              </Translate>
            </p>
          </div>
        </div>
      </div>
    </header>
  );
}

function Compare() {
  return (
    <section className={styles.section} aria-labelledby="kt-compare">
      <div className={styles.container}>
        <SectionHead
          titleId="kt-compare"
          eyebrow={
            <Translate id="page.kotlinDsl.compare.eyebrow">Same screen, two languages</Translate>
          }
          title={<Translate id="page.kotlinDsl.compare.title1">Read the Java.</Translate>}
          accent={<Translate id="page.kotlinDsl.compare.title2">Then read the Kotlin.</Translate>}
          lead={
            <Translate id="page.kotlinDsl.compare.lead">
              Both sides build the same components with the same webforJ classes. The Kotlin side
              drops the setters and add() calls: components nest inside each other and configuration
              happens in blocks.
            </Translate>
          }
        />
        <div className={styles.compareBox}>
          <div className={styles.pair}>
            <div className={styles.pane}>
              <p className={styles.paneHead}>
                <span className={styles.langJava}>Java</span>
                <span className={styles.paneFile}>HelloView.java</span>
              </p>
              <CodeBlock language="java">{HELLO_JAVA}</CodeBlock>
            </div>
            <div className={`${styles.pane} ${styles.paneKotlin}`}>
              <p className={styles.paneHead}>
                <span className={styles.langKotlin}>Kotlin</span>
                <span className={styles.paneFile}>HelloView.kt</span>
              </p>
              <CodeBlock language="kotlin">{HELLO_KOTLIN}</CodeBlock>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

function Concepts() {
  return (
    <section className={styles.section} aria-labelledby="kt-concepts">
      <div className={styles.container}>
        <SectionHead
          titleId="kt-concepts"
          eyebrow={
            <Translate id="page.kotlinDsl.features.eyebrow">What the DSL gives you</Translate>
          }
          title={<Translate id="page.kotlinDsl.features.title1">Builders, blocks</Translate>}
          accent={<Translate id="page.kotlinDsl.features.title2">and lambdas</Translate>}
          lead={
            <Translate id="page.kotlinDsl.concepts.lead">
              Each builder function creates a component, adds it to its parent container and runs a
              configuration block. The rest of the DSL follows from that.
            </Translate>
          }
        />
        <div className={styles.blockRow}>
          <div className={styles.blockText}>
            <h3 className={styles.conceptTitle}>
              <Translate id="page.kotlinDsl.concepts.blocks.title">Configuration blocks</Translate>
            </h3>
            <p className={styles.conceptText}>
              <Translate
                id="page.kotlinDsl.concepts.blocks.text"
                values={{ code: <code>this</code> }}>
                {
                  'The block receives the component as {code}, so you set its properties and call its methods directly.'
                }
              </Translate>
            </p>
          </div>
          <div className={styles.blockCode}>
            <CodeBlock language="kotlin">{CONFIG_BLOCK}</CodeBlock>
          </div>
        </div>
        <ul className={styles.concepts}>
          {getConcepts().map((concept) => (
            <li key={concept.id}>
              <h3 className={styles.conceptTitle}>{concept.title}</h3>
              <p className={styles.conceptText}>{concept.text}</p>
            </li>
          ))}
        </ul>
      </div>
    </section>
  );
}

function Extend() {
  return (
    <section className={styles.section} aria-labelledby="kt-extend">
      <div className={styles.container}>
        <div className={styles.extendGrid}>
          <div>
            <p className={styles.eyebrow}>
              <Translate id="page.kotlinDsl.extend.eyebrow">Extending the DSL</Translate>
            </p>
            <h2 className={styles.sectionTitle} id="kt-extend">
              <Translate id="page.kotlinDsl.extend.title1">Your own components,</Translate>{' '}
              <span>
                <Translate id="page.kotlinDsl.extend.title2">as builders too</Translate>
              </span>
            </h2>
          </div>
          <div className={styles.extendBody}>
            <p className={styles.sectionLead}>
              <Translate
                id="page.kotlinDsl.extend.short"
                values={{ hasComponents: <code>HasComponents</code>, init: <code>init</code> }}>
                {
                  'Custom components and third-party libraries join the DSL through an extension function on {hasComponents}. The {init} helper adds the component to its parent, runs the block and returns it. Composites you build with the DSL get a function the same way.'
                }
              </Translate>
            </p>
            <CodeBlock language="kotlin" title="StarRating.kt">
              {EXTEND_FUNCTION}
            </CodeBlock>
            <Link className={styles.textLink} to="/docs/integrations/kotlin-dsl/extending-the-dsl">
              <Translate id="page.kotlinDsl.extend.link">Extending the DSL in the docs</Translate>
              <ArrowIcon className={styles.icon} />
            </Link>
          </div>
        </div>
      </div>
    </section>
  );
}

function Start() {
  return (
    <section className={styles.section} aria-labelledby="kt-start">
      <div className={styles.container}>
        <SectionHead
          titleId="kt-start"
          eyebrow={<Translate id="page.kotlinDsl.start.eyebrow">How to start</Translate>}
          title={<Translate id="page.kotlinDsl.start.title1">Add Kotlin</Translate>}
          accent={<Translate id="page.kotlinDsl.start.title2">to a Maven project</Translate>}
          lead={
            <Translate id="page.kotlinDsl.start.shortLead">
              The Kotlin DSL is available from webforJ 25.12. A project that already builds with
              Maven needs a dependency and a plugin.
            </Translate>
          }
        />
        <ol className={styles.steps}>
          {getSteps().map((step, i) => (
            <li key={step.id}>
              <span className={styles.stepNumber}>{i + 1}</span>
              <h3 className={styles.conceptTitle}>{step.title}</h3>
              <p className={styles.conceptText}>{step.text}</p>
            </li>
          ))}
        </ol>
        <Actions
          setupLabel={<Translate id="page.kotlinDsl.start.setup">Set up the Kotlin DSL</Translate>}
          usingLabel={<Translate id="page.kotlinDsl.start.using">Using the DSL</Translate>}
        />
      </div>
    </section>
  );
}

export default function KotlinDsl() {
  return (
    <Layout
      title={translate({
        id: 'page.kotlinDsl.meta.title',
        message: 'Kotlin DSL for webforJ: write your webforJ screens in Kotlin',
      })}
      description={translate({
        id: 'page.kotlinDsl.meta.description',
        message:
          'The webforJ Kotlin DSL builds the same webforJ screens with type-safe Kotlin builders, nested blocks and lambdas, in the same Maven project as your Java code.',
      })}>
      <main className={styles.page}>
        <Hero />
        <Compare />
        <Concepts />
        <Extend />
        <Start />
      </main>
    </Layout>
  );
}
