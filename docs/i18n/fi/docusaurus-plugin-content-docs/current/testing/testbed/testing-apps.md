---
sidebar_position: 6
title: Sovellusten testaaminen
description: >-
  Start a TestBed test with the routes or the whole app, navigate, test secured
  routes, answer dialogs, control timers, and answer for the browser.
_i18n_hash: 4194d4d355b0e26f86b8c3c53ea353c9
---
Näkymä toimii harvoin yksinään. Reitti johtaa siihen, reitin käyttöoikeustarkistus voi suojata sitä, se kysyy käyttäjältä dialogien kautta, päivittää itsensä ajastimella ja pyytää vastauksia selaimelta. Käynnissä olevassa sovelluksessa palvelin, käyttäjä, kello ja selain osallistuvat kukin omalla tavallaan. TestBed-testissä testitapaus hoitaa kaikkien roolit: se päättää, kuinka suuri osa sovelluksesta käynnistetään, navigoi osoiterivin tavoin, vastaa dialogeihin käyttäjän puolesta, päättää ajan kulumisesta ja vastaa selaimen puolesta.

<!-- INTRO_END -->

## Testin lähtötilan valitseminen {#choosing-what-a-test-starts-with}

`@WebforjTest`-annotaation määritteet ratkaisevat, mistä kukin testi aloittaa:

- **Ei määritteitä.** Tyhjä sivu. Testi lisää testaamansa komponentit `context.add()`-kutsulla, kuten muiden sivujen testeissä tehdään.
- **`routeClasses`.** Luetellut `@Route`-näkymät annotaatioidensa mukaisissa poluissa, eikä mitään muuta sovelluksesta.
- **`routePackages`.** Kaikki lueteltujen pakettien `@Route`-näkymät, jotka löydetään samalla tavalla kuin `@Routify` löytää ne.
- **`app`.** Sovellusluokka käynnistetään kuten käyttöönotossa. Sen `run()`-metodi suoritetaan, sen `@Routify`-annotaatio rekisteröi reitit ja sen elinkaarikuuntelijat sekä reittien käyttöoikeustarkistukset suoritetaan. Tällöin `routeClasses` ja `routePackages` eivät vaikuta.

Aloita testin tarvitsemasta mahdollisimman pienestä kokonaisuudesta. Yhden näkymän testi rekisteröi kyseisen näkymän, kun taas sovelluksen käynnistymistä tai reitin käyttöoikeustarkistuksen toimintaa testaava testi käynnistää sovelluksen. `context.getApp()` palauttaa käynnissä olevan sovelluksen ja `context.getFrame()` sen ensimmäisen luoman kehyksen.

Testi, joka määrittää kontekstin itse, kutsuu `WebforjTestbed.setup()`-metodia `WebforjTestbedOptions`-asetuksilla, jotka sisältävät samat valinnat, ja kutsuu lopuksi `WebforjTestbed.tearDown()`-metodia. Tällaisessa luokassa ei ole `@WebforjTest`-annotaatiota. Asetuksiin voi antaa myös funktion, joka saa parametrikseen `RouteRegistry`-olion. Sen avulla voi tehdä rekisteröintejä, joita annotaatiolla ei voi ilmaista, kuten rekisteröidä näkymän muuhun polkuun kuin sen annotaatiossa määritettyyn.

## Reiteillä navigointi {#navigating-routes}

Testi siirtyy näkymään samalla tavalla kuin käyttäjä: reitin kautta tai navigoivan napsautuksen avulla. Seuraava luettelo linkittää kahteen tuotteeseen, ja tuotenäkymä näyttää reitin määrittämän tuotteen:

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

Testi rekisteröi molemmat näkymät. Ensimmäisessä testissä napsautetaan luettelossa olevaa tuotetta, ja toisessa navigoidaan suoraan tuotteen sivulle:

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

`context.navigate(view)` navigoi näkymän reittiin ja palauttaa näkymän, joka renderöitiin. `context.navigate(view, parameters)` täyttää reitin parametrit, joten `id=42` renderöi tuotteen polussa `/testbed/products/42`. `context.navigate(location)` saa parametrikseen polun, kuten `"testbed/products/42"`, ja palauttaa siellä renderöidyn näkymän.

