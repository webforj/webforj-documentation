---
sidebar_position: 2
title: Komponenttien etsiminen
description: >-
  Describe the components a TestBed test acts on the way a user picks them out
  on the page, by type, text, label and what they contain.
_i18n_hash: 3e31c9bc7b51ebd2ebaf1a5e44c6e94f
---
Ennen painikkeen napsauttamista käyttäjä etsii sen sivulta: **Tallenna**-painikkeen tai sähköpostiosoitetta varten merkityn kentän. TestBed-testi löytää komponentit samalla tavalla `ComponentLocator`-luokan avulla. Locator kuvaa komponentin sen tyypin ja siinä näkyvän käyttäjälle luettavan tekstin perusteella ja etsii sitä sivulta, kun testi pyytää tulosta.

Testi ei käytä näkymän kenttiä päästäkseen käsiksi sen komponentteihin. Se etsii ne samalla tavalla kuin käyttäjä, joten testi toimii edelleen, vaikka näkymän toteutus muuttuu, kunhan sivulla näkyvät samat asiat.

<!-- INTRO_END -->

## Komponentin etsiminen {#locating-a-component}

`ComponentLocator.of(type)` aloittaa kuvauksen komponentin tyypillä. `byText`-tyyppiset suodattimet tarkentavat sitä, ja lopullinen metodi, kuten `locate()`, etsii komponenttia sivulta ja palauttaa tuloksen.

Seuraava yhdistelmäkomponentti laskee **Lisää**-painikkeen napsautukset:

```java title="ClickCounter.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class ClickCounter extends Composite<Div> {
  private int clicks = 0;

  public ClickCounter() {
    Paragraph count = new Paragraph("Clicked 0 times");
    Button add = new Button(
        "Add", e -> count.setText("Clicked " + (++clicks) + " times"));

    getBoundComponent().add(add, count);
  }
}
```

Testi etsii painikkeen sen tekstin perusteella, napsauttaa sitä kahdesti ja etsii sitten kappaleen, jossa näkyy napsautusten määrä:

```java title="ClickCounterTest.java" showLineNumbers
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.samples.views.testbed.ClickCounter;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ClickCounterTest {
  @Test
  void shouldCountEveryClick(WebforjTestbed context) {
    context.add(new ClickCounter());

    var button = ComponentLocator.of(Button.class).byText("Add").locate();

    UserInteraction.click(button);
    UserInteraction.click(button);

    assertTrue(
        ComponentLocator.of(Paragraph.class)
            .byText("Clicked 2 times")
            .isPresent());
  }
}
```

Locator etsii sivulta vasta, kun sen lopullista metodia kutsutaan, joten napsautusten jälkeen tehtävä haku löytää uuden tekstin.

## Komponentin kuvaaminen {#describing-the-component}

Kuvaus alkaa tyypistä, ja siihen lisätään komponentin muista erottavia ominaisuuksia siinä järjestyksessä kuin käyttäjä ne huomaa: ensin komponentissa näkyvä teksti ja sitten sen sijainti sivulla. Kukin suodatin säilyttää vain siihen täsmäävät komponentit, ja suodattimia voi ketjuttaa, joten komponentin on täsmättävä niihin kaikkiin.

- `byText`, `byTextContaining`, `byLabel` ja `byPlaceholder` vastaavat komponentissa näkyvää käyttäjälle luettavaa tekstiä.
- `byValue`, `byTheme`, `byExpanse`, `byClassName`, `byStyle` ja `byAttribute` vastaavat arvoja, jotka sovellus on asettanut komponentille.
- `byName` vastaa nimeä, jonka sovellus antaa komponentille, jossa ei näy tekstiä, kuten pelkän kuvakkeen sisältävälle painikkeelle.
- `has` etsii komponentin sen sisältä löytyvän toisen komponentin perusteella.
- `filter` vastaa mitä tahansa testissä määritettyä ehtoa.

Suodatin ohittaa komponentit, joista puuttuu sen lukema ominaisuus, joten `byLabel` ohittaa `Div`-komponentin sen sijaan, että haku epäonnistuisi.

### Käyttäjälle näkyvä teksti {#what-the-user-reads}

Seuraavassa kirjautumislomakkeessa on sähköpostikenttä, jossa on selite, salasanakenttä, jossa on paikkamerkkiteksti, sekä ohjepainike, jossa näkyy vain kuvake:

```java title="SignInForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.PasswordField;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.TablerIcon;

public class SignInForm extends Composite<Div> {

  public SignInForm() {
    TextField email = new TextField("Email");
    PasswordField password = new PasswordField().setPlaceholder("Password");
    Paragraph hint = new Paragraph();
    Button help = new Button(
        TablerIcon.create("help"), e -> hint.setText("Use your work email"));
    help.setName("help");

    getBoundComponent().add(email, password, help, hint);
  }
}
```

Testi etsii kolme komponenttia selitteen, paikkamerkkitekstin ja nimen perusteella, täyttää kentät ja pyytää apua:

