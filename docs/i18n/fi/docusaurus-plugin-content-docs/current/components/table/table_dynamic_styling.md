---
sidebar_position: 21
title: Dynamic Styling
slug: styling
description: >-
  Apply data-driven CSS to Table rows and cells using setRowPartProvider and
  setCellPartProvider with shadow part selectors.
_i18n_hash: 28911597e1e885b0531bb27aa3c8e1a7
---
<!-- vale off -->
# Dynaaminen tyylitys <DocChip chip='since' label='25.00' />
<!-- vale on -->

WebforJ 25:ssä ja korkeammissa versioissa on mahdollista muokata yksittäisten rivien ja solujen tyyliä taulukossa mukautettujen osan nimien avulla. Nämä nimet voidaan määrittää dynaamisesti sovelluksesi logiikan mukaan, mikä antaa sinulle hienovaraisen hallinnan taulukon ulkoasusta.

## Rivin tyyli {#row-styling}

`setRowPartProvider()`-metodi määrää osan nimet kokoille riveille niiden sisältämän tietoelementin mukaan. Tämä mahdollistaa kokonaisten rivien korostamisen, jotka täyttävät tietyt ehdot—esimerkiksi vuorottelevat taustavärit parillisille riveille.

Nämä tyyli nimet voidaan kohdistaa `::part()`-valitsimella CSS:ssäsi.

:::tip Varjo-osat
`::part()`-valitsin on erityinen CSS-ominaisuus, joka mahdollistaa komponentin varjo-DOM:in sisällä olevien elementtien tyylittelyn—niin kauan kuin nämä elementit paljastavat `part`-attribuutin. Tämä on erityisen hyödyllistä webforJ-komponenttien sisäisten osien, kuten rivien tai solujen tyylittelyssä taulukossa.

Lisätietoja varjo-osien toiminnasta sekä niiden määrittelystä ja kohdistamisesta löydät [Tyylittely](../../styling/shadow-parts) osiosta.
:::


<ComponentDemo
path='/webforj/tablerowstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableRowStylingView.java',
  'src/main/frontend/css/table/table-row-styling-view.css',
]}
height='300px'
/>

## Solun tyyli {#cell-styling}

`setCellPartProvider()`-metodi muokkaa yksittäisten solujen tyyliä sekä tietoelementin että sen kuuluvan sarakkeen perusteella. Tämä on erityisen sopiva korostamaan tiettyjä arvoja, kuten ikien esiin tuomista, jotka ylittävät tietyn rajan tai virheellisiä merkintöjä.

Kuten riviosat, soluosat määritellään nimellä ja niitä kohdistetaan `::part()`-valitsimella.

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/frontend/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## Reagointi tietopäivityksiin {#reacting-to-data-updates}

Jos sovelluksesi muokkaa tietoja ohjelmallisesti, kuten päivittämällä käyttäjän ikää, taulukko arvioi automaattisesti uudelleen ja soveltaa uudelleen kaikkia siihen liittyviä rivin tai solun tyylejä, kun päivitetty elementti on sitoutettu varastoon.

Tässä demossa solut Ikä-sarakkeessa on tyylitelty rajan mukaan: yli 30-vuotiaat näyttävät vihreiltä, kun taas 30-vuotiaat ja sitä nuoremmat näyttävät punaisilta. Painiketta napsauttamalla Alice'n ikä vaihtuu 28 ja 31 välillä, mikä aktivoi `setCellPartProvider`-metodin soveltamaan asianmukaisen tyylin, kun data sitoutuu.

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/frontend/css/table/table-dynamic-styling-view.css',
]}
height='475px'
/>

## Raidalliset rivit {#striped-rows}

Ota käyttöön vuorottelevat taustavärit riveille parantaaksesi luettavuutta:

```java
// Ota käyttöön raidallinen rivejä
table.setStriped(true);
```

## Reunat {#borders}

Määritä, mitkä reunat näkyvät `Taulukon`, sarakkeiden ja rivien ympärillä:

```java
// Ota käyttöön kaikki reunat
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Poista kaikki reunat
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

Alla oleva demo näyttää yksinkertaisen tavan sovittaa `Taulukon` visuaalinen ulkoasu sovelluksesi muuhun osaan käyttäen `setStriped()` ja `setBordersVisible()`.

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={['src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java']}
height='300px'
/>

:::tip Datan hallinta ja kysely
Tietoa siitä, miten käyttää `Repository`-mallia hallitsemaan ja kysymään kokoelmaa, löydät [Repository-artikkeleista](/docs/advanced/repository/overview).
:::
