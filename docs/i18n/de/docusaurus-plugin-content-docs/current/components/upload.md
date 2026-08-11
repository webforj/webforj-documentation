---
title: Upload
sidebar_position: 160
sidebar_class_name: new-content
description: >-
  Select and upload one or more files from the local machine with the Upload
  component using drag-and-drop, filters, and per-file or batch event tracking.
_i18n_hash: 76f8c00c7754fed0a87c27e7963e2877
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-upload" />
<DocChip chip='since' label='26.01' />
<JavadocLink type="foundation" location="com/webforj/component/upload/Upload" top='true'/>


Die `Upload`-Komponente ist ein Inline-Dateiauswahlwerkzeug, das es dem Benutzer ermöglicht, eine oder mehrere Dateien von seinem lokalen Computer auszuwählen und an den Server zu senden. Im Gegensatz zu [`FileUploadDialog`](/docs/components/option-dialogs/file-upload), der den Picker in einem modalen Dialog präsentiert, der die App blockiert, bis der Benutzer fertig ist, wird `Upload` direkt im Seitenlayout gerendert. Es passt überall dorthin, wo ein Datei-Input benötigt wird: ein Profilformular, ein Anhangsfeld neben einer Kommentarfeld oder eine Dropzone auf einer Medienverwaltungsseite.

<!-- INTRO_END -->

:::tip Wann man ein `Upload` verwenden sollte
Verwende die `Upload`-Komponente, wenn die Dateiauswahl mit anderen Aktionen in einem Workflow verbunden ist, wie z.B. das Bearbeiten eines Profils oder das Erstellen eines Beitrags. Greife stattdessen auf [`FileUploadDialog`](/docs/components/option-dialogs/file-upload) zurück, wenn Uploads modal sein sollen, beispielsweise wenn eine Datei unbedingt erforderlich ist, bevor der Benutzer fortfahren kann.
:::

## Erstellen eines Uploads {#creating-an-upload}

Standardmäßig zeigt eine `Upload`-Komponente eine Schaltfläche zum Auswählen, einen Drop-Bereich, die aktuelle Datei-Liste und eine Upload-Schaltfläche. Die Abbrechen-Schaltfläche ist standardmäßig ausgeblendet. Nach dem Erstellen eines `Upload` kannst du Filter hinzufügen, wie zulässige Dateitypen, und ändern, welche Teile sichtbar sind.

```java
Upload upload = new Upload();
upload.addFilter("Bilder", "*.png;*.jpg");
upload.setVisible(false, Upload.Part.LIST);
layout.add(upload);
```

Im folgenden Beispiel wird ein Lebenslauf-`Upload` in ein Einstellungsformular eingefügt, zusammen mit einem Namensfeld und einer Schaltfläche zum Senden.

<ComponentDemo
path='/webforj/upload'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='550px'
/>

## Dateien auswählen {#picking-files}

Wie der Picker funktioniert, wird durch einige unabhängige Einstellungen gesteuert: wie viele Dateien der Benutzer gleichzeitig auswählen kann, was vom lokalen Dateisystem auswählbar ist und welche Typen im Datei-Dialog sichtbar sind. Gemeinsam formen sie die Auswahl-Erfahrung, um sie an das Feld anzupassen.

Hier ist ein Galerie-Uploader, der sowohl Bild- als auch Video-Filter, Mehrfachauswahl und eine Begrenzung auf 20 Dateien konfiguriert hat:

<ComponentDemo
path='/webforj/uploadpickingfiles'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPickingFilesView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='450px'
/>

### Auswahlmodus {#selection-mode}

Der Auswahlmodus begrenzt den Picker auf eine Datei oder viele. `MULTIPLE` ist der Standard und eignet sich für Batch-Operationen wie Fotogalerien oder Rechnungsanhänge. `SINGLE` passt zu Feldern, die konzeptionell nur einen Wert speichern, wie ein Profilfoto oder einen unterzeichneten Vertrag.

```java
upload.setSelectionMode(Upload.SelectionMode.SINGLE);
upload.setSelectionMode(Upload.SelectionMode.MULTIPLE);
```

### Picker-Quelle {#picker-source}

Die Picker-Quelle bestimmt, was der Benutzer vom lokalen Dateisystem auswählen kann. Der Standard, `FILES`, öffnet einen standardmäßigen Datei-Dialog. `DIRECTORY` ermöglicht es dem Benutzer, einen Ordner auszuwählen und die obersten Dateien hochzuladen. `DIRECTORY_RECURSIVE` durchläuft den gesamten Baum und lädt jede Datei darin hoch.

