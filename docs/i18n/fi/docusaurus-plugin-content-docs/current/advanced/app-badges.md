---
sidebar_position: 38
sidebar_class_name: new-content
title: App badges
description: >-
  Paint notification badges onto the operating system app icon and the browser
  tab favicon.
_i18n_hash: ff5a388432db849aa6d7b7ac1f48aa89
---
# Sovelluksen merkinnät <DocChip chip='since' label='26.01' />

webforJ tarjoaa kaksi täydentävää merkki-APIa. <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>`.setBadge` maalaa käyttöjärjestelmän sovellusikonin, joka näkyy dokeissa, tehtäväpalkissa tai aloitusnäytöllä. <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink>`.setIconBadge` maalaa asiakirjan faviconin, joka näkyy selaimen välilehtirivillä. Ne kohdistavat eri pinnoille ja niillä on erilaiset vaatimukset, joten useimmat sovellukset kutsuvat molempia laajimman näkyvyyden saavuttamiseksi.

<!-- INTRO_END -->

## Sovelluksen kuvakebadge {#app-icon-badge}

`App.setBadge` renderoi merkin sovelluksen käytössä olevan kuvakkeen päälle: macOS-dokissa, Windowsin tehtäväpalkissa, Chrome OS:ssä tai Androidin aloitusnäytöllä.

![Sovelluksen kuvakebadge macOS-dokissa](/img/app-badges/app-badge.png)

### Ehdot {#app-prerequisites}

Merkki on näkyvissä vain, kun kaikki seuraavat asiat ovat totta:

- Selain tukee merkkien piirtämistä sovelluskuvakkeille.
- Sivusto tarjoillaan turvallisesta kontekstista (HTTPS tai `http://localhost` kehityksen aikana). Tavalliset HTTP-alkuperät hylkäävät kutsun.
- Sovellus on asennettu laitteelle. Asennusprosessi vaihtelee selaimen mukaan: Chromium-selaimet tarjoavat asennuskehotteen kaikille sivuille, joilla on manifesti, Safari macOS:ssä käyttää **Tiedosto → Lisää Dockiin**, ja Safari iOS:ssä käyttää **Jaa → Lisää aloitusnäytölle**.

Jotta sovellus olisi asennettavissa Spring Bootin tai itsenäisen webforJ-palvelimen alla, lisää <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>-alamalliin <JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" code='true'>AppProfile</JavadocLink>. Annotaatioprosessori luo manifestin, sovelluskuvaketunnisteet ja metatiedot, joita selain tarvitsee tarjotakseen asennuskehotteen.

```java
@AppProfile(name = "Inbox", shortName = "Inbox")
public class Application extends App {}
```

Katso [Asennettavat sovellukset](../configuration/installable-apps) -sivulta täydellinen luettelo `@AppProfile`-jäsenistä, kuvakkeen koosta ja alustakohtaisista ohjeista asennusprosessissa.

### Selaintuki {#app-browser-support}

