---
title: FlexWrap your mind around webforJ's FlexLayout
description: How to use FlexLayout
date: 2025-08-19
authors: Garrison Osteen
tags: [webforJ, FlexLayout, FlexBox, Responsive Design, CSS, Web Development]
---

One of the great benefits of web apps is gaining access to the powerful styling and layout capabilities of CSS. 
In particular, CSS lets you create responsive layouts, allowing your apps to smoothly adjust their layout according to parameters like window size and device type. 
Apps with responsive layouts look and feel better, and they allow users to use your app in their preferred context and size.
With webforJ, you have that power in Java!

But, knowing what to do with that power is another story. 
Perhaps you're used to carefully laying out forms with a rigid structure, and the prospect of creating designs that are adaptable and dynamic seems intimidating and complicated. 
So where do you start? With webforJ's `FlexLayout`!

<!-- truncate -->

## What is `FlexLayout`?
The `FlexLayout` component is a powerful layout tool based on CSS FlexBox. 
It can create responsive layout designs that adapt to the available space and number of elements in a natural way. 
`FlexLayout` shares its terminology and conceptual structure with FlexBox, so if you're a seasoned CSS pro, your existing knowledge will apply just as well in webforJ.

### Translating from Flexbox to `FlexLayout`
So, if webforJ's `FlexLayout` provides the same functionality as CSS Flexbox, how does one translate to the other?
If you've used Flexbox before, your experience will translate directly to `FlexLayout`, with slightly different syntax. 
Instead of writing CSS like `justify-content: space-between;` you will write the Java code `layout.SetJustifyContent(FlexJustifyContent.BETWEEN);`.

| Function | CSS Flexbox | webforJ `FlexLayout` |
|----------|-------------|-------------------|
| **Flex Container** | An HTML element with the `display` attribute set to `flex`. | A `FlexLayout` component, created with the `FlexLayoutBuilder`. |
| **Items** | Child elements of the container are items in the container. | Add components with the `add(component)` method, or create the `FlexLayout` with a list of components to include. |
| **Properties and Values** | Set CSS properties on the container or items, using values like `space-evenly` or `wrap` | Use FlexLayout methods to set specific properties, using enums like `FlexJustifyContent.EVENLY` or `FlexWrap.WRAP` |

### Creating a FlexLayout
To create a FlexLayout, use the `FlexLayout.create()` method to create a `FlexLayoutBuilder`, and use the `build()` method to build it:
`FlexLayout flex = FlexLayout.create().build();`
When creating a `FlexLayout`, you can set some properties right away with methods like `wrap()`, `horizontal()`, `vertical()`, and more. 
See the [FlexLayoutBuilder JavaDocs](https://javadoc.io/doc/com.webforj/webforj-flexlayout/latest/com/webforj/component/layout/flexlayout/FlexLayoutBuilder.html) for a full list of methods.

### Populating your `FlexLayout`
To add a component to a `FlexLayout`, just use the `add()` method:

`flex.add(new Button("A button in a FlexLayout"));`

You can also add all the components right away when you create the `FlexLayout` by supplying a list of components in the constructor.
## `FlexLayout` Configuration Cheatsheet: The Container
Configuration is where the real power of `FlexLayout` shines, because different configurations will yield very different behavior. 
It can sometimes be difficult to wrap your head around the various configuration options, so visual cheatsheets are an invaluable resource. 
Luckily, that's exactly what we have here!

When configuring a `FlexLayout`, you can set properties on the `FlexLayout` itself (the container) or on the individual items within it. 
This post focuses on configuring the `FlexLayout` container. 
A `FlexLayout` has a main axis and a cross axis, and you can configure them independently.

### The main axis
The main axis is the primary axis on which the items in a `FlexLayout` appear, and can be either horizontal (row) or vertical (column), depending on the direction setting. 
You can control whether items will wrap to a new line with `setWrap()`, which corresponds to the CSS property `flex-wrap`. 
When items wrap, they create a new main axis line in the direction of the cross axis.

#### `setDirection()`
You can control the direction of the main axis with `setDirection()`, which corresponds to the CSS property `flex-direction`. 
The image below shows the four possible values of the `FlexDirection` enum. 
Each series of boxes is a `FlexLayout`, and the number corresponds to the order the element was added. 
For ROW and COLUMN, the items appear in order from left to right or top to bottom, but for ROW_REVERSE and COLUMN_REVERSE, they are reversed:

#### `setJustifyContent()`
You can control the alignment of items along the main axis with `setJustifyContent()` (CSS property `justify-content`). 
The image below shows all the possible values of the `FlexJustifyContent` enum in horizontal `FlexLayout`. 
Bear in mind that in a column layout, this would control the vertical alignment of items instead of the horizontal alignment.

### The cross axis
The cross axis is perpendicular to the main axis, so it is either horizontal or vertical, whichever is the opposite of the direction set on the main axis. 

#### `setAlignment()`
You can control how items are aligned along the cross axis with `setAlignment()` (CSS property `align-items`). 
The following image shows all the possible values of the `FlexAlignment` enum for items of varying sizes with a horizontal main axis and vertical cross axis. 
Notice how the BASELINE alignment depends on text size, because the items are aligned according to the text baseline.

#### `setAlignContent()`
If wrapping is enabled, you may have multiple lines of content, each with their own main and cross axes. 
To control the alignment of the lines of content, use FlexLayout.`setAlignContent()`, which corresponds to the CSS property `align-content`. 
In the image below, every value of the `FlexContentAlignment` enum is used to arrange three lines of items within a horizontal `FlexLayout` with wrapping enabled.

## Summary
By using `setDirection()` and `setJustifyContent()` on the main axis, and `setAlignment()` and `setAlignContent()` on the cross axis, you can exert a great deal of control over the content in your app, while letting `FlexLayout` handle the complications of positioning and sizing automatically.

