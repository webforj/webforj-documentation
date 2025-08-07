---
title: Error Handling
sidebar_position: 25
_i18n_hash: a758848bf429e84f33f8b7ba8a4f7277
---
Virheiden käsittely on olennainen osa vankkojen verkkosovellusten kehittämistä. webforJ:ssä virheiden käsittely on suunniteltu joustavaksi ja mukautettavaksi, jolloin kehittäjät voivat käsitellä poikkeuksia parhaiten sovelluksensa tarpeiden mukaan.

## Yleiskatsaus {#overview}

webforJ:ssä virheiden käsittely perustuu `ErrorHandler`-rajapintaan. Tämä rajapinta mahdollistaa kehittäjille määrittää, miten heidän sovelluksensa tulisi reagoida poikkeusten sattuessa suorituksen aikana. Oletuksena webforJ tarjoaa `GlobalErrorHandler`:in, joka käsittelee kaikki poikkeukset yleisellä tavalla. Kehittäjät voivat kuitenkin luoda mukautettuja virheenkäsittelijöitä tiettyjä poikkeuksia varten tarjotakseen räätälöidympiä vasteita.

## Virheenkäsittelijöiden löytäminen ja käyttö {#discovering-and-using-error-handlers}

webforJ käyttää Java:n palveluntarjoajaliittymää (SPI) virheenkäsittelijöiden löytämiseen ja lataamiseen.

### Löytämisprosessi {#discovery-process}

1. **Palvelun rekisteröinti**: Virheenkäsittelijät rekisteröidään `META-INF/services` -mekanismin kautta.
2. **Palvelun lataaminen**: Sovelluksen käynnistyessä webforJ lataa kaikki luokat, jotka on lueteltu tiedostossa `META-INF/services/com.webforj.error.ErrorHandler`.
3. **Virheiden käsittely**: Kun poikkeus tapahtuu, webforJ tarkistaa, onko olemassa virheenkäsittelijää kyseiselle poikkeukselle.

### Käsittelijän valinta {#handler-selection}

- Jos poikkeukselle on olemassa erityinen käsittelijä, sitä käytetään.
- Jos erityistä käsittelijää ei löydy, mutta mukautettu globaali virheenkäsittelijä `WebforjGlobalErrorHandler` on määritelty, sitä käytetään.
- Jos kumpaakaan ei löydy, käytetään oletuksena `GlobalErrorHandler`:ia.

## `ErrorHandler`-rajapinta {#the-errorhandler-interface}

`ErrorHandler`-rajapinta on suunniteltu käsittelemään virheitä, jotka ilmenevät webforJ-sovelluksen suorituksen aikana. Sovellusten, jotka haluavat hallita erityisiä poikkeuksia, tulee toteuttaa tämä rajapinta.

### Metodit {#methods}

- **`onError(Throwable throwable, boolean debug)`**: Kutsutaan, kun virhe tapahtuu. Tässä metodissa tulisi olla logiikka poikkeuksen käsittelemiseksi.
- **`showErrorPage(String title, String content)`**: Oletusmetodi, joka näyttää virhesivun annetuilla otsikolla ja sisällöllä.

### Nimeämiskäytäntö {#naming-convention}

Toteuttavan luokan on oltava nimetty sen käsitteleman poikkeuksen mukaan, ja sen loppuliitteenä on `ErrorHandler`. Esimerkiksi, jos halutaan käsitellä `NullPointerException`:ia, luokan tulee olla nimeltään `NullPointerExceptionErrorHandler`.

### Rekisteröinti {#registration}

Mukautettu virheenkäsittelijä on rekisteröitävä tiedostoon `META-INF/services/com.webforj.error.ErrorHandler`, jotta webforJ voi löytää ja hyödyntää sitä.

## Mukautetun virheenkäsittelijän toteuttaminen {#implementing-a-custom-error-handler}

Seuraavat vaiheet kuvaavat mukautetun virheenkäsittelijän toteuttamista tiettyä poikkeusta varten:

