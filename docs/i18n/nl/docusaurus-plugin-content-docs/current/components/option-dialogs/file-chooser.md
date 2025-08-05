---
sidebar_position: 10
title: File Chooser
_i18n_hash: d0efdadb8ec1e44cfab2fb26f95efa0d
---
# Bestandskiezer Dialoog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` is een modale dialoog die is ontworpen om de gebruiker in staat te stellen een bestand of een directory uit het serversysteem te selecteren. De dialoog blokkeert de app-uitvoering totdat de gebruiker een selectie maakt of de dialoog sluit.

```java
OptionDialog.showFileChooserDialog("Selecteer een bestand");
```

## Gebruik {#usages}

De `FileChooserDialog` biedt een manier om bestanden of directories van het bestandssysteem te selecteren, waardoor gebruikers directories kunnen kiezen voor het opslaan van gegevens of bewerkingen op bestanden kunnen uitvoeren.

<ComponentDemo 
path='/webforj/filechooserdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java'
height = '600px'
/>

## Resultaat {#result}

De `FileChooserDialog` retourneert het geselecteerde bestand of de directory als een string. Als de gebruiker de dialoog sluit zonder een selectie te maken, is het resultaat `null`.

:::info
De resulterende string zal worden geretourneerd vanuit de `show()`-methode, of de equivalente `OptionDialog`-methode zoals hieronder getoond.
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

De `FileChooserDialog` ondersteunt verschillende selectiemodi, zodat je de selectiemethode kunt afstemmen op je specifieke behoeften:

1. **FILES**: Sta alleen de selectie van bestanden toe.
2. **DIRECTORIES**: Sta alleen de selectie van directories toe.
3. **FILES_AND_DIRECTORIES**: Sta de selectie van zowel bestanden als directories toe.

## Initiële pad {#initial-path}

De `FileChooserDialog` stelt je in staat om een initiële pad op te geven die de dialoog opent wanneer deze wordt weergegeven. Dit kan gebruikers een startpunt bieden voor hun bestandselectie.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
String result = dialog.show();
```

## Beperking {#restriction}

Je kunt de dialoog beperken tot een specifieke directory, waardoor gebruikers worden verhinderd om daarbuiten te navigeren via de `setRestricted(boolean restricted)`-methode.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Filters {#filters}

Wanneer de selectiemodus `FILES` is, staat de `FileChooserDialog` je toe om filters in te stellen om de soorten bestanden die worden vermeld te beperken. Je kunt filters configureren met de `setFilters(List<FileChooserFilter> filters)`-methode.

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### Aangepaste filters {#custom-filters}

Je kunt gebruikers in staat stellen om aangepaste filters toe te voegen door de functie voor aangepaste filters in te schakelen met de `setCustomFilters(boolean customFilters)`-methode. Aangepaste filters worden standaard opgeslagen in de lokale opslag van de browser en hersteld wanneer de dialoog opnieuw wordt weergegeven.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Internationalisatie (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de component zijn volledig aanpasbaar met behulp van de `FileChooserI18n`-klasse. Deze flexibiliteit stelt je in staat om de dialooginterface aan te passen aan specifieke lokalisatievereisten of personalisatievoorkeuren.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Kies");
i18n.setCancel("Annuleren");
dialog.setI18n(i18n);
```

## Beste praktijken {#best-practices}

1. **Duidelijke en bondige prompts**: Zorg ervoor dat de promptmelding duidelijk uitlegt wat de gebruiker gevraagd wordt te selecteren.
2. **Geschikte selectiemodi**: Kies selectiemodi die overeenkomen met de vereiste gebruikersactie om nauwkeurige en relevante selecties te garanderen.
3. **Logische initiële paden**: Stel initiële paden in die gebruikers een nuttig startpunt bieden voor hun selectie.
4. **Beperk directory-navigatie**: Beperk de dialoog tot een specifieke directory wanneer nodig om te voorkomen dat gebruikers naar ongeoorloofde gebieden navigeren.
