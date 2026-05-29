---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: 6e04ceea1fadc5f159b8d4dd9645e014
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

`ChoiceBox` 组件提供了一个下拉列表，用户可以从中选择单个选项。当选择一个选项时，所选值会显示在按钮上。当用户需要从固定的一组预定义选项中进行选择时，这是一个很好的选择，用户还可以使用箭头键在列表中导航。

<!-- INTRO_END -->

## 用法 {#usages}

<ParentLink parent="List" />

`ChoiceBox` 组件用于多种目的，例如从菜单中选择项目、从类别列表中选择或从预定义集合中选择选项。它们为用户提供了一种结构化且视觉上令人愉悦的选择方式，特别是在有多个选项可用时。常见用法包括：

1. **用户选项选择**：`ChoiceBox` 的主要目的是允许用户从列表中选择单个选项。这在要求用户作出选择的应用程序中是非常有价值的，例如：
    - 从类别列表中选择
    - 从预定义集合中选择选项

2. **表单输入**：在设计需要用户输入特定选项的表单时，`ChoiceBox` 简化了选择过程。无论是选择国家、州还是从预定义列表中选择任何其他选项，`ChoiceBox` 都简化了输入过程。

3. **过滤和排序**：`ChoiceBox` 可用于应用程序中的过滤和排序任务。用户可以从列表中选择过滤条件或排序偏好，方便数据的组织和导航。

4. **配置和设置**：当您的应用程序包含设置或配置选项时，`ChoiceBox` 提供了一个直观的方式让用户调整偏好。用户可以从列表中选择设置，轻松定制应用程序以满足他们的需求。

:::tip
`ChoiceBox` 适用于可用选项数量固定的情况，不应允许或包含自定义选项。如果希望允许用户输入自定义值，请使用 [`ComboBox`](./combo-box.md)。
:::

## 下拉类型 {#dropdown-type}

使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> 方法将为 `ChoiceBox` 的 `type` 属性分配一个值，并为 `ChoiceBox` 的下拉列表中的 `data-dropdown-for` 属性分配相应的值。这对于样式非常有帮助，因为下拉列表在打开时会从其当前在DOM中的位置移除并重新定位到页面主体的末尾。

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

这种分离创造了一种情况，直接使用CSS或父组件的shadow part选择器定位下拉列表变得具有挑战性，除非您利用下拉类型属性。

在下面的演示中，下拉类型被设置并在CSS文件中用于选择下拉列表并更改背景颜色。

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## 最大行数 {#max-row-count}

默认情况下，`ChoiceBox` 下拉列表中显示的行数将增加以适应内容。但是，使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> 方法可以控制显示的项数。

:::tip
使用小于或等于0的数字将导致取消设置此属性。
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## 打开和关闭 {#opening-and-closing}

`ChoiceBox` 的选项可通过 `open()` 和 `close()` 方法进行编程控制。这些方法允许您根据需要显示选择选项列表或隐藏它，从而在管理 `ChoiceBox` 的行为时提供更大的灵活性。

此外，webforJ 还提供了 `ChoiceBox` 关闭和打开时的事件监听器，使您可以更好地触发特定操作。

```Java
//在表单中聚焦或打开下一个组件
ChoiceBox university = new ChoiceBox("University");
ChoiceBox major = new ChoiceBox("Major");
Button submit = new Button("Submit");

//... 添加大学和专业的列表

university.onClose(e -> {
  major.focus();
});

major.onClose(e -> {
  submit.focus();
});
```

## 打开尺寸 {#opening-dimensions}

`ChoiceBox` 组件具有可以操作下拉尺寸的方法。下拉列表的 **最大高度** 和 **最小宽度** 可以分别使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> 和 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> 方法设置。

:::tip
将 `String` 值传递给这两个方法中的任意一个将允许应用 [任何有效的CSS单位](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units)，例如像素、视口尺寸或其他有效规则。传递 `int` 将将传递的值设置为像素。
:::

## 前缀和后缀 {#prefix-and-suffix}

插槽为增强 `ChoiceBox` 的能力提供了灵活的选项。您可以在 `ChoiceBox` 内部嵌套图标、标签、加载旋转器、清除/重置功能、头像/个人资料图片以及其他有益组件，以进一步明确对用户的意图。
`ChoiceBox` 有两个插槽：`prefix` 和 `suffix` 插槽。使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `ChoiceBox` 中的显示选项前后插入各种组件。

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## 样式 {#styling}

<TableBuilder name="ChoiceBox" />

## 最佳实践 {#best-practices}

为了确保在使用 `ChoiceBox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清晰且有限的选项**：在可能的情况下尽量保持选项列表简洁，并与用户的任务相关。`ChoiceBox` 非常适合清晰地呈现选项列表。

2. **用户友好的标签**：确保每个选项的显示标签对用户友好且不含糊。确保用户可以轻松理解每个选择的目的。

3. **默认选择**：在初次显示 `ChoiceBox` 时设置默认选择。这确保了预选的选项，减少了做出选择所需的交互次数。

4. **ChoiceBox 与其他列表组件**：如果您需要将用户输入限制为从预定选项列表中选择单个选项，则 `ChoiceBox` 是最佳选择。如果您需要以下行为，则其他列表组件可能更合适：
    - 多重选择并一次性显示所有项目：[`ListBox`](./list-box.md)
    - 允许自定义输入：[`ComboBox`](./combo-box.md)
