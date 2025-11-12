---
sidebar_position: 35
title: 过滤
slug: filtering
sidebar_class_name: updated-content
_i18n_hash: 008eef50f8ab27ec3f8a455fb5649f41
---
`Table` 组件允许您实现过滤，以便根据特定标准缩小显示的数据。通过使用与表格关联的 `Repository` 提供的 `setFilter(Predicate<T> filter)` 方法，可以定义过滤标准来实现过滤。

以下示例使用来自搜索字段的用户定义标准和 `setBaseFilter()` 方法，基于 `MusicRecord` 的标题对 `CollectionRepository` 应用过滤器。当触发 `commit()` 方法时，表格将刷新以显示过滤后的数据。

<!-- vale off -->
<ComponentDemo
path='/webforj/tablefiltering?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableFilteringView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

:::note
`setBaseFilter()` 方法属于 `CollectionRepository` 类，而不是 `Table` 组件。
:::
