---
sidebar_position: 10
title: Lifecycle Listeners
_i18n_hash: ffb3121402861d501b322c7efca6f669
---
<!-- vale off -->
# Lifecycle-kuuntelijat <DocChip chip='since' label='25.02' />
<!-- vale on -->

`AppLifecycleListener`-rajapinta mahdollistaa ulkoisen koodin havainnoida ja reagoida sovelluksen elinkaaritapahtumiin. Toteuttamalla tämän rajapinnan voit suorittaa koodia tietyissä vaiheissa sovelluksen käynnistämisen ja sulkemisen aikana ilman, että sinun tarvitsee muokata `App`-luokkaa.

Lifecycle-kuuntelijat havaitaan ja ladataan automaattisesti ajonaikaisesti palveluntarjoajien kokoonpanotiedostojen kautta. Jokainen sovelluksen instanssi saa omat kuuntelijainsidenssinsä, säilyttäen eristyksen eri sovellusten välillä, jotka toimivat samassa ympäristössä.

## Milloin käyttää lifecycle-kuuntelijoita {#when-to-use-lifecycle-listeners}

Käytä lifecycle-kuuntelijoita, kun sinun tarvitsee:

- Alustaa resursseja tai palveluja ennen kuin sovellus käynnistyy
- Puhdistaa resursseja, kun sovellus lopetetaan
- Lisätä poikkileikkaavia huolia ilman, että sinun tarvitsee muokata `App`-luokkaa
- Rakentaa liitännäisarkkitehtuureja

## `AppLifecycleListener`-rajapinta {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
  default void onWillCreate(Environment env) {}     // Alkaen 25.03
  default void onDidCreate(App app) {}              // Alkaen 25.03
  default void onWillRun(App app) {}
  default void onDidRun(App app) {}
  default void onWillTerminate(App app) {}
  default void onDidTerminate(App app) {}
}
```

:::info Sovellusten eristys
Jokainen sovelluksen instanssi saa omat kuuntelijainsidenssinsä:

- Kuuntelijat ovat eristyksissä eri sovellusten välillä
- Kuuntelijoiden staattiset kentät eivät jaa sovellusten välillä
- Kuuntelijainsidenssit luodaan, kun sovellus käynnistyy, ja tuhotaan, kun se lopetetaan

Jos sinun tarvitsee jakaa tietoja sovellusten välillä, käytä ulkoisia tallennusmekanismeja, kuten tietokantoja tai jaettuja palveluja.
:::

### Elinkaaritapahtumat {#lifecycle-events}

| Tapahtuma         | Milloin kutsutaan                                      | Yleisimmät käyttötarkoitukset                       |
| ----------------- | ----------------------------------------------------- | --------------------------------------------------- |
| `onWillCreate`&nbsp;<DocChip chip='since' label='25.03' /> | Ympäristön alustamisen jälkeen, ennen sovelluksen luomista  | Muokkaa kokoonpanoa, yhdistä ulkoisia kokoonpanolähteitä |
| `onDidCreate`&nbsp;<DocChip chip='since' label='25.03' />  | Sovelluksen instanssoinnin jälkeen, ennen alustusta        | Varhaiset sovellustason asetukset, rekisteröi palveluja  |
| `onWillRun`       | Ennen `app.run()`-suoritusta                          | Alustaa resursseja, määrittää palveluja                |
| `onDidRun`        | Kun `app.run()` on suoritettu onnistuneesti          | Käynnistä taustatehtäviä, kirjaa onnistunut käynnistys  |
| `onWillTerminate` | Ennen sovelluksen lopetusta                           | Tallenna tila, valmistaudu sammuttamiseen              |
| `onDidTerminate`  | Kun sovellus on lopetettu                            | Puhdista resurssit, lopullinen lokitus                 |

## Elinkaarikuuntelijan luominen {#creating-a-lifecycle-listener}

### Perustoteutus {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;
import com.webforj.Environment;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class StartupListener implements AppLifecycleListener {

  @Override
  public void onWillCreate(Environment env) {
    // Muokkaa kokoonpanoa ennen sovelluksen luomista
    Config additionalConfig = ConfigFactory.parseString(
      "myapp.feature.enabled = true"
    );
    env.setConfig(additionalConfig);
  }

  @Override
  public void onDidCreate(App app) {
    System.out.println("Sovellus luotu: " + app.getId());
  }

  @Override
  public void onWillRun(App app) {
    System.out.println("Sovellus käynnistyy: " + app.getId());
  }

  @Override
  public void onDidRun(App app) {
    System.out.println("Sovellus käynnistynyt: " + app.getId());
  }
}
```

