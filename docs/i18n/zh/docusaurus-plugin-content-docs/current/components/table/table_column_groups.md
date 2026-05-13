---
title: Column Groups
sidebar_position: 7
_i18n_hash: f4ab153f6d1e8c4d029e16c3abc41762
---
<DocChip chip='since' label='25.12' />

列组允许您在共享的多行标题下组织相关列。组标签跨越其子列，使用户更容易浏览和理解复杂表格的结构。组可以嵌套到任意深度，`Table` 会自动呈现正确数量的标题行。

## 创建列组 {#creating-column-groups}

使用 `ColumnGroup.of()` 工厂方法创建组，然后链接 `add()` 调用以填充列引用、其他组或两者混合。使用 `setColumnGroups()` 将组应用于 `Table`。

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

当设置组时，`Table` 渲染一个多行标题，每个组标签跨越其子列。嵌套深度决定了出现多少标题行。平面组添加一行，而两层嵌套则添加两行，依此类推。

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
组可以在 `Table` 渲染之前或之后的任何时间进行设置或更改。传递 `null` 或空列表到 `setColumnGroups()` 可以删除所有分组并返回到单行标题。
<!-- vale Google.OxfordComma = YES -->

```java
// 删除所有列组
table.setColumnGroups(null);
```

## 列顺序 {#column-ordering}

当组处于活动状态时，视觉列顺序由组树决定，而不是根据列添加到 `Table` 的顺序。树以深度优先的方式从左到右遍历。

```
添加的列:  [A, B, C, D, E]
组:         组 "G1" [C, A], 组 "G2" [E, D]
视觉顺序:   C, A, E, D, B
```

未分组列，即未在任何组中引用的列，并未隐藏。它们在相对于分组列的自然位置出现，基于它们最初添加到 `Table` 的顺序。

在此示例中，`Number` 首先出现，因为它添加在 `Title` 之前。`Label` 出现在 `Genre` 和 `Cost` 之间，因为它在最初的列顺序中在它们之间添加：

```
添加的列:  [Number, Title, Artist, Genre, Label, Cost]
组:         组 "Music" [Title, Artist, Genre], 组 "Pricing" [Cost]
视觉顺序:   Number, Title, Artist, Genre, Label, Cost
```

以下演示说明了这种行为。`Number` 和 `Label` 没有在任何组中引用，但它们根据添加到 `Table` 的顺序保留了它们的自然位置。

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
要显式控制未分组列的位置，请将它们作为顶级列引用包含在组树中。
:::

## 在组内移动列 {#column-moving-within-groups}

当组处于活动状态时，拖放列的移动受到限制，以维护组的完整性：

- **在组内**：组内的列只能在其直接父组内移动。将其拖出组的尝试将被拒绝，列会弹回到其原始位置。
- **未分组列**：未分组列只能移动到其他未分组列占据的位置。不能放置到组的中间位置。
- **重新排序组**：整个组可以被拖动以重新排序到同一嵌套级别的其他兄弟组中。

```
组:  组 "G1" [A, B, C], 组 "G2" [D, E]

将 A 移动到位置 2 -> OK (在 G1 内，结果: [B, C, A])
将 A 移动到位置 3 -> 被拒绝 (位置 3 在 G2 内)
将 D 移动到位置 4 -> OK (在 G2 内，结果: [E, D])
将 D 移动到位置 1 -> 被拒绝 (位置 1 在 G1 内)
```

## 固定组 {#pinning-groups}

可以使用 `setPinDirection()` 将组固定在左侧或右侧。组内的所有列都继承组的固定方向，单独列的固定设置被组覆盖。

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// "number" 和 "title" 都固定在左侧，
// 无论它们自己的固定设置如何
```

未分组列保留其列定义中的固定方向。

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

## 组头高度 {#group-header-height}

组头行的高度可以通过 `setGroupHeaderHeight()` 独立控制。

```java
table.setGroupHeaderHeight(32); // 组行高度为 32px
table.setHeaderHeight(48);      // 列头行保持在 48px
```

默认的组头高度与默认的头高度相同。

## 使用 CSS 部件样式化组 {#styling-groups-with-css-parts}

组头和列暴露 CSS 部件以通过 `::part()` 进行样式化。可以使用以下部件：

| 部件 | 描述 |
| --- | --- |
| `cell-group-{ID}` | 组头单元格，按组 ID 定位 |
| `cell-group-depth-{N}` | 组头单元格，按深度定位 (`0` = 顶级，`1` = 第二级，等等) |
| `cell-column-{ID}` | 具有给定列 ID 的所有单元格（头和体） |
| `cell-content-group-{ID}` | 组头中的内容包装器 |
| `cell-label-group-{ID}` | 组头中的标签 |

<!-- vale off -->
<ComponentDemo
path='/webforj/tablestyledcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableStyledColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
  'src/main/resources/static/css/table/tablestyledcolumngroups.css',
]}
height='600px'
/>
<!-- vale on -->

### 按组 ID 样式化 {#styling-by-group-id}

使用组 ID 目标特定组，以独特的颜色或排版。

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

基于深度的部件在您想要对所有在某一嵌套级别的组应用一致样式时非常有用，而无需单独定位每个组。

```css
/* 样式化所有顶级组 */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* 样式化所有第二级组 */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## 隐藏列 {#hidden-columns}

隐藏列从视觉顺序和标题布局中排除。如果一个组包含可见和隐藏列的混合，则只有可见列会出现，并且组的 `colspan` 会相应调整。如果组中的每一列都是隐藏的，则根本不会呈现组头。

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
