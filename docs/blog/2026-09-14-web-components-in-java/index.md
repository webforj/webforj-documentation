---
title: "Web components in Java: wrap any custom element with webforJ"
description: "Use web components in Java with webforJ: wrap a third-party custom element with ElementComposite, typed properties, event classes and slots."
slug: web-components-in-java
date: 2026-09-14
authors: Matthew Hawkins
tags: [components, javascript, annotations]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-14-web-components-in-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-14-web-components-in-java/cover.jpg)

To use web components in Java with webforJ, you write a class that extends `ElementComposite`, name the custom element with `@NodeName`, declare its properties as `PropertyDescriptor` fields, and expose typed Java methods and events. The result behaves like any other webforJ component, and you add it to a layout with `add()`.

webforJ's own components are built on web components, so this is the same mechanism in the open. This post wraps the Web Awesome rating element as a Java class, from loading the script to typed events and slots.

<!-- truncate -->

## Why wrap a web component instead of using it raw? {#why-wrap}

You could drop an `Element` into a view and set properties by string. That works for one-off DOM work. A wrapper is better when the element appears in more than one place, because the compiler then checks names and types and your IDE completes them.

If a built-in component such as `TextField` or `Button` already covers the need, use that. Reach for `ElementComposite` when the component you want isn't in webforJ. The [Element Composite guide](/docs/building-ui/element-composite) is the reference for everything below. The same wrapper approach works for [a React component turned into a custom element](/blog/wrap-react-component-java).

## Which custom element are we wrapping? {#the-element}

