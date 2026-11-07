---
sidebar_position: 15
title: ListBox
slug: listbox
description: >-
  Show a scrollable, always-visible list with the ListBox component, supporting
  single or multiple selection and keyboard navigation.
_i18n_hash: 0a9d307607ef33fdebb557f190abd7cf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

`ListBox` 组件会显示一个可滚动的项目列表，无需打开下拉菜单即可始终保持可见。它支持单选和多选，适用于用户需要一次查看所有可用选项的场景。

<!-- INTRO_END -->

## 使用场景 {#usages}

<ParentLink parent="List" />

1. **用户角色分配**：在具有用户访问控制功能的应用程序中，管理员可以使用 `ListBox` 为用户分配角色和权限。用户从列表中进行选择，系统根据其选择分配相应的角色或权限。这样可以精确、可控地管理用户对应用程序中不同功能和数据的访问权限。

2. **项目任务分配**：在项目管理软件中，`ListBox` 组件可用于将任务分配给团队成员。用户可以从列表中选择任务，并将其分配给不同的团队成员。这简化了任务委派，并确保团队内的职责分工清晰明确。

3. **多类别筛选**：在搜索应用程序中，用户通常需要根据多个条件筛选搜索结果。`ListBox` 可以显示各种筛选选项，例如：
>- 产品特性
>- 价格范围
>- 品牌。

  用户可以从每个筛选类别中选择项目，从而缩小搜索范围，准确找到所需内容。

4. **内容分类**：在内容管理系统中，`ListBox` 组件可以帮助用户为文章、图片或文件分类。用户可以选择一个或多个类别与内容关联，从而更轻松地整理和搜索系统中的内容。

## 选择选项 {#selection-options}

默认情况下，列表框配置为每次只能选择一个项目。不过，`ListBox` 实现了 <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink> 接口，可以通过内置方法进行配置，让用户使用 ***<kbd>Shift</kbd> 键*** 选择连续的项目，或使用 ***<kbd>Ctrl</kbd>（Windows）或 <kbd>Cmd</kbd>（Mac）键*** 选择多个不连续的项目。

使用 <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> 函数更改此属性。此方法接受 `SelectionMode.SINGLE` 或 `SelectionMode.MULTIPLE`。

:::info 触摸设备行为
在触摸设备上启用多选后，用户无需按住 Shift 键即可选择多个项目。
:::

此外，用户可以使用方向键浏览 `ListBox`。当 `ListBox` 获得焦点时，按下字母键会选中以该字母开头的选项；如果存在多个以该字母开头的选项，则会在这些选项之间循环切换。

<ComponentDemo
path='/webforj/listboxmultipleselection'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java']}
height='250px'
/>

## 样式 {#styling}

<TableBuilder name="ListBox" />

## 最佳实践 {#best-practices}

为确保使用 `ChoiceBox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **优先考虑信息层次结构**：使用 `ListBox` 时，请确保项目按逻辑顺序和层次结构排列。将最重要、最常用的选项放在列表顶部。这样用户无需过多滚动就能找到所需内容。

2. **限制列表长度**：避免使用过长的 `ListBox`，以免给用户造成负担。如果要显示大量项目，可以考虑实现分页、搜索或筛选功能，帮助用户快速找到项目。或者，也可以将项目分组，以缩短列表。

3. **使用清晰且描述明确的标签**：为 `ListBox` 中的每个项目提供清晰且描述明确的标签。用户应能毫无歧义地理解每个选项的用途。标签应简洁且有意义。

4. **提供多选反馈**：如果 `ListBox` 支持多选，请通过视觉或文本反馈明确告知用户可以从列表中选择多个项目。

5. **设置默认选项**：考虑为 `ListBox` 设置默认选项，尤其是在某个选项比其他选项更常用时。预先选中最有可能的选项，可以让用户更轻松地完成操作。
