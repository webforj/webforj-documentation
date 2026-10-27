---
sidebar_position: 10
title: Selection
slug: selection
description: >-
  Configure single, multi, or no-selection modes on the Table and respond to row
  selection events with appropriate listeners.
_i18n_hash: 47f6b24c0a0621d810e5e2975a0b4749
---
Die `Table`-Komponente bietet verschiedene Auswahlmöglichkeiten. Es gibt Methoden zur Auswahl eines einzelnen Elements, mehrerer Elemente oder zur programmatischen Verwaltung von Auswahlen.

:::tip Daten verwalten und abfragen
Für Informationen zur Verwendung des `Repository`-Mustes zur Verwaltung und Abfrage von Sammlungen siehe die [Repository-Artikel](/docs/advanced/repository/overview).
:::

## Auswahlmodus {#selection-mode}

Der Auswahlmodus in der Tabelle bestimmt, wie Elemente vom Benutzer ausgewählt werden können. Es bietet Optionen zur Konfiguration des Verhaltens bei der Elementauswahl. Die Table-Klasse bietet eine Methode, um den Auswahlmodus festzulegen:

```java
setSelectionMode(SelectionMode selectionMode)
```

Verfügbare Auswahlmodus-Optionen sind:

>- `SINGLE` - (Einzelauswahl)
>- `MULTI` - (Mehrfachauswahl)
>- `NONE` - (keine Auswahl).

## Auswahlereignis {#selection-event}

Das `Table`-Komponentenpaket erzeugt mehrere Ereignisse im Zusammenhang mit der Zeilenauswahl. Diese Ereignisse erfassen Änderungen im Auswahlstatus der `Table`-Zeilen. Nachfolgend sind die wichtigsten Auswahlereignisse zusammen mit ihren Beschreibungen aufgeführt:

>- `TableItemSelectEvent` - Wird ausgelöst, wenn ein Tabellenobjekt ausgewählt wird.
>- `TableItemDeselectEvent` - Wird ausgelöst, wenn ein Tabellenobjekt abgewählt wird.
>- `TableItemSelectionChange` - Wird ausgelöst, wenn sich die gesamte Auswahl in der Tabelle ändert oder wenn eine zusätzliche Auswahl getroffen wird.

:::info
Die `TableItemSelectEvent`- und `TableItemDeselectEvent`-Ereignisse werden nicht ausgelöst, wenn der Mehrfachauswahlmodus aktiv ist und die Auswahl über das Kontrollkästchen in der Kopfzeile erfolgt. In diesem Fall sollte stattdessen das `TableItemSelectionChange` verwendet werden.
:::

Im folgenden Beispiel wird ein `TableItemSelectEvent`-Ereignis ausgelöst, wann immer ein Benutzer eine Zeile auswählt. Das Ereignis kann verarbeitet werden, indem ein Listener zur Tabelle mit der `onItemSelect()`-Methode hinzugefügt wird.

<ComponentDemo
path='/webforj/tablesingleselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Auswahl per Kontrollkästchen {#checkbox-selection}

Die Auswahl über Kontrollkästchen ist aktiviert, wenn der Auswahlmodus `MULTI` ist, und ermöglicht es den Benutzern, bequem ein oder mehrere Elemente mithilfe von Kontrollkästchen auszuwählen, die mit jeder Zeile verbunden sind. Diese Funktion ist besonders nützlich für Szenarien, in denen Benutzer Massenaktionen an ausgewählten Elementen durchführen müssen. Die Table-Klasse bietet Methoden, um die Auswahl per Kontrollkästchen zu aktivieren und anzupassen.

Durch die Verwendung der Methode `setCheckboxSelection(boolean checkboxSelection)` können Kontrollkästchen so konfiguriert werden, dass sie neben jeder Zeile angezeigt werden, wodurch Benutzer Elemente auswählen können. Das folgende Programm zeigt die aktivierte Mehrfachauswahl und Auswahl per Kontrollkästchen:

<ComponentDemo
path='/webforj/tablemultiselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Programmatische Auswahl {#programmatic-selection}

Die `Table`-Komponente bietet programmatische Auswahlanforderungen, mit denen Sie ausgewählte Elemente entweder über ihre Schlüssel oder über die gesamten Elemente manipulieren können.

### Auswahl nach Schlüssel {#select-by-key}

Die Methode `selectKey(Object... keys)` ermöglicht es Ihnen, programmatisch Elemente mithilfe ihrer Schlüssel auszuwählen. Sie können dieser Methode einen oder mehrere Schlüssel übergeben, und sie wird die Auswahl entsprechend aktualisieren.

### Auswahl von Elementeinstellungen {#selecting-entry-items}

Schließlich ermöglicht die Methode `select(T... items)` eine programmatische Auswahl von Elementen, indem Sie ein oder mehrere Elemente selbst an diese Methode übergeben, um die Auswahl entsprechend zu aktualisieren.
