---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: a1c9e9a41325f2f1ffb75fd07204106a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

`ColumnsLayout` 组件将项目排列成响应式的基于列的布局，能够根据可用宽度进行调整。断点和对齐方式会自动管理，因此构建多列表单和内容网格不需要自定义响应式逻辑。

<!-- INTRO_END -->

## 默认行为 {#default-behavior}

默认情况下，`ColumnsLayout` 将项目排列为两列，并占据其父级的全部宽度。可以进一步通过断点和对齐设置进行调整，具体内容在下面的章节中说明。

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

:::info 水平布局 
这可以替代 [`FlexLayout`](./flex-layout) 组件使用，或者与其结合使用，它同样是用于水平布局的强大工具。
:::

## 断点 {#breakpoints}

`ColumnsLayout` 的核心设计是提供一个流畅的、类似网格的系统，能够适应其父容器的宽度。与传统的固定网格系统不同，此布局允许开发人员在给定宽度下指定列数，并根据设置的 `Breakpoint` 对象动态计算显示的列数。

这使得 `ColumnsLayout` 能够在小屏幕上从更受限的空间自如适应到大屏幕上的更宽区域，为开发人员提供响应式设计，而无需自定义实现。

### 理解 `Breakpoint` {#understanding-a-breakpoint}

可以使用 `Breakpoint` 类来指定 `Breakpoint`，该类需要三个参数：

1. **名称（可选）**：
为断点命名允许在未来的配置中引用它。

2. **最小宽度**：
每个断点都有一个特定范围，决定何时应用其布局。最小宽度是明确规定的，下一个断点决定最大宽度（如果存在）。可以使用整数来定义最小宽度（以像素为单位），或者使用 `String` 来指定其他单位，如 `vw`，`%` 或 `em`。

3. **列数**：
使用该整数指定断点应有多少列。

:::info `Breakpoint` 评估
断点按宽度的升序进行评估，这意味着布局将使用第一个匹配的断点。
:::

### 应用断点 {#applying-breakpoints}

断点通过两种方式应用于 `ColumnsLayout`：在构造期间或使用 `setBreakpoints()` 方法在 `List` 中：

```java
ColumnsLayout layout = new ColumnsLayout();

List<Breakpoint> breakpoints = List.of(
  // 栏宽 >= 0px 时一列
  new Breakpoint(0, 1),
  // 栏宽 >= 600px 时两列
  new Breakpoint(600, 2),
  // 栏宽 >= 1200px 时四列
  new Breakpoint(1200, 4));

layout.setBreakpoints(breakpoints);
```

