---
sidebar_position: 20
title: File Upload
_i18n_hash: e25933325d4f0d5a7044a5e0776e3741
---
# Bestandsupload Dialoog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

Een `FileUploadDialog` is een modale dialoog ontworpen om de gebruiker in staat te stellen bestanden van hun lokale bestandssysteem te uploaden. De dialoog blokkeert de uitvoering van de applicatie totdat de gebruiker bestanden selecteert om te uploaden of de dialoog sluit.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Upload een bestand");
```

## Gebruik {#usages}

De `FileUploadDialog` biedt een manier om bestanden te selecteren en te uploaden, waardoor gebruikers documenten, afbeeldingen of andere bestandstypen kunnen indienen die door de app nodig zijn.

## Resultaat {#result}

De `FileUploadDialog` retourneert een `UploadedFile` object dat informatie bevat over het geüploade bestand, zoals de naam, grootte en inhoud. Als de gebruiker de dialoog sluit zonder een bestand te selecteren, is het resultaat `null`.

:::important
De resulterende string zal worden geretourneerd vanuit de `show()` methode, of de equivalente `OptionDialog` methode zoals hieronder weergegeven. 
:::

<ComponentDemo 
path='/webforj/fileuploaddialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java'
height = '400px'
/>

### Geüploade bestanden verplaatsen {#moving-uploaded-files}

Standaard slaat webforJ geüploade bestanden op in een tijdelijke map die regelmatig wordt schoongemaakt. Als je het bestand niet ergens anders verplaatst, wordt het verwijderd. Gebruik de `move` methode en geef het bestemmingspad op om het bestand te verplaatsen.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Selecteer een bestand om te uploaden");
try {
    File file = uploadedFile.move("mijn/volledige/pad/" + uploadedFile.getSanitizedClientName());
    // ... doe iets met het bestand
} catch (IOException e) {
    // behandel de uitzondering
}
```
:::tip Geformatteerde Clientnaam
Gebruik de `getSanitizedClientName` methode om een geformatteerde versie van de naam van het geüploade bestand te verkrijgen. Deze methode helpt beveiligingsrisico's te voorkomen, zoals directory traversal aanvallen of ongeldige tekens in bestandsnamen, waardoor de integriteit en veiligheid van je bestandsopslagsysteem gewaarborgd blijft.
:::

## Filters {#filters}

De `FileUploadDialog` stelt je in staat om filters in te stellen om de soorten bestanden te beperken die kunnen worden geselecteerd voor upload. Je kunt filters configureren met behulp van de `setFilters(List<FileChooserFilter> filters)` methode.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
    "Upload een bestand", 
    Arrays.asList(new FileChooserFilter("Tekstbestanden", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Validatie van Filters
De server valideert het geüploade bestand niet tegen de filters. De filters worden alleen in de gebruikersinterface toegepast om de selectie van de gebruiker te begeleiden. Je moet server-side validatie implementeren om te waarborgen dat de geüploade bestanden voldoen aan de eisen van je app.
:::

## Maximale grootte {#max-size}

Het is mogelijk om de maximale bestandsgrootte voor uploads in te stellen om ervoor te zorgen dat gebruikers geen bestanden uploaden die te groot zijn voor je app om te verwerken. Dit kan worden geconfigureerd met behulp van de `setMaxFileSize(long maxSize)` methode, waarbij maxSize in bytes is opgegeven.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Stel de maximale grootte in op 2 MB
```

## Internationalisatie (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de component zijn volledig aanpasbaar met behulp van de `FileUploadI18n` klasse. Deze flexibiliteit stelt je in staat om de dialooginterface aan te passen aan specifieke lokalisatie-eisen of personalisatievoorkeuren.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Bestand uploaden");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Uploaden");
i18n.setCancel("Annuleren");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Best Practices {#best-practices}

1. **Duidelijke en Bondige Vragen**: Zorg ervoor dat de vraag duidelijk uitlegt wat de gebruiker wordt gevraagd te uploaden.
2. **Geschikte Filters**: Stel bestandsfilters in die overeenkomen met de vereiste bestandstypen om te verzekeren dat gebruikers relevante bestanden uploaden.
3. **Logische Startpaden**: Stel startpaden in die gebruikers een nuttig vertrekpunt bieden voor hun bestandsselectie.
4. **Beperk Directory Navigatie**: Beperk de dialoog tot een specifieke map wanneer nodig om te voorkomen dat gebruikers naar ongeautoriseerde gebieden navigeren.
5. **Consistente Theming**: Stem de thema's van de dialoog en uploadvelden af op het ontwerp van je app voor een samenhangende gebruikerservaring.
6. **Minimaal Overmatig Gebruik**: Gebruik bestandsuploaddialogen spaarzaam om gebruikersfrustratie te voorkomen. Reserveer ze voor acties die specifieke gebruikersbestandsuploads vereisen.
