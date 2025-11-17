---
sidebar_position: 3
title: Testing avec Selenium
_i18n_hash: fe85942b4638ef9828b334ef986b4436
---
Cette documentation décrit le processus de test des applications webforJ à l'aide de Selenium, en se concentrant spécifiquement sur le `HelloWorldView` de
l'`webforj-archetype-hello-world`.

:::info App Basics
Pour en savoir plus sur l'`webforj-archetype-hello-world`, consultez la section [Introduction aux concepts de base](../../introduction/basics).
:::

## Prérequis {#prerequisites}

Avant d'exécuter les tests Selenium, assurez-vous des éléments suivants :
- L'application webforJ est correctement configurée et en cours d'exécution sur votre serveur local.
- Vous avez installé :
  - Les bindings Java de Selenium.
  - Un WebDriver compatible pour votre navigateur.
  - Maven pour les dépendances du projet.

## Configuration Maven {#maven-configuration}

Ajoutez les dépendances nécessaires dans votre `pom.xml` pour Selenium et d'autres bibliothèques de test :

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

Le code suivant montre un test basé sur Selenium pour le composant `HelloWorldView`.

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
    assertEquals("Say Hello", button.getText(), "Mismatch du texte du bouton !");
  }
}
```

### Étapes clés {#key-steps}

1. **Initialiser WebDriver** :
   - Utilisez [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager) pour gérer automatiquement l'exécutable du driver pour le navigateur.

2. **Configurer l'environnement de test** :
   - Démarrez le serveur de test sur `http://localhost:<port>/`.
   - Attendez que le titre de la page corresponde au `webforJ Hello World` attendu.

3. **Interagir avec les éléments** :
   - Localisez les éléments en utilisant `By.tagName`, `By.id` ou d'autres localisateurs Selenium.
   - Vérifiez les comportements attendus, tels que les clics sur les boutons ou les changements de texte.

  :::info
  Étant donné que webforJ produit une application web à une seule page, Selenium n'est pas conscient de la manipulation du DOM après le chargement initial de la page. Vous pouvez utiliser l'[API WebDriverWait de Selenium](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html) pour attendre que le DOM soit compilé.
  :::

4. **Teardown** :
   - Quittez la session WebDriver pour libérer les ressources.

### Exécution des tests {#running-tests}

1. Démarrez le serveur webforJ :
   ```bash
   mvn jetty:run
   ```

2. Exécutez les cas de test :
   ```bash
   mvn test
   ```

## Comportement attendu {#expected-behavior}

- En visitant `http://localhost:<port>/`, la page `HelloWorldView` se charge.
- L'élément `dwc-button` avec le texte `Say Hello` doit être présent.
