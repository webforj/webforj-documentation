---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: 8f910c729d1108faeaba860a2e0f3546
---
<!-- vale off -->
# Dynaaminen tyylitys <DocChip chip='since' label='25.00' />
<!-- vale on -->

webforJ:ssä versiosta 25 ja ylemmät on mahdollista tyylittää yksittäisiä rivejä ja soluja taulukossa mukautettujen osan nimien avulla. Nämä nimet voidaan määrittää dynaamisesti sovelluksesi logiikan perusteella, mikä antaa sinulle tarkan hallinnan taulukon ulkonäöstä.

## Rivin tyylitys {#row-styling}

`setRowPartProvider()`-metodi määrittää osan nimiä koko riveille niiden sisältämän datan perusteella. Tämä mahdollistaa koko rivien korostamisen, jotka täyttävät tietyt ehdot—esimerkiksi vuorottelevat taustavärit parillisille riveille.

Näitä tyylinimiä voidaan kohdistaa CSS:ssä `::part()`-valitsimella.

:::tip Varjopartit
`::part()`-valitsin on erityinen CSS-ominaisuus, joka mahdollistaa komponentin shadow DOM:ssa olevien elementtien tyylittämisen—niin kauan kuin nämä elementit paljastavat `part`-attribuutin. Tämä on erityisen hyödyllistä webforJ-komponenttien, kuten rivien tai solujen taulukossa, sisäisten osien tyylittämisessä.

Lisätietoja varjoparttien toiminnasta ja niiden määrittämisestä sekä kohdistamisesta, katso [Tyylittely](../../styling/shadow-parts) osio.
:::


<ComponentDemo
path='/webforj/tablerowstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableRowStylingView.java',
  'src/main/resources/static/css/table/table-row-styling-view.css',
]}
height='300px'
/>

## Solun tyylitys {#cell-styling}

`setCellPartProvider()`-metodi tyylittää yksittäisiä soluja sekä datan että niiden kuulumisen perusteella sarakkeeseen. Tämä tekee siitä ihanteellisen korostamaan tiettyjä arvoja, kuten ikien merkitsemistä, jotka ylittävät kynnyksen tai virheellisiä tietoja.

Kuten riviosat, soluosat määritetään nimellä ja niitä kohdistetaan `::part()`-valitsimella.

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/resources/static/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## Reagoiminen datan päivityksiin {#reacting-to-data-updates}

Jos sovelluksesi muuttaa dataa ohjelmallisesti, kuten päivittämällä käyttäjän ikää, taulukko arvioi ja uudelleen soveltaa automaattisesti kaikille liittyville riville tai soluille määritettyjä tyylejä, kun päivitetty kohde on vahvistettu tallennuksessa.

Tässä demossa Ikä-sarakkeen solujen tyylit perustuvat kynnykseen: yli 30-vuotiaat näyttävät vihreiltä, kun taas 30-vuotiaat ja sitä nuoremmat näyttävät punaisilta. Nappi vaihtaa Alicen ikää 28 ja 31 välillä, mikä laukaisee `setCellPartProvider`:n uudelleen soveltamaan sopivaa tyyliä, kun data on vahvistettu.

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/resources/static/css/table/table-dynamic-styling-view.css',
]}
height='475px'
/>

## Viivoitetut rivit {#striped-rows}

Ota käyttöön vuorottelevat taustavärit riveille parantaaksesi luettavuutta:

```java
// Sovella viivoitettua rivityyliä
table.setStriped(true);
```

## Reunat {#borders}

Määritä, mitkä reunat näkyvät `Table`:n, sarakkeiden ja rivien ympärillä:

```java
// Ota käyttöön kaikki reunat
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Poista kaikki reunat
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

Alla oleva demo esittelee yksinkertaisen tavan sovittaa `Table`:n visuaalinen ulkonäkö sovelluksesi muuhun osaan `setStriped()` ja `setBordersVisible()` avulla.

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={['src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java']}
height='300px'
/>

:::tip Datan hallinta ja kysely
Tietoja siitä, miten voit käyttää `Repository`-mallia hallita ja kysyä kokoelmia, katso [Repository-artikkelit](/docs/advanced/repository/overview).
:::
