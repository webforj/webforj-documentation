---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: Add the webforJ Maven or Gradle plugin to your build, the goals it binds to each phase, and the options it accepts.
---

# webforJ build plugin <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

The webforJ build plugin runs webforJ's build time work as part of your Maven or Gradle build. You add it once, and it binds its goals to the phases you already run, with no separate frontend project to keep in sync. It drives the [frontend bundler](/docs/managing-resources/bundler/overview), compiling the frontend, running the frontend tests, and serving the development watch.

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

## Goals {#goals}

The plugin binds four goals, each to a phase you already run, so a normal `mvn package` or `gradle build` produces an app with its frontend compiled in, and `mvn test` runs the frontend tests alongside the Java tests.

| Maven goal | Gradle task | Phase | What it does |
|------------|-------------|-------|--------------|
| `bundle` | `webforjBundle` | `prepare-package` | Compiles the frontend for production |
| `test` | `webforjTest` | `test` | Runs the frontend tests |
| `clean` | `webforjCleanFrontend` | `clean` | Removes the generated frontend |
| `watch` | `webforjWatch` | run by hand | Rebuilds on change during development |

The `watch` goal is the one you run by hand during development, alongside the app. Its reload behavior is covered in [Frontend watch](/docs/configuration/deploy-reload/frontend-watch).

## Options {#options}

Set options as Maven `<configuration>` (or `-D` properties on the command line), and as Gradle `webforj { }` extension values. The two build tools mirror each other.

| Option | Maven property | Gradle | Default | Purpose |
|--------|----------------|--------|---------|---------|
| Bun version | `webforj.bundler.version` | `bunVersion` | managed | Pin the Bun version for reproducible builds |
| Bun binary | `webforj.bundler.path` | `bunPath` | download | Use an existing Bun binary instead of downloading |
| Cache directory | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Where managed Bun binaries are cached |
| Source root | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Where the frontend entry sources live |
| Work directory | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Where the plugin writes its generated build files |
| Extensions | `plugins` | `plugins` | — | Turn an [extension](/docs/managing-resources/bundler/extensions/overview) on or off by id, such as `webforj-tailwind` |
| Exclude packages | `webforj.bundler.excludePackages` | `excludePackages` | — | Package prefixes to skip during the annotation scan |
| Eager | `webforj.bundler.eager` | `eager` | `false` | Load the whole frontend at app start instead of per view, see [Eager bundle](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| Test arguments | `webforj.bundler.testArgs` | `testArgs` | — | Extra arguments passed to the frontend test runner |
| Skip tests | `skipTests`, `maven.test.skip` | — | `false` | Skip the frontend tests |

For example, to pin the Bun version and turn on Tailwind:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <version>1.3.0</version>
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
