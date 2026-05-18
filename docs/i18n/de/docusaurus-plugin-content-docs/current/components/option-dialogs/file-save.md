---
title: File Save
sidebar_position: 15
_i18n_hash: 7cad72847c86a30f8ad6000a283a51c2
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` ist ein modales Dialogfeld, das den Benutzern ermöglicht, eine Datei an einem bestimmten Ort im Server-Dateisystem zu speichern. Der Dialog blockiert die Ausführung der App, bis der Benutzer einen Dateinamen angibt und die Aktion bestätigt oder den Dialog abbricht.

<!-- INTRO_END -->

## Usos {#usages}

Der `FileSaveDialog` bietet eine vereinfachte Methode zum Speichern von Dateien im Dateisystem und bietet benutzerkonfigurierbare Optionen für die Benennung von Dateien und den Umgang mit vorhandenen Dateien.

<ComponentDemo
path='/webforj/filesavedialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java']}
height='800px'
/>

## Ergebnis {#result}

Der `FileSaveDialog` gibt den ausgewählten Pfad als String zurück. Wenn der Benutzer den Dialog abbricht, wird das Ergebnis `null` sein.

:::important Zweck des Dialogs
Dieser Dialog verursacht tatsächlich keine Dateispeicherung, sondern gibt den vom Benutzer ausgewählten Dateinamen zurück.
:::

:::info
Der resultierende String wird von der Methode `show()` oder der entsprechenden `OptionDialog`-Methode zurückgegeben, wie unten gezeigt.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Speichern Sie Ihre Datei", "/home/user/documents", "report.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Datei gespeichert unter: " + path, "Pfad ausgewählt");
} else {
  OptionDialog.showMessageDialog("Kein Pfad ausgewählt", "Pfad ausgewählt",
      MessageDialog.MessageType.ERROR);
}
```

## Existiert-Aktion {#exists-action}

Der `FileSaveDialog` bietet konfigurierbares Verhalten, wenn eine Datei mit dem angegebenen Namen bereits exists:

* **ACCEPT_WITHOUT_ACTION**: Die Auswahl wird ohne zusätzliche Benutzeraktion akzeptiert.
* **ERROR_DIALOGUE**: Der Benutzer erhält einen Fehlermeldungsdialog; die Auswahl ist nicht erlaubt.
* **CONFIRMATION_DIALOGUE**: Der Benutzer erhält einen Dialog, der um Bestätigung bittet. Dies ist die Voreinstellung.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Speichern Sie Ihre Datei", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Auswahlmodus {#selection-mode}

Der `FileSaveDialog` unterstützt verschiedene Auswahlmodi, die es Ihnen ermöglichen, die Auswahlmethode an Ihre spezifischen Bedürfnisse anzupassen:

1. **DATEIEN**: Ermöglicht die Auswahl von Dateien.
2. **VERZEICHNISSE**: Ermöglicht die Auswahl von Verzeichnissen.
3. **DATEIEN_UND_VERZEICHNISSE**: Ermöglicht die Auswahl sowohl von Dateien als auch von Verzeichnissen.

## Initialer Pfad {#initial-path}

Geben Sie das Verzeichnis an, in dem der Dialog geöffnet wird, indem Sie den anfänglichen Pfad verwenden. Dies hilft den Benutzern, in einem logischen Verzeichnis für ihre Speicheroperationen zu beginnen.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Speichern Sie Ihre Datei", "/home/user/documents", "report.xls");
String result = dialog.show();
```

## Einschränkung {#restriction}

Sie können den Dialog auf ein spezifisches Verzeichnis beschränken, sodass Benutzer nicht außerhalb davon navigieren können, indem Sie die Methode `setRestricted(boolean restricted)` verwenden.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Speichern Sie Ihre Datei", "/home/user/documents", "report.xls");
dialog.setRestricted(true);
dialog.show();
```

## Dateiname {#filename}

Legen Sie einen Standarddateinamen für die Speicheroperation fest, um die Benutzer zu leiten und Fehler zu minimieren.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei");
dialog.setName("report.xls");
String result = dialog.show();
```

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Labels und Nachrichten innerhalb der Komponente sind vollständig anpassbar mit der Klasse `FileSaveI18n`. Dies stellt sicher, dass der Dialog an verschiedene Lokalisierungs- oder Personalisierungsanforderungen angepasst werden kann.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Abbrechen");
dialog.setI18n(i18n);
```

## Filter {#filters}

Der `FileSaveDialog` ermöglicht es Ihnen, Filter festzulegen, um die Arten von Dateien, die gespeichert werden können, mit der Methode `setFilters(List<FileSaveFilter> filters)` einzuschränken.

<ComponentDemo
path='/webforj/filesavedialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java']}
height='800px'
/>

### Benutzerdefinierte Filter {#custom-filters}

Sie können benutzerdefinierte Filter aktivieren, um es den Benutzern zu ermöglichen, ihre eigenen Dateifilter mit der Methode `setCustomFilters(boolean customFilters)` zu definieren. Filter werden standardmäßig im lokalen Speicher gespeichert und bei nachfolgenden Dialogaufrufen wiederhergestellt.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Best Practices {#best-practices}

* **Vordefinierte Dateinamen**: Stellen Sie, wo möglich, einen logischen Standarddateinamen bereit.
* **Bestätigen Sie Überschreibungen**: Verwenden Sie `CONFIRMATION_DIALOGUE` für `ExistsAction`, um versehentliche Überschreibungen zu verhindern.
* **Intuitiver anfänglicher Pfad**: Legen Sie einen anfänglichen Pfad fest, der den Erwartungen der Benutzer entspricht.
* **Internationalisierung**: Passen Sie den Dialogtext an, um die Benutzerfreundlichkeit für internationale Zielgruppen zu verbessern.
* **Dateitypfilter**: Nutzen Sie Filter, um Dateitypen einzuschränken und die Benutzer zu gültigen Dateierweiterungen zu leiten.
