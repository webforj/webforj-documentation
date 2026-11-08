---
title: "Java web app testing strategy: what to test, and where"
description: "A Java web app testing strategy for webforJ: JUnit for logic, TestBed for views without a browser, Bun for frontend code, and a few browser tests for journeys."
slug: java-web-app-testing-strategy
date: 2026-11-06
authors: Eric Handtke
tags: [tutorial, integrations]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-11-06-java-web-app-testing-strategy/cover-v2.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-11-06-java-web-app-testing-strategy/cover-v2.jpg)

Your app works on your machine. Then a refactor lands, a button stops doing its job, and nobody notices until a user does. Most teams know they need tests. The hard part is deciding which kind goes where, so the suite stays fast and people still trust a red build.

A Java web app testing strategy answers that question layer by layer. In webforJ every layer runs from JUnit in one Maven build, and the newest one tests your views without opening a browser at all.

<!-- truncate -->

This guide walks through the four layers, what each one catches, and how to keep the slow layer small.

## The testing pyramid for a Java UI {#testing-pyramid}

The classic testing pyramid puts many fast tests at the bottom and a few slow ones at the top. The idea holds for a Java UI. In webforJ the UI is written in Java, so the line between "logic" and "view" is a design choice, not a language boundary.

webforJ [testing](/docs/testing/overview) gives you four layers:

1. **Unit tests** check business logic with JUnit.
2. **TestBed tests** run views and components in plain JUnit, with no browser and no server.
3. **Frontend tests** cover TypeScript or JavaScript sources you author with the frontend bundler.
4. **End-to-end tests** drive a real browser against the running app with Selenium or Playwright.

Each layer answers one question. Is this rule correct? Does this view behave when a user works with it? Does this client code behave? Does the page look and work right in a real browser?

## Unit tests: keep logic out of views {#unit-tests}

Most bugs in a business app live in rules: pricing, validation, eligibility, state transitions. None of them need a view. Put them in a plain Java class and a JUnit test checks them in milliseconds.

Keep views thin. A view builds components and wires events. A service decides the answer. The [MVC post](/blog/webforj-mvc) shows this split in a real webforJ app.

Here is a small service with a rule worth testing:

```java title="DiscountService.java"
public class DiscountService {

  public BigDecimal apply(BigDecimal total, String code) {
    if ("SAVE10".equals(code)) {
      return total.multiply(new BigDecimal("0.90")).setScale(2, RoundingMode.HALF_UP);
    }
    return total;
  }
}
```

And the JUnit test that pins its behavior:

```java title="DiscountServiceTest.java"
class DiscountServiceTest {

  private final DiscountService service = new DiscountService();

  @Test
  void knownCodeTakesTenPercentOff() {
    assertEquals(new BigDecimal("90.00"), service.apply(new BigDecimal("100.00"), "SAVE10"));
  }

  @Test
  void unknownCodeLeavesTotalUnchanged() {
    assertEquals(new BigDecimal("100.00"), service.apply(new BigDecimal("100.00"), "NOPE"));
  }
}
```

No server, no browser, no setup. Hundreds of tests like this finish in seconds. JUnit 5 runs them, Mockito stands in for repositories, and Spring's test support helps when the service is a bean.

## Test views without a browser {#testbed-tests}

A unit test can't tell you that the **Apply code** button is wired to the right handler. That used to mean a browser test. Since 26.03 the [TestBed](/docs/testing/testbed/overview) covers it in plain JUnit. The view runs inside the test, components fire their events as they do in the running app, and every interaction finishes before the next line runs. Nothing waits and nothing flakes.

Here is a checkout view that uses the service:

```java title="CheckoutView.java"
public class CheckoutView extends Composite<Div> {

  private final DiscountService discounts = new DiscountService();

  public CheckoutView() {
    TextField code = new TextField("Discount code");
    Paragraph total = new Paragraph("Total: 100.00");
    Button apply = new Button("Apply code",
        e -> total.setText("Total: " + discounts.apply(new BigDecimal("100.00"), code.getValue())));

    getBoundComponent().add(code, apply, total);
  }
}
```

The test does what a user does. It finds the field by its label, types a code, clicks the button, and checks the total:

```java title="CheckoutViewTest.java"
@WebforjTest
class CheckoutViewTest {

  @Test
  void discountCodeUpdatesTheTotal(WebforjTestbed context) {
    context.add(new CheckoutView());

    var code = ComponentLocator.of(TextField.class).byLabel("Discount code").locate();
    var apply = ComponentLocator.of(Button.class).byText("Apply code").locate();

    UserInteraction.type(code, "SAVE10");
    UserInteraction.click(apply);

    verifyThat(ComponentLocator.of(Paragraph.class).locate()).hasText("Total: 90.00");
  }
}
```

The app isn't running and no browser opens. The test only needs the `webforj-testbed-junit` dependency with test scope, and the [getting started page](/docs/testing/testbed/getting-started) shows the setup. Beyond single views, the TestBed navigates routes, tests secured views, answers dialogs and runs timers, as [testing apps](/docs/testing/testbed/testing-apps) shows. Spring Boot apps use the `webforj-spring-testbed` module instead.

This layer moves most UI checks out of the browser. The full walkthrough is in [Java UI testing without a browser](/blog/java-ui-testing-without-browser).

## Frontend tests: cover your client code {#frontend-tests}

