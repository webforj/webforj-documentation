import React, { useEffect, useRef, useState } from 'react';
import clsx from 'clsx';
import Layout from '@theme/Layout';
import Link from '@docusaurus/Link';
import useBaseUrl from '@docusaurus/useBaseUrl';
import CodeBlock from '@theme/CodeBlock';
import Translate, { translate } from '@docusaurus/Translate';
import CodeSwatches from '@site/src/components/codeSwatch';
import { ArrowIcon } from '@site/src/components/pageKit/icons';
import { getGroups, indexByName } from './_components';
import styles from './ui-components.module.css';

const GALLERY_URL = '/docs/components/overview';
const DOCS_URL = '/docs/components/';

// The hero wall. Large tiles span two columns and two rows.
const WALL = [
  ['AppLayout', true],
  ['Button'],
  ['ChoiceBox'],
  ['Badge'],
  ['Avatar'],
  ['Login', true],
  ['Slider'],
  ['ProgressBar'],
  ['Switch'],
  ['MaskedDateField'],
  ['GoogleCharts', true],
  ['Navigator'],
  ['Tree'],
  ['AppNav'],
  ['CheckBox'],
  ['Upload', true],
  ['Toast'],
  ['Icon'],
  ['Accordion'],
  ['Alert'],
];

// Copied from docs/building-ui/using-components.md
const ADD_CODE = `FlexLayout container = new FlexLayout();

container.add(new Button("Click Me"));

TextField nameField = new TextField("Name");
TextField emailField = new TextField("Email");
Button submitButton = new Button("Submit");

container.add(nameField, emailField, submitButton);`;

const STATE_CODE = `// Reveal an additional field when a checkbox is checked
TextField advancedField = new TextField("Advanced setting");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Show advanced settings");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Enable a button only when the required field has a value
Button submitButton = new Button("Submit");
submitButton.setEnabled(false);

TextField nameField = new TextField("Name");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));`;

// Copied from docs/building-ui/component-fundamentals.md
const COMPOSE_CODE = `public class SearchBar extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private TextField searchField;
  private Button searchButton;

  public SearchBar() {
    searchField = new TextField("Search");
    searchButton = new Button("Go");

    self.setDirection(FlexDirection.ROW)
      .add(searchField, searchButton);
  }
}`;

// Copied from docs/styling/themes.md
const THEME_CODE = `@AppTheme("system")
@AppLightTheme("light")
@AppDarkTheme("dark")
class MyApp extends App {
  // app code
}`;

const TOKEN_CODE = `:root {
  --dwc-color-primary-seed: #6366f1;
  --dwc-font-size: var(--dwc-font-size-l);
}`;

// The gallery holds a few components this page leaves out, so its total
// comes from its own sections, not from the groups on this page.
const GALLERY_TOTAL = 11 + 19 + 6 + 21;

// The typed HTML element classes listed in docs/components/html-elements.md
// under "Available components": 23 single classes plus H1 to H6.
const HTML_TOTAL = 29;

// The gallery's own sections and how many cards each one holds.
function getGallerySections() {
  return [
    [translate({ id: 'page.uiComponents.gallery.layouts', message: 'Layouts' }), '#layouts', 11],
    [
      translate({ id: 'page.uiComponents.gallery.dataEntry', message: 'Data entry' }),
      '#data-entry',
      19,
    ],
    [
      translate({ id: 'page.uiComponents.gallery.optionDialogs', message: 'Option dialogs' }),
      '#option-dialogs',
      6,
    ],
    [
      translate({
        id: 'page.uiComponents.gallery.interaction',
        message: 'Interaction and display',
      }),
      '#interaction-and-display',
      21,
    ],
  ];
}

