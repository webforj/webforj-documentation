---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: b1ed30653bdca5af11b2f138a491baef
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

`ComboBox` 组件组合了下拉列表和文本输入，用户可以从预定义的选项中选择，也可以输入自定义值。当需要允许自定义条目与一组建议选项并存时，它填补了 `ChoiceBox` 无法覆盖的空白。

<!-- INTRO_END -->

## 用法 {#usages}

<ParentLink parent="List" />

ComboBox 组件是一个多功能输入元素，结合了下拉列表和文本输入字段的功能。用户可以从预定义列表中选择项目或根据需要输入自定义值。本节探讨 ComboBox 组件在各种场景中的常见用法：

1. **产品搜索和输入**：使用 ComboBox 实现产品搜索和输入功能。用户可以从预定义列表中选择产品或输入自定义产品名称。这对于产品种类繁多的电子商务网站等应用很有帮助。

2. **标签选择和输入**：在涉及内容标签的应用中，ComboBox 可以作为一个极好的选择。用户可以从现有标签列表中选择，或通过输入来添加自定义标签。这对内容的组织和分类很有用。这类标签的例子包括：
    >- 项目标签：在项目管理工具中，用户可以选择标签（例如“紧急”，“进行中”，“已完成”）来对任务或项目进行分类，并可以根据需要创建自定义标签。
    >- 食谱成分：在烹饪或食谱应用中，用户可以从列表中选择成分（例如“番茄”，“洋葱”，“鸡肉”），或者为自定义食谱添加自己的成分。
    >- 位置标签：在地图或地理标记应用中，用户可以选择预定义的位置标签（例如“海滩”，“城市”，“公园”），或创建自定义标签以在地图上标记特定地点。

3. **带建议值的数据输入**：在数据输入表单中，ComboBox 可以通过提供基于用户输入的建议值列表来加快输入速度。这帮助用户准确有效地输入数据。

    :::tip
    当允许用户输入自定义值时，应使用 `ComboBox`。如果只希望使用预设值，请使用 [`ChoiceBox`](./choice-box.md)。
    :::

## 自定义值 {#custom-value}

更改自定义值属性可以控制用户是否能够更改 `ComboBox` 组件输入字段中的值。如果设置为 `true`（这是默认值），用户可以更改该值。如果设置为 `false`，用户将无法更改该值。这可以通过 <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> 方法进行设置。

<ComponentDemo 
path='/webforj/comboboxcustomvalue?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java'
height = '200px'
/>

## 占位符 {#placeholder}

可以为 `ComboBox` 设置一个占位符，当组件为空时，将在文本字段中显示，以提示用户输入所需条目。这可以通过 <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> 方法完成。

<ComponentDemo 
path='/webforj/comboboxplaceholder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java'
height = '200px'
/>

## 下拉类型 {#dropdown-type}

使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> 方法将值分配给 `ComboBox` 的 `type` 属性，并为 `ComboBox` 下拉菜单中的 `data-dropdown-for` 属性分配相应的值。这对于样式设计很有帮助，因为下拉菜单在打开时会从其当前在 DOM 中的位置移动到页面主体的末尾。

这种分离会导致直接使用 CSS 或父组件的阴影部件选择器来定位下拉菜单变得困难，除非您利用下拉类型属性。

在下面的演示中，下拉类型已设置并在 CSS 文件中使用以选择下拉菜单并改变背景颜色。

<ComponentDemo 
path='/webforj/comboboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## 最大行数 {#max-row-count}

默认情况下，`ComboBox` 下拉菜单中显示的行数会增加以适应内容。然而，使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> 方法可以控制显示的项目数量。

:::caution
使用小于或等于 0 的数字将导致取消设置此属性。
:::

<ComponentDemo 
path='/webforj/comboboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java'
height='450px'
/>

## 打开和关闭 {#opening-and-closing}

