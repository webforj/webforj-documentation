---
title: File Upload
sidebar_position: 20
_i18n_hash: 0c52346e43f2f615464dde85f39d7cd0
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

Ein `FileUploadDialog` ist ein modales Dialogfeld, das entwickelt wurde, um dem Benutzer das Hochladen von Dateien aus seinem lokalen Dateisystem zu ermöglichen. Der Dialog blockiert die Anwendung, bis der Benutzer Dateien zum Hochladen auswählt oder den Dialog schließt.

<!-- INTRO_END -->

## Verwendungen {#usages}

Der `FileUploadDialog` bietet eine Möglichkeit, Dateien auszuwählen und hochzuladen, sodass Benutzer Dokumente, Bilder oder andere Dateitypen einreichen können, die von der Anwendung benötigt werden. Verwenden Sie `showFileUploadDialog()`, um den Dialog anzuzeigen und die hochgeladene Datei zu erfassen.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Eine Datei hochladen");
```

## Ergebnis {#result}

Der `FileUploadDialog` gibt ein `UploadedFile`-Objekt zurück, das Informationen über die hochgeladene Datei enthält, wie ihren Namen, ihre Größe und ihren Inhalt. Wenn der Benutzer den Dialog schließt, ohne eine Datei auszuwählen, ist das Ergebnis `null`.

:::important
Der resultierende String wird von der `show()`-Methode oder der entsprechenden `OptionDialog`-Methode wie unten gezeigt zurückgegeben.
:::

<ComponentDemo 
path='/webforj/fileuploaddialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java'
height = '400px'
/>

### Hochgeladene Dateien verschieben {#moving-uploaded-files}

Standardmäßig speichert webforJ hochgeladene Dateien in einem temporären Ordner, der regelmäßig bereinigt wird. Wenn Sie die Datei nicht an einen anderen Ort verschieben, wird sie gelöscht. Um die Datei zu verschieben, verwenden Sie die Methode `move` und geben Sie den Zielpfad an.

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
Verwenden Sie die Methode `getSanitizedClientName`, um eine bereinigte Version des Namens der hochgeladenen Datei zu erhalten. Diese Methode hilft, Sicherheitsrisiken wie Directory Traversal-Angriffe oder ungültige Zeichen in Dateinamen zu verhindern und gewährleistet die Integrität und Sicherheit Ihres Dateispeichersystems.
:::

## Filter {#filters}

Der `FileUploadDialog` ermöglicht es Ihnen, Filter festzulegen, um die Arten von Dateien zu beschränken, die ausgewählt werden können. Sie können Filter mit der Methode `setFilters(List<FileChooserFilter> filters)` konfigurieren.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
  "Eine Datei hochladen", 
  Arrays.asList(new FileChooserFilter("Textdateien", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Validierung der Filter
Der Server validiert die hochgeladene Datei nicht gegen die Filter. Die Filter werden nur in der Benutzeroberfläche angewendet, um die Auswahl des Benutzers zu leiten. Sie müssen eine serverseitige Validierung implementieren, um sicherzustellen, dass die hochgeladenen Dateien den Anforderungen Ihrer Anwendung entsprechen.
:::

## Maximalgröße {#max-size}

Es ist möglich, die maximale Dateigröße für Uploads festzulegen, um sicherzustellen, dass Benutzer keine Dateien hochladen, die zu groß für Ihre Anwendung sind. Dies kann mit der Methode `setMaxFileSize(long maxSize)` konfiguriert werden, wobei maxSize in Bytes angegeben wird.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Maximalgröße auf 2 MB festlegen
```

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Labels und Nachrichten innerhalb des Components sind vollständig anpassbar mithilfe der Klasse `FileUploadI18n`. Diese Flexibilität ermöglicht es Ihnen, die Dialogoberfläche an spezifische Lokalisierungsanforderungen oder Personalisierungspräferenzen anzupassen.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Datei hochladen");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Hochladen");
i18n.setCancel("Stornieren");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Best Practices {#best-practices}

1. **Klare und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, was der Benutzer hochladen soll.
2. **Angemessene Filter**: Setzen Sie Dateifilter, die den erforderlichen Dateitypen entsprechen, um sicherzustellen, dass Benutzer relevante Dateien hochladen.
3. **Logische Anfangswege**: Legen Sie Anfangswege fest, die den Benutzern einen nützlichen Ausgangspunkt für ihre Dateiauswahl bieten.
4. **Einschränkung der Verzeichnissnavigation**: Beschränken Sie den Dialog bei Bedarf auf ein bestimmtes Verzeichnis, um zu verhindern, dass Benutzer in nicht autorisierte Bereiche navigieren.
5. **Konsistente Gestaltung**: Stimmen Sie die Themen des Dialogs und des Upload-Felds mit dem Design Ihrer Anwendung ab, um ein kohärentes Benutzererlebnis zu gewährleisten.
6. **Übernutzung minimieren**: Verwenden Sie Datei-Upload-Dialoge sparsam, um Frustrationen bei den Benutzern zu vermeiden. Reservieren Sie sie für Aktionen, die bestimmte Datei-Uploads des Benutzers erfordern.
