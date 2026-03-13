---
title: File Upload
sidebar_position: 20
_i18n_hash: 70bfb275a09a977cf365a14535aaf02e
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

Een `FileUploadDialog` is een modale dialoog die is ontworpen om de gebruiker in staat te stellen bestanden van hun lokale bestandssysteem te uploaden. De dialoog blokkeert de uitvoering van de app totdat de gebruiker bestanden selecteert om te uploaden of de dialoog sluit.

<!-- INTRO_END -->

## Usages {#usages}

De `FileUploadDialog` biedt een manier om bestanden te selecteren en te uploaden, zodat gebruikers documenten, afbeeldingen of andere bestandstypen kunnen indienen die door de app vereist zijn. Gebruik `showFileUploadDialog()` om de dialoog weer te geven en het geüploade bestand vast te leggen.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Upload een bestand");
```

## Result {#result}

De `FileUploadDialog` retourneert een `UploadedFile` object dat informatie bevat over het geüploade bestand, zoals de naam, grootte en inhoud. Als de gebruiker de dialoog sluit zonder een bestand te selecteren, is het resultaat `null`.

:::important
De resulterende string wordt geretourneerd vanuit de `show()` methode, of de equivalente `OptionDialog` methode zoals hieronder weergegeven. 
:::

<ComponentDemo 
path='/webforj/fileuploaddialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java'
height = '400px'
/>

### Verplaatsen van geüploade bestanden {#moving-uploaded-files}

Standaard slaat webforJ geüploade bestanden op in een tijdelijke map die regelmatig wordt schoongemaakt. Als je het bestand niet ergens anders verplaatst, wordt het verwijderd. Om het bestand te verplaatsen, gebruik je de `move` methode en geef je het pad van de bestemming op.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Selecteer een bestand om te uploaden");
try {
    File file = uploadedFile.move("mijn/volledige/pad/" + uploadedFile.getSanitizedClientName());
    // ... doe iets met het bestand
} catch (IOException e) {
    // behandel de uitzondering
}
```
:::tip Genormaliseerde Klantnaam
Gebruik de `getSanitizedClientName` methode om een genormaliseerde versie van de naam van het geüploade bestand te verkrijgen. Deze methode helpt beveiligingsrisico's te voorkomen, zoals directory traversal aanvallen of ongeldige tekens in bestandsnamen, waardoor de integriteit en veiligheid van je bestandopslagsysteem wordt gewaarborgd.
:::

## Filters {#filters}

De `FileUploadDialog` stelt je in staat om filters in te stellen om de types bestanden die geselecteerd kunnen worden voor upload te beperken. Je kunt filters configureren met de `setFilters(List<FileChooserFilter> filters)` methode.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
    "Upload een bestand", 
    Arrays.asList(new FileChooserFilter("Tekstbestanden", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Validatie van Filters
De server valideert het geüploade bestand niet tegen de filters. De filters worden alleen toegepast in de UI om de selectie van de gebruiker te begeleiden. Je moet server-side validatie implementeren om ervoor te zorgen dat de geüploade bestanden voldoen aan de vereisten van je app.
:::

## Max grootte {#max-size}

Het is mogelijk om de maximale bestandsgrootte voor uploads in te stellen om ervoor te zorgen dat gebruikers geen bestanden uploaden die te groot zijn voor je app om te verwerken. Dit kan worden geconfigureerd met de `setMaxFileSize(long maxSize)` methode, waarbij maxSize is opgegeven in bytes.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Stel de maximale grootte in op 2 MB
```

## Internationalisatie (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de component zijn volledig aanpasbaar met behulp van de `FileUploadI18n` klasse. Deze flexibiliteit stelt je in staat om de dialooginterface aan te passen aan specifieke lokalisatievereisten of persoonlijke voorkeuren.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Bestand uploaden");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Uploaden");
i18n.setCancel("Annuleren");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Best practices {#best-practices}

1. **Duidelijke en Bondige Aanwijzingen**: Zorg ervoor dat de prompt duidelijk uitlegt wat de gebruiker wordt gevraagd te uploaden.
2. **Passende Filters**: Stel bestandsfilters in die overeenkomen met de vereiste bestandstypen om ervoor te zorgen dat gebruikers relevante bestanden uploaden.
3. **Logische Beginpaden**: Stel beginpaden in die gebruikers een nuttig uitgangspunt bieden voor hun bestandsselectie.
4. **Beperk Directory Navigatie**: Beperk de dialoog tot een specifieke directory wanneer nodig om te voorkomen dat gebruikers naar ongeautoriseerde gebieden navigeren.
5. **Consistente Thema's**: Stem de thema's van de dialoog en uploadveld af op het ontwerp van je app voor een samenhangende gebruikerservaring.
6. **Minimaliseer Overmatig Gebruik**: Gebruik bestandsuploaddialogen spaarzaam om gebruikersfrustratie te voorkomen. Beperk ze tot acties die specifieke gebruikersbestandsuploads vereisen.
