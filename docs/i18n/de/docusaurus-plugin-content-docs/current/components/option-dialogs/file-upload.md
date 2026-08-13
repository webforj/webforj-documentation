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

Ein `FileUploadDialog` ist ein modales Dialogfeld, das entwickelt wurde, um es dem Benutzer zu ermöglichen, Dateien von seinem lokalen Dateisystem hochzuladen. Der Dialog blockiert die Ausführung der Anwendung, bis der Benutzer Dateien zum Hochladen auswählt oder den Dialog schließt.

<!-- INTRO_END -->

:::tip Inline-Komponente
Wenn Sie einen Dateiauswähler wünschen, der direkt im Seitenlayout und nicht in einem Dialog angezeigt wird, sollten Sie die [`Upload`](/docs/components/upload) Komponente in Betracht ziehen.
:::

## Verwendungen {#usages}

Der `FileUploadDialog` bietet eine Möglichkeit, Dateien auszuwählen und hochzuladen, sodass Benutzer Dokumente, Bilder oder andere Dateitypen, die von der Anwendung benötigt werden, einreichen können. Verwenden Sie `showFileUploadDialog()`, um den Dialog anzuzeigen und die hochgeladene Datei zu erfassen.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Eine Datei hochladen");
```

## Ergebnis {#result}

Der `FileUploadDialog` gibt ein `UploadedFile`-Objekt zurück, das Informationen über die hochgeladene Datei enthält, wie z.B. ihren Namen, ihre Größe und ihren Inhalt. Wenn der Benutzer den Dialog schließt, ohne eine Datei auszuwählen, wird das Ergebnis `null` sein.

:::important
Der resultierende String wird von der `show()`-Methode oder der äquivalenten `OptionDialog`-Methode wie unten gezeigt zurückgegeben.
:::

<ComponentDemo
path='/webforj/fileuploaddialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java']}
height='400px'
/>

### Verschieben hochgeladener Dateien {#moving-uploaded-files}

Standardmäßig speichert webforJ hochgeladene Dateien in einem temporären Ordner, der regelmäßig gereinigt wird. Wenn Sie die Datei nicht anderswo verschieben, wird sie gelöscht. Um die Datei zu verschieben, verwenden Sie die `move`-Methode und geben Sie den Zielpfad an.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Wählen Sie eine Datei zum Hochladen aus");
try {
  File file = uploadedFile.move("mein/voller/pfad/" + uploadedFile.getSanitizedClientName());
  // ... machen Sie etwas mit der Datei
} catch (IOException e) {
  // Ausnahme behandeln
}
```
:::tip Sanitized Client Name
Verwenden Sie die Methode `getSanitizedClientName`, um eine sanitisierte Version des Namens der hochgeladenen Datei zu erhalten. Diese Methode hilft, Sicherheitsrisiken wie Verzeichnistraversierungsangriffe oder ungültige Zeichen in Dateinamen zu verhindern, und gewährleistet die Integrität und Sicherheit Ihres Dateispeichersystems.
:::

## Filter {#filters}

Der `FileUploadDialog` ermöglicht es Ihnen, Filter festzulegen, um die Arten von Dateien zu begrenzen, die für das Hochladen ausgewählt werden können. Sie können Filter mit der Methode `setFilters(List<FileChooserFilter> filters)` konfigurieren.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
  "Eine Datei hochladen",
  Arrays.asList(new FileChooserFilter("Textdateien", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Filtervalidierung
Der Server überprüft die hochgeladene Datei nicht gegen die Filter. Die Filter werden nur in der Benutzeroberfläche angewendet, um die Auswahl des Benutzers zu leiten. Sie müssen eine serverseitige Validierung implementieren, um sicherzustellen, dass die hochgeladenen Dateien den Anforderungen Ihrer Anwendung entsprechen.
:::

## Maximale Größe {#max-size}

Es ist möglich, die maximale Dateigröße für Uploads festzulegen, um sicherzustellen, dass Benutzer keine Dateien hochladen, die zu groß sind, um von Ihrer Anwendung verarbeitet zu werden. Dies kann mit der Methode `setMaxFileSize(long maxSize)` konfiguriert werden, wobei maxSize in Byte angegeben wird.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Setze die maximale Größe auf 2 MB
```

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Beschriftungen und Nachrichten innerhalb der Komponente sind vollständig anpassbar, indem die Klasse `FileUploadI18n` verwendet wird. Diese Flexibilität ermöglicht es Ihnen, die Dialogoberfläche an spezifische Lokalisierungsanforderungen oder Personalisierungsvorlieben anzupassen.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Datei hochladen");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Hochladen");
i18n.setCancel("Stornieren");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Best Practices {#best-practices}

1. **Klarheit und Prägnanz**: Stellen Sie sicher, dass die Eingabeaufforderung klar erklärt, was der Benutzer hochladen soll.
2. **Angemessene Filter**: Setzen Sie Dateifilter, die den erforderlichen Dateitypen entsprechen, um sicherzustellen, dass die Benutzer relevante Dateien hochladen.
3. **Logische Anfangsverzeichnisse**: Legen Sie Anfangsverzeichnisse fest, die den Benutzern einen nützlichen Ausgangspunkt für ihre Dateiauswahl bieten.
4. **Einschränkung der Verzeichnisnavigation**: Beschränken Sie den Dialog bei Bedarf auf ein bestimmtes Verzeichnis, um zu verhindern, dass Benutzer nicht autorisierte Bereiche durchsuchen.
5. **Konsistente Gestaltung**: Richten Sie die Themen des Dialogs und des Upload-Felds nach dem Design Ihrer Anwendung aus, um ein kohärentes Benutzererlebnis zu gewährleisten.
6. **Übermäßige Nutzung minimieren**: Verwenden Sie Datei-Upload-Dialoge sparsam, um Benutzerfrustration zu vermeiden. Reservieren Sie sie für Aktionen, die spezifische Benutzer-Datei-Uploads erfordern.
