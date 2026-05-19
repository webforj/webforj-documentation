---
title: Column Groups
sidebar_position: 7
_i18n_hash: f4ab153f6d1e8c4d029e16c3abc41762
---
<DocChip chip='since' label='25.12' />

Spaltengruppen ermöglichen es Ihnen, verwandte Spalten unter gemeinsamen, mehrzeiligen Überschriften zu organisieren. Ein Gruppenlabel erstreckt sich über seine untergeordneten Spalten, wodurch es für Benutzer einfacher wird, komplexe Tabellen zu scannen und deren Struktur zu verstehen. Gruppen können beliebig tief geschachtelt werden, und die `Table` rendert automatisch die richtige Anzahl von Kopfzeilen.

## Erstellen von Spaltengruppen {#creating-column-groups}

Erstellen Sie eine Gruppe mit der `ColumnGroup.of()` Fabrikmethode und verketten Sie `add()`-Aufrufe, um sie mit Spaltenreferenzen, anderen Gruppen oder einer Mischung aus beiden zu füllen. Wenden Sie die Gruppen mit `setColumnGroups()` auf eine `Table` an.

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

Wenn Gruppen festgelegt sind, rendert die `Table` eine mehrzeilige Kopfzeile, in der jedes Gruppenlabel über seinen untergeordneten Spalten erscheint. Die Schachtelungstiefe bestimmt, wie viele Kopfzeilen angezeigt werden. Eine flache Gruppe fügt eine zusätzliche Zeile hinzu, während eine zweistufige Schachtelung zwei hinzufügt, und so weiter.

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
Gruppen können jederzeit, vor oder nach dem Rendern der `Table`, gesetzt oder geändert werden. Übergeben Sie `null` oder eine leere Liste an `setColumnGroups()`, um alle Gruppierungen zu entfernen und zur einzeiligen Kopfzeile zurückzukehren.
<!-- vale Google.OxfordComma = YES -->

```java
// Entfernen Sie alle Spaltengruppen
table.setColumnGroups(null);
```

## Spaltenanordnung {#column-ordering}

Wenn Gruppen aktiv sind, wird die visuelle Spaltenreihenfolge durch den Gruppierungsbaum bestimmt und nicht durch die Reihenfolge, in der Spalten zur `Table` hinzugefügt wurden. Der Baum wird tiefenfirst von links nach rechts durchlaufen.

```
Hinzugefügte Spalten:  [A, B, C, D, E]
Gruppen:             Gruppe "G1" [C, A], Gruppe "G2" [E, D]
Visuelle Reihenfolge: C, A, E, D, B
```

Ungruppierte Spalten, die in keiner Gruppe referenziert sind, werden nicht ausgeblendet. Sie erscheinen an ihrer natürlichen Position in Bezug auf die gruppierten Spalten, basierend auf der Reihenfolge, in der sie ursprünglich zur `Table` hinzugefügt wurden.

In diesem Beispiel erscheint `Number` zuerst, weil es vor `Title` hinzugefügt wurde. `Label` erscheint zwischen `Genre` und `Cost`, weil es zwischen ihnen in der ursprünglichen Spaltenreihenfolge hinzugefügt wurde:

```
Hinzugefügte Spalten:  [Number, Title, Artist, Genre, Label, Cost]
Gruppen:             Gruppe "Music" [Title, Artist, Genre], Gruppe "Pricing" [Cost]
Visuelle Reihenfolge: Number, Title, Artist, Genre, Label, Cost
```

Die folgende Demo veranschaulicht dieses Verhalten. `Number` und `Label` sind in keiner Gruppe referenziert, behalten jedoch ihre natürlichen Positionen basierend auf der Reihenfolge, in der sie zur `Table` hinzugefügt wurden.

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

:::tip Steuerung der Platzierung ungruppierter Spalten
Um die Platzierung ungruppierter Spalten explizit zu steuern, fügen Sie sie als oberste Spaltenreferenzen im Gruppierungsbaum hinzu.
:::

## Spaltenbewegung innerhalb von Gruppen {#column-moving-within-groups}

Wenn Gruppen aktiv sind, ist die Drag-and-Drop-Bewegung von Spalten eingeschränkt, um die Integrität der Gruppen zu wahren:

