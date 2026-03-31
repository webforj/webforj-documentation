---
title: File Save
sidebar_position: 15
_i18n_hash: 728fd2b9211c993aed9b00abddb29b3e
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` is een modale dialoog die gebruikers in staat stelt om een bestand op te slaan op een specifieke locatie in het serverbestandssysteem. De dialoog blokkeert de uitvoering van de app totdat de gebruiker een bestandsnaam heeft opgegeven en de actie heeft bevestigd of de dialoog heeft geannuleerd.

<!-- INTRO_END -->

## Usages {#usages}

De `FileSaveDialog` biedt een gestroomlijnde methode voor het opslaan van bestanden op het bestandssysteem, met gebruiksvriendelijke opties voor het benoemen van bestanden en het omgaan met bestaande bestanden.

<ComponentDemo 
path='/webforj/filesavedialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java'
height = '800px'
/>

## Result {#result}

De `FileSaveDialog` retourneert het geselecteerde pad als een string. Als de gebruiker de dialoog annuleert, is het resultaat `null`.

:::important Doel van de dialoog
Deze dialoog slaat in werkelijkheid geen bestanden op, maar retourneert de bestandsnaam die de gebruiker heeft geselecteerd.
:::

:::info
De resulterende string wordt geretourneerd door de `show()`-methode of de equivalente `OptionDialog`-methode zoals hieronder weergegeven.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Sla uw bestand op", "/home/user/documents", "report.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Bestand opgeslagen in: " + path, "Pad Geselecteerd");
} else {
  OptionDialog.showMessageDialog("Geen pad geselecteerd", "Pad Geselecteerd",
      MessageDialog.MessageType.ERROR);
}
```

## Exists action {#exists-action}

De `FileSaveDialog` biedt configureerbaar gedrag wanneer er al een bestand met de opgegeven naam bestaat:

* **ACCEPT_WITHOUT_ACTION**: De selectie wordt geaccepteerd zonder extra actie van de gebruiker.
* **ERROR_DIALOGUE**: De gebruiker krijgt een foutdialoog te zien; de selectie is niet toegestaan.
* **CONFIRMATION_DIALOGUE**: De gebruiker krijgt een dialoog te zien waarin om bevestiging wordt gevraagd. Dit is de standaardinstelling.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Sla uw bestand op", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Selection mode {#selection-mode}

De `FileSaveDialog` ondersteunt verschillende selectiemodi, waardoor u de selectiemethode kunt aanpassen aan uw specifieke behoeften:

1. **FILES**: Staat alleen de selectie van bestanden toe.
2. **DIRECTORIES**: Staat alleen de selectie van mappen toe.
3. **FILES_AND_DIRECTORIES**: Staat de selectie van zowel bestanden als mappen toe.

## Initial path {#initial-path}

Specificeer de map waarin de dialoog geopend zal worden met behulp van het initiële pad. Dit helpt gebruikers om te starten in een logische map voor hun opslaan-operatie.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Sla uw bestand op", "/home/user/documents", "report.xls");
String result = dialog.show();
```

## Restriction {#restriction}

U kunt de dialoog beperken tot een specifieke map, waardoor gebruikers worden verhinderd om buiten deze map te navigeren met de methode `setRestricted(boolean restricted)`.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Sla uw bestand op", "/home/user/documents", "report.xls");
dialog.setRestricted(true);
dialog.show();
```

## Filename {#filename}

Stel een standaard bestandsnaam in voor de opslaan-operatie om gebruikers te begeleiden en fouten te minimaliseren.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Sla uw bestand op");
dialog.setName("report.xls");
String result = dialog.show();
```

## Internationalization (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de component zijn volledig aanpasbaar met behulp van de klasse `FileSaveI18n`. Dit zorgt ervoor dat de dialoog kan worden aangepast aan verschillende lokalisatie- of personalisatie-eisen.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Sla uw bestand op");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Kiezen");
i18n.setCancel("Annuleren");
dialog.setI18n(i18n);
```

## Filters {#filters}

De `FileSaveDialog` stelt u in staat om filters in te stellen om de types bestanden die kunnen worden opgeslagen te beperken met behulp van de methode `setFilters(List<FileSaveFilter> filters)`.

<ComponentDemo 
path='/webforj/filesavedialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java'
height = '800px'
/>

### Custom filters {#custom-filters}

U kunt aangepaste filters inschakelen zodat gebruikers hun eigen bestandsfilters kunnen definiëren met behulp van de methode `setCustomFilters(boolean customFilters)`. Filters worden standaard in de lokale opslag opgeslagen en hersteld bij volgende oproepen van de dialoog.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Sla uw bestand op", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Best practices {#best-practices}

* **Vooraf gedefinieerde bestandsnamen**: Bied een logische standaard bestandsnaam waar van toepassing.
* **Bevestig overschrijvingen**: Gebruik `CONFIRMATION_DIALOGUE` voor `ExistsAction` om onbedoelde overschrijvingen te voorkomen.
* **Intuïtief initiëel pad**: Stel een initiëel pad in dat aansluit bij de verwachtingen van de gebruiker.
* **Internationalisering**: Pas de tekst van de dialoog aan om de bruikbaarheid voor internationale doelgroepen te verbeteren.
* **Bestandstypefilters**: Maak gebruik van filters om bestandstypen te beperken en gebruikers te begeleiden naar geldige bestandsextensies.
