---
sidebar_position: 15
title: Sorting
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: 94345d1313939015f02cf46ecaa5af99
---
排序允许用户通过顺序排列列中的数据，使信息更易于阅读和分析。当用户需要快速找到某一列中的最大或最小值时，这非常有用。

:::tip 管理和查询数据
有关如何使用 `Repository` 模式来管理和查询集合的信息，请参阅 [Repository 文章](/docs/advanced/repository/overview)。
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

默认情况下，列不可排序，除非显式启用。要允许对特定列进行排序，请使用 `setSortable(true)` 方法：

```java
table.getColumn("Age").setSortable(true);
```

## 多重排序 {#multi-sorting}

:::warning webforJ `25.00` 中默认禁用多列排序
在 webforj `25.00` 之前，表格默认支持多列排序。从版本 `25.00` 开始，此行为发生了变化——开发人员现在需要显式启用多列排序。
:::

如果需要多重排序，必须对表应用 `setMultiSorting(true)`。这允许用户依次对多个列进行排序：

```java
table.setMultiSorting(true);
```

启用多重排序后，单击多个列标题将按照顺序对它们进行排序。排序优先级在表格用户界面中以视觉方式表示。

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

您还可以为服务器端排序以编程方式定义排序优先级。在您希望排序的列上按优先级顺序使用 `setSortIndex()`：

```java
// 服务器端排序顺序
nameColumn.setSortIndex(1);
ageColumn.setSortIndex(2);
```

:::info 列顺序很重要
除非使用 `setSortIndex()`，否则表格默认按列声明的顺序进行排序。
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

可以为列的排序方向选择三种可用设置：

- `SortDirection.ASC`: 按升序排序列。
- `SortDirection.DESC`: 按降序排序列。
- `SortDirection.NONE`: 列没有排序应用。

当列启用排序时，您会在已设置的列顶部看到一组垂直箭头指示器。这些箭头允许用户在不同的排序方向之间切换。

当选择升序时，显示 `^`，而降序则显示 `v`。


## 客户端 vs. 服务器端排序 {#client-vs-server-side-sorting}

数据排序可以大致分为两种主要方法：**客户端排序**和**服务器排序**。

### 客户端排序 {#client-sorting}

客户端排序涉及直接在客户端应用程序的用户界面中排列和显示数据。当用户单击列标题时，这就是用户交互的排序方式，会影响屏幕上数据的视觉表示。

开发人员无法直接控制客户端排序，而是由在Java中提供的列类型决定。目前支持以下类型：

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
在客户端仅有部分数据可用时，客户端排序无法正常工作。
:::

### 服务器排序 {#server-sorting}

与客户端排序不同，服务器排序是在服务器上排列和组织数据，然后将其传输到客户端。这种方法在处理可能不切实际地完全转移到客户端的大型数据集时特别有利。

开发人员在服务器排序的逻辑上具有更多的控制权。这允许实施复杂的排序算法和优化，使其适用于大量数据的场景。这确保客户端接收预排序的数据，最小化了大量客户端处理的需求。

:::info
服务器排序是一种面向性能的策略，用于处理超出有效客户端处理能力的数据集，是 `Table` 使用的默认方法。
:::

### 列属性名称 {#column-property-name}

默认情况下，`Table` 使用列的 ID 作为构建后端存储库排序条件的属性名称。当列的显示 ID 与底层数据属性不匹配，或者当列显示计算值时，使用 `setPropertyName()` 明确告诉 `Table` 要按哪个属性排序。

```java
// 列 ID 为 "Full Name"，但后端属性为 "fullName"
table.addColumn("Full Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

当排序事件触发时，属性名称会转发给 `OrderCriteria`，使得后端存储库如 Spring Data JPA 或 REST 适配器能够构建正确的 `ORDER BY` 子句。

:::warning
如果没有 `setPropertyName()`，`Table` 将回退到列 ID。如果这与一个有效的后端属性不匹配，排序可能会静默失败或返回错误的排序数据。
:::

也支持使用点符号表示法的嵌套属性路径：

```java
table.addColumn("City", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### 比较器 {#comparators}

`Column` 组件允许开发人员使用 Java `Comparators` 进行动态和自定义排序。`Comparator` 是一种用于对同一类的两个对象进行排序的机制，即使该类是用户定义的。这项功能为开发人员提供了定制数据排序方式的灵活性，使其更好地控制基于自然顺序的默认排序行为。

要在 `Column` 中利用 `Comparator` 排序，可以使用 `setComparator()` 方法。该方法允许您定义一个自定义的 `Comparator` 函数，规定排序逻辑。

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

在上述示例中，指定了一个自定义比较器函数，它接收两个元素（a 和 b），并基于 `Number` 属性的解析整数值定义排序顺序。

使用比较器进行列排序在处理非数值值时特别有用。它们也用于实现复杂的排序算法。

:::info
默认情况下，`Table` 使用服务器端排序，并使用对象的 `toString()` 方法对非原始值进行排序，将它们转换为字符串值，然后进行排序。
:::
