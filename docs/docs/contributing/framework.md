---
sidebar_position: 2
title: Framework Contributions
description: Set up the webforJ framework repository, choose the correct module, implement Java changes, and prepare them for review.
hide_giscus_comments: true
---

Framework contributions are made in the [webforJ repository](https://github.com/webforj/webforj). This repository contains the Java APIs, components, runtime foundations, integrations, and build plugins that applications use. Read the repository's [contribution policy](https://github.com/webforj/webforj/blob/main/CONTRIBUTING.md) alongside this guide for its issue, code-quality, commit, and review requirements.

## Before you start {#before-you-start}

Search the [framework issues](https://github.com/webforj/webforj/issues) before starting work. Open an issue before making a significant behavior or API change so maintainers can confirm the problem, scope, and intended design. Use the [bug report](https://github.com/webforj/webforj/issues/new?template=bug_report.yml) for incorrect behavior and the [feature request](https://github.com/webforj/webforj/issues/new?template=feature_request.yml) for a new capability.

Security vulnerabilities must be reported through the [private security advisory form](https://github.com/webforj/webforj/security/advisories/new), not a public issue or pull request. All participation is subject to the repository's [Code of Conduct](https://github.com/webforj/webforj/blob/main/CODE_OF_CONDUCT.md).

## Set up the repository {#set-up-the-repository}

Framework development requires:

| Tool | Requirement |
| --- | --- |
| Java | JDK 21 |
| Maven | A current Maven installation available as `mvn` |
| Git | A version that supports `git switch` |

Follow the [pull request guide](./pull-requests#fork-and-clone-the-repository) to clone your fork and configure the upstream remote. From the framework repository root, confirm the toolchain and run a small module build:

```bash
java -version
mvn -version
mvn -pl webforj-foundation -am test
```

The framework is a multi-module Maven project. The `-pl` option selects a module, and `-am` also builds the modules it depends on.

## Choose the module {#choose-the-module}

Make the change in the narrowest module that owns the behavior. Common module groups include:

| Path | Responsibility |
| --- | --- |
| `webforj-foundation` | Base framework behavior, communication, and shared component APIs |
| `webforj-components/*` | Individual UI component modules |
| `webforj-data` | Data APIs |
| `webforj-dispatcher` | Event dispatching |
| `webforj-bundle/*` | Frontend resource bundling |
| `webforj-spring/*` | Spring integration, starter, and development tools |
| `webforj-plugins/*` | Maven and Gradle plugins, installation, and minification |
| `webforj-kotlin` | Kotlin support |
| `webforj-rewrite` | OpenRewrite migration recipes |
| `webforj-webswing` | Webswing integration |

Read the module's `pom.xml`, nearby implementation classes, and existing tests before editing. Follow the established package and API patterns in that module instead of introducing a repository-wide abstraction for a local change.

## Implement the change {#implement-the-change}

Keep production code, tests, and API documentation in the same focused change:

- Preserve existing public behavior unless the linked issue explicitly approves a change.
- Match nearby naming, overload, null-handling, and exception conventions.
- Update Javadoc when a public type, method, parameter, return value, or exception contract changes.
- Add a JUnit 5 test under the module's `src/test/java` tree for new behavior and bug fixes.
- Avoid unrelated formatting, dependency, or refactoring changes.
- Explain any new dependency in the pull request. Framework CI performs dependency review for pull requests.

Run the framework formatter, Checkstyle, and affected tests as described in [Editor Settings and Local Setup](./editor-settings#framework-formatting-and-analysis) and [Creating Tests](./creating-tests#framework-tests).

## Keep documentation in sync {#keep-documentation-in-sync}

A framework change that affects users may also require a change in the [documentation repository](https://github.com/webforj/webforj-documentation). This includes new APIs, changed defaults, renamed methods, migration requirements, and behavior that changes an existing example.

Because framework and documentation changes use separate repositories, open a companion documentation issue or pull request and cross-link it from the framework pull request. Don't leave a user-facing API change documented only in Javadoc or a pull request description.

## Framework checklist {#framework-checklist}

Before opening the pull request:

- The issue and implementation agree on scope and behavior.
- The change is in the module that owns the behavior.
- Public API documentation is accurate.
- New behavior and bug fixes have focused JUnit 5 coverage.
- Formatting, Checkstyle, and affected module tests pass.
- The full `mvn install` build passes when practical.
- A related documentation issue or pull request is linked when needed.
