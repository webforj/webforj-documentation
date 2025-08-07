---
sidebar_position: 35
title: Filtering
slug: filtering
_i18n_hash: 110061605b615701c1832988833fe959
---
`Table` 组件允许您实现过滤功能，以根据特定标准缩小显示的数据。要实现过滤，可以通过使用与表格关联的 `Repository` 提供的 `setFilter(Predicate<T> filter)` 方法来定义过滤标准。

<ComponentDemo
path='/webforj/tablefiltering?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableFilteringView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

在上述示例中，`setFilter()` 方法用于根据 `MusicRecord` 的标题定义过滤标准。

:::tip
`setFilter()` 方法属于 `Repository` 类，并不是 `Table` 本身的内置行为。
:::

当用户修改搜索字段的内容时，过滤器将被应用，从而更新 searchTerm，并触发 `commit()` 方法以刷新显示的数据。
