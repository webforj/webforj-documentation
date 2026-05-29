---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 46e92f0b5b3f1dafbf040176711ae5ac
---
De `Table`-component biedt verschillende selectiemogelijkheden. Er zijn methoden voor het selecteren van een enkel item, meerdere items of het programmatisch beheren van selecties.

:::tip Beheren en opvragen van gegevens
Voor informatie over het gebruik van het `Repository`-patroon om collecties te beheren en op te vragen, zie de [Repository-artikelen](/docs/advanced/repository/overview).
:::

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

De `Table`-componentenpakket genereert verschillende evenementen gerelateerd aan rijenselectie. Deze evenementen registreren wijzigingen in de selectietoestand van `Table`-rijen. Hieronder staan de belangrijkste selectie-evenementen met hun beschrijvingen:

>- `TableItemSelectEvent` - Geëmitteerd wanneer een tabelitem is geselecteerd.
>- `TableItemDeselectEvent` - Geëmitteerd wanneer een tabelitem is gedeselecteerd.
>- `TableItemSelectionChange` - Geëmitteerd wanneer de algehele selectie in de tabel verandert of wanneer een extra selectie is gekozen.

:::info
De `TableItemSelectEvent` en `TableItemDeselectEvent` worden niet geactiveerd wanneer de meervoudige selectiemodus actief is en de selectie wordt gemaakt via het selectievakje in de kop. In dit geval moet de `TableItemSelectionChange` in plaats daarvan worden gebruikt.
:::

In het onderstaande voorbeeld zal een `TableItemSelectEvent`-evenement worden geactiveerd wanneer een gebruiker een rij selecteert. Het evenement kan worden afgehandeld door een listener aan de tabel toe te voegen met behulp van de `onItemSelect()`-methode.

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

Selectie met selectievakjes is ingeschakeld wanneer de selectiemodus `MULTI` is, en stelt gebruikers in staat om eenvoudig een of meer items te selecteren met behulp van selectievakjes die aan elke rij zijn gekoppeld. Deze functie is bijzonder nuttig voor scenario's waarin gebruikers bulkacties op geselecteerde items moeten uitvoeren. De Table-klasse biedt methoden om de selectie met selectievakjes in te schakelen en aan te passen.

Met de `setCheckboxSelection(boolean checkboxSelection)`-methode kunnen selectievakjes worden geconfigureerd om naast elke rij te worden weergegeven, zodat gebruikers items kunnen selecteren. Het onderstaande programma toont meervoudige selectie en selectie met selectievakjes ingeschakeld:

<ComponentDemo
path='/webforj/tablemultiselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Programmatische selectie {#programatic-selection}

De `Table`-component biedt programmatische selectiemethoden, waarmee u geselecteerde items kunt manipuleren op basis van hun sleutels of de volledige items.

### Selecteren op sleutel {#select-by-key}

De `selectKey(Object... keys)`-methode stelt u in staat om programmatisch items te selecteren met behulp van hun sleutels. U kunt een of meer sleutels aan deze methode doorgeven, en deze zal de selectie dienovereenkomstig bijwerken.

### Selecteren van invoeritems {#selecting-entry-items}

Ten slotte stelt de `select(T... items)`-methode u in staat om programmatisch items te selecteren door een of meer items zelf aan deze methode door te geven om de selectie dienovereenkomstig bij te werken.
