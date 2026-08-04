---
sidebar_position: 7
title: Events
description: Listen for component events, read the event payload, configure element events, and dispatch your own custom events with the EventDispatcher.
slug: events
sidebar_class_name: new-content
---

Components, whether custom or part of the framework, support event handling. You can add event listeners to capture various types of events, such as user interactions, changes in state, or events you define yourself. These listeners let you trigger specific behavior in response to what happens in your app.

## Adding events {#adding-events}

Add a listener with the event-specific method on the component. Each component exposes a pair: an `addXxxListener` method and, in most cases, a shorter `on` alias that does the same thing. A `Button`, for example, exposes both `addClickListener` and `onClick`.

You can pass the listener as a lambda:

```java
Button button = new Button("Save");
button.onClick(event -> {
  // Handle the click
});
```

or as a method reference:

```java
button.onClick(this::handleSave);
```

Not every event has an `on` alias. Value changes, for instance, are added with `addValueChangeListener` only:

```java
TextField name = new TextField("Name");
name.addValueChangeListener(event -> {
  String value = event.getValue();
  // Handle the new value
});
```

## Removing an event {#removing-an-event}

Adding a listener returns a `ListenerRegistration`. Keep it to remove the listener later.

```java
ListenerRegistration<ButtonClickEvent> registration =
    button.onClick(event -> {
      // Handle the click
    });

// Later, when the listener is no longer needed
registration.remove();
```

## Using event payload {#using-event-payload}

Events carry a payload with information about what happened. Reading that payload in the handler gives you the relevant data without a round trip to the client.

For example, a `ModifyEvent` from a `TextField` carries the field's current text. You can query the component for it:

```java
TextField field = new TextField("Search");
field.onModify(event -> {
  String text = field.getText();
  // Use text
});
```

The same value is already on the event, so reading it from the payload avoids going back to the component:

```java
field.onModify(event -> {
  String text = event.getText();
  // Use text
});
```

Read from the payload wherever an event exposes the data you need. For more on why this matters, see [Client/Server Interaction](../architecture/client-server).

## Configuring element events {#configuring-element-events}

When you work directly with an <JavadocLink type="foundation" location="com/webforj/component/element/Element" code='true'>Element</JavadocLink>, its events are configured with <JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" code='true'>ElementEventOptions</JavadocLink>. This controls what data the event carries, whether it fires at all, and how often, all evaluated on the client before the event reaches the server.

### Event data {#event-data}

Event data attaches values from the client to the event, so information is available on the server without an extra request. You add it with `addData()`, giving each entry a key and a JavaScript expression that produces the value.

Two variables are available inside these expressions: `event`, the client event object, and `component`, the element the listener is attached to.

```java
ElementEventOptions options = new ElementEventOptions()
    .addData("value", "component.value")
    .addData("key", "event.key");
```

On the server, each value is read from the event by its key.

### Executing JavaScript {#executing-javascript}

`setCode()` runs a snippet of JavaScript on the client before the event fires. This is useful for preparing event data or reacting on the client without a server round trip.

```java
ElementEventOptions options = new ElementEventOptions()
    .setCode("event.target.value = event.target.value.trim();");
```

### Filtering events {#filtering-events}

`setFilter()` sets a JavaScript expression that decides whether the event fires. If it evaluates to false, the event never reaches the server. This is useful when you only care about an event under certain conditions, such as an input passing a minimum length.

```java
ElementEventOptions options = new ElementEventOptions()
    .setFilter("event.target.value.length > 2");
```

### Debouncing and throttling {#debouncing-and-throttling}

Debouncing and throttling limit how often an event reaches the server, which is useful for rapid events like typing or scrolling.

Debouncing waits until the activity settles before firing. `setDebounce()` takes a timeout in milliseconds and an optional <JavadocLink type="foundation" location="com/webforj/component/element/event/DebouncePhase" code='true'>DebouncePhase</JavadocLink>: `LEADING` fires at the start of the burst, `TRAILING` fires after it ends, and `BOTH` fires at each edge. When you omit the phase, it defaults to `TRAILING`.

```java
ElementEventOptions options = new ElementEventOptions()
    .setDebounce(300, DebouncePhase.TRAILING);
```

Throttling fires at a steady maximum rate while the activity continues. `setThrottle()` takes a timeout in milliseconds.

