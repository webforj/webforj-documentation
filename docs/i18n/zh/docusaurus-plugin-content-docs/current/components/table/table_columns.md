---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: fbae9063370715e9f6dc2cb490a27511
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

`Table` 类使用列实例来定义和自定义数据的显示方式。列控制显示哪些数据、数据的外观以及用户如何与之交互。此页面涵盖列的身份，展示，大小，用户交互和相关事件。

## 列身份 {#column-identity}

列的身份定义了如何在 `Table` 中识别它。这包括它的标签、它提供的值以及它是否可见或可导航。

### 标签 {#label}

列的标签是其公开标识符，有助于澄清显示的数据。  

使用 `setLabel()` 来设置或修改标签。

:::tip
默认情况下，标签将与列 ID 相同。
:::

```java
table.addColumn("产品ID", Product::getProductId).setLabel("ID");
```

### 值提供者 {#value-providers}

值提供者是一个函数，负责将基础数据集中的原始数据转换为适合在特定列中显示的格式。您定义的函数接受一个行数据类型 (T) 的实例，并返回要在该行的相关列中展示的值。

要为列设置值提供者，请使用 `Table` 组件中的 `addColumn()` 方法之一。

在以下代码片段中，一列将尝试从 JSON 对象中访问数据，仅在数据不是 null 时才呈现。

```java
List<String> columnsList = List.of("运动员", "年龄", "国家", "年份", "运动", "金牌", "银牌", "铜牌", "总数");
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

可以设置列的可见性，以确定它是否将在 `Table` 中显示。这在许多情况下都很有用，例如决定是否显示敏感信息。

```java
table.addColumn("信用卡", Customer::getCreditCardNumber).setHidden(true);
```

### 可导航性 {#navigable}

可导航属性决定了用户在导航过程中是否可以与列交互。将 `setSuppressNavigable()` 设置为 true 会限制用户与该列的交互，实现只读体验。

```java
table.addColumn("只读列", Product::getDescription).setSuppressNavigable(true);
```

## 布局和格式 {#layout-and-formatting}

建立列的身份后，下一步是控制其内容对用户的显示方式。布局选项如对齐和固定位置决定了数据的位置以及在使用 `Table` 时如何保持可见。

### 对齐 {#alignment}

设置列的对齐方式可以帮助您创建有序的表格，从而帮助用户识别 `Table` 中的不同部分。

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

- `Column.Alignment.LEFT`: 适合文本或描述性数据，如果保持左侧流向是合乎逻辑的。对于强调内容的起点很有用。
- `Column.Alignment.CENTER`: 中心对齐的列非常适合较短的值，如字符键、状态或其他任何具有平衡呈现的内容。
- `Column.Alignment.RIGHT`: 考虑为数值使用右对齐的列，这样更有助于快速浏览，例如日期、金额和百分比。

在上述示例中，`Cost` 的最后一列已右对齐，以提供更明显的视觉区分。

### 固定位置 {#pinning}

列固定是一项功能，允许用户将列固定或“钉”在 `Table` 的特定侧面。当某些列（例如标识符或关键信息）在水平滚动表格时需要保持可见时，这很有用。

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

固定列有三种可用方向：

- `PinDirection.LEFT`: 将列固定在左侧。
- `PinDirection.RIGHT`: 将列固定在右侧。
- `PinDirection.AUTO`: 列根据插入顺序出现。

可以通过编程方式设置固定方向，允许您根据用户交互或应用程序逻辑更改固定方向。

## 列大小 <DocChip chip='since' label='25.03' /> {#column-sizing} 

### 固定宽度 {#fixed-width}

使用 `setWidth()` 方法为列设置确切的宽度，指定所需的宽度（以像素为单位）：

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

宽度属性定义了列的初始所需宽度。如何使用此宽度取决于其他属性和列类型：

- **常规列**: 仅设置宽度时，列以指定的宽度呈现，但在容器过小时可以按比例收缩。原始宽度作为所需宽度，但如果没有显式的最小约束，列可以渲染为小于设置的宽度。
- [**固定列**](#pinning): 始终保持其确切宽度，从不参与响应式收缩。
- [**灵活列**](#flex-sizing): 设置宽度与灵活性不兼容。使用宽度（固定）或灵活性（按比例），不能同时使用。

如果未指定，列将根据对前几行内容的分析使用其估计宽度。

```java
// 获取当前宽度
float currentWidth = column.getWidth();
```

### 最小宽度 {#minimum-width}

`setMinWidth()` 方法允许您定义列的最小宽度。如果未提供最小宽度，`Table` 将根据列内容计算最小宽度。

```java
table.addColumn("价格", Product::getPrice).setMinWidth(100f);
```

传递的值表示最小宽度（以像素为单位）。

最小宽度属性控制列可以具有的最小宽度：

- **常规列**: 仅设置最小宽度时，列使用最小宽度作为所需和最小宽度。通过宽度 + 最小宽度，列可以从宽度收缩到最小宽度，但不会更小。
- [**固定列**](#pinning): 如果仅设置最小宽度（没有宽度），它将成为固定宽度。
- [**灵活列**](#flex-sizing): 使列在容器空间有限时也不能小于此宽度。

```java
// 获取当前最小宽度
float minWidth = column.getMinWidth();
```

### 最大宽度 {#maximum-width}

`setMaxWidth()` 方法限制了列可以增长的宽度，防止内容过长导致列变得过宽而影响可读性：

```java
table.addColumn("描述", Product::getDescription)
  .setMinWidth(100f)
  .setMaxWidth(300f);
