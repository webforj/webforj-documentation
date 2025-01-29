---
sidebar_position: 0
title: Lists
---

import componentData from '@site/static/field_data.js'
import ComponentViewer from '@site/src/components/PageTools/ComponentViewer'

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
This section describes common features of all list components, and isn't a class that can be instantiated or used directly.
:::

There are three types of lists available for use within your applications: [`ListBox`](./lists/listbox), [`ChoiceBox`](./lists/choicebox), and [`ComboBox`](./lists/combobox). These components all display a list of key-value items, and provide methods to add, remove, select, and manage the items within the list.

This page outlines the shared features and behavior of all list components, while specific details for each are covered in their respective pages.

## Using `ListItem`

List components are composed of <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> objects, which represent individual items within a list. Each <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> is associated with a unique key and display text. Important features of the <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> class include:

- A <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> encapsulates a unique key `Object` and a text `String` to display within the list component. 
- You can construct a <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> by providing a key and text, or by specifying only the text so that a random key is generated.

## Managing `ListItem` objects with the API

The various List components offer several methods for managing the list of items and maintaining a consistent state between the list and the client. By using these methods, you can effectively manage the items within the list. The API allows you to interact with and manipulate the list to meet your app's requirements.

### Adding items

- **Adding an item**:

   - To add a `ListItem` to the list, you can use the <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink> method.
   - You can also add a new `ListItem` by specifying the key and text using the <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> or <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink> method.


- **Inserting an item at a specific index:**

   - To insert an item at a specific index, use the <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink> method.
   - You can insert an item with key and text using the <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> or <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink> method.

- **Inserting multiple items:** 

   - You can insert multiple items at a specified index using the <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink> method.

:::tip
To optimize performance, instead of triggering a server-to-client message each time you use the `add()` method, it's more efficient to create a List of <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> objects first. Once you have this list, you can add them all at once using the `insert(int index, List<ListItem> items)` method. This approach reduces server-client communication, enhancing overall efficiency. For detailed guidelines on this and other best practices in webforJ architecture, refer to [Client/Server Interaction](/architecture/architecture.md).
:::

### Removing items

- **Removing an item:**

   - To remove an item from the list, use the <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> or <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink> method.

- **Removing all items:**
   - You can remove all items from the list using <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink>.

### Selecting items

All of the list types implement the `SelectableList` interface. This interface allows multiple different ways of selecting the current `ListItem`.

#### With a given `ListItem`

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> takes a `ListItem` as parameter to select.

```java {4}
List demoList = new List();
ListItem demoItem = new ListItem("demo","Demo Item");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### With a given key of a `ListItem`

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> takes a key to a `ListItem` as parameter to select.

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey("demo");
```

#### With a given index of a `ListItem`

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> takes an index to a `ListItem`as a parameter to select.

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey(0);
```

### Other list operations

- **Accessing and updating items:**

   - To access items by key or index, use <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> or <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>.
   - You can update the text of an item using the <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink> method within the <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> class.

- **Retrieving information about the list:**
   - You can get the size of the list using the <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink> method.
   - To check if the list is empty, use the <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink> method.

### Iterating over lists

All List components implement the Java [`Iteratable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html) interface, providing an efficient and intuitive way to iterate through a list's contents. With this interface, you can easily loop through every `ListItem`, making it simple to access, modify, or perform actions on each item with minimal effort. The `Iterable` interface is a standard pattern of the Java language, ensuring your code is familiar and maintainable for any Java developer.

The code snippet below demonstrates two easy ways to iterate through a list:

```java
list.forEach(item -> {
   item.setText("Modified: " + item.getText());
});

for (ListItem item : list) {
   item.setText("Modified2: " + item.getText());
}
```

## Shared list properties

### Label

All List components can be assigned a label, which is a descriptive text or title associated with the component. Labels provide a brief explanation or prompt to help users understand the purpose or expected selection for that particular list. In addition to their importance for usability, list labels also play a crucial role in accessibility, enabling screen readers and assistive technologies to provide accurate information and facilitate keyboard navigation.

### Helper text

Each List component can display helper text beneath the list using the `setHelperText()` method. This helper text offers additional context or explanations about the available options, ensuring users have the necessary information to make informed selections.
<!-- ADD DEMO WITH ALL THREE AND A LABEL -->

## Topics

<DocCardList className="topics-section" />
