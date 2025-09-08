---
sidebar_position: 1
title: Table
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 59525ff188c1c03526b8c601a82c7a76
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

`Table` 类是一个多功能组件，旨在以结构化和易于理解的方式展示表格信息。优化用于处理大型数据集并保持高性能，这个组件提供了先进的可视化功能和一整套事件，以便进行动态用户交互。

<!-- vale off -->
<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
height='600px'
/>
<!-- vale on -->

## 创建一个 `Table` {#creating-a-table}

为了在应用程序中创建和填充一个 `Table`，可以采取以下步骤：

### 1. 创建一个实体类 {#1-create-an-entity-class}

定义一个类来表示您想要在表中展示的实体（数据）。在这个例子中，这个类是 MusicRecord。

```java
public class MusicRecord {
    // 表示每个记录属性的字段和方法
}
```

### 2. 创建一个仓库 {#2-create-a-repository}

创建实体类之后，使用它来填充这些实体的集合，并为 `Table` 创建一个 `Repository`。提供的 `CollectionRepository` 类可将任何有效的 Java 集合转换为可用的 `Repository`，无需实现您自己的 `Repository` 类。

```java
List<MusicRecord> data = new Gson().fromJson(
    Assets.contentOf(
        Assets.resolveContextUrl("context://data/CDStore.json")
    ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

:::tip 更多信息
有关 webforJ 中 `Repository` 模式的更多信息，请参阅 [Repository 文章](/docs/advanced/repository/overview)。
:::

### 3. 实例化 `Table` 并添加列 {#3-instantiate-table-and-add-columns}

实例化一个新的 `Table` 对象，并使用提供的工厂方法之一将所需列添加到新创建的 `Table` 中：

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Number", MusicRecord::getNumber);
table.addColumn("Title", MusicRecord::getTitle);
table.addColumn("Artist", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Cost", MusicRecord::getCost);
```

### 4. 设置 `Table` 数据 {#4-set-the-table-data}

最后，为上一步创建的 `Table` 设置 `Repository`：

```java
table.setRepository(Service.getMusicRecords());
```

:::info
另外，`setItems()` 方法可以传入任何有效的 Java 集合，这将在后台为您创建一个 `CollectionRepository`。
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
