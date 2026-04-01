---
title: File Chooser
sidebar_position: 10
_i18n_hash: c8d1ebc420bc1e1749c5c98a9fd3284c
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` is een modaal dialoogvenster dat is ontworpen om de gebruiker in staat te stellen een bestand of een map uit het besturingssysteem van de server te selecteren. Het dialoogvenster blokkeert de uitvoering van de app totdat de gebruiker een keuze maakt of het dialoogvenster sluit.

<!-- INTRO_END -->

## Usages {#usages}

De `FileChooserDialog` biedt een manier om bestanden of mappen uit het bestandssysteem te selecteren, zodat gebruikers mappen kunnen kiezen voor het opslaan van gegevens of bestandbewerkingen kunnen uitvoeren.

<ComponentDemo 
path='/webforj/filechooserdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java'
height = '600px'
/>

## Result {#result}

De `FileChooserDialog` retourneert het geselecteerde bestand of de map als een string. Als de gebruiker het dialoogvenster sluit zonder een selectie te maken, is het resultaat `null`.

:::info
De resulterende string wordt geretourneerd vanuit de `show()`-methode, of de equivalente `OptionDialog`-methode zoals hieronder weergegeven. 
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

## Selection mode {#selection-mode}

De `FileChooserDialog` ondersteunt verschillende selectiemodi, zodat je de selectiemethode kunt afstemmen op je specifieke behoeften:

1. **FILES**: Staat alleen de selectie van bestanden toe.
2. **DIRECTORIES**: Staat alleen de selectie van mappen toe.
3. **FILES_AND_DIRECTORIES**: Staat de selectie van zowel bestanden als mappen toe.

## Initial path {#initial-path}

De `FileChooserDialog` stelt je in staat om een initiële pad op te geven waar het dialoogvenster naar opent wanneer het wordt weergegeven. Dit kan gebruikers een startpunt bieden voor hun bestandselectie.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
String result = dialog.show();
```

## Restriction {#restriction}

Je kunt het dialoogvenster beperken tot een specifieke map, zodat gebruikers niet buiten deze map kunnen navigeren met de `setRestricted(boolean restricted)`-methode.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Filters {#filters}

Wanneer de selectiemodus `FILES` is, staat de `FileChooserDialog` je toe om filters in te stellen om de soorten bestanden die worden weergegeven te beperken. Je kunt filters configureren met de `setFilters(List<FileChooserFilter> filters)`-methode.

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### Custom filters {#custom-filters}

Je kunt gebruikers in staat stellen om aangepaste filters toe te voegen door de functie voor aangepaste filters in te schakelen met de `setCustomFilters(boolean customFilters)`-methode. Aangepaste filters worden standaard opgeslagen in de lokale opslag van de browser en hersteld wanneer het dialoogvenster opnieuw wordt weergegeven.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Selecteer een bestand", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Internationalization (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de component zijn volledig aanpasbaar met de `FileChooserI18n`-klasse. Deze flexibiliteit stelt je in staat om de interface van het dialoogvenster af te stemmen op specifieke lokalisatievereisten of personalisatievoorkeuren.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Kies een bestand", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Kiezen");
i18n.setCancel("Annuleren");
dialog.setI18n(i18n);
```

## Best practices {#best-practices}

1. **Duidelijke en Bondige Berichten**: Zorg ervoor dat het bericht de gebruiker duidelijk uitlegt wat er wordt gevraagd.
2. **Geschikte Selectiemodi**: Kies selectiemodi die overeenkomen met de benodigde gebruikersactie om nauwkeurige en relevante selecties te waarborgen.
3. **Logische Initiële Paden**: Stel initiële paden in die gebruikers een nuttig startpunt bieden voor hun selectie.
4. **Beperk Navigatie in Mappen**: Beperk het dialoogvenster tot een specifieke map wanneer nodig om te voorkomen dat gebruikers naar ongeoorloofde gebieden navigeren.
