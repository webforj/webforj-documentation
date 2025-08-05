---
sidebar_position: 2
title: Testing with Playwright
---

This documentation outlines the process for testing webforJ applications using Playwright, specifically focusing on the `HelloWorldView` from the `webforj-archetype-hello-world`.

:::info App Basics
To learn more about the `webforj-archetype-hello-world`, refer to the [App Basics Introduction](../../introduction/basics) section.
:::

## Prerequisites {#prerequisites}

Before writing and running the Playwright tests, ensure the following:
- The webforJ app is correctly set up and running on your local server.
- You have installed:
  - Playwright Java bindings.
  - A compatible browser (Playwright can automatically install browsers during setup).
  - Maven for project dependencies.

## Maven configuration {#maven-configuration}

Add the necessary dependencies in your `pom.xml` for Playwright:

```xml title="pom.xml"
<dependencies>
  <dependency>
    <groupId>com.microsoft.playwright</groupId>
    <artifactId>playwright</artifactId>
    <version>1.49.0</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <scope>test</scope>
  </dependency>
</dependencies>
```

## Testing example: `HelloWorldView` {#testing-example-helloworldview}

The following code demonstrates a Playwright based test for the `HelloWorldView` component.

```java title="HelloWorldViewTest.java"
package com.example.views;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

class HelloWorldViewTest {

  static Playwright playwright = Playwright.create();
  Browser browser;
  Page page;
  String port = System.getProperty("server.port", "8080");

  @BeforeEach
  void setUp() {
    browser = playwright.chromium().launch(); 
    page = browser.newPage();
    page.navigate("http://localhost:" + port + "/");
  }

  @Test
  void shouldClickButton() {
    page.locator("input").fill("webforJ");
    page.getByText("Say Hello").click();

    assertThat(page.locator("dwc-toast").first())
        .containsText("Welcome to webforJ Starter webforJ!");
  }
}
```

### Key steps {#key-steps}

1. **Initialize Playwright**:
   - Create a `Playwright` instance.
   - Launch a browser instance using `playwright.chromium().launch()`.

2. **Set Up Test Environment**:
   - Open a new browser page with `browser.newPage()`.
   - Navigate to the `HelloWorldView` page using the `navigate` method.

3. **Interact with Elements**:
   - Use [Playwright's locators](https://playwright.dev/java/docs/api/class-locator) to interact with DOM elements.
   - Fill input fields using `locator("input").fill()` and trigger actions using `getByText("Say Hello").click()`.

4. **Assertions**:
   - Verify the displayed toast message with `PlaywrightAssertions.assertThat()`.

5. **Teardown**:
   - Playwright automatically handles browser cleanup when the test finishes. For manual cleanup, you can close the browser using `browser.close()`.

### Running tests {#running-tests}

1. Start the webforJ server:
   ```bash
   mvn jetty:run
   ```

2. Execute the test cases:
   ```bash
   mvn test
   ```

## Expected behavior {#expected-behavior}

- On visiting `http://localhost:<port>/`, the `HelloWorldView` page loads.
- Input webforJ into the text field and click the `Say Hello` button.
- A toast message should appear with the text: `Welcome to webforJ Starter webforJ!`.
