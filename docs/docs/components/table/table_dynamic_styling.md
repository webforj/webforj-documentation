---
sidebar_position: 21
title: Dynamic Styling
slug: styling
---

The `Table` component lets you style specific rows or cells using custom part names. You can apply these names based on your app's needs, giving you control over how different parts of your table look.

## Row styling

The `setRowPartProvider()` method assigns part names to entire rows based on the data item they contain. This lets you highlight full rows that meet specific conditions—for example, alternating background colors for even rows.

These style names can be targeted using the `::part()` selector in your CSS.

:::tip Shadow parts
The `::part()` selector is a special CSS feature that allows you to style elements inside a component's shadow DOM—as long as those elements expose a `part` attribute. This is especially useful for styling internal parts of webforJ components, like rows or cells in a table.

For more on how shadow parts work and how to define and target them, see the [Styling](../../styling/shadow-parts) section.
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Cell styling

The `setCellPartProvider()` method styles individual cells based on both the data item and the column they belong to. This makes it ideal for highlighting specific values, like calling out ages preceding a threshold or invalid entries.

Like row parts, cell parts are defined by a name and targeted using the `::part()` selector.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>


## Reacting to data updates

If your app modifies data programmatically, such as updating a user's age, the table will automatically re-evaluate and reapply any associated row or cell styles once the updated item is committed in the repository 

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

