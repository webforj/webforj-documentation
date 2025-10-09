---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: 8e9f61685fbb3a7fb830463f1320e8cf
---
<!-- vale off -->
# 动态样式 <DocChip chip='since' label='25.00' />
<!-- vale on -->

在 webforJ 25 及更高版本中，可以使用自定义部件名称对表格中的单独行和单元格进行样式设置。这些名称可以根据您的应用程序逻辑动态分配，给您提供对表格外观的细致控制。

## 行样式 {#row-styling}

`setRowPartProvider()` 方法根据其包含的数据项将部件名称分配给整行。这使您可以突出显示符合特定条件的完整行，例如，偶数行的交替背景颜色。

这些样式名称可以在 CSS 中使用 `::part()` 选择器进行定位。

:::tip 阴影部件
`::part()` 选择器是一种特殊的 CSS 功能，允许您样式化组件阴影 DOM 内的元素，只要这些元素暴露了 `part` 属性。这对于样式化 webforJ 组件的内部部分尤其有用，如表格中的行或单元格。

有关阴影部件的工作原理以及如何定义和定位它们的更多信息，请参见 [样式](../../styling/shadow-parts) 部分。
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## 单元格样式 {#cell-styling}

`setCellPartProvider()` 方法基于数据项和它们所属的列来样式化单个单元格。这使其非常适合突出特定值，例如标记出低于阈值的年龄或无效条目。

与行部件一样，单元格部件由名称定义，并使用 `::part()` 选择器进行定位。

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## 响应数据更新 {#reacting-to-data-updates}

如果您的应用程序以编程方式修改数据，例如更新用户的年龄，则在更新项在存储库中提交后，表格将自动重新评估并重新应用任何相关的行或单元格样式。 

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

配置显示在 `Table`、列和行周围的边框：

```java
// 启用所有边框
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// 移除所有边框
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

下面的演示展示了一种简单的方法，通过使用 `setStriped()` 和 `setBordersVisible()` 将 `Table` 的视觉外观与您应用的其余部分对齐。

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>

:::tip 管理和查询数据
有关如何使用 `Repository` 模式来管理和查询集合的信息，请参见 [Repository 文章](/docs/advanced/repository/overview)。
:::
