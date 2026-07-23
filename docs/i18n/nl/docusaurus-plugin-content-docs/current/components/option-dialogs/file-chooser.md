---
title: File Chooser
sidebar_position: 10
description: >-
  Open a blocking FileChooserDialog to let users pick files or directories from
  the server, with selection modes and initial paths.
_i18n_hash: c86dfab4207241cab3bb28da3e1236ab
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` is een modaal dialoogvenster dat is ontworpen om de gebruiker in staat te stellen een bestand of een map uit het besturingssysteem van de server te selecteren. Het dialoogvenster blokkeert de uitvoering van de app totdat de gebruiker een selectie maakt of het dialoogvenster sluit.

<!-- INTRO_END -->

## Usages {#usages}

De `FileChooserDialog` biedt een manier om bestanden of mappen uit het besturingssysteem te selecteren, zodat gebruikers mappen kunnen kiezen voor het opslaan van gegevens of bestandshandelingen kunnen uitvoeren.

<ComponentDemo
path='/webforj/filechooserdialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java']}
height='600px'
/>

## Result {#result}

De `FileChooserDialog` retourneert het geselecteerde bestand of de map als een string. Als de gebruiker het dialoogvenster sluit zonder een selectie te maken, is het resultaat `null`.

:::info
De resulterende string zal worden geretourneerd vanuit de `show()` methode, of de equivalente `OptionDialog` methode zoals hieronder weergegeven.
:::

```java showLineNumbers
String result = OptionDialog.showFileChooserDialog(
  "Selecteer een bestand", "/home/user", FileChooserDialog.SelectionMode.FILES);

if (result != null) {
  OptionDialog.showMessageDialog("U hebt geselecteerd: " + result, "Selectie gemaakt");
} else {
  OptionDialog.showMessageDialog("Geen selectie gemaakt", "Selectie geannuleerd");
}
```

## Selection mode {#selection-mode}

De `FileChooserDialog` ondersteunt verschillende selectiemodi, waarmee u de selectie-methode kunt afstemmen op uw specifieke behoeften:

1. **FILES**: Maakt de selectie van alleen bestanden mogelijk.
2. **DIRECTORIES**: Maakt de selectie van alleen mappen mogelijk.
3. **FILES_AND_DIRECTORIES**: Maakt de selectie van zowel bestanden als mappen mogelijk.

## Initial path {#initial-path}

De `FileChooserDialog` stelt u in staat om een initiële pad op te geven die het dialoogvenster zal openen wanneer het wordt weergegeven. Dit kan gebruikers een startpunt bieden voor hun bestandsselectie.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
String result = dialog.show();
```

## Restriction {#restriction}

U kunt het dialoogvenster beperken tot een specifieke map, waardoor gebruikers worden voorkomen dat ze buiten deze map navigeren met de `setRestricted(boolean restricted)` methode.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Filters {#filters}

Wanneer de selectiemodus `FILES` is, staat de `FileChooserDialog` u toe om filters in te stellen om de types bestanden die worden weergegeven te beperken. U kunt filters configureren met behulp van de `setFilters(List<FileChooserFilter> filters)` methode.

<ComponentDemo
path='/webforj/filechooserdialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java']}
height='600px'
/>

### Custom filters {#custom-filters}

U kunt gebruikers toestaan om aangepaste filters toe te voegen door de functie voor aangepaste filters in te schakelen met de `setCustomFilters(boolean customFilters)` methode. Aangepaste filters worden standaard opgeslagen in de lokale opslag van de browser en hersteld wanneer het dialoogvenster opnieuw wordt weergegeven.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Internationalization (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de component zijn volledig aanpasbaar met behulp van de `FileChooserI18n` klasse. Deze flexibiliteit stelt u in staat om de interface van het dialoogvenster aan te passen aan specifieke lokalisatievereisten of persoonlijke voorkeuren.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Kiezen");
i18n.setCancel("Annuleren");
dialog.setI18n(i18n);
```

## Best practices {#best-practices}

1. **Duidelijke en bondige prompts**: Zorg ervoor dat de prompt duidelijk uitlegt wat de gebruiker wordt gevraagd te selecteren.
2. **Geschikte selectiemodi**: Kies selectiemodi die overeenkomen met de vereiste gebruikersactie om nauwkeurige en relevante selecties te waarborgen.
3. **Logische initiële paden**: Stel initiële paden in die gebruikers een nuttig startpunt bieden voor hun selectie.
4. **Beperk navigatie in mappen**: Beperk het dialoogvenster tot een specifieke map indien nodig om te voorkomen dat gebruikers naar niet-geautoriseerde gebieden navigeren.
