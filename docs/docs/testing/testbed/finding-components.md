---
sidebar_position: 2
title: Finding components
description: Describe the components a TestBed test acts on the way a user picks them out on the page, by type, text, label and what they contain.
---

Before a user clicks a button, they find it on the page: the **Save** button, or the field labeled for the email address. A TestBed test finds components the same way, with `ComponentLocator`. A locator describes a component by its type and by what the user reads on it, and searches the page when the test asks for the result.

A test never holds on to the fields of a view to reach its components. It finds them the way a user does, so the test keeps working when the view is built differently, as long as the page shows the same things.

<!-- INTRO_END -->

## Locating a component {#locating-a-component}

`ComponentLocator.of(type)` starts a description with the type of the component. Filters such as `byText` narrow it, and a final method such as `locate()` searches the page and returns the result.

The following composite counts the clicks on its **Add** button:

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

The test finds the button by its text, clicks it twice, and then finds the paragraph that shows the count:

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

A locator searches the page when its final method runs, so the search after the clicks finds the new text.

## Describing the component {#describing-the-component}

A description starts from the type and adds what sets the component apart, in the order a user would: first what the user reads on it, then how it's placed on the page. Every filter keeps only the components that match it, and filters chain, so a component must match all of them.

- `byText`, `byTextContaining`, `byLabel` and `byPlaceholder` match what the user reads on the component.
- `byValue`, `byTheme`, `byExpanse`, `byClassName`, `byStyle` and `byAttribute` match what the app set on the component.
- `byName` matches the name the app gives a component that shows no text, such as a button with only an icon.
- `has` matches a component by another component inside it.
- `filter` matches any condition the test writes.

A filter skips components that don't have what it reads, so `byLabel` passes over a `Div` instead of failing the search.

### What the user reads {#what-the-user-reads}

The following sign in form has an email field with a label, a password field with a placeholder, and a help button that shows only an icon:

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

The test finds the three components by label, placeholder and name, fills in the fields, and asks for help:

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

### What a component contains {#what-a-component-contains}

Some components are told apart only by what's inside them, such as cards that differ in the name they show. `has` keeps a component when another locator finds a match inside it. The following composite shows one card per contact, with the name as the title, the email as the caption, and a **Delete** button:

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

The test finds the card of Linus by the name inside it, then checks that the email on that card is his:

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

Only the components inside a card count, never the card itself.

### Any other condition {#any-other-condition}

`filter` takes a condition on the type the locator searches for, so it can read anything that type offers. The following form has three fields, and two of them are required:

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

The test finds the required fields with `filter` and reads their labels:

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

The <JavadocLink type="automation" location="com/webforj/automation/find/ComponentLocator" code="true">ComponentLocator</JavadocLink> reference lists every filter with the components it reads.

## Searching inside a component {#searching-inside-a-component}

A user who finds the same button twice looks at the part of the page around the one they want. `within(component)` does the same and limits the search to that component and the components inside it.

Both [contact cards](#what-a-component-contains) have a **Delete** button, so `byText("Delete")` alone matches two buttons. The following test searches only the first card:

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

Only the first card is removed, and the card of Linus stays.

## Reading the result {#reading-the-result}

The final method decides what the test gets back. `locate()` returns the one component that matches. `all()` returns every match in the order of the page, `first()` and `last()` the first and the last match, `count()` the number of matches, and `isPresent()` whether anything matches.

The following list shows one checkbox per item:

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

The test reads every item with `all()`, the first and the last with `first()` and `last()`, and the number of items with `count()`:

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

:::tip Destroyed components
A component the app destroyed is no longer on the page, so no locator finds it, whichever method ends the search.
:::

## When the description is wrong {#when-the-description-is-wrong}

`locate()` expects exactly one match, so a description that fits no component, or several, fails instead of picking one at random. Both [contact cards](#what-a-component-contains) have a **Delete** button. The following test describes a button by its text alone and reads the failure:

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

The message names the type and the number of matches, followed by the component tree the locator searched. The tree shows the two **Delete** buttons, one in each card, so the fix is to search [within](#searching-inside-a-component) one card.

## Composites {#composites}

A locator finds a `Composite` by its own type, and also by the type of the component it binds. The following composite is an email field. It binds a `TextField`, gives it a label and a placeholder, and returns the typed address with `getAddress()`:

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

The test finds the composite by its own type and the field it binds by `TextField` and its label. It types into the field and reads the address through the composite:

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

When a composite binds another composite, each type in that chain finds it.
