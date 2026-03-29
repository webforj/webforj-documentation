---
title: File Save
sidebar_position: 15
_i18n_hash: 728fd2b9211c993aed9b00abddb29b3e
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` ist ein modales Dialogfeld, das Benutzern die Möglichkeit bietet, eine Datei an einem bestimmten Ort im Dateisystem des Servers zu speichern. Der Dialog blockiert die Ausführung der Anwendung, bis der Benutzer einen Dateiname angibt und die Aktion bestätigt oder den Dialog abbricht.

<!-- INTRO_END -->

## Verwendungszwecke {#usages}

Das `FileSaveDialog` bietet eine unkomplizierte Methode zum Speichern von Dateien im Dateisystem und bietet benutzerkonfigurierte Optionen für die Benennung von Dateien und den Umgang mit vorhandenen Dateien.

<ComponentDemo 
path='/webforj/filesavedialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java'
height = '800px'
/>

## Ergebnis {#result}

Das `FileSaveDialog` gibt den ausgewählten Pfad als String zurück. Wenn der Benutzer den Dialog abbricht, wird das Ergebnis `null` sein.

:::important Zweck des Dialogs
Dieser Dialog speichert tatsächlich keine Dateien, sondern gibt den vom Benutzer ausgewählten Dateinamen zurück.
:::

:::info
Der resultierende String wird aus der Methode `show()` oder der entsprechenden Methode von `OptionDialog` zurückgegeben, wie unten gezeigt.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Speichern Sie Ihre Datei", "/home/user/documents", "bericht.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Datei gespeichert unter: " + path, "Ausgewählter Pfad");
} else {
  OptionDialog.showMessageDialog("Kein Pfad ausgewählt", "Ausgewählter Pfad",
      MessageDialog.MessageType.ERROR);
}
```

## Exists-Aktion {#exists-action}

Das `FileSaveDialog` bietet konfigurierbares Verhalten, wenn eine Datei mit dem angegebenen Namen bereits existiert:

* **ACCEPT_WITHOUT_ACTION**: Die Auswahl wird akzeptiert, ohne dass der Benutzer weitere Aktionen durchführen muss.
* **ERROR_DIALOGUE**: Der Benutzer erhält ein Fehlerdialogfeld; die Auswahl ist nicht erlaubt.
* **CONFIRMATION_DIALOGUE**: Der Benutzer erhält ein Dialogfeld, das um Bestätigung bittet. Dies ist die Standardeinstellung.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Speichern Sie Ihre Datei", "/home/user/documents", "bericht.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Auswahlmodus {#selection-mode}

Das `FileSaveDialog` unterstützt verschiedene Auswahlmodi, die es Ihnen ermöglichen, die Auswahlmethode an Ihre spezifischen Bedürfnisse anzupassen:

1. **DATEIEN**: Erlaubt die Auswahl von Dateien.
2. **VERZEICHNISSE**: Erlaubt die Auswahl von Verzeichnissen.
3. **DATEIEN UND VERZEICHNISSE**: Erlaubt die Auswahl von sowohl Dateien als auch Verzeichnissen.

## Anfangspfad {#initial-path}

Geben Sie das Verzeichnis an, in dem der Dialog geöffnet wird, und helfen Sie Benutzern, mit einer sinnvollen Verzeichnisstruktur für ihren Speicherbetrieb zu beginnen.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Speichern Sie Ihre Datei", "/home/user/documents", "bericht.xls");
String result = dialog.show();
```

## Einschränkung {#restriction}

Sie können den Dialog auf ein bestimmtes Verzeichnis beschränken, um zu verhindern, dass Benutzer außerhalb navigieren, indem Sie die Methode `setRestricted(boolean restricted)` verwenden.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Speichern Sie Ihre Datei", "/home/user/documents", "bericht.xls");
dialog.setRestricted(true);
dialog.show();
```

## Dateiname {#filename}

Setzen Sie einen Standarddateinamen für den Speicherbetrieb, um Benutzern zu helfen und Fehler zu minimieren.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei");
dialog.setName("bericht.xls");
String result = dialog.show();
```

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Labels und Nachrichten innerhalb der Komponente sind vollständig anpassbar über die Klasse `FileSaveI18n`. Dies stellt sicher, dass der Dialog an verschiedene Lokalisierungs- oder Personalisierungsanforderungen angepasst werden kann.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Abbrechen");
dialog.setI18n(i18n);
```

## Filter {#filters}

Das `FileSaveDialog` ermöglicht es Ihnen, Filter festzulegen, um die Arten von Dateien zu begrenzen, die gespeichert werden können, über die Methode `setFilters(List<FileSaveFilter> filters)`.

<ComponentDemo 
path='/webforj/filesavedialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java'
height = '800px'
/>

### Benutzerdefinierte Filter {#custom-filters}

Sie können benutzerdefinierte Filter aktivieren, um Benutzern zu ermöglichen, ihre eigenen Datei-Filter über die Methode `setCustomFilters(boolean customFilters)` zu definieren. Filter werden standardmäßig im lokalen Speicher gespeichert und bei nachfolgenden Aufrufen des Dialogs wiederhergestellt.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Beste Praktiken {#best-practices}

* **Vordefinierte Dateinamen**: Stellen Sie einen logischen Standarddateinamen zur Verfügung, wo dies zutreffend ist.
* **Bestätigen Sie Überschreibungen**: Verwenden Sie `CONFIRMATION_DIALOGUE` für `ExistsAction`, um versehentliche Überschreibungen zu verhindern.
* **Intuitiver Anfangspfad**: Setzen Sie einen Anfangspfad, der mit den Erwartungen der Benutzer übereinstimmt.
* **Internationalisierung**: Passen Sie den Dialogtext an, um die Benutzerfreundlichkeit für internationale Benutzer zu verbessern.
* **Dateityp-Filter**: Nutzen Sie Filter, um Dateitypen zu beschränken und Benutzer zu validen Dateierweiterungen zu leiten.
