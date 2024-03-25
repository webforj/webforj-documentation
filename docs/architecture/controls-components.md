---
sidebar_position: 10
title: BBj Controls and webforJ Components
---


The webforJ framework is designed to provide a Java API around the BBj language's DWC, offers a robust architecture for building and managing components. 

## Mapping BBj Controls to webforJ Components
One of the fundamental principles of webforJ is the binding of BBj controls with webforJ components. In this architecture, every webforJ component shipped with the product has a one-to-one mapping with an underlying BBj control. This mapping ensures that Java components mirror the behavior and properties of their BBj counterparts seamlessly.

This close correspondence between webforJ components and BBj controls simplifies development and allows Java developers to work with familiar concepts when building web-based applications without the need to write any BBj code.

## The `DwcComponent` Base Class
At the heart of webforJ's component architecture lies the DWCComponent base class. All webforJ components inherit from this class. This inheritance grants each webforJ component access to its underlying BBj control, providing a direct link between the Java component and the BBj control it represents.

However, it's important to note that developers are restricted from extending the DWCComponent class further. Attempting to do so will result in a runtime exception that disallows such extensions. This restriction is in place to maintain the integrity of the underlying BBj control and ensure that developers do not inadvertently manipulate it in ways that could lead to unintended consequences.

### Final Classes and Extension Restrictions
In webforJ, most component classes, with the exception of the built-in HTML Elements and any classes extending these, are declared as `final`. This means that they are not available for extension or subclassing. This design choice is deliberate and serves multiple purposes:

1. **Control Over Underlying BBj Control**: As mentioned earlier, extending webforJ component classes would grant developers control over the underlying BBj control. To maintain the consistency and predictability of component behavior, this level of control is restricted.

2. **Preventing Unintended Modifications**: Making the component classes `final` prevents unintentional modifications to core components, reducing the risk of introducing unexpected behaviors or vulnerabilities.

3. **Promoting the Use of Composites**: To extend the functionality of components, webforJ framework encourages developers to use a composite approach. Composite components are Java classes that contain other webforJ components or standard HTML elements. While traditional inheritance is discouraged, composite components offer a way to create new, customized components that encapsulate existing ones. 

<!-- ## Adding Components to the DOM
TODO: Talk about how webforJ will search for a BBj control - means you can't add something that doesn't have a control -->

## Composite Components: Extending Through Composition
In webforJ framework, the concept of composite components plays a pivotal role in extending component functionality. Composite components are Java classes that are not restricted by the final keyword, allowing developers to create new components that extends the behavior of a single component, or combines multiple components into one, by composing existing components. Classes which facilitate this behavior have been created for developer use. See the `Composite` and `ElementComposite` sections to see how to properly create composite components.

This approach encourages a more modular and flexible development style, enabling developers to build tailored components that meet specific requirements.

<!-- 
## Component Hierarchy
TODO: Create and show graphic -->