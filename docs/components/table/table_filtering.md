---
sidebar_position: 15
title: Filtering
slug: filtering
---

The `Table` component allows you to implement filtering functionality to narrow down displayed data based on specific criteria. Filtering can be achieved by defining a filtering criteria using the `setFilter(Predicate<T> filter)` method provided by the `Repository` associated with the table.

<ComponentDemo
path='https://demo.webforj.com/webapp/controlsamples/tablefiltering?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableFilteringView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

In the above example, the `setFilter()` method is used to define a filtering criteria based on the title of `MusicRecord`. 

:::tip
The `setFilter()` method belongs to the `Repository` class, and is not a built-in behavior of the `Table` itself.
:::

The filter is then applied when the user modifies the content of the search field, updating the searchTerm and triggering the `commit()` method to refresh the displayed data.