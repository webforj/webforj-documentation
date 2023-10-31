---
sidebar_position: 0
title: ButtonClickEvent
sidebar_class_name: sidebar--item__hidden
slug: ButtonClickEvent
---

import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/button/event/ButtonClickEvent" top='true' />


The `ButtonClickEvent` is an event type that is triggered when a user clicks on a <JavadocLink type="engine" location="org/dwcj/component/button/Button" code='true'>Button</JavadocLink> element. This event is essential for capturing user interactions with buttons on a web page, such as submitting forms, triggering actions, or navigating to different sections of the site.



## Event Payload

| Method | Description |
|:-:|-|
|`getX()`|Returns the X location of the mouse within the <JavadocLink type="engine" location="org/dwcj/component/button/Button" code='true'>Button</JavadocLink> component at the time the event was fired.|
|`getY()`|Returns the Y location of the mouse within the <JavadocLink type="engine" location="org/dwcj/component/button/Button" code='true'>Button</JavadocLink> component at the time the event was fired.|

## See Also

- [Event](./event)
- [Button](../button)