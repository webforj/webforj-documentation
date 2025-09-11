---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: 8e9f61685fbb3a7fb830463f1320e8cf
---
<!-- vale off -->
# Dynamisches Styling <DocChip chip='since' label='25.00' />
<!-- vale on -->

In webforJ 25 und höher ist es möglich, einzelne Reihen und Zellen in der Tabelle mit benutzerdefinierten Teilenamen zu stylen. Diese Namen können dynamisch basierend auf der Logik Ihrer App zugewiesen werden, sodass Sie eine feinkörnige Kontrolle über das Aussehen der Tabelle haben.

## Reihenstyling {#row-styling}

Die Methode `setRowPartProvider()` weist gesamten Reihen basierend auf dem enthaltenen Datenobjekt Teilenamen zu. Dadurch können Sie vollständige Reihen hervorheben, die bestimmten Bedingungen entsprechen – zum Beispiel abwechselnde Hintergrundfarben für gerade Reihen.

Diese Stilenamen können in Ihrem CSS mit dem Selektor `::part()` angesprochen werden.

:::tip Schatten Teile
Der Selektor `::part()` ist eine spezielle CSS-Funktion, die es Ihnen ermöglicht, Elemente im Shadow DOM eines Komponentes zu stylen – solange diese Elemente ein `part`-Attribut bereitstellen. Dies ist besonders nützlich, um interne Teile von webforJ-Komponenten, wie Reihen oder Zellen in einer Tabelle, zu stylen.

Für weitere Informationen, wie Schatten Teile funktionieren und wie man sie definiert und anspricht, siehe den Abschnitt [Styling](../../styling/shadow-parts).
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Zellenstyling {#cell-styling}

Die Methode `setCellPartProvider()` stylt einzelne Zellen basierend sowohl auf dem Datenobjekt als auch auf der Spalte, zu der sie gehören. Dies ist ideal, um spezifische Werte hervorzuheben, wie etwa Altersangaben, die einen Schwellenwert überschreiten, oder ungültige Einträge.

Wie bei Reihen sind Zellen Teile durch einen Namen definiert und können mit dem Selektor `::part()` angesprochen werden.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Reagieren auf Datenaktualisierungen {#reacting-to-data-updates}

Wenn Ihre App Daten programmgesteuert ändert, z. B. das Alter eines Benutzers aktualisiert, wird die Tabelle automatisch alle zugehörigen Reihen- oder Zellstile neu bewerten und anwenden, sobald das aktualisierte Element im Repository gespeichert wird.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Gestreifte Reihen {#striped-rows}

Aktivieren Sie abwechselnde Hintergrundfarben für Reihen, um die Lesbarkeit zu verbessern:

```java
// Gestreiftes Reihenstyling anwenden
table.setStriped(true);
```

## Ränder {#borders}

Konfigurieren Sie, welche Ränder um die `Table`, Spalten und Reihen angezeigt werden:

```java
// Alle Ränder aktivieren
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Alle Ränder entfernen
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

Die folgende Demo zeigt eine einfache Möglichkeit, das visuelle Erscheinungsbild Ihrer `Table` mit dem Rest Ihrer App mithilfe von `setStriped()` und `setBordersVisible()` abzustimmen.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>

:::tip Verwaltung und Abfrage von Daten
Für Informationen zur Verwendung des `Repository`-Musters zur Verwaltung und Abfrage von Sammlungen siehe die [Repository-Artikel](/docs/advanced/repository/overview).
:::
