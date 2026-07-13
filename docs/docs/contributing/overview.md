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

Contributions help keep webforJ accurate, practical, and easier to use. You can help by reporting unclear documentation, proposing new topics, improving examples, adding tests, or contributing framework code.

Start by choosing the right place for the work. This keeps issues and pull requests visible to the maintainers who can review them.

| Contribution | Where to start |
| --- | --- |
| Documentation fixes, tutorial updates, cookbook recipes, sample apps, and docs site issues | [webforJ documentation repository](https://github.com/webforj/webforj-documentation) |
| Framework bugs, API changes, core components, and runtime behavior | [webforJ framework repository](https://github.com/webforj/webforj) |
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

### Submit a change {#submit-a-change}

Small corrections can go directly to a pull request. Open an issue first for new sections, significant rewrites, demos, or behavior changes so maintainers can confirm the scope.

The [pull request guide](./pull-requests) covers forks, branches, descriptions, validation, and review. Framework contributors should also review the [framework contribution guide](https://github.com/webforj/webforj/blob/main/CONTRIBUTING.md) for code-specific expectations.

## Before opening a pull request {#before-opening-a-pull-request}

Make sure the change has a clear purpose, is limited to one topic, and can be reviewed without unrelated cleanup. If the pull request closes an issue, include `Closes #<issue-number>` in the description.

For documentation-only edits, build the English docs site before submitting. For Java samples, demos, or integration test changes, run the relevant Maven validation as well.

## Contributor guides {#contributor-guides}

<DocCardList className="topics-section" />
