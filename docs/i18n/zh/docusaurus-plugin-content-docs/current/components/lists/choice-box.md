---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: 1c1224ca662a0e268606dc1cb6a0e96a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

`ChoiceBox` 组件展示了一个下拉列表，用户可以从中选择一个选项。当选项被选择时，所选值会显示在按钮上。它非常适合用户需要从固定预定义选项中进行选择的场景，并且可以使用箭头键来导航列表。

<!-- INTRO_END -->

## 用法 {#usages}

<ParentLink parent="List" />

`ChoiceBox` 组件用于各种目的，比如从菜单中选择项目、从类别列表中选择、或从预定义集合中选择选项。它们为用户提供了一种有组织且视觉上令人愉悦的选择方式，特别是在可用的选项较多时。常见用法包括：

1. **用户选项选择**：`ChoiceBox` 的主要目的是允许用户从列表中选择一个选项。这在需要用户做出选择的应用程序中非常有价值，例如：
    - 从类别列表中选择
    - 从预定义集合中选择选项

2. **表单输入**：在设计需要用户输入特定选项的表单时，`ChoiceBox` 简化了选择过程。无论是选择国家、州，还是从预定义列表中选择其他选项，`ChoiceBox` 都能流畅地处理输入过程。

3. **过滤和排序**：`ChoiceBox` 可用于应用程序中的过滤和排序任务。用户可以从列表中选择过滤条件或排序偏好，方便数据的组织和导航。

4. **配置和设置**：当您的应用程序包含设置或配置选项时，`ChoiceBox` 提供了一种直观的方式让用户调整偏好。用户可以从列表中选择设置，轻松定制应用程序以符合他们的需求。

:::tip
`ChoiceBox` 旨在用于可用选项数量固定的情况，不应允许或包含自定义选项。如果希望允许用户输入自定义值，请改用 [`ComboBox`](./combo-box.md)。
:::

## 下拉类型 {#dropdown-type}

使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> 方法将为 `ChoiceBox` 的 `type` 属性分配一个值，并为 `ChoiceBox` 的下拉菜单中的 `data-dropdown-for` 属性分配相应的值。这对于样式调整很有帮助，因为下拉菜单在打开时会脱离其当前在 DOM 中的位置并重新定位到页面主体的末尾。

这种分离会导致直接使用 CSS 或从父组件的影子部分选择器来定位下拉菜单变得具有挑战性，除非您利用下拉类型属性。

在下面的演示中，下拉类型被设置并在 CSS 文件中使用，用于在您悬停时放大选项。

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## 最大行数 {#max-row-count}

默认情况下，`ChoiceBox` 的下拉菜单中显示的行数将增加以适应内容。然而，使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> 方法可以控制显示的项目数量。

:::tip
使用小于或等于 0 的数字将导致取消设置此属性。
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## 打开和关闭 {#opening-and-closing}

可以使用 `open()` 和 `close()` 方法以编程方式控制 `ChoiceBox` 的选项可见性。这些方法允许您根据需要显示或隐藏选择的选项列表，从而在管理 `ChoiceBox` 的行为时提供更大的灵活性。

此外，webforJ 设置了事件监听器，以便在 `ChoiceBox` 关闭和打开时触发特定操作。

```Java
//聚焦或打开表单中的下一个组件
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

`ChoiceBox` 组件具有允许操作下拉菜单尺寸的方法。可以使用 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> 和 <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> 方法分别设置下拉菜单的**最大高度**和**最小宽度**。

:::tip
将 `String` 值传递给这两个方法中的任何一个都将允许应用 [任何有效的 CSS 单位](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units)，例如像素、视口尺寸或其他有效规则。传递 `int` 将设置以像素为单位的传入值。
:::

## 前缀和后缀 {#prefix-and-suffix}

插槽提供了灵活的选项来增强 `ChoiceBox` 的能力。您可以在 `ChoiceBox` 中嵌入图标、标签、加载指示器、清除/重置功能、头像/个人资料图片以及其他有益的组件，以进一步明确所传达的含义。`ChoiceBox` 有两个插槽：`prefix` 和 `suffix` 插槽。使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `ChoiceBox` 中显示的选项之前和之后插入各种组件。

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## 样式 {#styling}

<TableBuilder name="ChoiceBox" />

## 最佳实践 {#best-practices}

为了确保在使用 `ChoiceBox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清晰和有限的选项**：尽可能保持选择列表简洁，并与用户的任务相关。`ChoiceBox` 适用于呈现清晰的选项列表。

2. **用户友好的标签**：确保每个选项的显示标签是用户友好的并且便于理解。确保用户可以轻松理解每个选项的目的。

3. **默认选择**：在初次显示 `ChoiceBox` 时设置默认选择。这确保了预选选项，减少了做出选择所需的交互次数。

4. **ChoiceBox 与其他列表组件的比较**：如果您需要将用户输入限制为从预定选项列表中的单个选择，`ChoiceBox` 是最佳选择。如果需要以下行为，则其他列表组件可能更适合：
    - 多重选择并一次性显示所有项目： [`ListBox`](./list-box.md)
    - 允许自定义输入： [`ComboBox`](./combo-box.md)
