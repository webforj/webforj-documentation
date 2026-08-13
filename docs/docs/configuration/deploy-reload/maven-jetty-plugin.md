---
title: Jetty
sidebar_position: 40
description: Run a webforJ app on the embedded Jetty server with the Maven Jetty plugin, with live reload and hotswap during development.
---

The Maven Jetty plugin runs the app in an embedded Jetty server straight from the project. An archetype project sets `compile webforj:watch jetty:run` as its default Maven goal, so `mvn` with no arguments compiles the app, starts the [frontend watch](/docs/configuration/deploy-reload/frontend-watch), and serves the app on Jetty.

## Requirements {#requirements}

A Jetty project declares the development tools itself, in the profile used for development runs:

```xml title="pom.xml"
<profiles>
  <profile>
    <id>dev</id>
    <activation>
      <activeByDefault>true</activeByDefault>
    </activation>
    <dependencies>
      <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-devtools</artifactId>
      </dependency>
    </dependencies>
  </profile>
</profiles>
```

The version comes from the webforJ Bill of Materials (BOM). The profile keeps the dependency out of the packaged war. A project created from an [archetype](/docs/introduction/getting-started) has this profile.

## Turning live reload on {#turning-live-reload-on}

```ini title="webforj.conf"
webforj.devtools.livereload.enabled = true
```

The keys are the same ones a Spring Boot app sets in `application.properties`, listed in the [settings](/docs/configuration/deploy-reload/overview#settings).

## Class changes {#class-changes}

With a [hotswap tool](/docs/configuration/deploy-reload/hotswap) configured, the tool applies class changes and Jetty redeploys nothing. Two Jetty properties support this, and an archetype project sets both:

- `scan` is `0`, which turns Jetty's file scanning off.
- `deployMode` stays unset. Hotswap requires the forked mode, and the plugin selects it. A build that sets `deployMode` to another value starts without the tool and logs it.

Without a hotswap tool, set `scan` to an interval in seconds and Jetty redeploys the app when compiled classes or resources change:

| Property | Description | Default |
|----------|-------------|---------|
| `scan` | Interval in seconds between scans of the compiled output, set as the `jetty.scan` property. `0` turns scanning off. Longer intervals lower the load and delay the redeploy. | `1` |

## Usage considerations {#usage-considerations}

- **Memory and CPU**: low `scan` values raise resource consumption on large projects. Longer intervals lower it and delay the redeploy.
- **Development only**: the Jetty plugin isn't for production deployments.
- **Sessions**: a redeploy can drop user sessions. A [hotswap tool](/docs/configuration/deploy-reload/hotswap) applies changes without a redeploy, and the session survives.
