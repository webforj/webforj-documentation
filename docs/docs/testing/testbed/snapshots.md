---
sidebar_position: 5
title: Snapshots
description: Compare everything a view shows with a stored text description of it, read that description, and add the state of your own components to it.
---

Checking a view fact by fact takes one assertion per label, button, and field. A snapshot checks all of it at once. It writes down everything a user would see in the view, one line per component, and stores that description next to the test. Every later run describes the view again and compares, so any change to what the page shows fails the test, and the change shows up as a plain text diff in code review.

<!-- INTRO_END -->

## Matching a snapshot {#matching-a-snapshot}

The following summary shows an order number, a note field, a discount line that's hidden, a **Pay** button with an icon, and a disabled **Cancel** button:

```java title="OrderSummary.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.TablerIcon;

public class OrderSummary extends Composite<Div> {

  public OrderSummary() {
    Paragraph order = new Paragraph("Order 1042");
    TextField note = new TextField("Note")
        .setPlaceholder("Delivery instructions");
    Paragraph discount = new Paragraph("Discount applied");
    discount.setVisible(false);
    Button pay = new Button("Pay", ButtonTheme.PRIMARY);
    pay.setPrefixComponent(TablerIcon.create("credit-card"));
    Button cancel = new Button("Cancel");
    cancel.setEnabled(false);

    getBoundComponent().add(order, note, discount, pay, cancel);
  }
}
```

The test adds the summary and checks it with `matchesSnapshot()`:

```java title="OrderSummaryTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.samples.views.testbed.OrderSummary;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class OrderSummaryTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var summary = new OrderSummary();
    context.add(summary);

    verifyThat(summary).matchesSnapshot();
  }
}
```

The first run writes the description to a file and passes. Every later run fails when the view no longer matches the stored file:

```snapshot title="OrderSummaryTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- OrderSummary [composite=Div] [node=div]:
  - Paragraph "Order 1042" [composite=Element] [node=p]
  - TextField [label=Note] [placeholder="Delivery instructions"]
  - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
  - Button "Pay" [theme=primary]:
    - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
  - Button "Cancel" [disabled]
```

`matchesSnapshot` is a check of `verifyThat` like the ones on the [Assertions](./assertions) page, so it works on any component, not only on a view.

:::tip When to use snapshots
A snapshot pins a whole view. Use it for views whose content shouldn't change, and check single facts, such as the text of a button after a click, with [assertions](./assertions).
:::

## Reading a snapshot {#reading-a-snapshot}

