---
sidebar_position: 1
title: Table
hide_giscus_comments: true
description: 使用 Table 组件展示表格数据，将实体类绑定到 Repository 以填充列并渲染行。
_i18n_title_hash: 51c45b795d5d18a3e4e0c37e8b20a141
_i18n_description_hash: cad5d5953a2d2c3f85ad59b558632b0b
_i18n_hash: 680ee8ce00bf4efc4ae933a913fe1c1a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

`Table`类是一个多功能组件，旨在以结构化和易于理解的方式呈现表格信息。该组件经过优化，能够高效处理大型数据集，提供高级可视化以及一整套事件以促进动态用户交互。

<!-- INTRO_END -->

## 创建一个 `Table` {#creating-a-table}

<!-- vale off -->
<ComponentDemo
path='/webforj/datatable'
files={['src/main/java/com/webforj/samples/views/table/DataTableView.java']}
height='600px'
/>
<!-- vale on -->

为了在应用程序中创建并填充一个 `Table`，可以按照以下步骤进行：

### 1. 创建实体类 {#1-create-an-entity-class}

定义一个类来表示您想在表格中展示的实体（数据）。在这个例子中，这个类是 MusicRecord。

```java
public class MusicRecord {
  // 字段和方法，用于表示每个记录的属性
}
```

### 2. 创建一个仓库 {#2-create-a-repository}

创建实体类后，使用该类填充所需数据的实体集合。

从这些数据中，需要为 `Table` 创建一个 `Repository`。提供的 `CollectionRepository` 类可以将任何有效的 Java 集合转换为可用的 `Repository`，您无需自己实现 `Repository` 类。

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

实例化一个新的 `Table` 对象，并使用提供的工厂方法之一向新创建的 `Table` 添加所需的列：

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("编号", MusicRecord::getNumber);
table.addColumn("标题", MusicRecord::getTitle);
table.addColumn("艺术家", MusicRecord::getArtist);
table.addColumn("流派", MusicRecord::getMusicType);
table.addColumn("成本", MusicRecord::getCost);
```

### 4. 设置 `Table` 数据 {#4-set-the-table-data}

最后，为在前一步中创建的 `Table` 设置 `Repository`：

```java
table.setRepository(Service.getMusicRecords());
```

:::info
另外，`setItems()` 方法可以传入任何有效的 Java 集合，这将为您在后台创建一个 `CollectionRepository`。
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

## 样式 {#styling}

<TableBuilder name="Table" />
