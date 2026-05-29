---
title: File Chooser
sidebar_position: 10
_i18n_hash: 3fb68fdcc1fc0d263114babc2a64a6f4
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` ist ein modales Dialogfenster, das es dem Benutzer ermöglicht, eine Datei oder ein Verzeichnis aus dem Dateisystem des Servers auszuwählen. Der Dialog blockiert die Ausführung der Anwendung, bis der Benutzer eine Auswahl trifft oder den Dialog schließt.

<!-- INTRO_END -->

## Usages {#usages}

Der `FileChooserDialog` bietet eine Möglichkeit, Dateien oder Verzeichnisse aus dem Dateisystem auszuwählen, wodurch Benutzer Verzeichnisse zum Speichern von Daten auswählen oder Dateioperationen durchführen können.

<ComponentDemo
path='/webforj/filechooserdialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java']}
height='600px'
/>

## Result {#result}

Der `FileChooserDialog` gibt die ausgewählte Datei oder das ausgewählte Verzeichnis als String zurück. Wenn der Benutzer den Dialog schließt, ohne eine Auswahl zu treffen, ist das Ergebnis `null`.

:::info
Der resultierende String wird aus der `show()`-Methode oder der entsprechenden `OptionDialog`-Methode zurückgegeben, wie unten gezeigt. 
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

Der `FileChooserDialog` unterstützt verschiedene Auswahlmodi, die es Ihnen ermöglichen, die Auswahlmethode an Ihre spezifischen Anforderungen anzupassen:

1. **FILES**: Ermöglicht nur die Auswahl von Dateien.
2. **DIRECTORIES**: Ermöglicht nur die Auswahl von Verzeichnissen.
3. **FILES_AND_DIRECTORIES**: Ermöglicht die Auswahl von sowohl Dateien als auch Verzeichnissen.

## Initial path {#initial-path}

Der `FileChooserDialog` ermöglicht es Ihnen, einen initialen Pfad anzugeben, zu dem der Dialog beim Anzeigen geöffnet wird. Dies kann den Benutzern einen Ausgangspunkt für ihre Dateiauswahl bieten.

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

Wenn der Auswahlmodus `FILES` ist, ermöglicht der `FileChooserDialog` das Setzen von Filtern, um die Arten von Dateien zu beschränken, die aufgelistet werden. Sie können Filter mit der Methode `setFilters(List<FileChooserFilter> filters)` konfigurieren.

<ComponentDemo
path='/webforj/filechooserdialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java']}
height='600px'
/>

### Custom filters {#custom-filters}

Sie können den Benutzern erlauben, benutzerdefinierte Filter hinzuzufügen, indem Sie die Funktion für benutzerdefinierte Filter mit der Methode `setCustomFilters(boolean customFilters)` aktivieren. Benutzerdefinierte Filter werden standardmäßig im lokalen Speicher des Browsers gespeichert und beim erneuten Anzeigen des Dialogs wiederhergestellt.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Internationalization (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Labels und Nachrichten innerhalb der Komponente sind vollständig anpassbar mit der Klasse `FileChooserI18n`. Diese Flexibilität ermöglicht es Ihnen, die Dialogoberfläche an spezifische Lokalisierungsanforderungen oder persönliche Vorlieben anzupassen.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Abbrechen");
dialog.setI18n(i18n);
```

## Best practices {#best-practices}

1. **Klare und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, was der Benutzer auswählen soll.
2. **Angemessene Auswahlmodi**: Wählen Sie Auswahlmodi, die mit der erforderlichen Benutzeraktion übereinstimmen, um genaue und relevante Auswahlen zu gewährleisten.
3. **Logische Anfangspfad**: Setzen Sie Anfangspfade, die den Benutzern einen nützlichen Ausgangspunkt für ihre Auswahl bieten.
4. **Einschränkung der Verzeichnisnavigation**: Beschränken Sie den Dialog auf ein bestimmtes Verzeichnis, wenn notwendig, um zu verhindern, dass Benutzer in nicht autorisierte Bereiche navigieren.
