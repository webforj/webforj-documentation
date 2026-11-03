---
sidebar_position: 1
title: Aloittaminen
description: >-
  Add the TestBed dependency, write a first test that adds a view and acts on
  it, and run the test.
_i18n_hash: 8a5fde986acda527dbefeadc4e0dc329
---
# Aloittaminen <DocChip chip='since' label='26.03' />

TestBed on webforJ:n selainriippumaton testausratkaisu: se suorittaa näkymiä ja komponentteja tavallisissa JUnit-testeissä ilman selainta tai palvelinta. Tällä sivulla lisätään riippuvuus, kirjoitetaan ensimmäinen testi ja suoritetaan se.

`@WebforjTest`-annotaatio luo `WebforjTestbed`-kontekstin luokan jokaista testiä varten. Testissä `ComponentLocator` etsii sivun komponentit, ja `UserInteraction` simuloi käyttäjän toimia niiden kanssa.

<!-- INTRO_END -->

## Riippuvuuden lisääminen {#adding-the-dependency}

TestBed toimitetaan erillisenä moduulina. Lisää se sovellukseesi testiriippuvuutena:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-testbed-junit</artifactId>
  <scope>test</scope>
</dependency>
```

Versio määräytyy webforJ Bill of Materials (BOM) -määrityksen mukaan, joten riippuvuudelle ei ilmoiteta versiota. [Archetypesta](/docs/introduction/getting-started) luotu projekti tuo BOM:n jo valmiiksi. Moduuli tuo mukanaan myös JUnit Jupiter API:n.

:::info Spring Boot -sovellukset
Spring Boot -sovellukset käyttävät sen sijaan `webforj-spring-testbed`-moduulia. Katso [Spring-testaus](/docs/integrations/spring/testing).
:::

## Ensimmäisen testin kirjoittaminen {#writing-a-first-test}

Seuraava lomake tervehtii käyttäjän kirjoittamaa nimeä:

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

Testi kirjoittaa nimen, napsauttaa **Greet**-painiketta ja tarkistaa tervehdyksen:

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

Testi etenee kuin käyttäjän toiminta:

1. `@WebforjTest` antaa jokaiselle testimetodille `WebforjTestbed`-kontekstin, jossa testi suoritetaan.
2. `context.add()` lisää lomakkeen sivulle ja suorittaa sen koko elinkaaren, kuten lomakkeen lisääminen kehykseen tekisi käynnissä olevassa sovelluksessa.
3. `ComponentLocator` etsii kentän sen tunnisteen ja painikkeen sen tekstin perusteella. Katso [Komponenttien etsiminen](./finding-components).
4. `UserInteraction` kirjoittaa ja napsauttaa kuten käyttäjä, jolloin painikkeen kuuntelija suoritetaan. Katso [Käyttäjän toiminta](./user-interaction).
5. `verifyThat` tarkistaa tekstin, jonka käyttäjä näkisi. Katso [Vahvistukset](./assertions).

## Mitä `@WebforjTest` tarjoaa {#what-webforjtest-provides}

Jokainen annotaatiolla merkityn luokan testi suoritetaan omassa `WebforjTestbed`-kontekstissaan. Konteksti alustetaan ennen jokaista testiä ja puretaan sen jälkeen, joten jokainen testi alkaa tyhjältä sivulta eikä edellisen testin tila säily.

Testi saa yhteyden kontekstiinsa kahdella tavalla:

- Määrittämällä testimetodille `WebforjTestbed`-parametrin ensimmäisen testin tapaan.
- Kutsumalla `WebforjTestbed.getCurrent()`-metodia esimerkiksi metodista, jota luokan testit käyttävät yhdessä.

`context.add()` ottaa vastaan yhden tai useamman komponentin ja lisää ne kontekstin juurikehykseen, jonka `context.getFrame()` palauttaa.

Reittien välillä siirtyvissä testeissä komponenttien lisäämisen sijaan määritetään `@WebforjTest`-annotaation `app`-, `routeClasses`- tai `routePackages`-attribuutti. Katso [Sovellusten testaaminen](./testing-apps).

## Testien suorittaminen {#running-the-tests}

TestBed-testit ovat tavallisia JUnit-testejä. Suorita ne koontityökalulla tai IDE:stä kuten mikä tahansa JUnit-testi:

```bash title="Terminal"
mvn test
```
