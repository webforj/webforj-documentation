---
sidebar_position: 5
title: Columns
slug: columns
description: >-
  Define Table columns with labels, value providers, visibility, navigability,
  sizing, and user-interaction events.
_i18n_hash: 5ca9d9959c258db42780e52dad8c463d
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

`Table`类使用列实例来定义和自定义数据的显示方式。列控制显示的数据、外观以及用户与数据的交互方式。此页面涵盖列的身份、展示、大小、用户交互和相关事件。

## 列身份 {#column-identity}

列的身份定义了它在`Table`中的识别方式。这包括其标签、提供的值以及其是否可见或可导航。

### 标签 {#label}

列的标签是其公共标识符，帮助澄清显示的数据。

使用`setLabel()`来设置或修改标签。

:::tip
默认情况下，标签将与列 ID 相同。
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

### 值提供者 {#value-providers}

值提供者是一个负责将基础数据集中的原始数据转换为适合在特定列中显示格式的函数。该函数由您定义，接受行数据类型 (T) 的实例，并返回要在该特定行的关联列中展示的值。

要在列上设置值提供者，请使用`Table`组件的`addColumn()`方法之一。

在以下代码片段中，列将尝试从 JSON 对象访问数据，仅在数据不为 null 时渲染。

```java
List<String> columnsList = List.of("athlete", "age", "country", "year", "sport", "gold", "silver", "bronze", "total");
for (String column : columnsList) {
  table.addColumn(column, (JsonObject person) -> {
    JsonElement element = person.get(column);
    if (!element.isJsonNull()) {
      return element.getAsString();
    }
    return "";
  });
}
```

### 可见性 {#visibility}

可以设置列的可见性，以确定它是否在`Table`中显示。这在确定是否显示敏感信息时非常有用。

```java
table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);
```

### 可导航 {#navigable}

可导航属性决定用户在导航时是否可以与列进行交互。将`setSuppressNavigable()`设置为 true 将限制用户与该列的交互，提供只读体验。

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## 布局和格式化 {#layout-and-formatting}

在建立列的身份之后，下一步是控制其内容如何向用户呈现。布局选项如对齐和固定决定数据的位置以及在您操作`Table`时如何保持可见。

### 对齐 {#alignment}

设置列的对齐方式可以创建有序的表格，这可以帮助用户识别`Table`中的不同部分。

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnalignment'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

`Table` 组件支持三种对齐选项：

- `Column.Alignment.LEFT`：适合文本或描述性数据，保持左向流动是直观的。在强调内容的起点时很有用。
- `Column.Alignment.CENTER`：居中对齐的列适用于较短的值，如字符键、状态或任何其他具有平衡展示的内容。
- `Column.Alignment.RIGHT`：考虑为数字值使用右对齐列，这些值便于快速扫描，例如日期、金额和百分比。

在前面的示例中，`Cost`的最后一列已右对齐，以提供更明显的视觉区分。

### 固定 {#pinning}

列固定是一个功能，允许用户将列"固定"到`Table`的特定侧面。当某些列（例如标识符或关键信息）需要在水平滚动表格时保持可见时，这非常有用。

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnpinning'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

列的固定可以有三种方向：

- `PinDirection.LEFT`：将列固定到左侧。
- `PinDirection.RIGHT`：将列固定到右侧。
- `PinDirection.AUTO`：列根据插入顺序显示。

固定可以以编程方式设置，允许您根据用户交互或应用程序逻辑更改固定方向。

## 列大小 <DocChip chip='since' label='25.03' /> {#column-sizing}

### 固定宽度 {#fixed-width}

使用`setWidth()`方法为列设置精确的宽度，指定所需的像素宽度：

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

宽度属性定义了列的初始所需宽度。该宽度的使用情况取决于其他属性和列类型：

