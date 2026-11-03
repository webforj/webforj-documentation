---
title: "Java UI testing without a browser: the webforJ TestBed guide"
description: "Run Java UI testing without a browser in webforJ. The TestBed runs views in plain JUnit, with no server, and covers forms, dialogs, routes, and Spring."
slug: java-ui-testing-without-browser
date: 2026-11-03T09:00:00Z
authors: Hyyan Abo Fakher
tags: [tutorial, spring, annotations]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-11-03-java-ui-testing-without-browser/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-11-03-java-ui-testing-without-browser/cover.jpg)

You can test a Java web UI without a browser by using the webforJ TestBed. It runs a view inside a plain JUnit test. It needs no browser and no server. The test finds components, types, and clicks the way a user does, and asserts on what the page shows, all from Java.

That makes UI tests as fast as unit tests, and it answers the usual question about browserless UI tests in Java: how do you unit test a web UI without starting a stack? This guide covers setup, finding components, acting on them, assertions, snapshots, dialogs, routes, and Spring Boot.

<!-- truncate -->

## How do you test a Java UI without a browser? {#how-to-test-without-a-browser}

In a typical setup, a UI test starts the app, opens a browser, and drives it with Selenium or Playwright. Every test pays for the browser, the server, and the waiting between them. That is the right tool for what only a browser shows, such as styling.

The TestBed covers everything else, which is what your views do when a user works with them. Components attach, fire their events, and navigate exactly as they do in a running app. Every interaction finishes before the next line of the test runs, so nothing waits and nothing flakes.

A test follows the steps of a user:

1. **Find** a component with `ComponentLocator`.
2. **Act** on it with `UserInteraction`.
3. **Check** the result with `verifyThat`, or compare the whole view with a snapshot.

## Add the dependency {#set-up-the-testbed}

Add the TestBed with test scope. The webforJ bill of materials manages the version:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-testbed-junit</artifactId>
  <scope>test</scope>
</dependency>
```

Annotate the test class with `@WebforjTest`. Every test method then receives its own `WebforjTestbed`, set up before the test and torn down after it, so each test starts from an empty page. Tests run with `mvn test` or from the IDE like any JUnit test. The [Getting started](/docs/testing/testbed/getting-started) page walks through a first test.

## Find components the way a user sees them {#find-components}

`ComponentLocator` describes a component by its type and by what it shows, such as a field by its label or a button by its text:

```java
var name = ComponentLocator.of(TextField.class).byLabel("Name").locate();
var save = ComponentLocator.of(Button.class).byText("Save").locate();
```

The test never reaches into the private fields of the view. It works through what the page shows, so it keeps passing when you restructure the view and fails when the user would see something different. If the description matches zero components or several, the test fails with a message that shows the component tree. See [Finding components](/docs/testing/testbed/finding-components) for the other filters, such as searching inside a container.

## Act with UserInteraction, assert with verifyThat {#act-and-assert}

`UserInteraction` makes only the gestures a user could make. It has verbs such as `click`, `type`, `check`, `select`, and `open`, and it runs the component's real listeners in the same order the browser reports them. Clicking a disabled button fails the test with an `InteractionRejectedException` instead of running the listener.

Here is a test of a customer form. The form binds its fields to a `Customer` bean with Jakarta constraints. The test types an invalid email, clicks **Save**, and checks that the field is invalid with the right message and that nothing was saved:

```java
@WebforjTest
class CustomerFormTest {
  @Test
  void shouldRejectAnInvalidEmail(WebforjTestbed context) {
    List<Customer> saved = new ArrayList<>();
    CustomerForm form = new CustomerForm(saved::add);
    form.edit(new Customer());
    context.add(form);

    var name = ComponentLocator.of(TextField.class).byLabel("Name").locate();
    var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
    var save = ComponentLocator.of(Button.class).byText("Save").locate();

    UserInteraction.type(name, "Ada Lovelace");
    UserInteraction.type(email, "not-an-email");
    UserInteraction.click(save);

    verifyThat(email).isInvalid().hasInvalidMessage("Not a valid email address");
    assertTrue(saved.isEmpty());
  }
}
```

`verifyThat` reads the same state the browser shows: text, label, value, visible, enabled, read only, valid, focused, the selection of lists and tables, and the children of a container. Each check returns the assertion, so checks on one component chain in one statement, and a failure names the component and both values. The [Assertions](/docs/testing/testbed/assertions) page lists every check.

## Check a whole view with a snapshot {#snapshots}

Checking a view fact by fact takes one assertion per label, button, and field. A snapshot checks all of it at once. It writes down everything the user would see, one line per component, and stores that description as a text file with your tests:

```java
@WebforjTest
class CustomerFormSnapshotTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var form = new CustomerForm(customer -> {});
    context.add(form);

    verifyThat(form).matchesSnapshot();
  }
}
```

The first run writes the file and passes. For the customer form it reads:

```text
# webforJ testbed snapshot v1
- CustomerForm [composite=FlexLayout] [node=div]:
  - TextField [label=Name] [required]
  - TextField [label=Email] [required]
  - TextField [label=City]
  - Button "Save" [theme=primary]
