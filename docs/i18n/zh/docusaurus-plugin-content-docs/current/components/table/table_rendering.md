---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: 12791f1583592c6f000a45b74e52ba65
---
# 富内容和客户端渲染

webforJ 中的表格也可以使用以下工具进行配置，以在单元格内显示丰富的内容。这包括在表格单元格内的交互式组件或格式化数据。

这些元素在客户端渲染，这意味着生成和显示富内容的过程直接在浏览器中完成，仅在必要时使用 JavaScript，从而提高使用 `Table` 的应用程序的性能。

## Lodash 渲染器 {#lodash-renderers}

渲染器提供了一个强大的机制，用于自定义在 `Table` 中显示数据的方式。主要类 `Renderer` 旨在扩展以基于 lodash 模板创建自定义渲染器，从而实现动态和交互式内容渲染。

Lodash 模板允许将 HTML 直接插入表格单元格，使其在渲染复杂单元格数据时非常有效。这种方法允许基于单元格数据动态生成 HTML，从而促进了丰富和交互式的表格单元格内容。

### Lodash 语法 {#lodash-syntax}

以下部分概述了 Lodash 语法的基础知识。虽然这不是全面的概述，但可以用于启动在 `Table` 组件中使用 Lodash。

#### Lodash 模板的语法概述: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - 插入值，将 JavaScript 代码的结果插入模板中。
- `<% ... %>` - 执行 JavaScript 代码，允许循环、条件等。
- `<%- ... %>` - 转义 HTML 内容，确保插入的数据不会受到 HTML 注入攻击的影响。

#### 使用单元格数据的示例: {#examples-using-cell-data}

**1. 简单值插入**: 直接显示单元格的值。

`<%= cell.value %>`

**2. 条件渲染**: 使用 JavaScript 逻辑条件性地渲染内容。

`<% if (cell.value > 100) { %> '高' <% } else { %> '正常' <% } %>`

**3. 组合数据字段**: 使用单元格中的多个数据字段渲染内容。

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. 转义 HTML 内容**: 安全地渲染用户生成的内容。

渲染器可以访问详细的单元格、行和列属性在客户端：

**TableCell 属性:**

| 属性        | 类型                | 描述                           |
|------------|--------------------|-------------------------------|
| column     | `TableColumn`      | 关联的列对象。                |
| first      | `boolean`          | 指示该单元格是否是行中的第一个。|
| id         | `String`           | 单元格 ID。                   |
| index      | `int`              | 单元格在其行中的索引。        |
| last       | `boolean`          | 指示该单元格是否是行中的最后一个。 |
| row        | `TableRow`        | 与单元格关联的行对象。        |
| value      | `Object`           | 单元格的原始值，来自数据源。  |

**TableRow 属性:**

| 属性        | 类型                | 描述                           |
|------------|--------------------|-------------------------------|
| cells      | `TableCell[]`      | 行内的单元格。              |
| data       | `Object`           | 应用程序为行提供的数据。      |
| even       | `boolean`          | 指示行是否是偶数行（用于样式目的）。 |
| first      | `boolean`          | 指示该行是否是表格中的第一行。|
| id         | `String`           | 行的唯一 ID。                 |
| index      | `int`              | 行索引。                     |
| last       | `boolean`          | 指示该行是否是表格中的最后一行。 |
| odd        | `boolean`          | 指示行是否是奇数行（用于样式目的）。 |

**TableColumn 属性:**

| 属性        | 类型                | 描述                           |
|------------|--------------------|-------------------------------|
| align      | ColumnAlignment     | 列的对齐方式（左、居中、右）。 |
| id         | String             | 获取单元格数据的行对象字段。   |
| label      | String             | 在列头中渲染的名称。          |
| pinned     | ColumnPinDirection  | 列的钉住方向（左、右、自动）。  |
| sortable    | boolean            | 如果为 true，则可以对该列进行排序。 |
| sort       | SortDirection      | 列的排序顺序。                |
| type       | ColumnType         | 列的类型（文本、数字、布尔等）。 |
| minWidth   | number             | 列的最小宽度（以像素为单位）。  |

## 可用的渲染器 {#available-renderers}

虽然可以创建自定义渲染器，但有多个预配置的渲染器可供在 `Table` 中使用。以下是开发人员可以直接使用的可用渲染器，无需创建自定义渲染器：

>- `ButtonRenderer` - webforJ 按钮的渲染器。
>- `NativeButtonRenderer` - 原生 HTML 按钮的渲染器。
>- `ElementRenderer` - 所有渲染器的基类，用于渲染带内容的 HTML 标签。
>- `VoidElementRenderer` - 所有渲染器的基类，用于渲染无内容的元素，或没有内容的 HTML 标签。
>- `IconRenderer` - 图标的渲染器 - **[查看此](../../components/icon)** 文章以获取有关图标的更多信息。

渲染器还允许通过扩展任何支持的基渲染器来编写自定义事件。目前，渲染器配备了一个 `RendererClickEvent`，供开发人员使用。

以下是一个使用渲染器显示丰富内容的 `Table` 示例：

<ComponentDemo 
path='/webforj/tablerichcontent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRichContentView.java'
cssURL='/css/table/tableRichContent.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
