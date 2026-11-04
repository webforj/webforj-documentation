---
sidebar_position: 4
title: Zusicherungen
description: >-
  Check what the user would see on a component with verifyThat, and read
  failures that name the component and both values.
_i18n_hash: 6414e2fab20d3bf7828413494af79b74
---
Nachdem ein Test eine Aktion ausgeführt hat, prüft er, was der Benutzer sehen würde: den Text auf einer Schaltfläche, ob ein Feld bearbeitet werden kann und welches Element in einer Liste als ausgewählt angezeigt wird. Mit `verifyThat(component)` werden diese Erwartungen in denselben Worten formuliert, zum Beispiel mit `isReadOnly()` oder `hasText("Save")`. Jede Prüfung liest den Zustand der Komponente aus – denselben Zustand, den der Browser anzeigt. Bei einer fehlgeschlagenen Prüfung werden die Komponente und beide Werte genannt.

<!-- INTRO_END -->

## Eine Komponente prüfen {#checking-a-component}

Der folgende Editor zeigt einen Namen an, der erst geändert werden kann, wenn der Benutzer auf **Edit** klickt. Durch den Klick wird das Feld bearbeitbar und fokussiert, und die Schaltfläche erhält den Text **Save**:

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

Der Test prüft das Feld vor und nach dem Klick:

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

`verifyThat` stammt aus `com.webforj.testbed.assertion.WebforjAssertions` und wird statisch importiert. Jede Prüfung gibt die Assertion zurück, sodass sich Prüfungen für dieselbe Komponente in einer Anweisung verketten lassen. Sie werden der Reihe nach ausgeführt. Sobald eine Prüfung fehlschlägt, wird der Test beendet.

Für jeden Zustand gibt es eine Prüfung für beide Seiten, zum Beispiel `isReadOnly()` und `isEditable()` oder `isVisible()` und `isHidden()`. So kann ein Test den erwarteten Zustand direkt angeben.

## Was eine Prüfung ausliest {#what-a-check-reads}

Eine Prüfung liest die Komponente über eine [Concern-Schnittstelle](/docs/building-ui/component-fundamentals#concern-interfaces) aus. Daher funktioniert sie mit jeder Komponente, die diese Schnittstelle implementiert, einschließlich einer Komponente deiner App. `hasLabel` funktioniert mit jeder Komponente, die ein Label hat, und `isReadOnly` mit jeder Komponente, die schreibgeschützt sein kann. Die Prüfungen decken Folgendes ab:

- Was der Benutzer liest: Text, HTML-Inhalt, Label, Platzhalter, Hilfetext und Tooltip.
- Den Zustand der Komponente: sichtbar, aktiviert, schreibgeschützt, erforderlich, gültig und fokussiert.
- Den Wert eines Felds sowie die Elemente und die Auswahl von Listen, Tabellen und Bäumen.
- Was die App festgelegt hat: Name, Attribute, Eigenschaften, Stil, Theme und Ausdehnung.
- Die Komponenten in einem Container sowie, ob eine Komponente angehängt oder zerstört ist.

Die Referenz <JavadocLink type="testbed-foundation" location="com/webforj/testbed/assertion/AbstractComponentAssert" code="true">AbstractComponentAssert</JavadocLink> führt alle Prüfungen und die jeweils ausgelesene Schnittstelle auf.

## Werte {#values}

Das folgende Feld berechnet aus der vom Benutzer eingegebenen Menge einen Gesamtbetrag:

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

Der Test gibt eine Menge ein und prüft anschließend den Feldwert und den Gesamtbetrag:

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

`hasValue` vergleicht mit `equals`, daher muss der erwartete Wert denselben Typ haben wie der Feldwert. Ein `NumberField` enthält einen `Double`, deshalb erwartet der Test `3.0` und nicht `3`.

:::info Werttypen
Ein `DateField` enthält einen `LocalDate` und ein `TimeField` einen `LocalTime`. Erwarte einen Wert dieses Typs.
:::

## Untergeordnete Komponenten {#child-components}

Mit `hasComponentCount` wird geprüft, wie viele Komponenten ein Container enthält, und mit `hasComponent`, ob er eine bestimmte Komponente enthält. Der folgende Editor fügt jedes vom Benutzer eingegebene Tag zu einer Tag-Liste hinzu:

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

Der Test fügt zwei Tags hinzu und prüft die Liste:

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

## Auswahl {#selection}

Mit Auswahlprüfungen wird ausgelesen, welches Element eine Liste, Tabelle oder ein Baum als ausgewählt anzeigt – anhand des Elements, seines Index oder seines Schlüssels. Die folgende Liste bietet drei Früchte an:

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

Der Test prüft, dass nichts ausgewählt ist, wählt eine Frucht aus und prüft die Auswahl anhand des Schlüssels und des Index:

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

Bei einer Komponente, die mehrere ausgewählte Elemente zulässt, sind `hasSelected`, `hasSelectedIndex` und `hasSelectedKey` erfolgreich, wenn das erwartete Element darunter ist. Mit `hasItemCount` wird die Anzahl der Elemente einer Komponente mit Repository geprüft, zum Beispiel einer `Table`.

## Lebenszyklus {#lifecycle}

Mit `isAttached()` wird geprüft, ob sich eine Komponente auf der Seite befindet, und mit `isDestroyed()`, ob die App sie zerstört hat. Das folgende Banner zerstört sich selbst, wenn der Benutzer es schließt:

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

Der Test schließt das Banner und prüft es:

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

## Fehlermeldungen {#failure-messages}

Eine fehlgeschlagene Prüfung wirft einen `AssertionError`, der den Komponententyp, den erwarteten Wert und den tatsächlichen Wert nennt. Der folgende Test lässt absichtlich eine Prüfung fehlschlagen und liest die Meldung aus:

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

Eine Prüfung für eine Komponente, die den Zustand nicht bereitstellt, schlägt mit einer Meldung fehl, die die fehlende Schnittstelle nennt. Ein `Div` enthält keinen Wert, daher schlägt `hasValue` bei einem `Div` fehl und nennt `ValueAware`.

## Prüfungen für eigene Komponenten {#checks-for-your-own-components}

Eine Komponente deiner App, die Concern-Schnittstellen implementiert, erhält die dazugehörigen Prüfungen ohne zusätzlichen Code. Für einen Zustand, den nur deine Komponente kennt, kannst du eine Assertion-Klasse schreiben. Die folgende Sternebewertung zählt die vom Benutzer angeklickten Sterne und gibt sie über `getStars()` zurück:

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

Die Assertion-Klasse erweitert `AbstractComponentAssert` und ergänzt eine Prüfung für die Sterne. Eine statische Methode `verifyThat` erstellt sie, sodass ein Test sie wie die integrierten Prüfungen aufruft:

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

Innerhalb einer Prüfung ist `actual` die Komponente, `failWithMessage` bricht mit einer formatierten Meldung ab, `componentName()` nennt die Komponente und die Rückgabe von `myself` ermöglicht das Verketten von Prüfungen. Die neue Klasse ergänzt Prüfungen, ersetzt aber keine. Ein Test importiert beide `verifyThat`-Methoden statisch. Java wählt die Methode aus, deren Parameter zum Argument passt: Ein `StarRating` wird an `StarRatingAssert` übergeben, alle anderen Komponenten an `WebforjAssertions`. Auch für die Sternebewertung bleiben die integrierten Prüfungen verfügbar, etwa `isAttached()`:

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
