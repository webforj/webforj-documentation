---
sidebar_position: 1
title: Contributing
description: Report issues, propose improvements, and contribute documentation, demos, tests, and framework changes to webforJ.
hide_table_of_contents: true
hide_giscus_comments: true
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Contributions help keep webforJ reliable, accurate, and easier to use. You can report incorrect behavior, implement framework features, add tests, improve examples, or clarify the documentation.

Start by choosing the right place for the work. This keeps issues and pull requests visible to the maintainers who can review them.

| Contribution | Where to start |
| --- | --- |
| Framework bugs, API changes, components, integrations, and runtime behavior | [webforJ framework repository](https://github.com/webforj/webforj) and the [framework contribution guide](./framework) |
| Documentation fixes, tutorial updates, cookbook recipes, sample apps, and docs site issues | [webforJ documentation repository](https://github.com/webforj/webforj-documentation) and the [documentation contribution guide](./documentation) |
| Security reports | Use the security advisories for the affected [framework](https://github.com/webforj/webforj/security/advisories) or [documentation](https://github.com/webforj/webforj-documentation/security/advisories) repository instead of a public issue |

## Ways to contribute {#ways-to-contribute}

### Report an issue {#report-an-issue}

Search existing issues before opening a new one. A useful report includes:

- The affected webforJ version
- Expected and actual behavior
- A minimal sample that reproduces the problem
- Relevant logs, stack traces, screenshots, or recordings
- The documentation URL when the report concerns a page

Use the [documentation issue chooser](https://github.com/webforj/webforj-documentation/issues/new/choose) for docs and sample problems. Use the [framework issue chooser](https://github.com/webforj/webforj/issues/new/choose) for API, component, and runtime problems.

### Propose an improvement {#propose-an-improvement}

Describe the problem or use case before describing a solution. Include the people affected, the desired outcome, and alternatives you considered. Use the [documentation proposal form](https://github.com/webforj/webforj-documentation/issues/new?template=feature_request.yml) for new guides or site changes, and the [framework issue chooser](https://github.com/webforj/webforj/issues/new/choose) for product features.

For a focused how-to topic, use the [cookbook request form](https://github.com/webforj/webforj-documentation/issues/new?template=cookbook-request.yml).

### Contribute to the framework {#contribute-to-the-framework}

Framework contributions include bug fixes, components, data APIs, integrations, build plugins, and tests. Start with the [framework contribution guide](./framework) to set up the Java 21 multi-module Maven build, locate the module that owns the behavior, and keep public API documentation and tests with the implementation.

### Improve the documentation {#improve-the-documentation}

Documentation contributions include articles, cookbook recipes, runnable samples, assets, navigation, and site tooling. The [documentation contribution guide](./documentation) explains the repository structure and the requirements for pages and demos.

Small corrections can go directly to a pull request. Open an issue first for new sections, significant rewrites, demos, or framework behavior changes so maintainers can confirm the scope. The [pull request guide](./pull-requests) covers the shared fork, branch, commit, review, and validation workflow for both repositories.

## Before opening a pull request {#before-opening-a-pull-request}

Make sure the change has a clear purpose, is limited to one topic, and can be reviewed without unrelated cleanup. If the pull request closes an issue, include `Closes #<issue-number>` in the description.

For framework code, run the affected module tests and the full Maven build when practical. For documentation-only edits, build the English docs site. Java samples, demos, and integration test changes also require the relevant Maven validation.

## Contributor guides {#contributor-guides}

<DocCardList className="topics-section" />
