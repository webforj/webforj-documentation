---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 203177b6049bc42493e3d0dbc0bf5233
---
De `Table`-component biedt verschillende selectiemogelijkheden. Er zijn methoden voor het selecteren van een enkel item, meerdere items of het programmatically beheren van selecties.

:::tip Beheren en opvragen van gegevens
Voor informatie over hoe u het `Repository`-patroon kunt gebruiken om verzamelingen te beheren en op te vragen, zie de [Repository-artikelen](/docs/advanced/repository/overview).
:::

## Selectiemodus {#selection-mode}

De selectiemodus in de tabel bepaalt hoe items door de gebruiker kunnen worden geselecteerd. Het biedt opties voor het configureren van het gedrag van itemselectie. De Table-klasse biedt een methode om de selectiemodus in te stellen:

```java
setSelectionMode(SelectionMode selectionMode)
```

Beschikbare opties voor SelectionMode zijn:

>- `SINGLE` - (enkele selectie) 
>- `MULTI` - (meerdere selecties)
>- `NONE` - (geen selectie).

## Selectie-evenement {#selection-event}

Het `Table`-componentpakket geeft verschillende evenementen weer die verband houden met rijselectie. Deze evenementen registreren wijzigingen in de selectiestatus van `Table`-rijen. Hieronder volgen de belangrijkste selectievevenementen samen met hun beschrijvingen:

>- `TableItemSelectEvent` -  Verzonden wanneer een tabelitem wordt geselecteerd.
>- `TableItemDeselectEvent` - Verzonden wanneer een tabelitem wordt gedeselecteerd.
>- `TableItemSelectionChange` - Verzonden wanneer de algehele selectie in de tabel verandert of wanneer een aanvullende selectie wordt gekozen.

:::info
De `TableItemSelectEvent` en `TableItemDeselectEvent` worden niet geactiveerd wanneer de modus voor meerdere selecties actief is en de selectie via het kopvak wordt gemaakt. In dit geval moet de `TableItemSelectionChange` worden gebruikt.
:::

In het onderstaande voorbeeld wordt een `TableItemSelectEvent`-evenement geactiveerd telkens wanneer een gebruiker een rij selecteert. Het evenement kan worden behandeld door een luisteraar aan de tabel toe te voegen met de `onItemSelect()`-methode.

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Checkbox-selectie {#checkbox-selection}

Checkbox-selectie is ingeschakeld wanneer de selectiemodus `MULTI` is en stelt gebruikers in staat om op een gemakkelijke manier een of meer items te selecteren met behulp van selectievakjes die aan elke rij zijn gekoppeld. Deze functie is vooral nuttig voor scenario's waarin gebruikers bulkacties op geselecteerde items moeten uitvoeren. De Table-klasse biedt methoden om checkbox-selectie in te schakelen en aan te passen.

Door de methode `setCheckboxSelection(boolean checkboxSelection)` te gebruiken, kunnen selectievakjes worden geconfigureerd om naast elke rij te worden weergegeven, zodat gebruikers items kunnen selecteren. Het programma hieronder toont meerdere selecties en ingeschakelde checkbox-selectie:

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Programmaconselectie {#programatic-selection}

De `Table`-component biedt methoden voor programmaconselectie, waarmee u geselecteerde items kunt manipuleren op basis van hun sleutels of op basis van de gehele items. 

### Selecteren op sleutel {#select-by-key}

De methode `selectKey(Object... keys)` stelt u in staat om items programmatically te selecteren met behulp van hun sleutels. U kunt een of meer sleutels aan deze methode doorgeven en het zal de selectie dienovereenkomstig bijwerken.

### Selecteren op index {#select-by-index}

Met de methode `selectIndex(int... indices)` kunt u een of meer indices aan de methode doorgeven en worden de geselecteerde items dienovereenkomstig bijgewerkt.

### Gehele items selecteren {#selecting-entire-items}

Tot slot stelt de methode `select(T... items)` u in staat om programmatically items te selecteren door een of meer items zelf aan deze methode door te geven om de selectie dienovereenkomstig bij te werken.
