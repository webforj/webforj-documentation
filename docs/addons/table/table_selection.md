---
sidebar_position: 3
title: Selection
---

The `Table` component provides various selection capabilities. There are methods for selecting a single item, multiple items, or programmatically managing selections.

### Selection Mode

The selection mode in the table determines how items can be selected by the user. It provides options for configuring the behavior of item selection. The Table class provides a method to set the selection mode:

`setSelectionMode(SelectionMode selectionMode)`

Available SelectionMode options include:

>- `SINGLE` - (single selection) 
>- `MULTI` - (multiple selection)
>- `NONE` - (no selection).

### Checkbox Selection

Checkbox selection is enabled when the selection mode is `MULTI`, and allows users to conveniently select one or more items using checkboxes associated with each row. This feature is particularly useful for scenarios where users need to perform bulk actions on selected items. The Table class provides methods to enable and customize checkbox selection.

By setting checkboxSelection to true, checkboxes will be displayed next to each row, allowing users to select items.

### Selection Event

The `Table` component package emits several events related to row selection. These events capture  changes in the selection state of `Table` rows. Below are the key selection events along with their descriptions:

>- `TableItemSelectEvent` -  Emitted when a table item (row) is selected. This event provides information about the selected item, allowing developers to respond to individual row selections.

>- `TableItemDeselectEvent` - Emitted when a previously selected table item (row) is deselected. Developers can use this event to handle actions associated with row deselection.
>- `TableItemSelectionChange` - Emitted when the overall selection in the table changes. This event provides a consolidated view of the selected items, allowing developers to respond to changes in the table's selection state.