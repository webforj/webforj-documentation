---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 91df1121dac6d410883e3b43ddf767d5
---
Die `Table`-Komponente bietet verschiedene Auswahlmöglichkeiten. Es gibt Methoden zur Auswahl eines einzelnen Elements, mehrerer Elemente oder zur programmgesteuerten Verwaltung von Auswahlen.

## Auswahlmodus {#selection-mode}

Der Auswahlmodus in der Tabelle bestimmt, wie Elemente vom Benutzer ausgewählt werden können. Er bietet Optionen zur Konfiguration des Verhaltens der Elementauswahl. Die Klasse Table stellt eine Methode zum Festlegen des Auswahlmodus bereit:

```java
setSelectionMode(SelectionMode selectionMode)
```

Verfügbare Auswahlmodus-Optionen sind:

>- `SINGLE` - (einzelne Auswahl) 
>- `MULTI` - (mehrfache Auswahl)
>- `NONE` - (keine Auswahl).

## Auswahlereignis {#selection-event}

Das `Table`-Komponentenpaket gibt mehrere Ereignisse im Zusammenhang mit der Zeilenauswahl aus. Diese Ereignisse erfassen Änderungen im Auswahlzustand der `Table`-Reihen. Nachfolgend sind die wichtigsten Auswahlereignisse sowie deren Beschreibungen aufgeführt:

>- `TableItemSelectEvent` - Wird ausgelöst, wenn ein Tabellenitem ausgewählt wird.
>- `TableItemDeselectEvent` - Wird ausgelöst, wenn ein Tabellenitem abgewählt wird.
>- `TableItemSelectionChange` - Wird ausgelöst, wenn sich die Gesamtwahl in der Tabelle ändert oder wenn eine zusätzliche Auswahl getroffen wird.

:::info
Die `TableItemSelectEvent` und `TableItemDeselectEvent` werden nicht ausgelöst, wenn der Mehrfachauswahlmodus aktiv ist und die Auswahl über das Kontrollkästchen in der Kopfzeile erfolgt. In diesem Fall sollte stattdessen `TableItemSelectionChange` verwendet werden.
:::

Im folgenden Beispiel wird ein `TableItemSelectEvent`-Ereignis ausgelöst, wann immer ein Benutzer eine Zeile auswählt. Das Ereignis kann behandelt werden, indem ein Listener zur Tabelle unter Verwendung der Methode `onItemSelect()` hinzugefügt wird.

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Kontrollkästchen-Auswahl {#checkbox-selection}

Die Kontrollkästchen-Auswahl ist aktiviert, wenn der Auswahlmodus `MULTI` ist, und ermöglicht es den Benutzern, bequem ein oder mehrere Elemente mithilfe von Kontrollkästchen neben jeder Zeile auszuwählen. Diese Funktion ist besonders nützlich für Szenarien, in denen Benutzer Massenaktionen für ausgewählte Elemente ausführen müssen. Die Table-Klasse bietet Methoden, um die Auswahl mit Kontrollkästchen zu aktivieren und anzupassen.

Durch Verwendung der Methode `setCheckboxSelection(boolean checkboxSelection)` können Kontrollkästchen konfiguriert werden, die neben jeder Zeile angezeigt werden, sodass Benutzer Elemente auswählen können. Das folgende Programm zeigt die aktivierte Mehrfachauswahl und Kontrollkästchen-Auswahl:

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Programmgesteuerte Auswahl {#programatic-selection}

Die `Table`-Komponente bietet programmgesteuerte Auswahlmethoden, mit denen Sie ausgewählte Elemente entweder über ihre Schlüssel oder über die gesamten Elemente manipulieren können.

### Auswahl nach Schlüssel {#select-by-key}

Die Methode `selectKey(Object... keys)` ermöglicht es Ihnen, programmgesteuert Elemente anhand ihrer Schlüssel auszuwählen. Sie können der Methode einen oder mehrere Schlüssel übergeben, und sie wird die Auswahl entsprechend aktualisieren.

### Auswahl nach Index {#select-by-index}

Mit der Methode `selectIndex(int... indices)` können Sie der Methode einen oder mehrere Indizes übergeben und die ausgewählten Elemente entsprechend aktualisieren.

### Auswahl ganzer Elemente {#selecting-entire-items}

Schließlich ermöglicht die Methode `select(T... items)` die programmgesteuerte Auswahl von Elementen, indem Sie ein oder mehrere Elemente selbst an diese Methode übergeben, um die Auswahl entsprechend zu aktualisieren.
