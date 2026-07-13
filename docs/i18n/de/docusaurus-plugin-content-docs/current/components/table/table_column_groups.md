---
title: Column Groups
sidebar_position: 7
description: >-
  Group Table columns under shared, nestable multi-row headers using
  ColumnGroup.of and setColumnGroups for complex layouts.
_i18n_hash: 7a0ad76ea48fafddc0aa9965309b48b8
---
<DocChip chip='since' label='25.12' />

Spaltengruppen ermöglichen es Ihnen, verwandte Spalten unter gemeinsamen, mehrzeiligen Überschriften zu organisieren. Ein Gruppenlabel spannt sich über seine Kindspalten und erleichtert es den Benutzern, die Struktur komplexer Tabellen zu scannen und zu verstehen. Gruppen können beliebig tief geschachtelt werden, und die `Table` rendert automatisch die richtige Anzahl an Kopfzeilen.

## Erstellen von Spaltengruppen {#creating-column-groups}

Erstellen Sie eine Gruppe mit der Fabrikmethode `ColumnGroup.of()`, und verketten Sie dann `add()`-Aufrufe, um sie mit Spaltenverweisen, anderen Gruppen oder einer Mischung aus beidem zu füllen. Wenden Sie die Gruppen mit `setColumnGroups()` auf eine `Table` an.

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

Wenn Gruppen festgelegt sind, rendert die `Table` eine mehrzeilige Kopfzeile, in der jedes Gruppenlabel über seinen Kindspalten spannt. Die Schachtelungstiefe bestimmt, wie viele Kopfzeilen erscheinen. Eine flache Gruppe fügt eine zusätzliche Zeile hinzu, während eine zweistufige Schachtelung zwei hinzufügt, und so weiter.

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
Gruppen können jederzeit festgelegt oder geändert werden, bevor oder nachdem die `Table` gerendert wird. Übergeben Sie `null` oder eine leere Liste an `setColumnGroups()`, um alle Gruppierungen zu entfernen und zu einer einzeiligen Kopfzeile zurückzukehren.
<!-- vale Google.OxfordComma = YES -->

```java
// Alle Spaltengruppen entfernen
table.setColumnGroups(null);
```

## Spaltenreihenfolge {#column-ordering}

Wenn Gruppen aktiv sind, wird die visuelle Spaltenreihenfolge durch den Gruppenzweig bestimmt, nicht durch die Reihenfolge, in der die Spalten zur `Table` hinzugefügt wurden. Der Baum wird tiefenorientiert von links nach rechts durchlaufen.

```
Hinzufügte Spalten:  [A, B, C, D, E]
Gruppen:           Gruppe "G1" [C, A], Gruppe "G2" [E, D]
Visuelle Reihenfolge:   C, A, E, D, B
```

Ungruppierte Spalten, die in keiner Gruppe referenziert sind, werden nicht ausgeblendet. Sie erscheinen an ihrer natürlichen Position im Verhältnis zu gruppierten Spalten, basierend auf der Reihenfolge, in der sie ursprünglich zur `Table` hinzugefügt wurden.

In diesem Beispiel erscheint `Number` zuerst, weil es vor `Title` hinzugefügt wurde. `Label` erscheint zwischen `Genre` und `Cost`, weil es zwischen ihnen in der ursprünglichen Spaltenreihenfolge hinzugefügt wurde:

```
Hinzufügte Spalten:  [Number, Title, Artist, Genre, Label, Cost]
Gruppen:           Gruppe "Music" [Title, Artist, Genre], Gruppe "Pricing" [Cost]
Visuelle Reihenfolge:   Number, Title, Artist, Genre, Label, Cost
```

Die folgende Demo veranschaulicht dieses Verhalten. `Number` und `Label` sind in keiner Gruppe referenziert, behalten aber ihre natürlichen Positionen basierend auf der Reihenfolge, in der sie zur `Table` hinzugefügt wurden.

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

:::tip Kontrolle der Platzierung ungruppierter Spalten
Um die Platzierung ungruppierter Spalten explizit zu steuern, fügen Sie sie als oberste Spaltenverweise im Gruppenzweig hinzu.
:::

## Spaltenverschiebung innerhalb von Gruppen {#column-moving-within-groups}

Wenn Gruppen aktiv sind, ist die Drag-and-Drop-Spaltenbewegung eingeschränkt, um die Integrität der Gruppe aufrechtzuerhalten:

