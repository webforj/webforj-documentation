---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: ab74c802642742faeaa38ee9a2f6e8da
---
<!-- vale off -->
# 动态样式 <DocChip chip='since' label='25.00' />
<!-- vale on -->

在 webforJ 25 及更高版本中，可以使用自定义部分名称为表格中的单行和单元格设置样式。这些名称可以根据应用程序的逻辑动态分配，使您能够对表格的外观进行细致控制。

## 行样式 {#row-styling}

`setRowPartProvider()` 方法根据所包含的数据项分配整个行的部分名称。这使您能够突出显示满足特定条件的完整行，例如，为偶数行交替设置背景颜色。

这些样式名称可以在您的 CSS 中使用 `::part()` 选择器进行定位。

:::tip 影子部分
`::part()` 选择器是一种特殊的 CSS 特性，它允许您样式化组件影子 DOM 内的元素，只要这些元素公开了 `part` 属性。这对于样式化 webforJ 组件的内部部分（如表格中的行或单元格）尤其有用。

有关影子部分工作原理以及如何定义和定位它们的更多信息，请参阅 [样式](../../styling/shadow-parts) 部分。
:::

<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## 单元格样式 {#cell-styling}

`setCellPartProvider()` 方法根据数据项和它们所属的列为单个单元格设置样式。这使得高亮显示特定值（例如，突出显示某一阈值之前的年龄或无效条目）成为理想选择。

与行部分一样，单元格部分也是通过名称定义的，并使用 `::part()` 选择器进行定位。

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## 对数据更新的反应 {#reacting-to-data-updates}

如果您的应用程序以编程方式修改数据，例如更新用户的年龄，则在更新的项目在存储库中提交后，表格将自动重新评估并重新应用任何相关的行或单元格样式。

在这个演示中，年龄列中的单元格样式是根据阈值设置的：30 岁以上的年龄显示为绿色，而 30 岁及以下的年龄显示为红色。点击按钮会在 28 和 31 之间切换 Alice 的年龄，当数据提交时，会触发 `setCellPartProvider` 重新应用适当的样式。

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

配置 `Table`、列和行周围显示的边框：

```java
// 启用所有边框
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// 移除所有边框
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

下面的演示展示了一种简单的方法，使用 `setStriped()` 和 `setBordersVisible()` 将您的 `Table` 的视觉外观与应用的其他部分对齐。

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>

:::tip 管理和查询数据
有关如何使用 `Repository` 模式管理和查询集合的信息，请参阅 [Repository 文章](/docs/advanced/repository/overview)。
:::
