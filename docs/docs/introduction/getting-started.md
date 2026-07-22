---
title: Getting Started
description: Start building web apps in pure Java — pick your path, explore the components, and see what people are shipping with webforJ.
sidebar_position: 1
hide_table_of_contents: true
---

import Link from '@docusaurus/Link';
import Hero from '@site/src/components/GettingStarted/Hero';
import Illo from '@site/src/components/GettingStarted/Illo';
import BentoCard from '@site/src/components/GettingStarted/BentoCard';
import TaskTile from '@site/src/components/GettingStarted/TaskTile';
import CommunityBand from '@site/src/components/GettingStarted/CommunityBand';
import styles from '@site/src/components/GettingStarted/GettingStarted.module.css';

<div className={styles.page}>

<Hero
  eyebrow="Getting started"
  title="Build web apps in pure Java"
  subtitle="Ship production-ready UIs with components, routing, and data binding — no HTML templates, no client-side glue, no build tools running in your browser."
  graphic={<Illo variant="hero" />}
  ctas={
    <>
      <Link className={styles.heroCtaPrimary} to="/docs/introduction/quickstart">
        Start the quickstart →
      </Link>
      <a className={styles.heroCtaSecondary} href="https://docs.webforj.com/startforj/" target="_blank" rel="noopener noreferrer">
        Try startforJ
      </a>
    </>
  }
/>

<div className={styles.bentoRow}>

<BentoCard
  variant="wizard"
  title="Prefer a wizard to a CLI?"
  description="Pick options in the browser — app name, archetype, theme color, Spring flavor — and download a ready-to-run project."
  cta="Open startforJ →"
  to="https://docs.webforj.com/startforj/"
  external
/>

<BentoCard
  variant="projects"
  title="Built with webforJ"
  description="Real apps from the community — dashboards, AI chat, dev tools, games. Browse the source and get inspired."
  cta="Explore the gallery →"
  to="/built-with-webforj"
/>

</div>

<div className={styles.gridHeader}>
  <h2 className={styles.gridTitle}>What do you want to build next?</h2>
  <p className={styles.gridSubtitle}>Dive into a topic when you're ready.</p>
</div>

<div className={styles.grid}>
  <TaskTile
    variant="pages"
    title="Add pages & navigation"
    description="Multi-page apps with routes, layouts, and deep linking."
    to="/docs/routing/overview"
  />
  <TaskTile
    variant="forms"
    title="Build forms & bind data"
    description="Two-way binding, validation, and Java-model-driven forms."
    to="/docs/data-binding/overview"
  />
  <TaskTile
    variant="theme"
    title="Style & theme your app"
    description="Customize with DWC tokens — no CSS gymnastics."
    to="/docs/styling/overview"
  />
  <TaskTile
    variant="ai"
    title="Add AI features"
    description="Spring AI, streaming chat, MarkdownViewer, MCP integration."
    to="/docs/integrations/ai-tooling"
  />
  <TaskTile
    variant="spring"
    title="Connect to Spring Boot"
    description="Spring Data JPA, Security, and your existing stack."
    to="/docs/integrations/spring/spring-boot"
  />
  <TaskTile
    variant="ship"
    title="Ship it"
    description="Package as JAR or WAR, deploy anywhere Java runs."
    to="/docs/configuration/overview"
  />
</div>

<CommunityBand />

</div>
