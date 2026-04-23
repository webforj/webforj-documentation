---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: 03c0fb81b4bcabef5db6dfb9785eef3d
---
<!-- vale off -->
# Dynamisches Styling <DocChip chip='since' label='25.00' />
<!-- vale on -->

In webforJ 25 und höher ist es möglich, einzelne Zeilen und Zellen in der Tabelle mit benutzerdefinierten Teilnamen zu stylen. Diese Namen können dynamisch basierend auf der Logik Ihrer Anwendung zugewiesen werden, wodurch Sie eine präzise Kontrolle über das Erscheinungsbild der Tabelle erhalten.

## Zeilenstyling {#row-styling}

Die Methode `setRowPartProvider()` weist Teilnamen an ganze Zeilen basierend auf dem enthaltenen Datenobjekt zu. Dadurch können Sie vollständige Zeilen hervorheben, die bestimmte Bedingungen erfüllen – zum Beispiel abwechselnde Hintergrundfarben für gerade Zeilen.

Diese Stilenamen können mit dem `::part()`-Selector in Ihrem CSS angesprochen werden.

:::tip Schattenparts
Der `::part()`-Selector ist ein spezielles CSS-Feature, das es Ihnen ermöglicht, Elemente im Shadow DOM eines Components zu stylen – solange diese Elemente ein `part`-Attribut bereitstellen. Dies ist besonders nützlich, um interne Teile von webforJ-Komponenten, wie Zeilen oder Zellen in einer Tabelle, zu stylen.

Für weitere Informationen darüber, wie Schattenparts funktionieren und wie Sie sie definieren und gezielt ansprechen, siehe den Abschnitt [Styling](../../styling/shadow-parts).
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
cssURL='/css/table/table-row-styling-view.css'
height='300px'
/>

## Zellstylings {#cell-styling}

Die Methode `setCellPartProvider()` stylt einzelne Zellen basierend auf dem Datenobjekt und der zugehörigen Spalte. Dies ist ideal, um spezifische Werte hervorzuheben, wie das Hervorheben von Altersangaben, die einen Schwellenwert überschreiten, oder ungültigen Einträgen.

Ähnlich wie bei Zeilenparts werden Zellparts durch einen Namen definiert und mit dem `::part()`-Selector angesprochen.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
cssURL='/css/table/table-cell-styling-view.css'
height='300px'
/>

## Reaktion auf Datenaktualisierungen {#reacting-to-data-updates}

Wenn Ihre Anwendung Daten programmgesteuert ändert, wie beispielsweise das Aktualisieren des Alters eines Benutzers, wird die Tabelle automatisch neu bewertet und alle zugehörigen Zeilen- oder Zellstylings erneut angewendet, sobald das aktualisierte Element im Repository übergeben wird.

In diesem Demo werden die Zellen in der Alters-Spalte basierend auf einem Schwellenwert gestylt: Altersangaben über 30 erscheinen grün, während Altersangaben von 30 und darunter rot erscheinen. Das Klicken auf die Schaltfläche wechselt das Alter von Alice zwischen 28 und 31, wodurch `setCellPartProvider` ausgelöst wird, um den entsprechenden Stil nach der Datenübergabe erneut anzuwenden.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
cssURL='/css/table/table-dynamic-styling-view.css'
height='475px'
/>

## Gestreifte Zeilen {#striped-rows}

Aktivieren Sie abwechselnde Hintergrundfarben für Zeilen, um die Lesbarkeit zu verbessern:

```java
// Gestreifte Zeilen-Styling anwenden
table.setStriped(true);
```

## Grenzen {#borders}

Konfigurieren Sie, welche Grenzen um die `Tabelle`, Spalten und Zeilen angezeigt werden:

```java
// Alle Grenzen aktivieren
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Alle Grenzen entfernen
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

Die folgende Demo zeigt eine einfache Möglichkeit, das visuelle Erscheinungsbild Ihrer `Tabelle` mit dem Rest Ihrer Anwendung abzugleichen, indem Sie `setStriped()` und `setBordersVisible()` verwenden.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>

:::tip Verwaltung und Abfrage von Daten
Für Informationen zur Verwendung des `Repository`-Musters zur Verwaltung und Abfrage von Sammlungen siehe die [Repository-Artikel](/docs/advanced/repository/overview).
:::
