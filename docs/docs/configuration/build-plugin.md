---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: Add the webforJ Maven or Gradle plugin to your build, the goals it binds to each phase, and the options it accepts.
---

# webforJ build plugin <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

The webforJ build plugin runs webforJ's build time work as part of your Maven or Gradle build. You add it once, and it binds its goals to the phases you already run, with no separate frontend project to keep in sync. It drives the [frontend bundler](/docs/managing-resources/bundler/overview), compiling the frontend, running the frontend tests, serving the development watch, and attaching a [hotswap tool](/docs/configuration/deploy-reload/hotswap) to the app it starts.

## Adding the plugin {#adding-the-plugin}

A webforJ project created from an [archetype](/docs/introduction/getting-started) already has the plugin. To add it to an existing project:

<Tabs>
<TabItem value="maven" label="Maven">

Declaring the plugin with `<extensions>true</extensions>` binds its goals to the build with no execution blocks to write:

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

Add the plugin through a `buildscript` classpath dependency and apply it:

```groovy title="build.gradle"
buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath "com.webforj:webforj-gradle-plugin:${webforjVersion}"
  }
}

apply plugin: 'com.webforj'
```

</TabItem>
</Tabs>

## Goals and tasks {#goals-and-tasks}

Three goals bind to phases you already run, so a normal `mvn package` or `./gradlew build` produces an app with its frontend compiled in, and the test phase runs the frontend tests alongside the Java tests. The watch is the one you start by hand during development:

| Maven goal | Gradle task | Runs | What it does |
|------------|-------------|------|--------------|
| `bundle` | `webforjBundle` | `prepare-package`, before every jar and war | Compiles the frontend for the packaged app |
| `test` | `webforjTest` | with the test phase | Runs the frontend tests |
| `clean` | `webforjCleanFrontend` | with the clean phase | Removes the generated frontend |
| `watch` | `webforjWatch` | by hand, alongside the app | Rebuilds on change during development |
| `push-keys` | `webforjPushKeys` | by hand, once per deployment | Generates the key pair for [push notifications](/docs/advanced/push-notifications) and prints the configuration lines |

Start the watch as the goal before the one that runs the app, `mvn compile webforj:watch spring-boot:run` for example. An archetype project sets this as the default goal, so `mvn` alone starts everything. Its reload behavior is covered in [Frontend watch](/docs/configuration/deploy-reload/frontend-watch).

Skip the frontend tests together with the Java tests, `-DskipTests` or `-Dmaven.test.skip` with Maven and `-PskipTests` with Gradle.

## Options {#options}

Set options as Maven `<configuration>` elements, or as Gradle `webforj { }` extension values. Every Maven option except `plugins` and `hotswap` also accepts a `-D` property on the command line. The two build tools mirror each other:

| Maven element | Maven property | Gradle | Default | Purpose |
|---------------|----------------|--------|---------|---------|
| `bunVersion` | `webforj.bundler.version` | `bunVersion` | managed | Pin the Bun version for reproducible builds |
| `bunPath` | `webforj.bundler.path` | `bunPath` | download | Use an existing Bun binary instead of downloading |
| `cacheDir` | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Where managed Bun binaries are cached |
| `sourceRoot` | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Where the frontend entry sources live |
| `workDir` | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Where the plugin writes its generated build files |
| `plugins` | — | `plugins` | — | Turn an [extension](/docs/managing-resources/bundler/extensions/overview) on or off by id, such as `webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | Package prefixes to skip during the annotation scan |
| `eager` | `webforj.bundler.eager` | `eager` | `false` | Load the whole frontend at app start instead of per view, see [Eager bundle](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs` | `webforj.bundler.testArgs` | `testArgs` | — | Extra arguments passed to the frontend test runner |
| `hotswap` | — | `hotswap` | — | Attach a class update tool to the app the build starts, see [Hotswap](/docs/configuration/deploy-reload/hotswap) |

For example, to pin the Bun version and turn on Tailwind:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <bunVersion>1.3.0</bunVersion>
    <plugins>
      <webforj-tailwind>true</webforj-tailwind>
    </plugins>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  bunVersion = '1.3.0'
  plugins.put('webforj-tailwind', 'true')
}
```

</TabItem>
</Tabs>
