---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: f4d9229ae204894cda7263a6dc09ba0c
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

`ColumnsLayout` 组件将项目安排为基于响应式列的布局，布局会根据可用宽度进行调整。断点和对齐方式是自动管理的，因此构建多列表单和内容网格无需自定义响应逻辑。

<!-- INTRO_END -->

## 默认行为 {#default-behavior}

默认情况下，`ColumnsLayout` 将项目排列为两列，并占用其父元素的全部宽度。显示方式可以通过下面的部分调整断点和对齐设置。

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

:::info 水平布局 
这可以替代或与 [`FlexLayout`](./flex-layout) 组件结合使用——一个同样强大的水平布局工具。
:::

## 断点 {#breakpoints}

从本质上讲，`ColumnsLayout` 旨在提供一个流体的、网格状的系统，能够适应其父容器的宽度。与传统的固定网格系统不同，该布局允许开发者在给定宽度下指定列数，并根据设置的 `Breakpoint` 对象动态计算显示的列数。

这使得 `ColumnsLayout` 能够从小屏幕上更为受限的空间，平滑适应到更大屏幕上的更宽区域，为开发者提供响应式设计，而无需自定义实现。

### 理解 `Breakpoint` {#understanding-a-breakpoint}

可以使用 `Breakpoint` 类来指定 `Breakpoint`，该类接受三个参数：

1. **名称（可选）**：
为断点命名使得您能够在以后的配置中引用它。

2. **最小宽度**：
每个断点都有一个特定范围，决定何时应用其布局。最小宽度是明确规定的，接下来的断点决定最大宽度（如果存在）。您可以使用整数以像素为单位定义最小宽度，或使用 `String` 指定其他单位，如 `vw`、`%` 或 `em`。

3. **列数**：
使用该整数指定断点应有多少列。


:::info `Breakpoint` 评估
断点是按宽度升序评估的，这意味着布局将使用第一个匹配的断点。
:::


### 应用断点 {#applying-breakpoints}

在构造期间，或使用 `setBreakpoints()` 方法在 `List` 中应用断点：

```java
ColumnsLayout layout = new ColumnsLayout();

List<Breakpoint> breakpoints = List.of(
    // 在宽度 >= 0px 时，列数为一列
    new Breakpoint(0, 1),
    // 在宽度 >= 600px 时，列数为两列
    new Breakpoint(600, 2),
    // 在宽度 >= 1200px 时，列数为四列
    new Breakpoint(1200, 4));

layout.setBreakpoints(breakpoints);
```

下面的演示显示了在构造时设置多个断点的示例，使用断点配置 [`Span`](#column-span-and-spans-per-breakpoint) 组件，以及演示了在调整应用大小时 `ColumnsLayout` 的调整能力：

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## 列 `Span` 和每个 `Breakpoint` 的跨度 {#column-span-and-spans-per-breakpoint}

`ColumnsLayout` 中的列跨度允许您控制一个项目占据多少列，给您在不同宽度下的布局外观上提供更多控制。这在您需要某些元素根据屏幕大小或设计要求占据更多或更少空间时特别有用。

### 管理列跨度 {#managing-column-spans}

默认情况下，`ColumnsLayout` 中的每个项目占用正好一列。然而，您可以通过为单独的项目设置列跨度来自定义此行为。跨度指定项目应占据的列数。

```java
Button button = new Button("Click Me");
layout.addComponent(button);
// 项目跨越两列
layout.setSpan(button, 2);
```

在上述示例中，按钮占用了两列，而不是默认的一列。`setSpan()` 方法允许您指定组件在布局中应跨越多少列。

### 使用断点调整列跨度 {#adjusting-column-spans-with-breakpoints}

您还可以根据断点动态调整列跨度。此功能在您希望根据屏幕大小使项目跨越不同数量的列时非常有用。例如，您可能希望某个元素在移动设备上占用一列，但在更大屏幕上跨越多列。

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

这种级别的自定义确保您的布局在不同设备上保持响应和适当结构化。

## 在列中放置项目 {#placing-items-within-columns}

`ColumnsLayout` 提供了在特定列中放置项目的能力，进一步控制元素的排布。您可以手动指定一个项目应在布局中的位置，确保重要组件按预期显示。

### 基本列放置 {#basic-column-placement}

默认情况下，项目放置在下一个可用列中，从左到右填充。然而，您可以覆盖此行为，并指定一个项目应放置的确切列。要在特定列中放置项目，请使用 `setColumn()` 方法。在此示例中，按钮被放置在布局的第二列，无论它相对于其他组件添加的顺序如何：

```java
Button button = new Button("Submit");
layout.addComponent(button);
// 将项目放置在第二列
layout.setColumn(button, 2);  
```

### 根据断点调整放置位置 {#adjusting-placement-per-breakpoint}

就像列跨度一样，您可以使用断点根据屏幕大小调整项目的放置。这在调整布局时很有用，随着视口的变化可以重新排序或重新放置元素。

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

在下面的演示中，请注意当 `"medium"` 断点被触发时，`email` 字段跨越两列，而 `confirmPassword` 字段则放置在第一列，而不是其在第二列的默认位置：

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip 避免冲突
当多个项目放置在具有不同跨度和/或列分配的布局中时，确保一行中项目的组合跨度和放置不会重叠。布局尝试自动优雅地管理间距，但仔细设计跨度和断点可以防止意外显示项目。
:::

## 垂直和水平项目对齐 {#vertical-and-horizontal-item-alignments}

`ColumnsLayout` 中的每个项目都可以在其列中进行水平和垂直对齐，控制内容在布局中的位置。

**项目的水平对齐**通过使用 `setHorizontalAlignment()` 方法来控制。此属性决定项目在其列中的水平对齐方式。

**垂直对齐**指定项目在其列中的垂直定位。这在列具有不同高度时非常有用，因为您希望控制项目的垂直分布。

可用的 `Alignment` 选项包括：

- `START`：将项目对齐到列的左侧（默认）。
- `CENTER`：在列中水平居中。
- `END`：将项目对齐到列的右侧。
- `STRETCH`：拉伸组件以填充布局
- `BASELINE`：基于列中内容的文本或内容进行对齐，将项目对齐到文本基线，而不是其他对齐选项。
- `AUTO`：自动对齐。

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

在上面的演示中，`Submit` 按钮被设置为 `ColumnsLayout.Alignment.END`，以确保它出现在其列的末尾，或者在这种情况下靠右。

## 项目间距 {#item-spacing}

控制 `ColumnsLayout` 中列与列之间（水平间距）和行与行之间（垂直间距）的空间帮助开发者微调布局。

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
您可以使用一个整数定义最小宽度（以像素为单位），或使用 `String` 指定其他单位，如 `vw`、`%` 或 `em`。
:::

## 水平和垂直布局 {#horizontal-and-vertical-layouts}

使用 [`FlexLayout`](./flex-layout) 组件和 `ColumnsLayout` 组件以及两者的组合构建响应式和吸引人的布局是可能的。以下是 [FlexLayout](./flex-layout#example-form) 文章中创建的表单的示例，但使用 `ColumnLayout` 方案：

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## 样式 {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
