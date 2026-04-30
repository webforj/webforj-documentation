---
title: Column Groups
sidebar_position: 7
---
<DocChip chip='since' label='25.12' />

Column groups let you organize related columns under shared, multi-row headers. A group label spans across its child columns, making it easier for users to scan and understand the structure of complex tables. Groups can be nested to any depth, and the `Table` automatically renders the correct number of header rows.

## Creating column groups {#creating-column-groups}

Create a group with the `ColumnGroup.of()` factory method, then chain `add()` calls to populate it with column references, other groups, or a mix of both. Apply the groups to a `Table` with `setColumnGroups()`.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

When groups are set, the `Table` renders a multi-row header where each group label spans across its child columns. The nesting depth determines how many header rows appear. A flat group adds one extra row, while a two-level nesting adds two, and so on.

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
Groups can be set or changed at any time, before or after the `Table` is rendered. Pass `null` or an empty list to `setColumnGroups()` to remove all grouping and return to a single-row header.
<!-- vale Google.OxfordComma = YES -->

```java
// Remove all column groups
table.setColumnGroups(null);
```

## Column ordering {#column-ordering}

When groups are active, the visual column order is determined by the group tree rather than the order in which columns were added to the `Table`. The tree is walked depth-first, left to right.

```
Columns added:  [A, B, C, D, E]
Groups:         Group "G1" [C, A], Group "G2" [E, D]
Visual order:   C, A, E, D, B
```

Ungrouped columns, those not referenced in any group, aren't hidden. They appear at their natural position relative to grouped columns, based on the order they were originally added to the `Table`.

In this example, `Number` appears first because it was added before `Title`. `Label` appears between `Genre` and `Cost` because it was added between them in the original column order:

```
Columns added:  [Number, Title, Artist, Genre, Label, Cost]
Groups:         Group "Music" [Title, Artist, Genre], Group "Pricing" [Cost]
Visual order:   Number, Title, Artist, Genre, Label, Cost
```

The following demo illustrates this behavior. `Number` and `Label` aren't referenced in any group, but they retain their natural positions based on the order they were added to the `Table`.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumngroupordering?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnGroupOrderingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

:::tip Controlling ungrouped column placement
To control ungrouped column placement explicitly, include them as top-level column references in the group tree.
:::

## Column moving within groups {#column-moving-within-groups}

When groups are active, drag-and-drop column movement is constrained to maintain group integrity:

- **Within a group**: a column inside a group can only be moved within its immediate parent group. Dragging it outside the group is rejected, and the column snaps back to its original position.
- **Ungrouped columns**: an ungrouped column can only move to positions occupied by other ungrouped columns. It can't be dropped into the middle of a group.
- **Reordering groups**: an entire group can be dragged to reorder it among its siblings at the same nesting level.

```
Groups:  Group "G1" [A, B, C], Group "G2" [D, E]

Move A to position 2 -> OK (within G1, result: [B, C, A])
Move A to position 3 -> Rejected (position 3 is inside G2)
Move D to position 4 -> OK (within G2, result: [E, D])
Move D to position 1 -> Rejected (position 1 is inside G1)
```

## Pinning groups {#pinning-groups}

A group can be pinned left or right using `setPinDirection()`. All columns inside the group inherit the group pin direction, and individual column pin settings are overridden by the group.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// Both "number" and "title" are pinned left,
// regardless of their own pin settings
```

Ungrouped columns retain their own pin direction from their column definition.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablepinnedcolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TablePinnedColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

## Group header height {#group-header-height}

Group header row height can be controlled independently from regular column headers using `setGroupHeaderHeight()`.

```java
table.setGroupHeaderHeight(32); // Group rows are 32px tall
table.setHeaderHeight(48);      // Column header row stays at 48px
```

The default group header height matches the default header height.

## Styling groups with CSS parts {#styling-groups-with-css-parts}

Group headers and columns expose CSS parts for styling via `::part()`. The following parts are available:

| Part | Description |
| --- | --- |
| `cell-group-{ID}` | Group header cell, targeted by group ID |
| `cell-group-depth-{N}` | Group header cell, targeted by depth (`0` = top-level, `1` = second-level, etc.) |
| `cell-column-{ID}` | All cells (header and body) for a given column ID |
| `cell-content-group-{ID}` | Content wrapper within a group header |
| `cell-label-group-{ID}` | Label within a group header |

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

### Styling by group ID {#styling-by-group-id}

Use the group ID to target specific groups with unique colors or typography.

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

### Styling by depth {#styling-by-depth}

Depth-based parts are useful when you want to apply consistent styling to all groups at a certain nesting level without targeting each group individually.

```css
/* Style all top-level groups */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Style all second-level groups */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Hidden columns {#hidden-columns}

Hidden columns are excluded from the visual order and the header layout. If a group contains a mix of visible and hidden columns, only the visible ones appear and the group `colspan` adjusts accordingly. If every column in a group is hidden, the group header isn't rendered at all.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablehiddencolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableHiddenColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->