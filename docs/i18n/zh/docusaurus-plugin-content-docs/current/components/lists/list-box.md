---
sidebar_position: 15
title: ListBox
slug: listbox
description: >-
  Show a scrollable, always-visible list with the ListBox component, supporting
  single or multiple selection and keyboard navigation.
_i18n_hash: ea83ed0b82b2f6f91d7fbe9dedebeef2
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

`ListBox` 组件显示一个可滚动的项目列表，无需打开下拉框即可保持可见。它支持单选和多选，在用户需要同时查看所有可用选项时效果良好。

<!-- INTRO_END -->

## 用法 {#usages}

<ParentLink parent="List" />

1. **用户角色分配**：在有用户访问控制的应用程序中，管理员可以使用 `ListBox` 来分配角色和权限给用户。用户从列表中选择，角色或权限根据他们的选择进行分配。这确保在应用程序内对不同功能和数据的精确和受控访问。

2. **项目任务分配**：在项目管理软件中，`ListBox` 组件有助于将任务分配给团队成员。用户可以从列表中选择任务并将其分配给不同的团队成员。这简化了任务委派，并确保团队内的责任清晰。

3. **多分类过滤**：在搜索应用中，用户通常需要根据多个标准过滤搜索结果。`ListBox` 可以显示各种过滤选项，例如
>- 产品特征
>- 价格范围
>- 品牌。

  用户可以从每个过滤类别中选择项目，从而帮助他们细化搜索结果，找到所需的确切内容。

4. **内容分类**：在内容管理系统中，`ListBox` 组件有助于对文章、图像或文件进行分类。用户可以选择一个或多个类别来与其内容关联，从而更容易组织和搜索系统中的内容项。

## 选择选项 {#selection-options}

默认情况下，列表框配置为只允许一次选择一个项目。然而，`ListBox` 实现了 <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink> 接口，可以通过内置方法进行配置以允许用户使用 ***`Shift` 键*** 进行连续选择和 ***`Control`（Windows）或 `Command`（Mac）键*** 进行独立多项选择。

使用 <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> 函数来更改此属性。此方法接受 `SelectionMode.SINGLE` 或 `SelectionMode.MULTIPLE`。

:::info 触摸设备行为
在触摸设备上，当启用多选时，用户可以在不按住 shift 键的情况下选择多个项目。
:::

此外，箭头键可用于导航 `ListBox`，而在 `ListBox` 获得焦点时按字母键将选择以该字母开头的选项，或者在存在多个以该字母开头的选项时循环选择。

<ComponentDemo
path='/webforj/listboxmultipleselection'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java']}
height='250px'
/>

## 样式 {#styling}

<TableBuilder name="ListBox" />

## 最佳实践 {#best-practices}

为了确保在使用 `ChoiceBox` 组件时用户体验最佳，请考虑以下最佳实践：

1. **优先考虑信息层次**：在使用 `ListBox` 时，请确保项目按逻辑和层次顺序组织。将最重要和最常用的选项放在列表顶部。这使用户更容易找到他们所需的内容，而不必过多滚动。

2. **限制列表长度**：避免用过长的 `ListBox` 让用户感到不知所措。如果有大量项目需要显示，请考虑实施分页、搜索或过滤选项，以帮助用户快速定位项目。或者，您可以将项目分组为类别以减少列表长度。

3. **清晰和描述性的标签**：为 `ListBox` 中的每个项目提供清晰和描述性的标签。用户应能够毫不含糊地理解每个选项的目的。使用简洁而有意义的项目标签。

4. **多选反馈**：如果您的 `ListBox` 允许多选，请提供视觉或文本反馈，指示可以从列表中选择多个项目。

5. **默认选择**：考虑为 `ListBox` 设置默认选择，特别是如果某个选项比其他选项更常用。这可以通过预选最可能的选择来简化用户体验。
