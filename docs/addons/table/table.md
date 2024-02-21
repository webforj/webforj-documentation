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

<DocChip tooltipText="The name of the web component that will render in the DOM." label="bbj-table" clickable={false} iconName='code'/>

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

For more information on the `Column`, see [this article](./table_columns.md).

## Rich content

### Renderers

### Client Side Rendering

### Available Renderers

### Lodash Renderers

## Selection

The `Table` component provides various selection capabilities. There are methods for selecting a single item, multiple items, or programmatically managing selections, the Table is designed to accommodate diverse selection needs.
### Selection Mode
### Checkbox Selection
### Selection Event

## Virtual Scrolling

## Editing and refreshing

## Filtering