```java
upload.setPicker(Upload.Picker.DIRECTORY_RECURSIVE);
```

Ordner-Uploads eignen sich für Tools, die Ordnerstrukturen spiegeln, wie Bereitstellungssysteme, Asset-Management-Apps oder Backup-Utilities. Für die meisten Formularfelder ist der Standard-Datepicker die richtige Wahl.

### Filter {#filters}

Filter begrenzen, was der Benutzer vom lokalen Dateisystem auswählen kann. Jeder Filter hat eine Beschreibung und ein oder mehrere Glob-Muster, die durch Semikolons getrennt sind. Der aktive Filter erscheint in einem Dropdown neben der Picker-Schaltfläche, und der Benutzer kann zwischen ihnen wechseln.

```java
upload.addFilter("Bilder", "*.png;*.jpg;*.jpeg");
upload.addFilter("Dokumente", "*.pdf;*.docx");
upload.setActiveFilter("Bilder");
```

Einige verwandte Einstellungen bestimmen, wie das Filter-Dropdown funktioniert: `setFiltersVisible(false)` versteckt das Dropdown, während die Filter aktiv bleiben, `setMultiFilterSelection(true)` erlaubt es dem Benutzer, Filter zu kombinieren, und `setAllFilesFilterEnabled(false)` entfernt die implizite "Alle Dateien"-Option.

Einige dieser Einstellungen gelten nur für den standardmäßigen Picker. Wenn die File System Access API verwendet wird, verwaltet der native OS-Picker die Filterauswahl selbst, sodass `setFiltersVisible(false)` ignoriert wird und `setMultiFilterSelection(true)` keine Wirkung hat (der native Picker akzeptiert nur einen Filter gleichzeitig). Deaktiviere die File System Access API mit `setFileSystemAccess(false)`, um diese Einstellungen browserübergreifend zuverlässig zu machen.

### Drop-Zone {#drop-zone}

Dateien können vom Desktop gezogen und auf die Komponente fallen gelassen werden. Das Drop-Label ändert sich, wenn eine Datei darüber schwebt, was signalisiert, dass der Drop akzeptiert wird. Das Drop ist standardmäßig aktiviert und kann deaktiviert werden, wenn der Picker nur Dateien aus dem Datei-Dialog akzeptieren soll.

```java
upload.setDrop(false);
```

## Validierung und Limits {#validation-and-limits}

`setMaxFileSize` begrenzt die Bytegröße einer einzelnen Datei, und `setMaxFiles` begrenzt die Gesamtanzahl der Dateien in einem Batch. Beide laufen, bevor irgendwelche Bytes übertragen werden, sodass eine zu große Datei auf der Client-Seite abgelehnt wird, ohne Bandbreite zu verbrauchen.

```java
upload.setMaxFileSize(5 * 1024 * 1024); // 5 MB
upload.setMaxFiles(10);
```

Wenn eine ausgewählte oder abgelegte Datei eine dieser Begrenzungen überschreitet, wird `UploadRejectEvent` mit dem Grund ausgelöst. Die serverseitige Eigenschaft `webforj.fileUpload.maxSize` gilt weiterhin und fungiert als harte Grenze, unabhängig von der clientseitigen Beschränkung.

:::warning Serverseitige Validierung
Filter, maximale Größe und maximale Dateizahl werden in der Benutzeroberfläche durchgesetzt, um den Benutzer zu leiten, nicht um den Server zu schützen. Jede hochgeladene Datei sollte auf dem Server erneut überprüft werden, bevor sie gespeichert wird, und die temporären Dateien sollten kurz nach Abschluss des Uploads verschoben oder gelöscht werden.
:::

## Upload-Verhalten {#upload-behavior}

Sobald Dateien ausgewählt sind, gibt es zwei Entscheidungen: wann der Upload beginnt und was mit bestehenden Einträgen passiert, wenn der Benutzer erneut auswählt. Standardmäßig klickt der Benutzer auf **Upload**, um den Transfer zu starten, und bestehende Einträge bleiben in der Liste, bis sie ausdrücklich gelöscht werden.

### Automatischer Upload {#auto-upload}

