---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 2c6b6a04c5c33d9e3f1663d15c85a2e9
---
排序允许用户按照顺序排列列中的数据，使信息更易于阅读和分析。当用户需要快速找到特定列中的最高或最低值时，这非常有用。

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

默认情况下，列是不可排序的，除非明确启用。要允许在特定列上进行排序，请使用 `setSortable(true)` 方法：

```java 
table.getColumn("Age").setSortable(true);
```

## 多重排序 {#multi-sorting}

:::warning 默认情况下，webforJ `25.00` 中禁用多列排序
在 webforj `25.00` 之前，表格默认支持多列排序。从版本 `25.00` 开始，这一行为发生了变化——开发人员现在需要明确启用多列排序。
:::

如果需要多重排序，则必须对表应用 `setMultiSorting(true)`。这允许用户按顺序对多个列进行排序：

```java
table.setMultiSorting(true);
```

启用多重排序后，点击多个列头将按顺序对它们进行排序。排序优先级在表格 UI 中以视觉方式指示。

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

您还可以为服务器端排序以编程方式定义排序优先级。使用 `setSortOrder()` 对要排序的列按优先级进行设置：

```java
// 服务器端排序顺序
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info 列顺序很重要
除非使用 `setSortOrder()`，否则表格默认按列声明的顺序排序。
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## 排序方向 {#sort-direction}

可用的列排序方向有三种设置：

- `SortDirection.ASC`: 按升序对列进行排序。
- `SortDirection.DESC`: 按降序对列进行排序。
- `SortDirection.NONE`: 不对列应用排序。

当一列启用排序时，您将在该列顶端看到一组垂直箭头指示符。这些箭头允许用户在不同的排序方向之间切换。

选择升序时，将显示一个 `^`，而降序将显示一个 `v`。

## 客户端与服务器端排序 {#client-vs-server-side-sorting}

数据排序可以大致分为两种主要方法：**客户端排序** 和 **服务器排序**。

### 客户端排序 {#client-sorting}

客户端排序涉及在客户端应用程序的用户界面中直接排列和显示数据。它是用户点击列标题时与之交互的排序，会影响屏幕上数据的可视表示。

开发人员对客户端排序没有直接控制，而是由提供的 Java 列类型决定。目前支持以下类型：

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
当客户端仅可用部分数据时，客户端排序无法工作。
:::

### 服务器排序 {#server-sorting}

与客户端排序相对，服务器排序涉及在服务器上排列和组织数据，然后将其传输到客户端。这种方法在处理大量数据集时特别有用，这些数据集完全传输到客户端可能不切实际。

开发人员对服务器排序的逻辑具有更大的控制权。这允许实施复杂的排序算法和优化，使其适用于大数据量的场景。这样可以确保客户端接收预排序的数据，从而最小化客户端处理的需要。

:::info
服务器排序是一种面向性能的策略，旨在处理超出高效客户端处理能力的数据集，并是 `Table` 使用的默认方法。
:::

#### 比较器 {#comparators}

`Column` 组件允许开发人员使用 Java `Comparators` 进行动态和自定义排序。`Comparator` 是一种用于对同一类的两个对象进行排序的机制，即使该类是用户定义的。此功能为开发人员提供了自定义排序方式的灵活性，提供了更高的控制权，以基于自然顺序自定义默认排序行为。

要在 `Column` 中利用 `Comparator` 排序，可以使用 `setComparator()` 方法。此方法允许您定义一个自定义 `Comparator` 函数来描述排序逻辑。

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

在上面的示例中，指定了一个自定义比较器函数，该函数接收两个元素（a 和 b），并根据 `Number` 属性的解析整数值定义排序顺序。

在处理非数值类型时，使用 Comparators 进行列排序特别有用。它们也可以用于实现复杂的排序算法。

:::info
默认情况下，`Table` 使用服务器端排序，并使用对象的 `toString()` 方法对非原始值进行排序，将它们转换为字符串值，然后进行排序。
:::
