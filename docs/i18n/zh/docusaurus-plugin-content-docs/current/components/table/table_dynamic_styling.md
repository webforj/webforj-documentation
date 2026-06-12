---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: 8f910c729d1108faeaba860a2e0f3546
---
<!-- vale off -->
# 动态样式 <DocChip chip='since' label='25.00' />
<!-- vale on -->

在 webforJ 25 及以上版本中，可以使用自定义部分名称为表格中的单行和单元格设置样式。可以根据应用程序的逻辑动态分配这些名称，从而对表格的外观进行细致控制。

## 行样式 {#row-styling}

`setRowPartProvider()` 方法根据包含的数据项为整个行分配部分名称。这使您可以突出显示符合特定条件的完整行，例如，为偶数行交替背景颜色。

这些样式名称可以在 CSS 中使用 `::part()` 选择器进行定位。

:::tip 阴影部分
`::part()` 选择器是一种特殊的 CSS 功能，允许您为组件的阴影 DOM 内的元素设置样式——只要这些元素公开了 `part` 属性。这对于样式化 webforJ 组件的内部部分尤其有用，例如表格中的行或单元格。

有关阴影部分的工作原理以及如何定义和定位它们的更多信息，请参见 [样式]（../../styling/shadow-parts）部分。
:::


<ComponentDemo
path='/webforj/tablerowstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableRowStylingView.java',
  'src/main/resources/static/css/table/table-row-styling-view.css',
]}
height='300px'
/>

## 单元格样式 {#cell-styling}

`setCellPartProvider()` 方法根据数据项及其所属的列为单个单元格设置样式。这非常适合突出显示特定值，例如调用超过阈值的年龄或无效条目。

与行部分一样，单元格部分也通过名称定义，并使用 `::part()` 选择器进行定位。

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/resources/static/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## 对数据更新的反应 {#reacting-to-data-updates}

如果您的应用程序以编程方式修改数据，例如更新用户的年龄，则表格将在仓库中提交更新项后自动重新评估并重新应用任何关联的行或单元格样式。

在此演示中，年龄列中的单元格根据阈值进行样式化：超过 30 岁的年龄显示为绿色，而 30 岁及以下显示为红色。单击按钮将在 28 和 31 之间切换 Alice 的年龄，触发 `setCellPartProvider` 在数据提交时重新应用适当的样式。

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/resources/static/css/table/table-dynamic-styling-view.css',
]}
height='475px'
/>

## 条纹行 {#striped-rows}

启用交替背景颜色以提高行的可读性：

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

下面的演示展示了一种简单的方法，通过 `setStriped()` 和 `setBordersVisible()` 来使您的 `Table` 的视觉外观与应用的其余部分对齐。

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={['src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java']}
height='300px'
/>

:::tip 管理和查询数据
有关如何使用 `Repository` 模式来管理和查询集合的信息，请参见 [Repository 文章](/docs/advanced/repository/overview)。
:::
