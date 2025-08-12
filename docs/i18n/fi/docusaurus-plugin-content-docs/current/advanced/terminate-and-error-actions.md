---
title: Terminate and Error Actions
sidebar_position: 40
_i18n_hash: 1a250a51020b32c8b3471ae75ea8f750
---
<!-- vale off -->
# Päättävä ja virhetoiminnot <DocChip chip='since' label='23.06' />
<!-- vale on -->

Kun kehität sovelluksia webforJ:llä, on tärkeää määritellä, kuinka sovelluksesi käyttäytyy, kun se päättyy tai kohtaa virheen. Kehys tarjoaa mekanismeja näiden käyttäytymisten mukauttamiseen `terminate` ja `error` -toimintojen avulla.

## Yhteenveto {#overview}

`App`-luokka mahdollistaa toimintojen määrittämisen, jotka suoritetaan, kun sovellus päättyy normaalisti tai kun se kohtaa virheen. Nämä toiminnot ovat `AppCloseAction`-rajapinnan instansseja ja ne voidaan asettaa seuraavasti:

- `setTerminateAction(AppCloseAction action)`: Asettaa toiminnon, joka suoritetaan normaalin päättymisen yhteydessä.
- `setErrorAction(AppCloseAction action)`: Asettaa toiminnon, joka suoritetaan virheen tapahtuessa.

Saatavilla olevat `AppCloseAction`:n toteutukset sisältävät:

- `DefaultAction`: Tyhjentää selaimen ja näyttää lokalisoidun viestin, joka kehottaa käyttäjää lataamaan sovelluksen uudelleen.
- `NoneAction`: Ei suorita mitään toimintoa, mikä käytännössä palauttaa aiemmin asetetun toiminnon.
- `MessageAction`: Näyttää mukautetun linkkiviestin.
- `RedirectAction`: Ohjaa käyttäjän tiettyyn URL-osoitteeseen.

:::info Erojen määrittäminen päättymistoimintojen ja virhetoimintojen välillä webforJ:ssä
webforJ ei käsittele päättymistä heitetyn tai käsittelemättömän poikkeuksen vuoksi virhetoimintona, vaan pikemminkin päättymistoimintona, koska sovellus sammuttaa normaalisti. Virhetoiminto tapahtuu, kun sovellus vastaanottaa päättymissignaalin ulkoisen virheen vuoksi, kuten kun selain ei voi yhdistää sovellusta ajavaan palvelimeen.
:::

## Oletuskäyttäytyminen {#default-behavior}

webforJ versiossa `24.11` ja aikaisemmissa sovellus käyttää oletuksena `DefaultAction`:ia sekä päättymis- että virhetapahtumissa. Tämä tarkoittaa, että kun sovellus päättyy tai kohtaa virheen, selain näyttää viestin, joka kehottaa käyttäjää lataamaan sovelluksen uudelleen.

Versiosta `24.12` alkaen webforJ käyttää oletuksena `NoneAction`:ia sekä päättymis- että virhetapahtumissa. Tämä muutos tarkoittaa, että mitään toimintoa ei suoriteta, kun sovellus päättyy tai virhe tapahtuu, jolloin webforJ voi delegoida virheiden käsittelyn sopivalle `ErrorHandler`:lle, jos sellainen on määritelty, tai turvautua sen yleisiin virheenkäsittelymekanismeihin. Käyttämällä `NoneAction`:ia sovellus välttää oletuksena olevan virheenkäsittelyvirtauksen keskeyttämisen, mahdollistaen mukautettujen virheenkäsittelijöiden määrittämisen tai webforJ:n sisäänrakennetun virhehallinnan hyödyntämisen.

## Toimintojen mukauttaminen {#customizing-actions}

Oletuskäyttäytymisen muuttamiseksi käytä `setTerminateAction()` ja `setErrorAction()` -metodeja `App`-aliluokassasi.

### Mukautetun viestitoiminnon asettaminen {#setting-a-custom-message-action}

Jos haluat näyttää mukautetun viestin normaalin päättymisen yhteydessä:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Aseta mukautettu viestitoiminto
    setTerminateAction(new MessageAction(
        "Kiitos, että käytit sovellustamme!. Napsauta lataaksesi uudelleen"
    ));
  }
}
```

### Ohjaustoiminnon asettaminen {#setting-a-redirect-action}

Ohjataksesi käyttäjän tiettyyn URL-osoitteeseen normaalin päättymisen yhteydessä:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Aseta ohjaustoiminto virheen yhteydessä
    setTerminateAction(new RedirectAction(
        "https://example.com/error"
    ));
  }
}
```

## Sovelluksen päättäminen {#terminating-the-app}

Voit ohjelmallisesti päättää sovelluksesi kutsumalla `terminate()`-metodia:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Päättää sovelluksen tietyissä olosuhteissa
    if (someCondition) {
      terminate();
    }
  }
}
```

Kun `terminate()`-metodia kutsutaan, `setTerminateAction()`-metodilla määritelty toiminto suoritetaan.

## Päättämisen koukut {#hooks-for-termination}

`App`-luokka tarjoaa koukkuja toimintojen suorittamiseksi ennen ja jälkeen päättymisen:

- `onWillTerminate()`: Kutsutaan ennen päättymistä.
- `onDidTerminate()`: Kutsutaan päättymisen jälkeen.

```java
public class MyApp extends App {

  @Override
  protected void onWillTerminate() {
    // Suorita siivoustehtävät
  }

  @Override
  protected void onDidTerminate() {
    // Toimintoja päättymisen jälkeen
  }
}
```

:::tip Ulkoiset elinkaarikuuntelijat
Edistyksellisemmän elinkaaren hallinnan vuoksi harkitse `AppLifecycleListener`:n käyttöä päättymistapahtumien käsittelemiseksi ulkoisista komponenteista ilman `App`-luokan muokkaamista. Tämä on erityisen hyödyllistä liitännäisarkkitehtuureissa tai kun useat komponentit tarvitsevat reagoida sovelluksen päättymiseen. Lue lisää [Elinkaarikuuntelijoista](lifecycle-listeners.md).
:::

### Mukautettu päättymissivu {#custom-termination-page}

Joissakin tapauksissa saatat haluta näyttää mukautetun päättymissivun, kun sovelluksesi päättyy, tarjoten käyttäjille henkilökohtaisen viestin tai lisäresursseja. Tämä voidaan saavuttaa ylikirjoittamalla `onDidTerminate()`-metodi `App`-aliluokassasi ja injektoimalla mukautettua HTML:ää sivulle.

Tässä esimerkki mukautetun päättymissivun luomiseksi:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    setTerminateAction(new NoneAction());
    terminate();
  }

  @Override
  protected void onDidTerminate() {
    String html = """
    <div style="display: flex; justify-content: center; align-items: center; height: 100vh; flex-direction: column;">
        <h1>Kiitos, että käytit webforJ:ä</h1>
        <p>Lisätietoja varten, käy osoitteessa <a href="https://webforj.com">webforj.com</a></p>
    </div>
    """;

    Page.getCurrent().executeJsVoidAsync(
        String.format("document.body.innerHTML = `%s`", html)
    );
  }
}
```
