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