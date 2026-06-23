---
title: Build and tests
sidebar_position: 40
sidebar_class_name: new-content
description: What the bundler does across the build, the development watch, running frontend tests, tuning a compiler, and producing a minified production bundle.
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

The bundler runs as goals of the [webforJ build plugin](/docs/configuration/build-plugin). Add the plugin once, as shown there, and a normal `mvn package` or `gradle build` produces an app with its frontend compiled in, while `mvn test` runs the frontend tests alongside the Java tests. This page covers what the bundler does across those steps.

## The development watch {#the-development-watch}

The `watch` step is the one you run by hand during development, alongside the app. It compiles the frontend once, then rebuilds on every change and refreshes the browser.

```bash
mvn compile webforj:watch spring-boot:run
```

A webforJ project sets this as its default goal, so `mvn` with no arguments starts the watch and the app together. The reload behavior it drives, a stylesheet change applied in place against a script change that reloads the view, is covered in [Frontend watch](/docs/configuration/deploy-reload/frontend-watch).

## Frontend tests {#frontend-tests}

The `test` step runs the Bun test runner over `src/main/frontend` during the test phase, so `mvn test` runs the frontend tests with the Java tests. When the source root holds no test files, the step is skipped, and a failing frontend test fails the build, so a broken frontend stops a release the same way a broken Java test does. For writing those tests, see [Frontend testing](/docs/testing/frontend-testing).

## Tuning a compiler {#tuning-a-compiler}

A compiler reads its settings from `src/main/frontend/bun.config.ts`, keyed by the extension id, so a setting reaches the right compiler with no flag on the build. See [SCSS and Sass](/docs/managing-resources/bundler/extensions/scss) for a worked example that gives the SCSS compiler a load path.

## The production bundle {#the-production-bundle}

The `bundle` step runs during `prepare-package`, so packaging an app compiles its frontend for production. A production build differs from the development one in two ways that matter once an app is deployed.

- **Hashed file names.** Each output file carries a hash of its content in its name. A browser can then cache a file for a long time, because a change to the content produces a new name, and the new name forces a fresh fetch. Caching stays safe without a manual version bump.
- **Minified output.** Whitespace and dead code are stripped, so the files a browser downloads are as small as the compile can make them.

A development build skips both. It keeps stable names and readable output, so the watch can swap one file in place and you can read what loads while you debug.

Because minification is part of this step, a project that uses the bundler needs nothing else to ship minified CSS and JavaScript. For an app that loads assets through the [asset annotations](/docs/managing-resources/importing-assets) without the bundler, the [minifier plugin](/docs/configuration/minifier-plugin) covers that production minification instead.

## Eager bundle {#eager-bundle}

By default, each view loads only the frontend it uses, when a component of that class is created, so a view pays for nothing it doesn't render.

Eager mode loads the whole frontend at app start as a single bundle, instead of per view. Turn it on with the `eager` option:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <eager>true</eager>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  eager = true
}
```

</TabItem>
</Tabs>

Eager is off by default, and the per view model suits most apps. Reach for it when you want the whole frontend in place from the start rather than loaded as views render.
