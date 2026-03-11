---
title: FlexLayout
sidebar_position: 45
_i18n_hash: ddb7d5ef1e583af6e3a7072d91329c7b
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

`FlexLayout` 组件使用 CSS Flexbox 模型以行或列的方式排列子组件。它使您能够控制对齐、间距、换行以及如何调整项目的大小以填充可用空间。

<!-- INTRO_END -->

## `FlexLayout` 属性 {#flex-layout-properties}

`FlexLayout` 属性可以分为两类：应用于布局中的项目的属性，以及应用于布局本身的属性。`FlexLayout` 或父元素是一个可以包含一个或多个组件的框/容器。`FlexLayout` 内部的所有内容称为项目或子元素。`FlexLayout` 提供了一些对齐能力，可以通过容器或项目属性来实现。

:::tip
`FlexLayout` 组件遵循 [CSS 的 flexbox 布局](https://css-tricks.com/snippets/css/a-guide-to-flexbox/) 模式。然而，`FlexLayout` 是完全在 Java 中使用的，不需要使用 Java API 方法之外的 CSS。
:::

## 容器属性 {#container-properties}

容器属性将应用于组件内部的所有组件，而不是布局本身。它们不会影响父元素的方向或位置，仅影响内部的子组件。

### 方向 {#direction}

`FlexLayout` 根据其方向（水平或垂直）将组件添加在一起。在使用构建器时，将 `horizontal()`、`horizontalReverse()`、`vertical()` 或 `verticalReverse()` 方法与 `FlexLayout.create()` 方法链式调用，以便在创建对象时配置布局。

要在现有的 `FlexLayout` 对象上设置方向，请使用 `setDirection()` 方法。水平选项为 `FlexDirection.ROW`（从左到右）或 `FlexDirection.ROW_REVERSE`（从右到左），而垂直选项为 `FlexDirection.COLUMN`（从上到下）或 `FlexDirection.COLUMN_REVERSE`（从下到上）。

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### 定位 {#positioning}

水平添加的组件也可以在水平和垂直方向上进行定位。使用 `FlexLayout` Builder 中的 `justify()`、`align()` 和 `contentAlign()` 方法在创建新的 `FlexLayout` 时配置定位。

或者，在实际的 `FlexLayout` 对象上，您可以使用 `setJustifyContent()` 方法以水平定位项目，使用 `setAlignment()` 方法进行垂直定位。要修改跨轴（对于水平布局为 y 轴）的组件周围区域，请使用 `setAlignContent()` 方法。

:::tip
`setAlignment()` 方法控制项目在容器内部的交叉轴的显示方式，对单行布局有效。

`setAlignContent()` 方法控制交叉轴周围的空间，只有在布局有多行时才会生效。
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### 换行 {#wrapping}

为了进一步自定义 `FlexLayout` 组件，您可以指定在添加组件时其行为，即当组件不再适合显示时。要使用构建器配置此内容，请使用 `nowrap()`（默认）、`wrap()` 和 `wrapReverse()` 方法进行换行配置。要在现有的 `FlexLayout` 对象上配置此内容，请使用 `setWrap()` 方法。

### 间距 {#spacing}

为了在项目之间应用最小间距，可以设置 `gap` 属性。它仅在项目之间应用间距，而不影响外缘。

间距属性的行为可以被认为是项目之间的最小距离，因此只有在它是项目之间计算的最大空间时，才会生效。如果由于另一计算属性（例如通过 `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`）导致项目之间的空间更大，则将忽略间距属性。

### 流向 {#flow}

Flex 流向是方向和换行属性的组合，可以使用 `setFlow()` 方法在 `FlexLayout` 对象上设置。

:::info
要在创建布局时配置这个属性，请使用适当的方向和换行方法。例如，要创建一个列换行流，使用 `.vertical().wrap()` 的组合。
:::

### 容器构建器 {#container-builder}

以下演示允许您从各种菜单中选择所需的 flex 属性来构建容器。此工具不仅可用于创建各种方法的视觉示例，还可以用于根据您的所需属性创建自己的布局。要使用您自定义的布局，只需复制生成的代码并添加您希望在程序中使用的元素。

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## 项目属性 {#item-properties}

项目属性不会影响 `FlexLayout` 内的任何子元素，但会影响实际的布局本身。这对于单个 `FlexLayout` 元素的样式很有用，该元素是较大 `FlexLayout` 元素的子元素，独立于应用于所有子元素的样式。

### 顺序 {#order}

`ItemOrder` 属性决定了组件在 `FlexLayout` 中的显示顺序，当在 `FlexLayout` 上使用时，将为该布局的特定顺序分配项目。这覆盖了每个项目内置的默认“源顺序”（添加组件到其父级的顺序），意味着它将显示在顺序更高的项目之前，并在顺序更低的项目之后。

此属性接受一个无单位整数值，指定在容器内的 flex 项的相对顺序。值越低，项目出现在顺序中的越早。例如，顺序值为 1 的项目将出现在顺序值为 2 的项目之前。

:::caution
重要的是要注意，顺序属性仅影响容器内项目的可视顺序，而不是它们在 DOM 中的实际位置。这意味着屏幕阅读器和其他辅助技术仍将按源代码中出现的顺序读取项目，而不是按可视顺序。
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### 自我对齐 {#self-alignment}

`FlexLayout` 的自我对齐是指单个 `FlexLayout` 对象在其父弹性容器内沿交叉轴的对齐方式，交叉轴与主轴垂直。交叉轴对齐由 `Alignment` 属性控制。

align-self 属性指定单个 flex 项沿交叉轴的对齐方式，覆盖在 `FlexLayout` 对象中由 `AlignContent` 属性设置的默认对齐。这使您能够将单个 `FlexLayout` 对象与容器中的其他对象不同地对齐。

:::info
自我对齐使用与内容对齐相同的值。
:::

此属性在您需要将特定项目与容器中的其他项目不同地对齐时尤其有用。请参见下面的示例，以对齐单个项目：

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Flex 基础 {#flex-basis}

`Item Basis` 是与 `FlexLayout` 的方向结合使用的属性，用于设置 flex 项的初始大小，在任何剩余空间分配之前。

`Item Basis` 属性指定沿主轴的 flex 项的默认大小，主轴可以是水平（对于行方向）或垂直（对于列方向）。此属性根据 flex-direction 属性的值设置 flex 项的宽度或高度。

:::info
默认情况下，`Item Basis` 属性设置为 `auto`，这意味着项目的大小由其内容决定。然而，您还可以使用各种单位（如像素（px）、em、百分比（%）或任何其他 CSS 长度单位）为项目设置特定大小。
:::

以下演示允许您选择一个或多个框并更改所选项目的 `Item Basis`。

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex 增长和收缩 {#flex-grow--shrink}

`Item Grow` 和 `Item Shrink` 是两个相互结合并与 `Item Basis` 属性一起工作的属性，用于确定 flex 项如何增长或收缩以填充 `FlexLayout` 对象内的可用空间。

`Item Grow` 属性指定弹性项目相对于容器中的其他项目能增长的程度。它接受一个无单位值，表示应分配给项目的可用空间的比例。例如，如果一个项目的 `Item Grow` 值为 1，另一个值为 2，第二个项目将增长两倍于第一个项目。

另一方面，`Item Shrink` 属性指定弹性项目相对于容器中的其他项目能收缩的程度。它也接受一个无单位值，表示应分配给项目的可用空间的比例。例如，如果一个项目的 `Item Shrink` 值为 1，另一个值为 2，第二个项目将收缩两倍于第一个项目。

当容器中有比其内容所需的空间更多时，`Item Grow` 值大于 0 的弹性项目将扩展以填充可用空间。每个项目获得的空间量由其 `Item Grow` 值与容器中所有项目的总 `Item Grow` 值的比率决定。

类似地，当一个容器没有足够的空间容纳其内容时，`Item Shrink` 值大于 0 的弹性项目将收缩以适应可用空间。每个项目放弃的空间量由其 `Item Shrink` 值与容器中所有项目的总 `Item Shrink` 值的比率决定。

## 示例表单 {#example-form}
下面的表单演示了 `FlexLayout` 如何将输入字段组织成结构化布局。

:::tip
如果您更喜欢基于列的结构，可以查看 [`ColumnsLayout`](../components/columns-layout) 文章中的该表单版本，以便了解它的比较。
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
