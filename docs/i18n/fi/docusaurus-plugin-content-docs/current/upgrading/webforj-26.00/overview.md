---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
slug: /upgrading/webforj-26.00
pagination_next: null
sidebar_position: 1
_i18n_hash: 3b9827a67a81e207508d7db72a650b64
---
Tämä dokumentaatio toimii oppaana webforJ-sovellusten päivitykselle versioon 25.00–26.00. Tässä ovat muutokset, jotka on tarpeen tehdä nykyisille sovelluksille, jotta ne voivat jatkaa toimintaansa sujuvasti. Kuten aina, katso [GitHubin julkaisun yhteenveto](https://github.com/webforj/webforj/releases) saadaksesi kattavamman listan muutoksista julkaisujen välillä.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## POM-tiedoston muutokset {#pom-file-changes}

### Java 21 ja 25 {#java-21-and-25}

webforJ 25.12 on viimeinen versio, joka toimii Java 17:n kanssa. Alkaen versiosta webforJ 26.00 tarvitset Java-version, joka on joko Java 21 tai Java 25, riippuen asetuksistasi.

Asenna tarvittava Java-versio, kuten on lueteltu [esivaatimuksissa](/docs/introduction/prerequisites), ja päivitä sitten pom.xml-tiedostosi:

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### Maven-repositorion URL {#maven-repository-url}

Kohta, josta snapshot-artikkelit isännöidään, on muuttunut. Projektisi pom.xml-tiedostossa, oletko ladannut riippuvuutesi [Central Portalista](https://central.sonatype.com/)?

**Ennen:**
```xml
<repositories>
  <repository>
    <id>snapshots-repo</id>
    <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
    ....
  </repository>
</repositories>
```

**Jälkeen:**
```xml {3-5}
<repositories>
  <repository>
    <name>Central Portal Snapshots</name>
    <id>central-portal-snapshots</id>
    <url>https://central.sonatype.com/repository/maven-snapshots/</url>
    ....
  </repository>
</repositories>
```

### Spring Boot -päivitys {#spring-boot-upgrade}

webforJ 25.12 on viimeinen versio, joka käyttää Spring Boot 3.x:ää. Alkaen versiosta webforJ 26.00, projektisi on käytettävä Spring Boot 4.x:ää.

```xml {4}
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.5</version>
</parent>
```

:::tip Poista Tomcat-version ylitykset
Spring Boot 4.x:llä, Tomcat 11.x sisältyy nyt riippuvuudeksi, joten voit poistaa kaikki projektikohtaiset ylitykset Tomcat-version osalta.
:::

## Taulukko-API:n muutokset {#table-api-changes}

### `IconRenderer` merkkijono-pohjaiset konstruktorit {#iconrenderer-string-based-constructors}

Seuraavat merkkijono-pohjaiset konstruktorit on poistettu versiossa 26.00; käytä sen sijaan `IconDefinition`-pohjaisia konstruktoreita:

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition,  EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### Poistetut valintamenetelmät {#deprecated-selection-methods}

Alkaen versiosta webforJ 26.00, valitsemisen sijaan elementtejä `Table`-komponentissa indeksien perusteella, valitse elementtejä taulukosta käyttäen kohteen avainta. Voit käyttää `setKeyProvider()`-menetelmää, jotta voit antaa mukautettuja avaimia taulukon kohteille.

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)` tai `selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`, `deselectKey(keys)` tai `deselectAll()` |
| `getSelectedIndex()` | `getSelected()` tai `getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### Valinta-tapahtumat {#selection-events}

Vahvistaakseen edelleen siirtymistä tavasta valita elementtejä taulukossa, `TableItemSelectionChange` ei enää toteuta `SelectEvent`-tapahtumaa.

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## Tuetaan käyttökelvottomia Webswing-buuttivaihtoehtoja {#unsupported-webswing-bootstrap-options}

Seuraavat `WebswingOptions`-menetelmät on poistettu versiossa 26.00, koska niitä ei enää tueta Webswing API:ssa.

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

`PingParams`-luokka on myös poistettu. Ne, jotka käyttivät näitä menetelmiä tai `PingParams`-luokkaa, tulisi sen sijaan käyttää Webswingin hallintakonsolia vaihtoehtojen suoraan määrittämiseen.

## Suodattimet `Repository`-komponentille {#filters-for-repository}

`RetrievalCriteria` ja `RetrievalBuilder` -rajapinnat on poistettu webforJ 26.00:ssa. Sen sijaan, että käytät yleistä `Repository`-rajapintaa, käytä joko `RepositoryCriteria<T, F>`, `CollectionRepository` yksinkertaisia suodattimia varten, tai [`QueryableRepository`](/docs/advanced/repository/querying-data) monimutkaisempien suodatusmuotojen, lajittelun ja sivutuksen varten.

**Ennen:**
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
});
```

**Jälkeen:**
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
});
```

