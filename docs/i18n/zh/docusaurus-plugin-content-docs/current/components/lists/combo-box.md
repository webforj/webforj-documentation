---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: ec3f88523477bf08e92fe9153b014b91
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

<ParentLink parent="列表" />

`ComboBox` 组件是一个用户界面元素，旨在为用户呈现选项或选择列表，以及一个输入自定义值的字段。用户可以从此列表中选择一个选项，通常通过单击 `ComboBox`，这将触发显示包含可用选择的下拉列表，或者输入自定义值。用户还可以使用箭头键与 `ComboBox` 进行交互。当用户做出选择后，所选选项将显示在 `ComboBox` 中。

## 用法 {#usages}

ComboBox 组件是一种多功能输入元素，结合了下拉列表和文本输入字段的特点。它允许用户从预定义列表中选择项目或根据需要输入自定义值。本节探讨 ComboBox 组件在各种场景中的常见用法：

1. **产品搜索与录入**：使用 ComboBox 实现产品搜索和录入功能。用户可以从预定义列表中选择产品，也可以输入自定义产品名称。这对于像电子商务网站这样产品种类繁多的应用非常有帮助。

2. **标签选择与录入**：在涉及内容标记的应用中，ComboBox 可以作为一个极好的选择。用户可以从现有标签列表中选择，或通过输入它们添加自定义标签。这对于组织和分类内容非常有用。这些标签的示例包括：
    >- 项目标签：在项目管理工具中，用户可以选择标签（例如，“紧急”，“进行中”，“已完成”）来对任务或项目进行分类，并可以根据需要创建自定义标签。
    >- 食谱原料：在烹饪或食谱应用中，用户可以从列表中选择原料（例如，“西红柿”，“洋葱”，“鸡肉”）或为自定义食谱添加自己的原料。
    >- 位置标签：在地图或地理标记应用中，用户可以选择预定义的位置标签（例如，“海滩”，“城市”，“公园”）或创建自定义标签以在地图上标记特定地点。

3. **带建议值的数据录入**：在数据录入表单中，ComboBox 可以通过根据用户输入提供建议值列表来加快输入。这有助于用户准确高效地输入数据。

    :::tip
    应在允许用户输入自定义值时使用 `ComboBox`。如果只希望有预设值，请使用 [`ChoiceBox`](./choice-box.md)。
    :::

## 自定义值 {#custom-value}

更改自定义值属性可以控制用户是否能够更改 `ComboBox` 组件输入字段中的值。如果为 `true`（这是默认值），则用户可以更改值。如果设置为 `false`，则用户将无法更改该值。这可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> 方法进行设置。

<ComponentDemo 
path='/webforj/comboboxcustomvalue?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java'
height = '200px'
/>

## 占位符 {#placeholder}

可以为 `ComboBox` 设置一个占位符，当组件为空时将在文本字段中显示，以提示用户在该字段中输入所需内容。这可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> 方法完成。

<ComponentDemo 
path='/webforj/comboboxplaceholder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java'
height = '200px'
/>

## 下拉类型 {#dropdown-type}

使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> 方法将为 `ComboBox` 的 `type` 属性分配一个值，并为 `ComboBox` 下拉框中的 `data-dropdown-for` 属性分配相应的值。这对于样式设定很有帮助，因为下拉框在打开时被移出其当前在 DOM 中的位置并移到页面主体的末尾。

这种分离使得直接使用 CSS 或从父组件的阴影部分选择器来定位下拉框变得具有挑战性，除非您利用下拉类型属性。

在下面的演示中，下拉类型被设置并在 CSS 文件中用于选择下拉框并更改背景颜色。

<ComponentDemo 
path='/webforj/comboboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## 最大行数 {#max-row-count}

默认情况下，`ComboBox` 下拉框中显示的行数将增加以适应内容。然而，使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> 方法可以控制显示的项数。

:::caution
使用小于或等于 0 的数字将导致取消设置此属性。
:::

