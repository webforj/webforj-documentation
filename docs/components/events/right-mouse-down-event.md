---
sidebar_position: 0
title: RightMouseDownEvent
sidebar_class_name: sidebar--item__hidden
slug: RightMouseDownEvent
---

import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/event/RightMouseDownEvent" top='true' />

An event which is fired when the user clicks the right mouse button while in the bounds of a the component.

## Event Payload

| Events | Description |
|:-:|-|
|`getMouseButton()`|Returns whether or not a mouse button was pressed while the event happened.|
|`getScreenX()`|Returns the absolute abscissa screen coordinate where this event happened.|
|`getScreenY()`|Returns the absolute ordinate screen coordinate where this event happened.|
|`getX()`|The X coordinate of the point at which this event occurred, relative to the parent <JavadocLink type="engine" location="org/dwcj/component/window/Frame" code='true'>Frame</JavadocLink>.|
|`getY()`|The Y coordinate of the point at which this event occurred, relative to the parent <JavadocLink type="engine" location="org/dwcj/component/window/Frame" code='true'>Frame</JavadocLink>.|
|`isAltDown()`|A boolean value indicating whether the `alt` key was pressed at the time this event was generated|
|`isCmdDown()`|A boolean value indicating whether the `cmd` key was pressed at the time this event was generated|
|`isControlDown()`|A boolean value indicating whether the `ctrl` key was pressed at the time this event was generated|
|`isShiftDown()`|A boolean value indicating whether the `shift` key was pressed at the time this event was generated|