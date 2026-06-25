---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: cf4d092418fcf1f593b8b8d00a47344b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

`ChoiceBox` 组件展示一个下拉列表，用户可以从中选择单个选项。当选择作出时，所选的值会显示在按钮上。这非常适合需要从固定预定义选项中进行选择的用户，并且可以使用箭头键在列表中导航。

<!-- INTRO_END -->

## 用法 {#usages}

<ParentLink parent="List" />

`ChoiceBox` 组件用于多种目的，例如从菜单中选择项目、从类别列表中选择或从预定义集合中选择选项。它们为用户提供了一种有组织且视觉上愉快的选择方式，尤其是在存在多个选项可用时。常见用法包括：

1. **用户选项选择**：`ChoiceBox` 的主要目的是允许用户从列表中选择单个选项。这在要求用户做出选择的应用程序中是非常有价值的，例如：
    - 从类别列表中选择
    - 从预定义集合中选择选项

2. **表单输入**：在设计需要用户输入特定选项的表单时，`ChoiceBox` 简化了选择过程。无论是选择国家、州还是任何其他来自预定义列表的选项，`ChoiceBox` 都能流畅地处理输入过程。

3. **过滤和排序**：`ChoiceBox` 可用于应用程序中的过滤和排序任务。用户可以从列表中选择过滤条件或排序偏好，从而方便地组织和导航数据。

4. **配置和设置**：当您的应用程序包含设置或配置选项时，`ChoiceBox` 提供了一种直观的方式供用户调整偏好。用户可以从列表中选择设置，使得定制应用程序变得简单。

:::tip
`ChoiceBox` 旨在用于当可用选项数是预设时，且不应允许或包含自定义选项。如果希望允许用户输入自定义值，请改用 [`ComboBox`](./combo-box.md)。
:::

## 下拉类型 {#dropdown-type}

使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> 方法将为 `ChoiceBox` 的 `type` 属性分配一个值，并为 `ChoiceBox` 的下拉菜单中的 `data-dropdown-for` 属性分配相应的值。这有助于样式设置，因为下拉菜单在打开时会从其当前 DOM 位置中移出并重新定位到页面主体的末尾。

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

这种分离创造了一种情况，直接使用 CSS 或从父组件的影子部分选择器来定位下拉菜单变得具有挑战性，除非您使用下拉类型属性。

在下面的演示中，设置下拉类型并在 CSS 文件中使用它，以在悬停时放大一个选项。

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## 最大行数 {#max-row-count}

默认情况下，`ChoiceBox` 下拉菜单中显示的行数会增加以适应内容。但是，使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> 方法可以控制显示的项目数。

:::tip
使用小于或等于 0 的数字将会取消设置此属性。
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## 打开和关闭 {#opening-and-closing}

`ChoiceBox` 的选项可通过 `open()` 和 `close()` 方法以编程方式进行控制。这些方法允许您根据需要显示选项列表或隐藏它，从而提供在管理 `ChoiceBox` 行为时更多的灵活性。

此外，webforJ 为 `ChoiceBox` 关闭和打开时提供了事件监听器，允许您有更大的控制权去触发特定的操作。

```Java
//在表单中聚焦或打开下一个组件
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

## 打开尺寸 {#opening-dimensions}

`ChoiceBox` 组件具有允许操控下拉尺寸的方法。下拉菜单的 **最大高度** 和 **最小宽度** 可以通过 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> 和 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> 方法设置。

:::tip
将 `String` 值传递给这两个方法中的任何一个，将允许适用 [任何有效的 CSS 单位](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units)，如像素、视口维度或其他有效规则。传递一个 `int` 将会设置传递的值为像素。
:::

## 前缀和后缀 {#prefix-and-suffix}

插槽提供灵活的选项来增强 `ChoiceBox` 的功能。您可以在 `ChoiceBox` 中嵌入图标、标签、加载旋转器、清除/重置功能、头像/个人资料图片和其他有益组件，以进一步明确用户的意图。
`ChoiceBox` 有两个插槽：`prefix` 和 `suffix` 插槽。使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `ChoiceBox` 中所显示的选项之前和之后插入各种组件。

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## 样式 {#styling}

<TableBuilder name="ChoiceBox" />

## 最佳实践 {#best-practices}

为了确保在使用 `ChoiceBox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清晰和有限的选项**：在可能的情况下，保持选择列表简洁，并与用户的任务相关。`ChoiceBox` 非常适合展示清晰的选项列表。

2. **用户友好的标签**：确保每个选项的显示标签对用户友好且自解释。确保用户能够轻松理解每个选择的目的。

3. **默认选择**：在 `ChoiceBox` 最初显示时设置一个默认选择。这确保了一个预选的选项，减少了做出选择所需的交互次数。

4. **ChoiceBox 与其他列表组件**：如果您需要限制用户输入为从预定义选项列表中选择单个选项，则 `ChoiceBox` 是最佳选择。如果您需要以下行为，另一个列表组件可能更合适：
    - 多重选择并同时显示所有条目：[`ListBox`](./list-box.md)
    - 允许自定义输入：[`ComboBox`](./combo-box.md)
