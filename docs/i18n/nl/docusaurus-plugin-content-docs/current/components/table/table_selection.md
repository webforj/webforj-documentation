---
sidebar_position: 10
title: Selection
slug: selection
description: >-
  Configure single, multi, or no-selection modes on the Table and respond to row
  selection events with appropriate listeners.
_i18n_hash: 47f6b24c0a0621d810e5e2975a0b4749
---
De `Table`-component biedt verschillende selectiemogelijkheden. Er zijn methoden voor het selecteren van een enkel item, meerdere items of het programmatically beheren van selecties.

:::tip Gegevens beheren en opvragen
Voor informatie over het gebruik van het `Repository`-patroon om verzamelingen te beheren en te raadplegen, zie de [Repository-artikelen](/docs/advanced/repository/overview).
:::

## Selectiemodus {#selection-mode}

De selectiemodus in de tabel bepaalt hoe items door de gebruiker kunnen worden geselecteerd. Het biedt opties om het gedrag van itemselectie te configureren. De Table-klasse biedt een methode om de selectiemodus in te stellen:

```java
setSelectionMode(SelectionMode selectionMode)
```

Beschikbare opties voor SelectionMode zijn:

>- `SINGLE` - (enkele selectie)
>- `MULTI` - (meerdere selecties)
>- `NONE` - (geen selectie).

## Selectie-gebeurtenis {#selection-event}

Het `Table`-componentpakket geeft verschillende gebeurtenissen weer met betrekking tot rijenselectie. Deze gebeurtenissen vangen veranderingen in de selectiestatus van `Table`-rijen. Hieronder staan de belangrijkste selectiegemeenten met hun beschrijvingen:

>- `TableItemSelectEvent` - Emitted wanneer een tabelitem wordt geselecteerd.
>- `TableItemDeselectEvent` - Emitted wanneer een tabelitem wordt gedeselecteerd.
>- `TableItemSelectionChange` - Emitted wanneer de algehele selectie in de tabel verandert, of wanneer een aanvullende selectie wordt gemaakt.

:::info
De `TableItemSelectEvent` en `TableItemDeselectEvent` worden niet geactiveerd wanneer de modus voor meerdere selecties is ingeschakeld en de selectie wordt gemaakt via het selectievakje in de header. In dit geval moet `TableItemSelectionChange` worden gebruikt.
:::

In het onderstaande voorbeeld wordt een `TableItemSelectEvent`-gebeurtenis geactiveerd telkens wanneer een gebruiker een rij selecteert. De gebeurtenis kan worden afgehandeld door een luisteraar aan de tabel toe te voegen met de methode `onItemSelect()`.

<ComponentDemo
path='/webforj/tablesingleselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Selectie met selectievakjes {#checkbox-selection}

Selectie met selectievakjes is ingeschakeld wanneer de selectiemodus `MULTI` is, en stelt gebruikers in staat om een of meer items gemakkelijk te selecteren met behulp van selectievakjes die aan elke rij zijn gekoppeld. Deze functie is bijzonder nuttig voor scenario's waarin gebruikers bulkacties moeten uitvoeren op geselecteerde items. De Table-klasse biedt methoden om selectie met selectievakjes in te schakelen en aan te passen.

Door de methode `setCheckboxSelection(boolean checkboxSelection)` te gebruiken, kunnen selectievakjes worden geconfigureerd om naast elke rij te worden weergegeven, zodat gebruikers items kunnen selecteren. Het onderstaande programma toont meerdere selecties en ingeschakelde selectie met selectievakjes:

<ComponentDemo
path='/webforj/tablemultiselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Programmatically selecteren {#programmatic-selection}

De `Table`-component biedt programmatic selectiemethoden, waarmee je geselecteerde items kunt manipuleren, hetzij op basis van hun sleutels of op basis van de hele items.

### Selecteren op sleutel {#select-by-key}

De methode `selectKey(Object... keys)` stelt je in staat om programmatically items te selecteren met behulp van hun sleutels. Je kunt een of meer sleutels naar deze methode doorgeven, en het zal de selectie dienovereenkomstig bijwerken.

### Items selecteren {#selecting-entry-items}

Tot slot stelt de methode `select(T... items)` je in staat om programmatically items te selecteren door een of meer items zelf naar deze methode door te geven om de selectie dienovereenkomstig bij te werken.
