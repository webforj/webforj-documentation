---
title: File Upload
sidebar_position: 20
_i18n_hash: 70bfb275a09a977cf365a14535aaf02e
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

Ein `FileUploadDialog` ist ein modales Dialogfenster, das es dem Benutzer ermöglicht, Dateien von seinem lokalen Dateisystem hochzuladen. Der Dialog blockiert die Ausführung der Anwendung, bis der Benutzer eine Datei zum Hochladen auswählt oder den Dialog schließt.

<!-- INTRO_END -->

## Anwendungen {#usages}

Der `FileUploadDialog` bietet eine Möglichkeit, Dateien auszuwählen und hochzuladen, sodass Benutzer Dokumente, Bilder oder andere Dateitypen einreichen können, die von der Anwendung benötigt werden. Verwenden Sie `showFileUploadDialog()`, um den Dialog anzuzeigen und die hochgeladene Datei zu erfassen.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Eine Datei hochladen");
```

## Ergebnis {#result}

Der `FileUploadDialog` gibt ein `UploadedFile`-Objekt zurück, das Informationen über die hochgeladene Datei enthält, wie z.B. ihren Namen, ihre Größe und ihren Inhalt. Wenn der Benutzer den Dialog schließt, ohne eine Datei auszuwählen, ist das Ergebnis `null`.

:::important
Der resultierende String wird aus der `show()`-Methode oder der entsprechenden `OptionDialog`-Methode wie unten gezeigt zurückgegeben.
:::

<ComponentDemo 
path='/webforj/fileuploaddialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java'
height = '400px'
/>

### Hochgeladene Dateien verschieben {#moving-uploaded-files}

Standardmäßig speichert webforJ hochgeladene Dateien in einem temporären Ordner, der regelmäßig gereinigt wird. Wenn Sie die Datei nicht woanders verschieben, wird sie gelöscht. Um die Datei zu verschieben, verwenden Sie die Methode `move` und geben Sie den Zielpfad an.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Wählen Sie eine Datei zum Hochladen aus");
try {
    File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
    // ... machen Sie etwas mit der Datei
} catch (IOException e) {
    // behandeln Sie die Ausnahme
}
```
:::tip Sanitized Client Name
Verwenden Sie die Methode `getSanitizedClientName`, um eine sanitierte Version des Namens der hochgeladenen Datei zu erhalten. Diese Methode hilft, Sicherheitsrisiken wie Verzeichnisdurchquerungsangriffe oder ungültige Zeichen in Dateinamen zu verhindern und die Integrität und Sicherheit Ihres Dateispeichersystems zu gewährleisten.
:::

## Filter {#filters}

Der `FileUploadDialog` ermöglicht es Ihnen, Filter festzulegen, um die Arten von Dateien zu begrenzen, die für den Upload ausgewählt werden können. Sie können Filter mit der Methode `setFilters(List<FileChooserFilter> filters)` konfigurieren.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
    "Eine Datei hochladen", 
    Arrays.asList(new FileChooserFilter("Textdateien", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Filtervalidierung
Der Server validiert die hochgeladene Datei nicht gegen die Filter. Die Filter werden nur in der Benutzeroberfläche angewendet, um die Auswahl des Benutzers zu leiten. Sie müssen eine serverseitige Validierung implementieren, um sicherzustellen, dass die hochgeladenen Dateien die Anforderungen Ihrer Anwendung erfüllen.
:::

## Maximalgröße {#max-size}

Es ist möglich, die maximale Dateigröße für Uploads festzulegen, um sicherzustellen, dass Benutzer keine Dateien hochladen, die zu groß sind, um von Ihrer Anwendung verarbeitet zu werden. Dies kann mit der Methode `setMaxFileSize(long maxSize)` konfiguriert werden, wobei maxSize in Byte angegeben wird.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Maximale Größe auf 2 MB setzen
```

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Labels und Nachrichten innerhalb des Komponentens sind vollständig anpassbar, indem die Klasse `FileUploadI18n` verwendet wird. Diese Flexibilität ermöglicht es Ihnen, die Dialogoberfläche so anzupassen, dass sie spezifische Lokalisierungsanforderungen oder persönliche Vorlieben erfüllt.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Datei hochladen");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Hochladen");
i18n.setCancel("Stornieren");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Best Practices {#best-practices}

1. **Klare und präzise Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, was der Benutzer hochladen soll.
2. **Angemessene Filter**: Setzen Sie Dateifilter, die den erforderlichen Dateitypen entsprechen, um sicherzustellen, dass Benutzer relevante Dateien hochladen.
3. **Logische Startpfade**: Setzen Sie Anfangs-Pfade, die den Benutzern einen nützlichen Ausgangspunkt für die Dateiauswahl bieten.
4. **Einschränkung der Verzeichnissnavigation**: Beschränken Sie den Dialog bei Bedarf auf ein bestimmtes Verzeichnis, um zu verhindern, dass Benutzer in nicht autorisierte Bereiche navigieren.
5. **Konsistente Gestaltung**: Stimmen Sie die Themes des Dialogs und des Upload-Felds mit dem Design Ihrer Anwendung ab, um ein einheitliches Benutzererlebnis zu gewährleisten.
6. **Minimierung der Übernutzung**: Verwenden Sie Datei-Upload-Dialoge sparsam, um Benutzerfrustration zu vermeiden. Reservieren Sie sie für Aktionen, die spezifische Benutzerdateiuploads erfordern.
