---
title: Navigator
sidebar_position: 75
_i18n_hash: 7c09a72456eb84a8227da3ff263b6c69
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

`Navigator` 组件是一个可自定义的分页组件，旨在在数据集之间进行导航，支持多种布局。您可以配置它以显示各种导航控件，例如第一个、最后一个、下一个和上一个按钮，以及页面编号或快速跳转字段，具体取决于布局设置。

它支持根据当前页面和总项自动禁用导航按钮，并提供文本和工具提示的自定义选项，以适应导航器的不同部分。此外，您可以将其绑定到 `Paginator` 实例，以管理数据集的分页逻辑，并在导航控件中反映更改。

## 绑定到仓库 {#binding-to-repositories}

通常，`Navigator` 组件显示在绑定的 `Repository` 中找到的信息。这种绑定使 `Navigator` 能够自动对仓库管理的数据进行分页，并根据导航的数据刷新其他可绑定组件，例如表格。

要做到这一点，只需将所需的 `Repository` 对象传递给适用的 `Navigator` 对象的构造函数：

<ComponentDemo 
path='/webforj/navigatortable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java'
height='475px'
/>

此示例创建 `Navigator` 和 [`Table`](table/overview) ，使用相同的 `Repository` 实例。这意味着当使用 `Navigator` 导航到新页面时，[`Table`](table/overview) 能够识别此更改并重新渲染。

## 分页 {#pagination}

`Navigator` 组件与 `Paginator` 模型类紧密相连，计算分页元数据，例如总页数、当前页中项目的起始/结束索引，以及用于导航的页码数组。

虽然不是绝对必要，利用 `Paginator` 能够使导航背后的逻辑发挥作用。当与 `Paginator` 集成时，导航器会响应 `Paginator` 内的任何更改。`Navigator` 对象可以通过使用 `getPaginator()` 方法访问内置的 `Paginator`，也可以通过 `setPaginator()` 方法接受 `Paginator` 实例，或者利用适用的构造函数之一。

本节包括实用的代码片段，以说明这种集成在实践中的工作原理。

### 项目 {#items}

“项目”一词指的是分页的单个元素或数据条目。这些可以是记录、条目或数据集中任何离散单位。您可以使用 `setTotalItems()` 方法设置总项数。

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
与 `Paginator` 实例关联的仓库直接管理总项数，无法直接设置。
:::

### 最大页数 {#maximum-pages}

`setMax()` 方法允许您定义要在分页导航中显示的最大页面链接数。当处理大量页面时，这尤其有用，因为它控制任何时候用户可见的页面链接数量。

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='/webforj/navigatorpages?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java'
height='125px'
/>

该程序在 `Navigator` 上最多显示五个页面，通过使用 `getPaginator()` 方法检索与 `Navigator` 对象关联的 `Paginator`，然后使用 `setMax()` 方法指定希望显示的最大页面数。

### 页面大小 {#page-size}

`setSize()` 方法允许您指定在每个分页页面上显示的项目数量。当您调用此方法并提供新的页面大小时，它会相应地调整分页。

```java
navigator.getPaginator().setSize(pageSize);
```

## 自定义按钮、文本和工具提示 {#customizing-buttons-text-and-tooltips}

`Navigator` 组件提供了广泛的按钮、文本和工具提示的自定义选项。要更改 `Navigator` 组件上显示的文本，请使用 `setText()` 方法。该方法接受文本以及所需的 `Part` 的 `Navigator`。

在以下示例中，`setText()` 方法向用户显示一个数字值。点击按钮会触发 `Navigator` 的 `onChange` 方法，该方法带有被点击按钮的 `Direction` 值。

<ComponentDemo 
path='/webforj/navigatorbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java'
height='100px'
/>

### 按钮和组件文本 {#buttons-and-component-text}

`setText()` 方法使用以下参数将文本参数作为 JavaScript 表达式进行计算：

- `page` - 当前页码
- `current` - 当前所选页码
- `x` - 当前页的别名
- `startIndex` - 当前页面的起始索引。
- `endIndex` - 当前页面的结束索引。
- `totalItems` - 总项数。
- `startPage` - 起始页码。
- `endPage` - 结束页码。
- `component` - 导航器客户端组件。

<!-- vale off -->
例如，要将具有 10 页的 `Navigator` 中最后一页按钮的文本设置为“转到第 10 页”，请使用以下代码片段：
<!-- vale on -->

```java
navigator.setText("'Go to page ' + endPage", Navigator.Part.LAST_BUTTON);
```

### 工具提示文本 {#tooltip-text}

您可以使用 `setTooltipText()` 方法自定义 `Navigator` 组件不同部分的工具提示。工具提示在用户悬停于导航元素时提供有用提示。

:::info
工具提示文本不会像 `setText()` 方法中使用的文本那样被评估为 JavaScript。
:::

<!-- vale off -->
例如，要将 `Navigator` 中最后一页按钮的工具提示文本设置为“转到最后一页”，请使用以下代码片段：
<!-- vale on -->

```java
navigator.setTooltipText("Go to the last page", Navigator.Part.LAST_BUTTON);
```

## 布局 {#layouts}

`Navigator` 组件提供多种布局选项，以灵活显示分页控件。要访问这些布局，请使用 `Navigator.Layout` 枚举的值。选项如下：

<ComponentDemo 
path='/webforj/navigatorlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java'
height='200px'
/>

### 1. 无布局 {#1-none-layout}

`NONE` 布局在 `Navigator` 中不呈现任何文本，仅显示导航按钮，而没有默认的文本显示。要激活此布局，请使用：

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. 编号布局 {#2-numbered-layout}

编号布局在 `Navigator` 的显示区域内显示对应于每个页面的编号芯片。使用此布局非常适合用户更愿意直接导航到特定页面的情况。要激活此布局，请使用：

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. 预览布局 {#3-preview-layout}

预览布局显示当前页码和总页数，适合于空间有限的紧凑分页界面。

:::info
预览是默认的 `Navigator` 布局。
:::

要激活此布局，请使用：

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. 快速跳转布局 {#4-quick-jump-layout}

快速跳转布局提供一个 [NumberField](./fields/number-field.md) 让用户输入页码以快速导航。当用户需要快速导航到特定页面，尤其是对于大型数据集时，这非常有用。要激活此布局，请使用：

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## 样式 {#styling}

<TableBuilder name="Navigator" />

## 最佳实践 {#best-practices}

为了确保在使用 `Navigator` 组件时获得最佳用户体验，请考虑以下最佳实践：

- **了解数据集**：在将 `Navigator` 组件集成到您的应用程序之前，充分了解用户的数据浏览需求。考虑数据集的大小、典型的用户交互和首选的导航模式等因素。

- **选择适当的布局**：选择与用户体验目标和可用屏幕空间一致的 `Navigator` 组件布局。

- **自定义文本和工具提示**：自定义 `Navigator` 组件的文本和工具提示，以匹配您的应用中使用的语言和术语。提供描述性标签和有助于用户有效导航数据集的提示。
