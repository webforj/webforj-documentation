---
sidebar_position: 1
title: Table
hide_giscus_comments: true
_i18n_hash: 0d467fd377fff1994c025ba8a95c957f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

`Table` 类是一个多功能组件，旨在以结构化和易于理解的方式呈现表格信息。经过优化以处理大型数据集并实现高性能，该组件提供了先进的可视化和全面的事件套件，以实现动态用户互动。

<!-- INTRO_END -->

## 创建一个 `Table` {#creating-a-table}

<!-- vale off -->
<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
height='600px'
/>
<!-- vale on -->

为了在应用程序中创建和填充一个 `Table`，可以采取以下步骤：

### 1. 创建实体类 {#1-create-an-entity-class}

定义一个类以表示您希望在表格中显示的实体（数据）。在这个例子中，类是 MusicRecord。

```java
public class MusicRecord {
    // 表示每条记录属性的字段和方法
}
```

### 2. 创建一个仓库 {#2-create-a-repository}

创建实体类后，使用它来填充包含所需数据的这些实体集合。

从这些数据中，需要创建一个 `Repository` 以在 `Table` 中使用。提供 `CollectionRepository` 类，以将任何有效的 Java 集合转换为可用的 `Repository`，省去自己实现 `Repository` 类的需要。

```java
List<MusicRecord> data = new Gson().fromJson(
    Assets.contentOf(
        Assets.resolveContextUrl("context://data/CDStore.json")
    ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

:::tip 更多信息
有关webforJ中 `Repository` 模式的更多信息，请参见 [Repository 文章](/docs/advanced/repository/overview)。
:::

### 3. 实例化 `Table` 并添加列 {#3-instantiate-table-and-add-columns}

实例化一个新的 `Table` 对象，并使用提供的工厂方法之一向新创建的 `Table` 添加所需的列：

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Number", MusicRecord::getNumber);
table.addColumn("Title", MusicRecord::getTitle);
table.addColumn("Artist", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Cost", MusicRecord::getCost);
```

### 4. 设置 `Table` 数据 {#4-set-the-table-data}

最后，为在前一步中创建的 `Table` 设置 `Repository`：

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
