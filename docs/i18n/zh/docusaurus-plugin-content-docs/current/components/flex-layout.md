---
title: FlexLayout
sidebar_position: 45
description: >-
  Arrange children in rows or columns with the FlexLayout component, controlling
  direction, justification, alignment, wrapping, and growth.
_i18n_hash: cd16392e244062d863d403e50cc56ddd
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

`FlexLayout` 组件使用 CSS Flexbox 模型以行或列的方式排列子组件。它使您能够控制对齐、间距、换行以及如何使项目增长或缩小以填充可用空间。

<!-- INTRO_END -->

## `FlexLayout` 属性 {#flex-layout-properties}

`FlexLayout` 属性可以分为两类：适用于布局中项的属性和适用于布局本身的属性。`FlexLayout` 或父元素是一个可以容纳一个或多个组件的框/容器。`FlexLayout` 内部的所有内容称为项或子元素。`FlexLayout` 提供了一些对齐功能，这些功能可以通过容器属性或项属性来实现。

:::tip
`FlexLayout` 组件遵循 [CSS 的 flexbox 布局](https://css-tricks.com/snippets/css/a-guide-to-flexbox/) 的模式。然而，`FlexLayout` 是为在 Java 中完全使用而设计的，不需要在提供的 Java API 方法之外使用 CSS。
:::

## 容器属性 {#container-properties}

容器属性将应用于组件内的所有项，而不是布局本身。它们不会影响父元素的方向或放置，仅影响内部的子组件。

### 方向 {#direction}

`FlexLayout` 根据其方向（水平或垂直）将组件并排添加。当使用构建器时，将 `horizontal()`、`horizontalReverse()`、`vertical()` 或 `verticalReverse()` 方法与 `FlexLayout.create()` 方法链式调用，以便在创建对象时配置布局。

要在现有的 `FlexLayout` 对象上设置方向，请使用 `setDirection()` 方法。水平选项是 `FlexDirection.ROW`（从左到右）或 `FlexDirection.ROW_REVERSE`（从右到左），而垂直选项是 `FlexDirection.COLUMN`（从上到下）或 `FlexDirection.COLUMN_REVERSE`（从下到上）。

<ComponentDemo
path='/webforj/flexdirection'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='275px'
/>

### 定位 {#positioning}

水平添加的组件也可以在水平和垂直方向上进行定位。使用 `FlexLayout` Builder 的 `justify()`、`align()` 和 `contentAlign()` 方法在创建新的 `FlexLayout` 时配置定位。

或者，在实际的 `FlexLayout` 对象上，可以使用 `setJustifyContent()` 方法来水平方向定位项，使用 `setAlignment()` 方法来配置垂直定位。要修改沿交叉轴（对于水平布局而言，是 y 轴）项目周围的区域，请使用 `setAlignContent()` 方法。

:::tip
`setAlignment()` 方法控制项如何在容器的交叉轴上整体显示，并对单行布局有效。

`setAlignContent()` 方法控制交叉轴周围的空间，只有在布局有多行时才会生效。
:::

<ComponentDemo
path='/webforj/flexpositioning'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='375px'
/>

### 换行 {#wrapping}

为进一步自定义 `FlexLayout` 组件，您可以指定当添加的组件不再适合显示时的行为。使用构建器配置此行为时，使用 `nowrap()`（默认）、`wrap()` 和 `wrapReverse()` 方法来配置换行。要在现有的 `FlexLayout` 对象上配置此项，请使用 `setWrap()` 方法。

### 间距 {#spacing}

为了在项目之间应用最小间距，您可以设置 `gap` 属性。它仅在项目之间应用该间距，而不在外部边缘上应用。

间距属性的行为可以被视为项目之间的最小距离，因此只有在它是项目之间计算出的最大空间时才会生效。如果由于另一个计算属性（例如由于 `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`）的影响，项目之间的空间大于计算出的间距，则间距属性将被忽略。

### 流动 {#flow}

Flex 流动是方向和换行属性的组合，可以在 `FlexLayout` 对象上使用 `setFlow()` 方法进行设置。

:::info
要在创建布局时配置此属性，请使用正确的方向和换行方法。例如，要创建列换行流，使用 `.vertical().wrap()` 组合。
:::

### 容器构建器 {#container-builder}

以下演示允许您从各种菜单中选择所需的 flex 属性来构建容器。此工具不仅可以用于创建各种方法的视觉示例，还可以创建具有所需属性的自定义布局。要使用自定义的布局，只需复制输出代码并添加所需元素以在您的程序中使用。

<ComponentDemo
path='/webforj/flexcontainerbuilder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='600px'
/>


<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## 项属性 {#item-properties}

项属性不会影响 `FlexLayout` 内的任何子元素，而影响实际的布局本身。这对于独立于应用于所有子项的样式对单个 `FlexLayout` 元素进行样式设置非常有用。

### 顺序 {#order}

`ItemOrder` 属性决定了组件在 `FlexLayout` 中的显示顺序，并且在 `FlexLayout` 上使用时将分配该布局的特定顺序号。这将覆盖每个项内置的默认“源顺序”（即将组件添加到其父项的顺序），并意味着它将在具有更高顺序的项之前显示，而在具有更低顺序的项之后显示。

此属性接受一个无单位的整数值，指定弹性项在容器内的相对顺序。值越小，项在顺序中出现得越早。例如，顺序值为 1 的项将在顺序值为 2 的项之前出现。

:::caution
重要的是要注意，顺序属性仅影响容器内项的视觉顺序，而不影响它们在 DOM 中的实际位置。这意味着屏幕阅读器和其他辅助技术将仍然按照源代码中出现的顺序读取项，而不是视觉顺序。
:::

<ComponentDemo
path='/webforj/flexorder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='320px'
/>

### 自我对齐 {#self-alignment}

`FlexLayout` 的自我对齐是指单个 `FlexLayout` 对象在交叉轴上如何在其父弹性容器内对齐，交叉轴与主轴垂直。交叉轴对齐由 `Alignment` 属性控制。

align-self 属性指定沿交叉轴单个弹性项的对齐，覆盖 `FlexLayout` 对象中由 `AlignContent` 属性设置的默认对齐方式。这允许您将单个 `FlexLayout` 对象与容器中的其他对象不同地对齐。

:::info
自我对齐使用与内容对齐相同的值。
:::

当您需要将特定项与容器中的其他项不同地对齐时，此属性特别有用。请查看下面的示例以了解对齐单个项的示例：

<ComponentDemo
path='/webforj/flexselfalign'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='350px'
/>

### 弹性基础 {#flex-basis}

`Item Basis` 是与 `FlexLayout` 方向一起使用的属性，用于设置分配剩余空间之前弹性项的初始大小。

`Item Basis` 属性指定沿主轴（对于行方向为水平， 对于列方向为垂直）弹性项的默认大小。此属性根据弹性方向属性设置弹性项的宽度或高度。

:::info
默认情况下，`Item Basis` 属性设置为 `auto`，这意味着项的大小由其内容决定。然而，您也可以使用各种单位（如像素（px）、ems（em）、百分比（%）或任何其他 CSS 长度单位）为项目设置特定大小。
:::

以下演示允许您选择一个或多个框并更改选定项的 `Item Basis`。

<ComponentDemo
path='/webforj/flexbasis'
files={['src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java']}
height='300px'
/>

### 弹性增长和缩小 {#flex-grow--shrink}

`Item Grow` 和 `Item Shrink` 是两个相互配合使用的属性，配合 `Item Basis` 属性一起确定弹性项如何在 `FlexLayout` 对象内增长或缩小以填充可用空间。

`Item Grow` 属性指定弹性项相对容器内其他项的增长程度。它接受一个无单位的值，表示应分配给该项的可用空间的比例。例如，如果一个项的 `Item Grow` 值为 1，另一个项的值为 2，则第二个项的增长速度是第一个项的两倍。

另一方面，`Item Shrink` 属性指定弹性项相对容器内其他项的缩小程度。它也接受一个无单位的值，表示应分配给该项的可用空间的比例。例如，如果一个项的 `Item Shrink` 值为 1，另一个项的值为 2，则第二个项的缩小速度是第一个项的两倍。

当容器有比其内容所需的更多空间时，`Item Grow` 值大于 0 的弹性项将扩展以填充可用空间。每个项获得的空间量由其 `Item Grow` 值与容器内所有项的总 `Item Grow` 值的比例决定。

类似地，当容器没有足够的空间来容纳其内容时，`Item Shrink` 值大于 0 的弹性项将缩小以适应可用空间。每个项放弃的空间量由其 `Item Shrink` 值与容器内所有项的总 `Item Shrink` 值的比例决定。

## 示例表单 {#example-form}
下面的表单演示了 `FlexLayout` 如何将输入字段组织成结构化布局。

:::tip
如果您更喜欢基于列的结构，请查看 [`ColumnsLayout`](../components/columns-layout) 文章中的此表单的 `ColumnsLayout` 版本，以了解其对比。
:::

<ComponentDemo
path='/webforj/flexlayout'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java',
  'src/main/frontend/css/flexlayout/flexLayout.css',
]}
height='620px'
/>
