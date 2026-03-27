---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 59dc1d0f2eff7880d818123654e8febf
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

`Table` 类使用列实例定义和定制数据的显示方式。列控制显示的数据、其外观及用户如何与之互动。此页面涵盖列身份、展示、大小、用户互动以及相关事件。

## 列身份 {#column-identity}

列的身份定义了它在 `Table` 中的识别方式。这包括它的标签、提供的值，以及它是否可见或可导航。

### 标签 {#label}

列的标签是其面向公众的标识，帮助澄清显示的数据。  

使用 `setLabel()` 设置或修改标签。

:::tip
默认情况下，标签将与列 ID 相同。
:::

```java
table.addColumn("产品 ID", Product::getProductId).setLabel("ID");
```

### 值提供者 {#value-providers}

值提供者是一个函数，负责将底层数据集中的原始数据转化为适合在特定列中显示的格式。您定义的函数接受行数据类型 (T) 的实例，并返回要在该特定行关联列中显示的值。

要在列上设置值提供者，请使用 `Table` 组件的 `addColumn()` 方法之一。

在以下代码片段中，列将尝试从 JSON 对象访问数据，只有在数据不为 null 时才进行渲染。

```java
    List<String> columnsList = List.of("运动员", "年龄", "国家", "年份", "运动", "金牌", "银牌", "铜牌", "总计");

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

可以设置列的可见性，以确定它是否将在 `Table` 中显示。这在确定是否显示敏感信息等方面非常有用。

```java
table.addColumn("信用卡", Customer::getCreditCardNumber).setHidden(true);
```

### 可导航 {#navigable}

可导航属性决定用户在导航期间是否可以与列互动。将 `setSuppressNavigable()` 设置为 true 会限制用户对该列的互动，提供只读体验。

```java
table.addColumn("只读列", Product::getDescription).setSuppressNavigable(true);
```

## 布局和格式 {#layout-and-formatting}

在确定列的身份后，下一步是控制其内容对用户的显示方式。布局选项如对齐和固定决定数据所处的位置以及在与 `Table` 交互时如何保持可见。

### 对齐 {#alignment}

设置列的对齐可以让您创建组织良好的表格，这可以帮助用户识别 `Table` 中的不同部分。

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

- `Column.Alignment.LEFT`：适用于文本或描述性数据，在左侧流动是直观的。适合强调内容的起始点。
- `Column.Alignment.CENTER`：居中对齐的列适用于较短的值，如字符键、状态或其他具有平衡展示的内容。
- `Column.Alignment.RIGHT`：对于有助于快速浏览的数字值，例如日期、金额和百分比，考虑使用右对齐的列。

在上述示例中，`Cost` 的最后一列已被右对齐以提供更明显的视觉区别。

### 固定 {#pinning}

列固定是一项功能，允许用户将列“固定”到 `Table` 的特定侧面。对于某些列，例如标识符或重要信息，需要在水平滚动表格时保持可见，这非常有用。

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

列固定有三个可用方向：

- `PinDirection.LEFT`：将列固定在左侧。
- `PinDirection.RIGHT`：将列固定在右侧。
- `PinDirection.AUTO`：列根据插入顺序显示。

可以以编程方式设置固定，允许您根据用户交互或应用程序逻辑更改固定方向。

## 列大小 <DocChip chip='since' label='25.03' /> {#column-sizing} 

### 固定宽度 {#fixed-width}

使用 `setWidth()` 方法设置列的确切宽度，指定所需的像素宽度：

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

宽度属性定义了列的期望初始宽度。此宽度的使用取决于其他属性和列类型：

- **常规列**：仅设置宽度时，列按指定宽度呈现，但在容器过小的情况下可以比例缩小。原始宽度作为期望宽度，但没有明确的最小限制，列可以比设置的宽度呈现得小。
- [**固定列**](#pinning)：始终维护其确切宽度，永远不会参与响应式缩小。
- [**弹性列**](#flex-sizing)：设置宽度与弹性不兼容。使用宽度（固定）或弹性（比例），而不是两者。

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

- **常规列**：仅设置最小宽度时，列将最小宽度作为期望宽度和最小宽度。设置宽度和最小宽度时，列可以从宽度缩小到最小宽度，但不能更小。
- [**固定列**](#pinning)：如果仅设置最小宽度（不设置宽度），则它成为固定宽度。
- [**弹性列**](#flex-sizing)：防止列在容器空间有限时缩小到该宽度以下。

```java
// 获取当前最小宽度
float minWidth = column.getMinWidth();
```

### 最大宽度 {#maximum-width}

`setMaxWidth()` 方法限制列的最大扩展宽度，防止长内容的列变得过宽并影响可读性：

```java
table.addColumn("描述", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

`maxWidth` 属性限制所有列类型的宽度增长，无论内容、容器大小或弹性设置如何，都不会超过。

```java
// 获取当前最大宽度
float maxWidth = column.getMaxWidth();
```

### 弹性大小 {#flex-sizing}

`setFlex()` 方法启用按比例列大小，使列在分配固定宽度列后共享可用空间：

```java
// 标题列占据艺术家列的两倍空间
table.addColumn("标题", Product::getTitle).setFlex(2f);
table.addColumn("艺术家", Product::getArtist).setFlex(1f);
```

主要弹性行为：

- **弹性值**：决定可用空间的比例。弹性为2的列占据的空间是弹性为1的列的两倍。
- **与宽度不兼容**：不能与宽度属性一起使用。当弹性大于零时，优先使用弹性设置而不是宽度设置。
- **尊重限制**：受最小宽度/最大宽度约束的影响。没有最小宽度时，弹性列可以缩小到0。

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
宽度和弹性属性是互斥的。设置其中一个会自动清除另一个。使用宽度进行精确控制或使用弹性进行响应行为。
:::

### 自动大小 {#automatic-sizing}

除了手动的宽度和弹性设置，列还可以自动调节大小。自动调节大小允许 `Table` 根据内容分析或按比例分配空间来确定最佳宽度。

#### 基于内容的自动大小 {#content-based-auto-sizing}

根据其内容自动调整列的大小。`Table` 分析每列中的数据并计算出最佳宽度，以确保内容不被截断。

```java
// 自动调整所有列的大小以适应内容
table.setColumnsToAutoSize().thenAccept(c -> {
    // 调整完成 - 列现在适合其内容
});

// 自动调整特定列的大小
table.setColumnToAutoSize("描述");
```

#### 按比例自动适应 {#proportional-auto-fit}

在可用的 `Table` 宽度中均匀分配所有列。这一操作将每列设置为 flex=1，使它们在无论内容长度如何的情况下均等分享总的 `Table` 宽度。列将扩大或收缩以填充确切的 `Table` 尺寸，无剩余空间。

```java
// 将列调整到表格宽度（等同于将所有列设置为 flex=1）
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // 所有列现在均等分享空间
});
```

:::info 异步操作
自动调整大小的方法返回 `PendingResult<Void>`，因为它们需要客户端计算。使用 `thenAccept()` 在调整完成后执行代码。如果您不需要等待完成，可以在不使用 `thenAccept()` 的情况下调用这些方法。
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

## 用户互动 <DocChip chip='since' label='25.03' /> {#user-interactions}

### 列调整大小 {#column-resizing}

列调整大小使用户可以通过拖动列边界控制每列占用的空间。

在构建表时，您可以控制单个列的调整大小行为：

```java
// 启用用户调整此列的大小
table.addColumn("标题", Product::getTitle).setResizable(true);

// 禁用调整大小
table.addColumn("ID", Product::getId).setResizable(false);

// 检查当前状态
boolean canResize = column.isResizable();
```

对于希望在多个列之间保持一致行为的表，可以使用批量配置方法：

```java
// 使所有现有列都可以调整大小
table.setColumnsToResizable(true);

// 锁定所有现有列，禁止调整大小
table.setColumnsToResizable(false);
```

### 列重新排序 {#column-reordering}

列重新排序允许用户拖放列到他们偏好的顺序，为他们的工作流程个性化 `Table` 布局。

在设置表时配置列移动权限：

```java
// 允许用户移动此列
table.addColumn("标题", Product::getTitle).setMovable(true);

// 阻止列移动（对 ID 或操作列有用）
table.addColumn("ID", Product::getId).setMovable(false);

// 检查当前状态
boolean canMove = column.isMovable();
```

同时对多个列应用移动设置：

```java
// 允许所有现有列重新排序
table.setColumnsToMovable(true);

// 禁用所有现有列重新排序  
table.setColumnsToMovable(false);
```

:::note 批量操作
`setColumnsToResizable()` 和 `setColumnsToMovable()` 方法仅影响调用时存在的列。它们不会为未来的列设置默认值。
:::

### 程序化列移动 {#programmatic-column-movement} 

除了拖放之外，您还可以通过索引或 ID 程序性地重新定位列。请注意，索引仅基于可见列；在计算位置时会忽略任何隐藏列。

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

`Table` 组件在用户与列互动时会发出事件，允许您响应布局更改并保存用户偏好。

支持的事件：

- `TableColumnResizeEvent`：当用户通过拖动列边界调整列大小时触发。
- `TableColumnMoveEvent`：当用户通过拖动列标题重新排序列时触发。

您可以将侦听器附加到 `Table`，以响应用户修改表布局时的操作。

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
