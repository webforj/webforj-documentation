---
sidebar_position: 20
title: File Upload
_i18n_hash: 1218c7729c6cb025d2d6b4312bd95658
---
# Dateiupload-Dialog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

Ein `FileUploadDialog` ist ein modaler Dialog, der es dem Benutzer ermöglicht, Dateien von seinem lokalen Dateisystem hochzuladen. Der Dialog blockiert die Ausführung der App, bis der Benutzer Dateien zum Hochladen auswählt oder den Dialog schließt.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Eine Datei hochladen");
```

## Verwendungen {#usages}

Der `FileUploadDialog` bietet eine Möglichkeit, Dateien auszuwählen und hochzuladen, wodurch Benutzer Dokumente, Bilder oder andere Dateitypen, die von der App benötigt werden, einreichen können.

## Ergebnis {#result}

Der `FileUploadDialog` gibt ein `UploadedFile`-Objekt zurück, das Informationen über die hochgeladene Datei enthält, wie ihren Namen, ihre Größe und ihren Inhalt. Wenn der Benutzer den Dialog schließt, ohne eine Datei auszuwählen, wird das Ergebnis `null` sein.

:::important
Der resultierende String wird von der `show()`-Methode oder der entsprechenden `OptionDialog`-Methode wie unten gezeigt zurückgegeben. 
:::

<ComponentDemo 
path='/webforj/fileuploaddialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java'
height = '400px'
/>

### Verschieben hochgeladener Dateien {#moving-uploaded-files}

Standardmäßig speichert webforJ hochgeladene Dateien in einem temporären Ordner, der regelmäßig gereinigt wird. Wenn Sie die Datei nicht anderswo verschieben, wird sie gelöscht. Um die Datei zu verschieben, verwenden Sie die `move`-Methode und geben Sie den Zielpfad an.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Eine Datei zum Hochladen auswählen");
try {
    File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
    // ... tun Sie etwas mit der Datei
} catch (IOException e) {
    // Ausnahme behandeln
}
```
:::tip Sanitized Client Name
Verwenden Sie die Methode `getSanitizedClientName`, um eine bereinigte Version des Namens der hochgeladenen Datei zu erhalten. Diese Methode hilft, Sicherheitsrisiken wie Verzeichnisdurchquerungsangriffe oder ungültige Zeichen in Dateinamen zu verhindern, wodurch die Integrität und Sicherheit Ihres Dateispeichersystems gewährleistet wird.
:::

## Filter {#filters}

Der `FileUploadDialog` ermöglicht es Ihnen, Filter festzulegen, um die Arten von Dateien, die für den Upload ausgewählt werden können, einzuschränken. Sie können Filter mit der Methode `setFilters(List<FileChooserFilter> filters)` konfigurieren.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
    "Eine Datei hochladen", 
    Arrays.asList(new FileChooserFilter("Textdateien", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Validierung der Filter
Der Server validiert die hochgeladene Datei nicht gegen die Filter. Die Filter werden nur in der Benutzeroberfläche angewendet, um die Auswahl des Benutzers zu leiten. Sie müssen eine serverseitige Validierung implementieren, um sicherzustellen, dass die hochgeladenen Dateien den Anforderungen Ihrer App entsprechen.
:::

## Maximale Größe {#max-size}

Es ist möglich, die maximale Dateigröße für Uploads festzulegen, um sicherzustellen, dass Benutzer keine Dateien hochladen, die für Ihre App zu groß sind. Dies kann mit der Methode `setMaxFileSize(long maxSize)` konfiguriert werden, wobei maxSize in Bytes angegeben wird.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Maximale Größe auf 2 MB festlegen
```

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Beschriftungen und Nachrichten innerhalb der Komponente sind vollständig anpassbar mit der Klasse `FileUploadI18n`. Diese Flexibilität ermöglicht es Ihnen, die Dialogoberfläche an spezifische Lokalisierungsanforderungen oder persönliche Vorlieben anzupassen.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Datei hochladen");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Hochladen");
i18n.setCancel("Stornieren");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Beste Praktiken {#best-practices}

1. **Klare und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, was der Benutzer hochladen soll.
2. **Angemessene Filter**: Legen Sie Dateifilter fest, die mit den erforderlichen Dateitypen übereinstimmen, um sicherzustellen, dass Benutzer relevante Dateien hochladen.
3. **Logische Anfangspfade**: Legen Sie Anfangspfade fest, die den Benutzern einen nützlichen Ausgangspunkt für ihre Dateiauswahl bieten.
4. **Einschränkung der Verzeichnissnavigation**: Beschränken Sie den Dialog bei Bedarf auf ein bestimmtes Verzeichnis, um zu verhindern, dass Benutzer nicht autorisierte Bereiche navigieren.
5. **Konsistente Gestaltung**: Richten Sie die Dialog- und Uploadfeld-Designs nach dem Design Ihrer App aus, um ein kohärentes Benutzererlebnis zu gewährleisten.
6. **Minimierung des Übergebrauchs**: Verwenden Sie Dateiupload-Dialoge sparsam, um die Frustration der Benutzer zu vermeiden. Reservieren Sie sie für Aktionen, die spezifische Benutzerdateiuploads erfordern.