```

Every later run describes the view again and compares. If someone changes a label, drops a field, or makes one required, the test fails and the change shows up as a plain text diff in code review. When a change is intended, run `mvn test -Dwebforj.testbed.updateSnapshots=true`, review the diff, and commit it. Use snapshots for views whose content should not change, and single assertions for facts such as the text of a button after a click. The [Snapshots](/docs/testing/testbed/snapshots) page explains how to read the format.

## Test dialogs, timers, and the browser {#dialogs-timers-browser}

A view rarely stands alone. It asks the user through dialogs, it refreshes on a timer, and it asks the browser for answers. In a TestBed test, the test plays each of those parts.

An option dialog blocks the code that showed it until the user answers. There is no user in a test, so the test gives the answer first. This test selects a row in a customer table, answers the confirm dialog with **Cancel**, and then with **OK**:

```java
@Test
void shouldDeleteOnlyWhenTheUserConfirms(WebforjTestbed context) {
  Customer ada = new Customer();
  ReflectionTestUtils.setField(ada, "id", 1L);
  ada.setName("Ada Lovelace");
  when(customers.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(ada)));
  when(customers.findById(1L)).thenReturn(Optional.of(ada));
  context.navigate(CustomerView.class);

  var table = ComponentLocator.of(Table.class).locate();
  var delete = ComponentLocator.of(Button.class).byText("Delete").locate();

  UserInteraction.select(table, ada);
  context.answerConfirmDialog(ConfirmDialog.Result.CANCEL);
  UserInteraction.click(delete);
  verify(customers, never()).delete(ada);

  context.answerConfirmDialog(ConfirmDialog.Result.OK);
  UserInteraction.click(delete);
  verify(customers).delete(ada);
}
```

Each kind of dialog has its own answer method, such as `answerInputDialog` and `answerFileChooserDialog`. `context.getShownOptionDialogs()` returns every dialog the app showed, so a test can also check what the dialog asked. A dialog shown without an answer fails the test and names the method to call.

Time works the same way. A test does not wait for an `Interval` or a `Debouncer`. It calls `context.runIntervals()`, which acts as if the delay of every running interval elapsed once. For code that asks the browser for a result, `answerExecuteJs` supplies the answer. The [Testing apps](/docs/testing/testbed/testing-apps) page covers each of these.

## Test routes and navigation {#routes-and-navigation}

`@WebforjTest` decides what a test starts with. With no attribute, the page is empty and the test adds components with `context.add()`. With `routeClasses`, the listed `@Route` views are registered at their routes. With `routePackages`, every `@Route` view in the listed packages is. With `app`, the app class starts as in a deployment, and its route security runs.

Navigation then works like the address bar. `context.navigate(CustomerView.class)` goes to the route of a view and returns the view that rendered, and `context.navigate("customers")` takes a path. Route lifecycle observers such as `onDidEnter` run before `navigate` returns. Start with as little as the test needs: a test of one view registers that view, and a test of how route security decides launches the app.

## Test a Spring Boot view with real beans {#spring-boot}

In a Spring Boot app, views get their beans from Spring and Spring Security guards their routes. Add `webforj-spring-testbed` with test scope instead of `webforj-testbed-junit`, then put `@SpringBootTest` and `@WebforjTest` on the same class. `navigate` creates the view through Spring, so the view receives its beans exactly as in the running app.

This test replaces the repository with a Spring `@MockitoBean`, navigates to the customer view, and saves a customer through the dialog:

```java
@SpringBootTest
@WebforjTest(routeClasses = CustomerView.class)
class CustomerViewTest {
  @MockitoBean
  private CustomerRepository customers;

