---
sidebar_position: 15
title: Sorting
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: f577bea532193b97e6fef03a8bcb641b
---
排序允许用户按顺序排列列中的数据，使信息更易于阅读和分析。这在用户需要快速找到特定列中的最高或最低值时特别有用。

:::tip 管理和查询数据
有关如何使用 `Repository` 模式管理和查询集合的信息，请参阅 [Repository 文章](/docs/advanced/repository/overview)。
:::

<ComponentDemo
path='/webforj/tablesorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

默认情况下，列是不可排序的，除非显式启用。要允许特定列排序，请使用 `setSortable(true)` 方法：

```java
table.getColumn("Age").setSortable(true);
```

## 多重排序 {#multi-sorting}

:::warning webforJ `25.00` 中默认禁用多列排序
在 webforj `25.00` 之前，表格默认支持多列排序。从版本 `25.00` 开始，这种行为发生了变化——开发人员现在需要显式启用多列排序。
:::

如果需要多重排序，必须对表应用 `setMultiSorting(true)`。这允许用户按顺序对多个列进行排序：

```java
table.setMultiSorting(true);
```

启用多重排序后，单击多个列标题将按顺序对它们进行排序。排序优先级在表格用户界面中以视觉方式指示。

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

您还可以为服务器端排序以编程方式定义排序优先级。对您希望按优先级排序的列使用 `setSortOrder()`：

```java
// 服务器端排序顺序
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info 列顺序重要
除非使用 `setSortOrder()`，否则表默认为按列声明的顺序进行排序。
:::

<ComponentDemo
path='/webforj/tablesortorder'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## 排序方向 {#sort-direction}

列可以排序的方向有三种可用设置：

- `SortDirection.ASC`：按升序对列进行排序。
- `SortDirection.DESC`：按降序对列进行排序。
- `SortDirection.NONE`：对列不应用排序。

当列启用排序时，您会在该列的顶部看到一组垂直箭头指示符。这些箭头允许用户在不同的排序方向之间切换。

选择升序时，将显示一个 `^`，而降序将显示一个 `v`。

## 客户端与服务器端排序 {#client-vs-server-side-sorting}

数据的排序大致可以分为两种主要方法：**客户端排序** 和 **服务器排序**。

### 客户端排序 {#client-sorting}

客户端排序涉及直接在客户端应用程序的用户界面中排列和显示数据。这是用户在单击列标题时与之交互的排序，影响数据在屏幕上的可视表示。

开发人员对客户端排序没有直接控制，排序由 Java 中提供的列类型决定。当前支持以下类型：

- 文本
- 数字
- 布尔值
- 日期
- 日期时间
- 时间

:::info
当客户端仅可用部分数据时，客户端排序不起作用。
:::

### 服务器排序 {#server-sorting}

与客户端排序相反，服务器排序是在将数据发送到客户端之前在服务器上排列和组织数据。这种方法在处理可能不切实际完全传输到客户端的大型数据集时特别有用。

开发人员对服务器排序的逻辑有更多控制。这允许实现复杂的排序算法和优化，使其适用于大数据场景。这确保客户端接收到预排序的数据，最小化了大量客户端处理的需求。

:::info
服务器排序是一种性能导向策略，用于处理超出高效客户端处理能力的数据集，并且是 `Table` 使用的默认方法。
:::

### 列属性名称 {#column-property-name}

默认情况下，`Table` 使用列的 ID 作为构建后端存储库排序标准的属性名称。当列的显示 ID 与底层数据属性不匹配，或当列显示计算值时，请使用 `setPropertyName()` 明确告诉 `Table` 按哪个属性排序。

```java
// 列 ID 为 "Full Name"，但后端属性为 "fullName"
table.addColumn("Full Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

在触发排序事件时，属性名称会转发到 `OrderCriteria`，使后端存储库（例如 Spring Data JPA 或 REST 适配器）构建正确的 `ORDER BY` 子句。

:::warning
不使用 `setPropertyName()` 时，`Table` 会回退到列 ID。如果这不匹配有效的后端属性，排序将静默失败或返回错误排序的数据。
:::

也支持使用点标记法的嵌套属性路径：

```java
table.addColumn("City", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### 比较器 {#comparators}

`Column` 组件允许开发人员使用 Java `Comparators` 进行动态和自定义排序。`Comparator` 是用于对同一类的两个对象排序的机制，即使该类是用户定义的。此功能为开发人员提供了自定义数据排序的灵活性，根据自然顺序提供对默认排序行为的更高控制。

要在 `Column` 中利用 `Comparator` 排序，您可以使用 `setComparator()` 方法。该方法允许您定义一个自定义的 `Comparator` 函数，该函数规定排序逻辑。

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

在上述示例中，指定了一个自定义比较器函数，该函数接受两个元素 (a 和 b)，并根据 `Number` 属性的解析整数值定义排序顺序。

在处理非数字值时，使用比较器进行列排序特别有用。它们对于实现复杂的排序算法也非常有用。

:::info
默认情况下，`Table` 使用服务器端排序，并使用对象的 `toString()` 方法对非原始值进行排序，将它们转换为字符串值，然后再排序。
:::
