---
title: Background Jobs
sidebar_position: 25
_i18n_hash: 6770951556a0f793ce218daeb686b581
---
Kun käyttäjät napsauttavat painiketta raportin luomiseksi tai tietojen käsittelemiseksi, he odottavat käyttöliittymän pysyvän reagoivana. Edistymispalkkien tulisi animoitua, painikkeiden tulisi reagoida hiiren mukana kulkemiseen, eikä sovelluksen pitäisi jumiutua. Springin `@Async`-annotaatio tekee tämän mahdolliseksi siirtämällä pitkäkestoisia operaatioita taustasäikeisiin.

webforJ varmistaa säikeiden turvallisuuden käyttöliittymäkomponenteille - kaikki päivitykset on suoritettava käyttöliittymässä. Tämä aiheuttaa haasteen: kuinka taustatehtävät voivat päivittää edistymispalkkeja tai näyttää tuloksia? Vastaus on `Environment.runLater()`, joka siirtää käyttöliittymän päivitykset turvallisesti Springin taustasäikeistä webforJ:n käyttöliittymän säikeeseen.

## Asynkronisen suorituksen mahdollistaminen {#enabling-asynchronous-execution}

Springin asynkroninen metodisuoritus vaatii eksplisiittistä konfigurointia. Ilman sitä, `@Async`-annotaatiolla merkittyjä metodeja suoritetaan synkronisesti, mikä kumoaa niiden tarkoituksen.

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

`@EnableAsync` -annotaatio aktivoi Springin infrastruktuurin `@Async`-metodien havaitsemiseksi ja niiden suorittamiseksi taustasäikeissä.

:::tip[Springin asynkroninen opas]
Nopeaa esittelyä Springin `@Async` -annotaatiosta ja peruskäyttötavoista varten, katso [Asynkronisten metodien luominen](https://spring.io/guides/gs/async-method).
:::

## Asynkronisten palveluiden luominen {#creating-async-services}

`@Service`-annotaatiolla merkittyjen palveluiden metodeilla voi olla `@Async`-merkintä, jotta niitä voidaan suorittaa taustasäikeissä. Nämä metodit palauttavat tyypillisesti `CompletableFuture`, jotta oikean valmistumisen käsittely ja peruutus voidaan mahdollistaa:

```java
@Service
public class BackgroundService {

  @Async
  public CompletableFuture<String> performLongRunningTask(Consumer<Integer> progressCallback) {
    try {
      for (int i = 0; i <= 10; i++) {
          // Ilmoita edistyksestä
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

Tämä palvelu vastaanottaa edistymisen palautuksen (`Consumer<Integer>`), jota kutsutaan taustasäikeestä. Palautusmalli mahdollistaa palvelun ilmoittavan edistyksestä tietämättä käyttöliittymäkomponenteista.

Metodi simuloi 5 sekunnin tehtävää, jossa on 10 edistymispäivitystä. Tuotannossa tämä olisi todellista työtä, kuten tietokantakyselyjä tai tiedostojen käsittelyä. Poikkeusten käsittely palauttaa keskeytyksen tilan tukemaan oikeaa tehtävän peruuttamista, kun `cancel(true)` kutsutaan.

## Taustatehtävien käyttäminen näkymissä {#using-background-tasks-in-views}

Näkymä vastaanottaa taustapalvelun konstruktorin injektoinnin kautta:

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

Spring injektoi `BackgroundService`-palvelun näkymän konstruktorille, aivan kuten minkä tahansa muun Springin beanin. Näkymä käyttää tätä palvelua käynnistääkseen taustatehtäviä. Keskeinen käsite: palvelun palautukset suoritetaan taustasäikeissä, joten kaikki käyttöliittymän päivitykset niissä palautuksissa on suoritettava `Environment.runLater()`-kutsun avulla.

Valmistumisen käsittely vaatii samaa huolellista säiettä hallintaa:

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

`whenComplete`-palautetta suoritetaan myös taustasäikeessä. Jokainen käyttöliittymätoiminto - painikkeen mahdollistaminen, edistymispalkin piilottaminen, toastin näyttäminen - on pakattava `Environment.runLater()`-kutsun sisään. Ilman tätä pakkausta webforJ heittää poikkeuksia, koska taustasäikeet eivät voi käyttää käyttöliittymäkomponentteja.

:::warning[Säieturvallisuus]
Jokainen käyttöliittymän päivitys taustasäikeestä on pakattava `Environment.runLater()`-kutsun sisään. Tälle säännölle ei ole poikkeuksia. Suora komponenttipääsy `@Async`-metodeista epäonnistuu aina.
:::

:::tip[Oppia lisää säieturvallisuudesta]
Yksityiskohtaisia tietoja webforJ:n säietilasta, suoritus käyttäytymisestä ja siitä, mitkä operaatiot vaativat `Environment.runLater()`, katso [Asynkroniset päivitykset](../../advanced/asynchronous-updates).
:::

## Tehtävän peruuttaminen ja siivoaminen {#task-cancellation-and-cleanup}

Oikea elinkaaren hallinta estää muistivuotoja ja tarpeettomia käyttöliittymän päivityksiä. Näkymä tallentaa `CompletableFuture`-viitteen:

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

`cancel(true)`-parametri on ratkaiseva. Se keskeyttää taustasäikeen, mikä aiheuttaa esteet operaatiot, kuten `Thread.sleep()`, heittävän `InterruptedException`. Tämä mahdollistaa välittömän tehtävän lopettamisen. Ilman keskeytyslippua (`cancel(false)`) tehtävä jatkaisi suorittamista, kunnes se oikein tarkistaa peruutuksen.

Tämä siivoaminen estää useita ongelmia:
- Taustasäikeet kuluttavat edelleen resursseja näkymän kadottua
- Käyttöliittymän päivitykset yrittävät muokata tuhottuja komponentteja
- Muistivuodot palautuksista, jotka pitävät viittauksia käyttöliittymäkomponentteihin
