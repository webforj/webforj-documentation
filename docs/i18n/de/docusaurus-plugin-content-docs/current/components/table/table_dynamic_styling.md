---
sidebar_position: 21
title: Dynamisches Styling
slug: styling
description: >-
  Apply data-driven CSS to Table rows and cells using setRowPartProvider and
  setCellPartProvider with shadow part selectors.
_i18n_hash: 6d82241fb67ffa1ce5a1e8c974a96dea
---
<!-- vale off -->
# Dynamisches Styling <DocChip chip='since' label='25.00' />
<!-- vale on -->

Ab webforJ 25 ist es möglich, einzelne Zeilen und Zellen in der Table mithilfe benutzerdefinierter Part-Namen zu formatieren. Diese Namen können dynamisch anhand der Logik Ihrer App zugewiesen werden und ermöglichen so eine präzise Kontrolle über das Erscheinungsbild der Tabelle.

## Zeilen formatieren {#row-styling}

Mit der Methode `setRowPartProvider()` werden ganzen Zeilen anhand des darin enthaltenen Datenelements Part-Namen zugewiesen. So können Sie vollständige Zeilen hervorheben, die bestimmten Bedingungen entsprechen – beispielsweise mit abwechselnden Hintergrundfarben für gerade Zeilen.

Diese Style-Namen können in Ihrem CSS mit dem Selektor `::part()` angesprochen werden.

:::tip Shadow Parts
Der Selektor `::part()` ist ein spezielles CSS-Feature, mit dem Sie Elemente innerhalb des Shadow DOM einer Komponente formatieren können – sofern diese Elemente ein `part`-Attribut bereitstellen. Das ist besonders nützlich, um interne Teile von webforJ-Komponenten wie Zeilen oder Zellen in einer Tabelle zu formatieren.

Weitere Informationen zur Funktionsweise von Shadow Parts und dazu, wie sie definiert und angesprochen werden, finden Sie im Abschnitt [Styling](../../styling/shadow-parts).
:::


<ComponentDemo
path='/webforj/tablerowstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableRowStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-row-styling-view.css',
]}
height='300px'
/>

## Zellen formatieren {#cell-styling}

Mit der Methode `setCellPartProvider()` werden einzelne Zellen anhand des Datenelements und der Spalte formatiert, zu der sie gehören. Damit eignet sie sich ideal, um bestimmte Werte hervorzuheben – etwa Altersangaben über einem Grenzwert oder ungültige Einträge.

Wie bei Zeilen-Parts wird auch bei Zellen-Parts ein Name definiert und der Part mit dem Selektor `::part()` angesprochen.

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableCellStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## Auf Datenaktualisierungen reagieren {#reacting-to-data-updates}

Wenn Ihre App Daten programmgesteuert ändert, beispielsweise das Alter eines Benutzers aktualisiert, wertet die Tabelle alle zugehörigen Zeilen- oder Zellenformatierungen automatisch neu aus und wendet sie erneut an, sobald das aktualisierte Element im Repository gespeichert wurde.

In dieser Demo werden Zellen in der Spalte „Alter“ anhand eines Grenzwerts formatiert: Alter über 30 erscheinen grün, Alter von 30 oder darunter rot. Durch Klicken auf die Schaltfläche wird Alices Alter zwischen 28 und 31 umgeschaltet. Dadurch wird `setCellPartProvider` ausgelöst, um die entsprechende Formatierung nach dem Speichern der Daten erneut anzuwenden.

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-dynamic-styling-view.css',
]}
height='475px'
/>

## Gestreifte Zeilen {#striped-rows}

Aktivieren Sie abwechselnde Hintergrundfarben für Zeilen, um die Lesbarkeit zu verbessern:

```java
// Gestreifte Zeilen formatieren
table.setStriped(true);
```

## Rahmen {#borders}

Legen Sie fest, welche Rahmen um die `Table`, Spalten und Zeilen angezeigt werden:

```java
// Alle Rahmen aktivieren
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Alle Rahmen entfernen
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

Die folgende Demo zeigt, wie Sie das Erscheinungsbild Ihrer `Table` mit `setStriped()` und `setBordersVisible()` an den Rest Ihrer App anpassen können.

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
]}
height='300px'
/>

:::tip Daten verwalten und abfragen
Informationen zur Verwendung des `Repository`-Musters zum Verwalten und Abfragen von Sammlungen finden Sie in den [Repository-Artikeln](/docs/advanced/repository/overview).
:::
