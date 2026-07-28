---
sidebar_position: 10
title: Event Options
description: Configure element events with ElementEventOptions to attach payload data, filter, debounce, throttle, and run client-side code before firing.
---
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` is a versatile webforJ tool designed to encapsulate and manage configuration settings for `Element` events within webforJ applications. As a container for various options, it allows developers to precisely dictate how events associated with elements should be processed.

## Event data {#event-data}

Event data is a key feature of `ElementEventOptions`, allowing developers to attach specific information to the event options. This functionality facilitates the passing of custom data from the client to the server when an event is triggered. This capability is instrumental in conveying additional context or parameters associated with the event, and allows for information to be accessed and utilized without additional trips to the client needing to be made.

For instance, consider a scenario where you have a button click event, and you want to pass the current user's username along with the event. Instead on querying a user's username from the client each time, send this information along with the event as data.

:::tip
For more information, see the [events](/docs/building-ui/events) and [Client/Server Interaction](/docs/architecture/client-server) pages.
:::

To add data to the event options, you can use the `addData()` method.

## Executing JavaScript {#executing-javascript}

The `ElementEventOptions` class allows developers to specify JavaScript code to be evaluated on the client-side before the associated event is fired. This feature enables clients to prepare event data or trigger additional events as needed. This is helpful in many cases, for instance when wanting to validate form data on the client side before submitting it via a form submission event.

### Usage {#usage}
To set the event code, use the `setCode()` method.

## Filtering events {#filtering-events}

`ElementEventOptions` includes a feature for setting a filter expression to be evaluated on the client before the event is fired. This filter expression enables the client to determine whether the event should proceed or be halted based on certain conditions. Consider an input field where you want to trigger an event only if the entered text meets specific criteria, such as a minimum length.

### Usage {#usage-1}
To set the event filter, use the `setFilter()` method.

## Debouncing and throttling {#debouncing-and-throttling}

### Purpose {#purpose}
`ElementEventOptions` provides mechanisms for debouncing and throttling events. These features are useful for controlling the frequency of event listeners, ensuring that they are triggered only under certain conditions.

### Usage {#usage-2}
- To set debounce, use the `setDebounce` method.
- To set throttle, use the `setThrottle` method.

### Example {#example}
In scenarios where you want to handle rapid user input, such as search input fields, you can use debounce to delay execution until the user has finished typing.

## Merging event options {#merging-event-options}

The `ElementEventOptions` class supports merging with other instances, allowing developers to aggregate various options. This is helpful when a shared base configuration is reused across several events.

Use the `mergeWith()` method to merge one or more option sets into the current instance. Data entries are combined, while code, filter, debounce, and throttle settings from the passed options take precedence over those already set, so a shared base applied last overrides the current values. When several options define the same setting, the last one encountered wins.

```java
// A specific event's own options
ElementEventOptions options = new ElementEventOptions()
    .addData("value", "component.value")
    .setFilter("event.target.value.length > 2");

// A shared base reused across events
ElementEventOptions base = new ElementEventOptions()
    .addData("id", "component.id")
    .setDebounce(200);

// Merge the base in: its data is added, and its settings take precedence
options.mergeWith(base);
```

## Annotations {#annotations}

### Purpose {#purpose-1}
For convenience, `ElementEventOptions` can be configured using annotations. These annotations provide a more concise and expressive way to set event options.

### Example {#example-1}
Consider the following example annotation:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
debounce = @DebounceSettings(value = 200))
```