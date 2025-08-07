---
title: FlexLayout
sidebar_position: 45
_i18n_hash: df051e46de48f07807bf0dc3bcaa641a
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

webforJ 为开发人员提供了一种高效且直观的方法来布局各种应用程序和组件 - Flex Layout。这个工具集允许项目以垂直或水平方式显示。

## Flex 布局属性 {#flex-layout-properties}

Flex 布局的属性可以分为两类：应用于布局内项目的属性，以及应用于布局本身的属性。Flex 布局，或称父元素，是一个可以包含一个或多个组件的盒子/容器。Flex Layout 内的所有内容称为项目或子元素。Flex Layout 提供了一些强大的对齐能力，这可以通过容器或项目属性来实现。

:::tip
webforJ 的布局组件遵循 [CSS 的 flexbox 布局](https://css-tricks.com/snippets/css/a-guide-to-flexbox/) 模式。然而，这些工具完全可以在 Java 中使用，不需要应用 CSS，除非在提供的方法外。
:::

## 容器属性 {#container-properties}

容器属性将适用于组件中的所有元素，而不是布局本身。它们不会影响父元素的方向或位置 - 只会影响子组件。

### 方向 {#direction}

Flex Layout 将根据开发人员选择的方向 - 水平或垂直，添加相邻的组件。当使用构建器时，在调用 `FlexLayout` 对象上的 `create()` 方法时，使用 `horizontal()`、`horizontalReverse()`、`vertical()` 或 `verticalReverse()` 方法来配置此布局。

另外，可以使用 `setDirection()` 方法。水平选项是 `FlexDirection.ROW`（从左到右）或 `FlexDirection.ROW_REVERSE`（从右到左），垂直选项是 `FlexDirection.COLUMN`（从上到下）或 `FlexDirection.COLUMN_REVERSE`（从下到上）。这可以通过 FlexLayout 对象实现，而不是构建器。

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### 定位 {#positioning}

水平添加的组件也可以在水平和垂直方向上进行定位。使用 Flex Layout Builder 的 `justify()`、`align()` 和 `contentAlign()` 方法在创建新的 Flex Layout 时配置定位。

另外，在实际的 FlexLayout 对象上，可以使用 `setJustifyContent()` 方法来水平定位项目，使用 `setAlignment()` 方法来配置垂直定位。要修改组件在交叉轴（水平布局的 y 轴）周围的区域，请使用 `setAlignContent()` 方法。

:::tip
`setAlignment()` 方法控制项目在容器内整体在交叉轴上的显示，并且适用于单行布局。

`setAlignContent()` 方法控制交叉轴周围的空间，仅在布局具有多行时生效。
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### 包装 {#wrapping}

为了进一步自定义 Flex Layout 组件，可以指定在添加的组件不再适合显示时的行为。使用构建器配置时，利用 `nowrap()`（默认）、`wrap()` 和 `wrapReverse()` 方法来配置包装。

另外，如果布局已经存在，请使用 `setWrap()` 方法来决定组件在无法适应单行时的行为。

### 间距 {#spacing}

为了在项目之间应用最小间距，可以设置间隔属性。它只在项目之间的间距上应用，而不在外缘上。

间隔属性的行为可以被认为是项目之间的最小距离 - 该属性仅在它是项目之间计算的最大空间时生效。如果由于其他计算的属性导致项目之间的空间更大，例如使用 `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)` 时，则间隔属性将被忽略。

### 流动 {#flow}

Flex 流动，是方向和包装属性的组合，可以使用 `setFlow()` 方法在 Flex Layout 对象上进行设置。

:::info
在创建布局时配置此属性时，使用适当的方向和包装方法。例如，要创建一个列包装流，使用 `.vertical().wrap()` 组合。
:::

### 容器构建器 {#container-builder}

以下演示允许您从各种菜单中选择所需的 flex 属性来构建容器。此工具不仅可以用来创建各种方法的可视示例，还可以作为一个工具，用于使用所需属性创建您自己的布局。要使用您自定义的布局，只需复制输出代码并添加您在程序中使用的所需元素。

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

## 项目属性 {#item-properties}

项目属性不会影响 Flex Layout 内的任何子元素，而是影响实际的布局本身。这对于独立于应用于所有子元素的样式而样式化单个 Flex Layout 元素很有用。

### 顺序 {#order}

`ItemOrder` 属性决定组件在 Flex Layout 中的显示顺序，当在 Flex Layout 上使用时，将为该布局分配一个特定的顺序编号。这将覆盖内置于每个项目中的默认“源顺序”（组件添加到其父级的顺序），意味着它将在更高顺序的项目之前显示，在更低顺序的项目之后显示。

该属性接受一个无单位的整数值，指定该 flex 项在容器内的相对顺序。值越低，该项目在顺序中出现得越早。例如，顺序值为 1 的项目将在顺序值为 2 的项目之前出现。

:::caution
需要注意的是，顺序属性只影响容器内项目的视觉顺序，而不是它们在 DOM 中的实际位置。这意味着屏幕阅读器和其他辅助技术仍会按照源代码中出现的顺序读取项目，而不是视觉顺序。
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### 自我对齐 {#self-alignment}

Flex Layout 的自我对齐是指单个 Flex Layout 对象在其父 flex 容器内沿交叉轴的对齐方式，交叉轴与主轴垂直。交叉轴对齐由 `Alignment` 属性控制。

align-self 属性指定单个 flex 项沿交叉轴的对齐方式，覆盖由 Flex Layout 对象中的 `AlignContent` 属性设置的默认对齐。这使您能够与容器中的其他 Flex Layout 对象不同地对齐单个 Flex Layout 对象。

:::info
自我对齐使用与内容对齐相同的值
:::

该属性在需要与容器中的其他项目不同对齐特定项目时尤其有用。下面的示例演示了对齐单个项目的示例：

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Flex 基础 {#flex-basis}

`Item Basis` 是与 Flex Layout 的方向结合使用的属性，用于设置 flex 项最初的大小，即在分配剩余空间之前的大小。

`Item Basis` 属性指定沿主轴的 flex 项的默认大小，主轴可以是水平（对于行方向）或垂直（对于列方向）。该属性根据 flex-direction 属性设置 flex 项的宽度或高度。

:::info
默认情况下，`Item Basis` 属性设置为 auto，这意味着该项目的大小由其内容决定。然而，您也可以使用像像素（px）、ems（em）、百分比（%）或任何其他 CSS 长度单位这样的各种单位为项目设置特定大小。
:::

以下演示允许您选择一个或多个框并更改所选项目的 `Item Basis`。

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex 增长 / 缩小 {#flex-grow--shrink}

`Item Grow` 和 `Item Shrink` 是两个相互配合使用的属性，与 `Item Basis` 属性一起决定 flex 项如何增长或缩小，以填充 Flex Layout 对象内的可用空间。

`Item Grow` 属性指定 flex 项可以相对于容器内的其他项目增长多少。它采用一个无单位值，表示应分配给该项目的可用空间的比例。例如，如果一个项目的 `Item Grow` 值为 1，而另一个的值为 2，则第二个项目的增长量将是第一个项目的两倍。

另一方面，`Item Shrink` 属性指定 flex 项可以相对于容器内的其他项目缩小多少。它也采用一个无单位值，表示应分配给该项目的可用空间的比例。例如，如果一个项目的 `Item Shrink` 值为 1，而另一个的值为 2，则第二个项目的缩小量将是第一个项目的两倍。

当容器有比其内容所需的更多空间时，`Item Grow` 值大于 0 的 flex 项将扩展以填充可用空间。每个项目获得的空间量由其 `Item Grow` 值和容器内所有项目的总 `Item Grow` 值的比例决定。

同样，当容器没有足够的空间容纳其内容时，`Item Shrink` 值大于 0 的 flex 项将缩小以适应可用空间。每个项目放弃的空间量由其 `Item Shrink` 值和容器内所有项目的总 `Item Shrink` 值的比例决定。

## 示例表单 {#example-form}
下面的表单演示了 `FlexLayout` 如何将输入字段组织成结构化布局。

:::tip
如果您更喜欢基于列的结构，请查看该表单的 [`ColumnsLayout`](../components/columns-layout) 版本，以了解其如何进行比较。
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
