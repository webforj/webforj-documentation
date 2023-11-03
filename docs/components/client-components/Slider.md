---
sidebar_position: 0
title: Slider
sidebar_class_name: sidebar--item__hidden
slug: Slider
// pagination_prev: null
// pagination_next: null
---

import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import DocChip from '@site/src/components/DocsTools/DocChip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" target="_blank" clickable={false} iconName='shadow' />

<DocChip tooltipText="The name of the web component that will render in the DOM." label="bbj-slider" clickable={false} iconName='code'/>

## Styling

### Shadow Parts
These are the various parts of the shadow DOM for the component, which will be required when styling via CSS is desired.
<TableBuilder tag='bbj-slider' table="parts"/>

### CSS Properties

  These are the various CSS properties that are used in the component, with a short description of their use.
  
  <TableBuilder tag='bbj-slider' table="properties"/>

### Reflected Attributes

  The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.
  
  <TableBuilder tag='bbj-slider' table="reflects"/>


