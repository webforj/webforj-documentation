---
title: App Info
sidebar_position: 10
description: Read the versions, Java runtime, and project root of the app craftforJ is attached to.
---

App info reports what your app is actually running with, which isn't always what your `pom.xml` says it should be running with. Alongside the webforJ and BBj Services versions, it covers the Java runtime, the operating system, and where the app is rooted on disk.

![The App Info tab](/img/craftforj/app-info/app-info-tab.png#rounded-border)

Two of these values affect how craftforJ behaves:

- **The project root** is where craftforJ looks for your sources. [Writing to source](/docs/craftforj/source-changes) can't work when it's wrong, so set [`project-root`](/docs/craftforj/configuration.#project-root) if the reported value doesn't match your project.
- **The Java runtime** determines how thoroughly the assistant's [Java changes](/docs/craftforj/ai#it-writes-java) are validated, because full validation needs a compiler.

:::tip Filing an issue
Include everything on this page, together with a log downloaded from craftforJ troubleshooting settings.
:::
