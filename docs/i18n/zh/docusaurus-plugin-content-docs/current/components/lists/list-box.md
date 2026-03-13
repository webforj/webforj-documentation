---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 9bf0e23b101252295342c62ce6a0dee9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

`ListBox` 组件显示一个可滚动的项目列表，无需打开下拉菜单。它支持单选和多选，并适用于用户需要一次性查看所有可用选项的场景。

<!-- INTRO_END -->

## 用途 {#usages}

<ParentLink parent="List" />

1. **用户角色分配**：在具有用户访问控制的应用程序中，管理员可以使用 `ListBox` 来分配角色和权限给用户。用户从列表中选择，基于他们的选择分配角色或权限。这确保了对应用程序中不同功能和数据的精确和控制的访问。

2. **项目任务分配**：在项目管理软件中，`ListBox` 组件对于将任务分配给团队成员非常有用。用户可以从列表中选择任务，并将其分配给不同的团队成员。这简化了任务委派，并确保团队内部的责任被清晰定义。

3. **多类别过滤**：在搜索应用程序中，用户经常需要根据多个标准过滤搜索结果。`ListBox` 可以显示各种过滤选项，例如 
>- 产品特性
>- 价格范围
>- 品牌

  用户可以从每个过滤类别中选择项目，以便精炼搜索结果并找到他们所需的内容。

4. **内容分类**：在内容管理系统中，`ListBox` 组件有助于对文章、图像或文件进行分类。用户可以选择一个或多个类别来与他们的内容相关联，从而更容易在系统中组织和搜索内容项。

## 选择选项 {#selection-options}

默认情况下，列表框配置为一次选择一个项目。然而，`ListBox` 实现了 <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink> 接口，该接口可以通过内置方法进行配置，允许用户使用 ***`Shift` 键*** 进行连续输入选择和 ***`Control` (Windows) 或 `Command` (Mac) 键*** 进行分开、多项选择。

使用 <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> 函数来更改此属性。该方法接受 `SelectionMode.SINGLE` 或 `SelectionMode.MULTIPLE`。

:::info 触屏设备行为
在触摸设备上，当启用多重选择时，用户可以在不按住 Shift 键的情况下选择多个项目。
:::

此外，可以使用箭头键在 `ListBox` 中导航，当 `ListBox` 具有焦点时，按下字母键将选择以该字母开头的选项，如果存在多个选项，则循环选择以该字母开头的选项。

<ComponentDemo 
path='/webforj/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
height = '250px'
/>

## 样式 {#styling}

<TableBuilder name="ListBox" />

## 最佳实践 {#best-practices}

为了确保在使用 `ChoiceBox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **优先考虑信息层次结构**：使用 `ListBox` 时，确保项目以逻辑和层次顺序组织。将最重要和常用的选项放在列表的顶部。这使用户更容易找到他们所需的内容，而无需过多滚动。

2. **限制列表长度**：避免让用户面临过于冗长的 `ListBox`。如果要显示的项目数量较大，考虑实施分页、搜索或过滤选项，以帮助用户快速查找项目。或者，您可以将项目分组到类别中，以减少列表长度。

3. **清晰且描述性标签**：为 `ListBox` 中的每个项目提供清晰且描述性的标签。用户应能够毫不模糊地理解每个选项的目的。使用简洁且有意义的项目标签。

4. **多选反馈**：如果您的 `ListBox` 允许多项选择，请提供可视或文本反馈，指示可以从列表中选择多个项目。

5. **默认选择**：考虑为 `ListBox` 设置默认选择，特别是在某个选项比其他选项更常用的情况下。这可以通过预选最有可能的选择来简化用户体验。
