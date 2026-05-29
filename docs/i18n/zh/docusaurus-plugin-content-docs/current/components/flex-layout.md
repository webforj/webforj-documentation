---
title: FlexLayout
sidebar_position: 45
_i18n_hash: cf7ba76f1e13488c6fa3a419ba6ceaca
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

`FlexLayout` 组件使用 CSS Flexbox 模型排列子组件，允许您控制对齐、间距、换行以及如何让项目增长或缩小以填充可用空间。

<!-- INTRO_END -->

## `FlexLayout` 属性 {#flex-layout-properties}

`FlexLayout` 属性可以分为两类：适用于布局内项目的属性和适用于布局本身的属性。`FlexLayout` 作为父元素，是一个可以包含一个或多个组件的框/容器。`FlexLayout` 内部的所有内容被称为项目或子元素。`FlexLayout` 提供了一些对齐功能，这可以通过容器或项目属性的帮助来实现。

:::tip
`FlexLayout` 组件遵循 [CSS 的 flexbox 布局](https://css-tricks.com/snippets/css/a-guide-to-flexbox/) 模式。然而，`FlexLayout` 旨在完全在 Java 中使用，无需在 Java API 方法之外使用 CSS。
:::

## 容器属性 {#container-properties}

容器属性将应用于组件中的所有组件，而不是布局本身。它们不会影响父组件的方向或位置，只会影响内部的子组件。

### 方向 {#direction}

`FlexLayout` 根据方向（水平或垂直）将组件添加到彼此旁边。在使用构建器时，请将 `horizontal()`、`horizontalReverse()`、`vertical()` 或 `verticalReverse()` 方法与 `FlexLayout.create()` 方法链式调用，以在对象创建时配置布局。

要在现有的 `FlexLayout` 对象上设置方向，请使用 `setDirection()` 方法。水平选项为 `FlexDirection.ROW`（从左到右）或 `FlexDirection.ROW_REVERSE`（从右到左），垂直选项为 `FlexDirection.COLUMN`（从上到下）或 `FlexDirection.COLUMN_REVERSE`（从下到上）。 

<ComponentDemo
path='/webforj/flexdirection'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='275px'
/>

### 定位 {#positioning}

水平添加的组件也可以在水平和垂直方向上定位。使用 `FlexLayout` Builder 的 `justify()`、`align()` 和 `contentAlign()` 方法，在创建新 `FlexLayout` 时配置定位。

另外，在实际的 `FlexLayout` 对象上，您可以使用 `setJustifyContent()` 方法将项目水平定位，使用 `setAlignment()` 方法配置垂直定位。要修改沿交叉轴（水平布局的 y 轴）周围组件的区域，请使用 `setAlignContent()` 方法。

:::tip
`setAlignment()` 方法控制项目在容器内沿交叉轴的显示方式，对于单行布局有效。

`setAlignContent()` 方法控制交叉轴周围的空间，仅在布局有多行时有效。
:::

<ComponentDemo
path='/webforj/flexpositioning'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='375px'
/>

### 换行 {#wrapping}

要进一步自定义 `FlexLayout` 组件，可以指定在添加的组件无法再适应显示时的行为。使用构建器配置时，请使用 `nowrap()`（默认）、`wrap()` 和 `wrapReverse()` 方法配置换行。要在现有的 `FlexLayout` 对象上配置此属性，请使用 `setWrap()` 方法。

### 间距 {#spacing}

为了在项目之间应用最小间距，可以设置 `gap` 属性。它仅在项目间应用间距，而不在外边缘。

gap 属性的行为可以理解为项目之间的最小距离，因此仅在它是项目之间最大计算空间时生效。如果由于另一个计算属性（例如 `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`）导致项目之间的空间会更大，那么 gap 属性将被忽略。

### 流 {#flow}

Flex 流是方向和换行属性的组合，可以使用 `setFlow()` 方法在 `FlexLayout` 对象上设置。

:::info
要在创建布局时配置此属性，请使用适当的方向和换行方法。例如，要创建一个列换行流，请使用 `.vertical().wrap()` 组合。
:::

### 容器构建器 {#container-builder}

以下演示允许您使用从各种菜单中选择的所需 flex 属性构建一个容器。此工具不仅可以创建各种方法的可视示例，还可以创建具有所需属性的自定义布局。要使用您自定义的布局，只需复制输出代码并添加所需元素以在您的程序中使用。

<ComponentDemo
path='/webforj/flexcontainerbuilder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='600px'
/>


<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## 项目属性 {#item-properties}

项目属性不会影响 `FlexLayout` 内的任何子元素，但会影响实际布局本身。这对于样式独立于适用于所有子元素的样式的单个 `FlexLayout` 元素非常有用。

### 顺序 {#order}

`ItemOrder` 属性确定在 `FlexLayout` 内显示组件的顺序，当在 `FlexLayout` 上使用时，会分配给该布局的特定顺序编号。这会覆盖每个项目内置的默认 "源顺序"（将组件添加到其父元素的顺序），意味着它将在具有较高顺序的项目之前显示，并在具有较低顺序的项目之后显示。

此属性接受一个无单位的整数值，指定 flex 项在容器内的相对顺序。值越小，项目出现的顺序就越早。例如，顺序值为 1 的项目会在顺序值为 2 的项目之前出现。

:::caution
值得注意的是，顺序属性仅影响容器内项目的视觉顺序，而不影响它们在 DOM 中的实际位置。这意味着屏幕阅读器和其他辅助技术仍会按照源代码中出现的顺序读取项目，而不是按视觉顺序。
:::

<ComponentDemo
path='/webforj/flexorder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='320px'
/>

### 自我对齐 {#self-alignment}

`FlexLayout` 的自我对齐指的是单个 `FlexLayout` 对象在其父 flex 容器内沿交叉轴的对齐方式，该轴与主轴垂直。交叉轴的对齐由 `Alignment` 属性控制。

align-self 属性指定单个 flex 项在交叉轴上的对齐方式，覆盖 `FlexLayout` 对象中由 `AlignContent` 属性设置的默认对齐。这使您可以将单个 `FlexLayout` 对象与容器中的其他对象不同地对齐。

:::info
自我对齐使用与内容对齐相同的值。
:::

此属性在您需要将特定项目与容器中的其他项目不同地对齐时特别有用。请参见下面的示例以查看对单个项目的对齐：

<ComponentDemo
path='/webforj/flexselfalign'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='350px'
/>

### Flex 基础 {#flex-basis}

`Item Basis` 是一个属性，与 `FlexLayout` 的方向结合使用，用于设置 flex 项在任何剩余空间分配之前的初始大小。

`Item Basis` 属性指定在主轴上（水平针对行方向或垂直针对列方向）flex 项的默认大小。此属性根据 flex-direction 属性设置 flex 项的宽度或高度。

:::info
默认情况下，`Item Basis` 属性设置为 `auto`，这意味着项目的大小由其内容决定。然而，您也可以使用像像素(px)、ems(em)、百分比(%)或任何其他 CSS 长度单位等各种单位为项目设置特定大小。
:::

以下演示允许您选择一个或多个框并更改所选项目的 `Item Basis`。

<ComponentDemo
path='/webforj/flexbasis'
files={['src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java']}
height='300px'
/>

### Flex 增长与收缩 {#flex-grow--shrink}

`Item Grow` 和 `Item Shrink` 是两个属性，它们结合在一起以及 `Item Basis` 属性，用于确定 flex 项在 `FlexLayout` 对象内如何增长或收缩以填充可用空间。

`Item Grow` 属性指定相对于容器中其他项目，flex 项可以增长的程度。它接受一个无单位的值，表示应分配给该项目的可用空间比例。例如，如果一个项目的 `Item Grow` 值为 1，另一个的值为 2，则第二个项目的增长量将是第一个项目的两倍。

另一方面，`Item Shrink` 属性指定相对于容器中其他项目，flex 项可以收缩的程度。它也接受一个无单位的值，表示应分配给该项目的可用空间比例。例如，如果一个项目的 `Item Shrink` 值为 1，另一个的值为 2，则第二个项目的收缩量将是第一个项目的两倍。

当一个容器有比其内容所需更多的空间时，具有 `Item Grow` 值大于 0 的 flex 项将扩展以填充可用空间。每个项目获得的空间量由其 `Item Grow` 值与容器中所有项目的总 `Item Grow` 值的比例决定。

同样地，当一个容器没有足够的空间来容纳其内容时，具有 `Item Shrink` 值大于 0 的 flex 项将收缩以适应可用空间。每个项目放弃的空间量由其 `Item Shrink` 值与容器中所有项目的总 `Item Shrink` 值的比例决定。

## 示例表单 {#example-form}
以下表单演示了 `FlexLayout` 如何将输入字段组织成结构化的布局。

:::tip
如果您更喜欢基于列的结构，请查看在 [`ColumnsLayout`](../components/columns-layout) 文章中的该表单的 `ColumnsLayout` 版本，以查看它的比较。
:::

<ComponentDemo
path='/webforj/flexlayout'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java',
  'src/main/resources/static/css/flexlayout/flexLayout.css',
]}
height='620px'
/>
