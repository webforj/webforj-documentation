---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 46e92f0b5b3f1dafbf040176711ae5ac
---
Die `Table`-Komponente bietet verschiedene Auswahlfunktionen. Es gibt Methoden zum Auswählen eines einzelnen Elements, mehrerer Elemente oder zum programmatischen Verwalten von Auswahlen.

:::tip Verwalten und Abfragen von Daten
Für Informationen zur Verwendung des `Repository`-Musters zum Verwalten und Abfragen von Sammlungen siehe die [Repository-Artikel](/docs/advanced/repository/overview).
:::

## Auswahlmodus {#selection-mode}

Der Auswahlmodus in der Tabelle bestimmt, wie Elemente vom Benutzer ausgewählt werden können. Er bietet Optionen zur Konfiguration des Verhaltens der Auswahl von Elementen. Die Table-Klasse bietet eine Methode zum Festlegen des Auswahlmodus:

```java
setSelectionMode(SelectionMode selectionMode)
```

Verfügbare Auswahlmodus-Optionen sind:

>- `SINGLE` - (einzelne Auswahl) 
>- `MULTI` - (mehrfache Auswahl)
>- `NONE` - (keine Auswahl).

## Auswahlereignis {#selection-event}

Das `Table`-Komponentenpaket gibt mehrere Ereignisse im Zusammenhang mit der Zeilenauswahl aus. Diese Ereignisse erfassen Änderungen im Auswahlstatus der `Table`-Zeilen. Nachfolgend sind die wichtigsten Auswahlereignisse zusammen mit ihren Beschreibungen aufgeführt:

>- `TableItemSelectEvent` - Wird ausgelöst, wenn ein Tabellenitem ausgewählt wird.
>- `TableItemDeselectEvent` - Wird ausgelöst, wenn ein Tabellenitem abgewählt wird.
>- `TableItemSelectionChange` - Wird ausgelöst, wenn die Gesamtwahl in der Tabelle geändert wird oder wenn eine zusätzliche Auswahl getroffen wird.

:::info
Die `TableItemSelectEvent`- und `TableItemDeselectEvent`-Ereignisse werden nicht ausgelöst, wenn der Mehrfachauswahlmodus aktiv ist und die Auswahl über das Kontrollkästchen im Kopf erfolgt. In diesem Fall sollte stattdessen das `TableItemSelectionChange`-Ereignis verwendet werden.
:::

Im folgenden Beispiel wird ein `TableItemSelectEvent`-Ereignis jedes Mal ausgelöst, wenn ein Benutzer eine Zeile auswählt. Das Ereignis kann behandelt werden, indem ein Listener zur Tabelle mit der Methode `onItemSelect()` hinzugefügt wird.

<ComponentDemo
path='/webforj/tablesingleselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Auswahl über Kontrollkästchen {#checkbox-selection}

Die Auswahl über Kontrollkästchen ist aktiviert, wenn der Auswahlmodus `MULTI` ist, und ermöglicht es den Benutzern, bequem ein oder mehrere Elemente mit den Kontrollkästchen auszuwählen, die mit jeder Zeile verbunden sind. Diese Funktion ist besonders nützlich in Szenarien, in denen Benutzer Massenaktionen für ausgewählte Elemente durchführen müssen. Die Table-Klasse bietet Methoden zum Aktivieren und Anpassen der Auswahl über Kontrollkästchen.

Durch die Verwendung der Methode `setCheckboxSelection(boolean checkboxSelection)` können Kontrollkästchen so konfiguriert werden, dass sie neben jeder Zeile angezeigt werden, sodass Benutzer Elemente auswählen können. Das folgende Programm zeigt eine aktivierte Mehrfachauswahl und Auswahl über Kontrollkästchen:

<ComponentDemo
path='/webforj/tablemultiselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Programmatische Auswahl {#programatic-selection}

Die `Table`-Komponente bietet programmatische Auswahlmethoden, mit denen Sie ausgewählte Elemente entweder über ihre Schlüssel oder über die gesamten Elemente manipulieren können. 

### Auswahl nach Schlüssel {#select-by-key}

Die Methode `selectKey(Object... keys)` ermöglicht es Ihnen, programmatisch Elemente anhand ihrer Schlüssel auszuwählen. Sie können einen oder mehrere Schlüssel an diese Methode übergeben, und sie aktualisiert die Auswahl entsprechend.

### Auswählen von Elementen {#selecting-entry-items}

Schließlich ermöglicht die Methode `select(T... items)`, Elemente programmatisch auszuwählen, indem Sie ein oder mehrere Elemente selbst an diese Methode übergeben, um die Auswahl entsprechend zu aktualisieren.
