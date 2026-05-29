---
title: Home
description: webforJ is a Java framework for building modern, reactive web UIs without writing JavaScript.
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
---

import Head from '@docusaurus/Head';
import Link from '@docusaurus/Link';
import Translate from '@docusaurus/Translate';
import NavCard from '@site/src/components/HomePage/NavCard';
import ProjectCard from '@site/src/components/HomePage/ProjectCard';
import ComponentCard from '@site/src/components/HomePage/ComponentCard';
import { featuredProjects, announcements, componentShowcase } from '@site/src/pages/homePageData';
import styles from '@site/src/components/HomePage/HomePageLayout.module.css';
import RocketLaunchIcon from '@mui/icons-material/RocketLaunch';
import WidgetsIcon from '@mui/icons-material/Widgets';
import AccountTreeIcon from '@mui/icons-material/AccountTree';
import UpgradeIcon from '@mui/icons-material/Upgrade';
import AutoAwesomeIcon from '@mui/icons-material/AutoAwesome';
import DataObjectIcon from '@mui/icons-material/DataObject';
import PaletteIcon from '@mui/icons-material/Palette';
import LeafIcon from '@mui/icons-material/Spa';

<Head>
  <style>{`
    .container { max-width: 65em !important; }
  `}</style>
</Head>

<div className={styles.heroSection}>
  <div className={styles.heroContent}>
    <h1 className={styles.homeTitle}>Build web apps in pure Java</h1>
    <p className={styles.homeTagline}>
      Ship production-ready UIs with components, routing, and data binding with HTML, CSS, or JavaScript required.
    </p>
    <div className={styles.homeCtas}>
      <Link className="button button--primary button--lg" to="/docs/introduction/getting-started">
        <Translate id="homepage.hero.cta.start">Get Started</Translate>
      </Link>
      <a className="button button--secondary button--lg" href="https://docs.webforj.com/startforj/" target="_blank" rel="noopener noreferrer">StartforJ Quickstart</a>
    </div>
  </div>
</div>

---

<div className={styles.jbangSection}>
  <div className={styles.jbangContent}>
    <div className={styles.jbangText}>
      <p className={styles.jbangTitle}>Get started in one minute</p>
      <p className={styles.jbangSubtitle}>No Maven, no project setup, just Java and JBang.</p>
      <Link className="button button--primary button--sm" to="/docs/integrations/jbang">
        <Translate id="homepage.jbang.cta">Learn more</Translate>
      </Link>
    </div>
    
  </div>
</div>

---

## Find Your Path

<p className={styles.sectionTagline}>New to webforJ or looking for something specific? Pick a starting point.</p>

<div className={styles.navGrid}>
  <NavCard
    icon={<RocketLaunchIcon />}
    title={<Translate id="homepage.nav.start.title">New to webforJ?</Translate>}
    description={<Translate id="homepage.nav.start.desc">Set up your first project in minutes with the quickstart guide.</Translate>}
    to="/docs/introduction/getting-started"
  />
  <NavCard
    icon={<WidgetsIcon />}
    title={<Translate id="homepage.nav.components.title">Browse Components</Translate>}
    description={<Translate id="homepage.nav.components.desc">Explore the full library of UI components with live demos.</Translate>}
    to="/docs/components/overview"
  />
  <NavCard
    icon={<AutoAwesomeIcon />}
    title={<Translate id="homepage.nav.ai.title">AI Plugins</Translate>}
    description={<Translate id="homepage.nav.ai.desc">Connect Claude, Copilot, Cursor, and more to webforJ in one step.</Translate>}
    to="/docs/integrations/ai-tooling"
  />
  <NavCard
    icon={<AccountTreeIcon />}
    title={<Translate id="homepage.nav.routing.title">Routing & Layouts</Translate>}
    description={<Translate id="homepage.nav.routing.desc">Define routes, navigate between views, and build complex layouts.</Translate>}
    to="/docs/routing/overview"
  />
  <NavCard
    icon={<UpgradeIcon />}
    title={<Translate id="homepage.nav.upgrade.title">Upgrade Your App</Translate>}
    description={<Translate id="homepage.nav.upgrade.desc">Migration guides, changelogs, and automated upgrade tooling.</Translate>}
    to="/docs/upgrading/overview"
  />
  <NavCard
    icon={<DataObjectIcon />}
    title={<Translate id="homepage.nav.databinding.title">Data Binding & Forms</Translate>}
    description={<Translate id="homepage.nav.databinding.desc">Bind UI components to Java models with built-in validation.</Translate>}
    to="/docs/data-binding/overview"
  />
  <NavCard
    icon={<PaletteIcon />}
    title={<Translate id="homepage.nav.styling.title">Styling & Theming</Translate>}
    description={<Translate id="homepage.nav.styling.desc">Customize colors, typography, and component appearance with DWC tokens.</Translate>}
    to="/docs/styling/overview"
  />
  <NavCard
    icon={<LeafIcon />}
    title={<Translate id="homepage.nav.spring.title">Spring Boot</Translate>}
    description={<Translate id="homepage.nav.spring.desc">Integrate webforJ with Spring Boot, Spring Data JPA, and your existing stack.</Translate>}
    to="/docs/integrations/spring/spring-boot"
  />
</div>

---

## What's New

<p className={styles.sectionTagline}>Release highlights and upgrade guides for recent versions.</p>

<div className={styles.announcementList}>
  {announcements.map((item) => (
    <div key={item.title} className={styles.announcementItem}>
      <span className={styles.announcementDate}>{item.date}</span>
      <div className={styles.announcementBody}>
        <Link to={item.link} className={styles.announcementTitle}>{item.title}</Link>
        <p className={styles.announcementDesc}>{item.description}</p>
      </div>
    </div>
  ))}
</div>

---

## Components

<div className={styles.sectionHeader}>
  <p className={styles.sectionTagline} style={{margin: 0}}>A few highlights from the component library.</p>
  <Link className={styles.seeAll} to="/docs/components/overview">Browse all components →</Link>
</div>

<div className={styles.componentGrid}>
  {componentShowcase.map((comp) => (
    <ComponentCard key={comp.name} {...comp} />
  ))}
</div>

---

## Built with webforJ

<div className={styles.sectionHeader}>
  <p className={styles.sectionTagline} style={{margin: 0}}>Real apps from the community, browse the source and get inspired.</p>
  <Link className={styles.seeAll} to="/built-with-webforj">
    <Translate id="homepage.projects.seeAll">View all projects →</Translate>
  </Link>
</div>

<div className={styles.projectGrid}>
  {featuredProjects.map((project) => (
    <ProjectCard key={project.name} {...project} />
  ))}
</div>

