---
sidebar_position: 0
title: Lists
---

import componentData from '@site/static/field_data.js'
import ComponentViewer from '@site/src/components/PageTools/ComponentViewer'

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

There are three types of lists for use within your applications: [`ListBox`](listbox), [`ChoiceBox`](choicebox) and [`ComboBox`](combobox). These three components all display a list of key value items, and support methods to add, remove, select and manage the items within the list.

This section will list the commonalities within each of the aforementioned components, with the specific behaviors belonging to each outlined in their own sections.

:::info
This section describes common functionality amongst all of the list components, and is not itself a class that can be instantiated and used.
:::

<!-- Add a managing items section -->

<!-- Add vs insert, adding multiple items, iterate over list items, removal, etc -->

## Using `ListItem`

The <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> class represents individual items within a list. Each <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> is associated with a key and a display text. Here are the key points regarding the <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> class:

- A <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> encapsulates a unique key `Object`, and a text `String` for display within the component. List components are composed of <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> objects.
- You can construct a <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> by providing a key and text, or simply by specifying the text, where a random key is generated.

## Managing `ListItem` Objects with the API

The various List classes offer various methods for managing the list of items and maintaining a consistent state between the list and the client.

By using these methods provided in the classes, you can effectively manage the items within the list component. The API allows you to interact with and manipulate the list to meet your application's requirements.

1. **Adding an Item**:

   > - To add an item to the list, you can use the <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(org.dwcj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink> method.
   > - You can also add an item by specifying the key and text using the <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> or <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink> method.


2. **Inserting an Item at a Specific Index:**

   > - To insert an item at a specific index, use the <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,org.dwcj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink> method.
   > - You can insert an item with key and text using the <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> or <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink> method.

3. **Inserting Multiple Items:** You can insert multiple items at a specified index using the <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='l#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink> method.

:::tip
To optimize performance in applications, instead of triggering a server-to-client message each time you use the add method, it's more efficient to compile a list of <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> objects first. Once you have this list, you can add them all at once using the `insert(int index, List<ListItem> items)` method. This approach reduces server-client communication, enhancing overall efficiency. For detailed guidelines on this and other best practices in webforJ [architecture](/architecture/architecture.md), consult the documentation.
:::

4. **Removing an Item:**

   > - To remove an item from the list, use the <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> or <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink> method.
   > - You can also remove all items from the list using <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink>.

5. **Accessing and Updating Items:**

   > - To access items by key or index, use <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> or <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>.
   > - You can update the text of an item using the <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink> method within the <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> class.

6. **Selecting an Item:** To select an item within the list, you can use methods such as <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(org.dwcj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> or <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink>.

7. **Retrieving Information about the List:**
   > - You can get the size of the list using the <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink> method.
   > - To check if the list is empty, use the <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink> method.

## Shared List Properties

### Label

The various List components can be assigned a label, which is a descriptive text or title that is associated with the component. It provides a brief explanation or prompt to help users understand the purpose or expected selection for that particular list. List labels are not only important for usability but also play a crucial role in accessibility, as they enable screen readers and assistive technologies to provide accurate information and facilitate keyboard navigation.

<!-- ADD DEMO WITH ALL THREE AND A LABEL -->

