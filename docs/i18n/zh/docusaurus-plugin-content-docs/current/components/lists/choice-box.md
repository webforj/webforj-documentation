---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: 1da4824585c11423d72c2b75b451a6db
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

`ChoiceBox` 组件提供了一个下拉列表，用户可以从中选择一个选项。当进行选择时，所选的值会显示在按钮上。当用户需要从一组固定的预定义选项中进行选择时，它非常合适，并且可以使用箭头键来导航列表。

<!-- INTRO_END -->

## 用法 {#usages}

<ParentLink parent="List" />

`ChoiceBox` 组件用于多种目的，例如从菜单中选择项目、从类别列表中选择，或从预定义集合中选择选项。它们为用户提供了一种组织良好且视觉愉悦的方式以进行选择，特别是在有多个选项可用时。常见用法包括：

1. **用户选择选项**：`ChoiceBox` 的主要目的是允许用户从列表中选择一个选项。这在需要用户进行选择的应用程序中是非常有价值的，例如：
    - 从类别列表中选择
    - 从预定义集合中选择选项

2. **表单输入**：在设计需要用户输入特定选项的表单时，`ChoiceBox` 简化了选择过程。无论是选择一个国家、州，还是从预定义列表中的任何其他选项，`ChoiceBox` 都能简化输入过程。

3. **筛选和排序**：`ChoiceBox` 可以用于应用程序中的筛选和排序任务。用户可以从列表中选择筛选条件或排序偏好，从而促进数据的组织和导航。

4. **配置和设置**：当您的应用程序包含设置或配置选项时，`ChoiceBox` 为用户提供了一种直观的方式来调整偏好。用户可以从列表中选择设置，方便其根据需要定制应用程序。

:::tip
`ChoiceBox` 用于可用选项数量预设的情况，不应允许或包括自定义选项。如果希望用户输入自定义值，请使用 [`ComboBox`](./combo-box.md)。
:::

## 下拉类型 {#dropdown-type}

使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> 方法将为 `ChoiceBox` 的 `type` 属性分配一个值，并相应地为 `ChoiceBox` 的下拉列表中的 `data-dropdown-for` 属性分配一个值。这对于样式非常有帮助，因为下拉列表在打开时会被移出其当前在 DOM 中的位置，并 relocated 到页面主体的末尾。

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

这种脱离使得直接使用 CSS 或父组件的阴影部分选择器来定位下拉列表变得困难，除非您使用下拉类型属性。

在下面的演示中，下拉类型被设置并用于 CSS 文件中以选择下拉列表并更改背景颜色。

<ComponentDemo 
path='/webforj/choiceboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## 最大行数 {#max-row-count}

默认情况下，`ChoiceBox` 的下拉列表中显示的行数将增加以适应内容。然而，使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> 方法可以控制显示多少项目。

:::tip
使用小于或等于0的数字将导致取消此属性的设置。
:::

<ComponentDemo 
path='/webforj/choiceboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java'
height='450px'
/>

## 打开和关闭 {#opening-and-closing}

`ChoiceBox` 的选项可通过编程方式使用 `open()` 和 `close()` 方法控制其可见性。这些方法允许您根据需要显示选择的选项列表或隐藏它，为管理 `ChoiceBox` 的行为提供了更大的灵活性。

此外，webforJ 提供了事件监听器，当 `ChoiceBox` 关闭和打开时，您可以更好地控制触发特定动作。

```Java
//聚焦或打开表单中的下一个组件
ChoiceBox university = new ChoiceBox("University");
ChoiceBox major = new ChoiceBox("Major");
Button submit = new Button("Submit");

//... 添加大学和专业列表

university.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  submit.focus();
});
```

## 打开尺寸 {#opening-dimensions}

`ChoiceBox` 组件具有允许操作下拉维度的方法。可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> 和 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> 方法分别设置下拉列表的 **最大高度** 和 **最小宽度**。

:::tip
向这些方法传递 `String` 值将允许应用 [任何有效的 CSS 单位](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units)，例如像素、视口尺寸或其他有效规则。传递 `int` 将以像素设置传递的值。
:::

## 前缀和后缀 {#prefix-and-suffix}

插槽为提高 `ChoiceBox` 的功能提供了灵活的选项。您可以在 `ChoiceBox` 内嵌套图标、标签、加载指示器、清除/重置功能、头像/个人资料图片和其他有益组件，以进一步明确用户的意图。
`ChoiceBox` 具有两个插槽：`prefix` 和 `suffix` 插槽。使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在显示选项之前和之后插入各种组件。

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## 样式 {#styling}

<TableBuilder name="ChoiceBox" />

## 最佳实践 {#best-practices}

为了确保使用 `ChoiceBox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清晰且有限的选项**：在可能的情况下，保持选择列表简洁，并与用户的任务相关。`ChoiceBox` 非常适合提供清晰的选项列表。

2. **用户友好的标签**：确保每个选项的显示标签是用户友好的且自我解释。确保用户能够轻松理解每个选择的目的。

3. **默认选择**：在 `ChoiceBox` 初次显示时设置默认选择。这确保一个预先选择的选项，减少了做出选择所需的交互次数。

4. **`ChoiceBox` vs 其他列表组件**：如果您需要将用户输入限制为从预定义选项列表中选择单一选择，则 `ChoiceBox` 是最佳选择。如果需要以下行为，则可能需要使用其他列表组件：
    - 多重选择并一次性显示所有项目： [`ListBox`](./list-box.md)
    - 允许自定义输入： [`ComboBox`](./combo-box.md)
