---
sidebar_position: 15
title: File Save
_i18n_hash: 477f92765ae539fd69106297baa9a0da
---
# Dateidialog zum Speichern

<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` ist ein modales Dialogfeld, das es Benutzern ermöglicht, eine Datei an einem bestimmten Ort im Server-Dateisystem zu speichern. Der Dialog blockiert die Ausführung der App, bis der Benutzer einen Dateinamen angibt und die Aktion bestätigt oder den Dialog abbricht.

```java
OptionDialog.showFileSaveDialog("Speichern Sie Ihre Datei");
```

## Verwendungen {#usages}

Das `FileSaveDialog` bietet eine vereinfachte Methode zum Speichern von Dateien im Dateisystem und bietet benutzerkonfigurierbare Optionen zur Benennung von Dateien und zum Umgang mit vorhandenen Dateien.

<ComponentDemo 
path='/webforj/filesavedialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java'
height = '800px'
/>

## Ergebnis {#result}

Das `FileSaveDialog` gibt den ausgewählten Pfad als Zeichenfolge zurück. Wenn der Benutzer den Dialog abbricht, wird das Ergebnis `null` sein.

:::important Zweck des Dialogs
Dieser Dialog speichert tatsächlich keine Dateien, sondern gibt den Dateinamen zurück, den der Benutzer ausgewählt hat.
:::

:::info
Die resultierende Zeichenfolge wird von der `show()`-Methode oder der entsprechenden `OptionDialog`-Methode zurückgegeben, wie nachfolgend dargestellt.
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

Das `FileSaveDialog` bietet konfigurierbares Verhalten, wenn eine Datei mit dem angegebenen Namen bereits existiert:

* **ACCEPT_WITHOUT_ACTION**: Die Auswahl wird ohne zusätzliche Benutzeraktion akzeptiert.
* **ERROR_DIALOGUE**: Der Benutzer wird mit einem Fehlermeldungsdialog konfrontiert; die Auswahl ist nicht erlaubt.
* **CONFIRMATION_DIALOGUE**: Der Benutzer wird mit einem Dialog zur Bestätigung konfrontiert. Dies ist die Standardeinstellung.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Speichern Sie Ihre Datei", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Auswahlmodus {#selection-mode}

Das `FileSaveDialog` unterstützt verschiedene Auswahlmodi, mit denen Sie die Auswahlmethode an Ihre spezifischen Bedürfnisse anpassen können:

1. **DATEIEN**: Ermöglicht die Auswahl von Dateien nur.
2. **VERZEICHNISSE**: Ermöglicht die Auswahl von Verzeichnissen nur.
3. **DATEIEN_UND_VERZEICHNISSE**: Ermöglicht die Auswahl von sowohl Dateien als auch Verzeichnissen.

## Anfangspfad {#initial-path}

Geben Sie das Verzeichnis an, in dem der Dialog geöffnet wird, um den Benutzern zu helfen, in einem logischen Verzeichnis für ihre Speicheroperation zu beginnen.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Speichern Sie Ihre Datei", "/home/user/documents", "report.xls");
String result = dialog.show();
```

## Einschränkung {#restriction}

Sie können den Dialog auf ein bestimmtes Verzeichnis einschränken, um zu verhindern, dass Benutzer außerhalb navigieren, indem Sie die Methode `setRestricted(boolean restricted)` verwenden.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Speichern Sie Ihre Datei", "/home/user/documents", "report.xls");
dialog.setRestricted(true);
dialog.show();
```

## Dateiname {#filename}

Legen Sie einen Standard-Dateinamen für die Speicheraktion fest, um die Benutzer zu leiten und Fehler zu minimieren.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei");
dialog.setName("report.xls");
String result = dialog.show();
```

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Labels und Nachrichten innerhalb der Komponente sind vollständig anpassbar mit der Klasse `FileSaveI18n`. Dadurch kann der Dialog an verschiedene Lokalisierungs- oder Personalisierungsanforderungen angepasst werden.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Abbrechen");
dialog.setI18n(i18n);
```

## Filter {#filters}

Das `FileSaveDialog` ermöglicht es Ihnen, Filter festzulegen, um die Arten von Dateien zu begrenzen, die gespeichert werden können, indem Sie die Methode `setFilters(List<FileSaveFilter> filters)` verwenden.

<ComponentDemo 
path='/webforj/filesavedialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java'
height = '800px'
/>

### Benutzerdefinierte Filter {#custom-filters}

Sie können benutzerdefinierte Filter aktivieren, damit Benutzer ihre eigenen Datei-Filter definieren können, indem Sie die Methode `setCustomFilters(boolean customFilters)` verwenden. Filter werden standardmäßig im lokalen Speicher gespeichert und bei nachfolgenden Dialogaufrufen wiederhergestellt.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Beste Praktiken {#best-practices}

* **Vordefinierte Dateinamen**: Stellen Sie einen logischen Standard-Dateinamen bereit, wo dies möglich ist.
* **Überschreibungen bestätigen**: Verwenden Sie `CONFIRMATION_DIALOGUE` für `ExistsAction`, um versehentliche Überschreibungen zu verhindern.
* **Intuitiver Anfangspfad**: Setzen Sie einen Anfangspfad, der mit den Erwartungen der Benutzer übereinstimmt.
* **Internationalisierung**: Passen Sie den Dialogtext an, um die Benutzerfreundlichkeit für internationale Zielgruppen zu verbessern.
* **Dateitypfilter**: Nutzen Sie Filter, um den Dateityp einzuschränken und die Benutzer zu gültigen Dateierweiterungen zu führen.
