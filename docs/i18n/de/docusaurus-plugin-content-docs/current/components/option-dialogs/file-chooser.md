---
title: File Chooser
sidebar_position: 10
_i18n_hash: 49a069004ead8d962b32e132183819e8
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` ist ein modales Dialogfeld, das es dem Benutzer ermöglicht, eine Datei oder ein Verzeichnis aus dem Dateisystem des Servers auszuwählen. Der Dialog blockiert die Ausführung der Anwendung, bis der Benutzer eine Auswahl trifft oder den Dialog schließt.

<!-- INTRO_END -->

## Usages {#usages}

Der `FileChooserDialog` bietet eine Möglichkeit, Dateien oder Verzeichnisse aus dem Dateisystem auszuwählen, sodass Benutzer Verzeichnisse zum Speichern von Daten auswählen oder Dateioperationen durchführen können.

<ComponentDemo 
path='/webforj/filechooserdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java'
height = '600px'
/>

## Result {#result}

Der `FileChooserDialog` gibt die ausgewählte Datei oder das ausgewählte Verzeichnis als Zeichenfolge zurück. Wenn der Benutzer den Dialog schließt, ohne eine Auswahl zu treffen, ist das Ergebnis `null`.

:::info
Die resultierende Zeichenfolge wird von der Methode `show()` oder der entsprechenden `OptionDialog`-Methode zurückgegeben, wie unten gezeigt. 
:::

```java showLineNumbers
String result = OptionDialog.showFileChooserDialog(
    "Wählen Sie eine Datei aus", "/home/user", FileChooserDialog.SelectionMode.FILES);

if (result != null) {
    OptionDialog.showMessageDialog("Sie haben ausgewählt: " + result, "Auswahl getroffen");
} else {
    OptionDialog.showMessageDialog("Keine Auswahl getroffen", "Auswahl abgebrochen");
}
```

## Selection mode {#selection-mode}

Der `FileChooserDialog` unterstützt verschiedene Auswahlmodi, sodass Sie die Auswahlmethode an Ihre spezifischen Anforderungen anpassen können:

1. **FILES**: Ermöglicht die Auswahl von Dateien.
2. **DIRECTORIES**: Ermöglicht die Auswahl von Verzeichnissen.
3. **FILES_AND_DIRECTORIES**: Ermöglicht die Auswahl von sowohl Dateien als auch Verzeichnissen.

## Initial path {#initial-path}

Der `FileChooserDialog` ermöglicht es Ihnen, einen Anfangspfad anzugeben, zu dem der Dialog beim Anzeigen geöffnet wird. Dies kann den Benutzern einen Ausgangspunkt für ihre Dateiauswahl bieten.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/home/user");
String result = dialog.show();
```

## Restriction {#restriction}

Sie können den Dialog auf ein bestimmtes Verzeichnis beschränken, um zu verhindern, dass Benutzer außerhalb davon navigieren, indem Sie die Methode `setRestricted(boolean restricted)` verwenden.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Filters {#filters}

Wenn der Auswahlmodus `FILES` ist, ermöglicht der `FileChooserDialog` das Festlegen von Filtern, um die aufgelisteten Dateitypen zu beschränken. Sie können Filter mit der Methode `setFilters(List<FileChooserFilter> filters)` konfigurieren.

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### Custom filters {#custom-filters}

Sie können Benutzern erlauben, benutzerdefinierte Filter hinzuzufügen, indem Sie die Funktion für benutzerdefinierte Filter mit der Methode `setCustomFilters(boolean customFilters)` aktivieren. Benutzerdefinierte Filter werden standardmäßig im lokalen Speicher des Browsers gespeichert und beim nächsten Anzeigen des Dialogs wiederhergestellt.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Internationalization (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Etiketten und Nachrichten innerhalb des Elements sind vollständig anpassbar mit der Klasse `FileChooserI18n`. Diese Flexibilität ermöglicht es Ihnen, die Dialogoberfläche an spezifische Lokalisierungsanforderungen oder persönliche Präferenzen anzupassen.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Stornieren");
dialog.setI18n(i18n);
```

## Best practices {#best-practices}

1. **Klare und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderung klar erklärt, was der Benutzer auswählen soll.
2. **Geeignete Auswahlmodi**: Wählen Sie Auswahlmodi, die der erforderlichen Benutzeraktion entsprechen, um genaue und relevante Auswahlen zu gewährleisten.
3. **Logische Anfangspfade**: Legen Sie Anfangspfade fest, die den Benutzern einen nützlichen Ausgangspunkt für ihre Auswahl bieten.
4. **Einschränkung der Verzeichnissnavigation**: Beschränken Sie den Dialog auf ein bestimmtes Verzeichnis, wenn erforderlich, um zu verhindern, dass Benutzer in nicht autorisierte Bereiche navigieren.
