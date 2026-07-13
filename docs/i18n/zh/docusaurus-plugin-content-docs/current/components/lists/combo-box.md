---
sidebar_position: 10
title: ComboBox
slug: combobox
description: >-
  Combine a dropdown list with a text input in the ComboBox to let users select
  preset items or type custom values with placeholder support.
_i18n_hash: 9e5c0f54f07f604ee91a84210189ca30
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

`ComboBox` 组件将下拉列表与文本输入结合在一起，以便用户可以从预定义选项中选择或输入自定义值。当需要允许自定义条目与一组建议选项并存时，它填补了 `ChoiceBox` 无法覆盖的空白。

<!-- INTRO_END -->

## 使用 {#usages}

<ParentLink parent="List" />

ComboBox 组件是一个多功能输入元素，结合了下拉列表和文本输入字段的功能。它允许用户从预定义列表中选择项目，或根据需要输入自定义值。本节探讨了 ComboBox 组件在各种场景中的常见用法：

1. **产品搜索和输入**：使用 ComboBox 实现产品搜索和输入功能。用户可以从预定义列表中选择产品或输入自定义产品名称。这对电子商务网站等产品种类繁多的应用程序非常有帮助。

2. **标签选择和输入**：在涉及内容标签的应用程序中，ComboBox 可以作为一个绝佳选择。用户可以从现有标签列表中选择标签，或通过输入添加自定义标签。这对于组织和分类内容非常有用。这类标签的示例包括：
    >- 项目标签：在项目管理工具中，用户可以选择标签（例如，“紧急”、“进行中”、“已完成”）来对任务或项目进行分类，还可以根据需要创建自定义标签。
    >- 食谱成分：在烹饪或食谱应用中，用户可以从列表中选择成分（例如，“西红柿”、“洋葱”、“鸡肉”）或添加自己的成分以制作自定义食谱。
    >- 位置标签：在地图或地理标记应用中，用户可以选择预定义位置标签（例如，“海滩”、“城市”、“公园”）或创建自定义标签以在地图上标记特定地点。

3. **带建议值的数据输入**：在数据输入表单中，可以使用 ComboBox 通过提供基于用户输入的建议值列表来加快输入。这有助于用户准确高效地输入数据。

    :::tip
    当允许用户输入自定义值时，应使用 `ComboBox`。如果只需要预设值，请使用 [`ChoiceBox`](./choice-box.md)。
    :::

## 自定义值 {#custom-value}

更改自定义值属性可以控制用户是否能够更改 `ComboBox` 组件输入字段中的值。如果值为 `true`（默认值），则用户可以更改该值。如果设置为 `false`，用户将无法更改该值。这可以通过 <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> 方法进行设置。

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## 占位符 {#placeholder}

可以为 `ComboBox` 设置占位符，当组件为空时，将在文本字段中显示以提示用户在字段中输入所需内容。这可以通过 <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> 方法完成。

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## 下拉类型 {#dropdown-type}

使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> 方法将值分配给 `ComboBox` 的 `type` 属性，并为 `ComboBox` 下拉列表中的 `data-dropdown-for` 属性分配相应值。这对样式很有帮助，因为下拉列表在打开时会脱离其在 DOM 中的当前位置并重新定位到页面主体的末尾。

这种分离造成了一个直接针对下拉列表进行 CSS 或父组件的 shadow part 选择器变得具有挑战性的情况，除非您使用下拉类型属性。

在下面的演示中，下拉类型被设置并在 CSS 文件中使用，以在鼠标悬停时放大选项。

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## 最大行数 {#max-row-count}

默认情况下，`ComboBox` 的下拉列表中显示的行数将增加以适应内容。然而，使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> 方法可以控制显示多少个项目。

:::caution
使用小于或等于 0 的数字将导致取消设置此属性。
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## 打开和关闭 {#opening-and-closing}

