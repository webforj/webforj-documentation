---
sidebar_position: 3
title: Selection
draft: true
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

### Selection Event

1. Selection
The table component provides flexible selection options to enhance user interaction.

1.1 Single and Multiple Selection Modes
The table supports both single and multiple selection modes. Users can choose between these modes to tailor the selection behavior based on their needs.

How to Use:

To enable single selection mode, set the selectionMode property to SINGLE.
For multiple selection, set the selectionMode property to MULTIPLE.
1.2 User-Friendly Selection Methods
Users can easily select rows using simple click interactions or opt for multiple selections using a checkbox column.

How to Use:

Click on a row to select it.
Utilize a checkbox column for convenient multiple selections.