Some apps ship their own client code, such as a custom element written in TypeScript or a helper that formats values in the browser. When a project authors those sources with the [frontend bundler](/docs/managing-resources/bundler/overview), webforJ tests them with the [Bun](https://bun.sh/) test runner.

Tests live under `src/main/frontend` next to the code they cover. A file named `*.test.ts` or `*.spec.ts` is picked up as a test, and it imports `expect`, `test` and `describe` from `bun:test`. These tests run in the same build as the Java tests, and a failing one fails the build. The [frontend testing page](/docs/testing/frontend-testing) has the layout and a runnable example, and the [build and tests page](/docs/managing-resources/bundler/build-and-tests#frontend-tests) explains the test step.

No custom frontend sources? Skip this layer. Plenty of webforJ apps are pure Java.

## Browser tests for key journeys {#end-to-end-tests}

Some questions only a real browser can answer: styling, layout, and how the page behaves on the client. That's the end-to-end layer. It confirms the server, the components, and the browser work together.

Java teams usually pick [Playwright](/docs/testing/e2e/playwright) or [Selenium](/docs/testing/e2e/selenium). Both run from JUnit, so they fit the same `mvn test` flow. Here is a Playwright test in the shape the docs use, filling a field, clicking a button and checking the toast webforJ renders:

```java title="CheckoutJourneyTest.java"
class CheckoutJourneyTest {

  static Playwright playwright;
  static Browser browser;

  @BeforeAll
  static void launch() {
    playwright = Playwright.create();
    browser = playwright.chromium().launch();
  }

  @AfterAll
  static void close() {
    playwright.close();
  }

  @Test
  void discountCodeShowsConfirmation() {
    Page page = browser.newPage();
    page.navigate("http://localhost:8080/checkout");

    page.locator("input").fill("SAVE10");
    page.getByText("Apply code").click();

    assertThat(page.locator("dwc-toast").first()).containsText("Discount applied");
  }
}
```

The app has to be running for this test to pass, so start it with `mvn jetty:run`, then run `mvn test`. That setup cost is why this layer stays small.

## Which test goes where {#which-test-goes-where}

Use this table when you're unsure where a new test belongs.

| What you want to check | Layer | Why |
| --- | --- | --- |
| A pricing, validation or eligibility rule | JUnit unit test | Fast, exact failure |
| A service with mocked collaborators | JUnit unit test | Plain Java, standard tooling |
| A button runs the right handler and the view updates | TestBed test | No browser, no server |
| A route loads, a secured view blocks, a dialog answers | TestBed test | Runs the real components in JUnit |
| A TypeScript helper or custom element logic | Bun frontend test | Runs in the same Maven build |
| Layout and styling in different browsers or devices | End-to-end test | Only a real browser can answer it |
| One full journey through the deployed stack | End-to-end test | Checks server and browser together |

Rule of thumb: push every check as far down as it can go. If the TestBed can answer it, don't open a browser for it.

## Keep the end-to-end layer small {#keep-e2e-small}

A common mistake is inverting the pyramid: a few unit tests and a long list of slow browser tests. The suite takes ten minutes, flakes on timing, and people stop trusting red builds.

Four habits keep the top layer healthy:

1. **One test per journey, not per field.** Cover sign in, create an order, and the other paths that would hurt if they broke.
2. **Move view behavior to the TestBed.** Clicks, field values and navigation don't need a browser anymore.
3. **Use stable locators.** Prefer visible text and roles over deep CSS paths that break when markup changes.
4. **Run it last.** Let the fast layers fail first, so a broken rule never costs you a browser launch.

## Why this works well in webforJ {#why-webforj}

One language and one build make the strategy cheap to follow. The UI, the services and the tests are all Java. The developer who writes a view also writes its unit test, its TestBed test and its Playwright test, without switching context. Frontend tests join the same `mvn test` run when the project has client code. One command, one report, one place for CI to look.

There's no REST contract between a client and a server to test separately, and no second test stack that drifts apart from the first.

## Add a TestBed test today {#add-a-testbed-test}

A short checklist to start:

- Move one rule out of a view into a service and pin it with a JUnit test.
- Add `webforj-testbed-junit` with test scope and write one TestBed test for the view that uses it.
- Keep a single Playwright test for the journey end to end.
- Run `mvn test` and watch all three pass in one build.

The [testing overview](/docs/testing/overview) lists every testing page, and [getting started](/docs/introduction/getting-started) creates a project you can try this in.

## Frequently asked questions {#faq}

### How do you test a Java web UI? {#best-way-to-test-java-web-ui}

Split the work by what each test can answer. Test rules with JUnit, test views with the TestBed, test client code with the Bun runner, and keep a short list of Playwright or Selenium tests for what only a real browser shows. Most checks stay fast, and the slow ones cover what only a browser can answer.

### Should I rely on unit tests or end-to-end tests? {#unit-vs-e2e}

Rely on both, in proportion, with the TestBed in between. Unit and TestBed tests should be the large majority because they're fast and point at the exact failure. End-to-end tests should be few, and each should protect a journey a user would notice breaking.

### Do I need a browser to test a webforJ view? {#browser-for-views}

No. The TestBed runs a view inside a JUnit test, finds components the way a user picks them out, clicks and types, and checks the result. You need a browser only for styling, layout, and client behavior.
