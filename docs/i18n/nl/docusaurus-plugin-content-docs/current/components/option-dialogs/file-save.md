---
title: File Opslaan
sidebar_position: 15
description: >-
  Prompt users for a save location with the FileSaveDialog, configuring initial
  path, default filename, and overwrite behavior.
_i18n_hash: 861257322ca233cfa9e0eed5e4d3aa95
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` is een modaal dialoogvenster dat is ontworpen om gebruikers in staat te stellen een bestand op te slaan op een opgegeven locatie in het serverbestandssysteem. Het dialoogvenster blokkeert de uitvoering van de applicatie totdat de gebruiker een bestandsnaam opgeven en de actie bevestigt of het dialoogvenster annuleert.

<!-- INTRO_END -->

## Gebruik {#usages}

De `FileSaveDialog` biedt een gestroomlijnde methode om bestanden op te slaan naar het bestandssysteem, met gebruikersconfigureerbare opties voor bestandsnamen en het omgaan met bestaande bestanden.

<ComponentDemo
path='/webforj/filesavedialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java']}
height='800px'
/>

## Resultaat {#result}

De `FileSaveDialog` retourneert het geselecteerde pad als een string. Als de gebruiker het dialoogvenster annuleert, wordt de uitkomst `null`.

:::important Doel van het dialoogvenster
Dit dialoogvenster zorgt er niet voor dat er daadwerkelijk bestanden worden opgeslagen, maar retourneert de bestandsnaam die de gebruiker heeft geselecteerd.
:::

:::info
De resulterende string wordt geretourneerd vanuit de `show()`-methode of de equivalente `OptionDialog`-methode zoals hieronder gedemonstreerd.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Bewaar uw bestand", "/home/user/documents", "rapport.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Geselecteerd pad: " + result, "Pad Geselecteerd");
} else {
  OptionDialog.showMessageDialog("Er is geen pad geselecteerd", "Pad Geselecteerd",
      MessageDialog.MessageType.ERROR);
}
```

## Bestaan actie {#exists-action}

De `FileSaveDialog` biedt configureerbaar gedrag wanneer er al een bestand met de opgegeven naam bestaat:

* **ACCEPT_WITHOUT_ACTION**: De selectie wordt geaccepteerd zonder verdere actie van de gebruiker.
* **ERROR_DIALOGUE**: De gebruiker krijgt een foutdialoog te zien; de selectie is niet toegestaan.
* **CONFIRMATION_DIALOGUE**: De gebruiker krijgt een dialoog te zien waarin om bevestiging wordt gevraagd. Dit is de standaardinstelling.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Bewaar uw bestand", "/home/user/documents", "rapport.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Selectiemodus {#selection-mode}

De `FileSaveDialog` ondersteunt verschillende selectiemodi, waardoor je de selectie methode kunt afstemmen op je specifieke behoeften:

1. **BESTANDEN**: Maakt alleen de selectie van bestanden mogelijk.
2. **DIRECTORIES**: Maakt alleen de selectie van mappen mogelijk.
3. **BESTANDEN_EN_DIRECTORIES**: Maakt de selectie van zowel bestanden als mappen mogelijk.

## Beginpad {#initial-path}

Specificeer de directory waar het dialoogvenster zal openen met behulp van het beginpad. Dit helpt gebruikers om te beginnen in een logische directory voor hun opslaanoperatie.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Bewaar uw bestand", "/home/user/documents", "rapport.xls");
String result = dialog.show();
```

## Beperking {#restriction}

Je kunt het dialoogvenster beperken tot een specifieke directory, zodat gebruikers zich niet buiten deze directory kunnen navigeren met behulp van de `setRestricted(boolean restricted)`-methode.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Bewaar uw bestand", "/home/user/documents", "rapport.xls");
dialog.setRestricted(true);
dialog.show();
```

## Bestandsnaam {#filename}

Stel een standaard bestandsnaam in voor de opslaan-operatie om gebruikers te begeleiden en fouten te minimaliseren.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Bewaar uw bestand");
dialog.setName("rapport.xls");
String result = dialog.show();
```

## Internationalisatie (i18n) {#internationalization-i18n}

Gebruik `FileChooserI18n` om de titels, beschrijvingen, labels en berichten van het dialoogvenster aan te passen voor verschillende lokale instellingen of applicatie-eisen.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Bewaar uw bestand");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Kies");
i18n.setCancel("Annuleren");
dialog.setI18n(i18n);
```

## Filters {#filters}

De `FileSaveDialog` stelt je in staat om filters in te stellen om de types bestanden die opgeslagen kunnen worden te beperken met behulp van de `setFilters(List<FileChooserFilter> filters)`-methode.

<ComponentDemo
path='/webforj/filesavedialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java']}
height='800px'
/>

### Aangepaste filters {#custom-filters}

Je kunt aangepaste filters inschakelen zodat gebruikers hun eigen bestandsfilters kunnen definiëren met behulp van de `setCustomFilters(boolean customFilters)`-methode. Filters worden standaard opgeslagen in de lokale opslag en hersteld bij volgende oproepen van het dialoogvenster.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Bewaar uw bestand", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Best practices {#best-practices}

* **Vooraf gedefinieerde bestandsnamen**: Bied een logische standaardbestandsnaam waar van toepassing.
* **Bevestig overschrijvingen**: Gebruik `CONFIRMATION_DIALOGUE` voor `ExistsAction` om onopzettelijke overschrijvingen te voorkomen.
* **Intuïtief beginpad**: Stel een beginpad in dat overeenkomt met de verwachtingen van de gebruiker.
* **Internationalisatie**: Pas de tekst van het dialoogvenster aan om de bruikbaarheid voor internationale doelgroepen te verbeteren.
* **Bestandstypefilters**: Maak gebruik van filters om bestandstypen te beperken en gebruikers te begeleiden naar geldige bestandsextensies.
