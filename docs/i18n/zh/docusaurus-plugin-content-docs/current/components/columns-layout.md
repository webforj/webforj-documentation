---
title: ColumnsLayout
sidebar_position: 25
description: >-
  Arrange child components into a responsive multi-column grid with the
  ColumnsLayout component using configurable breakpoints and alignment.
_i18n_hash: 3705994e0ddda1a54c0582b290942549
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

`ColumnsLayout` 组件将项目安排成一个响应式的基于列的布局，能够根据可用宽度进行调整。断点和对齐方式会自动管理，因此构建多列表单和内容网格不需要自定义的响应逻辑。

<!-- INTRO_END -->

## 默认行为 {#default-behavior}

默认情况下，`ColumnsLayout` 占据其父容器的全宽，并将项目安排在单列中，当宽度达到 `40em` 或更大时切换为两列。

<ComponentDemo
path='/webforj/columnslayout'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java']}
height='450px'
/>

:::info 水平布局
这可以替代或与 [`FlexLayout`](./flex-layout) 组件结合使用，后者同样是一个强大的水平布局工具。
:::

## 断点 {#breakpoints}

`ColumnsLayout` 的核心是提供一个流动的、网格状的系统，可以适应其父容器的宽度。与传统的固定网格系统不同，这种布局允许开发人员在给定的宽度下指定列数，并根据设定的 `Breakpoint` 对象动态计算显示的列数。

这使得 `ColumnsLayout` 能够平滑地从小屏幕上的限制空间适应到大屏幕上的宽广区域，为开发人员提供响应式设计，而无需自定义实现。

### 理解 `Breakpoint` {#understanding-a-breakpoint}

`Breakpoint` 可以使用 `Breakpoint` 类指定，该类需要三个参数：

1. **名称（可选）**：
为断点命名允许你在以后的配置中引用它。

2. **最小宽度**：
每个断点都有一个特定范围，用于确定何时应用其布局。最小宽度是明确定义的，下一个断点如果存在则决定最大宽度。可以使用整数定义最小宽度（以像素为单位），也可以使用 `String` 指定其他单位，如 `vw`、`%` 或 `em`。

3. **列数**：
使用此整数指定断点应有多少列。

:::info `Breakpoint` 评估
断点按照宽度的升序进行评估，这意味着布局将使用第一个匹配的断点。
:::

### 应用断点 {#applying-breakpoints}

可以通过两种方式将断点应用于 `ColumnsLayout`：在构造期间，或在使用 `setBreakpoints()` 方法的 `List` 中：

```java
ColumnsLayout layout = new ColumnsLayout();

List<Breakpoint> breakpoints = List.of(
  // 宽度 >= 0px 时为一列
  new Breakpoint(0, 1),
  // 宽度 >= 600px 时为两列
  new Breakpoint(600, 2),
  // 宽度 >= 1200px 时为四列
  new Breakpoint(1200, 4));

layout.setBreakpoints(breakpoints);
```

