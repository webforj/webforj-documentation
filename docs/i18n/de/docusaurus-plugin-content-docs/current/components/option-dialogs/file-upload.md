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

Ein `FileUploadDialog` ist ein modales Dialogfeld, das entwickelt wurde, um dem Benutzer das Hochladen von Dateien aus seinem lokalen Dateisystem zu ermöglichen. Das Dialogfeld blockiert die Ausführung der Anwendung, bis der Benutzer Dateien zum Hochladen auswählt oder das Dialogfeld schließt.

<!-- INTRO_END -->

:::tip Inline-Komponente
Wenn Sie einen Dateiauswähler möchten, der direkt im Seitenlayout anstelle eines Dialogfelds gerendert wird, sollten Sie die [`Upload`](/docs/components/upload) Komponente in Betracht ziehen.
:::

## Verwendungen {#usages}

Der `FileUploadDialog` bietet eine Möglichkeit, Dateien auszuwählen und hochzuladen, sodass Benutzer Dokumente, Bilder oder andere Dateitypen, die von der Anwendung benötigt werden, einreichen können. Verwenden Sie `showFileUploadDialog()`, um das Dialogfeld anzuzeigen und die hochgeladene Datei zu erfassen.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Eine Datei hochladen");
```

## Ergebnis {#result}

Der `FileUploadDialog` gibt ein `UploadedFile`-Objekt zurück, das Informationen über die hochgeladene Datei enthält, wie ihren Namen, ihre Größe und ihren Inhalt. Wenn der Benutzer das Dialogfeld schließt, ohne eine Datei auszuwählen, wird das Ergebnis `null` sein.

:::important
Das resultierende `UploadedFile`-Objekt wird von der `show()`-Methode oder der entsprechenden `OptionDialog`-Methode wie unten gezeigt zurückgegeben.
:::

<ComponentDemo
path='/webforj/fileuploaddialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java']}
height='400px'
/>

### Hochgeladene Dateien verschieben {#moving-uploaded-files}

Standardmäßig speichert webforJ hochgeladene Dateien in einem temporären Ordner, der regelmäßig gereinigt wird. Wenn Sie eine Datei nicht an einen anderen Ort verschieben, wird sie gelöscht. Um die Datei zu verschieben, verwenden Sie die `move`-Methode und geben Sie den Zielpfad an.

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
Verwenden Sie die Methode `getSanitizedClientName`, um eine sanierte Version des Namens der hochgeladenen Datei zu erhalten. Diese Methode hilft, Sicherheitsrisiken wie Verzeichnisdurchlaufangriffe oder ungültige Zeichen in Dateinamen zu verhindern und gewährleistet die Integrität und Sicherheit Ihres Dateispeichersystems.
:::

## Filter {#filters}

Der `FileUploadDialog` ermöglicht es Ihnen, Filter festzulegen, um die Arten von Dateien, die zum Hochladen ausgewählt werden können, einzuschränken. Sie können die Filter mit der Methode `setFilters(List<FileChooserFilter> filters)` konfigurieren.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
  "Eine Datei hochladen",
  Arrays.asList(new FileChooserFilter("Textdateien", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Filtervalidierung
Der Server validiert die hochgeladene Datei nicht gegen die Filter. Die Filter werden nur in der Benutzeroberfläche angewendet, um die Auswahl des Benutzers zu leiten. Sie müssen serverseitige Validierungen implementieren, um sicherzustellen, dass die hochgeladenen Dateien den Anforderungen Ihrer Anwendung entsprechen.
:::

## Maximale Größe {#max-size}

Verwenden Sie `setMaxFileSize(Number maxSize)`, um die maximale Uploadgröße in Bytes festzulegen.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Maximale Größe auf 2 MB festlegen
```

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Labels und Nachrichten innerhalb der Komponente sind vollständig anpassbar mit der `FileUploadI18n`-Klasse. Diese Flexibilität ermöglicht es Ihnen, die Dialogoberfläche an spezifische Lokalisierungsanforderungen oder Personalisierungspräferenzen anzupassen.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Datei hochladen");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Hochladen");
i18n.setCancel("Abbrechen");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Beste Praktiken {#best-practices}

1. **Klare und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, was der Benutzer hochladen soll.
2. **Angemessene Filter**: Setzen Sie Dateifilter, die den erforderlichen Dateitypen entsprechen, um sicherzustellen, dass Benutzer relevante Dateien hochladen.
3. **Konsistentes Design**: Passen Sie die Designs des Dialogs und des Uploadfelds an das Design Ihrer Anwendung an.
4. **Übermäßigen Gebrauch minimieren**: Verwenden Sie Dateiuploaddialoge sparsam, um Frustrationen bei den Benutzern zu vermeiden. Reservieren Sie sie für Aktionen, die spezifische Benutzer-Dateiuploads erfordern.
