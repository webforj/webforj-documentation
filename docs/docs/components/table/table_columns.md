---
sidebar_position: 5
title: Columns
slug: columns
sidebar_class_name: new-content
---

<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

The `Table` class uses column instances to define and customize how data is displayed. Columns control what data is shown, how it looks, and how users can interact with it. This page covers column identity, presentation, sizing, user interactions, and related events.

## Column identity {#column-identity}

A column’s identity defines how it's recognized in the `Table`. This includes its label, the value it provides, and whether it's visible or navigable.

### Label {#label}

The label of a column is its public-facing identifier, helping clarify displayed data.  

Use `setLabel()` to set or modify the label.

:::tip
By default, the label will be the same as the column ID.
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

### Value providers {#value-providers}

A value provider is a function responsible for translating raw data from the underlying dataset into a format suitable for display within a specific column. The function, that you define, takes an instance of the row data type (T) and returns the value to be showcased in the associated column for that particular row.

To set a value provider on a column, use one of the `addColumn()` methods from the `Table` component.

In the following snippet, a column will attempt to access data from a JSON object, rendering it only if the data isn't null.

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

### Visibility {#visibility}

It's possible to set a column's visibility, determining whether or not it will be shown within the `Table`. This can be useful when, among other things, determining whether or not to display sensitive information. 

```java
table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);
```

### Navigable {#navigable}

The navigable attribute determines whether users can interact with a column during navigation. Setting `setSuppressNavigable()` to true restricts user interaction with the column, providing a read-only experience.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Layout and formatting {#layout-and-formatting}

After establishing a column’s identity, the next step is to control how its content appears to users. Layout options such as alignment and pinning determine where data sits and how it stays visible as you work with a `Table`.

### Alignment {#alignment}

Setting a column’s alignment lets you create organized tables, which can help users identify the different sections in the `Table`.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

The `Table` Component supports three alignment options:

- `Column.Alignment.LEFT`: Suitable for textual or descriptive data where maintaining a leftward flow is intuitive. Useful when emphasizing the starting point of the content.
- `Column.Alignment.CENTER`: Center-aligned columns are ideal for shorter values, like a character key, status, or anything else that has balanced presentation.
- `Column.Alignment.RIGHT`: Consider using a right-aligned column for numerical values that are helpful to quickly scan through, such as dates, amounts, and percentages.

In the preceding example, the final column for `Cost` has been right-aligned to provide a more obvious visual distinction.

### Pinning {#pinning}

Column pinning is a feature that allows users to affix or "pin" a column to a specific side of the `Table`. This is useful when certain columns, such as identifiers or essential information, need to remain visible while scrolling horizontally through a table.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

There are three available directions for pinning a column:

- `PinDirection.LEFT`: Pins the column to the left side.
- `PinDirection.RIGHT`: Pins the column to the right side.
- `PinDirection.AUTO`: Column appears based on the insertion order.

Pinning can be set programmatically, allowing you to change the pin direction based on user interactions or by the app's logic.

## Column sizing <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Fixed width {#fixed-width}

Set an exact width for a column using the `setWidth()` method, specifying the desired width in pixels:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

The width property defines the desired initial width for the column. How this width is used depends on other properties and column type:

- **Regular columns**: With only width set, the column renders at the specified width but can shrink proportionally when the container is too small. The original width serves as the desired width, but without explicit minimum constraints, the column can render smaller than the set width.
- [**Pinned columns**](#pinning): Always maintain their exact width, never participating in responsive shrinking.
- [**Flex columns**](#flex-sizing): Setting width is incompatible with flex. Use either width (fixed) or flex (proportional), not both.

If not specified, the column will use its estimated width based on content analysis of the first few rows.

```java
// Get current width
float currentWidth = column.getWidth();
```

### Minimum width {#minimum-width}

The `setMinWidth()` method allows you to define the minimum width of a column. If the minimum width isn't provided, the `Table` will calculate the minimum width based on the column content.

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100f);
```

The value passed represents the minimum width in pixels.

The minimum width property controls the smallest width a column can have:

- **Regular columns**: With only minimum width set, the column uses the minimum width as both desired and minimum width. With width + minimum width, the column can shrink from the width down to the minimum width but no further.
- [**Pinned columns**](#pinning): If only minimum width is set (no width), it becomes the fixed width.
- [**Flex columns**](#flex-sizing): Prevents the column from shrinking below this width even when container space is limited.

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

### Flex sizing {#flex-sizing}

The `setFlex()` method enables proportional column sizing, making columns share available space after fixed-width columns are allocated:

```java
// Title column gets twice the space of the Artist column
table.addColumn("Title", Product::getTitle).setFlex(2f);
table.addColumn("Artist", Product::getArtist).setFlex(1f);
```

Key flex behaviors:

- **Flex value**: Determines the proportion of available space. A column with flex=2 gets twice the space of a column with flex=1.
- **Incompatible with width**: Can't be used together with the width property. When flex is greater than zero, it takes effect over the width setting.
- **Respects constraints**: Works with minimum width/maximum width constraints. Without minimum width, flex columns can shrink to 0.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnflexsizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnFlexSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

:::info Width vs Flex
Width and flex properties are mutually exclusive. Setting one automatically clears the other. Use width for precise control or flex for responsive behavior.
:::

### Automatic sizing {#automatic-sizing}

Beyond manual width and flex settings, columns can also be sized automatically. Automatic sizing lets the `Table` determine optimal widths either by analyzing content or by distributing space proportionally.

#### Content-based auto-sizing {#content-based-auto-sizing}

Automatically size columns based on their content. The `Table` analyzes the data in each column and calculates the optimal width to display the content without truncation.

```java
// Auto-size all columns to fit content
table.setColumnsToAutoSize().thenAccept(c -> {
    // Sizing complete - columns now fit their content
});

// Auto-size specific column
table.setColumnToAutoSize("description");
```

#### Proportional auto-fit {#proportional-auto-fit}

Distribute all columns proportionally across the available `Table` width. This operation sets each column to flex=1, making them share the total `Table` width equally, regardless of their content length. Columns will expand or contract to fill the exact `Table` dimensions with no remaining space.

```java
// Fit columns to table width (equivalent to setting flex=1 on all)
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // All columns now share space equally
});
```

:::info Async Operations
Auto-sizing methods return `PendingResult<Void>` because they require client-side calculations. Use `thenAccept()` to execute code after sizing completes. If you don't need to wait for completion, you can call the methods without `thenAccept()`
:::

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnautosizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAutoSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

## User interactions <DocChip chip='since' label='25.03' /> {#user-interactions}

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
The `setColumnsToResizable()` and `setColumnsToMovable()` methods only affect existing columns at the time of invocation. They don't set defaults for future columns.
:::

### Programmatic column movement {#programmatic-column-movement} 

In addition to drag-and-drop, you can also reposition columns programmatically by index or ID. Keep in mind that the index is based only on visible columns; any hidden columns are ignored when calculating positions.

```java
// Move column to first position
table.moveColumn("title", 0);

// Move column to last position
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Async movement with callback
table.moveColumn("description", 2).thenAccept(c -> {
    // Column moved successfully
});
```

## Event handling {#event-handling}

The `Table` component emits events when users interact with columns, allowing you to respond to layout changes and save user preferences.

Supported events:

- `TableColumnResizeEvent`: Fired when a user resizes a column by dragging its border.
- `TableColumnMoveEvent`: Fired when a user reorders a column by dragging its header.

You can attach listeners to the `Table` to respond when users modify the table layout.

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