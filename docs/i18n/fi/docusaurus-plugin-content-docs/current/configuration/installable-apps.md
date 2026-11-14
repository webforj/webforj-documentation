---
title: Asennettavat sovellukset
sidebar_position: 20
description: >-
  Annotate a webforJ app with AppProfile to generate a Web App Manifest with
  icons, screenshots, and metadata for device installation.
_i18n_hash: 9aa99cc9da864a6808d72602b6e5c572
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

webforJ:n `@AppProfile`-annotaation avulla voit tehdä sovelluksestasi tuetuilla alustoilla asennettavan.
Asennettavat verkkosovellukset integroituvat laitteen käyttöjärjestelmään.
Asennettuina ne näkyvät aloitusnäytössä tai sovellusvalikossa samalla tavalla kuin natiivisovellukset.
Tätä varten on annettava tietyt metatiedot, kuten nimi, kuvaus ja kuvakkeet.
Näiden tietojen avulla käyttöjärjestelmä tunnistaa sovelluksen ja näyttää sen.

:::info Suojatun alkuperän vaatimus
Jotta sovellus voidaan asentaa, sitä on tarjottava suojatusta alkuperästä, kuten `https`-osoitteesta.
Selaimet estävät asennusyritykset suojaamattomista alkuperistä. Tämä sääntö ei kuitenkaan koske paikallisesti `localhost`-osoitteesta kehityksen aikana tarjottavaa sovellusta.

