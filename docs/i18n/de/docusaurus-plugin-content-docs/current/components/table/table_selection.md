---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 203177b6049bc42493e3d0dbc0bf5233
---
Die `Table`-Komponente bietet verschiedene Auswahlmöglichkeiten. Es gibt Methoden zum Auswählen eines einzelnen Elements, mehrerer Elemente oder zum programmgesteuerten Verwalten von Auswahlen.

:::tip Daten verwalten und abfragen
Für Informationen zur Verwendung des `Repository`-Musters zur Verwaltung und Abfrage von Sammlungen siehe die [Repository-Artikel](/docs/advanced/repository/overview).
:::

## Auswahlmodus {#selection-mode}

Der Auswahlmodus in der Tabelle bestimmt, wie Elemente vom Benutzer ausgewählt werden können. Er bietet Optionen zur Konfiguration des Verhaltens der Elementauswahl. Die Table-Klasse bietet eine Methode zum Festlegen des Auswahlmodus:

```java
setSelectionMode(SelectionMode selectionMode)
```

Verfügbare Optionen für den Auswahlmodus sind:

>- `SINGLE` - (Einzelauswahl)
>- `MULTI` - (Mehrfachauswahl)
>- `NONE` - (keine Auswahl).

## Auswahlereignis {#selection-event}

Das `Table`-Komponentenpaket gibt mehrere Ereignisse im Zusammenhang mit der Zeilenauswahl aus. Diese Ereignisse erfassen Änderungen im Auswahlstatus von `Table`-Zeilen. Im Folgenden sind die wichtigsten Auswahlereignisse mit ihren Beschreibungen aufgeführt:

>- `TableItemSelectEvent` - Wird ausgelöst, wenn ein Tabellenitem ausgewählt wird.
>- `TableItemDeselectEvent` - Wird ausgelöst, wenn ein Tabellenitem abgewählt wird.
>- `TableItemSelectionChange` - Wird ausgelöst, wenn die Gesamtauswahl in der Tabelle geändert wird oder wenn eine zusätzliche Auswahl getroffen wird.

:::info
Die `TableItemSelectEvent`- und `TableItemDeselectEvent`-Ereignisse werden nicht ausgelöst, wenn der Modus für Mehrfachauswahl aktiv ist und die Auswahl über das Header-Checkbox-Feld erfolgt. In diesem Fall sollte das `TableItemSelectionChange`-Ereignis stattdessen verwendet werden.
:::

Im folgenden Beispiel wird ein `TableItemSelectEvent`-Ereignis ausgelöst, sobald ein Benutzer eine Zeile auswählt. Das Ereignis kann behandelt werden, indem ein Listener zur Tabelle mit der Methode `onItemSelect()` hinzugefügt wird.

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Checkbox-Auswahl {#checkbox-selection}

Die Checkbox-Auswahl wird aktiviert, wenn der Auswahlmodus `MULTI` ist, und ermöglicht es den Benutzern, bequem ein oder mehrere Elemente mithilfe von Kontrollkästchen auszuwählen, die mit jeder Zeile verbunden sind. Diese Funktion ist besonders nützlich für Szenarien, in denen Benutzer Massnahmen für ausgewählte Elemente durchführen müssen. Die Table-Klasse bietet Methoden zum Aktivieren und Anpassen der Checkbox-Auswahl.

Durch die Verwendung der Methode `setCheckboxSelection(boolean checkboxSelection)` können Kontrollkästchen so konfiguriert werden, dass sie neben jeder Zeile angezeigt werden, sodass Benutzer Elemente auswählen können. Das folgende Programm zeigt, dass die Mehrfachauswahl und die Checkbox-Auswahl aktiviert sind:

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Programmatische Auswahl {#programatic-selection}

Die `Table`-Komponente bietet programmatische Auswahlmethoden, die es ermöglichen, ausgewählte Elemente entweder über ihre Schlüssel oder über die gesamten Elemente zu manipulieren.

### Auswahl nach Schlüssel {#select-by-key}

Die Methode `selectKey(Object... keys)` ermöglicht es, Elemente programmgesteuert anhand ihrer Schlüssel auszuwählen. Sie können ein oder mehrere Schlüssel an diese Methode übergeben, und sie wird die Auswahl entsprechend aktualisieren.

### Auswahl nach Index {#select-by-index}

Mit der Methode `selectIndex(int... indices)` können Sie ein oder mehrere Indizes an die Methode übergeben und die ausgewählten Elemente entsprechend aktualisieren.

### Auswahl ganzer Elemente {#selecting-entire-items}

Schließlich ermöglicht die Methode `select(T... items)`, Elemente programmgesteuert auszuwählen, indem ein oder mehrere Elemente selbst an diese Methode übergeben werden, um die Auswahl entsprechend zu aktualisieren.
