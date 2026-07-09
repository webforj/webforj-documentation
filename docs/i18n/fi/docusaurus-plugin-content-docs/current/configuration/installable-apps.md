---
title: Installable Apps
sidebar_position: 20
description: >-
  Annotate a webforJ app with AppProfile to generate a Web App Manifest with
  icons, screenshots, and metadata for device installation.
_i18n_hash: 60a6bf616536e9c202af684e9a505af6
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

`@AppProfile`-annotaatio webforJ:ssä mahdollistaa sovelluksesi asentamisen tuetuilla alustoilla.
Asennettavat web-sovellukset integroituvat laitteen käyttöjärjestelmään.
Asennettuna ne näkyvät kotinäytössä tai sovellusvalikossa, kuten natiivisovellukset.
Tätä varten on annettava tiettyjä metatietoja, kuten nimi, kuvaus ja ikonit.
Nämä tiedot auttavat käyttöjärjestelmää tunnistamaan ja näyttämään sovelluksen.

:::info Turvallisen alkuperän vaatimus
Sovelluksen ollakseen asennettavissa, sen on oltava palvelimelta turvallisesta alkuperästä, kuten `https`.
Selain hylkää asennusyritykset epäturvallisista alkuperistä. Tämä sääntö ei kuitenkaan koske, kun sovellusta toimitetaan paikallisesti `localhost`-ympäristössä kehityksen aikana.

