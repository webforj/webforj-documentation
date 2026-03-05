---
title: Navigator
sidebar_position: 75
_i18n_hash: be5e20c3abb0d7b64b7a0eff03f7aded
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

`Navigator` 组件添加了用于导航数据集的分页控件。它可以显示第一页、最后一页、下一页和上一页按钮，以及页码或快速跳转字段，并在不适用时自动禁用控件。它绑定到一个 `Paginator` 实例以管理底层的分页逻辑。

<!-- INTRO_END -->

## 绑定到存储库 {#binding-to-repositories}

通常，`Navigator` 组件显示在绑定的 `Repository` 中找到的信息。此绑定使 `Navigator` 能够自动分页由存储库管理的数据，并根据导航的数据刷新其他可绑定组件，例如表格。

为此，只需将所需的 `Repository` 对象传递给适用的 `Navigator` 对象的构造函数：

<ComponentDemo 
path='/webforj/navigatortable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java'
height='475px'
/>

此示例创建了 `Navigator` 和 [`Table`](table/overview)，它们使用相同的 `Repository` 实例。这意味着当通过 `Navigator` 导航到新页面时，[`Table`](table/overview) 会识别此变化并重新渲染。

## 分页 {#pagination}

`Navigator` 组件与 `Paginator` 模型类密切相关，计算分页元数据，例如总页数、当前页面上项目的起始/结束索引，以及用于导航的页码数组。

虽然不是绝对必要，但利用 `Paginator` 启用导航背后的逻辑。当与 `Paginator` 集成时，导航器会响应 `Paginator` 中的任何更改。`Navigator` 对象可以通过使用 `getPaginator()` 方法访问内置的 `Paginator`。它还可以通过 `setPaginator()` 方法接受一个 `Paginator` 实例，或利用其中一个适用的构造函数。

本节包含实际代码片段，以说明此集成在实践中的工作方式。

### 项目 {#items}

术语“项目”表示单个分页元素或数据条目。这些可以是记录、条目或数据集中的任何离散单元。您可以使用 `setTotalItems()` 方法设置项目的总数。

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
与 `Paginator` 实例关联的存储库直接管理由存储库管理的总项目数，不能直接设置。
:::

### 最大页面数 {#maximum-pages}

`setMax()` 方法允许您定义在分页导航中显示的最大页面链接数。这在处理大量页面时特别有用，因为它控制用户在任何给定时间可见的页面链接数量。

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='/webforj/navigatorpages?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java'
height='125px'
/>

该程序在 `Navigator` 上显示最多五个页面，使用 `getPaginator()` 方法检索与 `Navigator` 对象关联的 `Paginator`，然后使用 `setMax()` 方法指定所需的最大显示页面数。

### 页面大小 {#page-size}

`setSize()` 方法允许您指定在每个分页页面上显示的项目数量。当您调用此方法并提供新的页面大小时，它会相应地调整分页。

```java
navigator.getPaginator().setSize(pageSize);
```

## 自定义按钮、文本和工具提示 {#customizing-buttons-text-and-tooltips}

`Navigator` 组件提供了丰富的按钮、文本和工具提示的自定义选项。要更改 `Navigator` 组件上显示的文本，请使用 `setText()` 方法。此方法接受文本以及所需的 `Part` 的 `Navigator`。

在以下示例中，`setText()` 方法向用户显示数值。单击按钮会触发 `Navigator` 的 `onChange` 方法，该方法附带了被单击按钮的 `Direction` 值。

<ComponentDemo 
path='/webforj/navigatorbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java'
height='100px'
/>

### 按钮和组件文本 {#buttons-and-component-text}

`setText()` 方法将文本参数评估为使用以下参数的 JavaScript 表达式：

- `page` - 当前页面编号
- `current` - 当前选定的页面编号
- `x` - 当前页面的别名
- `startIndex` - 当前页面的起始索引。
- `endIndex` - 当前页面的结束索引。
- `totalItems` - 项目的总数。
- `startPage` - 起始页码。
- `endPage` - 结束页码。
- `component` - `Navigator` 客户端组件。

<!-- vale off -->
例如，要将 `Navigator` 中最后一页按钮的文本设置为“前往第 10 页”，可以使用以下代码片段：
<!-- vale on -->

```java
navigator.setText("'Go to page ' + endPage", Navigator.Part.LAST_BUTTON);
```

### 工具提示文本 {#tooltip-text}

您可以使用 `setTooltipText()` 方法自定义 `Navigator` 组件各部分的工具提示。工具提示在用户悬停在导航元素上时提供有用的提示。

:::info
工具提示文本不会像 `setText()` 方法使用的文本那样评估为 JavaScript。
:::

<!-- vale off -->
例如，要将 `Navigator` 中最后一页按钮的工具提示文本设置为“前往最后一页”，可以使用以下代码片段：
<!-- vale on -->

```java
navigator.setTooltipText("Go to the last page", Navigator.Part.LAST_BUTTON);
```

## 布局 {#layouts}

`Navigator` 组件有多种布局选项，以提供灵活的分页控件显示。要访问这些布局，请使用 `Navigator.Layout` 枚举的值。选项如下：

<ComponentDemo 
path='/webforj/navigatorlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java'
height='200px'
/>

### 1. 无布局 {#1-none-layout}

`NONE` 布局在 `Navigator` 中不会呈现任何文本，仅显示导航按钮而不提供默认的文本显示。要激活此布局，请使用：

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. 编号布局 {#2-numbered-layout}

编号布局在 `Navigator` 的显示区域内显示对应于每个页面的编号芯片。使用此布局非常适合用户希望直接导航到特定页面的场景。要激活此布局，请使用：

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. 预览布局 {#3-preview-layout}

预览布局显示当前页码和总页数，适合具有有限空间的紧凑分页接口。

:::info
预览是默认的 `Navigator` 布局。
:::

要激活此布局，请使用：

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. 快速跳转布局 {#4-quick-jump-layout}

快速跳转布局为用户提供一个 [NumberField](./fields/number-field.md)，以输入页码进行快速导航。当用户需要快速导航到特定页面时，这对于大型数据集特别有用。要激活此布局，请使用：

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## 样式 {#styling}

<TableBuilder name="Navigator" />

## 最佳实践 {#best-practices}

为了确保在使用 `Navigator` 组件时获得最佳用户体验，请考虑以下最佳实践：

- **了解数据集**：在将 `Navigator` 组件集成到您的应用程序中之前，彻底了解用户的数据浏览需求。考虑数据集的大小、典型用户交互和偏好的导航模式等因素。

- **选择适当的布局**：为 `Navigator` 组件选择符合用户体验目标和可用屏幕空间的布局。

- **自定义文本和工具提示**：自定义 `Navigator` 组件的文本和工具提示，以匹配您应用中的语言和术语。提供描述性标签和有帮助的提示，帮助用户有效地浏览数据集。
