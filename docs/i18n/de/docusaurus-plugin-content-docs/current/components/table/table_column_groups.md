---
title: Column Groups
sidebar_position: 7
sidebar_class_name: new-content
_i18n_hash: fccec5a60bfd614d344758d3624bc602
---
<DocChip chip='since' label='25.12' />

Spaltengruppen ermöglichen es Ihnen, verwandte Spalten unter gemeinsamen, mehrzeiligen Überschriften zu organisieren. Ein Gruppenschild spannt sich über die untergeordneten Spalten und erleichtert es den Benutzern, die Struktur komplexer Tabellen zu scannen und zu verstehen. Gruppen können beliebig tief geschachtelt werden, und die `Table` rendert automatisch die korrekte Anzahl von Kopfzeilen.

## Erstellung von Spaltengruppen {#creating-column-groups}

Erstellen Sie eine Gruppe mit der Fabrikmethode `ColumnGroup.of()`, und verketten Sie `add()`-Aufrufe, um sie mit Spaltenreferenzen, anderen Gruppen oder einer Mischung aus beidem zu füllen. Wenden Sie die Gruppen mit `setColumnGroups()` auf eine `Table` an.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Wenn Gruppen festgelegt sind, rendert die `Table` eine mehrzeilige Kopfzeile, in der jedes Gruppenschild über den untergeordneten Spalten spannt. Die Schachteltiefe bestimmt, wie viele Kopfzeilen angezeigt werden. Eine flache Gruppe fügt eine zusätzliche Zeile hinzu, während eine zweistufige Schachtelung zwei hinzufügt und so weiter.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablenestedcolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableNestedColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

<!-- vale Google.OxfordComma = NO -->
Gruppen können jederzeit vor oder nach dem Rendern der `Table` festgelegt oder geändert werden. Übergeben Sie `null` oder eine leere Liste an `setColumnGroups()`, um alle Gruppierungen zu entfernen und zur Kopfzeile mit einer Zeile zurückzukehren.
<!-- vale Google.OxfordComma = YES -->

```java
// Entfernen aller Spaltengruppen
table.setColumnGroups(null);
```

## Spaltenreihenfolge {#column-ordering}

Wenn Gruppen aktiv sind, wird die visuelle Spaltenreihenfolge durch den Gruppenbaum bestimmt und nicht durch die Reihenfolge, in der die Spalten zur `Table` hinzugefügt wurden. Der Baum wird tiefenfirst von links nach rechts durchlaufen.

```
Hinzugefügte Spalten:  [A, B, C, D, E]
Gruppen:             Gruppe "G1" [C, A], Gruppe "G2" [E, D]
Visuelle Reihenfolge: C, A, E, D, B
```

Ungruppierte Spalten, die in keiner Gruppe referenziert werden, sind nicht ausgeblendet. Sie erscheinen an ihrer natürlichen Position relativ zu den gruppierten Spalten, basierend auf der Reihenfolge, in der sie ursprünglich zur `Table` hinzugefügt wurden.

In diesem Beispiel erscheint `Number` zuerst, da es vor `Title` hinzugefügt wurde. `Label` erscheint zwischen `Genre` und `Cost`, weil es zwischen ihnen in der ursprünglichen Spaltenreihenfolge hinzugefügt wurde:

```
Hinzugefügte Spalten:  [Number, Title, Artist, Genre, Label, Cost]
Gruppen:             Gruppe "Music" [Title, Artist, Genre], Gruppe "Pricing" [Cost]
Visuelle Reihenfolge: Number, Title, Artist, Genre, Label, Cost
```

Die folgende Demo veranschaulicht dieses Verhalten. `Number` und `Label` werden in keiner Gruppe referenziert, behalten jedoch ihre natürlichen Positionen basierend auf der Reihenfolge, in der sie zur `Table` hinzugefügt wurden.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumngroupordering?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnGroupOrderingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

:::tip Steuerung der Platzierung ungruppierter Spalten
Um die Platzierung ungruppierter Spalten ausdrücklich zu steuern, fügen Sie sie als Top-Level-Spaltenreferenzen im Gruppierungsbaum ein.
:::

## Spaltenbewegung innerhalb von Gruppen {#column-moving-within-groups}

Wenn Gruppen aktiv sind, wird die Drag-and-Drop-Bewegung von Spalten eingeschränkt, um die Integrität der Gruppen zu wahren:

