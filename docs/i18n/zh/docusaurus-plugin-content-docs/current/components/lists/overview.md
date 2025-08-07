---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
_i18n_hash: cf4917e0ffb74122b24c210ec7502cbf
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
本节描述了所有列表组件的共同特性，并不是可以实例化或直接使用的类。
:::

在您的应用程序中提供了三种可用的列表类型：[ `ListBox`](listbox)、[`ChoiceBox`](choicebox) 和 [`ComboBox`](combobox)。这些组件都显示键值项目的列表，并提供方法来添加、删除、选择和管理列表中的项目。

此页面概述了所有列表组件的共享特性和行为，而具体细节在各自的页面中涵盖。

## 使用 `ListItem` {#using-listitem}

列表组件由 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 对象组成，表示列表中的单个项目。每个 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 都与唯一的键和显示文本相关联。 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 类的重要特性包括：

- <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 封装了一个唯一的键 `Object` 和要在列表组件中显示的文本 `String`。
- 您可以通过提供键和文本，或仅指定文本以生成随机键来构建 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>。

## 使用 API 管理 `ListItem` 对象 {#managing-listitem-objects-with-the-api}

各种列表组件提供了几种管理项目列表和在列表与客户端之间保持一致状态的方法。通过使用这些方法，您可以有效地管理列表中的项目。API 允许您与列表进行交互和操作，以满足您的应用程序要求。

### 添加项目 {#adding-items}

- **添加项目**：

   - 要将 `ListItem` 添加到列表中，您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink> 方法。
   - 您还可以通过使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> 或 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink> 方法通过指定键和文本来添加新的 `ListItem`。

- **在特定索引处插入项目**：

   - 要在特定索引处插入项目，请使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink> 方法。
   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> 或 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink> 方法插入具有键和文本的项目。

- **插入多个项目**：

   - 您可以通过 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink> 方法在指定索引处插入多个项目。

:::tip
为了优化性能，您可以先创建一个 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 对象的列表，而不是每次使用 `add()` 方法时都触发一次服务器到客户端的消息。一旦您有了这个列表，就可以使用 `insert(int index, List<ListItem> items)` 方法一次性添加它们。这种方法减少了服务器与客户端的通信，提高了整体效率。有关 webforJ 架构中此方法和其他最佳实践的详细指南，请参阅 [客户端/服务器交互](/docs/architecture/client-server)。
:::

### 移除项目 {#removing-items}

- **移除项目**：

   - 要从列表中移除项目，请使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> 或 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink> 方法。

- **移除所有项目**：
   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink> 移除列表中的所有项目。

### 选择项目 {#selecting-items}

所有列表类型实现了 `SelectableList` 接口。该接口允许多种选择当前 `ListItem` 的方法。

#### 按给定的 `ListItem` 选择 {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> 接受一个 `ListItem` 作为参数进行选择。

```java {4}
List demoList = new List();
ListItem demoItem = new ListItem("demo","演示项目");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### 按给定的 `ListItem` 的键选择 {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> 接受一个键作为参数进行选择。

```java {3}
List demoList = new List();
demoList.add("demo","演示项目");
demoList.selectKey("demo");
```

#### 按给定的 `ListItem` 的索引选择 {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> 接受一个索引作为参数进行选择。

```java {3}
List demoList = new List();
demoList.add("demo","演示项目");
demoList.selectKey(0);
```

### 其他列表操作 {#other-list-operations}

- **访问和更新项目**：

   - 要按键或索引访问项目，请使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> 或 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>。
   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink> 方法更新项目的文本。

- **检索有关列表的信息**：
   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink> 方法获取列表的大小。
   - 要检查列表是否为空，请使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink> 方法。

### 迭代列表 {#iterating-over-lists}

所有列表组件实现了 Java [`Iteratable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html) 接口，提供了一种高效且直观的方法来迭代列表的内容。通过此接口，您可以轻松循环访问每个 `ListItem`，以最小的努力访问、修改或对每个项目执行操作。 `Iterable` 接口是 Java 语言的标准模式，确保您的代码对任何 Java 开发人员都是熟悉且易于维护的。

以下代码片段演示了两种简单的迭代列表的方法：

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

所有列表组件都可以分配标签，标签是与组件相关的描述文本或标题。标签提供了简要的解释或提示，以帮助用户理解该特定列表的目的或期望选择。除了对可用性的重大意义外，列表标签在无障碍方面也发挥着至关重要的作用，使得屏幕阅读器和辅助技术能够提供准确的信息并方便键盘导航。

### 辅助文本 {#helper-text}

每个列表组件都可以使用 `setHelperText()` 方法在列表下方显示辅助文本。此辅助文本提供关于可用选项的附加上下文或解释，确保用户拥有做出明智选择所需的信息。

### 水平对齐 {#horizontal-alignment}

所有列表组件实现了 <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink> 接口，使您能够控制文本和内容在组件内部的对齐方式。

使用 `setHorizontalAlignment()` 方法设置对齐方式：

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

### 组件大小 {#expanses}

webforJ 中的所有列表组件还实现了 <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink> 接口，允许您调整组件的整体大小和视觉权重。这对于将组件适配到各种 UI 上下文（例如表单、对话框、侧边栏等）非常有用。

使用 `setExpanse()` 方法设置大小级别。选项包括：

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

您可以使用以下代码检索当前设置：
```java
Expanse current = listBox.getExpanse();
```

## 主题 {#topics}

<DocCardList className="topics-section" />