```

`maxWidth` 属性限制了所有列类型的列增长，并且无论内容、容器大小或灵活性设置如何，都不会超过这一限制。

```java
// 获取当前最大宽度
float maxWidth = column.getMaxWidth();
```

### 灵活大小 {#flex-sizing}

`setFlex()` 方法启用比例列大小，允许列在分配固定宽度列后共享可用空间：

```java
// 标题列获得的空间是艺术家列的两倍
table.addColumn("标题", Product::getTitle).setFlex(2f);
table.addColumn("艺术家", Product::getArtist).setFlex(1f);
```

关键灵活性行为：

- **灵活值**: 决定可用空间的比例。灵活值为2的列获得的空间是灵活值为1的列的两倍。
- **与宽度不兼容**: 不能与宽度属性共同使用。当灵活性大于零时，它优先于宽度设置。
- **尊重约束**: 与最小宽度/最大宽度约束一起工作。如果没有最小宽度，灵活列可以收缩至0。

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
宽度和灵活性属性是互斥的。设置一个会自动清除另一个。使用宽度以精确控制，或使用灵活性以响应行为。
:::

### 自动大小 {#automatic-sizing}

除了手动宽度和灵活性设置外，列还可以自动调整大小。自动调整大小让 `Table` 通过分析内容或按比例分配空间来确定最佳宽度。

#### 基于内容的自动调整大小 {#content-based-auto-sizing}

根据其内容自动调整列的大小。`Table` 分析每列的数据并计算出显示内容的最佳宽度而不发生截断。

```java
// 自动调整所有列以适应内容
table.setColumnsToAutoSize().thenAccept(c -> {
  // 调整完成 - 列现在适应其内容
});

// 自动调整特定列
table.setColumnToAutoSize("描述");
```

#### 比例自动适应 {#proportional-auto-fit}

将所有列按比例分配到可用的 `Table` 宽度。此操作将每列设置为 flex=1，使它们平等共享整个 `Table` 宽度，无论内容长度如何。列将扩展或收缩以填充确切的 `Table` 尺寸，不会留下剩余空间。

```java
// 使列适应表宽度（相当于将所有列设置为 flex=1）
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // 所有列现在均匀分享空间
});
```

:::info 异步操作
自动调整大小的方法返回 `PendingResult<Void>`，因为它们需要客户端计算。使用 `thenAccept()` 来在调整完成后执行代码。如果您不需要等待完成，可以在没有 `thenAccept()` 的情况下调用这些方法。
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

### 列大小调整 {#column-resizing}

列大小调整使用户可以通过拖动列边界来控制每列占用的空间。

您可以在构建表时控制单个列的调整大小行为：

```java
// 启用用户调整此列的大小
table.addColumn("标题", Product::getTitle).setResizable(true);

// 禁用调整大小
table.addColumn("ID", Product::getId).setResizable(false);

// 检查当前状态
boolean canResize = column.isResizable();
```

对于希望在多个列中实现一致行为的表，使用批量配置方法：

```java
// 使所有现有列可调整大小
table.setColumnsToResizable(true);

// 锁定所有现有列，防止调整大小
table.setColumnsToResizable(false);
```

### 列重排序 {#column-reordering}

列重排序允许用户拖放列以自定义顺序，为他们的工作流程个性化 `Table` 布局。

在设置表格时配置列移动权限：

```java
// 允许用户移动此列
table.addColumn("标题", Product::getTitle).setMovable(true);

// 防止列移动（对于 ID 或操作列很有用）
table.addColumn("ID", Product::getId).setMovable(false);

// 检查当前状态
boolean canMove = column.isMovable();
```

同时对多个列应用移动设置：

```java
// 启用所有现有列的重排序
table.setColumnsToMovable(true);

// 禁用所有现有列的重排序  
table.setColumnsToMovable(false);
```

:::note 批量操作
`setColumnsToResizable()` 和 `setColumnsToMovable()` 方法仅影响调用时存在的列。它们不会为将来的列设置默认值。
:::

### 编程列移动 {#programmatic-column-movement} 

除了拖放，您还可以通过索引或 ID 编程方式重新定位列。请注意，索引仅基于可见列，任何隐藏列在计算位置时将被忽略。

```java
// 将列移动到第一个位置
table.moveColumn("title", 0);

// 将列移动到最后一个位置
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// 带回调的异步移动
table.moveColumn("description", 2).thenAccept(c -> {
  // 列成功移动
});
```

## 事件处理 {#event-handling}

`Table` 组件在用户与列交互时发出事件，允许您响应布局更改并保存用户首选项。

支持的事件：

- `TableColumnResizeEvent`: 当用户通过拖动边界调整列的大小时触发。
- `TableColumnMoveEvent`: 当用户通过拖动标题重排列时触发。

您可以将监听器附加到 `Table` 以响应用户修改表格布局。

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // 处理列调整大小事件
  // 访问: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // 处理列移动事件  
  // 访问: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
