---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: ab74c802642742faeaa38ee9a2f6e8da
---
<!-- vale off -->
# Dynamische Styling <DocChip chip='since' label='25.00' />
<!-- vale on -->

In webforJ 25 en hoger is het mogelijk om individuele rijen en cellen in de tabel te stylen met behulp van aangepaste part-namen. Deze namen kunnen dynamisch worden toegewezen op basis van de logica van je app, zodat je een gedetailleerde controle hebt over het uiterlijk van de tabel.

## Rijstyling {#row-styling}

De `setRowPartProvider()` methode wijst part-namen toe aan hele rijen op basis van het data-item dat ze bevatten. Dit stelt je in staat om volledige rijen te markeren die aan specifieke voorwaarden voldoen, bijvoorbeeld door afwisselende achtergrondkleuren voor even rijen te gebruiken.

Deze stylenamen kunnen worden aangesproken met de `::part()` selector in je CSS.

:::tip Schaduwonderdelen
De `::part()` selector is een speciale CSS-functie waarmee je elementen binnen de shadow DOM van een component kunt stylen, zolang die elementen een `part` attribuut hebben. Dit is vooral nuttig voor het stylen van interne onderdelen van webforJ-componenten, zoals rijen of cellen in een tabel.

Voor meer informatie over hoe schaduwonderdelen werken en hoe je ze kunt definiëren en aanspreken, zie de [Styling](../../styling/shadow-parts) sectie.
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Celstyling {#cell-styling}

De `setCellPartProvider()` methode stylet individuele cellen op basis van zowel het data-item als de kolom waartoe ze behoren. Dit maakt het ideaal om specifieke waarden te markeren, zoals het benadrukken van leeftijden die een drempelwaarde overschrijden of ongeldige invoer.

Net als rijonderdelen, worden celonderdelen gedefinieerd door een naam en aangesproken met de `::part()` selector.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Reageren op gegevensupdates {#reacting-to-data-updates}

Als je app gegevens programmatisch wijzigt, zoals het bijwerken van de leeftijd van een gebruiker, zal de tabel automatisch opnieuw evalueren en alle bijbehorende rij- of celstijlen opnieuw toepassen zodra het bijgewerkte item in de repository is vastgelegd.

In deze demo zijn cellen in de leeftijdskolom gestyled op basis van een drempel: leeftijden boven de 30 jaar verschijnen groen, terwijl leeftijden van 30 jaar en jonger rood verschijnen. Door op de knop te klikken, schakelt de leeftijd van Alice tussen 28 en 31 en wordt `setCellPartProvider` geactiveerd om de juiste stijl opnieuw toe te passen wanneer de gegevens zijn vastgelegd.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Gestreepte rijen {#striped-rows}

Schakel afwisselende achtergrondkleuren voor rijen in om de leesbaarheid te verbeteren:

```java
// Pas gestreepte rijstyling toe
table.setStriped(true);
```

## Randen {#borders}

Configureer welke randen zichtbaar zijn rond de `Table`, kolommen en rijen:

```java
// Schakel alle randen in
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Verwijder alle randen
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

De demo hieronder toont een eenvoudige manier om het visuele uiterlijk van je `Table` af te stemmen op de rest van je app met behulp van `setStriped()` en `setBordersVisible()`.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>

:::tip Beheren en opvragen van gegevens
Voor informatie over het gebruik van het `Repository`-patroon om collecties te beheren en op te vragen, zie de [Repository-artikelen](/docs/advanced/repository/overview).
:::