Der Standardmodus ist `NONE`, wo der Benutzer auf **Upload** klickt, um den Transfer zu starten. `setAutoUpload()` entfernt diesen Klick und startet den Transfer, sobald Dateien ausgewählt, abgelegt oder beides werden.

- **`NONE`** überlässt das Hochladen dem Benutzer, der auf **Upload** klickt.
- **`ON_SELECT`** lädt sofort hoch, wenn Dateien über den Datei-Dialog ausgewählt werden.
- **`ON_DROP`** lädt sofort hoch, wenn Dateien auf die Komponente abgelegt werden.
- **`ALWAYS`** umfasst beide Wege.

:::tip Kombination mit Presets
Automatischer Upload passt gut zu den Presets `BUTTON_ONLY` oder `INLINE`, wo es ohnehin keinen Upload-Button für den Benutzer gibt. Für Workflows, in denen der Benutzer die Auswahl vor dem Senden überprüfen muss, sollte der automatische Upload deaktiviert bleiben.
:::

### Automatisches Löschen {#auto-clear}

Wenn der Benutzer eine neue Gruppe auswählt, entscheidet das automatische Löschen, was mit den bereits in der Liste vorhanden Einträgen geschehen soll. Das Löschen erfolgt im Moment der nächsten Auswahl, nicht beim Abschluss des Uploads, sodass abgeschlossene Uploads sichtbar bleiben, bis der Benutzer erneut auswählt.

- **`COMPLETED`** löscht erfolgreich hochgeladene Einträge.
- **`IN_PROGRESS`** bricht Einträge ab und löscht sie, die noch übertragen werden.
- **`ALL`** löscht alles.
Wartende Einträge, die noch nicht mit dem Hochladen begonnen haben, werden unabhängig von der Einstellung beibehalten.

```java
upload.setAutoClear(Upload.AutoClear.COMPLETED);
upload.setAutoClear(Upload.AutoClear.IN_PROGRESS);
upload.setAutoClear(Upload.AutoClear.ALL);
```

:::warning Automatisches Löschen hat subtile Auslöser
Automatisches Löschen tritt erst in Kraft, wenn eine zuvor ausgewählte Datei tatsächlich mit dem Hochladen begonnen oder abgeschlossen hat. Ohne einen Upload zwischen den Auswahlvorgängen passt keine Datei in den Filter und die Liste wächst weiter.
:::

Greife auf `COMPLETED` in Upladern zu, die auf dem Bildschirm über mehrere Aktionen hinweg leben, wie bei einem Chat-Komponisten, wo jede Nachricht ihre eigenen Anhänge hat, oder in einem Kommentarfeld, das für jede Antwort erneut verwendet wird. Ohne es sammelt die Liste der vorherigen Erfolge an, während der Benutzer arbeitet.

### Programmatische Aktionen {#programmatic-actions}

Die meisten Uploads beginnen mit einem Benutzerklick, aber die gleichen Aktionen sind auch aus dem Servercode verfügbar. Beide arbeiten an den Dateien, die der Benutzer bereits ausgewählt hat; es gibt keine Möglichkeit, Dateien im Namen des Benutzers vom Server auszuwählen.

```java
// Lade die aktuelle Auswahl hoch, als ob der Benutzer auf Upload geklickt hätte
upload.upload();

// Breche alle laufenden Übertragungen ab
upload.cancel();
```

Rufe `upload()` auf, um den Transfer von einer Steuerung außerhalb der Komponente auszulösen, wie z.B. von einer einzelnen Senden-Schaltfläche, die von einem größeren Formular geteilt wird. Rufe `cancel()` von einer "stop" Schaltfläche außerhalb der Komponente auf oder von einem Routen-Wächter, wenn der Benutzer während des Transfers navigiert.

## Mobiler Capture {#mobile-capture}

Auf mobilen Geräten öffnet Capture die Kamera oder das Mikrofon als Picker-Quelle anstelle des Datei-Browsers. `USER` zielt auf die Frontkamera oder das Mikrofon ab, `ENVIRONMENT` zielt auf die Rückkamera ab, und `NONE` (der Standard) verwendet den standardmäßigen Datei-Picker.

```java
upload.setCapture(Upload.Capture.ENVIRONMENT);
upload.addFilter("Foto", "*.jpg;*.png");
```

