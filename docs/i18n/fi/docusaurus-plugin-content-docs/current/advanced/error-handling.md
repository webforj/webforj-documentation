---
title: Error Handling
sidebar_position: 5
description: >-
  Register custom ErrorHandler implementations through the Java Service Provider
  Interface to respond to specific exceptions in webforJ apps.
_i18n_hash: 55731ba6ae9454377d363fa461c817bc
---
Virheiden käsittely on keskeinen osa luotettavien verkkosovellusten kehittämistä. webforJ:ssä virheiden käsittely on suunniteltu joustavaksi ja mukautettavaksi, mikä mahdollistaa kehittäjien käsitellä poikkeuksia tavalla, joka parhaiten vastaa sovelluksen tarpeita.

## Yleiskatsaus {#overview}

webforJ:ssä virheiden käsittely perustuu `ErrorHandler`-rajapintaan. Tämä rajapinta antaa kehittäjille mahdollisuuden määrittää, miten heidän sovelluksensa reagoi, kun poikkeuksia esiintyy suorituksen aikana. Oletuksena webforJ tarjoaa `GlobalErrorHandler`-komponentin, joka käsittelee kaikki poikkeukset yleisellä tavalla. Kehittäjät voivat kuitenkin luoda mukautettuja virheenkäsittelykappaleita erityisiä poikkeuksia varten tarjotakseen räätälöidympiä vastauksia.

## Virheenkäsittelykappaleiden löytäminen ja käyttäminen {#discovering-and-using-error-handlers}

webforJ hyödyntää Javassa olevaa Service Provider Interface (SPI) -mekanismia virheenkäsittelykappaleiden löytämisessä ja lataamisessa.

### Löytämisprosessi {#discovery-process}

1. **Palvelun rekisteröinti**: Virheenkäsittelykappaleet rekisteröidään `META-INF/services`-mekanismin kautta.
2. **Palvelun lataaminen**: Sovelluksen käynnistämisen yhteydessä webforJ lataa kaikki luokat, jotka on listattu `META-INF/services/com.webforj.error.ErrorHandler`.
3. **Virheiden käsittely**: Kun poikkeus esiintyy, webforJ tarkistaa, onko olemassa virheenkäsittelykappaletta tuolle erityiselle poikkeukselle.

### Käsittelijän valinta {#handler-selection}

- Jos poikkeukselle on olemassa erityinen käsittelijä, sitä käytetään.
- Jos erityistä käsittelijää ei löydy, mutta mukautettu ylätason virheenkäsittelykappale `WebforjGlobalErrorHandler` on määritelty, sitä käytetään.
- Jos kumpikaan ei löydy, käytetään oletus `GlobalErrorHandler`-kappaletta.

## `ErrorHandler`-rajapinta {#the-errorhandler-interface}

`ErrorHandler`-rajapinta on suunniteltu käsittelemään virheitä, jotka esiintyvät webforJ-sovelluksen suorituksen aikana. Sovellusten, jotka haluavat hallita erityisiä poikkeuksia, tulee toteuttaa tämä rajapinta.

### Menetelmät {#methods}

- **`onError(Throwable throwable, boolean debug)`**: Kutsutaan, kun virhe esiintyy. Tämän menetelmän tulisi sisältää logiikka poikkeuksen käsittelemiseksi.
- **`showErrorPage(String title, String content)`**: Oletusmenetelmä, joka näyttää virhesivun annetuilla nimellä ja sisällöllä.

### Nimikkeistön malli {#naming-convention}

Toteuttavan luokan on oltava nimetty käsiteltävän poikkeuksen mukaan, lisäyksellä `ErrorHandler`. Esimerkiksi `NullPointerException`-poikkeuksen käsittelemiseksi luokan tulisi olla nimeltään `NullPointerExceptionErrorHandler`.

### Rekisteröinti {#registration}

Mukautettu virheenkäsittelykappale on rekisteröitävä `META-INF/services/com.webforj.error.ErrorHandler`-tiedostoon, jotta webforJ voi löytää ja käyttää sitä.

## Mukautetun virheenkäsittelykappaleen toteuttaminen {#implementing-a-custom-error-handler}

