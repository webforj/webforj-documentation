---
title: File Upload
sidebar_position: 20
_i18n_hash: fc6515e16590085708ed61b3aedff9f1
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

Ein `FileUploadDialog` ist ein modales Dialogfeld, das es dem Benutzer ermöglicht, Dateien von seinem lokalen Dateisystem hochzuladen. Das Dialogfeld blockiert die App-Ausführung, bis der Benutzer Dateien zum Hochladen auswählt oder das Dialogfeld schließt.

<!-- INTRO_END -->

## Anwendungen {#usages}

Der `FileUploadDialog` bietet eine Möglichkeit, Dateien auszuwählen und hochzuladen, sodass Benutzer Dokumente, Bilder oder andere Dateitypen einreichen können, die von der App benötigt werden. Verwenden Sie `showFileUploadDialog()`, um das Dialogfeld anzuzeigen und die hochgeladene Datei zu erfassen.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Datei hochladen");
```

## Ergebnis {#result}

Der `FileUploadDialog` gibt ein `UploadedFile`-Objekt zurück, das Informationen über die hochgeladene Datei enthält, wie z. B. ihren Namen, ihre Größe und ihren Inhalt. Wenn der Benutzer das Dialogfeld schließt, ohne eine Datei auszuwählen, ist das Ergebnis `null`.

:::important
Der resultierende String wird von der `show()`-Methode oder der entsprechenden `OptionDialog`-Methode zurückgegeben, wie unten gezeigt. 
:::

<ComponentDemo
path='/webforj/fileuploaddialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java']}
height='400px'
/>

### Hochgeladene Dateien verschieben {#moving-uploaded-files}

Standardmäßig speichert webforJ hochgeladene Dateien in einem temporären Ordner, der regelmäßig gereinigt wird. Wenn Sie die Datei nicht woandershin verschieben, wird sie gelöscht. Um die Datei zu verschieben, verwenden Sie die `move`-Methode und geben Sie den Zielpfad an.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Wählen Sie eine Datei zum Hochladen aus");
try {
  File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
  // ... tun Sie etwas mit der Datei
} catch (IOException e) {
  // Ausnahme behandeln
}
```
:::tip Sanitized Client Name
Verwenden Sie die Methode `getSanitizedClientName`, um eine bereinigte Version des Namens der hochgeladenen Datei zu erhalten. Diese Methode trägt dazu bei, Sicherheitsrisiken wie Verzeichnisdurchquerungsangriffe oder ungültige Zeichen in Dateinamen zu verhindern und die Integrität und Sicherheit Ihres Dateispeichersystems zu gewährleisten.
:::

## Filter {#filters}

Der `FileUploadDialog` ermöglicht es Ihnen, Filter festzulegen, um die Arten von Dateien zu beschränken, die für den Upload ausgewählt werden können. Sie können Filter mit der Methode `setFilters(List<FileChooserFilter> filters)` konfigurieren.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
  "Datei hochladen", 
  Arrays.asList(new FileChooserFilter("Textdateien", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Filtervalidierung
Der Server validiert die hochgeladene Datei nicht anhand der Filter. Die Filter werden nur in der Benutzeroberfläche angewendet, um die Auswahl des Benutzers zu leiten. Sie müssen die serverseitige Validierung implementieren, um sicherzustellen, dass die hochgeladenen Dateien den Anforderungen Ihrer App entsprechen.
:::

## Maximalgröße {#max-size}

Es ist möglich, die maximale Dateigröße für Uploads festzulegen, um sicherzustellen, dass Benutzer keine Dateien hochladen, die zu groß sind, um von Ihrer App verarbeitet zu werden. Dies kann mit der Methode `setMaxFileSize(long maxSize)` konfiguriert werden, wobei maxSize in Bytes angegeben wird.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Maximalgröße auf 2 MB setzen
```

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Beschriftungen und Nachrichten innerhalb der Komponente sind vollständig anpassbar mithilfe der Klasse `FileUploadI18n`. Diese Flexibilität ermöglicht es Ihnen, die Dialogoberfläche an spezifische Lokalisierungsanforderungen oder persönliche Präferenzen anzupassen.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Datei hochladen");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Hochladen");
i18n.setCancel("Abbrechen");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Beste Praktiken {#best-practices}

1. **Klare und präzise Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, was der Benutzer hochladen soll.
2. **Geeignete Filter**: Stellen Sie Dateifilter ein, die den erforderlichen Dateitypen entsprechen, um sicherzustellen, dass Benutzer relevante Dateien hochladen.
3. **Logische Ausgangspfad**: Setzen Sie Anfangs-Pfade, die den Benutzern einen hilfreichen Ausgangspunkt für ihre Dateiauswahl bieten.
4. **Einschränkung der Verzeichnisnavigation**: Beschränken Sie das Dialogfeld bei Bedarf auf ein bestimmtes Verzeichnis, um zu verhindern, dass Benutzer unbefugte Bereiche durchstöbern.
5. **Konsistente Gestaltung**: Stimmen Sie das Design des Dialogs und des Upload-Felds mit dem Design Ihrer App ab, um ein einheitliches Benutzererlebnis zu gewährleisten.
6. **Weniger ist mehr**: Verwenden Sie Datei-Upload-Dialoge sparsam, um die Frustration der Benutzer zu vermeiden. Reservieren Sie sie für Aktionen, die spezifische Benutzerdatei-Uploads erfordern.