// What sits outside the count: typed HTML elements, and any component the
// web has to offer. Facts from docs/components/html-elements.md,
// docs/building-ui/element-composite.md and
// docs/managing-resources/bundler/overview.md.
function getBeyond() {
  return [
    {
      label: translate(
        { id: 'page.uiComponents.beyond.html.label', message: '{count} HTML elements' },
        { count: HTML_TOTAL },
      ),
      title: translate({
        id: 'page.uiComponents.beyond.html.title',
        message: 'The page itself, typed',
      }),
      text: translate({
        id: 'page.uiComponents.beyond.html.text',
        message:
          'webforJ ships components that map directly to standard HTML elements, such as Div, Paragraph, the headings, lists, Img and Anchor, with a typed Java API.',
      }),
      link: translate({ id: 'page.uiComponents.beyond.html.link', message: 'HTML components' }),
      to: '/docs/components/html-elements',
    },
    {
      label: translate({ id: 'page.uiComponents.beyond.web.label', message: 'Web components' }),
      title: translate({
        id: 'page.uiComponents.beyond.web.title',
        message: 'Any web component, wrapped',
      }),
      text: translate({
        id: 'page.uiComponents.beyond.web.text',
        message:
          'ElementComposite wraps a custom HTML element or a third-party web component and exposes its properties, attributes and events through the Java API.',
      }),
      link: translate({ id: 'page.uiComponents.beyond.web.link', message: 'Element Composite' }),
      to: '/docs/building-ui/element-composite',
    },
    {
      label: translate({ id: 'page.uiComponents.beyond.npm.label', message: 'npm packages' }),
      title: translate({
        id: 'page.uiComponents.beyond.npm.title',
        message: 'The npm registry, from Java',
      }),
      text: translate({
        id: 'page.uiComponents.beyond.npm.text',
        message:
          'The frontend bundler turns annotations on a class into compiled frontend assets, so a component can bring the npm package it depends on.',
      }),
      link: translate({ id: 'page.uiComponents.beyond.npm.link', message: 'Frontend bundler' }),
      to: '/docs/managing-resources/bundler/overview',
    },
  ];
}

// Both theme sets are in the page; CSS shows the one matching the site
// theme, so the switch never waits on React.
function Preview({ item, eager = false, alt = '' }) {
  const lightSrc = useBaseUrl(`/img/components/light/${item.name}.webp`);
  const darkSrc = useBaseUrl(`/img/components/dark/${item.name}.webp`);
  const imageProps = {
    width: item.width,
    height: item.height,
    alt,
    loading: eager ? 'eager' : 'lazy',
    decoding: 'async',
  };
  return (
    <>
      <img {...imageProps} src={lightSrc} className={clsx(styles.shot, styles.onLight)} />
      <img {...imageProps} src={darkSrc} className={clsx(styles.shot, styles.onDark)} />
    </>
  );
}

// A number that counts up to its value once, the first time it scrolls
// into view. The server renders the final value, the digits keep a fixed
// width so nothing shifts while it runs, and with reduced motion it simply
// shows the value.
function CountUp({ value }) {
  const ref = useRef(null);
  const [shown, setShown] = useState(value);
  useEffect(() => {
    const node = ref.current;
    if (!node || typeof IntersectionObserver === 'undefined') return undefined;
    if (window.matchMedia('(prefers-reduced-motion: reduce)').matches) return undefined;
    let frame = 0;
    setShown(0);
    const observer = new IntersectionObserver(
      ([entry]) => {
        if (!entry.isIntersecting) return;
        observer.disconnect();
        const start = performance.now();
        const duration = 1100;
        const tick = (now) => {
          const progress = Math.min(1, (now - start) / duration);
          // ease out: fast at first, settling onto the value
          setShown(Math.round(value * (1 - Math.pow(1 - progress, 3))));
          if (progress < 1) frame = requestAnimationFrame(tick);
        };
        frame = requestAnimationFrame(tick);
      },
      { threshold: 0.6 },
    );
    observer.observe(node);
    return () => {
      observer.disconnect();
      cancelAnimationFrame(frame);
    };
  }, [value]);
  return (
    <span ref={ref} className={styles.countNumber} style={{ '--digits': String(value).length }}>
      {shown}
    </span>
  );
}

// The drawn components in the theme panels and the SearchBar drawing.
function MockField({ children }) {
  return <span className={styles.mockField}>{children}</span>;
}

function MockButton({ primary = false, children }) {
  return (
    <span
      className={clsx(
        styles.mockButton,
        primary ? styles.mockButtonPrimary : styles.mockButtonDefault,
      )}>
      {children}
    </span>
  );
}

