---
sidebar_position: 4
title: Assertions
description: Check what the user would see on a component with verifyThat, and read failures that name the component and both values.
---

After a test acts, it checks what the user would see: the text on a button, whether a field can be edited, which item a list shows as selected. `verifyThat(component)` states these expectations in the same words, such as `isReadOnly()` or `hasText("Save")`. Each check reads the state of the component, the same state the browser shows, and a failed check names the component and both values.

<!-- INTRO_END -->

## Checking a component {#checking-a-component}

The following editor shows a name that can't be changed until the user clicks **Edit**. The click makes the field editable, moves the focus into it, and turns the button into **Save**:

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

The test checks the field before and after the click:

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

`verifyThat` comes from `com.webforj.testbed.assertion.WebforjAssertions` and is imported statically. Each check returns the assertion, so the checks on one component chain in one statement. They run in order, and the first one that fails ends the test.

Every state has a check for each side, such as `isReadOnly()` and `isEditable()`, or `isVisible()` and `isHidden()`, so a test states the expected state directly.

## What a check reads {#what-a-check-reads}

A check reads the component through a [concern interface](/docs/building-ui/component-fundamentals#concern-interfaces), so it works on every component that implements that interface, a component of your app included. `hasLabel` works on every component with a label, and `isReadOnly` on every component that can be read only. The checks cover:

- What the user reads: text, HTML content, label, placeholder, helper text and tooltip.
- The state of the component: visible, enabled, read only, required, valid and focused.
- The value of a field, and the items and selection of lists, tables and trees.
- What the app set: name, attributes, properties, style, theme and expanse.
- The components a container holds, and whether a component is attached or destroyed.

The <JavadocLink type="testbed-foundation" location="com/webforj/testbed/assertion/AbstractComponentAssert" code="true">AbstractComponentAssert</JavadocLink> reference lists every check with the interface it reads.

## Values {#values}

The following field computes a total from the quantity the user types:

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

The test types a quantity, then checks the value of the field and the total:

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

`hasValue` compares with `equals`, so the expected value has the type the field holds. A `NumberField` holds a `Double`, so the test expects `3.0`, not `3`.

:::info Value types
A `DateField` holds a `LocalDate`, and a `TimeField` a `LocalTime`. Expect a value of that type.
:::

## Child components {#child-components}

`hasComponentCount` checks how many components a container holds, and `hasComponent` checks that it holds a given one. The following editor adds each tag the user enters to a list of tags:

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

The test adds two tags and checks the list:

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

## Selection {#selection}

The selection checks read which item a list, a table or a tree shows as selected, by the item, its index, or its key. The following list offers three fruits:

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

The test checks that nothing is selected, picks a fruit, and checks the selection by key and by index:

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

On a component that allows several selected items, `hasSelected`, `hasSelectedIndex` and `hasSelectedKey` pass when the expected item is among them. `hasItemCount` checks the number of items of a component with a repository, such as a `Table`.

## Lifecycle {#lifecycle}

`isAttached()` checks that a component is on the page, and `isDestroyed()` that the app destroyed it. The following banner destroys itself when the user dismisses it:

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

The test dismisses the banner and checks it:

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

## Failure messages {#failure-messages}

A failed check throws an `AssertionError` that names the type of the component, the expected value, and the actual one. The following test makes a check fail on purpose and reads the message:

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

A check on a component that doesn't publish the state fails with a message that names the missing interface. A `Div` holds no value, so `hasValue` on a `Div` fails and names `ValueAware`.

## Checks for your own components {#checks-for-your-own-components}

A component of your app that implements concern interfaces gets their checks without extra code. For state that only your component knows, write an assertion class. The following rating counts the stars the user clicks and returns them from `getStars()`:

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

The assertion class extends `AbstractComponentAssert` and adds a check for the stars. A static `verifyThat` creates it, so a test calls it the same way as the built-in checks:

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

Inside a check, `actual` is the component, `failWithMessage` fails with a formatted message, `componentName()` names the component, and returning `myself` lets the checks chain. The new class adds checks, it replaces none. A test imports both `verifyThat` methods statically, and Java picks the one whose parameter fits the argument: a `StarRating` goes to `StarRatingAssert`, and every other component to `WebforjAssertions`. The built-in checks stay available on the rating too, such as `isAttached()`:

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
