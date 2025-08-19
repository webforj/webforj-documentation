---
sidebar_position: 21
title: Dynamic Styling
slug: styling
sidebar_class_name: updated-content
_i18n_hash: 38ec04cdf27a7de8a9e51ad24cf66009
---
<!-- vale off -->
# Dynamische Styling <DocChip chip='since' label='25.00' />
<!-- vale on -->

In webforJ 25 en hoger, is het mogelijk om individuele rijen en cellen in de tabel te stijlen met behulp van aangepaste part-namen. Deze namen kunnen dynamisch worden toegewezen op basis van de logica van uw app, waardoor u gedetailleerde controle heeft over het uiterlijk van de tabel.

## Rij-styling {#row-styling}

De `setRowPartProvider()` methode wijst part-namen toe aan volledige rijen op basis van het data-item dat ze bevatten. Dit stelt u in staat om volledige rijen te markeren die aan specifieke voorwaarden voldoen—bijvoorbeeld afwisselende achtergrondkleuren voor even rijen.

Deze stijl namen kunnen worden gericht met de `::part()` selector in uw CSS.

:::tip Schaduw onderdelen
De `::part()` selector is een speciale CSS-functie waarmee u elementen binnen de shadow DOM van een component kunt stylen, zolang deze elementen een `part` attribuut hebben. Dit is vooral nuttig voor het stylen van interne onderdelen van webforJ-componenten, zoals rijen of cellen in een tabel.

Voor meer informatie over hoe schaduwonderdelen werken en hoe u ze kunt definiëren en richten, zie de [Styling](../../styling/shadow-parts) sectie.
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Cel-styling {#cell-styling}

De `setCellPartProvider()` methode stijlt individuele cellen op basis van zowel het data-item als de kolom waartoe ze behoren. Dit maakt het ideaal voor het markeren van specifieke waarden, zoals leeftijd die een bepaalde drempel overschrijdt of ongeldige invoer.

Net als rijonderdelen worden celonderdelen gedefinieerd door een naam en gericht met de `::part()` selector.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Reageren op data-updates {#reacting-to-data-updates}

Als uw app data programmatig wijzigt, zoals het bijwerken van de leeftijd van een gebruiker, zal de tabel automatisch opnieuw evalueren en alle bijbehorende rij- of celstijlen opnieuw toepassen zodra het bijgewerkte item in de repository is gecommitteerd.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Gestreepte rijen {#striped-rows}

Schakel afwisselende achtergrondkleuren voor rijen in om de leesbaarheid te verbeteren:

```java
// Pas gestreepte rij-styling toe
table.setStriped(true);
```

## Randen {#borders}

Configureer welke randen zichtbaar zijn rond de `Table`, kolommen en rijen:

```java
// Zet alle randen aan
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Verwijder alle randen
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

De demo hieronder toont een eenvoudige manier om het visuele uiterlijk van uw `Table` af te stemmen op de rest van uw app met behulp van `setStriped()` en `setBordersVisible()`.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>
