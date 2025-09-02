---
sidebar_position: 35
title: Filtering
slug: filtering
sidebar_class_name: updated-content
---

Two types of repositories can help narrow down the data displayed in the `Table`. A `CollectionRepository` can help with simple list retrievals, while a [`QueryableRepository`](/docs/advanced/repository/querying-data) is for more advanced filtering types, sorting, and pagination.

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

:::tip
If you need more advanced filtering types, sorting, or pagination, consider using a [`QueryableRepository`](/docs/advanced/repository/querying-data).
:::