---
title: Installable Apps
sidebar_position: 10
description: >-
  Annotate a webforJ app with AppProfile to generate a Web App Manifest with
  icons, screenshots, and metadata for device installation.
_i18n_hash: 2d76df483c951a64d266380d7c96b692
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

`@AppProfile`-annotaatio webforJ:ssä mahdollistaa sovelluksesi asentamisen tuetuilla alustoilla. 
Asennettavat verkkosovellukset integroituvat laitteen käyttöjärjestelmään. 
Asennettuna ne näkyvät aloitusnäytöllä tai sovellusvalikossa, samankaltaisesti natiivisovellusten kanssa. 
Tämän saavuttamiseksi tietyt metatiedot, kuten nimi, kuvaus ja ikonit, on toimitettava. 
Nämä tiedot auttavat käyttöjärjestelmää tunnistamaan ja esittämään sovelluksen.

:::info Turvallisen alkuperän vaatimukset
Sovelluksen ollakseen asennettavissa, sen on oltava palvelimelta, jonka alkuperä on turvallinen, kuten `https`. 
Selainohjelmat hylkäävät asennusyritykset epävarmoista alkuperistä. Tämä sääntö ei kuitenkaan koske sovelluksen palvelemista paikallisesti `localhost`-osoitteesta kehitysvaiheessa.

