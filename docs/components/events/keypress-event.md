---
sidebar_position: 0
title: KeypressEvent
sidebar_class_name: sidebar--item__hidden
slug: KeypressEvent
---

import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/event/KeypressEvent" top='true' />

A Keypress event occurs when a user presses a specific key on the keyboard while an element, such as an input field or a specific section of a web page, has focus. This event is commonly used to capture and respond to user keyboard interactions.

When a Keypress event is triggered, the key that was pressed can be determine using the key code, and a specific action or actions can be performed based on the user's input. For example, it can be used to validate user input in form fields, implement keyboard shortcuts, or trigger certain actions based on specific key combinations.

To see a list of keys that trigger this event, <JavadocLink type="engine" location="org/dwcj/component/event/KeypressEvent.Key">see the Javadoc.</JavadocLink> This class also inherits methods from the [base `Event` class](./event)

## Event Payload

| Method | Description |
|:-:|-|
|`getCode()`|Returns the integer value of the key code.|
|`getName()`| Returns the name of the key code.|
|`fromCode(int value)`|Returns the key code that matches the given integer value.|
|`fromName(String name)`|Returns the key code that matches the given name.|
|`getKeyCode()`|Returns the key that was pressed.|
|`isAltKey()`|Returns whether or not the alt key was pressed when the event happened.|
|`isCmdKey()`|Returns whether or not the command key was pressed when the event happened.|
|`isControlKey()`|Returns whether or not the control key was pressed when the event happened.|
|`isShiftKey()`|Returns whether or not the shift key was pressed when the event happened.|

## See Also

- [Event](./event)