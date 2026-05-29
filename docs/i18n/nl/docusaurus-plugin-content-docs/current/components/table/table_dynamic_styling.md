---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: 8f910c729d1108faeaba860a2e0f3546
---
<!-- vale off -->
# Dynamische styling <DocChip chip='since' label='25.00' />
<!-- vale on -->

In webforJ 25 en hoger is het mogelijk om individuele rijen en cellen in de tabel te stylen met aangepaste deelnamen. Deze namen kunnen dynamisch worden toegewezen op basis van de logica van jouw app, waardoor je fijnmazige controle hebt over het uiterlijk van de tabel.

## Rij styling {#row-styling}

De `setRowPartProvider()`-methode wijst deel-namen toe aan volledige rijen op basis van het gegeven item dat ze bevatten. Dit stelt je in staat om volledige rijen te markeren die aan specifieke voorwaarden voldoen—bijvoorbeeld, afwisselende achtergrondkleuren voor even rijen.

Deze stylenamen kunnen worden gericht met de `::part()` selector in je CSS.

:::tip Schaduw delen
De `::part()` selector is een speciale CSS-functie die je in staat stelt om elementen binnen de schaduw DOM van een component te stylen—zolang die elementen een `part` attribuut hebben. Dit is vooral nuttig voor het stylen van interne delen van webforJ-componenten, zoals rijen of cellen in een tabel.

Voor meer informatie over hoe schaduw delen werken en hoe je ze kunt definiëren en richten, zie de [Styling](../../styling/shadow-parts) sectie.
:::


<ComponentDemo
path='/webforj/tablerowstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableRowStylingView.java',
  'src/main/resources/static/css/table/table-row-styling-view.css',
]}
height='300px'
/>

## Cel styling {#cell-styling}

De `setCellPartProvider()`-methode styled individuele cellen op basis van zowel het gegeven item als de kolom waartoe ze behoren. Dit maakt het ideaal voor het markeren van specifieke waarden, zoals het benadrukken van leeftijden boven een drempel of ongeldige vermeldingen.

Net als rijdelen worden cel-delen gedefinieerd door een naam en gericht met de `::part()` selector.

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/resources/static/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## Reageren op data-updates {#reacting-to-data-updates}

Als jouw app gegevens programatisch wijzigt, zoals het bijwerken van de leeftijd van een gebruiker, zal de tabel automatisch opnieuw evalueren en alle bijbehorende rij- of cel-stijlen opnieuw toepassen zodra het bijgewerkte item in de repository is bevestigd.

In deze demo worden cellen in de Leeftijd kolom gestyled op basis van een drempel: leeftijden boven de 30 verschijnen groen, terwijl leeftijden van 30 en jonger rood verschijnen. Door op de knop te klikken, schakelt de leeftijd van Alice tussen 28 en 31, wat de `setCellPartProvider` activeert om de juiste stijl opnieuw toe te passen wanneer de gegevens zijn bevestigd.

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/resources/static/css/table/table-dynamic-styling-view.css',
]}
height='475px'
/>

## Gestreepte rijen {#striped-rows}

Schakel afwisselende achtergrondkleuren voor rijen in om de leesbaarheid te verbeteren:

```java
// Pas gestreepte rijstijl toe
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

De demo hieronder toont een eenvoudige manier om het visuele uiterlijk van jouw `Table` af te stemmen op de rest van jouw app met `setStriped()` en `setBordersVisible()`.

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={['src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java']}
height='300px'
/>

:::tip Beheren en opvragen van gegevens
Voor informatie over hoe je het `Repository`-patroon kunt gebruiken om verzamelingen te beheren en op te vragen, zie de [Repository-artikelen](/docs/advanced/repository/overview).
:::
