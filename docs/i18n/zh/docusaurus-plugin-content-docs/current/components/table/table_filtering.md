---
sidebar_position: 35
title: Filtering
slug: filtering
description: >-
  Narrow Table rows by applying a Predicate through the bound Repository,
  refreshing results on commit.
_i18n_hash: 4e0709a55b763f553eeb8ddb8a3abb32
---
`Table` 组件允许您实现过滤，以根据特定标准缩小显示的数据。可以通过使用与表格关联的 `Repository` 提供的 `setFilter(Predicate<T> filter)` 方法来定义过滤标准来实现过滤。

以下示例使用来自搜索字段的用户定义标准和 `setBaseFilter()` 方法，将过滤器应用于基于 `MusicRecord` 标题的 `CollectionRepository`。当触发 `commit()` 方法时，表格将以过滤后的数据刷新。

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
