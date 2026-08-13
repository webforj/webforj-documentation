---
title: File Upload
sidebar_position: 20
description: >-
  Capture client uploads with the FileUploadDialog, returning an UploadedFile
  that you can filter, move, and process server-side.
_i18n_hash: 708f41fa227a5a346bf58be64964dc9c
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

Een `FileUploadDialog` is een modale dialoog ontworpen om de gebruiker in staat te stellen bestanden van hun lokale bestandssysteem te uploaden. De dialoog blokkeert de app-executie totdat de gebruiker bestanden selecteert om te uploaden of de dialoog sluit.

<!-- INTRO_END -->

:::tip Inline component
Als u een bestandskiezer wilt die rechtstreeks in de paginalay-out wordt weergegeven in plaats van in een dialoog, overweeg dan om de [`Upload`](/docs/components/upload) component te gebruiken.
:::

## Usages {#usages}

De `FileUploadDialog` biedt een manier om bestanden te selecteren en te uploaden, waardoor gebruikers documenten, afbeeldingen of andere bestandstypen die door de app vereist zijn, kunnen indienen. Gebruik `showFileUploadDialog()` om de dialoog weer te geven en het geüploade bestand vast te leggen.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Upload een bestand");
```

## Result {#result}

De `FileUploadDialog` retourneert een `UploadedFile` object dat informatie bevat over het geüploade bestand, zoals de naam, grootte en inhoud. Als de gebruiker de dialoog sluit zonder een bestand te selecteren, is het resultaat `null`.

:::important
De resulterende string wordt geretourneerd vanuit de `show()` methode, of de equivalente `OptionDialog` methode zoals hieronder weergegeven.
:::

<ComponentDemo
path='/webforj/fileuploaddialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java']}
height='400px'
/>

### Beweging van geüploade bestanden {#moving-uploaded-files}

Standaard slaat webforJ geüploade bestanden op in een tijdelijke map die regelmatig wordt schoongemaakt. Als u het bestand niet elders verplaatst, wordt het verwijderd. Gebruik de `move` methode en geef het bestemmingspad op om het bestand te verplaatsen.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Selecteer een bestand om te uploaden");
try {
  File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
  // ... doe iets met het bestand
} catch (IOException e) {
  // behandel de uitzonderingen
}
```
:::tip Gesanitiseerde Klantnaam
Gebruik de `getSanitizedClientName` methode om een gesanitiseerde versie van de naam van het geüploade bestand te verkrijgen. Deze methode helpt beveiligingsrisico's zoals directory traversal-aanvallen of ongeldige tekens in bestandsnamen te voorkomen, waardoor de integriteit en veiligheid van uw bestandsopslagsysteem wordt gewaarborgd.
:::

## Filters {#filters}

De `FileUploadDialog` stelt u in staat om filters in te stellen om de typen bestanden die kunnen worden geselecteerd voor upload te beperken. U kunt filters configureren met de `setFilters(List<FileChooserFilter> filters)` methode.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
  "Upload een bestand",
  Arrays.asList(new FileChooserFilter("Tekstbestanden", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Validatie van filters
De server valideert het geüploade bestand niet tegen de filters. De filters worden alleen in de UI toegepast om de selectie van de gebruiker te begeleiden. U moet server-side validatie implementeren om ervoor te zorgen dat de geüploade bestanden voldoen aan de vereisten van uw app.
:::

## Max grootte {#max-size}

Het is mogelijk om de maximale bestandsgrootte voor uploads in te stellen om ervoor te zorgen dat gebruikers geen bestanden uploaden die te groot zijn voor uw app om te verwerken. Dit kan worden geconfigureerd met de `setMaxFileSize(long maxSize)` methode, waarbij maxSize in bytes wordt opgegeven.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Stel de maximale grootte in op 2 MB
```

## Internationalisering (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de component zijn volledig aanpasbaar met de `FileUploadI18n` klasse. Deze flexibiliteit stelt u in staat om de dialooginterface af te stemmen op specifieke lokalisatie-eisen of personalisatievoorkeuren.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Bestand uploaden");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Uploaden");
i18n.setCancel("Annuleren");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Best practices {#best-practices}

1. **Duidelijke en Bondige Aanwijzingen**: Zorg ervoor dat de aanwijzing duidelijk uitlegt wat de gebruiker wordt gevraagd te uploaden.
2. **Geschikte Filters**: Stel bestandsfilters in die overeenkomen met de vereiste bestandstypen om ervoor te zorgen dat gebruikers relevante bestanden uploaden.
3. **Logische Initiële Paden**: Stel initiële paden in die gebruikers een nuttig uitgangspunt bieden voor hun bestandsselectie.
4. **Beperk Directory Navigatie**: Beperk de dialoog tot een specifieke directory wanneer nodig om te voorkomen dat gebruikers naar ongeautoriseerde gebieden navigeren.
5. **Consistente Themalijn**: Stem de thema's van de dialoog en uploadvelden af op het ontwerp van uw app voor een samenhangende gebruikerservaring.
6. **Minimaliseer Overmatig Gebruik**: Gebruik bestandsuploaddialogen spaarzaam om frustratie bij gebruikers te voorkomen. Beperk ze tot acties die specifieke bestandsuploads van gebruikers vereisen.
