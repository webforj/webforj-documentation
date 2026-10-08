---
title: Upload
sidebar_position: 160
description: >-
  Select and upload one or more files from the local machine with the Upload
  component using drag-and-drop, filters, and per-file or batch event tracking.
_i18n_hash: df26b1e4b51f3ba6ece7602ca1a1f33f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-upload" />
<DocChip chip='since' label='26.01' />
<JavadocLink type="foundation" location="com/webforj/component/upload/Upload" top='true'/>

Die `Upload`-Komponente ist ein Inline-Dateiauswähler, der es dem Benutzer ermöglicht, eine oder mehrere Dateien von seinem lokalen Computer auszuwählen und an den Server zu senden. Im Gegensatz zum [`FileUploadDialog`](/docs/components/option-dialogs/file-upload), der den Auswähler in einem Modal präsentiert, das die App blockiert, bis der Benutzer fertig ist, wird `Upload` direkt im Seitenlayout gerendert. Es passt überall dorthin, wo ein Datei-Input hingehört: ein Profilformular, ein Anhangsfeld neben einem Kommentarfeld oder eine Dropzone auf einer Mediamanagement-Seite.

<!-- INTRO_END -->

:::tip Wann man ein `Upload` verwenden sollte
Verwenden Sie die `Upload`-Komponente, wenn die Dateiauswahl von anderen Aktionen in einem Workflow begleitet wird, wie z. B. beim Bearbeiten eines Profils oder beim Erstellen eines Beitrags. Greifen Sie stattdessen auf den [`FileUploadDialog`](/docs/components/option-dialogs/file-upload) zurück, wenn Uploads modal sein sollten, zum Beispiel wenn eine Datei unbedingt erforderlich ist, bevor der Benutzer fortfahren kann.
:::

## Erstellen eines Uploads {#creating-an-upload}

Standardmäßig zeigt eine `Upload`-Komponente eine Schaltfläche zum Auswählen, einen Drop-Bereich, die aktuelle Dateiliste und eine Upload-Schaltfläche. Die Schaltfläche zum Abbrechen ist standardmäßig ausgeblendet. Nachdem Sie einen `Upload` erstellt haben, können Sie Filter hinzufügen, wie zulässige Dateitypen, und ändern, welche Teile sichtbar sind.

```java
Upload upload = new Upload();
upload.addFilter("Bilder", "*.png;*.jpg");
upload.setVisible(false, Upload.Part.LIST);
layout.add(upload);
```

Im folgenden Beispiel wird ein Lebenslauf-`Upload` in ein Einstellungsformular eingefügt, zusammen mit einem Namensfeld und einer Schaltfläche zum Absenden.

<ComponentDemo
path='/webforj/upload'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='550px'
/>

## Dateien auswählen {#picking-files}

Wie der Auswähler funktioniert, wird durch einige unabhängige Einstellungen gesteuert: wie viele Dateien der Benutzer gleichzeitig auswählen kann, was vom lokalen Dateisystem auswählbar ist und welche Typen im Dateidialog sichtbar sind. Gemeinsam formen sie das Auswahlverfahren, um es zu dem Feld passend zu machen.

Hier ist ein Galerie-Uploader, der sowohl Bild- als auch Video-Filter, Mehrfachauswahl und ein Limit von 20 Dateien konfiguriert:

<ComponentDemo
path='/webforj/uploadpickingfiles'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPickingFilesView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='450px'
/>

### Auswahlmodus {#selection-mode}

Der Auswahlmodus begrenzt den Auswähler auf eine Datei oder viele. `MULTIPLE` ist der Standard und eignet sich für Batch-Operationen wie Fotogalerien oder Rechnungsanhänge. `SINGLE` passt zu Feldern, die konzeptionell einen Wert halten, wie ein Profilfoto oder einen unterschriebenen Vertrag.

```java
upload.setSelectionMode(Upload.SelectionMode.SINGLE);
upload.setSelectionMode(Upload.SelectionMode.MULTIPLE);
```

### Auswählerquelle {#picker-source}

Die Auswählerquelle bestimmt, was der Benutzer aus dem lokalen Dateisystem auswählen kann. Der Standardwert `FILES` öffnet einen standardmäßigen Dateidialog. `DIRECTORY` lässt den Benutzer einen Ordner auswählen und lädt die obersten Dateien hoch. `DIRECTORY_RECURSIVE` durchläuft den gesamten Baum und lädt jede Datei darin hoch.

