---
sidebar_position: 0
title: <dwc-slider>
sidebar_class_name: sidebar--item__hidden
slug: slider
description: A user guide article for the slider
// pagination_prev: null
// pagination_next: null
---

import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import DocChip from '@site/src/components/DocsTools/DocChip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" target="_blank" clickable={false} iconName='shadow' />

<br />
<br />

:::info CLIENT COMPONENT
This section outlines styling information for the **`<dwc-slider>`** component. This component is **client side only** - it cannot be instantiated on its own via the API, but may make up part of API components.
:::

### Shadow Parts
These are the various parts of the shadow DOM for the component, which will be required when styling via CSS is desired.
<TableBuilder tag='dwc-slider' table="parts"/>



### Reflected Attributes

  The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.
  
  <TableBuilder tag='dwc-slider' table="reflects"/>