可以通过 `open()` 和 `close()` 方法以编程方式控制 `ComboBox` 选项的可见性。这些方法允许您根据需要显示选项列表或将其隐藏，从而提供更大的灵活性来管理 `ComboBox` 的行为。

此外，webforJ 拥有在 `ComboBox` 关闭和打开时的事件监听器，为您提供更多触发特定操作的控制。

```Java
//聚焦或打开表单中的下一个组件
ComboBox university = new ComboBox("University");
ComboBox major = new ComboBox("Major");
Button submit = new Button("Submit");

//... 添加大学和专业的列表

university.onClose( e ->{
  major.open();
});

major.onClose( e ->{
  submit.focus();
});
```

## 打开尺寸 {#opening-dimensions}

`ComboBox` 组件具有允许操作下拉菜单尺寸的方法。下拉菜单的**最大高度**和**最小宽度**可以分别使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> 和 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> 方法进行设置。

:::tip
为这两个方法传递 `String` 值将允许应用 [任何有效的 CSS 单位](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units)，例如像素、视口尺寸或其他有效规则。传递 `int` 将在像素中设置传递的值。
:::

## 高亮显示 {#highlighting}

在使用 `ComboBox` 时，您可以根据组件获得焦点的方式自定义文本高亮显示的时机。此功能可以减少用户填写表单时的输入错误，并改善整体导航体验。使用 `setHighlightOnFocus()` 方法与内置的 `HasHighlightOnFocus.Behavior` 枚举之一改变高亮显示行为：

- `ALL`
组件的内容在组件获得焦点时始终自动高亮显示。
- `FOCUS`
组件的内容在程序控制下组件获得焦点时自动高亮显示。
- `FOCUS_OR_KEY`
组件的内容在程序控制下组件获得焦点时或通过制表键进入时自动高亮显示。
- `FOCUS_OR_MOUSE`
组件的内容在程序控制下组件获得焦点时或通过单击进入时自动高亮显示。
- `KEY`
组件的内容在通过制表键进入时自动高亮显示。
- `KEY_MOUSE`
组件的内容在通过制表键进入或用鼠标单击进入时自动高亮显示。
- `MOUSE`
组件的内容在通过鼠标单击进入时自动高亮显示。
- `NONE`
组件的内容在组件获得焦点时从不自动高亮显示。

:::note
如果内容在失去焦点时被高亮显示，那么在重新获得焦点时将再次高亮显示，无论设定的行为是什么。
:::

## 前缀和后缀 {#prefix-and-suffix}

插槽提供了灵活的选项，以提高 `ComboBox` 的能力。您可以在 `ComboBox` 中嵌入图标、标签、加载指示器、清除/重置功能、头像/个人资料图片以及其他有用的组件，以进一步明确目的。
`ComboBox` 有两个插槽：`prefix` 和 `suffix` 插槽。使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `ComboBox` 内选项之前和之后插入各种组件。

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## 样式 {#styling}

<TableBuilder name="ComboBox" />

## 最佳实践 {#best-practices}

为了确保在使用 `ComboBox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **预加载常见值**：如果有常见或经常使用的项目，请在 `ComboBox` 列表中预加载它们。这可以加快对常用项目的选择，并鼓励一致性。

2. **用户友好的标签**：确保每个选项显示的标签对用户友好且自解释。确保用户能够轻松理解每个选择的目的。

3. **验证**：实现输入验证以处理自定义条目。检查数据的准确性和一致性。您可能需要建议修正或确认模棱两可的条目。

4. **默认选择**：设置默认选择，尤其是如果有常见或推荐的选择。这增强了用户体验，减少了不必要的点击。

5. **ComboBox 与其他列表组件的选择**：如果您需要用户的单个输入，并希望为其提供预先确定的选择和自定义输入的能力，则选择 `ComboBox` 是最佳选择。如果您需要以下行为，其他列表组件可能会更好：
    - 多重选择并一次性显示所有条目：[ListBox](./list-box.md)
    - 防止自定义输入：[ChoiceBox](./choice-box.md)
