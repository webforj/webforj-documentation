---
sidebar_position: 1
title: Columns
slug: columns
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';

The `Table` class utilizes `Column` classes to handle the creation of data columns within it. This class has a wide range of functionality that allows a user to thoroughly customize what is displayed within each of the columns.
To add a `Column` to a `Table`, use one of the `addColumn` factory methods.

## Value Providers

A Value Provider is a function responsible for translating raw data from the underlying dataset into a format suitable for display within a specific column. The function, defined by the user, takes an instance of the row data type (T) and returns the value to be showcased in the associated column for that particular row.

To set a value provider on a column, use one of the `addColumn` factory methods that accept a provider as an argument:

```java
    List<String> columnsList = Arrays.asList("athlete", "age", "country", "year", "sport", "gold", "silver", "bronze", "total");

    for (String column : columnsList) {
      table.addColumn(column, (JsonObject person) -> {
        JsonElement element = person.get(column);
        if (!element.isJsonNull()) {
          return element.getAsString();
        }
        return "";
      });
    }
```

In this example, a column will attempt to access data from a JSON object, rendering it only if the data is not null.

## Pin Direction

Column pinning is a feature that allows users to affix or "pin" a column to a specific side of the table, enhancing visibility and accessibility. This is useful when certain columns, such as identifiers or essential information, need to remain visible while scrolling horizontally through a table.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

There are three available directions for pinning a column:

- `PinDirection.LEFT`: Pins the column to the left side.
- `PinDirection.RIGHT`: Pins the column to the right side.
- `PinDirection.AUTO`: Column appears based on the insertion order.

Pinning can be set programmatically, allowing users to change the pin direction based on user interactions or application logic.


## Alignment

Column alignment defines the horizontal positioning of data within a column. It influences how data values are displayed, providing a visual guide to users about the nature of the information. 

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

The Table Component supports three primary alignment options:

- `Column.Alignment.LEFT`: Suitable for textual or descriptive data where maintaining a leftward flow is intuitive. Useful when emphasizing the starting point of the content.
- `Column.Alignment.CENTER`: Ideal for numerical or categorical data where a balanced presentation is desired. Creates a visually centered display.
- `Column.Alignment.RIGHT`: Commonly used for numerical data, especially when the magnitude or precision of numbers is significant. Aligns data towards the right for a natural reading flow.

In the above example, the final column for `Cost` has been right-aligned to provide a more obvious visual distinction.

## Visibility

It is possible to set the visibility of a `Column`, determining whether or not it will be shown within the table. This can be useful when, among other things, determining whether or not to display sensitive information. 

Use the `setHidden()` method, as shown below, to set this property on a `Column`:

`table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);`

## Navigable

The navigable attribute determines whether users can interact with a column during navigation. Setting `setSuppressNavigable()` to true restricts user interaction with the column, providing a read-only experience.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Label

The label of a column is its public-facing identifier, contributing to the clarity and understanding of displayed data. Use setLabel to set or modify the label associated with a column.

:::tip
By default, the label will be the same as the column ID
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

## Minimum Width

 The `setMinWidth()` method allows you to define the minimum width of a column, ensuring consistent and aesthetically pleasing layout.

 If the minimum width is not provided, the table will calculate the minimum width based on the column content.

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100.0);
```

:::info
The value passed represents the desired minimum width in pixels.  
:::

