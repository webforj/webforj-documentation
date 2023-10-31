---
sidebar_position: 0
title: MouseExitEvent
sidebar_class_name: sidebar--item__hidden
slug: MouseExitEvent
---

import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/event/MouseExitEvent" top='true' />

An event that is triggered when the mouse cursor exits the boundaries of an element. It occurs when the user moves the mouse pointer out of the boundaries of the specified element, indicating that the mouse has exited its area.

## Event Payload

| Method | Description |
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

## See Also

- [Event](./event)
- [MouseEnter Event](./MouseEnterEvent)
- [RightMouseDown Event](./RightMouseDownEvent)