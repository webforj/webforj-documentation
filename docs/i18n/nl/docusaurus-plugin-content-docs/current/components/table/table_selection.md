---
sidebar_position: 10
title: Selection
slug: selection
description: >-
  Configure single, multi, or no-selection modes on the Table and respond to row
  selection events with appropriate listeners.
_i18n_hash: 3dc9f9e7462f97e260e1112a2966dc18
---
De `Table` component biedt verschillende selectiemogelijkheden. Er zijn methoden voor het selecteren van een enkel item, meerdere items of het programmatistisch beheren van selecties.

:::tip Beheren en opvragen van gegevens
Voor informatie over hoe u het `Repository`-patroon kunt gebruiken om collecties te beheren en op te vragen, zie de [Repository-artikelen](/docs/advanced/repository/overview).
:::

## Selectiemodus {#selection-mode}

De selectiemodus in de tabel bepaalt hoe items door de gebruiker kunnen worden geselecteerd. Het biedt opties voor het configureren van het gedrag van itemselectie. De Table-klasse biedt een methode om de selectiemodus in te stellen:

```java
setSelectionMode(SelectionMode selectionMode)
```

Beschikbare SelectieMode-opties zijn onder andere:

>- `SINGLE` - (enkele selectie)
>- `MULTI` - (meervoudige selectie)
>- `NONE` - (geen selectie).

## Selectie-evenement {#selection-event}

Het `Table`-componentpakket zendt verschillende evenementen uit die verband houden met rijenselectie. Deze evenementen vangen wijzigingen in de selectietoestand van `Table`-rijen. Hieronder staan de belangrijkste selecteerevenementen met hun beschrijvingen:

>- `TableItemSelectEvent` -  Uitgezonden wanneer een tabelitem wordt geselecteerd.
>- `TableItemDeselectEvent` - Uitgezonden wanneer een tabelitem wordt deselecteerd.
>- `TableItemSelectionChange` - Uitgezonden wanneer de algehele selectie in de tabel verandert, of wanneer een extra selectie wordt gekozen.

:::info
De `TableItemSelectEvent` en `TableItemDeselectEvent` worden niet geactiveerd wanneer de modus voor meervoudige selectie actief is en de selectie via de headercheckbox is gemaakt. In dit geval moet in plaats daarvan de `TableItemSelectionChange` worden gebruikt.
:::

In het onderstaande voorbeeld wordt een `TableItemSelectEvent`-evenement geactiveerd wanneer een gebruiker een rij selecteert. Het evenement kan worden verwerkt door een listener aan de tabel toe te voegen met de `onItemSelect()`-methode.

<ComponentDemo
path='/webforj/tablesingleselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Checkboxselectie {#checkbox-selection}

Checkboxselectie is ingeschakeld wanneer de selectiemodus `MULTI` is, en stelt gebruikers in staat om eenvoudig een of meer items te selecteren met behulp van checkboxes die aan elke rij zijn gekoppeld. Deze functie is bijzonder handig voor scenario's waarin gebruikers bulkacties op geselecteerde items moeten uitvoeren. De Table-klasse biedt methoden om checkboxselectie in te schakelen en aan te passen.

Door de methode `setCheckboxSelection(boolean checkboxSelection)` te gebruiken, kunnen checkboxen worden geconfigureerd om naast elke rij te worden weergegeven, zodat gebruikers items kunnen selecteren. Het onderstaande programma toont meervoudige selectie en checkboxselectie ingeschakeld:

<ComponentDemo
path='/webforj/tablemultiselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Programmaselectie {#programatic-selection}

De `Table`-component biedt programmatistische selectiemethoden, waarmee u geselecteerde items kunt manipuleren, hetzij op basis van hun sleutels, hetzij op basis van de volledige items.

### Selecteren op sleutel {#select-by-key}

De methode `selectKey(Object... keys)` stelt u in staat om programmatistisch items te selecteren met behulp van hun sleutels. U kunt een of meer sleutels aan deze methode doorgeven, en het zal de selectie dienovereenkomstig bijwerken.

### Het selecteren van invoeritems {#selecting-entry-items}

Ten slotte stelt de methode `select(T... items)` u in staat om programmatistisch items te selecteren door een of meer items zelf aan deze methode door te geven om de selectie dienovereenkomstig bij te werken.
