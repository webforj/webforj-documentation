---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 7bd48c55ca5607255c1d6503c500a25d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

<ParentLink parent="List" />

`ListBox` 组件是一个用户界面元素，旨在显示可滚动的对象列表，并允许用户从中选择单个或多个项目。用户还可以使用箭头键与 `ListBox` 进行交互。

## 用法 {#usages}

1. **用户角色分配**：在具有用户访问控制的应用程序中，管理员可以使用 `ListBox` 为用户分配角色和权限。用户从列表中选择，角色或权限根据其选择进行分配。这确保了对应用程序内不同功能和数据的精确和控制访问。

2. **项目任务分配**：在项目管理软件中，`ListBox` 组件用于将任务分配给团队成员。用户可以从列表中选择任务，并将其分配给不同的团队成员。这简化了任务委派，确保团队内的职责明确。

3. **多类别过滤**：在搜索应用程序中，用户通常需要根据多个标准过滤搜索结果。`ListBox` 可以显示各种过滤选项，例如
>- 产品特征
>- 价格范围
>- 品牌。

  用户可以从每个过滤类别中选择项目，从而精炼搜索结果，找到他们所需的确切内容。

4. **内容分类**：在内容管理系统中，`ListBox` 组件帮助分类文章、图像或文件。用户可以选择一个或多个类别与其内容关联，使得组织和搜索系统中的内容项变得更加容易。

## 选择选项 {#selection-options}

默认情况下，列表框配置为一次只能选择一个项目。然而，`ListBox` 实现了 <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink> 接口，可以通过内置方法配置，允许用户使用 ***`Shift` 键*** 进行连续项选择，***`Control` (Windows) 或 `Command` (Mac) 键*** 进行分开的多项选择。

使用 <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> 函数更改该属性。此方法接受 `SelectionMode.SINGLE` 或 `SelectionMode.MULTIPLE`。

:::info 触摸设备行为
在启用多重选择的触摸设备上，用户可以在不按住 Shift 键的情况下选择多个项目。
:::

此外，箭头键可用于导航 `ListBox`，并且在 `ListBox` 处于焦点时输入字母键将选择以该字母开头的选项，或者在存在多个选项的情况下循环选择以该字母开头的选项。

<ComponentDemo 
path='/webforj/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
height = '250px'
/>

## 样式 {#styling}

<TableBuilder name="ListBox" />

## 最佳实践 {#best-practices}

为确保在使用 `ChoiceBox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **优先考虑信息层级**：使用 `ListBox` 时，确保项目按逻辑和层级顺序组织。将最重要和常用的选项放在列表的顶部。这使得用户更容易找到所需内容，而无需过多滚动。

2. **限制列表长度**：避免让用户感到不知所措的过长 `ListBox`。如果有大量项目要显示，请考虑实施分页、搜索或过滤选项，以帮助用户快速定位项目。或者，您可以将项目分组到类别中以减少列表长度。

3. **清晰和描述性标签**：为 `ListBox` 中的每个项目提供清晰和描述性的标签。用户应该能够毫不含糊地理解每个选项的目的。使用简明而有意义的项目标签。

4. **多选反馈**：如果您的 `ListBox` 允许进行多项选择，请提供视觉或文本反馈，指示可以从列表中选择多个项目。

5. **默认选择**：考虑为 `ListBox` 设置默认选择，尤其是当一个选项比其他选项更常用时。这可以通过预先选择最可能的选择来简化用户体验。
