---
sidebar_position: 3
title: Testing mit Selenium
_i18n_hash: fe85942b4638ef9828b334ef986b4436
---
Diese Dokumentation beschreibt den Prozess zum Testen von webforJ-Anwendungen mit Selenium, insbesondere fokussiert auf die `HelloWorldView` aus der `webforj-archetype-hello-world`.

:::info App Grundlagen
Um mehr über die `webforj-archetype-hello-world` zu erfahren, lesen Sie den Abschnitt [Einführung in die App-Grundlagen](../../introduction/basics).
:::

## Voraussetzungen {#prerequisites}

Bevor Sie die Selenium-Tests ausführen, stellen Sie Folgendes sicher:
- Die webforJ-App ist korrekt eingerichtet und läuft auf Ihrem lokalen Server.
- Sie haben Folgendes installiert:
  - Selenium Java-Bindings.
  - Einen kompatiblen WebDriver für Ihren Browser.
  - Maven für Projektabhängigkeiten.

## Maven-Konfiguration {#maven-configuration}

Fügen Sie die erforderlichen Abhängigkeiten in Ihrer `pom.xml` für Selenium und andere Testbibliotheken hinzu:

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

Der folgende Code demonstriert einen Selenium-basierten Test für die `HelloWorldView`-Komponente.

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
   - Lokalisieren Sie Elemente mit `By.tagName`, `By.id` oder anderen Selenium-Selektoren.
   - Überprüfen Sie erwartete Verhaltensweisen wie das Klicken auf Schaltflächen oder Textänderungen.

  :::info
  Da webforJ eine einseitige Webanwendung produziert, ist Selenium sich nach dem Laden der ursprünglichen Seite nicht über DOM-Manipulationen bewusst. Sie können die [WebDriverWait API](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html) von Selenium verwenden, um zu warten, bis das DOM kompiliert ist.
  :::

4. **Bereinigung**:
   - Beenden Sie die WebDriver-Sitzung, um Ressourcen freizugeben.

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

- Beim Besuch von `http://localhost:<port>/` wird die `HelloWorldView`-Seite geladen.
- Das Element `dwc-button` mit dem Text `Say Hello` sollte vorhanden sein.
