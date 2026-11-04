---
sidebar_position: 6
title: Apps testen
description: >-
  Start a TestBed test with the routes or the whole app, navigate, test secured
  routes, answer dialogs, control timers, and answer for the browser.
_i18n_hash: 4194d4d355b0e26f86b8c3c53ea353c9
---
Een view staat zelden op zichzelf. Een route leidt ernaartoe, routebeveiliging kan de toegang bewaken, de view vraagt de gebruiker om input via dialogen, werkt zichzelf bij op een timer en vraagt de browser om antwoorden. In een actieve app speelt een server, een gebruiker, de klok en de browser elk hun rol. In een TestBed-test speelt de test al die rollen: de test bepaalt welk deel van de app wordt uitgevoerd, navigeert zoals de adresbalk dat doet, beantwoordt dialogen namens de gebruiker, bepaalt wanneer er tijd verstrijkt en geeft antwoorden namens de browser.

<!-- INTRO_END -->

## Kiezen waarmee een test begint {#choosing-what-a-test-starts-with}

De attributen van `@WebforjTest` bepalen waarmee elke test begint:

- **Geen attribuut.** Een lege pagina. De test voegt de componenten die hij test toe met `context.add()`, zoals de tests op de andere pagina's doen.
- **`routeClasses`.** De opgegeven `@Route`-views, elk op het pad van de annotatie, en verder niets van de app.
- **`routePackages`.** Elke `@Route`-view in de opgegeven packages, gevonden op dezelfde manier als `@Routify` ze vindt.
- **`app`.** De app-klasse, gestart zoals bij een deployment. De methode `run()` wordt uitgevoerd, de annotatie `@Routify` registreert de routes en de lifecycle-listeners en routebeveiliging worden uitgevoerd. `routeClasses` en `routePackages` hebben dan geen effect.

Begin met niet meer dan de test nodig heeft. Een test van één view registreert die view, terwijl een test van hoe de app start of hoe de routebeveiliging beslist de app start. `context.getApp()` retourneert de actieve app en `context.getFrame()` het eerste frame dat de app heeft aangemaakt.

Een test die de context zelf instelt, roept `WebforjTestbed.setup()` aan met een `WebforjTestbedOptions` die dezelfde keuzes bevat, en roept aan het einde `WebforjTestbed.tearDown()` aan. Zo'n klasse heeft geen `@WebforjTest`. De opties accepteren ook een functie die de `RouteRegistry` ontvangt, voor registraties die niet met de annotatie kunnen worden uitgedrukt, zoals een view op een ander pad dan het pad in de annotatie.

## Navigeren tussen routes {#navigating-routes}

Een test bereikt een view zoals een gebruiker dat doet: via de route of door op iets te klikken dat navigeert. De volgende lijst bevat links naar twee producten en de productview toont het product dat in de route wordt genoemd:

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

De test registreert beide views. De eerste test klikt op een product in de lijst en de tweede navigeert rechtstreeks naar een product:

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

`context.navigate(view)` navigeert naar de route van een view en retourneert de view die is gerenderd. `context.navigate(view, parameters)` vult de parameters van de route in, zodat `id=42` het product op `/testbed/products/42` rendert. `context.navigate(location)` neemt een pad, zoals `"testbed/products/42"`, en retourneert wat daar is gerenderd.

Navigatie voert dezelfde [route-lifecycle-observers](/docs/routing/navigation-lifecycle/observers) uit als een actieve app, waardoor `onDidEnter` van de productview wordt uitgevoerd voordat `navigate` retourneert. Voor andere functies van de router, zoals navigeren waarbij het huidige geschiedenisitem wordt vervangen, roep je de router van de test aan; die retourneert `context.getRouter()`. De geschiedenis ervan bevat ook de huidige locatie.

## Beveiligde routes testen {#testing-secured-routes}

Een test van routebeveiliging start de app, stelt de gebruiker in zoals de app die uitleest en navigeert vervolgens. In een app met een [aangepaste beveiligingsimplementatie](/docs/security/architecture/custom-implementation) meldt de test zich aan via de aanmeldmethode van de eigen beveiligingsmanager van de app.

:::info Routebeveiliging vereist de app
Routebeveiliging wordt uitgevoerd wanneer een test de app start met `@WebforjTest(app = ...)`. Met `routeClasses` of `routePackages` worden de views weergegeven zonder routebeveiliging, zodat een test een beveiligde view afzonderlijk kan controleren, zonder zich aan te melden.
:::

Elke test begint met een nieuwe sessie. Een test die geen gebruiker aanmeldt, navigeert dus als anonieme bezoeker.

