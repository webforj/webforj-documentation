---
title: Navigator
sidebar_position: 75
_i18n_hash: 920c1d604673e69a32f58161e3fd4e14
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

`Navigator` 组件是一个可定制的分页组件，用于在数据集之间进行导航，支持多种布局。您可以配置它以显示各种导航控件，例如第一页、最后一页、下一页和上一页的按钮，以及根据布局设置的页码或快速跳转字段。

它支持根据当前页面和总项目自动禁用导航按钮，并为导航器的不同部分提供文本和工具提示的定制选项。此外，您可以将其绑定到 `Paginator` 实例，以管理数据集的分页逻辑，并反映导航控件中的更改。

## 绑定到仓储 {#binding-to-repositories}

通常，`Navigator` 组件显示的信息来自于绑定的 `Repository`。这种绑定使 `Navigator` 能够自动分页由仓储管理的数据，并根据遍历的数据刷新其他可绑定的组件，例如表格。

为此，只需将所需的 `Repository` 对象传递给适用的 `Navigator` 对象的构造函数：

<ComponentDemo 
path='/webforj/navigatortable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java'
height='475px'
/>

这个示例创建了具有相同 `Repository` 实例的 `Navigator` 和 [`Table`](table/overview)。这意味着当通过 `Navigator` 导航到新页面时，[`Table`](table/overview) 会识别到这一变化并重新渲染。

## 分页 {#pagination}

`Navigator` 组件与 `Paginator` 模型类紧密相连，计算分页元数据，例如总页数、当前页的项目起始/结束索引，以及用于导航的页码数组。

虽然不是绝对必要，使用 `Paginator` 会增强导航背后的逻辑。当与 `Paginator` 集成时，导航器会对 `Paginator` 中的任何更改作出响应。`Navigator` 对象通过使用 `getPaginator()` 方法访问内置的 `Paginator`。它还可以通过 `setPaginator()` 方法接受一个 `Paginator` 实例，或者利用其中一个适用的构造函数。

本节包含实用的代码片段，说明这种集成在实践中是如何工作的。

### 项目 {#items}

“项目”一词表示单个分页元素或数据条目。这些可以是记录、条目或数据集中的任何离散单位。您可以使用 `setTotalItems()` 方法设置项目的总数。

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
与 `Paginator` 实例关联的仓储具有由仓储直接管理的总项目数，并且不能直接设置。
:::

### 最大页数 {#maximum-pages}

`setMax()` 方法允许您定义要在分页导航中显示的最大页面链接数。这在处理大量页面时特别有用，因为它可控制用户在任何给定时间可见的页面链接数量。

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='/webforj/navigatorpages?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java'
height='125px'
/>

此程序在 `Navigator` 上一次最多显示五个页面，通过使用 `getPaginator()` 方法检索与 `Navigator` 对象关联的 `Paginator`，然后使用 `setMax()` 方法指定要显示的最大页面数量。

### 页面大小 {#page-size}

`setSize()` 方法允许您指定要在分页的每个页面上显示的项目数量。当您调用此方法并提供新的页面大小时，它会相应地调整分页。

```java
navigator.getPaginator().setSize(pageSize);
```

## 自定义按钮、文本和工具提示 {#customizing-buttons-text-and-tooltips}

`Navigator` 组件提供广泛的按钮、文本和工具提示的自定义选项。要更改 `Navigator` 组件上显示的文本，请使用 `setText()` 方法。此方法接受文本以及 `Navigator` 的所需 `Part`。

在以下示例中，`setText()` 方法向用户显示数字值。点击按钮触发 `Navigator` 的 `onChange` 方法，该方法附带点击按钮的 `Direction` 值。

<ComponentDemo 
path='/webforj/navigatorbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java'
height='100px'
/>

### 按钮和组件文本 {#buttons-and-component-text}

`setText()` 方法将文本参数作为 JavaScript 表达式进行评估，使用以下参数：

- `page` - 当前页码
- `current` - 当前选定页码
- `x` - 当前页的别名
- `startIndex` - 当前页的起始索引。
- `endIndex` - 当前页的结束索引。
- `totalItems` - 项目的总数。
- `startPage` - 起始页码。
- `endPage` - 结束页码。
- `component` - Navigator 客户端组件。

<!-- vale off -->
例如，要将 `Navigator` 中最后一页按钮的文本设置为“跳转到第10页”，可以使用以下代码片段：
<!-- vale on -->

```java
navigator.setText("'Go to page ' + endPage", Navigator.Part.LAST_BUTTON);
```

### 工具提示文本 {#tooltip-text}

您可以使用 `setTooltipText()` 方法自定义 `Navigator` 组件各部分的工具提示。当用户悬停在导航元素上时，工具提示会向用户提供有用的提示。

:::info
工具提示文本不会被评估为 JavaScript，区别于 `setText()` 方法使用的文本。
:::

<!-- vale off -->
例如，要将 `Navigator` 中最后一页按钮的工具提示文本设置为“跳转到最后一页”，可以使用以下代码片段：
<!-- vale on -->

```java
navigator.setTooltipText("Go to the last page", Navigator.Part.LAST_BUTTON);
```

## 布局 {#layouts}

`Navigator` 组件有多种布局选项，以便提供灵活性来显示分页控件。要访问这些布局，请使用 `Navigator.Layout` 枚举的值。选项如下：

<ComponentDemo 
path='/webforj/navigatorlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java'
height='200px'
/>

### 1. 无布局 {#1-none-layout}

`NONE` 布局在 `Navigator` 中不渲染文本，仅显示导航按钮，而没有默认文本显示。要激活此布局，请使用：

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. 编号布局 {#2-numbered-layout}

编号布局在 `Navigator` 的显示区域内显示与每个页面对应的编号筹码。使用此布局在用户希望直接导航到特定页面的场景中非常理想。要激活此布局，请使用：

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. 预览布局 {#3-preview-layout}

预览布局显示当前页码和总页数，适用于空间有限的紧凑分页界面。

:::info
预览是默认的 `Navigator` 布局。
:::

要激活此布局，请使用：

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. 快速跳转布局 {#4-quick-jump-layout}

快速跳转布局为用户提供一个 [NumberField](./fields/number-field.md)，让他们输入页面编号以快速导航。当用户需要快速导航到特定页面时，这种布局特别有用，尤其是在处理大型数据集时。要激活此布局，请使用：

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## 样式 {#styling}

<TableBuilder name="Navigator" />

## 最佳实践 {#best-practices}

为了确保在使用 `Navigator` 组件时提供最佳用户体验，请考虑以下最佳实践：

- **了解数据集**：在将 `Navigator` 组件集成到应用程序之前，彻底了解用户的数据浏览需求。考虑数据集的大小、典型用户交互和首选导航模式等因素。

- **选择合适的布局**：选择与用户体验目标和可用屏幕 real estate 对齐的 `Navigator` 组件的布局。

- **自定义文本和工具提示**：自定义 `Navigator` 组件的文本和工具提示，以匹配您应用中使用的语言和术语。提供描述性标签和有用的提示，以帮助用户有效地浏览数据集。
