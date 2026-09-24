---
title: Card
sidebar_position: 17
sidebar_class_name: new-content
description: >-
  Group related content and actions with the Card component, including slotted
  regions. orientation, elevation, dividers, and click handling.
_i18n_hash: 08b0239bc5bbeb0b14f3b03dda7b8b17
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-card" />
<DocChip chip='since' label='26.02' />
<JavadocLink type="card" location="com/webforj/component/card/Card" top='true'/>

`Card` 组件提供了一个表面，用于将相关内容和操作组合成一个单一项目。它支持插槽区域，包括图形、标题、正文和页脚，以及控制卡片呈现方式的方向、阴影、分隔符和密度设置。

<!-- INTRO_END -->

## 创建一个 `Card` {#creating-a-card}

通过将内容传递给构造函数来创建一个 `Card`，该内容将放置在卡片的正文中。创建后，正文也可以通过 `add()` 或 `addToBody()` 进行填充，两者功能相同。

```java
Card card = new Card(new Paragraph("销售在每个地区都有所上升。"));

//等效
Card card = new Card();
card.addToBody(new Paragraph("销售在每个地区都有所上升。"));
```

一个空的 `Card` 只渲染其框架，不显示其他内容。

## 卡片区域 {#card-regions}

除了正文外，每个区域都通过其自己的插槽进行填充，插槽中没有内容的区域不会被渲染。没有页脚的 `Card` 在正文结束后闭合，而只有正文的 `Card` 则是一个有框的内容块。

- `addToFigure()` 保存卡片的插图，例如图像、视频或图表。其位置取决于卡片的方向。
- `addToIcon()` 设置标题行中的前导视觉，接受任何组件，包括 `Icon` 或 `Avatar`。
- `addToTitle()` 设置标题行中的标题。
- `addToCaption()` 在标题下添加一行辅助内容，适合用于日期、作者或状态。
- `addToHeaderActions()` 填充标题行的末尾，通常包含一个 `Button` 或菜单。
- `addToFooter()` 关闭 `Card`，通常用于操作或元数据。

```java
Card card = new Card(new Paragraph("销售在每个地区都有所上升。"));
card.addToFigure(new Img("cover.png", "报告封面"))
    .addToIcon(TablerIcon.create("chart-bar"))
    .addToTitle(new H3("月度报告"))
    .addToCaption(new Paragraph("2026年7月"))
    .addToHeaderActions(new Button("分享"))
    .addToFooter(new Button("阅读更多"));
```

:::info 标题和可访问名称
`Card` 宣布自己是一个区域，标题成为其可访问名称。请在此使用 `H3` 等标题元素，以便屏幕阅读器用户可以通过页面的标题结构找到 `Card`。
:::

<ComponentDemo
path='/webforj/cardregions'
files={[
  'src/main/java/com/webforj/samples/views/card/CardRegionsView.java',
  'src/main/frontend/css/card/cardRegions.css',
]}
height='700px'
/>

## 方向 {#orientation}

方向控制插图相对于其他区域的位置，可以通过 `setOrientation()` 设置。

卡片默认是垂直的，因此它们将插图堆叠在标题、正文和页脚之上。这适合以网格排列的卡片，每个卡片占据狭窄的列。将 `Card.Orientation.HORIZONTAL` 传递给 `setOrientation()` 会使卡片变为水平放置，将插图放在这些区域旁边。

```java
card.setOrientation(Card.Orientation.HORIZONTAL);
```

<ComponentDemo
path='/webforj/cardorientation'
files={['src/main/java/com/webforj/samples/views/card/CardOrientationView.java']}
height='500px'
/>

因为该设置仅移动插图而不移动其他内容，所以没有插图的 `Card` 在任何方向下看起来都是相同的。

## 阴影和边框 {#elevation-and-border}

两个设置决定了 `Card` 与其后面的页面之间的分隔程度。`setShadow()` 应用来自阴影比例的值，范围从 `NONE` 到 `XSMALL`、`SMALL`、`MEDIUM`、`LARGE`、`XLARGE` 和 `XXLARGE`。 `setBorderless()` 控制 `Card` 是否绘制边框。默认值是 `Shadow.XSMALL`，并绘制边框。

这些设置是独立的，因此任何阴影都可以与有或没有边框搭配使用。

<ComponentDemo
path='/webforj/cardappearance'
files={[
  'src/main/java/com/webforj/samples/views/card/CardAppearanceView.java']}
height='300px'
/>

## 分隔符和间距 {#dividers-and-expanse}

虽然阴影和边框设置控制了 `Card` 如何与页面相对，但分隔符和间距控制卡片内部的可读区域。

`setDivided(true)` 在页眉后和页脚前绘制一个分隔符，这在各区域包含密集内容时非常有用。默认情况下，分隔符是关闭的。没有内容的区域的分隔符不会被绘制，因此没有页脚的分隔卡只显示一个分隔符，在页眉下方。分隔符在平面卡上更为重要，因为没有框架可以进行起到分隔的作用。

`setExpanse()` 控制密度，驱动填充、区域间的间隙、标题和说明的大小。`Card`使用共享的 `Expanse` 枚举，提供 `NONE`、`XSMALL`、`SMALL`、`MEDIUM`、`LARGE` 和 `XLARGE`，其中 `MEDIUM` 为默认值。较小的间距适合仪表盘小部件和侧边栏，其中多个卡片共享屏幕。

以下示例展示了两个 `Card` 组件，分别使用分隔符。一个 `Card` 使用 `Expanse.LARGE`，而另一个使用 `Expanse.SMALL`：

<ComponentDemo
path='/webforj/carddensity'
files={[
  'src/main/java/com/webforj/samples/views/card/CardDensityView.java',
  'src/main/frontend/css/card/cardDensity.css',
]}
height='400px'
/>

## 点击事件 {#click-events}

`Card` 组件实现了 `HasElementClickListener`，因此通过 `onClick()` 或 `addClickListener()` 注册的监听器会接收 `ElementClickEvent`。这使得整个表面成为一个单一的目标。

```java
card.onClick(event -> Router.getCurrent().navigate(new Location("/reports/july")));
```

:::warning 来自 `Card` 内部的点击
在 `Card` 内部的组件上的点击事件也会触及 `Card`，因此具有自己监听器的 `Card` 在用户按下标题操作或页脚中的 `Button` 时会触发此监听器。当 `Card` 有一个明显的操作时，请向其添加监听器，并将其中的按钮保留用于 `Card` 本身不执行的操作。
:::

## 样式 {#styling}

<TableBuilder name="Card" />
