---
sidebar_position: 4
title: Event Options
slug: event_options
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` is a versatile webforJ tool designed to encapsulate and manage configuration settings for `Element` events within webforJ applications. As a container for various options, it allows developers to precisely dictate how events associated with elements should be processed.

## Event Data

Event data is a key feature of `ElementEventOptions`, allowing developers to attach specific information to the event options. This functionality facilitates the passing of custom data from the client to the server when an event is triggered. This capability is instrumental in conveying additional context or parameters associated with the event, and allows for information to be accessed and utilized without additional trips to the client needing to be made.

For instance, consider a scenario where you have a button click event, and you want to pass the current user's username along with the event. Instead on querying a user's username from the client each time, send this information along with the event as data.

:::tip
For more information, see the [events](../../ui/events.md) and [architecture](../../architecture/architecture.md) pages.
:::

To add data to the event options, you can use the `addData()` method.

#### Example

## Executing JavaScript

The `ElementEventOptions` class allows developers to specify JavaScript code to be evaluated on the client-side before the associated event is fired. This feature enables clients to prepare event data or trigger additional events as needed. This is helpful in many cases, for instance when wanting to validate form data on the client side before submitting it via a form submission event.

#### Usage
To set the event code, use the `setCode()` method.

## Filtering Events

`ElementEventOptions` includes a feature for setting a filter expression to be evaluated on the client before the event is fired. This filter expression enables the client to determine whether the event should proceed or be halted based on certain conditions. Consider an input field where you want to trigger an event only if the entered text meets specific criteria, such as a minimum length.

#### Usage
To set the event filter, use the `setFilter()` method.

## Debouncing and Throttling

#### Purpose
`ElementEventOptions` provides mechanisms for debouncing and throttling events. These features are useful for controlling the frequency of event listeners, ensuring that they are triggered only under certain conditions.

#### Usage
- To set debounce, use the `setDebounce` method.
- To set throttle, use the `setThrottle` method.

#### Example
In scenarios where you want to handle rapid user input, such as search input fields, you can use debounce to delay execution until the user has finished typing.

## Merging Event Options

The `ElementEventOptions` class supports merging with other instances, allowing developers to aggregate various options. This feature is helpful when combining settings from different sources.

## Annotations

### Purpose
For convenience, `ElementEventOptions` can be configured using annotations. These annotations provide a more concise and expressive way to set event options.

### Example
Consider the following example annotation:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
      debounce = @DebounceSettings(value = 200))
```