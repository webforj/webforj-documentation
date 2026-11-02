---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
sidebar_class_name: new-content
description: >-
  Manage shared list features across ChoiceBox, ComboBox, and ListBox, including
  ListItem objects, adding, removing, and selection APIs.
_i18n_hash: 79a006a2c6a0435414d931e5bfb161bd
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
本节描述了所有列表组件的通用特性，并不是可以实例化或直接使用的类。
:::

在您的应用中可用的列表有三种类型：[`ListBox`](listbox)、[`ChoiceBox`](choicebox) 和 [`ComboBox`](combobox)。这些组件都显示一组键值项，并提供方法来添加、删除、选择和管理列表中的项。

本页面概述了所有列表组件的共同特性和行为，而每个组件的具体细节则在其各自的页面中介绍。

## 使用 `ListItem` {#using-listitem}

列表组件由 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 对象组成，代表列表中的单个项。每个 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 都与一个唯一的键和显示文本相关联。 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 类的重要特性包括：

- <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 封装了一个唯一的键 `Object` 和一个要在列表组件中显示的文本 `String`。
- 您可以通过提供一个键和文本或仅指定文本（也用作键）来构造 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>。

键在列表中必须是唯一的。添加第二个仅包含文本的项且文本相同会抛出 `IllegalArgumentException`。若要显示重复标签，请为这些项提供不同的键。

## 使用 API 管理 `ListItem` 对象 {#managing-listitem-objects-with-the-api}

各种列表组件提供了几个方法来管理项列表，并保持列表与客户端之间的一致状态。通过使用这些方法，您可以有效管理列表中的项。API 允许您与列表进行交互并对其进行操作以满足应用的需求。

### 添加项 {#adding-items}

- **添加一个项**：

   - 要将 `ListItem` 添加到列表中，您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink> 方法。
   - 您还可以通过使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> 或 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink> 方法指定键和文本来添加新的 `ListItem`。

- **在特定索引处插入项：**

   - 要在特定索引处插入项，请使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink> 方法。
   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> 或 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink> 方法插入具有键和文本的项。

- **插入多个项：**

   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink> 方法在指定索引处插入多个项。

:::tip
为了优化性能，最好在每次使用 `add()` 方法时，不要触发服务与客户端之间的消息，而是先创建一个 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> 对象的列表。一旦获取了此列表，您可以通过 `insert(int index, List<ListItem> items)` 方法一次性添加所有项。这种方法减少了服务与客户端的通信，提高了整体效率。有关此和其他 webforJ 架构最佳实践的详细指引，请参阅 [Client/Server Interaction](/docs/architecture/client-server)。
:::

### 删除项 {#removing-items}

- **删除一个项：**

   - 要从列表中删除一个项，请使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> 或 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink> 方法。

- **删除所有项：**
   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink> 从列表中删除所有项。

### 选择项 {#selecting-items}

所有列表类型实现了 `SelectableList` 接口。该接口允许以多种不同方式选择当前的 `ListItem`。

#### 使用给定的 `ListItem` {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> 以 `ListItem` 为参数进行选择。

```java {4}
ListBox demoList = new ListBox();
ListItem demoItem = new ListItem("demo","示例项");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### 以 `ListItem` 的给定键 {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> 以 `ListItem` 的键作为参数进行选择。

```java {3}
ListBox demoList = new ListBox();
demoList.add("demo","示例项");
demoList.selectKey("demo");
```

#### 以 `ListItem` 的给定索引 {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> 以 `ListItem` 的索引作为参数进行选择。

```java {3}
ListBox demoList = new ListBox();
demoList.add("demo","示例项");
demoList.selectIndex(0);
```

### 其他列表操作 {#other-list-operations}

- **访问和更新项：**

   - 要通过键或索引访问项，请使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> 或 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>。
   - 您可以在 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">ListItem</JavadocLink> 类中使用 <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink> 方法更新项的文本。

- **检索有关列表的信息：**
   - 您可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink> 方法获取列表的大小。
   - 要检查列表是否为空，请使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink> 方法。

### 遍历列表 {#iterating-over-lists}

所有列表组件都实现了 Java [`Iterable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html) 接口。使用 `forEach()` 或 `for` 循环来访问或修改每个 `ListItem`。

