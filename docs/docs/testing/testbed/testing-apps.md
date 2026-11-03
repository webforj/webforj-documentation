---
sidebar_position: 6
title: Testing apps
description: Start a TestBed test with the routes or the whole app, navigate, test secured routes, answer dialogs, control timers, and answer for the browser.
---

A view rarely stands alone. A route leads to it, route security may guard it, it asks the user through dialogs, it updates itself on a timer, and it asks the browser for answers. In a running app, a server, a user, the clock, and the browser each play their part. In a TestBed test, the test plays all of them: it decides how much of the app runs, navigates the way the address bar does, answers dialogs for the user, decides when time passes, and answers for the browser.

<!-- INTRO_END -->

## Choosing what a test starts with {#choosing-what-a-test-starts-with}

The attributes of `@WebforjTest` decide what each test starts with:

- **No attribute.** An empty page. The test adds the components it tests with `context.add()`, as the tests on the other pages do.
- **`routeClasses`.** The listed `@Route` views, each at the path of its annotation, and nothing else of the app.
- **`routePackages`.** Every `@Route` view in the listed packages, found the way `@Routify` finds them.
- **`app`.** The app class, started as in a deployment. Its `run()` method runs, its `@Routify` annotation registers its routes, and its lifecycle listeners and route security run. `routeClasses` and `routePackages` then have no effect.

Start with as little as the test needs. A test of one view registers that view, and a test of how the app starts or how its route security decides launches the app. `context.getApp()` returns the running app, and `context.getFrame()` the first frame it created.

A test that sets up the context itself calls `WebforjTestbed.setup()` with a `WebforjTestbedOptions` that holds the same choices, and `WebforjTestbed.tearDown()` at the end. Such a class carries no `@WebforjTest`. The options also take a function that receives the `RouteRegistry`, for registrations the annotation can't express, such as a view at another path than the one in its annotation.

## Navigating routes {#navigating-routes}

A test reaches a view the way a user does: by its route, or by a click that navigates. The following list links to two products, and the product view shows the product its route names:

```java title="ProductListView.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.router.Router;
import com.webforj.router.annotation.Route;
import com.webforj.router.history.ParametersBag;
import java.util.List;

@Route("testbed/products")
public class ProductListView extends Composite<Div> {

  public ProductListView() {
    for (String id : List.of("41", "42")) {
      getBoundComponent()
          .add(
              new Button(
                  "Product " + id,
                  e ->
                      Router.getCurrent()
                          .navigate(
                              ProductView.class,
                              ParametersBag.of("id=" + id))));
    }
  }
}
```

```java title="ProductView.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.Route;
import com.webforj.router.event.DidEnterEvent;
import com.webforj.router.history.ParametersBag;
import com.webforj.router.observer.DidEnterObserver;

@Route("testbed/products/:id")
public class ProductView extends Composite<Div> implements DidEnterObserver {
  private final Paragraph title = new Paragraph();

  public ProductView() {
    getBoundComponent().add(title);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    title.setText("Product " + parameters.get("id").orElse(""));
  }
}
```

The test registers both views. The first test clicks a product in the list, and the second navigates straight to a product:

```java title="ProductListViewTest.java" showLineNumbers
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.history.ParametersBag;
import com.webforj.samples.views.testbed.ProductListView;
import com.webforj.samples.views.testbed.ProductView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest(routeClasses = {ProductListView.class, ProductView.class})
class ProductListViewTest {
  @Test
  void shouldOpenTheProductTheUserClicks(WebforjTestbed context) {
    context.navigate(ProductListView.class);

    UserInteraction.click(
        ComponentLocator.of(Button.class).byText("Product 42").locate());

    assertTrue(
        ComponentLocator.of(Paragraph.class).byText("Product 42").isPresent());
  }

  @Test
  void shouldShowTheProductOfTheRoute(WebforjTestbed context) {
    var product = context.navigate(
        ProductView.class, ParametersBag.of("id=42"));

    assertTrue(
        ComponentLocator.of(Paragraph.class)
            .within(product)
            .byText("Product 42")
            .isPresent());
    assertEquals(
        "/testbed/products/42",
        context.getRouter()
            .orElseThrow()
            .getHistory()
            .getLocation()
            .orElseThrow()
            .getFullURI());
  }
}
```

`context.navigate(view)` navigates to the route of a view and returns the view that rendered. `context.navigate(view, parameters)` fills the parameters of the route, so `id=42` renders the product at `/testbed/products/42`. `context.navigate(location)` takes a path, such as `"testbed/products/42"`, and returns whatever rendered there.

Navigation runs the same [route lifecycle observers](/docs/routing/navigation-lifecycle/observers) as a running app, so `onDidEnter` of the product view runs before `navigate` returns. For anything else the router offers, such as navigation that replaces the history entry, call the router of the test, which `context.getRouter()` returns. Its history also holds the current location.