```java
upload.setPicker(Upload.Picker.DIRECTORY_RECURSIVE);
```

Ordner-Uploads eignen sich für Tools, die Ordnerstrukturen spiegeln, wie Bereitstellungssysteme, Asset-Management-Apps oder Backup-Dienstprogramme. Für die meisten Formularfelder ist der Standard-Dateiauswähler die richtige Wahl.

### Filter {#filters}

Filter schränken ein, was der Benutzer aus dem lokalen Dateisystem auswählen kann. Jeder Filter hat eine Beschreibung und ein oder mehrere Glob-Muster, die durch Semikolons getrennt sind. Der aktive Filter wird in einem Dropdown-Menü neben der Auswähltaste angezeigt, und der Benutzer kann zwischen ihnen wechseln.

```java
upload.addFilter("Bilder", "*.png;*.jpg;*.jpeg");
upload.addFilter("Dokumente", "*.pdf;*.docx");
upload.setActiveFilter("Bilder");
```

Einige verwandte Einstellungen sorgen dafür, wie sich das Filter-Dropdown verhält: `setFiltersVisible(false)` blendet das Dropdown aus und hält die Filter aktiv, `setMultiFilterSelection(true)` ermöglicht es dem Benutzer, Filter zu kombinieren, und `setAllFilesFilterEnabled(false)` entfernt die implizite Option "Alle Dateien".

Einige dieser Einstellungen gelten nur für den standardmäßigen Auswähler. Bei Verwendung der File System Access API verwaltet der native OS-Auswähler die Filterauswahl selbst, sodass `setFiltersVisible(false)` ignoriert wird und `setMultiFilterSelection(true)` keine Wirkung hat (der native Auswähler akzeptiert nur einen Filter auf einmal). Deaktivieren Sie die File System Access API mit `setFileSystemAccess(false)`, um diese Einstellungen browserübergreifend zuverlässig zu machen.

### Drop-Zone {#drop-zone}

Dateien können vom Desktop gezogen und auf die Komponente abgelegt werden. Das Drop-Label ändert sich, wenn eine Datei darüber schwebt, um anzuzeigen, dass das Ablegen akzeptiert wird. Das Ablegen ist standardmäßig aktiviert und kann deaktiviert werden, wenn der Auswähler nur Dateien aus dem Dateidialog akzeptieren sollte.

```java
upload.setDrop(false);
```

## Validierung und Grenzen {#validation-and-limits}

`setMaxFileSize` begrenzt die Byte-Größe einer einzelnen Datei und `setMaxFiles` begrenzt die Gesamtzahl der Dateien in einem Batch. Beide laufen ab, bevor Bytes übertragen werden, sodass eine übergroße Datei auf der Client-Seite abgelehnt wird, ohne Bandbreite zu verbrauchen.

```java
upload.setMaxFileSize(5 * 1024 * 1024); // 5 MB
upload.setMaxFiles(10);
```

Wenn eine ausgewählte oder abgelegte Datei eine dieser Grenzen überschreitet, wird ein `UploadRejectEvent` mit dem Grund ausgelöst. Eigenschaft `webforj.fileUpload.maxSize` auf der Serverseite gilt ebenfalls und wirkt als harte Obergrenze, unabhängig von der clientseitigen Grenze.

:::warning Serverseitige Validierung
Filter, maximale Größe und maximale Dateizahl werden in der UI durchgesetzt, um den Benutzer zu leiten, nicht um den Server zu schützen. Jede hochgeladene Datei sollte auf dem Server erneut überprüft werden, bevor sie gespeichert wird, und die temporären Dateien sollten kurz nach Abschluss des Uploads verschoben oder gelöscht werden.
:::

## Upload-Verhalten {#upload-behavior}

Sobald Dateien ausgewählt sind, bleiben zwei Entscheidungen: wann der Upload beginnt und was mit bestehenden Einträgen passiert, wenn der Benutzer erneut auswählt. Standardmäßig klickt der Benutzer auf **Hochladen**, um die Übertragung zu starten, und bestehende Einträge bleiben in der Liste, bis sie ausdrücklich gelöscht werden.

### Automatischer Upload {#auto-upload}

