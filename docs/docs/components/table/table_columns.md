---
sidebar_position: 5
title: Columns
slug: columns
sidebar_class_name: updated-content
---

<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

The `Table` class utilizes `Column` classes to handle the creation of data columns within it. This class has a wide range of functionality that allows a user to thoroughly customize what is displayed within each of the columns.
To add a `Column` to a `Table`, use one of the `addColumn` factory methods.

## Value providers {#value-providers}

A value provider is a function responsible for translating raw data from the underlying dataset into a format suitable for display within a specific column. The function, defined by the user, takes an instance of the row data type (T) and returns the value to be showcased in the associated column for that particular row.

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

## Pin Direction {#pin-direction}

Column pinning is a feature that allows users to affix or "pin" a column to a specific side of the table, enhancing visibility and accessibility. This is useful when certain columns, such as identifiers or essential information, need to remain visible while scrolling horizontally through a table.

<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

There are three available directions for pinning a column:

- `PinDirection.LEFT`: Pins the column to the left side.
- `PinDirection.RIGHT`: Pins the column to the right side.
- `PinDirection.AUTO`: Column appears based on the insertion order.

Pinning can be set programmatically, allowing users to change the pin direction based on user interactions or application logic.


## Alignment {#alignment}

Column alignment defines the horizontal positioning of data within a column. It influences how data values are displayed, providing a visual guide to users about the nature of the information. 

<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

The `Table` Component supports three primary alignment options:

- `Column.Alignment.LEFT`: Suitable for textual or descriptive data where maintaining a leftward flow is intuitive. Useful when emphasizing the starting point of the content.
- `Column.Alignment.CENTER`: Ideal for numerical or categorical data where a balanced presentation is desired. Creates a visually centered display.
- `Column.Alignment.RIGHT`: Commonly used for numerical data, especially when the magnitude or precision of numbers is significant. Aligns data towards the right for a natural reading flow.

In the above example, the final column for `Cost` has been right-aligned to provide a more obvious visual distinction.

## Visibility {#visibility}

It is possible to set the visibility of a `Column`, determining whether or not it will be shown within the table. This can be useful when, among other things, determining whether or not to display sensitive information. 

Use the `setHidden()` method, as shown below, to set this property on a `Column`:

`table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);`

## Navigable {#navigable}

