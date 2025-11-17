---
title: Virheiden käsittely
sidebar_position: 25
_i18n_hash: 15106dd9fa7ccf0d4f722ca675f0d362
---
Virheiden käsittely on tärkeä osa kestävien verkkosovellusten kehittämistä. webforJ:ssä virheiden käsittely on suunniteltu joustavaksi ja mukautettavaksi, jolloin kehittäjät voivat käsitellä poikkeuksia tavalla, joka parhaiten sopii heidän sovelluksensa tarpeisiin.

## Yleiskatsaus {#overview}

webforJ:ssä virheiden käsittely keskittyy `ErrorHandler` -rajapintaan. Tämä rajapinta mahdollistaa kehittäjien määritellä, miten heidän sovelluksensa reagoi, kun poikkeuksia tapahtuu suorituksen aikana. Oletuksena webforJ tarjoaa `GlobalErrorHandler`in, joka käsittelee kaikki poikkeukset yleisellä tavalla. Kehittäjät voivat kuitenkin luoda mukautettuja virheiden käsittelijöitä tiettyjä poikkeuksia varten tarjotakseen tarkemmin kohdennettuja vastauksia.

## Virheiden käsittelijöiden löytö ja käyttö {#discovering-and-using-error-handlers}

webforJ käyttää Java:n Service Provider Interface (SPI) -mekanismia virheiden käsittelijöiden löytämiseen ja lataamiseen.

### Löytöprosessi {#discovery-process}

1. **Palvelun rekisteröinti**: Virheiden käsittelijät rekisteröidään `META-INF/services` -mekanismin avulla.
2. **Palvelun lataaminen**: Sovelluksen käynnistyessä webforJ lataa kaikki luokat, jotka on lueteltu tiedostossa `META-INF/services/com.webforj.error.ErrorHandler`.
3. **Virheiden käsittely**: Kun poikkeus tapahtuu, webforJ tarkistaa, onko kyseiselle poikkeukselle olemassa virheiden käsittelijää.

### Käsittelijän valinta {#handler-selection}

- Jos poikkeukselle on olemassa erityinen käsittelijä, sitä käytetään.
- Jos erityistä käsittelijää ei löydy, mutta mukautettu globaali virheiden käsittelijä `WebforjGlobalErrorHandler` on määritelty, sitä käytetään.
- Jos kumpaakaan ei löydy, oletusarvoista `GlobalErrorHandler` -käsittelijää käytetään.

## `ErrorHandler` -rajapinta {#the-errorhandler-interface}

`ErrorHandler` -rajapinta on suunniteltu käsittelemään virheitä, jotka tapahtuvat webforJ-sovelluksen suorituksen aikana. Hakemukset, jotka haluavat hallita tiettyjä poikkeuksia, tulisi toteuttaa tämä rajapinta.

### Metodit {#methods}

- **`onError(Throwable throwable, boolean debug)`**: Kutsutaan, kun virhe tapahtuu. Tämän metodin tulisi sisältää logiikka virheen käsittelemiseksi.
- **`showErrorPage(String title, String content)`**: Oletusmetodi, joka näyttää virhesivun annetulla otsikolla ja sisällöllä.

### Nimeämiskäytäntö {#naming-convention}

Toteuttavan luokan on oltava nimetty sen käsittelemän poikkeuksen mukaan, ja sen lopussa on oltava `ErrorHandler`. Esimerkiksi, jotta `NullPointerException` -poikkeus voitaisiin käsitellä, luokan on oltava nimeltään `NullPointerExceptionErrorHandler`.

### Rekisteröinti {#registration}

Mukautettu virheiden käsittelijä on rekisteröitävä tiedostoon `META-INF/services/com.webforj.error.ErrorHandler`, jotta webforJ voi löytää ja käyttää sitä.

## Mukautetun virheiden käsittelijän toteuttaminen {#implementing-a-custom-error-handler}

Seuraavat vaiheet kuvaavat mukautetun virheiden käsittelijän toteutusta tiettyä poikkeusta varten:

