---
sidebar_position: 21
title: Dynamic Styling
slug: styling
sidebar_class_name: updated-content
_i18n_hash: 38ec04cdf27a7de8a9e51ad24cf66009
---
<!-- vale off -->
# Dynamische Gestaltung <DocChip chip='since' label='25.00' />
<!-- vale on -->

In webforJ 25 und höher ist es möglich, einzelne Zeilen und Zellen in der Tabelle mithilfe von benutzerdefinierten Parts-Namen zu gestalten. Diese Namen können dynamisch basierend auf der Logik Ihrer App zugewiesen werden, wodurch Sie eine feine Steuerung über das Erscheinungsbild der Tabelle erhalten.

## Zeilengestaltung {#row-styling}

Die Methode `setRowPartProvider()` weist gesamten Zeilen basierend auf dem Datenobjekt, das sie enthalten, Parts-Namen zu. Dies ermöglicht es Ihnen, vollständige Zeilen hervorzuheben, die bestimmten Bedingungen entsprechen—zum Beispiel alternative Hintergrundfarben für gerade Zeilen.

Diese Stilnamen können mit dem `::part()`-Selektor in Ihrem CSS angesprochen werden.

:::tip Schatten-Parts
Der `::part()`-Selektor ist ein spezielles CSS-Feature, das es Ihnen ermöglicht, Elemente innerhalb des Shadow DOM eines Komponentes zu gestalten—sofern diese Elemente ein `part`-Attribut bereitstellen. Dies ist besonders nützlich, um interne Teile von webforJ-Komponenten wie Zeilen oder Zellen in einer Tabelle zu gestalten.

Für mehr Informationen darüber, wie Schatten-Parts funktionieren und wie man sie definiert und anvisiert, siehe den Abschnitt [Styling](../../styling/shadow-parts).
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Zellengestaltung {#cell-styling}

Die Methode `setCellPartProvider()` gestaltet einzelne Zellen basierend auf dem Datenobjekt und der Spalte, zu der sie gehören. Dies ist ideal, um spezifische Werte hervorzuheben, wie Altersangaben, die einen Schwellenwert überschreiten, oder ungültige Einträge.

Ähnlich wie bei Zeilen-Parts werden Zell-Parts durch einen Namen definiert und mit dem `::part()`-Selektor angesprochen.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Reaktion auf Datenaktualisierungen {#reacting-to-data-updates}

Wenn Ihre App Daten programmgesteuert ändert, wie zum Beispiel das Aktualisieren des Alters eines Benutzers, wird die Tabelle automatisch alle zugehörigen Zeilen- oder Zellstile erneut evaluieren und anwenden, sobald das aktualisierte Element im Repository gespeichert wurde.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Gestreifte Zeilen {#striped-rows}

Aktivieren Sie abwechselnde Hintergrundfarben für Zeilen, um die Lesbarkeit zu verbessern:

```java
// Gestreifte Zeilengestaltung anwenden
table.setStriped(true);
```

## Rahmen {#borders}

Konfigurieren Sie, welche Rahmen um die `Tabelle`, Spalten und Zeilen angezeigt werden:

```java
// Alle Rahmen aktivieren
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Alle Rahmen entfernen
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

Die Demo unten zeigt eine einfache Möglichkeit, das visuelle Erscheinungsbild Ihrer `Tabelle` mit dem Rest Ihrer App in Einklang zu bringen, indem Sie `setStriped()` und `setBordersVisible()` verwenden.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>
