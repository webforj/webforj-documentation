---
sidebar_position: 10
title: Selection
slug: selection
description: >-
  Configure single, multi, or no-selection modes on the Table and respond to row
  selection events with appropriate listeners.
_i18n_hash: 3dc9f9e7462f97e260e1112a2966dc18
---
Die `Table`-Komponente bietet verschiedene Auswahlmöglichkeiten. Es gibt Methoden zum Auswählen eines einzelnen Elements, mehrerer Elemente oder zur programmgesteuerten Verwaltung von Auswahlen.

:::tip Verwaltung und Abfrage von Daten
Für Informationen zur Verwendung des `Repository`-Musterns zur Verwaltung und Abfrage von Sammlungen siehe die [Repository-Artikel](/docs/advanced/repository/overview).
:::

## Auswahlmodus {#selection-mode}

Der Auswahlmodus in der Tabelle bestimmt, wie Elemente vom Benutzer ausgewählt werden können. Er bietet Optionen zur Konfiguration des Verhaltens der Elementauswahl. Die Table-Klasse bietet eine Methode zur Festlegung des Auswahlmodus:

```java
setSelectionMode(SelectionMode selectionMode)
```

Verfügbare Optionen für den SelectionMode sind:

>- `SINGLE` - (einzelne Auswahl)
>- `MULTI` - (mehrfache Auswahl)
>- `NONE` - (keine Auswahl).

## Auswahlereignis {#selection-event}

Das `Table`-Komponentenpaket gibt mehrere Ereignisse im Zusammenhang mit der Zeilenauswahl aus. Diese Ereignisse erfassen Änderungen im Auswahlzustand der `Table`-Zeilen. Nachfolgend sind die wichtigsten Auswahlereignisse mit ihren Beschreibungen aufgeführt:

>- `TableItemSelectEvent` - Wird ausgegeben, wenn ein Tabellenitem ausgewählt wird.
>- `TableItemDeselectEvent` - Wird ausgegeben, wenn ein Tabellenitem abgewählt wird.
>- `TableItemSelectionChange` - Wird ausgegeben, wenn sich die Gesamtauswahl in der Tabelle ändert oder wenn eine zusätzliche Auswahl getroffen wird.

:::info
Die `TableItemSelectEvent` und `TableItemDeselectEvent` werden nicht ausgelöst, wenn der Modus für mehrere Auswahlen aktiv ist und die Auswahl über das Header-Checkbox erfolgt. In diesem Fall sollte stattdessen das `TableItemSelectionChange` verwendet werden.
:::

Im folgenden Beispiel wird ein `TableItemSelectEvent`-Ereignis ausgelöst, wann immer ein Benutzer eine Zeile auswählt. Das Ereignis kann behandelt werden, indem ein Listener zur Tabelle mit der Methode `onItemSelect()` hinzugefügt wird.

<ComponentDemo
path='/webforj/tablesingleselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Checkbox-Auswahl {#checkbox-selection}

Die Checkbox-Auswahl ist aktiviert, wenn der Auswahlmodus `MULTI` ist, und ermöglicht es den Benutzern, bequem ein oder mehrere Elemente mithilfe von Kontrollkästchen auszuwählen, die mit jeder Zeile verknüpft sind. Diese Funktion ist besonders nützlich in Szenarien, in denen Benutzer Sammelaktionen für ausgewählte Elemente durchführen müssen. Die Table-Klasse bietet Methoden zum Aktivieren und Anpassen der Checkbox-Auswahl.

Durch die Verwendung der Methode `setCheckboxSelection(boolean checkboxSelection)` können Kontrollkästchen so konfiguriert werden, dass sie neben jeder Zeile angezeigt werden, wodurch Benutzer Elemente auswählen können. Das folgende Programm zeigt mehrere Auswahl- und Checkbox-Auswahl aktiv:

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

Die Methode `selectKey(Object... keys)` ermöglicht es Ihnen, Elemente programmgesteuert mithilfe ihrer Schlüssel auszuwählen. Sie können ein oder mehrere Schlüssel an diese Methode übergeben, und sie aktualisiert die Auswahl entsprechend.

### Auswahl von Elementeinträgen {#selecting-entry-items}

Schließlich ermöglicht die Methode `select(T... items)` die programmgesteuerte Auswahl von Elementen, indem Sie ein oder mehrere Elemente selbst an diese Methode übergeben, um die Auswahl entsprechend zu aktualisieren.
