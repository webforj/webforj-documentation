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


# Testing in webforJ

Testing in `webforJ` applications combines traditional Java unit testing with end-to-end (E2E) testing to ensure robust functionality and user experience.

## Unit testing

Unit tests in webforJ follow standard Java practices using frameworks like JUnit. These tests focus on backend logic and individual components, ensuring each unit functions as expected.

## End-to-End testing

Since `webforJ` generates dynamic, single-page web applications, end-to-end testing is essential for validating user interactions and UI behavior. Tools like Selenium and Playwright allow you to:
- Automate browser interactions (e.g., button clicks, form submissions).
- Verify the rendering and functionality of dynamic components.
- Ensure consistent behavior across different browsers.

By combining unit and E2E testing, you can build reliable, well-tested webforJ applications.

## Topics

<DocCardList className="topics-section" />