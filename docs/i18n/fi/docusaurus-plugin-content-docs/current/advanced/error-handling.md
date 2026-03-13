---
title: Error Handling
sidebar_position: 5
_i18n_hash: 7957d907ae8a5bd9e7b3f7c2fdba2623
---
Virheiden käsittely on olennainen osa luotettavien verkkosovellusten kehittämistä. webforJ:ssa virheiden käsittely on suunniteltu joustavaksi ja mukautettavaksi, mikä mahdollistaa kehittäjien käsitellä poikkeuksia tavalla, joka parhaiten palvelee sovelluksen tarpeita.

## Yleisnäkymä {#overview}

webforJ:ssa virheiden käsittely keskittyy `ErrorHandler` rajapintaan. Tämä rajapinta mahdollistaa kehittäjille määrittää, miten sovelluksen tulisi reagoida, kun poikkeuksia ilmenee suorituksen aikana. Oletuksena webforJ tarjoaa `GlobalErrorHandler`:in, joka käsittelee kaikki poikkeukset yleisellä tavalla. Kehittäjät voivat kuitenkin luoda mukautettuja virheanalyytikoita tietyille poikkeuksille tarjotakseen räätälöityjä vastauksia.

## Virheanalyytikoiden löytäminen ja käyttäminen {#discovering-and-using-error-handlers}

webforJ käyttää Java:n Service Provider Interface (SPI) -mekanismia virheanalyytikoiden löytämiseen ja lataamiseen.

### Löytämisprosessi {#discovery-process}

1. **Palvelun rekisteröinti**: Virheanalyytikot rekisteröidään `META-INF/services` -mekanismin avulla.
2. **Palvelun lataaminen**: Sovelluksen käynnistyessä webforJ lataa kaikki luokat, jotka on lueteltu `META-INF/services/com.webforj.error.ErrorHandler`.
3. **Virheiden käsittely**: Kun poikkeus tapahtuu, webforJ tarkistaa, onko olemassa virheanalyytikkoa kyseiselle poikkeukselle.

### Analyytikon valinta {#handler-selection}

- Jos spesifinen analyytikko kyseiselle poikkeukselle on olemassa, sitä käytetään.
- Jos spesifistä analyytikkoa ei löydy, mutta mukautettu globaali virheanalyytikko `WebforjGlobalErrorHandler` on määritetty, sitä käytetään.
- Jos kumpaakaan ei löydy, käytetään oletus `GlobalErrorHandler` -analyytikkoa.

## `ErrorHandler` rajapinta {#the-errorhandler-interface}

`ErrorHandler` rajapinta on suunniteltu käsittelemään virheitä, jotka tapahtuvat webforJ-sovelluksen suorituksen aikana. Sovellusten, jotka haluavat hallita tiettyjä poikkeuksia, tulisi toteuttaa tämä rajapinta.

### Metodit {#methods}

- **`onError(Throwable throwable, boolean debug)`**: Kutsutaan, kun virhe tapahtuu. Tämän metodin tulisi sisältää logiikka poikkeuksen käsittelyyn.
- **`showErrorPage(String title, String content)`**: Oletusmetodi, joka näyttää virhesivun annetuilla otsikolla ja sisällöllä.

### Nimeämiskäytäntö {#naming-convention}

Toteuttavan luokan tulee olla nimetty virheen mukaan, jota se käsittelee, ja siihen on lisättävä pääte `ErrorHandler`. Esimerkiksi, jos haluat käsitellä `NullPointerException`:ia, luokan tulee olla nimeltään `NullPointerExceptionErrorHandler`.

### Rekisteröinti {#registration}

Mukautettu virheanalyytikko on rekisteröitävä tiedostoon `META-INF/services/com.webforj.error.ErrorHandler`, jotta webforJ voi löytää ja käyttää sitä.

## Mukautetun virheanalyytikon toteuttaminen {#implementing-a-custom-error-handler}

Seuraavat vaiheet kuvaavat mukautetun virheanalyytikon toteutusta tietylle poikkeukselle:

### Vaihe 1: Luo virheanalyytikkoluokka {#step-1-create-the-error-handler-class}

Luo uusi luokka, joka toteuttaa `ErrorHandler` rajapinnan ja on nimetty käsiteltävän poikkeuksen mukaan.

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Mukautettu käsittelylogiikka NullPointerException:lle
    String title = "Null Pointer Exception";
    String content = "Null-arvo kohtasi paikan, jossa objekti on vaadittu.";

    showErrorPage(title, content);
  }
}
```

:::info `showErrorPage()` metodi
`showErrorPage` -metodi on utiliittimetodi, joka käyttää webforJ API:a lähettääkseen annetun HTML-sisällön ja sivun otsikon selaimelle, näyttäen virhesivun. Kun poikkeus tapahtuu, eikä sovellus kykene toipumaan, webforJ-komponenttien käyttäminen mukautetun virhesivun luomiseksi muuttuu mahdottomaksi. Kuitenkin `Page` API:in on edelleen käytettävissä, jolloin kehittäjä voi ohjata tai näyttää virhesivun viimeisenä yrityksenä.
:::

### Vaihe 2: Rekisteröi virheanalyytikko {#step-2-register-the-error-handler}

Luo tiedosto nimeltä `com.webforj.error.ErrorHandler` sovelluksesi `META-INF/services` -hakemistoon. Lisää tämän tiedoston sisään oma virheanalyytikoluokkasi täysin laadittu nimi.

**Tiedosto**: `META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

Nyt, kun `NullPointerException` heitetään, webforJ valitsee rekisteröidyn analyytikkosi ja suorittaa sen logiikan virheen käsittelyyn.

## `AutoService`:n käyttäminen rekisteröinnin yksinkertaistamiseksi {#using-autoservice-to-simplify-registration}

On helppo unohtaa päivittää tai määrittää palvelukohtia oikein. Käyttämällä Googlen `AutoService`:a voit automatisoida `META-INF/services/com.webforj.error.ErrorHandler` -tiedoston generoinnin. Sinun tarvitsee vain merkitä virheanalyytikkosi `AutoService`-annotaatiolla. Voit oppia lisää [AutoService:sta täällä](https://github.com/google/auto/blob/main/service/README.md).

```java
@AutoService(ErrorHandler.class)
public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Mukautettu käsittelylogiikka NullPointerException:lle
    String title = "Null Pointer Exception";
    String content = "Null-arvo kohtasi paikan, jossa objekti on vaadittu.";

    showErrorPage(title, content);
  }
}
```

## `GlobalErrorHandler` luokka {#the-globalerrorhandler-class}

`GlobalErrorHandler` on oletusvirheanalyytikko, jonka webforJ tarjoaa. Se toteuttaa `ErrorHandler` rajapinnan ja tarjoaa yleistä virheiden käsittelyä.

### Käyttäytyminen {#behavior}

- **Lokitus**: Virheet kirjataan sekä palvelimelle että selaimen konsoliin.
- **Virhesivun näyttäminen**: Riippuen debug-tilasta, virhesivu näyttää pinon jäljityksen tai yleisen virheilmoituksen.

### Mukautetun globaalin virheanalyytikon määrittäminen {#defining-a-custom-global-error-handler}

Mukautetun globaalin virheanalyytikon määrittämiseksi sinun on luotava uusi virheanalyytikko nimeltä `WebforjGlobalErrorHandler` ja seurattava [virheanalyytikoiden rekisteröintivaiheita](#step-2-register-the-error-handler), kuten aiemmin on selitetty. Tässä tapauksessa webforJ etsii ensin mahdollisia mukautettuja virheanalyytikoita poikkeusten hallitsemiseksi. Jos niitä ei löydy, webforJ palaa mukautettuun globaaliin virheanalyytikkoon.

:::info
Jos useita `WebforjGlobalErrorHandler`-analyytikkoja on rekisteröity, webforJ valitsee ensimmäisen niistä.
:::