// A drawn panel of components, in the fixed colours of one theme.
// Decoration only, so it is hidden from assistive technology.
function ThemeMock({ mode }) {
  return (
    <div
      className={clsx(
        styles.mockPanel,
        styles.themeMock,
        mode === 'dark' ? styles.mockDark : styles.mockLight,
      )}>
      <p className={styles.visuallyHidden}>
        {mode === 'dark' ? (
          <Translate id="page.uiComponents.theme.darkSummary">
            The same field, buttons, switch, badge and progress bar in the dark theme.
          </Translate>
        ) : (
          <Translate id="page.uiComponents.theme.lightSummary">
            The same field, buttons, switch, badge and progress bar in the light theme.
          </Translate>
        )}
      </p>
      <div className={styles.mock} aria-hidden="true">
        <div className={styles.mockFieldGroup}>
          <span className={styles.mockLabel}>
            <Translate id="page.uiComponents.theme.mock.name">Name</Translate>
          </span>
          <MockField>Lauren Alamo</MockField>
        </div>
        <div className={styles.mockRow}>
          <MockButton primary>
            <Translate id="page.uiComponents.theme.mock.save">Save</Translate>
          </MockButton>
          <MockButton>
            <Translate id="page.uiComponents.theme.mock.cancel">Cancel</Translate>
          </MockButton>
        </div>
        <div className={styles.mockRow}>
          <span className={styles.mockSwitch} />
          <span className={styles.mockBadge}>
            <Translate id="page.uiComponents.theme.mock.new">New</Translate>
          </span>
        </div>
        <div className={styles.mockProgress}>
          <span />
        </div>
      </div>
    </div>
  );
}

function Hero({ groups, byName }) {
  return (
    <header className={styles.hero} aria-labelledby="uic-title">
      <div className={styles.container}>
        <div className={styles.heroTop}>
          <div className={styles.heroText}>
            <p className={styles.eyebrow}>
              <Translate id="page.uiComponents.hero.eyebrow">UI components</Translate>
            </p>
            <h1 className={styles.heroTitle} id="uic-title">
              <Translate id="page.uiComponents.hero.title1">
                Every part of a business screen,
              </Translate>{' '}
              <span>
                <Translate id="page.uiComponents.hero.title2">ready in Java.</Translate>
              </span>
            </h1>
            <p className={styles.heroLead}>
              <Translate id="page.uiComponents.hero.lead">
                {
                  'webforJ is the full-stack Java framework for business web apps: you build the whole app, screens included, in Java. Its components cover layout, data entry, dialogs, data display and feedback, and each one is a Java class you create, configure and add to a layout.'
                }
              </Translate>
            </p>
            <div className={styles.actions}>
              <Link className={clsx(styles.button, styles.buttonPrimary)} to={GALLERY_URL}>
                <Translate id="page.uiComponents.hero.openGallery">
                  Open the component gallery
                </Translate>
                <ArrowIcon className={styles.icon} />
              </Link>
              <Link className={clsx(styles.button, styles.buttonSecondary)} to="#groups">
                <Translate id="page.uiComponents.hero.browse">Browse by purpose</Translate>
              </Link>
            </div>
          </div>
          <dl className={styles.stats}>
            <div>
              <dt>
                <Translate id="page.uiComponents.stats.gallery">
                  Components in the gallery
                </Translate>
              </dt>
              <dd>{GALLERY_TOTAL}</dd>
            </div>
            <div>
              <dt>
                <Translate id="page.uiComponents.stats.html">Typed HTML elements</Translate>
              </dt>
              <dd>{HTML_TOTAL}</dd>
            </div>
            <div>
              <dt>
                <Translate id="page.uiComponents.stats.themes">Built-in themes</Translate>
              </dt>
              <dd>3</dd>
            </div>
            <div>
              <dt>
                <Translate id="page.uiComponents.stats.groups">Groups by purpose</Translate>
              </dt>
              <dd>{groups.length}</dd>
            </div>
          </dl>
        </div>

        <ul
          className={styles.wall}
          aria-label={translate({
            id: 'page.uiComponents.hero.wallLabel',
            message: 'A selection of webforJ components',
          })}>
          {WALL.map(([name, big]) => {
            const item = byName[name];
            return (
              <li
                key={name}
                className={clsx(
                  styles.wallItem,
                  big && styles.wallBig,
                  big && styles[`wall${name}`],
                )}>
                <Link
                  className={styles.wallLink}
                  to={`${DOCS_URL}${item.path}`}
                  aria-label={translate(
                    { id: 'page.uiComponents.hero.wallLink', message: '{name} documentation' },
                    { name: item.name },
                  )}>
                  <Preview
                    item={item}
                    eager
                    alt={translate(
                      { id: 'page.uiComponents.hero.wallAlt', message: '{name} component' },
                      { name: item.name },
                    )}
                  />
                </Link>
              </li>
            );
          })}
        </ul>
      </div>
    </header>
  );
}

