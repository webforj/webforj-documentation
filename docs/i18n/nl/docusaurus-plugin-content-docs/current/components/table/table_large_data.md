---
sidebar_position: 25
title: Large Data Sets
slug: data
_i18n_hash: 83619419eb87c85aa5e309ee153af7fb
---
## Virtueel scrollen {#virtual-scrolling}

De `Table` component is gebouwd om efficiënt om te gaan met grote datasets door gebruik te maken van virtueel scrollen, een techniek die wordt gebruikt in webapplicaties om de weergave en prestaties van grote lijsten of tabellen te optimaliseren door alleen de zichtbare items op het scherm weer te geven.

### Eerste weergave {#initial-render}

Virtueel scrollen is een ontwerppatroon waarbij aanvankelijk alleen een klein subset items die binnen het zichtbare gebied van de scrollbare container passen, wordt weergegeven. Dit minimaliseert het aantal gemaakte DOM-elementen en versnelt het initiële weergaveproces.

### Dynamisch laden {#dynamic-loading}
Naarmate de gebruiker naar beneden of omhoog scrolt, worden nieuwe items dynamisch in het zicht geladen. Deze items worden doorgaans opgehaald uit de gegevensbron op basis van de huidige scrollpositie.

### Item recycling {#item-recycling}
In plaats van voor elk item een nieuw DOM-element te creëren, hergebruikt virtueel scrollen vaak bestaande DOM-elementen. Wanneer een item uit het zichtbare gebied beweegt, wordt het DOM-element gerecycled en herbestemd voor een nieuw item dat het zichtbare gebied binnenkomt. Dit recyclingproces helpt om het geheugengebruik te verminderen en de prestaties te verbeteren.

### Prestatien voordelen {#performance-benefits}

Het belangrijkste voordeel van virtueel scrollen is verbeterde prestaties, vooral bij het omgaan met een groot aantal items. Het vermindert de hoeveelheid DOM-manipulatie en verbetert de algehele responsiviteit van de gebruikersinterface.

De onderstaande `Table` toont alle olympische winnaars - een grote dataset die sterk profiteert van de virtuele scrollfunctionaliteit van de tabel:

<ComponentDemo
path='/webforj/tableolympicwinners?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Overscan {#overscan}

Het instellen van de `Overscan`-eigenschap van de tabel bepaalt hoeveel rijen er buiten het zichtbare gebied moeten worden weergegeven. Deze instelling kan worden geconfigureerd met de `setOverscan(double value)`-methode.

Een hogere overscanwaarde kan de frequentie van rendering tijdens het scrollen verminderen, maar dit ten koste van het weergeven van meer rijen dan op elk moment zichtbaar zijn. Dit kan een afweging zijn tussen renderingprestaties en scrollvloeiendheid.
