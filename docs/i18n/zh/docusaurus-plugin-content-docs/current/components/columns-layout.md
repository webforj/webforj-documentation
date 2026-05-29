---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: 27b0727ced855ad047db6be3e142801f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

`ColumnsLayout` 组件将项目排列成响应式的基于列的布局，该布局根据可用宽度进行调整。断点和对齐方式由系统自动管理，因此构建多列表单和内容网格时不需要自定义响应逻辑。

<!-- INTRO_END -->

## 默认行为 {#default-behavior}

默认情况下，`ColumnsLayout` 将项目排列成两列，并占据其父元素的全部宽度。可以通过断点和对齐设置进一步调整显示，具体内容在下面的章节中介绍。

<ComponentDemo
path='/webforj/columnslayout'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java']}
height='450px'
/>

:::info 水平布局
这可以替代或与 [`FlexLayout`](./flex-layout) 组件结合使用 - 这是一个功能同样强大的水平布局工具。
:::

## 断点 {#breakpoints}

在其核心，`ColumnsLayout` 旨在提供一个流体的、网格状的系统，能够适应其父容器的宽度。与传统的固定网格系统不同，此布局允许开发人员在给定宽度下指定列数，并动态计算基于设置的 `Breakpoint` 对象的显示列数。

这使得 `ColumnsLayout` 能够平滑地适应小屏幕上的约束空间到大屏幕上的更宽区域，为开发人员提供响应设计，而无需自定义实现。

### 理解 `Breakpoint` {#understanding-a-breakpoint}

可以使用 `Breakpoint` 类指定 `Breakpoint`，该类接受三个参数：

1. **名称（可选）**：
为断点命名允许您在未来的配置中引用它。

2. **最小宽度**：
每个断点都有一个特定的范围，用于确定何时应用其布局。最小宽度显式定义，下一断点确定最大宽度（如果存在）。您可以使用整数来定义以像素为单位的最小宽度，也可以使用 `String` 指定其他单位，例如 `vw`、`%` 或 `em`。

3. **列数**：
指定断点应具有多少列，使用此整数。

:::info `Breakpoint` 评估
断点按宽度的升序评估，这意味着布局将使用第一个匹配的断点。
:::


### 应用断点 {#applying-breakpoints}

断点可以在构造期间或在使用 `setBreakpoints()` 方法的 `List` 中应用于 `ColumnsLayout`：

```java
ColumnsLayout layout = new ColumnsLayout();

List<Breakpoint> breakpoints = List.of(
  // 在宽度 >= 0px 时为一列
  new Breakpoint(0, 1),
  // 在宽度 >= 600px 时为两列
  new Breakpoint(600, 2),
  // 在宽度 >= 1200px 时为四列
  new Breakpoint(1200, 4));

layout.setBreakpoints(breakpoints);
```

