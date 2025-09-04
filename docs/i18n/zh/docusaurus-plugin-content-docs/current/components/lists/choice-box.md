---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: e90d77e503b1c8f7fc20109633b1e7be
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

<ParentLink parent="List" />

`ChoiceBox` 组件是一个用户界面元素，旨在为用户提供一个选项或选择的列表。用户可以从该列表中选择一个选项，通常通过点击 `ChoiceBox`，这会触发下拉列表的显示，其中包含可用的选择。用户还可以使用箭头键与 `ChoiceBox` 进行交互。当用户做出选择时，所选选项将显示在 `ChoiceBox` 按钮中。

## Usages {#usages}
`ChoiceBox` 组件用于多种目的，例如从菜单选择项目、从类别列表中选择，或从预定义的集合中选择选项。它们为用户提供了一种有组织和视觉上令人愉悦的选择方式，特别是在可用选项较多时。常见用法包括：

1. **用户选择选项**： `ChoiceBox` 的主要目的是允许用户从列表中选择一个选项。这在需要用户做出选择的应用程序中是非常有价值的，例如：
    - 从类别列表中选择
    - 从预定义集合中选择选项

2. **表单输入**：当设计需要用户输入特定选项的表单时，`ChoiceBox` 简化了选择过程。无论是从预定义列表中选择国家、州还是任何其他选项，`ChoiceBox` 都能简化输入过程。

3. **过滤和排序**： `ChoiceBox` 可用于应用程序中的过滤和排序任务。用户可以从列表中选择过滤标准或排序偏好，以便于数据的组织和导航。

4. **配置和设置**：当您的应用程序包含设置或配置选项时，`ChoiceBox` 为用户提供了一种直观的方式来调整偏好。用户可以从列表中选择设置，使其易于根据需要定制应用程序。

:::tip
`ChoiceBox` 旨在在可用选项数量预先设定的情况下使用，不应允许或包含自定义选项。如果希望允许用户输入自定义值，请使用 [`ComboBox`](./combo-box.md)。
:::

## Dropdown type {#dropdown-type}

使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> 方法将为 `ChoiceBox` 的 `type` 属性分配一个值，并为 `ChoiceBox` 下拉框中的 `data-dropdown-for` 属性分配相应值。这对于样式非常有帮助，因为下拉框在打开时会被移出其当前在 DOM 中的位置，并重新定位到页面主体的末尾。

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

这种分离导致直接使用父组件的 CSS 或 Shadow part 选择器来定位下拉框变得具有挑战性，除非您使用下拉类型属性。

在下面的演示中，下拉类型被设置并在 CSS 文件中用于选择下拉框并更改背景颜色。

<ComponentDemo 
path='/webforj/choiceboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Max row count {#max-row-count}

默认情况下，`ChoiceBox` 的下拉框中显示的行数将增加以适应内容。然而，使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> 方法可以控制显示多少个项目。

:::tip
使用小于或等于 0 的数字将导致取消此属性的设置。
:::

<ComponentDemo 
path='/webforj/choiceboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java'
height='450px'
/>

## Opening and closing {#opening-and-closing}

`ChoiceBox` 选项的可见性可以通过 `open()` 和 `close()` 方法进行程序控制。这些方法允许您显示可供选择的选项列表或根据需要隐藏它，提供更大的灵活性来管理 `ChoiceBox` 的行为。

此外，webforJ 在 `ChoiceBox` 关闭和打开时提供事件监听器，让您能够更加灵活地触发特定操作。

```Java
//重点或打开表单中的下一个组件
ChoiceBox university = new ChoiceBox("University");
ChoiceBox major = new ChoiceBox("Major");
Button submit = new Button("Submit");

//... 添加大学和专业的列表

university.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  submit.focus();
});
```

## Opening dimensions {#opening-dimensions}

`ChoiceBox` 组件具有允许操作下拉框维度的方法。下拉框的**最大高度**和**最小宽度**可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> 和 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> 方法分别进行设置。

:::tip
将 `String` 值传递给这些方法中的任意一个将允许应用 [任何有效的 CSS 单位](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units)，例如像素、视口尺寸或其他有效规则。传递一个 `int` 将以像素为单位设置传递的值。
:::

## Prefix and suffix {#prefix-and-suffix}

插槽提供了灵活的选项，以提高 `ChoiceBox` 的能力。您可以在 `ChoiceBox` 中嵌入图标、标签、加载旋转器、清除/重置功能、头像/个人资料图片和其他有益组件，以进一步澄清用户的意图。`ChoiceBox` 具有两个插槽：`prefix` 和 `suffix` 插槽。使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `ChoiceBox` 中显示选项之前和之后插入各种组件。

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ChoiceBox" />

## Best practices {#best-practices}

为确保在使用 `ChoiceBox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清晰且有限的选项**：尽可能保持选择列表简洁，并与用户的任务相关。`ChoiceBox` 非常适合展示清晰的选项列表。

2. **用户友好的标签**：确保每个选项显示的标签用户友好且自解释。确保用户能够轻松理解每个选择的用途。

3. **默认选择**：在初始显示 `ChoiceBox` 时设置一个默认选择。这确保了选择一个预选选项，减少了做出选择所需的交互次数。

4. **ChoiceBox 与其他列表组件的区别**：如果您需要将用户输入限制为从预定选项列表中选择一个，则 `ChoiceBox` 是最佳选择。如果您需要以下行为，其他列表组件可能更合适：
    - 多重选择，并一次性显示所有项目：[`ListBox`](./list-box.md)
    - 允许自定义输入：[`ComboBox`](./combo-box.md)