下面的演示展示了在构造时设置多个断点的示例，使用断点配置组件的 [`Span`](#column-span-and-spans-per-breakpoint)，并演示了当应用程序调整大小时 `ColumnsLayout` 的调整能力：

<ComponentDemo
path='/webforj/columnslayoutbreakpoints'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java']}
height='375px'
/>

## 列 `Span` 和每个 `Breakpoint` 的跨度 {#column-span-and-spans-per-breakpoint}

`ColumnsLayout` 中的列跨度允许你控制项目占用多少列，使你能更好地控制在不同宽度下布局的外观。这在某些元素需要根据屏幕大小或设计要求占用更多或更少空间时特别有用。

### 管理列跨度 {#managing-column-spans}

默认情况下，`ColumnsLayout` 中的每个项目恰好占用一列。然而，你可以通过为单个项目设置列跨度来自定义此行为。跨度指定项目应占据的列数。

```java
Button button = new Button("点击我");
layout.add(button);
// 项目占用两列
layout.setSpan(button, 2);
```

在上面的例子中，按钮占用了两列，而不是默认的一列。`setSpan()` 方法允许你指定组件在布局中应跨越多少列。

### 使用断点调整列跨度 {#adjusting-column-spans-with-breakpoints}

你还可以根据断点动态调整列跨度。当你希望某个项目根据屏幕大小跨越不同数量的列时，这个功能非常有用。例如，你可能希望一个元素在移动设备上占用单列，而在更大屏幕上跨越多列。

```java
TextField email = new TextField("邮箱");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
//email 字段在中等断点激活时将跨越两列
columnsLayout.setSpan(email, "medium", 2);
//...
```

这种定制级别确保您的布局在不同设备之间保持响应和适当结构。

## 在列中放置项目 {#placing-items-within-columns}

`ColumnsLayout` 提供了在特定列中放置项目的能力，给予了元素排列的更多控制。你可以手动指定项目在布局中的位置，确保重要组件按预期显示。

### 基本列放置 {#basic-column-placement}

默认情况下，项目放置在下一个可用的列中，从左到右填充。然而，你可以覆盖此行为，指定某个项目应放置的确切列。要在特定列中放置项目，请使用 `setColumn()` 方法。在这个例子中，按钮被放置在布局的第二列，无论其添加的顺序是相对于其他组件的：

```java
Button button = new Button("提交");
layout.add(button);
// 将项目放置在第二列
layout.setColumn(button, 2);
```

### 按断点调整放置 {#adjusting-placement-per-breakpoint}

与列跨度一样，您可以使用断点根据屏幕大小调整项目的放置。这在视口变化时重新排列或重新定位布局中的元素非常有用。

```java
TextField email = new TextField("邮箱");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
//在中等断点激活时，email 字段将出现在第二列
columnsLayout.setColumn(email, "medium", 2);
//...
```

在下面的演示中，请注意，当触发 `"medium"` 断点时，`email` 字段跨越了两个列：

<ComponentDemo
path='/webforj/columnslayoutspancolumn'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java']}
height='375px'
/>

:::tip 避免冲突
当多个项目以不同的跨度和/或列分配放置在布局中时，确保同行中项目的组合跨度和放置不重叠。布局会尝试自动优雅地管理间距，但对跨度和断点的仔细设计可以防止项目意外显示。
:::

## 垂直和水平项目对齐 {#vertical-and-horizontal-item-alignments}

`ColumnsLayout` 中的每个项目都可以在其列内水平和垂直对齐，控制内容在布局内的位置。

**项目的水平对齐** 通过使用 `setHorizontalAlignment()` 方法来控制。该属性决定项目在其列内水平轴上的对齐方式。

**垂直对齐** 指定项目在其列内的垂直轴上的位置。这在列具有不同高度且你希望控制项目的垂直分布时非常有用。

可用的 `Alignment` 选项包括：

- `START`：将项目对齐到列的左侧。
- `CENTER`：在列中水平居中项目。
- `END`：将项目对齐到列的右侧。
- `STRETCH`：拉伸组件以填充布局。
- `BASELINE`：根据列内的文本或内容对齐，按文本基线对齐项目，而不是其他对齐选项。
- `AUTO`：自动对齐（默认）。

<ComponentDemo
path='/webforj/columnslayoutalignment'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java']}
height='500px'
/>

在上述演示中，`提交` 按钮被赋予 `ColumnsLayout.Alignment.END` 以确保其出现在列的末尾或右侧。

## 项目间距 {#item-spacing}

控制 `ColumnsLayout` 中列之间（水平间距）和行之间（垂直间距）的空间有助于开发人员微调布局。

要设置布局的水平间距，请使用 `setHorizontalSpacing()` 方法：

```java
// 设置列之间的空间为 20px
layout.setHorizontalSpacing(20);
```

同样，使用 `setVerticalSpacing()` 方法配置布局行之间的空间：

```java
// 设置行之间的空间为 15px
layout.setVerticalSpacing(15);
```

:::tip CSS 单位
间距接受整数作为像素，或作为其他单位（如 `vw` 或 `%`）的 `String`。
:::

## 水平和垂直布局 {#horizontal-and-vertical-layouts}

可以使用 [`FlexLayout`](./flex-layout) 组件和 `ColumnsLayout` 组件，以及两者的组合，构建响应式和美观的布局。以下是 [在 FlexLayout 中创建的表单](./flex-layout#example-form) 文章的示例，但使用 `ColumnLayout` 方案：

<ComponentDemo
path='/webforj/columnslayoutform'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java']}
height='700px'
/>

## 样式 {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
