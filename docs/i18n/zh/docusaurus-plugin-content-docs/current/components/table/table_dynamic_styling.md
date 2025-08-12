---
sidebar_position: 21
title: Dynamic Styling
slug: styling
sidebar_class_name: updated-content
_i18n_hash: 38ec04cdf27a7de8a9e51ad24cf66009
---
<!-- vale off -->
# 动态样式 <DocChip chip='since' label='25.00' />
<!-- vale on -->

在 webforJ 25 及更高版本中，可以使用自定义部件名称为表格中的单独行和单元格进行样式设置。这些名称可以根据应用的逻辑动态分配，使您对表格的外观有细致的控制。

## 行样式 {#row-styling}

`setRowPartProvider()` 方法根据其包含的数据项为整个行分配部件名称。这使您能够突出显示符合特定条件的完整行，例如，为偶数行交替背景颜色。

这些样式名称可以使用 CSS 中的 `::part()` 选择器进行目标定位。

:::tip 阴影部件
`::part()` 选择器是一个特殊的 CSS 特性，它允许您为组件的阴影 DOM 中的元素设置样式——只要这些元素公开了 `part` 属性。这对于样式 webforJ 组件的内部部件（例如表格中的行或单元格）尤其有用。

有关阴影部件的工作原理及如何定义和定位它们的更多信息，请参见 [样式](../../styling/shadow-parts) 部分。
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## 单元格样式 {#cell-styling}

`setCellPartProvider()` 方法根据数据项和它所属的列为单个单元格设置样式。这使得突出显示特定值（例如指出低于某个阈值的年龄或无效条目）成为理想选择。

与行部件一样，单元格部件由名称定义并使用 `::part()` 选择器进行目标定位。

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## 响应数据更新 {#reacting-to-data-updates}

如果您的应用程序以编程方式修改数据，例如更新用户的年龄，则在更新项提交到存储库后，表格将自动重新评估并重新应用任何关联的行或单元格样式。

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## 条纹行 {#striped-rows}

启用交替背景颜色的行，以提高可读性：

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

下面的演示展示了一种简单的方法，通过使用 `setStriped()` 和 `setBordersVisible()` 来使您的 `Table` 的视觉外观与应用的其余部分对齐。

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>