:::tip Capture und Filter
Begrenze die Auswahl auf Bild-Erweiterungen, sodass die Kamera im Standbildmodus geöffnet wird, oder auf Video-Erweiterungen, sodass sie im Aufnahme-Modus geöffnet wird. Ohne einen entsprechenden Filter fällt ein Capturing-Modus auf den standardmäßigen Picker auf den meisten Plattformen zurück. Desktop-Browser ignorieren die Capture-Einstellung völlig.
:::

Für mobile-first Apps passt Capture gut zu [installierbaren Apps](/docs/configuration/installable-apps), wo die Kamera und das Mikrofon Teil der Erfahrung auf dem Startbildschirm werden.

## Native Dateizugriffssteuerung {#native-file-system-access}

Die Komponente verwendet die [File System Access API](https://developer.mozilla.org/en-US/docs/Web/API/File_System_Access_API) des Browsers, wenn die Plattform dies unterstützt. Der native Picker kann der Seite persistenten Zugriff auf einen Ordner gewähren, sodass der Benutzer einmal auswählt, und nachfolgende Uploads aus demselben Ordner überspringen den Dialog. Bei Browsern ohne Unterstützung fällt die Komponente automatisch auf den standardmäßigen Picker zurück.

```java
upload.setFileSystemAccess(false); // zwinge den standardmäßigen Picker
```

Deaktiviere es, wenn jeder Upload von einem frischen Dialog ausgehen soll, oder wenn konsistentes Verhalten in jedem Browser wichtiger ist als der Komfort des persistenten Zugriffs.

## Anpassen des Layouts {#customizing-the-layout}

Die Komponente besteht aus fünf Teilen: der Picker-Schaltfläche, dem Drop-Label, der Dateiliste, der Upload-Schaltfläche und der Abbrechen-Schaltfläche. Die ersten vier sind standardmäßig sichtbar; die Abbrechen-Schaltfläche ist ausgeblendet und kann mit `setVisible(true, Upload.Part.CANCEL_BUTTON)` angezeigt werden. Das Layout kann mit Presets für gängige Picker-Formate oder mit Sichtbarkeitssteuerungen pro Teil für feinere Anpassungen umgestaltet werden.

### Presets {#presets}

Presets bündeln mehrere Sichtbarkeitseinstellungen für Teile in benannten Picker-Formaten. Sie sind ein schnellerer Weg, um eine gängige Konfiguration zu erreichen, als Teile einzeln zu aktivieren oder zu deaktivieren.

- **`FULL`**: Picker-Schaltfläche, Drop-Label, Dateiliste und Upload-Schaltfläche. Der Standard.
- **`INLINE`**: Picker-Schaltfläche und Drop-Label, mit der aktuellen Auswahl als Text neben dem Picker gerendert. Nützlich für kompakte Formularfelder.
- **`BUTTON_ONLY`**: Nur die Picker-Schaltfläche. Nützlich, wenn die umgebende Benutzeroberfläche bereits die ausgewählten Dateien anzeigt.
- **`DROPZONE`**: Drop-Label und Dateiliste, keine Picker-Schaltfläche. Nützlich, wenn Drag-and-Drop die einzige Möglichkeit sein soll, Dateien hinzuzufügen.
- **`HEADLESS`**: Jeder Teil ist ausgeblendet, wobei der äußere Rand, der Radius und die Polsterung zusammengeklappt werden, sodass der projizierte Inhalt direkt innerhalb der Komponentenbegrenzungen sitzt.

```java
upload.setPreset(Upload.Preset.INLINE);
```

<ComponentDemo
path='/webforj/uploadpresets'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPresetsView.java',
  'src/main/frontend/css/upload/uploadPresets.css'
]}
height='650px'
/>

### Sichtbarkeit der Teile {#part-visibility}

Wenn ein Preset nah aber nicht ganz dem gewünschten Format entspricht, können einzelne Teile angezeigt oder ausgeblendet werden. Dies ist nützlich für kleine Anpassungen wie das Ausblenden der Abbrechen-Schaltfläche bei einem Einzelfile-Loader, der sofort hochlädt, oder das Ausblenden des Drop-Labels in einem button-only Feld, das dennoch Ablagen erlaubt. Wenn `setPreset()` und `setVisible()` zusammen verwendet werden, rufe zuerst `setPreset()` auf.

```java
upload.setVisible(false, Upload.Part.DROP_LABEL);
upload.setVisible(false, Upload.Part.CANCEL_BUTTON);
```

### Standard-Slot {#default-slot}

