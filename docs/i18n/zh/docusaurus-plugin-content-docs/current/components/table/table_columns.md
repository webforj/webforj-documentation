---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 19fe294c57ad6b7d105039c25aedab11
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

`Table` 类使用列实例来定义和自定义数据的显示方式。列控制显示的数据、外观以及用户如何与其交互。此页面涵盖列身份、表现、公差、用户交互和相关事件。

## 列身份 {#column-identity}

列的身份定义了它在 `Table` 中是如何被识别的。这包括其标签、提供的值以及是否可见或可导航。

### 标签 {#label}

列的标签是其公开标识符，有助于澄清显示的数据。  

使用 `setLabel()` 来设置或修改标签。

:::tip
默认情况下，标签将与列ID相同。
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

### 值提供者 {#value-providers}

值提供者是负责将基础数据集中的原始数据转换为适合在特定列中显示格式的函数。您定义的函数接受一行数据类型的实例（T），并返回要在该特定行的关联列中展示的值。

要在列上设置值提供者，请使用 `Table` 组件中的一个 `addColumn()` 方法。

在以下代码片段中，列将尝试访问来自JSON对象的数据，仅在数据不为空时进行渲染。

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

可以设置列的可见性，以确定它是否将在 `Table` 中显示。这在确定是否显示敏感信息时尤其有用。

```java
table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);
```

### 可导航 {#navigable}

可导航属性决定用户在导航期间是否可以与列交互。将 `setSuppressNavigable()` 设置为 true 可以限制用户与该列的交互，提供只读体验。

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## 布局和格式 {#layout-and-formatting}

在确定列的身份后，下一步是控制其内容如何呈现给用户。对齐和固定选项等布局选项决定了数据的位置以及在使用 `Table` 时如何保持可见性。

### 对齐 {#alignment}

设置列的对齐方式可以创建有序的表格，这可以帮助用户识别 `Table` 中的不同部分。

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

- `Column.Alignment.LEFT`：适合文本或描述性数据，其中保持向左流动是直观的。用于强调内容的起始点。
- `Column.Alignment.CENTER`：中心对齐的列非常适合较短的值，如字符键、状态或其他任何具有平衡展示的内容。
- `Column.Alignment.RIGHT`：考虑对数值使用右对齐的列，这些数值有助于快速浏览，例如日期、金额和百分比。

在前面的示例中，`Cost` 的最后一列已被右对齐，以提供更加明显的视觉区分。

### 固定 {#pinning}

列固定是允许用户将列固定到 `Table` 特定侧面的一项功能。这在需要在横向滚动表格时保持某些列（例如标识符或关键信息）可见时非常有用。

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

列的固定有三个可用方向：

- `PinDirection.LEFT`：将列固定到左侧。
- `PinDirection.RIGHT`：将列固定到右侧。
- `PinDirection.AUTO`：根据插入顺序显示列。

可以通过编程设置固定，允许您根据用户交互或应用逻辑更改固定方向。

## 列大小 <DocChip chip='since' label='25.03' /> {#column-sizing} 

### 固定宽度 {#fixed-width}

使用 `setWidth()` 方法为列设置确切宽度，指定所需的宽度（以像素为单位）：

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

宽度属性定义列的所需初始宽度。宽度的使用取决于其他属性和列类型：