- **Innerhalb einer Gruppe**: Eine Spalte innerhalb einer Gruppe kann nur innerhalb ihrer unmittelbaren übergeordneten Gruppe verschoben werden. Das Ziehen außerhalb der Gruppe wird abgelehnt, und die Spalte springt an ihren ursprünglichen Platz zurück.
- **Ungruppeierte Spalten**: Eine ungruppierte Spalte kann nur an Positionen verschoben werden, die von anderen ungruppierten Spalten belegt sind. Sie kann nicht mitten in eine Gruppe abgelegt werden.
- **Neuordnung von Gruppen**: Eine gesamte Gruppe kann gezogen werden, um sie unter ihren Geschwistern auf derselben Schachtelungsebene neu anzuordnen.

```
Gruppen:  Gruppe "G1" [A, B, C], Gruppe "G2" [D, E]

Bewege A an Position 2 -> OK (innerhalb von G1, Ergebnis: [B, C, A])
Bewege A an Position 3 -> Abgelehnt (Position 3 liegt innerhalb von G2)
Bewege D an Position 4 -> OK (innerhalb von G2, Ergebnis: [E, D])
Bewege D an Position 1 -> Abgelehnt (Position 1 liegt innerhalb von G1)
```

## Gruppen fixieren {#pinning-groups}

Eine Gruppe kann links oder rechts fixiert werden, indem `setPinDirection()` verwendet wird. Alle Spalten innerhalb der Gruppe erben die Fixierrichtung der Gruppe, und die individuellen Spaltenfixiereinstellungen werden durch die Gruppe überschrieben.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// Sowohl "number" als auch "title" sind links fixiert,
// unabhängig von ihren eigenen Fixiereinstellungen
```

Ungruppeierte Spalten behalten ihre eigene Fixierrichtung von ihrer Spaltendefinition.

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

Die Höhe der Gruppenkopfzeile kann unabhängig von den regulären Spaltenköpfen mit `setGroupHeaderHeight()` gesteuert werden.

```java
table.setGroupHeaderHeight(32); // Gruppenzeilen sind 32px hoch
table.setHeaderHeight(48);      // Die Kopfzeile bleibt bei 48px
```

Die Standardhöhe der Gruppenüberschrift entspricht der Standardhöhe der Kopfzeile.

## Gruppen mit CSS-Teilen stylen {#styling-groups-with-css-parts}

Gruppenüberschriften und Spalten bieten CSS-Teile für das Styling über `::part()`. Die folgenden Teile sind verfügbar:

| Teil | Beschreibung |
| --- | --- |
| `cell-group-{ID}` | Gruppenkopfzeilenzelle, die nach Gruppen-ID angesteuert wird |
| `cell-group-depth-{N}` | Gruppenkopfzeilenzelle, die nach Tiefe angesteuert wird (`0` = oberste Ebene, `1` = zweite Ebene usw.) |
| `cell-column-{ID}` | Alle Zellen (Kopf- und Körperzellen) für eine gegebene Spalten-ID |
| `cell-content-group-{ID}` | Inhaltswrapper innerhalb einer Gruppenüberschrift |
| `cell-label-group-{ID}` | Label innerhalb einer Gruppenüberschrift |

<!-- vale off -->
<ComponentDemo
path='/webforj/tablestyledcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableStyledColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
  'src/main/resources/static/css/table/tablestyledcolumngroups.css',
]}
height='600px'
/>
<!-- vale on -->

### Styling nach Gruppen-ID {#styling-by-group-id}

Verwenden Sie die Gruppen-ID, um gezielt bestimmte Gruppen mit einzigartigen Farben oder Typografie anzusprechen.

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

### Styling nach Tiefe {#styling-by-depth}

Tiefe-basierte Teile sind nützlich, wenn Sie konsistentes Styling für alle Gruppen auf einer bestimmten Schachtelungsebene anwenden möchten, ohne jede Gruppe einzeln anzusprechen.

```css
/* Stil für alle obersten Gruppen */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Stil für alle zweite Ebenen Gruppen */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Verborgene Spalten {#hidden-columns}

Verborgene Spalten werden von der visuellen Reihenfolge und dem Layout der Kopfzeile ausgeschlossen. Wenn eine Gruppe eine Mischung aus sichtbaren und verborgenen Spalten enthält, erscheinen nur die sichtbaren, und der `colspan` der Gruppe wird entsprechend angepasst. Wenn jede Spalte in einer Gruppe verborgen ist, wird die Gruppenüberschrift überhaupt nicht gerendert.

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