`Upload` implementiert `HasComponents`. Kinder, die über `add()` hinzugefügt werden, werden im Drop-Bereich gerendert, oberhalb des standardmäßigen Chromas. In Kombination mit dem `HEADLESS`-Preset ermöglicht der Slot, die visuelle Fläche vollständig zu übernehmen, während das Picker-, Drop- und Upload-Verhalten intakt bleibt.

```java
upload.setPreset(Upload.Preset.HEADLESS);
upload.add(new Table<>());
```

Im folgenden Beispiel wird das `HEADLESS`-Preset verwendet, um eine `Table` innerhalb der Grenzen von Upload zu projizieren. Füge eine CSV-Datei hinzu und ihre Zeilen werden direkt innerhalb der Komponente gerendert, wobei die Spalten aus der Kopfzeile der Datei erstellt werden.

<ComponentDemo
path='/webforj/uploaddefaultslot'
files={['src/main/java/com/webforj/samples/views/upload/UploadDefaultSlotView.java']}
height='400px'
/>

## Ereignisse {#events}

`Upload` gibt Ereignisse auf drei Ebenen aus: Dinge, die der Benutzer mit der gesamten Komponente tut, der Übertragungszustand einer einzelnen Datei und der Lebenszyklus des Batches insgesamt. Die meisten Apps registrieren einige Listener über diese Ebenen hinweg, je nachdem, worauf sie reagieren müssen. Ein Formular benötigt möglicherweise nur `onUpload`, um zu wissen, wann die Dateien den Server erreicht haben; ein Uploader mit einer Fortschrittsbenutzeroberfläche benötigt `onListProgress` und `onComplete`; eine Dropzone, die Ablehnungen anzeigen muss, benötigt `onReject`.

Die meisten Ereignisse, die Dateien übertragen, zeigen sowohl `getFile()` (die erste oder einzige Datei im Payload) als auch `getFiles()` (die vollständige Liste). Verwende `getFile()` für Ereignisse mit einer einzelnen Datei wie `onReject`, und `getFiles()` wenn du einen Batch erwartest. `UploadCompleteEvent` ist die Ausnahme; es hat seine eigenen `getUploadedFiles()` und `getFailedFiles()` Zugriffsmethoden, da das Ergebnis des Batches zwischen Erfolgen und Misserfolgen aufgeteilt ist.

### Benutzeraktionen {#user-actions}

Diese werden in Reaktion auf etwas ausgelöst, was der Benutzer mit der Komponente als Ganzes tut. Sie sagen nichts über den Übertragungsfortschritt aus, nur dass der Benutzer etwas getan hat, worauf die App reagieren möchte.

| Ereignis | Wird ausgelöst |
| --- | --- |
| `UploadChangeEvent` | Wenn sich die Liste der ausgewählten Dateien ändert |
| `UploadEvent` | Wenn der Benutzer auf **Upload** klickt und die Dateien den Server erreichen |
| `UploadCancelEvent` | Wenn der Benutzer auf **Abbrechen** klickt |
| `UploadFilterChangeEvent` | Wenn der aktive Filter geändert wird |

```java
upload.onChange(e -> {
    // Wird ausgelöst, wenn die Liste der ausgewählten Dateien geändert wird.
    List<UploadedFile> files = e.getFiles();
});

upload.onUpload(e -> {
    // Wird ausgelöst, wenn der Upload gestartet wird; Dateien haben den Server erreicht.
});
```

`UploadEvent` und `UploadCompleteEvent` sehen auf den ersten Blick ähnlich aus, beantworten jedoch unterschiedliche Fragen. `UploadEvent` wird ausgelöst, wenn der Benutzer den Upload explizit auslöst (oder `setAutoUpload()` es in deren Namen tut), und ist der natürliche Ort, um die hochgeladenen Dateien zu speichern oder weiterzugeben. `UploadCompleteEvent` wird ausgelöst, sobald der Transfer jeder in der Warteschlange stehenden Datei abgeschlossen ist, und ist der richtige Hook für Benutzeroberflächenaktualisierungen, die sagen "Der Batch ist fertig".

### Pro-File-Transfer {#per-file-transfer}

Diese werden einmal pro Datei ausgelöst, während ein Transfer stattfindet oder direkt nachdem er fehlgeschlagen ist. Verwende sie, wenn die Benutzeroberfläche den Status einzelner Dateien und nicht des Batches widerspiegeln muss.