function Groups({ groups }) {
  const total = groups.reduce((sum, group) => sum + group.items.length, 0);
  return (
    <section className={styles.groups} id="groups" aria-labelledby="uic-groups">
      <div className={styles.container}>
        <div className={styles.sectionHead}>
          <p className={styles.label}>
            <Translate id="page.uiComponents.groups.label">By purpose</Translate>
          </p>
          {/* The heading is read as one sentence; what is seen is the sum
              itself: two counted numbers and "any", each over its label. */}
          <h2
            className={styles.countTitle}
            id="uic-groups"
            aria-label={translate(
              {
                id: 'page.uiComponents.groups.title',
                message:
                  '{total} business components, plus {html} HTML elements and any web component.',
              },
              { total, html: HTML_TOTAL },
            )}>
            <span className={styles.count} aria-hidden="true">
              <CountUp value={total} />
              <span className={styles.countLabel}>
                <Translate id="page.uiComponents.groups.count.business">
                  business components
                </Translate>
              </span>
            </span>
            <span className={styles.countPlus} aria-hidden="true">
              +
            </span>
            <span className={styles.count} aria-hidden="true">
              <CountUp value={HTML_TOTAL} />
              <span className={styles.countLabel}>
                <Translate id="page.uiComponents.groups.count.html">HTML elements</Translate>
              </span>
            </span>
            <span className={styles.countPlus} aria-hidden="true">
              +
            </span>
            <span className={clsx(styles.count, styles.countAny)} aria-hidden="true">
              <span className={styles.countNumber}>
                <Translate id="page.uiComponents.groups.count.any">any</Translate>
              </span>
              <span className={styles.countLabel}>
                <Translate id="page.uiComponents.groups.count.web">web component</Translate>
              </span>
            </span>
          </h2>
          <p className={styles.sectionLead}>
            <Translate id="page.uiComponents.groups.lead">
              {
                "Pick a group, then a component. Every tile opens that component's own page in the docs."
              }
            </Translate>
          </p>
        </div>

        <nav
          className={styles.groupNav}
          aria-label={translate({
            id: 'page.uiComponents.groups.navLabel',
            message: 'Component groups',
          })}>
          {groups.map((group) => (
            <a key={group.id} className={styles.groupNavLink} href={`#${group.id}`}>
              <span>{group.title}</span>
              <span className={styles.groupNavCount}>{group.items.length}</span>
            </a>
          ))}
        </nav>

        {groups.map((group, index) => (
          <div key={group.id} className={styles.group} id={group.id}>
            <div className={styles.groupHead}>
              <span className={styles.groupIndex}>{String(index + 1).padStart(2, '0')}</span>
              <h3 className={styles.groupTitle}>{group.title}</h3>
              <p className={styles.groupLead}>{group.lead}</p>
              <p className={styles.groupCount}>
                <Translate
                  id="page.uiComponents.groups.count"
                  values={{ count: group.items.length }}>
                  {'{count} components'}
                </Translate>
              </p>
            </div>
            <ul className={styles.tiles}>
              {group.items.map((item) => (
                <li key={item.name}>
                  <Link className={styles.tile} to={`${DOCS_URL}${item.path}`}>
                    <span className={styles.shotFrame}>
                      <Preview item={item} />
                    </span>
                    <span className={styles.tileBody}>
                      <span className={styles.tileName}>{item.name}</span>
                      <span className={styles.tileText}>{item.text}</span>
                    </span>
                  </Link>
                </li>
              ))}
            </ul>
          </div>
        ))}
      </div>
    </section>
  );
}

