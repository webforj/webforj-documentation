---
sidebar_position: 21
title: Dynamic Styling
slug: styling
sidebar_class_name: updated-content
_i18n_hash: c958a549dfbac715dfce9f26d729f106
---
<!-- vale off -->
# Dynamische Stijlgeving <DocChip chip='since' label='25.00' />
<!-- vale on -->

In webforJ 25 en hoger is het mogelijk om individuele rijen en cellen in de Tabel te stylen met behulp van aangepaste partnamen. Deze namen kunnen dynamisch worden toegewezen op basis van de logica van je app, waardoor je nauwkeurige controle krijgt over de uitstraling van de tabel.

## Rijstijlgeving {#row-styling}

De `setRowPartProvider()`-methode wijst partnamen toe aan volledige rijen op basis van het gegevensitem dat ze bevatten. Dit stelt je in staat om volledige rijen te markeren die aan specifieke voorwaarden voldoen, bijvoorbeeld door afwisselend achtergrondkleuren voor even rijen te gebruiken.

Deze stijlnamen kunnen worden gericht met behulp van de `::part()`-selector in je CSS.

:::tip Schaduw onderdelen
De `::part()`-selector is een speciale CSS-functie die je in staat stelt om elementen binnen de schaduw DOM van een component te stylen, zolang die elementen een `part`-attribuut hebben. Dit is vooral nuttig voor het stylen van interne onderdelen van webforJ-componenten, zoals rijen of cellen in een tabel.

Voor meer informatie over hoe schaduwonderdelen werken en hoe je ze kunt definiëren en targeten, zie de sectie [Styling](../../styling/shadow-parts).
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Celstijlgeving {#cell-styling}

De `setCellPartProvider()`-methode styleert individuele cellen op basis van zowel het gegevensitem als de kolom waartoe ze behoren. Dit maakt het ideaal voor het markeren van specifieke waarden, zoals het aanwijzen van leeftijden die een drempel overschrijden of ongeldige invoer.

Net als rijonderdelen worden celonderdelen gedefinieerd door een naam en gericht met behulp van de `::part()`-selector.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Reageren op gegevensupdates {#reacting-to-data-updates}

Als je app gegevens programmatig wijzigt, zoals het bijwerken van de leeftijd van een gebruiker, zal de tabel automatisch opnieuw evalueren en alle bijbehorende rij- of celstijlen opnieuw toepassen zodra het bijgewerkte item is opgeslagen in de repository.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Gestreepte rijen {#striped-rows}

Schakel afwisselende achtergrondkleuren voor rijen in om de leesbaarheid te verbeteren:

```java
// Pas gestreepte rijstijl toe
table.setStriped(true);
```

## Randen {#borders}

Configureer welke randen worden weergegeven rond de `Tabel`, kolommen en rijen:

```java
// Schakel alle randen in
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Verwijder alle randen
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

De demo hieronder toont een eenvoudige manier om de visuele uitstraling van je `Tabel` af te stemmen op de rest van je app met `setStriped()` en `setBordersVisible()`.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>
