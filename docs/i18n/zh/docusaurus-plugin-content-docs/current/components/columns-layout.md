---
title: ColumnsLayout
sidebar_position: 25
description: >-
  Arrange child components into a responsive multi-column grid with the
  ColumnsLayout component using configurable breakpoints and alignment.
_i18n_hash: d75bb3fcf3260672e15ef9acbb38e295
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

`ColumnsLayout` 组件将项目排列成响应式的基于列的布局，调整基于可用宽度。断点和对齐方式会自动管理，因此构建多列表单和内容网格无需自定义响应逻辑。

<!-- INTRO_END -->

## 默认行为 {#default-behavior}

默认情况下，`ColumnsLayout` 将项目排列为两列，并占据其父元素的全部宽度。显示可以通过下面的部分进一步调整，涉及断点和对齐设置。

<ComponentDemo
path='/webforj/columnslayout'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java']}
height='450px'
/>

:::info 水平布局
这可以替代或结合 [`FlexLayout`](./flex-layout) 组件使用 - 一个同样强大的水平布局工具。
:::

## 断点 {#breakpoints}

在其核心，`ColumnsLayout` 旨在提供一个流动的、网格状的系统，能够适应其父容器的宽度。与传统的固定网格系统不同，此布局允许开发人员在给定宽度时指定列数，并动态计算基于设定的 `Breakpoint` 对象显示的列数。

这使得 `ColumnsLayout` 能从小屏幕上的更受限空间平滑地适应到大屏幕上的更宽区域，为开发人员提供响应式设计，而无需自定义实现。

### 理解 `Breakpoint` {#understanding-a-breakpoint}

可以使用 `Breakpoint` 类指定一个 `Breakpoint`，该类接受三个参数：

1. **名称（可选）**：
为断点命名允许您在未来的配置中引用它。

2. **最小宽度**：
每个断点都有一个特定的范围，决定何时应用其布局。最小宽度是明确设定的，若存在下一个断点，则确定最大宽度。您可以使用整数定义最小宽度（以像素为单位）或使用 `String` 指定其他单位，如 `vw`、`%` 或 `em`。

3. **列数**：
使用此整数指定断点应具有的列数。

:::info `Breakpoint` 评估
断点按照宽度的升序进行评估，这意味着布局将使用第一个匹配的断点。
:::


### 应用断点 {#applying-breakpoints}

断点可以通过两种方式应用于 `ColumnsLayout`：在构造期间，或使用 `setBreakpoints()` 方法在 `List` 中：

```java
ColumnsLayout layout = new ColumnsLayout();

List<Breakpoint> breakpoints = List.of(
  // 在宽度 >= 0px 时一列
  new Breakpoint(0, 1),
  // 在宽度 >= 600px 时两列
  new Breakpoint(600, 2),
  // 在宽度 >= 1200px 时四列
  new Breakpoint(1200, 4));

layout.setBreakpoints(breakpoints);
```

