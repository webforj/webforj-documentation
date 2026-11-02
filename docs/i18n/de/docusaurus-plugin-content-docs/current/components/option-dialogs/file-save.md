---
title: File Save
sidebar_position: 15
description: >-
  Prompt users for a save location with the FileSaveDialog, configuring initial
  path, default filename, and overwrite behavior.
_i18n_hash: 861257322ca233cfa9e0eed5e4d3aa95
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` ist ein modales Dialogfeld, das es Benutzern ermöglicht, eine Datei an einem bestimmten Ort im Serverdateisystem zu speichern. Der Dialog blockiert die Ausführung der Anwendung, bis der Benutzer einen Dateinamen angibt und die Aktion bestätigt oder den Dialog abbricht.

<!-- INTRO_END -->

## Verwendungen {#usages}

Das `FileSaveDialog` bietet eine optimierte Methode zum Speichern von Dateien im Dateisystem und bietet benutzerkonfigurierbare Optionen für die Benennung von Dateien und den Umgang mit vorhandenen Dateien.

<ComponentDemo
path='/webforj/filesavedialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java']}
height='800px'
/>

## Ergebnis {#result}

Das `FileSaveDialog` gibt den ausgewählten Pfad als String zurück. Wenn der Benutzer den Dialog abbricht, ist das Ergebnis `null`.

:::important Zweck des Dialogs
Dieser Dialog bewirkt nicht tatsächlich, dass Dateien gespeichert werden, sondern gibt den Dateinamen zurück, den der Benutzer ausgewählt hat.
:::

:::info
Der resultierende String wird von der Methode `show()` oder der entsprechenden Methode `OptionDialog` wie unten demonstriert zurückgegeben.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Speichern Sie Ihre Datei", "/home/user/documents", "bericht.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Ausgewählter Pfad: " + result, "Pfad ausgewählt");
} else {
  OptionDialog.showMessageDialog("Kein Pfad ausgewählt", "Pfad ausgewählt",
      MessageDialog.MessageType.ERROR);
}
```

## Existiert-Aktion {#exists-action}

Das `FileSaveDialog` bietet konfigurierbares Verhalten, wenn eine Datei mit dem angegebenen Namen bereits existiert:

* **ACCEPT_WITHOUT_ACTION**: Die Auswahl wird ohne zusätzliche Benutzeraktion akzeptiert.
* **ERROR_DIALOGUE**: Dem Benutzer wird ein Fehlerdialog angezeigt; die Auswahl ist nicht zulässig.
* **CONFIRMATION_DIALOGUE**: Dem Benutzer wird ein Dialog angezeigt, der um Bestätigung bittet. Dies ist die Standardoption.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Speichern Sie Ihre Datei", "/home/user/documents", "bericht.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Auswahlmodus {#selection-mode}

Das `FileSaveDialog` unterstützt verschiedene Auswahlmodi, die es Ihnen ermöglichen, die Auswahlmethode an Ihre spezifischen Bedürfnisse anzupassen:

1. **DATEIEN**: Ermöglicht die Auswahl von Dateien nur.
2. **VERZEICHNISSE**: Ermöglicht die Auswahl von Verzeichnissen nur.
3. **DATEIEN_UND_VERZEICHNISSE**: Ermöglicht die Auswahl von sowohl Dateien als auch Verzeichnissen.

## Anfangspfad {#initial-path}

Geben Sie das Verzeichnis an, in dem der Dialog geöffnet wird, indem Sie den Anfangspfad angeben. Dies hilft den Benutzern, in einem logischen Verzeichnis für ihre Speicheroperation zu starten.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Speichern Sie Ihre Datei", "/home/user/documents", "bericht.xls");
String result = dialog.show();
```

## Einschränkung {#restriction}

Sie können den Dialog auf ein bestimmtes Verzeichnis einschränken, um zu verhindern, dass Benutzer außerhalb davon navigieren, indem Sie die Methode `setRestricted(boolean restricted)` verwenden.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Speichern Sie Ihre Datei", "/home/user/documents", "bericht.xls");
dialog.setRestricted(true);
dialog.show();
```

## Dateiname {#filename}

Legen Sie einen Standarddateinamen für die Speicheroperation fest, um den Benutzern zu helfen und Fehler zu minimieren.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei");
dialog.setName("bericht.xls");
String result = dialog.show();
```

## Internationalisierung (i18n) {#internationalization-i18n}

Verwenden Sie `FileChooserI18n`, um die Titel, Beschreibungen, Labels und Nachrichten des Dialogs für verschiedene lokale Anforderungen oder App-Anforderungen anzupassen.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Abbrechen");
dialog.setI18n(i18n);
```

## Filter {#filters}

Das `FileSaveDialog` ermöglicht es Ihnen, Filter festzulegen, um die Arten von Dateien zu begrenzen, die gespeichert werden können, indem Sie die Methode `setFilters(List<FileChooserFilter> filters)` verwenden.

<ComponentDemo
path='/webforj/filesavedialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java']}
height='800px'
/>

### Benutzerdefinierte Filter {#custom-filters}

Sie können benutzerdefinierte Filter aktivieren, um es Benutzern zu ermöglichen, ihre eigenen Dateifilter mit der Methode `setCustomFilters(boolean customFilters)` zu definieren. Filter werden standardmäßig im lokalen Speicher gespeichert und bei nachfolgenden Dialogaufrufen wiederhergestellt.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Best Practices {#best-practices}

* **Vordefinierte Dateinamen**: Geben Sie einen logischen Standarddateinamen an, wo dies möglich ist.
* **Bestätigen Sie Überschreibungen**: Verwenden Sie `CONFIRMATION_DIALOGUE` für `ExistsAction`, um versehentliche Überschreibungen zu verhindern.
* **Intuitiver Anfangspfad**: Setzen Sie einen Anfangspfad, der den Erwartungen der Benutzer entspricht.
* **Internationalisierung**: Passen Sie den Dialogtext an, um die Benutzerfreundlichkeit für internationale Benutzer zu verbessern.
* **Dateitypfilter**: Nutzen Sie Filter, um Dateitypen einzuschränken und Benutzer zu gültigen Dateiendungen zu leiten.
