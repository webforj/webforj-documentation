---
sidebar_position: 5
title: Columns
slug: columns
sidebar_class_name: new-content
_i18n_hash: 280a70bb65c45d3b200157f3462d7b10
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

`Table` 类使用列实例定义和自定义数据的显示方式。列控制所显示的数据、外观以及用户如何与之交互。本页面涵盖列的身份、展示、大小、用户交互和相关事件。

## 列的身份 {#column-identity}

列的身份定义了它在 `Table` 中的识别方式。这包括其标签、提供的值以及是否可见或可导航。

### 标签 {#label}

列的标签是其公共标识符，帮助澄清显示的数据。  

使用 `setLabel()` 设置或修改标签。

:::tip
默认情况下，标签将与列 ID 相同。
:::

```java
table.addColumn("产品 ID", Product::getProductId).setLabel("ID");
```

### 值提供者 {#value-providers}

值提供者是一个函数，负责将来自基础数据集的原始数据转换为适合在特定列中显示的格式。您定义的函数接受行数据类型（T）的实例，并返回要在该行的关联列中展示的值。

要在列上设置值提供者，请使用 `Table` 组件中的 `addColumn()` 方法之一。

在以下代码段中，列将尝试从 JSON 对象中访问数据，仅在数据不为 null 时呈现。