下面的演示展示了在构造期间设置多个断点的示例，使用断点配置 [`Span`](#column-span-and-spans-per-breakpoint) 组件，并演示在应用程序调整大小时 `ColumnsLayout` 的调整能力：

<ComponentDemo
path='/webforj/columnslayoutbreakpoints'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java']}
height='375px'
/>

## 列 `Span` 和每个 `Breakpoint` 的跨度 {#column-span-and-spans-per-breakpoint}

`ColumnsLayout` 中的列跨度允许您控制项目占据多少列，从而更好地控制在不同宽度下布局的外观。当您需要某些元素根据屏幕大小或设计要求占用更多或更少的空间时，这尤其有用。

### 管理列跨度 {#managing-column-spans}

默认情况下，`ColumnsLayout` 中的每个项目恰好占据一列。然而，您可以通过为单个项目设置列跨度来自定义此行为。跨度指定一个项目应占据的列数。

```java
Button button = new Button("Click Me");
layout.addComponent(button);
// 项目占据两列
layout.setSpan(button, 2);
```

在上面的示例中，按钮占据了两列而不是默认的一列。`setSpan()` 方法允许您指定组件在布局中应跨越多少列。

### 根据断点调整列跨度 {#adjusting-column-spans-with-breakpoints}

您还可以根据断点动态调整列跨度。当您希望一个项目根据屏幕大小跨越不同数量的列时，此功能非常有用。例如，您可能希望在移动设备上占据一列，但在更大屏幕上跨越多列。

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
// 当中等断点处于活动状态时，email 字段将跨越两列
columnsLayout.setSpan(email, "medium", 2);
//...
```

这种级别的自定义确保了您的布局在不同设备上保持响应性和适当的结构。

## 在列中放置项目 {#placing-items-within-columns}

`ColumnsLayout` 提供了在特定列中放置项目的能力，为元素的排列提供了更多控制。您可以手动指定项目在布局中应该出现的位置，以确保重要组件按预期显示。

### 基本列放置 {#basic-column-placement}

默认情况下，项目放置在下一个可用列中，按从左到右填充。然而，您可以覆盖此行为，指定项目应该放置的确切列。要将项目放置在特定列中，请使用 `setColumn()` 方法。在此示例中，按钮将被放置在布局的第二列，而不管它相对于其他组件的添加顺序：

```java
Button button = new Button("Submit");
layout.addComponent(button);
// 将该项目放置在第二列
layout.setColumn(button, 2);  
```

### 根据断点调整放置位置 {#adjusting-placement-per-breakpoint}

与列跨度一样，您可以使用断点根据屏幕大小调整项目的位置。这对于在视口变化时重新排序或重新定位布局中的元素非常有用。

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
// 当中等断点处于活动状态时，email 字段将出现在第二列
columnsLayout.setColumn(email, "medium", 2); 
//...
```

在下面的演示中，请注意，当触发 `"medium"` 断点时，`email` 字段占据了两列，而 `confirmPassword` 字段则放置在第一列，而不是默认放置在第二列：

<ComponentDemo
path='/webforj/columnslayoutspancolumn'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java']}
height='375px'
/>

:::tip 避免冲突
当多个项目放置在具有不同跨度和/或列分配的布局中时，请确保一行中项目的组合跨度和放置不会重叠。布局试图自动管理间距，但仔细设计跨度和断点可以防止项目的不当显示。
:::

## 垂直和水平项目对齐 {#vertical-and-horizontal-item-alignments}

`ColumnsLayout` 中的每个项目都可以在其列内水平和垂直对齐，控制内容在布局中的位置。

**项目的水平对齐** 使用 `setHorizontalAlignment()` 方法进行控制。此属性确定项目在水平轴上如何在其列内对齐。

**垂直对齐** 指定项目在其列内沿垂直轴的位置。这在列高度不一时尤为有用，您希望控制项目的垂直分布。

可用的 `Alignment` 选项包括：

- `START`: 将项目左对齐（默认）。
- `CENTER`: 在列内水平居中项目。
- `END`: 将项目右对齐。
- `STRETCH`: 将组件扩展以填充布局
- `BASELINE`: 根据列内文本或内容进行对齐，以文本基线而不是其他对齐选项对齐项目。
- `AUTO`: 自动对齐。

<ComponentDemo
path='/webforj/columnslayoutalignment'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java']}
height='500px'
/>

在上面的演示中，`Submit` 按钮被设置为 `ColumnsLayout.Alignment.END`，以确保它出现在其列的末尾或右侧。

## 项目间距 {#item-spacing}

控制 `ColumnsLayout` 中列间的空间（水平间距）和行间的空间（垂直间距）有助于开发人员微调布局。

要设置布局的水平间距，请使用 `setHorizontalSpacing()` 方法：

```java
// 设置列间距为 20px
layout.setHorizontalSpacing(20);  
```

同样，使用 `setVerticalSpacing()` 方法配置布局行间的空间：

```java
// 设置行间距为 15px
layout.setVerticalSpacing(15);  
```

:::tip CSS 单位
您可以使用整数来定义以像素为单位的最小宽度，也可以使用 `String` 指定其他单位，例如 `vw`、`%` 或 `em`。
:::

## 水平和垂直布局 {#horizontal-and-vertical-layouts}

使用 [`FlexLayout`](./flex-layout) 组件和 `ColumnsLayout` 组件，以及两者的组合，可以构建响应式和美观的布局。以下是使用 `ColumnLayout` 方案创建的 [FlexLayout 中创建的表单示例](./flex-layout#example-form)：

<ComponentDemo
path='/webforj/columnslayoutform'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java']}
height='700px'
/>

## 样式 {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