function Composition() {
  return (
    <section className={styles.compose} aria-labelledby="uic-compose">
      <div className={styles.container}>
        <div className={styles.sectionHead}>
          <p className={styles.label}>
            <Translate id="page.uiComponents.compose.label">Composition</Translate>
          </p>
          <h2 className={styles.sectionTitle} id="uic-compose">
            <Translate id="page.uiComponents.compose.title1">Your own components,</Translate>{' '}
            <span>
              <Translate id="page.uiComponents.compose.title2">built from ours.</Translate>
            </span>
          </h2>
          <p className={styles.sectionLead}>
            <Translate
              id="page.uiComponents.compose.lead"
              values={{ button: <code>Button</code>, composite: <code>Composite</code> }}>
              {
                'webforJ uses composition over inheritance. You do not extend a {button}. You write a class that extends {composite}, combine components inside it, and decide which methods the rest of your app can see.'
              }
            </Translate>
          </p>
        </div>
        <div className={styles.composeRow}>
          <div className={styles.composeLeft}>
            <figure className={styles.codeWindow}>
              <figcaption className={styles.codeTitle}>SearchBar.java</figcaption>
              <CodeBlock language="java" className={styles.code}>
                {COMPOSE_CODE}
              </CodeBlock>
            </figure>
            <div className={clsx(styles.mockPanel, styles.composeDemo)} aria-hidden="true">
              <div className={styles.composeBox}>
                <span className={styles.composeBoxName}>SearchBar</span>
                <div className={styles.composeInner}>
                  <MockField>
                    <Translate id="page.uiComponents.compose.demo.search">Search</Translate>
                  </MockField>
                  <MockButton primary>
                    <Translate id="page.uiComponents.compose.demo.go">Go</Translate>
                  </MockButton>
                </div>
              </div>
            </div>
          </div>
          <ul className={clsx(styles.ruledList, styles.composePoints)}>
            <li>
              <h3 className={styles.ruledTitle}>
                <Translate id="page.uiComponents.compose.point1.title">
                  One class, one unit.
                </Translate>
              </h3>
              <p className={styles.ruledText}>
                <Translate
                  id="page.uiComponents.compose.point1.text"
                  values={{ composite: <code>Composite</code> }}>
                  {
                    'A {composite} wraps a single bound component and holds your internal structure, so a search bar, an address form or a whole dashboard card becomes one reusable class.'
                  }
                </Translate>
              </p>
            </li>
            <li>
              <h3 className={styles.ruledTitle}>
                <Translate id="page.uiComponents.compose.point2.title">
                  You choose the API.
                </Translate>
              </h3>
              <p className={styles.ruledText}>
                <Translate id="page.uiComponents.compose.point2.text">
                  Callers see only the methods you expose, unlike inheritance, where everything is
                  exposed.
                </Translate>
              </p>
            </li>
            <li>
              <h3 className={styles.ruledTitle}>
                <Translate id="page.uiComponents.compose.point3.title">
                  Built-in components stay intact.
                </Translate>
              </h3>
              <p className={styles.ruledText}>
                <Translate id="page.uiComponents.compose.point3.text">
                  The built-in components are final, which keeps the web component underneath
                  consistent and predictable. Your behaviour goes around them, not into them.
                </Translate>
              </p>
            </li>
          </ul>
        </div>
        <div className={clsx(styles.linkRow, styles.inlineLinks)}>
          <Link className={styles.textLink} to="/docs/building-ui/composing-components">
            <Translate id="page.uiComponents.compose.link.composing">
              Composing components
            </Translate>
            <ArrowIcon className={styles.icon} />
          </Link>
          <Link className={styles.textLink} to="/docs/building-ui/component-fundamentals">
            <Translate id="page.uiComponents.compose.link.understanding">
              Understanding components
            </Translate>
            <ArrowIcon className={styles.icon} />
          </Link>
        </div>
      </div>
    </section>
  );
}

function Beyond() {
  const items = getBeyond();
  return (
    <section className={styles.beyond} aria-labelledby="uic-beyond">
      <div className={styles.container}>
        <div className={styles.sectionHead}>
          <p className={styles.label}>
            <Translate id="page.uiComponents.beyond.label">Beyond the set</Translate>
          </p>
          <h2 className={styles.sectionTitle} id="uic-beyond">
            <Translate id="page.uiComponents.beyond.title1">Not in the set?</Translate>{' '}
            <span>
              <Translate id="page.uiComponents.beyond.title2">
                Use any component on the web.
              </Translate>
            </span>
          </h2>
          <p className={styles.sectionLead}>
            <Translate id="page.uiComponents.beyond.lead">
              The business components are where you start, not where you stop. Every HTML element is
              a Java class, and any web component can become one.
            </Translate>
          </p>
        </div>
        <ul className={clsx(styles.ruledList, styles.beyondList)}>
          {items.map((item) => (
            <li key={item.to}>
              <span className={styles.beyondLabel}>{item.label}</span>
              <h3 className={styles.ruledTitle}>{item.title}</h3>
              <p className={styles.ruledText}>{item.text}</p>
              <Link className={styles.beyondLink} to={item.to}>
                {item.link}
                <ArrowIcon className={styles.icon} />
              </Link>
            </li>
          ))}
        </ul>
      </div>
    </section>
  );
}