### Poistetut rekisteröintimenetelmät {#deprecated-repository-methods}

Käytä seuraavaa taulukkoa nähdäksesi poistetut rekisteröintimenetelmät ja mitä menetelmiä käyttää tulevaisuudessa.

| v25 | v26 |
|---|---|
| `setFilter(...)` | `setBaseFilter(...)` |
| `getFilter()` | `getBaseFilter()` |
| `getOrderBy()` | `getOrderCriteriaList()` |
| `setOrderBy(Comparator<T>)` | `getOrderCriteriaList().add(new OrderCriteria<>(keyExtractor, direction))` |
| `findOneBy(...)` | `findBy(...)` sitten `.findFirst()` |
| `findBy(RetrievalCriteria<T>)` | `findBy(RepositoryCriteria<T, F>)` |
| `size(RetrievalCriteria<T>)` | `size(RepositoryCriteria<T, F>)` |
| `getIndex(T)` | `find(key)` tai `findBy(criteria)` |
| `findByIndex(int)` | `find(key)` tai `findBy(criteria)` |

## `WebforjBBjBridge`-poistaminen {#removal-of-webforjbbjbridge}

Alkaen webforJ 25.11:stä, WebforjBBjBridge ja kaikki sen API:t on poistettu. Sen sijaan, että käyttäisit siltaa, webforJ käyttää suoraa Java API:a kommunikoidakseen ja päästäkseen käsiksi kaikkiin tarvittaviin BBj API:hin.

## Suunnittelujärjestelmän muutokset (DWC 26) {#design-system-changes-dwc-26}

webforJ 26.00 julkaisee version 26 DWC-suunnittelujärjestelmästä. Päivitys on vaiheittainen eikä täysin uudelleenkirjoitettu: suurin osa v25 CSS-muuttujista on edelleen käytettävissä, julkinen token-API säilyy ja olemassa olevat räätälöinnit toimivat edelleen ilman muutoksia.

Tässä osiossa luetellaan rikkojaavat muutokset, joihin saatat joutua reagoimaan. Kattavan yleiskuvan, mukaan lukien miltä uusi värimoottori näyttää, kuinka `--dwc-dark-mode` etenee, miksi repeämät on poistettu ja alueittaiset mekanismit, katso [DWC 26 -suunnittelujärjestelmä](/docs/upgrading/webforj-26.00/design-system).

### Nopea arvio {#design-system-quick-verdict}

