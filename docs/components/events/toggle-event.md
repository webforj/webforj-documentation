---
sidebar_position: 0
title: ToggleEvent
sidebar_class_name: sidebar--item__hidden
slug: ToggleEvent
---

import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/event/ToggleEvent" top='true' />

A "toggle" event occurs when an element or control changes its state between two possible states, such as "on" and "off" or "visible" and "hidden." It can apply to various elements, such as buttons, dropdowns, menus, or modals. When a "toggle" event is triggered, the element switches its state from one option to another, reflecting the user's action or changing conditions. This class also inherits methods from the [base `Event` class](./event)

## Event Payload

| Method | Description |
|:-:|-|
|`isToggled()`|Gets the value of the component to determine whether it was toggled on or off.|

## See Also

- [Event](./event)
- [Check Event](./CheckEvent)
- [Uncheck Event](./UncheckEvent)
- [CheckBox](../checkbox)
- [RadioButton](../radiobutton)