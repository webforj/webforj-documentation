---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: f897ac9d3f5c252ac323762080e1edcf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

`ChoiceBox` 组件提供了一个下拉列表，用户可以从中选择一个选项。当进行选择时，所选的值会显示在按钮上。它非常适合需要用户从固定的一组预定义选项中进行选择的场景，并且可以使用箭头键在列表中进行导航。

<!-- INTRO_END -->

## 用法 {#usages}

<ParentLink parent="列表" />

`ChoiceBox` 组件用于多种目的，例如从菜单中选择项目、从类别列表中进行选择或从预定义的集合中挑选选项。它们为用户提供了一种组织良好且视觉上令人愉悦的选择方式，特别是在可用选项较多的情况下。常见用法包括：

1. **用户选项选择**：`ChoiceBox` 的主要目的是允许用户从列表中选择一个选项。这对于需要用户进行选择的应用程序非常有价值，例如：
    - 从类别列表中进行选择 
    - 从预定义集合中选择选项

2. **表单输入**：在设计需要用户输入特定选项的表单时，`ChoiceBox` 简化了选择过程。无论是选择国家、州或其他选项，`ChoiceBox` 都能简化输入流程。

3. **过滤和排序**：`ChoiceBox` 可以用于应用程序中的过滤和排序任务。用户可以从列表中选择过滤条件或排序偏好，从而促进数据的组织和导航。

4. **配置和设置**：当您的应用程序包含设置或配置选项时，`ChoiceBox` 提供了一种直观的方式让用户调整偏好。用户可以从列表中选择设置，便于根据个人需求自定义应用程序。

:::tip
`ChoiceBox` 旨在在预设数量的选项可用时使用，不应允许或包含自定义选项。如果希望允许用户输入自定义值，请改用 [`ComboBox`](./combo-box.md)。
:::

## 下拉类型 {#dropdown-type}

使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> 方法将为 `ChoiceBox` 的 `type` 属性分配一个值，并为 `ChoiceBox` 下拉列表中的 `data-dropdown-for` 属性分配相应的值。这对于样式设置非常有帮助，因为下拉列表在打开时会被移出其在 DOM 中的当前状态，从而重新放置到页面主体的末尾。

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

这种拆分导致直接使用 CSS 或影子部分选择器从父组件定位下拉列表变得困难，除非您使用下拉类型属性。

在下面的演示中，设置了下拉类型，并在 CSS 文件中使用它来放大鼠标悬停时的选项。

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## 最大行数 {#max-row-count}

默认情况下，`ChoiceBox` 的下拉列表中显示的行数会增加以适应内容。然而，通过使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> 方法，可以控制显示的项目数量。

:::tip
使用小于或等于 0 的数字将导致此属性被取消设置。
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## 开启和关闭 {#opening-and-closing}

可以通过 `open()` 和 `close()` 方法以编程方式控制 `ChoiceBox` 选项的可见性。这些方法允许您根据需要显示或隐藏选项列表，从而提供了更大的灵活性来管理 `ChoiceBox` 的行为。

此外，webforJ 提供了用于何时关闭以及何时打开 `ChoiceBox` 的事件监听器，使您能够更好地控制触发特定操作。

```Java
//聚焦或打开表单中的下一个组件
ChoiceBox university = new ChoiceBox("大学");
ChoiceBox major = new ChoiceBox("专业");
Button submit = new Button("提交");

//... 添加大学和专业的列表

university.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  submit.focus();
});
```

## 开启尺寸 {#opening-dimensions}

`ChoiceBox` 组件具有允许操作下拉列表尺寸的方法。可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> 和 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> 方法设置下拉列表的 **最大高度** 和 **最小宽度**。

:::tip
将 `String` 值传递给任何一个方法都将允许应用 [任何有效的 CSS 单位](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units)，例如像素、视口尺寸或其他有效规则。传递一个 `int` 将设置传入的值为像素。
:::

## 前缀和后缀 {#prefix-and-suffix}

插槽提供了灵活的选项来改善 `ChoiceBox` 的功能。您可以在 `ChoiceBox` 中嵌套图标、标签、加载指示器、清除/重置功能、头像/个人资料图片以及其他有益的组件，以进一步明确用户的意图。
`ChoiceBox` 有两个插槽：`prefix` 和 `suffix` 插槽。使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `ChoiceBox` 中插入显示选项之前和之后的各种组件。

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## 样式 {#styling}

<TableBuilder name="ChoiceBox" />

## 最佳实践 {#best-practices}

为了确保使用 `ChoiceBox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清晰且有限的选项**：尽可能保持选择列表简洁，并与用户的任务相关。`ChoiceBox` 理想用于呈现清晰的选项列表。

2. **用户友好的标签**：确保每个选项的显示标签用户友好且易于理解。确保用户可以轻松理解每个选择的目的。

3. **默认选择**：在初次显示 `ChoiceBox` 时设定默认选择。这确保了预先选择一个选项，减少了做出选择所需的交互次数。

4. **ChoiceBox 与其他列表组件**：如果您需要限制用户输入为从预设选项中选择单个选项，则 `ChoiceBox` 是最佳选择。如果您需要以下行为，则可能需要其他列表组件：
    - 多选并一次性显示所有项目：[`ListBox`](./list-box.md)
    - 允许自定义输入：[`ComboBox`](./combo-box.md)
