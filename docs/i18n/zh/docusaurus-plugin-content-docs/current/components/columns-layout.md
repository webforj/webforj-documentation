---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: 25558ea9869bae96974e292e7cc1939d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

`ColumnsLayout` 组件在 webforJ 中允许开发人员使用灵活且响应式的垂直布局创建布局。该布局提供了动态列，可以根据可用宽度进行调整。这个组件通过自动管理断点和对齐方式，简化了多列布局的创建。

:::info 水平布局 
这可以替代或与 [`FlexLayout`](./flex-layout) 组件结合使用，这是一个同样强大的水平布局工具。
:::

## 基础 {#basics}

在首次实例化时，`ColumnsLayout` 使用两列来显示添加到布局中的项目。默认情况下，它占据其父元素的全部宽度，并在需要时随着额外内容的增加而增长。通过使用 [`Breakpoint`](./columns-layout#breakpoints) 和 [`Alignment`](./columns-layout#vertical-and-horizontal-item-alignments) 设置，可以进一步校准已添加项目的显示，后续章节将更详细地讨论这些设置。

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

## 断点 {#breakpoints}

`ColumnsLayout` 本质上旨在提供一个流畅的网格系统，该系统能够根据其父容器的宽度进行调整。与传统的固定网格系统不同，该布局允许开发人员在给定宽度下指定列的数量，并根据设定的 `Breakpoint` 对象动态计算显示的列数。

这使得 `ColumnsLayout` 可以从小屏幕上的受限空间平滑过渡到大屏幕上的更宽区域，为开发人员提供响应式设计，而无需自定义实现。

### 了解 `Breakpoint` {#understanding-a-breakpoint}

`Breakpoint` 可以使用 `Breakpoint` 类来指定，该类接受三个参数：

1. **名称（可选）**：
命名一个断点可以在以后的配置中引用它。

2. **最小宽度**：
每个断点都有一个特定的范围，用于确定何时应用其布局。最小宽度是明确规定的，下一个断点确定最大宽度（如果存在）。您可以使用整数定义最小宽度（以像素为单位），或使用 `String` 指定其他单位，例如 `vw`、`%` 或 `em`。

3. **列数**：
使用该整数指定一个断点应该有多少列。


:::info `Breakpoint` 评估
断点按宽度的升序进行评估，这意味着布局将使用第一个匹配的断点。
:::


### 应用断点 {#applying-breakpoints}

断点以两种方式应用于 `ColumnsLayout`：在构造期间，或在使用 `setBreakpoints()` 方法的 `List` 中：

```java
ColumnsLayout layout = new ColumnsLayout();

List<Breakpoint> breakpoints = List.of(
    // 在宽度 >= 0px 时使用一列
    new Breakpoint(0, 1),
    // 在宽度 >= 600px 时使用两列
    new Breakpoint(600, 2),
    // 在宽度 >= 1200px 时使用四列
    new Breakpoint(1200, 4));

layout.setBreakpoints(breakpoints);
```

下面的演示展示了如何在构造时设置多个断点的示例，使用断点配置某个组件的 [`Span`](#column-span-and-spans-per-breakpoint)，并演示了在应用程序调整大小时 `ColumnsLayout` 的调整能力：

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## 列 `Span` 和每个 `Breakpoint` 的跨度 {#column-span-and-spans-per-breakpoint}

`ColumnsLayout` 中的列跨度允许您控制某个项目占据多少列，使您能够更好地控制在不同宽度下布局的外观。这在您需要某些元素根据屏幕尺寸或设计需求占据更多或更少空间时特别有用。

### 管理列跨度 {#managing-column-spans}

默认情况下，`ColumnsLayout` 中的每个项目占据正好一列。然而，您可以通过设置单个项目的列跨度来自定义此行为。跨度指定一个项目应该占据的列数。

```java
Button button = new Button("Click Me");
layout.addComponent(button);
// 项目占据两列
layout.setSpan(button, 2);
```

在上述示例中，按钮占据两列而不是默认的一个。`setSpan()` 方法允许您指定组件在布局中应跨越多少列。

### 使用断点调整列跨度 {#adjusting-column-spans-with-breakpoints}

您还可以根据断点动态调整列跨度。当您希望一个项目在不同屏幕尺寸下跨越不同数量的列时，此功能非常有用。例如，您可能希望在移动设备上占用单列，但在大屏幕上跨越多个列。

```java
TextField email = new TextField("Email");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
//在中等断点激活时，email 字段将跨越两列
columnsLayout.setSpan(email, "medium", 2);
//...
```

这一层次的自定义确保您的布局在不同设备上保持响应式和适当构建。

## 在列内放置项目 {#placing-items-within-columns}

`ColumnsLayout` 提供了在特定列中放置项目的能力，从而增强了元素排列的控制。您可以手动指定项目在布局中的位置，确保重要组件按预期显示。

### 基本列放置 {#basic-column-placement}

默认情况下，项目按从左到右的方式放置在下一个可用列中。然而，您可以覆盖此行为并指定项目应放置的确切列。要将项目放置在特定列中，请使用 `setColumn()` 方法。在这个示例中，按钮被放置在布局的第二列，无论它与其他组件的添加顺序如何：

```java
Button button = new Button("Submit");
layout.addComponent(button);
// 将项目放置在第二列
layout.setColumn(button, 2);  
```

### 根据断点调整放置 {#adjusting-placement-per-breakpoint}

与列跨度一样，您可以使用断点根据屏幕尺寸调整项目的位置。这对于在视口变化时重新排列或移动布局中的元素非常有用。

```java
TextField email = new TextField("Email");
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

在下面的演示中，请注意，当 `"medium"` 断点被触发时，`email` 字段跨越了两列，而 `confirmPassword` 字段被放置在第一列，而不是其在第二列的默认位置：

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip 避免冲突
当多个项目以不同的跨度和/或列指定放置在布局中时，确保一行中项目的总跨度和放置不重叠。布局会尝试自动优雅地管理间距，但仔细设计跨度和断点可防止项目的意外显示。
:::

## 垂直和水平项目对齐 {#vertical-and-horizontal-item-alignments}

`ColumnsLayout` 中的每个项目都可以在其列内水平和垂直对齐，从而控制内容在布局内部的位置。

项目的 **水平对齐** 由 `setHorizontalAlignment()` 方法控制。该属性确定项目如何在其列内沿水平方向对齐。

**垂直对齐** 指定项目在其列内沿垂直方向的位置。此功能在列具有不同高度时非常有用，您希望控制项目的垂直分布。

可用的 `Alignment` 选项包括：

- `START`: 将项目对齐到列的左侧（默认）。
- `CENTER`: 将项目水平居中对齐于列内。
- `END`: 将项目对齐到列的右侧。
- `STRETCH`: 将组件拉伸以填充布局。
- `BASELINE`: 根据列内文本或内容进行对齐，将项目对齐到文本基线而非其他对齐选项。
- `AUTO`: 自动对齐。

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

在上面的演示中，`Submit` 按钮被设置为 `ColumnsLayout.Alignment.END`，以确保它出现在其列的末尾（在此情况下是右侧）。

## 项目间距 {#item-spacing}

控制 `ColumnsLayout` 中列与列之间（水平间距）和行与行之间（垂直间距）的空间有助于开发人员微调布局。

要设置布局的水平间距，请使用 `setHorizontalSpacing()` 方法：

```java
// 设置列之间的间距为20px
layout.setHorizontalSpacing(20);  
```

同样，使用 `setVerticalSpacing()` 方法配置布局中行之间的间距：

```java
// 设置行之间的间距为15px
layout.setVerticalSpacing(15);  
```

:::tip CSS 单位
您可以使用整数定义以像素为单位的最小宽度，或使用 `String` 指定其他单位，例如 `vw`、`%` 或 `em`。
:::

## 水平和垂直布局 {#horizontal-and-vertical-layouts}

使用 [`FlexLayout`](./flex-layout) 组件和 `ColumnsLayout` 组件，以及两者的组合，可以构建响应式和美观的布局。下面是[在 FlexLayout 中创建的表单](./flex-layout#example-form) 文章的示例，但使用 `ColumnLayout` 方案：

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## 样式 {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
