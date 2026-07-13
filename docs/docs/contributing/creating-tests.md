---
sidebar_position: 4
title: Creating Tests
description: Decide when to add tests for webforJ documentation, demos, frontend resources, and integration scenarios.
hide_giscus_comments: true
---

Tests should match the risk of the contribution. Documentation-only wording changes usually need a docs build. Demo, sample, frontend, or behavior changes need validation that exercises the code path.

## What to test {#what-to-test}

| Change type | Expected validation |
| --- | --- |
| Markdown or Markdown with JSX content only | Build the docs site |
| Navigation, sidebar, redirects, or Docusaurus config | Build the docs site and verify affected links |
| Cookbook recipe changes | Run the cookbook validation scripts |
| Java demo or sample changes | Run Maven checks and add or update integration tests when behavior changes |
| Frontend assets or bundler examples | Add or update frontend tests when logic changes |
| Bug fix | Add a regression test when the behavior can be tested locally |

## Documentation builds {#documentation-builds}

Run the docs build from the `docs` directory:

```bash
cd docs
npm run build -- --locale en
```

This catches broken links, invalid Markdown with JSX, missing imports, and Docusaurus configuration problems. Run the full `npm run build` only when translation content or shared locale configuration changes.

## Cookbook checks {#cookbook-checks}

When you edit cookbook recipes, run the relevant validation script from the `docs` directory:

```bash
cd docs
npm run validate:cookbook
npm run validate:cookbook-index
npm run validate:cookbook-snippets
```

Use the snippet report when you need more detail:

```bash
npm run review:cookbook-snippets
```

## Java sample and integration tests {#java-sample-and-integration-tests}

Run the full Maven verification from the repository root when Java samples, demo routes, or integration tests change:

```bash
mvn -Pen-only verify
```

To run a single integration test, pass the Failsafe test name:

```bash
mvn -Pen-only -Dit.test=TableSortingViewIT verify
```

Add or update integration tests when a sample demonstrates interactive behavior, navigation, validation, dynamic rendering, or a previously broken path.

For more details, see [Testing](/docs/testing/overview), [Frontend testing](/docs/testing/frontend-testing), and [End-to-End Testing](/docs/testing/e2e/overview).

## Framework tests {#framework-tests}

The Maven commands on this page apply to the documentation repository and its sample app. Framework changes belong in the [webforJ repository](https://github.com/webforj/webforj), which uses JUnit 5 tests. Run the affected module's tests and follow its [contribution guide](https://github.com/webforj/webforj/blob/main/CONTRIBUTING.md).

## When local validation isn't practical {#when-local-validation-isnt-practical}

If local validation is too slow, flaky, or blocked by your environment, submit the best focused change you can. In the pull request description, list what you didn't run and the reason.
