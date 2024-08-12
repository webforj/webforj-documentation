---
title: FlexLayout
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>


webforJ provides developers with an efficient and intuitive way to layout their various applications and components - the Flex Layout. This toolset allows for items to be displayed either vertically or horizontally. 


## Constructing a Flex Layout

The FlexLayout class has three constructors and an additional construction utilizing a builder pattern:

- `FlexLayout()`
- `FlexLayout(AbstractComponent...)`
- `FlexLayout(FlexDirection, AbstractComponent...)`

Of note, the Flex Layout comes with a builder class to help streamline and simplify the creation of a layout. **It is recommended to use the builder to quickly and efficiently configure your layout**, as will be shown in examples below, though methods for the Layout class are also available to customize various attributes once the object is created. 

This builder follows a builder pattern, and is intended to allow for full customization of desired attributes of the layout to avoid needing to set individual attributes later on.

It is also possible to use the default in conjunction with the various setter methods available. The following snippet shows how to utilize the various constructors available for the FlexLayout class:

```java
//Creates a default FlexLayout
FlexLayout defaultLayout = new FlexLayout();

//Creates a default FlexLayout which one or more existing components
FlexLayout layoutWithComponent = new FlexLayout(myButton);

//Creates a FlexLayout which one or more existing components and has a specified direction
FlexLayout layoutWithDirection = new FlexLayout(FlexDirection.COLUMN, myButton);

//Creates a FlexLayout using the various methods available in the FlexLayoutBuilder
FlexLayout layoutFromBuilder = FlexLayout.create()
.horizontal()
.build();
```

## Flex Layout Properties

Flex layout's properties can be grouped into two categories: properties that apply to the items within a layout, and properties that apply to the layout itself. The flex layout, or the parent element, is a box/container that can contain one or more components. Everything inside a Flex Layout is called an item or child element. The Flex Layout provides some robust alignment capabilities, which can be achieved with the help of either container or item properties.

:::tip
webforJ's layout component follows the pattern of [CSS's flexbox layout](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). However, these tools are made to be utilized fully in Java, and do not require the application of CSS outside of the Java API methods provided.
:::

### Container Properties

Container properties will apply to all of the components within a component and not the layout itself. They will not affect the orientation or placement of the parent - only the child components within.

#### Direction

The Flex Layout will add components next to one another according to the direction chosen by the developer - either horizontal or vertical. When using he builder, utilize either the `horizontal()`, `horizontalReverse()`, `vertical()` or `verticalReverse()` methods when calling the `create()` method on a `FlexLayout` object to configure this layout as the object is created.

Alternatively, use the `setDirection()` method. The horizontal options are either `FlexDirection.ROW` (left to right) or `FlexDirection.ROW_REVERSE` (right to left), and the vertical options are either `FlexDirection.COLUMN` (top to bottom) or `FlexDirection.COLUMN_REVERSE` (bottom to top). This is done with the FlexLayout object, as opposed to the builder.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=layout_demos.container.Direction' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/container/Direction.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/flexstyles/container_styles.css'
height="275px"
/>

#### Positioning

Components that are added horizontally can also be positioned both horizontally and vertically. Use the `justify()`, `align()` and `contentAlign()` methods from the Flex Layout Builder to configure the positioning when creating a new Flex Layout.

Alternatively, on the actual FlexLayout object you can use the `setJustifyContent()` method to position items horizontally, and the `setAlignment()` method to configure vertical positioning. To modify the area around components along the cross axis (y-axis for horizontal layouts), use the `setAlignContent()` method.

:::tip
The `setAlignment()` method components how items will display along the cross axis as a whole within the container, and is effective for single-line layouts.

The `setAlignContent()` methods components the space around the cross axis, and will take effect only when a layout has multiple lines.  
:::

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=layout_demos.container.Positioning' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/container/Positioning.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/flexstyles/container_styles.css'
height="375px"
/>

#### Wrapping

To further customize the Flex Layout component, you can specify the behavior of the flex layout when components that are added no longer fit within the display. To configure this using the builder, utilize the utilize the - `nowrap()` (default), `wrap()` and `wrapReverse()` methods to configure wrapping.

Alternatively, if your layout already exists, use the `setWrap()` method to dictate how components will behave once they are no longer able to fit on a single line.

#### Spacing

In order to apply minimum spacing between items, you can set the gap property. It applies that spacing only between items not on the outer edges. 


