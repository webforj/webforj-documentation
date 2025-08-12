---
sidebar_position: 10
title: Lifecycle Listeners
sidebar_class_name: new-content
_i18n_hash: 95e3a7e349b0cf54679daf76d2bf209c
---
<!-- vale off -->
# Elämänsyklin kuuntelijat <DocChip chip='since' label='25.02' />
<!-- vale on -->

`AppLifecycleListener`-rajapinta mahdollistaa ulkoisen koodin havainnoida ja reagoida sovelluksen elämänsyklin tapahtumiin. Toteuttamalla tämän rajapinnan voit suorittaa koodia tietyissä kohdissa sovelluksen käynnistämisen ja sulkemisen aikana ilman, että sinun tarvitsee muuttaa `App`-luokkaa itse.

Elämänsyklin kuuntelijat löydetään ja ladataan automaattisesti ajon aikana palveluntarjoajan konfiguraatiotiedostojen kautta. Jokainen sovelluksen ilmentymä saa oman joukon kuuntelijoita, mikä pitää eristyneenä eri sovellusten välillä, jotka ajavat samassa ympäristössä.

## Milloin käyttää elämänsyklin kuuntelijoita {#when-to-use-lifecycle-listeners}

Käytä elämänsyklin kuuntelijoita, kun sinun on:
- Alustaa resursseja tai palveluja ennen sovelluksen ajamista
- Siivota resursseja, kun sovellus päättyy  
- Lisätä ristiinpoliittisia huolenaiheita ilman, että muokkaat `App`-luokkaa
- Rakentaa liitännäisarkkitehtuureja

## `AppLifecycleListener`-rajapinta {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
    default void onWillRun(App app) {}
    default void onDidRun(App app) {}
    default void onWillTerminate(App app) {}
    default void onDidTerminate(App app) {}
}
```

:::info Sovellus eristyneenä
Jokainen sovellusinstanssi saa oman joukon kuuntelijoita:
- Kuuntelijat ovat eristyneitä eri sovellusten välillä
- Statiiset kentät kuuntelijoissa eivät jaa tietoja sovellusten välillä
- Kuuntelija-instanssit luodaan, kun sovellus käynnistyy, ja ne tuhotaan, kun sovellus päättyy

Jos sinun tarvitsee jakaa tietoja sovellusten välillä, käytä ulkoisia tallennusmekanismeja, kuten tietokantoja tai jaettuja palveluja.
:::

### Elämänsyklin tapahtumat {#lifecycle-events}

| Tapahtuma | Milloin kutsutaan | Yleiset käytöt |
|-----------|------------------|----------------|
| `onWillRun` | Ennen `app.run()`-suoritusta | Resurssien alustaminen, palveluiden konfigurointi |
| `onDidRun` | Kun `app.run()` on onnistuneesti suoritettu | Taustatehtävien käynnistäminen, onnistuneen käynnistyksen lokittaminen |
| `onWillTerminate` | Ennen sovelluksen päättymistä | Tilanteen tallentaminen, sulkemiseen valmistautuminen |
| `onDidTerminate` | Sovelluksen päättymisen jälkeen | Resurssien siivoaminen, lopullinen lokitus |

## Elämänsyklin kuuntelijan luominen {#creating-a-lifecycle-listener}

### Perusimplementaatio {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;

public class StartupListener implements AppLifecycleListener {
    
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

### Kuuntelijan rekisteröinti {#registering-the-listener}

Luo palveluntarjoajan konfiguraatiotiedosto:

**Tiedosto**: `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener`

```
com.example.listeners.StartupListener
```

:::tip AutoService:n käyttö
On helppo unohtaa päivittää palvelukuvaajat. Käytä Googlen [AutoService](https://github.com/google/auto/blob/main/service/README.md) -työkalua luodaksesi palvelutiedoston automaattisesti:

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
    // Implementation
}
```
:::

## Suoritusjärjestyksen hallinta {#controlling-execution-order}

Kun useita kuuntelijoita on rekisteröity, voit hallita niiden suoritusjärjestystä käyttämällä `@AppListenerPriority`-annotaatiota. Tämä on erityisen tärkeää, kun kuuntelijoilla on toisiinsa liittyviä riippuvuuksia tai kun tietty alustaminen on suoritettava ennen toisia.

Prioriteettiarvot toimivat nousevassa järjestyksessä - **pienemmät numerot suoritetaan ensin**. Oletusprioriteetti on 10, joten kuuntelijat, joilla ei ole eksplisiittisiä prioriteettiannotaatioita, suoritetaan niiden jälkeen, joilla on pienempiä prioriteettiarvoja.

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // Suoritetaan ensimmäisenä - kriittinen turvallisuusasetelma
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

### Suoritusvirran hallinta sovelluksen koukuilla {#execution-flow-with-app-hooks}

Lisäksi hallitsemalla järjestystä useiden kuuntelijoiden välillä, on tärkeää ymmärtää, miten kuuntelijat ovat vuorovaikutuksessa `App`-luokan omien elämänsyklin koukkujen kanssa. Jokaiselle elämänsyklin tapahtumalle kehys seuraa tiettyä suoritusjärjestystä, joka määrää, milloin kuuntelijat suoritetaan suhteessa sovelluksen sisäänrakennettuihin koukkuihin.

Alla oleva kaavio havainnollistaa tätä suoritusvirtaa, näyttäen tarkasti, milloin `AppLifecycleListener`-menetelmät kutsutaan suhteessa vastaaviin `App`-koukkuihin: 

<div align="center">

![AppLifecycleListener kuuntelijat VS `App` koukut](/img/lifecycle-listeners.svg)

</div>


## Virheiden käsittely {#error-handling}

Kuuntelijoiden heittämät poikkeukset kirjataan, mutta ne eivät estä muiden kuuntelijoiden suorittamista tai sovelluksen ajamista. Käsittele aina poikkeuksia kuuntelijoissasi:

```java title="Virheiden käsittelyn esimerkki"
@Override
public void onWillRun(App app) {
    try {
        riskyInitialization();
    } catch (Exception e) {
        logger.error("Alustaminen epäonnistui", e);
    }
}
```
