---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: 03c0fb81b4bcabef5db6dfb9785eef3d
---
<!-- vale off -->
# Dynamische styling <DocChip chip='since' label='25.00' />
<!-- vale on -->

In webforJ 25 en hoger is het mogelijk om individuele rijen en cellen in de tabel te stylen met behulp van aangepaste deelnamen. Deze namen kunnen dynamisch worden toegewezen op basis van de logica van je app, waardoor je nauwkeurige controle krijgt over het uiterlijk van de tabel.

## Rijstyling {#row-styling}

De `setRowPartProvider()` methode wijst deelnamen toe aan volledige rijen op basis van het gegevenselement dat ze bevatten. Dit stelt je in staat om volledige rijen te markeren die aan specifieke voorwaarden voldoen, bijvoorbeeld het afwisselend gebruiken van achtergrondkleuren voor even rijen.

Deze stijlnaam kan worden gericht met behulp van de `::part()` selector in je CSS.

:::tip Schaduwonderdelen
De `::part()` selector is een speciale CSS-functie die je in staat stelt om elementen binnen de schaduw DOM van een component te stylen, zolang die elementen een `part` attribuut hebben. Dit is vooral nuttig voor het stylen van interne onderdelen van webforJ-componenten, zoals rijen of cellen in een tabel.

Voor meer informatie over hoe schaduwonderdelen werken en hoe je ze kunt definiëren en richten, zie de [Styling](../../styling/shadow-parts) sectie.
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
cssURL='/css/table/table-row-styling-view.css'
height='300px'
/>

## Celstyling {#cell-styling}

De `setCellPartProvider()` methode stijlt individuele cellen op basis van zowel het gegevenselement als de kolom waartoe ze behoren. Dit maakt het ideaal voor het markeren van specifieke waarden, zoals het aangeven van leeftijden die een drempel overschrijden of ongeldige invoer.

Net als rijonderdelen zijn celonderdelen gedefinieerd door een naam en worden ze gericht met behulp van de `::part()` selector.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
cssURL='/css/table/table-cell-styling-view.css'
height='300px'
/>

## Reageren op gegevensupdates {#reacting-to-data-updates}

Als je app gegevens programmatisch wijzigt, zoals het bijwerken van de leeftijd van een gebruiker, zal de tabel automatisch opnieuw evalueren en eventuele bijbehorende rij- of celstijlen opnieuw toepassen zodra het bijgewerkte item in de repository is bevestigd.

In deze demo zijn cellen in de leeftijdskolom gestyled op basis van een drempel: leeftijden boven de 30 verschijnen groen, terwijl leeftijden van 30 en jonger rood verschijnen. Het klikken op de knop schakelt de leeftijd van Alice tussen 28 en 31, waardoor de `setCellPartProvider` wordt geactiveerd om de juiste stijl opnieuw toe te passen wanneer de gegevens worden bevestigd.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
cssURL='/css/table/table-dynamic-styling-view.css'
height='475px'
/>

## Gestreepte rijen {#striped-rows}

Schakel afwisselende achtergrondkleuren in voor rijen om de leesbaarheid te verbeteren:

```java
// Pas gestreepte rijstyling toe
table.setStriped(true);
```

## Randen {#borders}

Configureer welke randen rondom de `Table`, kolommen en rijen worden weergegeven:

```java
// Schakel alle randen in
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Verwijder alle randen
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

De onderstaande demo toont een eenvoudige manier om het visuele uiterlijk van je `Table` af te stemmen op de rest van je app met `setStriped()` en `setBordersVisible()`.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>

:::tip Beheren en opvragen van gegevens
Voor informatie over hoe je het `Repository` patroon kunt gebruiken om collecties te beheren en op te vragen, zie de [Repository artikelen](/docs/advanced/repository/overview).
:::
