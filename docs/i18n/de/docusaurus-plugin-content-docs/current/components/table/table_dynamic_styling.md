---
sidebar_position: 21
title: Dynamic Styling
slug: styling
sidebar_class_name: updated-content
_i18n_hash: c958a549dfbac715dfce9f26d729f106
---
<!-- vale off -->
# Dynamisches Styling <DocChip chip='since' label='25.00' />
<!-- vale on -->

In webforJ 25 und höher ist es möglich, einzelne Zeilen und Zellen in der Tabelle mit benutzerdefinierten Teilenamen zu stylen. Diese Namen können dynamisch basierend auf der Logik Ihrer Anwendung zugewiesen werden, was Ihnen eine feingranulare Kontrolle über das Erscheinungsbild der Tabelle ermöglicht.

## Zeilenstyling {#row-styling}

Die Methode `setRowPartProvider()` weist Teilenamen ganzen Zeilen basierend auf dem Datenobjekt, das sie enthalten, zu. Dies ermöglicht es Ihnen, vollständige Zeilen hervorzuheben, die bestimmten Bedingungen entsprechen – zum Beispiel abwechselnde Hintergrundfarben für gerade Zeilen.

Diese Stilenamen können mit dem Selektor `::part()` in Ihrem CSS angesprochen werden.

:::tip Schattenparts
Der Selektor `::part()` ist eine spezielle CSS-Funktion, die es Ihnen ermöglicht, Elemente innerhalb des Schatten-DOM eines Komponente zu stylen – solange diese Elemente ein `part`-Attribut besitzen. Dies ist besonders nützlich, um interne Teile von webforJ-Komponenten zu stylen, wie Zeilen oder Zellen in einer Tabelle.

Für mehr Informationen darüber, wie Schattenparts funktionieren und wie man sie definiert und anvisiert, siehe den Abschnitt [Styling](../../styling/shadow-parts).
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Zellstyling {#cell-styling}

Die Methode `setCellPartProvider()` stylt einzelne Zellen basierend sowohl auf dem Datenobjekt als auch auf der Spalte, zu der sie gehören. Dies ist ideal, um spezifische Werte hervorzuheben, wie das Herausstellen von Altersangaben, die einen Schwellenwert überschreiten, oder ungültigen Einträgen.

Wie Zeilenparts werden auch Zellparts durch einen Namen definiert und mit dem Selektor `::part()` angesprochen.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Reaktion auf Datenaktualisierungen {#reacting-to-data-updates}

Wenn Ihre Anwendung Daten programmgesteuert ändert, beispielsweise das Aktualisieren des Alters eines Benutzers, wird die Tabelle automatisch alle zugehörigen Zeilen- oder Zellstile neu bewerten und anwenden, sobald das aktualisierte Element im Repository gespeichert wird.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Gestreifte Zeilen {#striped-rows}

Aktivieren Sie abwechselnde Hintergrundfarben für Zeilen zur Verbesserung der Lesbarkeit:

```java
// Gestreiftes Zeilenstyling anwenden
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

Die Demo unten zeigt eine einfache Möglichkeit, das visuelle Erscheinungsbild Ihrer `Tabelle` mit dem Rest Ihrer Anwendung unter Verwendung von `setStriped()` und `setBordersVisible()` abzugleichen.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>
