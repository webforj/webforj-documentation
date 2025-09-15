---
title: Event Dispatcher System
description: How the webforJ event dispatcher system works and how to use it for event-driven programming.
sidebar_position: 50
sidebar_class_name: new-content
---

<DocChip chip='since' label='23.06' />
<JavadocLink type="dispatcher" location="com/webforj/dispatcher/EventDispatcher" top='true'/>

The webforJ event dispatcher system provides a flexible and type-safe way to handle events in your app. It allows you to register listeners for specific event types and dispatch events to those listeners, enabling event-driven programming across components.

:::tip Element and component events
The `EventDispatcher` is for custom event management, see the [Events](/docs/building-ui/events) articles to learn how to handle standard element and component events.
:::

<ComponentDemo 
path='/webforj/eventdispatchercustomevent' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/advanced/EventDispatcherCustomEventView.java'
height = '300px'
/>

## Event dispatcher APIs

[`EventDispatcher`](https://webforj.com/javadoc/com/webforj/dispatcher/EventDispatcher.html) is a minimalistic event manager for dispatching events to listeners. It's not tied to UI components or element events.

**Available methods:**

- `addListener(Class<T> eventClass, EventListener<T> listener)`: Registers a listener for a specific event type. Returns a `ListenerRegistration<T>` for later removal.
- `removeListener(Class<T> eventClass, EventListener<T> listener)`: Removes a specific listener for the given event type.
- `removeAllListeners(Class<T> eventClass)`: Removes all listeners for a given event type.
- `removeAllListeners()`: Removes all listeners from the dispatcher.
- `dispatchEvent(T event)`: Notifies all listeners of the given event.

## Creating and registering listeners

To respond to events in your app, you first need to register one or more listeners with the `EventDispatcher`. A listener is simply a function or lambda that will be called whenever an event of the specified type is dispatched. This mechanism allows you to decouple event producers from consumers. You can register listeners for standard Java events or for your own custom event classes.


```java
import com.webforj.dispatcher.EventDispatcher;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;
import java.util.EventObject;

// Create the dispatcher
EventDispatcher dispatcher = new EventDispatcher();

// Register a listener
ListenerRegistration<EventObject> reg = dispatcher.addListener(EventObject.class, event -> {
    console().log("Event received: " + event);
});

// Remove a listener
reg.remove();
// or
dispatcher.removeListener(EventObject.class, reg.getListener());

// Dispatch an event
dispatcher.dispatchEvent(new EventObject(this));
```



## Creating and registering custom events

Create a class that extends `EventObject` (or another suitable base event class). Add any custom fields or methods you need:

```java
public static class MyCustomEvent extends EventObject {
    private final String message;
    public MyCustomEvent(String message) {
        super(message); // The source can be any object, e.g., the dispatcher or sender
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
```

Then register a listener listening for your custom event.

```java
ListenerRegistration<MyCustomEvent> reg = dispatcher.addListener(MyCustomEvent.class, event -> {
    // Handle the event (access custom fields)
    console().log("Custom event received: " + event.getMessage());
});
```

Whenever needed, dispatch the event and the listener will be triggered.

```java
dispatcher.dispatchEvent(new MyCustomEvent("Hello from custom event!"));
```

## Best practices

- Always remove listeners when they're no longer needed to avoid memory leaks.
- Use the provided event payload methods to access event data efficiently.
- Use the EventDispatcher for custom, non-UI event flows. For UI/component events, see the relevant component documentation.
