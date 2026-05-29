---
title: File Chooser
sidebar_position: 10
_i18n_hash: 3fb68fdcc1fc0d263114babc2a64a6f4
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` is een gemodelleerde dialoog die is ontworpen om de gebruiker in staat te stellen een bestand of een map te selecteren uit het serversysteem. De dialoog blokkeert de uitvoering van de applicatie totdat de gebruiker een selectie maakt of de dialoog sluit.

<!-- INTRO_END -->

## Usages {#usages}

De `FileChooserDialog` biedt een manier om bestanden of mappen van het bestandssysteem te selecteren, zodat gebruikers mappen kunnen kiezen voor het opslaan van gegevens, of besturingshandelingen kunnen uitvoeren.

<ComponentDemo
path='/webforj/filechooserdialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java']}
height='600px'
/>

## Result {#result}

De `FileChooserDialog` retourneert het geselecteerde bestand of de geselecteerde map als een string. Als de gebruiker de dialoog sluit zonder een selectie te maken, zal het resultaat `null` zijn.

:::info
De resulterende string zal worden geretourneerd vanuit de `show()`-methode, of de equivalente `OptionDialog`-methode zoals hieronder weergegeven. 
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

## Selection mode {#selection-mode}

De `FileChooserDialog` ondersteunt verschillende selectie-modus, zodat je de selectiemethode kunt afstemmen op je specifieke behoeften:

1. **FILES**: Stelt de selectie van alleen bestanden toe.
2. **DIRECTORIES**: Stelt de selectie van alleen mappen toe.
3. **FILES_AND_DIRECTORIES**: Stelt de selectie van zowel bestanden als mappen toe.

## Initial path {#initial-path}

De `FileChooserDialog` stelt je in staat een initiële map op te geven waar de dialoog naar opent wanneer deze wordt weergegeven. Dit kan gebruikers een uitgangspunt bieden voor hun bestandselectie.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
String result = dialog.show();
```

## Restriction {#restriction}

Je kunt de dialoog beperken tot een specifieke map, zodat gebruikers niet buiten deze map kunnen navigeren met behulp van de `setRestricted(boolean restricted)`-methode.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Filters {#filters}

Wanneer de selectiemodus `FILES` is, staat de `FileChooserDialog` je toe om filters in te stellen om de soorten bestanden te beperken die worden weergegeven. Je kunt filters configureren met de `setFilters(List<FileChooserFilter> filters)`-methode.

<ComponentDemo
path='/webforj/filechooserdialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java']}
height='600px'
/>

### Custom filters {#custom-filters}

Je kunt gebruikers toestaan om aangepaste filters toe te voegen door de functie voor aangepaste filters in te schakelen met de `setCustomFilters(boolean customFilters)`-methode. Aangepaste filters worden standaard opgeslagen in de lokale opslag van de browser en hersteld wanneer de dialoog opnieuw wordt weergegeven.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Internationalization (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de component zijn volledig aanpasbaar met behulp van de `FileChooserI18n`-klasse. Deze flexibiliteit stelt je in staat om de dialooginterface aan te passen om te voldoen aan specifieke lokalisatievereisten of persoonlijke voorkeuren.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Kies");
i18n.setCancel("Annuleren");
dialog.setI18n(i18n);
```

## Best practices {#best-practices}

1. **Duidelijke en beknopte prompts**: Zorg ervoor dat het promptbericht duidelijk uitlegt wat de gebruiker wordt gevraagd te selecteren.
2. **Geschikte selectiemodi**: Kies selectiemodi die overeenkomen met de vereiste actie van de gebruiker om nauwkeurige en relevante selecties te garanderen.
3. **Logische initiële paden**: Stel initiële paden in die gebruikers een nuttig uitgangspunt bieden voor hun selectie.
4. **Beperk navigatie in mappen**: Beperk de dialoog tot een specifieke map wanneer dat nodig is om te voorkomen dat gebruikers naar ongeautoriseerde gebieden navigeren.
