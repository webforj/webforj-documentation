---
sidebar_position: 1
title: Testing
description: Combine JUnit unit tests with Selenium or Playwright end-to-end tests to validate webforJ components, logic, and full user journeys.
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

# webforJ testing

Testing in webforJ apps combines unit, frontend, and end-to-end (E2E) testing, each serving a distinct purpose in keeping the app stable and reliable.

## Unit testing {#unit-testing}

Unit testing focuses on verifying individual components or backend logic in isolation. By following standard Java testing practices, such as using [JUnit](https://junit.org/junit5/), developers can validate specific app logic and check that each "unit" performs as expected.

For views and components, the [TestBed](./testbed/overview) does browserless testing in plain JUnit tests without a browser or a server. A test adds a view to the page, acts on it the way a user does, and checks the result from Java.

## Frontend testing {#frontend-testing}

Frontend testing covers the sources a project authors with the [frontend bundler](/docs/managing-resources/bundler/overview). The [Bun](https://bun.sh/) test runner runs them as part of the same build that runs the Java tests, so a TypeScript component or a piece of client logic is verified the same way the backend is. See [Frontend testing](./frontend-testing).

## End-to-End (E2E) testing {#end-to-end-e2e-testing}

End-to-end testing validates what users experience in webforJ apps, which generate dynamic, single-page web interfaces. These tests simulate user interactions and verify the features of the entire app.

Using tools like [**Selenium**](https://www.selenium.dev/) and [**Playwright**](https://playwright.dev/java/docs/intro), you can:

- Automate browser interactions by simulating button clicks and form submissions.
- Verify consistent rendering and interactivity of dynamic UI components.
- Confirm that behavior stays consistent across browsers and devices.

## Combining testing strategies {#combining-testing-strategies}

By combining unit and E2E testing:

1. **Isolate issues**: detect and resolve component-level bugs early with unit testing.
2. **Confirm reliability**: validate complete user journeys and system integrations with E2E testing.

## Topics {#topics}

<DocCardList className="topics-section" />