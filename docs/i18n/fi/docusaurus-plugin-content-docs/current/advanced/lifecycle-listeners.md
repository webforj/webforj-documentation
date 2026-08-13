---
sidebar_position: 10
title: Lifecycle Listeners
description: >-
  Hook into webforJ app startup and shutdown phases with AppLifecycleListener to
  initialize services, modify config, or clean up resources.
_i18n_hash: 3ef33ca5104ef421c38d3db16c9fa453
---
<!-- vale off -->
# Elämänkierron kuuntelijat <DocChip chip='since' label='25.02' />
<!-- vale on -->

`AppLifecycleListener` -rajapinta mahdollistaa ulkoisen koodin havaita ja reagoida sovelluksen elämänkierron tapahtumiin. Toteuttamalla tämän rajapinnan voit suorittaa koodia tietyissä vaiheissa sovelluksen käynnistyksen ja sulkemisen aikana ilman, että sinun tarvitsee muuttaa itse `App`-luokkaa.

Elämänkierron kuuntelijat havaitaan ja ladataan automaattisesti ajon aikana palveluntarjoajien konfigurointitiedostojen kautta. Jokaiselle sovellusinstanssille annetaan oma joukko kuuntelija-instansseja, mikä ylläpitää eristyneisyyttä eri sovellusten välillä, jotka toimivat samassa ympäristössä.

## Milloin käyttää elämänkierron kuuntelijoita {#when-to-use-lifecycle-listeners}

Käytä elämänkierron kuuntelijoita, kun sinun tarvitsee:

- Alustaa resursseja tai palveluja ennen sovelluksen suorittamista
- Siivota resursseja, kun sovellus päättyy
- Lisätä poikkileikkaavia huolenaiheita ilman, että muutat `App`-luokkaa
- Rakentaa lisäosarakenteita

## `AppLifecycleListener` -rajapinta {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
  default void onWillCreate(Environment env) {}     // Nyt 25.03
  default void onDidCreate(App app) {}              // Nyt 25.03
  default void onWillRun(App app) {}
  default void onDidRun(App app) {}
  default void onWillTerminate(App app) {}
  default void onDidTerminate(App app) {}
}
```

:::info Sovellusten eristyneisyys
Jokaiselle sovellusinstanssille annetaan oma joukko kuuntelija-instansseja:

- Kuuntelijat ovat eristyksissä eri sovellusten välillä
- Kuuntelijoiden staattisia kenttiä ei jaeta sovellusten välillä
- Kuuntelija-instanssit luodaan, kun sovellus alkaa, ja ne tuhotaan, kun se päättyy

Jos sinun tarvitsee jakaa tietoja sovellusten välillä, käytä ulkoisia tallennusmekanismeja, kuten tietokantoja tai jaettuja palveluja.
:::

### Elämänkierron tapahtumat {#lifecycle-events}

| Tapahtuma         | Milloin kutsutaan                                       | Yleisimmät käyttötarkoitukset                        |
| ----------------- | ------------------------------------------------------- | ----------------------------------------------------- |
| `onWillCreate`&nbsp;<DocChip chip='since' label='25.03' /> | Ympäristön alustamisen jälkeen, ennen sovelluksen luomista  | Muokata asetuksia, yhdistää ulkoisia konfiguraatioita |
| `onDidCreate`&nbsp;<DocChip chip='since' label='25.03' />  | Sovelluksen luomisen jälkeen, ennen alustamista        | Varhaiset sovelluskohtaiset asetukset, rekisteröi palveluja  |
| `onWillRun`       | Ennen `app.run()` -suoritusta                           | Alustaa resursseja, konfiguroi palveluja              |
| `onDidRun`        | Kun `app.run()` onnistuneesti päättyy                  | Aloittaa taustatehtäviä, lokittaa onnistunut käynnistys |
| `onWillTerminate` | Ennen sovelluksen sulkemista                            | Tallettaa tilan, valmistautua sulkemiseen            |
| `onDidTerminate`  | Sovelluksen sulkemisen jälkeen                           | Siivoa resursseja, lopullinen lokitus                |

## Elämänkierron kuuntelijan luominen {#creating-a-lifecycle-listener}

### Perusimplementaatio {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;
import com.webforj.Environment;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class StartupListener implements AppLifecycleListener {

  @Override
  public void onWillCreate(Environment env) {
    // Muokkaa asetuksia ennen sovelluksen luomista
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
    System.out.println("Sovellus käynnistyi: " + app.getId());
  }
}
```

### Kuuntelijan rekisteröiminen {#registering-the-listener}

Luo palveluntarjoajan konfigurointitiedosto:

**Tiedosto**: `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener`

```
com.example.listeners.StartupListener
```

:::tip AutoService -käyttö
On helppo unohtaa päivittää palvelukuvastot. Käytä Googlen [AutoService](https://github.com/google/auto/blob/main/service/README.md) -työkalua palvelutiedoston automaattiseen luomiseen:

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
  // Implementaatio
}
```
:::

## Suoritusjärjestyksen hallinta {#controlling-execution-order}

Kun useita kuuntelijoita on rekisteröity, voit hallita niiden suoritusjärjestystä käyttäen `@AppListenerPriority` -annotaatiota. Tämä on erityisen tärkeää, kun kuuntelijoilla on riippuvuuksia toisistaan tai kun tiettyjen alustusten on tapahduttava ennen muita.

Prioriteettiarvot toimivat nousevassa järjestyksessä - **alemmat numerot suoritetaan ensin**. Oletusprioriteetti on 10, joten ilmaisutaidot ilman erityistä prioriteettiannotaatiota suoritetaan niiden jälkeen, joilla on alemmat prioriteettiarvot.

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // Suoritetaan ensin - kriittinen tietoturvapuuhastelu
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

### Suoritusprosessi sovelluksen koukuilla {#execution-flow-with-app-hooks}

Useiden kuuntelijoiden suoritusjärjestyksen hallinnan ohella on tärkeää ymmärtää, miten kuuntelijat vuorovaikuttavat `App`-luokan omien elämänkierron koukkujen kanssa. Jokaisessa elämänkierron tapahtumassa kehys seuraa erityistä suoritusjärjestystä, joka määrittää, milloin kuuntelijasi suoritetaan suhteessa sovelluksen sisäisiin koukkuihin.

Alla oleva kaavio havainnollistaa tätä suoritusprosessia, näyttäen tarkan ajankohdan, jolloin `AppLifecycleListener` -menetelmiä kutsutaan suhteessa vastaaviin `App`-koukkuihin:

<div align="center">

![AppLifecycleListener kuuntelijat VS `App` koukut](/img/lifecycle-listeners.svg)

</div>


## Virheiden käsittely {#error-handling}

Kuuntelijoiden heittämät poikkeukset kirjataan, mutta ne eivät estä muiden kuuntelijoiden suorittamista tai sovelluksen toimintaa. Käsittele aina poikkeuksia kuuntelijoissasi:

```java title="Virheenkäsittelyesimerkki"
@Override
public void onWillRun(App app) {
  try {
    riskyInitialization();
  } catch (Exception e) {
    logger.error("Alustus epäonnistui", e);
  }
}
```
