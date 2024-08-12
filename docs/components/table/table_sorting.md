---
sidebar_position: 3
title: Sorting
slug: sorting
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';

Sorting enables users to organize data within a column in a specific order, enhancing data readability and analysis. This is useful when users need to quickly find the highest or lowest values in a particular column.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=addondemos.tabledemos.TableSorting' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/tabledemos/TableSorting.java'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/tabledemos/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/tabledemos/Service.java']}
height='600px'
/>

By default, a column will not be set as sortable. To change this, you can use the `setSortable(boolean sortable)` method. 

## Sort Direction

There are three available settings for the direction in which a column can be sorted:

- `SortDirection.ASC`: Sorts the column in ascending order.
- `SortDirection.DESC`: Sorts the column in descending order.
- `SortDirection.NONE`: No sorting applied to the column.

When a column has sorting enabled, you will see a set of vertical arrow indicators appear at the top of the set column. These arrows allow the user to toggle between the different sorting directions.

When ascending order is selected, a `^` will be displayed, whereas descending order will display a `v`.


## Client vs. Server-side Sorting

Sorting of data plays can be broadly categorized into two main approaches: **Client Sorting** and **Server Sorting**.

### Client Sorting

Client sorting involves arranging and displaying data directly within the user interface of the client application. It's the sorting users interact with when they click on column headers, influencing the visual representation of data on the screen.

The developer has no direct control over client-side sorting, but rather is determined by the column [`Type`](#) provided in Java. The following types are currently supported:

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
Client sorting does not work when only a portion of the data is available in the client.
:::

### Server Sorting

In contrast to client-side sorting, server sorting entails arranging and organizing data on the server before transmitting it to the client. This approach is particularly beneficial when dealing with large datasets that might be impractical to transfer entirely to the client.

Developers have more control over the logic of server sorting. This allows for the implementation of complex sorting algorithms and optimizations, making it suitable for scenarios with extensive data. This then ensures that the client receives pre-sorted data, minimizing the need for extensive client-side processing.


:::info
Server sorting is a performance-oriented strategy for dealing with datasets that exceed the capabilities of efficient client-side processing, and is the default method used by the `Table`.
:::

#### Comparators

The `Column` component allows developers to use Java `Comparators` for dynamic and custom sorting. A `Comparator` is a mechanism used to order two objects of the same class, even if that class is user-defined. This functionality provides developers with the flexibility to customize how data is sorted, providing higher control over the default sorting behavior based on natural ordering.

To leverage `Comparator` sorting in a `Column`, you can use the `setComparator()` method. This method allows you to define a custom `Comparator` function that dictates the sorting logic.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=addondemos.tabledemos.TableColumnComparator' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/tabledemos/TableColumnComparator.java'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/tabledemos/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/tabledemos/Service.java']}
height='600px'
/>

In the above example, there is a specifies a custom comparator function which takes two elements (a and b), and defines the sorting order based on the parsed integer values of the `Number` attribute.

Using Comparators for column sorting is particularly useful when handling non-numeric values. They are also useful for implementing complex sorting algorithms.

:::info
By default, the `Table` uses server side sorting, and sorts non-primitive values using the `toString()` method of Object, converting them to their string values and then sorting them.
:::