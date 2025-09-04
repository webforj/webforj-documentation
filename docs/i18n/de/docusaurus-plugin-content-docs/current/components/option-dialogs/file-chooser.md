---
sidebar_position: 10
title: File Chooser
_i18n_hash: 037ee8efaa2ed2f474d79c7e22dab3b5
---
# Dateiauswahl-Dialog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` ist ein modales Dialogfeld, das es dem Benutzer ermöglicht, eine Datei oder ein Verzeichnis aus dem Dateisystem des Servers auszuwählen. Der Dialog blockiert die Ausführung der App, bis der Benutzer eine Auswahl trifft oder den Dialog schließt.

```java
OptionDialog.showFileChooserDialog("Wählen Sie eine Datei aus");
```

## Verwendungen {#usages}

Der `FileChooserDialog` bietet eine Möglichkeit, Dateien oder Verzeichnisse aus dem Dateisystem auszuwählen, damit die Benutzer Verzeichnisse zum Speichern von Daten auswählen oder Dateivorgänge durchführen können.

<ComponentDemo 
path='/webforj/filechooserdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java'
height = '600px'
/>

## Ergebnis {#result}

Der `FileChooserDialog` gibt die ausgewählte Datei oder das ausgewählte Verzeichnis als Zeichenfolge zurück. Wenn der Benutzer den Dialog schließt, ohne eine Auswahl zu treffen, ist das Ergebnis `null`.

:::info
Die resultierende Zeichenfolge wird aus der `show()`-Methode oder der entsprechenden `OptionDialog`-Methode wie unten gezeigt zurückgegeben.
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

## Startpfad {#initial-path}

Der `FileChooserDialog` ermöglicht es Ihnen, einen Startpfad anzugeben, auf den der Dialog beim Anzeigen geöffnet wird. Dies kann den Benutzern einen Ausgangspunkt für ihre Dateiauswahl bieten.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/home/user");
String result = dialog.show();
```

## Einschränkung {#restriction}

Sie können den Dialog auf ein bestimmtes Verzeichnis beschränken, sodass Benutzer nicht außerhalb dieses Verzeichnisses navigieren können, indem Sie die Methode `setRestricted(boolean restricted)` verwenden.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Filter {#filters}

Wenn der Auswahlmodus `FILES` ist, ermöglicht der `FileChooserDialog` Ihnen, Filter festzulegen, um die Arten von aufgelisteten Dateien zu beschränken. Sie können Filter mit der Methode `setFilters(List<FileChooserFilter> filters)` konfigurieren.

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### Benutzerdefinierte Filter {#custom-filters}

Sie können Benutzern ermöglichen, benutzerdefinierte Filter hinzuzufügen, indem Sie die Funktion der benutzerdefinierten Filter mit der Methode `setCustomFilters(boolean customFilters)` aktivieren. Benutzerdefinierte Filter werden standardmäßig im lokalen Speicher des Browsers gespeichert und wiederhergestellt, wenn der Dialog erneut angezeigt wird.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Labels und Nachrichten innerhalb der Komponente sind vollständig anpassbar mit der Klasse `FileChooserI18n`. Diese Flexibilität ermöglicht es Ihnen, die Dialogoberfläche an spezifische Lokalisierungsanforderungen oder Anpassungspräferenzen anzupassen.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Abbrechen");
dialog.setI18n(i18n);
```

## Beste Praktiken {#best-practices}

1. **Klare und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, was der Benutzer auswählen soll.
2. **Angemessene Auswahlmodi**: Wählen Sie Auswahlmodi, die der erforderlichen Benutzeraktion entsprechen, um genaue und relevante Auswahlen zu gewährleisten.
3. **Logische Startpfade**: Legen Sie Startpfade fest, die den Benutzern einen nützlichen Ausgangspunkt für ihre Auswahl bieten.
4. **Einschränkung der Verzeichnissnavigation**: Beschränken Sie den Dialog, wenn notwendig, auf ein bestimmtes Verzeichnis, um zu verhindern, dass Benutzer zu unbefugten Bereichen navigieren.
