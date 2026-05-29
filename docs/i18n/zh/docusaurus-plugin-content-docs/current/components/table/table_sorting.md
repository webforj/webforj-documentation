---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 16c35fa416c4ebba3b680deb2d8925ef
---
排序让用户能够按顺序排列数据列，使信息更容易阅读和分析。这在用户需要快速找到特定列中的最高或最低值时特别有用。

:::tip 管理和查询数据
有关如何使用 `Repository` 模式来管理和查询集合的信息，请参见 [Repository 文章](/docs/advanced/repository/overview)。
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

默认情况下，列不支持排序，除非显式启用。要允许对特定列进行排序，使用 `setSortable(true)` 方法：

```java 
table.getColumn("Age").setSortable(true);
```

## 多重排序 {#multi-sorting}

:::warning 在 webforJ `25.00` 中默认禁用多列排序
在 webforj `25.00` 之前，表格默认为支持多列排序。从版本 `25.00` 开始，这一行为发生了变化——开发人员现在需要显式启用多列排序。
:::

如果需要多重排序，必须对表应用 `setMultiSorting(true)`。这样用户就可以按顺序对多个列进行排序：

```java
table.setMultiSorting(true);
```

启用多重排序后，单击多个列标题将按顺序对它们进行排序。排序优先级在表格用户界面中以可视方式指示。

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

您还可以为服务器端排序以编程方式定义排序优先级。对要排序的列使用 `setSortOrder()`，按照优先级顺序进行：

```java
// 服务器端排序顺序
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info 列顺序很重要
除非使用 `setSortOrder()`，否则表格默认为按照声明列的顺序进行排序。
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

对列进行排序时，有三种可用设置：

- `SortDirection.ASC`：按升序排序列。
- `SortDirection.DESC`：按降序排序列。
- `SortDirection.NONE`：不对列应用排序。

当列启用排序时，您将看到一组垂直箭头指示器出现在列标题顶部。这些箭头允许用户在不同的排序方向之间切换。

选择升序时，会显示 `^`，而降序则显示 `v`。

## 客户端与服务器端排序 {#client-vs-server-side-sorting}

数据排序大致可分为两种主要方法：**客户端排序**和**服务器排序**。

### 客户端排序 {#client-sorting}

客户端排序涉及在客户端应用程序的用户界面中直接排列和显示数据。当用户单击列标题时，它是影响数据在屏幕上可视化表示的排序。

开发人员无法直接控制客户端排序，而是由 Java 中提供的列类型决定。目前支持以下类型：

- 文本
- 数字
- 布尔值
- 日期
- 日期时间
- 时间

:::info
当客户端仅可用部分数据时，客户端排序将无法工作。
:::

### 服务器排序 {#server-sorting}

与客户端排序相反，服务器排序涉及在服务器上对数据进行整理和组织，然后将其传输到客户端。这种方法在处理可能不切实际的庞大数据集时特别有用。

开发人员可以更好地控制服务器排序的逻辑。这使得能够实施复杂的排序算法和优化，适用于大数据量的场景。这确保客户端接收预排序的数据，从而最小化对广泛客户端处理的需求。

:::info
服务器排序是一种面向性能的策略，适用于超过高效客户端处理能力的数据集，是 `Table` 使用的默认方法。
:::

### 列属性名称 {#column-property-name}

默认情况下，`Table` 在为后端存储构建排序条件时使用列的 ID 作为属性名称。当列的显示 ID 与底层数据属性不匹配，或者当列显示计算值时，使用 `setPropertyName()` 显式告诉 `Table` 按哪个属性进行排序。

```java
// 列 ID 是 "Full Name"，但后端属性是 "fullName"
table.addColumn("Full Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

当排序事件触发时，属性名称会转发到 `OrderCriteria`，允许后端存储（如 Spring Data JPA 或 REST 适配器）构建正确的 `ORDER BY` 子句。

:::warning
如果没有 `setPropertyName()`，`Table` 将回退到列 ID。如果这与有效的后端属性不匹配，排序将静默失败或返回错误顺序的数据。
:::

也支持使用点表示法的嵌套属性路径：

```java
table.addColumn("City", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### 比较器 {#comparators}

`Column` 组件允许开发人员使用 Java `Comparators` 进行动态和自定义排序。`Comparator` 是一种用于排序相同类的两个对象的机制，即使该类是用户定义的。此功能为开发人员提供了自定义数据排序的灵活性，提供比默认排序行为更高的控制。

要在 `Column` 中利用 `Comparator` 排序，可以使用 `setComparator()` 方法。此方法允许您定义一个自定义的 `Comparator` 函数，该函数指示排序逻辑。

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

在上面的示例中，指定了一个自定义比较器函数，该函数接受两个元素（a 和 b），并根据 `Number` 属性的解析整数值定义排序顺序。

对于处理非数值类型时，使用比较器进行列排序特别有用。它们还适用于实现复杂的排序算法。

:::info
默认情况下，`Table` 使用服务器端排序，并使用对象的 `toString()` 方法对非基本值进行排序，将它们转换为字符串值，然后进行排序。
:::