The [Web Awesome rating](https://webawesome.com/docs/components/rating/) is a star input registered as `wa-rating`. Its documented properties include `value` (default 0), `max` (default 5), `precision` (default 1), `readonly` and `disabled`. It dispatches a `change` event when the value changes and a `wa-hover` event while the pointer moves across the stars, with a `detail` that carries a `phase` and a `value`.

Those names are all you need. The wrapper is a mapping from that documentation to Java.

## How do you load the element's script? {#loading}

Two options exist. `@JavaScript` loads a script from a URL, and webforJ deduplicates repeat loads, so several components can depend on the same file. Pair it with `@StyleSheet` for CSS.

For an npm package, webforJ has a frontend bundler, available since 26.01. `@BundlePackage` declares the dependency, and `@BundleEntry` points at the modules you want from it. The build adds the package to your `package.json`, installs it, and compiles the entries you named. The [packages and assets page](/docs/managing-resources/bundler/npm-and-assets) covers the details.

```java
@NodeName("wa-rating")
@BundlePackage(value = "@awesome.me/webawesome", version = "^3.12.0")
@BundleEntry("@awesome.me/webawesome/dist/styles/themes/default.css")
@BundleEntry("@awesome.me/webawesome/dist/components/rating/rating.js")
public class Rating extends ElementComposite {
  // properties and events follow
}
```

`@NodeName` is required. It tells webforJ which tag to create, and the name must match the element registered in the browser.

## How do you map properties to Java? {#properties}

Declare each property as a private `PropertyDescriptor`, with the web component's name and a default value. The default also fixes the Java type. Then write public getters and setters that delegate to the protected `set()` and `get()` methods.

```java
private final PropertyDescriptor<Double> value = PropertyDescriptor.property("value", 0.0);
private final PropertyDescriptor<Integer> max = PropertyDescriptor.property("max", 5);
private final PropertyDescriptor<Double> precision =
    PropertyDescriptor.property("precision", 1.0);
private final PropertyDescriptor<Boolean> readonly =
    PropertyDescriptor.property("readonly", false);

public Rating setValue(double v) {
  set(value, v);
  return this;
}

public double getValue() {
  return get(value);
}

public Rating setPrecision(double p) {
  set(precision, p);
  return this;
}
```

`set()` pushes the value to the browser and caches it on the server, so `get()` returns the cached value without a round trip. If the value can change in the browser without the server knowing, such as text a user types, pass `true` as the second argument to `get()` to read from the client.

### Property or attribute? {#property-or-attribute}

`PropertyDescriptor.property()` binds to a JavaScript property on the element. `PropertyDescriptor.attribute()` binds to an HTML attribute. Check the component's documentation for which one it exposes. Using the wrong kind means the value is silently ignored.

Enum-like properties need one extra step. webforJ serializes enums with Gson, which writes the constant name in capitals by default, so annotate each constant with `@SerializedName("primary")` and the like to send the lowercase value the element expects.

## How do you listen to events with typed classes? {#events}

For built-in click handling, implement `HasElementClickListener`. For any other event the element dispatches, write an event class. `@EventName` binds it to the DOM event name, and `@EventOptions` with `@EventData` entries pairs a key with a JavaScript expression evaluated in the browser, so your Java event receives only the values you ask for.

```java
@EventName("wa-hover")
@EventOptions(data = {
    @EventData(key = "phase", exp = "event.detail.phase"),
    @EventData(key = "value", exp = "event.detail.value")})
public static class HoverEvent extends ComponentEvent<Rating> {

  public HoverEvent(Rating component, Map<String, Object> payload) {
    super(component, payload);
  }

  public String getPhase() {
    return String.valueOf(getData().get("phase"));
  }

  public double getHoverValue() {
    return ((Number) getData().get("value")).doubleValue();
  }
}

public ListenerRegistration<HoverEvent> onHover(EventListener<HoverEvent> listener) {
  return addEventListener(HoverEvent.class, listener);
}
```

Now a view can write `rating.onHover(e -> preview.setText("Hovering " + e.getHoverValue()))` with real types. `ElementComposite` only accepts event classes annotated with `@EventName`. To listen by a raw string name, go through `getElement().addEventListener(...)`.

You can also tune delivery with `ElementEventOptions`, which supports a debounce or throttle in milliseconds, a filter expression, and code that runs before the event fires. That helps for chatty events such as `input`.

## How do slots work? {#slots}

Many web components accept child content through named slots. The [Web Awesome card](https://webawesome.com/docs/components/card/) is registered as `wa-card` and documents slots named `header`, `footer`, `media` and more, plus a default slot for the body. To fill slots from Java, extend `ElementCompositeContainer` instead of `ElementComposite`. Children added with `add()` go to the default slot, and `getElement().add(slotName, components)` targets a named one.

```java
@NodeName("wa-card")
public class Card extends ElementCompositeContainer {

  public Card addToHeader(Component... components) {
    getElement().add("header", components);
    return this;
  }

  public Card addToFooter(Component... components) {
    getElement().add("footer", components);
    return this;
  }
}
```

You can then place any webforJ component, a `Button` or a `Paragraph`, inside a third-party element.

## Can you add the usual component features? {#concern-interfaces}

Yes. Concern interfaces add behavior without method bodies. `HasClassName`, `HasStyle` and `HasText` forward to the underlying element, so `implements HasStyle<Rating>` gives the wrapper `setStyle()` with chaining. They are listed in the [component fundamentals](/docs/building-ui/component-fundamentals) guide.

Once the wrapper is done, `PropertyDescriptorTester` checks in a unit test that every descriptor has a matching getter and setter and that defaults round-trip. The [testing page](/docs/testing/property-descriptor-tester) shows the call.

## Where to go from here {#next-steps}

Pick a web component you already use, find its property and event tables, and mirror them as descriptors and event classes. The [Element Composite guide](/docs/building-ui/element-composite) has the full reference with live demos, and [startforJ](https://docs.webforj.com/startforj) gives you a project to try your wrapper in.

## Frequently asked questions {#faq}

### Can I use any web component from npm in webforJ? {#faq-any-component}

Yes, any custom element works if you know its tag name, properties, and events. Declare the package with `@BundlePackage`, import the module with `@BundleEntry`, and map its API with `ElementComposite`.

### Do I need to write JavaScript to use a web component? {#faq-no-javascript}

Not for wrapping one. The element's own script comes from the package or a URL, and your code stays in Java. Use `@EventData` expressions only when you need a value from the browser event.

### How does `Element` differ from `ElementComposite`? {#faq-element-vs-composite}

`Element` is a general DOM wrapper for one-off use and accepts any string event name. `ElementComposite` is a reusable, typed component class that binds to a tag with `@NodeName` and exposes a Java API.

### Why is my property ignored by the web component? {#faq-ignored}

You probably bound an attribute where the element exposes a property, or the reverse. Check which one the component's documentation lists and switch between `PropertyDescriptor.property()` and `PropertyDescriptor.attribute()`.
