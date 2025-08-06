---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: c0e15da31dab7ea7701ea65b47ce67f8
---
De `Table` component biedt verschillende selectiemogelijkheden. Er zijn methodes voor het selecteren van een enkel item, meerdere items of het programmatisch beheren van selecties.

## Selectiemodus {#selection-mode}

De selectiemodus in de tabel bepaalt hoe items door de gebruiker kunnen worden geselecteerd. Het biedt opties om het gedrag van itemselectie te configureren. De Table-klasse biedt een methode om de selectiemodus in te stellen:

```java
setSelectionMode(SelectionMode selectionMode)
```

Beschikbare selectiemogelijkheden zijn:

>- `SINGLE` - (enkele selectie) 
>- `MULTI` - (meerdere selecties)
>- `NONE` - (geen selectie).

## Selectie-evenement {#selection-event}

Het `Table` component pakket genereert verschillende evenementen gerelateerd aan rijselectie. Deze evenementen registreren veranderingen in de selectietoestand van `Table` rijen. Hieronder staan de belangrijkste selectie-evenementen met hun beschrijvingen:

>- `TableItemSelectEvent` -  Geëmitteerd wanneer een tabelitem is geselecteerd.
>- `TableItemDeselectEvent` - Geëmitteerd wanneer een tabelitem is gedeselecteerd.
>- `TableItemSelectionChange` - Geëmitteerd wanneer de algehele selectie in de tabel verandert, of wanneer een aanvullende selectie wordt gekozen.

:::info
De `TableItemSelectEvent` en `TableItemDeselectEvent` worden niet geactiveerd wanneer de modus voor meerdere selecties actief is, en de selectie wordt gemaakt via het checkbox in de header. In dit geval moet de `TableItemSelectionChange` in plaats daarvan worden gebruikt.
:::

In het onderstaande voorbeeld zal een `TableItemSelectEvent` worden geactiveerd telkens wanneer een gebruiker een rij selecteert. Het evenement kan worden afgehandeld door een listener toe te voegen aan de tabel met behulp van de `onItemSelect()` methode.

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Checkboxselectie {#checkbox-selection}

Checkboxselectie is ingeschakeld wanneer de selectiemodus `MULTI` is, en stelt gebruikers in staat om gemakkelijk een of meer items te selecteren met behulp van checkboxen die aan elke rij zijn gekoppeld. Deze functie is bijzonder nuttig voor scenario's waar gebruikers bulkacties op geselecteerde items moeten uitvoeren. De Table-klasse biedt methoden om checkboxselectie in te schakelen en aan te passen.

Door gebruik te maken van de `setCheckboxSelection(boolean checkboxSelection)` methode, kunnen checkboxen worden geconfigureerd om weergegeven te worden naast elke rij, zodat gebruikers items kunnen selecteren. Het onderstaande programma toont zowel meerdere selecties als checkboxselectie ingeschakeld:

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Programmatische selectie {#programatic-selection}

De `Table` component biedt programmatische selectiemethoden, waarmee je geselecteerde items kunt manipuleren op basis van hun sleutels of door de volledige items.

### Selecteren op sleutel {#select-by-key}

De `selectKey(Object... keys)` methode stelt je in staat om items programmatisch te selecteren met behulp van hun sleutels. Je kunt een of meer sleutels aan deze methode doorgeven, en het zal de selectie dienovereenkomstig bijwerken.

### Selecteren op index {#select-by-index}

Met de `selectIndex(int... indices)` methode kun je een of meer indices aan de methode doorgeven en worden de geselecteerde items dienovereenkomstig bijgewerkt.

### Het selecteren van volledige items {#selecting-entire-items}

Ten slotte stelt de `select(T... items)` methode je in staat om items programmatisch te selecteren door een of meer items zelf aan deze methode door te geven om de selectie dienovereenkomstig bij te werken.
