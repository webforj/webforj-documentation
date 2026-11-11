---
sidebar_position: 10
title: Component Architecture
description: See how webforJ components are built on the DwcComponent base class, why component classes are final, and why composition replaces inheritance.
---


webforJ gives you a Java API for a library of web components and manages each one from the server. This page explains how components are structured and how to extend them.

## How components are structured {#how-components-are-structured}
Every webforJ component shipped with the product is a Java class paired one-to-one with a client-side web component from the DWC component library. The Java class holds the state and the API of the component on the server, and the framework keeps the web component in the browser in sync with it.

You work only with the Java class. Properties, events, and rendering in the browser are handled by the framework, so you build web apps in Java alone.

## The `DwcComponent` base class {#the-dwccomponent-base-class}
At the heart of webforJ's component architecture lies the DWCComponent base class. All webforJ components inherit from this class. It manages the link between the Java component and its client-side counterpart, including creation, property updates, and events.

However, it's important to note that developers are restricted from extending the DWCComponent class further. Attempting to do so will result in a runtime exception that disallows such extensions. This restriction is in place to maintain the integrity of that link and ensure that developers do not inadvertently manipulate it in ways that could lead to unintended consequences.

### Final classes and extension restrictions {#final-classes-and-extension-restrictions}
In webforJ, most component classes, with the exception of the built-in HTML Elements and any classes extending these, are declared as `final`. This means that they are not available for extension or subclassing. This design choice is deliberate and serves multiple purposes:

1. **Control over the client-side link**: As mentioned earlier, extending webforJ component classes would expose the internal link between the Java component and its client-side counterpart. To maintain the consistency and predictability of component behavior, this level of control is restricted.

2. **Preventing Unintended Modifications**: Making the component classes `final` prevents unintentional modifications to core components, reducing the risk of introducing unexpected behaviors or vulnerabilities.

3. **Promoting the Use of Composites**: To extend the functionality of components, webforJ framework encourages developers to use a composite approach. Composite components are Java classes that contain other webforJ components or standard HTML elements. While traditional inheritance is discouraged, composite components offer a way to create new, customized components that encapsulate existing ones.

<!-- ## Adding Components to the DOM
TODO: Talk about how webforJ resolves the client-side counterpart of a component -->

## Composite components: extending through composition {#composite-components-extending-through-composition}
In webforJ framework, the concept of composite components plays a pivotal role in extending component functionality. Composite components are Java classes that are not restricted by the final keyword, allowing developers to create new components that extends the behavior of a single component, or combines multiple components into one, by composing existing components. Classes which facilitate this behavior have been created for developer use. See the `Composite` and `ElementComposite` sections to see how to properly create composite components.

This approach encourages a more modular and flexible development style, enabling developers to build tailored components that meet specific requirements.

<!--
## Component Hierarchy {#component-hierarchy}
TODO: Create and show graphic -->