### Kuuntelijan rekisteröinti {#registering-the-listener}

Luo palveluntarjoajien kokoonpanotiedosto:

**Tiedosto**: `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener`

```
com.example.listeners.StartupListener
```

:::tip Käyttämällä AutoService
On helppo unohtaa päivittää palvelukuvaajat. Käytä Googlen [AutoServicea](https://github.com/google/auto/blob/main/service/README.md) automaattisesti palvelutiedoston generointiin:

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
  // Toteutus
}
```
:::

## Suorituksen järjestyksen hallinta {#controlling-execution-order}

Kun useita kuuntelijoita on rekisteröity, voit hallita niiden suorituksen järjestystä käyttämällä `@AppListenerPriority`-annotaatiota. Tämä on erityisen tärkeää, kun kuuntelijoilla on riippuvuuksia toisiinsa tai kun tietty alustus on suoritettava ennen muita.

Prioriteettiarvot toimivat nousevassa järjestyksessä - **alemmat numerot suoritetaan ensin**. Oletusprioriteetti on 10, joten kuuntelijat, joilla ei ole eksplisiittisiä prioriteettiannotaatioita, suoritetaan niiden jälkeen, joilla on alhaisemmat prioriteettiarvot.

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // Suoritetaan ensin - kriittinen turvallisuusasetukset
public class SecurityListener implements AppLifecycleListener {
  @Override
  public void onWillRun(App app) {
    initializeSecurity();
  }
}

@AutoService(AppLifecycleListener.class)
@AppListenerPriority(10) // Oletusprioriteetti - yleinen lokitus
public class LoggingListener implements AppLifecycleListener {
  @Override
  public void onWillRun(App app) {
    initializeLogging();
  }
}
```

### Suorituksen virta App-koukkujen kanssa {#execution-flow-with-app-hooks}

Useiden kuuntelijoiden järjestyksen hallinnan lisäksi on tärkeää ymmärtää, miten kuuntelijat vuorovaikuttavat `App`-luokan omien elinkaarikoukkujen kanssa. Kullekin elinkaaritapahtumalle kehys seuraa tiettyä suoritussekvenssiä, joka määrää, milloin kuuntelijasi suoritetaan suhteessa sovelluksen sisäänrakennettuihin koukkuihin.

Alla oleva kaavio havainnollistaa tätä suorituksen virtaa, näyttäen tarkasti, milloin `AppLifecycleListener`-metodit kutsutaan suhteessa vastaaviin `App`-koukkuihin:

<div align="center">

![AppLifecycleListener kuuntelijat VS `App` koukut](/img/lifecycle-listeners.svg)

</div>


## Virheen käsittely {#error-handling}

Kuuntelijoiden aiheuttamat poikkeukset lokitetaan, mutta ne eivät estä muiden kuuntelijoiden suorittamista tai sovelluksen käynnistymistä. Käsittele aina poikkeuksia kuuntelijoissasi:

```java title="Virheiden käsittely esimerkki"
@Override
public void onWillRun(App app) {
  try {
    riskyInitialization();
  } catch (Exception e) {
    logger.error("Alustus epäonnistui", e);
  }
}
```
