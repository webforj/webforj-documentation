---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: ab74c802642742faeaa38ee9a2f6e8da
---
<!-- vale off -->
# Dynamisches Styling <DocChip chip='since' label='25.00' />
<!-- vale on -->

In webforJ 25 und höher ist es möglich, einzelne Zeilen und Zellen in der Tabelle mit benutzerdefinierten Teilenamen zu stylen. Diese Namen können dynamisch basierend auf der Logik Ihrer App zugewiesen werden, was Ihnen eine feine Kontrolle über das Erscheinungsbild der Tabelle ermöglicht.

## Zeilen-Styling {#row-styling}

Die Methode `setRowPartProvider()` weist gesamten Zeilen basierend auf dem Datenobjekt, das sie enthalten, Teilenamen zu. Dadurch können Sie vollständige Zeilen hervorheben, die bestimmte Bedingungen erfüllen, z.B. abwechselnde Hintergrundfarben für gerade Zeilen.

Diese Stilnamen können mit dem Selektor `::part()` in Ihrem CSS angesprochen werden.

:::tip Schatten-Teile
Der Selektor `::part()` ist eine spezielle CSS-Funktion, die es Ihnen ermöglicht, Elemente innerhalb des Schatten-DOM eines Komponents zu stylen, solange diese Elemente ein `part`-Attribut haben. Dies ist besonders nützlich, um interne Teile von webforJ-Komponenten, wie Zeilen oder Zellen in einer Tabelle, zu stylen.

Für weitere Informationen darüber, wie Schatten-Teile funktionieren und wie Sie sie definieren und ansprechen können, siehe den Abschnitt [Styling](../../styling/shadow-parts).
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Zellen-Styling {#cell-styling}

Die Methode `setCellPartProvider()` stylt einzelne Zellen basierend auf sowohl dem Datenobjekt als auch der Spalte, zu der sie gehören. Dies ist ideal, um bestimmte Werte hervorzuheben, wie das Heranziehen von Altersangaben, die einen Schwellenwert überschreiten oder ungültige Einträge.

Wie bei Zeilen-Teilen werden Zellen-Teile durch einen Namen definiert und mit dem Selektor `::part()` angesprochen.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Reaktionen auf Datenaktualisierungen {#reacting-to-data-updates}

Wenn Ihre App Daten programmgesteuert ändert, z.B. das Alter eines Benutzers aktualisiert, wird die Tabelle automatisch alle zugehörigen Zeilen- oder Zellstile erneut bewerten und erneut anwenden, sobald das aktualisierte Objekt im Repository gespeichert ist.

In dieser Demo werden die Zellen in der Alters-Spalte basierend auf einem Schwellenwert gestylt: Alter über 30 erscheint grün, während Alter 30 und darunter rot erscheinen. Ein Klick auf den Button wechselt das Alter von Alice zwischen 28 und 31, was die `setCellPartProvider`-Methode auslöst, um den entsprechenden Stil erneut anzuwenden, wenn die Daten gespeichert werden.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Gestreifte Zeilen {#striped-rows}

Aktivieren Sie abwechselnde Hintergrundfarben für Zeilen, um die Lesbarkeit zu verbessern:

```java
// Gestreiftes Zeilenstyling anwenden
table.setStriped(true);
```

## Ränder {#borders}

Konfigurieren Sie, welche Ränder um die `Table`, Spalten und Zeilen angezeigt werden:

```java
// Alle Ränder aktivieren
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Alle Ränder entfernen
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

Die folgende Demo zeigt eine einfache Möglichkeit, das visuelle Erscheinungsbild Ihrer `Table` mit dem Rest Ihrer App mithilfe von `setStriped()` und `setBordersVisible()` abzugleichen.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>

:::tip Verwaltung und Abfrage von Daten
Für Informationen zur Verwendung des `Repository`-Musters zur Verwaltung und Abfrage von Sammlungen siehe die [Repository-Artikel](/docs/advanced/repository/overview).
:::