- **常规列**：仅设置宽度时，列按指定宽度呈现，但当容器过小时可以按比例缩小。原始宽度作为所需宽度，但如果没有明确的最小限制，列可以呈现得比设置的宽度更小。
- [**固定列**](#pinning)：始终保持其确切宽度，从不参与响应式缩小。
- [**弹性列**](#flex-sizing)：设置宽度与灵活性不兼容。请分别使用宽度（固定）或灵活性（按比例），而不是两者。

如果未指定，列将根据对前几行内容的分析使用其估算宽度。

```java
// 获取当前宽度
float currentWidth = column.getWidth();
```

### 最小宽度 {#minimum-width}

`setMinWidth()`方法允许您定义列的最小宽度。如果未提供最小宽度，`Table`将根据列内容计算最小宽度。

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100f);
```

传递的值表示最小宽度，以像素为单位。

最小宽度属性控制列可以具有的最小宽度：

- **常规列**：仅设置最小宽度时，列使用最小宽度作为所需宽度和最小宽度。设定宽度+最小宽度时，列可以从宽度缩小到最小宽度，但不能再小。
- [**固定列**](#pinning)：如果仅设置最小宽度（未设置宽度），则该值将成为固定宽度。
- [**弹性列**](#flex-sizing)：即使在容器空间有限的情况下，也可防止列缩小到低于此宽度。

```java
// 获取当前最小宽度
float minWidth = column.getMinWidth();
```

### 最大宽度 {#maximum-width}

`setMaxWidth()`方法限制了列可以增长的宽度，防止具有长内容的列变得过宽，从而影响可读性：

```java
table.addColumn("Description", Product::getDescription)
  .setMinWidth(100f)
  .setMaxWidth(300f);
```

`maxWidth`属性限制了所有列类型的列增长，并且无论内容、容器大小或灵活性设置如何，都不会超过。

```java
// 获取当前最大宽度
float maxWidth = column.getMaxWidth();
```

### 弹性大小 {#flex-sizing}

`setFlex()`方法启用按比例列大小，使列在分配固定宽度列后共享可用空间：

```java
// Title 列的空间是 Artist 列的两倍
table.addColumn("Title", Product::getTitle).setFlex(2f);
table.addColumn("Artist", Product::getArtist).setFlex(1f);
```

弹性的关键行为：

- **Flex 值**：决定可用空间的比例。 flex=2 的列获得 flex=1 的列两倍的空间。
- **与宽度不兼容**：无法与宽度属性一起使用。当 flex 大于零时，它优先于宽度设置。
- **尊重约束**：适用于最小宽度/最大宽度约束。如果没有最小宽度，弹性列可以缩小到 0。

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnflexsizing'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnFlexSizingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='550px'
/>
<!-- vale on -->

:::info 宽度与灵活性
宽度和灵活性属性是相互排斥的。设置一个会自动清除另一个。使用宽度实现精确控制，或使用灵活性实现响应式行为。
:::

### 自动大小 {#automatic-sizing}

除了手动设置宽度和灵活性外，列还可以自动调整大小。自动调整大小允许`Table`通过分析内容或按比例分配空间来确定最优宽度。

#### 基于内容的自动大小 {#content-based-auto-sizing}

根据内容自动调整列的大小。`Table`分析每列中的数据并计算出显示内容而不被截断的最佳宽度。

```java
// 自动调整所有列以适合内容
table.setColumnsToAutoSize().thenAccept(c -> {
  // 调整完成 - 列现在适合其内容
});

// 自动调整特定列
table.setColumnToAutoSize("description");
```

#### 按比例自动适配 {#proportional-auto-fit}

按比例分配所有列在可用的`Table`宽度中。这一操作将每列设置为flex=1，使它们平等地分享总的`Table`宽度，而不考虑内容长度。列将扩展或收缩以填充确切的`Table`尺寸，不留空余空间。

```java
// 使列适合表格宽度（等同于在所有列上设置 flex=1）
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // 所有列现在共享空间
});
```

:::info 异步操作
自动调整大小的方法返回`PendingResult<Void>`，因为它们需要客户端计算。使用`thenAccept()`在调整完成后执行代码。如果您不希望等待完成，可以在没有`thenAccept()`的情况下调用这些方法。
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnautosizing'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnAutoSizingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='550px'
/>
<!-- vale on -->

## 用户交互 <DocChip chip='since' label='25.03' /> {#user-interactions}

### 列调整大小 {#column-resizing}

列调整大小让用户控制每列占用多少空间，通过拖动列边界来实现。

您可以在构建表格时控制单个列的调整大小行为：

```java
// 启用用户调整此列大小
table.addColumn("Title", Product::getTitle).setResizable(true);

// 禁用调整大小
table.addColumn("ID", Product::getId).setResizable(false);

// 检查当前状态
boolean canResize = column.isResizable();
```

对于希望在多个列之间保持一致行为的表格，使用批量配置方法：

```java
// 使所有现有列可调整大小
table.setColumnsToResizable(true);

// 锁定所有现有列以防止调整大小
table.setColumnsToResizable(false);
```

### 列重新排序 {#column-reordering}

列重新排序允许用户将列拖放到他们喜欢的顺序中，从而个性化`Table`的布局，以符合他们的工作流程。

在设置表格时配置列移动权限：

```java
// 允许用户移动此列
table.addColumn("Title", Product::getTitle).setMovable(true);

// 防止列移动（对 ID 或操作列很有用）
table.addColumn("ID", Product::getId).setMovable(false);

// 检查当前状态
boolean canMove = column.isMovable();
```

同时将移动设置应用于多个列：

```java
// 启用所有现有列的重新排序
table.setColumnsToMovable(true);

// 禁用所有现有列的重新排序
table.setColumnsToMovable(false);
```

:::note 批量操作
`setColumnsToResizable()`和`setColumnsToMovable()`方法仅影响调用时的现有列。它们不为将来列设置默认值。
:::

### 编程列移动 {#programmatic-column-movement}

除了拖放，您还可以通过索引或 ID 以编程方式重新定位列。请记住，索引仅基于可见列；在计算位置时会忽略任何隐藏列。

```java
// 将列移动到第一个位置
table.moveColumn("title", 0);

// 将列移动到最后一个位置
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// 使用回调进行异步移动
table.moveColumn("description", 2).thenAccept(c -> {
  // 列移动成功
});
```

## 事件处理 {#event-handling}

`Table`组件在用户与列交互时发出事件，允许您响应布局变化并保存用户偏好。

支持的事件：

- `TableColumnResizeEvent`：当用户通过拖动列边界调整列大小时触发。
- `TableColumnMoveEvent`：当用户通过拖动列标题重新排列列时触发。

您可以附加监听器到`Table`以在用户修改表格布局时进行响应。

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // 处理列调整大小事件
  // 访问：event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // 处理列移动事件
  // 访问：event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
