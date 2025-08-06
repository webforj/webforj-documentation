---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 33476ec9bd7a601aaec3f1e37e7c099f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

<ParentLink parent="List" />

`ListBox` 组件是一个用户界面元素，旨在显示可滚动的对象列表，并允许用户从列表中选择单个或多个项目。用户还可以通过箭头键与 `ListBox` 进行交互。

## 使用案例 {#usages}

1. **用户角色分配**：在具有用户访问控制的应用程序中，管理员可以使用 `ListBox` 将角色和权限分配给用户。用户从列表中选择，角色或权限根据他们的选择进行分配。这确保了对应用程序内不同功能和数据的精确和受控访问。

2. **项目任务分配**：在项目管理软件中，`ListBox` 组件对于将任务分配给团队成员非常有用。用户可以从列表中选择任务，并将其分配给不同的团队成员。这简化了任务委派，并确保团队内责任明确。

3. **多类别过滤**：在搜索应用中，用户通常需要根据多个标准过滤搜索结果。`ListBox` 可以显示各种过滤选项，如 
>- 产品特性
>- 价格范围
>- 品牌。 

  用户可以从每个过滤类别中选择项目，从而允许他们细化搜索结果，找到他们所需的内容。

4. **内容分类**：在内容管理系统中，`ListBox` 组件有助于对文章、图片或文件进行分类。用户可以选择一个或多个类别与他们的内容相关联，从而更轻松地组织和搜索系统中的内容项。

## 选择选项 {#selection-options}

默认情况下，列表框配置为一次选择单个项目。然而，`ListBox` 实现了 <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink> 接口，可以通过内置方法进行配置，允许用户使用 ***`Shift` 键*** 进行连续的项目选择，以及使用 ***`Control`（Windows）或 `Command`（Mac）键*** 进行独立的多个项目选择。

使用 <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> 函数来更改此属性。此方法接受 `SelectionMode.SINGLE` 或 `SelectionMode.MULTIPLE`。

:::info 触摸设备行为
在启用多选的触摸设备上，用户可以在不按住 Shift 键的情况下选择多个项目。
:::

此外，箭头键可以用于导航 `ListBox`，而在 `ListBox` 获得焦点时，按下字母键将选择以该字母开头的选项，或者如果存在多个选项，将循环遍历以该字母开头的选项。

<ComponentDemo 
path='/webforj/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
height = '250px'
/>

## 样式 {#styling}

<TableBuilder name="ListBox" />

## 最佳实践 {#best-practices}

为了确保在使用 `ChoiceBox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **优先考虑信息层次**：在使用 `ListBox` 时，确保项目按照逻辑和层次顺序组织。将最重要和常用的选项放在列表顶部。这使得用户更容易找到所需内容，而无需过多滚动。

2. **限制列表长度**：避免用过长的 `ListBox` 让用户感到不知所措。如果需要显示大量项目，请考虑实施分页、搜索或过滤选项，帮助用户快速找到项目。或者，您可以将项目分组以减少列表长度。

3. **清晰且描述性标签**：为 `ListBox` 中的每个项目提供清晰且描述性的标签。用户应该能够理解每个选项的目的，而没有任何歧义。使用简明和有意义的项目标签。

4. **多选反馈**：如果您的 `ListBox` 允许多次选择，则提供视觉或文本反馈，指示可以从列表中选择多个项目。

5. **默认选择**：考虑为 `ListBox` 设置默认选择，特别是当某个选项比其他选项更常用时。这可以通过预选最可能的选择来简化用户体验。
