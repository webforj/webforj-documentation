---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: 8e9f61685fbb3a7fb830463f1320e8cf
---
<!-- vale off -->
# Dynaaminen tyylittely <DocChip chip='since' label='25.00' />
<!-- vale on -->

WebforJ 25:ssä ja sitä uudempissa versioissa on mahdollista tyylittää yksittäisiä rivejä ja soluja taulukossa käyttäen mukautettuja osanimiä. Nämä nimet voidaan määrittää dynaamisesti sovelluksesi logiikan perusteella, mikä antaa sinulle tarkkaa hallintaa taulukon ulkonäöstä.

## Rivin tyylitys {#row-styling}

`setRowPartProvider()`-metodi määrittää osanimiä koko riveille niiden sisältämän tietoelementin perusteella. Tämä mahdollistaa koko rivien korostamisen, jotka täyttävät tietyt ehdot—esimerkiksi vuorottelevat taustavärit parillisille riveille.

Näitä tyylinimiä voidaan kohdistaa `::part()`-valitsimella CSS:ssäsi.

:::tip Varjo-osiot
`::part()`-valitsin on erityinen CSS-ominaisuus, joka mahdollistaa komponentin varjo-DOM:issa olevien elementtien tyylittämisen—niin kauan kuin nämä elementit altistavat `part`-attribuutin. Tämä on erityisen hyödyllistä webforJ-komponenttien sisäisten osien, kuten rivien tai solujen, tyylittämiseen.

Lisätietoja siitä, miten varjo-osat toimivat ja miten niitä määritellään ja kohdistetaan, katso [Tyylittely](../../styling/shadow-parts) -osio.
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Solun tyylitys {#cell-styling}

`setCellPartProvider()`-metodi tyylittää yksittäisiä soluja sekä tiedoelementin että siihen liittyvän sarakkeen perusteella. Tämä tekee siitä ihanteellisen tiettyjen arvojen, kuten ikien korostamisen, kun ne ovat ennen kynnystä tai virheellisiä merkintöjä.

Kuten riviosat, soluosat määritellään nimellä ja niitä kohdistetaan `::part()`-valitsimella.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Reagoiminen tietopäivityksiin {#reacting-to-data-updates}

Jos sovelluksesi muuttaa tietoja ohjelmallisesti, kuten päivittämällä käyttäjän ikää, taulukko arvioi automaattisesti ja soveltaa uudelleen kaikki siihen liittyvät rivin tai solun tyylit, kun päivitettävä kohde on sitoutettu varastoon.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Raidalliset rivit {#striped-rows}

Ota käyttöön vuorottelevat taustavärit riveille parantaaksesi luettavuutta:

```java
// Käytä raidallista rivityylitystä
table.setStriped(true);
```

## Reunat {#borders}

Määritä, mitkä reunat näytetään `Table`:n, sarakkeiden ja rivien ympärillä:

```java
// Ota kaikki reunat käyttöön
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Poista kaikki reunat
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

Alla oleva demo esittelee yksinkertaisen tavan sovittaa `Table`:n visuaalinen ilme sovelluksesi muuhun osaan käyttäen `setStriped()` ja `setBordersVisible()`.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>

:::tip Tietojen hallinta ja kysely
Lisätietoja `Repository`-mallin käytöstä kokoelmainen hallitsemiseen ja kyselyyn, katso [Repository-artikkelit](/docs/advanced/repository/overview).
:::
