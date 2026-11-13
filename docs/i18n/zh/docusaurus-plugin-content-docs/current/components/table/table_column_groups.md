---
title: 列分组
sidebar_position: 7
description: >-
  Group Table columns under shared, nestable multi-row headers using
  ColumnGroup.of and setColumnGroups for complex layouts.
_i18n_hash: 4babef21ef20728da6aa7eec5829e4d1
---
<!-- vale off -->
# 列分组 <DocChip chip='since' label='25.12' />
<!-- vale on -->

列分组可将相关列组织在共享的多行表头下。分组标签横跨其子列，帮助用户快速浏览并理解复杂表格的结构。分组可以嵌套任意层级，`Table` 会自动渲染正确数量的表头行。

## 创建列分组 {#creating-column-groups}

使用工厂方法 `ColumnGroup.of()` 创建分组，然后通过链式调用 `add()` 添加列引用、其他分组，或两者的组合。使用 `setColumnGroups()` 将分组应用到 `Table`。

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

设置分组后，`Table` 会渲染多行表头，每个分组标签横跨其子列。嵌套深度决定表头行数。平面分组会额外增加一行，嵌套两层则会增加两行，以此类推。

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
可以在 `Table` 渲染之前或之后随时设置或更改分组。向 `setColumnGroups()` 传入 `null` 或空列表，即可移除所有分组并恢复为单行表头。
<!-- vale Google.OxfordComma = YES -->

```java
// 移除所有列分组
table.setColumnGroups(null);
```

## 列顺序 {#column-ordering}

启用分组后，列的视觉顺序由分组树决定，而不是由列添加到 `Table` 的顺序决定。遍历分组树时按深度优先、从左到右的顺序进行。

```
列添加顺序：  [A, B, C, D, E]
分组：        Group "G1" [C, A], Group "G2" [E, D]
视觉顺序：    C, A, E, D, B
```

未分组的列（即未在任何分组中引用的列）不会被隐藏。它们会根据最初添加到 `Table` 的顺序，出现在相对于分组列的自然位置上。

在此示例中，`Number` 最先出现，因为它在 `Title` 之前添加。`Label` 出现在 `Genre` 和 `Cost` 之间，因为它在原始列顺序中添加于这两列之间：

```
列添加顺序：  [Number, Title, Artist, Genre, Label, Cost]
分组：        Group "Music" [Title, Artist, Genre], Group "Pricing" [Cost]
视觉顺序：    Number, Title, Artist, Genre, Label, Cost
```

以下演示展示了这一行为。`Number` 和 `Label` 未在任何分组中引用，但它们仍根据添加到 `Table` 的顺序保留在自然位置上。

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

:::tip 控制未分组列的位置
若要明确控制未分组列的位置，请将它们作为顶层列引用添加到分组树中。
:::

## 在分组内移动列 {#column-moving-within-groups}

启用分组后，拖放移动列的操作会受到限制，以保持分组完整性：

- **在分组内**：分组中的列只能在其直接父分组内移动。将其拖出分组的操作会被拒绝，该列会返回原来的位置。
- **未分组的列**：未分组的列只能移动到其他未分组列所在的位置。不能将其放到分组中间。
- **重新排序分组**：可以拖动整个分组，在同一嵌套层级的同级分组之间重新排序。

```
分组：Group "G1" [A, B, C], Group "G2" [D, E]

将 A 移到位置 2 -> 成功（在 G1 内，结果：[B, C, A]）
将 A 移到位置 3 -> 拒绝（位置 3 在 G2 内）
将 D 移到位置 4 -> 成功（在 G2 内，结果：[E, D]）
将 D 移到位置 1 -> 拒绝（位置 1 在 G1 内）
```

## 固定分组 {#pinning-groups}

可以使用 `setPinDirection()` 将分组固定在左侧或右侧。分组中的所有列都会继承该分组的固定方向，且分组设置会覆盖各列单独的固定设置。

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// "number" 和 "title" 都固定在左侧，
// 无论它们各自的固定设置如何
```

未分组的列保留其列定义中设置的固定方向。

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

## 分组表头高度 {#group-header-height}

可以使用 `setGroupHeaderHeight()` 单独控制分组表头行的高度，不受常规列表头高度影响。

```java
table.setGroupHeaderHeight(32); // 分组行高度为 32px
table.setHeaderHeight(48);      // 列标题行保持 48px 高
```

默认情况下，分组表头高度与默认表头高度相同。

## 使用 CSS 部件设置分组样式 {#styling-groups-with-css-parts}

分组表头和列会公开 CSS 部件，可通过 `::part()` 设置样式。可用部件如下：

| 部件 | 描述 |
| --- | --- |
| `cell-group-{ID}` | 分组表头单元格，可按分组 ID 定位 |
| `cell-group-depth-{N}` | 分组表头单元格，可按深度定位（`0` = 顶层，`1` = 第二层，以此类推） |
| `cell-column-{ID}` | 指定列的所有单元格（表头和正文） |
| `cell-content-group-{ID}` | 分组表头中的内容包装器 |
| `cell-label-group-{ID}` | 分组表头中的标签 |

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

### 按分组 ID 设置样式 {#styling-by-group-id}

使用分组 ID 定位特定分组，并为其设置独特的颜色或字体样式。

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

### 按深度设置样式 {#styling-by-depth}

如果要为特定嵌套层级的所有分组应用一致的样式，而不逐一定位每个分组，则可以使用基于深度的部件。

```css
/* 为所有顶层分组设置样式 */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* 为所有第二层分组设置样式 */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## 隐藏列 {#hidden-columns}

隐藏列不会出现在视觉顺序和表头布局中。如果一个分组同时包含可见列和隐藏列，则只显示可见列，并相应调整该分组的 `colspan`。如果分组中的所有列都被隐藏，则不会渲染该分组表头。

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
