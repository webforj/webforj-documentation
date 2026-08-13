---
title: Column Groups
sidebar_position: 7
description: >-
  Group Table columns under shared, nestable multi-row headers using
  ColumnGroup.of and setColumnGroups for complex layouts.
_i18n_hash: 7a0ad76ea48fafddc0aa9965309b48b8
---
<DocChip chip='since' label='25.12' />

列组使您能够将相关列组织在共享的多行标题下。组标签跨越其子列，使用户更容易浏览和理解复杂表格的结构。组可以嵌套到任意深度，`Table` 会自动渲染正确数量的标题行。

## 创建列组 {#creating-column-groups}

使用 `ColumnGroup.of()` 工厂方法创建一个组，然后链式调用 `add()` 来填充它，包含列引用、其他组或两者的混合。使用 `setColumnGroups()` 将组应用于 `Table`。

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

设置组后，`Table` 会渲染一个多行标题，其中每个组标签跨越其子列。嵌套深度决定了呈现的标题行数。一个扁平组增加一行，而二级嵌套增加两行，依此类推。

<!-- vale off -->
<ComponentDemo
path='/webforj/tablenestedcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableNestedColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

<!-- vale Google.OxfordComma = NO -->
组可以在任何时候设置或更改，无论是在渲染 `Table` 之前还是之后。将 `null` 或空列表传递给 `setColumnGroups()` 以移除所有分组并返回到单行标题。
<!-- vale Google.OxfordComma = YES -->

```java
// 移除所有列组
table.setColumnGroups(null);
```

## 列排序 {#column-ordering}

当组处于活动状态时，视觉列顺序由组树决定，而不是按列添加到 `Table` 的顺序。树是深度优先遍历的，从左到右。

```
添加的列:  [A, B, C, D, E]
组:         组 "G1" [C, A], 组 "G2" [E, D]
视觉顺序:   C, A, E, D, B
```

未分组的列，即没有在任何组中引用的列，并不会被隐藏。它们根据列最初添加到 `Table` 时的顺序，出现在相对于分组列的自然位置。

在这个例子中，`Number` 先出现，因为它在 `Title` 之前添加。`Label` 在 `Genre` 和 `Cost` 之间，因为它是按照原始列顺序在它们之间添加的：

```
添加的列:  [Number, Title, Artist, Genre, Label, Cost]
组:         组 "Music" [Title, Artist, Genre], 组 "Pricing" [Cost]
视觉顺序:   Number, Title, Artist, Genre, Label, Cost
```

以下演示说明了此行为。`Number` 和 `Label` 没有在任何组中引用，但它们保持基于添加到 `Table` 时的顺序的自然位置。

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumngroupordering'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnGroupOrderingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

:::tip 控制未分组列位置
要明确控制未分组列的位置，将它们作为顶级列引用包含在组树中。
:::

## 在组内移动列 {#column-moving-within-groups}

当组处于活动状态时，拖放列移动受到约束，以维护组的完整性：

- **在组内**：组内的列只能在其直接父组内移动。将其拖出该组是被拒绝的，列将回到其原始位置。
- **未分组列**：未分组的列只能移动到其他未分组列所占据的位置。它不能被放置到组的中间。
- **重新排序组**：整个组可以被拖动以在同一嵌套级别的兄弟组之间重新排序。

```
组:  组 "G1" [A, B, C], 组 "G2" [D, E]

将 A 移动到位置 2 -> OK（在 G1 内，结果: [B, C, A]）
将 A 移动到位置 3 -> 被拒绝（位置 3 在 G2 内）
将 D 移动到位置 4 -> OK（在 G2 内，结果: [E, D]）
将 D 移动到位置 1 -> 被拒绝（位置 1 在 G1 内）
```

## 固定组 {#pinning-groups}

可以使用 `setPinDirection()` 将组固定到左侧或右侧。组内的所有列将继承组的固定方向，而个别列的固定设置会被组覆盖。

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// "number" 和 "title" 都被固定在左侧，
// 无论它们自己的固定设置
```

未分组的列保留其来自列定义的固定方向。

<!-- vale off -->
<ComponentDemo
path='/webforj/tablepinnedcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TablePinnedColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

## 组标题高度 {#group-header-height}

组标题行的高度可以独立于常规列标题进行控制，使用 `setGroupHeaderHeight()`。

```java
table.setGroupHeaderHeight(32); // 组行高为 32px
table.setHeaderHeight(48);      // 列标题行保持在 48px
```

默认的组标题高度与默认的标题高度相匹配。

## 使用 CSS 部分样式化组 {#styling-groups-with-css-parts}

组标题和列暴露 CSS 部分以便通过 `::part()` 进行样式化。可用的部分如下：

| 部分 | 描述 |
| --- | --- |
| `cell-group-{ID}` | 组标题单元格，按组 ID 定位 |
| `cell-group-depth-{N}` | 组标题单元格，按深度定位（`0` = 顶级，`1` = 二级，等等） |
| `cell-column-{ID}` | 给定列 ID 的所有单元格（标题和主体） |
| `cell-content-group-{ID}` | 组标题中的内容包装 |
| `cell-label-group-{ID}` | 组标题中的标签 |

<!-- vale off -->
<ComponentDemo
path='/webforj/tablestyledcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableStyledColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
  'src/main/frontend/css/table/tablestyledcolumngroups.css',
]}
height='600px'
/>
<!-- vale on -->

### 按组 ID 样式化 {#styling-by-group-id}

使用组 ID 定位特定组以应用独特的颜色或排版。

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

### 按深度样式化 {#styling-by-depth}

基于深度的部分在您希望对所有在某一嵌套级别的组应用一致样式时非常有用，而无需单独定位每个组。

```css
/* 样式所有顶级组 */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* 样式所有二级组 */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## 隐藏列 {#hidden-columns}

隐藏的列从视觉顺序和标题布局中排除。如果一个组包含可见和隐藏列的混合，只有可见列才会出现，组的 `colspan` 会相应调整。如果组中的每一列都是隐藏的，则组标题根本不会渲染。

<!-- vale off -->
<ComponentDemo
path='/webforj/tablehiddencolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableHiddenColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->
