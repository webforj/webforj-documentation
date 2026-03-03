---
title: FlexLayout
sidebar_position: 45
_i18n_hash: 5c12042a5890f07259e77e0d2111a5c6
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

`FlexLayout`组件使用CSS Flexbox模型将子组件排列为行或列。它让你控制对齐、间距、换行，以及如何扩展或收缩项目以填充可用空间。

<!-- INTRO_END -->

## Flex布局属性 {#flex-layout-properties}

Flex布局的属性可以分为两类：适用于布局内项的属性，以及适用于布局本身的属性。Flex布局，或父元素，是一个可以包含一个或多个组件的框/容器。Flex Layout内部的所有内容称为项或子元素。Flex Layout提供了一些强大的对齐功能，可以通过容器或项属性实现。

:::tip
webforJ的布局组件遵循[CSS的flexbox布局](https://css-tricks.com/snippets/css/a-guide-to-flexbox/)模式。然而，这些工具是为了在Java中充分利用，而不需要在Java API方法之外应用CSS。
:::

## 容器属性 {#container-properties}

容器属性将应用于组件内的所有组件，而不是布局本身。它们不会影响父元素的方向或位置——只影响内部的子组件。

### 方向 {#direction}

Flex Layout将根据开发者选择的方向（水平或垂直）将组件逐个添加。当使用构建器时，在调用`FlexLayout`对象上的`create()`方法时，利用`horizontal()`、`horizontalReverse()`、`vertical()`或`verticalReverse()`方法来配置此布局。

或者，使用`setDirection()`方法。水平选项为`FlexDirection.ROW`（从左到右）或`FlexDirection.ROW_REVERSE`（从右到左），垂直选项为`FlexDirection.COLUMN`（从上到下）或`FlexDirection.COLUMN_REVERSE`（从下到上）。这是通过FlexLayout对象完成的，而不是通过构建器。

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### 定位 {#positioning}

水平添加的组件也可以在水平和垂直方向上定位。使用Flex Layout Builder中的`justify()`、`align()`和`contentAlign()`方法在创建新的Flex Layout时配置定位。

或者，在实际的FlexLayout对象上，可以使用`setJustifyContent()`方法来水平定位项目，使用`setAlignment()`方法来配置垂直定位。要修改沿交叉轴的组件周围的区域（水平布局的y轴），请使用`setAlignContent()`方法。

:::tip
`setAlignment()`方法控制项目在交叉轴上的整体显示方式，适用于单行布局。

`setAlignContent()`方法控制交叉轴上的空间，只有在布局具有多行时才会生效。  
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### 换行 {#wrapping}

要进一步自定义Flex Layout组件，可以指定当添加的组件不再适合显示时，flex布局的行为。要使用构建器配置此项，请使用`nowrap()`（默认）、`wrap()`和`wrapReverse()`方法来配置换行。

或者，如果您的布局已经存在，请使用`setWrap()`方法来指示组件在无法适应单行时的行为。

### 间距 {#spacing}

为了在项目之间应用最小间距，您可以设置gap属性。它仅在项目之间应用该间距，而不在外边缘上。

gap属性的行为可以被视为最小距离——该属性只有在它是项目之间计算出的最大空间时才会生效。如果由于其他计算属性（例如`setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`）的原因，项目之间的空间更大，则将忽略gap属性。

### 流 {#flow}

Flex流，结合了方向和换行属性，可以使用`setFlow()`方法在Flex Layout对象上进行设置。

:::info
在创建布局时配置此属性，使用适当的方向和换行方法。例如，要创建一个列的换行流，可以使用`.vertical().wrap()`组合。
:::

### 容器构建器 {#container-builder}

以下演示允许您构建一个具有所需flex属性的容器，您可以从不同的菜单中选择。这种工具不仅可以创建各种方法的可视示例，还可以作为工具来创建您自己的布局，自定义属性。要使用您自定义的布局，只需复制输出代码，并添加您希望在程序中使用的元素。

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>



<!-- 大代码片段显示容器 -->
## 项属性 {#item-properties}

项属性不会影响Flex Layout内的任何子元素，而是实际布局本身。这对于样式化是一个更大Flex Layout元素的子元素的单个Flex Layout元素是非常有用的。

### 顺序 {#order}

`ItemOrder`属性决定了组件在Flex Layout中的显示顺序，使用在Flex Layout上时，将分配该布局的特定顺序号。这将覆盖内置于每个项中的默认“源顺序”（组件添加到其父级的顺序），意味着它会在具有更高顺序的项之前显示，并在具有更低顺序的项之后显示。

此属性接受一个无单位的整数值，指定flex项在容器内的相对顺序。值越小，项出现在顺序中的位置就越早。例如，顺序值为1的项会出现在顺序值为2的项之前。

:::caution
值得注意的是，顺序属性仅影响容器内项的视觉顺序，而不影响它们在DOM中的实际位置。这意味着屏幕阅读器和其他辅助技术仍将根据源代码中的实际顺序读取这些项，而不是视觉顺序。
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### 自我对齐 {#self-alignment}

Flex Layout的自我对齐指的是单个Flex Layout对象在其父flex容器内沿交叉轴的对齐方式，该轴与主轴垂直。交叉轴的对齐由`Alignment`属性控制。

align-self属性指定单个flex项沿交叉轴的对齐方式，覆盖在Flex Layout对象中由`AlignContent`属性设置的默认对齐方式。这使您能够将单个Flex Layout对象与容器中的其他对象不同地对齐。

:::info
自我对齐使用与内容对齐相同的值
:::

当您需要使特定项与容器中的其他项不同对齐时，这个属性特别有用。请参见下面的示例以了解如何对齐单个项：

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### 弹性基础 {#flex-basis}

`Item Basis`是一个属性，它与Flex Layout的方向结合使用，以设置flex项的初始大小，然后再分配任何剩余空间。

`Item Basis`属性指定沿主轴的flex项的默认大小，主轴可以是水平（对于行方向）或垂直（对于列方向）。此属性根据flex-direction属性的值设置flex项的宽度或高度。

:::info
默认情况下，`Item Basis`属性设置为auto，这意味着该项的大小由其内容决定。然而，您也可以使用像像素（px）、em、百分比（%）或任何其他CSS长度单位等各种单位为该项设置特定大小。
:::

以下演示允许您选择一个或多个框，并更改所选项的`Item Basis`。

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### 弹性增长/收缩 {#flex-grow--shrink}

`Item Grow`和`Item Shrink`是两个相互配合工作的属性，与`Item Basis`属性共同决定flex项如何增长或收缩，以填充Flex Layout对象中的可用空间。

`Item Grow`属性指定flex项相对于容器中其他项的增长程度。它取一个无单位的值，表示应分配给该项的可用空间的比例。例如，如果一个项的`Item Grow`值为1，而另一个值为2，则第二个项将增长两倍于第一个项。

另一方面，`Item Shrink`属性指定flex项相对于容器中其他项的收缩程度。它也取一个无单位的值，表示应分配给该项的可用空间的比例。例如，如果一个项的`Item Shrink`值为1，而另一个值为2，则第二个项将收缩两倍于第一个项。

当一个容器的空间超出容纳其内容的需求时，具有`Item Grow`值大于0的flex项将扩展以填充可用空间。每个项分配的空间量由其`Item Grow`值与容器中所有项的总`Item Grow`值的比例决定。

类似地，当容器没有足够的空间容纳其内容时，具有`Item Shrink`值大于0的flex项将收缩以适应可用空间。每个项放弃的空间量由其`Item Shrink`值与容器中所有项的总`Item Shrink`值的比例决定。


## 示例表单 {#example-form}
下面的表单演示了`FlexLayout`是如何将输入字段组织成结构化布局的。

:::tip
如果你更喜欢基于列的结构，请查看[`ColumnsLayout`](../components/columns-layout)文章中的此表单的ColumnsLayout版本，以了解其比较。
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
