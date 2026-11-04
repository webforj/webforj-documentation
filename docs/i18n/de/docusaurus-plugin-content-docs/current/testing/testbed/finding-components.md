---
sidebar_position: 2
title: Komponenten finden
description: >-
  Describe the components a TestBed test acts on the way a user picks them out
  on the page, by type, text, label and what they contain.
_i18n_hash: 3e31c9bc7b51ebd2ebaf1a5e44c6e94f
---
Bevor ein Benutzer auf eine Schaltfläche klickt, sucht er sie auf der Seite: die Schaltfläche **Save** oder das Feld mit der Beschriftung für die E-Mail-Adresse. Ein TestBed-Test findet Komponenten auf dieselbe Weise – mit `ComponentLocator`. Ein Locator beschreibt eine Komponente anhand ihres Typs und anhand dessen, was der Benutzer darauf liest. Wenn der Test das Ergebnis anfordert, durchsucht der Locator die Seite.

Ein Test greift nie über die Felder einer Ansicht auf ihre Komponenten zu. Er findet sie so, wie ein Benutzer es tun würde. Daher funktioniert der Test weiterhin, wenn die Ansicht anders aufgebaut ist – solange auf der Seite dieselben Dinge zu sehen sind.

<!-- INTRO_END -->

## Eine Komponente suchen {#locating-a-component}

`ComponentLocator.of(type)` beginnt eine Beschreibung mit dem Typ der Komponente. Filter wie `byText` grenzen die Suche ein, und eine abschließende Methode wie `locate()` durchsucht die Seite und gibt das Ergebnis zurück.

Das folgende Composite zählt die Klicks auf seine Schaltfläche **Add**:

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

Der Test findet die Schaltfläche anhand ihres Textes, klickt zweimal darauf und sucht anschließend den Absatz, der die Anzahl anzeigt:

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

Ein Locator durchsucht die Seite, sobald seine abschließende Methode ausgeführt wird. Daher findet die Suche nach den Klicks den neuen Text.

## Eine Komponente beschreiben {#describing-the-component}

Eine Beschreibung beginnt mit dem Typ und wird um Merkmale ergänzt, die die Komponente von anderen unterscheiden – in der Reihenfolge, in der ein Benutzer sie wahrnimmt: zuerst, was er auf der Komponente liest, dann, wie sie auf der Seite platziert ist. Jeder Filter behält nur Komponenten bei, die zu ihm passen. Filter lassen sich verketten, sodass eine Komponente alle Bedingungen erfüllen muss.

- `byText`, `byTextContaining`, `byLabel` und `byPlaceholder` gleichen mit dem ab, was der Benutzer auf der Komponente liest.
- `byValue`, `byTheme`, `byExpanse`, `byClassName`, `byStyle` und `byAttribute` gleichen mit den Werten ab, die die App für die Komponente festgelegt hat.
- `byName` gleicht mit dem Namen ab, den die App einer Komponente ohne Text gibt, etwa einer Schaltfläche, die nur ein Symbol enthält.
- `has` gleicht mit einer anderen Komponente innerhalb der Komponente ab.
- `filter` gleicht mit einer beliebigen Bedingung ab, die der Test vorgibt.

Ein Filter überspringt Komponenten, die das gesuchte Merkmal nicht haben. So überspringt `byLabel` beispielsweise ein `Div`, anstatt die Suche fehlschlagen zu lassen.

### Was der Benutzer liest {#what-the-user-reads}

Das folgende Anmeldeformular enthält ein E-Mail-Feld mit Beschriftung, ein Passwortfeld mit Platzhalter und eine Hilfe-Schaltfläche, die nur ein Symbol anzeigt:

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

Der Test findet die drei Komponenten anhand ihrer Beschriftung, ihres Platzhalters und ihres Namens, füllt die Felder aus und ruft die Hilfe auf:

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

### Was eine Komponente enthält {#what-a-component-contains}

Manche Komponenten lassen sich nur anhand ihres Inhalts unterscheiden, etwa Karten, die unterschiedliche Namen anzeigen. `has` behält eine Komponente bei, wenn ein anderer Locator innerhalb dieser Komponente einen Treffer findet. Das folgende Composite zeigt für jeden Kontakt eine Karte mit dem Namen als Titel, der E-Mail-Adresse als Untertitel und einer Schaltfläche **Delete**:

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

