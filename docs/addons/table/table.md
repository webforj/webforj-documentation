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

The `Table` class is a versatile component designed for presenting tabular information in a structured and easily understandable manner. Optimized for handling large datasets with high performance, this component offers advanced visualization and a comprehensive suite of events for dynamic user engagement.

The example below demonstrates a simple table suitable for simply displaying data without much user interaction. Many other behaviors can be enabled to enhance the user experience, which will be outlined in subsequent sections.


<ComponentDemo 
path='https://eu.bbx.kitchen/webapp/controlsamples?class=addondemos.tabledemos.TableBasic' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/addondemos/tabledemos/TableBasic.java'
height='600px'
/>


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

The `Table` component provides various selection capabilities. There are methods for selecting a single item, multiple items, or programmatically managing selections. See **[this section](./table_selection.md)** for a more detailed walkthrough of selection configuration. 

## Virtual Scrolling

## Editing and refreshing

## Filtering

