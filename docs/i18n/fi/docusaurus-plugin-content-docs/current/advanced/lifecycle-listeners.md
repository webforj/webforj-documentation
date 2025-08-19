---
sidebar_position: 10
title: Lifecycle Listeners
sidebar_class_name: new-content
_i18n_hash: 8134c6a2d602b0d69733de9770b44afe
---
<!-- vale off -->
# Elinkaari kuuntelijat <DocChip chip='since' label='25.02' />
<!-- vale on -->

`AppLifecycleListener`-rajapinta mahdollistaa ulkoisen koodin havainnoida ja reagoida sovelluksen elinkaaritapahtumiin. Tämän rajapinnan toteuttamalla voit suorittaa koodia tietyissä vaiheissa sovelluksen käynnistämisen ja sulkemisen aikana ilman, että sinun tarvitsee muokata `App`-luokkaa itse.

Elinkaari kuuntelijat havaitaan ja ladataan automaattisesti ajonaikana palveluntarjoajan konfiguraatiotiedostojen avulla. Jokainen sovellusesimerkki saa oman joukon kuuntelijaesimerkkejä, mikä pitää eristyksen erilaisten sovellusten välillä, jotka toimivat samassa ympäristössä.

## Milloin käyttää elinkaari kuuntelijoita {#when-to-use-lifecycle-listeners}

Käytä elinkaari kuuntelijoita, kun sinun tarvitsee:

- Alustaa resursseja tai palveluja ennen sovelluksen käynnistymistä
- Siivota resursseja, kun sovellus päättyy
- Lisätä poikkileikkaavia huolenaiheita ilman, että muokkaat `App`-luokkaa
- Rakentaa liitännäisarkkitehtuureja

## `AppLifecycleListener`-rajapinta {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
    default void onWillCreate(Environment env) {}     // Seitsemästä 25.03
    default void onDidCreate(App app) {}              // Seitsemästä 25.03
    default void onWillRun(App app) {}
    default void onDidRun(App app) {}
    default void onWillTerminate(App app) {}
    default void onDidTerminate(App app) {}
}
```

:::info Sovelluksen eristys
Jokainen sovellusesimerkki saa oman joukon kuuntelijaesimerkkejä:

- Kuuntelijat ovat eristyksissä eri sovellusten kesken
- Kuuntelijassa olevia staattisia kenttiä ei jaeta sovellusten välillä
- Kuuntelijaesimerkit luodaan, kun sovellus käynnistyy, ja tuhotaan, kun se päättyy

Jos sinun tarvitsee jakaa tietoja sovellusten välillä, käytä ulkoisia tallennusmekanismeja, kuten tietokantoja tai jaettuja palveluja.
:::

### Elinkaari tapahtumat {#lifecycle-events}

| Tapahtuma         | Milloin kutsutaan                                    | Yleiset käyttötarkoitukset                          |
| ----------------- | ---------------------------------------------------- | --------------------------------------------------- |
| `onWillCreate`&nbsp;<DocChip chip='since' label='25.03' /> | Ympäristön alustamisen jälkeen, ennen sovelluksen luomista | Muokata konfiguraatiota, yhdistää ulkoisia konfiguraatiolähteitä |
| `onDidCreate`&nbsp;<DocChip chip='since' label='25.03' />  | Sovelluksen instanssin luomisen jälkeen, ennen alustusta | Varhainen sovellustason asettaminen, rekisteröi palveluja |
| `onWillRun`       | Ennen `app.run()` suoritusta                         | Alustaa resursseja, konfiguroi palveluja            |
| `onDidRun`        | Sen jälkeen, kun `app.run()` on onnistuneesti suoritettu | Aloittaa taustatehtäviä, kirjaa onnistunut käynnistys |
| `onWillTerminate` | Ennen sovelluksen päättymistä                        | Tallenna tila, valmistele sulkemista                |
| `onDidTerminate`  | Sen jälkeen, kun sovellus on päättynyt              | Siivoa resursseja, lopullinen lokitus               |

## Elinkaari kuuntelijan luominen {#creating-a-lifecycle-listener}

### Perus toteutus {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;
import com.webforj.Environment;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class StartupListener implements AppLifecycleListener {

    @Override
    public void onWillCreate(Environment env) {
        // Muokkaa konfiguraatiota ennen sovelluksen luomista
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

### Kuuntelijan rekisteröinti {#registering-the-listener}

Luo palveluntarjoajan konfiguraatiotiedosto:

**Tiedosto**: `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener`

```
com.example.listeners.StartupListener
```

:::tip Käyttämällä AutoServicea
On helppo unohtaa päivittää palvelun kuvastot. Käytä Googlen [AutoService](https://github.com/google/auto/blob/main/service/README.md) automaattisesti luomaan palvelutiedosto:

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
    // Toteutus
}
```
:::

## Suoritusjärjestyksen hallinta {#controlling-execution-order}

Kun useita kuuntelijoita on rekisteröity, voit hallita niiden suoritusjärjestystä käyttämällä `@AppListenerPriority`-annotaatiota. Tämä on erityisen tärkeää, kun kuuntelijoilla on riippuvuuksia toisiinsa tai kun tietty alustaminen on tapahtuttava ennen muita.

Prioriteettiarvot toimivat nousevassa järjestyksessä - **matalammat numerot suoritetaan ensin**. Oletusprioriteetti on 10, joten kuuntelijat ilman eksplisiittisiä prioriteettiannotaatioita suoritetaan niiden jälkeen, joilla on matalammat prioriteettiarvot.

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // Suoritetaan ensin - kriittinen turvallisuusasetaminen
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

### Suoritusvirta sovelluksen koukuilla {#execution-flow-with-app-hooks}

Yhdessä hallitsemalla useiden kuuntelijoiden järjestystä on tärkeää ymmärtää, miten kuuntelijat vuorovaikuttavat `App`-luokan omien elinkaarikoukkuiden kanssa. Jokaisen elinkaaritapahtuman osalta kehys seuraa tiettyä suoritusjärjestystä, joka määrittää, milloin kuuntelijat suoritetaan suhteessa sovelluksen sisäisiin koukkuihin.

Alla oleva kaavio havainnollistaa tätä suoritusvirtaa, jossa näytetään tarkka ajoitus, jolloin `AppLifecycleListener`-menetelmiä kutsutaan suhteessa vastaaviin `App`-koukkuihin:

<div align="center">

![AppLifecycleListener kuuntelijat VS `App` koukut](/img/lifecycle-listeners.svg)

</div>


## Virheiden käsittely {#error-handling}

Kuuntelijoiden heittämät poikkeukset tallennetaan lokiin, mutta eivät estä muiden kuuntelijoiden suorittamista tai sovelluksen käynnistämistä. Käsittele aina poikkeuksia kuuntelijoissasi:

```java title="Virheiden käsittelyn esimerkki"
@Override
public void onWillRun(App app) {
    try {
        riskyInitialization();
    } catch (Exception e) {
        logger.error("Alustus epäonnistui", e);
    }
}
```
