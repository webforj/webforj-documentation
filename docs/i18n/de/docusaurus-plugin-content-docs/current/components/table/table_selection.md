---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: c0e15da31dab7ea7701ea65b47ce67f8
---
Die `Table`-Komponente bietet verschiedene Auswahlmöglichkeiten. Es gibt Methoden zum Auswählen eines einzelnen Elements, mehrerer Elemente oder zum programmgesteuerten Verwalten von Auswahlen.

## Auswahlmodus {#selection-mode}

Der Auswahlmodus in der Tabelle bestimmt, wie Elemente vom Benutzer ausgewählt werden können. Er bietet Optionen zur Konfiguration des Verhaltens der Elementauswahl. Die Table-Klasse bietet eine Methode zur Festlegung des Auswahlmodus:

```java
setSelectionMode(SelectionMode selectionMode)
```

Verfügbare Auswahlmodus-Optionen sind:

>- `SINGLE` - (einzelne Auswahl) 
>- `MULTI` - (mehrfache Auswahl)
>- `NONE` - (keine Auswahl).

## Auswahlereignis {#selection-event}

Das `Table`-Komponentenpaket sendet mehrere Ereignisse, die mit der Zeilenauswahl zusammenhängen. Diese Ereignisse erfassen Änderungen im Auswahlstatus der `Table`-Zeilen. Nachfolgend sind die wichtigsten Auswahlereignisse zusammen mit deren Beschreibungen aufgeführt:

>- `TableItemSelectEvent` -  Wird ausgelöst, wenn ein Tabellenitem ausgewählt wird.
>- `TableItemDeselectEvent` - Wird ausgelöst, wenn ein Tabellenitem abgewählt wird.
>- `TableItemSelectionChange` - Wird ausgelöst, wenn sich die Gesamtauswahl in der Tabelle ändert oder wenn eine zusätzliche Auswahl getroffen wird.

:::info
Die `TableItemSelectEvent`- und `TableItemDeselectEvent`-Ereignisse werden nicht ausgelöst, wenn der Mehrfachauswahlmodus aktiv ist und die Auswahl über das Kontrollkästchen in der Kopfzeile erfolgt. In diesem Fall sollte stattdessen das `TableItemSelectionChange` verwendet werden.
:::

Im folgenden Beispiel wird ein `TableItemSelectEvent`-Ereignis ausgelöst, wann immer ein Benutzer eine Zeile auswählt. Das Ereignis kann behandelt werden, indem ein Listener zur Tabelle mit der Methode `onItemSelect()` hinzugefügt wird.

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Checkbox-Auswahl {#checkbox-selection}

Die Checkbox-Auswahl ist aktiviert, wenn der Auswahlmodus `MULTI` ist, und ermöglicht es den Benutzern, bequem ein oder mehrere Elemente über die mit jeder Zeile verbundenen Kontrollkästchen auszuwählen. Diese Funktion ist besonders nützlich in Szenarien, in denen Benutzer Massenaktionen auf ausgewählten Elementen durchführen müssen. Die Table-Klasse bietet Methoden zum Aktivieren und Anpassen der Checkbox-Auswahl.

Durch die Verwendung der Methode `setCheckboxSelection(boolean checkboxSelection)` können Kontrollkästchen so konfiguriert werden, dass sie neben jeder Zeile angezeigt werden, wodurch die Benutzer Elemente auswählen können. Das folgende Programm zeigt die aktivierte Mehrfachauswahl und Checkbox-Auswahl:

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Programmatische Auswahl {#programatic-selection}

Die `Table`-Komponente bietet programmgesteuerte Auswahlmethoden, mit denen Sie ausgewählte Elemente entweder über ihre Schlüssel oder direkt über die gesamten Elemente manipulieren können. 

### Auswahl nach Schlüssel {#select-by-key}

Die Methode `selectKey(Object... keys)` ermöglicht es Ihnen, Elemente programmgesteuert über ihre Schlüssel auszuwählen. Sie können ein oder mehrere Schlüssel an diese Methode übergeben, und sie wird die Auswahl entsprechend aktualisieren.

### Auswahl nach Index {#select-by-index}

Mit der Methode `selectIndex(int... indices)` können Sie ein oder mehrere Indizes an die Methode übergeben und die ausgewählten Elemente entsprechend aktualisieren.

### Auswahl ganzer Elemente {#selecting-entire-items}

Schließlich ermöglicht die Methode `select(T... items)` die programmgesteuerte Auswahl von Elementen, indem Sie ein oder mehrere Elemente selbst an diese Methode übergeben, um die Auswahl entsprechend zu aktualisieren.
