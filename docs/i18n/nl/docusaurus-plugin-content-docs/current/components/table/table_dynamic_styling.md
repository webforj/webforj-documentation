---
sidebar_position: 21
title: Dynamic Styling
slug: styling
description: >-
  Apply data-driven CSS to Table rows and cells using setRowPartProvider and
  setCellPartProvider with shadow part selectors.
_i18n_hash: 28911597e1e885b0531bb27aa3c8e1a7
---
<!-- vale off -->
# Dynamische Styling <DocChip chip='since' label='25.00' />
<!-- vale on -->

In webforJ 25 en hoger is het mogelijk om individuele rijen en cellen in de Tabel te stylen met behulp van aangepaste deelnamen. Deze namen kunnen dynamisch worden toegewezen op basis van de logica van je app, waardoor je gedetailleerde controle hebt over het uiterlijk van de tabel.

## Rijstyling {#row-styling}

De `setRowPartProvider()` methode wijst deelnamen toe aan volledige rijen op basis van het gegevensitem dat ze bevatten. Dit stelt je in staat om volledige rijen te markeren die aan specifieke voorwaarden voldoen, bijvoorbeeld door afwisselend achtergrondkleuren voor even rijen toe te passen.

Deze stijl benamingen kunnen worden gericht met de `::part()` selector in je CSS.

:::tip Schaduw delen
De `::part()` selector is een speciale CSS-functie waarmee je elementen in de schaduw DOM van een component kunt stylen, zolang die elementen een `part` attribuut hebben. Dit is vooral nuttig voor het stylen van interne delen van webforJ-componenten, zoals rijen of cellen in een tabel.

Voor meer informatie over hoe schaduw delen werken en hoe je ze kunt definiëren en richten, zie de [Styling](../../styling/shadow-parts) sectie.
:::


<ComponentDemo
path='/webforj/tablerowstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableRowStylingView.java',
  'src/main/frontend/css/table/table-row-styling-view.css',
]}
height='300px'
/>

## Celstyling {#cell-styling}

De `setCellPartProvider()` methode styled individuele cellen op basis van zowel het gegevensitem als de kolom waartoe ze behoren. Dit maakt het ideaal voor het markeren van specifieke waarden, zoals het benadrukken van leeftijden die boven een drempel liggen of ongeldige invoeren.

Net als rij delen, worden cel delen gedefinieerd door een naam en gericht met de `::part()` selector.

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/frontend/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## Reageren op gegevensupdates {#reacting-to-data-updates}

Als je app gegevens programmatisch wijzigt, zoals het bijwerken van de leeftijd van een gebruiker, zal de tabel automatisch opnieuw evalueren en alle bijbehorende rij- of celstijlen opnieuw toepassen zodra het bijgewerkte item is vastgesteld in de repository.

In deze demo zijn cellen in de Leeftijd kolom gestyled op basis van een drempel: leeftijden boven de 30 verschijnen groen, terwijl leeftijden van 30 en jonger rood verschijnen. Door op de knop te klikken, wisselt de leeftijd van Alice tussen 28 en 31, waardoor de `setCellPartProvider` de juiste stijl opnieuw toepast wanneer de gegevens zijn bevestigd.

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/frontend/css/table/table-dynamic-styling-view.css',
]}
height='475px'
/>

## Gestreepte rijen {#striped-rows}

Schakel afwisselende achtergrondkleuren voor rijen in om de leesbaarheid te verbeteren:

```java
// Pas gestreepte rij styling toe
table.setStriped(true);
```

## Randen {#borders}

Configureer welke randen rond de `Tabel`, kolommen en rijen worden weergegeven:

```java
// Schakel alle randen in
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Verwijder alle randen
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

De demo hieronder toont een eenvoudige manier om het visuele uiterlijk van je `Tabel` af te stemmen op de rest van je app met behulp van `setStriped()` en `setBordersVisible()`.

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={['src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java']}
height='300px'
/>

:::tip Gegevens beheren en opvragen
Voor informatie over hoe je het `Repository` patroon kunt gebruiken om verzamelingen te beheren en op te vragen, zie de [Repository artikelen](/docs/advanced/repository/overview).
:::
