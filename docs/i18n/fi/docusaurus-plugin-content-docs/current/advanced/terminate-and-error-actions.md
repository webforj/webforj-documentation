---
title: Terminate and Error Actions
sidebar_position: 40
_i18n_hash: d0f7532dd9019f6cd611255055c76754
---
<!-- vale off -->
# Päättämis- ja virhetoiminnot <DocChip chip='since' label='23.06' />
<!-- vale on -->

Kun kehität sovelluksia webforJ:llä, on tärkeää määrittää, miten sovelluksesi käyttäytyy, kun se päättyy tai kohtaa virheen. Kehys tarjoaa mekanismeja näiden käyttäytymisten mukauttamiseen `terminate`- ja `error`-toimintojen kautta.

## Yleiskuvaus {#overview}

`App`-luokka sallii sinun määritellä toimintoja, jotka suoritetaan, kun sovellus päättyy normaalisti tai kun se kohtaa virheen. Nämä toiminnot ovat `AppCloseAction`-rajapinnan instansseja ja ne voidaan määrittää käyttämällä:

- `setTerminateAction(AppCloseAction action)`: Määrittää toiminnon, joka suoritetaan normaalin päättymisen yhteydessä.
- `setErrorAction(AppCloseAction action)`: Määrittää toiminnon, joka suoritetaan, kun virhe tapahtuu.

Saatavilla olevat `AppCloseAction`-implementaatiot ovat:

- `DefaultAction`: Tyhjentää selaimen ja näyttää paikallistettua viestiä, joka kehottaa käyttäjää lataamaan sovelluksen uudelleen.
- `NoneAction`: Ei suorita mitään toimintoa, käytännössä nollaa aiemmin asetetut toiminnot.
- `MessageAction`: Myöntää mukautetun linkkiviestin.
- `RedirectAction`: Ohjaa käyttäjän määritettyyn URL-osoitteeseen.

:::info Erojen määrittäminen päättymistoimintojen ja virhetoimintojen välillä webforJ:ssä
webforJ ei käsittele päättymistä heitetyn tai käsittelemättömän poikkeuksen vuoksi virhetoimintona, vaan päättymistoimintona, koska sovellus sulkeutuu normaalisti. Virhetoiminto tapahtuu, kun sovellus saa päättymissignaalin ulkoisesta virheestä, kuten kun selain ei pysty muodostamaan yhteyttä sovellusta ajavaan palvelimeen.
:::

## Oletuskäyttäytyminen {#default-behavior}

webforJ-version `24.11` ja aiemmissa versioissa sovellus käyttää oletuksena `DefaultAction`-toimintoa sekä päättymis- että virhetilanteissa. Tämä tarkoittaa, että kun sovellus päättyy tai kohtaa virheen, selain näyttää viestin, joka kehottaa käyttäjää lataamaan sovelluksen uudelleen.

Versiosta `24.12` alkaen webforJ käyttää oletuksena `NoneAction`-toimintoa sekä päättymis- että virhetilanteissa. Tämä muutos tarkoittaa, että mitään toimintoa ei suoriteta, kun sovellus päättyy tai virhe tapahtuu, jolloin webforJ voi delegoida virheiden käsittelyn asianmukaiselle `ErrorHandler`:lle, jos sellainen on määritetty, tai turvautua sen yleisiin virheiden käsittelymekanismeihin. Käyttämällä `NoneAction`:ia sovellus vältää häiritsemästä oletuksena olevaa virheiden käsittelyprosessia, mahdollistaen mukautettujen virheiden käsittelijöiden määrittämisen tai tukien webforJ:n sisäänrakennettua virheiden hallintaa.

## Toimintojen mukauttaminen {#customizing-actions}

Muutoksen tekemiseksi oletuskäyttäytymiselle käytä `setTerminateAction()` ja `setErrorAction()` menetelmiä `App`-alaluokassasi.

### Mukautetun viestin toiminnon määrittäminen {#setting-a-custom-message-action}

Jos haluat näyttää mukautetun viestin normaalin päättymisen yhteydessä:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Määritetään mukautettu viestitoiminto
    setTerminateAction(new MessageAction(
        "Kiitos, että käytät sovellustamme! Klikkaa lataaksesi uudelleen."
    ));
  }
}
```

### Ohjaustoiminnon määrittäminen {#setting-a-redirect-action}

Ohjataksesi käyttäjän tiettyyn URL-osoitteeseen normaalin päättymisen yhteydessä:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Määritetään ohjaustoiminto virheen yhteydessä
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

Kun kutsut `terminate()`, `setTerminateAction()`-metodilla määritelty toiminto suoritetaan.

## Päättymiseen liittyvät koukut {#hooks-for-termination}

`App`-luokka tarjoaa koukku-metodeja toimintojen suorittamiseen ennen ja jälkeen päättymisen:

- `onWillTerminate()`: Kutsutaan ennen päättymistä.
- `onDidTerminate()`: Kutsutaan päättymisen jälkeen.

```java
public class MyApp extends App {

  @Override
  protected void onWillTerminate() {
    // Suorita siivoustöitä
  }

  @Override
  protected void onDidTerminate() {
    // Toimintoja päättymisen jälkeen
  }
}
```

:::tip Ulkoiset elinkaarikuuntelijat
Monimutkaisempaa elinkaaren hallintaa varten harkitse `AppLifecycleListener`:n käyttöä päättymistapahtumien käsittelemiseksi ulkoisista komponenteista ilman `App`-luokan muuttamista. Tämä on erityisen hyödyllistä liitännäisrakenteissa tai kun useat komponentit tarvitsevat reagoida sovelluksen päättymiseen. Lisätietoja [Elinkaarikuuntelijoista](lifecycle-listeners.md).
:::

### Mukautettu päättymis-sivu {#custom-termination-page}

Joissakin tapauksissa saatat haluta näyttää mukautetun päättymissivun, kun sovelluksesi päättyy, tarjoten käyttäjille henkilökohtaisen viestin tai lisäresursseja. Tämä voidaan saavuttaa ohi­dam­lemalla `onDidTerminate()`-metodia `App`-alaluokassasi ja injectoimalla mukautettua HTML:ää sivulle.

Tässä on esimerkki mukautetun päättymissivun luomisesta:

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
        <h1>Kiitos, että käytät webforJ:ää</h1>
        <p>Lisätietoja varten, käy <a href="https://webforj.com">webforj.com</a></p>
    </div>
    """;

    Page.getCurrent().executeJsVoidAsync(
        String.format("document.body.innerHTML = `%s`", html)
    );
  }
}
```