```java title="SignInFormTest.java" showLineNumbers
@Test
void shouldShowTheHintWhenTheUserAsksForHelp(WebforjTestbed context) {
  context.add(new SignInForm());

  var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
  var password = ComponentLocator.of(PasswordField.class)
      .byPlaceholder("Password")
      .locate();
  var help = ComponentLocator.of(Button.class).byName("help").locate();

  UserInteraction.type(email, "ada@example.com");
  UserInteraction.type(password, "secret");
  UserInteraction.click(help);

  assertEquals("ada@example.com", email.getValue());
  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Use your work email")
          .isPresent());
}
```

### Komponentin sisältö {#what-a-component-contains}

Jotkin komponentit voi erottaa toisistaan vain niiden sisällön perusteella, esimerkiksi kortit, joissa näkyy eri nimi. `has` säilyttää komponentin, jos toinen locator löytää sen sisältä vastaavan komponentin. Seuraava yhdistelmäkomponentti näyttää kullekin yhteyshenkilölle kortin, jossa nimi on otsikkona, sähköposti kuvatekstinä ja **Poista**-painike:

```java title="ContactCards.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Span;

public class ContactCards extends Composite<Div> {

  public ContactCards() {
    getBoundComponent()
        .add(
            card("Ada", "ada@example.com"), card("Linus", "linus@example.com"));
  }

  private Card card(String name, String email) {
    Card card = new Card();
    card.addToTitle(new Span(name))
        .addToCaption(new Span(email))
        .addToFooter(new Button("Delete", e -> card.destroy()));

    return card;
  }
}
```

Testi etsii Linusin kortin sen sisällä olevan nimen perusteella ja tarkistaa sitten, että kortissa on hänen sähköpostiosoitteensa:

```java title="ContactCardsTest.java" showLineNumbers
@Test
void shouldShowTheEmailOnTheCardOfTheContact(WebforjTestbed context) {
  context.add(new ContactCards());

  var linus =
      ComponentLocator.of(Card.class)
          .has(ComponentLocator.of(Span.class).byText("Linus"))
          .locate();

  assertTrue(
      ComponentLocator.of(Span.class)
          .within(linus)
          .byText("linus@example.com")
          .isPresent());
}
```

Vain kortin sisällä olevat komponentit lasketaan mukaan, ei koskaan itse korttia.

### Mikä tahansa muu ehto {#any-other-condition}

`filter` saa ehdon tyypille, jota locator etsii, joten sillä voi lukea mitä tahansa kyseisen tyypin tarjoamaa. Seuraavassa lomakkeessa on kolme kenttää, joista kaksi on pakollisia:

```java title="ShippingForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;

public class ShippingForm extends Composite<Div> {

  public ShippingForm() {
    getBoundComponent()
        .add(
            new TextField("Name").setRequired(true),
            new TextField("Company"),
            new TextField("City").setRequired(true));
  }
}
```

Testi etsii pakolliset kentät `filter`-suodattimella ja lukee niiden selitteet:

```java title="ShippingFormTest.java" showLineNumbers
@Test
void shouldMarkTheNameAndTheCityAsRequired(WebforjTestbed context) {
  context.add(new ShippingForm());

  var required = ComponentLocator.of(TextField.class)
      .filter(TextField::isRequired)
      .all();

  assertEquals(
      List.of("Name", "City"),
      required.stream().map(TextField::getLabel).toList());
}
```

<JavadocLink type="automation" location="com/webforj/automation/find/ComponentLocator" code="true">ComponentLocator</JavadocLink>-viitteessä luetellaan kaikki suodattimet ja komponentit, joista ne lukevat tietoja.

## Haku komponentin sisältä {#searching-inside-a-component}

Jos käyttäjä löytää kaksi samannäköistä painiketta, hän katsoo sivun osaa sen ympäriltä, jota haluaa käyttää. `within(component)` toimii samalla tavalla ja rajaa haun kyseiseen komponenttiin ja sen sisältä löytyviin komponentteihin.