- **Innerhalb einer Gruppe**: Eine Spalte innerhalb einer Gruppe kann nur innerhalb ihrer unmittelbaren übergeordneten Gruppe verschoben werden. Das Ziehen nach außerhalb der Gruppe wird abgelehnt, und die Spalte springt an ihre ursprüngliche Position zurück.
- **Ungruppierte Spalten**: Eine ungruppierte Spalte kann nur in Positionen verschoben werden, die von anderen ungruppierten Spalten besetzt sind. Sie kann nicht in die Mitte einer Gruppe fallen.
- **Gruppen neu anordnen**: Eine gesamte Gruppe kann gezogen werden, um sie unter ihren Geschwistern auf derselben Schachtelungsebene neu anzuordnen.

```
Gruppen:  Gruppe "G1" [A, B, C], Gruppe "G2" [D, E]

Bewege A zu Position 2 -> OK (innerhalb G1, Ergebnis: [B, C, A])
Bewege A zu Position 3 -> Abgelehnt (Position 3 liegt innerhalb G2)
Bewege D zu Position 4 -> OK (innerhalb G2, Ergebnis: [E, D])
Bewege D zu Position 1 -> Abgelehnt (Position 1 liegt innerhalb G1)
```

## Gruppen anheften {#pinning-groups}

Eine Gruppe kann links oder rechts mit `setPinDirection()` angeheftet werden. Alle Spalten innerhalb der Gruppe erben die Gruppenheftrichtung, und individuelle Spaltenhefteinstellungen werden von der Gruppe überschrieben.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// Sowohl "number" als auch "title" sind links angepinnt,
// unabhängig von ihren eigenen Heftungseinstellungen
```

Ungrroupierte Spalten behalten ihre eigene Heftrichtung aus ihrer Spaltendefinition.

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

Die Höhe der Gruppenüberschrift kann unabhängig von regulären Spaltenüberschriften mit `setGroupHeaderHeight()` kontrolliert werden.

```java
table.setGroupHeaderHeight(32); // Gruppenzeilen sind 32px hoch
table.setHeaderHeight(48);      // Die Spaltenüberschrift bleibt bei 48px
```

Die Standardhöhe der Gruppenüberschrift entspricht der Standardhöhe der Kopfzeile.

## Stylen von Gruppen mit CSS-Teilen {#styling-groups-with-css-parts}

Gruppenüberschriften und -spalten bieten CSS-Teile für das Styling über `::part()`. Die folgenden Teile sind verfügbar:

| Teil | Beschreibung |
| --- | --- |
| `cell-group-{ID}` | Gruppenüberschriftzelle, die nach Gruppen-ID angesprochen wird |
| `cell-group-depth-{N}` | Gruppenüberschriftzelle, die nach Tiefe angesprochen wird (`0` = oberste Ebene, `1` = zweite Ebene usw.) |
| `cell-column-{ID}` | Alle Zellen (Kopf- und Bodenteil) für eine gegebene Spalten-ID |
| `cell-content-group-{ID}` | Inhaltswrapper innerhalb einer Gruppenüberschrift |
| `cell-label-group-{ID}` | Label innerhalb einer Gruppenüberschrift |

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

### Styling nach Gruppen-ID {#styling-by-group-id}

Verwenden Sie die Gruppen-ID, um bestimmte Gruppen mit einzigartigen Farben oder Typografie anzusprechen.

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

Tiefe-basierte Teile sind nützlich, wenn Sie ein einheitliches Styling für alle Gruppen auf einer bestimmten Schachtelungsebene anwenden möchten, ohne jede Gruppe einzeln anzusprechen.

```css
/* Stil für alle Gruppen auf oberster Ebene */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Stil für alle Gruppen auf zweiter Ebene */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Versteckte Spalten {#hidden-columns}

Versteckte Spalten sind von der visuellen Reihenfolge und dem Layout der Kopfzeile ausgeschlossen. Wenn eine Gruppe eine Mischung aus sichtbaren und versteckten Spalten enthält, erscheinen nur die sichtbaren und die `colspan` der Gruppe passt sich entsprechend an. Wenn jede Spalte in einer Gruppe versteckt ist, wird die Gruppenüberschrift überhaupt nicht gerendert.

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