- **Innerhalb einer Gruppe**: Eine Spalte innerhalb einer Gruppe kann nur innerhalb ihrer unmittelbaren Elterngruppe verschoben werden. Das Ziehen außerhalb der Gruppe wird abgelehnt, und die Spalte springt an ihren ursprünglichen Platz zurück.
- **Ungruppierte Spalten**: Eine ungruppierte Spalte kann nur auf Positionen verschoben werden, die von anderen ungruppierten Spalten belegt sind. Sie kann nicht in die Mitte einer Gruppe fallen.
- **Gruppen neu anordnen**: Eine gesamte Gruppe kann gezogen werden, um sie unter ihren Geschwistern auf derselben Schachtelungsebene neu anzuordnen.

```
Gruppen:  Gruppe "G1" [A, B, C], Gruppe "G2" [D, E]

A auf Position 2 verschieben -> OK (innerhalb von G1, Ergebnis: [B, C, A])
A auf Position 3 verschieben -> Abgelehnt (Position 3 befindet sich innerhalb von G2)
D auf Position 4 verschieben -> OK (innerhalb von G2, Ergebnis: [E, D])
D auf Position 1 verschieben -> Abgelehnt (Position 1 befindet sich innerhalb von G1)
```

## Gruppieren von Gruppen {#pinning-groups}

Eine Gruppe kann links oder rechts mit `setPinDirection()` fixiert werden. Alle Spalten innerhalb der Gruppe erben die Pin-Richtung der Gruppe, und individuelle Pin-Einstellungen für Spalten werden von der Gruppe überschrieben.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// Sowohl "number" als auch "title" sind links fixiert,
// unabhängig von ihren eigenen Pin-Einstellungen
```

Ungruppierte Spalten behalten ihre eigene Pin-Richtung aus ihrer Spaltendefinition.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablepinnedcolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TablePinnedColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

## Höhe der Gruppenüberschrift {#group-header-height}

Die Höhe der Gruppenüberschriftszeile kann unabhängig von den regulären Spaltenüberschriften mit `setGroupHeaderHeight()` gesteuert werden.

```java
table.setGroupHeaderHeight(32); // Gruppenzeilen sind 32px hoch
table.setHeaderHeight(48);      // Die Zeile der Spaltenüberschrift bleibt bei 48px
```

Die standardmäßige Höhe der Gruppenüberschrift entspricht der standardmäßigen Höhenüberschrift.

## Gruppierung von Styles mit CSS-Teilen {#styling-groups-with-css-parts}

Gruppenüberschriften und Spalten bieten CSS-Teile für das Styling über `::part()`. Die folgenden Teile sind verfügbar:

| Teil | Beschreibung |
| --- | --- |
| `cell-group-{ID}` | Gruppenüberschriftzelle, angesprochen durch die Gruppen-ID |
| `cell-group-depth-{N}` | Gruppenüberschriftzelle, angesprochen durch die Tiefe (`0` = oberste Ebene, `1` = zweite Ebene usw.) |
| `cell-column-{ID}` | Alle Zellen (Kopf- und Körper) für eine gegebene Spalten-ID |
| `cell-content-group-{ID}` | Inhaltshülle innerhalb einer Gruppenüberschrift |
| `cell-label-group-{ID}` | Bezeichnung innerhalb einer Gruppenüberschrift |

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablestyledcolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableStyledColumnGroupsView.java'
cssURL='/css/table/tablestyledcolumngroups.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

### Styling nach Gruppen-ID {#styling-by-group-id}

Verwenden Sie die Gruppen-ID, um spezifische Gruppen mit einzigartigen Farben oder Typografie anzusprechen.

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

Tiefe-basierte Teile sind nützlich, wenn Sie ein konsistentes Styling auf alle Gruppen auf einer bestimmten Schachtelungsebene anwenden möchten, ohne jede Gruppe einzeln anzusprechen.

```css
/* Alle obersten Gruppen stilisieren */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Alle Gruppen der zweiten Ebene stilisieren */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Ausgeblendete Spalten {#hidden-columns}

Ausgeblendete Spalten sind von der visuellen Reihenfolge und dem Header-Layout ausgeschlossen. Wenn eine Gruppe eine Mischung aus sichtbaren und ausgeblendeten Spalten enthält, erscheinen nur die sichtbaren und die Gruppenspalte wird entsprechend angepasst. Wenn jede Spalte in einer Gruppe verborgen ist, wird die Gruppenüberschrift gar nicht gerendert.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablehiddencolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableHiddenColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->
