---
title: Event Dispatcher System
description: How the webforJ event dispatcher system works and how to use it for event-driven programming.
sidebar_position: 50
---

# Event dispatcher system

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

## Advanced usage

- You can remove all listeners for a specific event type with `removeAllListeners(Class<?> eventClass)`.
- The dispatcher supports concurrent access and is safe for use in multi-threaded environments.
- Custom events can be defined by extending `EventObject` and registering listeners for your custom event class.

## Best practices

- Always remove listeners when they're no longer needed to avoid memory leaks.
- Use the provided event payload methods to access event data efficiently, avoiding unnecessary client-server round-trips.
- Prefer using component APIs for event handling unless you have advanced requirements.