Der Test findet die Karte von Linus anhand des darin enthaltenen Namens und prüft anschließend, ob die E-Mail-Adresse auf dieser Karte seine ist:

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

Es zählen nur die Komponenten innerhalb einer Karte, niemals die Karte selbst.

### Beliebige andere Bedingung {#any-other-condition}

`filter` nimmt eine Bedingung für den Typ entgegen, nach dem der Locator sucht. Dadurch kann der Filter alles abfragen, was dieser Typ bereitstellt. Das folgende Formular enthält drei Felder, von denen zwei Pflichtfelder sind:

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

Der Test findet mit `filter` die Pflichtfelder und liest ihre Beschriftungen aus:

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

Die Referenz zu <JavadocLink type="automation" location="com/webforj/automation/find/ComponentLocator" code="true">ComponentLocator</JavadocLink> führt alle Filter und die Komponenten auf, die sie abfragen.

## Innerhalb einer Komponente suchen {#searching-inside-a-component}

Wenn ein Benutzer dieselbe Schaltfläche zweimal findet, sieht er sich den Bereich der Seite rund um die gesuchte Schaltfläche an. `within(component)` macht dasselbe und beschränkt die Suche auf diese Komponente und die darin enthaltenen Komponenten.

Beide [Kontaktkarten](#what-a-component-contains) haben eine Schaltfläche **Delete**, sodass `byText("Delete")` allein zwei Schaltflächen findet. Der folgende Test sucht nur in der ersten Karte:

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

Nur die erste Karte wird entfernt. Die Karte von Linus bleibt bestehen.

## Das Ergebnis auslesen {#reading-the-result}

Die abschließende Methode bestimmt, was der Test zurückgibt. `locate()` gibt die eine passende Komponente zurück. `all()` gibt alle Treffer in der Reihenfolge auf der Seite zurück, `first()` und `last()` den ersten beziehungsweise letzten Treffer, `count()` die Anzahl der Treffer und `isPresent()` gibt an, ob es überhaupt einen Treffer gibt.

Die folgende Liste zeigt für jeden Eintrag ein Kontrollkästchen:

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

Der Test liest mit `all()` alle Einträge aus, mit `first()` und `last()` den ersten und letzten Eintrag und mit `count()` die Anzahl der Einträge:

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

:::tip Zerstörte Komponenten
Eine von der App zerstörte Komponente befindet sich nicht mehr auf der Seite. Daher findet kein Locator sie – unabhängig davon, mit welcher Methode die Suche abgeschlossen wird.
:::

## Wenn die Beschreibung nicht eindeutig ist {#when-the-description-is-wrong}

`locate()` erwartet genau einen Treffer. Passt eine Beschreibung auf keine oder mehrere Komponenten, schlägt die Suche fehl, anstatt willkürlich eine Komponente auszuwählen. Beide [Kontaktkarten](#what-a-component-contains) haben eine Schaltfläche **Delete**. Der folgende Test beschreibt eine Schaltfläche nur anhand ihres Textes und liest die Fehlermeldung aus:

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

Die Meldung nennt den Typ und die Anzahl der Treffer, gefolgt vom Komponentenbaum, den der Locator durchsucht hat. Der Baum zeigt die beiden Schaltflächen **Delete**, eine in jeder Karte. Die Lösung besteht also darin, die Suche mit [within](#searching-inside-a-component) auf eine Karte zu beschränken.

## Composites {#composites}

Ein Locator findet ein `Composite` sowohl anhand seines eigenen Typs als auch anhand des Typs der Komponente, an die es gebunden ist. Das folgende Composite ist ein E-Mail-Feld. Es bindet ein `TextField`, versieht es mit einer Beschriftung und einem Platzhalter und gibt die eingegebene Adresse mit `getAddress()` zurück:

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

Der Test findet das Composite anhand seines eigenen Typs und das gebundene Feld anhand des Typs `TextField` und seiner Beschriftung. Er gibt Text in das Feld ein und liest die Adresse über das Composite aus:

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

Wenn ein Composite an ein weiteres Composite gebunden ist, findet jeder Typ in dieser Kette das Composite.
