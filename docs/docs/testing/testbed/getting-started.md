---
sidebar_position: 1
title: Getting started
description: Add the TestBed dependency, write a first test that adds a view and acts on it, and run the test.
---

# Getting started <DocChip chip='since' label='26.03' />

The TestBed is webforJ's browserless testing: it runs views and components inside plain JUnit tests without a browser or a server. This page adds the dependency, writes a first test, and runs it.

The `@WebforjTest` annotation sets up a `WebforjTestbed` context for every test of a class. Inside a test, `ComponentLocator` finds components on the page and `UserInteraction` performs what a user does with them.

<!-- INTRO_END -->

## Adding the dependency {#adding-the-dependency}

The TestBed is provided by a separate module. Add it to your app with test scope:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-testbed-junit</artifactId>
  <scope>test</scope>
</dependency>
```

The version comes from the webforJ Bill of Materials (BOM), so the dependency declares none. A project created from an [archetype](/docs/introduction/getting-started) already imports the BOM. The module also brings in the JUnit Jupiter API.

:::info Spring Boot apps
Spring Boot apps use the `webforj-spring-testbed` module instead. See [Spring testing](/docs/integrations/spring/testing).
:::

## Writing a first test {#writing-a-first-test}

The following form greets the name the user types:

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

The test types a name, clicks **Greet**, and checks the greeting:

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

The test reads like the steps of a user:

1. `@WebforjTest` gives each test method a `WebforjTestbed`, the context the test runs in.
2. `context.add()` puts the form on the page and runs its full lifecycle, as adding it to a frame does in a running app.
3. `ComponentLocator` finds the field by its label and the button by its text. See [Finding components](./finding-components).
4. `UserInteraction` types and clicks the way a user does, so the listener of the button runs. See [User interaction](./user-interaction).
5. `verifyThat` checks the text the user would read. See [Assertions](./assertions).

## What `@WebforjTest` provides {#what-webforjtest-provides}

Every test of an annotated class runs in its own `WebforjTestbed`. The context is set up before each test and torn down after it, so each test starts from an empty page and no state carries over from the previous test.

A test reaches its context in one of two ways:

- Declare a `WebforjTestbed` parameter on the test method, as in the first test.
- Call `WebforjTestbed.getCurrent()`, for example from a method the tests of a class share.

`context.add()` accepts one or more components and adds them to the root frame of the context, which `context.getFrame()` returns.

Tests that navigate between routes instead of adding components set the `app`, `routeClasses` or `routePackages` attributes of `@WebforjTest`. See [Testing apps](./testing-apps).

## Running the tests {#running-the-tests}

TestBed tests are plain JUnit tests. Run them with the build tool, or from the IDE like any other JUnit test:

```bash title="Terminal"
mvn test
```
