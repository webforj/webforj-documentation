---
sidebar_position: 4
title: Element Composite
slug: element_composite
---

<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

The `ElementComposite` class provides a structured base for building reusable web components in webforJ. It allows you to easily define properties, attributes, and event listeners of the underlying HTML elements in a type-safe, maintainable way. Use `ElementComposite` to encapsulate and integrate custom elements or third-party web components within your app.

While using the `ElementComposite` class, using the `getElement()` method will give you access to the underlying `Element` component. Similarly, the `getNodeName()` method gives you the name of that node in the DOM. 

:::tip
While it's possible to do everything with the `Element` class itself, the provided methods in the `ElementComposite` give you a way to reuse the work that's being done. 
:::

:::info
This guide uses the [Shoelace QR code web component](https://shoelace.style/components/qr-code) to show how to integrate third-party web components.
:::

## Annotations

The `ElementComposite` class supports several annotations to simplify integration with web components:

- **@NodeName**: Defines the custom HTML tag for your component. For example, `@NodeName("sl-qr-code")` will create a `<sl-qr-code>` element in the DOM.

- **@JavaScript**: Loads external JavaScript resources (such as third-party web components):

  ```java
  @JavaScript(value = "https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.0.0-beta.87/dist/shoelace.js",
    attributes = {@Attribute(name = "type", value = "module")})
  ```

- **@StyleSheet**: Loads external CSS files for your component:

  ```java
  @StyleSheet(url = "https://cdn.example.com/library.css")
  ```

:::tip
These annotations are typically placed on the subclass extending the `ElementComposite` class to make sure it's loaded automatically when the component is used.
:::

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Concern Interfaces

To add common behaviors to your custom element, implement the appropriate concern interfaces. For example:

- **`HasText`**: Adds support for getting and setting text content.
- **`HasClassName`**: Adds support for manipulating CSS class names.
- **`HasStyle`**: Adds support for inline styles.

You can implement multiple interfaces to compose the desired capability:

```java
@NodeName("my-component")
public final class MyComponent extends ElementComposite
    implements HasText<MyComponent>, HasClassName<MyComponent>, HasStyle<MyComponent> {
    // ...
}
```

## `ElementCompositeContainer` for components with slots

For components that need to manage child components in named slots (such as headers, footers, or content areas), extend <JavadocLink type="foundation" location="com/webforj/component/element/ElementCompositeContainer" code='true' >ElementCompositeContainer</JavadocLink> instead of `ElementComposite`.

`ElementCompositeContainer` provides a structured way to add, remove, and manage components in specific slots:

```java
@NodeName("my-container")
public final class MyContainer extends ElementCompositeContainer
    implements HasClassName<MyContainer>, HasStyle<MyContainer> {
    private static final String HEADER_SLOT = "header";
    private static final String CONTENT_SLOT = "content";
    private static final String FOOTER_SLOT = "footer";

    public MyContainer() {
        super();
    }

    public MyContainer addToHeader(Component... components) {
        getElement().add(HEADER_SLOT, components);
        return this;
    }

    public MyContainer addToContent(Component... components) {
        add(components); // Default slot
        return this;
    }

    public MyContainer addToFooter(Component... components) {
        getElement().add(FOOTER_SLOT, components);
        return this;
    }
}
```

Use `ElementCompositeContainer` whenever your component needs to support multiple content areas or slots, such as layouts, dialogs, or toolbars.


## Property and attribute descriptors {#property-and-attribute-descriptors}

Properties and attributes in web components represent the state and configuration of a component. You can define, set, and get properties and attributes using the <JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptor" code='true' >PropertyDescriptor</JavadocLink> class.

### Defining properties and attributes

To define a property or attribute, declare a `PropertyDescriptor` as a field in your `ElementComposite` subclass. Use `PropertyDescriptor.property("name", defaultValue)` for properties and `PropertyDescriptor.attribute("name", defaultValue)` for attributes.

**Syntax:**
```java
// For a property (not reflected in the DOM)
private final PropertyDescriptor<Type> PROPERTY_NAME = PropertyDescriptor.property("property-name", defaultValue);

// For an attribute (reflected in the DOM)
private final PropertyDescriptor<Type> ATTRIBUTE_NAME = PropertyDescriptor.attribute("attribute-name", defaultValue);
```

### Defining the type of properties

The generic type parameter `<Type>` specifies the Java type of the property or attribute. This guarantees type safety when setting or getting values.

```java
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
private final PropertyDescriptor<Integer> COUNT = PropertyDescriptor.attribute("count", 0);
```

### Setting and getting values

Use the `set()` method to assign a value, and the `get()` method to retrieve it.

```java
set(TITLE, "My Title");
String title = get(TITLE);
```

You can also use the overloaded `get()` method to specify whether to fetch the value from the client, and to cast to a specific type.

```java
String title = get(TITLE, false, String.class);
```

### Best practices for validating properties

To guarantee valid values, add validation logic in your setter methods or before calling `set()`:

```java
public void setCount(int count) {
    if (count < 0) {
        throw new IllegalArgumentException("Count must be non-negative");
    }
    set(COUNT, count);
}
```
This approach helps prevent an invalid state.

### Enum-style properties

For properties that should only accept a fixed set of values, use Java enums. Define the enum, then use it as the type parameter for your `PropertyDescriptor`.

```java
public enum Status {
    ACTIVE, INACTIVE, DISABLED
}

private final PropertyDescriptor<Status> STATUS = PropertyDescriptor.attribute("status", Status.ACTIVE);

// Usage
set(STATUS, Status.INACTIVE);
Status status = get(STATUS);
```


<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Event registration {#event-registration}

Events enable communication between parts of your app and create interactive components. `ElementComposite` makes event handling straightforward: register listeners for specific event types using `addEventListener()` with the event class, your listener, and optional options.

```java
// Example: Adding a click event listener
addEventListener(ClickEvent.class, event -> {
    // Handle the click event
});
```

:::info
The `ElementComposite` events are different from `Element` events, in that this doesn't allow any class, but only specified `Event` classes.
:::

In the following demonstration, a click event has been created and then added to the QR code component. This event, when fired, will display the "X" coordinate of the mouse at the time of clicking the component, which is provided to the Java event as data. A method is then implemented to allow the user to access this data, which is how it's displayed in the app.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## Interacting with Slots {#interacting-with-slots}

Web components often use slots to allow developers to define the structure of a component from the outside. A slot is a placeholder inside a web component that can be filled with content when using the component. In the context of the `ElementComposite` class, slots provide a way to customize the content within a component. The following methods are provided to allow developers to interact with and manipulate slots:

1. **`findComponentSlot()`**: This method is used to search for a specific component across all slots in a component system. It returns the name of the slot where the component is located. If the component isn't found in any slot, an empty string is returned.

2. **`getComponentsInSlot()`**: This method retrieves the list of components assigned to a given slot in a component system. Optionally, pass a specific class type to filter the results of the method.

3. **`getFirstComponentInSlot()`**: This method is designed to fetch the first component assigned to the slot. Optionally pass a specific class type to filter the results of this method.

It's also possible to use the `add()` method with a `String` parameter to specify the desired slot in which to add the passed component.

These interactions allow developers to harness the power of web components by providing a clean and straightforward API for manipulating slots, properties, and handling events within the `ElementComposite` class.