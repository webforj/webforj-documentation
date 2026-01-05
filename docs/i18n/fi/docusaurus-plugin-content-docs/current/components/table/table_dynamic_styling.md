---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: ab74c802642742faeaa38ee9a2f6e8da
---
<!-- vale off -->
# Dynaaminen tyylittely <DocChip chip='since' label='25.00' />
<!-- vale on -->

WebforJ 25:ssä ja sitä uudemmissa versioissa on mahdollista tyylitellä yksittäisiä rivejä ja soluja taulukossa mukautettujen osan nimien avulla. Nämä nimet voidaan määrittää dynaamisesti sovelluksesi logiikan mukaan, mikä antaa sinulle tarkkaa kontrollia taulukon ulkoasusta.

## Rivin tyylittely {#row-styling}

`setRowPartProvider()`-metodi määrittää osan nimet kokonaisille riveille niiden sisältämän datakohteen perusteella. Tämä mahdollistaa koko rivien korostamisen, jotka täyttävät tietyt ehdot – esimerkiksi vuorottelevat taustavärit parillisille riveille.

Nämä tyylinimet voidaan kohdistaa CSS:ssä `::part()`-valitsimella.

:::tip Varjopartit
`::part()`-valitsin on erityinen CSS-ominaisuus, joka mahdollistaa komponentin varjo-DOM:in sisällä olevien elementtien tyylittelyn – niin kauan kuin nämä elementit altistavat `part`-attribuutin. Tämä on erityisen hyödyllinen webforJ-komponenttien, kuten taulukon rivien tai solujen, sisäisten osien tyylittelyssä.

Lisätietoa siitä, miten varjopartit toimivat ja miten ne määritellään ja kohdistetaan, katso [Tyylittely](../../styling/shadow-parts) -osiosta.
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Solun tyylittely {#cell-styling}

`setCellPartProvider()`-metodi tyylittelee yksittäisiä soluja sekä datakohteen että niihin kuuluvan sarakkeen perusteella. Tämä on ihanteellinen tyylittämään tiettyjä arvoja, kuten ikien korostamista, jotka ylittävät rajan tai virheellisiä tietoja.

Kuten riviosat, soluosat määritellään nimellä ja kohdistetaan käyttämällä `::part()`-valitsinta.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Reagoiminen datapäivityksiin {#reacting-to-data-updates}

Jos sovelluksesi muuttaa tietoja ohjelmallisesti, kuten päivittämällä käyttäjän ikää, taulukko arvioi automaattisesti uudelleen ja soveltaa uudelleen kaikkia siihen liittyviä rivin tai solun tyylejä, kun päivitetty kohde on tallennettu varastoon.

Tässä demossa Soluissa Ikä-sarakkeessa on tyyliteltävä rajan mukaan: yli 30-vuotiaat näkyvät vihreinä, kun taas 30 vuotta tai alle näkyvät punaisina. Napsauttamalla painiketta vaihdetaan Alicen ikä 28:n ja 31:n välillä, mikä laukaisee `setCellPartProvider`-metodin, jotta sovelletaan oikeaa tyyliä datan sitoutumisen yhteydessä.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Raidalliset rivit {#striped-rows}

Ota käyttöön vuorottelevat taustavärit riveille luettavuuden parantamiseksi:

```java
// Käytä raidallista rivityylitystä
table.setStriped(true);
```

## Reunat {#borders}

Määritä, mitkä reunoja näytetään `Table`-aseman, sarakkeiden ja rivien ympärillä:

```java
// Ota käyttöön kaikki reunat
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Poista kaikki reunat
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

Alla oleva demo esittelee yksinkertaisen tavan sovittaa `Table`:n visuaalinen ulkoasu sovelluksen muuhun osaan käyttämällä `setStriped()` ja `setBordersVisible()`.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>

:::tip Datan hallinta ja kysely
Lisätietoja siitä, miten voit käyttää `Repository`-mallia hallitaksesi ja kysyä kokoelmia, katso [Repository-artikkelit](/docs/advanced/repository/overview).
:::
