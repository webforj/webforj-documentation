---
title: Existing Projects
sidebar_position: 3
description: Move an existing webforJ project from the webforJ install plugin to BBjShip with one OpenRewrite run, and what the migration changes in the build and the configuration.
---

A project that deploys with the deprecated webforJ install plugin moves to BBjShip with one OpenRewrite run. The run changes the `pom.xml` and the `webforj.conf` of the project. Java code, stylesheets, and resources stay unchanged.

## Migrate with OpenRewrite {#migrate-with-openrewrite}

Add the OpenRewrite Maven plugin to your `pom.xml`, with the webforJ 26.03 recipe:

```xml title="pom.xml"
<plugin>
  <groupId>org.openrewrite.maven</groupId>
  <artifactId>rewrite-maven-plugin</artifactId>
  <version>6.36.0</version>
  <configuration>
    <activeRecipes>
      <recipe>com.webforj.rewrite.v26.UpgradeWebforj_26_03</recipe>
    </activeRecipes>
  </configuration>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-rewrite</artifactId>
      <version>26.03</version>
    </dependency>
  </dependencies>
</plugin>
```

Run the migration, then review what changed:

```bash
mvn rewrite:run
git diff
```

The migrated project deploys with BBjShip. `mvn install` deploys the app, and `mvn` starts a [development run](/docs/configuration/deploy-reload/bbjservices). Remove the OpenRewrite plugin from your `pom.xml` afterwards. [Automated upgrades](/docs/upgrading/automated-upgrades#previewing-changes) shows how to preview the changes before the run.

## What the migration changes {#what-the-migration-changes}

### The build plugin {#the-build-plugin}

The install plugin becomes the [webforJ build plugin](/docs/configuration/build-plugin) with `<extensions>true</extensions>`, so `mvn install` deploys without an execution. Its settings move into the `<bbj>` block:

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <extensions>true</extensions>
  <configuration>
    <bbj>
      <publishName>inventory</publishName>
      <deployment>
        <url>http://localhost:8888/webforj-bbjship</url>
      </deployment>
    </bbj>
  </configuration>
</plugin>
```

| Install plugin | BBjShip |
|---|---|
| `<deployurl>` | `<bbj><deployment><url>`, with the path `/webforj-bbjship` |
| `<publishname>` | `<bbj><publishName>` |
| `<username>`, `<password>`, `<token>` | `<bbj><admin>` |
| `<classname>` | `webforj.entry` in `webforj.conf` |
| `<debug>` | `webforj.debug` in `webforj.conf` |

[Deploying](/docs/configuration/bbjship/deploying#settings) lists every setting of the `<bbj>` block.

### The webforJ engine {#the-webforj-engine}

BBjServices provides the webforJ engine, so your app must not carry its own. The `webforj` dependency already excludes `webforj-engine`. The migration declares it again in `provided` scope so your IDE still compiles against it:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-engine</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

### The configuration file {#webforjconf}

The entry class and the debug flag move from the install plugin to `webforj.conf`, next to the settings a BBjShip app runs with:

```hocon title="webforj.conf"
webforj.entry = "com.example.Application"
webforj.debug = true
webforj.assetsSource = packaged
webforj.devtools.livereload.enabled = true
webforj.devtools.craftforj.enabled = true
```

`webforj.assetsSource = packaged` serves the `ws://` static files and the `icons://` icons from the app. Without it, an app that uses `icons://` fails to start in BBjServices. Keys the file already sets stay unchanged.

### Development runs {#development-runs}

The migration adds HotswapAgent to the configuration of the build plugin:

```xml title="pom.xml"
<hotswap>
  <hotswapAgent/>
</hotswap>
```

It also sets `compile webforj:watch` as the default goal of the build, so `mvn` runs the app in the BBjServices of your own machine:

```xml title="pom.xml"
<defaultGoal>compile webforj:watch</defaultGoal>
```

See [BBjServices](/docs/configuration/deploy-reload/bbjservices) in Redeployment and Live Reload.

## Without the install plugin {#without-the-install-plugin}

A project that never used the install plugin gets the same configuration by hand: the [build plugin](#the-build-plugin) with a `<bbj>` block, the `webforj-engine` [exclusion and `provided` declaration](#the-webforj-engine), and [`webforj.conf`](#webforjconf) with `webforj.assetsSource = packaged`. The project uses `jar` packaging, and the `webforj` dependency excludes `webforj-engine`:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj</artifactId>
  <version>${webforj.version}</version>
  <exclusions>
    <exclusion>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-engine</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```
