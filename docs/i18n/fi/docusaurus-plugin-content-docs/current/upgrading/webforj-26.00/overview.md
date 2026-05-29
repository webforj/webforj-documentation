---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
slug: /upgrading/webforj-26.00
pagination_next: null
sidebar_class_name: new-content
sidebar_position: 1
_i18n_hash: e62ee79be86c51d62fe19d10af89cc1b
---
Tämä dokumentaatio toimii oppaana webforJ-sovellusten päivittämisessä versioon 26.00. 
Tässä ovat tarvittavat muutokset, jotta olemassa olevat sovellukset voivat jatkaa sujuvaa toimintaa. 
Kuten aina, katso [GitHubin julkaisun yleiskatsaus](https://github.com/webforj/webforj/releases) saadaksesi kattavamman luettelon muutoksista versioiden välillä.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## POM-tiedoston muutokset {#pom-file-changes}

### Java 21 ja 25 {#java-21-and-25}

webforJ 25.12 on viimeinen versio, joka toimii Java 17:n kanssa. 
Alkaen webforJ 26.00:sta, tarvitset Java-version, joka on joko Java 21 tai Java 25, riippuen asennuksestasi.

Asenna tarvittu Java-versio, kuten on lueteltu [esivaatimuksissa](/docs/introduction/prerequisites), ja päivitä sitten pom.xml-tiedostosi:

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### Maven-repositorion URL {#maven-repository-url}

Sijainti, jossa tilannekuvat isännöidään, on muuttunut. Oletko ladannut riippuvuutesi projektisi pom.xml-tiedostoon [Central Portalista](https://central.sonatype.com/)?

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

webforJ 25.12 on viimeinen versio, joka käyttää Spring Boot 3.x:iä. 
Alkaen webforJ 26.00:sta, projektisi on käytettävä Spring Boot 4.x:ää.

```xml {4}
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.5</version>
</parent>
```

:::tip Poista Tomcat-version ohi kirjoitukset
Spring Boot 4.x:n myötä Tomcat 11.x on nyt mukana riippuvuutena, joten voit poistaa kaikki projektiin liittyvät Tomcat-version ohi kirjoitukset.
:::

## Taulukon API-muutokset {#table-api-changes}

### `IconRenderer`-merkkipohjaiset konstruktorit {#iconrenderer-string-based-constructors}

Seuraavat merkkipohjaiset konstruktorit on poistettu versiossa 26.00; käytä sen sijaan `IconDefinition`-pohjaisia konstruktoreita:

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition,  EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### Poistetut valintamenetelmät {#deprecated-selection-methods}

Alkaen webforJ 26.00:sta, valitsemisen sijaan `Table`:ssa indeksien perusteella, valitse kohteita taulukosta käyttäen kohteen avainta. Voit käyttää `setKeyProvider()`-metodia mukautettujen avainten tarjoamiseen taulukon kohteille.

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)` tai `selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`, `deselectKey(keys)` tai `deselectAll()` |
| `getSelectedIndex()` | `getSelected()` tai `getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### Valintatapahtumat {#selection-events}

Korostaaksesi entistä enemmän siirtymistä kohteiden valintaan `Table`:ssa, `TableItemSelectionChange` ei enää toteuta `SelectEvent`-tapahtumaa.

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## Tuettu Webswingin käynnistysvaihtoehdot {#unsupported-webswing-bootstrap-options}

Seuraavat `WebswingOptions`-metodit on poistettu versiossa 26.00, koska ne eivät enää ole tuettuja Webswing API:ssa.

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

`PingParams`-luokka on myös poistettu käytöstä. Ne, jotka käyttivät näitä metodeja tai `PingParams`-luokkaa, tulisi sen sijaan käyttää Webswingin Hallintopaneelia asetusten suoraan muokkaamiseen.

## Suodattimet `Repository` {#filters-for-repository}

`RetrievalCriteria` ja `RetrievalBuilder` -rajapinnat on poistettu webforJ 26.00:ssa. Sen sijaan, että käytetään geneeristä `Repository`-rajapintaa, käytä joko `RepositoryCriteria<T, F>`, `CollectionRepository` yksinkertaisille suodattimille, tai [`QueryableRepository`](/docs/advanced/repository/querying-data) kehittyneille suodatusmenetelmille, lajittelulle ja sivutukselle.

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

### Poistetut repositorion metodit {#deprecated-repository-methods}

Käytä seuraavaa taulukkoa nähdäksesi poistettuja repositorion menetelmiä ja mitä metodeja tulee käyttää jatkossa.

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

## `WebforjBBjBridge`-poisto {#removal-of-webforjbbjbridge}

Alkaen webforJ 25.11:stä, WebforjBBjBridge ja kaikki sen API:t on poistettu. Sen sijaan, että käytetään siltaa, webforJ käyttää suoraa Java API:a kommunikoidakseen ja päästääkseen käsiksi kaikkiin tarvittaviin BBj API:hin.

## Suunnittelujärjestelmän muutokset (DWC 26) {#design-system-changes-dwc-26}

webforJ 26.00 toimitetaan DWC-suunnittelujärjestelmän version 26 kanssa. Päivitys on vähittäinen sen sijaan, että se olisi täysi kirjoitus; useimmat v25 CSS-muuttujat ovat edelleen saatavilla, julkinen token API säilyy, ja olemassa olevat mukautukset toimivat edelleen ilman muutoksia.

Tässä osiossa luetellaan puuttuvat muutokset, joiden kanssa saatat joutua toimimaan. Kattavan yleiskuvan, joka sisältää sen, miltä uusi värimoottori näyttää, miten `--dwc-dark-mode` leviää, miksi aallot on poistettu ja aluekohtaiset mekanismit, katso [DWC 26 suunnittelujärjestelmä](/docs/upgrading/webforj-26.00/design-system).

### Nopeita päätelmiä {#design-system-quick-verdict}

| Tapahtuma | Mitä odottaa |
|---|---|
| Käyttää oletustyylit | Visuaalinen virkistys. Oletuspalletin sävyt on säädetty (pääkohde siirtyi `h: 211 / s: 100%` -> `h: 223 / s: 91%`), varjot näyttävät kerroksellisemmilta, ja komponentit tuntuvat pyöreämmiltä. Ei koodimuutosta tarvita. |
| Ylikirjoittaa `--dwc-color-{name}-h` ja `-s` | Toimii edelleen. HSL-alkutielle on säilytetty. |
| Ylikirjoittaa yksittäiset palettiaskeleet (esim. `--dwc-color-primary-40`) | Askel numerot voivat liittyä eri väreihin. Katso [Väripalettimekaniikat](/docs/upgrading/webforj-26.00/design-system#the-color-system). |
| Perustuu `--dwc-color-{name}-c` | Poista. Vaalea/tumma tekstiripustus lasketaan nyt automaattisesti jokaisen sävyn mukaan. |
| Viittaa nimettyihin fonttikoon tokeneihin (`--dwc-font-size-m`, `-l` jne.) | Skaala siirtyi alas yhden ämpärin verran. `m` on nyt `14px` sen sijaan, että se olisi `16px`. Katso [Typografia](#design-system-typography). |
| Käyttää `--dwc-font-weight-semibold` saadakseen `500`-punnituksen | `semibold` on nyt `600`. Vaihda uuteen `--dwc-font-weight-medium`, jotta saat `500`. |
| Varaa tilaa keskipisteiden ympärille `--dwc-focus-ring-width` | Rengas nyt on rako. Lisää `--dwc-focus-ring-gap`. Katso [Keskitien rengas](#design-system-focus-ring). |
| Mukautetut painikkeen suurenekomės / aallotusefektit | Aallot on poistettu. Näppäilypalautteena on nyt pieni laajennus. |

### `--dwc-color-{name}-c` on poistettu {#design-system-c-removed}

Jos sinulla on mitään `--dwc-color-{name}-c` -ylikirjoituksia, voit poistaa ne, niillä ei ole vaikutusta. Vaalea/tumma tekstiripustus lasketaan nyt automaattisesti jokaisen sävyn mukaan.

### `--dwc-color-{name}-alt` -merkitys on muuttunut {#design-system-alt-changed}

| Token | v25 | v26 |
|---|---|---|
| `--dwc-color-{name}-alt` | Palettiaste `95` (lähes valkoinen tausta) | Siemen 12% läpinäkyvyydellä (läpinäkyvä sävy) |

Jos käytit `-alt` -sovellusta vahvana lähes valkoisena taustana, se lukee nyt läpinäkyvänä sävytettynä päällekkäisyytenä. Valitse jokin tietty askel (`--dwc-color-{name}-95`) tai suunnittele läpinäkyvän merkityksen ympärille.

### `--dwc-border-color-{name}` -merkitys on muuttunut {#design-system-border-color-changed}

| Token | v25 | v26 |
|---|---|---|
| `--dwc-border-color-{name}` | Määritelty per variaatioina `var(--dwc-color-{name})` (tämä on kylläinen sävy) | Lasketaan generaattorissa: moodista riippuvainen kevennetty sävy siemenestä |

Jos CSS:si lukee `--dwc-border-color-primary` ja odotetaan kylläistä pääväriä, visuaali on nyt hienovarainen erottelusävy. Jos haluat nimenomaisesti kylläisen ilmeen, vaihda suoraan `--dwc-color-primary`.

### `--dwc-shadow-color` -muoto on muuttunut {#design-system-shadow-color-changed}

|  | v25 | v26 |
|---|---|---|
| `--dwc-shadow-color` | HSL-tripletti (`h, s%, l%`) | Täysi OKLCH-väri |

Jos CSS:si käyttää perinteistä kolmonenmuotoa, kuten `hsla(var(--dwc-shadow-color), 0.07)`, vaihda täysimittaiseen varjotokeniin (`var(--dwc-shadow-m)`) tai kirjoita uudelleen `oklch(from var(--dwc-shadow-color) l c h / 0.07)`.

### Typografia {#design-system-typography}

Fonttikokoja on säätetty siten, että ämpärin nimet ovat siirtyneet alas yhdellä askel tasolla:

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

Oletus `--dwc-font-size` edelleen nousee **14px**, se vain saavutetaan `--dwc-font-size-m`:n (v26) kautta sen sijaan, että se olisi `--dwc-font-size-s`:n (v25) kautta. Jos CSS:si viittaa fonttikoko-otsikoihin nimellä (esim. `font-size: var(--dwc-font-size-l)`), näkyvä tulos on pienempi versiossa v26. Nosta yksi ämpäri säilyttääksesi v25-koko.

Fonttipainot saivat kolme tokenia (`thin`, `medium`, `black`) ja yksi olemassa oleva token siirtyi:

| Token | v25 | v26 |
|---|---|---|
| `--dwc-font-weight-semibold` | `500` | `600` |
| `--dwc-font-weight-medium`   | (ei ollut olemassa) | `500` |

Jos käytit `--dwc-font-weight-semibold` saadaksesi 500-painon tekstin, vaihda `--dwc-font-weight-medium` -tokeniin.

### Reuna-säteet {#design-system-border-radius}

|  | v25 | v26 |
|---|---|---|
| Yksikkö | `em` (skaalautuu vanhemman fonttikoon mukaan) | `rem` (skaalautuu juurifonttikoon mukaan) |
| Oletus `--dwc-border-radius` | `--dwc-border-radius-s` (`4px`) | `--dwc-border-radius-seed` (`8px`) |
| Saatavilla olevat askeleet | jopa `2xl` | lisää `3xl`, `4xl` |

Komponentit tuntuvat pyöreämmiltä suoraan ulos laatikosta. Jos komponentti, joka on suuremman tekstin sisällä, perii aiemmin suuremman säteen `em`-kunnan kautta, tätä skaalausta ei enää tapahdu, säteet on nyt kiinnitetty juurille. Jos haluat takaisin v25 oletuskoko, puolita siemen:

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px, puolittaa koko skaala */
}
```

### Keskitien rengas {#design-system-focus-ring}

Keskitien rengas käyttää nyt kaksoisrenkaan muotoa: pieni pinnanvärinen rako, sitten värirengas.

| Muuttuja | v25 | v26 |
|---|---|---|
| `--dwc-focus-ring-width` | `3px` | `2px` |
| `--dwc-focus-ring-a`     | `0.4` | `0.75` |
| `--dwc-focus-ring-gap`   | (ei mitään) | `2px` |
| `--dwc-focus-ring-l`     | `45%` | (poistettu, vaaleus lasketaan moodin mukaan) |

Jos varaat tilaa keskitie-elementtien ympärille käyttämällä `padding: var(--dwc-focus-ring-width)`, lisää väli tuohon pehmustukseen, jotta uusi rengas on tilassa renderoitavaksi:

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

### Aallot poistettu {#design-system-ripples-removed}

Materiaalimaisen aallon efektit on poistettu kaikista DWC-komponenteista. Uusi palautus jokaiselle napsautettavalle elementille on pieni alas.

```css
--dwc-scale-press: 0.97;      /* Normaalit 3% kutistus */
--dwc-scale-press-deep: 0.93; /* Syvä 7% kutistus painikkeille */
```

`ripple` SCSS-mixin ja `--dwc-ripple-color` CSS-muuttuja ovat edelleen olemassa rakentamisessa, mutta mitään tuodaan niistä oletusarvoisesti. Jos omat komponenttisi käyttivät mixiniä, siirry uuden tuntemuksen mukaisiin painoskaala-tokeneihin.

### Siirtymärakenteet tasapainotettu {#design-system-transitions}

| Muuttuja | v25 | v26 |
|---|---|---|
| `--dwc-transition-slow`   | `500ms` | `300ms` |
| `--dwc-transition-medium` | `250ms` | `250ms` |
| `--dwc-transition-fast`   | `150ms` | `150ms` |
| `--dwc-transition-x-fast` | `50ms`  | `100ms` |

Jos riippuu tietyistä kestoista, ylikirjoita se `:root`-tasolla.

### Käytännön päivityslista {#design-system-checklist}

1. Etsi `--dwc-color-*-c` ja poista nuo ilmoitukset.
2. Etsi `hsla(var(--dwc-shadow-color)` ja vaihda pois varjotokenista (`var(--dwc-shadow-m)`) tai kirjoita se uudelleen `oklch(from ...)`.
3. Etsi suoria palettiaskeleen viittauksia (`--dwc-color-{name}-{number}`). Jos jotkin syöttävät tumman mukautetun tyylin, vaihda variaatiotokeniksi (`--dwc-color-{name}`, `-dark`, `-light`).
4. Etsi nimettyjen fonttikoon viittauksia (`--dwc-font-size-m`, `-l` jne.). Jos haluat v25-kokoa, nosta yksi ämpäri.
5. Etsi `--dwc-font-weight-semibold`. Jos halusit `500`, vaihda `--dwc-font-weight-medium`.
6. Jos varaat tilaa keskitieuritus-elementtien ympärille käyttäen `--dwc-focus-ring-width`, lisää `--dwc-focus-ring-gap` pehmustukseen.
7. Avaa sovellus, klikkaa ympäri. Useimmat sovellukset eivät tarvitse muuta.