Each line of the [stored snapshot](#matching-a-snapshot) describes one component. The components it holds follow below it, indented by two spaces, and a line that holds others ends in a colon. A line reads in this order:

1. The type of the component, such as `Button`.
2. The text in double quotes, when the component shows one, such as `"Pay"`.
3. Attributes as `[key=value]`, such as `[theme=primary]` or `[label=Note]`.
4. Flags as `[key]`, such as `[disabled]` or `[hidden]`.

A line prints only what differs from the default of the component, so the summary reads at a glance. **Cancel** carries `[disabled]`, and the enabled **Pay** button carries nothing about its state. **Pay** carries `[theme=primary]`, and a button with the default theme carries no theme. Sizes, styles, and class names never print.

Some attributes tell where a component sits:

- `slot` names the part of its parent a component fills. The icon of **Pay** sits before the text, so it reads `[slot=prefix]`.
- `composite` names the component a `Composite` binds, and `node` the element it renders. The summary binds a `Div`, so it reads `[composite=Div] [node=div]`.
- `route` names the route a view is registered under.
- `hidden` marks a hidden component, such as the discount line. Hiding or showing a component changes the snapshot.

Text is always quoted. An attribute value is quoted when it contains anything besides letters, digits and `.`, `_`, `+`, `/` or `-`, as in `[placeholder="Delivery instructions"]`.

## Long lists {#long-lists}

A long list would bury the rest of the view, so the items of a list, the rows of a table and the nodes of a tree print up to ten lines under their parent. The following list holds twelve countries:

```java title="CountryList.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ListBox;
import java.util.List;

public class CountryList extends Composite<Div> {

  public CountryList() {
    ListBox countries = new ListBox("Country");
    for (String country :
        List.of(
            "Austria", "Belgium", "Canada", "Denmark", "Egypt", "France",
            "Germany", "Hungary",
            "India", "Japan", "Kenya", "Mexico", "Hyyan")) {
      countries.add(country);
    }

    getBoundComponent().add(countries);
  }
}
```

```java title="CountryListTest.java" showLineNumbers
@Test
void shouldMatchTheSnapshot(WebforjTestbed context) {
  var list = new CountryList();
  context.add(list);

  verifyThat(list).matchesSnapshot();
}
```

The stored snapshot prints the first ten countries and a line for the two that follow:

```snapshot title="CountryListTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- CountryList [composite=Div] [node=div]:
  - ListBox [label=Country]:
    - ListItem "Austria" [key=Austria]
    - ListItem "Belgium" [key=Belgium]
    - ListItem "Canada" [key=Canada]
    - ListItem "Denmark" [key=Denmark]
    - ListItem "Egypt" [key=Egypt]
    - ListItem "France" [key=France]
    - ListItem "Germany" [key=Germany]
    - ListItem "Hungary" [key=Hungary]
    - ListItem "India" [key=India]
    - ListItem "Japan" [key=Japan]
    - … 3 more
```

To store more items, pass the limit in [snapshot options](#snapshot-options) with `setMaxWidth`. `matchesSnapshot(name, options)` renders with exactly the options it receives, so the test turns the other three settings of a stored snapshot on as well, and names the snapshot:

```java title="CountryListTest.java" showLineNumbers
@Test
void shouldMatchTheSnapshotOfEveryCountry(WebforjTestbed context) {
  var list = new CountryList();
  context.add(list);

  verifyThat(list)
      .matchesSnapshot(
          "every-country",
          new SnapshotOptions()
              .setMaxWidth(20)
              .setBrowserState(true)
              .setHiddenComponents(true)
              .setComposites(true));
}
```

The stored snapshot holds all twelve countries, in a file named after the snapshot:

```snapshot title="CountryListTest.every-country.yml" showLineNumbers
# webforJ testbed snapshot v1
- CountryList [composite=Div] [node=div]:
  - ListBox [label=Country]:
    - ListItem "Austria" [key=Austria]
    - ListItem "Belgium" [key=Belgium]
    - ListItem "Canada" [key=Canada]
    - ListItem "Denmark" [key=Denmark]
    - ListItem "Egypt" [key=Egypt]
    - ListItem "France" [key=France]
    - ListItem "Germany" [key=Germany]
    - ListItem "Hungary" [key=Hungary]
    - ListItem "India" [key=India]
    - ListItem "Japan" [key=Japan]
    - ListItem "Kenya" [key=Kenya]
    - ListItem "Mexico" [key=Mexico]
    - ListItem "Hyyan" [key=Hyyan]
```

:::tip Tables and trees
A table doesn't count the items of its repository, so it ends with a bare `- …` instead of a count. The limit applies to each parent on its own, so a tree cuts every level at ten nodes.
:::

## Where snapshots are stored {#where-snapshots-are-stored}

Snapshot files live under `src/test/resources/webforj-snapshots`, in the folders of the package of the test class. Each file is named after the test class, followed by the snapshot name and `.yml`:

```text title="src/test/resources"
src/test/resources/webforj-snapshots/
  com/webforj/samples/unit/views/testbed/
    CountryListTest.shouldMatchTheSnapshot.yml
    OrderSummaryTest.shouldMatchTheSnapshot.yml
```

The files belong in version control next to the tests. The first line of each file names the format version. A file written with another version fails with a message that names both versions instead of a diff.

## Naming snapshots {#naming-snapshots}

`matchesSnapshot()` names the snapshot after the running test method, so the snapshot of `shouldMatchTheSnapshot` in `OrderSummaryTest` lands in `OrderSummaryTest.shouldMatchTheSnapshot.yml`. A test that checks more than one snapshot passes a name to each with `matchesSnapshot(name)`, and the name takes the place of the method in the filename. A name keeps letters, digits, spaces, dots, hyphens, and the `_` character, and every other character becomes a hyphen, so `save/form:v2` is stored as `save-form-v2`.

`matchesSnapshot(name, options)` also takes the [`SnapshotOptions`](#snapshot-options) to render with, for example to store more rows of a table than the default.

## When a snapshot doesn't match {#when-a-snapshot-doesnt-match}

When the view no longer matches the stored file, the check fails and prints what changed. After the text of the **Pay** button in the [order summary](#matching-a-snapshot) changes to **Pay now**, `mvn test` reports:

```diff title="Terminal"
java.lang.AssertionError: 
Snapshot <shouldMatchTheSnapshot> does not match src/test/resources/webforj-snapshots/…/OrderSummaryTest.shouldMatchTheSnapshot.yml.
First difference at line 5.
--- stored
+++ actual
@@ -3,5 +3,5 @@
   - TextField [label=Note] [placeholder="Delivery instructions"]
   - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
-  - Button "Pay" [theme=primary]:
+  - Button "Pay now" [theme=primary]:
     - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
   - Button "Cancel" [disabled]
Actual:
- OrderSummary [composite=Div] [node=div]:
  - Paragraph "Order 1042" [composite=Element] [node=p]
  - TextField [label=Note] [placeholder="Delivery instructions"]
  - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
  - Button "Pay now" [theme=primary]:
    - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
  - Button "Cancel" [disabled]
Run with -Dwebforj.testbed.updateSnapshots=true to update the snapshot, or verify more than one snapshot in the same test with explicit names.
```

## Updating snapshots {#updating-snapshots}

When a change to a view is intended, run the tests once with the `webforj.testbed.updateSnapshots` system property set to `true`. Every snapshot the run checks is written again, and the snapshot files no test checks anymore are deleted. Review the diff of the files before you commit them.

```bash title="Terminal"
mvn test -Dwebforj.testbed.updateSnapshots=true
```

## Obsolete snapshots {#obsolete-snapshots}

After every test of a class ran, the TestBed looks for snapshot files of that class that none of its tests checked, for example the file of a renamed test. Such a file fails the test class with a message that lists each of these files and asks you to delete them, or to run with `-Dwebforj.testbed.updateSnapshots=true`, which deletes them.

:::info Single test methods
The check runs only when every test of the class ran. A single test method started from the IDE skips it.
:::

## Continuous integration {#continuous-integration}

Snapshots are written locally and committed. When the `CI` environment variable is set to a value other than `false` or `0`, as most continuous integration services do, the TestBed never writes a snapshot file:

- A missing snapshot fails the test with a message that names the expected file, instead of writing it.
- A run with `-Dwebforj.testbed.updateSnapshots=true` fails and asks you to update the snapshots locally.

## Printing a component {#printing-a-component}

`Snapshot.render(component)` returns the description of a component as a string, without a file. Print it while you write a test to see what the page holds. It leaves out the state the browser holds, such as the value a user typed, and hidden components, unless the options it receives include them.

## Snapshot options {#snapshot-options}

`SnapshotOptions` decides how much a description includes. `setMaxWidth` sets how many items print under a parent, ten by default. `setBrowserState` adds the state the browser holds, such as the value a user typed and the focus. `setHiddenComponents` adds hidden components with the `hidden` flag, and `setComposites` adds `composite` and `node`.

A stored snapshot includes everything: the browser state, the hidden components, and the composites. `Snapshot.render` and `new SnapshotOptions()` include none of them. `matchesSnapshot(name, options)` renders with exactly the options it receives, so to store more items, set the larger `maxWidth` and turn the other three settings on. The <JavadocLink type="automation" location="com/webforj/automation/see/SnapshotOptions" code="true">SnapshotOptions</JavadocLink> reference describes each setting.

## Snapshots for your own components {#snapshots-for-your-own-components}

A component of your app prints its type, its text, and the state of every [concern interface](/docs/building-ui/component-fundamentals#concern-interfaces) it implements, such as a label or a theme, without extra code. State that only your component knows needs a `SnapshotSerializer`. The following rating counts the stars the user clicks and returns them from `getStars()`:

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

The serializer names the component class it describes in `getContract()`, and adds the stars to the line of the component in `describe`:

```java title="StarRatingSerializer.java" showLineNumbers
import com.webforj.automation.see.SnapshotNode;
import com.webforj.automation.see.SnapshotSerializer;
import com.webforj.samples.views.testbed.StarRating;

public final class StarRatingSerializer
    implements SnapshotSerializer<StarRating> {

  @Override
  public Class<StarRating> getContract() {
    return StarRating.class;
  }

  @Override
  public void describe(StarRating rating, SnapshotNode node) {
    node.setAttribute("stars", rating.getStars());
  }
}
```

The serializer is listed by its fully qualified name in a file under the test resources:

```text title="src/test/resources/META-INF/services/com.webforj.automation.see.SnapshotSerializer"
com.webforj.samples.unit.views.testbed.StarRatingSerializer
```

Every snapshot of the test run then uses it. The following test clicks four stars and checks the snapshot:

```java title="StarRatingSnapshotTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.samples.views.testbed.StarRating;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class StarRatingSnapshotTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var rating = new StarRating();
    context.add(rating);

    UserInteraction.click(
        ComponentLocator.of(Button.class).byText("4").locate());

    verifyThat(rating).matchesSnapshot();
  }
}
```

The line of the rating carries the stars:

```snapshot title="StarRatingSnapshotTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- StarRating [composite=Div] [node=div] [stars=4]:
  - Button "1"
  - Button "2"
  - Button "3"
  - Button "4"
  - Button "5"
```

Besides `setAttribute`, the `SnapshotNode` a serializer receives sets the text of the line and its flags, and adds lines below it for parts of the component, such as the columns of a table and its rows. A serializer reads state the browser holds only when `isBrowserState()` returns `true`. The <JavadocLink type="automation" location="com/webforj/automation/see/SnapshotNode" code="true">SnapshotNode</JavadocLink> reference lists every method.

A serializer of your app runs after the ones of webforJ, so an attribute it sets replaces the value webforJ set for the same key. Two serializers for the same component class, or two that write the same attribute on the same line, fail the snapshot with a message that names both classes.
