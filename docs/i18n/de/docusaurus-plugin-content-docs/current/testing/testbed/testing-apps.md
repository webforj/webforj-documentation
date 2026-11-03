---
sidebar_position: 6
title: Apps testen
description: >-
  Start a TestBed test with the routes or the whole app, navigate, test secured
  routes, answer dialogs, control timers, and answer for the browser.
_i18n_hash: 4194d4d355b0e26f86b8c3c53ea353c9
---
Eine Ansicht steht selten für sich allein. Eine Route führt zu ihr, die Routensicherheit kann sie schützen, sie fragt den Benutzer über Dialoge, aktualisiert sich nach einem Zeitgeber und fragt den Browser nach Antworten. In einer laufenden App übernehmen ein Server, ein Benutzer, die Uhr und der Browser jeweils ihren Teil. In einem TestBed-Test übernimmt der Test alle diese Rollen: Er legt fest, wie viel von der App ausgeführt wird, navigiert wie die Adressleiste, beantwortet Dialoge für den Benutzer, bestimmt, wann Zeit vergeht, und antwortet für den Browser.

<!-- INTRO_END -->

## Womit ein Test startet {#choosing-what-a-test-starts-with}

Die Attribute von `@WebforjTest` legen fest, womit jeder Test startet:

- **Kein Attribut.** Eine leere Seite. Der Test fügt die getesteten Komponenten mit `context.add()` hinzu, wie es die Tests auf den anderen Seiten tun.
- **`routeClasses`.** Die aufgeführten `@Route`-Ansichten, jeweils unter dem Pfad ihrer Annotation, und nichts anderes aus der App.
- **`routePackages`.** Alle `@Route`-Ansichten in den aufgeführten Paketen, gefunden auf dieselbe Weise wie bei `@Routify`.
- **`app`.** Die App-Klasse, gestartet wie bei einem Deployment. Ihre `run()`-Methode wird ausgeführt, ihre `@Routify`-Annotation registriert ihre Routen, und ihre Lifecycle-Listener sowie die Routensicherheit werden ausgeführt. `routeClasses` und `routePackages` haben dann keine Wirkung.

Starte mit so wenig wie nötig. Ein Test für eine einzelne Ansicht registriert diese Ansicht, während ein Test dafür, wie die App startet oder wie ihre Routensicherheit entscheidet, die App startet. `context.getApp()` gibt die laufende App zurück und `context.getFrame()` den ersten von ihr erstellten Frame.

Ein Test, der den Kontext selbst einrichtet, ruft `WebforjTestbed.setup()` mit einem `WebforjTestbedOptions`-Objekt auf, das dieselben Optionen enthält, und am Ende `WebforjTestbed.tearDown()`. Eine solche Klasse erhält kein `@WebforjTest`. Die Optionen akzeptieren außerdem eine Funktion, die das `RouteRegistry` entgegennimmt. Damit lassen sich Registrierungen vornehmen, die sich nicht durch die Annotation ausdrücken lassen, etwa eine Ansicht unter einem anderen Pfad als dem in ihrer Annotation.

## Routen aufrufen {#navigating-routes}

Ein Test erreicht eine Ansicht wie ein Benutzer: über ihre Route oder durch einen Klick, der zur Navigation führt. Die folgende Liste enthält Links zu zwei Produkten, und die Produktansicht zeigt das Produkt an, das in ihrer Route angegeben ist:

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

Der Test registriert beide Ansichten. Im ersten Test wird ein Produkt in der Liste angeklickt, im zweiten wird direkt zu einem Produkt navigiert:

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

`context.navigate(view)` navigiert zur Route einer Ansicht und gibt die gerenderte Ansicht zurück. `context.navigate(view, parameters)` füllt die Parameter der Route aus, sodass `id=42` das Produkt unter `/testbed/products/42` rendert. `context.navigate(location)` nimmt einen Pfad entgegen, etwa `"testbed/products/42"`, und gibt zurück, was dort gerendert wurde.