- **常规列**：仅设置宽度的情况下，列将在指定宽度上呈现，但在容器过小时可能会按比例缩小。原始宽度作为所需宽度，但没有明确的最小约束，列可能会呈现小于设置宽度的情况。
- [**固定列**](#pinning)：始终保持其确切宽度，从不参与响应式缩小。
- [**弹性列**](#flex-sizing)：设置宽度与弹性不兼容。只能使用宽度（固定）或弹性（按比例），而不能同时使用。

如果未指定，列将根据对前几行内容的分析使用其估计宽度。

```java
// 获取当前宽度
float currentWidth = column.getWidth();
```

### 最小宽度 {#minimum-width}

`setMinWidth()` 方法允许您定义列的最小宽度。如果未提供最小宽度，`Table` 将根据列内容计算最小宽度。

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100f);
```

传递的值表示最小宽度（以像素为单位）。

最小宽度属性控制列的最小宽度：

- **常规列**：仅设置最小宽度的情况下，列使用最小宽度作为所需宽度和最小宽度。设置宽度 + 最小宽度的情况下，列可以从宽度缩小到最小宽度，但不能再进一步缩小。
- [**固定列**](#pinning)：如果仅设置最小宽度（无宽度），则它成为固定宽度。
- [**弹性列**](#flex-sizing)：即使在容器空间有限时，也会阻止列缩小到此宽度以下。

```java
// 获取当前最小宽度
float minWidth = column.getMinWidth();
```

### 最大宽度 {#maximum-width}

`setMaxWidth()` 方法限制了列的最大增长宽度，防止具有长内容的列变得过宽，从而影响可读性：

```java
table.addColumn("Description", Product::getDescription)
  .setMinWidth(100f)
  .setMaxWidth(300f);
```

`maxWidth` 属性限制了所有列类型的列增加宽度，并且无论内容、容器大小或弹性设置如何，都不会被超出。

```java
// 获取当前最大宽度
float maxWidth = column.getMaxWidth();
```

### 弹性大小 {#flex-sizing}

`setFlex()` 方法启用按比例列大小，使列在分配了固定宽度列后共享可用空间：

```java
// 标题列获得的空间是艺术家列的两倍
table.addColumn("Title", Product::getTitle).setFlex(2f);
table.addColumn("Artist", Product::getArtist).setFlex(1f);
```

关键弹性行为：

- **弹性值**：决定可用空间的比例。弹性为2的列获得的空间是弹性为1的列的两倍。
- **与宽度不兼容**：不能与宽度属性一起使用。当弹性大于零时，弹性设置将覆盖宽度设置。
- **遵循约束**：与最小宽度/最大宽度约束一起工作。如果没有最小宽度，弹性列可以缩小到0。

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
宽度和弹性属性是互斥的。设置一个属性会自动清除另一个属性。使用宽度以实现精确控制，或使用弹性以实现响应式行为。
:::

### 自动大小 {#automatic-sizing}

除了手动宽度和弹性设置，列还可以自动调整大小。自动大小允许 `Table` 通过分析内容或按比例分配空间来确定最佳宽度。

#### 基于内容的自动大小 {#content-based-auto-sizing}

根据内容自动调整列的大小。`Table` 分析每列的数据，并计算出显示内容而不被截断的最佳宽度。

```java
// 自动调整所有列以适应内容
table.setColumnsToAutoSize().thenAccept(c -> {
  // 调整大小完成 - 列现在适应其内容
});

// 自动调整特定列
table.setColumnToAutoSize("description");
```

#### 按比例自动适应 {#proportional-auto-fit}

在可用的 `Table` 宽度上按比例分配所有列。这一操作将每列设置为 flex=1，使其平等分享总的 `Table` 宽度，无论其内容长度如何。列将扩展或收缩以填满确切的 `Table` 尺寸，没有多余的空间。

```java
// 将列调整到表格宽度（相当于为所有列设置 flex=1）
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // 所有列现在平等分享空间
});
```

:::info 异步操作
自动调整大小的方法返回 `PendingResult<Void>`，因为它们需要客户端计算。使用 `thenAccept()` 在调整大小完成后执行代码。如果您不需要等待完成，可以在不使用 `thenAccept()` 的情况下调用这些方法。
:::

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnautosizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAutoSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

## 用户交互 <DocChip chip='since' label='25.03' /> {#user-interactions}

### 列调整大小 {#column-resizing}

列调整大小允许用户通过拖动列边界来控制每列占用的空间。

在构建表时，您可以控制单个列的调整大小行为：

```java
// 启用用户对这一列的调整大小
table.addColumn("Title", Product::getTitle).setResizable(true);

// 禁用调整大小
table.addColumn("ID", Product::getId).setResizable(false);

// 检查当前状态
boolean canResize = column.isResizable();
```

对于希望多个列具有一致行为的表，可以使用批量配置方法：

```java
// 使所有现有列可调整大小
table.setColumnsToResizable(true);

// 锁定所有现有列不允许调整大小
table.setColumnsToResizable(false);
```

### 列重新排序 {#column-reordering}

列重新排序允许用户将列拖放到他们的首选顺序，个性化 `Table` 布局以适应他们的工作流程。

在设置表格时配置列移动权限：

```java
// 允许用户移动这一列
table.addColumn("Title", Product::getTitle).setMovable(true);

// 防止列移动（对ID或操作列非常有用）
table.addColumn("ID", Product::getId).setMovable(false);

// 检查当前状态
boolean canMove = column.isMovable();
```

同时应用移动设置到多个列：

```java
// 为所有现有列启用重新排序
table.setColumnsToMovable(true);

// 禁用所有现有列重新排序  
table.setColumnsToMovable(false);
```

:::note 批量操作
`setColumnsToResizable()` 和 `setColumnsToMovable()` 方法仅影响调用时已存在的列。它们不会为未来的列设置默认值。
:::

### 程序化列移动 {#programmatic-column-movement} 

除了拖放，您还可以通过索引或ID以编程方式重新定位列。请记住，索引仅基于可见列；在计算位置时，会忽略任何隐藏列。

```java
// 将列移动到第一位
table.moveColumn("title", 0);

// 将列移动到最后一位
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// 异步移动并回调
table.moveColumn("description", 2).thenAccept(c -> {
  // 列移动成功
});
```

## 事件处理 {#event-handling}

`Table` 组件在用户与列交互时发出事件，允许您对布局更改作出响应并保存用户偏好。

支持的事件：

- `TableColumnResizeEvent`：当用户通过拖动边界调整列大小时触发。
- `TableColumnMoveEvent`：当用户通过拖动标题重新排序列时触发。

您可以将监听器附加到 `Table` 以响应用户修改表格布局。

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