<!-- vale off -->
Lisätietoja turvallisista konteksteista ja niiden tärkeydestä löydät [Secure Contexts MDN -dokumentaatiosta](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<div class="videos-container">
  <video controls>
    <source src="/video/install-chrome.mp4" type="video/mp4"/>
  </video>
</div>

## Selain tuki {#browser-support}

Tuki verkkosovellusten asentamiseen vaihtelee selainten ja alustojen mukaan.

### Työpöytä {#browser-support-desktop}

- **Chromium-selaimet** (Chrome, Edge, Opera, Brave ja muut) asentavat minkä tahansa sovelluksen, joka toimittaa manifestitiedoston kaikilla tuetuilla työpöytäkäyttöjärjestelmillä.
- **Safari** tukee **Tiedosto → Lisää Dockiin** macOS Sonamassa (Safari 17) ja myöhemmissä versioissa. Prosessi toimii kaikille verkkosovelluksille, riippumatta siitä, onko manifestitiedostoa tai ei.
- **Firefox** ei tue verkkosovellusten asentamista manifestitiedostosta työpöydällä.

### Mobiili {#browser-support-mobile}

- **Androidissa** Chrome, Edge, Firefox, Opera ja Samsung Internet tukevat verkkosovellusten asentamista.
- **iOS 16.3 ja aiemmissa versioissa** verkkosovelluksia voi asentaa vain Safarista (**Jaa → Lisää aloitusnäyttöön**).
- **iOS 16.4 ja myöhemmissä versioissa** verkkosovelluksia voi asentaa Jaa-valikosta Saferista, Chromesta, Edgestä, Firefoxista ja Orionista.

## `@AppProfile`-annotaatio {#appprofile-annotation}

`@AppProfile`-annotaatio sovelletaan pääsovellusluokkaan ja se vaatii minimaalista konfigurointia. Vähintään sinun on toimitettava:

- **name**: Sovelluksen koko nimi.
- **shortName**: Lyhennetty versio nimestä käytettäväksi rajoitetuissa tiloissa.

Lisävalinnaiset ominaisuudet mahdollistavat sovelluksen ulkoasun ja toiminnan mukauttamisen.

Kun `@AppProfile`-annotaatio on läsnä, webforJ:

- Määrittää automaattisesti tarvittavat meta-tagit.
- Generoi [Web Application Manifest](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Palvelee siihen liittyviä resursseja, kuten ikoneita ja kuvakaappauksia.

### Esimerkki: `@AppProfile`-annotaation käyttö {#example-applying-appprofile}

```java
@AppProfile(
  name = "Zyntric Bank",
  shortName = "ZBank",
  description = "Zyntric Bank on yksinkertainen pankkisovellus, joka on rakennettu webforJ:llä",
  screenshots = {
    @AppProfile.Screenshot(
      src = "ws://img/screenshots/s1.jpg",
      sizes = "1080x1920"
    )
  }
)
public class Application extends App {
}
```

## `@AppProfile`-ominaisuudet {#appprofile-properties}

Seuraavassa taulukossa esitetään kaikki `@AppProfile`-annotaation tukemat ominaisuudet:

| **Ominaisuus**     | **Tyyppi**                                           | **Kuvaus**                                                                                           | **Oletusarvo**     |
| -------------------| --------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | -------------------- |
| `name`             | `String`                                           | Sovelluksen koko nimi, joka näkyy sovellusvalikoissa ja asennusdialogeissa.                                 | **Pakollinen**         |
| `shortName`        | `String`                                           | Lyhyt versio nimestä, jota käytetään rajoitetuissa tiloissa. Sen ei pitäisi ylittää 12 merkkiä.         | **Pakollinen**         |
| `description`      | `String`                                           | Lyhyt kuvaus sovelluksesta, joka näkyy asennuksen ja sovelluksen asetusten aikana.                        | `""`                  |
| `themeColor`       | `String`                                           | Sovelluksen teema väri, joka sovelletaan selainkäyttöliittymään, kun sovellus käynnistetään.             | `"#ffffff"`           |
| `backgroundColor`  | `String`                                           | Alkuperäinen taustaväri sovelluksessa latauksen aikana.                                                  | `"#f8fafc"`           |
| `startUrl`         | `String`                                           | URL-osoite, joka avautuu, kun sovellus käynnistetään.                                                  | `"."`                 |
| `display`          | `Display` **_Enum_**                               | Sovelluksen näyttötila (esim. `FULLSCREEN`, `STANDALONE`, `BROWSER`).                                   | `STANDALONE`          |
| `orientation`      | `Orientation` **_Enum_**                           | Sovelluksen oletusorientaatio (esim. `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                               | `NATURAL`             |
| `icons`            | [`Icon[]`](#appprofileicon-properties)            | Taulukko ikoneista, jotka edustavat sovellusta eri resoluutioilla.                                     | `[]`                  |
| `defaultIcon`      | [`DefaultIcon`](#appprofiledefaulticon-properties) | Määrittää oletuskuvakkeen sovellukselle. Automaattisesti luo ikonikuvapolkuja useissa ko'oissa, jos konfiguroitu. | `icons://icon.png` |
| `screenshots`      | [`Screenshot[]`](#appprofilescreenshot-properties) | Taulukko kuvakaappauksista sovellusta varten, käytetään asennusdialogeissa.                             | `[]`                  |
| `categories`       | `String[]`                                         | Kategorioita, joilla sovellus luokitellaan (esim. `Rahoitus`, `Ostokset`).                               | `[]`                  |

### `@AppProfile.Icon` -ominaisuudet {#appprofileicon-properties}

Ikonit määrittelevät sovelluksesi visuaalisen esityksen valikoissa ja aloitusnäytöissä. `@AppProfile.Icon` -annotaatio tukee seuraavia ominaisuuksia:

| **Ominaisuus**                                                                      | **Tyyppi** | **Kuvaus**                                                                                          | **Oletusarvo** |
| ----------------------------------------------------------------------------------- | ---------  | ---------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                              | `String`   | Polku ikoniin. Tämä voi olla absoluuttinen URL-osoite tai `ws://`-polku.                              | **Pakollinen**    |
| `sizes`                                                                            | `String`   | Merkkijono, joka määrittää yhden tai useamman kuvan koon muodossa `LeveysxKorkeus` (esim. `512x512`). | **Pakollinen**    |
| `type`                                                                             | `String`   | Ikonin mediatyypi (esim. `image/png`, `image/jpeg`). Jos ei anneta, se tunnistetaan automaattisesti | `""`              |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose)  | `String`   | Ikonin tarkoitus (esim. `any`, `maskable`, `monochrome`).                                           | `""`              |

### Esimerkki {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### `@AppProfile.DefaultIcon` -ominaisuudet {#appprofiledefaulticon-properties}

`DefaultIcon`-annotaatio yksinkertaistaa sovellusikonien konfigurointia luomalla useita koko versioita perusikonista.
Se tuottaa ikoneita laitteiden yleisesti pyytämillä resoluutioilla.

| **Ominaisuus** | **Tyyppi** | **Kuvaus**                                                                | **Oletusarvo** |
| --------------- | ---------  | ------------------------------------------------------------------------- | ----------------- |
| `value`         | `String`   | Polku perusikonitiedostoon. Tämä voi olla absoluuttinen URL-osoite tai `ws://`-polku. | **Pakollinen**    |
| `sizes`         | `int[]`    | Taulukko generaattipituudet, määritettyinä kokonaislukkoina (esim. `{144, 192, 512}`). | `{144, 192, 512}` |

:::info Ikonitiedostovaatimukset
Tämä konfigurointi ei luo varsinaisia ikonikuvastoja sovellukselle automaattisesti. Sen sijaan, se käyttää `@AppProfile.DefaultIcon` -annotaatiota luodakseen vastaavat [`@AppProfile.Icon`](#appprofileicon-properties) merkinnät jokaiselle määritetylle koolle.

#### Jos käytät [web-palvelimen protokollaa](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- Sinun on toimitettava perus `icon.png` -tiedosto `static/icons` -kansiossa.
- Sinun on odotettava lisäikonimuunnelmien lisäämistä nimillä `icon-144x144.png`, `icon-192x192.png` ja `icon-512x512.png`.
- Nämä erityiset koot kattavat resoluutiot, joita laitteet yleisesti pyytävät.

#### Jos käytät [ikoni-protokollaa](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- Sinun on odotettava tarjoavasi perus `icon.png` -tiedosto `/icons` -kansiossa.
- `icons`-piste dynaamisesti tarjoaa erilaisia ikonikokoja pyynnöstä.

:::

### `@AppProfile.Screenshot` -ominaisuudet {#appprofilescreenshot-properties}

Kuvakaappaukset antavat ennakkokatsauksen sovelluksesta asennusdialogeissa tai sovelluskaupoissa. `@AppProfile.Screenshot` -annotaatio tukee seuraavia ominaisuuksia:

| **Ominaisuus**                                                                                                   | **Tyyppi** | **Kuvaus**                                                                                             | **Oletusarvo** |
| ---------------------------------------------------------------------------------------------------------------- | ---------  | ------------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                                                           | `String`   | Polku kuvakaappaukseen. Tämä voi olla absoluuttinen URL-osoite tai `ws://`-polku.                    | **Pakollinen**    |
| `sizes`                                                                                                         | `String`   | Merkkijono, joka määrittää yhden tai useamman kuvan koon muodossa `LeveysxKorkeus` (esim. `1080x1920`). | **Pakollinen**    |
| `type`                                                                                                          | `String`   | Kuvakaappauksen mediatyypi (esim. `image/png`, `image/jpeg`). Jos ei anneta, se tunnistetaan automaattisesti | `""`              |
| `label`                                                                                                         | `String`   | Kuvakaappauksen kuvaava etiketti.                                                                      | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor)                  | `String`   | Kuvakaappauksen muototekijä (esim. `narrow`, `wide`).                                                | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)                        | `String`   | Alusta, jolle kuvakaappaus on tarkoitettu (esim. `ios`, `android`).                                   | `""`              |

### Esimerkki {#example-1}

```java
@AppProfile.Screenshot(
  src = "ws://img/screenshots/s1.jpg",
  sizes = "1080x1920"
)
```

<div class="videos-container">
  <video controls>
    <source src="/video/install-android.mp4" type="video/mp4"/>
  </video>
</div>
