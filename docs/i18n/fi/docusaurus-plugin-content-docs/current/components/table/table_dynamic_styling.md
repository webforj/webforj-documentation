---
sidebar_position: 21
title: Dynaaminen tyylittely
slug: styling
description: >-
  Apply data-driven CSS to Table rows and cells using setRowPartProvider and
  setCellPartProvider with shadow part selectors.
_i18n_hash: 6d82241fb67ffa1ce5a1e8c974a96dea
---
<!-- vale off -->
# Dynaaminen tyylitys <DocChip chip='since' label='25.00' />
<!-- vale on -->

WebforJ 25:stä alkaen Table-taulukon yksittäisiä rivejä ja soluja voi tyylitellä mukautetuilla osanimillä. Nimet voidaan määrittää dynaamisesti sovelluksen logiikan perusteella, mikä antaa tarkkaa hallintaa taulukon ulkoasuun.

## Rivien tyylittely {#row-styling}

`setRowPartProvider()`-metodilla voidaan määrittää osanimet kokonaisille riveille niiden sisältämän tietokohteen perusteella. Näin voit korostaa kokonaisia rivejä, jotka täyttävät tietyt ehdot – esimerkiksi käyttämällä parillisilla riveillä vuorotellen erilaisia taustavärejä.

Näihin tyylinimiin voidaan kohdistaa CSS:n `::part()`-valitsimella.

:::tip Varjo-osat
`::part()`-valitsin on CSS:n erikoisominaisuus, jolla voi tyylitellä komponentin shadow DOM -rakenteen sisällä olevia elementtejä, kunhan nämä elementit tuovat esiin `part`-attribuutin. Tämä on erityisen hyödyllistä webforJ-komponenttien sisäisten osien, kuten taulukon rivien tai solujen, tyylittelyssä.

Lisätietoja varjo-osien toiminnasta sekä niiden määrittämisestä ja kohdistamisesta on kohdassa [Tyylittely](../../styling/shadow-parts).
:::


<ComponentDemo
path='/webforj/tablerowstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableRowStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-row-styling-view.css',
]}
height='300px'
/>

## Solujen tyylittely {#cell-styling}

`setCellPartProvider()`-metodilla tyylitellään yksittäisiä soluja sekä tietokohteen että siihen kuuluvan sarakkeen perusteella. Tämä sopii hyvin tiettyjen arvojen korostamiseen, esimerkiksi kynnysarvon ylittävien ikien tai virheellisten tietojen huomioimiseen.

Riviosien tapaan myös soluosat määritetään nimellä, ja niihin kohdistetaan `::part()`-valitsimella.

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableCellStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## Reagointi tietojen päivityksiin {#reacting-to-data-updates}

Jos sovelluksesi muokkaa tietoja ohjelmallisesti, esimerkiksi päivittää käyttäjän iän, taulukko arvioi siihen liittyvät rivi- ja solutyylit automaattisesti uudelleen ja ottaa ne käyttöön, kun päivitetty kohde on vahvistettu tietovarastoon.

Tässä esimerkissä Age-sarakkeen solut tyylitellään kynnysarvon perusteella: yli 30-vuotiaat näkyvät vihreinä ja enintään 30-vuotiaat punaisina. Painikkeen napsauttaminen vaihtaa Alicen iän 28:n ja 31:n välillä, jolloin `setCellPartProvider` ottaa sopivan tyylin uudelleen käyttöön tietojen vahvistamisen jälkeen.

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-dynamic-styling-view.css',
]}
height='475px'
/>

## Raidalliset rivit {#striped-rows}

Paranna luettavuutta käyttämällä riveillä vuorottelevia taustavärejä:

```java
// Ota käyttöön raidallisten rivien tyylittely
table.setStriped(true);
```

## Reunukset {#borders}

Määritä, mitkä reunukset näytetään `Table`-taulukon, sarakkeiden ja rivien ympärillä:

```java
// Ota käyttöön kaikki reunukset
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Poista kaikki reunukset
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

Alla oleva esimerkki näyttää yksinkertaisen tavan sovittaa `Table`-taulukon ulkoasu sovelluksen muuhun ilmeeseen `setStriped()`- ja `setBordersVisible()`-metodeilla.

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
]}
height='300px'
/>

:::tip Tietojen hallinta ja kyselyt
Lisätietoja `Repository`-mallin käyttämisestä kokoelmien hallintaan ja kyselyihin on [Repository-artikkeleissa](/docs/advanced/repository/overview).
:::
