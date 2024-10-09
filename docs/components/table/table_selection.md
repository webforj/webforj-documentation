---
sidebar_position: 2
title: Selection
slug: selection
---

The `Table` component provides various selection capabilities. There are methods for selecting a single item, multiple items, or programmatically managing selections.

### Selection Mode

The selection mode in the table determines how items can be selected by the user. It provides options for configuring the behavior of item selection. The Table class provides a method to set the selection mode:

```java
setSelectionMode(SelectionMode selectionMode)
```

Available SelectionMode options include:

>- `SINGLE` - (single selection) 
>- `MULTI` - (multiple selection)
>- `NONE` - (no selection).

### Selection Event

The `Table` component package emits several events related to row selection. These events capture  changes in the selection state of `Table` rows. Below are the key selection events along with their descriptions:

>- `TableItemSelectEvent` -  Emitted when a table item is selected.
>- `TableItemDeselectEvent` - Emitted when a table item is deselected.
>- `TableItemSelectionChange` - Emitted when the overall selection in the table changes, or when an additional selection is chosen.

:::info
The `TableItemSelectEvent` and `TableItemDeselectEvent` are not triggered when multiple selection mode is active, and the selection is made via the header checkbox. In this case, the `TableItemSelectionChange` should be used instead.
:::

In the example below, a `TableItemSelectEvent` event will be fired whenever a user selects a row. The event can be handled by adding a listener to the table using the `onItemSelect()` method.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

### Checkbox Selection

Checkbox selection is enabled when the selection mode is `MULTI`, and allows users to conveniently select one or more items using checkboxes associated with each row. This feature is particularly useful for scenarios where users need to perform bulk actions on selected items. The Table class provides methods to enable and customize checkbox selection.

By using the `setCheckboxSelection(boolean checkboxSelection)` method, checkboxes can be configured to be displayed next to each row, allowing users to select items. The program below shows multiple selection and checkbox selection enabled:

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

### Programatic Selection

The `Table` component provides programmatic selection methods, allowing you to manipulate selected items either by their keys or by the entire items. 

#### Select by Key

The `selectKey(Object... keys)` method enables you to programmatically select items using their keys. You can pass one or more keys to this method, and it will update the selection accordingly.

#### Select by Index

Using the `selectIndex(int... indices)` method allows you to pass one or more indices to the method and updates the selected items accordingly.

#### Selecting Entire Items

Finally, the `select(T... items)` method allows you to programmatically select items by passing one or more items themselves to this method to update the selection accordingly.