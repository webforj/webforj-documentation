---
sidebar_position: 2
title: Columns
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';

The `Table` class utilizes `Column` classes to handle the creation of data columns within it. This class has a wide range of functionality that allows a user to thoroughly customize what is displayed within each of the columns.


## Constructor

Creating a `Column` can be done using the constructor, which takes a reference to the `Table` it is being added to, and an ID. Note that this ID will be auto generated if it is not provided. 

`Column(Table<T> table, String id)`

In addition to creating a standalone column, the `Table` contains a set of methods which can be used to quickly add new columns to a `Table`:

- `addColumn(Function<T, V> provider)`
- `addColumn(String id, Function<T, V> provider)`
- `addColumn(Renderer<T> renderer)`
- `addColumn(String id, Renderer<T> renderer)`

## Column Settings

There are various settings that a column can be configured with for more control over data display:

### Pin Direction

Column pinning is a feature that allows users to affix or "pin" a column to a specific side of the table, enhancing visibility and accessibility. This is useful when certain columns, such as identifiers or essential information, need to remain visible while scrolling horizontally through a table.

<ComponentDemo 
path='https://eu.bbx.kitchen/webapp/controlsamples?class=addondemos.tabledemos.TableColumnPinning' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/addondemos/tabledemos/TableColumnPinning.java'
height='600px'
/>

There are three available directions for pinning a column:

- `PinDirection.LEFT`: Pins the column to the left side.
- `PinDirection.RIGHT`: Pins the column to the right side.
- `PinDirection.AUTO`: Column appears based on the insertion order.

Pinning can be set programmatically, allowing users to change the pin direction based on user interactions or application logic.


### Alignment

Column alignment defines the horizontal positioning of data within a column. It influences how data values are displayed, providing a visual guide to users about the nature of the information. 

<ComponentDemo 
path='https://eu.bbx.kitchen/webapp/controlsamples?class=addondemos.tabledemos.TableColumnAlignment' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/addondemos/tabledemos/TableColumnAlignment.java'
height='600px'
/>

The Table Component supports three primary alignment options:

- `Column.Alignment.LEFT`: Suitable for textual or descriptive data where maintaining a leftward flow is intuitive. Useful when emphasizing the starting point of the content.
- `Column.Alignment.CENTER`: Ideal for numerical or categorical data where a balanced presentation is desired. Creates a visually centered display.
- `Column.Alignment.RIGHT`: Commonly used for numerical data, especially when the magnitude or precision of numbers is significant. Aligns data towards the right for a natural reading flow.

In the above example, the final column for `Cost` has been right-aligned to provide a more obvious visual distinction.

### Other Settings

#### Visibility

It is possible to set the visibility of a `Column`, determining whether or not it will be shown within the table. This can be useful when, among other things, determining whether or not to display sensitive information. 

Use the `setHidden()` method, as shown below, to set this property on a `Column`:

`table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);`

#### Navigable

The navigable attribute determines whether users can interact with a column during navigation. Setting `setSuppressNavigable()` to true restricts user interaction with the column, providing a read-only experience.

`table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);`

#### Label

The label of a column is its public-facing identifier, contributing to the clarity and understanding of displayed data. Use setLabel to set or modify the label associated with a column.

`table.addColumn("Product ID", Product::getProductId).setLabel("ID");`

#### Minimum Width

 The `setMinWidth()` method allows you to define the minimum width of a column, ensuring consistent and aesthetically pleasing layout.

`table.addColumn("Price", Product::getPrice).setMinWidth(100.0);`

:::info
The value passed represents the desired minimum width in pixels.
:::

## Value Providers

Value Provider is a function responsible for translating raw data from the underlying dataset into a format suitable for display within a specific column. The function, defined by the user, takes an instance of the row data type (T) and returns the value to be showcased in the associated column for that particular row.

To set a value provider on a column, use the `setValueProvider()` method, as shown below:

```java
table.addColumn("Product Name", Product::getProductName)
    .setValueProvider(product -> formatProductName(product.getProductName()));
```

In this example, the "Product Name" column uses a custom value provider to format the product names before display. The `formatProductName()` function dynamically transforms raw data into a more readable representation.