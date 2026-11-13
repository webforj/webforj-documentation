---
sidebar_position: 2
title: Tests avec Playwright
description: >-
  Drive a webforJ app from JUnit using the Playwright Java bindings to fill
  fields, click buttons, and assert rendered output in the browser.
_i18n_hash: 3f815af0ad7916fdd5a53d6178846c6d
---
Cette documentation décrit le processus de test des applications webforJ avec Playwright, en se concentrant sur `HelloWorldView` de `webforj-archetype-hello-world`.

:::info Notions de base de l’application
Pour en savoir plus sur `webforj-archetype-hello-world`, consultez la section [Introduction aux notions de base de l’application](../../introduction/basics).
:::

## Prérequis {#prerequisites}

Avant d’écrire et d’exécuter les tests Playwright, vérifiez que :
- L’application webforJ est correctement configurée et s’exécute sur votre serveur local.
- Vous avez installé :
  - Les liaisons Java de Playwright.
  - Un navigateur compatible (Playwright peut installer automatiquement les navigateurs lors de la configuration).
  - Maven pour gérer les dépendances du projet.

## Configuration de Maven {#maven-configuration}

Ajoutez les dépendances nécessaires à Playwright dans votre fichier `pom.xml` :

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

## Exemple de test : `HelloWorldView` {#testing-example-helloworldview}

Le code suivant présente un test basé sur Playwright pour le composant `HelloWorldView`.

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

### Étapes clés {#key-steps}

1. **Initialiser Playwright** :
   - Créez une instance de `Playwright`.
   - Lancez une instance de navigateur avec `playwright.chromium().launch()`.

2. **Configurer l’environnement de test** :
   - Ouvrez une nouvelle page du navigateur avec `browser.newPage()`.
   - Accédez à la page `HelloWorldView` à l’aide de la méthode `navigate`.

3. **Interagir avec les éléments** :
   - Utilisez les [locateurs de Playwright](https://playwright.dev/java/docs/api/class-locator) pour interagir avec les éléments du DOM.
   - Remplissez les champs de saisie avec `locator("input").fill()` et déclenchez des actions avec `getByText("Say Hello").click()`.

4. **Assertions** :
   - Vérifiez le message toast affiché avec `PlaywrightAssertions.assertThat()`.

5. **Nettoyage** :
   - Playwright s’occupe automatiquement du nettoyage du navigateur à la fin du test. Pour le fermer manuellement, vous pouvez utiliser `browser.close()`.

### Exécution des tests {#running-tests}

1. Démarrez le serveur webforJ :
   ```bash
   mvn
   ```

2. Exécutez les cas de test :
   ```bash
   mvn test
   ```

## Comportement attendu {#expected-behavior}

- Lorsque vous accédez à `http://localhost:<port>/`, la page `HelloWorldView` s’affiche.
- Saisissez webforJ dans le champ de texte, puis cliquez sur le bouton `Say Hello`.
- Un message toast doit apparaître avec le texte : `Welcome to webforJ Starter webforJ!`.