Der Standardmodus ist `NONE`, bei dem der Benutzer auf **Hochladen** klickt, um die Übertragung zu starten. `setAutoUpload()` entfernt diesen Klick und startet die Übertragung, sobald Dateien ausgewählt oder abgelegt werden oder beides.

- **`NONE`** überlässt das Hochladen dem Benutzer, der auf **Hochladen** klickt.
- **`ON_SELECT`** lädt hoch, sobald Dateien über den Dateidialog ausgewählt werden.
- **`ON_DROP`** lädt hoch, sobald Dateien auf die Komponente abgelegt werden.
- **`ALWAYS`** deckt beide Pfade ab.

:::tip Kombination mit Voreinstellungen
Automatischer Upload passt gut zu den Voreinstellungen `BUTTON_ONLY` oder `INLINE`, bei denen es ohnehin keine Upload-Schaltfläche für den Benutzer gibt. Für Workflows, bei denen der Benutzer die Auswahl überprüfen muss, bevor er sendet, lassen Sie den automatischen Upload deaktiviert.
:::

### Automatisches Löschen {#auto-clear}

Wenn der Benutzer einen neuen Stapel auswählt, entscheidet automatisches Löschen, was mit den bereits in der Liste befindlichen Einträgen passieren soll. Das Löschen erfolgt zum Zeitpunkt der nächsten Auswahl, nicht nach Abschluss des Uploads, sodass abgeschlossene Uploads sichtbar bleiben, bis der Benutzer erneut auswählt.

- **`COMPLETED`** löscht erfolgreich hochgeladene Einträge.
- **`IN_PROGRESS`** bricht die Übertragung und das Löschen von Einträgen ab, die noch übertragen werden.
- **`ALL`** löscht alles.
Wartende Einträge, die noch nicht mit dem Hochladen begonnen haben, werden unabhängig von der Einstellung beibehalten.

```java
upload.setAutoClear(Upload.AutoClear.COMPLETED);
upload.setAutoClear(Upload.AutoClear.IN_PROGRESS);
upload.setAutoClear(Upload.AutoClear.ALL);
```

:::warning Automatisches Löschen hat subtile Auslöser
Automatisches Löschen tritt nur in Kraft, wenn eine zuvor ausgewählte Datei tatsächlich mit dem Hochladen begonnen hat oder beendet ist. Ohne einen Upload zwischen den Auswahlen, entspricht keine Datei dem Filter und die Liste wächst weiter.
:::

Greifen Sie auf `COMPLETED` in Uploads zu, die über mehrere Aktionen hinweg auf dem Bildschirm bleiben, wie z. B. einen Chat-Composer, bei dem jede Nachricht ihre eigenen Anhänge hat, oder ein Kommentarfeld, das bei jeder Antwort wiederverwendet wird. Ohne es sammelt sich die Liste der vorherigen Erfolge an, während der Benutzer arbeitet.

### Programmatische Aktionen {#programmatic-actions}

Die meisten Uploads beginnen mit einem Benutzerklick, aber dieselben Aktionen sind auch aus dem Servercode verfügbar. Beide operieren auf den Dateien, die der Benutzer bereits ausgewählt hat; es gibt keine Möglichkeit, Dateien im Namen des Benutzers vom Server aus auszuwählen.

```java
// Lade die aktuelle Auswahl hoch, als ob der Benutzer auf Hochladen geklickt hätte
upload.upload();

// Breche alle laufenden Übertragungen ab
upload.cancel();
```

Rufen Sie `upload()` auf, um die Übertragung von einer Steuerung außerhalb der Komponente auszulösen, z. B. von einer einzelnen Schaltfläche "Absenden", die von einem größeren Formular geteilt wird. Rufen Sie `cancel()` von einer "Stop"-Schaltfläche außerhalb der Komponente oder von einem Routenwächter auf, wenn der Benutzer während der Übertragung navigiert.

## Mobiler Capture {#mobile-capture}

Auf mobilen Geräten öffnet Capture die Kamera oder das Mikrofon als Auswählerquelle anstelle des Dateibrowsers. `USER` zielt auf die Frontkamera oder das Mikrofon ab, `ENVIRONMENT` zielt auf die Rückkamera ab, und `NONE` (der Standard) verwendet den standardmäßigen Dateiauswähler.

```java
upload.setCapture(Upload.Capture.ENVIRONMENT);
upload.addFilter("Foto", "*.jpg;*.png");
```