<!-- vale off -->
Lisätietoja suojatuista konteksteista ja niiden merkityksestä on [MDN:n Secure Contexts -dokumentaatiossa](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<VideoPlayer video="installable-apps/install-chrome" width={1908} height={1052} title="Install chrome" />

## Selainyhteensopivuus {#browser-support}

Verkkosovelluksen asennuksen tuki vaihtelee selaimen ja alustan mukaan.

### Tietokoneet {#browser-support-desktop}

- **Chromium-selaimet** (Chrome, Edge, Opera, Brave ja muut) asentavat manifestitiedoston sisältävät sovellukset kaikissa tuetuissa tietokonekäyttöjärjestelmissä.
- **Safari** tukee macOS Sonomassa (Safari 17) ja uudemmissa versioissa toimintoa **Tiedosto → Lisää Dockiin**. Toiminto toimii kaikissa verkkosovelluksissa, riippumatta siitä, sisältävätkö ne manifestitiedoston.
- **Firefox** ei tue manifestitiedostoon perustuvien verkkosovellusten asentamista tietokoneille.

### Mobiililaitteet {#browser-support-mobile}

- **Androidissa** Chrome, Edge, Firefox, Opera ja Samsung Internet tukevat kaikki verkkosovellusten asentamista.
- **iOS 16.3:ssa ja sitä vanhemmissa versioissa** verkkosovelluksia voi asentaa vain Safarista (**Jaa → Lisää Koti-valikkoon**).
- **iOS 16.4:ssä ja uudemmissa versioissa** verkkosovelluksia voi asentaa Safarin, Chromen, Edgen, Firefoxin ja Orionin Jaa-valikosta.

## `@AppProfile`-annotaatio {#appprofile-annotation}

`@AppProfile`-annotaatiota käytetään sovelluksen pääluokassa, ja se vaatii vain vähän määrityksiä. Vähintään on annettava seuraavat tiedot:

- **name**: Sovelluksen koko nimi.
- **shortName**: Nimen lyhyt versio tilanteisiin, joissa tilaa on rajoitetusti.

Muiden valinnaisten ominaisuuksien avulla voit mukauttaa sovelluksen ulkoasua ja toimintaa.

Kun `@AppProfile`-annotaatio on käytössä, webforJ:

- Määrittää tarvittavat metatunnisteet automaattisesti.
- Luo [verkkosovelluksen manifestin](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Tarjoaa siihen liittyvät resurssit, kuten kuvakkeet ja kuvakaappaukset.

### Esimerkki: `@AppProfile`-annotaation käyttö {#example-applying-appprofile}

```java
@AppProfile(
  name = "Zyntric Bank",
  shortName = "ZBank",
  description = "Zyntric Bank is a simple banking application built with webforJ",
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

| **Ominaisuus**    | **Tyyppi**                                         | **Kuvaus**                                                                                                  | **Oletusarvo**        |
| ----------------- | -------------------------------------------------- | ----------------------------------------------------------------------------------------------------------- | --------------------- |
| `name`            | `String`                                           | Sovelluksen koko nimi, joka näytetään sovellusvalikoissa ja asennusikkunoissa.                               | **Pakollinen**        |
| `shortName`       | `String`                                           | Nimen lyhyt versio tilanteisiin, joissa tilaa on rajoitetusti. Enintään 12 merkkiä.                          | **Pakollinen**        |
| `description`     | `String`                                           | Sovelluksen lyhyt kuvaus, joka näytetään asennuksen aikana ja sovelluksen asetuksissa.                       | `""`                  |
| `themeColor`      | `String`                                           | Sovelluksen teemaväri, jota käytetään selaimen käyttöliittymässä sovellusta käynnistettäessä.                | `"#ffffff"`           |
| `backgroundColor` | `String`                                           | Sovelluksen taustaväri latauksen aikana.                                                                    | `"#f8fafc"`           |
| `startUrl`        | `String`                                           | Osoite, joka avataan sovellusta käynnistettäessä.                                                           | `"."`                 |
| `display`         | `Display` **_Enum_**                               | Sovelluksen näyttötila (esim. `FULLSCREEN`, `STANDALONE`, `BROWSER`).                                       | `STANDALONE`          |
| `orientation`     | `Orientation` **_Enum_**                           | Sovelluksen oletussuunta (esim. `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                                        | `NATURAL`             |
| `icons`           | [`Icon[]`](#appprofileicon-properties)             | Taulukko sovelluksen eri tarkkuuksia vastaavista kuvakkeista.                                                | `[]`                  |
| `defaultIcon`     | [`DefaultIcon`](#appprofiledefaulticon-properties) | Määrittää sovelluksen oletuskuvakkeen. Luo automaattisesti erikokoisten kuvakkeiden polut, jos ominaisuus määritetään. | `icons://icon.png` |
| `screenshots`     | [`Screenshot[]`](#appprofilescreenshot-properties) | Taulukko sovelluksen kuvakaappauksista, joita käytetään asennusikkunoissa.                                   | `[]`                  |
| `categories`      | `String[]`                                         | Sovelluksen luokitteluun käytettävät kategoriat (esim. `Finance`, `Shopping`).                              | `[]`                  |

### `@AppProfile.Icon`-ominaisuudet {#appprofileicon-properties}

Kuvakkeet määrittävät sovelluksen visuaalisen ilmeen valikoissa ja aloitusnäytöissä. `@AppProfile.Icon`-annotaatio tukee seuraavia ominaisuuksia:

| **Ominaisuus**                                                                   | **Tyyppi** | **Kuvaus**                                                                                              | **Oletusarvo** |
| -------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------ | ----------------- |
| `src`                                                                            | `String` | Kuvakkeen polku. Tämä voi olla absoluuttinen URL-osoite tai `ws://`-polku.                              | **Pakollinen**     |
| `sizes`                                                                          | `String` | Merkkijono, joka määrittää yhden tai useamman kuvakoon muodossa `LeveysxKorkeus` (esim. `512x512`).    | **Pakollinen**     |
| `type`                                                                           | `String` | Kuvakkeen mediatyyppi (esim. `image/png`, `image/jpeg`). Jos arvoa ei anneta, tyyppi tunnistetaan.      | `""`              |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | Kuvakkeen käyttötarkoitus (esim. `any`, `maskable`, `monochrome`).                                     | `""`              |

### Esimerkki {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### `@AppProfile.DefaultIcon`-ominaisuudet {#appprofiledefaulticon-properties}

`DefaultIcon`-annotaatio helpottaa sovelluskuvakkeiden määritystä luomalla peruskuvakkeesta useita erikokoisia versioita.
Se luo kuvakkeet tarkkuuksilla, joita laitteet tavallisesti pyytävät.

| **Ominaisuus** | **Tyyppi** | **Kuvaus**                                                                 | **Oletusarvo** |
| ------------ | -------- | ------------------------------------------------------------------------------- | ----------------- |
| `value`      | `String` | Peruskuvaketiedoston polku. Tämä voi olla absoluuttinen URL-osoite tai `ws://`-polku. | **Pakollinen**     |
| `sizes`      | `int[]`  | Taulukko luotavista kokovaihtoehdoista kokonaislukuina (esim. `{144, 192, 512}`). | `{144, 192, 512}` |

:::info Kuvaketiedostojen vaatimukset
Tämä määritys ei luo sovelluksen varsinaisia kuvaketiedostoja automaattisesti. Sen sijaan `@AppProfile.DefaultIcon`-annotaatio luo vastaavat [`@AppProfile.Icon`](#appprofileicon-properties)-merkinnät jokaiselle määritetylle koolle.

#### Jos käytät [webserver-protokollaa](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- Sinun on lisättävä peruskuvaketiedosto `icon.png` kansioon `static/icons`.
- Sinun on lisättävä myös erikokoiset kuvakkeet nimillä `icon-144x144.png`, `icon-192x192.png` ja `icon-512x512.png`.
- Nämä koot kattavat tarkkuudet, joita laitteet tavallisesti pyytävät.

#### Jos käytät [icons-protokollaa](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- Sinun on lisättävä peruskuvaketiedosto `icon.png` kansioon `/icons`.
- `icons`-päätepiste tarjoaa pyydettäessä dynaamisesti erikokoisia kuvakkeita.
:::

### `@AppProfile.Screenshot`-ominaisuudet {#appprofilescreenshot-properties}

Kuvakaappaukset näyttävät sovelluksen esikatselun asennusikkunoissa tai sovelluskaupoissa. `@AppProfile.Screenshot`-annotaatio tukee seuraavia ominaisuuksia:

| **Ominaisuus**                                                                              | **Tyyppi** | **Kuvaus**                                                                                                 | **Oletusarvo** |
| --------------------------------------------------------------------------------------------- | -------- | ----------------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                                         | `String` | Kuvakaappauksen polku. Tämä voi olla absoluuttinen URL-osoite tai `ws://`-polku.                            | **Pakollinen**     |
| `sizes`                                                                                       | `String` | Merkkijono, joka määrittää yhden tai useamman kuvakoon muodossa `LeveysxKorkeus` (esim. `1080x1920`).      | **Pakollinen**     |
| `type`                                                                                        | `String` | Kuvakaappauksen mediatyyppi (esim. `image/png`, `image/jpeg`). Jos arvoa ei anneta, tyyppi tunnistetaan.    | `""`              |
| `label`                                                                                       | `String` | Kuvakaappauksen kuvaava nimi.                                                                               | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | Kuvakaappauksen muoto (esim. `narrow`, `wide`).                                                             | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)      | `String` | Alusta, jolle kuvakaappaus on tarkoitettu (esim. `ios`, `android`).                                         | `""`              |

### Esimerkki {#example-1}

```java
@AppProfile.Screenshot(
  src = "ws://img/screenshots/s1.jpg",
  sizes = "1080x1920"
)
```

<VideoPlayer video="installable-apps/install-android" width={374} height={824} title="Install android" />
