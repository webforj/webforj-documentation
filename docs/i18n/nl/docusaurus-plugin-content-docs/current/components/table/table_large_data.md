---
sidebar_position: 25
title: Grote datasets
slug: data
description: >-
  Handle large Table datasets efficiently with virtual scrolling, dynamic row
  loading, DOM recycling, and overscan tuning.
_i18n_hash: 63e45952d3d3f88d3aa435435c0905e6
---
## Virtueel scrollen {#virtual-scrolling}

De component `Table` is ontworpen om grote datasets efficiënt te verwerken met behulp van virtueel scrollen. Dit is een techniek die in webapplicaties wordt gebruikt om de rendering en prestaties van grote lijsten of tabellen te optimaliseren door alleen de items weer te geven die op het scherm zichtbaar zijn.

### Eerste rendering {#initial-render}

Virtueel scrollen is een ontwerppatroon waarbij aanvankelijk alleen een kleine subset van items wordt weergegeven die in het zichtbare gedeelte van de scrollbare container past. Hierdoor worden er minder DOM-elementen aangemaakt en verloopt het eerste renderen sneller.

### Dynamisch laden {#dynamic-loading}

Wanneer de gebruiker omlaag of omhoog scrolt, worden er dynamisch nieuwe items in de weergave geladen. Deze items worden doorgaans opgehaald uit de gegevensbron op basis van de huidige scrollpositie.

### Items hergebruiken {#item-recycling}

In plaats van voor elk item een nieuw DOM-element aan te maken, worden bij virtueel scrollen vaak bestaande DOM-elementen hergebruikt. Wanneer een item uit het zichtbare gebied verdwijnt, wordt het bijbehorende DOM-element opnieuw gebruikt voor een nieuw item dat in het zichtbare gebied verschijnt. Dit hergebruik helpt het geheugengebruik te verminderen en de prestaties te verbeteren.

### Prestatievoordelen {#performance-benefits}

Het belangrijkste voordeel van virtueel scrollen is betere performance, vooral bij een groot aantal items. Het vermindert het aantal DOM-manipulaties en maakt de gebruikersinterface over het algemeen responsiever.

De onderstaande `Table` toont alle olympische winnaars — een grote dataset die veel baat heeft bij de functionaliteit voor virtueel scrollen van de tabel:

<ComponentDemo
path='/webforj/tableolympicwinners'
files={['src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java']}
height='600px'
/>

## Overscan {#overscan}

Met de eigenschap `Overscan` van de tabel bepaal je hoeveel rijen buiten het zichtbare gebied worden gerenderd. Je kunt deze instelling configureren met de methode `setOverscan(double value)`.

Een hogere overscan-waarde kan de frequentie van rendering tijdens het scrollen verlagen, maar betekent wel dat er meer rijen worden gerenderd dan er op een bepaald moment zichtbaar zijn. Dit vormt een afweging tussen renderprestaties en soepel scrollen.
