---
sidebar_position: 1
title: Bien démarrer
description: >-
  Add the TestBed dependency, write a first test that adds a view and acts on
  it, and run the test.
_i18n_hash: 8a5fde986acda527dbefeadc4e0dc329
---
# Premiers pas <DocChip chip='since' label='26.03' />

Le TestBed permet de tester webforJ sans navigateur : il exécute des vues et des composants dans de simples tests JUnit, sans navigateur ni serveur. Cette page explique comment ajouter la dépendance, écrire un premier test et l’exécuter.

L’annotation `@WebforjTest` configure un contexte `WebforjTestbed` pour chaque test d’une classe. Dans un test, `ComponentLocator` trouve les composants sur la page et `UserInteraction` reproduit les actions d’un utilisateur.

<!-- INTRO_END -->

## Ajouter la dépendance {#adding-the-dependency}

Le TestBed est fourni par un module distinct. Ajoutez-le à votre application avec la portée `test` :

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-testbed-junit</artifactId>
  <scope>test</scope>
</dependency>
```

La version provient du Bill of Materials (BOM) de webforJ ; la dépendance ne déclare donc aucune version. Un projet créé à partir d’un [archétype](/docs/introduction/getting-started) importe déjà le BOM. Le module inclut également l’API JUnit Jupiter.

:::info Applications Spring Boot
Les applications Spring Boot utilisent plutôt le module `webforj-spring-testbed`. Consultez [Tests avec Spring](/docs/integrations/spring/testing).
:::

## Écrire un premier test {#writing-a-first-test}

Le formulaire suivant salue l’utilisateur en reprenant le nom qu’il saisit :

```java title="GreetingForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class GreetingForm extends Composite<Div> {

  public GreetingForm() {
    TextField name = new TextField("Name");
    Paragraph greeting = new Paragraph();
    Button greet = new Button(
        "Greet", e -> greeting.setText("Hello, " + name.getValue() + "!"));

    getBoundComponent().add(name, greet, greeting);
  }
}
```

Le test saisit un nom, clique sur **Greet** et vérifie le message de salutation :

```java title="GreetingFormTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.samples.views.testbed.GreetingForm;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class GreetingFormTest {
  @Test
  void shouldGreetTheNameTheUserTyped(WebforjTestbed context) {
    context.add(new GreetingForm());

    var name = ComponentLocator.of(TextField.class).byLabel("Name").locate();
    var greet = ComponentLocator.of(Button.class).byText("Greet").locate();

    UserInteraction.type(name, "Ada");
    UserInteraction.click(greet);

    verifyThat(ComponentLocator.of(Paragraph.class).locate())
        .hasText("Hello, Ada!");
  }
}
```

Le test suit les étapes d’un utilisateur :

1. `@WebforjTest` fournit à chaque méthode de test un `WebforjTestbed`, le contexte dans lequel le test s’exécute.
2. `context.add()` place le formulaire sur la page et exécute son cycle de vie complet, comme lorsqu’on l’ajoute à un frame dans une application en cours d’exécution.
3. `ComponentLocator` trouve le champ à partir de son libellé et le bouton à partir de son texte. Consultez [Trouver des composants](./finding-components).
4. `UserInteraction` saisit du texte et clique comme le ferait un utilisateur, ce qui déclenche le listener du bouton. Consultez [Interactions utilisateur](./user-interaction).
5. `verifyThat` vérifie le texte qu’un utilisateur lirait. Consultez [Assertions](./assertions).

## Ce que fournit `@WebforjTest` {#what-webforjtest-provides}

Chaque test d’une classe annotée s’exécute dans son propre `WebforjTestbed`. Le contexte est configuré avant chaque test et supprimé après celui-ci : chaque test commence donc sur une page vide, sans récupérer l’état du test précédent.

Un test peut accéder à son contexte de deux manières :

- Déclarer un paramètre `WebforjTestbed` dans la méthode de test, comme dans le premier test.
- Appeler `WebforjTestbed.getCurrent()`, par exemple depuis une méthode partagée par les tests d’une classe.

`context.add()` accepte un ou plusieurs composants et les ajoute au frame racine du contexte, que renvoie `context.getFrame()`.

Les tests qui naviguent entre des routes au lieu d’ajouter des composants définissent les attributs `app`, `routeClasses` ou `routePackages` de `@WebforjTest`. Consultez [Tester des applications](./testing-apps).

## Exécuter les tests {#running-the-tests}

Les tests TestBed sont de simples tests JUnit. Exécutez-les avec l’outil de build ou depuis l’IDE, comme n’importe quel autre test JUnit :

```bash title="Terminal"
mvn test
```