<ComponentDemo 
path='/webforj/comboboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java'
height='450px'
/>

## 打开与关闭 {#opening-and-closing}

可以通过 `open()` 和 `close()` 方法以编程方式控制 `ComboBox` 的选项可见性。这些方法允许您根据需要显示选择列表或隐藏它，从而在管理 `ComboBox` 的行为时提供更大的灵活性。

此外，webforJ 具有用于监听 `ComboBox` 关闭和打开事件的事件监听器，使您能够触发特定操作。

```Java
//聚焦或打开表单中的下一个组件
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

## 打开的尺寸 {#opening-dimensions}

`ComboBox` 组件具有允许操作下拉框尺寸的方法。可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> 和 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> 方法设置下拉框的 **最大高度** 和 **最小宽度**。

:::tip
将 `String` 值传递给这两个方法中的任意一个将允许应用 [任何有效的 CSS 单位](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units)，例如像素、视口尺寸或其他有效规则。传递一个 `int` 将设置以像素为单位的值。
:::

## 高亮显示 {#highlighting}

在处理 `ComboBox` 时，您可以自定义文本何时被高亮显示，这取决于组件如何获得焦点。此功能可以减少用户在填写表单时的输入错误，并改善整体导航体验。使用 `setHighlightOnFocus()` 方法更改高亮行为，使用内置的 `HasHighlightOnFocus.Behavior` 枚举之一：

- `ALL`
组件的内容在组件获得焦点时始终自动高亮显示。
- `FOCUS`
组件的内容在组件获得程序控制下的焦点时自动高亮显示。
- `FOCUS_OR_KEY`
组件的内容在组件获得程序控制下的焦点时或通过 Tab 键进入时自动高亮显示。
- `FOCUS_OR_MOUSE`
组件的内容在组件获得程序控制下的焦点时或通过鼠标单击进入时自动高亮显示。
- `KEY`
组件的内容在组件通过 Tab 键进入时自动高亮显示。
- `KEY_MOUSE`
组件的内容在组件通过 Tab 键进入或通过鼠标单击进入时自动高亮显示。
- `MOUSE`
组件的内容在组件通过鼠标单击进入时自动高亮显示。
- `NONE`
组件的内容在组件获得焦点时从不自动高亮显示。

:::note
如果内容在失去焦点时被高亮显示，则在重新获得焦点时将再次高亮，无论设置的行为如何。
:::

## 前缀和后缀 {#prefix-and-suffix}

插槽提供了灵活的选项，以提高 `ComboBox` 的功能。您可以在 `ComboBox` 中嵌入图标、标签、加载旋转器、清除/重置功能、头像/个人资料图片和其他有用组件，以进一步向用户阐明意图。
`ComboBox` 有两个插槽：`prefix` 和 `suffix` 插槽。使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `ComboBox` 中的选项之前和之后插入各种组件。

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## 样式 {#styling}

<TableBuilder name="ComboBox" />

## 最佳实践 {#best-practices}

为确保使用 `ComboBox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **预加载常用值**：如果有常见或频繁使用的项目，请将它们预加载到 `ComboBox` 列表中。这加快了对常用项的选择，并促进了一致性。

2. **用户友好的标签**：确保每个选项显示的标签是用户友好的且自解释。确保用户能够轻松理解每个选项的目的。

3. **验证**：实施输入验证以处理自定义条目。检查数据的准确性和一致性。您可能希望为模棱两可的输入建议更正或确认。

4. **默认选择**：设置一个默认选择，特别是在存在常见或推荐选择的时候。这通过减少额外点击的需要来增强用户体验。

5. **ComboBox 与其他列表组件的区别**：如果您需要用户提供单个输入并希望提供预先确定的选项和自定义输入的能力，则 `ComboBox` 是最佳选择。如果您需要以下行为，则其他列表组件可能更好：
    - 多重选择并同时显示所有项目：[ListBox](./list-box.md)
    - 防止自定义输入：[ChoiceBox](./choice-box.md)