:::tip Capture und Filter
Schränken Sie die Auswahl auf Bilddateiendungen ein, damit die Kamera im Standbildmodus öffnet, oder auf Videoformaten, damit sie im Aufnahme-Modus öffnet. Ohne einen entsprechenden Filter fällt ein Capture-Modus auf den standardmäßigen Auswähler auf den meisten Plattformen zurück. Desktop-Browser ignorieren die Capture-Einstellung vollständig.
:::

Für mobile-first-Apps passt Capture gut zu [installierbaren Apps](/docs/configuration/installable-apps), bei denen die Kamera und das Mikrofon einen natürlichen Teil des Home-Screen-Erlebnisses werden.

## Zugriff auf das native Dateisystem {#native-file-system-access}

Die Komponente verwendet die [File System Access API](https://developer.mozilla.org/en-US/docs/Web/API/File_System_Access_API) des Browsers, wenn die Plattform dies unterstützt. Der native Auswähler kann der Seite dauerhafte Berechtigungen für einen Ordner gewähren, sodass der Benutzer einmal auswählt und nachfolgende Uploads aus demselben Ordner den Dialog überspringen. In Browsern ohne Unterstützung wechselt die Komponente automatisch zum standardmäßigen Auswähler.

```java
upload.setFileSystemAccess(false); // zwingt den standardmäßigen Auswähler
```

Schalten Sie es aus, wenn jeder Upload mit einem neuen Dialog gestartet werden soll, oder wenn konsistentes Verhalten über jeden Browser hinweg wichtiger ist als der Komfort der dauerhaften Berechtigung.

## Anpassen des Layouts {#customizing-the-layout}

Die Komponente besteht aus fünf Teilen: der Auswähltaste, dem Drop-Label, der Dateiliste, der Upload-Taste und der Abbrechen-Taste. Die ersten vier sind standardmäßig sichtbar; die Abbrechen-Taste ist ausgeblendet und kann mit `setVisible(true, Upload.Part.CANCEL_BUTTON)` angezeigt werden. Das Layout kann mit Voreinstellungen für gängige Auswählerformen oder mit Sichtbarkeitskontrollen für jeden Teil für genauere Anpassungen umgestaltet werden.

### Voreinstellungen {#presets}

Voreinstellungen bündeln mehrere Sichtbarkeitseinstellungen zu Teilbereichen in benannten Auswählerformen. Sie sind eine schnellere Möglichkeit, eine gängige Konfiguration zu erreichen, als Teile einzeln umzuschalten.

- **`FULL`**: Auswähltaste, Drop-Label, Dateiliste und Upload-Taste. Der Standard.
- **`INLINE`**: Auswähltaste und Drop-Label, wobei die aktuelle Auswahl als Text neben dem Auswähler gerendert wird. Nützlich für kompakte Formulare.
- **`BUTTON_ONLY`**: Die Auswähltaste allein. Nützlich, wenn die umgebende UI bereits die ausgewählten Dateien anzeigt.
- **`DROPZONE`**: Drop-Label und Dateiliste, keine Auswähltaste. Nützlich, wenn Drag-and-Drop die einzige Möglichkeit sein soll, Dateien hinzuzufügen.
- **`HEADLESS`**: Jeder Teil verborgen, wobei der äußere Rand, der Radius und die Polsterung zusammengeklappt werden, sodass projizierte Inhalte bündig innerhalb der Komponentenbegrenzungen sitzen.

```java
upload.setPreset(Upload.Preset.INLINE);
```

<ComponentDemo
path='/webforj/uploadpresets'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPresetsView.java',
]}
height='650px'
/>

### Sichtbarkeit von Teilen {#part-visibility}

Wenn eine Voreinstellung nahe, aber nicht ganz an der gewünschten Form ist, können einzelne Teile gezeigt oder verborgen werden. Dies ist nützlich für kleine Anpassungen wie das Ausblenden der Abbrechen-Taste bei einem Einzeldatei-Uploader, der sofort hochlädt, oder das Ausblenden des Drop-Labels in einem button-only-Feld, das dennoch Drops ermöglicht. Verwenden Sie `setPreset()` und `setVisible()` zusammen, rufen Sie zuerst `setPreset()` auf.

```java
upload.setVisible(false, Upload.Part.DROP_LABEL);
upload.setVisible(false, Upload.Part.CANCEL_BUTTON);
```

### Standard-Slot {#default-slot}

