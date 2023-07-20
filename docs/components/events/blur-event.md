---
sidebar_position: 0
title: BlurEvent
sidebar_class_name: sidebar--item__hidden
slug: BlurEvent
---

import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/event/BlurEvent" top='true' />

An event that is triggered when an element loses focus. It occurs when the user interacts with an element, such as clicking inside an input field, and then moves the focus away from that element, typically by clicking outside of it or tabbing to another element on the page. This event is useful when you want to detect when a user moves away from a particular element on a web page, such as an input field or a button.

## Event Payload

| Events | Description |
|:-:|-|
|`text`|A string containing the text of the component when it loses focus.|
|`client-validation-valid`|A boolean value indicating the result of running the client validation function, if any, when this component lost focus.|