Bei der Navigation werden dieselben [Route-Lifecycle-Observer](/docs/routing/navigation-lifecycle/observers) ausgeführt wie in einer laufenden App. Daher wird `onDidEnter` der Produktansicht ausgeführt, bevor `navigate` zurückkehrt. Für alles Weitere, was der Router bietet, etwa eine Navigation, die den History-Eintrag ersetzt, ruf den Router des Tests auf, den `context.getRouter()` zurückgibt. Seine History enthält auch den aktuellen Standort.

## Routensicherheit testen {#testing-secured-routes}

Ein Test der Routensicherheit startet die App, legt den Benutzer so fest, wie die App ihn abruft, und navigiert. In einer App mit einer [benutzerdefinierten Sicherheitsimplementierung](/docs/security/architecture/custom-implementation) meldet sich der Test über die Login-Methode des app-eigenen Sicherheitsmanagers an.

:::info Routensicherheit benötigt die App
Routensicherheit wird ausgeführt, wenn ein Test die App mit `@WebforjTest(app = ...)` startet. Mit `routeClasses` oder `routePackages` werden die Ansichten ohne Routensicherheit gerendert. So kann ein Test eine geschützte Ansicht für sich allein und ohne Anmeldung prüfen.
:::

Jeder Test startet mit einer neuen Sitzung. Ein Test, der keinen Benutzer anmeldet, navigiert daher als anonymer Besucher.

