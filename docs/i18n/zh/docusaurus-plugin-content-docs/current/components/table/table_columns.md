---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: b3545c590336bb6574bf196fbd417349
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

`Table` 类利用 `Column` 类在其中处理数据列的创建。此类具有广泛的功能，使用户能够彻底自定义每一列中显示的内容。
要向 `Table` 添加 `Column`，请使用其中一个接受提供者作为参数的 `addColumn` 工厂方法。

## 值提供者 {#value-providers}

值提供者是一个函数，负责将来自基础数据集的原始数据转换为适合在特定列中显示的格式。由用户定义的函数接受行数据类型 (T) 的实例，并返回要在该特定行的关联列中展示的值。

要在列上设置值提供者，请使用一个接受提供者作为参数的 `addColumn` 工厂方法：

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

在此示例中，列将尝试访问 JSON 对象中的数据，仅当数据不为 null 时才呈现。

## 固定方向 {#pin-direction}

列固定是一项功能，允许用户将列固定或“钉住”在表的特定侧面，从而增强可见性和可访问性。当某些列，例如标识符或重要信息，需要在水平滚动表格时保持可见时，这将是非常有用的。

<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

有三种可用的列固定方向：

- `PinDirection.LEFT`: 将列固定在左侧。
- `PinDirection.RIGHT`: 将列固定在右侧。
- `PinDirection.AUTO`: 列根据插入顺序出现。

固定可以通过编程方式设置，允许用户根据用户交互或应用逻辑更改固定方向。

## 对齐 {#alignment}

列对齐定义了列中数据的水平位置。它影响数据值的显示方式，为用户提供关于信息性质的视觉指南。

<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

表格组件支持三种主要对齐选项：

- `Column.Alignment.LEFT`: 适用于文本或描述性数据，其中保持左向流动是直观的。用于强调内容的起始点。
- `Column.Alignment.CENTER`: 适合于希望呈现均衡的数值或分类数据。创建视觉中心的显示。
- `Column.Alignment.RIGHT`: 通常用于数值数据，特别是当数字的大小或精度很重要时。将数据向右对齐，以便自然的阅读流。

在上述示例中，`Cost` 的最后一列已右对齐，以提供更明显的视觉区分。

## 可见性 {#visibility}

可以设置 `Column` 的可见性，决定其是否在表中显示。这在确定是否显示敏感信息等情况下非常有用。

使用 `setHidden()` 方法，如下所示，以在 `Column` 上设置此属性：

`table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);`

## 可导航 {#navigable}

可导航属性决定用户在导航时是否可以与列交互。将 `setSuppressNavigable()` 设置为 true 会限制用户与该列的交互，提供只读体验。

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## 标签 {#label}

列的标签是其面向公众的标识，有助于数据清晰性和理解。使用 setLabel 设置或修改与列关联的标签。

:::tip
默认情况下，标签将与列 ID 相同
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

## 最小宽度 {#minimum-width}

 `setMinWidth()` 方法允许您定义列的最小宽度，以确保一致和美观的布局。

 如果未提供最小宽度，则表将根据列内容计算最小宽度。

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100.0);
```

:::info
传递的值表示所需的最小宽度（以像素为单位）。  
:::
