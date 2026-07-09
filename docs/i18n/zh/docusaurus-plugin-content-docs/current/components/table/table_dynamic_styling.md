---
sidebar_position: 21
title: Dynamic Styling
slug: styling
description: >-
  Apply data-driven CSS to Table rows and cells using setRowPartProvider and
  setCellPartProvider with shadow part selectors.
_i18n_hash: 28911597e1e885b0531bb27aa3c8e1a7
---
<!-- vale off -->
# 动态样式 <DocChip chip='since' label='25.00' />
<!-- vale on -->

在 webforJ 25 及更高版本中，可以使用自定义部分名称对表中的单独行和单元格进行样式设置。这些名称可以根据您的应用逻辑动态分配，从而让您对表的外观进行精细控制。

## 行样式 {#row-styling}

`setRowPartProvider()` 方法根据所包含的数据项为整行分配部分名称。这使您可以突出显示满足特定条件的完整行，例如为偶数行交替设置背景颜色。

这些样式名称可以在您的 CSS 中使用 `::part()` 选择器进行定位。

:::tip 阴影部分
`::part()` 选择器是一个特殊的 CSS 特性，可以让您对组件的阴影 DOM 内部的元素进行样式设置，只要这些元素暴露 `part` 属性。这对于样式设置 webforJ 组件的内部部分（如表中的行或单元格）特别有用。

有关阴影部分的工作原理以及如何定义和定位它们，请参见 [Styling](../../styling/shadow-parts) 部分。
:::


<ComponentDemo
path='/webforj/tablerowstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableRowStylingView.java',
  'src/main/frontend/css/table/table-row-styling-view.css',
]}
height='300px'
/>

## 单元格样式 {#cell-styling}

`setCellPartProvider()` 方法根据数据项和它们所属的列来样式化单个单元格。这使其非常适合突出特定值，例如标出低于某一阈值的年龄或无效条目。

像行部分一样，单元格部分由名称定义，并使用 `::part()` 选择器进行定位。

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/frontend/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## 响应数据更新 {#reacting-to-data-updates}

如果您的应用程序以编程方式修改数据，例如更新用户的年龄，则在更新项在存储库中提交后，表将自动重新评估并重新应用任何相关的行或单元格样式。

在此演示中，年龄列中的单元格的样式基于阈值：超过 30 岁的年龄显示为绿色，而 30 岁及以下的年龄显示为红色。点击按钮在 28 和 31 之间切换 Alice 的年龄，当数据提交时，会触发 `setCellPartProvider` 重新应用适当的样式。

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/frontend/css/table/table-dynamic-styling-view.css',
]}
height='475px'
/>

## 斑马条纹行 {#striped-rows}

启用行的交替背景颜色以提高可读性：

```java
// 应用斑马条纹行样式
table.setStriped(true);
```

## 边框 {#borders}

配置 `Table`、列和行周围显示的边框：

```java
// 启用所有边框
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// 移除所有边框
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

下面的演示展示了一种简单的方法，用于使用 `setStriped()` 和 `setBordersVisible()` 将您的 `Table` 的视觉外观与应用的其余部分对齐。

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={['src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java']}
height='300px'
/>

:::tip 管理和查询数据
有关如何使用 `Repository` 模式来管理和查询集合的信息，请参见 [Repository articles](/docs/advanced/repository/overview)。
:::
