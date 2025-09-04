---
sidebar_position: 21
title: Dynamic Styling
slug: styling
sidebar_class_name: updated-content
---
<!-- vale off -->
# Dynamic Styling <DocChip chip='since' label='25.00' />
<!-- vale on -->

In webforJ 25 and higher, it's possible to style individual rows and cells in the Table using custom part names. These names can be assigned dynamically based on your app's logic, giving you fine-grained control over the table’s appearance.

## Row styling {#row-styling}

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

## Cell styling {#cell-styling}

The `setCellPartProvider()` method styles individual cells based on both the data item and the column they belong to. This makes it ideal for highlighting specific values, like calling out ages preceding a threshold or invalid entries.

Like row parts, cell parts are defined by a name and targeted using the `::part()` selector.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Reacting to data updates {#reacting-to-data-updates}

If your app modifies data programmatically, such as updating a user's age, the table will automatically re-evaluate and reapply any associated row or cell styles once the updated item is committed in the repository 

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Striped rows {#striped-rows}

Enable alternating background colors for rows to improve readability:

```java
// Apply striped row styling
table.setStriped(true);
```

## Borders {#borders}

Configure which borders are shown around the `Table`, columns, and rows:

```java
// Enable all borders
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Remove all borders
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

The demo below showcases a simple way to align your `Table's` visual appearance with the rest of your app using `setStriped()` and `setBordersVisible()`.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>
