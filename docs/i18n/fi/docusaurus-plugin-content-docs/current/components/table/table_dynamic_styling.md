---
sidebar_position: 21
title: Dynamic Styling
slug: styling
sidebar_class_name: updated-content
_i18n_hash: c958a549dfbac715dfce9f26d729f106
---
<!-- vale off -->
# Dynaaminen muotoilu <DocChip chip='since' label='25.00' />
<!-- vale on -->

WebforJ 25:ssä ja korkeammissa versioissa on mahdollista muotoilla yksittäisiä rivejä ja soluja Taulukossa käyttäen mukautettuja osanimiä. Näitä nimiä voidaan määrätä dynaamisesti sovelluksesi logiikan perusteella, mikä antaa sinulle tarkkaa kontrollia taulukon ulkonäöstä.

## Rivien muotoilu {#row-styling}

`setRowPartProvider()`-metodi määrittää osanimiä koko riveille niiden sisältämän datan perusteella. Tämä mahdollistaa täydellisten rivien korostamisen, jotka täyttävät tietyt ehdot—esimerkiksi vuorotellen vaihtuvat taustavärit parillisten rivien kohdalla.

Näitä tyylinimiä voidaan kohdistaa CSS:ssä `::part()`-valitsimen avulla.

:::tip Shadow-osat
`::part()`-valitsin on erityinen CSS-ominaisuus, joka mahdollistaa komponentin varjo-DOM:issa olevien elementtien muotoilun—niin kauan kuin nämä elementit altistavat `part`-attribuutin. Tämä on erityisen hyödyllinen webforJ-komponenttien sisäisten osien, kuten taulukon rivien tai solujen muotoilussa.

Lisätietoja siitä, kuinka varjo-osat toimivat ja kuinka määritellä ja kohdistaa niitä, katso [Muotoilu](../../styling/shadow-parts)-osio.
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Solujen muotoilu {#cell-styling}

`setCellPartProvider()`-metodi muotoilee yksittäisiä soluja sekä datan että niiden kuulumisen sarakkeen perusteella. Tämä tekee siitä ihanteellisen tiettyjen arvojen korostamiseen, kuten ikien korostamiseen, jotka ylittävät kynnysarvon tai virheellisten tietojen merkitsemiseen.

Kuten riviosat, soluosat määritellään nimellä ja niitä kohdistetaan `::part()`-valitsimen avulla.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Reagointi datapäivityksiin {#reacting-to-data-updates}

Jos sovelluksesi muuttaa dataa ohjelmallisesti, kuten päivittämällä käyttäjän ikää, taulukko arvioi automaattisesti uudelleen ja soveltaa uudelleen kaikkiin liittyviin rivin tai solun tyyleihin, kun päivitetty kohde on vahvistettu varastoon. 

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Raidalliset rivit {#striped-rows}

Ota käyttöön vuorotellen vaihtuvat taustavärit riveille parantaaksesi luettavuutta:

```java
// Ota käyttöön raidalliset rivin muotoilut
table.setStriped(true);
```

## Reunukset {#borders}

Määritä, mitkä reunukset näkyvät Taulukon, sarakkeiden ja rivien ympärillä:

```java
// Ota käyttöön kaikki reunukset
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Poista kaikki reunukset
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

Alla oleva esittely näyttää yksinkertaisen tavan sovittaa Taulukon visuaalinen ulkonäkö sovelluksesi muiden osien kanssa käyttäen `setStriped()` ja `setBordersVisible()`.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>
