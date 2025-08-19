---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 91df1121dac6d410883e3b43ddf767d5
---
De `Table`-component biedt verschillende selectiemogelijkheden. Er zijn methoden voor het selecteren van een enkel item, meerdere items of het programmatig beheren van selecties.

## Selectiemodus {#selection-mode}

De selectiemodus in de tabel bepaalt hoe items door de gebruiker kunnen worden geselecteerd. Het biedt opties voor het configureren van het gedrag van itemselectie. De Table-klasse biedt een methode om de selectiemodus in te stellen:

```java
setSelectionMode(SelectionMode selectionMode)
```

Beschikbare SelectionMode-opties zijn onder andere:

>- `SINGLE` - (enkele selectie) 
>- `MULTI` - (meervoudige selectie)
>- `NONE` - (geen selectie).

## Selectie-evenement {#selection-event}

De `Table`-componentpakket genereert verschillende evenementen die verband houden met rijselectie. Deze evenementen vangen wijzigingen in de selectietoestand van `Table`-rijen. Hieronder staan de belangrijkste selectie-evenementen met hun beschrijvingen:

>- `TableItemSelectEvent` -  Geactiveerd wanneer een tabelitem is geselecteerd.
>- `TableItemDeselectEvent` - Geactiveerd wanneer een tabelitem is gedeselecteerd.
>- `TableItemSelectionChange` - Geactiveerd wanneer de algehele selectie in de tabel verandert, of wanneer een extra selectie wordt gekozen.

:::info
De `TableItemSelectEvent` en `TableItemDeselectEvent` worden niet geactiveerd wanneer de meervoudige selectiemodus actief is en de selectie via het headerselectievakje wordt gemaakt. In dit geval moet de `TableItemSelectionChange` worden gebruikt.
:::

In het onderstaande voorbeeld wordt een `TableItemSelectEvent`-evenement geactiveerd telkens wanneer een gebruiker een rij selecteert. Het evenement kan worden afgehandeld door een listener aan de tabel toe te voegen met de methode `onItemSelect()`.

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Checkboxselectie {#checkbox-selection}

Checkboxselectie is ingeschakeld wanneer de selectiemodus `MULTI` is, en stelt gebruikers in staat om gemakkelijk een of meer items te selecteren met behulp van selectievakjes die aan elke rij zijn gekoppeld. Deze functie is bijzonder nuttig in scenario's waarin gebruikers bulkacties op geselecteerde items moeten uitvoeren. De Table-klasse biedt methoden om checkboxselectie in te schakelen en aan te passen.

Met de methode `setCheckboxSelection(boolean checkboxSelection)` kunnen selectievakjes worden geconfigureerd en weergegeven naast elke rij, zodat gebruikers items kunnen selecteren. Het onderstaande programma laat meerdere selecties en checkboxselectie ingeschakeld zien:

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Programmaticale selectie {#programatic-selection}

De `Table`-component biedt programmatic selectie methoden, waarmee je geselecteerde items kunt manipuleren op basis van hun sleutels of door de gehele items.

### Selectie op sleutel {#select-by-key}

De methode `selectKey(Object... keys)` maakt het mogelijk om items programmatig te selecteren met behulp van hun sleutels. Je kunt een of meer sleutels aan deze methode doorgeven, en deze zal de selectie dienovereenkomstig bijwerken.

### Selectie op index {#select-by-index}

Door de methode `selectIndex(int... indices)` te gebruiken, kun je een of meer indices aan de methode doorgeven en worden de geselecteerde items dienovereenkomstig bijgewerkt.

### Hele items selecteren {#selecting-entire-items}

Ten slotte maakt de methode `select(T... items)` het mogelijk om items programmatig te selecteren door een of meer items zelf aan deze methode door te geven om de selectie dienovereenkomstig bij te werken.
