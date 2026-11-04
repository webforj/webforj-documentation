---
sidebar_position: 2
title: Componenten vinden
description: >-
  Describe the components a TestBed test acts on the way a user picks them out
  on the page, by type, text, label and what they contain.
_i18n_hash: 3e31c9bc7b51ebd2ebaf1a5e44c6e94f
---
Voordat een gebruiker op een knop klikt, zoekt die de knop op de pagina: de knop **Save** of het veld met het label voor het e-mailadres. Een TestBed-test zoekt componenten op dezelfde manier, met `ComponentLocator`. Een locator beschrijft een component aan de hand van het type en van wat de gebruiker erop leest, en doorzoekt de pagina wanneer de test om het resultaat vraagt.

Een test houdt nooit verwijzingen naar de velden van een view bij om de componenten te bereiken. De test zoekt ze op dezelfde manier als een gebruiker, zodat die blijft werken wanneer de view anders is opgebouwd, zolang de pagina maar hetzelfde toont.

<!-- INTRO_END -->

## Een component zoeken {#locating-a-component}

`ComponentLocator.of(type)` begint een beschrijving met het type van de component. Filters zoals `byText` verfijnen de beschrijving, en een afsluitende methode zoals `locate()` doorzoekt de pagina en retourneert het resultaat.

De volgende composite houdt bij hoe vaak op de knop **Add** is geklikt:

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

De test zoekt de knop op basis van de tekst, klikt er twee keer op en zoekt vervolgens de alinea die de teller weergeeft:

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

Een locator doorzoekt de pagina zodra de afsluitende methode wordt uitgevoerd. Daarom vindt de zoekopdracht na de klikken de nieuwe tekst.

## De component beschrijven {#describing-the-component}

Een beschrijving begint met het type en voegt kenmerken toe die de component onderscheiden, in de volgorde waarin een gebruiker ze zou waarnemen: eerst wat de gebruiker erop leest en daarna hoe de component op de pagina staat. Elke filter behoudt alleen de componenten die eraan voldoen. Filters kunnen worden gecombineerd, dus een component moet aan alle filters voldoen.

- `byText`, `byTextContaining`, `byLabel` en `byPlaceholder` komen overeen met wat de gebruiker op de component leest.
- `byValue`, `byTheme`, `byExpanse`, `byClassName`, `byStyle` en `byAttribute` komen overeen met wat de app op de component heeft ingesteld.
- `byName` komt overeen met de naam die de app geeft aan een component waarop geen tekst wordt weergegeven, zoals een knop met alleen een pictogram.
- `has` komt overeen met een component op basis van een andere component die erin zit.
- `filter` komt overeen met een willekeurige voorwaarde die de test opgeeft.

Een filter slaat componenten over die niet bevatten wat de filter probeert te lezen. Zo slaat `byLabel` een `Div` over in plaats van dat de zoekopdracht mislukt.

### Wat de gebruiker leest {#what-the-user-reads}

Het volgende aanmeldformulier heeft een e-mailveld met een label, een wachtwoordveld met een placeholder en een helpknop waarop alleen een pictogram staat:

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

De test zoekt de drie componenten op basis van hun label, placeholder en naam, vult de velden in en vraagt om hulp:

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

### Wat een component bevat {#what-a-component-contains}

Sommige componenten zijn alleen te onderscheiden aan de hand van wat erin staat, zoals kaarten die verschillen in de naam die ze weergeven. `has` behoudt een component wanneer een andere locator binnen die component een overeenkomst vindt. De volgende composite toont één kaart per contact, met de naam als titel, het e-mailadres als bijschrift en een knop **Delete**:

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

De test zoekt de kaart van Linus op basis van de naam die erin staat en controleert vervolgens of het e-mailadres op die kaart van hem is:

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

Alleen componenten binnen een kaart tellen mee, nooit de kaart zelf.

### Elke andere voorwaarde {#any-other-condition}

`filter` neemt een voorwaarde aan voor het type waarop de locator zoekt, zodat die alles kan uitlezen wat dat type aanbiedt. Het volgende formulier heeft drie velden, waarvan er twee verplicht zijn:

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

De test zoekt de verplichte velden met `filter` en leest hun labels uit:

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

De referentie voor <JavadocLink type="automation" location="com/webforj/automation/find/ComponentLocator" code="true">ComponentLocator</JavadocLink> vermeldt alle filters en de componenten die ze uitlezen.

## Binnen een component zoeken {#searching-inside-a-component}

Als een gebruiker dezelfde knop twee keer ziet, kijkt die naar het deel van de pagina rond de knop die die zoekt. `within(component)` doet hetzelfde en beperkt de zoekopdracht tot die component en de componenten erin.

Beide [contactkaarten](#what-a-component-contains) hebben een knop **Delete**, dus `byText("Delete")` alleen vindt twee knoppen. De volgende test zoekt alleen binnen de eerste kaart:

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

Alleen de eerste kaart wordt verwijderd; de kaart van Linus blijft staan.

## Het resultaat uitlezen {#reading-the-result}

De afsluitende methode bepaalt wat de test terugkrijgt. `locate()` retourneert de ene component die overeenkomt. `all()` retourneert alle overeenkomsten in de volgorde waarin ze op de pagina staan, `first()` en `last()` de eerste en laatste overeenkomst, `count()` het aantal overeenkomsten en `isPresent()` of er een overeenkomst is.

De volgende lijst toont voor elk item een selectievakje:

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

De test leest alle items uit met `all()`, het eerste en laatste item met `first()` en `last()`, en het aantal items met `count()`:

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

:::tip Vernietigde componenten
Een component die door de app is vernietigd, staat niet meer op de pagina. Geen enkele locator vindt die dus, ongeacht welke methode de zoekopdracht afsluit.
:::

## Wanneer de beschrijving niet klopt {#when-the-description-is-wrong}

`locate()` verwacht precies één overeenkomst. Als een beschrijving bij geen enkele of bij meerdere componenten past, mislukt de methode in plaats van willekeurig een component te kiezen. Beide [contactkaarten](#what-a-component-contains) hebben een knop **Delete**. De volgende test beschrijft een knop alleen aan de hand van de tekst en leest de foutmelding uit:

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

De melding vermeldt het type en het aantal overeenkomsten, gevolgd door de componentenstructuur waarin de locator heeft gezocht. De structuur toont de twee knoppen **Delete**, één op elke kaart. De oplossing is dus om binnen [één kaart](#searching-inside-a-component) te zoeken.

## Composites {#composites}

Een locator vindt een `Composite` op basis van het eigen type en ook op basis van het type van de component waaraan die is gekoppeld. De volgende composite is een e-mailveld. De composite koppelt een `TextField`, geeft het een label en een placeholder en retourneert het getypte adres met `getAddress()`:

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

De test zoekt de composite op basis van het eigen type en het veld waaraan die is gekoppeld op basis van `TextField` en het label. De test typt in het veld en leest het adres uit via de composite:

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

Als een composite een andere composite koppelt, vindt elke type in die keten de component.