下面的演示展示了在构造时设置多个断点的示例，使用断点来配置 [`Span`](#column-span-and-spans-per-breakpoint) 的组件，并演示了当应用程序调整大小时 `ColumnsLayout` 的调整能力：

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## 列 `Span` 和每个 `Breakpoint` 的跨度 {#column-span-and-spans-per-breakpoint}

在 `ColumnsLayout` 中，列跨度允许您控制项占据多少列，从而在不同宽度下更好地控制布局外观。当需要某些元素根据屏幕大小或设计要求占用更多或更少空间时，这一点特别有用。

### 管理列跨度 {#managing-column-spans}

默认情况下，`ColumnsLayout` 中的每个项占据正好一列。然而，可以通过为单个项设置列跨度来自定义此行为。跨度指定项应占据多少列。

```java
Button button = new Button("Click Me");
layout.addComponent(button);
// 项占据两列
layout.setSpan(button, 2);
```

在上面的示例中，按钮占据两个列，而不是默认的一列。`setSpan()` 方法允许您指定组件在布局中应跨越多少列。

### 根据断点调整列跨度 {#adjusting-column-spans-with-breakpoints}

您还可以根据断点动态调整列跨度。当您希望某个项根据屏幕大小跨越不同数量的列时，此功能非常有用。例如，您可能希望一个元素在移动设备上占据一列，但在更大屏幕上跨越多列。

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
//email 字段将在中等断点激活时跨越两列
columnsLayout.setSpan(email, "medium", 2);
//...
```

这种级别的自定义确保您的布局在不同设备之间仍然是响应式的且结构合理。

## 在列中放置项目 {#placing-items-within-columns}

`ColumnsLayout` 提供了在特定列中放置项目的能力，使您能更好地控制元素的排列。您可以手动指定项目在布局中的出现位置，以确保重要组件按预期显示。

### 基本列放置 {#basic-column-placement}

默认情况下，项目按顺序放置在下一个可用列，从左到右填充。然而，您可以覆写此行为，指定项目应放置的确切列。要将项目放置在特定列中，请使用 `setColumn()` 方法。在此示例中，按钮被放置在布局的第二列，无论它相对于其他组件的添加顺序：

```java
Button button = new Button("Submit");
layout.addComponent(button);
// 将项目放置在第二列
layout.setColumn(button, 2);  
```

### 根据断点调整放置 {#adjusting-placement-per-breakpoint}

与列跨度一样，您可以使用断点根据屏幕大小调整项目的放置。这对于在视口变化时重新排序或重新定位布局中的元素非常有用。

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
//email 字段将在中等断点激活时出现在第二列
columnsLayout.setColumn(email, "medium", 2); 
//...
```

在下面的演示中，请注意当 `"medium"` 断点被触发时，`email` 字段跨越了两个列，而 `confirmPassword` 字段被放置在第一列，而不是其默认位置在第二列：

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip 避免冲突
当多个项目在具有不同跨度和/或列分配的布局中放置时，请确保一行中项目的组合跨度和放置不重叠。布局尝试优雅地自动管理间距，但仔细设计跨度和断点可以防止意外显示项目。
:::

## 垂直和水平项目对齐 {#vertical-and-horizontal-item-alignments}

`ColumnsLayout` 中的每个项目都可以在其列内水平和垂直对齐，从而控制内容在布局内的定位。

**项目的水平对齐** 使用 `setHorizontalAlignment()` 方法控制。此属性决定项在其列内沿水平轴的对齐方式。

**垂直对齐** 指定项在其列内沿垂直轴的位置。当列的高度各异时，这一点尤其有用，因为您希望控制项目的垂直分布。

可用的 `Alignment` 选项包括：

- `START`：将项目对齐到列的左侧（默认）。
- `CENTER`：将项目在列内水平居中。
- `END`：将项目对齐到列的右侧。
- `STRETCH`：将组件拉伸以填充布局。
- `BASELINE`：根据列中内容或文本对齐项目，将项目对齐到文本基线，而不是其他对齐选项。
- `AUTO`：自动对齐。

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

在上面的演示中，`Submit` 按钮被设置为 `ColumnsLayout.Alignment.END` 以确保它出现在其列的末尾，也就是在这种情况下向右。

## 项目间距 {#item-spacing}

控制 `ColumnsLayout` 中列之间（水平间距）和行之间（垂直间距）的空间有助于开发人员微调布局。

要设置布局的水平间距，请使用 `setHorizontalSpacing()` 方法：

```java
// 设置列之间的空间为20px
layout.setHorizontalSpacing(20);  
```

同样，使用 `setVerticalSpacing()` 方法配置布局的行间距：

```java
// 设置行之间的空间为15px
layout.setVerticalSpacing(15);  
```

:::tip CSS 单位
您可以使用整数定义最小宽度（以像素为单位），或使用 `String` 指定其他单位，如 `vw`，`%` 或 `em`。
:::

## 水平和垂直布局 {#horizontal-and-vertical-layouts}

通过使用 [`FlexLayout`](./flex-layout) 组件和 `ColumnsLayout` 组件，以及两者的组合，可以构建响应式和吸引人的布局。下面是使用 `ColumnLayout` 方案的 [FlexLayout 中创建的表单示例](./flex-layout#example-form)：

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## 样式 {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