### Vaihe 1: Luo virheiden käsittelijäluokka {#step-1-create-the-error-handler-class}

Luo uusi luokka, joka toteuttaa `ErrorHandler` -rajapinnan ja on nimetty sen käsittelemän poikkeuksen mukaan.

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Mukautettu käsittelylogiikka NullPointerExceptionille
    String title = "Null Pointer Exception";
    String content = "Virheellisesti käytettiin null-arvoa, missä objekti tarvitaan.";

    showErrorPage(title, content);
  }
}
```

:::info `showErrorPage()` -metodi
`showErrorPage` -metodi on hyödyllinen metodi, joka käyttää webforJ:n API:a lähettääkseen annetun HTML-sisällön ja sivun otsikon selaimelle, jolloin virhesivu näytetään. Kun poikkeus tapahtuu eikä sovellus kykene palautumaan, webforJ:n komponentteja ei voida enää käyttää mukautetun virhesivun luomiseen. Kuitenkin `Page` API pysyy käytettävissä, mikä mahdollistaa kehittäjän ohjata tai näyttää virhesivun viimeisenä yrityksenä.
:::

### Vaihe 2: Rekisteröi virheiden käsittelijä {#step-2-register-the-error-handler}

Luo tiedosto nimeltä `com.webforj.error.ErrorHandler` sovelluksesi `META-INF/services` -hakemistoon. Lisää tähän tiedostoon virheiden käsittelijäsi täysi nimi.

**Tiedosto**: `META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

Nyt, aina kun `NullPointerException` heitetään, webforJ valitsee rekisteröidyn käsittelijäsi ja suorittaa sen logiikan virheen käsittelemiseksi.

## `AutoService`in käyttäminen rekisteröinnin yksinkertaistamiseksi {#using-autoservice-to-simplify-registration}

Kehittäjät voivat helposti unohtaa päivittää tai määrittää palvelukuvaajia oikein. Käyttämällä Googlen `AutoService`:a voit automatisoida `META-INF/services/com.webforj.error.ErrorHandler` -tiedoston generaation. Sinun tarvitsee vain merkitä virheiden käsittelijä `AutoService` -annotaatiolla. Voit lukea lisää [AutoService:sta täältä](https://github.com/google/auto/blob/main/service/README.md).

```java
@AutoService(ErrorHandler.class)
public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Mukautettu käsittelylogiikka NullPointerExceptionille
    String title = "Null Pointer Exception";
    String content = "Virheellisesti käytettiin null-arvoa, missä objekti tarvitaan.";

    showErrorPage(title, content);
  }
}
```

## `GlobalErrorHandler` -luokka {#the-globalerrorhandler-class}

`GlobalErrorHandler` on oletusvirheiden käsittelijä, jonka webforJ tarjoaa. Se toteuttaa `ErrorHandler` -rajapinnan ja tarjoaa yleistä virheiden käsittelyä.

### Käyttäytyminen {#behavior}

- **Lokitus**: Virheet kirjataan sekä palvelimen että selaimen konsoliin.
- **Virhesivun näyttäminen**: Riippuen virheen jäljittämismoodista, virhesivu näyttää pinojäljen tai yleisen virheilmoituksen.

### Mukautetun globaalin virheiden käsittelijän määrittäminen {#defining-a-custom-global-error-handler}

Mukautetun globaalin virheiden käsittelijän määrittämiseksi sinun on luotava uusi virheiden käsittelijä, joka on nimetty `WebforjGlobalErrorHandler`. Seura sitten [vaiheita virheiden käsittelijöiden rekisteröimiseksi](#step-2-register-the-error-handler) kuten aiemmin on selitetty. Tässä tapauksessa webforJ etsii ensin mukautettuja virheiden käsittelijöitä hoitamaan poikkeuksia. Jos niitä ei löydy, webforJ turvautuu mukautettuun globaaliin virheiden käsittelijään.

:::info
Jos useita `WebforjGlobalErrorHandler` -luokkia on rekisteröity, webforJ valitsee ensimmäisen
:::
