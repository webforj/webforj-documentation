---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: 8f910c729d1108faeaba860a2e0f3546
---
<!-- vale off -->
# Dynamisches Styling <DocChip chip='since' label='25.00' />
<!-- vale on -->

In webforJ 25 und höher ist es möglich, einzelne Zeilen und Zellen in der Tabelle mit benutzerdefinierten Teilnamen zu stylen. Diese Namen können dynamisch basierend auf der Logik Ihrer Anwendung zugewiesen werden, wodurch Sie eine feine Kontrolle über das Erscheinungsbild der Tabelle erhalten.

## Zeilenstyling {#row-styling}

Die Methode `setRowPartProvider()` weist ganzen Zeilen basierend auf dem Datenelement, das sie enthalten, Teilnamen zu. Dies ermöglicht es Ihnen, vollständige Zeilen hervorzuheben, die bestimmten Bedingungen entsprechen—zum Beispiel abwechselnde Hintergrundfarben für gerade Zeilen.

Diese Stilenamen können in Ihrem CSS mit dem `::part()`-Selektor angesprochen werden.

:::tip Schattenteile
Der `::part()`-Selektor ist ein spezielles CSS-Feature, das es Ihnen ermöglicht, Elemente im Schatten-DOM eines Komponents zu stylen—solange diese Elemente ein `part`-Attribut exponieren. Dies ist besonders nützlich für das Styling interner Teile von webforJ-Komponenten, wie Zeilen oder Zellen in einer Tabelle.

Für weitere Informationen darüber, wie Schattenteile funktionieren und wie man sie definiert und anspricht, siehe den Abschnitt [Styling](../../styling/shadow-parts).
:::


<ComponentDemo
path='/webforj/tablerowstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableRowStylingView.java',
  'src/main/resources/static/css/table/table-row-styling-view.css',
]}
height='300px'
/>

## Zellenstyling {#cell-styling}

Die Methode `setCellPartProvider()` stylt einzelne Zellen basierend sowohl auf dem Datenelement als auch auf der Spalte, zu der sie gehören. Dies macht es ideal, um spezifische Werte hervorzuheben, wie zum Beispiel Alter, die einen Schwellenwert überschreiten oder ungültige Einträge.

Wie bei Zeilenpartien sind Zellteile durch einen Namen definiert und werden mit dem `::part()`-Selektor angesprochen.

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/resources/static/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## Reagieren auf Datenaktualisierungen {#reacting-to-data-updates}

Wenn Ihre Anwendung Daten programmgesteuert ändert, wie zum Beispiel das Alter eines Benutzers aktualisiert, wird die Tabelle automatisch neu bewertet und alle zugehörigen Zeilen- oder Zellstile erneut angewendet, sobald das aktualisierte Element im Repository gespeichert ist.

In dieser Demo werden die Zellen in der Alters-Spalte basierend auf einem Schwellenwert gestylt: Alter über 30 erscheint grün, während Alter 30 und darunter rot erscheinen. Durch Klicken auf die Schaltfläche wird Alices Alter zwischen 28 und 31 umgeschaltet, wodurch `setCellPartProvider` aufgefordert wird, den entsprechenden Stil erneut anzuwenden, wenn die Daten gespeichert werden.

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/resources/static/css/table/table-dynamic-styling-view.css',
]}
height='475px'
/>

## Gestreifte Zeilen {#striped-rows}

Aktivieren Sie abwechselnde Hintergrundfarben für Zeilen, um die Lesbarkeit zu verbessern:

```java
// Gestreifte Zeilen-Darstellung anwenden
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

Die folgende Demo zeigt eine einfache Möglichkeit, das visuelle Erscheinungsbild Ihrer `Tabelle` mit dem Rest Ihrer Anwendung mithilfe von `setStriped()` und `setBordersVisible()` abzugleichen.

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={['src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java']}
height='300px'
/>

:::tip Verwaltung und Abfrage von Daten
Für Informationen zur Verwendung des `Repository`-Musters zur Verwaltung und Abfrage von Sammlungen siehe die [Repository-Artikel](/docs/advanced/repository/overview).
:::
