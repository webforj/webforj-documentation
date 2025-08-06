---
sidebar_position: 2
title: Testing with Playwright
_i18n_hash: 43cec2eab876a8dc170f4fb69aaa8214
---
Cette documentation décrit le processus de test des applications webforJ en utilisant Playwright, en se concentrant spécifiquement sur le `HelloWorldView` du `webforj-archetype-hello-world`.

:::info Principes de base de l'application
Pour en savoir plus sur le `webforj-archetype-hello-world`, consultez la section [Introduction aux Principes de base de l'application](../../introduction/basics).
:::

## Prérequis {#prerequisites}

Avant d'écrire et d'exécuter les tests Playwright, assurez-vous des éléments suivants :
- L'application webforJ est correctement configurée et fonctionne sur votre serveur local.
- Vous avez installé :
  - Les liaisons Java de Playwright.
  - Un navigateur compatible (Playwright peut installer automatiquement des navigateurs lors de la configuration).
  - Maven pour les dépendances du projet.

## Configuration de Maven {#maven-configuration}

Ajoutez les dépendances nécessaires dans votre `pom.xml` pour Playwright :

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

Le code suivant illustre un test basé sur Playwright pour le composant `HelloWorldView`.

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
   - Créer une instance de `Playwright`.
   - Lancer une instance de navigateur en utilisant `playwright.chromium().launch()`.

2. **Configurer l'environnement de test** :
   - Ouvrir une nouvelle page de navigateur avec `browser.newPage()`.
   - Naviguer vers la page `HelloWorldView` en utilisant la méthode `navigate`.

3. **Interagir avec les éléments** :
   - Utilisez les [localisateurs de Playwright](https://playwright.dev/java/docs/api/class-locator) pour interagir avec les éléments du DOM.
   - Remplissez les champs de saisie en utilisant `locator("input").fill()` et déclenchez des actions avec `getByText("Say Hello").click()`.

4. **Assertions** :
   - Vérifiez le message toast affiché avec `PlaywrightAssertions.assertThat()`.

5. **Nettoyage** :
   - Playwright gère automatiquement le nettoyage du navigateur lorsque le test se termine. Pour un nettoyage manuel, vous pouvez fermer le navigateur en utilisant `browser.close()`.

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
- Entrez webforJ dans le champ de texte et cliquez sur le bouton `Say Hello`.
- Un message toast devrait apparaître avec le texte : `Welcome to webforJ Starter webforJ!`.
