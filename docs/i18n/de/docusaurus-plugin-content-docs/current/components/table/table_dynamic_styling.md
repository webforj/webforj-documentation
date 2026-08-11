---
sidebar_position: 21
title: Dynamic Styling
slug: styling
description: >-
  Apply data-driven CSS to Table rows and cells using setRowPartProvider and
  setCellPartProvider with shadow part selectors.
_i18n_hash: 28911597e1e885b0531bb27aa3c8e1a7
---
<!-- vale off -->
# Dynamische Gestaltung <DocChip chip='since' label='25.00' />
<!-- vale on -->

In webforJ 25 und höher ist es möglich, einzelne Zeilen und Zellen in der Tabelle mit benutzerdefinierten Teilenamen zu gestalten. Diese Namen können dynamisch basierend auf der Logik Ihrer Anwendung zugewiesen werden, was Ihnen eine feine Kontrolle über das Erscheinungsbild der Tabelle gibt.

## Zeilengestaltung {#row-styling}

Die Methode `setRowPartProvider()` weist Teilnamen ganzen Zeilen zu, basierend auf dem Datenobjekt, das sie enthalten. Dies ermöglicht es Ihnen, vollständige Zeilen hervorzuheben, die bestimmte Bedingungen erfüllen—zum Beispiel abwechselnde Hintergrundfarben für gerade Zeilen.

Diese Stilnamen können mit dem Selektor `::part()` in Ihrem CSS angesprochen werden.

:::tip Schattenteile
Der Selektor `::part()` ist eine spezielle CSS-Funktion, die es Ihnen ermöglicht, Elemente im Schatten-DOM eines Components zu stylen—solange diese Elemente ein `part`-Attribut haben. Dies ist besonders nützlich, um interne Teile von webforJ-Komponenten zu stylen, wie Zeilen oder Zellen in einer Tabelle.

Für weitere Informationen dazu, wie Schattenteile funktionieren und wie man sie definiert und anspricht, siehe den Abschnitt [Styling](../../styling/shadow-parts).
:::


<ComponentDemo
path='/webforj/tablerowstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableRowStylingView.java',
  'src/main/frontend/css/table/table-row-styling-view.css',
]}
height='300px'
/>

## Zellengestaltung {#cell-styling}

Die Methode `setCellPartProvider()` gestaltet einzelne Zellen basierend auf dem Datenobjekt und der Spalte, zu der sie gehören. Dies ist ideal, um spezifische Werte hervorzuheben, wie zum Beispiel Altersangaben, die einen Schwellenwert überschreiten, oder ungültige Einträge.

Wie bei Zeilenparts werden Zellparts durch einen Namen definiert und mit dem Selektor `::part()` angesprochen.

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/frontend/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## Reaktion auf Datenaktualisierungen {#reacting-to-data-updates}

Wenn Ihre Anwendung Daten programmgesteuert ändert, wie zum Beispiel das Alter eines Benutzers aktualisiert, wird die Tabelle automatisch neu ausgewertet und die entsprechenden Zeilen- oder Zellstile erneut angewendet, sobald der aktualisierte Datensatz im Repository gespeichert ist.

In dieser Demo werden die Zellen in der Alterskolonne basierend auf einem Schwellenwert gestaltet: Alter über 30 erscheinen grün, während Alter von 30 und darunter rot erscheint. Das Klicken auf die Schaltfläche schaltet Alices Alter zwischen 28 und 31 um, wodurch `setCellPartProvider` erneut den entsprechenden Stil anwendet, wenn die Daten gespeichert werden.

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/frontend/css/table/table-dynamic-styling-view.css',
]}
height='475px'
/>

## Gestreifte Zeilen {#striped-rows}

Aktivieren Sie abwechselnde Hintergrundfarben für Zeilen, um die Lesbarkeit zu verbessern:

```java
// Gestreifte Zeilengestaltung anwenden
table.setStriped(true);
```

## Ränder {#borders}

Konfigurieren Sie, welche Ränder um die `Tabelle`, Spalten und Zeilen angezeigt werden:

```java
// Alle Ränder aktivieren
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Alle Ränder entfernen
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

Die folgende Demo zeigt eine einfache Möglichkeit, das visuelle Erscheinungsbild Ihrer `Tabelle` mit dem Rest Ihrer Anwendung mithilfe von `setStriped()` und `setBordersVisible()` abzustimmen.

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={['src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java']}
height='300px'
/>

:::tip Verwaltung und Abfrage von Daten
Für Informationen zur Verwendung des `Repository`-Musters zur Verwaltung und Abfrage von Sammlungen siehe die [Repository-Artikel](/docs/advanced/repository/overview).
:::