| Ereignis | Wird ausgelöst |
| --- | --- |
| `UploadProgressEvent` | Während eine einzelne Datei übertragen wird |
| `UploadErrorEvent` | Wenn ein einzelner Datei-Transfer fehlschlägt |
| `UploadRejectEvent` | Wenn eine ausgewählte oder abgelegte Datei die konfigurierten Einschränkungen nicht erfüllt |

```java
upload.onProgress(e -> {
    // Wird während der Übertragung einer einzelnen Datei wiederholt ausgelöst.
    double percent = e.getProgress();
});

upload.onReject(e -> {
    // Wird ausgelöst, wenn eine Datei wegen Größe, Anzahl oder Filtergründen abgelehnt wird.
    String reason = e.getMessage();
});
```

Innerhalb dieser Gruppe ist `UploadRejectEvent` die Ausnahme. Es wird ausgelöst, bevor Bytes übertragen werden, wenn eine Datei eine clientseitige Einschränkung wie `setMaxFileSize` oder `setMaxFiles` nicht erfüllt. `UploadErrorEvent` hingegen wird ausgelöst, nachdem der Transfer begonnen hat und unterwegs etwas schiefgelaufen ist.

### Gesamter Batch {#whole-batch}

Diese werden auf den Batch und nicht auf eine einzelne Datei ausgelöst. Verwende sie für die aggregierte Benutzeroberfläche, wie eine Gesamtfortschrittsanzeige oder eine "Fertig"-Nachricht, die die gesamte Auswahl zusammenfasst.

| Ereignis | Wird ausgelöst |
| --- | --- |
| `UploadListProgressEvent` | Zusammen mit `UploadProgressEvent`, mit dem Status der gesamten Liste |
| `UploadCompleteEvent` | Einmal pro Batch, wenn jede Datei mit dem Übertragen fertig ist |

```java
upload.onComplete(e -> {
    // Wird einmal ausgelöst, wenn der gesamte Batch abgeschlossen ist.
    List<UploadedFile> succeeded = e.getUploadedFiles();
    List<UploadedFile> failed = e.getFailedFiles();
});
```

`onProgress` und `onListProgress` behandeln denselben Transfer aus zwei Perspektiven. `onProgress` ist pro Datei und der richtige Hook, wenn jede Datei ihre eigene Fortschrittsbenutzeroberfläche hat. `onListProgress` wird gleichzeitig ausgelöst und bietet aggregierte Zähler (`getListTotal`, `getListRemaining`, `getListProgress`) für einen einzelnen batchweiten Indikator.

Im folgenden Beispiel steuern `onChange`, `onListProgress` und `onComplete` eine Fortschrittsleiste und eine Statuszeile, die sich aktualisieren, während sich die Dateiliste ändert und Dateien übertragen werden.

<ComponentDemo
path='/webforj/uploadevents'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadEventsView.java',
  'src/main/frontend/css/upload/uploadEvents.css'
]}
height='450px'
/>

## Internationalisierung (i18n) {#internationalization-i18n}

Die Beschriftungen und Nachrichten innerhalb der Komponente sind über das Bundle `FileUploadI18n` anpassbar. Der Bündeltyp behält den Namen `FileUploadI18n`, da er mit dem modalen [`FileUploadDialog`](/docs/components/option-dialogs/file-upload) geteilt wird.

```java
FileUploadI18n bundle = new FileUploadI18n();
bundle.setUpload("Senden");
bundle.setCancel("Verwerfen");
bundle.setDropFile("Datei hier ablegen");
upload.setI18n(bundle);
```

## Themen {#themes}

`UploadTheme` spiegelt die Standardfarbpalette von DWC wider und enthält umrissene Varianten für ein leichteres visuelles Gewicht. Themen gelten für die Picker-, Upload- und Abbrechen-Schaltflächen. Die Liste und der Drop-Bereich behalten eine neutrale Gestaltung bei, unabhängig vom Thema.

```java
upload.setTheme(UploadTheme.PRIMARY);
upload.setTheme(UploadTheme.SUCCESS);
upload.setTheme(UploadTheme.OUTLINED_GRAY);
```

Die folgende Demo zeigt das `PRIMARY`-Thema kombiniert mit dem `INLINE`-Preset.

<ComponentDemo
path='/webforj/uploadthemes'
files={['src/main/java/com/webforj/samples/views/upload/UploadThemesView.java']}
height='200px'
/>

## Stilgebung {#styling}

<TableBuilder name="Upload" />
