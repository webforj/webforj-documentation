---
sidebar_position: 15
title: File Save
_i18n_hash: 477f92765ae539fd69106297baa9a0da
---
# Bestandsopslagdialoog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` is een modale dialoog ontworpen om gebruikers in staat te stellen een bestand op te slaan op een aangegeven locatie in het serverbestandssysteem. De dialoog blokkeert de uitvoering van de app totdat de gebruiker een bestandsnaam opgeeft en de actie bevestigt of de dialoog annuleert.

```java
OptionDialog.showFileSaveDialog("Sla uw bestand op");
```

## Gebruik {#usages}

De `FileSaveDialog` biedt een gestroomlijnde methode voor het opslaan van bestanden naar het bestandssysteem, met gebruikersconfigureerbare opties voor bestandsnaamgeving en het omgaan met bestaande bestanden.

<ComponentDemo 
path='/webforj/filesavedialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java'
height = '800px'
/>

## Resultaat {#result}

De `FileSaveDialog` geeft het geselecteerde pad terug als een string. Als de gebruiker de dialoog annuleert, is het resultaat `null`.

:::important Doel van de dialoog
Deze dialoog zorgt er niet voor dat bestanden daadwerkelijk worden opgeslagen, maar retourneert de bestandsnaam die de gebruiker heeft geselecteerd.
:::

:::info
De resulterende string wordt geretourneerd vanuit de `show()`-methode of de equivalente `OptionDialog`-methode zoals hieronder gedemonstreerd.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Sla uw bestand op", "/home/user/documents", "rapport.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Bestand opgeslagen naar: " + path, "Pad Gekozen");
} else {
  OptionDialog.showMessageDialog("Geen pad geselecteerd", "Pad Gekozen",
      MessageDialog.MessageType.ERROR);
}
```

## Bestaat actie {#exists-action}

De `FileSaveDialog` biedt configureerbaar gedrag wanneer er al een bestand met de opgegeven naam bestaat:

* **ACCEPT_WITHOUT_ACTION**: De selectie wordt geaccepteerd zonder aanvullende actie van de gebruiker.
* **ERROR_DIALOGUE**: De gebruiker krijgt een foutdialoog gepresenteerd; de selectie is niet toegestaan.
* **CONFIRMATION_DIALOGUE**: De gebruiker krijgt een dialoog ter bevestiging gepresenteerd. Dit is de standaardinstelling.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Sla uw bestand op", "/home/user/documents", "rapport.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Selectiemodus {#selection-mode}

De `FileSaveDialog` ondersteunt verschillende selectiemodi, waardoor je de selectie methode kunt afstemmen op jouw specifieke behoeften:

1. **BESTANDEN**: Maakt alleen de selectie van bestanden mogelijk.
2. **DIRECTORIES**: Maakt alleen de selectie van mappen mogelijk.
3. **BESTANDEN_EN_DIRECTORIES**: Maakt de selectie van zowel bestanden als mappen mogelijk.

## Initiële pad {#initial-path}

Geef de map op waarin de dialoog zal openen met behulp van het initiële pad. Dit helpt gebruikers te beginnen in een logische map voor hun opslaanhandeling.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Sla uw bestand op", "/home/user/documents", "rapport.xls");
String result = dialog.show();
```

## Beperking {#restriction}

Je kunt de dialoog beperken tot een specifieke map, waardoor gebruikers worden voorkomen dat ze er buiten navigeren met behulp van de `setRestricted(boolean restricted)`-methode.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Sla uw bestand op", "/home/user/documents", "rapport.xls");
dialog.setRestricted(true);
dialog.show();
```

## Bestandsnaam {#filename}

Stel een standaard bestandsnaam in voor de opslaanhandeling om gebruikers te begeleiden en fouten te minimaliseren.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Sla uw bestand op");
dialog.setName("rapport.xls");
String result = dialog.show();
```

## Internationalisering (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de component zijn volledig aanpasbaar met behulp van de `FileSaveI18n`-klasse. Dit zorgt ervoor dat de dialoog kan worden afgestemd op verschillende lokalisatie- of personalisatievereisten.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Sla uw bestand op");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Kiezen");
i18n.setCancel("Annuleren");
dialog.setI18n(i18n);
```

## Filters {#filters}

De `FileSaveDialog` stelt je in staat om filters in te stellen om de soorten bestanden die kunnen worden opgeslagen te beperken met behulp van de `setFilters(List<FileSaveFilter> filters)`-methode.

<ComponentDemo 
path='/webforj/filesavedialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java'
height = '800px'
/>

### Aangepaste filters {#custom-filters}

Je kunt aangepaste filters inschakelen om gebruikers in staat te stellen hun eigen bestandsfilters te definiëren met behulp van de `setCustomFilters(boolean customFilters)`-methode. Filters worden standaard opgeslagen in de lokale opslag en hersteld bij volgende oproepen van de dialoog.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Sla uw bestand op", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Best practices {#best-practices}

* **Vooraf gedefinieerde Bestandsnamen**: Bied een logische standaard bestandsnaam waar van toepassing.
* **Bevestig Overschrijvingen**: Gebruik `CONFIRMATION_DIALOGUE` voor `ExistsAction` om onopzettelijke overschrijvingen te voorkomen.
* **Intuïtief Initiële Pad**: Stel een initiële pad in die aansluit bij de verwachtingen van de gebruiker.
* **Internationalisering**: Pas de tekst van de dialoog aan om de bruikbaarheid voor internationale doelgroepen te verbeteren.
* **Bestandstypefilters**: Maak gebruik van filters om bestandstypen te beperken en gebruikers te begeleiden naar geldige bestandsextensies.