Asennuksen jälkeen merkin renderointi riippuu selaimesta. Asennustuki käsitellään [Asennettavat sovellukset](../configuration/installable-apps#browser-support) -sivulla.

| Selaimen nimi | Merkki asennuksen jälkeen |
| --- | --- |
| Chrome, Edge, Opera ja muut Chromium-selaimet (työpöytä ja Android) | Kyllä |
| Safari macOS Sononassa (Safari 17) ja myöhemmin | Kyllä |
| Safari iOS 16.4 ja myöhemmin | Kyllä |
| Firefox (kaikki alustat) | Ei. Kutsu palauttaa ilman renderointia. |

### Merkin asettaminen ja tyhjentäminen {#app-setting-clearing}

Anna positiivinen kokonaisluku näyttääksesi numeerisen merkin. Anna `null` tai `0` tyhjentääksesi sen. Kutsu ilman argumentteja näyttääksesi lipun indikaattorin (pieni piste, tarkka visuaalinen ilme on alustoittain määritetty).

```java
App.setBadge(5);     // numeerinen merkki
App.setBadge();      // lipun indikaattori ilman numeroa
App.setBadge(0);     // tyhjennä
App.setBadge(null);  // tyhjennä
```

`App.setBadge` palauttaa välittömästi. Selain kirjoittaa merkin käyttöjärjestelmän pintaan asynkronisesti, eikä muutosta raportoida takaisin sovellukseen.

## Selaimen välilehden kuvakebadge {#browser-tab-icon-badge}

`Page.setIconBadge` maalaa laskurin asiakirjan faviconin päälle. Se toimii missä tahansa välilehdessä ilman asennusta eikä vaadi manifestia. Merkki on näkyvissä selaimen välilehtirivillä ja muissa kohteissa, jotka renderöivät faviconin, kuten kirjanmerkit tai äskettäin katsotut sivut.

![Selaimen välilehden favicon numeerisella merkillä](/img/app-badges/icon-badge.png)

### Merkin asettaminen ja tyhjentäminen {#tab-setting-clearing}

```java
Page page = Page.getCurrent();

page.setIconBadge(5);     // numeerinen merkki
page.setIconBadge();      // lipun indikaattori ilman numeroa
page.setIconBadge(0);     // tyhjennä
page.setIconBadge(null);  // tyhjennä
```

Merkin tyhjentäminen palauttaa alkuperäisen faviconin.

:::info Käytettäessä `BBjServices`
Kun sovellus tarjoillaan `BBjServices`:llä, favicon on **Pikanäppäimen kuva**, joka on määritetty sovellukselle Enterprise Managerissa. Merkki maalataan mille tahansa kuvakkeelle, jonka Enterprise Manager tarjoaa. Jos pikakuvaketta ei ole määritetty, `Page.setIconBadge` ei overlayaa faviconia ja ei tee mitään hiljaa.
:::

### Merkin tyylittely {#styling-the-badge}

Anna <JavadocLink type="foundation" location="com/webforj/IconBadgeOptions" code='true'>IconBadgeOptions</JavadocLink> säätääksesi väriä, muotoa ja kokoa:

```java
IconBadgeOptions options = new IconBadgeOptions()
    .setColor(new Color(0x2e, 0x7d, 0x32))
    .setShape(IconBadgeOptions.Shape.SQUARE)
    .setSize(1.25);

Page.getCurrent().setIconBadge(5, options);
```

Options-objekti on arvon säilyttäjä. Kaikki asettajat palauttavat `this`, joten kutsuja voidaan ketjuttaa.

| Vaihtoehto | Tyyppi | Oletus | Huomautukset |
|---|---|---|---|
| `color` | `java.awt.Color` | `#e53935` | Merkin taustaväri. Tekstin väri johdetaan automaattisesti kontrastista, jotta numerot pysyvät luettavina valitussa värissä. |
| `shape` | `Shape` | `CIRCLE` | `CIRCLE` tai `SQUARE`. |
| `size` | `double` | `1.0` | Suhde. `0.5` on puoli oletusläpimittaa; `1.5` on 50% suurempi. Merkki rajataan sopimaan favicon-kankaan sisälle. |

### Selaimen varoitus {#browser-caveat}

Safari ei päivitä faviconia ensimmäisen sivun lataamisen jälkeen. Kutsut `Page.setIconBadge` valmistuvat ilman virhettä, mutta Safari jatkaa alkuperäisen kuvakkeen näyttämistä. Käytä `Page.setTitle` -metodia myös lisätäksesi laskurin asiakirjan otsikkoon, jos tarvitset näkyvän vihjeen Safarissa.

```java
int unread = 5;
Page page = Page.getCurrent();
page.setIconBadge(unread);
page.setTitle("(" + unread + ") Inbox");
```

## Valinta kahden välillä {#choosing-between-the-two}

| Pinta | API | Vaatii asennuksen | Näkyy Safarissa |
|---|---|---|---|
| Käyttöjärjestelmän sovelluskuvake | `App.setBadge` | Kyllä | Kyllä (macOS Sonoma / iOS 16.4 ja myöhemmin) |
| Selaimen välilehden favicon | `Page.setIconBadge` | Ei | Ei. Kutsu valmistuu ilman virhettä, mutta välilehtirivi ei päivity. |

Useimmat sovellukset kutsuvat molempia, jotta merkki olisi näkyvissä riippumatta siitä, onko käyttäjä asennetussa ikkunassa vai tavallisessa selaimen välilehdessä.
