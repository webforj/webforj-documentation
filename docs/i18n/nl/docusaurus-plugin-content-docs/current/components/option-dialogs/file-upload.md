---
title: File Upload
sidebar_position: 20
description: >-
  Capture client uploads with the FileUploadDialog, returning an UploadedFile
  that you can filter, move, and process server-side.
_i18n_hash: 411ef7de8459154002fa2a620f073b0d
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

Een `FileUploadDialog` is een modaal dialoogvenster dat is ontworpen om de gebruiker in staat te stellen bestanden van hun lokale bestandssysteem te uploaden. Het dialoogvenster blokkeert de uitvoering van de applicatie totdat de gebruiker bestanden selecteert om te uploaden of het dialoogvenster sluit.

<!-- INTRO_END -->

:::tip Inline component
Als je een bestandskiezer wilt die rechtstreeks in de paginalay-out wordt weergegeven in plaats van in een dialoogvenster, overweeg dan om de [`Upload`](/docs/components/upload) component te gebruiken.
:::

## Gebruik {#usages}

De `FileUploadDialog` biedt een manier om bestanden te selecteren en te uploaden, waardoor gebruikers documenten, afbeeldingen of andere bestandstypen kunnen indienen die door de applicatie nodig zijn. Gebruik `showFileUploadDialog()` om het dialoogvenster weer te geven en het geüploade bestand vast te leggen.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Upload een bestand");
```

## Resultaat {#result}

De `FileUploadDialog` retourneert een `UploadedFile` object dat informatie bevat over het geüploade bestand, zoals de naam, grootte en inhoud. Als de gebruiker het dialoogvenster sluit zonder een bestand te selecteren, is het resultaat `null`.

:::important
Het resulterende `UploadedFile` object wordt geretourneerd vanuit de `show()` methode, of de equivalente `OptionDialog` methode zoals hieronder weergegeven.
:::

<ComponentDemo
path='/webforj/fileuploaddialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java']}
height='400px'
/>

### Gegevens verplaatsen {#moving-uploaded-files}

Standaard slaat webforJ geüploade bestanden op in een tijdelijke map die regelmatig wordt geleegd. Als je het bestand niet ergens anders verplaatst, wordt het verwijderd. Om het bestand te verplaatsen, gebruik je de `move` methode en geef je het doelpad op.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Selecteer een bestand om te uploaden");
try {
  File file = uploadedFile.move("mijn/volledige/pad/" + uploadedFile.getSanitizedClientName());
  // ... doe iets met het bestand
} catch (IOException e) {
  // verwerk de uitzondering
}
```
:::tip Genormaliseerde Klantnaam
Gebruik de `getSanitizedClientName` methode om een genormaliseerde versie van de naam van het geüploade bestand te verkrijgen. Deze methode helpt bij het voorkomen van beveiligingsrisico's zoals directory traversal-aanvallen of ongeldige tekens in bestandsnamen, waardoor de integriteit en veiligheid van je bestandopslagsysteem gewaarborgd blijft.
:::

## Filters {#filters}

De `FileUploadDialog` staat je toe filters in te stellen om de types bestanden die geselecteerd kunnen worden voor upload te beperken. Je kunt filters configureren met de `setFilters(List<FileChooserFilter> filters)` methode.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
  "Upload een bestand",
  Arrays.asList(new FileChooserFilter("Tekstbestanden", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Validatie van filters
De server valideert het geüploade bestand niet tegen de filters. De filters worden alleen toegepast in de UI om de selectie van de gebruiker te begeleiden. Je moet server-side validatie implementeren om ervoor te zorgen dat de geüploade bestanden voldoen aan de vereisten van je applicatie.
:::

## Maximale grootte {#max-size}

Gebruik `setMaxFileSize(Number maxSize)` om de maximale uploadgrootte in bytes in te stellen.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Stel maximale grootte in op 2 MB
```

## Internationalisatie (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de component zijn volledig aanpasbaar met behulp van de `FileUploadI18n` klasse. Deze flexibiliteit stelt je in staat de dialooginterface aan te passen aan specifieke lokalisatievereisten of personalisatievoorkeuren.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Bestand uploaden");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Uploaden");
i18n.setCancel("Annuleren");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Beste werkwijzen {#best-practices}

1. **Duidelijke en Bondige Prompts**: Zorg ervoor dat de prompt duidelijk uitlegt wat de gebruiker wordt gevraagd te uploaden.
2. **Passende Filters**: Stel bestandsfilters in die overeenkomen met de vereiste bestandstypen om ervoor te zorgen dat gebruikers relevante bestanden uploaden.
3. **Consistente Thematiek**: Stem de thema's van het dialoogvenster en het uploadveld af op het ontwerp van je applicatie.
4. **Minimaliseer Overmatig Gebruik**: Gebruik bestandsuploaddialoogvensters spaarzaam om frustratie bij de gebruiker te voorkomen. Reserveer ze voor acties waarvoor specifieke gebruikersbestanden nodig zijn.
