---
title: File Save
sidebar_position: 15
_i18n_hash: 7cad72847c86a30f8ad6000a283a51c2
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` is een modale dialoog ontworpen om gebruikers in staat te stellen een bestand op te slaan op een opgegeven locatie op het serverbestandssysteem. De dialoog blokkeert de uitvoering van de app totdat de gebruiker een bestandsnaam opgeeft en de actie bevestigt of de dialoog annuleert.

<!-- INTRO_END -->

## Usages {#usages}

De `FileSaveDialog` biedt een gestroomlijnde methode voor het opslaan van bestanden op het bestandssysteem en biedt gebruikers configureerbare opties voor bestandsnaamgeving en het omgaan met bestaande bestanden.

<ComponentDemo
path='/webforj/filesavedialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java']}
height='800px'
/>

## Result {#result}

De `FileSaveDialog` retourneert het geselecteerde pad als een tekenreeks. Als de gebruiker de dialoog annuleert, is het resultaat `null`.

:::important Doel van de dialoog
Deze dialoog zorgt er niet voor dat bestanden daadwerkelijk worden opgeslagen, maar retourneert de bestandsnaam die de gebruiker heeft geselecteerd.
:::

:::info
De resulterende tekenreeks wordt geretourneerd vanuit de `show()`-methode of de equivalente `OptionDialog`-methode zoals hieronder aangetoond.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Opslaan van uw bestand", "/home/user/documents", "report.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Bestand opgeslagen op: " + path, "Pad Geselecteerd");
} else {
  OptionDialog.showMessageDialog("Geen pad geselecteerd", "Pad Geselecteerd",
      MessageDialog.MessageType.ERROR);
}
```

## Exists action {#exists-action}

De `FileSaveDialog` biedt configureerbaar gedrag wanneer een bestand met de opgegeven naam al bestaat:

* **ACCEPT_WITHOUT_ACTION**: De selectie wordt geaccepteerd zonder verdere actie van de gebruiker.
* **ERROR_DIALOGUE**: De gebruiker krijgt een foutdialoog te zien; de selectie is niet toegestaan.
* **CONFIRMATION_DIALOGUE**: De gebruiker krijgt een dialoog te zien waarin om bevestiging wordt gevraagd. Dit is de standaardoptie.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Opslaan van uw bestand", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Selection mode {#selection-mode}

De `FileSaveDialog` ondersteunt verschillende selectiemodi, waarmee u de selectiemethode kunt aanpassen aan uw specifieke behoeften:

1. **FILES**: staat alleen de selectie van bestanden toe.
2. **DIRECTORIES**: staat alleen de selectie van mappen toe.
3. **FILES_AND_DIRECTORIES**: staat de selectie van zowel bestanden als mappen toe.

## Initial path {#initial-path}

Specificeer de map waarin de dialoog zal openen met behulp van het initiële pad. Dit helpt gebruikers te starten in een logische map voor hun opslagoperatie.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Opslaan van uw bestand", "/home/user/documents", "report.xls");
String result = dialog.show();
```

## Restriction {#restriction}

U kunt de dialoog beperken tot een specifieke map, waardoor gebruikers worden verhinderd om buiten de map te navigeren met behulp van de `setRestricted(boolean restricted)`-methode.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Opslaan van uw bestand", "/home/user/documents", "report.xls");
dialog.setRestricted(true);
dialog.show();
```

## Filename {#filename}

Stel een standaard bestandsnaam in voor de opslagoperatie om gebruikers te begeleiden en fouten te minimaliseren.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Opslaan van uw bestand");
dialog.setName("report.xls");
String result = dialog.show();
```

## Internationalization (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de component zijn volledig aanpasbaar met behulp van de `FileSaveI18n`-klasse. Dit zorgt ervoor dat de dialoog kan worden aangepast aan verschillende lokalisatie- of personalisatievereisten.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Kiezen");
i18n.setCancel("Annuleren");
dialog.setI18n(i18n);
```

## Filters {#filters}

De `FileSaveDialog` stelt u in staat om filters in te stellen om de soorten bestanden die kunnen worden opgeslagen te beperken met behulp van de `setFilters(List<FileSaveFilter> filters)`-methode.

<ComponentDemo
path='/webforj/filesavedialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java']}
height='800px'
/>

### Custom filters {#custom-filters}

U kunt aangepaste filters inschakelen om gebruikers in staat te stellen hun eigen bestandsfilters te definiëren met behulp van de `setCustomFilters(boolean customFilters)`-methode. Filters worden standaard opgeslagen in de lokale opslag en hersteld bij volgende oproepen van de dialoog.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Opslaan van uw bestand", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Best practices {#best-practices}

* **Vooraf gedefinieerde bestandsnamen**: Bied een logische standaard bestandsnaam waar mogelijk.
* **Bevestigen van overschrijvingen**: Gebruik `CONFIRMATION_DIALOGUE` voor `ExistsAction` om per ongeluk overschrijven te voorkomen.
* **Intuïtief initiël pad**: Stel een initiël pad in dat overeenkomt met de verwachtingen van de gebruiker.
* **Internationalization**: Pas de dialoogtekst aan om de bruikbaarheid voor internationale doelgroepen te verbeteren.
* **Bestandstypefilters**: Maak gebruik van filters om bestandstypen te beperken en gebruikers te begeleiden naar geldige bestandsextensies.