:::tip Spring Boot-apps
In een Spring Boot-app met Spring Security stelt een test de gebruiker in met `@WithMockUser`, zoals beschreven in [testen met Spring](/docs/integrations/spring/testing#testing-secured-routes).
:::

### Omleidingen controleren {#checking-redirects}

Wanneer routebeveiliging de toegang weigert, wordt de gebruiker omgeleid naar de authenticatie- of weigeringslocatie die de app heeft ingesteld. `navigate(String)` retourneert de component die daar is gerenderd, zodat een test de omleiding controleert aan de hand van het type van die component.

`navigate(Class)` verwacht de aangevraagde view. Wanneer routebeveiliging in plaats daarvan een andere view rendert, mislukt de aanroep met een `IllegalStateException` waarin beide views worden genoemd en naar `navigate(String)` wordt verwezen.

Wanneer de toegang wordt geweigerd en de app geen authenticatie- of weigeringslocatie heeft ingesteld, wordt er niets gerenderd. Beide `navigate`-methoden mislukken dan met een bericht dat de route noemt en aangeeft dat de navigatie is tegengehouden. Dezelfde foutmelding wordt gegeven wanneer een route-lifecycle-observer een navigatie tegenhoudt.

## Dialogen beantwoorden {#answering-dialogs}

Een [optiedialoog](/docs/components/option-dialogs/overview) onderbreekt de code die de dialoog heeft weergegeven totdat de gebruiker antwoord geeft. In een test is er geen gebruiker om te antwoorden, dus geeft de test het antwoord voordat de code de dialoog weergeeft. De volgende view vraagt de gebruiker om bevestiging voordat een rapport wordt verwijderd:

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

De eerste test geeft **OK** als antwoord voordat er op **Delete** wordt geklikt, en de tweede geeft **Cancel** als antwoord:

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

`context.getShownOptionDialogs()` retourneert alle dialogen die de app tijdens de test heeft weergegeven, in volgorde en met hun titel en bericht. De eerste test controleert dus ook wat de dialoog vroeg.

Elk type dialoog heeft een eigen antwoordmethode. De volgende view vraagt via een invoerdialoog om een nieuwe naam:

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

De test geeft als antwoord de tekst die de gebruiker zou invoeren:

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

`answerConfirmDialog` neemt de knop in waarop de gebruiker drukt en `answerInputDialog` de tekst die de gebruiker invoert. De bestandsdialogen nemen het pad of de bestandsnaam die de gebruiker kiest, via `answerFileChooserDialog`, `answerFileSaveDialog` en `answerFileUploadDialog`. Als je `null` doorgeeft aan een invoer- of bestandsdialoog, wordt die beantwoord zoals bij **Cancel**. Een berichtdialoog heeft geen antwoord nodig, omdat de gebruiker die alleen kan sluiten.

Antwoorden worden in de volgorde waarin de test ze heeft opgegeven gebruikt voor dialogen van hetzelfde type. Een view die twee keer iets vraagt, ontvangt dus het tweede antwoord bij de tweede dialoog. Als een dialoog wordt weergegeven zonder dat er een antwoord klaarstaat, mislukt de test met een `IllegalStateException` waarin de dialoog wordt genoemd en staat welke antwoordmethode moet worden aangeroepen.

## Timers {#timers}

Een test wacht niet tot er tijd verstrijkt. De test bepaalt wanneer de tijd verstrijkt met `context.runIntervals()`. De volgende view ververst zichzelf elke vijf seconden:

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

Elke aanroep van `runIntervals()` doet alsof de vertraging van elk actief [interval](/docs/advanced/interval) één keer is verstreken en retourneert het aantal timers dat is afgegaan:

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

Een [debouncer](/docs/advanced/debouncing) wacht op dezelfde manier. Het volgende zoekveld voert de zoekopdracht pas uit wanneer de gebruiker stopt met typen:

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

De test typt twee keer. Er wordt pas gezocht wanneer de test `runIntervals()` aanroept. De debouncer wordt dan één keer uitgevoerd, met de laatste invoer:

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

[`Environment.runLater`](/docs/advanced/asynchronous-updates) vereist geen aanroep. Of die nu vanuit de test of vanuit de code die de test aanstuurt wordt aangeroepen: de taak wordt uitgevoerd voordat de methode retourneert en de `PendingResult` is dan al voltooid.

## De browser antwoorden geven {#answering-the-browser}

Sommige code vraagt de browser om een resultaat, zoals de breedte van het venster. In een test is er geen browser, dus geeft de test namens de browser antwoord. De volgende view toont de breedte die de browser rapporteert:

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

`answerExecuteJs` geeft het resultaat van de volgende `executeJs`-aanroep. De test geeft het resultaat dus voordat er wordt geklikt:

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

`executeJs` retourneert `null` als de test geen resultaat heeft opgegeven. Code die niet op de browser wacht, gebruikt `executeJsAsync`, dat later wordt voltooid. De volgende knop kopieert de link naar de pagina en bevestigt dat dit is gelukt zodra de browser klaar is:

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

Het script wordt gestart wanneer erop wordt geklikt. De test geeft er daarom na de klik antwoord op met `answerExecuteJsAsync`, dat het oudste script voltooit dat nog op een antwoord wacht:

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

`answerExecuteJsAsync` mislukt met een `IllegalStateException` wanneer geen enkel script op een resultaat wacht. Een `Element` wacht tot de browser het element definieert. Daarom wachten `whenDefined()` en de aanroepen die het in de wachtrij zet, zoals `callJsFunctionAsync`, totdat de test een `ElementDefinedEvent` voor het element afvuurt met `UserInteraction.fireEvent`.

De referentie voor <JavadocLink type="testbed-foundation" location="com/webforj/testbed/WebforjTestbed" code="true">WebforjTestbed</JavadocLink> bevat een overzicht van alle methoden van de testcontext.
