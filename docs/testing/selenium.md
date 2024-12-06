---
sidebar_position: 3
title: Testing with Selenium
---

This documentation outlines the process for testing a webforJ apps using Selenium, specifically focusing on the `HelloWorldView` from
the `webforj-archetype-hello-world`.

## Prerequisites

Before running the Selenium tests, ensure the following:
- webforJ app is correctly set up and running on your local server.
- You have installed:
  - Selenium Java bindings.
  - A compatible WebDriver for your browser (e.g., `ChromeDriver` or `GeckoDriver`).
  - Maven for project dependencies.

## Maven configuration

Add the necessary dependencies in your `pom.xml` for Selenium and other testing libraries:

```xml
<dependencies>
  <dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.27.0</version>
  </dependency>
  <dependency>
    <groupId>io.github.bonigarcia</groupId>
    <artifactId>webdrivermanager</artifactId>
    <version>5.9.2</version>
  </dependency>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <scope>test</scope>
  </dependency>
</dependencies>
```

## Testing example: `HelloWorldView`

The following code demonstrates a Selenium-based test for the `HelloWorldView` component.

```java
package com.example.views;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import io.github.bonigarcia.wdm.WebDriverManager;

class HelloWorldViewTest {

  private WebDriver driver;
  private static final String PORT = System.getProperty("server.port", "8080");

  @BeforeAll
  static void setupAll() {
    WebDriverManager.chromedriver().setup();
  }

  @BeforeEach
  void setup() {
    driver = new ChromeDriver();
    driver.get("http://localhost:" + PORT + "/");
    new WebDriverWait(driver, ofSeconds(30))
            .until(titleIs("webforJ Hello World"));
  }

  @AfterEach
  void teardown() {
    if (driver != null) {
        driver.quit();
    }
  }

  @Test
  void shouldClickButton() {
    WebElement button = driver.findElement(By.tagName("dwc-button"));
    assertEquals("Say Hello", button.getText(), "Button text mismatch!");
  }
}
```

### Key steps

1. **Initialize WebDriver**:
   - Use [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager) to automatically manage the driver executable for the browser.

2. **Set Up Test Environment**:
   - Start the test server on `http://localhost:<port>/`.
   - Wait until the page title matches the expected `webforJ Hello World`.

3. **Interact with Elements**:
   - Locate elements using `By.tagName`, `By.id`, or other Selenium locators.
   - Verify expected behaviors like button clicks or text changes.

    :::info
    Because webforJ produces a single-page web app, Selenium isn’t aware of DOM manipulation after the initial page has been loaded. You can use Selenium’s WebDriverWait API to wait until the DOM has been compiled.
    :::

4. **Teardown**:
   - Quit the WebDriver session to release resources.

### Running tests

1. Start the webforJ server:
   ```bash
   mvn jetty:run
   ```

2. Execute the test cases:
   ```bash
   mvn test
   ```

## Expected behavior

- On visiting `http://localhost:<port>/`, the `HelloWorldView` page loads.
- The `dwc-button` element with the text `Say Hello` should be present.