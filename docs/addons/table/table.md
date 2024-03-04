---
sidebar_position: 1
title: Table
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import EventTable from '@site/src/components/DocsTools/EventTable';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';
import DocChip from '@site/src/components/DocsTools/DocChip';
import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import Chip from '@mui/material/Chip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" component="a" href="../glossary#shadow-dom" target="_blank" clickable={true} iconName="shadow" />

<DocChip tooltipText="The name of the web component that will render in the DOM." label="dwc-table" clickable={false} iconName='code'/>

<JavadocLink type="engine" location="org/dwcj/component/button/Button" top='true'/>

:::warning
The `Table` component's API may change in future versions.
:::

The `Table` class is a versatile component designed for presenting tabular information in a structured and easily understandable manner. Optimized for handling large datasets with high performance, this component offers advanced visualization and a comprehensive suite of events for dynamic user engagement.

See these sections for more information on:
- [Columns](./table_columns.md)
- [Selection](./table_selection.md)
- [Sorting](./table_sorting.md)

<ComponentDemo 
path='https://eu.bbx.kitchen/webapp/controlsamples/DataTable?' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/addondemos/tabledemos/DataTable.java'
height='600px'
/>

<!-- ## Creating and Populating a `Table`




<ComponentDemo 
path='https://eu.bbx.kitchen/webapp/controlsamples?class=addondemos.tabledemos.TableBasic' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/addondemos/tabledemos/TableBasic.java'
height='600px'
/> -->


## Column Configuration

To render the `Table` above, use the `addColumn()` method to delineate which columns the `Table` should have, as well as providing data to the `Table` using the `setRepository()` method.

For more information on the `Column`, see **[this article](./table_columns.md)**.

## Rich Content and Client-Side Rendering

Tables in DWCj are also configurable using the following tools to display rich content within cells. This includes various multimedia elements, interactive components, or formatted data within the table cells.

These elements are rendered client-side, meaning the process of generating and displaying rich content is done directly in the browser, using JavaScript only when needed, increasing performance of applications using the `Table`.

### Lodash Renderers

Renderers offer a powerful mechanism for customizing the way data is displayed within a `Table`. The primary class, `Renderer`, is designed to be extended to create custom renderers based on lodash templates, enabling dynamic and interactive content rendering. 

Lodash templates enable the insertion of JavaScript logic directly into HTML, making them highly effective for rendering complex cell data in a table. This approach allows for the dynamic generation of HTML based on cell data, facilitating rich and interactive table cell content.

### Available Renderers

While custom renderers can be created, there are multiple pre-configured renderers available for use within a `Table`. The following are available for developers to use out of the box without the need to create a custom renderer:

>- `ButtonRenderer` - Renderer for a DWCj button.
>- `NativeButtonRenderer` - Renderer for a native HTML button.
>- `ElementRenderer` - The base class for all renderers which render an HTML tag **with** content.
>- `VoidElementRenderer` - The base class for all renderers which render a void element, or an HTML tag **without** content.
>- `IconRenderer` - Renderer for an icon - **[see this](../../components/dwc-icon.md)** article for more information on icons.

Renderers currently come with a `RendererClickEvent` available for use by developers, and allow for custom events to be written as well should they be needed.

Below is an example of a `Table` that uses renderers to display rich content:

<ComponentDemo 
path='https://eu.bbx.kitchen/webapp/controlsamples?class=addondemos.tabledemos.TableRichContent' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/addondemos/tabledemos/TableRichContent.java'
height='600px'
/>

## Selection

The `Table` component provides various selection capabilities, including methods for the configuration of selection type, programmatically managing selections, and events.

The details of `Table` selection are outlined in **[this article](./table_selection.md)**. 

## Sorting

Sorting is supported out of the box by the `Table` component, allowing for more efficient visualization of data for users.

The details of `Table` sorting are outlined in **[this article](./table_sorting.md)**. 

## Virtual Scrolling

The `Table` component is built to efficiently handle large datasets by utilizing virtual scrolling, which renders only the visible rows, thus improving performance and reducing resource consumption. The below `Table` shows all olympic winners - a large dataset that benefits greatly from the table's virtual scrolling functionality:

<ComponentDemo 
path='https://eu.bbx.kitchen/webapp/controlsamples?class=addondemos.tabledemos.TableOlympicWinners' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/addondemos/tabledemos/TableOlympicWinners.java'
height='600px'
/>

### Overscan

Setting the table's `Overscan` property determines how many rows to render outside of the visible area. This setting can be configured with the `setOverscan(double value)` method.

A higher overscan value can reduce the frequency of rendering when scrolling, but at the cost of rendering more rows than are visible at any one time. This can be a trade-off between rendering performance and scroll smoothness.


## Editing and Refreshing

Editing data within the `Table` works via interaction with the `Repository` containing the data for the `Table`. The `Repository` serves as a bridge between the `Table` and the underlying dataset, offering methods for data retrieval, modification, and refreshing. Below is an example which implements behavior to edit the "Title" of a desired row based.

<ComponentDemo 
path='https://eu.bbx.kitchen/webapp/controlsamples?class=addondemos.tabledemos.TableEditData' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/addondemos/tabledemos/TableEditData.java'
height='600px'
/>

In the above example, the `TitleEditorComponent` class facilitates the editing of the "Title" field for a selected `MusicRecord`. The component includes an input field for the new title, along with "Save" and "Cancel" buttons.

To connect the editing component with the `Table`, an "Edit" button is added to the `Table` via a `VoidElementRenderer`. Clicking this button triggers the `edit()` method of the `TitleEditorComponent`, allowing users to modify the "Title".

### Commit Method

Once the user modifies the title and clicks the "Save" button, the `TitleEditorComponent` triggers the `save()` method. This method updates the title of the corresponding `MusicRecord` and dispatches a custom `SaveEvent`.

The real-time update of data in the repository is achieved through the `commit()` method. This method is employed within the `onSave` event listener, ensuring that changes made through the editing component are reflected in the underlying dataset.

## Filtering

The `Table` component allows you to implement filtering functionality to narrow down displayed data based on specific criteria. Filtering can be achieved by defining a filtering criteria using the `setFilter(Predicate<T> filter)` method provided by the `Repository` associated with the table.

<ComponentDemo 
path='https://eu.bbx.kitchen/webapp/controlsamples?class=addondemos.tabledemos.TableFiltering' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/addondemos/tabledemos/TableFiltering.java'
height='600px'
/>

In the above example, the `setFilter()` method is used to define a filtering criteria based on the title of `MusicRecord`. The filter is then applied when the user modifies the content of the search field, updating the searchTerm and triggering the `commit()` method to refresh the displayed data.