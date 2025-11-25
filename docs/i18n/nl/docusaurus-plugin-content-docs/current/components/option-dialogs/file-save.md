---
sidebar_position: 15
title: File Opslaan
_i18n_hash: 9f5ecfb61386cfa8c4eb3c31305b1838
---
# Bestandsopslag Dialoog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` is een modale dialoog ontworpen om gebruikers in staat te stellen een bestand op een opgegeven locatie op het serverbestandssysteem op te slaan. De dialoog blokkeert de uitvoering van de app totdat de gebruiker een bestandsnaam opgeeft en de actie bevestigt of de dialoog annuleert.

```java
OptionDialog.showFileSaveDialog("Bewaar je bestand");
```

## Toepassingen {#usages}

De `FileSaveDialog` biedt een gestroomlijnde methode voor het opslaan van bestanden naar het bestandssysteem, met gebruiksvriendelijke opties voor bestandsnaam en het omgaan met bestaande bestanden.

<ComponentDemo 
path='/webforj/filesavedialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java'
height = '800px'
/>

## Resultaat {#result}

De `FileSaveDialog` retourneert het geselecteerde pad als een string. Als de gebruiker de dialoog annuleert, is het resultaat `null`.

:::important Doel van de Dialoog
Deze dialoog voorkomt niet daadwerkelijk dat bestanden worden opgeslagen, maar retourneert de bestandsnaam die de gebruiker heeft geselecteerd.
:::

:::info
De resulterende string wordt teruggegeven vanuit de `show()`-methode of de equivalente `OptionDialog`-methode zoals hieronder gedemonstreerd.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Bewaar je bestand", "/home/user/documents", "rapport.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Bestand opgeslagen naar: " + path, "Geselecteerd Pad");
} else {
  OptionDialog.showMessageDialog("Geen pad geselecteerd", "Geselecteerd Pad",
      MessageDialog.MessageType.ERROR);
}
```

## Bestaande actie {#exists-action}

De `FileSaveDialog` biedt configureerbaar gedrag wanneer een bestand met de opgegeven naam al bestaat:

* **ACCEPT_WITHOUT_ACTION**: De selectie wordt geaccepteerd zonder extra actie van de gebruiker.
* **ERROR_DIALOGUE**: De gebruiker krijgt een foutdialoog; de selectie is niet toegestaan.
* **CONFIRMATION_DIALOGUE**: De gebruiker krijgt een dialoog waarin bevestiging wordt gevraagd. Dit is de standaard.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Bewaar je bestand", "/home/user/documents", "rapport.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Selectiemodus {#selection-mode}

De `FileSaveDialog` ondersteunt verschillende selectiemodi, zodat u de selectiemethode kunt afstemmen op uw specifieke behoeften:

1. **BESTANDEN**: Staat alleen de selectie van bestanden toe.
2. **DIRECTORIES**: Staat alleen de selectie van directories toe.
3. **BESTANDEN_EN_DIRECTORIES**: Staat de selectie van zowel bestanden als directories toe.

## Aanvangspad {#initial-path}

Geef de directory op waar de dialoog zal openen met behulp van het aanvangspad. Dit helpt gebruikers te starten in een logische directory voor hun opslaan operatie.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Bewaar je bestand", "/home/user/documents", "rapport.xls");
String result = dialog.show();
```

## Beperking {#restriction}

U kunt de dialoog beperken tot een specifieke directory, zodat gebruikers niet buiten deze directory kunnen navigeren met behulp van de `setRestricted(boolean restricted)`-methode.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Bewaar je bestand", "/home/user/documents", "rapport.xls");
dialog.setRestricted(true);
dialog.show();
```

## Bestandsnaam {#filename}

Stel een standaard bestandsnaam in voor de opslaan operatie om gebruikers te begeleiden en fouten te minimaliseren.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Bewaar je bestand");
dialog.setName("rapport.xls");
String result = dialog.show();
```

## Internationalisatie (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de component zijn volledig aanpasbaar met de `FileSaveI18n`-klasse. Dit zorgt ervoor dat de dialoog kan worden afgestemd op verschillende lokalisatie- of personalisatie-eisen.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Stornieren");
dialog.setI18n(i18n);
```

## Filters {#filters}

De `FileSaveDialog` stelt u in staat filters in te stellen om de soorten bestanden die kunnen worden opgeslagen te beperken met de `setFilters(List<FileSaveFilter> filters)`-methode.

<ComponentDemo 
path='/webforj/filesavedialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java'
height = '800px'
/>

### Aangepaste filters {#custom-filters}

U kunt aangepaste filters inschakelen om gebruikers hun eigen bestandsfilters te laten definiëren met de `setCustomFilters(boolean customFilters)`-methode. Filters worden standaard in lokale opslag opgeslagen en hersteld bij volgende dialooguitvoeringen.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Bewaar je bestand", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Beste praktijken {#best-practices}

* **Vooraf gedefinieerde Bestandsnamen**: Bied een logische standaard bestandsnaam waar mogelijk.
* **Bevestigen van Overschrijvingen**: Gebruik `CONFIRMATION_DIALOGUE` voor `ExistsAction` om per ongeluk overschrijven te voorkomen.
* **Intuïtief Aanvangspad**: Stel een aanvangspad in dat aansluit bij de verwachtingen van de gebruiker.
* **Internationalisatie**: Pas de dialogtekst aan om de bruikbaarheid voor internationale doelgroepen te verbeteren.
* **Bestandstypefilters**: Maak gebruik van filters om bestandstypen te beperken en gebruikers te begeleiden naar geldige bestandsextensies.