可以通过 `open()` 和 `close()` 方法以编程方式控制 `ComboBox` 的选项的可见性。这些方法允许您根据需要显示选项列表以供选择或隐藏它，从而提供对 `ComboBox` 行为的更大灵活性。

此外，webforJ 还为 `ComboBox` 关闭时和打开时提供了事件监听器，让您可以更好地控制触发特定操作。

```Java
//在表单中聚焦或打开下一个组件
ComboBox university = new ComboBox("University");
ComboBox major = new ComboBox("Major");
Button submit = new Button("Submit");

//... 添加大学和专业列表

university.onClose( e ->{
  major.open();
});

major.onClose( e ->{
  submit.focus();
});
```

## 打开尺寸 {#opening-dimensions}

`ComboBox` 组件具有允许操作下拉维度的方法。可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> 和 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> 方法分别设置下拉列表的 **最大高度** 和 **最小宽度**。

:::tip
将 `String` 值传递给这两个方法中的任何一个将允许应用 [任何有效的 CSS 单位](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units)，例如像素、视口维度或其他有效规则。传递 `int` 将以像素设置所传递的值。
:::

## 高亮显示 {#highlighting}

在使用 `ComboBox` 时，您可以根据组件获得焦点的方式自定义文本的高亮显示。当用户填写表单时，这个功能可以减少输入错误，并改善整体导航体验。通过 `setHighlightOnFocus()` 方法使用内置的 `HasHighlightOnFocus.Behavior` 枚举之一更改高亮显示行为：

- `ALL`
组件的内容在组件获得焦点时始终自动高亮显示。
- `FOCUS`
组件的内容在组件被程序控制获得焦点时自动高亮显示。
- `FOCUS_OR_KEY`
组件的内容在组件被程序控制获得焦点或通过 Tab 键进入时自动高亮显示。
- `FOCUS_OR_MOUSE`
组件的内容在组件被程序控制获得焦点或通过鼠标点击进入时自动高亮显示。
- `KEY`
组件的内容在组件通过 Tab 键进入时自动高亮显示。
- `KEY_MOUSE`
组件的内容在组件通过 Tab 键进入或通过鼠标点击进入时自动高亮显示。
- `MOUSE`
组件的内容在组件通过鼠标点击进入时自动高亮显示。
- `NONE`
组件的内容在组件获得焦点时从不自动高亮显示。

:::note
如果内容在失去焦点时被高亮显示，则在重新获得焦点时将再次高亮显示，无论设置的行为如何。
:::

## 前缀和后缀 {#prefix-and-suffix}

插槽为提高 `ComboBox` 的能力提供了灵活的选项。您可以在 `ComboBox` 中嵌入图标、标签、加载指示器、清除/重置能力、头像/个人资料图片和其他有益的组件，以进一步明确用户的意图。
`ComboBox` 具有两个插槽：`prefix` 和 `suffix` 插槽。使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `ComboBox` 的选项前后插入各种组件。

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## 样式 {#styling}

<TableBuilder name="ComboBox" />

## 最佳实践 {#best-practices}

为了确保在使用 `ComboBox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **预加载常用值**：如果有常见或经常使用的项目，请在 `ComboBox` 列表中预加载它们。这可以加快对常选项目的选择，并促进一致性。

2. **用户友好的标签**：确保为每个选项显示的标签用户友好且自我解释。确保用户能够轻松理解每个选择的目的。

3. **验证**：实现输入验证以处理自定义条目。检查数据的准确性和一致性。您可能希望为模糊条目建议更正或确认。

4. **默认选择**：设置默认选择，特别是在存在常见或推荐选择的情况下。这通过减少额外点击的需要来增强用户体验。

5. **ComboBox 与其他列表组件**：如果您需要从用户那里获得单一输入，并希望提供预定选择和自定义输入的能力，`ComboBox` 是最佳选择。如果您需要以下行为，另一种列表组件可能更好：
    - 多重选择并同时显示所有项目：[ListBox](./list-box.md)
    - 防止自定义输入：[ChoiceBox](./choice-box.md)
