---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: 199696bfbf6489520cec364f16226489
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

`ComboBox` 组件结合了下拉列表和文本输入，因此用户可以从预定义的选项中选择，或输入自定义值。当需要允许自定义条目与一系列建议选项并存时，它填补了 `ChoiceBox` 无法覆盖的空白。

<!-- INTRO_END -->

## 用法 {#usages}

<ParentLink parent="List" />

ComboBox 组件是一个多功能输入元素，结合了下拉列表和文本输入字段的功能。它允许用户从预定义列表中选择项目或根据需要输入自定义值。本节探讨了 ComboBox 组件在各种场景中的常见用法：

1. **产品搜索和输入**：使用 ComboBox 实现产品搜索和输入功能。用户可以从预定义列表中选择产品，或输入自定义产品名称。这对于像电子商务网站这样的应用程序非常有帮助，因为产品种类繁多且多样化。

2. **标签选择和输入**：在涉及内容标记的应用程序中，ComboBox 可以作为一个优秀的选择。用户可以从现有标签列表中选择，或通过输入自定义标签来添加。这对于组织和分类内容非常有用。这些标签的示例包括：
    >- 项目标签：在项目管理工具中，用户可以选择标签或标签（例如，“紧急”，“进行中”，“已完成”）来对任务或项目进行分类，并可以根据需要创建自定义标签。
    >- 食谱成分：在烹饪或食谱应用程序中，用户可以从列表中选择成分（例如，“西红柿”，“洋葱”，“鸡肉”）或为自定义食谱添加自己的成分。
    >- 位置标签：在映射或地理标注应用程序中，用户可以选择预定义位置标签（例如，“海滩”，“城市”，“公园”）或创建自定义标签，以在地图上标记特定地点。

3. **带建议值的数据输入**：在数据输入表单中，ComboBox 可用于通过提供基于用户输入的建议值列表来加快输入。这有助于用户准确高效地输入数据。

    :::tip
    当允许用户输入自定义值时，应使用 `ComboBox`。如果只需要预设值，请使用 [`ChoiceBox`](./choice-box.md)。
    :::

## 自定义值 {#custom-value}

更改自定义值属性可以控制用户是否能够更改 `ComboBox` 组件输入字段中的值。如果为 `true`（默认值），则用户可以更改该值。如果设置为 `false`，则用户将无法更改该值。这可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> 方法进行设置。

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com.webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## 占位符 {#placeholder}

可以为 `ComboBox` 设置占位符，当组件为空时会在文本字段中显示，以提示用户在该字段中输入所需的内容。这可以通过 <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> 方法来完成。

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com.webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## 下拉类型 {#dropdown-type}

使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> 方法将值分配给 `ComboBox` 的 `type` 属性，并在 `ComboBox` 的下拉菜单中分配相应的 `data-dropdown-for` 属性。这对样式很有帮助，因为下拉菜单在打开时会被移出其当前在 DOM 中的位置，并 relocated 到页面主体的末尾。

这种分离会导致直接使用 CSS 或 Shadow part 选择器从父组件中定位下拉菜单变得具有挑战性，除非您使用下拉类型属性。

在下面的演示中，下拉类型被设置并在 CSS 文件中用于选择下拉菜单并更改背景颜色。

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## 最大行数 {#max-row-count}

默认情况下，`ComboBox` 下拉菜单中显示的行数将增加以适应内容。然而，使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> 方法可以控制显示的项目数量。

:::caution
使用小于或等于 0 的数字将导致取消设置此属性。
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## 打开和关闭 {#opening-and-closing}

可以通过 `open()` 和 `close()` 方法以编程方式控制 `ComboBox` 选项的可见性。这些方法允许您展示供选择的选项列表或根据需要隐藏它，从而提供更大的灵活性来管理 `ComboBox` 的行为。

此外，webforJ 还具有在 `ComboBox` 关闭和打开时的事件监听器，使您可以更好地控制触发特定操作。

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

## 打开尺寸 {#opening-dimensions}

`ComboBox` 组件具有可以操作下拉菜单尺寸的方法。可以通过 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> 和 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> 方法来设置下拉菜单的 **最大高度** 和 **最小宽度**。

:::tip
将 `String` 值传递给这两个方法中的任意一个将允许应用[任何有效的 CSS 单位](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units)，例如像素、视口尺寸或其他有效规则。传递一个 `int` 将以像素设置所传递的值。
:::

## 高亮 {#highlighting}

在使用 `ComboBox` 时，您可以根据组件获取焦点的方式自定义文本何时高亮。此功能可以减少用户填写表单时的输入错误，并改善整体导航体验。使用 `setHighlightOnFocus()` 方法和内置的 `HasHighlightOnFocus.Behavior` 枚举之一来更改高亮行为：

- `ALL`
组件的内容在组件获得焦点时始终自动高亮。
- `FOCUS`
组件的内容在组件获得程序控制的焦点时自动高亮。
- `FOCUS_OR_KEY`
组件的内容在组件获得程序控制的焦点或通过 Tab 键进入时自动高亮。
- `FOCUS_OR_MOUSE`
组件的内容在组件获得程序控制的焦点或通过鼠标单击进入时自动高亮。
- `KEY`
组件的内容在通过 Tab 键进入时自动高亮。
- `KEY_MOUSE`
组件的内容在通过 Tab 键进入或通过鼠标单击进入时自动高亮。
- `MOUSE`
组件的内容在通过鼠标单击进入时自动高亮。
- `NONE`
组件的内容在获得焦点时从不自动高亮。

:::note
如果在失去焦点时内容被高亮，再次获得焦点时将再次高亮，无论设置的行为如何。
:::

## 前缀和后缀 {#prefix-and-suffix}

插槽提供了改善 `ComboBox` 能力的灵活选项。您可以在 `ComboBox` 中嵌套图标、标签、加载旋转器、清除/重置功能、头像/个人资料图片以及其他有益组件，以进一步向用户阐明预期含义。
`ComboBox` 有两个插槽：`prefix` 和 `suffix` 插槽。使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `ComboBox` 中的选项之前和之后插入各种组件。

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## 样式 {#styling}

<TableBuilder name="ComboBox" />

## 最佳实践 {#best-practices}

为了确保在使用 `ComboBox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **预加载常见值**：如果有常见或经常使用的项目，请在 `ComboBox` 列表中预加载它们。这可以加快对常用项目的选择并鼓励一致性。

2. **用户友好的标签**：确保每个选项显示的标签是用户友好的，并且易于理解。确保用户可以轻松理解每个选择的目的。

3. **验证**：实施输入验证以处理自定义条目。检查数据的准确性和一致性。您可能想对模糊条目建议更正或确认。

4. **默认选择**：设置默认选择，特别是如果有常见或推荐的选择。这通过减少额外点击来提升用户体验。

5. **ComboBox 与其他列表组件的选择**：如果您需要用户的单项输入，并希望提供预定选项同时允许用户自定义其输入，则 `ComboBox` 是最佳选择。如果您需要以下行为，则其他列表组件可能更合适：
    - 多选并一次性显示所有项目：[ListBox](./list-box.md)
    - 防止自定义输入：[ChoiceBox](./choice-box.md)
