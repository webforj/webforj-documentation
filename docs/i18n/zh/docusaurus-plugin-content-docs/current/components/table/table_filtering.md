---
sidebar_position: 35
title: Filtering
slug: filtering
_i18n_hash: e35c9b340f9faa796a4dbf5635f59495
---
`Table` 组件允许您实现过滤，以根据特定标准缩小显示的数据。过滤可以通过使用与表关联的 `Repository` 提供的 `setFilter(Predicate<T> filter)` 方法来定义过滤标准。

以下示例使用搜索字段中的用户定义标准和 `setBaseFilter()` 方法，根据 `MusicRecord` 的标题将过滤应用于 `CollectionRepository`。当触发 `commit()` 方法时，表格将使用过滤后的数据刷新。

<!-- vale off -->
<ComponentDemo
path='/webforj/tablefiltering'
files={[
  'src/main/java/com/webforj/samples/views/table/TableFilteringView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

:::note
`setBaseFilter()` 方法属于 `CollectionRepository` 类，而不是 `Table` 组件。
:::
