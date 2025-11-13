---
sidebar_position: 21
title: Dynamische styling
slug: styling
_i18n_hash: 8e9f61685fbb3a7fb830463f1320e8cf
---
<!-- vale off -->
# Dynamische Styling <DocChip chip='since' label='25.00' />
<!-- vale on -->

In webforJ 25 en hoger is het mogelijk om individuele rijen en cellen in de Tabel te stylen met behulp van aangepaste deelnamen. Deze namen kunnen dynamisch worden toegewezen op basis van de logica van je app, waardoor je fijne controle hebt over het uiterlijk van de tabel.

## Rijstyling {#row-styling}

De `setRowPartProvider()`-methode wijst deelnamen toe aan hele rijen op basis van het gegevensitem dat ze bevatten. Hiermee kun je volledige rijen markeren die aan specifieke voorwaarden voldoen, bijvoorbeeld door afwisselende achtergrondkleuren voor even rijen te gebruiken.

Deze stijlnamen kunnen worden getarget met de `::part()`-selector in je CSS.

:::tip Schaduw onderdelen
De `::part()`-selector is een speciale CSS-functie waarmee je elementen binnen de shadow DOM van een component kunt stylen, zolang die elementen een `part`-attribuut hebben. Dit is vooral handig voor het stylen van interne onderdelen van webforJ-componenten, zoals rijen of cellen in een tabel.

Voor meer informatie over hoe schaduwonderdelen werken en hoe je ze kunt definiëren en targeten, zie de [Styling](../../styling/shadow-parts) sectie.
:::

<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Celstyling {#cell-styling}

De `setCellPartProvider()`-methode styleert individuele cellen op basis van zowel het gegevensitem als de kolom waartoe ze behoren. Dit maakt het ideaal voor het markeren van specifieke waarden, zoals het aanwijzen van leeftijden die een drempelwaarden overschrijden of ongeldige invoer.

Net als rijonderdelen worden celonderdelen gedefinieerd door een naam en getarget met de `::part()`-selector.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Reageren op gegevensupdates {#reacting-to-data-updates}

Als je app gegevens programmatig wijzigt, bijvoorbeeld door de leeftijd van een gebruiker bij te werken, zal de tabel automatisch opnieuw evalueren en alle bijbehorende rij- of celstijlen opnieuw toepassen zodra het bijgewerkte item in de repository is vastgelegd.

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

Configureer welke randen rond de `Table`, kolommen en rijen worden weergegeven:

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
Voor informatie over hoe je het `Repository`-patroon kunt gebruiken om collecties te beheren en op te vragen, zie de [Repository artikelen](/docs/advanced/repository/overview).
:::
