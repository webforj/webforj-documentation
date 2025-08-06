---
title: Installable Apps
sidebar_position: 10
_i18n_hash: 611c70817a57e6cad940081f90d4e0a2
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

`@AppProfile` -annotaatio webforJ:ssä mahdollistaa sovelluksesi asentamisen tuetuissa alustoissa. Asennettavat verkkosovellukset integroituvat saumattomasti laitteen käyttöjärjestelmään. Asennettuna ne näkyvät aloitusnäytöllä tai sovellusvalikossa, samoin kuin natiivisovellukset. Tämän saavuttamiseksi tietyt metatiedot, kuten nimi, kuvaus ja ikonit, on annettava. Nämä tiedot auttavat käyttöjärjestelmää tunnistamaan ja näyttämään sovelluksen.

:::info Turvallisen alkuperän vaatimus
Sovelluksen ollakseen asennettavissa, se on toimitettava turvallisesta alkuperästä, kuten `https`. Tämä vaatimus varmistaa, että sovellus täyttää asennukseen tarvittavat turvallisuusstandardit. Tämä sääntö ei kuitenkaan koske sovelluksen paikallista tarjoamista `localhost`-osoitteesta kehityksen aikana.

<!-- vale off -->
Lisätietoja turvallisista konteksteista ja niiden tärkeydestä saat [Secure Contexts MDN -dokumentaatiosta](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<div class="videos-container">
  <video controls>
    <source src="/video/install-chrome.mp4" type="video/mp4"/>
  </video>
</div>

## `@AppProfile` -annotaatio {#appprofile-annotation}

`@AppProfile` -annotaatio sovelletaan pääsovellusluokkaan ja vaatii minimaalisen konfiguroinnin. Vähintään sinun on annettava:

- **nimi**: Sovelluksen koko nimi.
- **lyhytNimi**: Tiivis versio nimestä käytettäväksi rajoitetun tilan konteksteissa.

Lisävalinnaiset ominaisuudet mahdollistavat sovelluksen ulkoasun ja käytöksen mukauttamisen.

Kun `@AppProfile` -annotaatio on läsnä, webforJ:

- Asettaa automaattisesti tarvittavat metatunnisteet.
- Luo [Web-sovellusmanifesteja](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Palvelee siihen liittyviä resursseja, kuten ikoneita ja näyttökuvia.

### Esimerkki: `@AppProfile` -annotaation käyttäminen {#example-applying-appprofile}

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

## `@AppProfile` -ominaisuudet {#appprofile-properties}

Seuraavassa taulukossa luetellaan kaikki `@AppProfile` -annotaation tukemat ominaisuudet:

| **Ominaisuus**      | **Tyyppi**                                           | **Kuvaus**                                                                                           | **Oletusarvo**     |
| --------------------| ----------------------------------------------------| -----------------------------------------------------------------------------------------------------| -------------------|
| `name`              | `String`                                           | Sovelluksen koko nimi, joka näkyy sovellusvalikoissa ja asennusikkunoissa.                          | **Pakollinen**     |
| `shortName`         | `String`                                           | Lyhyt versio nimestä, käytetään rajoitetun tilan konteksteissa. Sen ei tulisi ylittää 12 merkkiä.   | **Pakollinen**     |
| `description`       | `String`                                           | Tiivis kuvaus sovelluksesta, joka näkyy asennuksen aikana ja sovelluksen asetuksissa.               | `""`                |
| `themeColor`        | `String`                                           | Sovelluksen teema väri, joka sovelletaan selainkäyttöliittymään sovelluksen käynnistyessä.          | `"#ffffff"`         |
| `backgroundColor`   | `String`                                           | Sovelluksen aloitus väri latauksen aikana.                                                            | `"#f8fafc"`         |
| `startUrl`          | `String`                                           | URL, joka avataan, kun sovellus käynnistetään.                                                     | `"."`               |
| `display`           | `Display` **_Enum_**                               | Sovelluksen näyttötila (esim. `FULLSCREEN`, `STANDALONE`, `BROWSER`).                               | `STANDALONE`        |
| `orientation`       | `Orientation` **_Enum_**                           | Sovelluksen oletussuunta (esim. `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                                | `NATURAL`           |
| `icons`             | [`Icon[]`](#appprofileicon-properties)             | Taulukko ikoneista, jotka esittävät sovellusta eri resoluutioissa.                                  | `[]`                |
| `defaultIcon`       | [`DefaultIcon`](#appprofiledefaulticon-properties) | Määrittelee sovelluksen oletusikonin. Luodaan automaattisesti ikonipolkut eri kokoissa, jos on määritetty. | `icons://icon.png` |
| `screenshots`       | [`Screenshot[]`](#appprofilescreenshot-properties) | Taulukko näyttökuvista sovelluksesta, käytetään asennusikkunoissa.                                  | `[]`                |
| `categories`        | `String[]`                                         | Kategoriat sovelluksen luokittelua varten (esim. `Rahoitus`, `Ostokset`).                           | `[]`                |

### `@AppProfile.Icon` -ominaisuudet {#appprofileicon-properties}

Ikonit määrittelevät sovelluksesi visuaalisen esityksen valikoissa ja aloitusnäytöissä. `@AppProfile.Icon` -annotaatio tukee seuraavia ominaisuuksia:

| **Ominaisuus**                                                                     | **Tyyppi** | **Kuvaus**                                                                                       | **Oletusarvo** |
| ---------------------------------------------------------------------------------- | --------- | -------------------------------------------------------------------------------------------------| ----------------- |
| `src`                                                                             | `String`  | Polku ikoniin. Tämä voi olla absoluuttinen URL-osoite tai `ws://`-polku.                        | **Pakollinen**   |
| `sizes`                                                                           | `String`  | Merkkijono, joka määrittelee yhden tai useamman kuvan koon muodossa `LeveysxKorkeus` (esim. `512x512`). | **Pakollinen**   |
| `type`                                                                            | `String`  | Ikonin MIME-tyyppi (esim. `image/png`, `image/jpeg`). Jos ei anneta, se määritetään automaattisesti | `""`              |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String`  | Ikonin käyttötarkoitus (esim. `any`, `maskable`, `monochrome`).                                   | `""`              |

### Esimerkki {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### `@AppProfile.DefaultIcon` -ominaisuudet {#appprofiledefaulticon-properties}

`DefaultIcon` -annotaatio yksinkertaistaa sovelluksen ikonien konfigurointia luomalla useita kokoja perusikonista.
Tämä on erityisen hyödyllistä varmistaessa yhteensopivuus eri resoluutioilla varustettujen laitteiden kanssa.

| **Ominaisuus** | **Tyyppi** | **Kuvaus**                                                                   | **Oletusarvo** |
| --------------- | ---------- | ---------------------------------------------------------------------------- | ----------------- |
| `value`         | `String`   | Polku perusikonitiedostoon. Tämä voi olla absoluuttinen URL-osoite tai `ws://`-polku. | **Pakollinen**   |
| `sizes`         | `int[]`    | Taulukko koosta, joka on määritelty kokonaislukuina (esim. `{144, 192, 512}`). | `{144, 192, 512}` |

:::info Ikonitiedoston vaatimukset
Tämä konfiguraatio ei luo sovelluksen ikonitiedostoja automaattisesti. Sen sijaan se käyttää `@AppProfile.DefaultIcon` -annotaatiota luodakseen vastaavat [`@AppProfile.Icon`](#appprofileicon-properties) -merkinnät jokaiselle määritetylle koolle.

#### Jos käytät [verkkopalvelinprotokollaa](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- Sinun on annettava perus `icon.png` -tiedosto `static/icons` -kansiossa.
- Sinun odotetaan lisäävän lisäikoniversioita nimellä `icon-144x144.png`, `icon-192x192.png`, ja `icon-512x512.png`.
- Nämä erityiset koot varmistavat yhteensopivuuden eri laitteiden ja resoluutioiden kanssa.

#### Jos käytät [ikonit-protokollaa](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- Sinun odotetaan tarjoavan perus `icon.png` -tiedosto `/icons` -kansiossa.
- `icons`-päätepiste toimittaa eri ikonikoostumuksia tarpeen mukaan, kun niitä pyydetään.
:::

### `@AppProfile.Screenshot` -ominaisuudet {#appprofilescreenshot-properties}

Näyttökuvat tarjoavat esikatselun sovelluksesta asennusdialogeissa tai sovelluskaupoissa. `@AppProfile.Screenshot` -annotaatio tukee seuraavia ominaisuuksia:

| **Ominaisuus**                                                                                 | **Tyyppi** | **Kuvaus**                                                                                             | **Oletusarvo** |
| ---------------------------------------------------------------------------------------------- | --------- | ----------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                                          | `String`  | Polku näyttökuvaan. Tämä voi olla absoluuttinen URL-osoite tai `ws://`-polku.                        | **Pakollinen**   |
| `sizes`                                                                                        | `String`  | Merkkijono, joka määrittelee yhden tai useamman kuvan koon muodossa `LeveysxKorkeus` (esim. `1080x1920`). | **Pakollinen**   |
| `type`                                                                                         | `String`  | Näyttökuvan MIME-tyyppi (esim. `image/png`, `image/jpeg`). Jos ei anneta, se määritetään automaattisesti | `""`              |
| `label`                                                                                        | `String`  | Kuvaileva etiketti näyttökuvalle.                                                                       | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String`  | Näyttökuvan muototekijä (esim. `narrow`, `wide`).                                                     | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)      | `String`  | Alusta, jolle näyttökuva on tarkoitettu (esim. `ios`, `android`).                                    | `""`              |

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
