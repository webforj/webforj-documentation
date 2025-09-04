---
title: Background Jobs
sidebar_position: 25
_i18n_hash: d419b53d933af4ef48890b8be2eab4dc
---
When users click a button to generate a report or process data, they expect the interface to remain responsive. Progress bars should animate, buttons should react to hover, and the app shouldn't freeze. Springin `@Async` annotaatio mahdollistaa tämän siirtämällä pitkäkestoiset toiminnot taustakäthreadille.

webforJ varmistaa säikeiden turvallisuuden käyttöliittymän komponenteille - kaikki päivitykset on suoritettava käyttöliittymässä. Tämä luo haasteen: miten taustatehtävät voivat päivittää edistymispalkkeja tai näyttää tuloksia? Vastaus on `Environment.runLater()`, joka siirtää käyttöliittymän päivitykset turvallisesti Springin taustasäikeiltä webforJ:n käyttöliittymän säikeelle.

## Asynkronisen suorittamisen mahdollistaminen {#enabling-asynchronous-execution}

Springin asynkroninen metodin suorittaminen vaatii eksplisiittistä konfigurointia. Ilman sitä, `@Async`-annotaatiolla merkittyjä metodeja suoritetaan synkronisesti, mikä kumoaa niiden tarkoituksen.

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

`@EnableAsync` annotaatio aktivoi Springin infrastruktuurin havaitsemaan `@Async`-metodit ja suorittamaan ne taustasäikeillä.

:::tip[Spring async guide]
Springin `@Async` annotaation ja perustavanlaatuisten käyttömallien lyhyen esittelyn saamiseksi, katso [Luodaan asynkronisia metodeja](https://spring.io/guides/gs/async-method).
:::

## Asynkronisten palvelujen luominen {#creating-async-services}

`@Service`-annotaatiolla merkittyjen palvelujen metodeilla voi olla `@Async`-Annotaatio, jotta ne voivat toimia taustasäikeillä. Nämä metodit palauttavat tyypillisesti `CompletableFuture`-arvon, jotta voidaan mahdollistaa asianmukainen päättymisen käsittely ja peruutus:

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

Tämä palvelu hyväksyy edistymiskutsun (`Consumer<Integer>`), joka kutsutaan taustasäikeeltä. Kutsumalli mahdollistaa palvelun ilmoittaa edistymisestä ilman, että se tietää käyttöliittymäkomponenteista.

Metodi simuloi 5 sekunnin tehtävää, jossa on 10 edistymispäivitystä. Tuotannossa tämä olisi todellista työtä, kuten tietokantakyselyjä tai tiedostojen käsittelyä. Poikkeusten käsittely palauttaa keskeytyksen tilan tuen tarjoamiseksi, kun `cancel(true)` kutsutaan.

## Taustatehtävien käyttäminen näkymissä {#using-background-tasks-in-views}

Näkymä vastaanottaa taustapalvelun konstruktorisisaanton kautta:

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

Spring injektoi `BackgroundService`-palvelun näkymän konstruktorin, aivan kuten minkä tahansa muun Spring-bigin. Näkymä käyttää tätä palvelua aloittaakseen taustatehtäviä. Keskeinen käsite: palvelusta tulevat palautteet suoritetaan taustasäikeillä, joten kaikki käyttöliittymäpäivitykset näissä palautteissa on käytettävä `Environment.runLater()`-metodia siirtääkseen suoritus käyttöliittymän säikeelle.

Valmistumisen käsittely vaatii samanlaista huolellista säiehallintaa:

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

`whenComplete`-palautteen suorittaminen tapahtuu myös taustasäikeellä. Jokainen käyttöliittymätoiminto - napin mahdollistaminen, edistymispalkin piilottaminen, toastien näyttäminen - on pakattava `Environment.runLater()`-metodin sisään. Ilman tätä pakkausta webforJ heittää poikkeuksia, koska taustasäikeet eivät voi käyttää käyttöliittymäkomponentteja.

:::warning[Säieturvallisuus]
Jokainen käyttöliittymäpäivitys taustasäikeeltä on pakattava `Environment.runLater()`-metodiin. Tällä säännöllä ei ole poikkeuksia. Suora komponenttien käyttö `@Async`-metodeista epäonnistuu aina.
:::

:::tip[Lisätietoja säieturvallisuudesta]
WebforJ:n säietta koskevasta mallista, suorituskäyttäytymisestä ja siitä, mitkä toiminnot vaativat `Environment.runLater()`-metodia, saat lisätietoja [Asynkroniset päivitykset](../../advanced/asynchronous-updates).
:::

## Tehtävän peruutus ja siivous {#task-cancellation-and-cleanup}

Oikea elinkaaren hallinta estää muistivuodot ja ei-toivotut käyttöliittymäpäivitykset. Näkymä tallentaa `CompletableFuture`-viittauksen:

```java
private CompletableFuture<String> currentTask;
```

Kun näkymä tuhotaan, se peruuttaa kaikki käynnissä olevat tehtävät:

```java
@Override
protected void onDestroy() {
    // Peruuta tehtävä, jos näkymä tuhotaan
    if (currentTask != null && !currentTask.isDone()) {
        currentTask.cancel(true);
    }
}
```

`cancel(true)`-parametri on ratkaiseva. Se keskeyttää taustasäikeen, mikä aiheuttaa estävät toiminnot, kuten `Thread.sleep()`, heittämään `InterruptedException`. Tämä mahdollistaa välittömän tehtävän lopettamisen. Ilman keskeytyslippua (`cancel(false)`) tehtävä jatkaisi suorittamista, kunnes se tarkistaa peruutuksen erikseen.

Tämä siivous estää useita ongelmia:
- Taustasäikeet jatkavat resurssien kuluttamista, kun näkymä on poistettu
- Käyttöliittymäpäivitykset yrittävät muuttaa tuhottuja komponentteja
- Muistivuodot palautteista, jotka pitävät viittauksia käyttöliittymäkomponentteihin
