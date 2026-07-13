---
title: Navigator
sidebar_position: 75
description: >-
  Add pagination controls with the Navigator component, binding to a Paginator
  or Repository to drive page size, navigation, and labels.
_i18n_hash: 1223e167b76000411cd73c4bbbbda3d5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

`Navigator` 组件添加了用于在数据集之间导航的分页控件。它可以显示第一页、最后一页、下一页和上一页按钮以及页码或快速跳转字段，并在不适用时自动禁用控件。它绑定到 `Paginator` 实例以管理底层分页逻辑。

<!-- INTRO_END -->

## 绑定到存储库 {#binding-to-repositories}

通常，`Navigator` 组件显示在绑定 `Repository` 中找到的信息。此绑定使 `Navigator` 能够自动对由存储库管理的数据进行分页，并根据导航数据刷新其他可绑定的组件，如表格。

为此，只需将所需的 `Repository` 对象传递给适用的 `Navigator` 对象的构造函数：

<ComponentDemo
path='/webforj/navigatortable'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java']}
height='475px'
/>

此示例创建了与同一 `Repository` 实例关联的 `Navigator` 和 [`Table`](table/overview)。这意味着当使用 `Navigator` 导航到新页面时，[`Table`](table/overview) 会识别此变化并重新渲染。

## 分页 {#pagination}

`Navigator` 组件与 `Paginator` 模型类紧密相关，计算分页元数据，如总页数、当前页的项目开始/结束索引以及用于导航的页码数组。

虽然不是绝对必要，但使用 `Paginator` 使导航背后的逻辑变得更加明确。在与 `Paginator` 集成时，导航器会对 `Paginator` 中的任何更改做出响应。`Navigator` 对象可以通过使用 `getPaginator()` 方法访问内置的 `Paginator`，它还可以通过 `setPaginator()` 方法或使用其中一个适用的构造函数接受 `Paginator` 实例。

本节包括实用代码片段，以说明此集成在实际中的工作原理。

### 项目 {#items}

“项目”一词指的是单个分页元素或数据条目。这些可以是记录、条目或数据集中任何离散单元。您可以使用 `setTotalItems()` 方法设置项目的总数。

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
与 `Paginator` 实例关联的存储库具有直接由存储库管理的总项目数，而不能直接设置。
:::

### 最大页数 {#maximum-pages}

`setMax()` 方法允许您定义在分页导航中显示的最大页面链接数。当处理大量页面时，这特别有用，因为它控制用户在任何给定时间看到的页面链接数。

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo
path='/webforj/navigatorpages'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java']}
height='125px'
/>

此程序在 `Navigator` 上一次最多显示五个页面，使用 `getPaginator()` 方法检索与 `Navigator` 对象关联的 `Paginator`，然后使用 `setMax()` 方法指定所需的最大页数。

### 页面大小 {#page-size}

`setSize()` 方法允许您指定在每个分页页上显示的项目数。当您调用此方法并提供一个新的页面大小时，它会相应调整分页。

```java
navigator.getPaginator().setSize(pageSize);
```

## 自定义按钮、文本和工具提示 {#customizing-buttons-text-and-tooltips}

`Navigator` 组件提供广泛的按钮、文本和工具提示的自定义选项。要更改 `Navigator` 组件上显示的文本，请使用 `setText()` 方法。此方法接受文本以及所需的 `Navigator` 部分。

在以下示例中，`setText()` 方法向用户显示一个数字值。单击按钮会触发 `Navigator` 的 `onChange` 方法，该方法带有单击按钮的 `Direction` 值。

<ComponentDemo
path='/webforj/navigatorbasic'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java']}
height='100px'
/>

### 按钮和组件文本 {#buttons-and-component-text}

`setText()` 方法使用以下参数将文本参数作为 JavaScript 表达式进行评估：

- `page` - 当前页码
- `current` - 当前选定的页码
- `x` - 当前页的别名
- `startIndex` - 当前页的开始索引。
- `endIndex` - 当前页的结束索引。
- `totalItems` - 项目的总数。
- `startPage` - 开始页码。
- `endPage` - 结束页码。
- `component` - Navigator 客户端组件。

<!-- vale off -->
例如，要在具有 10 页的 `Navigator` 中将最后一页按钮的文本设置为“跳转到第 10 页”，请使用以下代码片段：
<!-- vale on -->

```java
navigator.setText("'Go to page ' + endPage", Navigator.Part.LAST_BUTTON);
```

### 工具提示文本 {#tooltip-text}

您可以使用 `setTooltipText()` 方法自定义 `Navigator` 组件各个部分的工具提示。当用户将鼠标悬停在导航元素上时，工具提示提供有用的提示。

:::info
工具提示文本不会像 `setText()` 方法使用的文本那样评估为 JavaScript。
:::

<!-- vale off -->
例如，要将 `Navigator` 中最后一页按钮的工具提示文本设置为“跳转到最后一页”，请使用以下代码片段：
<!-- vale on -->

```java
navigator.setTooltipText("Go to the last page", Navigator.Part.LAST_BUTTON);
```

## 布局 {#layouts}

`Navigator` 组件有多种布局选项，以提供在显示分页控件时的灵活性。要访问这些布局，请使用 `Navigator.Layout` 枚举的值。选项如下：

<ComponentDemo
path='/webforj/navigatorlayout'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java']}
height='200px'
/>

### 1. 无布局 {#1-none-layout}

`NONE` 布局在 `Navigator` 内部不渲染文本，仅显示导航按钮而没有默认的文本显示。要激活此布局，请使用：

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. 编号布局 {#2-numbered-layout}

编号布局在 `Navigator` 的显示区域内显示与每一页相对应的编号芯片。使用此布局非常适合用户希望直接导航到特定页面的场景。要激活此布局，请使用：

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. 预览布局 {#3-preview-layout}

预览布局显示当前页码和总页数，适合具有有限空间的紧凑型分页接口。

:::info
预览是默认的 `Navigator` 布局。
:::

要激活此布局，请使用：

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. 快速跳转布局 {#4-quick-jump-layout}

快速跳转布局为用户提供一个[NumberField](./fields/number-field.md)，以便用户输入页码进行快速导航。当用户需要快速导航到特定页面时，此功能尤为实用，特别是对于大型数据集。要激活此布局，请使用：

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## 样式 {#styling}

<TableBuilder name="Navigator" />

## 最佳实践 {#best-practices}

为确保在使用 `Navigator` 组件时获得最佳用户体验，请考虑以下最佳实践：

- **了解数据集**：在将 `Navigator` 组件集成到您的应用中之前，深入了解用户的数据浏览需求。考虑数据集的大小、典型用户交互和首选导航模式等因素。

- **选择适当的布局**：为 `Navigator` 组件选择一个与用户体验目标和可用屏幕面积相匹配的布局。

- **自定义文本和工具提示**：自定义 `Navigator` 组件的文本和工具提示，以匹配您应用程序中使用的语言和术语。提供描述性标签和有用提示，以有效帮助用户导航数据集。
