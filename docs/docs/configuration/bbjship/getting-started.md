---
title: Getting Started
sidebar_position: 2
description: Create a webforJ app that runs with BBjServices from startforJ or the Maven archetypes, run it on your local BBj and deploy it with BBjShip.
---

This guide creates a webforJ app that runs with BBjServices, runs it on your local BBj while you develop, and deploys it with BBjShip.

:::tip Prerequisites
You need a Maven project and a BBj installation of the same version as webforJ, for example BBj 26.03 for webforJ 26.03, with BBjServices running. BBjShip doesn't support Gradle. Review the [prerequisites](/docs/introduction/prerequisites) for the rest of the tools.
:::

## Create a BBj app {#create-a-bbj-app}

You have two options for creating a new webforJ app that runs with BBjServices: using the graphical startforJ tool or the Maven command line.

<!-- vale off -->
### Option 1: Using startforJ {#option-1-using-startforj}
<!-- vale on -->

When you create an app with [startforJ](https://docs.webforj.com/startforj), you can customize it by providing the following information:

- Basic project metadata (App Name, Group ID, Artifact ID)
- webforJ version and Java version
- Theme Color and Icon
- Archetype
- **Runs on** - Select **BBjServices** to create a project that runs with BBjServices

### Option 2: Using the command line {#option-2-using-the-command-line}

If you prefer to use the command line, generate the project with the official webforJ archetypes:

```bash {8}
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=org.example \
  -DartifactId=my-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-bbj
```

With the `flavor` parameter, the archetype generates a project that runs with BBjServices.

This creates a project with:
- The `webforj` dependency without the webforJ engine, which BBjServices provides
- The webforJ Maven plugin, set up to deploy to BBjShip on your local BBj under the artifact ID as the app name
- HotswapAgent for development runs
- Example views
- webforJ configuration files for development and production

## Run the app {#run-the-app}

An archetype project sets its default Maven goal, so `mvn` with no arguments compiles the app and starts a [development run](/docs/configuration/deploy-reload/bbjservices) in your local BBjServices:

```bash
mvn
```

The first run prepares your local BBj. It installs BBjShip and adds HotswapAgent to BBjServices, which restarts BBjServices once. The build then prints the address of your app:

```text
[INFO] webforJ watch: my-app is running at http://localhost:8888/webapp/my-app
[INFO] webforJ watch: press Ctrl+C to stop the app and the watch
```

Classes you compile reach the running app without a restart. Stop the build with `Ctrl+C` to stop the app.

## Deploy the app {#deploy-the-app}

To deploy the packaged app, run:

```bash
mvn install
```

The app is then published at `http://localhost:8888/webapp/my-app` until you deploy it again or remove it. See [Deploying](/docs/configuration/bbjship/deploying) to deploy to another BBj server, for example from a CI pipeline.
