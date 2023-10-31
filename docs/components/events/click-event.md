---
sidebar_position: 0
title: ListClickEvent
sidebar_class_name: sidebar--item__hidden
slug: ListClickEvent
---

import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/event/ListClickEvent" top='true' />

This event is triggered when the user clicks an item from a List-based component. It provides essential information about the selected item and allows developers to implement custom actions or responses when an item is chosen.

## Event Payload

| Method | Description |
|:-:|-|
|`getSelectedIndex()`|	Returns the selected index, or -1 if no item is selected.|
|`getSelectedItem()`|	Returns the selected item, or null if no item is selected.|
|`getSelectedIndices()`|	Returns a list of selected indices, or an empty list if no item is selected. If the list doesn't support multiple selection, the list will contain only one item.|
|`getSelectedItems()`|	Returns a list of selected items. If the list doesn't support multiple selection, the list will contain only one item.|

## See Also

- [Event](./event)