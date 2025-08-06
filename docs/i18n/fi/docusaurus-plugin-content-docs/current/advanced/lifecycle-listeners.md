---
sidebar_position: 10
title: Lifecycle Listeners
sidebar_class_name: new-content
_i18n_hash: 475cc2842226c605bbe7f2ee931955dd
---
<!-- vale off -->
# Elinkku Kuuntelijat <DocChip chip='since' label='25.02' />
<!-- vale on -->

`AppLifecycleListener` -rajapinta mahdollistaa ulkoisen koodin havainnoida ja reagoida sovelluksen elinkaaritapahtumiin. Tämän rajapinnan toteuttamalla voit suorittaa koodia tietyissä vaiheissa sovelluksen käynnistyksen ja sulkemisen aikana ilman, että sinun tarvitsee muuttaa `App`-luokkaa.

Elinkaari kuuntelijat havaitaan ja ladataan automaattisesti ajon aikana palveluntarjoajan määritystiedostojen kautta. Jokainen sovellusesimerkki saa oman joukon kuuntelijan instansseja, jolloin eri sovellusten välillä säilyy eristyneisyys samassa ympäristössä.

## Milloin käyttää elinkaarikuuntelijoita {#when-to-use-lifecycle-listeners}

Käytä elinkaarikuuntelijoita, kun sinun tarvitsee:
- Alustaa resursseja tai palveluja ennen sovelluksen suorittamista
- Siivota resursseja, kun sovellus päättyy  
- Lisätä leikkaavia huolenaiheita ilman, että muutat `App`-luokkaa
- Rakentaa laajennusarkkitehtuureja

## `AppLifecycleListener` -rajapinta {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
    default void onWillRun(App app) {}
    default void onDidRun(App app) {}
    default void onWillTerminate(App app) {}
    default void onDidTerminate(App app) {}
}
```

:::info Sovelluksen eristyneisyys
Jokainen sovellusesimerkki saa oman joukon kuuntelijan instansseja:
- Kuuntelijat ovat eristyneitä eri sovellusten välillä
- Kuuntelijoiden staattisia kenttiä ei jaeta sovellusten välillä
- Kuuntelijan instanssit luodaan, kun sovellus käynnistyy ja tuhotaan, kun se päättyy

Jos sinun tarvitsee jakaa tietoja sovellusten välillä, käytä ulkoisia tallennusmekanismeja, kuten tietokantoja tai jaettuja palveluja.
:::

### Elinkaaritapahtumat {#lifecycle-events}

| Tapahtuma | Milloin kutsutaan | Yleisimmät käyttötarkoitukset |
|-----------|-------------------|-------------------------------|
| `onWillRun` | Ennen `app.run()` -suorittamista | Resurssien alustaminen, palveluiden konfigurointi |
| `onDidRun` | Kun `app.run()` on suoritettu onnistuneesti | Taustatehtävien käynnistäminen, onnistuneen käynnistyksen lokitus |
| `onWillTerminate` | Ennen sovelluksen päättymistä | Tilan tallentaminen, sulkemiseen valmistautuminen |
| `onDidTerminate` | Sovelluksen päättymisen jälkeen | Resurssien siivous, lopullinen lokitus |

## Elinkaarikuuntelijan luominen {#creating-a-lifecycle-listener}

### Perus toteutus {#basic-implementation}

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

Luo palveluntarjoajan määritystiedosto:

**Tiedosto**: `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener`

```
com.example.listeners.StartupListener
```

:::tip Käytä AutoService
On helppo unohtaa päivittää palvelukuvaajia. Käytä Googlen [AutoService](https://github.com/google/auto/blob/main/service/README.md) -työkalua, joka automaattisesti tuottaa palvelutiedoston:

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
    // Toteutus
}
```
:::

## Suorituksen järjestyksen säätäminen {#controlling-execution-order}

Kun useita kuuntelijoita on rekisteröity, voit hallita niiden suoritusjärjestystä käyttämällä `@AppListenerPriority` -annotaatiota. Tämä on erityisen tärkeää, kun kuuntelijoilla on riippuvuuksia toisiinsa tai kun tietty alustaminen on pakollista ennen muita.

Prioriteettiarvot toimivat nousevassa järjestyksessä - **alemmat numerot suoritetaan ensin**. Oletusprioriteetti on 10, joten kuuntelijat, joilla ei ole ilmaisia prioriteettikuvastimia, suoritetaan niiden jälkeen, joilla on alhaisemmat prioriteettiarvot.

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // Suorittaa ensin - kriittinen turvallisuusasetelma
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

### Suoritusvirta App-haakujen kanssa {#execution-flow-with-app-hooks}

Useiden kuuntelijoiden suoritusjärjestyksen hallinnan lisäksi on tärkeää ymmärtää, kuinka kuuntelijat vuorovaikuttavat `App`-luokan omien elinkaarin haakujen kanssa. Jokaiselle elinkaaritapahtumalle kehys seuraa tiettyä suoritusjärjestystä, joka määrittää, milloin kuuntelijasi suoritetaan suhteessa sovelluksen sisäänrakennettuihin haakuihin.

Alla oleva kaavio havainnollistaa tätä suoritusvirtaa, näyttäen tarkat ajat, jolloin `AppLifecycleListener` -menetelmiä kutsutaan suhteessa vastaaviin `App`-haakuihin: 

<div align="center">

![AppLifecycleListener -kuuntelijat VS `App` haakut](/img/lifecycle-listeners.svg)

</div>


## Virheiden käsittely {#error-handling}

Kuuntelijoiden aiheuttamat poikkeukset kirjataan, mutta ne eivät estä muiden kuuntelijoiden suoritusta tai sovelluksen toimintaa. Käsittele aina poikkeuksia kuuntelijoissasi:

```java title="Virheiden käsittely esimerkki"
@Override
public void onWillRun(App app) {
    try {
        riskyInitialization();
    } catch (Exception e) {
        logger.error("Alustaminen epäonnistui", e);
    }
}
```
