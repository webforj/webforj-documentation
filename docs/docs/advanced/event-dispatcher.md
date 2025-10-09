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

<ComponentDemo 
path='/webforj/eventdispatchercustomevent' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/advanced/EventDispatcherCustomEventView.java'
height = '300px'
/>

### Removing listeners and avoiding memory leaks

When to remove listeners

- Remove listeners when the consumer object is disposed or no longer reachable from the app UI (for example: closing a dialog, navigating away from a view, or when a long-lived service no longer needs callbacks).
- Remove listeners when the event handling is tied to a transient lifecycle (short-lived tasks, temporary subscriptions, or one-time flows).

How to remove listeners

Use the returned `ListenerRegistration` to unregister a listener explicitly:

```java
ListenerRegistration<MyCustomEvent> reg = dispatcher.addListener(MyCustomEvent.class, ev -> handle(ev));
// Later, when no longer needed
reg.remove();
```

Or remove by passing the listener to the dispatcher:

```java
dispatcher.removeListener(MyCustomEvent.class, myListener);
```

If you registered multiple listeners for the same class and want to remove all of them at once:

```java
dispatcher.removeAllListeners(MyCustomEvent.class);
```

Listeners are objects or lambdas that often reference surrounding objects (for example, a view or its model). The EventDispatcher keeps those listener objects in internal collections; while stored, neither the listener nor the objects it references can be garbage-collected. In apps that create many short-lived views or components, forgotten listeners accumulate and keep those views alive, causing memory that can't be freed.

- A dialog registers a listener that references the dialog's model. If the dialog is closed but the listener isn't removed, the dispatcher still references the listener, which in turn references the dialog and its model. The dialog can't be garbage-collected.
- Lambdas or inner classes implicitly capture `this` or local variables; the captured references become part of the listener object retained by the dispatcher.

Removing the listener explicitly breaks the chain of strong references: the dispatcher no longer references the listener, so the listener and the objects it captured become eligible for garbage collection if there are no other live references to them.

Prefer storing the returned `ListenerRegistration` where you can remove it during cleanup (for example, in a component's `dispose` or a view's `onDetach`), and avoid anonymous registrations that you can't later unhook.

