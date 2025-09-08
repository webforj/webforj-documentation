---
sidebar_position: 5
title: Columns
slug: columns
sidebar_class_name: new-content
_i18n_hash: 6be8663364f0b321c603eb8b870cc104
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

`Table` 类使用列实例来定义和自定义数据的显示方式。列控制显示哪些数据、数据的外观以及用户如何与之交互。此页面涵盖列的身份、展示、大小、用户交互及相关事件。

## 列身份 {#column-identity}

列的身份定义了它在 `Table` 中的识别方式。这包括它的标签、提供的值，以及它是否可见或可导航。

### 标签 {#label}

列的标签是它的公共标识符，帮助澄清显示的数据。

使用 `setLabel()` 来设置或修改标签。

:::tip
默认情况下，标签将与列 ID 相同。
:::

```java
table.addColumn("产品 ID", Product::getProductId).setLabel("ID");
```

### 值提供者 {#value-providers}

值提供者是一个函数，负责将来自基础数据集的原始数据转换为适合在特定列中显示的格式。您定义的该函数接受一个行数据类型 (T) 的实例，并返回要在该行关联的列中显示的值。

要在列上设置值提供者，请使用 `Table` 组件中的 `addColumn()` 方法之一。

在以下片段中，列将尝试从 JSON 对象中访问数据，仅在数据不为 null 时才渲染。

