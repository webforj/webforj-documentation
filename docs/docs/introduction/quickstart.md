---
title: Quickstart
description: Install the prerequisites, generate a webforJ project, run it, and open it in your browser — four steps, one running app.
sidebar_position: 2
hide_table_of_contents: true
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import Hero from '@site/src/components/GettingStarted/Hero';
import { Quickstart, QuickstartStep } from '@site/src/components/GettingStarted/Quickstart';
import styles from '@site/src/components/GettingStarted/GettingStarted.module.css';

<div className={styles.page}>

<Hero
  eyebrow="Quickstart"
  title="From zero to a running webforJ app"
  subtitle="Four steps, one running app. Pick Maven or JBang — the choice is remembered as you scroll."
/>

<Quickstart
  title="Four steps to your first app"
  subtitle="Total time: about a minute after the prerequisites."
>

<QuickstartStep number="01" title="Install the prerequisites">

You need three things on your machine:

- **JDK 21** — Install via [Eclipse Temurin](https://adoptium.net/temurin/releases/), [Oracle Java](https://www.oracle.com/java/technologies/downloads/), or (on Unix) [SDKMAN!](https://sdkman.io/). Verify with `java -version`.
- **Maven** or **[JBang](/docs/integrations/jbang)** — Maven is the traditional path ([download](https://maven.apache.org/download.cgi), verify with `mvn -v`). JBang is a lighter single-file alternative — pick either.
- **A Java IDE** — [Visual Studio Code](https://code.visualstudio.com/Download), [IntelliJ IDEA](https://www.jetbrains.com/idea/download/), or [NetBeans](https://netbeans.apache.org/download/index.html). Any Java-capable editor works.

</QuickstartStep>

<QuickstartStep number="02" title="Create a project">

Pick the tool you prefer. Your choice is remembered across the next step.

<Tabs groupId="webforj-install">
<TabItem value="maven" label="Maven" default>

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

</TabItem>
<TabItem value="jbang" label="JBang">

Save the following as `HelloWorld.java` — no project structure, no `pom.xml`, just a single file:

```java title="HelloWorld.java"
///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.webforj:webforj-jbang-starter:25.11
//JAVA 21

package bang;

import com.webforj.App;
import com.webforj.annotation.Routify;
import com.webforj.component.html.elements.H1;
import com.webforj.router.annotation.Route;

@Routify
public class HelloWorld extends App {

  @Route("/")
  public static class HomeView extends H1 {
    public HomeView() {
      super("Hello, webforJ!");
    }
  }
}
```

The `//DEPS` directive tells JBang which webforJ version to pull. See the [JBang integration guide](/docs/integrations/jbang) for more directives.

</TabItem>
</Tabs>

</QuickstartStep>

<QuickstartStep number="03" title="Run it">

<Tabs groupId="webforj-install">
<TabItem value="maven" label="Maven" default>

Change into your new project directory and run:

```bash
cd my-app
mvn
```

The archetype ships a `<defaultGoal>` in its `pom.xml`, so a bare `mvn` boots the app on the embedded Jetty server.

</TabItem>
<TabItem value="jbang" label="JBang">

From the folder that holds `HelloWorld.java`:

```bash
jbang HelloWorld.java
```

JBang downloads the dependencies on the first run, then launches the app. Close the browser tab to stop the server automatically.

</TabItem>
</Tabs>

</QuickstartStep>

<QuickstartStep number="04" title="Open it in your browser">

Visit [http://localhost:8080](http://localhost:8080). You should see your hello-world app rendered from pure Java — no build tools running in your browser, no framework glue between you and the UI.

Next: walk through the [app basics](./basics) to see how the `Application` and view classes fit together, or head back to [Getting Started](./getting-started) to pick a topic to explore.

</QuickstartStep>

</Quickstart>

</div>