  @Test
  void shouldSaveTheCustomerFromTheDialog(WebforjTestbed context) {
    when(customers.findAll(any(Pageable.class))).thenReturn(Page.empty());
    context.navigate(CustomerView.class);

    UserInteraction.click(ComponentLocator.of(Button.class).byText("New").locate());
    UserInteraction.type(ComponentLocator.of(TextField.class).byLabel("Name").locate(), "Ada");
    UserInteraction.type(
        ComponentLocator.of(TextField.class).byLabel("Email").locate(), "ada@example.com");
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Save").locate());

    verify(customers).save(any(Customer.class));
  }
}
```

Secured routes work too. When a test launches the app with `@WebforjTest(app = ...)`, route annotations such as `@RolesAllowed` apply during navigation, and `@WithMockUser` from `spring-security-test` sets the user. The test then checks the redirect by the type of the view that rendered. See [Spring testing](/docs/integrations/spring/testing) for the details.

## Why fast tests matter for AI-written code {#fast-tests-and-ai}

An AI assistant that writes a view can also write its tests, but a test only helps if the assistant can run it after every change. A browser test that takes a server start and a page load per run makes that loop slow, and a flaky one sends the assistant chasing failures that are not real.

TestBed tests run in the same JVM as the code, finish each interaction before the next line, and fail with a message that names the component and the page tree. That gives a person or an assistant a short, deterministic loop: change the view, run the test, read the diff. A snapshot adds a second guard, because any unintended change to what the page shows appears as a text diff. For more on working with assistants in a webforJ project, read [AI coding assistant for Java](/blog/ai-coding-assistant-java).

## Where TestBed fits next to browser tests {#testbed-and-browser-tests}

Use the TestBed for form logic, validation, navigation, security rules, dialogs, and what a view shows after each action. Keep Selenium or Playwright for what only a browser can show: styling, layout, and client behavior. The [testing overview](/docs/testing/overview) describes both layers. Running most of your UI tests in the TestBed leaves a small set of browser tests for the checks that need one.

## Next step {#next-step}

Add `webforj-testbed-junit` to a project, annotate a test class with `@WebforjTest`, and write the first test against a form you already have. [Getting started](/docs/testing/testbed/getting-started) has the full example, and [startforJ](https://docs.webforj.com/startforj) generates a project to try it in.

## Frequently asked questions {#faq}

### Can you test a Java UI without a browser or server? {#faq-no-browser}

Yes. The webforJ TestBed runs views inside a plain JUnit test. It needs no browser and no server. Components attach, fire events and navigate as they do in a running app.

### Should I still keep browser tests? {#faq-selenium-playwright}

Yes. The TestBed covers what your views do when a user works with them. Browser tests remain the right tool for styling, layout, and client behavior.

### How do I test a webforJ view that uses Spring beans? {#faq-spring}

Add `webforj-spring-testbed` and put `@SpringBootTest` and `@WebforjTest` on the test class. The view is created through Spring, so it gets its real or mocked beans, and Spring Security applies to routes when the test launches the app.

### What is the difference between an assertion and a snapshot? {#faq-assertion-vs-snapshot}

`verifyThat` checks single facts, such as the text of a button or whether a field is invalid. A snapshot stores a text description of the whole view and fails on any change to what the page shows, with the difference visible as a text diff.