The navigable attribute determines whether users can interact with a column during navigation. Setting `setSuppressNavigable()` to true restricts user interaction with the column, providing a read-only experience.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Width and sizing {#width-and-sizing}

### Fixed width {#fixed-width}

Set an exact width for a column using the `setWidth()` method:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

The width property defines the desired initial width for the column. How this width is used depends on other properties and column type:

- **Regular columns**: With only width set, the column renders at the specified width but can shrink proportionally when the container is too small. The width acts as both desired and minimum width.
- **Pinned columns**: Always maintain their exact width, never participating in responsive shrinking.
- **Flex columns**: Setting width is incompatible with flex. Use either width (fixed) or flex (proportional), not both.

If not specified, the column will use its estimated width based on content analysis of the first few rows.

```java
// Get current width
float currentWidth = column.getWidth();
```

### Minimum width {#minimum-width}

The `setMinWidth()` method allows you to define the minimum width of a column. If the minimum width is not provided, the `Table` will calculate the minimum width based on the column content.

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100f);
```

The value passed represents the desired minimum width in pixels.

The minimum width property controls the smallest width a column can have:

- **Regular columns**: With only `minWidth` set, the column uses minWidth as both desired and minimum width. With width + minWidth, the column can shrink from width down to `minWidth` but no further.
- **Pinned columns**: If only `minWidth` is set (no width), it becomes the fixed width.
- **Flex columns**: Prevents the column from shrinking below this width even when container space is limited.

```java
// Get current minimum width
float minWidth = column.getMinWidth();
```

### Maximum width {#maximum-width}

The `setMaxWidth()` method limits how wide a column can grow, preventing columns with long content from becoming too wide and affecting readability:

```java
table.addColumn("Description", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

The `maxWidth` property limits column growth for all column types and will never be exceeded regardless of content, container size, or flex settings.

```java
// Get current maximum width
float maxWidth = column.getMaxWidth();
```

### Flexible sizing {#flexible-sizing}

The `setFlex()` method enables proportional column sizing, making columns share available space after fixed-width columns are allocated:

```java
// Title column gets twice the space of Artist column
table.addColumn("Title", Product::getTitle).setFlex(2f);
table.addColumn("Artist", Product::getArtist).setFlex(1f);
```

Key flex behaviors:

- **Flex value**: Determines the proportion of available space. A column with flex=2 gets twice the space of a column with flex=1.
- **Incompatible with width**: Cannot be used together with the width property. When flex is greater than zero, it takes effect over the width setting.
- **Respects constraints**: Works with `minWidth`/`maxWidth`. Without `minWidth`, flex columns can shrink to 0.

<ComponentDemo 
path='/webforj/tablecolumnflexsizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnFlexSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>

:::info Width vs Flex
Width and flex properties are mutually exclusive. Setting one automatically clears the other. Use width for precise control or flex for responsive behavior.
:::

## User interactions {#user-interactions}

### Column resizing {#column-resizing}

Column resizing gives users control over how much space each column occupies by dragging the column borders.

You can control resizing behavior on individual columns when building your table:

```java
// Enable user resizing for this column
table.addColumn("Title", Product::getTitle).setResizable(true);

// Disable resizing
table.addColumn("ID", Product::getId).setResizable(false);

// Check current state
boolean canResize = column.isResizable();
```

For tables where you want consistent behavior across multiple columns, use the bulk configuration methods:

```java
// Make all existing columns resizable
table.setColumnsToResizable(true);

// Lock all existing columns from resizing
table.setColumnsToResizable(false);
```

### Column reordering {#column-reordering}

Column reordering allows users to drag and drop columns into their preferred order, personalizing the `Table` layout for their workflow.

Configure column movement permissions when setting up your table:

```java
// Allow users to move this column
table.addColumn("Title", Product::getTitle).setMovable(true);

// Prevent column movement (useful for ID or action columns)
table.addColumn("ID", Product::getId).setMovable(false);

// Check current state
boolean canMove = column.isMovable();
```

Apply movement settings to multiple columns simultaneously:

```java
// Enable reordering for all existing columns
table.setColumnsToMovable(true);

// Disable reordering for all existing columns  
table.setColumnsToMovable(false);
```

:::note Bulk Operations
The `setColumnsToResizable()` and `setColumnsToMovable()` methods only affect existing columns at the time of invocation. They do not set defaults for future columns.
:::

The `Table` below demonstrates both column resizing and reordering interactions. Try dragging the column borders to resize them, or drag the column headers to reorder them. 

<ComponentDemo 
path='/webforj/tablecolumninteractions?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnInteractionsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>

## Automatic sizing {#automatic-sizing}

### Content-based auto-sizing {#content-based-auto-sizing}

Automatically size columns based on their content. The `Table` analyzes the data in each column and calculates the optimal width to display the content without truncation.

```java
// Auto-size all columns to fit content
table.setColumnsToAutoSize().thenAccept(ignored -> {
    // Sizing complete - columns now fit their content
});

// Auto-size specific column
table.setColumnToAutoSize("description");
```

### Proportional auto-fit {#proportional-auto-fit}

Distribute all columns proportionally across the available `Table` width, ensuring the table uses its full width with no unused space.

```java
// Fit columns to table width (equivalent to setting flex=1 on all)
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // All columns now share space equally
});
```

:::info Async Operations
Auto-sizing methods return `PendingResult<Void>` because they require client-side calculations. Use `thenAccept()` to execute code after sizing completes. If you don't need to wait for completion, you can call the methods without `thenAccept()`
:::

<ComponentDemo 
path='/webforj/tablecolumnautosizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAutoSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>

## Programmatic column movement {#programmatic-column-movement}

Move columns to specific positions programmatically:

```java
// Move column to first position
table.moveColumn("title", 0);

// Move column to last position
table.moveColumn(titleColumn.getId(), table.getColumns().size() - 1);

// Async movement with callback
table.moveColumn("description", 2).thenAccept(ignored -> {
    // Column moved successfully
});
```

:::warning Column Indexing
Column indices refer only to visible columns. Hidden columns are excluded from position calculations.
:::

## Event handling {#event-handling}

The `Table` component emits events when users interact with columns, allowing you to respond to layout changes and save user preferences.

Supported events:

- `TableColumnResizeEvent`: Fired when a user resizes a column by dragging its border.
- `TableColumnMoveEvent`: Fired when a user reorders a column by dragging its header.

You can attach listeners to these events to run logic when users modify the table layout.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Handle column resize event
  // Access: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Handle column move event  
  // Access: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```

## Label {#label}

The label of a column is its public-facing identifier, contributing to the clarity and understanding of displayed data. Use setLabel to set or modify the label associated with a column.

:::tip
By default, the label will be the same as the column ID
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

