---
sidebar_position: 10
title: ComboBox
slug: combobox
description: >-
  Combine a dropdown list with a text input in the ComboBox to let users select
  preset items or type custom values with placeholder support.
_i18n_hash: 4ef8ce7040bed877e314790f155f728a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

`ComboBox` 组件结合了下拉列表和文本输入的功能，用户可以从预定义选项中选择，或者输入自定义值。当同时需要允许自定义条目和一组建议选项时，它填补了 `ChoiceBox` 未覆盖的空白。

<!-- INTRO_END -->

## 用法 {#usages}

<ParentLink parent="List" />

ComboBox 组件是一个多功能输入元素，结合了下拉列表和文本输入字段的功能。它允许用户从预定义列表中选择项，或根据需要输入自定义值。本节探讨 ComboBox 组件在各种场景中的常见用法：

1. **产品搜索和输入**：使用 ComboBox 实现产品搜索和输入功能。用户可以从预定义列表中选择产品，或输入自定义产品名称。这对于如电子商务网站等产品繁多的应用程序非常有帮助。

2. **标签选择和输入**：在涉及内容标记的应用中，ComboBox 可以作为一个优秀的选择。用户可以从现有标签列表中选择，或通过输入自定义标签来添加。这对于组织和分类内容非常有用。这些标签的示例包括：
    >- 项目标签：在项目管理工具中，用户可以选择标签或标签（例如“紧急”，“进行中”，“已完成”）来对任务或项目进行分类，并根据需要创建自定义标签。
    >- 食谱成分：在烹饪或食谱应用中，用户可以从列表中选择成分（例如“番茄”，“洋葱”，“鸡肉”）或为自定义食谱添加自己的成分。
    >- 位置标签：在地图或地理标记应用中，用户可以选择预定义位置标签（例如“海滩”，“城市”，“公园”）或创建自定义标签以在地图上标记特定位置。

3. **带建议值的数据输入**：在数据输入表单中，可以使用 ComboBox 提速输入，通过提供基于用户输入的建议值列表。这帮助用户准确高效地输入数据。

    :::tip
    当允许用户输入自定义值时，应使用 `ComboBox`。如果只希望预设值，请使用 [`ChoiceBox`](./choice-box.md)。
    :::

## 自定义值 {#custom-value}

更改自定义值属性可以控制用户是否能够更改 `ComboBox` 组件的输入字段中的值。如果为 `true`（默认值），则用户可以更改值。如果设置为 `false`，则用户将无法更改值。这可以通过 <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> 方法进行设置。

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## 占位符 {#placeholder}

可以为 `ComboBox` 设置占位符，当组件为空时，提示用户在字段中输入所需条目。这可以通过 <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> 方法进行设置。

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## 下拉类型 {#dropdown-type}

使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> 方法将值分配给 `ComboBox` 的 `type` 属性，并为 `ComboBox` 下拉框中的 `data-dropdown-for` 属性分配相应值。这对于样式非常有帮助，因为下拉框在打开时将从当前 DOM 位置移除并 relocated 到页面主体的末尾。

这种分离会导致直接使用 CSS 或从父组件的 shadow 部分选择器目标化下拉框变得具有挑战性，除非利用下拉类型属性。

在下面的演示中，下拉类型已设置并在 CSS 文件中使用，以在悬停时放大选项。

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## 最大行数 {#max-row-count}

默认情况下，显示在 `ComboBox` 下拉框中的行数将增加以适应内容。但是，使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> 方法可以控制显示多少项。

:::caution
使用小于或等于0的数字将导致此属性被取消设置。
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## 打开和关闭 {#opening-and-closing}

可以通过 `open()` 和 `close()` 方法程序性控制 `ComboBox` 的选项可见性。这些方法允许您根据需要显示选项列表以供选择或隐藏它，从而提供更大的灵活性来管理 `ComboBox` 的行为。

此外，webforJ 具有事件监听器，用于监听 `ComboBox` 关闭和打开时的状态，给予您更多控制来触发特定操作。

```Java
//在表单中聚焦或打开下一个组件
ComboBox university = new ComboBox("大学");
ComboBox major = new ComboBox("专业");
Button submit = new Button("提交");

//... 添加大学和专业列表

university.onClose( e ->{
  major.open();
});

major.onClose( e ->{
  submit.focus();
});
```

## 打开尺寸 {#opening-dimensions}

`ComboBox` 组件具有允许操作下拉框尺寸的方法。下拉框的 **最大高度** 和 **最小宽度** 可以分别使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> 和 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> 方法进行设置。

:::tip
将 `String` 值传递给这两种方法中的任何一种，允许应用 [任何有效的 CSS 单位](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units)，例如像素、视口尺寸或其他有效规则。传入一个 `int` 将设置为像素传递的值。
:::

## 高亮显示 {#highlighting}

在使用 `ComboBox` 时，可以根据组件获得焦点的方式自定义文本何时高亮显示。这一特性可以减少用户在填写表单时的输入错误，并改善整体导航体验。使用 `setHighlightOnFocus()` 方法更改高亮行为，使用内置的 `HasHighlightOnFocus.Behavior` 枚举之一：

- `ALL`
组件内容在组件获得焦点时始终自动高亮。
- `FOCUS`
组件内容在组件以编程方式获得焦点时自动高亮。
- `FOCUS_OR_KEY`
组件内容在组件以编程方式获得焦点或通过制表键进入时自动高亮。
- `FOCUS_OR_MOUSE`
组件内容在组件以编程方式获得焦点或通过鼠标单击进入时自动高亮。
- `KEY`
组件内容在组件通过制表键进入时自动高亮。
- `KEY_MOUSE`
组件内容在组件通过制表键进入或通过鼠标单击进入时自动高亮。
- `MOUSE`
组件内容在组件通过鼠标单击进入时自动高亮。
- `NONE`
组件内容在组件获得焦点时从不自动高亮。

:::note
如果在失去焦点时内容被高亮，再次获得焦点时将重新高亮，无论设置的行为如何。
:::

## 前缀和后缀 {#prefix-and-suffix}

插槽为增强 `ComboBox` 功能提供灵活的选项。您可以在 `ComboBox` 中嵌入图标、标签、加载旋转器、清除/重置功能、头像/个人资料图片及其他有益组件，以进一步阐明用户的意图。
`ComboBox` 有两个插槽：`prefix` 和 `suffix` 插槽。使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `ComboBox` 的选项前后插入各种组件。

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## 样式 {#styling}

<TableBuilder name="ComboBox" />

## 最佳实践 {#best-practices}

为了确保在使用 `ComboBox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **预加载常见值**：如果存在常见或经常使用的项，请在 `ComboBox` 列表中预加载它们。这会加快对常用项的选择，鼓励一致性。

2. **用户友好的标签**：确保每个选项的显示标签是用户友好的并且自解释。确保用户可以轻松理解每个选择的目的。

3. **验证**：实施输入验证，以处理自定义条目。检查数据的准确性和一致性。您可能需要建议对模糊条目的更正或确认。

4. **默认选择**：设置默认选择，尤其是如果有常见或推荐的选择。这通过减少额外点击的需求来增强用户体验。

5. **ComboBox 与其他列表组件**：如果您需要用户的单一输入，并希望向他们提供预定选择以及自定义输入的能力，则 `ComboBox` 是最佳选择。如果您需要以下行为，则另一个列表组件可能更好：
    - 多重选择并一次性显示所有项：[ListBox](./list-box.md)
    - 防止自定义输入：[ChoiceBox](./choice-box.md)