function OwnSystem() {
  return (
    <section className={styles.own} aria-labelledby="uic-own">
      <div className={styles.container}>
        <div className={styles.sectionHead}>
          <p className={styles.label}>
            <Translate id="page.uiComponents.own.label">Your design system</Translate>
          </p>
          <h2 className={styles.sectionTitle} id="uic-own">
            <Translate id="page.uiComponents.own.title1">Have your own design system?</Translate>{' '}
            <span>
              <Translate id="page.uiComponents.own.title2">Bring it along.</Translate>
            </span>
          </h2>
          <p className={styles.sectionLead}>
            <Translate id="page.uiComponents.own.lead">
              The webforJ components are an offer, not a requirement. If your design system ships as
              web components, you wrap it once and build the whole app with it, in Java.
            </Translate>
          </p>
        </div>
        <ol className={clsx(styles.ruledList, styles.ownSteps)}>
          <li>
            <span className={styles.ownNumber}>01</span>
            <h3 className={styles.ruledTitle}>
              <Translate id="page.uiComponents.own.step1.title">Wrap the components</Translate>
            </h3>
            <p className={styles.ruledText}>
              <Translate
                id="page.uiComponents.own.step1.text"
                values={{ composite: <code>ElementComposite</code> }}>
                {
                  '{composite} turns each custom element into a Java class and exposes its properties, attributes and events through the Java API.'
                }
              </Translate>
            </p>
          </li>
          <li>
            <span className={styles.ownNumber}>02</span>
            <h3 className={styles.ruledTitle}>
              <Translate id="page.uiComponents.own.step2.title">Ship them as one module</Translate>
            </h3>
            <p className={styles.ruledText}>
              <Translate id="page.uiComponents.own.step2.text">
                The frontend bundler lets a class declare the npm packages it needs, so the wrappers
                carry their design system with them. An app only adds the dependency.
              </Translate>
            </p>
          </li>
          <li>
            <span className={styles.ownNumber}>03</span>
            <h3 className={styles.ruledTitle}>
              <Translate id="page.uiComponents.own.step3.title">Build the app as usual</Translate>
            </h3>
            <p className={styles.ruledText}>
              <Translate
                id="page.uiComponents.own.step3.text"
                values={{ valueAware: <code>ValueAware</code> }}>
                {
                  'Routing, security and data binding work the same. A wrapped component that implements {valueAware} binds to your model like any built-in field.'
                }
              </Translate>
            </p>
          </li>
        </ol>
        <div className={styles.ownPanel}>
          <p className={styles.ownPanelText}>
            <Translate id="page.uiComponents.own.panel">
              Your screens keep the look your company already designed. The framework underneath is
              still webforJ.
            </Translate>
          </p>
          <div className={styles.linkRow}>
            <Link className={styles.textLink} to="/docs/building-ui/element-composite">
              <Translate id="page.uiComponents.own.link.composite">Element Composite</Translate>
              <ArrowIcon className={styles.icon} />
            </Link>
            <Link className={styles.textLink} to="/docs/managing-resources/bundler/overview">
              <Translate id="page.uiComponents.own.link.bundler">Frontend bundler</Translate>
              <ArrowIcon className={styles.icon} />
            </Link>
          </div>
        </div>
      </div>
    </section>
  );
}