| Tilanne | Mitä odottaa |
|---|---|
| Käyttää oletustyylitystä | Visuaalinen päivitys. Oletuspaletin värit on säädetty (ensisijainen siirtyminen `h: 211 / s: 100%` → `h: 223 / s: 91%`), varjot näyttävät kerrostetummilta ja komponentit tuntuvat pyöreämmiltä. Ei tarvitse koodimuutosta. |
| Korvaukset `--dwc-color-{name}-h` ja `-s` | Toimii edelleen. HSL-siemenpolku on säilytetty. |
| Korvaukset yksittäiset paletti-askeleet (esim. `--dwc-color-primary-40`) | Askelnumerot saattavat johtaa eri väreihin. Katso [Väripaletin mekanismit](/docs/upgrading/webforj-26.00/design-system#the-color-system). |
| Nojautuu `--dwc-color-{name}-c` | Poista. Vaalean/tumman tekstin vaihto lasketaan nyt automaattisesti per sävy. |
| Viittaa nimettyihin fonttikokotokenihin (`--dwc-font-size-m`, `-l`, jne.) | Skaala on siirtynyt alas yhteen ämpäriin. `m` on nyt `14px` entisen `16px` sijaan. Katso [Typografia](#design-system-typography). |
| Käyttää `--dwc-font-weight-semibold` saadakseen `500`-painon | `semibold` on nyt `600`. Vaihda uuteen `--dwc-font-weight-medium` saadaksesi `500`. |
| Varaa tilaa kohdistettujen elementtien ympärille `--dwc-focus-ring-width` | Vihreä on nyt väli. Lisää `--dwc-focus-ring-gap`. Katso [Keskittymissormus](#design-system-focus-ring). |
| Räätälöi painikkeen hover/ripple-efektejä | Repeämät on poistettu. Paina palautetta on nyt pieni koon vähennys. |

### `--dwc-color-{name}-c` on poistettu {#design-system-c-removed}

Jos sinulla on joitakin `--dwc-color-{name}-c`-korvauksia, voit poistaa ne, niillä ei ole vaikutusta. Vaalean/tumman tekstin vaihto lasketaan nyt automaattisesti per sävy.

### `--dwc-color-{name}-alt` merkitys muuttui {#design-system-alt-changed}

| Token | v25 | v26 |
|---|---|---|
| `--dwc-color-{name}-alt` | Palettiaskel `95` (lähes valkoinen tausta) | Siemen 12% läpikuultavuudella (läpinäkyvä sävy) |

Jos käytit `-alt` osana kiinteää lähes valkoista taustaa, se luetaan nyt läpinäkyvänä sävynä. Valitse erityinen askel (`--dwc-color-{name}-95`) tai suunnittele läpinäkyvän merkityksen ympärille.

### `--dwc-border-color-{name}` merkitys muuttui {#design-system-border-color-changed}

| Token | v25 | v26 |
|---|---|---|
| `--dwc-border-color-{name}` | Asetettu per muunnelma `var(--dwc-color-{name})` (tiivistetty sävy) | Lasketaan generaattorissa: tilannekohtaisesti vaaleampi sävy siemenestä |

Jos CSS:si lukee `--dwc-border-color-primary` odottaen tiivistettyä ensisijaista väriä, visuaalinen on nyt hienovarainen erotusväri sen sijaan. Jos haluat erityisesti tiivistetyn ilmeen, vaihda suoraan `--dwc-color-primary`-tokeniin.

### `--dwc-shadow-color` formaatti muuttui {#design-system-shadow-color-changed}

|  | v25 | v26 |
|---|---|---|
| `--dwc-shadow-color` | HSL-trio (`h, s%, l%`) | Täysi OKLCH-väri |

Jos CSS:si käyttää vanhaa triplet-muotoa kuten `hsla(var(--dwc-shadow-color), 0.07)`, vaihda täysimuotoiseen varjosymboliin (`var(--dwc-shadow-m)`) tai kirjoita se uudelleen muodossa `oklch(from var(--dwc-shadow-color) l c h / 0.07)`.

### Typografia {#design-system-typography}

Fonttiskaala on säädetty, joten ämpärin nimet siirtyivät alas yhdestä askeleesta:

| Token | v25 | v26 |
|---|---|---|
| `--dwc-font-size-3xs` | `10px` | `10px` |
| `--dwc-font-size-2xs` | `12px` | `11px` |
| `--dwc-font-size-xs`  | `13px` | `12px` |
| `--dwc-font-size-s`   | `14px` | `13px` |
| `--dwc-font-size-m`   | `16px` | `14px` |
| `--dwc-font-size-l`   | `18px` | `16px` |
| `--dwc-font-size-xl`  | `22px` | `20px` |
| `--dwc-font-size-2xl` | `28px` | `26px` |
| `--dwc-font-size-3xl` | `36px` | `34px` |

Oletus `--dwc-font-size` ratkaisee edelleen **14px**, se vain saadaan aikaiseksi `--dwc-font-size-m` (v26) kautta sen sijaan, että käytetään `--dwc-font-size-s` (v25). Jos CSS:si viittaa fonttikokotokenien nimeen (esim. `font-size: var(--dwc-font-size-l)`), näkyvä tulos on pienempi v26:ssa. Nosta yksi ämpäri, jotta säilyttää v25-koko.

Fonttipainot saivat kolme tokenia (`thin`, `medium`, `black`) ja yksi olemassa oleva token siirtyi:

| Token | v25 | v26 |
|---|---|---|
| `--dwc-font-weight-semibold` | `500` | `600` |
| `--dwc-font-weight-medium`   | (ei ollut olemassa) | `500` |

Jos käytit `--dwc-font-weight-semibold` saadaksesi 500-painon tekstiä, vaihda `--dwc-font-weight-medium`-tokeniin.

### Reunus säde {#design-system-border-radius}

|  | v25 | v26 |
|---|---|---|
| Yksikkö | `em` (skaalautuu vanhemman fonttikoon mukaan) | `rem` (skaalautuu juurifonttikoon mukaan) |
| Oletus `--dwc-border-radius` | `--dwc-border-radius-s` (`4px`) | `--dwc-border-radius-seed` (`8px`) |
| Saatavilla askeleet | jopa `2xl` | lisää `3xl`, `4xl` |

Komponentit tuntuvat pyöreämmiltä suoraan laatikosta. Jos komponentti, joka on sijoitettu suuremman tekstin sisään, perii isomman säteen `em`:n kautta, tämä skaala ei enää toimi, säteet ovat nyt sidottuja juureen. Jos haluat v25 oletuskoko takaisin, puolita siemen:

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px, puolittaa koko mittakaavan */
}
```

### Keskittymissormus {#design-system-focus-ring}

Keskittymissormus käyttää nyt kaksinkertaista renkaan mallia: pieni pinnan väri tavara, sitten värillinen rengas.

| Muuttuja | v25 | v26 |
|---|---|---|
| `--dwc-focus-ring-width` | `3px` | `2px` |
| `--dwc-focus-ring-a`     | `0.4` | `0.75` |
| `--dwc-focus-ring-gap`   | (ei ollut) | `2px` |
| `--dwc-focus-ring-l`     | `45%` | (poistettu, vaaleus lasketaan tilannekohtaisesti) |

Jos varaat tilaa kohdistettuille elementeille `padding: var(--dwc-focus-ring-width)`, lisää väli tuohon paddingiin, jotta uusi rengas mahtuu:

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

### Repeämät poistettu {#design-system-ripples-removed}

Materiaalimaiset repeäntäefektit eivät enää ole käytössä yhdessäkään DWC-komponentissa. Uusi palaute kaikille napsautettaville elementeille on pieni koon vähennys:

```css
--dwc-scale-press: 0.97;      /* Standardi 3% kutistus */
--dwc-scale-press-deep: 0.93; /* Syvempi 7% kutistus painikkeille */
```

`ripple` SCSS-mixin ja `--dwc-ripple-color` CSS-muuttuja ovat edelleen olemassa rakenteessa, mutta niitä ei tuoda oletusarvoisesti. Jos omat komponentit optimoitivat mixin, vaihda painoskaalatokeihin vastaamaan uutta tunnetta.

### Siirtymäajat tasapainotettu {#design-system-transitions}

| Muuttuja | v25 | v26 |
|---|---|---|
| `--dwc-transition-slow`   | `500ms` | `300ms` |
| `--dwc-transition-medium` | `250ms` | `250ms` |
| `--dwc-transition-fast`   | `150ms` | `150ms` |
| `--dwc-transition-x-fast` | `50ms`  | `100ms` |

Jos riippuu tietyistä ajoista, ylikirjoita ne :root:ssa.

### Käytännöllinen päivityslista {#design-system-checklist}

1. Etsi `--dwc-color-*-c` ja poista nämä ilmoitukset.
2. Etsi `hsla(var(--dwc-shadow-color)` ja vaihda varjosymbooliin (`var(--dwc-shadow-m)`) tai kirjoita se uudelleen muodossa `oklch(from ...)`.
3. Etsi suorat palettiaskelviittaukset (`--dwc-color-{name}-{number}`). Jos ne syöttävät tumma-moodin erityistä tyyliä, vaihda vaihtelua tokenit (`--dwc-color-{name}`, `-dark`, `-light`).
4. Etsi nimettyjä fonttikoko viittauksia (`--dwc-font-size-m`, `-l`, jne.). Jos haluat v25 koon, nosta yksi ämpäri.
5. Etsi `--dwc-font-weight-semibold`. Jos halusit `500`, vaihda `--dwc-font-weight-medium`.
6. Jos varaat tilaa kohdistettuille elementeille `--dwc-focus-ring-width`, lisää `--dwc-focus-ring-gap` paddingiin.
7. Avaa sovellus, napsauttele ympäri. Useimmat sovellukset eivät tarvitse muuta.
