---
title: Taustatyöt
sidebar_position: 25
description: >-
  Run Spring @Async services from webforJ views and marshal progress and results
  back to the UI thread with Environment.runLater.
_i18n_hash: 2760d6db6a7b7045e7612ca359d04a28
---
Kun käyttäjät napsauttavat painiketta raportin luomiseksi tai tietojen käsittelemiseksi, he odottavat käyttöliittymän pysyvän responsiivisena. Edistymispalkkien pitäisi animoitua, painikkeiden reagoida osoittimen viemiseen niiden päälle, eikä sovelluksen pitäisi jumiutua. Springin `@Async`-annotaatio mahdollistaa tämän siirtämällä pitkäkestoiset toiminnot taustasäikeisiin.

webforJ varmistaa käyttöliittymäkomponenttien säieturvallisuuden – kaikki päivitykset on tehtävä käyttöliittymäsäikeessä. Tästä syntyy haaste: miten taustatehtävät voivat päivittää edistymispalkkeja tai näyttää tuloksia? Ratkaisu on `Environment.runLater()`, joka siirtää käyttöliittymäpäivitykset turvallisesti Springin taustasäikeistä tehtävän käynnistäneen käyttäjän käyttöliittymäsäikeeseen.

## Asynkronisen suorituksen käyttöönotto {#enabling-asynchronous-execution}

Springin asynkronisten metodien suoritus vaatii nimenomaisen määrityksen. Ilman sitä `@Async`-annotaatiolla merkityt metodit suoritetaan synkronisesti, jolloin niiden tarkoitus jää toteutumatta.

Lisää `@EnableAsync` Spring Boot -sovellusluokkaasi:

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

`@EnableAsync`-annotaatio ottaa käyttöön Springin infrastruktuurin, joka tunnistaa `@Async`-metodit ja suorittaa ne taustasäikeissä.

:::tip[Springin asynkronisuuden opas]
Springin `@Async`-annotaation ja sen peruskäyttötapojen pikaesittely löytyy oppaasta [Asynchronous Methods](https://spring.io/guides/gs/async-method).
:::

## Asynkronisten palveluiden luominen {#creating-async-services}

`@Service`-annotaatiolla merkityt palvelut voivat sisältää `@Async`-annotaatiolla merkittyjä metodeja, jotka suoritetaan taustasäikeissä. Nämä metodit palauttavat yleensä `CompletableFuture`-olion, jotta suorituksen valmistumista voidaan käsitellä ja tehtävä peruuttaa asianmukaisesti:

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
        "Taustapalvelun tehtävä suoritettiin onnistuneesti!");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return CompletableFuture.failedFuture(e);
    }
  }
}
```

Palvelu vastaanottaa edistymisen takaisinkutsun (`Consumer<Integer>`), jota kutsutaan taustasäikeestä. Takaisinkutsumallin ansiosta palvelu voi ilmoittaa edistymisestä tuntematta käyttöliittymäkomponentteja.

Metodi simuloi viiden sekunnin tehtävää ja ilmoittaa edistymisestä 10 kertaa. Tuotantokäytössä tehtävä olisi todellista työtä, kuten tietokantakyselyitä tai tiedostojen käsittelyä. Poikkeusten käsittely palauttaa keskeytystilan, jotta tehtävän peruutus toimii oikein, kun kutsutaan `cancel(true)`.

## Taustatehtävien käyttäminen näkymissä {#using-background-tasks-in-views}

Näkymä saa taustapalvelun konstruktorin injektoinnin kautta:

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("Käynnistä taustatehtävä");
  private ProgressBar progressBar = new ProgressBar();
  private CompletableFuture<String> currentTask;

  public HelloWorldView(BackgroundService backgroundService) {
    // Spring injektoi palvelun
    asyncBtn.addClickListener(e -> {
      // Napsautuksen käsittelijä suoritetaan tämän käyttäjän käyttöliittymäsäikeessä
      Environment env = Environment.getCurrent();

      currentTask = backgroundService.performLongRunningTask(progress -> {
        Environment.runLater(env, () -> {
          progressBar.setValue(progress);
        });
      });
    });
  }
}
```

