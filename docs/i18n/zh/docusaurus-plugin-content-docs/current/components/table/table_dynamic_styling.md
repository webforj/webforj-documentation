---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: 03c0fb81b4bcabef5db6dfb9785eef3d
---
<!-- vale off -->
# 动态样式 <DocChip chip='since' label='25.00' />
<!-- vale on -->

在 webforJ 25 及更高版本中，可以使用自定义部分名称来样式化表格中的单独行和单元格。这些名称可以根据您的应用逻辑动态分配，使您能够精细控制表格的外观。

## 行样式 {#row-styling}

`setRowPartProvider()` 方法根据其包含的数据项为整行分配部分名称。这使您可以突出显示符合特定条件的整行，例如，偶数行的交替背景颜色。

这些样式名称可以在您的 CSS 中使用 `::part()` 选择器进行目标定位。

:::tip 阴影部分
`::part()` 选择器是一个特殊的 CSS 功能，允许您样式化组件阴影 DOM 内部的元素——只要这些元素暴露出 `part` 属性。这对于样式化 webforJ 组件的内部部分特别有用，例如表格中的行或单元格。

有关阴影部分如何工作以及如何定义和定位它们的更多信息，请参见 [样式](../../styling/shadow-parts) 部分。
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
cssURL='/css/table/table-row-styling-view.css'
height='300px'
/>

## 单元格样式 {#cell-styling}

`setCellPartProvider()` 方法根据数据项和它们所属的列来样式化单个单元格。这使得突出显示特定值（例如，突出显示超过阈值的年龄或无效条目）变得理想。

像行部分一样，单元格部分由一个名称定义，并使用 `::part()` 选择器进行目标定位。

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
cssURL='/css/table/table-cell-styling-view.css'
height='300px'
/>

## 响应数据更新 {#reacting-to-data-updates}

如果您的应用程序以编程方式修改数据，例如更新用户的年龄，则在更新的项目提交到仓库后，表格将自动重新评估并重新应用任何相关的行或单元格样式。

在这个演示中，年龄列的单元格根据阈值样式化：超过 30 岁的年龄显示为绿色，而 30 岁及以下的年龄显示为红色。单击按钮会在 28 岁和 31 岁之间切换爱丽丝的年龄，当数据被提交时，触发 `setCellPartProvider` 重新应用适当的样式。

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
cssURL='/css/table/table-dynamic-styling-view.css'
height='475px'
/>

## 条纹行 {#striped-rows}

启用行的交替背景颜色，以提高可读性：

```java
// 应用条纹行样式
table.setStriped(true);
```

## 边框 {#borders}

配置围绕 `Table`、列和行显示的边框：

```java
// 启用所有边框
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// 移除所有边框
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

下面的演示展示了一种简单的方法，通过使用 `setStriped()` 和 `setBordersVisible()` 将您的 `Table` 的视觉外观与您应用的其余部分对齐。

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>

:::tip 管理和查询数据
有关如何使用 `Repository` 模式来管理和查询集合的信息，请参见 [仓库文章](/docs/advanced/repository/overview)。
:::
