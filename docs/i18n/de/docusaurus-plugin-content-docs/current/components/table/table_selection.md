---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 35cb02c29edafbe9a0715b4630be56c1
---
Die `Table`-Komponente bietet verschiedene Auswahlmöglichkeiten. Es gibt Methoden zur Auswahl eines einzelnen Elements, mehrerer Elemente oder zur programmgesteuerten Verwaltung von Auswahlen.

:::tip Verwaltung und Abfrage von Daten
Für Informationen zur Verwendung des `Repository`-Musters zur Verwaltung und Abfrage von Sammlungen siehe die [Repository-Artikel](/docs/advanced/repository/overview).
:::

## Auswahlmodus {#selection-mode}

Der Auswahlmodus in der Tabelle bestimmt, wie Elemente vom Benutzer ausgewählt werden können. Er bietet Optionen zur Konfiguration des Verhaltens der Elementauswahl. Die Table-Klasse stellt eine Methode zum Festlegen des Auswahlmodus bereit:

```java
setSelectionMode(SelectionMode selectionMode)
```

Verfügbare Auswahlmodus-Optionen umfassen:

>- `SINGLE` - (Einzelauswahl) 
>- `MULTI` - (Mehrfachauswahl)
>- `NONE` - (Keine Auswahl).

## Auswahlereignis {#selection-event}

Das `Table`-Komponentenpaket gibt mehrere Ereignisse aus, die mit der Zeilenauswahl zusammenhängen. Diese Ereignisse erfassen Änderungen im Auswahlstatus der `Table`-Zeilen. Im Folgenden sind die wichtigsten Auswahlereignisse zusammen mit ihren Beschreibungen aufgeführt:

>- `TableItemSelectEvent` -  Wird ausgelöst, wenn ein Tabellenelement ausgewählt wird.
>- `TableItemDeselectEvent` - Wird ausgelöst, wenn ein Tabellenelement abgewählt wird.
>- `TableItemSelectionChange` - Wird ausgelöst, wenn sich die Gesamtauswahl in der Tabelle ändert oder wenn eine zusätzliche Auswahl getroffen wird.

:::info
Die `TableItemSelectEvent`- und `TableItemDeselectEvent`-Ereignisse werden nicht ausgelöst, wenn der Mehrfachauswahlmodus aktiv ist und die Auswahl über das Kontrollkästchen in der Kopfzeile erfolgt. In diesem Fall sollte stattdessen das `TableItemSelectionChange`-Ereignis verwendet werden.
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

Die Checkbox-Auswahl ist aktiviert, wenn der Auswahlmodus `MULTI` ist, und ermöglicht es den Benutzern, bequem ein oder mehrere Elemente mithilfe von Kontrollkästchen auszuwählen, die mit jeder Zeile verbunden sind. Diese Funktion ist besonders nützlich für Szenarien, in denen Benutzer Massenvorgänge auf ausgewählte Elemente ausführen müssen. Die Table-Klasse stellt Methoden bereit, um die Checkbox-Auswahl zu aktivieren und anzupassen.

Durch die Verwendung der Methode `setCheckboxSelection(boolean checkboxSelection)` können Kontrollkästchen konfiguriert werden, die neben jeder Zeile angezeigt werden, sodass die Benutzer Elemente auswählen können. Das folgende Programm zeigt die aktivierte Mehrfachauswahl und Checkbox-Auswahl:

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Programmgesteuerte Auswahl {#programatic-selection}

Die `Table`-Komponente bietet programmgesteuerte Auswahlmethoden, mit denen Sie ausgewählte Elemente entweder über ihre Schlüssel oder durch die gesamten Elemente manipulieren können.

### Auswahl nach Schlüssel {#select-by-key}

Die Methode `selectKey(Object... keys)` ermöglicht es Ihnen, programmatisch Elemente über ihre Schlüssel auszuwählen. Sie können ein oder mehrere Schlüssel an diese Methode übergeben, und sie wird die Auswahl entsprechend aktualisieren.

### Auswahl von Eintragselementen {#selecting-entry-items}

Schließlich ermöglicht die Methode `select(T... items)` die programmatische Auswahl von Elementen, indem Sie ein oder mehrere Elemente selbst an diese Methode übergeben, um die Auswahl entsprechend zu aktualisieren.