Navigointi suorittaa samat [reitin elinkaaren tarkkailijat](/docs/routing/navigation-lifecycle/observers) kuin käynnissä olevassa sovelluksessa, joten tuotenäkymän `onDidEnter` suoritetaan ennen kuin `navigate` palauttaa arvon. Jos tarvitset jotain muuta reitittimen tarjoamaa toimintoa, kuten navigointia, joka korvaa historiatietueen, kutsu testin reititintä, jonka `context.getRouter()` palauttaa. Sen historia sisältää myös nykyisen sijainnin.

## Suojattujen reittien testaaminen {#testing-secured-routes}

Reittien käyttöoikeustarkistusta testaava testi käynnistää sovelluksen, asettaa käyttäjän samalla tavalla kuin sovellus sen lukee ja navigoi. Sovelluksessa, jossa on [mukautettu käyttöoikeuksien hallinnan toteutus](/docs/security/architecture/custom-implementation), testi kirjautuu sisään sovelluksen oman käyttöoikeuksien hallinnan kirjautumismetodilla.

:::info Reittien käyttöoikeustarkistus vaatii sovelluksen
Reittien käyttöoikeustarkistus suoritetaan, kun testi käynnistää sovelluksen asetuksella `@WebforjTest(app = ...)`. Kun käytössä on `routeClasses` tai `routePackages`, näkymät renderöidään ilman reittien käyttöoikeustarkistuksia, joten suojattua näkymää voi testata sellaisenaan kirjautumatta sisään.
:::

Kukin testi alkaa uudella istunnolla, joten testi, jossa käyttäjä ei kirjaudu sisään, navigoi anonyyminä vierailijana.

