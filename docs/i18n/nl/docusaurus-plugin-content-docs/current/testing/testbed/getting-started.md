---
sidebar_position: 1
title: Aan de slag
description: >-
  Add the TestBed dependency, write a first test that adds a view and acts on
  it, and run the test.
_i18n_hash: 8a5fde986acda527dbefeadc4e0dc329
---
# Aan de slag <DocChip chip='since' label='26.03' />

De TestBed is de browserloze testomgeving van webforJ: hiermee voer je views en componenten uit in gewone JUnit-tests, zonder browser of server. Op deze pagina voeg je de dependency toe, schrijf je een eerste test en voer je die uit.

De annotatie `@WebforjTest` stelt voor elke test van een klasse een `WebforjTestbed`-context in. Binnen een test zoekt `ComponentLocator` componenten op de pagina en voert `UserInteraction` acties uit zoals een gebruiker dat zou doen.

<!-- INTRO_END -->

## De dependency toevoegen {#adding-the-dependency}

De TestBed wordt geleverd door een aparte module. Voeg deze met test scope toe aan je app:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-testbed-junit</artifactId>
  <scope>test</scope>
</dependency>
```

De versie is afkomstig uit de webforJ Bill of Materials (BOM), dus de dependency hoeft geen versie te declareren. Een project dat is gemaakt op basis van een [archetype](/docs/introduction/getting-started) importeert de BOM al. De module bevat ook de JUnit Jupiter API.

:::info Spring Boot-apps
Spring Boot-apps gebruiken in plaats daarvan de module `webforj-spring-testbed`. Zie [Testen met Spring](/docs/integrations/spring/testing).
:::

## Een eerste test schrijven {#writing-a-first-test}

Het volgende formulier begroet de gebruiker met de naam die deze invoert:

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

De test voert een naam in, klikt op **Greet** en controleert de begroeting:

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

De test leest als de stappen die een gebruiker uitvoert:

1. `@WebforjTest` geeft elke testmethode een `WebforjTestbed`, de context waarin de test wordt uitgevoerd.
2. `context.add()` plaatst het formulier op de pagina en voert de volledige levenscyclus ervan uit, net zoals wanneer je het in een frame plaatst in een draaiende app.
3. `ComponentLocator` zoekt het veld op basis van het label en de knop op basis van de tekst. Zie [Componenten zoeken](./finding-components).
4. `UserInteraction` typt en klikt zoals een gebruiker dat doet, zodat de listener van de knop wordt uitgevoerd. Zie [Gebruikersinteractie](./user-interaction).
5. `verifyThat` controleert de tekst die de gebruiker zou lezen. Zie [Assertions](./assertions).

## Wat `@WebforjTest` biedt {#what-webforjtest-provides}

Elke test van een geannoteerde klasse wordt uitgevoerd in een eigen `WebforjTestbed`. De context wordt vóór elke test ingesteld en erna afgebroken. Zo begint elke test op een lege pagina en wordt er geen status overgedragen van de vorige test.

Een test kan op twee manieren toegang krijgen tot de context:

- Declareer een `WebforjTestbed`-parameter voor de testmethode, zoals in de eerste test.
- Roep `WebforjTestbed.getCurrent()` aan, bijvoorbeeld vanuit een methode die de tests van een klasse delen.

`context.add()` accepteert een of meer componenten en voegt deze toe aan het rootframe van de context. Dit frame wordt geretourneerd door `context.getFrame()`.

Tests die tussen routes navigeren in plaats van componenten toe te voegen, stellen de attributen `app`, `routeClasses` of `routePackages` van `@WebforjTest` in. Zie [Apps testen](./testing-apps).

## De tests uitvoeren {#running-the-tests}

TestBed-tests zijn gewone JUnit-tests. Voer ze uit met de buildtool of vanuit de IDE, net als elke andere JUnit-test:

```bash title="Terminal"
mvn test
```
