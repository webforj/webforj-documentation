---
sidebar_position: 0
title: ListSelectEvent
sidebar_class_name: sidebar--item__hidden
slug: ListSelectEvent
---

import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="org/dwcj/component/event/ListSelectEvent" top='true' />

This event is triggered when the user selects an item from a List-based component. It provides essential information about the selected item and allows developers to implement custom actions or responses when an item is chosen.

## Event Payload

| Method | Description |
|:-:|-|
|`getSelectedIndex()`|	Returns the selected index, or -1 if no item is selected.|
|`getSelectedItem()`|	Returns the selected item, or null if no item is selected.|
|`getSelectedIndices()`|	Returns a list of selected indices, or an empty list if no item is selected. If the list doesn't support multiple selection, the list will contain only one item.|
|`getSelectedItems()`|	Returns a list of selected items. If the list doesn't support multiple selection, the list will contain only one item.|

## See Also

### Events
- [Event](./event)
- [ListOpenEvent](./ListOpenEvent)
- [ListCloseEvent](./ListCloseEvent)

### Components
- [ChoiceBox](../list-components/ChoiceBox)
- [ComboBox](../list-components/ComboBox)
- [ListBox](../list-components/ListBox)