---
sidebar_position: 4
title: Element Composite
slug: element_composite
---

<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>


The `ElementComposite` class serves as a versatile foundation for managing composite elements in webforJ apps. Its primary purpose is to facilitate the interaction with HTML elements, represented by the `Element` class, by providing a structured approach to handle properties, attributes, and event listeners. It allows for implementation and reuse of elements in an app. Use the `ElementComposite` class when implementing Web Components for use in webforJ apps.

While using the `ElementComposite` class, using the `getElement()` method will give you access to the underlying `Element` component. Similarly, the `getNodeName()` method gives you the name of that node in the DOM. 

:::tip
It's possible to do everything with the `Element` class itself, without using `ElementComposite` class. However, the provided methods in the `ElementComposite` give users a way to reuse the work that's being done. 
:::


## Annotations

The `ElementComposite` class supports several annotations to simplify integration with web components:

- **@NodeName**: Defines the custom HTML tag for your component. For example, `@NodeName("sl-qr-code")` will create a `<sl-qr-code>` element in the DOM.
- **@JavaScript**: Loads external JavaScript resources (such as third-party web components). Example:
  ```java
  @JavaScript(value = "https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace/dist/shoelace.js", attributes = {@Attribute(name = "type", value = "module")})
  ```
- **@StyleSheet**: Loads external CSS files for your component. Example:
  ```java
  @StyleSheet("https://cdn.example.com/library.css")
  ```

These annotations are typically placed on your `ElementComposite` subclass to make sure resources are loaded automatically when the component is used.


This guide demonstrates integration of the [Shoelace QR code web component](https://shoelace.style/components/qr-code) using the `ElementComposite` class.
## Concern Interfaces

To add common behaviors to your custom element, implement the appropriate concern interfaces. For example:

- **HasText**: Adds support for getting and setting text content.
- **HasClassName**: Adds support for manipulating CSS class names.
- **HasStyle**: Adds support for inline styles.

You can implement multiple interfaces to compose the desired capability. Example:
```java
@NodeName("my-component")
public final class MyComponent extends ElementComposite
    implements HasText<MyComponent>, HasClassName<MyComponent>, HasStyle<MyComponent> {
    // ...
}
```
## `ElementCompositeContainer` for components with slots

For components that need to manage child components in named slots (such as headers, footers, or content areas), extend `ElementCompositeContainer` instead of `ElementComposite`.

`ElementCompositeContainer` provides a structured way to add, remove, and manage components in specific slots. For example:

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

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>


## Property and attribute descriptors

Properties and attributes in web components represent the state and configuration of a component. In webforJ, the `ElementComposite` class provides a structured way to define, set, and get properties and attributes using `PropertyDescriptor`.

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

**Example:**
```java
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
private final PropertyDescriptor<Integer> COUNT = PropertyDescriptor.attribute("count", 0);
```

### Setting and getting values

Use the `set()` method to assign a value, and the `get()` method to retrieve it:
```java
set(TITLE, "My Title");
String title = get(TITLE);
```

You can also use the overloaded `get()` method to specify whether to fetch the value from the client, and to cast to a specific type:
```java
String title = get(TITLE, false, String.class);
```

### Best practices for validating properties

To make sure only valid values are used, add validation logic in your setter methods or before calling `set()`. For example:
```java
public void setCount(int count) {
    if (count < 0) {
        throw new IllegalArgumentException("Count must be non-negative");
    }
    set(COUNT, count);
}
```

### Enum-style properties

For properties that should only accept a fixed set of values, use Java enums. Define the enum, then use it as the type parameter for your `PropertyDescriptor`:

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

## Event registration

Events are a crucial part of web components, allowing communication between different parts of an app. The `ElementComposite` class simplifies event registration and handling. To register an event listener, use the `addEventListener()` method to register event listeners for specific event types. Specify the event class, the listener, and optional event options.

```java
// Example: Adding a click event listener
addEventListener(ClickEvent.class, event -> {
    // Handle the click event
});
```

:::info
The `ElementComposite` events are different than `Element` events, in that this doesn't allow any class, but only specified `Event` classes.
:::

In the demonstration below, a click event has been created and then added to the QR code component. This event, when fired, will display the "X" coordinate of the mouse at the time of clicking the component, which is provided to the Java event as data. A method is then implemented to allow the user to access this data, which is how it is displayed in the app.
<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## Interacting with slots

Web components often use slots to allow developers to define the structure of a component from the outside. A slot is a placeholder inside a web component that can be filled with content when using the component. In the context of the `ElementComposite` class, slots provide a way to customize the content within a component. The following methods are provided to allow developers to interact with and manipulate slots:

1. **`findComponentSlot()`**: This method is used to search for a specific component across all slots in a component system. It returns the name of the slot where the component is located. If the component is not found in any slot, an empty string is returned.

2. **`getComponentsInSlot()`**: This method retrieves the list of components assigned to a given slot in a component system. Optionally, pass a specific class type to filter the results of the method.

3. **`getFirstComponentInSlot()`**: This method is designed to fetch the first component assigned to the slot. Optionally pass a specific class type to filter the results of this method.

It is also possible to use the `add()` method with a `String` parameter to specify the desired slot in which to add the passed component.

These interactions allow developers to harness the power of web components by providing a clean and straightforward API for manipulating slots, properties, and handling events within the `ElementComposite` class.