Molemmissa [yhteystietokorteissa](#what-a-component-contains) on **Poista**-painike, joten pelkkä `byText("Delete")` vastaa kahta painiketta. Seuraava testi hakee vain ensimmäisestä kortista:

```java title="ContactCardsTest.java" showLineNumbers
@Test
void shouldRemoveOnlyTheCardWhoseDeleteButtonTheUserClicks(
    WebforjTestbed context) {
  context.add(new ContactCards());

  var ada = ComponentLocator.of(Card.class).all().get(0);

  UserInteraction.click(
      ComponentLocator.of(Button.class).within(ada).byText("Delete").locate());

  assertEquals(1, ComponentLocator.of(Card.class).count());
  assertTrue(ComponentLocator.of(Span.class).byText("Linus").isPresent());
}
```

Vain ensimmäinen kortti poistetaan, ja Linusin kortti säilyy.

## Tuloksen lukeminen {#reading-the-result}

Lopullinen metodi määrittää, mitä testi saa tulokseksi. `locate()` palauttaa yhden vastaavan komponentin. `all()` palauttaa kaikki vastaavat komponentit sivun mukaisessa järjestyksessä, `first()` ja `last()` ensimmäisen ja viimeisen vastaavan komponentin, `count()` vastaavien komponenttien määrän ja `isPresent()` tiedon siitä, löytyykö vastaavuuksia.

Seuraavassa luettelossa on yksi valintaruutu kutakin tuotetta kohden:

```java title="GroceryList.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optioninput.CheckBox;
import java.util.List;

public class GroceryList extends Composite<Div> {

  public GroceryList() {
    for (String item : List.of("Milk", "Bread", "Eggs")) {
      getBoundComponent().add(new CheckBox(item));
    }
  }
}
```

Testi lukee kaikki tuotteet `all()`-metodilla, ensimmäisen ja viimeisen `first()`- ja `last()`-metodeilla sekä tuotteiden määrän `count()`-metodilla:

```java title="GroceryListTest.java" showLineNumbers
@Test
void shouldListEveryItemInOrder(WebforjTestbed context) {
  context.add(new GroceryList());

  var items = ComponentLocator.of(CheckBox.class).all();

  assertEquals(
      List.of("Milk", "Bread", "Eggs"),
      items.stream().map(CheckBox::getText).toList());
  assertEquals(
      "Milk",
      ComponentLocator.of(CheckBox.class).first().orElseThrow().getText());
  assertEquals(
      "Eggs",
      ComponentLocator.of(CheckBox.class).last().orElseThrow().getText());
  assertEquals(3, ComponentLocator.of(CheckBox.class).count());
}
```

:::tip TuHotetut komponentit
Sovelluksen tuhoamaa komponenttia ei enää ole sivulla, joten mikään locator ei löydä sitä, riippumatta siitä, millä metodilla haku päätetään.
:::

## Kun kuvaus ei vastaa komponenttia {#when-the-description-is-wrong}

`locate()` odottaa löytävänsä täsmälleen yhden vastaavan komponentin, joten kuvaus, joka sopii yhteenkään komponenttiin tai useaan komponenttiin, aiheuttaa virheen sen sijaan, että valittaisiin yksi satunnaisesti. Molemmissa [yhteystietokorteissa](#what-a-component-contains) on **Poista**-painike. Seuraavassa testissä painike kuvataan pelkän tekstin perusteella ja luetaan virhe:

```java title="ContactCardsTest.java" showLineNumbers
@Test
void shouldNameEveryMatchWhenTheDescriptionFitsTwoButtons(
    WebforjTestbed context) {
  context.add(new ContactCards());

  var delete = ComponentLocator.of(Button.class).byText("Delete");

  var failure = assertThrows(IllegalStateException.class, delete::locate);

  assertEquals(
      """
      Expected exactly one Button but found 2 in the tree:
      - Frame:
        - ContactCards [composite=Div] [node=div]:
          - Card [composite=Element] [node=dwc-card]:
            - Span [slot=title] [composite=Element] [node=span]
            - Span [slot=caption] [composite=Element] [node=span]
            - Button "Delete" [slot=footer]
          - Card [composite=Element] [node=dwc-card]:
            - Span [slot=title] [composite=Element] [node=span]
            - Span [slot=caption] [composite=Element] [node=span]
            - Button "Delete" [slot=footer]
      """,
      failure.getMessage());
}
```

Virheilmoituksessa kerrotaan tyyppi ja vastaavuuksien määrä, minkä jälkeen näytetään locatorin hakema komponenttipuu. Puussa näkyvät kaksi **Poista**-painiketta, yksi kummassakin kortissa, joten korjaus on rajata haku [yhteen korttiin](#searching-inside-a-component).

## Yhdistelmäkomponentit {#composites}

Locator löytää `Composite`-komponentin sen oman tyypin perusteella sekä sen komponentin tyypin perusteella, johon se on sidottu. Seuraava yhdistelmäkomponentti on sähköpostikenttä. Se sitoo `TextField`-komponentin, asettaa sille selitteen ja paikkamerkkitekstin sekä palauttaa kirjoitetun osoitteen `getAddress()`-metodilla:

```java title="EmailField.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;

public class EmailField extends Composite<TextField> {

  public EmailField() {
    getBoundComponent().setLabel("Email").setPlaceholder("name@example.com");
  }

  public String getAddress() {
    return getBoundComponent().getValue();
  }
}
```

Testi etsii yhdistelmäkomponentin sen oman tyypin perusteella ja siihen sidotun kentän `TextField`-tyypin ja selitteen perusteella. Se kirjoittaa kenttään ja lukee osoitteen yhdistelmäkomponentin kautta:

```java title="EmailFieldTest.java" showLineNumbers
@Test
void shouldReadTheAddressTheUserTypedIntoTheBoundField(WebforjTestbed context) {
  context.add(new EmailField());

  var email = ComponentLocator.of(EmailField.class).locate();
  var field = ComponentLocator.of(TextField.class).byLabel("Email").locate();

  UserInteraction.type(field, "ada@example.com");

  assertEquals("ada@example.com", email.getAddress());
}
```

Kun yhdistelmäkomponentti sitoo toisen yhdistelmäkomponentin, kaikki ketjun tyypit löytävät sen.