:::tip Spring-Boot-Apps
In einer Spring-Boot-App mit Spring Security legt ein Test den Benutzer mit `@WithMockUser` fest, wie unter [Spring-Tests](/docs/integrations/spring/testing#testing-secured-routes) beschrieben.
:::

### Weiterleitungen prüfen {#checking-redirects}

Wenn die Routensicherheit den Zugriff verweigert, wird zur Authentifizierungs- oder Ablehnungsadresse weitergeleitet, die in der App konfiguriert ist. `navigate(String)` gibt die Komponente zurück, die dort gerendert wurde. Der Test prüft die Weiterleitung daher anhand des Typs dieser Komponente.

`navigate(Class)` erwartet die angeforderte Ansicht. Wenn die Routensicherheit stattdessen eine andere Ansicht rendert, schlägt der Aufruf mit einer `IllegalStateException` fehl, die beide Ansichten nennt und auf `navigate(String)` verweist.

Wenn der Zugriff verweigert wird und die App weder eine Authentifizierungs- noch eine Ablehnungsadresse konfiguriert hat, wird nichts gerendert. Beide `navigate`-Methoden schlagen dann mit einer Meldung fehl, die die Route nennt und angibt, dass die Navigation abgelehnt wurde. Derselbe Fehler wird auch gemeldet, wenn ein Route-Lifecycle-Observer die Navigation ablehnt.

## Dialoge beantworten {#answering-dialogs}

Ein [Optionsdialog](/docs/components/option-dialogs/overview) hält den Code, der ihn angezeigt hat, an, bis der Benutzer antwortet. In einem Test gibt es keinen Benutzer, der antworten könnte. Daher legt der Test die Antwort fest, bevor der Code den Dialog anzeigt. Die folgende Ansicht bittet den Benutzer um Bestätigung, bevor sie einen Bericht löscht:

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

Der erste Test antwortet **OK**, bevor er auf **Delete** klickt, und der zweite antwortet **Cancel**:

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

`context.getShownOptionDialogs()` gibt alle Dialoge zurück, die die App während des Tests angezeigt hat, in der Reihenfolge ihrer Anzeige und jeweils mit Titel und Nachricht. So prüft der erste Test auch, was der Dialog gefragt hat.

Jede Dialogart hat eine eigene Antwortmethode. Die folgende Ansicht fragt in einem Eingabedialog nach einem neuen Namen:

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

Der Test antwortet mit dem Text, den der Benutzer eingeben würde:

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

`answerConfirmDialog` nimmt die Schaltfläche entgegen, die der Benutzer drückt, und `answerInputDialog` den Text, den der Benutzer eingibt. Die Dateidialoge erwarten über `answerFileChooserDialog`, `answerFileSaveDialog` und `answerFileUploadDialog` den Pfad oder Dateinamen, den der Benutzer auswählt. Wird an einen Eingabe- oder Dateidialog `null` übergeben, entspricht die Antwort **Cancel**. Ein Meldungsdialog benötigt keine Antwort, da der Benutzer ihn nur schließen kann.

Antworten werden den Dialogen ihrer Art in der Reihenfolge zugewiesen, in der der Test sie festgelegt hat. Eine Ansicht, die zweimal fragt, erhält also die zweite Antwort beim zweiten Dialog. Wird ein Dialog ohne Antwort angezeigt, schlägt der Test mit einer `IllegalStateException` fehl, die den Dialog und die aufzurufende Antwortmethode nennt.

## Zeitgeber {#timers}

Ein Test wartet nicht darauf, dass Zeit vergeht. Mit `context.runIntervals()` legt er fest, wann Zeit vergeht. Die folgende Ansicht aktualisiert sich alle fünf Sekunden:

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

Jeder Aufruf von `runIntervals()` verhält sich so, als wäre die Verzögerung jedes laufenden [Intervalls](/docs/advanced/interval) einmal abgelaufen, und gibt die Anzahl der ausgelösten Intervalle zurück:

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

Ein [Debouncer](/docs/advanced/debouncing) wartet auf dieselbe Weise. Das folgende Suchfeld führt die Suche erst aus, wenn der Benutzer mit der Eingabe aufhört:

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

Der Test gibt zweimal Text ein. Erst nach dem Aufruf von `runIntervals()` wird gesucht. Dann wird der Debouncer einmal ausgeführt und verwendet die letzte Eingabe:

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

[`Environment.runLater`](/docs/advanced/asynchronous-updates) benötigt keinen Aufruf. Wird die Methode vom Test oder von dem durch ihn ausgelösten Code aufgerufen, führt sie die Aufgabe noch vor ihrer Rückkehr aus, und ihr `PendingResult` ist bereits abgeschlossen.

## Dem Browser antworten {#answering-the-browser}

Mancher Code fragt den Browser nach einem Ergebnis, etwa nach der Fensterbreite. In einem Test gibt es keinen Browser, also antwortet der Test für ihn. Die folgende Ansicht zeigt die vom Browser gemeldete Breite an:

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

`answerExecuteJs` liefert das Ergebnis des nächsten `executeJs`-Aufrufs. Deshalb legt der Test das Ergebnis vor dem Klick fest:

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

`executeJs` gibt `null` zurück, wenn der Test kein Ergebnis festgelegt hat. Code, der nicht auf den Browser wartet, verwendet `executeJsAsync`, das später abgeschlossen wird. Die folgende Schaltfläche kopiert den Link der Seite und bestätigt den Abschluss, sobald der Browser fertig ist:

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

Das Skript startet mit dem Klick. Daher beantwortet der Test es nach dem Klick mit `answerExecuteJsAsync`, wodurch das älteste noch auf ein Ergebnis wartende Skript abgeschlossen wird:

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

`answerExecuteJsAsync` schlägt mit einer `IllegalStateException` fehl, wenn kein Skript auf ein Ergebnis wartet. Ein `Element` wartet darauf, dass der Browser es definiert. Daher warten `whenDefined()` und die Aufrufe, die es in die Warteschlange stellt, etwa `callJsFunctionAsync`, bis der Test für das Element mit `UserInteraction.fireEvent` ein `ElementDefinedEvent` auslöst.

Die Referenz zu <JavadocLink type="testbed-foundation" location="com/webforj/testbed/WebforjTestbed" code="true">WebforjTestbed</JavadocLink> listet alle Methoden des Testkontexts auf.
