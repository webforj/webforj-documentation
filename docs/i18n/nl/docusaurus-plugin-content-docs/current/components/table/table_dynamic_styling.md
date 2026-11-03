---
sidebar_position: 21
title: Dynamische styling
slug: styling
description: >-
  Apply data-driven CSS to Table rows and cells using setRowPartProvider and
  setCellPartProvider with shadow part selectors.
_i18n_hash: 6d82241fb67ffa1ce5a1e8c974a96dea
---
<!-- vale off -->
# Dynamische styling <DocChip chip='since' label='25.00' />
<!-- vale on -->

In webforJ 25 en hoger kun je afzonderlijke rijen en cellen in de Table stylen met aangepaste part-namen. Deze namen kunnen dynamisch worden toegewezen op basis van de logica van je app, zodat je de weergave van de tabel nauwkeurig kunt aanpassen.

## Rijen stylen {#row-styling}

De methode `setRowPartProvider()` wijst part-namen toe aan volledige rijen op basis van het gegevensitem dat ze bevatten. Zo kun je volledige rijen markeren die aan specifieke voorwaarden voldoen, bijvoorbeeld met afwisselende achtergrondkleuren voor even rijen.

Je kunt deze stijlnamen targeten met de `::part()`-selector in je CSS.

:::tip Shadow parts
De `::part()`-selector is een speciale CSS-functie waarmee je elementen binnen de shadow DOM van een component kunt stylen, zolang deze elementen een `part`-attribuut beschikbaar stellen. Dit is vooral handig om interne onderdelen van webforJ-componenten te stylen, zoals rijen of cellen in een tabel.

Zie het gedeelte [Styling](../../styling/shadow-parts) voor meer informatie over de werking van shadow parts en hoe je deze definieert en target.
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

## Cellen stylen {#cell-styling}

De methode `setCellPartProvider()` stylet afzonderlijke cellen op basis van zowel het gegevensitem als de kolom waartoe ze behoren. Dit is ideaal om specifieke waarden te markeren, bijvoorbeeld leeftijden onder een bepaalde grenswaarde of ongeldige waarden.

Net als bij rijonderdelen worden celonderdelen gedefinieerd met een naam en getarget met de `::part()`-selector.

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableCellStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## Reageren op gegevensupdates {#reacting-to-data-updates}

Als je app gegevens programmatisch wijzigt, bijvoorbeeld door de leeftijd van een gebruiker bij te werken, evalueert de tabel automatisch alle bijbehorende rij- of celstijlen opnieuw en past deze opnieuw toe zodra het bijgewerkte item in de repository is opgeslagen.

In deze demo worden cellen in de kolom Age gestyled op basis van een grenswaarde: leeftijden boven de 30 worden groen weergegeven, terwijl leeftijden van 30 en lager rood worden weergegeven. Als je op de knop klikt, wisselt Alice's leeftijd tussen 28 en 31. Hierdoor past `setCellPartProvider` de juiste stijl opnieuw toe wanneer de gegevens zijn opgeslagen.

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-dynamic-styling-view.css',
]}
height='475px'
/>

## Gestreepte rijen {#striped-rows}

Schakel afwisselende achtergrondkleuren voor rijen in om de leesbaarheid te verbeteren:

```java
// Gestreepte rijstyling toepassen
table.setStriped(true);
```

## Randen {#borders}

Configureer welke randen rond de `Table`, kolommen en rijen worden weergegeven:

```java
// Alle randen inschakelen
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Alle randen verwijderen
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

De onderstaande demo laat zien hoe je de visuele weergave van je `Table` eenvoudig kunt afstemmen op de rest van je app met `setStriped()` en `setBordersVisible()`.

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
]}
height='300px'
/>

:::tip Gegevens beheren en opvragen
Zie de [Repository-artikelen](/docs/advanced/repository/overview) voor informatie over het gebruik van het `Repository`-patroon om collecties te beheren en op te vragen.
:::
