---
sidebar_position: 2
title: Editor Settings and Local Setup
description: Set up Java, Maven, Node, Docusaurus, formatting, and prose tooling for contributing to webforJ documentation and samples.
hide_giscus_comments: true
---

Set up the tools for the part of the project you plan to edit. Documentation pages use Docusaurus, while live demos and integration tests are built from the Maven project at the repository root.

## Required tools {#required-tools}

Use these tools for the documentation repository:

| Tool | Used for |
| --- | --- |
| Java 21 | Compiling and running the sample app and integration tests |
| Maven | Building Java samples, running the webforJ demo app, and running tests |
| Node.js 18 or newer | Building and previewing the Docusaurus site |
| npm | Installing and running docs site scripts |
| Git | Creating branches and submitting pull requests |

Docker is optional. Use it only when you need to reproduce containerized integration test behavior.

## Install dependencies {#install-dependencies}

Install the locked Docusaurus dependencies from the `docs` directory:

```bash
cd docs
npm ci
```

Maven downloads Java dependencies automatically when you run Maven commands from the repository root.

## Preview the docs site {#preview-the-docs-site}

Start the Docusaurus development server from the `docs` directory:

```bash
cd docs
npm run start
```

When you edit examples that render inside `ComponentDemo`, run the sample app from the repository root in another terminal:

```bash
mvn jetty:run
```

The Docusaurus server uses port `3000`, and local demo frames load the sample app from port `8080`. If port `8080` is unavailable, pass the same alternative port to Maven with `-Dport=<port>` and to Docusaurus with `WEBFORJ_PORT`.

## Formatting and linting {#formatting-and-linting}

Java sample code must follow Google Java Format. The Maven build uses Spotless to validate Java files under `src/main/java` and `src/test/java`.

Run this from the repository root before submitting Java changes:

```bash
mvn spotless:check
```

If formatting fails, apply formatting with:

```bash
mvn spotless:apply
```

SonarLint can provide additional editor feedback, but Spotless is the repository's required Java formatting validation.

## Prose style {#prose-style}

Markdown and Markdown with JSX content should be direct, accurate, and consistent with nearby pages. Vale runs in pull request CI and uses the repository configuration at `.vale.ini`.

Run Vale against each changed prose file from the repository root:

```bash
vale docs/docs/contributing/overview.md
```

Resolve Vale alerts before opening a pull request.
