---
sidebar_position: 7
title: Events
description: Listen for component events, reuse the event payload server-side, and use the EventDispatcher to define, publish, and clean up your own custom events.
slug: events
sidebar_class_name: new-content
---

<JavadocLink type="foundation" location="com/webforj/component/Event" top='true'/>

Components, whether custom or part of the framework, support event handling. You can add event listeners to capture various types of events, such as user interactions, changes in state, or other custom events. These event listeners can be used to trigger specific actions or behaviors in response to the events.

## Adding events {#adding-events}

You can add an event listener using one of the following patterns, where:

- **`myComponent`** is the component to which you want to attach the event listener.

- **`addEventListener`** is replaced with the event-specific method.

- **`EventListener`** is replaced with the type of event being listened for.

```java
myComponent.addEventListener(e -> {
  // Executed when the event fires
});

//OR

myComponent.addEventListener(this::eventMethod);
```

Additional syntactic sugar methods, or aliases, have been added to allow for alternative addition of events by using the `on` prefix followed by the event, such as:

```java
myComponent.onEvent(e -> {
  // Executed when the event fires
});
```

## Removing an event {#removing-an-event}

When adding an event listener, a `ListenerRegistration` object will be returned. This can be used, among other things, to remove the event later on.

```java
// Adding the event
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
    // Executed when the event fires
  });

// Removing the event
listenerRegistration.remove();
```

## Using event payload {#using-event-payload}

It's important to note that events often come with a payload, which contains additional information related to the event. You can efficiently utilize this payload within the event handler to access relevant data without making unnecessary round trips between the client and server. By doing so, you can improve the performance of your application.

The following code snippet queries the component to get information that, for our demonstration's purposes, is already included in the event payload, representing inefficient code:

```java
myComponent.addEventListener(e -> {
  // Access data from component
  String componentText = e.getComponent().getText();

  // OR if the component is accessible within the scope of the function
  String componentText = myComponent.getText();

  // Use the componentText to perform other actions.
});
```

Instead, utilizing the payload of the method, which for the sake of the example includes the text of the component, a roundtrip is avoided:

```java
myComponent.addEventListener(e -> {
  // Access data from the event payload
  String componentText = e.getText();

  // Use the componentText to perform other actions.
});
```

This approach minimizes the need to query the component for information, as the data is readily available in the event payload. By following this efficient event handling practice, you can enhance the performance and responsiveness of your components. For more information, you can refer to [Client/Server Interaction](../architecture/client-server).

### Reading from the payload {#reading-from-payload}

In the example below, choosing a shipping method fires a `ListSelectEvent`. The listener reads the chosen item from the event with `getSelectedItem()` and uses its key and text to show the delivery estimate, so no query back to the `ChoiceBox` is needed.

<ComponentDemo
path='/webforj/eventpayload'
files={[
  'src/main/java/com/webforj/samples/views/events/EventPayloadView.java',
  'src/main/frontend/events/eventpayload.css',
]}
height='350px'
/>

## Dispatching your own events {#dispatching-your-own-events}

The events above come from the component you're listening to. A component you write can publish events of its own in the same way, so the code using it can react without reaching into the component's internals.

This is for events your component decides to fire, such as a form reporting a completed submission or an editor reporting a saved record. Events that originate from a client interaction on an `Element`, such as a keystroke or a click, are configured with [Event Options](./event-options) instead.

Components don't come with an event dispatcher, so your component holds its own <JavadocLink type="foundation" location="com/webforj/dispatcher/EventDispatcher" code='true'>EventDispatcher</JavadocLink> and publishes events through it.

### Defining the event {#defining-the-event}

Define the event as a class extending `EventObject`, nested inside the component that fires it. Pass the source to the superclass, and add accessors for the data listeners need.

```java title="OrderSubmittedEvent (nested in OrderForm)"
public static class OrderSubmittedEvent extends EventObject {
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

Putting the data on the event follows the same reasoning as [using event payload](#using-event-payload) above. Listeners read what they need from the event instead of querying the component afterward.

### Registering and dispatching {#registering-and-dispatching}

Hold an `EventDispatcher` in the component and expose an `onXxx` method that registers listeners against it. Return the `ListenerRegistration` so callers can remove the listener later, the same way they would for a built-in event.

Dispatch the event once the work it reports is complete.

```java title="OrderForm.java"
public class OrderForm extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final EventDispatcher dispatcher = new EventDispatcher();
  private final TextField customer = new TextField("Customer");
  private final Button submit = new Button("Place order");

  public OrderForm() {
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("8px")
      .add(customer, submit);

    submit.onClick(event -> submitOrder());
  }

  public ListenerRegistration<OrderSubmittedEvent> onSubmit(
      EventListener<OrderSubmittedEvent> listener) {
    return dispatcher.addListener(OrderSubmittedEvent.class, listener);
  }

  private void submitOrder() {
    Order order = OrderService.create(customer.getValue());
    dispatcher.dispatchEvent(
      new OrderSubmittedEvent(this, order.getId(), order.getTotal()));
  }
}
```

Listeners register the same way they would for any built-in component:

```java
OrderForm form = new OrderForm();
form.onSubmit(event -> OrderService.confirm(event.getOrderId()));
```

### Dispatching in a component {#dispatching-in-component}

In the example below, the order form dispatches an `OrderSubmittedEvent` each time an order is placed. The surrounding view registers with `onSubmit` and builds each row of the placed orders list from the event data.

<ComponentDemo
path='/webforj/compositecustomevent'
files={[
  'src/main/java/com/webforj/samples/views/events/CompositeCustomEventView.java',
  'src/main/frontend/events/ordercustomevent.css',
]}
height='425px'
/>

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

A dispatcher holds its listeners in internal collections, and a listener holds whatever it captured. A lambda or inner class implicitly captures `this` along with any local variables it uses, so the objects behind a listener stay reachable for as long as the dispatcher keeps it.

That matters when the listener outlives what it references. If a dialog registers a listener that reads its own model and the dialog closes without removing it, the dispatcher still holds the listener, the listener still holds the dialog, and neither can be collected. In an app that creates many short-lived views, forgotten listeners accumulate this way.

Removing the listener breaks that chain, so remove listeners when:

- The object that registered them is finished, such as a closed dialog or a view that has been navigated away from.
- The subscription was tied to a short-lived task or a one-time flow.

Keep the returned `ListenerRegistration` somewhere you can reach during cleanup, rather than registering a listener you have no way to unhook later. In a component, `onDidDestroy()` is that cleanup point.

:::tip Configuring element events
For events that come from a client interaction on an `Element`, such as attaching payload data, filtering, debouncing, or throttling, see [Event Options](./event-options).
:::