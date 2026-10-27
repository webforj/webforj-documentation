---
title: BBjServices
sidebar_position: 45
sidebar_class_name: new-content
description: Set up live reload and hotswap for a webforJ app that runs in the BBjServices of your own machine through BBjShip.
---

In BBjServices, the build starts no server of its own. `mvn compile webforj:watch` runs the app in the BBjServices of your own machine through [BBjShip](/docs/configuration/bbjship/overview), straight from the project. An archetype project with the BBj flavor sets this as its default Maven goal, so `mvn` with no arguments compiles the app, starts the [frontend watch](/docs/configuration/deploy-reload/frontend-watch), and runs the app.

## Requirements {#requirements}

A Maven project set up for BBjShip, see [Getting started](/docs/configuration/bbjship/getting-started) or [Existing projects](/docs/configuration/bbjship/existing-projects), with BBj installed on the same machine as the project. BBjServices loads the classes from the folders the build compiles into, so a BBj server on another machine gets [deploys](/docs/configuration/bbjship/deploying) only, with neither live reload nor hotswap.

## Turning live reload on {#turning-live-reload-on}

```ini title="webforj.conf"
webforj.devtools.livereload.enabled = true
```

The remaining keys are listed in the [settings](/docs/configuration/deploy-reload/overview#settings).

## Class changes {#class-changes}

In BBjServices, a class change reaches the running app only through a [hotswap tool](/docs/configuration/deploy-reload/hotswap). Without one, a compiled change doesn't apply and the page doesn't reload until the watch starts again. An archetype project configures HotswapAgent:

```xml title="pom.xml"
<hotswap>
  <hotswapAgent/>
</hotswap>
```

The tool attaches to BBjServices, not to the build. The first run adds it to the Java arguments of BBjServices and restarts BBjServices once. Later runs start without a restart. On macOS and Linux, and on Windows when BBjServices isn't registered as a Windows service, the restarted BBjServices runs outside the service manager of the operating system until the machine restarts. [Troubleshooting](/docs/configuration/bbjship/troubleshooting#development-runs) lists the messages of a development run and what to do about each.


:::warning Restarts BBjServices
The first run changes the Java settings of BBjServices in `<BBj home>/cfg/BBj.properties` and restarts BBjServices. The restart ends every running BBj session on that machine, not only webforJ ones, so run it when nobody else uses that BBj. Before the first change, a copy of the file is kept as `BBj.properties.before-webforj` in the folder `<BBj home>/plugins/.webforj-bbjship-deployments`. Back up the file yourself first if you changed it by hand. `mvn webforj:bbj-setup` makes the same change.
:::

A dependency change needs a new start of the watch.

### Java of BBjServices {#java-of-bbjservices}

With HotswapAgent, [class structure changes](/docs/configuration/deploy-reload/hotswap#class-structure-changes) depend on the Java that BBjServices runs on, not on the Java of the build. The watch never changes it. To run BBjServices on a [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases):

<!-- vale Google.WordListCase = NO -->
1. In Enterprise Manager, go to **BBjServices**, **Java Settings**.
2. Select **BBjServices** under **Application** and set the Java home.
3. Restart BBjServices. **Java Details** in Enterprise Manager then shows the JDK and the version it runs on.
<!-- vale Google.WordListCase = YES -->

See [Prerequisites](/docs/introduction/prerequisites#java-development-kit-jdk-21) for installing a JetBrains Runtime.

## Production builds {#production-builds}

`mvn install` deploys the packaged app without development tools, see [Deploying](/docs/configuration/bbjship/deploying). The `webforj.devtools.livereload.enabled` property has no effect in a packaged app.