## Testing secured routes {#testing-secured-routes}

A test of route security launches the app, sets the user the way the app reads it, and navigates. In an app with a [custom security implementation](/docs/security/architecture/custom-implementation), the test signs in through the login method of the app's own security manager.

:::info Route security needs the app
Route security runs when a test launches the app with `@WebforjTest(app = ...)`. With `routeClasses` or `routePackages`, the views render without route security, so a test can check a secured view on its own, without signing in.
:::

Each test starts with a new session, so a test that signs in no user navigates as an anonymous visitor.

:::tip Spring Boot apps
In a Spring Boot app with Spring Security, a test sets the user with `@WithMockUser`, as described in [Spring testing](/docs/integrations/spring/testing#testing-secured-routes).
:::

### Checking redirects {#checking-redirects}

When route security denies access, it redirects to the authentication or deny location the app configures. `navigate(String)` returns the component that rendered there, so a test checks the redirect by the type of that component.

`navigate(Class)` expects the requested view. When route security renders another view instead, it fails with an `IllegalStateException` that names both views and points to `navigate(String)`.

When access is denied and the app configures no authentication or deny location, nothing renders. Both `navigate` methods then fail with a message that names the route and says the navigation was vetoed. The same failure reports a navigation that a route lifecycle observer vetoed.

## Answering dialogs {#answering-dialogs}

An [option dialog](/docs/components/option-dialogs/overview) stops the code that showed it until the user answers. In a test there's no user to answer, so the test gives the answer before the code shows the dialog. The following view asks the user to confirm before it deletes a report:

```java title="DeleteReport.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.optiondialog.ConfirmDialog;
import com.webforj.component.optiondialog.OptionDialog;

public class DeleteReport extends Composite<Div> {

  public DeleteReport() {
    Paragraph status = new Paragraph("report.txt");
    Button delete =
        new Button(
            "Delete",
            e -> {
              ConfirmDialog.Result answer =
                  OptionDialog.showConfirmDialog(
                      "Delete report.txt?",
                      "Delete",
                      ConfirmDialog.OptionType.OK_CANCEL);
              if (answer == ConfirmDialog.Result.OK) {
                status.setText("Deleted");
              }
            });

    getBoundComponent().add(status, delete);
  }
}
```

The first test answers **OK** before clicking **Delete**, and the second answers **Cancel**:

```java title="DeleteReportTest.java" showLineNumbers
@Test
void shouldDeleteTheReportOnceTheUserConfirms(WebforjTestbed context) {
  context.add(new DeleteReport());

  context.answerConfirmDialog(ConfirmDialog.Result.OK);
  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Delete").locate());

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("Deleted").isPresent());

  var dialog = context.getShownOptionDialogs().get(0);
  assertEquals("Delete", dialog.getTitle());
  assertEquals("Delete report.txt?", dialog.getMessage());
}

@Test
void shouldKeepTheReportWhenTheUserCancels(WebforjTestbed context) {
  context.add(new DeleteReport());

  context.answerConfirmDialog(ConfirmDialog.Result.CANCEL);
  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Delete").locate());

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("report.txt").isPresent());
}
```

`context.getShownOptionDialogs()` returns every dialog the app showed during the test, in order, with its title and message, so the first test also checks what the dialog asked.

Each kind of dialog has its own answer method. The following view asks for a new name with an input dialog:

```java title="RenameReport.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.optiondialog.OptionDialog;

public class RenameReport extends Composite<Div> {

  public RenameReport() {
    Paragraph name = new Paragraph("report.txt");
    Button rename =
        new Button(
            "Rename",
            e -> {
              String answer = OptionDialog.showInputDialog(
                  "New name", "Rename", name.getText());
              if (answer != null) {
                name.setText(answer);
              }
            });

    getBoundComponent().add(name, rename);
  }
}
```

The test answers with the text the user would type:

```java title="RenameReportTest.java" showLineNumbers
@Test
void shouldRenameTheReportToTheNameTheUserEnters(WebforjTestbed context) {
  context.add(new RenameReport());

  context.answerInputDialog("summary.txt");
  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Rename").locate());

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("summary.txt").isPresent());
}
```

`answerConfirmDialog` takes the button the user presses, and `answerInputDialog` the text the user enters. The file dialogs take the path or the filename the user picks, through `answerFileChooserDialog`, `answerFileSaveDialog` and `answerFileUploadDialog`. Passing `null` to an input or file dialog answers it the way **Cancel** does. A message dialog needs no answer, because the user can only dismiss it.

Answers serve the dialogs of their kind in the order the test gave them, so a view that asks twice receives the second answer on the second dialog. A dialog shown without an answer fails the test with an `IllegalStateException` that names the dialog and the answer method to call.

## Timers {#timers}

A test doesn't wait for time to pass. It decides when time passes with `context.runIntervals()`. The following view refreshes itself every five seconds:

```java title="RefreshCounter.java" showLineNumbers
import com.webforj.Interval;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class RefreshCounter extends Composite<Div> {
  private int refreshes = 0;

  public RefreshCounter() {
    Paragraph status = new Paragraph("Refreshed 0 times");
    Interval interval =
        new Interval(
            5f, e -> status.setText("Refreshed " + (++refreshes) + " times"));
    interval.start();

    getBoundComponent().add(status);
  }
}
```

Each call to `runIntervals()` acts as if the delay of every running [interval](/docs/advanced/interval) elapsed once, and returns how many fired:

```java title="RefreshCounterTest.java" showLineNumbers
@Test
void shouldRefreshEveryTimeTheIntervalElapses(WebforjTestbed context) {
  context.add(new RefreshCounter());

  assertEquals(1, context.runIntervals());
  assertEquals(1, context.runIntervals());

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Refreshed 2 times")
          .isPresent());
}
```

A [debouncer](/docs/advanced/debouncing) waits the same way. The following search box searches only once the user stops typing:

```java title="SearchBox.java" showLineNumbers
import com.webforj.Debouncer;
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class SearchBox extends Composite<Div> {

  public SearchBox() {
    TextField search = new TextField("Search");
    Paragraph results = new Paragraph();
    Debouncer debouncer = new Debouncer(0.3f);
    search.onModify(
        e -> debouncer.run(
            () -> results.setText("Results for " + search.getValue())));

    getBoundComponent().add(search, results);
  }
}
```

The test types twice. Nothing is searched until it calls `runIntervals()`, and then the debouncer runs once, with the last input:

```java title="SearchBoxTest.java" showLineNumbers
@Test
void shouldSearchOnlyForTheLastInput(WebforjTestbed context) {
  context.add(new SearchBox());

  var search = ComponentLocator.of(TextField.class).byLabel("Search").locate();
  var results = ComponentLocator.of(Paragraph.class).locate();

  UserInteraction.type(search, "jav");
  UserInteraction.type(search, "java");

  assertEquals("", results.getText());

  context.runIntervals();

  assertEquals("Results for java", results.getText());
}
```

[`Environment.runLater`](/docs/advanced/asynchronous-updates) needs no call. Called from the test or from the code it drives, it runs the task before it returns, and its `PendingResult` is already complete.

## Answering the browser {#answering-the-browser}

Some code asks the browser for a result, such as the width of the window. In a test there's no browser, so the test answers for it. The following view shows the width the browser reports:

```java title="WindowWidth.java" showLineNumbers
import com.webforj.Page;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class WindowWidth extends Composite<Div> {

  public WindowWidth() {
    Paragraph width = new Paragraph();
    Button measure =
        new Button(
            "Measure",
            e -> width.setText(
                "Width: " + Page.getCurrent().executeJs("window.innerWidth")));

    getBoundComponent().add(measure, width);
  }
}
```

`answerExecuteJs` gives the result of the next `executeJs` call, so the test gives it before the click:

```java title="WindowWidthTest.java" showLineNumbers
@Test
void shouldShowTheWidthTheBrowserReports(WebforjTestbed context) {
  context.add(new WindowWidth());

  context.answerExecuteJs(1280);
  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Measure").locate());

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("Width: 1280").isPresent());
}
```

`executeJs` returns `null` when the test gave no result. Code that doesn't wait for the browser uses `executeJsAsync`, which completes later. The following button copies the link of the page and confirms once the browser is done:

```java title="CopyLink.java" showLineNumbers
import com.webforj.Page;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class CopyLink extends Composite<Div> {

  public CopyLink() {
    Paragraph status = new Paragraph();
    Button copy =
        new Button(
            "Copy link",
            e ->
                Page.getCurrent()
                    .executeJsAsync(
                        "navigator.clipboard.writeText(location.href)")
                    .thenAccept(result -> status.setText("Link copied")));

    getBoundComponent().add(copy, status);
  }
}
```

The script starts with the click, so the test answers it after the click with `answerExecuteJsAsync`, which completes the oldest script still waiting:

```java title="CopyLinkTest.java" showLineNumbers
@Test
void shouldConfirmOnceTheBrowserCopiedTheLink(WebforjTestbed context) {
  context.add(new CopyLink());

  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Copy link").locate());
  context.answerExecuteJsAsync(null);

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("Link copied").isPresent());
}
```

`answerExecuteJsAsync` fails with an `IllegalStateException` when no script waits for a result. An `Element` waits for the browser to define it, so `whenDefined()` and the calls it queues, such as `callJsFunctionAsync`, wait until the test fires an `ElementDefinedEvent` for the element with `UserInteraction.fireEvent`.

The <JavadocLink type="testbed-foundation" location="com/webforj/testbed/WebforjTestbed" code="true">WebforjTestbed</JavadocLink> reference lists every method of the test context.
