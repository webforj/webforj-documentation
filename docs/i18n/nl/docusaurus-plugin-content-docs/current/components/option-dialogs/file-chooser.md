---
title: File Chooser
sidebar_position: 10
_i18n_hash: 49a069004ead8d962b32e132183819e8
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` is een modale dialoog ontworpen om de gebruiker in staat te stellen een bestand of een map van het serversysteem te selecteren. De dialoog blokkeert de uitvoering van de app totdat de gebruiker een keuze maakt of de dialoog sluit.

<!-- INTRO_END -->

## Gebruik {#usages}

De `FileChooserDialog` biedt een manier om bestanden of mappen van het bestandssysteem te selecteren, waardoor gebruikers mappen kunnen kiezen voor het opslaan van gegevens of bestandoperaties kunnen uitvoeren.

<ComponentDemo 
path='/webforj/filechooserdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java'
height = '600px'
/>

## Resultaat {#result}

De `FileChooserDialog` retourneert het geselecteerde bestand of de geselecteerde map als een string. Als de gebruiker de dialoog sluit zonder een selectie te maken, zal het resultaat `null` zijn.

:::info
De resulterende string wordt geretourneerd vanuit de `show()`-methode, of de equivalente `OptionDialog`-methode zoals hieronder weergegeven. 
:::

```java showLineNumbers
String result = OptionDialog.showFileChooserDialog(
    "Selecteer een bestand", "/home/user", FileChooserDialog.SelectionMode.FILES);

if (result != null) {
    OptionDialog.showMessageDialog("U heeft geselecteerd: " + result, "Selectie gemaakt");
} else {
    OptionDialog.showMessageDialog("Geen selectie gemaakt", "Selectie geannuleerd");
}
```

## Selectiemodus {#selection-mode}

De `FileChooserDialog` ondersteunt verschillende selectiemodi, zodat u de selectiemethode kunt afstemmen op uw specifieke behoeften:

1. **BESTANDEN**: Sta alleen de selectie van bestanden toe.
2. **MAPPEN**: Sta alleen de selectie van mappen toe.
3. **BESTANDEN_EN_MAPPEN**: Sta de selectie van zowel bestanden als mappen toe.

## Aanvangspad {#initial-path}

De `FileChooserDialog` stelt u in staat om een aanvangspad op te geven waar de dialoog naartoe opent wanneer deze wordt weergegeven. Dit kan gebruikers een startpunt bieden voor hun bestandselectie.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
String result = dialog.show();
```

## Beperking {#restriction}

U kunt de dialoog beperken tot een specifieke map, zodat gebruikers niet buiten deze map kunnen navigeren met de `setRestricted(boolean restricted)`-methode.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Filters {#filters}

Wanneer de selectiemodus `BESTANDEN` is, staat de `FileChooserDialog` u toe filters in te stellen om de soorten bestanden te beperken die worden vermeld. U kunt filters configureren met de `setFilters(List<FileChooserFilter> filters)`-methode.

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### Aangepaste filters {#custom-filters}

U kunt gebruikers toestaan om aangepaste filters toe te voegen door de functie voor aangepaste filters in te schakelen met de `setCustomFilters(boolean customFilters)`-methode. Aangepaste filters worden standaard opgeslagen in de lokale opslag van de browser en hersteld wanneer de dialoog opnieuw wordt weergegeven.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Internationalisering (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de component zijn volledig aanpasbaar met de `FileChooserI18n`-klasse. Deze flexibiliteit stelt u in staat om de dialooginterface aan te passen aan specifieke lokalisatie-eisen of personalisatievoorkeuren.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Kies");
i18n.setCancel("Annuleren");
dialog.setI18n(i18n);
```

## Best practices {#best-practices}

1. **Duidelijke en beknopte aanwijzingen**: Zorg ervoor dat de aanwijzing duidelijk uitlegt wat de gebruiker wordt gevraagd te selecteren.
2. **Geschikte selectiemodi**: Kies selectiemodi die overeenkomen met de vereiste gebruikersactie om nauwkeurige en relevante selecties te garanderen.
3. **Logische aanvangspaden**: Stel aanvangspaden in die gebruikers een nuttig startpunt bieden voor hun selectie.
4. **Beperk directory-navigatie**: Beperk de dialoog tot een specifieke map wanneer dat nodig is om te voorkomen dat gebruikers naar onbevoegde gebieden navigeren.
