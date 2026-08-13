---
title: File Chooser
sidebar_position: 10
description: >-
  Open a blocking FileChooserDialog to let users pick files or directories from
  the server, with selection modes and initial paths.
_i18n_hash: c86dfab4207241cab3bb28da3e1236ab
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` ist ein modales Dialogfeld, das es dem Benutzer ermöglicht, eine Datei oder ein Verzeichnis aus dem Dateisystem des Servers auszuwählen. Das Dialogfeld blockiert die Ausführung der Anwendung, bis der Benutzer eine Auswahl trifft oder das Dialogfeld schließt.

<!-- INTRO_END -->

## Usages {#usages}

Das `FileChooserDialog` bietet eine Möglichkeit, Dateien oder Verzeichnisse aus dem Dateisystem auszuwählen, sodass Benutzer Verzeichnisse zum Speichern von Daten auswählen oder Dateioperationen durchführen können.

<ComponentDemo
path='/webforj/filechooserdialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java']}
height='600px'
/>

## Result {#result}

Das `FileChooserDialog` gibt die ausgewählte Datei oder das ausgewählte Verzeichnis als String zurück. Wenn der Benutzer das Dialogfeld schließt, ohne eine Auswahl zu treffen, ist das Ergebnis `null`.

:::info
Der resultierende String wird von der Methode `show()` oder der entsprechenden `OptionDialog`-Methode, wie unten gezeigt, zurückgegeben.
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

Das `FileChooserDialog` unterstützt verschiedene Auswahlmodi, die es Ihnen ermöglichen, die Auswahlmethode an Ihre spezifischen Bedürfnisse anzupassen:

1. **FILES**: Erlaubt die Auswahl von Dateien.
2. **DIRECTORIES**: Erlaubt die Auswahl von Verzeichnissen.
3. **FILES_AND_DIRECTORIES**: Erlaubt die Auswahl von sowohl Dateien als auch Verzeichnissen.

## Initial path {#initial-path}

Das `FileChooserDialog` ermöglicht es Ihnen, einen Anfangspfad anzugeben, zu dem das Dialogfeld beim Anzeigen geöffnet wird. Dies kann den Benutzern einen Ausgangspunkt für ihre Dateiauswahl bieten.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/home/user");
String result = dialog.show();
```

## Restriction {#restriction}

Sie können das Dialogfeld auf ein bestimmtes Verzeichnis beschränken und verhindern, dass Benutzer außerhalb davon navigieren, indem Sie die Methode `setRestricted(boolean restricted)` verwenden.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Filters {#filters}

Wenn der Auswahlmodus `FILES` ist, erlaubt das `FileChooserDialog` Ihnen, Filter festzulegen, um die Arten von Dateien, die aufgelistet werden, zu begrenzen. Sie können Filter mit der Methode `setFilters(List<FileChooserFilter> filters)` konfigurieren.

<ComponentDemo
path='/webforj/filechooserdialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java']}
height='600px'
/>

### Custom filters {#custom-filters}

Sie können Benutzern erlauben, benutzerdefinierte Filter hinzuzufügen, indem Sie die Funktion für benutzerdefinierte Filter mit der Methode `setCustomFilters(boolean customFilters)` aktivieren. Benutzerdefinierte Filter werden standardmäßig im lokalen Speicher des Browsers gespeichert und wiederhergestellt, wenn das Dialogfeld erneut angezeigt wird.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Internationalization (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Etiketten und Nachrichten innerhalb der Komponente sind vollständig anpassbar mit der Klasse `FileChooserI18n`. Diese Flexibilität ermöglicht es Ihnen, die Dialogoberfläche an spezifische Lokalisierungsanforderungen oder Personalisierungsvorlieben anzupassen.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Abbrechen");
dialog.setI18n(i18n);
```

## Best practices {#best-practices}

1. **Klare und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, was der Benutzer auswählen soll.
2. **Angemessene Auswahlmodi**: Wählen Sie Auswahlmodi, die der erforderlichen Benutzeraktion entsprechen, um genaue und relevante Auswahlmöglichkeiten sicherzustellen.
3. **Logische Anfangspfade**: Setzen Sie Anfangspfade, die den Benutzern einen nützlichen Ausgangspunkt für ihre Auswahl bieten.
4. **Einschränkung der Verzeichnisnavigation**: Beschränken Sie das Dialogfeld bei Bedarf auf ein bestimmtes Verzeichnis, um zu verhindern, dass Benutzer zu nicht autorisierten Bereichen navigieren.
