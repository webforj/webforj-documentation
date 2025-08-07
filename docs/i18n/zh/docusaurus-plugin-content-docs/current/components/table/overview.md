---
sidebar_position: 1
title: Table
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 2e2825b1825bd2eb6ec7528399936749
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

`Table` 类是一个多功能组件，旨在以结构化和易于理解的方式呈现表格信息。经过优化，能够高效处理大型数据集，提供高级可视化功能和一整套事件以促进动态用户参与。

<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## 创建一个 `Table` {#creating-a-table}

为了在应用程序中创建和填充一个 `Table`，可以采取以下步骤：

### 1. 创建一个实体类 {#1-create-an-entity-class}

定义一个类以表示您想在表中显示的实体（数据）。在这个示例中，该类为 MusicRecord。

```java
public class MusicRecord {
    // 表示每个记录属性的字段和方法
}
```

### 2. 创建一个仓库 {#2-create-a-repository}

一旦创建了实体类，就可以用所需的数据填充这些实体的集合。

从这些数据中，需要创建一个 `Repository` 以在 `Table` 内使用。 `CollectionRepository` 类可将任何有效的 Java 集合转换为可用的 `Repository`，不需要实现自己的 `Repository` 类。

```java
List<MusicRecord> data = new Gson().fromJson(
    Assets.contentOf(
        Assets.resolveContextUrl("context://data/CDStore.json")
    ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

### 3. 实例化 `Table` 并添加列 {#3-instantiate-table-and-add-columns}

实例化一个新的 `Table` 对象，并使用提供的工厂方法之一将所需的列添加到新创建的 `Table` 中：

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("编号", MusicRecord::getNumber);
table.addColumn("标题", MusicRecord::getTitle);
table.addColumn("艺术家", MusicRecord::getArtist);
table.addColumn("类型", MusicRecord::getMusicType);
table.addColumn("费用", MusicRecord::getCost);
```

### 4. 设置 `Table` 数据 {#4-set-the-table-data}

最后，为前一步创建的 `Table` 设置 `Repository` ：

```java
table.setRepository(Service.getMusicRecords());
```

:::info
此外，`setItems()` 方法可以接受任何有效的 Java 集合，这将在内部为您创建一个 `CollectionRepository`。
:::

下面是实现上述步骤以创建基本 `Table` 组件的示例：

<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