```java
ElementEventOptions options = new ElementEventOptions()
    .setThrottle(300);
```

An event uses one or the other. Setting a debounce clears any throttle on the same options, and setting a throttle clears any debounce.

### Annotations {#annotations}

Element event options can also be set with annotations, which is a more concise way to configure a listener. The `@EventOptions` annotation holds the data entries, along with filter, debounce, and throttle settings.

```java
@EventOptions(
    data = {@EventData(key = "value", exp = "component.value")},
    debounce = @DebounceSettings(value = 200))
```

When you also pass an `ElementEventOptions` at the call site, its data combines with the annotation's data, and its code, filter, debounce, and throttle override the annotation's.

## Dispatching your own events {#dispatching-your-own-events}

The events covered so far come from the component you're listening to. A component you write can publish events of its own the same way, so the code using it can react without reaching into the component's internals.

:::tip When to dispatch a custom event
Dispatch a custom event when your component decides something has happened, such as a form reporting a completed submission or an editor reporting a saved record. Events that originate from a client interaction on an `Element` are configured with [element event options](#configuring-element-events) instead.
:::

Components don't come with an event dispatcher, so a component that publishes its own events holds its own <JavadocLink type="foundation" location="com/webforj/dispatcher/EventDispatcher" code='true'>EventDispatcher</JavadocLink> and publishes through it.

### Defining the event {#defining-the-event}

Define the event as a class extending `EventObject`. Pass the source, the object publishing the event, to the superclass, and add accessors for the data listeners need.

```java
public class OrderSubmittedEvent extends EventObject {
  private final String orderId;
  private final double total;

  public OrderSubmittedEvent(Object source, String orderId, double total) {
    super(source);
    this.orderId = orderId;
    this.total = total;
  }

  public String getOrderId() {
    return orderId;
  }

  public double getTotal() {
    return total;
  }
}
```

Reading the data from the event follows the same reasoning as [using event payload](#using-event-payload). Listeners get what they need from the event instead of querying the source afterward.

### Registering and dispatching {#registering-and-dispatching}

Create a dispatcher, register listeners for an event type, and dispatch an instance of that type when the event occurs. Registering returns a `ListenerRegistration`, which you keep to remove the listener later.

```java
EventDispatcher dispatcher = new EventDispatcher();

ListenerRegistration<OrderSubmittedEvent> registration =
    dispatcher.addListener(OrderSubmittedEvent.class, event -> {
      String id = event.getOrderId();
      // Handle the event
    });

dispatcher.dispatchEvent(new OrderSubmittedEvent(this, "ORD-1001", 49.99));
```

Every listener registered for that event type runs when the event is dispatched.

A component that publishes an event holds the dispatcher internally and exposes an `onXxx` method rather than the dispatcher itself, so callers subscribe the same way they would for a built-in event:

```java
public ListenerRegistration<OrderSubmittedEvent> onSubmit(
    EventListener<OrderSubmittedEvent> listener) {
  return dispatcher.addListener(OrderSubmittedEvent.class, listener);
}
```

### Removing listeners {#removing-listeners}

Remove a listener through its registration, or by passing the listener back to the dispatcher:

```java
registration.remove();

//OR

dispatcher.removeListener(OrderSubmittedEvent.class, registration.getListener());
```

To clear every listener registered for an event type at once:

```java
dispatcher.removeAllListeners(OrderSubmittedEvent.class);
```

### Avoiding memory leaks {#avoiding-memory-leaks}

A dispatcher retains its listeners, and each listener retains whatever it captured. A lambda or inner class implicitly captures `this` along with any local variables it uses, so the objects behind a listener remain reachable for as long as the dispatcher holds it.

This becomes a problem when a listener outlives what it references. If a dialog registers a listener that reads its own model and closes without removing it, the dispatcher still holds the listener, the listener still holds the dialog, and neither can be garbage collected. In an app that creates many short-lived views, retained listeners accumulate this way.

Remove a listener when:

- The object that registered it's finished, such as a closed dialog or a view that has been navigated away from.
- The subscription was tied to a short-lived task or a one-time flow.

Keep the returned `ListenerRegistration` where you can reach it during cleanup rather than registering a listener you can't later remove. In a component, `onDidDestroy()` is the cleanup point.