---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: ed7626149e8b31e663de874e83935567
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

`ColumnsLayout` 组件在 webforJ 中允许开发人员使用灵活且响应式的垂直布局创建布局。此布局提供动态列，根据可用宽度进行调整。该组件通过自动管理断点和对齐方式，简化了多列布局的创建。

:::info 水平布局
此布局可以替代或结合使用 [`FlexLayout`](./flex-layout) 组件——这也是一个同样强大的水平布局工具。
:::

## 基础 {#basics}

在首次实例化时，`ColumnsLayout` 使用两个列来显示添加到布局的项目。默认情况下，它占用父元素的全部宽度，并根据需要扩大以适应额外内容。通过使用 [`Breakpoint`](./columns-layout#breakpoints) 和 [`Alignment`](./columns-layout#vertical-and-horizontal-item-alignments) 设置，可以进一步校准添加项目的显示，这将在后面的部分中详细讨论。

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

## 断点 {#breakpoints}

从根本上讲，`ColumnsLayout`旨在提供一个流体的、网格状的系统，以适应其父容器的宽度。与传统的固定网格系统不同，该布局允许开发人员在给定宽度下指定列数，并根据设置的 `Breakpoint` 对象动态计算显示的列数。

这使得 `ColumnsLayout` 能够平稳地从小屏幕的更受限空间适应到大屏幕的更宽广区域，为开发人员提供响应式设计，而无需自定义实现。

### 理解 `Breakpoint` {#understanding-a-breakpoint}

可以使用 `Breakpoint` 类来指定 `Breakpoint`，该类接受三个参数：

1. **名称（可选）**：
给断点命名允许您在将来的配置中引用它。

2. **最小宽度**：
每个断点都有一个特定范围，决定何时应用其布局。最小宽度是明确定义的，下一个断点确定最大宽度（如果存在）。您可以使用整数来定义以像素为单位的最小宽度，或使用 `String` 指定其他单位，例如 `vw`、`%` 或 `em`。

3. **列数**：
使用此整数指定断点应具有的列数。

:::info `Breakpoint` 评估
断点以宽度的升序进行评估，意味着布局将使用第一个匹配的断点。
:::

### 应用断点 {#applying-breakpoints}

在构造期间或者使用 `addBreakpoint(Breakpoint)` 方法（如下所示）可以向 `ColumnsLayout` 应用断点。

```java
ColumnsLayout layout = new ColumnsLayout()
    // 在宽度 >= 0px 时使用一列
    .addBreakpoint(new Breakpoint(0, 1))
    // 在宽度 >= 600px 时使用两列
    .addBreakpoint(new Breakpoint(600, 2))
    // 在宽度 >= 1200px 时使用四列
    .addBreakpoint(new Breakpoint(1200, 4));  
```

下面的演示显示了在构造时设置多个断点的示例，演示如何使用断点来配置组件的 [`Span`](#column-span-and-spans-per-breakpoint)，并演示 `ColumnsLayout` 在应用重新调整大小时的能力：

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## 列 `Span` 和每个 `Breakpoint` 的跨度 {#column-span-and-spans-per-breakpoint}

`ColumnsLayout` 中的列跨度允许您控制一个项目占据多少列，使您可以更好地控制布局在不同宽度下的外观。当您需要某些元素根据屏幕大小或设计要求占用更多或更少空间时，这尤其有用。

### 管理列跨度 {#managing-column-spans}

默认情况下，`ColumnsLayout` 中的每个项目占据精确的一个列。然而，您可以通过为单个项目设置列跨度来自定义此行为。跨度指定一个项目应该占据的列数。

```java
Button button = new Button("点击我");
layout.addComponent(button);
// 项目跨越两列
layout.setSpan(button, 2);
```

在上述示例中，按钮占据两列，而不是默认的一个。`setSpan()` 方法允许您指定组件在布局中应跨越多少列。

### 使用断点调整列跨度 {#adjusting-column-spans-with-breakpoints}

您还可以根据断点动态调整列跨度。当您希望一个项目在不同的屏幕尺寸上跨越不同数量的列时，此功能非常有用。例如，您可能希望在移动设备上让一个元素占据单列，但在较大屏幕上跨越多列。

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
//当 medium 断点激活时，email 字段将跨越两列
columnsLayout.setSpan(email, "medium", 2);
//...
```

这种自定义水平可以确保您的布局在不同设备上保持响应性和适当的结构。

## 在列内放置项目 {#placing-items-within-columns}

`ColumnsLayout` 提供了在特定列中放置项目的能力，使您能对元素的排列有更多控制。您可以手动指定项目应在布局中出现的位置，确保重要组件按预期显示。

### 基本列摆放 {#basic-column-placement}

默认情况下，项目被放置在下一个可用列中，从左到右填充。然而，您可以覆盖此行为并指定特定列，以放置项目。要将项目放置在特定列中，请使用 `setColumn()` 方法。在此示例中，按钮被放置在布局的第二列，无论其相对于其他组件的添加顺序如何：

```java
Button button = new Button("提交");
layout.addComponent(button);
// 将项目放置在第二列
layout.setColumn(button, 2);  
```

### 根据断点调整摆放 {#adjusting-placement-per-breakpoint}

与列跨度一样，使用断点来根据屏幕大小调整项目的摆放。这对于在视口变化时重新排序或重新定位布局中的元素非常有用。

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
//当 medium 断点激活时，email 字段将出现在第二列
columnsLayout.setColumn(email, "medium", 2); 
//...
```

在下面的演示中，请注意，当“medium”断点被触发时，`email` 字段跨越两列，而 `confirmPassword` 字段被放置在第一列，而不是其在第二列的默认放置：

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip 避免冲突
当多个项目在具有不同跨度和/或列分配的布局中放置时，确保每行中项目的组合跨度和摆放不重叠。布局会尝试自动优雅地管理间距，但对跨度和断点的仔细设计可防止项目意外显示。
:::

## 垂直和水平项目对齐 {#vertical-and-horizontal-item-alignments}

`ColumnsLayout` 中的每个项目都可以水平和垂直对齐，控制内容在布局内的位置。

**项目的水平对齐**由 `setHorizontalAlignment()` 方法控制。此属性决定了项目在其列内沿水平轴的对齐方式。

**垂直对齐**指定项目在其列内沿垂直轴的位置。当列具有不同高度时，这很有用，您希望控制项目的垂直分布。

可用的 `Alignment` 选项包括：

- `START`: 将项目对齐到列的左侧（默认）。
- `CENTER`: 在列中水平居中项目。
- `END`: 将项目对齐到列的右侧。
- `STRETCH`: 将组件拉伸以填充布局。
- `BASELINE`: 基于列内的文本或内容对齐项目，将项目对齐到文本基线，而不是其他对齐选项。
- `AUTO`: 自动对齐。

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

在上面的演示中，`Submit` 按钮被赋予 `ColumnsLayout.Alignment.END` 以确保它显示在列的末尾，或在这种情况下显示在右侧。

## 项目间距 {#item-spacing}

控制 `ColumnsLayout` 中列之间的空间（水平间距）和行之间的空间（垂直间距）可以帮助开发人员微调布局。

要设置布局的水平间距，请使用 `setHorizontalSpacing()` 方法：

```java
// 设置列之间的间距为 20px
layout.setHorizontalSpacing(20);  
```

同样，使用 `setVerticalSpacing()` 方法配置布局中的行之间的空间：

```java
// 设置行之间的间距为 15px
layout.setVerticalSpacing(15);  
```

:::tip CSS 单位
您可以使用整数定义以像素为单位的最小宽度，或使用 `String` 指定其他单位，例如 `vw`、`%` 或 `em`。
:::

## 水平和垂直布局 {#horizontal-and-vertical-layouts}

使用 [`FlexLayout`](./flex-layout) 组件和 `ColumnsLayout` 组件，以及两者的组合，可以构建响应式和美观的布局。以下是使用 `ColumnLayout` 方案的 [FlexLayout 中创建的表单示例](./flex-layout#example-form)：

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## 样式 {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
