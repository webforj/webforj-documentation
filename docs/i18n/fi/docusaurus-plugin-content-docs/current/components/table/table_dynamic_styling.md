---
sidebar_position: 21
title: Dynamic Styling
slug: styling
sidebar_class_name: updated-content
_i18n_hash: 38ec04cdf27a7de8a9e51ad24cf66009
---
<!-- vale off -->
# Dynaaminen tyylitys <DocChip chip='since' label='25.00' />
<!-- vale on -->

WebforJ 25:ssä ja korkeammissa versioissa on mahdollista tyylittää yksittäisiä rivejä ja soluja taulukossa käyttäen mukautettuja osanimiä. Nämä nimet voidaan määrittää dynaamisesti sovelluksesi logiikan perusteella, mikä antaa sinulle tarkkaa hallintaa taulukon ulkoasusta.

## Rivien tyylitys {#row-styling}

`setRowPartProvider()`-metodi määrittää osanimiä koko riveille niiden sisältämän tietoelementin perusteella. Tämä mahdollistaa täydellisten rivien korostamisen, jotka täyttävät tietyt ehdot—esimerkiksi vuorotellen taustavärejä parillisille riveille.

Näitä tyylinimiä voidaan kohdistaa CSS:ssä `::part()`-valitsimen avulla.

:::tip Varjopartit
`::part()`-valitsin on erityinen CSS-ominaisuus, joka mahdollistaa komponentin varjo DOM:in sisällä olevien elementtien tyylittämisen—niin kauan kuin nämä elementit altistavat `part`-attribuutin. Tämä on erityisen hyödyllistä webforJ-komponenttien sisäisten osien, kuten rivien tai solujen tyylittämisessä.

Lisätietoja varjoparttien toiminnasta ja niiden määrittämisestä ja kohdistamisesta löydät [Tyylitys](../../styling/shadow-parts) osiosta.
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Solujen tyylitys {#cell-styling}

`setCellPartProvider()`-metodi tyylittää yksittäisiä soluja sekä tietoelementin että niihin kuuluvan sarakkeen perusteella. Tämä tekee siitä ihanteellisen tiettyjen arvojen, kuten kynnyksen alittavien ikien tai virheellisten tietojen korostamiseen.

Kuten riviosat, solupositiot määritellään nimellä ja kohdistetaan `::part()`-valitsimen avulla.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Reagointi datapäivityksiin {#reacting-to-data-updates}

Jos sovelluksesi muuttaa tietoja ohjelmallisesti, kuten päivittämällä käyttäjän ikää, taulukko arvioi automaattisesti uudelleen ja soveltaa mitään siihen liittyviä rivin tai solun tyylejä heti, kun päivitetty elementti on tallennettu varastoon. 

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Raidalliset rivit {#striped-rows}

Ota käyttöön vuorotellen taustavärit riveille parantaaksesi luettavuutta:

```java
// Ota käyttöön raidalliset rivistyylit
table.setStriped(true);
```

## Rajat {#borders}

Määritä, mitkä rajat näytetään `Table`:n, sarakkeiden ja rivien ympärillä:

```java
// Ota käyttöön kaikki rajat
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Poista kaikki rajat
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

Alla oleva esimerkki esittelee yksinkertaisen tavan sovittaa `Table`:n visuaalinen ulkoasu sovelluksesi muuhun visuaaliseen ilmeeseen käyttäen `setStriped()` ja `setBordersVisible()`.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>
