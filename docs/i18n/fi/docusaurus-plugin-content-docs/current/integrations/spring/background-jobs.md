---
title: Background Jobs
sidebar_position: 25
_i18n_hash: 4f924436d02caee3bb07967d7055b0bc
---
Kun käyttäjät napsauttavat painiketta raportin luomiseksi tai tietojen käsittelemiseksi, he odottavat käyttöliittymän pysyvän responsiivisena. Edistymispalkkien tulisi animoitua, painikkeiden tulisi reagoida hiiren hover-tilaan, ja sovelluksen ei pitäisi jäätyä. Springin `@Async`-annotaatio mahdollistaa tämän siirtämällä pitkäkestoiset toiminnot taustatekijöihin.

webforJ valvoo säikeen turvallisuutta käyttöliittymäkomponenteille - kaikki päivitykset on tehtävä käyttöliittymän säikeessä. Tämä luo haasteen: kuinka taustatehtävät päivittävät edistymispalkkeja tai näyttävät tuloksia? Vastaus on `Environment.runLater()`, joka siirtää käyttöliittymäpäivitykset turvallisesti Springin taustatekijöistä webforJ:n käyttöliittymän säikeeseen.

## Enabling asynchronous execution {#enabling-asynchronous-execution}

Springin asynkroninen menetelmäohjaus vaatii eksplisiittistä konfigurointia. Ilman sitä, `@Async`-annotaatiolla merkittyjä menetelmiä suoritetaan synkronisesti, mikä kumoaa niiden tarkoituksen.

Lisää `@EnableAsync` Spring Boot -sovelluksesi luokkaan:

```java {2}
@SpringBootApplication
@EnableAsync
@Routify(packages = { "com.example.views" })
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

`@EnableAsync`-annotaatio aktivoi Springin infrastruktuurin `@Async`-menetelmien havaitsemiseksi ja niiden suorittamiseksi taustatekijöissä.

:::tip[Spring async guide]
Nopea esittely Springin `@Async`-annotaatiosta ja peruskäyttökuvioista löytyy osoitteesta [Creating Asynchronous Methods](https://spring.io/guides/gs/async-method).
:::

## Creating async services {#creating-async-services}

`@Service`-annotaatiolla merkittyjen palvelujen menetelmissä voi olla `@Async`-merkintä, jotta ne voivat suorittaa toimintoja taustatekijöissä. Nämä menetelmät palauttavat tyypillisesti `CompletableFuture`-objektin asianmukaisen täydentämisen hallinnan ja keskeyttämisen mahdollistamiseksi:

```java
@Service
public class BackgroundService {

  @Async
  public CompletableFuture<String> performLongRunningTask(Consumer<Integer> progressCallback) {
    try {
      for (int i = 0; i <= 10; i++) {
          // Ilmoita edistymisestä
          int progress = i * 10;
          if (progressCallback != null) {
              progressCallback.accept(progress);
          }

          // Simuloi työtä
          Thread.sleep(500);
      }

      return CompletableFuture.completedFuture(
        "Tehtävä suoritettu onnistuneesti taustapalvelusta!");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return CompletableFuture.failedFuture(e);
    }
  }
}
```

Tämä palvelu hyväksyy edistymiskutsun (`Consumer<Integer>`), joka kutsutaan taustatekijästä. Kutsumallimalli mahdollistaa palvelun ilmoittavan edistymisestä tietämättä käyttöliittymäkomponenteista. 

Menetelmä simuloi 5 sekunnin tehtävää, johon kuuluu 10 edistymispäivitystä. Tuotannossa tämä olisi todellista työtä, kuten tietokantakyselyitä tai tiedostojen käsittelyä. Poikkeuksen käsittely palauttaa keskeyttämistilan tukemaan asianmukaista tehtävän keskeyttämistä, kun `cancel(true)` kutsutaan.

## Using background tasks in views {#using-background-tasks-in-views}

Näkymä vastaanottaa taustapalvelun konstruktori-injektion kautta:

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("Aloita taustatehtävä");
  private ProgressBar progressBar = new ProgressBar();
  private CompletableFuture<String> currentTask;

  public HelloWorldView(BackgroundService backgroundService) {
    // Palvelu injektoidaan Springin kautta
    asyncBtn.addClickListener(e -> {
      currentTask = backgroundService.performLongRunningTask(progress -> {
        Environment.runLater(() -> {
          progressBar.setValue(progress);
        });
      });
    });
  }
}
```

