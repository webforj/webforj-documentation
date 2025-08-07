---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: d0112ef19b8ef7b0b2621af5c500a6c9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

<ParentLink parent="List" />

`ComboBox` 组件是一个用户界面元素，旨在为用户提供一个选项或选择的列表，以及一个输入自定义值的字段。用户可以从列表中选择一个选项，通常通过单击 `ComboBox` 来触发显示包含可用选项的下拉列表，或者输入一个自定义值。用户还可以通过箭头键与 `ComboBox` 进行交互。当用户做出选择时，所选择的选项会显示在 `ComboBox` 中。

## Usages {#usages}

ComboBox 组件是一个多功能的输入元素，结合了下拉列表和文本输入字段的功能。它允许用户从预定义列表中选择项目或根据需要输入自定义值。本节探讨 ComboBox 组件在各种场景下的常见用法：

1. **产品搜索与输入**：使用 ComboBox 实现产品搜索和输入功能。用户可以从预定义列表中选择产品，或输入自定义产品名称。这对于电子商务网站等产品种类繁多的应用非常有帮助。

2. **标签选择与输入**：在涉及内容标记的应用中，ComboBox 可以作为一个绝佳的选择。用户可以从现有标签列表中选择或通过输入自定义标签添加标签。这有助于组织和分类内容。这类标签的例子包括：
    >- 项目标签：在项目管理工具中，用户可以选择标签或标签（例如“紧急”，“进行中”，“已完成”）来对任务或项目进行分类，并可以根据需要创建自定义标签。
    >- 食谱配料：在烹饪或食谱应用中，用户可以从列表中选择配料（例如“西红柿”，“洋葱”，“鸡肉”）或添加自己的配料以制作自定义食谱。
    >- 位置标签：在地图或地理标记应用中，用户可以选择预定义的位置标签（例如“海滩”，“城市”，“公园”）或创建自定义标签以标记地图上的特定地点。

3. **带建议值的数据输入**：在数据输入表单中，ComboBox 可用于通过提供基于用户输入的建议值列表来加快输入速度。这有助于用户准确高效地输入数据。

    :::tip
    当允许用户输入自定义值时，应使用 `ComboBox`。如果只需要预设值，请使用 [`ChoiceBox`](./choice-box.md)。
    :::

## Custom value {#custom-value}

更改自定义值属性可以控制用户是否能够更改 `ComboBox` 组件输入字段中的值。如果为 `true`（这是默认值），则用户可以更改值。如果设置为 `false`，用户将无法更改值。这可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> 方法进行设置。

<ComponentDemo 
path='/webforj/comboboxcustomvalue?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java'
height = '200px'
/>

## Placeholder {#placeholder}

可以为 `ComboBox` 设置一个占位符，该占位符将在组件的文本字段为空时显示，以提示用户在该字段中进行输入。这可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> 方法完成。

<ComponentDemo 
path='/webforj/comboboxplaceholder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java'
height = '200px'
/>

## Dropdown type {#dropdown-type}

使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> 方法将为 `ComboBox` 的 `type` 属性分配一个值，并为 `ComboBox` 的下拉菜单中的 `data-dropdown-for` 属性分配相应的值。这对于样式非常有用，因为打开时下拉菜单会从当前 DOM 位置中移出并重新定位到页面主体底部。

这种分离创建了一种情况，使得从父组件直接使用 CSS 或 shadow part 选择器来定位下拉菜单变得具有挑战性，除非使用下拉类型属性。

在下面的演示中，设置了 Dropdown 类型并在 CSS 文件中使用它来选择下拉菜单并更改背景颜色。

<ComponentDemo 
path='/webforj/comboboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Max row count {#max-row-count}

默认情况下，`ComboBox` 下拉菜单中显示的行数将增加到适应内容。但是，使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> 方法可以控制显示的项目数量。

:::caution
使用小于或等于 0 的数字将导致取消此属性的设置。
:::

