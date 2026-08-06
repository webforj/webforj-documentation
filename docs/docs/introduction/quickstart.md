---
title: Quickstart
description: Install the prerequisites, generate a webforJ project, run it, and open it in your browser — four steps, one running app.
sidebar_position: 1
hide_table_of_contents: true
---

import Hero from '@site/src/components/GettingStarted/Hero';
import Illo from '@site/src/components/GettingStarted/Illo';
import { Quickstart, QuickstartStep } from '@site/src/components/GettingStarted/Quickstart';
import styles from '@site/src/components/GettingStarted/GettingStarted.module.css';

<div className={styles.page}>

<Hero
  eyebrow="Quickstart"
  title="From zero to a running app"
  subtitle="Four short steps, one Maven command, one browser tab — you'll be building in pure Java in about a minute."
  graphic={<Illo variant="wizard" />}
/>

<aside className={styles.hint}>
  <span className={styles.hintText}>
    <strong>Prefer clicking to typing?</strong> Skip the terminal and download a ready-to-run project from the browser.
  </span>
  <a className={styles.hintCta} href="https://docs.webforj.com/startforj/" target="_blank" rel="noopener noreferrer">
    Open startforJ →
  </a>
</aside>

<Quickstart
  title="Four steps to your first app"
  subtitle="Total time: about a minute after the prerequisites."
>

<QuickstartStep number="01" title="Check the prerequisites">

You need three things on your machine before you start:

- **JDK 21** — Install via [Eclipse Temurin](https://adoptium.net/temurin/releases/), [Oracle Java](https://www.oracle.com/java/technologies/downloads/), or (on Unix) [SDKMAN!](https://sdkman.io/). Verify with `java -version`.
- **Apache Maven** — Grab the [latest release](https://maven.apache.org/download.cgi) and verify with `mvn -v`.
- **A Java IDE** — [Visual Studio Code](https://code.visualstudio.com/Download), [IntelliJ IDEA](https://www.jetbrains.com/idea/download/), or [NetBeans](https://netbeans.apache.org/download/index.html). Any Java-capable editor works.

</QuickstartStep>

<QuickstartStep number="02" title="Create a project">

Run this from the folder where you want your new project:

```bash
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=org.example \
  -DartifactId=my-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj \
  -DappName=MyApp
```

Need to tweak the group ID, artifact ID, or flavor? See the [archetypes reference](../building-ui/archetypes/overview) for every option.

_Prefer a single-file setup with no `pom.xml`? Try [JBang](/docs/integrations/jbang) instead._

</QuickstartStep>

<QuickstartStep number="03" title="Run it">

Change into your new project directory and run:

```bash
cd my-app
mvn
```

The archetype ships a `<defaultGoal>` in its `pom.xml`, so a bare `mvn` boots the app on the embedded Jetty server.

</QuickstartStep>

<QuickstartStep number="04" title="Open it in your browser">

Visit [http://localhost:8080](http://localhost:8080). You should see your hello-world app rendered from pure Java — no build tools running in your browser, no framework glue between you and the UI.

Next: walk through the [app basics](./basics) to see how the `Application` and view classes fit together, or head back to [Getting Started](/) to pick a topic to explore.

</QuickstartStep>

</Quickstart>

</div>
