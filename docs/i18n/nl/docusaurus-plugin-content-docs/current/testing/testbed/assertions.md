---
sidebar_position: 4
title: Asserties
description: >-
  Check what the user would see on a component with verifyThat, and read
  failures that name the component and both values.
_i18n_hash: 6414e2fab20d3bf7828413494af79b74
---
Nadat een test een actie uitvoert, controleert deze wat de gebruiker zou zien: de tekst op een knop, of een veld kan worden bewerkt en welk item in een lijst als geselecteerd wordt weergegeven. Met `verifyThat(component)` geef je deze verwachtingen op in dezelfde bewoordingen, zoals `isReadOnly()` of `hasText("Save")`. Elke controle leest de status van het component, dezelfde status die de browser weergeeft, en bij een mislukte controle worden het component en beide waarden genoemd.

<!-- INTRO_END -->

## Een component controleren {#checking-a-component}

De volgende editor toont een naam die niet kan worden gewijzigd totdat de gebruiker op **Edit** klikt. Door te klikken kan het veld worden bewerkt, wordt de focus erin geplaatst en verandert de knop in **Save**:

```java title="ProfileEditor.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;

public class ProfileEditor extends Composite<Div> {

  public ProfileEditor() {
    TextField name = new TextField("Name", "Ada");
    name.setReadOnly(true);
    Button edit = new Button("Edit");
    edit.onClick(
        e -> {
          name.setReadOnly(false).focus();
          edit.setText("Save");
        });

    getBoundComponent().add(name, edit);
  }
}
```

De test controleert het veld vóór en na de klik:

```java title="ProfileEditorTest.java" showLineNumbers
@Test
void shouldMakeTheNameEditableWhenTheUserClicksEdit(WebforjTestbed context) {
  context.add(new ProfileEditor());

  var name = ComponentLocator.of(TextField.class).byLabel("Name").locate();
  var edit = ComponentLocator.of(Button.class).byText("Edit").locate();

  verifyThat(name).hasValue("Ada").isReadOnly();

  UserInteraction.click(edit);

  verifyThat(name).isEditable().isFocused();
  verifyThat(edit).hasText("Save");
}
```

`verifyThat` komt uit `com.webforj.testbed.assertion.WebforjAssertions` en wordt statisch geïmporteerd. Elke controle retourneert de assertion, zodat controles voor één component in één statement kunnen worden gekoppeld. Ze worden op volgorde uitgevoerd; de eerste controle die mislukt, beëindigt de test.

Voor elke status is er een controle voor beide kanten, zoals `isReadOnly()` en `isEditable()`, of `isVisible()` en `isHidden()`. Zo vermeldt een test de verwachte status rechtstreeks.

## Wat een controle leest {#what-a-check-reads}

