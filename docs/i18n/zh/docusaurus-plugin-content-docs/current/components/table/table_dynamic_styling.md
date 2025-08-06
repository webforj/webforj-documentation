---
sidebar_position: 21
title: Dynamic Styling
slug: styling
sidebar_class_name: updated-content
_i18n_hash: c958a549dfbac715dfce9f26d729f106
---
<!-- vale off -->
# 动态样式 <DocChip chip='since' label='25.00' />
<!-- vale on -->

在 webforJ 25 及更高版本中，可以使用自定义部件名称对表格中的单行和单元格进行样式调整。这些名称可以根据您应用程序的逻辑动态分配，让您对表格的外观进行精细控制。

## 行样式 {#row-styling}

`setRowPartProvider()` 方法根据包含的数据项为整行分配部件名称。这使您可以突出显示满足特定条件的完整行，例如偶数行交替的背景颜色。

这些样式名称可以在您的 CSS 中使用 `::part()` 选择器进行定位。

:::tip Shadow parts
`::part()` 选择器是一种特殊的 CSS 特性，允许您为组件的影子 DOM 中的元素设置样式，只要这些元素暴露 `part` 属性。这对于样式调整 webforJ 组件的内部部件，如表格中的行或单元格，特别有用。

有关影子部件的工作原理以及如何定义和定位它们，请参见 [Styling](../../styling/shadow-parts) 部分。
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## 单元格样式 {#cell-styling}

`setCellPartProvider()` 方法根据数据项和它们所属的列对单个单元格进行样式设置。这使其非常适合突出显示特定值，例如标记超过阈值的年龄或无效条目。

与行部件类似，单元格部件通过名称定义，并使用 `::part()` 选择器进行定位。

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## 响应数据更新 {#reacting-to-data-updates}

如果您的应用程序以编程方式修改数据，例如更新用户的年龄，则在所更新的项在存储库中提交后，表格将自动重新评估并重新应用任何相关的行或单元格样式。

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## 条纹行 {#striped-rows}

启用行的交替背景颜色以提高可读性：

```java
// 应用条纹行样式
table.setStriped(true);
```

## 边框 {#borders}

配置在 `Table`、列和行周围显示的边框：

```java
// 启用所有边框
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// 移除所有边框
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

下面的演示展示了一种简单的方法，通过使用 `setStriped()` 和 `setBordersVisible()` 将您的 `Table` 的视觉外观与您应用程序的其余部分对齐。

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>
