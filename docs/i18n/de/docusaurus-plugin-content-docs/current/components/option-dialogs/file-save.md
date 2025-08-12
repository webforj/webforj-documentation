---
sidebar_position: 15
title: File Save
_i18n_hash: 9f5ecfb61386cfa8c4eb3c31305b1838
---
# Dateidialog zum Speichern

<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` ist ein modaler Dialog, der es den Benutzern ermöglicht, eine Datei an einem bestimmten Speicherort im Server-Dateisystem zu speichern. Der Dialog blockiert die Ausführung der Anwendung, bis der Benutzer einen Dateinamen angibt und die Aktion bestätigt oder den Dialog abbricht.

```java
OptionDialog.showFileSaveDialog("Speichern Sie Ihre Datei");
```

## Verwendungen {#usages}

Der `FileSaveDialog` bietet eine vereinfachte Methode zum Speichern von Dateien im Dateisystem und bietet benutzerkonfigurierbare Optionen für das Benennen von Dateien und den Umgang mit vorhandenen Dateien.

<ComponentDemo 
path='/webforj/filesavedialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java'
height = '800px'
/>

## Ergebnis {#result}

Der `FileSaveDialog` gibt den ausgewählten Pfad als String zurück. Wenn der Benutzer den Dialog abbricht, wird das Ergebnis `null` sein.

:::important Zweck des Dialogs
Dieser Dialog führt tatsächlich keine Dateien zu speichern, sondern gibt den Dateinamen zurück, den der Benutzer ausgewählt hat.
:::

:::info
Der resultierende String wird von der Methode `show()` oder der entsprechenden Methode `OptionDialog` zurückgegeben, wie im Folgenden gezeigt.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Speichern Sie Ihre Datei", "/home/user/documents", "report.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Datei gespeichert unter: " + path, "Ausgewählter Pfad");
} else {
  OptionDialog.showMessageDialog("Kein Pfad ausgewählt", "Ausgewählter Pfad",
      MessageDialog.MessageType.ERROR);
}
```

## Aktion bei Existenz {#exists-action}

Der `FileSaveDialog` bietet konfigurierbares Verhalten, wenn eine Datei mit dem angegebenen Namen bereits existiert:

* **ACCEPT_WITHOUT_ACTION**: Die Auswahl wird ohne zusätzliche Benutzeraktion akzeptiert.
* **ERROR_DIALOGUE**: Dem Benutzer wird ein Fehlermeldungsdialog angezeigt; die Auswahl ist nicht erlaubt.
* **CONFIRMATION_DIALOGUE**: Dem Benutzer wird ein Dialog zur Bestätigungsanfrage angezeigt. Dies ist die Standardoption.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Speichern Sie Ihre Datei", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Auswahlmodus {#selection-mode}

Der `FileSaveDialog` unterstützt verschiedene Auswahlmodi, die es Ihnen ermöglichen, die Auswahlmethode an Ihre spezifischen Bedürfnisse anzupassen:

1. **DATEIEN**: Erlaubt nur die Auswahl von Dateien.
2. **VERZEICHNISSE**: Erlaubt nur die Auswahl von Verzeichnissen.
3. **DATEIEN UND VERZEICHNISSE**: Erlaubt die Auswahl von sowohl Dateien als auch Verzeichnissen.

## Anfangspfad {#initial-path}

Geben Sie das Verzeichnis an, in dem der Dialog geöffnet wird, indem Sie den Anfangspfad festlegen. Dies hilft den Benutzern, in einem logischen Verzeichnis für ihre Speicheroperation zu beginnen.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Speichern Sie Ihre Datei", "/home/user/documents", "report.xls");
String result = dialog.show();
```

## Einschränkung {#restriction}

Sie können den Dialog auf ein bestimmtes Verzeichnis beschränken, um zu verhindern, dass Benutzer außerhalb davon navigieren, indem Sie die Methode `setRestricted(boolean restricted)` verwenden.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Speichern Sie Ihre Datei", "/home/user/documents", "report.xls");
dialog.setRestricted(true);
dialog.show();
```

## Dateiname {#filename}

Setzen Sie einen Standarddateinamen für die Speicheroperation, um den Benutzern zu helfen und Fehler zu minimieren.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei");
dialog.setName("report.xls");
String result = dialog.show();
```

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Beschriftungen und Nachrichten innerhalb der Komponente sind vollständig anpassbar, indem die Klasse `FileSaveI18n` verwendet wird. Dadurch kann der Dialog an verschiedene Lokalisierungs- oder Personalisierungsanforderungen angepasst werden.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Abbrechen");
dialog.setI18n(i18n);
```

## Filter {#filters}

Der `FileSaveDialog` ermöglicht es Ihnen, Filter festzulegen, um die Arten von Dateien zu beschränken, die gespeichert werden können, indem Sie die Methode `setFilters(List<FileSaveFilter> filters)` verwenden.

<ComponentDemo 
path='/webforj/filesavedialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java'
height = '800px'
/>

### Benutzerdefinierte Filter {#custom-filters}

Sie können benutzerdefinierte Filter aktivieren, um den Benutzern zu ermöglichen, ihre eigenen Dateifilter mithilfe der Methode `setCustomFilters(boolean customFilters)` zu definieren. Filter werden standardmäßig im lokalen Speicher gespeichert und bei nachfolgenden Dialogaufrufen wiederhergestellt.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Best Practices {#best-practices}

* **Vordefinierte Dateinamen**: Stellen Sie einen logischen Standarddateinamen bereit, wo dies zutreffend ist.
* **Bestätigen Sie Überschreibungen**: Verwenden Sie `CONFIRMATION_DIALOGUE` für `ExistsAction`, um versehentliche Überschreibungen zu verhindern.
* **Intuitiver Anfangspfad**: Setzen Sie einen Anfangspfad, der den Erwartungen der Benutzer entspricht.
* **Internationalisierung**: Passen Sie den Text des Dialogs an, um die Benutzerfreundlichkeit für internationale Zielgruppen zu verbessern.
* **Dateitypfilter**: Nutzen Sie Filter, um die Dateitypen einzuschränken und Benutzer zu gültigen Dateierweiterungen zu führen.
