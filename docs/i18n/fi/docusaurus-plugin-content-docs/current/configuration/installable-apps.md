---
title: Asennettavat sovellukset
sidebar_position: 10
_i18n_hash: 76c2d63a5d5ea13f5ce55431108e6a3d
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

`@AppProfile`-annotaatio webforJ:ssä mahdollistaa sovelluksesi asentamisen tuetuilla alustoilla. 
Asennettavat verkkosovellukset integroituvat saumattomasti laitteen käyttöjärjestelmään. 
Asennettuina ne ilmestyvät aloitusnäytölle tai sovellusvalikkoon, kuten natiivisovellukset. 
Tämän saavuttamiseksi tietyt metatiedot, kuten nimi, kuvaus ja ikonit, on annettava. 
Nämä tiedot auttavat käyttöjärjestelmää tunnistamaan ja näyttämään sovelluksen.

:::info Vaatimus Turvalliselle Alustalle
Sovelluksen ollakseen asennettavissa, se on tarjottava turvallisesta alustasta, kuten `https`. 
Tämä vaatimus varmistaa, että sovellus täyttää asennuksen vaatimukset turvallisuudessa. Kuitenkin, tätä sääntöä ei sovelleta, kun sovellus tarjotaan paikallisesti `localhost`-osoitteesta kehitysvaiheessa.

