---
sidebar_position: 10
title: Layouts
---

The DWCJ provides tools for users to create a variety of layouts with built-in API methods. These tools are
made to be utilized fully in Java, and do not require the application of CSS outside of the Java API methods provided.

:::info
The DWCJ's layout component follows the pattern of [CSS's flexbox layout](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). It is recommended
to review this resource if you are unfamiliar with CSS flexbox.
:::

To create a basic FlexLayout control, implement code similar to the following:

<!-- Insert code snippet here -->
<!-- *Code Snippet* -->

## Horizontal Layout

The horizontal layout option will add controls next to one another horizontally, or along the x-axis. Use the `setDirection()` method with either
`FlexDirection.ROW` (left to right) or `FlexDirection.ROW_REVERSE` (right to left) to position items as they're added along the x-axis. 

Alternatively, use either the `horizontal()` or `horizontalReverse()` option when calling the `create()` method on a `FlexLayout` object to configure this layout
as the object is created.

<!-- Code demo inserted here showing how to create this layout -->
<!-- *Code Snippet* -->

### Positioning

Controls that are added horizontally can also be positioned both horizontally and vertically. Use the `setJustifyContent()` method to position items horizontally, and the `setAlignment()` method to configure vertical positioning. To modify the area around controls along the cross axis (y-axis for horizontal layouts), use the 
`setAlignContent()` method.

:::note
The `setAlignment()` method controls how items will display along the cross axis as a whole within the container, and is effective for single-line layouts.

The `setAlignContent()` methods controls the space around the cross axis, and will take effect only when a layout has multiple lines.  
:::

These can also be configured when creating a `FlexLayout` using the `justify()`, `align()` and `contentAlign()` methods with their respective options.

<!-- Add demo that shows how to do this -->
<!-- *Code Snippet* -->

### Wrapping

To further customize the Flex Layout control, utilize the `setWrap()` method to dictate how controls will behave once they are no longer able to fit on a single line.

When creating the layout, utilize the - `nowrap()` (default), `wrap()` and `wrapReverse()` methods to configure wrapping.

<!-- Add demo that shows how to do this -->
<!-- *Code Snippet* -->


### Flow

Flex flow, which is a combination of both the direction and the wrap properties, can be set using the `setFlow()` method. 

:::note
To configure this property when creating the layout, use the proper directional and wrap methods. For example, to create a column wrap flow,
use the `.vertical().wrap()` methods.
:::

<!-- Add demo that shows how to do this -->
*Code Snippet*

### Gap

The gap property explicitly controls the minimum space between items. It applies that spacing only between items not on the outer edges. 

:::note
The gap property's behavior can be thought of as a minimum distance between - this property will only take effect if it is the largest calculated
space between items. If the space between items would otherwise be larger due to another calculated property, such as due to `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, then the gap property will be ignored.
:::

<!-- Add demo that shows how to do this -->
<!-- *Code Snippet* -->

<!-- ## Vertical Layout




### Positioning


### Flex Wrap

### Flex Direction

### Flex Flow

## Item Configuration
 -->































features two basic layout components: Vertical Layout and Horizontal Layout. As their names suggest, they render their contents vertically and horizontally, respectively. Components are shown in the order they are added to either layout.

Vertical Layout
Vertical Layout places components top-to-bottom in a column. By default, it has 100% width and undefined height, meaning its width is constrained by its parent component and its height is determined by the components it contains.

Open in a
new tab
Show code
Components in a Vertical Layout can be aligned both vertically and horizontally.

Vertical Alignment
You can position components at the top, middle, or bottom. Alternatively, you can position components by specifying how a layout’s excess space (if any) is distributed between them.

Open in a
new tab
Vertical alignment
 
 
 
 
 
Show code
Value	Description
START (default)

Positions items at the top.

CENTER

Vertically centers items.

END

Positions items at the bottom.

BETWEEN

Available space is distributed evenly between items. No space is added before the first item, or after the last.

AROUND

Available space is distributed evenly between items. The space before the first item, and after the last, is half of that between items.

EVENLY

Available space is distributed evenly between items. The space before the first item, between items and after the last item is the same.

Horizontal Alignment
Components in a Vertical Layout can be left-aligned (default), centered, right-aligned or stretched horizontally.

Open in a
new tab
Horizontal alignment
 
 
 
Show code
Value	Description
START (default)

Left-aligns (in left-to-right languages) or right-aligns (in right-to-left languages) items

CENTER

Horizontally centers items

END

Right-aligns (in left-to-right languages) or left-aligns (in right-to-left languages) items

STRETCH

Stretches items with undefined width horizontally

It’s also possible to horizontally align individual components, overriding the alignment set by the parent layout.

Open in a
new tab
Layout alignment
 
 
 
 
Item 1: alignment
 
 
 
 
Show code
Horizontal Layout
Horizontal Layout places components side-by-side in a row. By default, it has undefined width and height, meaning its size is determined by the components it contains.

Open in a
new tab
Show code
Like Vertical Layout, Horizontal Layout enables both vertical and horizontal alignment of components.

Vertical Alignment
You can position components at the top, middle, or bottom. Items can alternatively be made to stretch vertically or be positioned along the layout’s text baseline.

Open in a
new tab
Show code
Value	Description
STRETCH (default)

Stretches items with undefined height horizontally

START

Positions items at the top of the layout

CENTER

Vertically centers items

END

Positions items at the bottom of the layout

BASELINE

Position items along the layout’s (text) baseline.

It’s also possible to vertically align individual components, overriding the alignment set by the parent layout.

Open in a
new tab
Show code
Horizontal Alignment
Components in a Horizontal Layout can be left-aligned, centered or right-aligned. Alternatively, you can position components by specifying how a layout’s excess space is distributed between them.

Open in a
new tab
Show code
Value	Description
START (default)

Left-aligns (in left-to-right languages) or right-aligns (in right-to-left languages) items

END

Right-aligns (in left-to-right languages) or left-aligns (in right-to-left languages) items

CENTER

Horizontally centers items

BETWEEN

Available space is distributed evenly between items. No space is added before the first item, or after the last.

AROUND

Available space is distributed evenly between items. The space before the first item, and after the last, is half of that between items.

EVENLY

Available space is distributed evenly between items. The space before the first item, between items and after the last item is the same.

Spacing
Spacing is used to create space between components in the same layout. Spacing can help prevent mis-clicks and distinguish content areas.

Open in a
new tab
Show code
Five different spacing theme variants are available:

Open in a
new tab
Show code
Theme variant	Usage recommendations
spacing-xs

Extra-small space between items

spacing-s

Small space between items

spacing

Medium space between items

spacing-l

Large space between items

spacing-xl

Extra-large space between items

Padding
Padding is the space between a layout’s outer border and its content. Padding can help distinguish a layout’s content from its surroundings. Padding is applied using the padding theme variant.

Open in a
new tab
Show code
Margin
Use margin to create space around a layout.

Open in a
new tab
Show code
Expanding Items
Components can be made to expand and take up any excess space a layout may have.

Open in a
new tab
Show code
When multiple components expand, they do so relative to each other. For example, having two items with expand ratios of 2 and 1 results in the first item taking up twice as much of the available space as the second item.

Components
Badge
Components
Board
Updated 2023-03-08
Edit this page on GitHub
Was this page helpful?
Leave a comment or ask a question, or share your own code examples. You can also join the chat on Discord or ask questions on StackOverflow.


Online
