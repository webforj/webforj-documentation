---
sidebar_position: 0
title: MouseEnterEvent
sidebar_class_name: sidebar--item__hidden
slug: MouseEnterEvent
---

import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/event/MouseEnterEvent" top='true' />

An event that is triggered when the mouse cursor enters the boundaries of an element. It occurs when the user moves the mouse pointer over the specified element, indicating that the mouse has entered its area.

## Event Payload

| Events | Description |
|:-:|-|
|`mouseButton`|An ID indicating which mouse button was pressed.|
|`screenX`|The absolute screen X coordinate of the point at which this event occurred.|
|`screenY`|The absolute screen Y coordinate of the point at which this event occurred.|
|`x`|The X coordinate of the point at which this event occurred, relative to the parent <JavadocLink type="engine" location="org/dwcj/component/window/Frame" code='true'>Frame</JavadocLink>.|
|`y`|The Y coordinate of the point at which this event occurred, relative to the parent <JavadocLink type="engine" location="org/dwcj/component/window/Frame" code='true'>Frame</JavadocLink>.|
|`altDown`|A boolean value indicating whether the `alt` key was pressed at the time this event was generated|
|`cmdDown`|A boolean value indicating whether the `cmd` key was pressed at the time this event was generated|
|`controlDown`|A boolean value indicating whether the `ctrl` key was pressed at the time this event was generated|
|`shiftDown`|A boolean value indicating whether the `shift` key was pressed at the time this event was generated|
