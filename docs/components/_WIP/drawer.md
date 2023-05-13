---
sidebar_position: 26 
title: Drawer
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';

The Drawer class is a Java component that creates a drawer for a web application. The drawer is a container that slides into the viewport to expose additional options and information. Multiple drawers can be created in an application, and they will be stacked above each other.

## Constructors

The Drawer component contains a single default constructor which will create a new instance of the Drawer class.

```java
Drawer newDrawer = new Drawer()
```

## Properties

Various properties exist that allow for the customization of various attributes of the Drawer component. Below are those properties with examples for their modification.

### Autofocus

This attribute sets or retrieves whether the drawer should automatically focus the first focusable element within the component when opened.

<!-- Example -->

### Label
Sets or retrieves the label of the drawer used for accessibility purposes.

<!-- Example -->

### Max Size

maxSize: Sets or retrieves the maximum size of the drawer. The value represents the maximum width when the placement is left or right, or the maximum height when the placement is top or bottom.


<!-- Example: -->


### Toggling a Drawer Open or Closed

<!-- Example: -->

### Placement

placement: Sets or retrieves the placement of the drawer.

<!-- Example -->

### Size
size: Sets or retrieves the size of the drawer. The value represents the width when the placement is left or right, or the height when the placement is top or bottom.

<!-- Example -->


## Events

There are two supported events that can be used with the Drawer component, open and close events. 

### Drawer Open

### Drawer Close

<!-- One Example is fine here -->