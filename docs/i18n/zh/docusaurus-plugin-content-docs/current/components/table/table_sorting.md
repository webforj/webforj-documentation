---
sidebar_position: 15
title: 排序
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: 7a910f5bdcaa8a9e053bcda6706808c2
---
排序功能允许用户按列排列数据，让信息更易于阅读和分析。当用户需要快速查找某一列中的最大值或最小值时，这项功能会很有用。

:::tip 管理和查询数据
有关如何使用 `Repository` 模式管理和查询集合的信息，请参阅 [Repository 相关文章](/docs/advanced/repository/overview)。
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

默认情况下，除非显式启用，否则列不可排序。若要允许对特定列进行排序，请使用 `setSortable(true)` 方法：

```java
table.getColumn("Age").setSortable(true);
```

## 多列排序 {#multi-sorting}

:::warning webforJ `25.00` 默认禁用多列排序
在 webforj `25.00` 之前，表格默认支持多列排序。从 `25.00` 版本开始，此行为有所改变——开发者现在需要显式启用多列排序。
:::

如果需要多列排序，必须对表格应用 `setMultiSorting(true)`。这样用户便可按顺序对多个列进行排序：

```java
table.setMultiSorting(true);
```

启用多列排序后，单击多个列标题即可依次对这些列排序。排序优先级会在表格 UI 中以可视方式显示。

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

你也可以通过编程方式为服务器端排序定义排序优先级。对要排序的列使用 `setSortIndex()`，并按优先级顺序设置：

```java
// 服务器端排序顺序
nameColumn.setSortIndex(1);
ageColumn.setSortIndex(2);
```

:::info 列顺序很重要
除非使用 `setSortIndex()`，否则表格默认按列的声明顺序进行排序。
:::

<ComponentDemo
path='/webforj/tablesortorder'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSortOrderView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## 排序方向 {#sort-direction}

列的排序方向有以下三种设置：

- `SortDirection.ASC`：按升序排列该列。
- `SortDirection.DESC`：按降序排列该列。
- `SortDirection.NONE`：不对该列进行排序。

启用列排序后，你会看到一组垂直箭头指示符出现在指定列的顶部。这些箭头允许用户在不同的排序方向之间切换。

选择升序时会显示 `^`，选择降序时则会显示 `v`。


## 客户端排序与服务器端排序 {#client-vs-server-side-sorting}

数据排序大致可分为两种主要方式：**客户端排序**和**服务器端排序**。

### 客户端排序 {#client-sorting}

客户端排序是在客户端应用程序的用户界面中直接排列和显示数据。用户单击列标题时进行的排序就是客户端排序，它会影响数据在屏幕上的显示方式。

开发者无法直接控制客户端排序；其行为由 Java 中指定的列类型决定。目前支持以下类型：

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
如果客户端只有部分数据，客户端排序将无法生效。
:::

### 服务器端排序 {#server-sorting}

与客户端排序不同，服务器端排序是在服务器上排列和组织数据，然后再将数据传输到客户端。这种方式尤其适合处理大型数据集，因为将全部数据传输到客户端可能并不实际。

开发者对服务器端排序逻辑有更大的控制权，因此可以实现复杂的排序算法和优化，使其适用于数据量庞大的场景。这样，客户端收到的数据已经预先排序，从而减少客户端进行大量处理的需求。


:::info
服务器端排序是一种面向性能的策略，适用于处理超出客户端高效处理能力的数据集，也是 `Table` 默认使用的方式。
:::

### 列属性名称 {#column-property-name}

默认情况下，`Table` 使用列的 ID 作为属性名称，为后端仓库构建排序条件。如果列的显示 ID 与底层数据属性不匹配，或者列显示的是计算得出的值，请使用 `setPropertyName()` 明确告知 `Table` 应按哪个属性排序。

```java
// 列 ID 为 "Full Name"，但后端属性为 "fullName"
table.addColumn("Full Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

触发排序事件时，属性名称会传递给 `OrderCriteria`，这样 Spring Data JPA 或 REST 适配器等后端仓库便能构建正确的 `ORDER BY` 子句。

:::warning
如果不调用 `setPropertyName()`，`Table` 会回退到使用列 ID。如果该 ID 与有效的后端属性不匹配，排序可能会静默失败，或返回顺序错误的数据。
:::

也支持使用点号表示法指定嵌套属性路径：

```java
table.addColumn("City", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### 比较器 {#comparators}

`Column` 组件允许开发者使用 Java `Comparators` 实现动态排序和自定义排序。`Comparator` 是一种用于对同一类的两个对象进行排序的机制，即使该类由用户定义也适用。此功能让开发者能够灵活自定义数据的排序方式，并根据自然排序对默认排序行为进行更精细的控制。

若要在 `Column` 中使用 `Comparator` 排序，可以使用 `setComparator()` 方法。此方法允许你定义一个自定义 `Comparator` 函数，用来指定排序逻辑。

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

在上面的示例中，指定了一个自定义比较器函数。该函数接收两个元素（a 和 b），并根据 `Number` 属性解析后的整数值定义排序顺序。

使用比较器对列进行排序在处理非数值时尤其有用，也适用于实现复杂的排序算法。

:::info
默认情况下，`Table` 使用服务器端排序，并通过 Object 的 `toString()` 方法将非基本类型转换为字符串值，然后按字符串值对其排序。
:::