:::tip Spring Boot -sovellukset
Spring Securityä käyttävässä Spring Boot -sovelluksessa testi asettaa käyttäjän `@WithMockUser`-annotaatiolla, kuten kohdassa [Spring-testaus](/docs/integrations/spring/testing#testing-secured-routes) kuvataan.
:::

### Uudelleenohjausten tarkistaminen {#checking-redirects}

Kun reittien käyttöoikeustarkistus estää pääsyn, se ohjaa sovelluksen määrittämään todennus- tai estopolkuun. `navigate(String)` palauttaa siellä renderöidyn komponentin, joten testi tarkistaa uudelleenohjauksen kyseisen komponentin tyypin perusteella.

`navigate(Class)` odottaa pyydettyä näkymää. Jos reittien käyttöoikeustarkistus renderöi sen sijaan toisen näkymän, se heittää `IllegalStateException`-poikkeuksen, jossa nimetään molemmat näkymät ja ohjataan käyttämään `navigate(String)`-metodia.

Jos pääsy estetään eikä sovellus määritä todennus- tai estopaikkaa, mitään ei renderöidä. Tällöin molemmat `navigate`-metodit epäonnistuvat viestillä, jossa nimetään reitti ja kerrotaan navigoinnin tulleen estetyksi. Sama virhe ilmoitetaan, jos reitin elinkaaren tarkkailija estää navigoinnin.

## Dialogeihin vastaaminen {#answering-dialogs}

[Valintaikkuna](/docs/components/option-dialogs/overview) pysäyttää sen näyttäneen koodin suorituksen, kunnes käyttäjä vastaa. Testissä ei ole käyttäjää vastaamassa, joten testin on annettava vastaus ennen dialogin näyttämistä. Seuraava näkymä pyytää käyttäjää vahvistamaan ennen raportin poistamista:

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

Ensimmäisessä testissä annetaan vastaus **OK** ennen **Delete**-painikkeen napsauttamista, ja toisessa vastataan **Cancel**:

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

`context.getShownOptionDialogs()` palauttaa järjestyksessä kaikki testin aikana näytetyt dialogit otsikkoineen ja viesteineen, joten ensimmäisessä testissä tarkistetaan myös, mitä dialogissa kysyttiin.

Kullakin dialogityypillä on oma vastausmetodinsa. Seuraava näkymä pyytää uutta nimeä syötedialogissa:

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

Testi vastaa tekstillä, jonka käyttäjä kirjoittaisi:

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

`answerConfirmDialog` saa parametrikseen käyttäjän painaman painikkeen, ja `answerInputDialog` käyttäjän syöttämän tekstin. Tiedostodialogeille annetaan käyttäjän valitsema polku tai tiedostonimi metodeilla `answerFileChooserDialog`, `answerFileSaveDialog` ja `answerFileUploadDialog`. `null`-arvon antaminen syöte- tai tiedostodialogille vastaa **Cancel**-painikkeen painamista. Viestidialogi ei tarvitse vastausta, sillä käyttäjä voi vain sulkea sen.

Vastaukset annetaan kunkin tyyppisille dialogeille siinä järjestyksessä, jossa testi ne asetti. Jos näkymä kysyy kaksi kertaa, se saa siis toisen vastauksen toisella dialogikerralla. Jos dialogi näytetään ilman vastausta, testi epäonnistuu `IllegalStateException`-poikkeukseen, joka nimeää dialogin ja kutsuttavan vastausmetodin.

## Ajastimet {#timers}

Testi ei odota ajan kulumista. Se päättää ajan kulumisesta `context.runIntervals()`-kutsulla. Seuraava näkymä päivittää itsensä viiden sekunnin välein:

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

Jokainen `runIntervals()`-kutsu toimii ikään kuin kunkin käynnissä olevan [aikavälin](/docs/advanced/interval) viive olisi kulunut kerran. Se palauttaa käynnistyneiden ajastimien määrän:

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

[Debouncer](/docs/advanced/debouncing) toimii samalla tavalla. Seuraava hakukenttä suorittaa haun vasta, kun käyttäjä lopettaa kirjoittamisen:

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

Testi kirjoittaa kenttään kahdesti. Hakua ei tehdä ennen `runIntervals()`-kutsua, jonka jälkeen debouncer suoritetaan kerran viimeisellä syötteellä:

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

[`Environment.runLater`](/docs/advanced/asynchronous-updates)-metodia ei tarvitse kutsua erikseen. Kun sitä kutsutaan testistä tai sen ohjaamasta koodista, se suorittaa tehtävän ennen paluutaan, ja sen `PendingResult` on jo valmis.

## Selaimelle vastaaminen {#answering-the-browser}

Osa koodista pyytää selaimelta tulosta, kuten ikkunan leveyttä. Testissä ei ole selainta, joten testi vastaa sen puolesta. Seuraava näkymä näyttää selaimen ilmoittaman leveyden:

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

`answerExecuteJs` antaa vastauksen seuraavalle `executeJs`-kutsulle, joten testi antaa sen ennen napsautusta:

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

`executeJs` palauttaa `null`, jos testi ei antanut sille vastausta. Koodi, joka ei odota selainta, käyttää `executeJsAsync`-metodia, jonka suoritus valmistuu myöhemmin. Seuraava painike kopioi sivun linkin ja vahvistaa kopioinnin, kun selain on valmis:

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

Komentosarja käynnistyy napsautuksesta, joten testi vastaa siihen napsautuksen jälkeen `answerExecuteJsAsync`-metodilla. Se täydentää vanhimman tulosta odottavan komentosarjan:

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

`answerExecuteJsAsync` heittää `IllegalStateException`-poikkeuksen, jos mikään komentosarja ei odota tulosta. `Element` odottaa, että selain määrittelee sen, joten `whenDefined()` ja sen jonoon asettamat kutsut, kuten `callJsFunctionAsync`, odottavat siihen asti, että testi lähettää elementille `ElementDefinedEvent`-tapahtuman `UserInteraction.fireEvent`-metodilla.

<JavadocLink type="testbed-foundation" location="com/webforj/testbed/WebforjTestbed" code="true">WebforjTestbed</JavadocLink>-viite luettelee kaikki testikontekstin metodit.