下面的演示展示了在构造时设置多个断点的示例，使用断点配置 [`Span`](#column-span-and-spans-per-breakpoint) 的组件，并演示了当应用被调整大小时 `ColumnsLayout` 的调整能力：

<ComponentDemo
path='/webforj/columnslayoutbreakpoints'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java']}
height='375px'
/>

## 列 `Span` 和每个 `Breakpoint` 的跨度 {#column-span-and-spans-per-breakpoint}

在 `ColumnsLayout` 中的列跨度允许您控制项目占据的列数，使您能够更好地控制在不同宽度下布局的外观。当您需要特定元素根据屏幕大小或设计要求占用更多或更少的空间时，这尤其有用。

### 管理列跨度 {#managing-column-spans}

默认情况下，`ColumnsLayout` 中的每个项目占用恰好一列。然而，您可以通过为单个项目设置列跨度来自定义此行为。跨度指定一个项目应占用的列数。

```java
Button button = new Button("点击我");
layout.addComponent(button);
// 项目占两列
layout.setSpan(button, 2);
```

在上述示例中，按钮占用两列，而不是默认的一列。`setSpan()` 方法允许您指定组件在布局中应跨越多少列。

### 根据断点调整列跨度 {#adjusting-column-spans-with-breakpoints}

您还可以基于断点动态调整列跨度。当您希望一个项目根据屏幕大小跨越不同数量的列时，此功能非常有用。例如，您可能希望在移动设备上一个元素只占用一列，但在更大屏幕上占用多列。

```java
TextField email = new TextField("电子邮件");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
//电子邮件字段将在中等断点激活时跨两列
columnsLayout.setSpan(email, "medium", 2);
//...
```

这种级别的自定义确保了您的布局在不同设备之间保持响应性和适当的结构。

## 在列中放置项目 {#placing-items-within-columns}

`ColumnsLayout` 提供了在特定列内放置项目的能力，使您能够更好地控制元素的排列。您可以手动指定项目应该出现在布局中的位置，确保重要组件按预期显示。

### 基本列放置 {#basic-column-placement}

默认情况下，项目放置在下一个可用列中，从左到右填充。然而，您可以覆盖此行为并指定项目应该放置的确切列。要将项目放置在特定列中，请使用 `setColumn()` 方法。在此示例中，按钮被放置在布局的第二列，而不管其相对于其他组件的添加顺序：

```java
Button button = new Button("提交");
layout.addComponent(button);
// 将项目放置在第二列
layout.setColumn(button, 2);
```

### 根据断点调整放置 {#adjusting-placement-per-breakpoint}

与列跨度一样，您使用断点根据屏幕大小调整项目的放置。这对于在视口变化时重新排序或重新定位布局中的元素很有用。

```java
TextField email = new TextField("电子邮件");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
//电子邮件字段将在中等断点激活时出现在第二列
columnsLayout.setColumn(email, "medium", 2);
//...
```

在以下演示中，请注意当 `"medium"` 断点被触发时，`email` 字段跨越两列，而 `confirmPassword` 字段被放置在第一列，而不是其默认的第二列位置：

<ComponentDemo
path='/webforj/columnslayoutspancolumn'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java']}
height='375px'
/>

:::tip 避免冲突
当多个项目在具有不同跨度和/或列分配的布局中放置时，请确保行中项目的组合跨度和放置不会重叠。布局会尝试自动优雅地管理间距，但认真设计跨度和断点可以防止项目的意外显示。
:::

## 垂直和水平项目对齐 {#vertical-and-horizontal-item-alignments}

`ColumnsLayout` 中的每个项目都可以在其列内水平和垂直对齐，控制内容在布局中的位置。

**项目的水平对齐**使用 `setHorizontalAlignment()` 方法进行控制。该属性决定项目在其列中沿水平轴的对齐方式。

**垂直对齐**指定项目在其列中的垂直位置。当列具有不同高度时，这很有用，您希望控制项目的垂直分布。

可用的 `Alignment` 选项包括：

- `START`：将项目对齐到列的左侧（默认）。
- `CENTER`：在列中水平居中项目。
- `END`：将项目对齐到列的右侧。
- `STRETCH`：将组件拉伸以填满布局。
- `BASELINE`：根据列内的文本或内容对齐，按文本基线而非其他对齐选项对齐项目。
- `AUTO`：自动对齐。

<ComponentDemo
path='/webforj/columnslayoutalignment'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java']}
height='500px'
/>

在上面的演示中，`提交` 按钮被赋予 `ColumnsLayout.Alignment.END`，以确保它出现在其列的末尾，或者在此情况下出现在右侧。

## 项目间距 {#item-spacing}

控制 `ColumnsLayout` 中列之间（水平间距）和行之间（垂直间距）的空间帮助开发人员微调布局。

要设置布局的水平间距，请使用 `setHorizontalSpacing()` 方法：

```java
// 设置列之间的20px空间
layout.setHorizontalSpacing(20);
```

类似地，使用 `setVerticalSpacing()` 方法配置布局中行之间的空间：

```java
// 设置行之间的15px空间
layout.setVerticalSpacing(15);
```

:::tip CSS 单位
您可以使用整数定义最小宽度（以像素为单位）或使用 `String` 指定其他单位，如 `vw`、`%` 或 `em`。
:::

## 水平和垂直布局 {#horizontal-and-vertical-layouts}

使用 [`FlexLayout`](./flex-layout) 组件和 `ColumnsLayout` 组件以及两者的组合，可以构建响应式和吸引人的布局。下面是 [在 FlexLayout 中创建的表单](./flex-layout#example-form) 文章的示例，但使用 `ColumnLayout` 方案代替：

<ComponentDemo
path='/webforj/columnslayoutform'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java']}
height='700px'
/>

## 样式 {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
