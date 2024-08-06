---
sidebar_position: 4
title: Element Composite
slug: element_composite
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

The `ElementComposite` class serves as a versatile foundation for managing composite elements in webforJ applications. Its primary purpose is to facilitate the interaction with HTML elements, represented by the `Element` class, by providing a structured approach to handle properties, attributes, and event listeners. It allows for implementation and reuse of elements in an application. Use the `ElementComposite` class when implementing Web Components for use in webforJ applications.

While using the `ElementComposite` class, using the `getElement()` method will give you access to the underlying `Element` component. Similarly, the `getNodeName()` method gives you the name of that node in the DOM. 

:::tip
It is possible to do everything with the `Element` class itself, without using `ElementComposite` class. However, the provided methods in the `ElementComposite` give users a way to reuse the work that's being done. 
:::

Throughout this guide, we'll be implementing the [Shoelace QR code web component](#) using the `ElementComposite` class.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=demos.webcomponents.elementcomposite.QRDemo' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/demos/webcomponents/elementcomposite/QRDemo.java'
height='175px'
/>

## Property and Attribute Descriptors

Properties and attributes in web components represent the state of the component. They are often used to manage data or configuration. The `ElementComposite` class provides a convenient way to work with properties and attributes.

Properties and attributes can be declared and initialized as `PropertyDescriptor` members of the `ElementComposite` class being written, and then used in the code. To define properties and attributes, use the `set()` method to set the value of a property. For example, `set(PropertyDescriptor<V> property, V value)` sets a property to a specified value. 

:::info
Properties are accessed and manipulated internally within the component's code and do not reflect in the DOM. Attributes on the other hand are part of the component's external interface and can be used to pass information into a component from the outside, providing a way for external elements or scripts to configure the component.
:::

```java
// Example property called TITLE in an ElementComposite class
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// Example attribute called VALUE in an ElementComposite class
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "My Title");
set(VALUE, "My Value");
```

In addition to setting a property, utilize the `get()` method in the `ElementComposite` class to access and read properties. The `get()` method can be passed an optional `boolean` value, which is false by default, to dictate whether the method should make a trip to the client to retrieve the value. This impacts performance, but might be necessary if the property can be modified purely in the client. 

A `Type` can also be passed to the method, which dictates what to cast retrieved result to.

:::tip
This `Type` is not overtly necessary, and adds an extra layer of specification as the data is retrieved.
:::

```java
// Example property called TITLE in an ElementComposite class
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

In the demo below, properties have been added for the QR code based on the documentation for the web component. Methods have then been implemented which allow users to get and set the various properties that have been implemented.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=demos.webcomponents.elementcomposite.QRProperties' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/demos/webcomponents/elementcomposite/QRProperties.java'
height='250px'
/>

## Event Registration

Events are a crucial part of web components, allowing communication between different parts of an application. The `ElementComposite` class simplifies event registration and handling. To register an event listener, use the `addEventListener()` method to register event listeners for specific event types. Specify the event class, the listener, and optional event options.

```java
// Example: Adding a click event listener
addEventListener(ClickEvent.class, event -> {
    // Handle the click event
});
```

:::info
The `ElementComposite` events are different than `Element` events, in that this doesn't allow any class, but only specified `Event` classes.
:::

In the demonstration below, a click event has been created and then added to the QR code component. This event, when fired, will display the "X" coordinate of the mouse at the time of clicking the component, which is provided to the Java event as data. A method is then implemented to allow the user to access this data, which is how it is displayed in the application.
<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=demos.webcomponents.elementcomposite.QREvent' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/demos/webcomponents/elementcomposite/QREvent.java'
height='300px'
/>

## Interacting with Slots

Web components often use slots to allow developers to define the structure of a component from the outside. A slot is a placeholder inside a web component that can be filled with content when using the component. In the context of the `ElementComposite` class, slots provide a way to customize the content within a component. The following methods are provided to allow developers to interact with and manipulate slots:

1. **`findComponentSlot()`**: This method is used to search for a specific component across all slots in a component system. It returns the name of the slot where the component is located. If the component is not found in any slot, an empty string is returned.

2. **`getComponentsInSlot()`**: This method retrieves the list of components assigned to a given slot in a component system. Optionally, pass a specific class type to filter the results of the method.

3. **`getFirstComponentInSlot()`**: This method is designed to fetch the first component assigned to the slot. Optionally pass a specific class type to filter the results of this method.

It is also possible to use the `add()` method with a `String` parameter to specify the desired slot in which to add the passed component.

These interactions allow developers to harness the power of web components by providing a clean and straightforward API for manipulating slots, properties, and handling events within the `ElementComposite` class.