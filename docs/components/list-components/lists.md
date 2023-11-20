---
sidebar_position: 0
title: Lists
---

import componentData from '@site/static/field_data.js'
import ComponentViewer from '@site/src/components/PageTools/ComponentViewer'

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/list/DwcList"/>

There are three types of lists for use within your applications: [`ListBox`](docs/components/list-components/listbox), [`ChoiceBox`](docs/components/list-components/choicebox) and [`ComboBox`](docs/components/list-components/combobox). These three components all display a list of key value items, and support methods to add, remove, select and manage the items within the list.

This section will list the commonalities within each of the aforementioned components, with the specific behaviors belonging to each outlined in their own sections.

:::info
This section describes common functionality amongst all of the list components, and is not itself a class that can be instantiated and used.
:::

<!-- Add a managing items section -->

<!-- Add vs insert, adding multiple items, iterate over list items, removal, etc -->

## Using `ListItem`

The `ListItem` class represents individual items within a list. Each `ListItem` is associated with a key and a display text. Here are the key points regarding the `ListItem` class:

- A `ListItem` encapsulates a unique key `Object`, and a text `String` for display within the component. List components are composed of `ListItem` objects.
- You can construct a `ListItem` by providing a key and text, or simply by specifying the text, where a random key is generated.

## Managing `ListItem` Objects with the API

The various List classes offer various methods for managing the list of items and maintaining a consistent state between the list and the client.

By using these methods provided in the classes, you can effectively manage the items within the list component. The API allows you to interact with and manipulate the list to meet your application's requirements.

1. **Adding an Item**:

   > - To add an item to the list, you can use the `add(ListItem item)` method.
   > - You can also add an item by specifying the key and text using the `add(Object key, String text)` or `add(String text)` method.

   :::tip
   Using the add method triggers a server-to-client message each time it is used. For more efficient usage, create a list of `ListItem` objects, and put them in the list using the `insert(int index, List<ListItem> items)` method outlined below.
   :::

2. **Inserting an Item at a Specific Index:**

   > - To insert an item at a specific index, use the `insert(int index, ListItem item)` method.
   > - You can insert an item with key and text using the `insert(int index, Object key, String text)` or `insert(int index, String text)` method.

3. **Inserting Multiple Items:** You can insert multiple items at a specified index using the `insert(int index, List<ListItem> items)` method.

4. **Removing an Item:**

   > - To remove an item from the list, use the `remove(int index)` or `remove(Object key)` method.
   > - You can also remove all items from the list using `removeAll()`.

5. **Accessing and Updating Items:**

   > - To access items by key or index, use `getByKey(Object key)` or `getByIndex(int index)`.
   > - You can update the text of an item using the `setText(String text)` method within the `ListItem` class.

6. **Selecting an Item:** To select an item within the list, you can use methods such as `select(ListItem item)` or `selectKey(Object key)`.

7. **Retrieving Information about the List:**
   > - You can get the size of the list using the `size()` method.
   > - To check if the list is empty, use the `isEmpty()` method.

## Shared List Properties

### Label

The various List components can be assigned a label, which is a descriptive text or title that is associated with the component. It provides a brief explanation or prompt to help users understand the purpose or expected selection for that particular list. List labels are not only important for usability but also play a crucial role in accessibility, as they enable screen readers and assistive technologies to provide accurate information and facilitate keyboard navigation.

<!-- ADD DEMO WITH ALL THREE AND A LABEL -->

## Shared Events

:::caution Notice
All List components share the following methods to add and remove event listeners for the following events
:::

|                                 Events                                  | Description                                                                                                   |
| :---------------------------------------------------------------------: | ------------------------------------------------------------------------------------------------------------- |
| <JavadocLink type="engine" location="org/dwcj/component/event/BlurEvent"  code="true">BlurEvent</JavadocLink> | An event that is triggered when a component loses focus.                                                      |
|                  <JavadocLink type="engine" location="org/dwcj/component/event/FocusEvent"  code="true">FocusEvent</JavadocLink>                   | An event that is triggered when a component gains focus, opposite of a blur event.                            |
|             <JavadocLink type="engine" location="org/dwcj/component/event/MouseEnterEvent"  code="true">MouseEnterEvent</JavadocLink>              | An event that is triggered when the mouse cursor enters the boundaries of a component.                        |
|              <JavadocLink type="engine" location="org/dwcj/component/event/MouseExitEvent"  code="true">MouseExitEvent</JavadocLink>               | An event that is triggered when the mouse cursor exits the boundaries of a component.                         |
|         <JavadocLink type="engine" location="org/dwcj/component/event/RightMouseDownEvent"  code="true">RightMouseDownEvent</JavadocLink>          | An event that is triggered when the user presses the right mouse button while the cursor is over a component. |
|             <JavadocLink type="engine" location="org/dwcj/component/list/event/ListSelectEvent"  code="true">ListSelectEvent</JavadocLink>              | An event which is fired when the user selects an item from a list.
 |
