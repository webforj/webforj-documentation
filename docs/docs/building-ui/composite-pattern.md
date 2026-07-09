---
sidebar_position: 5
title: Composite Patterns
sidebar_class_name: new-content
description: Production patterns for building reusable business logic components with Composite, covering configuration, custom events, theming, state, and resource management.

---

<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

The [Composing Components](../building-ui/composing-components) article covers the basics of extending `Composite` and configuring a bound component. This article collects patterns for production components built on `Composite`: custom events and business logic, theme-aware and stateful components, and resource cleanup. Each section shows a pattern in a real-world component and notes the trade-offs it carries.
 
## Before you start {#before-you-start}
 
This article assumes the basics from [Composing Components](../building-ui/composing-components): extending `Composite`, configuring the bound component through `getBoundComponent()`, overriding `initBoundComponent()` for a parameterized bound component, and the `onDidCreate()` / `onDidDestroy()` / `whenAttached()` lifecycle hooks. It builds on those to show how the pieces combine in production components, and does not re-cover them here.
 
## Custom events and business logic {#custom-events-and-business-logic}
 
This pattern communicates outcomes through typed events instead of exposing a composite's internals. The base `Component` class does not provide an event dispatcher, so the composite holds its own `EventDispatcher` and exposes a registration method that returns a `ListenerRegistration`.
 
### Defining a custom event {#defining-a-custom-event}
 
The event is a nested class extending `EventObject`. It passes the source to the superclass and adds typed accessors for the data consumers read.
 
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
 
### Dispatching events and integrating logic {#dispatching-events}
 
The composite holds an `EventDispatcher`, exposes an `onXxx` method that registers listeners against it, and dispatches the event when the business logic completes.
 
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
    try {
      Order order = OrderService.create(customer.getValue());
      dispatcher.dispatchEvent(
        new OrderSubmittedEvent(this, order.getId(), order.getTotal()));
    } catch (OrderException e) {
      Toast.show("Could not place the order", Theme.DANGER);
    }
  }
}
```
 
Consumers register the same way they would for any built-in component:
 
```java
OrderForm form = new OrderForm();
form.onSubmit(event -> OrderService.confirm(event.getOrderId()));
```
 
The demo below dispatches an `OrderSubmittedEvent` on each submission. The surrounding view registers with `onSubmit` and adds each order to the placed-orders list from the event data.
 
<ComponentDemo
path='/webforj/compositecustomevent'
files={[
  'src/main/java/com/webforj/samples/views/composite/CompositeCustomEventView.java',
  'src/main/frontend/composite/ordercustomevent.css',
]}
height='450px'
/>
 
### Error handling in component methods {#error-handling}
 
Business logic inside a component method can fail. In the `OrderForm` above, `submitOrder` wraps the call in a `try`/`catch` and surfaces the failure with a `Toast` rather than letting the exception escape the listener. A few characteristics of that shape:
 
- The `catch` sits where the failure can be reported, not deep in a helper where the context is lost.
- UI state is reset or preserved, so the interaction can be retried.
- A dedicated failure event (for example an `OrderFailedEvent`) carries the failure to consumers when they need to react, as an alternative to logging alone.
## Advanced patterns {#advanced-patterns}
 
### Theme-aware components {#theme-aware-components}
 
webforJ components share a set of standard themes through the [`Theme`](../styling/themes) enum: `DEFAULT`, `PRIMARY`, `SUCCESS`, `WARNING`, `DANGER`, `INFO`, and `GRAY`. This pattern makes a composite theme-aware by storing the selected theme and reflecting it on the bound component as a `theme` attribute, which CSS then styles against.
 
```java title="StatusPanel.java"
public class StatusPanel extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final Paragraph message = new Paragraph();
  private Theme theme = Theme.DEFAULT;
 
  public StatusPanel() {
    self.add(message);
    applyTheme();
  }
 
  public StatusPanel setTheme(Theme theme) {
    this.theme = theme;
    applyTheme();
    return this;
  }
 
  public Theme getTheme() {
    return theme;
  }
 
  public StatusPanel setMessage(String text) {
    message.setText(text);
    return this;
  }
 
  private void applyTheme() {
    self.setAttribute("theme", theme.name().toLowerCase());
  }
}
```
 
When a composite wraps components that already support the standard `Theme` (for example `IconButton` or `Refresher`), their own `setTheme(theme)` method can receive the theme directly, in place of or alongside the attribute.
 
:::note App theme vs component theme
This pattern controls a component's theme, which is independent of the active app theme. Switching the whole app between light and dark is covered in [Themes](../styling/themes).
:::
 
### Section-based layouts {#section-based-layouts}
 
A component with distinct regions (a header, a body, a footer) can expose methods that add into internal containers, giving callers structured insertion points without exposing the layout.
 
```java title="CardPanel.java"
public class CardPanel extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Div header = new Div();
  private final Div body = new Div();
  private final Div footer = new Div();
 
  public CardPanel() {
    self.setDirection(FlexDirection.COLUMN).add(header, body, footer);
  }
 
  public CardPanel addToHeader(Component... components) {
    header.add(components);
    return this;
  }
 
  public CardPanel addToBody(Component... components) {
    body.add(components);
    return this;
  }
 
  public CardPanel addToFooter(Component... components) {
    footer.add(components);
    return this;
  }
}
```
 
:::note Section methods are not web component slots
This pattern adds children into server-side container components. It is different from slotted content in web components, which uses [`ElementCompositeContainer`](../building-ui/element-composite#interacting-with-slots) and named `<slot>` elements. When the bound component already exposes sections (for example a `Dialog` with header and footer slots), those are available directly, without separate containers.
:::
 
### State management {#state-management}
 
This pattern holds state in fields and centralizes UI updates in a single render method. Each change updates the field and then calls render, so the UI reads from one source of truth.
 
```java title="Counter.java"
public class Counter extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Paragraph display = new Paragraph();
  private final Button increment = new Button("Add");
  private int count = 0;
 
  public Counter() {
    self.add(display, increment);
    increment.onClick(event -> {
      count++;
      render();
    });
    render();
  }
 
  private void render() {
    display.setText("Count: " + count);
  }
}
```
 
Richer state follows the same shape: fields mutate through methods, then the affected parts re-render. For form data bound to input fields, a [`BindingContext`](../data-binding/overview) handles the wiring in place of per-field code.
 
### What each pattern suits {#what-each-pattern-suits}
 
- Custom events suit components that produce outcomes other parts of the app react to (forms, editors, wizards). The trade-off is a small amount of boilerplate per event.
- Theme reflection suits components reused across contexts with different palettes. The trade-off is owning the CSS keyed to the theme attribute.
- Section methods suit components with fixed regions and flexible content. The trade-off is a wider API surface.
- Centralized state suits interactive components, at negligible cost.
## Resource management {#resource-management}
 
`onDidCreate()` and `onDidDestroy()` are a matched pair: a resource acquired in the first is released in the second. The constructor has no matching teardown, so a resource started there has no framework hook to stop it and persists for the life of the application. The patterns below acquire in `onDidCreate()` and release in `onDidDestroy()`.
 
### Timers and intervals {#timers-and-intervals}
 
An `Interval` starts in `onDidCreate()` and stops in `onDidDestroy()`. While running, it holds a reference to the component and keeps pushing updates, so it stops explicitly on teardown.
 
```java
private Interval interval;
 