以下代码示例演示了两种遍历列表的简单方法：

```java
list.forEach(item -> {
   item.setText("已修改: " + item.getText());
});

for (ListItem item : list) {
   item.setText("已修改2: " + item.getText());
}
```

## 搜索 <DocChip chip='since' label='26.02' /> {#searching}

所有列表组件都具有内置的搜索字段，可以根据文本过滤项。该字段默认情况下关闭。使用 `getSearch()` 获取搜索配置，然后使用 `setFieldVisible(true)` 在组件列表顶部显示该字段。

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

过滤仅隐藏不匹配的项。项的索引和当前选择不受影响，因此 `getSelectedIndex()` 继续引用完整列表，而不是当前可见的项。

搜索字段可以再次隐藏，使用 `setFieldVisible(false)`。

### 配置字段 {#configuring-the-field}

- `setPlaceholder()` 设置搜索字段的占位符文本。默认值为 `搜索`。

-  `setEmptyMessage()` 设置在搜索返回无结果时显示的消息。默认值为 `没有数据可显示`。

每个设置都有一个匹配的获取器： `isFieldVisible()`、`getPlaceholder()`、`getEmptyMessage()` 和 `getTerm()`。

### 从代码过滤 {#filtering-from-code}

`setTerm()` 设置搜索词并过滤列表。无论字段是否可见，它都能正常工作，因此可以在不显示任何搜索 UI 的情况下过滤列表。

```java
listBox.getSearch().setTerm("苹果");
```

:::warning `getTerm()` 和搜索字段
在搜索字段中输入内容不会将术语写回配置。`getTerm()` 返回最后传递给 `setTerm()` 的值，而不是用户输入的内容。
:::

## 共享列表属性 {#shared-list-properties}

### 标签 {#label}

所有列表组件都可以分配一个标签，该标签是与组件关联的描述性文本或标题。标签提供了简要的说明或提示，帮助用户理解该特定列表的目的或预期选择。除了对可用性的重要性之外，列表标签在无障碍性中也起着至关重要的作用，使屏幕阅读器和辅助技术能够提供准确的信息并促进键盘导航。

### 辅助文本 {#helper-text}

每个列表组件都可以使用 `setHelperText()` 方法在列表下方显示辅助文本。该辅助文本提供有关可用选项的额外上下文或说明，确保用户拥有做出明智选择所需的信息。

### 水平对齐 {#horizontal-alignment}

所有列表组件实现了 <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink> 接口，使您能够控制文本和内容在组件内部的对齐方式。

使用 `setHorizontalAlignment()` 方法和 `HasHorizontalAlignment.Alignment` 枚举设置对齐方式：

- `Alignment.LEFT`（默认）
- `Alignment.MIDDLE`
- `Alignment.RIGHT`

```java
ListBox listBox = new ListBox();
listBox.setHorizontalAlignment(Alignment.LEFT);
```

要获取当前对齐方式：
```java
Alignment alignment = listBox.getHorizontalAlignment();
```

### 扩展 {#expanses}

webforJ 中的所有列表组件还实现了 <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink> 接口，允许您调整组件的整体大小和视觉重量。这对于将组件适应各种 UI 上下文（如表单、对话框、侧边栏等）非常有用。

使用 `setExpanse()` 方法设置扩展级别。选项包括：

- `Expanse.NONE`
- `Expanse.XSMALL`
- `Expanse.SMALL`
- `Expanse.MEDIUM`（默认）
- `Expanse.LARGE`
- `Expanse.XLARGE`

```java
ListBox listBox = new ListBox();
listBox.setExpanse(Expanse.LARGE);
```

您可以使用以下命令检索当前设置：
```java
Expanse current = listBox.getExpanse();
```

## 主题 {#topics}

<DocCardList className="topics-section" />
