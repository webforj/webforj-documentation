---
sidebar_position: 0
title: <dwc-combobox>
sidebar_class_name: sidebar--item__hidden
slug: combobox
description: A user guide article for the combobox
// pagination_prev: null
// pagination_next: null
---

import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import DocChip from '@site/src/components/DocsTools/DocChip';

<DocChip chip='shadow' />

<br />

:::info CLIENT COMPONENT
This section outlines styling information for the **`<dwc-combobox>`** component. This component is **client side only** - it cannot be instantiated on its own via the API, but may make up part of API components.
:::

### Shadow Parts
These are the various parts of the shadow DOM for the component, which will be required when styling via CSS is desired.
<TableBuilder tag='dwc-combobox' table="parts"/>

### CSS Properties

  These are the various CSS properties that are used in the component, with a short description of their use.
  
  <TableBuilder tag='dwc-combobox' table="properties"/>

### Reflected Attributes

  The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.
  
  <TableBuilder tag='dwc-combobox' table="reflects"/>

### Dependencies

  This component relies on the following components - see the related article for more detailed styling information:
  
  <TableBuilder tag='dwc-combobox' table="dependencies"/>
