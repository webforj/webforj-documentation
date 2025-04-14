---
sidebar_position: 45
title: Dynamic Styling
slug: styling
---

The `Table` component lets you style specific rows or cells using custom part names. You can apply these names based on your app's needs, giving you control over how different parts of your table look.

## Row styling

The `setRowPartProvider()` method assigns style names to entire rows based on the data item they contain. This lets you highlight full rows that meet specific conditions—for example, alternating background colors for even rows.

These style names can be targeted using the `::part()` selector in your CSS.

<ComponentDemo 
path='http://localhost:8080/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Cell styling

The `setCellPartProvider()` method styles individual cells based on both the data item and the column they belong to. This makes it ideal for highlighting specific values, like calling out ages above a threshold or invalid entries.

Like row parts, cell parts are defined by a name and targeted using the `::part()` selector.

<ComponentDemo 
path='http://localhost:8080/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>


## Reacting to data updates

If your app modifies data programmatically, such as updating a user's age, the table will reflect the new styles automatically after committing the changes using `table.getRepository().commit(item)`.

<ComponentDemo 
path='http://localhost:8080/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

