---
sidebar_position: 35
title: Filtering
slug: filtering
sidebar_class_name: updated-content
---

The `Table` component allows you to implement filtering to narrow down displayed data based on specific criteria. Filtering can be achieved by defining a filtering criteria using the `setFilter(Predicate<T> filter)` method provided by the `Repository` associated with the table

The following example uses a user-defined criteria from the search field and the `setBaseFilter()` method to apply a filter to the `CollectionRepository` based on the titles of `MusicRecord`. When the `commit()` method is triggered, the table refreshes with the filtered data. 

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
The `setBaseFilter()` method belongs to the `CollectionRepository` class, not the `Table` component.
:::