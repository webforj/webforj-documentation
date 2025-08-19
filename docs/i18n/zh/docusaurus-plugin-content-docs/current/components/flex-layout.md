---
title: FlexLayout
sidebar_position: 45
_i18n_hash: bd3f6177603a98c20d4958a9c40dd49f
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

webforJ为开发者提供了一种高效且直观的方式来布局他们的各种应用程序和组件——Flex布局。这个工具集允许项以垂直或水平的方式显示。

## Flex布局属性 {#flex-layout-properties}

Flex布局的属性可以分为两类：适用于布局内项目的属性和适用于布局本身的属性。Flex布局，或称父元素，是一个可以容纳一个或多个组件的框/容器。Flex布局中的所有内容称为项或子元素。Flex布局提供了一些强大的对齐功能，可以通过容器或项属性来实现。

:::tip
webforJ的布局组件遵循了[CSS的flexbox布局](https://css-tricks.com/snippets/css/a-guide-to-flexbox/)模式。然而，这些工具是为了在Java中充分利用，不需要在提供的Java API方法之外应用CSS。
:::

## 容器属性 {#container-properties}

容器属性将应用于组件内的所有元素，而不是布局本身。它们不会影响父元素的方向或位置——只有子组件会受到影响。

### 方向 {#direction}

Flex布局将根据开发者选择的方向（水平或垂直）将组件并排添加。当使用构建器时，使用`horizontal()`、`horizontalReverse()`、`vertical()`或`verticalReverse()`方法在调用FlexLayout对象的`create()`方法时来配置此布局。

或者，使用`setDirection()`方法。水平选项为`FlexDirection.ROW`（从左到右）或`FlexDirection.ROW_REVERSE`（从右到左），垂直选项为`FlexDirection.COLUMN`（从上到下）或`FlexDirection.COLUMN_REVERSE`（从下到上）。这是通过FlexLayout对象完成的，而不是通过构建器。

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### 定位 {#positioning}

水平添加的组件也可以进行水平和垂直定位。在创建新的Flex布局时，使用Flex布局构建器的`justify()`、`align()`和`contentAlign()`方法来配置定位。

另外，在实际的FlexLayout对象上，您可以使用`setJustifyContent()`方法来水平定位项，使用`setAlignment()`方法来配置垂直定位。要修改组件沿交叉轴（水平布局的y轴）周围的区域，请使用`setAlignContent()`方法。

:::tip
`setAlignment()`方法控制项目如何在容器中沿交叉轴整体显示，在单行布局中效果显著。

`setAlignContent()`方法控制交叉轴周围的空间，仅在布局有多行时生效。  
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### 换行 {#wrapping}

为了进一步自定义Flex布局组件，您可以指定当添加的组件不再适合显示时，Flex布局的行为。使用构建器时，使用`nowrap()`（默认）、`wrap()`和`wrapReverse()`方法来配置换行。

或者，如果您的布局已经存在，使用`setWrap()`方法来指定当组件无法在一行中适应时的行为。

### 间距 {#spacing}

为了在项之间应用最小间距，您可以设置gap属性。它仅在项之间施加间距，而不在外缘上。

gap属性的行为可以被视为项之间的最小距离——此属性仅在其为计算的项之间的最大空间时生效。如果由于另一个已计算的属性如`setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`而项之间的空间更大，则将忽略gap属性。

### 流 {#flow}

Flex流，既是方向和换行属性的组合，可以通过Flex Layout对象上的`setFlow()`方法来设置。

:::info
要在创建布局时配置此属性，使用适当的方向和换行方法。例如，要创建列换流，使用`.vertical().wrap()`组合。
:::

### 容器构建器 {#container-builder}

以下演示允许您构建一个带有所需flex属性的容器，并从各种菜单中选择。这项工具不仅可以用于创建各种方法的视觉示例，还可以作为一个工具，以便使用所需属性创建您自己的布局。要使用自定义的布局，只需复制输出代码并添加您所需的元素以在您的程序中使用。

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

## 项目属性 {#item-properties}

项目属性不会影响Flex布局内的任何子元素，而是影响实际的布局本身。这对于独立于应用于所有子元素的样式，单独样式化一个Flex布局元素很有用。

### 顺序 {#order}

`ItemOrder`属性决定了组件在Flex布局中的显示顺序，当在Flex布局上使用时，将为该布局分配特定的顺序号。这会覆盖每个项内置的默认“源顺序”（添加组件到其父项的顺序），并意味着它将在顺序较高的项之前显示，在顺序较低的项之后显示。

此属性接受一个无单位的整数值，指定项在容器内的相对顺序。值越低，项在顺序中出现得越早。例如，具有顺序值1的项将出现在具有顺序值2的项之前。

:::caution
请注意，顺序属性仅影响容器内项目的视觉顺序，而不影响它们在DOM中的实际位置。这意味着屏幕阅读器和其他辅助技术仍会按照源代码中出现的顺序读取项，而不是按视觉顺序读取。
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### 自对齐 {#self-alignment}

Flex布局的自对齐是指单个Flex布局对象在父flex容器中的交叉轴上对齐方式，交叉轴与主轴垂直。交叉轴对齐由`Alignment`属性控制。

align-self属性指定单个flex项沿交叉轴的对齐，与Flex布局对象中的默认对齐设置相冲突。这允许您将单个Flex布局对象与容器中的其他对象不同地对齐。

:::info
自对齐使用与内容对齐相同的值
:::

当您需要不同于容器中其他项地对齐特定项时，此属性尤其有用。请查看下面的示例以了解如何对齐单个项：

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Flex基础 {#flex-basis}

`Item Basis`是一个与Flex布局的方向结合使用的属性，用于设置在分配剩余空间之前flex项的初始大小。

`Item Basis`属性指定一个flex项沿主轴（水平（行方向）或垂直（列方向））的默认大小。根据flex-direction属性的值，此属性设置flex项的宽度或高度。

:::info
默认情况下，`Item Basis`属性设为auto，这意味着项的大小由其内容决定。然而，您还可以使用不同单位（如像素（px）、em、百分比（%）或任何其他CSS长度单位）为项设置特定大小。
:::

以下演示允许您选择一个或多个框并更改所选项的`Item Basis`。

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex增长/收缩 {#flex-grow--shrink}

`Item Grow`和`Item Shrink`是两个协同工作的属性，它们与`Item Basis`属性一起决定flex项如何增长或收缩，以填充Flex布局对象内的可用空间。

`Item Grow`属性指定了flex项相对于容器中其他项的增长量。它接受一个无单位值，表示应该分配给项的可用空间的比例。例如，如果一个项的`Item Grow`值为1，而另一个为2，则第二个项将比第一个项扩展两倍。

另一方面，`Item Shrink`属性规定了flex项相对于容器中其他项的收缩量。它也接受一个无单位值，表示应该分配给项的可用空间的比例。例如，如果一个项的`Item Shrink`值为1，而另一个为2，则第二个项将比第一个项收缩两倍。

当一个容器有多余的空间来容纳其内容时，`Item Grow`值大于0的flex项将扩展以填充可用空间。每个项获得的空间量由其`Item Grow`值与容器中所有项的总`Item Grow`值的比例决定。

同样，当容器没有足够的空间来容纳其内容时，`Item Shrink`值大于0的flex项将收缩以适应可用空间。每个项放弃的空间量由其`Item Shrink`值与容器中所有项的总`Item Shrink`值的比例决定。

## 示例表单 {#example-form}
下面的表单演示了`FlexLayout`如何将输入字段组织成结构化布局。

:::tip
如果您更喜欢基于列的结构，请查看[`ColumnsLayout`](../components/columns-layout)文章中的此表单的ColumnsLayout版本，以查看其比较。
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
