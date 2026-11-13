---
title: Spaltengruppen
sidebar_position: 7
description: >-
  Group Table columns under shared, nestable multi-row headers using
  ColumnGroup.of and setColumnGroups for complex layouts.
_i18n_hash: 4babef21ef20728da6aa7eec5829e4d1
---
<!-- vale off -->
# Spaltengruppen <DocChip chip='since' label='25.12' />
<!-- vale on -->

Mit Spaltengruppen können Sie zusammengehörige Spalten unter gemeinsamen, mehrzeiligen Überschriften organisieren. Die Beschriftung einer Gruppe erstreckt sich über die zugehörigen Spalten. So können Benutzer die Struktur komplexer Tabellen leichter erfassen. Gruppen lassen sich beliebig tief verschachteln, und die `Table` rendert automatisch die richtige Anzahl an Kopfzeilen.

## Spaltengruppen erstellen {#creating-column-groups}

Erstellen Sie eine Gruppe mit der Factory-Methode `ColumnGroup.of()` und fügen Sie anschließend mit verketteten `add()`-Aufrufen Spaltenreferenzen, weitere Gruppen oder eine Kombination aus beidem hinzu. Wenden Sie die Gruppen mit `setColumnGroups()` auf eine `Table` an.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

Wenn Gruppen festgelegt sind, rendert die `Table` eine mehrzeilige Kopfzeile, in der sich die Beschriftung jeder Gruppe über die zugehörigen Spalten erstreckt. Die Verschachtelungstiefe bestimmt, wie viele Kopfzeilen angezeigt werden. Eine einfache Gruppe fügt eine zusätzliche Zeile hinzu, eine zweistufige Verschachtelung zwei und so weiter.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablenestedcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableNestedColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

<!-- vale Google.OxfordComma = NO -->
Gruppen können jederzeit festgelegt oder geändert werden, sowohl vor als auch nach dem Rendern der `Table`. Übergeben Sie `null` oder eine leere Liste an `setColumnGroups()`, um alle Gruppierungen zu entfernen und wieder eine einzeilige Kopfzeile zu erhalten.
<!-- vale Google.OxfordComma = YES -->

```java
// Alle Spaltengruppen entfernen
table.setColumnGroups(null);
```

## Spaltenreihenfolge {#column-ordering}

Wenn Gruppen aktiv sind, wird die visuelle Spaltenreihenfolge durch den Gruppenbaum bestimmt und nicht durch die Reihenfolge, in der die Spalten zur `Table` hinzugefügt wurden. Der Baum wird tiefensuchend von links nach rechts durchlaufen.

```
Hinzugefügte Spalten: [A, B, C, D, E]
Gruppen:              Gruppe "G1" [C, A], Gruppe "G2" [E, D]
Visuelle Reihenfolge: C, A, E, D, B
```

Nicht gruppierte Spalten, also Spalten, auf die in keiner Gruppe verwiesen wird, werden nicht ausgeblendet. Sie erscheinen an ihrer natürlichen Position relativ zu den gruppierten Spalten, entsprechend der Reihenfolge, in der sie ursprünglich zur `Table` hinzugefügt wurden.

In diesem Beispiel erscheint `Number` zuerst, da es vor `Title` hinzugefügt wurde. `Label` erscheint zwischen `Genre` und `Cost`, da es in der ursprünglichen Spaltenreihenfolge zwischen ihnen hinzugefügt wurde:

```
Hinzugefügte Spalten: [Number, Title, Artist, Genre, Label, Cost]
Gruppen:              Gruppe "Music" [Title, Artist, Genre], Gruppe "Pricing" [Cost]
Visuelle Reihenfolge: Number, Title, Artist, Genre, Label, Cost
```

Die folgende Demo veranschaulicht dieses Verhalten. Auf `Number` und `Label` wird in keiner Gruppe verwiesen. Sie behalten jedoch ihre natürliche Position entsprechend der Reihenfolge, in der sie zur `Table` hinzugefügt wurden.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumngroupordering'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnGroupOrderingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

:::tip Platzierung nicht gruppierter Spalten steuern
Um die Platzierung nicht gruppierter Spalten explizit zu steuern, nehmen Sie sie als Spaltenreferenzen der obersten Ebene in den Gruppenbaum auf.
:::

## Spalten innerhalb von Gruppen verschieben {#column-moving-within-groups}

Wenn Gruppen aktiv sind, ist das Verschieben von Spalten per Drag-and-drop eingeschränkt, damit die Gruppenstruktur erhalten bleibt:

