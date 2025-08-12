---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
_i18n_hash: 15effbe238b9ea86c975499ed2faa30b
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
本节描述了所有列表组件的共通特性，并不是一个可以实例化或直接使用的类。
:::

在您的应用中可使用三种类型的列表：[`ListBox`](listbox)、[`ChoiceBox`](choicebox) 和 [`ComboBox`](combobox)。这些组件都显示一系列键值对项，并提供添加、删除、选择和管理列表内项的方法。

本页面概述了所有列表组件的共享特性和行为，而每个组件的具体细节则在各自的页面中介绍。

## 使用 `ListItem` {#using-listitem}

列表组件由 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 对象组成，这些对象代表列表中的单个项。每个 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 与一个唯一的键和显示文本相关联。<JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 类的重要特性包括：

- 一个 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 封装了一个唯一的键 `Object` 和一个文本 `String` 以在列表组件中显示。 
- 您可以通过提供键和值构造一个 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>，也可以仅指定文本，以使随机生成一个键。

## 通过 API 管理 `ListItem` 对象 {#managing-listitem-objects-with-the-api}

各种列表组件提供了多种方法来管理项目列表并保持列表与客户端之间的一致状态。通过使用这些方法，您可以有效地管理列表中的项目。API 允许您与列表进行交互和操作，以满足您的应用需求。

### 添加项目 {#adding-items}

- **添加一个项目**：

   - 若要将 `ListItem` 添加到列表，您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink> 方法。
   - 您还可以通过使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> 或 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink> 方法按键和值添加新的 `ListItem`。

- **在特定索引插入一个项目**：

   - 要在特定索引插入项目，请使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink> 方法。
   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> 或 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink> 方法插入具有键和文本的项目。

- **插入多个项目：** 

   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink> 方法在指定索引处插入多个项目。

:::tip
为了优化性能，减少每次使用 `add()` 方法时触发服务器到客户端的消息，先创建一个 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 对象的列表更有效。一旦您拥有这个列表，可以使用 `insert(int index, List<ListItem> items)` 方法一次性添加所有项目。这种方法减少了服务器与客户端之间的通信，提高了整体效率。有关此及 webforJ 架构中的其他最佳实践的详细指南，请参阅 [Client/Server Interaction](/docs/architecture/client-server)。
:::

### 移除项目 {#removing-items}

- **移除一个项目：**

   - 要从列表中移除项目，请使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> 或 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink> 方法。

- **移除所有项目：**
   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink> 移除列表中的所有项目。

### 选择项目 {#selecting-items}

所有列表类型都实现了 `SelectableList` 接口。该接口允许多种选择当前 `ListItem` 的方式。

#### 使用给定的 `ListItem` {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> 以参数形式接收一个 `ListItem` 进行选择。

```java {4}
List demoList = new List();
ListItem demoItem = new ListItem("demo","Demo Item");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### 使用给定的 `ListItem` 键 {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> 以参数形式接收 `ListItem` 的键进行选择。

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey("demo");
```

#### 使用给定的 `ListItem` 索引 {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> 以参数形式接收一个索引以选择 `ListItem`。

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey(0);
```

### 其他列表操作 {#other-list-operations}

- **访问和更新项目：**

   - 要通过键或索引访问项目，请使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> 或 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>。
   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink> 方法更新 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 类中项的文本。

- **获取有关列表的信息：**
   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink> 方法获取列表的大小。
   - 要检查列表是否为空，请使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink> 方法。

### 遍历列表 {#iterating-over-lists}

所有列表组件都实现了 Java [`Iteratable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html) 接口，提供了一种高效且直观的方法来遍历列表的内容。通过该接口，您可以轻松循环遍历每个 `ListItem`，简单地访问、修改或对每个项执行操作，几乎不费力。`Iterable` 接口是 Java 语言的标准模式，确保您的代码对任何 Java 开发者都熟悉且易于维护。

以下代码示例展示了两种简单的遍历列表的方法：

```java
list.forEach(item -> {
   item.setText("Modified: " + item.getText());
});

for (ListItem item : list) {
   item.setText("Modified2: " + item.getText());
}
```

## 共享列表属性 {#shared-list-properties}

### 标签 {#label}

所有列表组件都可以分配一个标签，这是与组件相关联的描述性文本或标题。标签提供了简单的解释或提示，以帮助用户理解特定列表的目的或预期选择。除了对可用性的意义外，列表标签在无障碍性方面也发挥着至关重要的作用，使屏幕阅读器和辅助技术能够提供准确的信息，并促进键盘导航。

### 辅助文本 {#helper-text}

每个列表组件都可以使用 `setHelperText()` 方法在列表下方显示辅助文本。该辅助文本提供有关可用选项的附加上下文或解释，确保用户拥有足够的信息以做出明智的选择。

### 水平对齐 {#horizontal-alignment}

所有列表组件都实现了 <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink> 接口，使您可以控制组件内部文本和内容的对齐方式。

使用 `setHorizontalAlignment()` 方法设置对齐：

- `HorizontalAlignment.LEFT` （默认）
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

webforJ 中的所有列表组件还实现了 <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink> 接口，允许您调整组件的整体大小和视觉权重。这对于将组件调整为各种 UI 上下文非常有用，例如表单、对话框、侧边栏等。

使用 `setExpanse()` 方法设置尺寸水平。选项包括：

- `Expanse.NONE`
- `Expanse.XSMALL`
- `Expanse.SMALL`
- `Expanse.MEDIUM` （默认）
- `Expanse.LARGE`
- `Expanse.XLARGE`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setExpanse(Expanse.LARGE);
```

您可以使用以下方式检索当前设置：
```java
Expanse current = listBox.getExpanse();
```

## 主题 {#topics}

<DocCardList className="topics-section" />
