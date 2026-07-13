---
sidebar_position: 5
title: Creating Tests
description: Add and run tests for webforJ framework code, documentation demos, frontend resources, and integration scenarios.
hide_giscus_comments: true
---

Tests should match the risk of the contribution and run in the repository that owns the behavior. Documentation-only wording changes usually need a docs build. Framework behavior, demos, samples, and frontend logic need tests that exercise the changed code path.

## What to test {#what-to-test}

| Change type | Expected validation |
| --- | --- |
| Framework behavior or public API | Add or update JUnit 5 tests in the affected module; run the module build |
| Framework bug fix | Add a regression test that fails without the fix; run the full Maven build when practical |
| Markdown or Markdown with JSX content only | Build the docs site |
| Navigation, sidebar, redirects, or Docusaurus config | Build the docs site and verify affected links |
| Cookbook recipe changes | Run the cookbook validation scripts |
| Java demo or sample changes | Run Maven checks and add or update integration tests when behavior changes |
| Frontend assets or bundler examples | Add or update frontend tests when logic changes |

## Framework tests {#framework-tests}

The framework uses JUnit 5, with Mockito available where test doubles are needed. Put tests under the affected module's `src/test/java` directory, use the same package as the production code, and follow nearby naming and structure.

From the framework repository root, run the affected module and the modules it depends on. For example:

```bash
mvn -pl webforj-components/webforj-accordion -am test
```

To select one test while building required modules, use Surefire's test filter and allow dependency modules without a matching test:

```bash
mvn -pl webforj-components/webforj-accordion -am \
  -Dtest=AccordionTest \
  -Dsurefire.failIfNoSpecifiedTests=false test
```

Run the same full build used by framework pull request CI before submitting a broad or shared change:

```bash
mvn install
```

Framework CI runs this build on Java 21, reports JaCoCo coverage, and compares results with thresholds of 40% overall coverage and 60% coverage for changed files. Coverage percentage doesn't replace a meaningful assertion. Test the public contract, important edge cases, and the reported failure for a bug fix.

## Documentation builds {#documentation-builds}

Run the docs build from the documentation repository's `docs` directory:

```bash
cd docs
npm run build -- --locale en
```

This catches broken links, invalid Markdown with JSX, missing imports, and Docusaurus configuration problems. Run the full `npm run build` only when translation content or shared locale configuration changes.

## Cookbook checks {#cookbook-checks}

When you edit cookbook recipes, run the validation scripts from the documentation repository's `docs` directory:

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

## Documentation sample and integration tests {#documentation-sample-and-integration-tests}

Run the full Maven verification from the documentation repository root when Java samples, demo routes, or integration tests change:

```bash
mvn -Pen-only verify
```

To run a single integration test, pass the Failsafe test name:

```bash
mvn -Pen-only -Dit.test=TableSortingViewIT verify
```

Add or update integration tests when a sample demonstrates interactive behavior, navigation, validation, dynamic rendering, or a previously broken path.

For more details, see [Testing](/docs/testing/overview), [Frontend testing](/docs/testing/frontend-testing), and [End-to-End Testing](/docs/testing/e2e/overview).

## When local validation isn't practical {#when-local-validation-isnt-practical}

If local validation is too slow, flaky, or blocked by your environment, submit the best focused change you can. In the pull request description, list what you didn't run and the reason.
