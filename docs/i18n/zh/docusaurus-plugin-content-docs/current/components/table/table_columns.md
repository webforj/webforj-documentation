---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 5ebe30c35548db6d3b603b8a72ed4c2a
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

`Table` 类利用 `Column` 类来处理其中数据列的创建。该类具有广泛的功能，允许用户充分自定义每一列中显示的内容。
要向 `Table` 添加 `Column`，请使用其中一个接受提供者作为参数的 `addColumn` 工厂方法。

## 值提供者 {#value-providers}

值提供者是一个函数，负责将底层数据集中的原始数据转换为适合在特定列中显示的格式。该函数由用户定义，接受一行数据类型（T）的实例，并返回该行关联列中展示的值。

要在列上设置值提供者，请使用其中一个接受提供者作为参数的 `addColumn` 工厂方法：

```java
    List<String> columnsList = Arrays.asList("athlete", "age", "country", "year", "sport", "gold", "silver", "bronze", "total");

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

在此示例中，列将尝试从 JSON 对象中访问数据，只有在数据不为 null 时才呈现。

## 固定方向 {#pin-direction}

列固定是一个功能，允许用户将列“固定”到表的特定侧面，以增强可见性和可访问性。当某些列（例如标识符或重要信息）需要在水平滚动表时保持可见时，这非常有用。

<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

可用于固定列的方向有三种：

- `PinDirection.LEFT`：将列固定在左侧。
- `PinDirection.RIGHT`：将列固定在右侧。
- `PinDirection.AUTO`：列根据插入顺序出现。

固定可以通过编程设置，允许用户根据用户交互或应用逻辑更改固定方向。

## 对齐 {#alignment}

列对齐定义了数据在列中的水平位置。这将影响数据值的显示方式，为用户提供有关信息性质的视觉指导。 

<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Table 组件支持三种主要对齐选项：

- `Column.Alignment.LEFT`：适合文本或描述性数据，保持左侧流动是直观的。强调内容的起始点时很有用。
- `Column.Alignment.CENTER`：理想用于数值或分类数据，期望创建平衡的展示效果。生成视觉上居中的显示。
- `Column.Alignment.RIGHT`：通常用于数值数据，特别是在数字的大小或精确度很重要时。将数据向右对齐，以实现自然的阅读流。

在上述示例中，`Cost` 的最后一列已右对齐，以提供更明显的视觉区分。

## 可见性 {#visibility}

可以设置 `Column` 的可见性，以确定它是否将在表中显示。这在确定是否显示敏感信息等情况下非常有用。

使用 `setHidden()` 方法，如下所示，在 `Column` 上设置此属性：

`table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);`

## 可导航性 {#navigable}

可导航属性决定用户在导航过程中是否可以与列进行交互。将 `setSuppressNavigable()` 设置为 true 限制用户与列的互动，提供只读体验。

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## 标签 {#label}

列的标签是其公开标识，有助于清晰理解所显示的数据。使用 setLabel 设置或修改与列关联的标签。

:::tip
默认情况下，标签将与列 ID 相同
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

## 最小宽度 {#minimum-width}

 `setMinWidth()` 方法允许您定义列的最小宽度，以确保一致且美观的布局。

如果未提供最小宽度，表将根据列内容计算最小宽度。

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100.0);
```

:::info
传递的值表示所需的最小宽度（以像素为单位）。  
:::