```java
List<String> columnsList = Arrays.asList("运动员", "年龄", "国家", "年份", "体育", "金牌", "银牌", "铜牌", "总计");

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

可以设置列的可见性，以确定它是否将在 `Table` 中显示。这在决定是否显示敏感信息时非常有用。

```java
table.addColumn("信用卡", Customer::getCreditCardNumber).setHidden(true);
```

### 可导航性 {#navigable}

可导航属性决定用户在导航时是否可以与列交互。将 `setSuppressNavigable()` 设置为 true 会限制用户与列的交互，提供只读体验。

```java
table.addColumn("只读列", Product::getDescription).setSuppressNavigable(true);
```

## 布局和格式 {#layout-and-formatting}

在建立列的身份后，下一步是控制其内容在用户面前的呈现方式。布局选项，如对齐和固定，决定数据的位置以及在您与 `Table` 交互时如何保持可见。

### 对齐 {#alignment}

设置列的对齐方式可以创建有组织的表格，帮助用户识别 `Table` 中的不同部分。

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

`Table` 组件支持三种对齐选项：

- `Column.Alignment.LEFT`：适合文本或描述性数据，保持左向流动是直观的。适用于强调内容的起始点。
- `Column.Alignment.CENTER`：中心对齐的列非常适合短值，如字符键、状态或其他具有平衡展示的内容。
- `Column.Alignment.RIGHT`：考虑使用右对齐列用于快速浏览的数值，如日期、金额和百分比。

在前面的示例中，`Cost` 的最终列已右对齐，以提供更明显的视觉区分。

### 固定 {#pinning}

列固定是一个允许用户将列“固定”到 `Table` 的特定侧面的功能。这在某些列（如标识符或重要信息）需要在水平滚动表格时保持可见时非常有用。

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

有三种可用的列固定方向：

- `PinDirection.LEFT`：将列固定在左侧。
- `PinDirection.RIGHT`：将列固定在右侧。
- `PinDirection.AUTO`：列根据插入顺序显示。

固定可以通过编程方式设置，使您可以根据用户交互或应用逻辑更改固定方向。

## 列大小 <DocChip chip='since' label='25.03' /> {#column-sizing} 

### 固定宽度 {#fixed-width}

使用 `setWidth()` 方法设置列的确切宽度，指定所需的宽度（以像素为单位）：

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

宽度属性定义列的期望初始宽度。此宽度的使用取决于其他属性和列类型：

- **常规列**：仅设置宽度时，列以指定宽度呈现，但在容器过小时可以按比例收缩。原始宽度作为期望宽度，但如果没有明确的最小约束，列可能会呈现小于设置宽度的效果。
- [**固定列**](#pinning)：始终保持其确切宽度，绝不会参与响应式收缩。
- [**弹性列**](#flex-sizing)：设置宽度与弹性不兼容。使用宽度（固定）或弹性（按比例），而不是两者。

如果未指定，列将使用根据前几行的内容分析得出的估计宽度。

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

- **常规列**：仅设置最小宽度时，列将最小宽度作为期望宽度和最小宽度。如果设置了宽度 + 最小宽度，则列可以从宽度收缩到最小宽度，但不能更小。
- [**固定列**](#pinning)：如果仅设置最小宽度（没有宽度），则它成为固定宽度。
- [**弹性列**](#flex-sizing)：即使在容器空间有限时，也防止列收缩低于此宽度。

```java
// 获取当前最小宽度
float minWidth = column.getMinWidth();
```

### 最大宽度 {#maximum-width}

`setMaxWidth()` 方法限制列可以增长的宽度，防止长内容的列变得过宽并影响可读性：

```java
table.addColumn("描述", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

`maxWidth` 属性限制所有列类型的列增长，无论内容、容器大小或弹性设置如何，都不会超过此限制。

```java
// 获取当前最大宽度
float maxWidth = column.getMaxWidth();
```

### 弹性大小 {#flex-sizing}

`setFlex()` 方法启用按比例列大小，使列在分配了固定宽度列后共享可用空间：

```java
// 标题列获得比艺术家列多两倍的空间
table.addColumn("标题", Product::getTitle).setFlex(2f);
table.addColumn("艺术家", Product::getArtist).setFlex(1f);
```

关键的弹性行为：

- **弹性值**：决定可用空间的比例。弹性值为2的列获得比弹性值为1的列多两倍的空间。
- **与宽度不兼容**：不能与宽度属性一起使用。当弹性值大于零时，优先使用弹性设置而非宽度设置。
- **遵循约束**：与最小宽度/最大宽度约束配合工作。如果没有最小宽度，弹性列可以收缩至0。

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnflexsizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnFlexSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

:::info 宽度与弹性
宽度和弹性属性是互斥的。设置一个会自动清除另一个。使用宽度进行精确控制，或使用弹性以响应式行为。
:::

### 自动大小 {#automatic-sizing}

除了手动宽度和弹性设置外，列也可以自动调整大小。自动调整大小允许 `Table` 通过分析内容或按比例分配空间来确定最佳宽度。

#### 基于内容的自动调整大小 {#content-based-auto-sizing}

基于内容自动调整列的大小。`Table` 会分析每列中的数据并计算出最合适的宽度，以显示内容而不截断。

```java
// 自动调整所有列的大小以适应内容
table.setColumnsToAutoSize().thenAccept(c -> {
    // 调整完成 - 列现在适合它们的内容
});

// 自动调整特定列的大小
table.setColumnToAutoSize("描述");
```

#### 按比例自动调整适应 {#proportional-auto-fit}

在可用 `Table` 宽度上按比例分配所有列。这一操作将每列设置为弹性值为1，使其平等共享总的 `Table` 宽度，无论其内容长度如何。列将扩展或收缩以填满确切的 `Table` 尺寸而没有剩余空间。

```java
// 让列适应表格宽度（相当于将所有列设置为弹性值为1）
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // 所有列现在均匀分享空间
});
```

:::info 异步操作
自动调整大小方法返回 `PendingResult<Void>`，因为它们需要客户端计算。使用 `thenAccept()` 在调整完成后执行代码。如果您不需要等待完成，可以在不使用 `thenAccept()` 的情况下调用这些方法。
:::

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnautosizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAutoSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

## 用户交互 <DocChip chip='since' label='25.03' /> {#user-interactions}

### 列调整大小 {#column-resizing}

列调整大小让用户控制每列占用的空间，通过拖动列边界来实现。

您可以在构建表时控制单个列的调整大小行为：

```java
// 为此列启用用户调整大小
table.addColumn("标题", Product::getTitle).setResizable(true);

// 禁用调整大小
table.addColumn("ID", Product::getId).setResizable(false);

// 检查当前状态
boolean canResize = column.isResizable();
```

对于希望在多列之间保持一致行为的表，使用批量配置方法：

```java
// 让所有现有列可调整大小
table.setColumnsToResizable(true);

// 锁定所有现有列不进行调整大小
table.setColumnsToResizable(false);
```

### 列重排序 {#column-reordering}

列重排序允许用户将列拖放到他们偏好的顺序，个性化 `Table` 布局以适应他们的工作流程。

在设置表时配置列移动权限：

```java
// 允许用户移动此列
table.addColumn("标题", Product::getTitle).setMovable(true);

// 防止列移动（对 ID 或操作列非常有用）
table.addColumn("ID", Product::getId).setMovable(false);

// 检查当前状态
boolean canMove = column.isMovable();
```

对多个列同时应用移动设置：

```java
// 启用所有现有列的重排序
table.setColumnsToMovable(true);

// 禁用所有现有列的重排序  
table.setColumnsToMovable(false);
```

:::note 批量操作
`setColumnsToResizable()` 和 `setColumnsToMovable()` 方法仅影响调用时已存在的列。它们不会为将来的列设置默认值。
:::

### 编程列移动 {#programmatic-column-movement} 

除了拖放，您还可以通过索引或 ID 在编程中重新定位列。请注意，索引仅基于可见列；在计算位置时，任何隐藏列都会被忽略。

```java
// 将列移动到第一个位置
table.moveColumn("标题", 0);

// 将列移动到最后一个位置
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// 异步移动并回调
table.moveColumn("描述", 2).thenAccept(c -> {
    // 列成功移动
});
```

## 事件处理 {#event-handling}

`Table` 组件在用户与列交互时触发事件，允许您响应布局更改并保存用户偏好。

支持的事件：

- `TableColumnResizeEvent`：当用户通过拖动边框调整列大小时触发。
- `TableColumnMoveEvent`：当用户通过拖动标题重新排序列时触发。

您可以将监听器附加到 `Table` 以响应用户修改表的布局。

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