Seuraavat vaiheet kuvaavat mukautetun virheenkäsittelykappaleen toteuttamista tiettyä poikkeusta varten:

### Vaihe 1: Luo virheenkäsittelyluokka {#step-1-create-the-error-handler-class}

Luo uusi luokka, joka toteuttaa `ErrorHandler`-rajapinnan ja on nimetty käsiteltävän poikkeuksen mukaan.

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Mukautettu käsittelylogiikka NullPointerExceptionille
    String title = "Null Pointer Exception";
    String content = "Nolla-arvo kohtasi paikan, jossa objekti on vaadittu.";

    showErrorPage(title, content);
  }
}
```

:::info `showErrorPage()`-menetelmä
`showErrorPage`-menetelmä on hyödyllinen menetelmä, joka käyttää webforJ API:a lähettääkseen annetut HTML-sisällöt ja sivun otsikon selaimelle, näyttäen virhesivun. Kun poikkeus esiintyy eikä sovellus kykene palautumaan, webforJ-komponentteja ei voi käyttää mukautetun virhesivun rakentamiseen. Kuitenkin `Page`-API on edelleen käytettävissä, mikä mahdollistaa kehittäjän ohjata tai näyttää virhesivun viimeisenä yrityksenä.
:::

### Vaihe 2: Rekisteröi virheenkäsittelykappale {#step-2-register-the-error-handler}

Luo tiedosto nimeltä `com.webforj.error.ErrorHandler` sovelluksesi `META-INF/services`-hakemistoon. Lisää tämän tiedoston sisään virheenkäsittelyluokkasi täysimittainen nimi.

**Tiedosto**: `META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

Nyt, aina kun `NullPointerException` esiintyy, webforJ valitsee rekisteröidyn käsittelijäsi ja suorittaa sen logiikan virheen käsittelemiseksi.

## `AutoService`-käytön yksinkertaistaminen rekisteröinnissä {#using-autoservice-to-simplify-registration}

On helppoa, että kehittäjät unohtavat päivittää tai määrittää palvelun kuvastot oikein. Käyttämällä Googlen `AutoService`-työkalua voit automatisoida `META-INF/services/com.webforj.error.ErrorHandler`-tiedoston luomisen. Tarvitset vain merkitä virheenkäsittelykappale `AutoService`-annotaatiolla. Voit lukea lisää [AutoServicestä täällä](https://github.com/google/auto/blob/main/service/README.md).

```java
@AutoService(ErrorHandler.class)
public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Mukautettu käsittelylogiikka NullPointerExceptionille
    String title = "Null Pointer Exception";
    String content = "Nolla-arvo kohtasi paikan, jossa objekti on vaadittu.";

    showErrorPage(title, content);
  }
}
```

## `GlobalErrorHandler`-luokka {#the-globalerrorhandler-class}

`GlobalErrorHandler` on oletusvirheenkäsittelykappale, jonka webforJ tarjoaa. Se toteuttaa `ErrorHandler`-rajapinnan ja tarjoaa yleistä virheenkäsittelyä.

### Käyttäytyminen {#behavior}

- **Lokitus**: Virheet tallennetaan sekä palvelimelle että selaimen konsoliin.
- **Virhesivun näyttäminen**: Virhesivulla näytetään pinojäljet tai yleinen virhesanoma riippuen virhetilan.

### Mukautetun ylätason virheenkäsittelykappaleen määrittäminen {#defining-a-custom-global-error-handler}

Mukautetun ylätason virheenkäsittelykappaleen määrittämiseksi sinun on luotava uusi virheenkäsittelykappale nimeltä `WebforjGlobalErrorHandler`. Seuraavaksi seuraa [virheenkäsittelykappaleiden rekisteröinnin vaiheita](#step-2-register-the-error-handler), kuten aiemmin on selostettu. Tässä tapauksessa webforJ etsii ensin mahdolliset mukautetut virheenkäsittelykappaleet hallitsemaan poikkeuksia. Jos niitä ei löydy, webforJ siirtyy mukautettuun ylätason virheenkäsittelykappaleeseen.

:::info
Jos useita `WebforjGlobalErrorHandler`-kappaleita on rekisteröity, webforJ valitsee ensimmäisen.
:::
