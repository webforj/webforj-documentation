---
title: Background Jobs
sidebar_position: 25
description: >-
  Run Spring @Async services from webforJ views and marshal progress and results
  back to the UI thread with Environment.runLater.
_i18n_hash: 1b265d2e723c0f58c97fd2c4375f15a1
---
Kun käyttäjät napsauttavat painiketta raportin luomiseksi tai tietojen käsittelemiseksi, he odottavat käyttöliittymän pysyvän reaktiivisena. Edistymispalkkien tulisi animoitua, painikkeet reagoida hover-tilassa, eikä sovelluksen tulisi jäätyä. Springin `@Async`-annotaatio mahdollistaa tämän siirtämällä pitkät toiminnot taustasäikeisiin.

webforJ valvoo säikeiden turvallisuutta käyttöliittymän komponenteille - kaikki päivitykset on tehtävä käyttöliittymän säikeessä. Tämä luo haasteen: kuinka taustatehtävät voivat päivittää edistymispalkkeja tai näyttää tuloksia? Vastaus on `Environment.runLater()`, joka siirtää käyttöliittymän päivitykset turvallisesti Springin taustasäikeistä webforJ:n käyttöliittymän säikeeseen.

## Asynkronisen suorittamisen mahdollistaminen {#enabling-asynchronous-execution}

Springin asynkronisen metodin suorittaminen vaatii eksplisiittistä konfigurointia. Ilman sitä, `@Async`-annotaatiolla varustetut metodit suoritetaan synkronisesti, mikä kumoaa niiden tarkoituksen.

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

`@EnableAsync` -annotaatio aktivoi Springin infrastruktuurin `@Async`-metodien tunnistamiseksi ja niiden suorittamiseksi taustasäikeissä.

:::tip[Springin asynkroninen opas]
Nopea johdanto Springin `@Async`-annotaatioon ja perustason käyttömallit löytyvät täältä: [Luodaan asynkronisia metodeja](https://spring.io/guides/gs/async-method).
:::

## Asynkronisten palvelujen luominen {#creating-async-services}

`@Service`-annotaatiolla varustetuilla palveluilla voi olla metodeja, jotka on merkitty `@Async`:lla ja joita suoritetaan taustasäikeissä. Nämä metodit palauttavat yleensä `CompletableFuture`-tyyppisiä arvoja, jotta voimme käsitellä valmistumista ja peruutusta oikein:

```java
@Service
public class BackgroundService {

  @Async
  public CompletableFuture<String> performLongRunningTask(Consumer<Integer> progressCallback) {
    try {
      for (int i = 0; i <= 10; i++) {
          // Raportoi edistyminen
          int progress = i * 10;
          if (progressCallback != null) {
              progressCallback.accept(progress);
          }

          // Simuloi työtä
          Thread.sleep(500);
      }

      return CompletableFuture.completedFuture(
        "Tehtävä suoritettu onnistuneesti taustapalvelimelta!");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return CompletableFuture.failedFuture(e);
    }
  }
}
```

Tämä palvelu hyväksyy edistymiskutsun (`Consumer<Integer>`), jota kutsutaan taustasäikeestä. Kutsumalli mahdollistaa palvelun raportoinnin edistyksestä ilman tietoa käyttöliittymän komponenteista.

Metodi simuloi 5 sekunnin toimintoa, jossa on 10 edistymispäivitystä. Tuotannossa tämä olisi todellista työtä, kuten tietokantakyselyjä tai tiedostojen käsittelyä. Poikkeusten käsittely palauttaa keskeytysstatuksen tukeakseen asianmukaista tehtävän peruutusta, kun `cancel(true)` kutsutaan.

## Taustatehtävien käyttäminen näkymissä {#using-background-tasks-in-views}

Näkymä vastaanottaa taustapalvelun konstruktori-injektion avulla:

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("Aloita taustatehtävä");
  private ProgressBar progressBar = new ProgressBar();
  private CompletableFuture<String> currentTask;

  public HelloWorldView(BackgroundService backgroundService) {
    // Palvelu injektoidaan Springin avulla
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

Spring injektoi `BackgroundService`-palvelun näkymän konstruktorin kautta, aivan kuten kaikki muutkin Spring-beanit. Näkymä käyttää tätä palvelua taustatehtävien käynnistämiseen. Keskeinen käsite: palvelusta tulevat kutsut suoritetaan taustasäikeissä, joten kaikki käyttöliittymän päivitykset näiden kutsujen sisällä on suoritettava `Environment.runLater()` -menetelmällä, jotta suoritus siirtyy käyttöliittymän säikeeseen.

Valmistumisen käsittely vaatii samaa huolellista säiemanagerointia:

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

`whenComplete`-kutsu suoritetaan myös taustasäikeessä. Jokainen käyttöliittymätoiminto - kuten painikkeen aktivointi, edistymispalkin piilottaminen, toast-viestien näyttäminen - on pakattava `Environment.runLater()` -menetelmään. Ilman tätä pakkausta webforJ heittää poikkeuksia, koska taustasäikeet eivät voi käyttää käyttöliittymäkomponentteja.

:::warning[Säieturvallisuus]
Jokainen käyttöliittymän päivitys taustasäikeestä on pakattava `Environment.runLater()` -menetelmään. Tälle säännölle ei ole poikkeuksia. Suora komponenttipääsy `@Async`-metodeista epäonnistuu aina.
:::

:::tip[Lisätietoja säieturvallisuudesta]
Yksityiskohtaiset tiedot webforJ:n säiemallista, suorituskäyttäytymisestä ja siitä, mitkä toiminnot vaativat `Environment.runLater()`, löydät täältä: [Asynkroniset päivitykset](../../advanced/asynchronous-updates).
:::

## Tehtävän peruutus ja siivous {#task-cancellation-and-cleanup}

Oikea elinkaaren hallinta estää muistivuodot ja ei-toivotut käyttöliittymän päivitykset. Näkymä tallentaa `CompletableFuture`-viittauksen:

```java
private CompletableFuture<String> currentTask;
```

Kun näkymä tuhoutuu, se peruuttaa kaikki käynnissä olevat tehtävät:

```java
@Override
protected void onDestroy() {
  // Peruuta tehtävä, jos näkymä tuhoutuu
  if (currentTask != null && !currentTask.isDone()) {
    currentTask.cancel(true);
  }
}
```

`cancel(true)`-parametri on ratkaiseva. Se keskeyttää taustasäikeen, jolloin estävät toiminnot, kuten `Thread.sleep()`, heittävät `InterruptedException`-poikkeuksen. Tämä mahdollistaa välittömän tehtävän lopettamisen. Ilman keskeytysmerkkiä (`cancel(false)`), tehtävä jatkaisi suorittamista, kunnes se tarkistaa nimenomaisesti peruutuksen.

Tämä siivous estää useita ongelmia:
- Taustasäikeet kuluttavat edelleen resursseja näkymän häviämisen jälkeen
- Käyttöliittymän päivitykset yrittävät muokata tuhottuja komponentteja
- Muistivuodot, jotka johtuvat kutsuista, jotka pitävät viittauksia käyttöliittymän komponentteihin
