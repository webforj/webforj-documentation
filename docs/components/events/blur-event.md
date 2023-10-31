---
sidebar_position: 0
title: BlurEvent
sidebar_class_name: sidebar--item__hidden
slug: BlurEvent
---

import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/event/BlurEvent" top='true' />

An event that is triggered when an element loses focus. It occurs when the user interacts with an element, such as clicking inside an input field, and then moves the focus away from that element, typically by clicking outside of it or tabbing to another element on the page. This event is useful when you want to detect when a user moves away from a particular element on a web page, such as an input field or a button. This class also inherits methods from the [base `Event` class](./event)

## Event Payload

| Method | Description |
|:-:|-|
|`getText()`|Gets the text of the component.|
|`isClientValidationValid()`|Returns the result of the client validation function, if any, when the component loses focus.|

## See Also

- [Event](./event)
- [Focus Event](./FocusEvent)