- **Innerhalb einer Gruppe**: Eine Spalte innerhalb einer Gruppe kann nur innerhalb ihrer unmittelbaren übergeordneten Gruppe verschoben werden. Wird sie aus der Gruppe gezogen, wird die Aktion abgelehnt und die Spalte springt an ihre ursprüngliche Position zurück.
- **Nicht gruppierte Spalten**: Eine nicht gruppierte Spalte kann nur an Positionen verschoben werden, die von anderen nicht gruppierten Spalten belegt sind. Sie kann nicht mitten in einer Gruppe abgelegt werden.
- **Gruppen neu anordnen**: Eine ganze Gruppe kann gezogen werden, um sie zwischen ihren Geschwistergruppen auf derselben Verschachtelungsebene neu anzuordnen.

```
Gruppen: Gruppe "G1" [A, B, C], Gruppe "G2" [D, E]

A an Position 2 verschieben -> OK (innerhalb von G1, Ergebnis: [B, C, A])
A an Position 3 verschieben -> Abgelehnt (Position 3 befindet sich innerhalb von G2)
D an Position 4 verschieben -> OK (innerhalb von G2, Ergebnis: [E, D])
D an Position 1 verschieben -> Abgelehnt (Position 1 befindet sich innerhalb von G1)
```

## Gruppen fixieren {#pinning-groups}

Eine Gruppe kann mit `setPinDirection()` links oder rechts fixiert werden. Alle Spalten innerhalb der Gruppe übernehmen die Fixierungsrichtung der Gruppe. Individuelle Fixierungseinstellungen der Spalten werden dabei überschrieben.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// Sowohl "number" als auch "title" werden links fixiert,
// unabhängig von ihren eigenen Fixierungseinstellungen
```

Nicht gruppierte Spalten behalten die Fixierungsrichtung aus ihrer Spaltendefinition bei.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablepinnedcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TablePinnedColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

## Höhe der Gruppenüberschrift {#group-header-height}

Die Höhe der Kopfzeilen für Gruppen lässt sich unabhängig von der Höhe regulärer Spaltenüberschriften mit `setGroupHeaderHeight()` steuern.

```java
table.setGroupHeaderHeight(32); // Gruppenzeilen sind 32px hoch
table.setHeaderHeight(48);      // Die Spaltenkopfzeile bleibt 48px hoch
```

Standardmäßig entspricht die Höhe der Gruppenüberschrift der Standardhöhe der Kopfzeile.

## Gruppen mit CSS-Parts gestalten {#styling-groups-with-css-parts}

Gruppenüberschriften und Spalten stellen CSS-Parts bereit, die sich mit `::part()` gestalten lassen. Die folgenden Parts sind verfügbar:

| Part | Beschreibung |
| --- | --- |
| `cell-group-{ID}` | Gruppenkopfzelle, gezielt über die Gruppen-ID |
| `cell-group-depth-{N}` | Gruppenkopfzelle, gezielt über die Verschachtelungstiefe (`0` = oberste Ebene, `1` = zweite Ebene usw.) |
| `cell-column-{ID}` | Alle Zellen (Kopf- und Inhaltsbereich) einer bestimmten Spalten-ID |
| `cell-content-group-{ID}` | Inhaltscontainer innerhalb einer Gruppenkopfzeile |
| `cell-label-group-{ID}` | Beschriftung innerhalb einer Gruppenkopfzeile |

<!-- vale off -->
<ComponentDemo
path='/webforj/tablestyledcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableStyledColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
  'src/main/frontend/css/table/tablestyledcolumngroups.css',
]}
height='600px'
/>
<!-- vale on -->

### Nach Gruppen-ID gestalten {#styling-by-group-id}

Verwenden Sie die Gruppen-ID, um bestimmte Gruppen mit eigenen Farben oder Schriftstilen anzusprechen.

```css
dwc-table::part(cell-group-catalog) {
  background: var(--dwc-color-primary-30);
  color: var(--dwc-color-primary-text-30);
  font-weight: 600;
}

dwc-table::part(cell-group-bio) {
  background: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}

dwc-table::part(cell-column-title) {
  font-weight: 600;
}
```

### Nach Verschachtelungstiefe gestalten {#styling-by-depth}

Parts, die auf der Verschachtelungstiefe basieren, sind nützlich, wenn Sie alle Gruppen auf einer bestimmten Ebene einheitlich gestalten möchten, ohne jede Gruppe einzeln anzusprechen.

```css
/* Alle Gruppen der obersten Ebene gestalten */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Alle Gruppen der zweiten Ebene gestalten */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Ausgeblendete Spalten {#hidden-columns}

Ausgeblendete Spalten werden aus der visuellen Reihenfolge und dem Kopfzeilenlayout ausgeschlossen. Enthält eine Gruppe sowohl sichtbare als auch ausgeblendete Spalten, werden nur die sichtbaren angezeigt und der `colspan` der Gruppe wird entsprechend angepasst. Sind alle Spalten einer Gruppe ausgeblendet, wird die Gruppenüberschrift gar nicht gerendert.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablehiddencolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableHiddenColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->
