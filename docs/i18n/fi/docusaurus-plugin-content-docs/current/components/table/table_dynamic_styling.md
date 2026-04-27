---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: 03c0fb81b4bcabef5db6dfb9785eef3d
---
<!-- vale off -->
# Dynaaminen tyylitys <DocChip chip='since' label='25.00' />
<!-- vale on -->

WebforJ 25:stä ja sitä uudemmista versioista lähtien on mahdollista tyylittää yksittäisiä rivejä ja soluja taulukossa käyttämällä mukautettuja osanimiä. Näitä nimiä voidaan määrittää dynaamisesti sovelluksesi logiikan perusteella, mikä antaa tarkkaa hallintaa taulukon ulkoasusta.

## Rivin tyylitys {#row-styling}

`setRowPartProvider()`-metodi määrittää osanimiä koko riveille niiden sisältämän datan perusteella. Tämä mahdollistaa koko rivien korostamisen, jotka täyttävät tietyt ehdot—esimerkiksi vuorottelevat taustavärit parillisille riveille.

Näitä tyylimääreitä voidaan kohdistaa käyttämällä `::part()`-valitsinta CSS:ssäsi.

:::tip Varjopuolikappaleet
`::part()`-valitsin on erityinen CSS-ominaisuus, joka mahdollistaa komponentin varjo-DOM:in sisällä olevien elementtien tyylittämisen—niin kauan kuin nämä elementit altistavat `part`-attribuutin. Tämä on erityisen hyödyllistä webforJ-komponenttien sisäisten osien, kuten rivien tai solujen, tyylittämiseen taulukossa.

Lisätietoja siitä, miten varjopuolikappaleet toimivat ja miten ne määritellään ja kohdistetaan, katso [Tyylitys](../../styling/shadow-parts) -osio.
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
cssURL='/css/table/table-row-styling-view.css'
height='300px'
/>

## Solun tyylitys {#cell-styling}

`setCellPartProvider()`-metodi tyylittää yksittäisiä soluja sekä datan että niiden kuuluvan sarakkeen perusteella. Tämä on ihanteellinen tiettyjen arvojen korostamiseen, kuten ikien merkitsemiseen, jotka ylittävät kynnysarvon tai virheellisten tietojen esittämiseen.

Kuten riviosat, soluosat määritellään nimellä ja niitä kohdistetaan `::part()`-valitsimen avulla.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
cssURL='/css/table/table-cell-styling-view.css'
height='300px'
/>

## Reagointi datan päivityksiin {#reacting-to-data-updates}

Jos sovelluksesi muuttaa dataa ohjelmallisesti, kuten päivittämällä käyttäjän ikää, taulukko arvioi ja soveltaa automaattisesti kaikkia siihen liittyviä rivin tai solun tyylejä, kun päivitetty kohde on tallennettu varastoon.

Tässä demossa Ikä-sarakkeen solut on tyylitettu kynnysarvon perusteella: yli 30-vuotiaat näyttävät vihreiltä, kun taas 30 vuotta täyttäneet tai nuoremmat näyttävät punaisilta. Napsauttamalla painiketta Alice'n ikä vaihtelee 28 ja 31 välillä, mikä laukaisee `setCellPartProvider`-metodin, joka soveltaa oikeaa tyyliä, kun data on tallennettu.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
cssURL='/css/table/table-dynamic-styling-view.css'
height='475px'
/>

## Raidalliset rivit {#striped-rows}

Ota käyttöön vuorottelevat taustavärit riveille parantaaksesi luettavuutta:

```java
// Ota käyttöön raidalliset rivityylit
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

Alla oleva demo esittelee yksinkertaisen tavan yhdistää `Taulukon` visuaalinen ulkoasu sovelluksesi muun osan kanssa käyttäen `setStriped()` ja `setBordersVisible()`.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>

:::tip Datan hallinta ja kysely
Lisätietoja siitä, kuinka käyttää `Repository`-mallia hallitaksesi ja kysyäksesi kokoelmia, katso [Repository-artikkelit](/docs/advanced/repository/overview).
:::
