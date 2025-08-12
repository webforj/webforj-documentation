---
sidebar_position: 10
title: File Chooser
_i18n_hash: 037ee8efaa2ed2f474d79c7e22dab3b5
---
# Bestandskiezer Dialoog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` is een modale dialoog ontworpen om de gebruiker in staat te stellen een bestand of een map van het serverbestandssysteem te selecteren. De dialoog blokkeert de uitvoering van de app totdat de gebruiker een selectie heeft gemaakt of de dialoog sluit.

```java
OptionDialog.showFileChooserDialog("Selecteer een bestand");
```

## Gebruik {#usages}

De `FileChooserDialog` biedt een manier om bestanden of mappen van het bestandssysteem te selecteren, waarmee gebruikers mappen kunnen kiezen om gegevens op te slaan of bestandsbewerkingen uit te voeren.

<ComponentDemo 
path='/webforj/filechooserdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java'
height = '600px'
/>

## Resultaat {#result}

De `FileChooserDialog` retourneert het geselecteerde bestand of de geselecteerde map als een string. Als de gebruiker de dialoog sluit zonder een selectie te maken, zal het resultaat `null` zijn.

:::info
De resulterende string wordt geretourneerd vanuit de `show()`-methode of de equivalente `OptionDialog`-methode zoals hieronder weergegeven.
:::

```java showLineNumbers
String result = OptionDialog.showFileChooserDialog(
    "Selecteer een bestand", "/home/user", FileChooserDialog.SelectionMode.FILES);

if (result != null) {
    OptionDialog.showMessageDialog("Je hebt geselecteerd: " + result, "Selectie gemaakt");
} else {
    OptionDialog.showMessageDialog("Geen selectie gemaakt", "Selectie geannuleerd");
}
```

## Selectiemodus {#selection-mode}

De `FileChooserDialog` ondersteunt verschillende selectiemodi, waarmee je de selectiemethode kunt afstemmen op jouw specifieke behoeften:

1. **BESTANDEN**: Staat alleen de selectie van bestanden toe.
2. **MAPPE**: Staat alleen de selectie van mappen toe.
3. **BESTANDEN_EN_MAPPE**: Staat de selectie van zowel bestanden als mappen toe.

## Beginpad {#initial-path}

De `FileChooserDialog` stelt je in staat om een beginpad op te geven waar de dialoog naar opent wanneer deze wordt weergegeven. Dit kan gebruikers een startpunt bieden voor hun bestandsselectie.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
String result = dialog.show();
```

## Beperking {#restriction}

Je kunt de dialoog beperken tot een specifieke map, zodat gebruikers niet buiten deze map kunnen navigeren met de `setRestricted(boolean restricted)`-methode.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Filters {#filters}

Wanneer de selectiemodus `BESTANDEN` is, staat de `FileChooserDialog` je toe om filters in te stellen om de soorten bestanden die worden weergegeven te beperken. Je kunt filters configureren met de `setFilters(List<FileChooserFilter> filters)`-methode.

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### Aangepaste filters {#custom-filters}

Je kunt gebruikers toestaan om aangepaste filters toe te voegen door de functie voor aangepaste filters in te schakelen met de `setCustomFilters(boolean customFilters)`-methode. Aangepaste filters worden standaard opgeslagen in de lokale opslag van de browser en hersteld wanneer de dialoog opnieuw wordt weergegeven.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Internationalisatie (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de component zijn volledig aanpasbaar met de `FileChooserI18n`-klasse. Deze flexibiliteit stelt je in staat om de dialooginterface aan te passen aan specifieke localisatie-eisen of personalisatievoorkeuren.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Kies een bestand", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Kiezen");
i18n.setCancel("Annuleren");
dialog.setI18n(i18n);
```

## Beste praktijken {#best-practices}

1. **Duidelijke en Bondige Berichten**: Zorg ervoor dat het bericht duidelijk uitlegt wat de gebruiker wordt gevraagd te selecteren.
2. **Geschikte Selectiemodi**: Kies selectiemodi die overeenkomen met de vereiste actie van de gebruiker om nauwkeurige en relevante selecties te garanderen.
3. **Logische Beginpaden**: Stel beginpaden in die gebruikers een nuttig startpunt bieden voor hun selectie.
4. **Beperk Directory-navigatie**: Beperk de dialoog tot een specifieke map wanneer dat nodig is om te voorkomen dat gebruikers naar onbevoegde gebieden navigeren.
