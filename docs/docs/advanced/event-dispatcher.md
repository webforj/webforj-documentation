---
title: Event Dispatcher System
description: How the webforJ event dispatcher system works and how to use it for event-driven programming.
sidebar_position: 50
---

The webforJ event dispatcher system provides a flexible and type-safe way to handle events in your app. It allows you to register listeners for specific event types and dispatch events to those listeners, enabling event-driven programming across components.

## Overview

At the core of the system is the[`EventDispatcher`](https://webforj.com/javadoc/com/webforj/dispatcher/EventDispatcher.html) class, which manages event listeners and dispatches events. Components use the dispatcher to bridge browser/JavaScript events with Java event objects, ensuring seamless integration between client and server logic.

## Key concepts

- **EventDispatcher**: Manages registration and notification of event listeners.
- **EventListener**: Functional interface for handling events of a specific type.
- **ListenerRegistration**: Represents a handle to a registered listener, allowing removal.
- **EventObject**: The base class for all event payloads.

## Registering an event listener

You can register an event listener for a specific event type using the `addListener` method:

```java
import com.webforj.dispatcher.EventDispatcher;
import com.webforj.dispatcher.EventListener;
import com.webforj.component.event.BlurEvent;

EventDispatcher dispatcher = new EventDispatcher();
dispatcher.addListener(BlurEvent.class, event -> {
    // Handle blur event
    System.out.println("Component blurred: " + event.getComponent());
});
```

## Removing an event listener

When you add a listener, you receive a `ListenerRegistration` object. Use this to remove the listener when it's no longer needed:

```java
ListenerRegistration<BlurEvent> registration = dispatcher.addListener(BlurEvent.class, listener);
// ...
registration.remove();
```

## Dispatching events

To notify all listeners of a specific event, use the `dispatchEvent` method:

```java
dispatcher.dispatchEvent(new BlurEvent(component));
```

## Component integration

Most webforJ components have an internal event dispatcher. You typically don't need to create your own dispatcher; instead, use the component's API to register listeners:

```java
passwordField.addEventListener(BlurEvent.class, event -> {
    // Handle blur event for this field
});
```

## Event types and custom events

Events in webforJ are Java classes that extend `EventObject`. Many built-in events are provided (such as `BlurEvent`, `ClickEvent`, etc.), but you can also define your own custom events for specialized use cases.

To define a custom event, extend `EventObject` (or `ComponentEvent` for UI components). For UI events, you can use annotations like `@EventName` and `@EventOptions` to configure event names and payloads.

```java
@EventName("my-custom-event")
@EventOptions(data = {
    @EventOptions.EventData(key = "value", exp = "event.detail.value"),
    @EventOptions.EventData(key = "timestamp", exp = "event.timeStamp")
})
public static class CustomEvent extends ComponentEvent<MyComponent> {
    public CustomEvent(MyComponent component, Map<String, Object> eventData) {
        super(component, eventData);
    }
    public String getValue() {
        return (String) getData().get("value");
    }
    public Long getTimestamp() {
        return ((Number) getData().get("timestamp")).longValue();
    }
}
```

You can then register listeners for your custom event just like for built-in events:

```java
myComponent.addEventListener(CustomEvent.class, event -> {
    System.out.println("Custom value: " + event.getValue());
});
```

## Advanced registration options

For advanced scenarios, you can register listeners with additional options using `ElementEventOptions`. These options allow you to:
- Add custom data to the event payload
- Debounce or throttle event firing
- Filter events based on conditions
- Execute JavaScript before the event is fired (for UI events)

Example:

```java
ElementEventOptions options = new ElementEventOptions();
options.addData("value", "event.target.value");
options.setDebounce(300); // Debounce by 300ms
options.setFilter("event.target.value.length > 2");

myComponent.addEventListener("input", event -> {
    String value = (String) event.getData().get("value");
    System.out.println("Input value: " + value);
}, options);
```

## Best practices

- Always remove listeners when they're no longer needed to avoid memory leaks.
- Use the provided event payload methods to access event data efficiently, avoiding unnecessary client-server round-trips.
- Prefer using component APIs for event handling unless you have advanced requirements.
- Use registration options (payload, debounce, filter) to optimize performance and event data handling.

