---
title: ColumnsLayout
---

<DocChip chip="shadow" />

<DocChip chip="name" label="dwc-columns-layout" />

<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

The `ColumnsLayout` component in webforJ allows developers to create layouts using a flexible and responsive vertical layout. This layout provides dynamic columns that adjust based on the available width. This component simplifies the creation of multi-column layouts by automatically managing breakpoints and alignments.

:::info Horizontal Layouts 
This can be used in place of, or in combination with, the [`FlexLayout`](./flex-layout) component - an equally powerful tool for horizontal layouts.
:::

## Basics

When first instantiated, the `ColumnsLayout` uses two columns to display items added to the layout. By default, it takes the full width of its parent elements and grows as needed to fit additional content. The display of added items can be further calibrated with the use of [`Breakpoint`](./columns-layout#breakpoints) and [`Alignment`](./columns-layout#vertical-and-horizontal-item-alignments) settings, which are discussed in the following sections in more detail.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="350px"
/>

## Breakpoints

At its core, the `ColumnsLayout` is designed to provide a fluid, grid-like system that adapts to the width of its parent container. Unlike traditional fixed-grid systems, this layout allows developers to specify a number of columns at a given width, and dynamically calculates the number of displayed columns based on set `Breakpoint` objects. 

This allows a `ColumnsLayout` to smoothly adapt from a more constrained space on small screens to a wider area on larger screens, offering a responsive design to a developer without needing custom implementation.

### Understanding a `Breakpoint`

A `Breakpoint` can be specified using the `Breakpoint` class, which takes three parameters:

1. **Name (optional)**:
Naming a breakpoint allows you to reference it in future configurations.

2. **Minimum width**:
Each breakpoint has a specific range that determines when its layout is applied. The minimum width is defined explicitly, and the next breakpoint determines the maximum width if it exists. You can use an integer to define the minimum width in pixels or use a `String` to specify other units such as `vw`, `%`, or `em`.

3. **Number of columns**:
Specify how many columns a breakpoint should have with this integer.


:::info `Breakpoint` evaluation
Breakpoints are evaluated in ascending order of the width, meaning the layout will use the first matching breakpoint.
:::


### Applying breakpoints

Breakpoints are applied to a `ColumnsLayout` in one of two ways: during construction, or by using the `addBreakpoint(Breakpoint)` method as shown below. 

```java
ColumnsLayout layout = new ColumnsLayout()
    // One column at widths >= 0px
    .addBreakpoint(new Breakpoint(0, 1))
    // Two columns at widths >= 600px
    .addBreakpoint(new Breakpoint(600, 2))
    // Four columns at widths >= 1200px
    .addBreakpoint(new Breakpoint(1200, 4));  
```

The demonstration below shows an example of setting multiple breakpoints at construction, using breakpoints to configure the [`Span`](#column-span-and-spans-per-breakpoint) of a component, and demonstrates the resizing capabilities of the `ColumnsLayout` when the app is resized:

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Column `Span` and spans per `Breakpoint`

Column spans in `ColumnsLayout` allow you to control how many columns an item occupies, giving you more control over the appearance of your layout at varying widths. This is especially useful when you need certain elements to take up more or less space depending on the screen size or design requirements.

### Managing column spans

By default, each item in the `ColumnsLayout` takes up exactly one column. However, you can customize this behavior by setting the column span for individual items. A span specifies the number of columns an item should occupy.

```java
Button button = new Button("Click Me");
layout.addComponent(button);
// Item spans two columns
layout.setSpan(button, 2);
```

In the above example, the button occupies two columns instead of the default one. The `setSpan()` method allows you to specify how many columns a component should span within the layout.

### Adjusting column spans with breakpoints

You can also adjust column spans dynamically based on breakpoints. This feature is useful when you want an item to span different numbers of columns depending on the screen size. For instance, you may want an element to occupy a single column on mobile devices but span multiple columns on larger screens.

```java
TextField email = new TextField("Email");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
//email field will span two columns when medium breakpoint is active
columnsLayout.setSpan(email, "medium", 2);
//...
```

This level of customization ensures that your layout remains responsive and appropriately structured across different devices.

## Placing items within columns

`ColumnsLayout` provides the ability to place items in specific columns, giving more control over the arrangement of elements. You can manually specify where an item should appear within the layout, ensuring important components display as intended.

### Basic column placement

By default, items are placed in the next available column, filling from left to right. However, you can override this behavior and specify the exact column where an item should be placed. To place an item in a specific column, use the `setColumn()` method. In this example, the button is placed in the second column of the layout, regardless of the order in which it was added relative to other components:

```java
Button button = new Button("Submit");
layout.addComponent(button);
// Place the item in the second column
layout.setColumn(button, 2);  
```

### Adjusting placement per breakpoint

Just like with column spans, you use breakpoints to adjust the placement of items based on the screen size. This is useful for reordering or relocating elements in the layout as the viewport changes.

```java
TextField email = new TextField("Email");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
//email field will appear in the second column when medium breakpoint is active
columnsLayout.setColumn(email, "medium", 2); 
//...
```

In the following demonstration, notice that when the `"medium"` breakpoint is triggered, the `email` field spans both columns, and the `confirmPassword` field is placed into the first column, rather than its default placement in the second column:

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="350px"
/>

:::tip Avoid collisions
When multiple items are placed in a layout with differing spans and/or column assignments, ensure that the combined spans and placements of items in a row don’t overlap. The layout attempts to gracefully manage spacing automatically, but careful design of spans and breakpoints prevents unintended display of items.
:::

## Vertical and horizontal item alignments

Each item in the `ColumnsLayout` can be aligned both horizontally and vertically within its column, giving control over how content is positioned inside the layout.

**Horizontal alignment** of an item is controlled using the `setHorizontalAlignment()` method. This property determines how an item aligns within its column along the horizontal axis.

**Vertical alignment** specifies how an item is positioned within its column along the vertical axis. This is useful when columns have varying heights and you want to control how items are vertically distributed. 

Available `Alignment` options include:

- `START`: Aligns the item to the left of the column (default).
- `CENTER`: Centers the item horizontally within the column.
- `END`: Aligns the item to the right of the column.
- `STRETCH`: Stretches the component to fill the layout
- `BASELINE`: Aligns based on the text or content inside the column, aligning items to the text baseline rather than other alignment options.
- `AUTO`: Auto alignment.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="350px"
/>

In the demo above, the `Submit` button has been given `ColumnsLayout.Alignment.END` to ensure that it appears at the end, or in this case to the right, of its column.

## Item spacing 

Controlling the space between columns in the `ColumnsLayout` between columns (horizontal spacing) and between rows (vertical spacing) helps developers fine-tune the layout.

To set the horizontal spacing of the layout, use the `setHorizontalSpacing()` method:

```java
// Set 20px space between columns
layout.setHorizontalSpacing(20);  
```

Similarly, use the `setVerticalSpacing()` method to configure the space between rows of the layout:

```java
// Set 15px space between rows
layout.setVerticalSpacing(15);  
```

:::tip CSS units
You can use an integer to define the minimum width in pixels or use a `String` to specify other units such as `vw`, `%`, or `em`.
:::

## Horizontal and vertical layouts

Building responsive and attractive layouts is possible using both the [`FlexLayout`](./flex-layout) component and the `ColumnsLayout` component, as well as a combination of the two. Below is a sample of the [form created in the FlexLayout](./flex-layout#example-form) article, but using a `ColumnLayout` scheme instead:

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="450px"
/>