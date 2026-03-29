---
title: Column Groups
sidebar_position: 7
sidebar_class_name: new-content
_i18n_hash: fccec5a60bfd614d344758d3624bc602
---
<DocChip chip='since' label='25.12' />

列组允许您在共享的多行标题下组织相关列。组标签跨越其子列，使用户更容易扫描和理解复杂表格的结构。组可以嵌套到任意深度，`Table`会自动渲染正确数量的标题行。

## 创建列组 {#creating-column-groups}

使用`ColumnGroup.of()`工厂方法创建一个组，然后链式调用`add()`填充列引用、其他组或两者的混合。使用`setColumnGroups()`将组应用于`Table`。

当设置组时，`Table`会渲染一个多行标题，其中每个组标签跨越其子列。嵌套深度决定了出现多少个标题行。扁平组会增加一行，而两级嵌套会增加两行，以此类推。

组可以在任何时候设置或更改，无论是在`Table`呈现之前还是之后。将`null`或空列表传递给`setColumnGroups()`以移除所有分组，并返回到单行标题。

```java
// 移除所有列组
table.setColumnGroups(null);
```

## 列排序 {#column-ordering}

当组处于活动状态时，视觉列顺序由组树决定，而不是列添加到`Table`的顺序。树的遍历采用深度优先，从左到右。

```
添加的列:  [A, B, C, D, E]
组:         组 “G1” [C, A], 组 “G2” [E, D]
视觉顺序:   C, A, E, D, B
```

未分组的列（未在任何组中引用的列）不会隐藏。它们会根据其相对于分组列的自然位置出现，基于它们最初添加到`Table`的顺序。

在这个例子中，`Number`首先出现，因为它是在`Title`之前添加的。`Label`则出现在`Genre`和`Cost`之间，因为它是在它们之间添加的：

```
添加的列:  [Number, Title, Artist, Genre, Label, Cost]
组:         组 “Music” [Title, Artist, Genre], 组 “Pricing” [Cost]
视觉顺序:   Number, Title, Artist, Genre, Label, Cost
```

以下演示说明了这种行为。`Number`和`Label`未在任何组中引用，但它们保留了基于添加到`Table`顺序的自然位置。

:::tip 控制未分组列的位置
要显式控制未分组列的位置，请将它们作为顶级列引用包含在组树中。
:::

## 在组内移动列 {#column-moving-within-groups}

当组处于活动状态时，拖放列移动受到限制，以维护组的完整性：

- **在组内**：组内的列只能在其直接父组内移动。将其拖出组会被拒绝，列会回到其原始位置。
- **未分组列**：未分组的列只能移动到其他未分组列占用的位置。它不能被放入组内的中间位置。
- **重新排序组**：可以拖动整个组以在其同级中重新排序。

```
组: 组 “G1” [A, B, C], 组 “G2” [D, E]

将 A 移到位置 2 -> OK（在 G1 内，结果: [B, C, A]）
将 A 移到位置 3 -> 被拒绝（位置 3 在 G2 内）
将 D 移到位置 4 -> OK（在 G2 内，结果: [E, D]）
将 D 移到位置 1 -> 被拒绝（位置 1 在 G1 内）
```

## 固定组 {#pinning-groups}

可以使用`setPinDirection()`将组固定在左侧或右侧。组内的所有列都会继承组的固定方向，单个列的固定设置会被组覆盖。

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// "number"和"title"都是固定在左侧，
// 无论它们自己的固定设置如何
```

未分组列保留其列定义中的固定方向。

## 组标题高度 {#group-header-height}

组标题行的高度可以独立于常规列标题使用`setGroupHeaderHeight()`来控制。

```java
table.setGroupHeaderHeight(32); // 组行高度为 32px
table.setHeaderHeight(48);      // 列标题行保持为 48px
```

默认的组标题高度与默认标题高度匹配。

## 使用 CSS 部件样式化组 {#styling-groups-with-css-parts}

组标题和列通过`::part()`暴露 CSS 部件以进行样式化。以下是可用的部件：

| 部件 | 描述 |
| --- | --- |
| `cell-group-{ID}` | 组标题单元格，通过组 ID 定位 |
| `cell-group-depth-{N}` | 组标题单元格，通过深度定位（`0` = 顶级，`1` = 二级，依此类推） |
| `cell-column-{ID}` | 给定列 ID 的所有单元格（标题和正文） |
| `cell-content-group-{ID}` | 组标题内的内容包装器 |
| `cell-label-group-{ID}` | 组标题内的标签 |

### 按组 ID 进行样式化 {#styling-by-group-id}

使用组 ID 针对特定组应用独特的颜色或排版。

```css
dwc-table::part(cell-group-catalog) {
  background: var(--dwc-color-primary-30);
  color: var(--dwc-color-primary-text-30);
  font-weight: 600;
}

dwc-table::part(cell-group-bio) {
  background: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}

dwc-table::part(cell-column-title) {
  font-weight: 600;
}
```

### 按深度进行样式化 {#styling-by-depth}

基于深度的部件在您希望对所有处于某个嵌套级别的组应用一致样式时非常有用，而无需单独针对每个组。

```css
/* 样式化所有顶级组 */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* 样式化所有二级组 */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## 隐藏列 {#hidden-columns}

隐藏列不包括在视觉顺序和标题布局中。如果组包含可见列和隐藏列的混合，只有可见列会出现，组的`colspan`会相应调整。如果组中的每一列都是隐藏的，则根本不会渲染该组标题。