### Vaihe 1: Luo virheenkäsittelijäluokka {#step-1-create-the-error-handler-class}

Luo uusi luokka, joka toteuttaa `ErrorHandler`-rajapinnan ja on nimetty sen käsittelemän poikkeuksen mukaan.

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Mukautettu käsittelylogiikka NullPointerException:lle
    String title = "Null Pointer Exception";
    String content = "Nolla-arvoa kohdattiin, kun objekti oli vaadittu.";

    showErrorPage(title, content);
  }
}
```

:::info `showErrorPage()`-metodi
`showErrorPage`-metodi on apumetodi, joka hyödyntää webforJ API:a lähettääkseen annetun HTML-sisällön ja sivun otsikon selaimelle, näyttäen virhesivun. Kun poikkeus tapahtuu ja sovellus ei kykene palautamaan tilaa, webforJ-komponenttien käyttäminen mukautetun virhesivun rakentamiseen ei ole mahdollista. Kuitenkin `Page`-API on edelleen käytettävissä, mikä mahdollistaa kehittäjän ohjata tai näyttää virhesivun viimeisenä keinona.
:::

### Vaihe 2: Rekisteröi virheenkäsittelijä {#step-2-register-the-error-handler}

Luo tiedosto nimeltä `com.webforj.error.ErrorHandler` sovelluksesi `META-INF/services`-hakemistoon. Lisää tämän tiedoston sisälle mukautetun virheenkäsittelijäluokan täysi nimi.

**Tiedosto**: `META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

Nyt, aina kun `NullPointerException` heitetään, webforJ valitsee rekisteröidyn käsittelijäsi ja suorittaa sen logiikan virheen käsittelemiseksi.

## `AutoService`:n käyttö rekisteröinnin helpottamiseksi {#using-autoservice-to-simplify-registration}

On helppoa, että kehittäjät unohtavat päivittää tai määrittää palvelu-deskriptorit oikein. Käyttämällä Googlen `AutoService`:a, voit automatisoida `META-INF/services/com.webforj.error.ErrorHandler`-tiedoston luomisen. Sinun tarvitsee vain merkitä virheenkäsittelijä `AutoService`-annotaatiolla. Voit oppia lisää [AutoServicesta täällä](https://github.com/google/auto/blob/main/service/README.md).

```java
@AutoService(ErrorHandler.class)
public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Mukautettu käsittelylogiikka NullPointerException:lle
    String title = "Null Pointer Exception";
    String content = "Nolla-arvoa kohdattiin, kun objekti oli vaadittu.";

    showErrorPage(title, content);
  }
}
```

## `GlobalErrorHandler`-luokka {#the-globalerrorhandler-class}

`GlobalErrorHandler` on oletusvirheenkäsittelijä, jonka webforJ tarjoaa. Se toteuttaa `ErrorHandler`-rajapinnan ja tarjoaa yleistä virheiden käsittelyä.

### Käyttäytyminen {#behavior}

- **Lokitus**: Virheitä lokitetaan sekä palvelimelle että selaimen konsoliin.
- **Virhesivun näyttö**: Riippuen virheenkäsittelytilasta, virhesivu näyttää pinon jäljen tai yleisen virheilmoituksen.

### Mukautetun globaalin virheenkäsittelijän määrittäminen {#defining-a-custom-global-error-handler}

Mukautetun globaalin virheenkäsittelijän määrittämiseksi sinun on luotava uusi virheenkäsittelijä nimeltä `WebforjGlobalErrorHandler`. Seuraavaksi noudata [virheenkäsittelijöiden rekisteröintivaiheita](#step-2-register-the-error-handler) edellä mainitun mukaan. Tässä tapauksessa webforJ etsii ensin mahdolliset mukautetut virheenkäsittelijät poikkeusten hallitsemiseksi. Jos mitään ei löydy, webforJ siirtyy mukautettuun globaaliin virheenkäsittelijään.

:::info
Jos useita `WebforjGlobalErrorHandler`:eja on rekisteröity, valitsee webforJ ensimmäisen.
:::