`Upload` implementiert `HasComponents`. Kinder, die über `add()` hinzugefügt werden, werden innerhalb des Drop-Bereichs gerendert, über der standardmäßigen Chrom. Kombiniert mit der Voreinstellung `HEADLESS`, ermöglicht der Slot, die visuelle Fläche vollständig zu übernehmen, während Verhaltensweisen für Auswähler, Drop und Upload intakt bleiben.

```java
upload.setPreset(Upload.Preset.HEADLESS);
upload.add(new Table<>());
```

Im folgenden Beispiel wird die Voreinstellung `HEADLESS` verwendet, um eine `Table` in den Grenzen des Uploads zu projizieren. Legen Sie eine CSV-Datei ab und ihre Zeilen werden direkt innerhalb der Komponente gerendert, wobei Spalten aus der Kopfzeile der Datei gebildet werden.

<ComponentDemo
path='/webforj/uploaddefaultslot'
files={['src/main/java/com/webforj/samples/views/upload/UploadDefaultSlotView.java']}
height='400px'
/>

## Ereignisse {#events}

`Upload` gibt Ereignisse auf drei Ebenen aus: Dinge, die der Benutzer an der gesamten Komponente macht, der Übertragungsstatus einer einzelnen Datei und der Lebenszyklus des gesamten Batches. Die meisten Apps registrieren ein paar Listener über diese Ebenen hinweg, je nach den erforderlichen Reaktionen. Ein Formular benötigt möglicherweise nur `onUpload`, um zu wissen, wann Dateien den Server erreichen; ein Uploader mit einer Fortschritts-UI benötigt `onListProgress` und `onComplete`; eine Dropzone, die Ablehnungen anzeigen muss, benötigt `onReject`.

Die meisten Ereignisse, die Dateien übertragen, bieten sowohl `getFile()` (die erste oder einzige Datei in der Nutzlast) als auch `getFiles()` (die vollständige Liste). Verwenden Sie `getFile()` für Einzeldateievents wie `onReject`, und `getFiles()` wenn Sie einen Batch erwarten. `UploadCompleteEvent` ist die Ausnahme; es hat seine eigenen `getUploadedFiles()` und `getFailedFiles()` Accessoren, da das Batch-Ergebnis zwischen Erfolgen und Fehlern aufgeteilt ist.

### Benutzeraktionen {#user-actions}

Diese werden als Reaktion auf etwas ausgelöst, das der Benutzer an der Komponente als Ganzes tut. Sie sagen nichts über den Übertragungsfortschritt aus, sondern lediglich, dass der Benutzer etwas getan hat, worauf die App möglicherweise reagieren möchte.

| Ereignis | Auslöser |
| --- | --- |
| `UploadChangeEvent` | Wenn die Liste der ausgewählten Dateien sich ändert |
| `UploadEvent` | Wenn der Benutzer auf **Hochladen** klickt und die Dateien den Server erreichen |
| `UploadCancelEvent` | Wenn der Benutzer auf **Abbrechen** klickt |
| `UploadFilterChangeEvent` | Wenn sich der aktive Filter ändert |

```java
upload.onChange(e -> {
    // Wird ausgelöst, wann immer sich die Liste der ausgewählten Dateien ändert.
    List<UploadedFile> files = e.getFiles();
});

upload.onUpload(e -> {
    // Wird ausgelöst, wenn der Upload ausgelöst wird; Dateien haben den Server erreicht.
});
```

`UploadEvent` und `UploadCompleteEvent` sehen auf den ersten Blick ähnlich aus, beantworten jedoch unterschiedliche Fragen. `UploadEvent` wird ausgelöst, wenn der Benutzer ausdrücklich den Upload auslöst (oder `setAutoUpload()` ihn in seinem Namen auslöst) und ist der natürliche Ort, um die hochgeladenen Dateien zu speichern oder weiterzugeben. `UploadCompleteEvent` wird ausgelöst, wenn die Übertragung jeder wartenden Datei abgeschlossen ist und ist der richtige Aufhänger für "der Batch ist fertig" UI-Updates.

### Übertragung pro Datei {#per-file-transfer}

Diese werden einmal pro Datei ausgelöst, während eine Übertragung stattfindet oder direkt nachdem sie fehlgeschlagen ist. Verwenden Sie sie, wenn die UI den Status einzelner Dateien anstelle des Batches widerspiegeln muss.

