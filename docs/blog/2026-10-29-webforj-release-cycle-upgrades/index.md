---
title: "webforJ Release Cycle: How Often It Ships and How Upgrades Work"
description: "The webforJ release cycle in plain terms: how often major, minor and patch versions ship, how security fixes arrive, how upgrades work, and how to test a release early."
slug: webforj-release-cycle-upgrades
date: 2026-10-29
authors: webforJ
tags: [insights, modernization]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-29-webforj-release-cycle-upgrades/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-29-webforj-release-cycle-upgrades/cover.jpg)

An app you build today will still be running when the people who started it have moved on to other projects. So before you pick a framework for it, you want to know how the framework itself behaves over time: how often new versions land, what an upgrade costs you, and how quickly a security problem gets fixed.

The webforJ release cycle is regular and written down. A major version arrives once a year, minor versions arrive every few weeks, patches ship when needed, and snapshot builds let you test a version before it is released. The sections below answer each question directly and link to the pages that back it up.

<!-- truncate -->

## How often does webforJ release? {#how-often}

webforJ ships a major version once a year, minor versions roughly every six to eight weeks, and patch releases when needed. That is the release model on our [upgrading overview](/docs/upgrading/overview). These are the recent majors and minors.

| Release | Type | Date |
|---|---|---|
| 24.00 | Major | April 8, 2024 |
| 25.00 | Major | April 22, 2025 |
| 25.12 | Minor | March 17, 2026 |
| 26.00 | Major | April 28, 2026 |
| 26.01 | Minor | June 30, 2026 |
| 26.02 | Minor | August 31, 2026 |

Each of those versions has a release article: [24.00](/blog/whats-new-24.00), [25.00](/blog/whats-new-v25.00), [25.12](/blog/whats-new-v25.12), [26.00](/blog/whats-new-v26.00), [26.01](/blog/whats-new-v26.01) and [26.02](/blog/whats-new-v26.02). Every release also comes with detailed [release notes on GitHub](https://github.com/webforj/webforj/releases) that list new features, bug fixes and any action you need to take.

The version number sets your expectations. A number such as 26.01 is a minor release with incremental improvements, optimizations, and smaller features. A number such as 26.00 is a major release that can require configuration changes or changes to your code.

## What do the release types mean? {#release-types}

The overview describes three kinds of release:

- **Major releases** happen annually. They bring significant new features and improvements and may need changes to existing apps.
- **Minor releases** happen several times a year. They bring incremental improvements, optimizations, and smaller features.
- **Patch releases** are released if needed. They address critical bugs, performance issues and security vulnerabilities, and carry an extra number such as 20.01.1.

We work to maintain compatibility, but major releases may include changes that require adjustments to apps, so the release notes list deprecated features for you to review.

## How do webforJ upgrades work? {#how-upgrades-work}

You read the upgrade guide for your target major version, then let an automated recipe do most of the edits. Major releases have upgrade guides in the [upgrading section](/docs/upgrading/overview), and each one lists the changes existing apps need.

The 25.00 guide covers the move to Jetty 12 and the Jakarta EE10 servlet architecture. The 26.00 guide covers the move to Java 21 or Java 25, the move to Spring Boot 4.x, and the removed or replaced Table and Repository methods, each with a table of the old call next to the new one.

Design changes get their own treatment. The [26.00 design system guide](/docs/upgrading/webforj-26.00/design-system) calls the update incremental rather than a full rewrite: most v25 CSS variables remain available, the public API of the theme engine is preserved, and existing customizations continue to work without changes.

## Can upgrades be automated? {#automated-upgrades}

Yes. webforJ ships [OpenRewrite recipes](/blog/openrewrite-java-framework-upgrade) in the `webforj-rewrite` artifact. The [automated upgrades guide](/docs/upgrading/automated-upgrades) describes the flow. You add the OpenRewrite Maven plugin to your `pom.xml` with `webforj-rewrite` as a dependency, choose the recipe for your target version, and preview the result first:

```bash
mvn rewrite:dryRun
```

That command writes a diff to `target/rewrite/rewrite.patch` without touching any file. When the diff looks right, apply it:

```bash
mvn rewrite:run
```

The recipe updates dependencies, renames methods, changes types, and adjusts return types. Where no one-to-one replacement exists, it leaves a `TODO` comment in your code with specific instructions, so you can search your project for `TODO` to see what is left. Then you remove the plugin from your `pom.xml`.

Recipes exist for standard webforJ projects and for Spring Boot projects. You can also run them between majors to move away from newly deprecated APIs before they are removed in a later version.

If you work with an AI coding assistant, the `webforj-upgrading-versions` skill in the [AI plugin](/docs/ai-tooling) runs the recipe for the target version and resolves the `TODO` comments with you. For targets with no published recipe, such as 24 to 25, it walks you through the manual route in the [25.00 guide](/docs/upgrading/webforj-25.00).

## How are security fixes delivered? {#security-fixes}

Critical security vulnerabilities are addressed in patch releases as soon as possible. Security is a priority in our release model. Patch releases address critical bugs, performance issues and security vulnerabilities, and carry an additional number such as 20.01.1.

## Can I test a release before it ships? {#test-before-release}

Yes. Snapshot builds are available before most releases, and we encourage you to test against them so problems surface early. The [snapshots guide](/docs/configuration/snapshots) shows how to find the newest snapshot version in the webforJ project on GitHub, set it as the `webforj.version` property in your POM, and add the Central Portal Snapshots repository so Maven can resolve it.

For a new project, [startforJ](https://docs.webforj.com/startforj/) offers the webforJ version that ends with `(pre)`. Snapshots are under active development and subject to change, so keep them out of live production apps and use them on a branch or a test environment.

## Is webforJ right for a long-lived app? {#long-lived-app}

webforJ gives you a documented release model to plan a long-lived app around: a yearly major, minors every six to eight weeks, and patches for security. Upgrades come with a written guide per major version, an automated recipe that does most of the edits, a dry run that shows the diff first, and snapshots that let you try the next version early. The questions a manager or architecture board asks beyond upgrades, such as lock-in, fit, testing and a pilot, are covered in [the business case for a Java UI framework](/blog/business-case-java-ui-framework).

For the wider picture, read our guide to the [full-stack Java framework](/blog/java-full-stack-framework).

## FAQ {#faq}

### How often does webforJ release a new version? {#faq-how-often}

A major version once a year, minor versions roughly every six to eight weeks, and patch versions when needed.

### What does a patch release change? {#faq-patch}

It addresses critical bugs, performance issues and security vulnerabilities, with a number such as 20.01.1.

### Do I have to upgrade by hand? {#faq-by-hand}

No. The `webforj-rewrite` OpenRewrite recipes handle most of the upgrade and leave `TODO` comments for the rest.

### Where do I find what changed in a version? {#faq-what-changed}

In the GitHub release notes, the release article for that version, and the upgrade guide for major versions.

## Plan your next webforJ upgrade {#next-upgrade}

Open your `pom.xml`, note your `webforj.version`, and read the [upgrading overview](/docs/upgrading/overview). Then run `mvn rewrite:dryRun` with the recipe for your target version and look at the diff before you change a single file.