```java
    List<String> columnsList = Arrays.asList("运动员", "年龄", "国家", "年份", "运动", "金牌", "银牌", "铜牌", "总计");

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

可以设置列的可见性，决定它是否将显示在 `Table` 中。当需要决定是否显示敏感信息时，这可能会很有用。

```java
table.addColumn("信用卡", Customer::getCreditCardNumber).setHidden(true);
```

### 可导航 {#navigable}

可导航属性决定用户在导航过程中是否可以与列交互。将 `setSuppressNavigable()` 设置为 true 将限制用户与该列的交互，提供只读体验。

```java
table.addColumn("只读列", Product::getDescription).setSuppressNavigable(true);
```

## 布局和格式化 {#layout-and-formatting}

在确定列的身份后，下一步是控制列内容如何呈现给用户。对齐和固定列的位置等布局选项决定数据的放置位置，以及在使用 `Table` 时如何保持可见。

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

- `Column.Alignment.LEFT`：适用于文本或描述数据，左对齐直观。本质上使内容的起始位置突出。
- `Column.Alignment.CENTER`：居中对齐的列非常适合较短的值，例如字符键、状态或其他任何具有平衡展示的内容。
- `Column.Alignment.RIGHT`：对于能够快速浏览的数值，考虑使用右对齐的列，例如日期、金额和百分比。

在前面的示例中，`成本` 的最后一列已右对齐，以提供更明显的视觉区别。

### 固定列 {#pinning}

列固定是一项功能，允许用户将列固定在 `Table` 的特定侧边。当某些列（例如标识符或重要信息）需要在水平滚动表格时保持可见时，这非常有用。

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

列固定的三种可用方向：

- `PinDirection.LEFT`：将列固定在左侧。
- `PinDirection.RIGHT`：将列固定在右侧。
- `PinDirection.AUTO`：列根据插入顺序出现。

可以通过编程设置固定方向，根据用户交互或应用逻辑更改固定方向。

## 列大小 <DocChip chip='since' label='25.03' /> {#column-sizing} 

### 固定宽度 {#fixed-width}

使用 `setWidth()` 方法为列设置确切的宽度，指定所需的像素宽度：

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

宽度属性定义了该列的初始所需宽度。该宽度的使用取决于其他属性和列类型：

- **常规列**：仅设置宽度时，列将在指定宽度下呈现，但当容器过小时可以按比例缩小。初始宽度作为所需宽度，但没有明确的最小约束时，列可以渲染小于设置的宽度。
- [**固定列**](#pinning)：始终保持其确切宽度，绝不参与响应性缩小。
- [**弹性列**](#flex-sizing)：设置宽度与弹性不兼容。要么使用宽度（固定），要么使用弹性（按比例），而不是两者都使用。

如果未指定，列将使用基于对前几行内容分析的估计宽度。

```java
// 获取当前宽度
float currentWidth = column.getWidth();
```

### 最小宽度 {#minimum-width}

`setMinWidth()` 方法允许您定义列的最小宽度。如果未提供最小宽度，`Table` 将根据列内容计算最小宽度。

```java
table.addColumn("价格", Product::getPrice).setMinWidth(100f);
```

传入的值表示像素中的最小宽度。

最小宽度属性控制列的最小宽度：

- **常规列**：仅设置最小宽度时，列使用该最小宽度作为所需和最小宽度。使用宽度 + 最小宽度，列可以从宽度缩小到最小宽度，但不能更小。
- [**固定列**](#pinning)：如果仅设置最小宽度（没有宽度），那么它将成为固定宽度。
- [**弹性列**](#flex-sizing)：即使在容器空间有限的情况下，也防止列缩小到此宽度以下。

```java
// 获取当前最小宽度
float minWidth = column.getMinWidth();
```

### 最大宽度 {#maximum-width}

`setMaxWidth()` 方法限制列可以增长的宽度，防止内容过长的列太宽而影响可读性：

```java
table.addColumn("描述", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

`maxWidth` 属性限制列的增长，对于所有列类型都是如此，并且无论内容、容器大小或弹性设置如何，都不会被超过。

```java
// 获取当前最大宽度
float maxWidth = column.getMaxWidth();
```

### 弹性大小 {#flex-sizing}

`setFlex()` 方法启用按比例列大小，使列在固定宽度列分配后共享可用空间：

```java
// 标题列获得比艺术家列多两倍的空间
table.addColumn("标题", Product::getTitle).setFlex(2f);
table.addColumn("艺术家", Product::getArtist).setFlex(1f);
```

主要弹性行为：

- **弹性值**：确定可用空间的比例。弹性=2 的列将获得的空间是弹性=1 的列的两倍。
- **与宽度不兼容**：不能与宽度属性一起使用。当弹性大于零时，将超过宽度设置。
- **遵循约束**：与最小宽度和最大宽度约束一起使用。在没有最小宽度的情况下，弹性列可以收缩至 0。

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
宽度和弹性属性是互斥的。设置一个会自动清除另一个。使用宽度以获得精确控制，或使用弹性以获得响应行为。
:::

### 自动大小 {#automatic-sizing}

除手动宽度和弹性设置外，列也可以自动调整大小。自动调整大小允许 `Table` 通过分析内容或按比例分配空间来确定最佳宽度。

#### 基于内容的自动调整大小 {#content-based-auto-sizing}

根据内容自动调整列的大小。`Table` 分析每列中的数据并计算出最佳宽度，以显示内容而不被截断。

```java
// 自动调整所有列以适应内容
table.setColumnsToAutoSize().thenAccept(c -> {
    // 调整完成 - 列现在适合其内容
});

// 自动调整特定列
table.setColumnToAutoSize("描述");
```

#### 按比例自动适应 {#proportional-auto-fit}

按比例分配所有列在可用 `Table` 宽度上。这一操作将每列设置为弹性=1，使它们平均分享总的 `Table` 宽度，无论它们的内容长度如何。列将扩展或收缩以填充准确的 `Table` 尺寸而不留任何剩余空间。

```java
// 使列适应表格宽度（等同于在所有列上设置弹性=1）
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // 所有列现在共享空间
});
```

:::info 异步操作
自动调整方法返回 `PendingResult<Void>`，因为它们需要客户端计算。使用 `thenAccept()` 在调整完成后执行代码。如果您不需要等待完成，可以在不使用 `thenAccept()` 的情况下调用方法。
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

列调整大小使用户可以通过拖动列边界来控制每列占用的空间。

在构建表格时，您可以控制单个列的调整大小行为：

```java
// 启用用户对该列的调整大小
table.addColumn("标题", Product::getTitle).setResizable(true);

// 禁用调整大小
table.addColumn("ID", Product::getId).setResizable(false);

// 检查当前状态
boolean canResize = column.isResizable();
```

对于希望在多列中保持一致行为的表格，请使用批量配置方法：

```java
// 使所有现有列可调整大小
table.setColumnsToResizable(true);

// 锁定所有现有列以防止调整大小
table.setColumnsToResizable(false);
```

### 列重新排序 {#column-reordering}

列重新排序允许用户将列拖放到他们首选的顺序，为他们的工作流程个性化 `Table` 布局。

在设置表格时配置列移动权限：

```java
// 允许用户移动此列
table.addColumn("标题", Product::getTitle).setMovable(true);

// 防止列移动（对于 ID 或操作列非常有用）
table.addColumn("ID", Product::getId).setMovable(false);

// 检查当前状态
boolean canMove = column.isMovable();
```

可以同时将移动设置应用于多个列：

```java
// 启用所有现有列的重新排序
table.setColumnsToMovable(true);

// 禁用所有现有列的重新排序  
table.setColumnsToMovable(false);
```

:::note 批量操作
`setColumnsToResizable()` 和 `setColumnsToMovable()` 方法仅影响调用时的现有列。它们不会为未来的列设置默认值。
:::

### 编程列移动 {#programmatic-column-movement} 

除了拖放外，您还可以通过索引或 ID 以编程方式重新定位列。请记住，索引仅基于可见列；在计算位置时，会忽略任何隐藏列。

```java
// 将列移动到第一个位置
table.moveColumn("标题", 0);

// 将列移动到最后一个位置
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// 带回调的异步移动
table.moveColumn("描述", 2).thenAccept(c -> {
    // 列成功移动
});
```

## 事件处理 {#event-handling}

`Table` 组件在用户与列交互时会发出事件，允许您响应布局更改并保存用户偏好。

支持的事件：

- `TableColumnResizeEvent`：当用户通过拖动列边界调整列大小时触发。
- `TableColumnMoveEvent`：当用户通过拖动列标题重新排序列时触发。

您可以将监听器附加到 `Table`，以响应用户修改表布局时的行为。

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