<!-- vale off -->
Lisätietoja turvallisista konteksteista ja niiden tärkeydestä löytyy [Secure Contexts MDN -dokumentaatiosta](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<div class="videos-container">
  <video controls>
    <source src="/video/install-chrome.mp4" type="video/mp4"/>
  </video>
</div>

## Selaintuki {#browser-support}

Tuki web-sovelluksen asentamiselle vaihtelee selaimen ja alustan mukaan.

### Työpöytä {#browser-support-desktop}

- **Chromium-selaimet** (Chrome, Edge, Opera, Brave ja muut) asentavat minkä tahansa sovelluksen, joka sisältää manifestitiedoston, kaikilla tuetuilla työpöytäkäyttöjärjestelmillä.
- **Safari** tukee **Tiedosto → Lisää Dockiin** macOS Sonamassa (Safari 17) ja myöhemmin. Tämä prosessi toimii minkä tahansa web-sovelluksen, oli sillä manifestitiedostoa tai ei.
- **Firefox** ei tue web-sovellusten asentamista manifestitiedostosta työpöydällä.

### Mobiili {#browser-support-mobile}

- **Androidilla** Chrome, Edge, Firefox, Opera ja Samsung Internet tukevat kaikkia web-sovellusten asentamista.
- **iOS 16.3 ja aikaisemmat** versiot mahdollistavat web-sovellusten asentamisen vain Safarista (**Jaa → Lisää Kotonäytölle**).
- **iOS 16.4 ja uudemmissa** versioissa web-sovelluksia voidaan asentaa Jaa-valikosta Safarissa, Chromessa, Edgessä, Firefoxissa ja Orionissa.

## `@AppProfile`-annotaatio {#appprofile-annotation}

`@AppProfile`-annotaatio on määritettävä pääsovellusluokkaan ja se vaatii vain vähäistä konfigurointia. Vähintään on annettava:

- **name**: Sovelluksen koko nimi.
- **shortName**: Tiivistetty versio nimestä käytettäväksi rajoitetuissa tiloissa.

Lisävalinnaiset ominaisuudet mahdollistavat sovelluksen ulkoasun ja käyttäytymisen mukauttamisen.

Kun `@AppProfile`-annotaatio on läsnä, webforJ:

- Asettaa automaattisesti tarvittavat metatiedot.
- Luo [Web-sovelluksen manifestin](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Palvelee siihen liittyviä resursseja, kuten ikoneita ja näyttökuvia.

### Esimerkki: `@AppProfile`-annotaation käyttäminen {#example-applying-appprofile}

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

Seuraavassa taulukossa on listattu kaikki `@AppProfile`-annotaatiossa tuetut ominaisuudet:

| **Ominaisuus**    | **Tyyppi**                                     | **Kuvaus**                                                                                          | **Oletusarvo**     |
| ------------------ | ---------------------------------------------- | --------------------------------------------------------------------------------------------------- | ------------------- |
| `name`             | `String`                                       | Sovelluksen koko nimi, joka näkyy sovellusvalikoissa ja asennusdialogeissa.                        | **Pakollinen**     |
| `shortName`        | `String`                                       | Nimen lyhyt versio, jota käytetään rajoitetuissa tiloissa. Sen ei tulisi ylittää 12 merkkiä.         | **Pakollinen**     |
| `description`      | `String`                                       | Lyhyt kuvaus sovelluksesta, joka näkyy asennuksen aikana ja sovelluksen asetuksissa.                | `""`                |
| `themeColor`       | `String`                                       | Sovelluksen teeman väri, jota sovelletaan selaimen käyttöliittymään sovelluksen avatessa.           | `"#ffffff"`         |
| `backgroundColor`  | `String`                                       | Alkuperäinen taustaväri sovellukselle latauksen aikana.                                            | `"#f8fafc"`         |
| `startUrl`         | `String`                                       | URL-osoite, joka avataan, kun sovellus käynnistetään.                                              | `"."`               |
| `display`          | `Display` **_Enum_**                          | Sovelluksen näyttötila (esim. `FULLSCREEN`, `STANDALONE`, `BROWSER`).                             | `STANDALONE`        |
| `orientation`      | `Orientation` **_Enum_**                       | Sovelluksen oletussuunnittelu (esim. `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                          | `NATURAL`           |
| `icons`            | [`Icon[]`](#appprofileicon-properties)        | Taulukko ikoneista, jotka edustavat sovellusta eri tarkkuuksilla.                                  | `[]`                |
| `defaultIcon`      | [`DefaultIcon`](#appprofiledefaulticon-properties) | Määrittää oletuskuvakkeen sovellukselle. Automatisoi ikoni-polkujen generoinnin useissa koossa, jos on konfiguroitu. | `icons://icon.png` |
| `screenshots`      | [`Screenshot[]`](#appprofilescreenshot-properties) | Taulukko sovelluksen näyttökuvista, joita käytetään asennusdialogeissa.                            | `[]`                |
| `categories`       | `String[]`                                     | Luokittelu, joka luokittelee sovelluksen (esim. `Rahoitus`, `Ostokset`).                          | `[]`                |

### `@AppProfile.Icon` -ominaisuudet {#appprofileicon-properties}

Ikonit määrittävät sovelluksesi visuaalisen esityksen valikoissa ja kotinäytöissä. `@AppProfile.Icon`-annotaatio tukee seuraavia ominaisuuksia:

| **Ominaisuus**                                                                     | **Tyyppi** | **Kuvaus**                                                                                        | **Oletusarvo** |
| ---------------------------------------------------------------------------------- | ---------- | -------------------------------------------------------------------------------------------------- | --------------- |
| `src`                                                                             | `String`   | Polku ikonille. Tämä voi olla absoluuttinen URL tai `ws://`-polku.                                 | **Pakollinen** |
| `sizes`                                                                           | `String`   | Merkkijono, joka määrittää yhden tai useamman kuvan koon muodossa `LeveysxKorkeus` (esim. `512x512`). | **Pakollinen** |
| `type`                                                                            | `String`   | Kuvakkeen media tyyppi (esim. `image/png`, `image/jpeg`). Jos ei annettu, se havaitaan automaattisesti | `""`            |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose)  | `String`   | Kuvakkeen käyttötarkoitus (esim. `any`, `maskable`, `monochrome`).                                   | `""`            |

### Esimerkki {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### `@AppProfile.DefaultIcon` -ominaisuudet {#appprofiledefaulticon-properties}

`DefaultIcon`-annotaatio yksinkertaistaa sovellusikonien konfigurointia luomalla useita koko variaatioita perustason ikonista.
Se tuottaa ikoneita laitteiden yleisesti pyytämillä tarkkuuksilla.

| **Ominaisuus** | **Tyyppi** | **Kuvaus**                                                                    | **Oletusarvo** |
| --------------- | ---------- | ---------------------------------------------------------------------------- | --------------- |
| `value`         | `String`   | Polku peruskuvaketiedostoon. Tämä voi olla absoluuttinen URL tai `ws://`-polku. | **Pakollinen** |
| `sizes`         | `int[]`    | Taulukko generaattoreista, jotka on määritelty kokonaislukuina (esim. `{144, 192, 512}`). | `{144, 192, 512}` |

:::info Kuvake tiedosto vaatimukset
Tämä konfigurointi ei automaattisesti luo varsinaisia kuvaketiedostoja sovellukselle. Sen sijaan se käyttää `@AppProfile.DefaultIcon` -annotaatiota luodakseen vastaavat [`@AppProfile.Icon`](#appprofileicon-properties) merkinnät jokaiselle määritellylle koolle.

#### Jos käytetään [webserver-protokollaa](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- Sinun on tarjottava perus `icon.png` -tiedosto `static/icons`-kansioon.
- Sinun odotetaan sisältävän lisäkuvakuvia nimeltään `icon-144x144.png`, `icon-192x192.png` ja `icon-512x512.png`.
- Nämä tietyt koot kattavat laitteiden yleisesti pyytämät tarkkuudet.

#### Jos käytetään [ikonit-protokollaa](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- Sinun odotetaan tarjoavan perus `icon.png` -tiedosto `/icons`-kansioon.
- `icons`-päätepiste tarjoaa dynaamisesti erilaiset kuvakokoja kysynnän mukaan, kun niitä kysytään.
:::

### `@AppProfile.Screenshot` -ominaisuudet {#appprofilescreenshot-properties}

Näyttökuvat tarjoavat esikatselun sovelluksesta asennusdialogeissa tai sovelluskaupoissa. `@AppProfile.Screenshot` -annotaatio tukee seuraavia ominaisuuksia:

| **Ominaisuus**                                                                                  | **Tyyppi** | **Kuvaus**                                                                                             | **Oletusarvo** |
| ----------------------------------------------------------------------------------------------- | ---------- | ------------------------------------------------------------------------------------------------------- | --------------- |
| `src`                                                                                           | `String`   | Polku näyttökuvalle. Tämä voi olla absoluuttinen URL tai `ws://`-polku.                                | **Pakollinen** |
| `sizes`                                                                                         | `String`   | Merkkijono, joka määrittää yhden tai useamman kuvan koon muodossa `LeveysxKorkeus` (esim. `1080x1920`). | **Pakollinen** |
| `type`                                                                                          | `String`   | Näyttökuvan media tyyppi (esim. `image/png`, `image/jpeg`). Jos ei annettu, se havaitaan automaattisesti | `""`            |
| `label`                                                                                         | `String`   | Kuvauksellinen etiketti näyttökuvalle.                                                                  | `""`            |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor)   | `String`   | Näyttökuvan muototekijä (esim. `narrow`, `wide`).                                                      | `""`            |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)        | `String`   | Alusta, johon näyttökuva on tarkoitettu (esim. `ios`, `android`).                                     | `""`            |

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
