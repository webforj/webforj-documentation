---
sidebar_position: 1
title: Table
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 9e123638ff60f46c96d369bce79da44e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

`Table` 类是一个多功能组件，旨在以结构化和易于理解的方式呈现表格信息。该组件经过优化，能够高效处理大型数据集，提供高级可视化效果，并拥有一整套事件以实现动态用户交互。

<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## 创建一个 `Table` {#creating-a-table}

为了在应用程序中创建和填充一个 `Table`，可以采取以下步骤：

### 1. 创建实体类 {#1-create-an-entity-class}

定义一个类来表示您想在表中显示的实体（数据）。在例子中，这个类是 MusicRecord。

```java
public class MusicRecord {
    // 表示每个记录属性的字段和方法
}
```

### 2. 创建一个仓库 {#2-create-a-repository}

一旦创建了实体类，就可以使用它来填充一个包含所需数据的实体集合。

基于这些数据，需要创建一个 `Repository`，以用于 `Table`。提供的 `CollectionRepository` 类可将任何有效的 Java 集合转换为可用的 `Repository`，从而省去自己实现 `Repository` 类的需要。

```java
List<MusicRecord> data = new Gson().fromJson(
    Assets.contentOf(
        Assets.resolveContextUrl("context://data/CDStore.json")
    ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

### 3. 实例化 `Table` 并添加列 {#3-instantiate-table-and-add-columns}

实例化一个新的 `Table` 对象，并使用提供的工厂方法之一将所需列添加到新创建的 `Table` 中：

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("编号", MusicRecord::getNumber);
table.addColumn("标题", MusicRecord::getTitle);
table.addColumn("艺术家", MusicRecord::getArtist);
table.addColumn("类型", MusicRecord::getMusicType);
table.addColumn("费用", MusicRecord::getCost);
```

### 4. 设置 `Table` 数据 {#4-set-the-table-data}

最后，为在前一步中创建的 `Table` 设置 `Repository`：

```java
table.setRepository(Service.getMusicRecords());
```

:::info
另外，`setItems()` 方法也可以传入任何有效的 Java 集合，这会在后台为您创建一个 `CollectionRepository`。
:::

以下是实现上述步骤以创建基本 `Table` 组件的示例：

<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## 样式

<TableBuilder name="Table" />