@Override
protected void onDidCreate(Div container) {
  interval = new Interval(5f, event -> refresh());
  interval.start();
}
 
@Override
protected void onDidDestroy() {
  if (interval != null) {
    interval.stop();
  }
}
```
 
The demo below is a live order queue driven by an `Interval`. It starts the interval in `onDidCreate`, so orders arrive on their own as soon as the component is in use, pauses and resumes from the control, and stops the interval in `onDidDestroy` on teardown; the oldest order drops off once the queue fills.
 
<ComponentDemo
path='/webforj/compositeinterval'
files={[
  'src/main/java/com/webforj/samples/views/composite/CompositeIntervalView.java',
  'src/main/frontend/composite/liveintervalpanel.css',
]}
height='450px'
/>
 
### Listener registration and removal {#listener-registration-and-removal}
 
Every `addListener` call returns a `ListenerRegistration`. For a subscription to a shared or long-lived object (an application service, a global event bus), the registration is kept and removed on destroy.
 
```java
private ListenerRegistration<PriceChangedEvent> subscription;
 
@Override
protected void onDidCreate(Div container) {
  subscription = PriceService.get().addListener(PriceChangedEvent.class, this::onPriceChanged);
}
 
@Override
protected void onDidDestroy() {
  if (subscription != null) {
    subscription.remove();
  }
}
```
 
Several external registrations can be collected in a list and removed together in `onDidDestroy()`.
 
### Preventing memory leaks {#preventing-memory-leaks}
 
The risk comes from references that outlive the component:
 
- Listeners registered on external, long-lived objects keep the composite reachable until they are removed in `onDidDestroy()`.
- Intervals and scheduled tasks run until they are stopped in `onDidDestroy()`.
- A composite's own `EventDispatcher` is collected together with the composite, so listeners other objects register on it do not require manual cleanup for the composite's sake.
The distinction is direction: a reference pointing outward, from the component to something shared, is removed explicitly, while a reference pointing inward is released with the component.
 
## Common use cases {#common-use-cases}
 
- Form components that validate input and emit a typed event on submit or cancel.
- Reusable cards and panels that expose header, body, and footer sections.
- Dashboard widgets that poll a service on an interval and refresh in place.
- Filter and search bars that emit a query event for a parent view to handle.
- Notification and status components that restyle through the standard themes.
- Grouped controls that bundle related inputs into a single reusable unit.
 