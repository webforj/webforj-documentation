---
sidebar_position: 3
title: Testing with Selenium
description: >-
  Drive a webforJ app from JUnit with Selenium WebDriver and WebDriverManager to
  simulate user interactions and assert UI state.
_i18n_hash: 865e29cc6d656fb60a0a3332a805c5f4
---
Diese Dokumentation beschreibt den Prozess zum Testen von webforJ-Anwendungen mit Selenium, wobei der Schwerpunkt auf der `HelloWorldView` aus dem `webforj-archetype-hello-world` liegt.

:::info Grundlagen der App
Um mehr über das `webforj-archetype-hello-world` zu erfahren, siehe den Abschnitt [Einführung in die Grundlagen der App](../../introduction/basics).
:::

## Voraussetzungen {#prerequisites}

Bevor Sie die Selenium-Tests ausführen, stellen Sie sicher, dass Folgendes erfüllt ist:
- Die webforJ-App ist korrekt eingerichtet und läuft auf Ihrem lokalen Server.
- Sie haben installiert:
  - Selenium Java-Bindings.
  - Ein kompatibler WebDriver für Ihren Browser.
  - Maven für Projektabhängigkeiten.

## Maven-Konfiguration {#maven-configuration}

Fügen Sie die erforderlichen Abhängigkeiten in Ihre `pom.xml` für Selenium und andere Testbibliotheken hinzu:

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

Der folgende Code demonstriert einen auf Selenium basierenden Test für die Komponente `HelloWorldView`.

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
    assertEquals("Say Hello", button.getText(), "Buttontext stimmt nicht überein!");
  }
}
```

### Wichtige Schritte {#key-steps}

1. **WebDriver initialisieren**:
   - Verwenden Sie [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager), um das Treiber-Executable für den Browser automatisch zu verwalten.

2. **Testumgebung einrichten**:
   - Starten Sie den Testserver unter `http://localhost:<port>/`.
   - Warten Sie, bis der Seitentitel dem erwarteten `webforJ Hello World` entspricht.

3. **Mit Elementen interagieren**:
   - Lokalisieren Sie Elemente mit `By.tagName`, `By.id` oder anderen Selenium-Lokatoren.
   - Überprüfen Sie erwartete Verhaltensweisen wie Button-Klicks oder Textänderungen.

  :::info
  Da webforJ eine Einzelseiten-Webanwendung produziert, ist Selenium sich nach dem Laden der Anfangsseite nicht über die DOM-Manipulation im Klaren. Sie können die [WebDriverWait-API von Selenium](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html) verwenden, um zu warten, bis das DOM kompiliert wurde.
  :::

4. **Teardown**:
   - Beenden Sie die WebDriver-Session, um Ressourcen freizugeben.

### Tests ausführen {#running-tests}

1. Starten Sie den webforJ-Server:
   ```bash
   mvn jetty:run
   ```

2. Führen Sie die Testfälle aus:
   ```bash
   mvn test
   ```

## Erwartetes Verhalten {#expected-behavior}

- Beim Besuch von `http://localhost:<port>/` wird die Seite `HelloWorldView` geladen.
- Das Element `dwc-button` mit dem Text `Say Hello` sollte vorhanden sein.
