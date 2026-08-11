---
title: Prerequisites
description: What a webforJ development environment needs, a Java 21 or higher JDK, Maven or Gradle, and an editor with Java support.
sidebar_position: 1
---

Getting started with webforJ is simple, because there are only a couple of prerequisites. Use this guide to set up your development environment with the essential tools you will need to get up and running with webforJ.

<!-- vale off -->
## Java Development Kit (JDK) {#java-development-kit-jdk-21}
<!-- vale on -->

webforJ requires Java **21** or higher. Any distribution at that version works, so pick the one your team already uses.

:::tip Recommended for development
Develop on a [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) build. It accepts the `-XX:+AllowEnhancedClassRedefinition` option, which is what lets a [hotswap tool](/docs/configuration/deploy-reload/hotswap) carry a change to the structure of a class, a new field or a new method, into the running app.

On any other build, edits inside a method body still apply in place, and a change to the structure of a class waits for a restart. The choice only concerns the machine you develop on, and it doesn't affect what you package or where you deploy it.
:::

A version manager is the easiest way to install a JDK, and the easiest way to move between versions later. [SDKMAN!](https://sdkman.io/) covers UNIX systems, and [Jabba](https://github.com/Jabba-Team/jabba) covers UNIX systems and Windows. Under SDKMAN!, `sdk install java 21.0.11-jbr` gets you a JetBrains Runtime.

To download a build yourself instead:

- **Oracle JDK**: the [Java Downloads](https://www.oracle.com/java/technologies/downloads/) page, with Oracle's [installation guide](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html).
- **Eclipse Temurin**: the [latest releases](https://adoptium.net/temurin/releases/) page, with Adoptium's [installation guide](https://adoptium.net/installation/).
- **JetBrains Runtime**: the [releases](https://github.com/JetBrains/JetBrainsRuntime/releases) page.

Run `java -version` to confirm which version is on your path.

## Build tool {#build-tool}

webforJ builds with Maven or Gradle. [Archetypes](/docs/introduction/getting-started) generate Maven projects, so Maven is the quickest way to a new app, and an existing Gradle build works the same way.

<Tabs>
<TabItem value="maven" label="Maven">

Install Maven from the [Apache Maven download page](https://maven.apache.org/download.cgi), following Maven's [installation instructions](https://maven.apache.org/install.html) or Baeldung's [guide for each operating system](https://www.baeldung.com/install-maven-on-windows-linux-mac).

Run `mvn -v` to confirm the install.

</TabItem>
<TabItem value="gradle" label="Gradle">

Install Gradle by following Gradle's [installation guide](https://gradle.org/install/).

Run `gradle -v` to confirm the install. A project that ships a Gradle wrapper needs no install at all, since `./gradlew` fetches the version the project pins.

</TabItem>
</Tabs>

Either build runs webforJ's build time work through the [webforJ build plugin](/docs/configuration/build-plugin), which a project created from an archetype already has.

## Editor {#java-ide}

Any editor with Java support works, so use the one that fits your workflow. Common choices:

- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: Java support and a plugin ecosystem out of the box.
- **[Visual Studio Code](https://code.visualstudio.com/Download)**: A lightweight editor that takes its Java support from extensions.
- **[Zed](https://zed.dev/download)**: A code editor that picks up Java through an extension, which downloads and manages the Eclipse Java language server for you.
