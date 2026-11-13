---
sidebar_position: 3
title: Tester avec Selenium
description: >-
  Drive a webforJ app from JUnit with Selenium WebDriver and WebDriverManager to
  simulate user interactions and assert UI state.
_i18n_hash: 482f683ba0db2228dfbfb9fdbea794e7
---
Cette documentation décrit le processus de test d’applications webforJ avec Selenium, en se concentrant plus particulièrement sur `HelloWorldView` de
`webforj-archetype-hello-world`.

:::info App Basics
Pour en savoir plus sur `webforj-archetype-hello-world`, consultez la section [Introduction aux bases de l’application](../../introduction/basics).
:::

## Prérequis {#prerequisites}

Avant d’exécuter les tests Selenium, assurez-vous que :
- L’application webforJ est correctement configurée et fonctionne sur votre serveur local.
- Vous avez installé :
  - Les liaisons Java de Selenium.
  - Un WebDriver compatible avec votre navigateur.
  - Maven pour les dépendances du projet.

## Configuration de Maven {#maven-configuration}

Ajoutez les dépendances nécessaires à Selenium et aux autres bibliothèques de test dans votre `pom.xml` :

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

## Exemple de test : `HelloWorldView` {#testing-example-helloworldview}

Le code suivant présente un test basé sur Selenium pour le composant `HelloWorldView`.

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

### Étapes clés {#key-steps}

1. **Initialiser WebDriver** :
   - Utilisez [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager) pour gérer automatiquement l’exécutable du pilote de navigateur.

2. **Configurer l’environnement de test** :
   - Démarrez le serveur de test à l’adresse `http://localhost:<port>/`.
   - Attendez que le titre de la page corresponde à `webforJ Hello World`.

3. **Interagir avec les éléments** :
   - Localisez les éléments à l’aide de `By.tagName`, `By.id` ou d’autres localisateurs Selenium.
   - Vérifiez les comportements attendus, comme les clics sur les boutons ou les changements de texte.

  :::info
  Comme webforJ produit une application web monopage, Selenium ne détecte pas les manipulations du DOM après le chargement initial de la page. Vous pouvez utiliser l’[API WebDriverWait](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html) de Selenium pour attendre que le DOM soit compilé.
  :::

4. **Nettoyage** :
   - Fermez la session WebDriver pour libérer les ressources.

### Exécuter les tests {#running-tests}

1. Démarrez le serveur webforJ :
   ```bash
   mvn
   ```

2. Exécutez les cas de test :
   ```bash
   mvn test
   ```

## Comportement attendu {#expected-behavior}

- Lors de l’accès à `http://localhost:<port>/`, la page `HelloWorldView` se charge.
- L’élément `dwc-button` contenant le texte `Say Hello` doit être présent.
