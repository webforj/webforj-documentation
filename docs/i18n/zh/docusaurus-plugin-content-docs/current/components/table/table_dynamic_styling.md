---
sidebar_position: 21
title: 动态样式
slug: styling
description: >-
  Apply data-driven CSS to Table rows and cells using setRowPartProvider and
  setCellPartProvider with shadow part selectors.
_i18n_hash: 6d82241fb67ffa1ce5a1e8c974a96dea
---
<!-- vale off -->
# 动态样式 <DocChip chip='since' label='25.00' />
<!-- vale on -->

在 webforJ 25 及更高版本中，可以使用自定义部件名称为 Table 中的各行和单元格单独设置样式。您可以根据应用逻辑动态分配这些名称，从而精细控制表格的外观。

## 行样式 {#row-styling}

`setRowPartProvider()` 方法会根据行中包含的数据项，为整行分配部件名称。这样，您就可以突出显示符合特定条件的整行，例如为偶数行设置交替的背景颜色。

您可以在 CSS 中使用 `::part()` 选择器来定位这些样式名称。

:::tip Shadow parts
`::part()` 选择器是一项特殊的 CSS 功能，可用于设置组件 shadow DOM 内元素的样式，前提是这些元素公开了 `part` 属性。这对于设置 webforJ 组件内部部件的样式尤其有用，例如表格中的行或单元格。

有关 shadow parts 的工作原理，以及如何定义和定位它们，请参阅 [样式设置](../../styling/shadow-parts) 部分。
:::


<ComponentDemo
path='/webforj/tablerowstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableRowStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-row-styling-view.css',
]}
height='300px'
/>

## 单元格样式 {#cell-styling}

`setCellPartProvider()` 方法会根据数据项及其所属列，为单个单元格设置样式。这非常适合突出显示特定值，例如标记超过某个阈值的年龄或无效条目。

与行部件一样，单元格部件也通过名称定义，并使用 `::part()` 选择器定位。

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableCellStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## 响应数据更新 {#reacting-to-data-updates}

如果应用以编程方式修改数据，例如更新用户的年龄，那么更新后的数据项提交到存储库后，表格会自动重新评估并应用相关的行或单元格样式。

在此演示中，Age 列中的单元格会根据阈值设置样式：年龄大于 30 的单元格显示为绿色，年龄小于或等于 30 的单元格显示为红色。点击按钮可在 28 岁和 31 岁之间切换 Alice 的年龄；数据提交后，会触发 `setCellPartProvider` 重新应用相应的样式。

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-dynamic-styling-view.css',
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

配置 `Table`、列和行周围显示哪些边框：

```java
// 启用所有边框
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// 移除所有边框
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

下面的演示展示了如何使用 `setStriped()` 和 `setBordersVisible()`，使 `Table` 的视觉外观与应用的其余部分保持一致。

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
]}
height='300px'
/>

:::tip 管理和查询数据
有关如何使用 `Repository` 模式管理和查询集合的信息，请参阅 [Repository 相关文章](/docs/advanced/repository/overview)。
:::
