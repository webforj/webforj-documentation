---
sidebar_position: 3
title: Testing with Selenium
_i18n_hash: 5d2e4b04f794236d9a8ea2a32d50579b
---
Cette documentation décrit le processus de test des applications webforJ à l'aide de Selenium, en se concentrant spécifiquement sur le `HelloWorldView` du `webforj-archetype-hello-world`.

:::info Bases de l'application
Pour en savoir plus sur le `webforj-archetype-hello-world`, consultez la section [Introduction aux bases de l'application](../../introduction/basics).
:::

## Prérequis {#prerequisites}

Avant d'exécuter les tests Selenium, assurez-vous de ce qui suit :
- L'application webforJ est correctement configurée et fonctionne sur votre serveur local.
- Vous avez installé :
  - Les bindings Java pour Selenium.
  - Un WebDriver compatible avec votre navigateur.
  - Maven pour les dépendances du projet.

## Configuration Maven {#maven-configuration}

Ajoutez les dépendances nécessaires dans votre `pom.xml` pour Selenium et les autres bibliothèques de test :

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

Le code suivant démontre un test basé sur Selenium pour le composant `HelloWorldView`.

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
    assertEquals("Say Hello", button.getText(), "Le texte du bouton ne correspond pas !");
  }
}
```

### Étapes clés {#key-steps}

1. **Initialiser WebDriver** :
   - Utilisez [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager) pour gérer automatiquement l'exécutable du driver pour le navigateur.

2. **Configurer l'environnement de test** :
   - Démarrez le serveur de test sur `http://localhost:<port>/`.
   - Attendez que le titre de la page corresponde à `webforJ Hello World`.

3. **Interagir avec les éléments** :
   - Localisez les éléments à l'aide de `By.tagName`, `By.id` ou d'autres localisateurs Selenium.
   - Vérifiez les comportements attendus comme les clics sur les boutons ou les changements de texte.
    
  :::info
  Étant donné que webforJ produit une application web à page unique, Selenium n'est pas conscient de la manipulation du DOM après le chargement initial de la page. Vous pouvez utiliser l'[API WebDriverWait de Selenium](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html) pour attendre que le DOM ait été compilé.
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
