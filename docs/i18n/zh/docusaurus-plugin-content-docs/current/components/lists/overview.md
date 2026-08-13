---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
description: >-
  Manage shared list features across ChoiceBox, ComboBox, and ListBox, including
  ListItem objects, adding, removing, and selection APIs.
_i18n_hash: 1fa51afef9c5af46944d4d74a6b5ec61
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
本节描述所有列表组件的常见功能，并不是可以实例化或直接使用的类。
:::

在您的应用程序中，可以使用三种类型的列表：[`ListBox`](listbox)、[`ChoiceBox`](choicebox)和[`ComboBox`](combobox)。这些组件都显示一个键值项的列表，并提供添加、删除、选择和管理列表中项的方法。

本页面概述了所有列表组件的共享特性和行为，而每个组件的具体细节则在各自的页面中进行介绍。

## 使用 `ListItem` {#using-listitem}

列表组件由<JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>对象组成，这些对象代表列表中的单个项。每个<JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>与唯一的键和显示文本相关联。<JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>类的重要特性包括：

- <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>封装了一个唯一的键`Object`和在列表组件中显示的文本`String`。
- 您可以通过提供键和文本来构造一个<JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>，或者仅指定文本以生成随机键。

## 使用API管理 `ListItem` 对象 {#managing-listitem-objects-with-the-api}

各种列表组件提供多种方法来管理项列表并保持列表与客户端之间的一致状态。通过使用这些方法，您可以有效管理列表中的项。API使您能够与列表进行交互和操作，以满足应用程序的需求。

### 添加项 {#adding-items}

- **添加项**：

   - 要将`ListItem`添加到列表，您可以使用<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink>方法。
   - 您还可以通过指定键和文本使用<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink>或<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink>方法添加新的`ListItem`。

- **在特定索引处插入项：**

   - 要在特定索引处插入项，请使用<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink>方法。
   - 您可以使用<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink>或<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink>方法插入具有键和文本的项。

- **插入多个项：**

   - 您可以使用<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink>方法在指定索引处插入多个项。

:::tip
为了优化性能，与其在每次使用`add()`方法时触发服务器与客户端之间的消息，更高效的做法是先创建一个<JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>对象的列表。一旦您拥有这个列表，您可以使用`insert(int index, List<ListItem> items)`方法一次性添加所有项。这种方法减少了服务器与客户端的通信，提高了整体效率。有关此方面及webforJ架构中的其他最佳实践的详细指南，请参阅[客户端/服务器交互](/docs/architecture/client-server)。
:::

### 删除项 {#removing-items}

- **删除项：**

   - 要从列表中删除项，请使用<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink>或<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink>方法。

- **删除所有项：**
   - 您可以使用<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink>方法删除列表中的所有项。

### 选择项 {#selecting-items}

所有列表类型实现了`SelectableList`接口。该接口允许以多种不同方式选择当前的`ListItem`。

#### 使用给定的 `ListItem` {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink>接受一个`ListItem`作为参数进行选择。

```java {4}
List demoList = new List();
ListItem demoItem = new ListItem("demo","演示项");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### 使用给定的 `ListItem` 键 {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink>接受一个键作为参数以选择`ListItem`。

```java {3}
List demoList = new List();
demoList.add("demo","演示项");
demoList.selectKey("demo");
```

#### 使用给定的 `ListItem` 索引 {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink>接受一个索引作为参数以选择`ListItem`。

```java {3}
List demoList = new List();
demoList.add("demo","演示项");
demoList.selectKey(0);
```

### 其他列表操作 {#other-list-operations}

- **访问和更新项：**

   - 要通过键或索引访问项目，请使用<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink>或<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>。
   - 您可以使用<JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink>方法更新项的文本，该方法在<JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>类中。

- **检索有关列表的信息：**
   - 您可以使用<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink>方法获取列表的大小。
   - 要检查列表是否为空，请使用<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink>方法。

### 遍历列表 {#iterating-over-lists}

所有列表组件实现了Java [`Iteratable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html)接口，提供了一种高效且直观的方式来遍历列表的内容。通过这个接口，您可以轻松循环访问每个`ListItem`，使得以最小的努力访问、修改或对每个项执行操作变得简单。`Iterable`接口是Java语言的标准模式，确保您的代码对任何Java开发人员都是熟悉且易于维护的。

下面的代码片段展示了两种简单的方法来遍历一个列表：

```java
list.forEach(item -> {
   item.setText("修改: " + item.getText());
});

for (ListItem item : list) {
   item.setText("修改2: " + item.getText());
}
```

## 共享列表属性 {#shared-list-properties}

### 标签 {#label}

所有列表组件都可以分配一个标签，即与组件关联的描述性文本或标题。标签提供简要说明或提示，帮助用户理解特定列表的目的或预期选择。除了对可用性的重要性外，列表标签也在可访问性方面起着关键作用，使屏幕阅读器和辅助技术能够提供准确的信息并促进键盘导航。

### 辅助文本 {#helper-text}

每个列表组件可以使用`setHelperText()`方法在列表下方显示辅助文本。这些辅助文本提供额外的上下文或关于可用选项的解释，确保用户具备必要的信息以做出明智的选择。

### 水平对齐 {#horizontal-alignment}

所有列表组件实现了<JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink>接口，允许您控制组件内部文本和内容的对齐方式。

使用`setHorizontalAlignment()`方法设置对齐方式：

- `HorizontalAlignment.LEFT`（默认）
- `HorizontalAlignment.MIDDLE`
- `HorizontalAlignment.RIGHT`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setHorizontalAlignment(HorizontalAlignment.LEFT);
```

要获取当前对齐方式：
```java
HorizontalAlignment alignment = listBox.getHorizontalAlignment();
```

### 尺寸 {#expanses}

webforJ中的所有列表组件也实现了<JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink>接口，允许您调整组件的整体尺寸和视觉权重。这对于在不同的UI上下文中调整组件（例如表单、对话框、侧边栏等）非常有用。

使用`setExpanse()`方法设置尺寸级别。选项包括：

- `Expanse.NONE`
- `Expanse.XSMALL`
- `Expanse.SMALL`
- `Expanse.MEDIUM`（默认）
- `Expanse.LARGE`
- `Expanse.XLARGE`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setExpanse(Expanse.LARGE);
```

可以使用以下方式检索当前设置：
```java
Expanse current = listBox.getExpanse();
```

## 主题 {#topics}

<DocCardList className="topics-section" />
