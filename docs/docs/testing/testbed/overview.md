---
sidebar_position: 0
title: TestBed
hide_table_of_contents: true
hide_giscus_comments: true
description: The TestBed is webforJ's browserless testing, which runs views and components in plain JUnit tests without a browser or a server.
sidebar_class_name: new-content
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale Google.Headings = NO -->
# TestBed <DocChip chip='since' label='26.03' />
<!-- vale Google.Headings = YES -->

Testing a webforJ view usually means starting the app, opening a browser and driving it with Selenium or Playwright. That catches what only a browser shows, but every test pays for the browser, the server, and the waiting in between.

The TestBed is webforJ's browserless testing. A view runs inside a plain JUnit test. No browser is opened and no server is started. The test adds the view, acts on it the way a user does, and checks the result from Java. Components attach, fire their events, and navigate exactly as they do in a running app, and every interaction finishes before the next line of the test runs, so nothing waits and nothing flakes.

## How a test works {#how-a-test-works}

A TestBed test follows the steps of a user. It finds a component on the page, acts on it, and checks what the page shows afterwards. The following form subscribes an email address, and its **Subscribe** button stays disabled until the field holds one:

```java title="NewsletterForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class NewsletterForm extends Composite<Div> {

  public NewsletterForm() {
    TextField email = new TextField("Email");
    Paragraph status = new Paragraph();
    Button subscribe =
        new Button(
            "Subscribe", e -> status.setText("Subscribed " + email.getValue()));
    subscribe.setEnabled(false);
    email.onModify(e -> subscribe.setEnabled(!email.getValue().isBlank()));

    getBoundComponent().add(email, subscribe, status);
  }
}
```

The test finds the field and the button, types an address, clicks, and checks the confirmation:

```java title="NewsletterFormTest.java" showLineNumbers
@Test
void shouldSubscribeTheEmailTheUserTyped(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
  var subscribe = ComponentLocator.of(Button.class)
      .byText("Subscribe")
      .locate();

  UserInteraction.type(email, "ada@example.com");
  UserInteraction.click(subscribe);

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Subscribed ada@example.com")
          .isPresent());
}
```

Each step has its own part of the TestBed:

- **Finding.** `ComponentLocator` describes a component the way a user picks it out, by its type and what it shows, such as a field by its label. See [Finding components](./finding-components).
- **Acting.** `UserInteraction` makes the gestures of a user, and only the ones a user could make. Clicking a disabled button fails the test instead of running its listener. See [User interaction](./user-interaction).
- **Checking.** `verifyThat` checks what the user would see on a component, and a snapshot compares the whole view against a stored description of it. See [Assertions](./assertions) and [Snapshots](./snapshots).

The test never reaches into the fields of the view. It works through what the page shows, so it keeps passing when the view is built differently and fails when the user would see something else.

Beyond single views, the TestBed navigates routes, tests secured views, answers dialogs and runs timers, as [Testing apps](./testing-apps) shows, and runs inside a Spring Boot app with its beans and security.

Browser tests still have their place for what only a browser can show, such as styling, layout, and client behavior. The TestBed covers the rest: what your views do when a user works with them.

Start with [Getting started](./getting-started) to add the dependency and write a first test.

## Topics {#topics}

<DocCardList className="topics-section" />