Spring injektoi `BackgroundService`:n näkymän konstruktoriin, aivan kuten mikä tahansa muu Spring-bean. Näkymä käyttää tätä palvelua taustatehtävien käynnistämiseen. Avainkällä: palvelusta tulevat palautteet suoritetaan taustatekijöissä, joten kaikki käyttöliittymäpäivitykset näissä palautteissa on suoritettava `Environment.runLater()` avulla siirtääkseen suorituksen käyttöliittymän säikeelle.

Valmistumisen käsittely vaatii samaa huolellista säiehallintaa:

```java
currentTask.whenComplete((result, error) -> {
  Environment.runLater(() -> {
    asyncBtn.setEnabled(true);
    progressBar.setVisible(false);
    if (error != null) {
      Toast.show("Tehtävä epäonnistui: " + error.getMessage(), Theme.DANGER);
    } else {
      Toast.show(result, Theme.SUCCESS);
    }
  });
});
```

`whenComplete`-kutsumalli suoritetaan myös taustatekijässä. Jokainen käyttöliittymätoiminto - painikkeen aktivointi, edistymispalkin piilottaminen, toastien näyttäminen - on käärittävä `Environment.runLater()`-kutsuun. Ilman tätä käärettä webforJ heittää poikkeuksia, koska taustatekijät eivät voi käyttää käyttöliittymäkomponentteja.

:::warning[Thread safety]
Jokainen käyttöliittymäpäivitys taustasäikeestä on käärittävä `Environment.runLater()`-kutsuun. Tälle säännölle ei ole poikkeuksia. Suora komponenttiin pääsy `@Async`-menetelmistä epäonnistuu aina.
:::

:::tip[Learn more about thread safety]
Yksityiskohtaiset tiedot webforJ:n säiemallista, suorituskäyttäytymisestä ja siitä, mitkä toiminnot vaativat `Environment.runLater()`, löydät sivulta [Asynchronous Updates](../../advanced/asynchronous-updates).
:::

## Task cancellation and cleanup {#task-cancellation-and-cleanup}

Oikea elinkaaren hallinta estää muistivuotot ja ei-toivotut käyttöliittymäpäivitykset. Näkymä tallentaa `CompletableFuture`-viittauksen:

```java
private CompletableFuture<String> currentTask;
```

Kun näkymä tuhotaan, se keskeyttää kaiken käynnissä olevan tehtävän:

```java
@Override
protected void onDestroy() {
  // Keskeytä tehtävä, jos näkymä tuhotaan
  if (currentTask != null && !currentTask.isDone()) {
    currentTask.cancel(true);
  }
}
```

`cancel(true)`-parametri on ratkaisevan tärkeä. Se keskeyttää taustatekijän, mikä aiheuttaa estoviiveet, kuten `Thread.sleep()` heittää `InterruptedException`. Tämä mahdollistaa välittömän tehtävän lopettamisen. Ilman keskeytyslippua (`cancel(false)`), tehtävä jatkaisi suorittamista, kunnes se tarkistaa nimenomaisesti keskeyttämisen.

Tämä puhdistus estää useita ongelmia:
- Taustatekijät kuluttavat edelleen resursseja näkymän ollessa poissa
- Käyttöliittymäpäivitykset yrittävät muokata tuhottuja komponentteja
- Muistivuotot palautekutsujen pitämistä viittauksia käyttöliittymäkomponentteihin
