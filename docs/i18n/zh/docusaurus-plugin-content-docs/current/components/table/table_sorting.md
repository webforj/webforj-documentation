---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: a51ea10e855e94a24cb6e74d8f774abe
---
排序让用户按照顺序排列列中的数据，使信息更易于阅读和分析。当用户需要快速找到特定列中的最大或最小值时，这非常有用。

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

默认情况下，列不可排序，除非显式启用。要允许对特定列进行排序，请使用 `setSortable(true)` 方法：

```java 
table.getColumn("Age").setSortable(true);
```

## 多重排序 {#multi-sorting}

:::warning 默认情况下，webforJ `25.00` 中禁用了多列排序
在 webforj `25.00` 之前，表格默认支持多列排序。从版本 `25.00` 开始，此行为发生了变化——开发人员现在需要显式启用多列排序。
:::

如果需要多重排序，必须对表格应用 `setMultiSorting(true)`。这允许用户按顺序对多个列进行排序：

```java
table.setMultiSorting(true);
```

启用多重排序后，点击多个列标题将按顺序对它们进行排序。排序优先级将在表格 UI 中以视觉方式指示。

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

您还可以为服务器端排序以编程方式定义排序优先级。使用 `setSortOrder()` 方法设置您希望按优先级排序的列：

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

列的排序方向有三种可用设置：

- `SortDirection.ASC`：按升序排序列。
- `SortDirection.DESC`：按降序排序列。
- `SortDirection.NONE`：列不应用排序。

当列启用了排序时，您会看到一组垂直箭头指示器出现在该列的顶部。这些箭头允许用户在不同的排序方向之间切换。

选择升序时，将显示 `^`，而选择降序时将显示 `v`。


## 客户端与服务器端排序 {#client-vs-server-side-sorting}

数据排序通常可以大致分为两种主要方法：**客户端排序**和**服务器排序**。

### 客户端排序 {#client-sorting}

客户端排序涉及在客户端应用程序的用户界面中直接排列和显示数据。这是用户在点击列标题时进行交互的排序，影响数据在屏幕上的视觉表现。

开发人员对客户端排序没有直接控制，而是由提供的 Java 列类型决定。目前支持以下类型：

- 文本
- 数字
- 布尔值
- 日期
- 日期时间
- 时间

:::info
当客户端仅有部分数据可用时，客户端排序将不起作用。
:::

### 服务器排序 {#server-sorting}

与客户端排序相对，服务器排序涉及在将数据传输到客户端之前，在服务器上排列和组织数据。这种方法在处理可能不便于完全传输到客户端的大型数据集时特别有用。

开发人员对服务器排序的逻辑有更多的控制。这允许实现复杂的排序算法和优化，使其适用于大量数据的场景。这样确保客户端接收到预先排序的数据，最小化了对大量客户端处理的需求。

:::info
服务器排序是一种面向性能的策略，用于处理超出高效客户端处理能力的数据集，也是 `Table` 的默认方法。
:::

#### 比较器 {#comparators}

`Column` 组件允许开发人员使用 Java `Comparators` 进行动态和自定义排序。`Comparator` 是一种用于对同一类的两个对象进行排序的机制，即使该类是用户定义的。这一功能为开发人员提供了定制数据排序的灵活性，根据自然排序提供更高的控制。

要在 `Column` 中利用 `Comparator` 排序，可以使用 `setComparator()` 方法。该方法允许您定义自定义的 `Comparator` 函数，决定排序逻辑。

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

在上述示例中，指定了一个自定义比较器函数，该函数接受两个元素（a 和 b），并根据 `Number` 属性的解析整数值定义排序顺序。

在处理非数值时，使用比较器进行列排序特别有用。它们也可用于实现复杂的排序算法。

:::info
默认情况下，`Table` 使用服务器端排序，并使用 Object 的 `toString()` 方法对非原始值进行排序，将其转换为字符串值，然后排序。
:::
