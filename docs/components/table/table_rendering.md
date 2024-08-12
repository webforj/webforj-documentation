---
sidebar_position: 4
title: Rendering
slug: rendering
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';

# Rich Content and Client-Side Rendering

Tables in webforj are also configurable using the following tools to display rich content within cells. This includes interactive components or formatted data within the table cells.

These elements are rendered client-side, meaning the process of generating and displaying rich content is done directly in the browser, using JavaScript only when needed, increasing performance of applications using the `Table`.

## Lodash Renderers

Renderers offer a powerful mechanism for customizing the way data is displayed within a `Table`. The primary class, `Renderer`, is designed to be extended to create custom renderers based on lodash templates, enabling dynamic and interactive content rendering. 

Lodash templates enable the insertion of HTML directly into table cells, making them highly effective for rendering complex cell data in a `Table`. This approach allows for the dynamic generation of HTML based on cell data, facilitating rich and interactive table cell content.

### Lodash Syntax

The following section outlines the basics of Lodash syntax. While this is not an exhaustive or comprehensive overview, it can be used to help start using Lodash within the `Table` component. 

#### Syntax overview for lodash templates:

- `<%= ... %>` - Interpolates values, inserting the JavaScript code's result into the template.
- `<% ... %>` - Executes JavaScript code, allowing loops, conditionals, and more.
- `<%- ... %>` - Escapes HTML content, ensuring interpolated data is safe from HTML injection attacks.

#### Examples using cell data:

**1. Simple value interpolation**: Directly display the cell's value.

`<%= cell.value %>`

**2. Conditional rendering**: Use JavaScript logic to conditionally render content.

`<% if (cell.value > 100) { %> 'High' <% } else { %> 'Normal' <% } %>`

**3. Combining data fields**: Render content using multiple data fields from the cell.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Escaping HTML content**: Safely render user-generated content.

The renderer has access to detailed cell, row, and column properties in the client side:

**TableCell Properties:**

|Property	|Type	|Description|
|-|-|-|
|column|`TableColumn`|The associated column object.|
|first|`boolean`|Indicates if the cell is the first in the row.|
|id|`String`|The cell ID.|
|index|`int`|The cell's index within its row.|
|last|`boolean`|Indicates if the cell is the last in the row.|
|row|`TableRow`|The associated row object for the cell.|
|value|`Object`|The raw value of the cell, directly from the data source.|

**TableRow Properties:**

|Property|Type|Description|
|-|-|-|
|cells|`TableCell[]`|The cells within the row.
|data|`Object`|The data provided by the application for the row.
|even|`boolean`|Indicates if the row is even-numbered (for styling purposes).
|first|`boolean`|Indicates if the row is the first in the table.
|id|`String`|Unique ID for the row.
|index|`int`|The row index.
|last|`boolean`|Indicates if the row is the last in the table.
|odd|`boolean`|Indicates if the row is odd-numbered (for styling purposes).

**TableColumn Properties:**

|Property	|Type	|Description|
|-|-|-|
|align|ColumnAlignment|The alignment of the column (left, center, right).
|id|String|The field of the row object to get the cell's data from.
|label|String|The name to render in the column header.
|pinned|ColumnPinDirection|The pin direction of the column (left, right, auto).
|sortable|boolean|If true, the column can be sorted.
|sort|SortDirection|The sort order of the column.
|type|ColumnType|The type of the column (text, number, boolean, etc.).
|minWidth|number|The minimum width of the column in pixels.

## Available Renderers

While custom renderers can be created, there are multiple pre-configured renderers available for use within a `Table`. The following are available for developers to use out of the box without the need to create a custom renderer:

>- `ButtonRenderer` - Renderer for a webforj button.
>- `NativeButtonRenderer` - Renderer for a native HTML button.
>- `ElementRenderer` - The base class for all renderers which render an HTML tag **with** content.
>- `VoidElementRenderer` - The base class for all renderers which render a void element, or an HTML tag **without** content.
>- `IconRenderer` - Renderer for an icon - **[see this](../../components/dwc-icon.md)** article for more information on icons.

Renderers allow for custom events to be written as well by extending any of the supported base renderer. Currently, renderers come with a `RendererClickEvent` available for use by developers.

Below is an example of a `Table` that uses renderers to display rich content:

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=addondemos.tabledemos.TableRichContent' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/tabledemos/TableRichContent.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/tablestyles/richcontent/styles.css'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/tabledemos/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/tabledemos/Service.java']}
height='600px'
/>