Een controle leest het component via een [concern-interface](/docs/building-ui/component-fundamentals#concern-interfaces), zodat deze werkt voor elk component dat die interface implementeert, ook voor een component van je app. `hasLabel` werkt voor elk component met een label, en `isReadOnly` voor elk component dat alleen-lezen kan zijn. De controles omvatten:

- Wat de gebruiker leest: tekst, HTML-inhoud, label, tijdelijke aanduiding, helptekst en tooltip.
- De status van het component: zichtbaar, ingeschakeld, alleen-lezen, verplicht, geldig en focus.
- De waarde van een veld, en de items en selectie van lijsten, tabellen en bomen.
- Wat de app heeft ingesteld: naam, attributen, eigenschappen, stijl, thema en omvang.
- De componenten die een container bevat, en of een component is gekoppeld of vernietigd.

De referentie <JavadocLink type="testbed-foundation" location="com/webforj/testbed/assertion/AbstractComponentAssert" code="true">AbstractComponentAssert</JavadocLink> vermeldt elke controle en de interface die deze uitleest.

## Waarden {#values}

Het volgende veld berekent een totaal op basis van de hoeveelheid die de gebruiker invoert:

```java title="OrderQuantity.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.field.NumberField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class OrderQuantity extends Composite<Div> {

  public OrderQuantity() {
    NumberField quantity = new NumberField("Quantity");
    Paragraph total = new Paragraph("Total: 0");
    quantity.onValueChange(
        e -> total.setText("Total: " + (int) (e.getValue() * 5)));

    getBoundComponent().add(quantity, total);
  }
}
```

De test voert een hoeveelheid in en controleert vervolgens de waarde van het veld en het totaal:

```java title="OrderQuantityTest.java" showLineNumbers
@Test
void shouldHoldTheTypedQuantityAsANumber(WebforjTestbed context) {
  context.add(new OrderQuantity());

  var quantity = ComponentLocator.of(NumberField.class)
      .byLabel("Quantity")
      .locate();

  UserInteraction.type(quantity, "3");

  verifyThat(quantity).hasValue(3.0);
  verifyThat(ComponentLocator.of(Paragraph.class).locate())
      .hasText("Total: 15");
}
```

`hasValue` vergelijkt met `equals`, dus de verwachte waarde moet hetzelfde type hebben als de waarde van het veld. Een `NumberField` bevat een `Double`, dus verwacht de test `3.0` en niet `3`.

:::info Waardetypen
Een `DateField` bevat een `LocalDate` en een `TimeField` een `LocalTime`. Verwacht een waarde van dat type.
:::

## Onderliggende componenten {#child-components}

`hasComponentCount` controleert hoeveel componenten een container bevat, en `hasComponent` controleert of deze een bepaald component bevat. De volgende editor voegt elke tag die de gebruiker invoert toe aan een lijst met tags:

```java title="TagEditor.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Span;

public class TagEditor extends Composite<Div> {

  public TagEditor() {
    TextField tag = new TextField("Tag");
    Div tags = new Div();
    tags.setName("tags");
    Button add = new Button("Add", e -> tags.add(new Span(tag.getValue())));

    getBoundComponent().add(tag, add, tags);
  }
}
```

De test voegt twee tags toe en controleert de lijst:

```java title="TagEditorTest.java" showLineNumbers
@Test
void shouldAddEveryTagTheUserEnters(WebforjTestbed context) {
  context.add(new TagEditor());

  var tag = ComponentLocator.of(TextField.class).byLabel("Tag").locate();
  var add = ComponentLocator.of(Button.class).byText("Add").locate();
  var tags = ComponentLocator.of(Div.class).byName("tags").locate();

  UserInteraction.type(tag, "java");
  UserInteraction.click(add);
  UserInteraction.type(tag, "kotlin");
  UserInteraction.click(add);

  verifyThat(tags)
      .hasComponentCount(2)
      .hasComponent(ComponentLocator.of(Span.class).byText("kotlin").locate());
}
```

## Selectie {#selection}

De selectiecontroles lezen welk item in een lijst, tabel of boom als geselecteerd wordt weergegeven, op basis van het item, de index of de sleutel. De volgende lijst biedt drie soorten fruit:

```java title="FruitPicker.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ListBox;

public class FruitPicker extends Composite<Div> {

  public FruitPicker() {
    ListBox fruits = new ListBox("Fruit");
    fruits.add("apple", "Apple");
    fruits.add("pear", "Pear");
    fruits.add("plum", "Plum");

    getBoundComponent().add(fruits);
  }
}
```

De test controleert dat er niets is geselecteerd, kiest een fruit en controleert de selectie op basis van de sleutel en de index:

```java title="FruitPickerTest.java" showLineNumbers
@Test
void shouldSelectTheFruitTheUserPicked(WebforjTestbed context) {
  context.add(new FruitPicker());

  var fruits = ComponentLocator.of(ListBox.class).byLabel("Fruit").locate();

  verifyThat(fruits).hasNoSelection();

  UserInteraction.selectKey(fruits, "pear");

  verifyThat(fruits).hasSelectedKey("pear").hasSelectedIndex(1);
}
```

Bij een component waarmee meerdere items kunnen worden geselecteerd, slagen `hasSelected`, `hasSelectedIndex` en `hasSelectedKey` als het verwachte item ertussen staat. `hasItemCount` controleert het aantal items van een component met een repository, zoals een `Table`.

## Levenscyclus {#lifecycle}

`isAttached()` controleert of een component op de pagina staat, en `isDestroyed()` of de app het heeft vernietigd. De volgende banner vernietigt zichzelf wanneer de gebruiker deze sluit:

```java title="WelcomeBanner.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class WelcomeBanner extends Composite<Div> {

  public WelcomeBanner() {
    getBoundComponent()
        .add(
            new Paragraph("Welcome back"),
            new Button("Dismiss", e -> destroy()));
  }
}
```

De test sluit de banner en controleert deze:

```java title="WelcomeBannerTest.java" showLineNumbers
@Test
void shouldRemoveTheBannerWhenTheUserDismissesIt(WebforjTestbed context) {
  context.add(new WelcomeBanner());

  var banner = ComponentLocator.of(WelcomeBanner.class).locate();

  verifyThat(banner).isAttached();

  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Dismiss").locate());

  verifyThat(banner).isDestroyed();
}
```

## Foutmeldingen {#failure-messages}

Een mislukte controle werpt een `AssertionError` met de naam van het type component, de verwachte waarde en de werkelijke waarde. De volgende test laat een controle opzettelijk mislukken en leest het bericht uit:

```java title="ProfileEditorTest.java" showLineNumbers
@Test
void shouldNameTheComponentAndBothValuesOnFailure(WebforjTestbed context) {
  context.add(new ProfileEditor());

  var name = ComponentLocator.of(TextField.class).byLabel("Name").locate();

  var failure = assertThrows(
      AssertionError.class, () -> verifyThat(name).hasValue("Grace"));

  assertEquals(
      "Expected TextField to have value <Grace> but it has <Ada>.",
      failure.getMessage());
}
```

Een controle op een component dat de status niet beschikbaar maakt, mislukt met een bericht waarin de ontbrekende interface wordt genoemd. Een `Div` bevat geen waarde, dus `hasValue` op een `Div` mislukt en noemt `ValueAware`.

## Controles voor je eigen componenten {#checks-for-your-own-components}

Een component van je app dat concern-interfaces implementeert, krijgt de bijbehorende controles zonder extra code. Schrijf voor een status die alleen je component kent een assertion-klasse. De volgende beoordeling telt de sterren waarop de gebruiker klikt en retourneert ze via `getStars()`:

```java title="StarRating.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;

public class StarRating extends Composite<Div> {
  private int stars = 0;

  public StarRating() {
    for (int i = 1; i <= 5; i++) {
      int value = i;
      getBoundComponent()
          .add(new Button(String.valueOf(i), e -> stars = value));
    }
  }

  public int getStars() {
    return stars;
  }
}
```

De assertion-klasse breidt `AbstractComponentAssert` uit en voegt een controle voor het aantal sterren toe. Een statische `verifyThat` maakt de klasse aan, zodat een test deze op dezelfde manier aanroept als de ingebouwde controles:

```java title="StarRatingAssert.java" showLineNumbers
import com.webforj.samples.views.testbed.StarRating;
import com.webforj.testbed.assertion.AbstractComponentAssert;

public final class StarRatingAssert
    extends AbstractComponentAssert<StarRatingAssert, StarRating> {

  private StarRatingAssert(StarRating actual) {
    super(actual, StarRatingAssert.class);
  }

  public static StarRatingAssert verifyThat(StarRating actual) {
    return new StarRatingAssert(actual);
  }

  public StarRatingAssert hasStars(int stars) {
    isNotNull();
    if (actual.getStars() != stars) {
      failWithMessage(
          "Expected %s to have %s stars but it has %s.",
          componentName(),
          stars,
          actual.getStars());
    }

    return myself;
  }
}
```

Binnen een controle is `actual` het component, `failWithMessage` laat de controle mislukken met een opgemaakt bericht, `componentName()` geeft de naam van het component en door `myself` te retourneren kunnen controles worden gekoppeld. De nieuwe klasse voegt controles toe, maar vervangt er geen. Een test importeert beide `verifyThat`-methoden statisch; Java kiest de methode waarvan de parameter bij het argument past: een `StarRating` gaat naar `StarRatingAssert` en elk ander component naar `WebforjAssertions`. De ingebouwde controles blijven ook beschikbaar voor de beoordeling, zoals `isAttached()`:

```java title="StarRatingTest.java" showLineNumbers
import static com.webforj.samples.unit.views.testbed.StarRatingAssert.verifyThat;
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.samples.views.testbed.StarRating;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class StarRatingTest {
  @Test
  void shouldHoldTheStarsTheUserClicked(WebforjTestbed context) {
    context.add(new StarRating());

    var rating = ComponentLocator.of(StarRating.class).locate();

    UserInteraction.click(
        ComponentLocator.of(Button.class).byText("4").locate());

    verifyThat(rating).hasStars(4).isAttached();
  }

  @Test
  void shouldCheckTheRatingAndTheButtonInOneTest(WebforjTestbed context) {
    context.add(new StarRating());

    var rating = ComponentLocator.of(StarRating.class).locate();
    var four = ComponentLocator.of(Button.class).byText("4").locate();

    UserInteraction.click(four);

    verifyThat(rating).hasStars(4);
    verifyThat(four).hasText("4").isEnabled();
  }
}
```
