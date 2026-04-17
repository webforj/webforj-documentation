---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 35cb02c29edafbe9a0715b4630be56c1
---
De `Table` component biedt verschillende selectiemogelijkheden. Er zijn methoden voor het selecteren van een enkel item, meerdere items, of voor het programmatisch beheren van selecties.

:::tip Beheren en opvragen van gegevens
Voor informatie over hoe u het `Repository`-patroon kunt gebruiken om verzamelingen te beheren en op te vragen, zie de [Repository-artikelen](/docs/advanced/repository/overview).
:::

## Selectiemodus {#selection-mode}

De selectiemodus in de tabel bepaalt hoe items door de gebruiker kunnen worden geselecteerd. Het biedt opties voor het configureren van het gedrag van itemselectie. De Table-klasse biedt een methode om de selectiemodus in te stellen:

```java
setSelectionMode(SelectionMode selectionMode)
```

Beschikbare SelectieModus-opties zijn onder andere:

>- `SINGLE` - (enkele selectie) 
>- `MULTI` - (meerdere selecties)
>- `NONE` - (geen selectie).

## Selectie-evenement {#selection-event}

De `Table` componentpakketten geven verschillende evenementen uit die verband houden met rijselectie. Deze evenementen registreren wijzigingen in de selectiestatus van `Table`-rijen. Hieronder staan de belangrijkste selecties evenementen samen met hun beschrijvingen:

>- `TableItemSelectEvent` -  Wordt uitgezonden wanneer een tabelitem wordt geselecteerd.
>- `TableItemDeselectEvent` - Wordt uitgezonden wanneer een tabelitem wordt gedeselecteerd.
>- `TableItemSelectionChange` - Wordt uitgezonden wanneer de algehele selectie in de tabel verandert, of wanneer een aanvullende selectie wordt gekozen.

:::info
De `TableItemSelectEvent` en `TableItemDeselectEvent` worden niet geactiveerd wanneer de modus voor meerdere selecties actief is en de selectie via het selectievakje in de header wordt gemaakt. In dit geval dient de `TableItemSelectionChange` in plaats daarvan te worden gebruikt.
:::

In het onderstaande voorbeeld wordt een `TableItemSelectEvent`-evenement geactiveerd telkens wanneer een gebruiker een rij selecteert. Het evenement kan worden afgehandeld door een listener aan de tabel toe te voegen met behulp van de `onItemSelect()`-methode.

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Selectie met selectievakjes {#checkbox-selection}

Selectie met selectievakjes is ingeschakeld wanneer de selectiemodus `MULTI` is, en stelt gebruikers in staat om eenvoudig een of meer items te selecteren met behulp van selectievakjes die aan elke rij zijn gekoppeld. Deze functie is bijzonder handig voor scenario's waarin gebruikers bulkacties op geselecteerde items moeten uitvoeren. De Table-klasse biedt methoden om de selectie met selectievakjes in te schakelen en aan te passen.

Door de methode `setCheckboxSelection(boolean checkboxSelection)` te gebruiken, kunnen selectievakjes worden geconfigureerd om naast elke rij te worden weergegeven, zodat gebruikers items kunnen selecteren. Het onderstaande programma toont meerdere selectie en selectie met selectievakjes ingeschakeld:

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Programmatische selectie {#programatic-selection}

De `Table` component biedt programmatische selectiemethoden, waarmee u geselecteerde items kunt manipuleren op basis van hun sleutels of door de gehele items.

### Selecteren op sleutel {#select-by-key}

De methode `selectKey(Object... keys)` stelt u in staat om programmatisch items te selecteren met behulp van hun sleutels. U kunt een of meer sleutels aan deze methode doorgeven, en het zal de selectie dienovereenkomstig bijwerken.

### Geselecteerde ingangitems {#selecting-entry-items}

Ten slotte stelt de methode `select(T... items)` u in staat om programmatisch items te selecteren door een of meer items zelf aan deze methode door te geven om de selectie dienovereenkomstig bij te werken.
