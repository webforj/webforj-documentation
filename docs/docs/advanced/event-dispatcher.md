---
title: Event Dispatcher System
description: How the webforJ event dispatcher system works and how to use it for event-driven programming.
sidebar_position: 50
---

The webforJ event dispatcher system provides a flexible and type-safe way to handle events in your app. It allows you to register listeners for specific event types and dispatch events to those listeners, enabling event-driven programming across components.


## `EventDispatcher`

[`EventDispatcher`](https://webforj.com/javadoc/com/webforj/dispatcher/EventDispatcher.html) is a minimalistic event manager for dispatching events to listeners. It's not tied to UI components or element events.

### Available APIs

- `addListener(Class<T> eventClass, EventListener<T> listener)`: Registers a listener for a specific event type. Returns a `ListenerRegistration<T>` for later removal.
- `removeListener(Class<T> eventClass, EventListener<T> listener)`: Removes a specific listener for the given event type.
- `removeAllListeners(Class<T> eventClass)`: Removes all listeners for a given event type.
- `removeAllListeners()`: Removes all listeners from the dispatcher.
- `dispatchEvent(T event)`: Notifies all listeners of the given event.

<ComponentDemo 
path='/webforj/eventdispatchercustomevent' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/advanced/EventDispatcherCustomEventView.java'
height = '300px'
/>

### Creating and registering listeners

```java
import com.webforj.dispatcher.EventDispatcher;
import com.webforj.dispatcher.EventListener;
import java.util.EventObject;

// Create the dispatcher
EventDispatcher dispatcher = new EventDispatcher();

// Register a listener
ListenerRegistration<EventObject> reg = dispatcher.addListener(EventObject.class, event -> {
    // handle event
    System.out.println("Event received: " + event);
});

// Remove a listener
reg.remove();
// or
dispatcher.removeListener(EventObject.class, reg.getListener());

// Dispatch an event
dispatcher.dispatchEvent(new EventObject(this));
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

