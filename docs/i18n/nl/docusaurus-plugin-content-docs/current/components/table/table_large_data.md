---
sidebar_position: 25
title: Grote Gegevenssets
slug: data
_i18n_hash: a8c510d518375e324ae1f1f0c95b5004
---
## Virtueel scrollen {#virtual-scrolling}

De `Table` component is gebouwd om efficiënt om te gaan met grote datasets door gebruik te maken van virtueel scrollen, een techniek die in webapplicaties wordt gebruikt om de weergave en prestaties van grote lijsten of tabellen te optimaliseren door alleen de zichtbare items op het scherm weer te geven.

### Eerste weergave {#initial-render}

Virtueel scrollen is een ontwerppatroon waarbij aanvankelijk slechts een klein subset van items die binnen het zichtbare gebied van de scrollbare container passen, wordt weergegeven. Dit minimaliseert het aantal aangemaakte DOM-elementen en versnelt het proces van de eerste weergave.

### Dynamisch laden {#dynamic-loading}

Naarmate de gebruiker naar beneden of omhoog scrolt, worden nieuwe items dynamisch in het beeld geladen. Deze items worden doorgaans opgehaald uit de gegevensbron op basis van de huidige scrollpositie.

### Itemrecycling {#item-recycling}

In plaats van voor elk item een nieuw DOM-element te creëren, hergebruikt virtueel scrollen vaak bestaande DOM-elementen. Zodra een item uit het zichtbare gebied beweegt, wordt het DOM-element gerecycled en opnieuw gebruikt voor een nieuw item dat het zichtbare gebied binnenkomt. Dit recyclingproces helpt om het geheugengebruik te verminderen en de prestaties te verbeteren.

### Prestatievoordelen: {#performance-benefits}

Het belangrijkste voordeel van virtueel scrollen is de verbeterde prestaties, vooral bij het omgaan met een groot aantal items. Het vermindert de hoeveelheid DOM-manipulatie en verbetert de algehele responsiviteit van de gebruikersinterface.

De onderstaande `Table` toont alle olympische winnaars - een grote dataset die enorm profiteert van de virtuele scrollfunctionaliteit van de tabel:

<ComponentDemo
path='/webforj/tableolympicwinners?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Overscan {#overscan}

Het instellen van de `Overscan` eigenschap van de tabel bepaalt hoeveel rijen buiten het zichtbare gebied moeten worden weergegeven. Deze instelling kan worden geconfigureerd met de `setOverscan(double value)` methode.

Een hogere overscanwaarde kan de frequentie van het renderen tijdens het scrollen verminderen, maar ten koste van het renderen van meer rijen dan op elk moment zichtbaar zijn. Dit kan een afruil zijn tussen renderprestaties en scrollsmoothness.
