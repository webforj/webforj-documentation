---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 99281603bebefd43f033e9d0c958c366
---
排序允许用户按顺序排列数据列，使信息更易于阅读和分析。当用户需要快速找到特定列中的最高或最低值时，这一点特别有用。

:::tip 管理和查询数据
有关如何使用 `Repository` 模式管理和查询集合的信息，请参见 [Repository 相关文章](/docs/advanced/repository/overview)。
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

:::warning 在 webforJ `25.00` 中默认禁用多列排序
在 webforj `25.00` 之前，表格默认支持多列排序。从版本 `25.00` 开始，该行为发生了变化——开发人员现在需要明确启用多列排序。
:::

如果需要多重排序，必须在表格上应用 `setMultiSorting(true)`。这允许用户按顺序对多列进行排序：

```java
table.setMultiSorting(true);
```

启用多重排序后，点击多个列标题将依次对它们进行排序。排序优先级在表格 UI 中可视化指示。

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

您还可以为服务器端排序以编程方式定义排序优先级。在您想要排序的列上使用 `setSortOrder()`，按照优先级排列：

```java
// 服务器端排序顺序
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info 列顺序重要
除非使用 `setSortOrder()`，否则表格默认按声明列的顺序进行排序。
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## 排序方向 {#sort-direction}

对列的排序方向有三种可用设置：

- `SortDirection.ASC`：按升序对列进行排序。
- `SortDirection.DESC`：按降序对列进行排序。
- `SortDirection.NONE`：不对列应用排序。

当列启用排序时，您会看到一组垂直箭头指示器出现在该列的顶部。这些箭头允许用户在不同的排序方向之间切换。

选择升序时，将显示一个 `^`，而选择降序时将显示一个 `v`。

## 客户端与服务器端排序 {#client-vs-server-side-sorting}

数据排序大致可以分为两种主要方法：**客户端排序** 和 **服务器排序**。

### 客户端排序 {#client-sorting}

客户端排序涉及在客户端应用程序的用户界面中直接排列和显示数据。这是用户在点击列标题时进行交互的排序，影响数据在屏幕上的可视化表示。

开发人员对客户端排序没有直接控制，而是由在 Java 中提供的列类型决定。目前支持以下类型：

- 文本
- 数字
- 布尔值
- 日期
- 日期时间
- 时间

:::info
当客户端仅有部分数据时，客户端排序将不起作用。
:::

### 服务器排序 {#server-sorting}

与客户端排序相比，服务器排序是在将数据传输到客户端之前，在服务器上排列和组织数据。这种方法在处理可能不适合完全传输到客户端的大型数据集时特别有用。

开发人员对服务器排序的逻辑有更多控制。这允许实现复杂的排序算法和优化，使其适用于大量数据的场景。这确保客户端接收预排序的数据，最小化了对客户端处理的需求。

:::info
服务器排序是一种以性能为导向的策略，适用于超过高效客户端处理能力的数据集，是 `Table` 使用的默认方法。
:::

### 列属性名称 {#column-property-name}

默认情况下，`Table` 使用列的 ID 作为构建后端存储库排序条件的属性名称。当列的显示 ID 与底层数据属性不匹配，或当列显示计算值时，可以使用 `setPropertyName()` 明确告知 `Table` 要按哪个属性排序。

```java
// 列 ID 为 "Full Name"，但后端属性为 "fullName"
table.addColumn("Full Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

当排序事件触发时，属性名称将转发到 `OrderCriteria`，允许后端存储库（例如 Spring Data JPA 或 REST 适配器）构建正确的 `ORDER BY` 子句。

:::warning
如果没有 `setPropertyName()`，`Table` 将回退到列 ID。如果这与有效的后端属性不匹配，排序将默默失败或返回不正确的排序数据。
:::

也支持使用点符号表示的嵌套属性路径：

```java
table.addColumn("City", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### 比较器 {#comparators}

`Column` 组件允许开发人员使用 Java `Comparators` 进行动态和自定义排序。比较器是一种用于对两个相同类的对象进行排序的机制，即使该类是用户定义的。这种功能为开发人员提供了自定义数据排序的灵活性，提供了更高的控制权，以便基于自然顺序进行默认排序。

要在 `Column` 中利用 `Comparator` 排序，可以使用 `setComparator()` 方法。此方法允许您定义一个自定义 `Comparator` 函数，指明排序逻辑。

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

在上面的示例中，指定了一个自定义比较器函数，该函数接受两个元素（a 和 b），并根据解析的 `Number` 属性的整数值定义排序顺序。

在处理非数值值时，使用比较器进行列排序特别有用。它们还便于实现复杂的排序算法。

:::info
默认情况下，`Table` 使用服务器端排序，并使用 Object 的 `toString()` 方法对非原始值进行排序，将它们转换为字符串值，然后进行排序。
:::
