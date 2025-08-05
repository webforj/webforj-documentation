---
sidebar_position: 20
title: File Upload
_i18n_hash: e25933325d4f0d5a7044a5e0776e3741
---
# Dateiupload-Dialog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

Ein `FileUploadDialog` ist ein modaler Dialog, der entwickelt wurde, um dem Benutzer das Hochladen von Dateien aus dem lokalen Dateisystem zu ermöglichen. Der Dialog blockiert die Ausführung der App, bis der Benutzer Dateien zum Hochladen auswählt oder den Dialog schließt.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Eine Datei hochladen");
```

## Anwendungen {#usages}

Der `FileUploadDialog` bietet eine Möglichkeit, Dateien auszuwählen und hochzuladen, sodass Benutzer Dokumente, Bilder oder andere von der App benötigte Dateitypen einreichen können.

## Ergebnis {#result}

Der `FileUploadDialog` gibt ein `UploadedFile`-Objekt zurück, das Informationen über die hochgeladene Datei enthält, wie z. B. ihren Namen, ihre Größe und ihren Inhalt. Wenn der Benutzer den Dialog schließt, ohne eine Datei auszuwählen, wird das Ergebnis `null` sein.

:::important
Der resultierende String wird von der `show()`-Methode oder der entsprechenden `OptionDialog`-Methode zurückgegeben, wie unten gezeigt. 
:::

<ComponentDemo 
path='/webforj/fileuploaddialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java'
height = '400px'
/>

### Hochladen von Dateien verschieben {#moving-uploaded-files}

Standardmäßig speichert webforJ hochgeladene Dateien in einem temporären Ordner, der regelmäßig gereinigt wird. Wenn Sie die Datei nicht anderswohin verschieben, wird sie gelöscht. Um die Datei zu verschieben, verwenden Sie die `move`-Methode und geben Sie den Zielpfad an.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Wählen Sie eine Datei zum Hochladen aus");
try {
    File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
    // ... etwas mit der Datei machen
} catch (IOException e) {
    // Ausnahme behandeln
}
```
:::tip Sanitized Client Name
Verwenden Sie die Methode `getSanitizedClientName`, um eine bereinigte Version des Namens der hochgeladenen Datei zu erhalten. Diese Methode hilft, Sicherheitsrisiken wie Verzeichnisdurchlaufangriffe oder ungültige Zeichen in Dateinamen zu vermeiden, um die Integrität und Sicherheit Ihres Dateispeichersystems zu gewährleisten.
:::

## Filter {#filters}

Der `FileUploadDialog` ermöglicht es Ihnen, Filter festzulegen, um die Arten von Dateien zu beschränken, die zum Hochladen ausgewählt werden können. Sie können Filter mit der Methode `setFilters(List<FileChooserFilter> filters)` konfigurieren.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
    "Eine Datei hochladen", 
    Arrays.asList(new FileChooserFilter("Textdateien", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Filtervalidierung
Der Server validiert die hochgeladene Datei nicht gegen die Filter. Die Filter werden nur in der Benutzeroberfläche angewendet, um die Auswahl des Benutzers zu leiten. Sie müssen eine serverseitige Validierung implementieren, um sicherzustellen, dass die hochgeladenen Dateien den Anforderungen Ihrer App entsprechen.
:::

## Maximale Größe {#max-size}

Es ist möglich, die maximale Dateigröße für Uploads festzulegen, um sicherzustellen, dass Benutzer keine zu großen Dateien hochladen, die Ihre App überlasten könnten. Dies kann mit der Methode `setMaxFileSize(long maxSize)` konfiguriert werden, wobei maxSize in Bytes angegeben wird.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Maximalgröße auf 2 MB festlegen
```

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Beschriftungen und Nachrichten innerhalb der Komponente sind vollständig anpassbar über die Klasse `FileUploadI18n`. Diese Flexibilität ermöglicht es Ihnen, die Dialogoberfläche auf spezifische Lokalisierungsanforderungen oder persönliche Präferenzen abzustimmen.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Datei hochladen");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Hochladen");
i18n.setCancel("Abbrechen");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Best Practices {#best-practices}

1. **Klare und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht deutlich erklärt, was der Benutzer hochladen soll.
2. **Geeignete Filter**: Legen Sie Datei-Filter fest, die den erforderlichen Dateitypen entsprechen, um sicherzustellen, dass die Benutzer relevante Dateien hochladen.
3. **Logische Anfangspfad**: Setzen Sie Anfangsdateipfade, die den Benutzern einen nützlichen Ausgangspunkt für ihre Dateiauswahl bieten.
4. **Einschränkung der Verzeichnisnavigation**: Beschränken Sie den Dialog auf ein bestimmtes Verzeichnis, wenn nötig, um zu verhindern, dass Benutzer in unbefugte Bereiche navigieren.
5. **Konsistentes Design**: Stimmen Sie das Design des Dialogs und des Upload-Feldes mit dem Design Ihrer App ab, um ein einheitliches Benutzererlebnis zu gewährleisten.
6. **Minimierung der Übernutzung**: Verwenden Sie Dateiupload-Dialoge sparsam, um Benutzerfrustration zu vermeiden. Reservieren Sie sie für Aktionen, die spezifische Benutzerdatei-Uploads erfordern.
