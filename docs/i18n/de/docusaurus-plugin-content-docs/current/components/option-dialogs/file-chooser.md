---
sidebar_position: 10
title: File Chooser
_i18n_hash: d0efdadb8ec1e44cfab2fb26f95efa0d
---
# Dateiauswahl-Dialog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` ist ein modaler Dialog, der es dem Benutzer ermöglicht, eine Datei oder ein Verzeichnis aus dem Server-Dateisystem auszuwählen. Der Dialog blockiert die Ausführung der Anwendung, bis der Benutzer eine Auswahl trifft oder den Dialog schließt.

```java
OptionDialog.showFileChooserDialog("Wählen Sie eine Datei aus");
```

## Verwendungen {#usages}

Der `FileChooserDialog` bietet eine Möglichkeit, Dateien oder Verzeichnisse aus dem Dateisystem auszuwählen, sodass Benutzer Verzeichnisse zum Speichern von Daten auswählen oder Dateioperationen durchführen können.

<ComponentDemo 
path='/webforj/filechooserdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java'
height = '600px'
/>

## Ergebnis {#result}

Der `FileChooserDialog` gibt die ausgewählte Datei oder das ausgewählte Verzeichnis als Zeichenfolge zurück. Wenn der Benutzer den Dialog schließt, ohne eine Auswahl zu treffen, ist das Ergebnis `null`.

:::info
Die resultierende Zeichenfolge wird von der `show()`-Methode oder der entsprechenden `OptionDialog`-Methode zurückgegeben, wie unten gezeigt. 
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

## Auswahlmodus {#selection-mode}

Der `FileChooserDialog` unterstützt verschiedene Auswahlmodi, die es Ihnen ermöglichen, die Auswahlmethode an Ihre spezifischen Bedürfnisse anzupassen:

1. **FILES**: Ermöglicht die Auswahl von Dateien.
2. **DIRECTORIES**: Ermöglicht die Auswahl von Verzeichnissen.
3. **FILES_AND_DIRECTORIES**: Ermöglicht die Auswahl von sowohl Dateien als auch Verzeichnissen.

## Anfangspfad {#initial-path}

Der `FileChooserDialog` ermöglicht es Ihnen, einen Anfangspfad anzugeben, zu dem der Dialog beim Anzeigen geöffnet wird. Dies kann den Benutzern einen Ausgangspunkt für ihre Dateiauswahl bieten.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/home/user");
String result = dialog.show();
```

## Einschränkung {#restriction}

Sie können den Dialog auf ein bestimmtes Verzeichnis beschränken, um zu verhindern, dass Benutzer außerhalb davon navigieren, indem Sie die Methode `setRestricted(boolean restricted)` verwenden.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Filter {#filters}

Wenn der Auswahlmodus `FILES` ist, ermöglicht der `FileChooserDialog` das Setzen von Filtern, um die Arten von aufgeführten Dateien einzuschränken. Sie können Filter mit der Methode `setFilters(List<FileChooserFilter> filters)` konfigurieren.

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### Benutzerdefinierte Filter {#custom-filters}

Sie können Benutzern erlauben, benutzerdefinierte Filter hinzuzufügen, indem Sie die benutzerdefinierte Filterfunktion aktivieren, indem Sie die Methode `setCustomFilters(boolean customFilters)` verwenden. Benutzerdefinierte Filter werden standardmäßig im lokalen Speicher des Browsers gespeichert und beim erneuten Anzeigen des Dialogs wiederhergestellt.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Labels und Nachrichten innerhalb der Komponente sind vollständig anpassbar mit der Klasse `FileChooserI18n`. Diese Flexibilität ermöglicht es Ihnen, die Dialogoberfläche an spezifische Lokalisierungsanforderungen oder Personalisierungspräferenzen anzupassen.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Stornieren");
dialog.setI18n(i18n);
```

## Beste Praktiken {#best-practices}

1. **Klare und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, was der Benutzer auswählen soll.
2. **Angemessene Auswahlmodi**: Wählen Sie Auswahlmodi, die mit der erforderlichen Benutzeraktion übereinstimmen, um genaue und relevante Auswahlmöglichkeiten zu gewährleisten.
3. **Logische Anfangspfade**: Setzen Sie Anfangspfade, die den Benutzern einen nützlichen Ausgangspunkt für ihre Auswahl bieten.
4. **Einschränkung der Verzeichnisnavigation**: Beschränken Sie den Dialog auf ein bestimmtes Verzeichnis, wenn nötig, um zu verhindern, dass Benutzer in unbefugte Bereiche navigieren.