The gap property's behavior can be thought of as a minimum distance between - this property will only take effect if it is the largest calculated
space between items. If the space between items would otherwise be larger due to another calculated property, such as due to `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, then the gap property will be ignored.

#### Flow

Flex flow, which is a combination of both the direction and the wrap properties, can be set using the `setFlow()` method on a Flex Layout object. 

:::info
To configure this property when creating the layout, use the proper directional and wrap methods. For example, to create a column wrap flow,
use the `.vertical().wrap()` combination.
:::

### Container Builder

The following demo allows you to build a container with the desired flex properties selected from the various menus. This tool can be used not only to create a visual example of the various methods, but also as a tool to create your own layouts with your desired properties. To use a layout you customize, simply copy the output code and add your desired elements for use in your program!

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=layout_demos.container.ContainerDemo' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/container/ContainerDemo.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/flexstyles/container_styles.css'
height="600px"
/>



<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
### Item Properties

Item properties will not affect any child elements within the Flex Layout, but rather the actual Layout itself. This is useful for styling a single Flex Layout element that is a child of a larger Flex Layout element independent of styles applying to all children.

#### Order

The `ItemOrder` property determines how components are displayed within the Flex Layout, and when used on a Flex Layout will assign an item that layout's specific order number. This overrides the default "source order" that is built into each item (the order in which a component is added to its parent), and means that it will be shown before items with a higher order, and after items with a lower order.

This property accepts a unitless integer value that specifies the relative order of the flex item within the container. The lower the value, the earlier the item appears in the order. For example, an item with an order value of 1 will appear before an item with an order value of 2.

:::caution
It's important to note that the order property only affects the visual order of the items within the container, not their actual position in the DOM. This means that screen readers and other assistive technologies will still read the items in the order they appear in the source code, not in the visual order.
:::

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=layout_demos.item.Order' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/item/Order.java'
height="200px"
/>

#### Self Alignment

Flex Layout's self-alignment refers to how a single Flex Layout object is aligned within its parent flex container along the cross axis, which is perpendicular to the main axis. The cross axis alignment is controlled by the `Alignment` property.

The align-self property specifies the alignment of a single flex item along the cross axis, overriding the default alignment set by the `AlignContent` property in a Flex Layout object. This allows you to align individual Flex Layout objects differently from the others in the container.

:::info
Self alignment uses the same values as content alignment
:::

This property is especially useful when you need to align a specific item differently from the other items in the container. See the sample below for an example of aligning a single item:

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=layout_demos.item.SelfAlign' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/item/SelfAlign.java'
height="350px"
/>

#### Flex Basis

`Item Basis` is a property that is used in conjunction with Flex Layout's direction to set the initial size of a flex item before any remaining space is distributed.

The `Item Basis` property specifies the default size of a flex item along the main axis, which is either horizontal (for a Row direction) or vertical (for a Column direction). This property sets the width or height of a flex item depending on the value of the flex-direction property.

:::info
By default, the `Item Basis` property is set to auto, which means that the size of the item is determined by its content. However, you can also set a specific size for the item using various units such as pixels (px), ems (em), percentages (%), or any other CSS length unit.
:::

For example, if you have a container with a total width of 500 pixels and three Flex Layout objects with `Item Basis` values of 100px, 200px, and auto respectively, the first two items will be assigned their specified sizes, while the third item will expand to fill the remaining space.

#### Flex Grow / Shrink

`Item Grow` and `Item Shrink` are two properties that work in conjunction with each other and with the `Item Basis` property to determine how flex items grow or shrink to fill the available space within a Flex Layout object.

The `Item Grow` property specifies how much the flex item can grow relative to the other items in the container. It takes a unitless value that represents a proportion of the available space that should be allocated to the item. For example, if one item has a `Item Grow` value of 1 and another has a value of 2, the second item will grow twice as much as the first item.

The `Item Shrink` property, on the other hand, specifies how much the flex item can shrink relative to the other items in the container. It also takes a unitless value that represents a proportion of the available space that should be allocated to the item. For example, if one item has a `Item Shrink` value of 1 and another has a value of 2, the second item will shrink twice as much as the first item.

When a container has more space than is needed to accommodate its contents, flex items with a `Item Grow` value greater than 0 will expand to fill the available space. The amount of space each item gets is determined by the ratio of its `Item Grow` value to the total `Item Grow` value of all items in the container.

Similarly, when a container does not have enough space to accommodate its contents, flex items with a `Item Shrink` value greater than 0 will shrink to fit the available space. The amount of space each item gives up is determined by the ratio of its `Item Shrink` value to the total `Item Shrink` value of all items in the container.


## Example Form

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=layout_demos.FlexDemo' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/FlexDemo.java'
height="500px"
/>