---
sidebar_position: 3
title: Testen mit Selenium
description: >-
  Drive a webforJ app from JUnit with Selenium WebDriver and WebDriverManager to
  simulate user interactions and assert UI state.
_i18n_hash: 482f683ba0db2228dfbfb9fdbea794e7
---
Diese Dokumentation beschreibt den Prozess zum Testen einer webforJ-App mit Selenium. Im Mittelpunkt steht dabei `HelloWorldView` aus dem `webforj-archetype-hello-world`.

:::info Grundlagen der App
Weitere Informationen zu `webforj-archetype-hello-world` finden Sie im Abschnitt [Einführung in die App-Grundlagen](../../introduction/basics).
:::

## Voraussetzungen {#prerequisites}

Stellen Sie vor dem Ausführen der Selenium-Tests sicher, dass:
- die webforJ-App korrekt eingerichtet ist und auf Ihrem lokalen Server ausgeführt wird.
- Sie Folgendes installiert haben:
  - Selenium-Java-Bindings.
  - Einen mit Ihrem Browser kompatiblen WebDriver.
  - Maven für Projektabhängigkeiten.

## Maven-Konfiguration {#maven-configuration}

Fügen Sie die erforderlichen Abhängigkeiten für Selenium und andere Testbibliotheken in Ihre `pom.xml` ein:

```xml title="pom.xml"
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

## Testbeispiel: `HelloWorldView` {#testing-example-helloworldview}

Der folgende Code zeigt einen Selenium-basierten Test für die Komponente `HelloWorldView`.

```java title="HelloWorldViewTest.java"
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

### Wichtige Schritte {#key-steps}

1. **WebDriver initialisieren**:
   - Verwenden Sie [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager), um die ausführbare Treiberdatei für den Browser automatisch zu verwalten.

2. **Testumgebung einrichten**:
   - Starten Sie den Testserver unter `http://localhost:<port>/`.
   - Warten Sie, bis der Seitentitel mit dem erwarteten `webforJ Hello World` übereinstimmt.

3. **Mit Elementen interagieren**:
   - Suchen Sie Elemente mit `By.tagName`, `By.id` oder anderen Selenium-Locators.
   - Überprüfen Sie das erwartete Verhalten, etwa das Klicken auf Schaltflächen oder Textänderungen.

  :::info
  Da webforJ eine Single-Page-Web-App erzeugt, erkennt Selenium keine DOM-Manipulationen nach dem Laden der ursprünglichen Seite. Mit der [WebDriverWait API](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html) von Selenium können Sie warten, bis das DOM kompiliert wurde.
  :::

4. **Aufräumen**:
   - Beenden Sie die WebDriver-Sitzung, um Ressourcen freizugeben.

### Tests ausführen {#running-tests}

1. Starten Sie den webforJ-Server:
   ```bash
   mvn
   ```

2. Führen Sie die Testfälle aus:
   ```bash
   mvn test
   ```

## Erwartetes Verhalten {#expected-behavior}

- Beim Aufrufen von `http://localhost:<port>/` wird die Seite `HelloWorldView` geladen.
- Das Element `dwc-button` mit dem Text `Say Hello` sollte vorhanden sein.
