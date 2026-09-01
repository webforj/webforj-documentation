---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
sidebar_class_name: new-content
description: >-
  Manage shared list features across ChoiceBox, ComboBox, and ListBox, including
  ListItem objects, adding, removing, and selection APIs.
_i18n_hash: f75147986adfbf756ebf603caa663134
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
本节描述所有列表组件的公共特性，并不是可以实例化或直接使用的类。
:::

在您的应用中可以使用三种类型的列表：[`ListBox`](listbox)、[`ChoiceBox`](choicebox)和[`ComboBox`](combobox)。这些组件都显示键值项的列表，并提供添加、移除、选择和管理列表项的方法。

此页面概述了所有列表组件的共享特性和行为，而每个组件的具体细节则在其各自的页面中进行说明。

## 使用 `ListItem` {#using-listitem}

列表组件由 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 对象组成，这些对象表示列表中的单独项。每个 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>与一个唯一的键和显示文本相关联。<JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 类的重要特性包括：

- 一个 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 封装了一个唯一键 `Object` 和一个文本 `String`，以便在列表组件中显示。
- 您可以通过提供键和文本来构造一个 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>，或仅指定文本以生成一个随机键。

## 使用 API 管理 `ListItem` 对象 {#managing-listitem-objects-with-the-api}

各种列表组件提供了若干方法用于管理项列表，并保持列表与客户端之间的一致状态。通过使用这些方法，您可以有效地管理列表中的项。API 允许您与列表进行交互和操作，以满足您的应用需求。

### 添加项 {#adding-items}

- **添加一个项**：

   - 要将 `ListItem` 添加到列表中，您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink> 方法。
   - 您还可以通过指定键和文本使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> 或 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink> 方法添加新的 `ListItem`。


- **在特定索引插入项：**

   - 要在特定索引插入项，请使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink> 方法。
   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> 或 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink> 方法插入带键和文本的项。

- **插入多个项：**

   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink> 方法在指定索引插入多个项。

:::tip
为了优化性能，与其在每次使用 `add()` 方法时触发服务器与客户端之间的消息，不如先创建一个 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 对象的列表。一旦拥有这个列表，您可以一次性使用 `insert(int index, List<ListItem> items)` 方法将它们全部添加进去。这种方法可以减少服务器与客户端之间的通信，从而提高整体效率。有关此及其他 webforJ 架构中的最佳实践的详细指南，请参阅 [Client/Server Interaction](/docs/architecture/client-server)。
:::

### 移除项 {#removing-items}

- **移除一个项：**

   - 要从列表中移除项，请使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> 或 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink> 方法。

- **移除所有项：**
   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink> 移除列表中的所有项。

### 选择项 {#selecting-items}

所有列表类型都实现了 `SelectableList` 接口。该接口允许多种方式选择当前的 `ListItem`。

#### 使用给定的 `ListItem` {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> 以 `ListItem` 作为参数进行选择。

```java {4}
List demoList = new List();
ListItem demoItem = new ListItem("demo","示例项");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### 使用给定的 `ListItem` 键 {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> 以 `ListItem` 的键作为参数进行选择。

```java {3}
List demoList = new List();
demoList.add("demo","示例项");
demoList.selectKey("demo");
```

#### 使用给定的 `ListItem` 索引 {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> 以 `ListItem` 的索引作为参数进行选择。

```java {3}
List demoList = new List();
demoList.add("demo","示例项");
demoList.selectKey(0);
```

### 其他列表操作 {#other-list-operations}

- **访问和更新项：**

   - 要通过键或索引访问项，请使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> 或 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>。
   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink> 方法更新项的文本，位置在 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 类中。

- **检索关于列表的信息：**
   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink> 方法获取列表的大小。
   - 要检查列表是否为空，请使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink> 方法。

### 遍历列表 {#iterating-over-lists}

所有列表组件都实现了 Java [`Iteratable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html) 接口，提供了一种高效和直观的方式来遍历列表的内容。通过此接口，您可以轻松循环遍历每个 `ListItem`，以最低的努力访问、修改或对每个项执行操作。`Iterable` 接口是 Java 语言的标准模式，确保您的代码对于任何 Java 开发者而言都是熟悉且可维护的。

下面的代码片段展示了两种简单的遍历列表的方法：

```java
list.forEach(item -> {
   item.setText("已修改: " + item.getText());
});

for (ListItem item : list) {
   item.setText("已修改2: " + item.getText());
}
```

## 搜索 <DocChip chip='since' label='26.02' /> {#searching}

所有列表组件都有一个嵌入的搜索字段，可以通过文本过滤项。该字段默认关闭。使用 `getSearch()` 访问搜索配置，然后使用 `setFieldVisible(true)` 在组件的列表顶部显示该字段。

```java
ComboBox comboBox = new ComboBox("水果");
comboBox.insert("苹果", "香蕉", "樱桃", "杏子", "菠萝");

comboBox.getSearch()
  .setFieldVisible(true)
  .setPlaceholder("搜索水果")
  .setEmptyMessage("未找到水果");
```

<ComponentDemo
path='/webforj/listsearch'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListSearchView.java']}
height='450px'
/>


过滤仅仅是隐藏不匹配的项。项的索引和当前选定的项保持不变，因此 `getSelectedIndex()` 继续引用完整列表，而不是当前可见的项。

搜索字段可以通过 `setFieldVisible(false)` 隐藏。

### 配置字段 {#configuring-the-field}

- `setPlaceholder()` 设置搜索字段的占位符文本。默认是 `搜索`。

-  `setEmptyMessage()` 设置搜索没有结果时显示的消息。默认是 `无数据可显示`。

每个设置都有一个对应的 getter：`isFieldVisible()`、`getPlaceholder()`、`getEmptyMessage()` 和 `getTerm()`。

### 从代码过滤 {#filtering-from-code}

`setTerm()` 设置搜索词并过滤列表。无论该字段是否可见都能正常工作，因此可以在不显示任何搜索 UI 的情况下对列表进行过滤。

```java
listBox.getSearch().setTerm("苹果");
```

:::warning `getTerm()` 和搜索字段
在搜索字段中输入内容不会将术语写回配置。`getTerm()` 返回最后传递给 `setTerm()` 的值，而不是用户输入的内容。
:::

## 共享列表属性 {#shared-list-properties}

### 标签 {#label}

所有列表组件都可以分配一个标签，这是与组件相关的描述性文本或标题。标签提供简要说明或提示，以帮助用户理解该列表的目的或预期选择。除了对可用性的重视外，列表标签在辅助功能中也发挥着至关重要的作用，使屏幕阅读器和辅助技术能够提供准确的信息并促进键盘导航。

### 辅助文本 {#helper-text}

每个列表组件可以使用 `setHelperText()` 方法在列表下方显示辅助文本。此辅助文本提供有关可用选项的额外上下文或解释，确保用户拥有作出知情选择所需的信息。

### 水平对齐 {#horizontal-alignment}

所有列表组件都实现了 <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink> 接口，让您控制组件内文本和内容的对齐方式。

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

### 扩展 {#expanses}

webforJ 中的所有列表组件也实现了 <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink> 接口，允许您调整组件的整体大小和视觉权重。这对于将组件适应于各种 UI 上下文（如表单、对话框、侧边栏等）非常有用。

使用 `setExpanse()` 方法设置扩展级别。选项包括：

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

您可以使用以下方式检索当前设置：
```java
Expanse current = listBox.getExpanse();
```

## 主题 {#topics}

<DocCardList className="topics-section" />
