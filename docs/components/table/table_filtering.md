---
sidebar_position: 15
title: Filtering
slug: filtering
---
import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';

The `Table` component allows you to implement filtering functionality to narrow down displayed data based on specific criteria. Filtering can be achieved by defining a filtering criteria using the `setFilter(Predicate<T> filter)` method provided by the `Repository` associated with the table.

<ComponentDemo
path='https://demo.webforj.com/webapp/controlsamples?class=addondemos.tabledemos.TableFiltering' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/tabledemos/TableFiltering.java'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/tabledemos/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/tabledemos/Service.java']}
height='600px'
/>

In the above example, the `setFilter()` method is used to define a filtering criteria based on the title of `MusicRecord`. 

:::tip
The `setFilter()` method belongs to the `Repository` class, and is not a built-in behavior of the `Table` itself.
:::

The filter is then applied when the user modifies the content of the search field, updating the searchTerm and triggering the `commit()` method to refresh the displayed data.