<!-- vale off -->
Lisätietoja turvallisista konteksteista ja niiden merkityksestä löydät [Secure Contexts MDN -dokumentaatiosta](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<div class="videos-container">
  <video controls>
    <source src="/video/install-chrome.mp4" type="video/mp4"/>
  </video>
</div>

## `@AppProfile`-annotaatio {#appprofile-annotation}

`@AppProfile`-annotaatio sovelletaan pääsovelluksen luokkaan ja se vaatii minimaalista konfigurointia. Vähintään sinun on annettava:

- **name**: Sovelluksen koko nimi.
- **shortName**: Lyhyt versio nimestä käytettäväksi rajoitetun tilan konteksteissa.

Lisävalinnaiset ominaisuudet mahdollistavat sovelluksen ulkoasun ja käyttäytymisen mukauttamisen.

Kun `@AppProfile`-annotaatio on läsnä, webforJ:

- Asettaa automaattisesti tarvittavat metatagi.
- Luodaan [Verkkosovelluksen manifesti](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Palvelutaa liittyviä resursseja, kuten ikoneita ja kuvakaappauksia.

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

Seuraavassa taulukossa luetellaan kaikki `@AppProfile`-annotaation tukemat ominaisuudet:

| **Ominaisuus**   | **Tyyppi**                                         | **Kuvaus**                                                                                              | **Oletusarvo**         |
| ----------------- | -------------------------------------------------- | ------------------------------------------------------------------------------------------------------- | ----------------------- |
| `name`            | `String`                                          | Sovelluksen koko nimi, joka näkyy sovellusvalikoissa ja asennusdialogeissa.                             | **Pakollinen**         |
| `shortName`       | `String`                                          | Lyhyt versio nimestä, käytettävä rajoitetun tilan konteksteissa. Ei saisi ylittää 12 merkkiä.           | **Pakollinen**         |
| `description`     | `String`                                          | Lyhyt kuvaus sovelluksesta, joka näkyy asennuksen aikana ja sovellusasetuksissa.                       | `""`                    |
| `themeColor`      | `String`                                          | Sovelluksen teeman väri, joka sovelletaan selaimen käyttöliittymään, kun sovellus käynnistyy.          | `"#ffffff"`             |
| `backgroundColor` | `String`                                          | Alkuperäinen taustaväri sovelluksen latauksen aikana.                                                  | `"#f8fafc"`             |
| `startUrl`        | `String`                                          | URL, joka avataan, kun sovellus käynnistetään.                                                         | `"."`                   |
| `display`         | `Display` **_Enum_**                              | Sovelluksen näyttötapa (esim. `FULLSCREEN`, `STANDALONE`, `BROWSER`).                                   | `STANDALONE`            |
| `orientation`     | `Orientation` **_Enum_**                          | Sovelluksen oletusorientaatio (esim. `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                              | `NATURAL`               |
| `icons`           | [`Icon[]`](#appprofileicon-properties)            | Taulukko ikoneista, jotka kuvaavat sovellusta eri resoluutioissa.                                      | `[]`                    |
| `defaultIcon`     | [`DefaultIcon`](#appprofiledefaulticon-properties)| Määrittää oletusikonin sovellukselle. Generoi automaattisesti ikonipolkuja eri kokoisina, jos konfiguroitu.| `icons://icon.png`   |
| `screenshots`     | [`Screenshot[]`](#appprofilescreenshot-properties)| Taulukko kuvakaappauksista sovellukselle, käytetään asennusdialogeissa.                                 | `[]`                    |
| `categories`      | `String[]`                                        | Kategoriat sovelluksen luokittelemiseksi (esim. `Finance`, `Shopping`).                                 | `[]`                    |

### `@AppProfile.Icon` -ominaisuudet {#appprofileicon-properties}

Ikkunat määrittävät sovelluksesi visuaalisen esityksen valikoissa ja aloitusnäytöissä. `@AppProfile.Icon`-annotaatio tukee seuraavia ominaisuuksia:

| **Ominaisuus**                                                                     | **Tyyppi** | **Kuvaus**                                                                                         | **Oletusarvo** |
| ---------------------------------------------------------------------------------- | --------- | -------------------------------------------------------------------------------------------------- | --------------- |
| `src`                                                                             | `String`  | Polku ikoniin. Tämä voi olla absoluuttinen URL tai `ws://`-polku.                                 | **Pakollinen** |
| `sizes`                                                                           | `String`  | Merkkijono, joka määrittää yhden tai useamman kuvan koon muodossa `LeveysxKorkeus` (esim. `512x512`). | **Pakollinen** |
| `type`                                                                            | `String`  | Ikonin MIME-tyyppi (esim. `image/png`, `image/jpeg`). Jos ei annettu, se havaitaan automaattisesti. | `""`           |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String`  | Ikonin tarkoitus (esim. `any`, `maskable`, `monochrome`).                                        | `""`           |

### Esimerkki {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### `@AppProfile.DefaultIcon` -ominaisuudet {#appprofiledefaulticon-properties}

`DefaultIcon`-annotaatio yksinkertaistaa sovellusikonien konfigurointia luomalla useita kokoa vaihtelevia versioita perusikonista.
Tämä on erityisen hyödyllistä taatun yhteensopivuuden varmistamiseksi laitteiden kanssa, joilla on erilaisia resoluutioita.

| **Ominaisuus** | **Tyyppi** | **Kuvaus**                                                                    | **Oletusarvo** |
| --------------- | ---------- | ------------------------------------------------------------------------------ | --------------- |
| `value`         | `String`   | Polku perusikonitiedostoon. Tämä voi olla absoluuttinen URL tai `ws://`-polku.| **Pakollinen** |
| `sizes`         | `int[]`    | Taulukko kokoista, joka määritetään kokonaislukuina (esim. `{144, 192, 512}`).| `{144, 192, 512}` |

:::info Ikonitiedostovaatimukset
Tämä konfiguraatio ei luo itse ikonitiedostoja sovellukselle automaattisesti. Sen sijaan se käyttää `@AppProfile.DefaultIcon`-annotaatiota luodakseen vastaavat [`@AppProfile.Icon`](#appprofileicon-properties) -merkinnät jokaiselle määritetylle koolle.

#### Jos käytetään [webserver-protokollaa](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- Sinun on annettava perus `icon.png` -tiedosto `static/icons` -kansiossa.
- Sinun on odotettava lisää ikonivariaatioita, joiden nimet ovat `icon-144x144.png`, `icon-192x192.png` ja `icon-512x512.png`.
- Nämä erityiset koot varmistavat yhteensopivuuden eri laitteiden ja resoluutioiden kanssa.

#### Jos käytetään [ikoniprotokollaa](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}
- Sinun on odotettava perus `icon.png` -tiedosto `icons` -kansiossa.
- `icons`-piste dynaamisesti tarjoaa erilaisia ikonikokoja kysyttäessä.
:::

### `@AppProfile.Screenshot` -ominaisuudet {#appprofilescreenshot-properties}

Kuvakaappaukset tarjoavat esikatselun sovelluksesta asennusdialogeissa tai sovelluskaupoissa. `@AppProfile.Screenshot`-annotaatio tukee seuraavia ominaisuuksia:

| **Ominaisuus**                                                                 | **Tyyppi** | **Kuvaus**                                                                                              | **Oletusarvo** |
| -------------------------------------------------------------------------------- | --------- | -------------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                           | `String`  | Polku kuvakaappaukseen. Tämä voi olla absoluuttinen URL tai `ws://`-polku.                             | **Pakollinen**     |
| `sizes`                                                                         | `String`  | Merkkijono, joka määrittää yhden tai useamman kuvan koon muodossa `LeveysxKorkeus` (esim. `1080x1920`).| **Pakollinen**     |
| `type`                                                                          | `String`  | Kuvakaappauksen MIME-tyyppi (esim. `image/png`, `image/jpeg`). Jos ei annettu, se havaitaan automaattisesti.| `""`              |
| `label`                                                                         | `String`  | Kuvaava etiketti kuvakaappausta varten.                                                               | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor)| `String`  | Kuvakaappauksen muoto (esim. `narrow`, `wide`).                                                       | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)| `String`  | Alusta, jolle kuvakaappaus on tarkoitettu (esim. `ios`, `android`).                                   | `""`              |

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
