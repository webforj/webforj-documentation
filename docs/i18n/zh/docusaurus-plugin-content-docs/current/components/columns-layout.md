---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: fb5b6ef5a20567d8a86d04c022a0449e
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

`ColumnsLayout` 组件在 webforJ 中允许开发者创建使用灵活且响应式的垂直布局的布局。该布局提供动态列，根据可用宽度进行调整。这个组件通过自动管理断点和对齐方式，简化了多列布局的创建。

:::info 水平布局 
此组件可替代或与 [`FlexLayout`](./flex-layout) 组件一起使用——这也是一个用于水平布局的强大工具。
:::

## 基础 {#basics}

首次实例化时，`ColumnsLayout` 使用两列来显示添加到布局中的项。默认情况下，它占据其父元素的全部宽度，并根据需要增长以适应额外内容。通过使用 [`Breakpoint`](./columns-layout#breakpoints) 和 [`Alignment`](./columns-layout#vertical-and-horizontal-item-alignments) 设置，可以进一步调整添加项的显示，这些设置将在以下部分中详细讨论。

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

## 断点 {#breakpoints}

`ColumnsLayout` 的核心设计旨在提供一个流动的、网格式的系统，可以根据其父容器的宽度进行调整。与传统的固定网格系统不同，该布局允许开发者在给定宽度下指定列数，并根据设定的 `Breakpoint` 对象动态计算显示的列数。

这使得 `ColumnsLayout` 能够平滑地从小屏幕的更紧凑空间过渡到大屏幕的更宽区域，为开发者提供响应式设计，而无需自定义实现。

### 理解 `Breakpoint` {#understanding-a-breakpoint}

`Breakpoint` 可以使用 `Breakpoint` 类来指定，该类接收三个参数：

1. **名称（可选）**：
给断点命名使您可以在未来的配置中引用它。

2. **最小宽度**：
每个断点具有特定范围，确定其布局应用的时机。最小宽度明确规定，下一断点如果存在，则确定最大宽度。您可以使用整数来定义最小宽度（以像素为单位）或使用 `String` 来指定其他单位，如 `vw`、`%` 或 `em`。

3. **列数**：
使用此整数指定一个断点应有多少列。

:::info `Breakpoint` 评估
断点按宽度升序评估，意味着布局将使用第一个匹配的断点。
:::


### 应用断点 {#applying-breakpoints}

断点可以在构造时或通过使用 `addBreakpoint(Breakpoint)` 方法来应用，如下所示。

```java
ColumnsLayout layout = new ColumnsLayout()
    // 在宽度 >= 0px 时使用一列
    .addBreakpoint(new Breakpoint(0, 1))
    // 在宽度 >= 600px 时使用两列
    .addBreakpoint(new Breakpoint(600, 2))
    // 在宽度 >= 1200px 时使用四列
    .addBreakpoint(new Breakpoint(1200, 4));  
```

下面的演示展示了在构造时设置多个断点的示例，使用断点配置组件的 [`Span`](#column-span-and-spans-per-breakpoint)，并演示了 `ColumnsLayout` 在应用程序调整大小时的响应能力：

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## 列 `Span` 和每个 `Breakpoint` 的列跨度 {#column-span-and-spans-per-breakpoint}

在 `ColumnsLayout` 中，列跨度允许您控制一个项占据多少列，从而为不同宽度下布局的外观提供更多控制。这在您需要某些元素根据屏幕大小或设计要求占据更多或更少空间时尤其有用。

### 管理列跨度 {#managing-column-spans}

默认情况下，`ColumnsLayout` 中每个项占据恰好一列。然而，您可以通过为单个项设置列跨度来自定义此行为。一个跨度指定一个项应占用的列数。

```java
Button button = new Button("Click Me");
layout.addComponent(button);
// 项占据两列
layout.setSpan(button, 2);
```

在上面的示例中，按钮占据两列，而不是默认的一列。`setSpan()` 方法允许您指定组件在布局中应跨越多少列。

### 根据断点调整列跨度 {#adjusting-column-spans-with-breakpoints}

您还可以根据断点动态调整列跨度。当您希望一个项根据屏幕大小跨越不同数量的列时，此功能非常有用。例如，您可能希望一个元素在移动设备上占据一列，而在大屏幕上跨越多列。

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
//电子邮件字段将在中等断点激活时跨越两列
columnsLayout.setSpan(email, "medium", 2);
//...
```

这种级别的定制确保您的布局在不同设备上保持响应并适当结构化。

## 在列内放置项目 {#placing-items-within-columns}

`ColumnsLayout` 提供了在特定列中放置项目的能力，给予每个元素的排列更多控制。您可以手动指定一个项目在布局中的出现位置，确保重要组件按预期显示。

### 基本列放置 {#basic-column-placement}

默认情况下，项目放置在下一个可用的列中，从左到右填充。然而，您可以覆盖此行为，指定一个项目应放置的确切列。要将项目放置在特定列中，请使用 `setColumn()` 方法。在此示例中，按钮被放置在布局的第二列，而不论其与其他组件的添加顺序：

```java
Button button = new Button("Submit");
layout.addComponent(button);
// 将项放置在第二列
layout.setColumn(button, 2);  
```

### 根据断点调整放置 {#adjusting-placement-per-breakpoint}

与列跨度一样，您可以使用断点根据屏幕大小调整项目的放置。这对于在视口变化时重新排序或重新定位布局中的元素很有用。

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
//当中断点激活时，电子邮件字段将出现在第二列
columnsLayout.setColumn(email, "medium", 2); 
//...
```

在以下演示中，请注意当“中等”断点被触发时，`email` 字段跨越了两列，`confirmPassword` 字段被放置在第一列，而不是默认放置在第二列：

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip 避免较量
当多个项目在具有不同跨度和/或列分配的布局中定位时，确保一行中的项目的总跨度和放置不重叠。布局尝试自动优雅地管理间距，但仔细设计跨度和断点可以防止项目不当显示。
:::

## 垂直和水平项目对齐 {#vertical-and-horizontal-item-alignments}

`ColumnsLayout` 中的每个项都可以在其列内进行水平和垂直对齐，从而控制内容在布局中的定位方式。

**项目的水平对齐** 使用 `setHorizontalAlignment()` 方法控制。此属性决定了项目在其列内沿水平轴的对齐方式。

**垂直对齐** 指定项目在其列内沿垂直轴的位置。这在列高不等时特别有用，您想控制项目的垂直分布。

可用的 `Alignment` 选项包括：

- `START`：将项目对齐到列的左侧（默认）。
- `CENTER`：在列内水平居中对齐项目。
- `END`：将项目对齐到列的右侧。
- `STRETCH`：拉伸组件以填充布局
- `BASELINE`：根据列内的文本或内容进行对齐，将项目对齐到文本基线，而不是其他对齐选项。
- `AUTO`：自动对齐。

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

在上面的演示中，`Submit` 按钮被赋予了 `ColumnsLayout.Alignment.END` 以确保它出现在其列的末尾，或此情况下的右侧。

## 项间距 {#item-spacing}

控制 `ColumnsLayout` 中列之间的空间（水平间距）以及行之间的空间（垂直间距）有助于开发者微调布局。

要设置布局的水平间距，请使用 `setHorizontalSpacing()` 方法：

```java
// 在列之间设置20px的间距
layout.setHorizontalSpacing(20);  
```

同样，使用 `setVerticalSpacing()` 方法来配置布局行之间的空间：

```java
// 在行之间设置15px的间距
layout.setVerticalSpacing(15);  
```

:::tip CSS 单位
您可以使用整数来定义最小宽度（以像素为单位），或使用 `String` 指定其他单位，如 `vw`、`%` 或 `em`。
:::

## 水平和垂直布局 {#horizontal-and-vertical-layouts}

使用 [`FlexLayout`](./flex-layout) 组件和 `ColumnsLayout` 组件，甚至将两者结合在一起，可以构建响应式和美观的布局。以下是使用 `ColumnLayout` 方案的 [在 FlexLayout 中创建的表单示例](./flex-layout#example-form)：

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## 样式 {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