<ComponentDemo 
path='/webforj/comboboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java'
height='450px'
/>

## Opening and closing {#opening-and-closing}

可以通过 `open()` 和 `close()` 方法以编程方式控制 `ComboBox` 的选项可见性。这些方法允许您显示可供选择的选项列表或根据需要隐藏它，从而提供更大的灵活性以管理 `ComboBox` 的行为。

此外，webforJ 有事件监听器，用于 `ComboBox` 关闭和打开时的情况，让您有更多的控制权触发特定的操作。

```Java
// 在表单中聚焦或打开下一个组件
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

## Opening dimensions {#opening-dimensions}

`ComboBox` 组件具有允许操作下拉菜单维度的方法。可以通过 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> 和 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> 方法分别设置下拉菜单的**最大高度**和**最小宽度**。

:::tip
将 `String` 值传递给这些方法中的任意一个将允许应用[任何有效的 CSS 单位](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units)，如像素、视口尺寸或其他有效规则。传递一个 `int` 将设置为以像素为单位传递的值。
:::

## Highlighting {#highlighting}

在使用 `ComboBox` 时，您可以根据组件获得焦点的方式自定义文本的高亮显示。这一特性能够减少用户在填写表单时出现的输入错误，并改善整体导航体验。使用 `setHighlightOnFocus()` 方法基于以下内置的 `HasHighlightOnFocus.Behavior` 枚举来更改高亮行为：

- `ALL`
组件的内容总是在组件获得焦点时自动高亮。
- `FOCUS`
组件的内容在程序控制下当组件获得焦点时自动高亮。
- `FOCUS_OR_KEY`
组件的内容在程序控制下或通过 Tab 键进入时自动高亮。
- `FOCUS_OR_MOUSE`
组件的内容在程序控制下或通过鼠标单击时自动高亮。
- `KEY`
组件的内容在通过 Tab 键进入时自动高亮。
- `KEY_MOUSE`
组件的内容在通过 Tab 键进入或鼠标单击时自动高亮。
- `MOUSE`
组件的内容在通过鼠标单击进入时自动高亮。
- `NONE`
组件的内容在组件获得焦点时永远不会自动高亮。

:::note
如果在失去焦点时内容被高亮，则在重新获得焦点时它会再次被高亮，无论设置的行为如何。
:::

## Prefix and suffix {#prefix-and-suffix}

插槽提供灵活的选项来提升 `ComboBox` 的能力。您可以在 `ComboBox` 中嵌入图标、标签、加载旋转器、清除/重置功能、头像/个人资料图片和其他有益的组件，以进一步明确用户的意图。
`ComboBox` 有两个插槽：`prefix` 和 `suffix` 插槽。使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `ComboBox` 中的选项之前和之后插入各种组件。

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ComboBox" />

## Best practices {#best-practices}

为确保在使用 `ComboBox` 组件时提供最佳用户体验，请考虑以下最佳实践：

1. **预加载常用值**：如果有常见或经常使用的项目，请将它们预加载到 `ComboBox` 列表中。这可以加快对常选项目的选择，并鼓励一致性。

2. **用户友好的标签**：确保每个选项的显示标签用户友好且自解释。确保用户能够轻松理解每个选择的目的。

3. **验证**：实现输入验证以处理自定义输入。检查数据的准确性和一致性。您可能想要为模棱两可的条目建议更正或确认。

4. **默认选择**：设置默认选择，特别是如果有常见或推荐的选择。这通过减少额外点击的需要来提升用户体验。

5. **ComboBox 与其他列表组件**：如果您需要用户的单一输入并想要向他们提供预先确定的选择和自定义输入的能力，则 `ComboBox` 是最佳选择。如果您需要以下行为，可能其他列表组件会更好：
    - 多重选择并一次性显示所有项目：[ListBox](./list-box.md)
    - 防止自定义输入：[ChoiceBox](./choice-box.md)
