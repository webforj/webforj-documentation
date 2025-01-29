---
sidebar_position: 1
title: Testing
hide_table_of_contents: true
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# webforJ Testing

Testing in webforJ apps involves a combination of unit and end-to-end (E2E) testing to ensure a stable and reliable app. Each type of testing serves a distinct purpose in maintaining app quality.

## Unit testing

Unit testing focuses on verifying individual components or backend logic in isolation. By following standard Java testing practices, such as using [JUnit](https://junit.org/junit5/), developers can efficiently validate specific app logic and ensure that each "unit" performs as expected.

## End-to-End (E2E) testing

End-to-end testing is important for validating the user experience in webforJ apps, which generate dynamic, single-page web interfaces. These tests simulate user interactions and verify the features of the entire app.

Using tools like [**Selenium**](https://www.selenium.dev/) and [**Playwright**](https://playwright.dev/java/docs/intro), you can:

- Automate browser interactions, such as button clicks and form submissions.
- Verify consistent rendering and interactivity of dynamic UI components.
- Ensure behavior consistency across different browsers and devices.

## Combining testing strategies

By combining unit and E2E testing:

1. **Isolate Issues**: Detect and resolve component-level bugs early with unit testing.
2. **Ensure Reliability**: Validate complete user journeys and system integrations with E2E testing.

## Topics

<DocCardList className="topics-section" />