function JavaSection({ byName }) {
  const used = ['FlexLayout', 'TextField', 'CheckBox', 'Button'].map((name) => byName[name]);
  return (
    <section className={styles.java} aria-labelledby="uic-java">
      <div className={styles.container}>
        <div className={styles.javaGrid}>
          <div className={styles.javaText}>
            <p className={styles.label}>
              <Translate id="page.uiComponents.java.label">Used from Java</Translate>
            </p>
            <h2 className={styles.sectionTitle} id="uic-java">
              <Translate id="page.uiComponents.java.title1">A component is an object</Translate>{' '}
              <span>
                <Translate id="page.uiComponents.java.title2" values={{ code: <code>new</code> }}>
                  {'you create with {code}.'}
                </Translate>
              </span>
            </h2>
            <p className={styles.sectionLead}>
              <Translate id="page.uiComponents.java.lead1">
                You set its state with typed methods, listen to its events with lambdas and add it
                to a container. The screen is Java code, with no template to keep in sync.
              </Translate>
            </p>
            <ul
              className={styles.usedList}
              aria-label={translate({
                id: 'page.uiComponents.java.usedLabel',
                message: 'Components in this code',
              })}>
              {used.map((item) => (
                <li key={item.name}>
                  <Link className={styles.usedLink} to={`${DOCS_URL}${item.path}`}>
                    <span className={styles.shotFrame}>
                      <Preview item={item} />
                    </span>
                    <span className={styles.usedName}>{item.name}</span>
                  </Link>
                </li>
              ))}
            </ul>
            <div className={clsx(styles.linkRow, styles.inlineLinks)}>
              <Link className={styles.textLink} to="/docs/building-ui/using-components">
                <Translate id="page.uiComponents.java.usingLink">Using components</Translate>
                <ArrowIcon className={styles.icon} />
              </Link>
              <Link className={styles.textLink} to="/docs/building-ui/composing-components">
                <Translate id="page.uiComponents.java.composingLink">
                  Composing components
                </Translate>
                <ArrowIcon className={styles.icon} />
              </Link>
            </div>
          </div>
          <div className={styles.codeStack}>
            <figure className={styles.codeWindow}>
              <figcaption className={styles.codeTitle}>
                <Translate id="page.uiComponents.java.codeAdd">
                  Add components to a container
                </Translate>
              </figcaption>
              <CodeBlock language="java" className={styles.code}>
                {ADD_CODE}
              </CodeBlock>
            </figure>
            <figure className={styles.codeWindow}>
              <figcaption className={styles.codeTitle}>
                <Translate id="page.uiComponents.java.codeState">
                  Change state from events
                </Translate>
              </figcaption>
              <CodeBlock language="java" className={styles.code}>
                {STATE_CODE}
              </CodeBlock>
            </figure>
          </div>
        </div>
      </div>
    </section>
  );
}

function ThemeSection() {
  return (
    <section className={styles.theme} aria-labelledby="uic-theme">
      <div className={styles.container}>
        <div className={styles.sectionHead}>
          <p className={styles.label}>
            <Translate id="page.uiComponents.theme.label">Theming</Translate>
          </p>
          <h2 className={styles.sectionTitle} id="uic-theme">
            <Translate id="page.uiComponents.theme.title1">One design system</Translate>{' '}
            <span>
              <Translate id="page.uiComponents.theme.title2">behind every component.</Translate>
            </span>
          </h2>
          <p className={styles.sectionLead}>
            <Translate id="page.uiComponents.theme.lead">
              webforJ ships with a design system named DWC. Its CSS variables, the design tokens,
              cover colors, typography, borders and spacing, and every component reads them. Change
              a token and every component that uses it follows.
            </Translate>
          </p>
        </div>

        <div className={styles.themePair}>
          <figure className={styles.themeShot}>
            <ThemeMock mode="light" />
            <figcaption>
              <code>light</code>
              <span>
                <Translate id="page.uiComponents.theme.lightCaption">
                  The default. Light background, subtle primary-color tint.
                </Translate>
              </span>
            </figcaption>
          </figure>
          <figure className={styles.themeShot}>
            <ThemeMock mode="dark" />
            <figcaption>
              <code>dark</code>
              <span>
                <Translate id="page.uiComponents.theme.darkCaption">
                  Dark background, subtle primary-color tint.
                </Translate>
              </span>
            </figcaption>
          </figure>
        </div>

        <div className={styles.themeFacts}>
          <div className={styles.fact}>
            <h3 className={styles.factTitle}>
              <Translate id="page.uiComponents.theme.fact1.title">Three themes built in</Translate>
            </h3>
            <p className={styles.factText}>
              <Translate
                id="page.uiComponents.theme.fact1.text"
                values={{
                  light: <code>light</code>,
                  dark: <code>dark</code>,
                  darkPure: <code>dark-pure</code>,
                  setTheme: <code>App.setTheme()</code>,
                }}>
                {
                  '{light}, {dark} and {darkPure}, which drops the primary tint for pure neutral grays. Switch at runtime with {setTheme}, with no rebuild.'
                }
              </Translate>
            </p>
          </div>
          <div className={styles.fact}>
            <h3 className={styles.factTitle}>
              <Translate id="page.uiComponents.theme.fact2.title">
                Follows the operating system
              </Translate>
            </h3>
            <p className={styles.factText}>
              <Translate
                id="page.uiComponents.theme.fact2.text"
                values={{ system: <code>"system"</code> }}>
                {
                  "Register a light and a dark theme and pass {system}. webforJ picks the one that matches the user's preference and switches again when it changes."
                }
              </Translate>
            </p>
            <CodeBlock language="java" className={clsx(styles.code, styles.factCode)}>
              {THEME_CODE}
            </CodeBlock>
          </div>
          <div className={styles.fact}>
            <h3 className={styles.factTitle}>
              <Translate id="page.uiComponents.theme.fact3.title">
                Rebrand with a few tokens
              </Translate>
            </h3>
            <p className={styles.factText}>
              <Translate id="page.uiComponents.theme.fact3.text">
                Override the seed tokens of the built-in themes and every component picks up the new
                look. Shadow parts reach inside a component when a token is not enough.
              </Translate>
            </p>
            <CodeSwatches>
              <CodeBlock language="css" className={clsx(styles.code, styles.factCode)}>
                {TOKEN_CODE}
              </CodeBlock>
            </CodeSwatches>
          </div>
        </div>

        <div className={clsx(styles.linkRow, styles.inlineLinks)}>
          <Link className={styles.textLink} to="/docs/styling/themes">
            <Translate id="page.uiComponents.theme.link.themes">Themes</Translate>
            <ArrowIcon className={styles.icon} />
          </Link>
          <Link className={styles.textLink} to="/docs/styling/css-variables">
            <Translate id="page.uiComponents.theme.link.cssVariables">CSS variables</Translate>
            <ArrowIcon className={styles.icon} />
          </Link>
          <Link className={styles.textLink} to="/docs/styling/shadow-parts">
            <Translate id="page.uiComponents.theme.link.shadowParts">Shadow parts</Translate>
            <ArrowIcon className={styles.icon} />
          </Link>
          <Link className={styles.textLink} to="/docs/styling/overview">
            <Translate id="page.uiComponents.theme.link.overview">Styling overview</Translate>
            <ArrowIcon className={styles.icon} />
          </Link>
        </div>
      </div>
    </section>
  );
}

