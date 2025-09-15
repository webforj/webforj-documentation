---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 3c9156ad5da204816bd4ce783003cbf7
---
排序使用户能够按顺序排列列中的数据，从而使信息更易于阅读和分析。当用户需要快速找到特定列中的最高或最低值时，这一点非常有用。

:::tip 数据管理和查询
有关如何使用 `Repository` 模式来管理和查询集合的信息，请参阅 [Repository 文章](/docs/advanced/repository/overview)。
:::

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

默认情况下，列不可排序，除非明确启用。要允许对特定列进行排序，请使用 `setSortable(true)` 方法：

```java 
table.getColumn("Age").setSortable(true);
```

## 多重排序 {#multi-sorting}

:::warning webforJ `25.00` 默认禁用多列排序
在 webforj `25.00` 之前，表格默认支持多列排序。从版本 `25.00` 开始，此行为发生了变化——开发人员现在需要明确启用多列排序。
:::

如果需要多重排序，必须在表上应用 `setMultiSorting(true)`。这允许用户按顺序对多个列进行排序：

```java
table.setMultiSorting(true);
```

启用多重排序后，点击多个列标题将按顺序进行排序。排序优先级在表 UI 中以视觉方式显示。

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

您还可以为服务器端排序以编程方式定义排序优先级。按优先级使用 `setSortOrder()` 在要排序的列上：

```java
// 服务器端排序顺序
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info 列顺序很重要
除非使用 `setSortOrder()`，否则表格默认按列声明的顺序进行排序。
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## 排序方向 {#sort-direction}

列可以排序的方向有三种可用设置：

- `SortDirection.ASC`: 按升序对列进行排序。
- `SortDirection.DESC`: 按降序对列进行排序。
- `SortDirection.NONE`: 对列不应用排序。

当列启用排序时，您会看到一组垂直箭头指示符出现在设定列的顶部。这些箭头允许用户在不同的排序方向之间切换。

当选择升序时，将显示 `^`，而降序则显示 `v`。

## 客户端与服务器端排序 {#client-vs-server-side-sorting}

数据排序大致可以分为两种主要方法：**客户端排序**和**服务器排序**。

### 客户端排序 {#client-sorting}

客户端排序涉及在客户端应用程序的用户界面中直接排列和显示数据。这是用户在单击列标题时与之交互的排序，会影响屏幕上数据的视觉表示。

开发人员对客户端排序没有直接控制权，而是由 Java 中提供的列类型决定。目前支持以下类型：

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
当客户端只提供部分数据时，客户端排序不起作用。
:::

### 服务器排序 {#server-sorting}

与客户端排序相反，服务器排序是在服务器上排列和组织数据，然后再将其传输到客户端。这种方法在处理可能不切实际完全传输到客户端的大型数据集时特别有益。

开发人员对服务器排序的逻辑具有更大的控制权。这允许实现复杂的排序算法和优化，使其适合于处理大量数据的场景。这样可以确保客户端接收到预排序的数据，最小化大量客户端处理的需要。

:::info
服务器排序是一种面向性能的策略，适用于超出高效客户端处理能力的数据集，并且是 `Table` 默认使用的方法。
:::

#### 比较器 {#comparators}

`Column` 组件允许开发人员使用 Java `Comparators` 进行动态和自定义排序。`Comparator` 是用于对两个相同类的对象进行排序的机制，即使该类是用户定义的。此功能为开发人员提供了自定义数据排序方式的灵活性，使其对基于自然排序的默认排序行为具有更高的控制。

要在 `Column` 中利用 `Comparator` 排序，您可以使用 `setComparator()` 方法。此方法允许您定义一个自定义 `Comparator` 函数，该函数决定排序逻辑。

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

在上面的示例中，指定了一个自定义的比较器函数，该函数接受两个元素（a 和 b），并根据 `Number` 属性解析的整数值来定义排序顺序。

在处理非数字值时，使用比较器进行列排序特别有用。它们也用于实现复杂的排序算法。

:::info
默认情况下，`Table` 使用服务器端排序，并使用对象的 `toString()` 方法对非原始值进行排序，将其转换为字符串值，然后进行排序。
:::