| Ereignis | Auslöser |
| --- | --- |
| `UploadProgressEvent` | Während eine einzelne Datei übertragen wird |
| `UploadErrorEvent` | Wenn eine einzelne Dateiübertragung fehlschlägt |
| `UploadRejectEvent` | Wenn eine ausgewählte oder abgelegte Datei die konfigurierten Anforderungen nicht erfüllt |

```java
upload.onProgress(e -> {
    // Wird wiederholt während der Übertragung einer einzelnen Datei ausgelöst.
    double percent = e.getProgress();
});

upload.onReject(e -> {
    // Wird ausgelöst, wenn eine Datei aufgrund von Größe, Anzahl oder Filtergründen abgelehnt wird.
    String reason = e.getMessage();
});
```

Innerhalb dieser Gruppe ist `UploadRejectEvent` das auffällige Element. Es wird ausgelöst, bevor Bytes übertragen werden, wenn eine Datei eine clientseitige Anforderung wie `setMaxFileSize` oder `setMaxFiles` nicht erfüllt. `UploadErrorEvent` hingegen wird ausgelöst, nachdem die Übertragung begonnen hat und etwas auf dem Weg zum Server schiefgelaufen ist.

### Gesamter Batch {#whole-batch}

Diese werden für den Batch und nicht für eine einzelne Datei ausgelöst. Verwenden Sie sie für aggregierte UIs wie eine Fortschrittsanzeige oder eine "Fertig"-Meldung, die den gesamten Auswahlvorgang zusammenfasst.

| Ereignis | Auslöser |
| --- | --- |
| `UploadListProgressEvent` | Zusätzlich zu `UploadProgressEvent`, mit dem Status der gesamten Liste |
| `UploadCompleteEvent` | Einmal pro Batch, wenn jede Datei abgeschlossen ist |

```java
upload.onComplete(e -> {
    // Wird einmal ausgelöst, wenn der gesamte Batch abgeschlossen ist.
    List<UploadedFile> succeeded = e.getUploadedFiles();
    List<UploadedFile> failed = e.getFailedFiles();
});
```

`onProgress` und `onListProgress` decken dieselbe Übertragung aus zwei Perspektiven ab. `onProgress` ist pro Datei und der richtige Aufhänger, wenn jede Datei ihre eigene Fortschritts-UI hat. `onListProgress` wird zusätzlich ausgelöst mit aggregierten Zählern (`getListTotal`, `getListRemaining`, `getListProgress`) für einen einzelnen batchweiten Indikator.

Im folgenden Beispiel steuern `onChange`, `onListProgress` und `onComplete` eine Fortschrittsanzeige und eine Statuszeile, die sich aktualisieren, wenn sich die Dateiliste ändert und während Dateien übertragen werden.

<ComponentDemo
path='/webforj/uploadevents'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadEventsView.java',
]}
height='450px'
/>

## Internationalisierung (i18n) {#internationalization-i18n}

Die Labels und Nachrichten innerhalb der Komponente sind über das `FileUploadI18n`-Bundle anpassbar. Der Bundlestyp verwendet den Namen `FileUploadI18n`, da er mit dem modalen [`FileUploadDialog`](/docs/components/option-dialogs/file-upload) geteilt wird.

```java
FileUploadI18n bundle = new FileUploadI18n();
bundle.setUpload("Senden");
bundle.setCancel("Verwerfen");
bundle.setDropFile("Die Datei hier ablegen");
upload.setI18n(bundle);
```

## Themen {#themes}

`UploadTheme` spiegelt die Standard-DWC-Theme-Palette wider und umfasst umrissene Varianten für ein geringeres visuelles Gewicht. Themen gelten für die Auswähltaste, Upload- und Abbrechenschaltflächen. Die Liste und der Drop-Bereich behalten ein neutrales Styling, unabhängig vom Thema.

```java
upload.setTheme(UploadTheme.PRIMARY);
upload.setTheme(UploadTheme.SUCCESS);
upload.setTheme(UploadTheme.OUTLINED_GRAY);
```

Die folgende Demo zeigt das `PRIMARY`-Thema kombiniert mit der `INLINE`-Voreinstellung.

<ComponentDemo
path='/webforj/uploadthemes'
files={['src/main/java/com/webforj/samples/views/upload/UploadThemesView.java']}
height='200px'
/>

## Styling {#styling}

<TableBuilder name="Upload" />