function Entrance() {
  const sections = getGallerySections();
  return (
    <section className={styles.entrance} aria-labelledby="uic-gallery">
      <div className={styles.container}>
        <div className={styles.entranceBox}>
          <div>
            <p className={styles.label}>
              <Translate id="page.uiComponents.entrance.label">The gallery</Translate>
            </p>
            <h2 className={styles.entranceTitle} id="uic-gallery">
              <Translate id="page.uiComponents.entrance.title1">Open the gallery</Translate>{' '}
              <span>
                <Translate id="page.uiComponents.entrance.title2" values={{ total: GALLERY_TOTAL }}>
                  {'and see all {total}.'}
                </Translate>
              </span>
            </h2>
            <p className={styles.sectionLead}>
              <Translate id="page.uiComponents.entrance.lead">
                The gallery in the docs lists every component with its preview and a short
                description, in the same four sections the docs use.
              </Translate>
            </p>
            <div className={styles.actions}>
              <Link
                className={clsx(styles.button, styles.buttonPrimary, styles.buttonLarge)}
                to={GALLERY_URL}>
                <Translate id="page.uiComponents.entrance.openGallery">
                  Open the component gallery
                </Translate>
                <ArrowIcon className={styles.icon} />
              </Link>
              <Link
                className={clsx(styles.button, styles.buttonSecondary, styles.buttonLarge)}
                to="/">
                <Translate id="page.uiComponents.entrance.home">Back to the docs home</Translate>
              </Link>
            </div>
          </div>
          <ul
            className={styles.sectionList}
            aria-label={translate({
              id: 'page.uiComponents.entrance.sectionsLabel',
              message: 'Gallery sections',
            })}>
            {sections.map(([name, hash, count]) => (
              <li key={hash}>
                <Link className={styles.sectionLink} to={`${GALLERY_URL}${hash}`}>
                  <span className={styles.sectionCount}>{count}</span>
                  <span className={styles.sectionName}>{name}</span>
                  <ArrowIcon className={styles.icon} />
                </Link>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </section>
  );
}

export default function UiComponents() {
  const groups = getGroups();
  const byName = indexByName(groups);
  return (
    <Layout
      title={translate({
        id: 'page.uiComponents.meta.title',
        message: 'webforJ UI components: the Java component set for business apps',
      })}
      description={translate({
        id: 'page.uiComponents.meta.description',
        message:
          'Browse the webforJ component set by what each component does: layout, forms and inputs, data, navigation and feedback. Every component is a Java class, styled by the DWC design system and its light, dark and dark-pure themes.',
      })}>
      <main className={styles.page}>
        <Hero groups={groups} byName={byName} />
        <Groups groups={groups} />
        <Composition />
        <Beyond />
        <OwnSystem />
        <JavaSection byName={byName} />
        <ThemeSection />
        <Entrance />
      </main>
    </Layout>
  );
}