Spring injektoi `BackgroundService`-palvelun näkymän konstruktoriin aivan kuten minkä tahansa muunkin Spring-beanin. Tämän jälkeen näkymä käyttää palvelua taustatehtävien käynnistämiseen. Keskeinen asia on, että palvelun takaisinkutsut suoritetaan Springin säievarannossa, jota kaikki sovelluksen käyttäjät jakavat. Siksi säievarannon säie ei voi tietää, kenen käyttäjän käyttöliittymää sen pitäisi päivittää. Napsautuksen käsittelijä lukee käyttäjän `Environment`-olion kutsumalla `Environment.getCurrent()`-metodia, kun se on vielä käyttöliittymäsäikeessä. Jokainen takaisinkutsun sisäinen käyttöliittymäpäivitys välittää tämän olion `Environment.runLater()`-metodille.

Suorituksen valmistumisen käsittely edellyttää yhtä huolellista säikeiden hallintaa:

```java
currentTask.whenComplete((result, error) -> {
  Environment.runLater(env, () -> {
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

Myös `whenComplete`-takaisinkutsu suoritetaan taustasäikeessä. Kaikki käyttöliittymätoiminnot – painikkeen käyttöönotto, edistymispalkin piilottaminen ja ilmoitusten näyttäminen – on käärittävä `Environment.runLater()`-kutsuun käyttäen samaa `env`-oliota. Ilman tätä käärettä webforJ heittää poikkeuksia, koska taustasäikeet eivät voi käyttää käyttöliittymäkomponentteja.

:::warning[Säieturvallisuus]
Jokainen taustasäikeestä tehtävä käyttöliittymäpäivitys on käärittävä `Environment.runLater()`-kutsuun. `Environment` on luettava käyttöliittymäsäikeessä ennen tehtävän käynnistämistä. Tästä säännöstä ei ole poikkeuksia. Suora komponenttien käyttö `@Async`-metodeista epäonnistuu aina, ja `Environment.getCurrent()` palauttaa `null` säievarannon säikeessä.
:::

:::tip[Lisätietoja säieturvallisuudesta]
Lisätietoja webforJ:n säiemallista, suorituksen toiminnasta ja siitä, mitkä toiminnot edellyttävät `Environment.runLater()`-kutsua, on kohdassa [Asynchronous Updates](../../advanced/asynchronous-updates).
:::

## Tehtävän peruutus ja siivous {#task-cancellation-and-cleanup}

Asianmukainen elinkaaren hallinta ehkäisee muistivuotoja ja ei-toivottuja käyttöliittymäpäivityksiä. Näkymä tallentaa viitteen `CompletableFuture`-olioon:

```java
private CompletableFuture<String> currentTask;
```

Kun näkymä tuhotaan, se peruuttaa käynnissä olevat tehtävät:

```java
@Override
protected void onDestroy() {
  // Peruuta tehtävä, jos näkymä tuhotaan
  if (currentTask != null && !currentTask.isDone()) {
    currentTask.cancel(true);
  }
}
```

Parametri `cancel(true)` on ratkaisevan tärkeä. Se keskeyttää taustasäikeen, jolloin estävät toiminnot, kuten `Thread.sleep()`, heittävät `InterruptedException`-poikkeuksen. Näin tehtävä voidaan pysäyttää heti. Ilman keskeytysmerkintää (`cancel(false)`) tehtävä jatkaisi suoritustaan, kunnes se tarkistaa erikseen, onko se peruutettu.

Tämä siivous ehkäisee useita ongelmia:
- Taustasäikeet kuluttavat resursseja edelleen näkymän sulkemisen jälkeen
- Käyttöliittymäpäivitykset yrittävät muokata tuhottuja komponentteja
- Takaisinkutsut säilyttävät viitteitä käyttöliittymäkomponentteihin ja aiheuttavat muistivuotoja
