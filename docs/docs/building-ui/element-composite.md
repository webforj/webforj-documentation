---
sidebar_position: 6
title: Element Composite
sidebar_class_name: new-content
---

<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

The `ElementComposite` class wraps a custom HTML element or [web component](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). It binds your Java class to the underlying `Element` and lets you work with that element's properties, attributes, and events through Java. Use it when integrating web components into a webforJ app.

:::tip When to use `ElementComposite`
Reach for `ElementComposite` when wrapping a third-party web component that webforJ doesn't already provide. If a built-in webforJ component covers the use case (`TextField`, `ColorField`, `Button`, and so on), use that instead. For one-off DOM work that doesn't need to be reused, the `Element` class can be used directly without a wrapper.
:::

This guide demonstrates how to implement the [Shoelace relative-time web component](https://shoelace.style/components/relative-time) using the `ElementComposite` class.

<ComponentDemo 
path='/webforj/relativetime' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Class annotations {#class-annotations}

Three annotations commonly appear at the top of an `ElementComposite` subclass: `@NodeName` declares the HTML tag the component wraps, and `@JavaScript` and `@StyleSheet` load any client-side assets the underlying web component depends on. `@NodeName` is required and specific to `ElementComposite`. `@JavaScript` and `@StyleSheet` are general webforJ asset annotations and work on any class, including views, components, or the `App` class.

### `@NodeName` {#nodename}

The `@NodeName` annotation declares the HTML tag the component wraps. webforJ uses this name when creating the underlying element in the DOM.

```java
@NodeName("sl-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

The tag name must match the custom element registered on the client. Without this annotation, the framework can't determine which element to create.

Inside a subclass, `getNodeName()` reads back the declared tag, and `getElement()` returns the underlying `Element` so you can call DOM-level methods on it directly.

### `@JavaScript` {#javascript}

The `@JavaScript` annotation loads the script that defines or registers the underlying web component. Place it on the class so the script loads only when the component is used.

```java
@NodeName("sl-relative-time")
@JavaScript("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/shoelace-autoloader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Multiple `@JavaScript` annotations are allowed, and webforJ deduplicates loads automatically. The same script won't load twice if several components depend on it.

See [Importing JavaScript files](../managing-resources/importing-assets#importing-javascript-files) for the full set of options, including `top`, `attributes`, and load timing.

### `@StyleSheet` {#stylesheet}

The `@StyleSheet` annotation loads a CSS file the component depends on. It's useful for third-party components that ship a separate style sheet, or for bundling component-specific styling alongside the wrapper.

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/themes/light.css")
```

For locally bundled assets, use the `ws://` prefix to reference files in `resources/static`:

```java
@StyleSheet("ws://components/relative-time.css")
```

See [Importing CSS files](../managing-resources/importing-assets#importing-css-files) for the full set of options.

## Property and attribute descriptors {#property-and-attribute-descriptors}

Properties and attributes represent the state of a web component, typically holding data or configuration. `ElementComposite` exposes both through `PropertyDescriptor`.

Two factory methods on `PropertyDescriptor` produce the descriptor itself, one per binding target:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` binds to a JavaScript property on the DOM node. `PropertyDescriptor.attribute()` binds to an HTML attribute. The first argument is the name the web component expects. The second is a default value, which also fixes the descriptor's Java type.

Declare the descriptor as a private field on the component, then read and write through it with `set(PropertyDescriptor<V> property, V value)` and `get(PropertyDescriptor<V> property)`.

:::info
Properties are internal state on the DOM node and don't reflect in the markup. Attributes are HTML markup, visible to external scripts and CSS.
:::

```java
// Example property called "title" in an ElementComposite class
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// Example attribute called "value" in an ElementComposite class
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "My Title");
set(value, "My Value");
```

The calls above use `set()` directly to show the primitive form. In practice, `set()` and `get()` are `protected` methods on `ElementComposite`. They're the primitive layer that synchronizes Java values with the underlying element, not the public API consumers call. The intended pattern is to keep the `PropertyDescriptor` private and write public `setX()` and `getX()` methods that delegate to the primitives.

```java
@NodeName("my-card")
public class Card extends ElementComposite {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Card setHeading(String value) {
    set(heading, value);     // protected primitive
    return this;
  }

  public String getHeading() {
    return get(heading);     // protected primitive
  }
}
```

A single call to `set(descriptor, value)` does three things at once. It pushes the value to the client through `setProperty()` for properties, or `setAttribute()` for attributes. It stores the value in a local server-side cache, one map per component instance. And it records the runtime type alongside the value, so later `get()` calls know how to deserialize.

That local cache is the reason `get()` can be cheap by default. `get(descriptor)` returns the cached value from the server-side store with no network call, because every `set()` keeps the cache in sync with the client. The optional `boolean` second argument controls whether to bypass the cache and read from the browser instead.

```java
String cached = get(heading);            // reads from the server-side cache
String live = get(heading, true);        // forces a read from the browser
```

Set `fromClient` to true when the value can change on the client without the server's knowledge, such as a typed `<input>` value. For server-driven properties, the default avoids a round trip.

The optional third argument is a `java.lang.reflect.Type` and controls how the result is deserialized. webforJ resolves the type in this order: the explicit `Type` argument if passed, then the runtime type recorded by a previous `set()` on the same descriptor, then `Object.class`. In practice the type recorded by a prior `set()` is enough, so the third argument can usually be omitted. It's needed when the recorded class loses information the deserializer depends on, such as a parameterized type like `List<String>` whose runtime class is just `ArrayList`.

The demo below adds properties for relative-time based on the web component's docs and exposes them through getters and setters. Each row in the activity feed uses different `format` and `numeric` values to show how the same component renders under varied configurations.

<ComponentDemo 
path='/webforj/relativetimeproperties' 
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/resources/static/css/elementcomposite/activity-feed.css',
]}
height='450px'
/>

### Properties versus attributes {#properties-versus-attributes}

Although `PropertyDescriptor.property()` and `PropertyDescriptor.attribute()` look interchangeable, they target different parts of the underlying element. Choosing the wrong one results in values that silently fail to apply.

Properties are JavaScript object properties on the DOM node. They can hold any type, including strings, booleans, numbers, objects, and arrays, and they represent the element's current runtime state. Setting a property is a direct JavaScript assignment.

Attributes are HTML markup. They live on the element's opening tag, are always strings, and represent the element's initial configuration. Setting an attribute triggers a DOM mutation and a string conversion.

For some cases the two stay in sync. For others they diverge. The `value` of an `<input>` is the classic example: the `value` attribute is the initial value, while the `value` property is the current value the user has typed. Reading the attribute after the user types gives back the original markup, but reading the property gives back the current contents of the field.

Use **properties** for:

- **Frequently changing runtime state**: counters, current selections, typed values
- **Non-string types**: booleans, numbers, objects, arrays
- **Performance-sensitive updates**: properties skip the string conversion required for attributes

Use **attributes** for:

- **Initial configuration**: settings the component reads once when it connects
- **CSS selectors**: values you want to target with selectors like `[disabled]` or `[variant="danger"]`
- **Accessibility hooks**: `aria-label`, `role`, and other ARIA attributes
- **String-like settings that rarely change**

When wrapping a third-party web component, check the component's documentation to confirm which name maps to a property and which to an attribute. Using `PropertyDescriptor.attribute()` for something the component exposes only as a property won't work, and the same is true in reverse. The component will silently ignore the value.

### Typing properties {#typing-properties}

A descriptor is parameterized by the Java type of its value. The full declaration syntax is:

```java
private final PropertyDescriptor<T> name =
  PropertyDescriptor.property(String name, T defaultValue);
```

The `<T>` generic parameter declares the value's type. The default value's runtime type also fixes `T`, so the generic argument rarely needs to be specified explicitly. webforJ uses `T` to serialize and deserialize values when communicating with the client.

```java
private final PropertyDescriptor<String> label =
  PropertyDescriptor.property("label", "");

private final PropertyDescriptor<Boolean> disabled =
  PropertyDescriptor.property("disabled", false);

private final PropertyDescriptor<Integer> max =
  PropertyDescriptor.property("max", 100);

private final PropertyDescriptor<Double> step =
  PropertyDescriptor.property("step", 1.0);
```

Serialization is automatic for primitives, their boxed equivalents, and `String`. For complex types, the value is serialized as JSON before it's assigned to the property on the client.

### Validating values {#validating-values}

Validate values in the setter before calling `set()`. The setter is the natural enforcement point because every mutation flows through it.

```java
private final PropertyDescriptor<Integer> max =
    PropertyDescriptor.property("max", 100);

public Slider setMax(int value) {
  if (value < 0) {
    throw new IllegalArgumentException("max must be non-negative");
  }
  set(max, value);
  return this;
}
```

For nullable references, use `Objects.requireNonNull()` so the failure surfaces at the boundary rather than later in the rendering pipeline.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading cannot be null");
  set(heading, value);
  return this;
}
```

Avoid validating in `get()`. Reads should stay cheap and consistent.

### Enum-style properties {#enum-style-properties}

Most web components expect lowercase or kebab-case string values for enum-like properties (`theme="primary"`, `expanse="xs"`). webforJ uses Gson to serialize enums, but Gson's default representation is the constant name in caps. Annotate each constant with `@SerializedName` so the serialized value matches what the web component expects.

```java
import com.google.gson.annotations.SerializedName;

public enum Variant {
  @SerializedName("primary")
  PRIMARY,

  @SerializedName("secondary")
  SECONDARY,

  @SerializedName("danger")
  DANGER
}
```

Declare the descriptor with the enum type and use the enum directly in the setter and getter.

```java
private final PropertyDescriptor<Variant> variant =
    PropertyDescriptor.property("variant", Variant.PRIMARY);

public MyButton setVariant(Variant value) {
  set(variant, value);
  return this;
}

public Variant getVariant() {
  return get(variant);
}
```

This is the same pattern webforJ's built-in components use for `Theme`, `Expanse`, and similar enums. The public Java API stays type-safe, and the value the web component receives is the string from `@SerializedName`.

### Testing properties {#testing-properties}

`PropertyDescriptorTester` validates that every `PropertyDescriptor` in a component is wired correctly. It scans the class for descriptor fields, calls each setter with the default value, and compares the result against what the getter returns. The tester catches integration mistakes before they reach a running app: a setter that writes to the wrong descriptor, a getter that reads a different property, a default value that doesn't round-trip, or a missing accessor for a declared descriptor.

A baseline test for a component looks like this:

```java
import com.webforj.component.element.PropertyDescriptorTester;
import org.junit.jupiter.api.Test;

class CardTest {

  @Test
  void validateProperties() {
    Card component = new Card();
    PropertyDescriptorTester.run(Card.class, component);
  }
}
```

#### Excluding properties {#excluding-properties}

Some descriptors don't follow standard getter and setter conventions, or they rely on external state the test can't satisfy. Annotate them with `@PropertyExclude` to skip them.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
  PropertyDescriptor.property("internal", "");
```

#### Custom getter and setter names {#custom-getter-and-setter-names}

If a descriptor uses non-standard accessor names, declare them with `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
  PropertyDescriptor.property("custom", "default");
```

The `target` parameter accepts a class when the accessors live somewhere other than the component itself.

For more detail on the testing surface, see [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Concern interfaces {#concern-interfaces}

Concern interfaces give an `ElementComposite` subclass component capabilities without writing the implementation yourself. The interfaces forward calls to the underlying element. Implement the ones the component should support, parameterized with the subclass type so chaining returns the component:

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // No implementation needed.
}

MyBadge badge = new MyBadge()
    .setText("New")
    .addClassName("highlight")
    .setStyle("color", "var(--dwc-color-primary)");
```

The three interfaces above cover everything `MyBadge` needs without any method bodies in the class. `HasText` exposes `setText()` and writes to the element's text content. `HasClassName` exposes `addClassName()`, which lets the badge be targeted from CSS. `HasStyle` exposes `setStyle()` for inline styling.

For the full set of available interfaces and what each one provides, see [Concern interfaces](./component-fundamentals#concern-interfaces) in the Understanding Components article. If a default forwarding doesn't match what the wrapped element exposes, override the method in the subclass.

## Events {#events}

### Event registration {#event-registration}

Web components dispatch DOM events when something happens in the browser. To react from Java, listen for those events with `addEventListener()`. The set of events a component dispatches varies, so check the component's own docs for the names and payloads available.

`ElementComposite` supports debouncing, throttling, filtering, and custom event data on registered listeners.

Register event listeners using the `addEventListener()` method:

```java
// Example: Adding a click event listener
addEventListener(ElementClickEvent.class, event -> {
  // Handle the click event
});
```

:::info
`ElementComposite` only accepts event classes annotated with `@EventName`, unlike `Element`, which accepts any string event name.
:::

### Built-in event classes {#built-in-event-classes}

`ElementClickEvent` is the one built-in event class `ElementComposite` ships with. It surfaces mouse click events on the underlying element with typed accessors for coordinates (`getClientX()`, `getClientY()`), button information (`getButton()`), and modifier keys (`isCtrlKey()`, `isShiftKey()`, and so on).

To expose click handling on the public API of a subclass, implement the `HasElementClickListener<T>` concern interface. It provides default `onClick()` and `addClickListener()` methods that delegate to the protected `addEventListener()` primitive.

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasElementClickListener<MyBadge> {
  // onClick() and addClickListener() are now available on MyBadge
}

new MyBadge().onClick(event -> {
  if (event.isShiftKey()) {
    // ...
  }
});
```

For any other event the underlying web component dispatches, define a custom event class. See [Custom event classes](#custom-event-classes).

### Event payloads {#event-payloads}

Events carry data from the client to your Java code. Access this data through `getData()` for raw event data or use typed methods when available on built-in event classes. See the [Events guide](../building-ui/events) for more on efficient payload handling.

### Custom event classes {#custom-event-classes}

Define custom event classes with `@EventName` and `@EventOptions` to capture client-side data in a typed Java event. Use this when the Java handler needs values from the browser.

`@EventName` binds the Java class to the event the component dispatches in the browser, so a class annotated `@EventName("sl-change")` fires whenever the underlying element emits `sl-change`. `@EventOptions` controls what travels back with that event. Each `@EventData` inside it pairs a key with a JavaScript expression evaluated against the DOM event. The result is available in the Java event class through `getData().get(key)`.

The product review form below uses this pattern with [`sl-rating`](https://shoelace.style/components/rating). The custom `ChangeEvent` carries the rating value as a typed `double`, and the listener uses it to enable the submit button:

<ComponentDemo 
path='/webforj/rating' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Event options {#event-options}

`ElementEventOptions` configures the event payload, debounce or throttle timing, filter expressions, and pre-execution code. The snippet below shows the options:

```java
ElementEventOptions options = new ElementEventOptions()
  // Collect custom data from the client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // Execute JavaScript before event fires
  .setCode("component.classList.add('processing');")
  
  // Only fire if conditions are met
  .setFilter("component.value.length >= 2")
  
  // Delay execution until user stops typing (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Apply these options when registering a listener for a custom event class
// (see the Custom event classes section above for how to define one):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` exposes only the class-based form `addEventListener(Class, listener, options)`. Use it with an event class annotated with `@EventName`. To register against a string event name directly, call `getElement().addEventListener("input", listener, options)`.
:::

#### Performance control {#performance-control}

**Debouncing** delays execution until activity stops:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Wait 300ms after last event
```

Available debounce phases:

- `LEADING`: Fire immediately, then wait
- `TRAILING`: Wait for quiet period, then fire (default)
- `BOTH`: Fire immediately and after quiet period

**Throttling** limits execution frequency:

```java
options.setThrottle(100); // Fire at most once per 100ms
```

## Interacting with slots {#interacting-with-slots}

Slots are placeholders inside a web component that users fill with content. The web component declares its slots in its template with `<slot>` or `<slot name="...">`, and the wrapper exposes methods that put Java components into those slots.

To add content to slots, extend `ElementCompositeContainer` instead of `ElementComposite`. The container carries the same property and attribute machinery plus the methods needed to add children. Children added through `add()` go into the default slot. Children added through `getElement().add(slotName, components)` go into the named slot.

```java
@NodeName("my-dialog")
public class Dialog extends ElementCompositeContainer {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Dialog setHeading(String value) {
    set(heading, value);
    return this;
  }

  public Dialog addToFooter(Component... components) {
    getElement().add("footer", components);
    return this;
  }
}
```

The demo below shows two pricing cards built with [`sl-card`](https://shoelace.style/components/card), populating the `header`, default, and `footer` slots from Java:

<ComponentDemo 
path='/webforj/card' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### Inspecting slot contents {#inspecting-slot-contents}

The underlying `Element` (accessed through `getElement()`) provides methods for reading back what's currently assigned to slots:

- **`findComponentSlot()`**: searches all slots for a specific component and returns the name of the slot containing it, or an empty string if the component isn't in any slot.
- **`getComponentsInSlot()`**: returns the list of components assigned to a given slot. Optionally takes a class type to filter the results.
- **`getFirstComponentInSlot()`**: returns the first component assigned to a slot. Optionally takes a class type to filter.

