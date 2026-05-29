---
sidebar_position: 1
title: Table
hide_giscus_comments: true
_i18n_hash: 3b4ddfbfc0fb9c5d67fa60136a23af73
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

`Table` 类是一个多功能组件，旨在以结构化且易于理解的方式呈现表格信息。它经过优化，能够高效处理大数据集，并提供先进的可视化和全面的事件套件，以实现动态用户交互。

<!-- INTRO_END -->

## 创建一个 `Table` {#creating-a-table}

<!-- vale off -->
<ComponentDemo
path='/webforj/datatable'
files={['src/main/java/com/webforj/samples/views/table/DataTableView.java']}
height='600px'
/>
<!-- vale on -->

为了在应用程序中创建和填充 `Table`，可以采取以下步骤：

### 1. 创建实体类 {#1-create-an-entity-class}

定义一个类来表示您希望在表格中显示的实体（数据）。在示例中，这个类是 MusicRecord。

```java
public class MusicRecord {
  // 字段和方法来表示每个记录的属性
}
```

### 2. 创建一个仓库 {#2-create-a-repository}

创建实体类后，使用该类填充这些实体的集合。

从这些数据中，需要为 `Table` 创建一个 `Repository`。提供了 `CollectionRepository` 类，可以将任何有效的 Java 集合转换为可用的 `Repository`，无需自己实现 `Repository` 类。

```java
List<MusicRecord> data = new Gson().fromJson(
  Assets.contentOf(
    Assets.resolveContextUrl("context://data/CDStore.json")
  ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

:::tip 更多信息
有关 webforJ 中 `Repository` 模式的更多信息，请参见 [Repository 文章](/docs/advanced/repository/overview)。
:::

### 3. 实例化 `Table` 并添加列 {#3-instantiate-table-and-add-columns}

实例化一个新的 `Table` 对象，并使用提供的工厂方法之一将所需的列添加到新创建的 `Table` 中：

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("编号", MusicRecord::getNumber);
table.addColumn("标题", MusicRecord::getTitle);
table.addColumn("艺术家", MusicRecord::getArtist);
table.addColumn("流派", MusicRecord::getMusicType);
table.addColumn("费用", MusicRecord::getCost);
```

### 4. 设置 `Table` 数据 {#4-set-the-table-data}

最后，为上一步中创建的 `Table` 设置 `Repository`：

```java
table.setRepository(Service.getMusicRecords());
```

:::info
或者，`setItems()` 方法可以接受任何有效的 Java 集合，这将为您在后台创建一个 `CollectionRepository`。
:::

以下是实现上述步骤以创建基本 `Table` 组件的示例：

<ComponentDemo
path='/webforj/tablebasic'
files={[
  'src/main/java/com/webforj/samples/views/table/TableBasicView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## 样式

<TableBuilder name="Table" />
