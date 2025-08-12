---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: 9bf580ccd6532be7fc66e2825d083724
---
# 富内容和客户端渲染

webforJ 中的表格也可以使用以下工具进行配置，以在单元格内显示富内容。这包括表格单元格内的交互式组件或格式化数据。

这些元素在客户端渲染，意味着生成和显示富内容的过程是在浏览器中直接完成的，使用 JavaScript 仅在需要时进行，从而提高使用 `Table` 的应用程序的性能。

## Lodash 渲染器 {#lodash-renderers}

渲染器提供了一种强大的机制，用于自定义数据在 `Table` 中的显示方式。主要类 `Renderer` 旨在能够扩展，以基于 lodash 模板创建自定义渲染器，从而实现动态和交互式内容渲染。

Lodash 模板能够直接将 HTML 插入到表格单元格中，使其非常有效地渲染 `Table` 中复杂的单元格数据。这种方法允许根据单元格数据动态生成 HTML，促进丰富和交互式表格单元内容的实现。

### Lodash 语法 {#lodash-syntax}

以下部分概述了 Lodash 语法的基本知识。虽然这不是一个详尽或全面的概述，但可以帮助您开始在 `Table` 组件中使用 Lodash。

#### Lodash 模板的语法概述: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - 插值值，将 JavaScript 代码的结果插入到模板中。
- `<% ... %>` - 执行 JavaScript 代码，允许循环、条件判断等。
- `<%- ... %>` - 转义 HTML 内容，确保插值数据不受 HTML 注入攻击影响。

#### 使用单元格数据的示例: {#examples-using-cell-data}

**1. 简单值插值**: 直接显示单元格的值。

`<%= cell.value %>`

**2. 条件渲染**: 使用 JavaScript 逻辑有条件地渲染内容。

`<% if (cell.value > 100) { %> '高' <% } else { %> '正常' <% } %>`

**3. 组合数据字段**: 使用来自单元格的多个数据字段渲染内容。

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. 转义 HTML 内容**: 安全地渲染用户生成的内容。

渲染器在客户端访问详细的单元格、行和列属性：

**TableCell 属性:**

|属性	|类型	|描述|
|-|-|-|
|column|`TableColumn`|相关的列对象。|
|first|`boolean`|指示单元格是否为行中的第一个。|
|id|`String`|单元格 ID。|
|index|`int`|单元格在其行中的索引。|
|last|`boolean`|指示单元格是否为行中的最后一个。|
|row|`TableRow`|与单元格相关的行对象。|
|value|`Object`|单元格的原始值，直接来自数据源。|

**TableRow 属性:**

|属性|类型|描述|
|-|-|-|
|cells|`TableCell[]`|行中的单元格。|
|data|`Object`|应用程序提供的行数据。|
|even|`boolean`|指示行是否为偶数行（用于样式目的）。|
|first|`boolean`|指示行是否为表中的第一行。|
|id|`String`|此行的唯一 ID。|
|index|`int`|行索引。|
|last|`boolean`|指示行是否为表中的最后一行。|
|odd|`boolean`|指示行是否为奇数行（用于样式目的）。|

**TableColumn 属性:**

|属性	|类型	|描述|
|-|-|-|
|align|ColumnAlignment|列的对齐方式（左、中、右）。|
|id|String|从中获取单元格数据的行对象字段。|
|label|String|在列标题中渲染的名称。|
|pinned|ColumnPinDirection|列的固定方向（左、右、自动）。|
|sortable|boolean|如果为真，则可以对列进行排序。|
|sort|SortDirection|列的排序顺序。|
|type|ColumnType|列的类型（文本、数字、布尔值等）。|
|minWidth|number|列的最小宽度（以像素为单位）。|

## 可用渲染器 {#available-renderers}

虽然可以创建自定义渲染器，但在 `Table` 中有多种预配置的渲染器可供使用。以下是开发人员可以即用即用的，您无需创建自定义渲染器：

>- `ButtonRenderer` - webforJ 按钮的渲染器。
>- `NativeButtonRenderer` - 原生 HTML 按钮的渲染器。
>- `ElementRenderer` - 渲染带内容的 HTML 标签的所有渲染器的基类。
>- `VoidElementRenderer` - 渲染不带内容的 HTML 标签的所有渲染器的基类。
>- `IconRenderer` - 图标渲染器 - **[请参见此](../../components/icon)** 文章以获取更多有关图标的信息。

渲染器还允许通过扩展任何支持的基渲染器编写自定义事件。目前，渲染器提供了一个供开发人员使用的 `RendererClickEvent`。

下面是一个使用渲染器显示富内容的 `Table` 示例：

<ComponentDemo 
path='/webforj/tablerichcontent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRichContentView.java'
cssURL='/css/table/tableRichContent.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
