---
sidebar_position: 0
title: Event
sidebar_class_name: sidebar--item__hidden
slug: event
---

import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/event/Event" top='true' />

This class is the base class for all events fired by the components. It is extended by the other events implemented by various components, and can also be extended to create custom events.

## Event Payload

| Events | Description |
|:-:|-|
|`getData()`|Get the event map sent by the component.|
|`getEventMap()`|Alias for the `getData()` method above.|
|`getControl()`|Gets the control.|