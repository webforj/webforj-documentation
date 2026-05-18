---
sidebar_position: 6
title: Element Composite
sidebar_class_name: new-content
---

<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

The `ElementComposite` class wraps a custom HTML element or web component. It binds your Java class to the underlying `Element` and lets you work with that element's properties, attributes, and events through Java. Use it when integrating Web Components into a webforJ app.

Inside a subclass, `getElement()` returns the underlying `Element`, and `getNodeName()` returns its DOM tag name.

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

Declare a `PropertyDescriptor` field, then use `set()` to assign a value. The signature is `set(PropertyDescriptor<V> property, V value)`.

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

Use `get()` to read a property. An optional `boolean` parameter (false by default) controls whether the read goes to the client. Reading from the client adds a network round trip but is necessary when the value can change purely on the client side.

The third parameter, a `Type`, controls how the result is cast.

:::tip
The `Type` parameter is rarely needed. The descriptor's generic type usually carries enough information for the cast.
:::

```java
// Example property called "title" in an ElementComposite class
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
//...
String currentTitle = get(title, false, String.class);
```

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

The generic parameter on `PropertyDescriptor<T>` declares the value's Java type. webforJ uses this information to serialize and deserialize values when communicating with the client.

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

For the full set of available interfaces and what each one provides, see [Concern interfaces](./component-fundamentals#concern-interfaces) in the Understanding Components article. If a default forwarding doesn't match what the wrapped element exposes, override the method in the subclass.

## Event registration {#event-registration}

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

webforJ provides pre-built event classes with typed data access:

- **`ElementClickEvent`**: Mouse click events with coordinates (`getClientX()`, `getClientY()`), button information (`getButton()`), and modifier keys (`isCtrlKey()`, `isShiftKey()`, etc.). Register through `addEventListener(ElementClickEvent.class, ...)`.
- **`ElementDefinedEvent`**: Fired when a custom element is defined in the DOM and ready for use. This event is dispatched on the underlying `Element`, so listen for it through `getElement()`.
- **`ElementEvent`**: The framework's raw event payload class, carrying the event type (`getType()`), event ID (`getId()`), and options. You typically work with `ElementClickEvent` or a custom event class instead of subscribing to `ElementEvent` directly.

### Event payloads {#event-payloads}

Events carry data from the client to your Java code. Access this data through `getData()` for raw event data or use typed methods when available on built-in event classes. See the [Events guide](../building-ui/events) for more on efficient payload handling.

## Custom event classes {#custom-event-classes}

Define custom event classes with `@EventName` and `@EventOptions` to capture client-side data in a typed Java event.

The product review form below uses this pattern with [`sl-rating`](https://shoelace.style/components/rating). The custom `ChangeEvent` carries the rating value as a typed `double`, and the listener uses it to enable the submit button:

<ComponentDemo 
path='/webforj/rating' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

## `ElementEventOptions` {#elementeventoptions}

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
// (see [Custom event classes](#custom-event-classes) for how to define one):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` exposes only the class-based form `addEventListener(Class, listener, options)`. Use it with an event class annotated with `@EventName`. To register against a string event name directly, call `getElement().addEventListener("input", listener, options)`.
:::

### Performance control {#performance-control}

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

## Options merging {#options-merging}

Use `mergeWith()` to combine event configurations. Later options override conflicting settings.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interacting with slots {#interacting-with-slots}

Slots are placeholders inside a web component that consumers fill with content. The following methods on the underlying `Element` (accessed through `getElement()`) work with slots:

1. **`findComponentSlot()`**: Searches all slots for a specific component and returns the name of the slot containing it. Returns an empty string if the component isn't in any slot.

2. **`getComponentsInSlot()`**: Returns the list of components assigned to a given slot. Optionally pass a class type to filter the results.

3. **`getFirstComponentInSlot()`**: Returns the first component assigned to a slot. Optionally pass a class type to filter.

The `add()` method also accepts a `String` slot name as its first argument.

### `ElementCompositeContainer` {#elementcompositecontainer}

`ElementComposite` is the right base class when the component is a leaf node with no children, like a button or a badge. When the component should accept child components into named or default slots, extend `ElementCompositeContainer` instead. It carries the same property and attribute machinery plus the methods needed to add children.

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

Children added through `add()` go into the default slot. Children added through `getElement().add(slotName, components)` go into the named slot. The web component declares its slots the same way any custom element does, with `<slot name="footer">` in its template.

The demo below shows two pricing cards built with [`sl-card`](https://shoelace.style/components/card), populating the `header`, default, and `footer` slots from Java:

<ComponentDemo 
path='/webforj/card' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>
 

## Testing properties {#testing-properties}

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

### Excluding properties {#excluding-properties}

Some descriptors don't follow standard getter and setter conventions, or they rely on external state the test can't satisfy. Annotate them with `@PropertyExclude` to skip them.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

### Custom getter and setter names {#custom-getter-and-setter-names}

If a descriptor uses non-standard accessor names, declare them with `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

The `target` parameter accepts a class when the accessors live somewhere other than the component itself.

For more detail on the testing surface, see [PropertyDescriptorTester](../testing/property-descriptor-tester).