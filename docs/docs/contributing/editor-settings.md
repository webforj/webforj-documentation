---
sidebar_position: 3
title: Editor Settings and Local Setup
description: Configure Java, Maven, Node, formatting, static analysis, and prose tools for webforJ framework and documentation contributions.
hide_giscus_comments: true
---

The framework and documentation repositories share Java 21 and Maven, but they use different formatting and validation tools. Run each command from the repository named in its section.

## Required tools {#required-tools}

| Tool | Framework repository | Documentation repository |
| --- | --- | --- |
| Java 21 | Required | Required for demos and integration tests |
| Maven | Required | Required for demos and integration tests |
| Node.js 18 or newer | Not required for most changes | Required |
| npm | Not required for most changes | Required |
| Git | Required | Required |

Docker is optional for documentation contributors. Use it only when you need to reproduce containerized integration test behavior.

## Framework setup {#framework-setup}

The framework repository is a multi-module Maven project. Maven downloads Java dependencies when you run the first build from the repository root:

```bash
mvn -pl webforj-foundation -am test
```

The repository includes `.editorconfig` settings for two-space indentation, UTF-8, LF line endings, and removal of trailing whitespace. It also includes Java settings for VS Code and Zed. Other editors should use the same Google Java Style profile.

### Framework formatting and analysis {#framework-formatting-and-analysis}

The framework build uses `formatter-maven-plugin` with the Google Java Style configuration. Apply formatting from the framework repository root:

```bash
mvn formatter:format
```

Checkstyle is bound to Maven's `validate` phase and uses `google_checks.xml`:

```bash
mvn validate
```

The framework contribution policy requires Java changes to pass SonarLint. Run [SonarQube for IDE](https://www.sonarsource.com/products/sonarlint/) in your editor and resolve its findings before review. The repository provides connected-mode settings for VS Code. SonarLint supplies editor feedback, while Checkstyle and the Maven build are the repeatable command-line checks.

## Documentation setup {#documentation-setup}

Install the locked Docusaurus dependencies from the documentation repository's `docs` directory:

```bash
cd docs
npm ci
```

### Preview the docs site {#preview-the-docs-site}

Start the Docusaurus development server from the `docs` directory:

```bash
cd docs
npm run start
```

When you edit examples that render inside `ComponentDemo`, run the sample app from the documentation repository root in another terminal:

```bash
mvn jetty:run
```

The Docusaurus server uses port `3000`, and local demo frames load the sample app from port `8080`. If port `8080` is unavailable, pass the same alternative port to Maven with `-Dport=<port>` and to Docusaurus with `WEBFORJ_PORT`.

### Documentation Java formatting {#documentation-java-formatting}

Java sample code uses Google Java Format through Spotless. Check formatting from the documentation repository root:

```bash
mvn spotless:check
```

Apply the required formatting with:

```bash
mvn spotless:apply
```

Don't use the framework's `formatter-maven-plugin` command in the documentation repository. The two repositories have separate Maven formatting configurations.

## Prose style {#prose-style}

Markdown and Markdown with JSX content should be direct, accurate, and consistent with nearby pages. Vale runs in documentation pull request CI and uses the configuration at `.vale.ini`.

Run Vale against each changed prose file from the documentation repository root:

```bash
vale docs/docs/contributing/overview.md
```

Resolve Vale alerts before opening a pull request.
