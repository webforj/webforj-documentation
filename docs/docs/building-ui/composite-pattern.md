---
sidebar_position: 5
title: Composite Patterns
sidebar_class_name: new-content
description: Production patterns for building reusable business logic components with Composite, covering configuration, custom events, theming, state, and resource management.

---

<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

The [Composing Components](../building-ui/composing-components) article covers the basics of extending `Composite` and configuring a bound component. This article collects patterns for production components built on `Composite`: where configuration runs, custom events and business logic, theme-aware and stateful components, and resource cleanup. Each section shows a pattern in a real-world component and notes the trade-offs it carries.

## Configuration approaches {#configuration-approaches}

A `Composite` has two places where configuration can run: the constructor and the `onDidCreate()` lifecycle hook. They differ in what is available and when they execute.

### Constructor vs `onDidCreate` {#constructor-vs-ondidcreate}

The bound component exists as soon as the constructor runs. Adding child components, setting properties, building the layout, and wiring internal event listeners all work there, since this is in-memory structure that does not depend on the component being part of the live UI.

```java title="ProductFilter.java"
public class ProductFilter extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField search = new TextField("Search products");
  private final Button apply = new Button("Apply");

  public ProductFilter() {
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("8px")
      .add(search, apply);

    apply.onClick(event -> applyFilter());
  }

  private void applyFilter() {
    // Filter logic
  }
}
```

`onDidCreate(T container)` runs later, when the composite is added to its window and enters the live component lifecycle. It pairs with `onDidDestroy()`, which the framework calls on teardown, so it runs work scoped to the component being in use, such as resources that are released again on teardown.

```java title="LiveMetricsPanel.java"
public class LiveMetricsPanel extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final Paragraph value = new Paragraph();
  private Interval interval;

  public LiveMetricsPanel() {
    self.add(value);
  }

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

  private void refresh() {
    value.setText("Active users: " + MetricsService.activeUsers());
  }
}
```

The demo below starts its interval in `onDidCreate`, refreshes every two seconds, and stops the interval in `onDidDestroy`. The pause control toggles the interval through `stop()` and `start()`.

<ComponentDemo
path='/webforj/compositeinterval'
files={[
  'src/main/java/com/webforj/samples/views/composite/CompositeIntervalView.java',
  'src/main/frontend/composite/liveintervalpanel.css',
]}
height='320px'
/>

### Resources across `onDidCreate` and `onDidDestroy` {#resources-across-lifecycle}

The constructor has no matching teardown callback. A timer, subscription, or listener registered on a shared service in the constructor has no framework hook to stop it, so the resource can outlive the component or run for a component that never enters the UI.

`onDidCreate()` and `onDidDestroy()` are a matched pair: anything acquired in the first can be released in the second. Resources that fit this shape include:

- Timers and intervals that push updates from the server.
- Subscriptions to shared services or application-level event buses.
- Background tasks tied to the component being on screen.

Structure and internal wiring, which are released with the component, do not need this pairing and run in the constructor.

### Multiple constructors {#multiple-constructors}

Several constructors let a component provide defaults while also accepting initial values. Chaining them with `this(...)` keeps the setup in a single constructor, which configures through `getBoundComponent()`.

```java title="UserCard.java"
public class UserCard extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Paragraph nameLabel = new Paragraph();
  private final Paragraph roleLabel = new Paragraph();

  public UserCard() {
    this("Unknown user", "");
  }

  public UserCard(String name) {
    this(name, "");
  }

  public UserCard(String name, String role) {
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("4px")
      .add(nameLabel, roleLabel);

    nameLabel.setText(name);
    roleLabel.setText(role);
  }
}
```

:::warning `initBoundComponent()` runs before your constructor body
When you override `initBoundComponent()`, it runs during the `Composite` superclass constructor, before your subclass fields are initialized and before your constructor body executes. Subclass constructor parameters are not readable inside it, and `getBoundComponent()` returns `null` there. Inside it, the bound component is built and returned from local variables; parameter-based configuration happens afterward in the constructor body through `getBoundComponent()`.
:::

Overriding `initBoundComponent()` applies when the bound component itself requires constructor arguments, such as children that must be passed in rather than added afterward. Otherwise the default no-argument creation with constructor-body configuration covers it.

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
      customer.setValue("");
      // Surface the failure through the app's messaging
    }
  }
}
```

Consumers register the same way they would for any built-in component:

```java
OrderForm form = new OrderForm();
form.onSubmit(event -> OrderService.confirm(event.getOrderId()));
```

The demo below dispatches an `OrderSubmittedEvent` on each submission. The surrounding view registers with `onSubmit` and appends a confirmation built from the event data.

<ComponentDemo
path='/webforj/compositecustomevent'
files={[
  'src/main/java/com/webforj/samples/views/composite/CompositeCustomEventView.java',
  'src/main/frontend/composite/ordercustomevent.css',
]}
height='450px'
/>

### Error handling in component methods {#error-handling}

Business logic inside a component method can fail. In the `OrderForm` above, `submitOrder` wraps the call in a `try`/`catch`, resets the field, and leaves the failure to be surfaced through the app rather than letting the exception escape the listener. A few characteristics of that shape:

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

Resources a composite acquires are released in `onDidDestroy()`. Without that, they persist for the life of the application. The cleanup point for each of the following is `onDidDestroy()`.

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