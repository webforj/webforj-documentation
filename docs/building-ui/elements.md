---
sidebar_position: 3
title: Elements
slug: element
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ developers have the option of choosing not only from the rich library of components provided, but also integrating components from elsewhere. To facilitate this, the `Element` component can be used to simplify the integration of anything from simple HTML elements, to more complex custom web components. 

:::important
The `Element` component cannot be extended, and is not the base component for all components within webforJ. To read more about webforJ's component hierarchy, read [this article](../architecture/controls-components.md).
:::

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
/>

## Adding Events

In order to utilize events that may come with your element, you can use the `Element` component's `addEventListener` methods. Adding an event requires at least the type/name of the event the component expects, and a listener to be added to the event. 

There are also additional options to further customize events by using the Event Options configurations.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
height='175px'
/>

## Component Interaction

The `Element` component acts like a container for other components. It's provides a way to organize and retrieve information for child components, and offers a clear set of functions to add or remove these child components as needed.


### Adding Child Components

The `Element` component supports the composition of child components. Developers can organize and manage complex UI structures by adding components as children to the `Element`. Three methods exist to set content within the an `Element`:

1. **`add(Component... components)`**: This method will allow one or multiple components to be added to an optional `String` which designates a specified slot when used with a Web Component. Omitting the slot will add the component between the HTML tags.

2. **`setHtml(String html)`**: This method take the `String` passed to the method and inject it as HTML within the component. Depending on the `Element`, this may be rendered in different ways.

3. **`setText(String text)`**: This method behaves similarly to the `setHtml()` method, but injects literal text into the `Element`.


<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/elementinputtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputTextView.java'
height='175px'
/>

:::tip
Calling `setHtml()` or `setText()` will replace content currently contained between the element's opening and closing tags.
:::

### Removing Components

In addition to adding components to an `Element`, the following methods are implemented for removal of various child components:

1. **`remove(Component... components)`**: This method takes one or more components and will remove them as child components.

2. **`removeAll()`**: This method will remove all child components from the `Element`.

### Accessing Components

To access the various child components present within an `Element`, or information regarding these components, the following methods are available:

1. **`getComponents()`**: This method returns a Java `List` of all children of the `Element`. 

2. **`getComponents(String id)`**: This method is similar to the method above, but will take the server-side ID of a specific component and return it when found.

3. **`getComponentCount()`**: Returns the number of child components present within the `Element`. 


## Calling JavaScript Functions

The `Element` component provides two API methods which allow for JavaScript functions to be called on HTML elements. 

1. **`callJsFunction(String functionName, Object... arguments)`**: This method takes a function name as a string, and optionally takes one or more Objects as parameters for the function. This method is executed synchronously, meaning that the **executing thread is blocked** until the JS method returns, and results in a round trip. The results of the function are returned as an `Object`, which can be casted and used in Java. 

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: As with the previous method, a function name and optional arguments for the function can be passed. This method executes asynchronously and **does not block the executing thread**. It returns a [`PendingResult`](#), which allows for further interaction with the function and its payload.

### Passing Parameters

Arguments that are passed to these methods which are used in the execution of JS functions are serialized as a JSON array. There are two notable arguments types that are handled as follows:
- `this`: Using the `this` keyword will give the method a reference to the client-side version of the invoking component.
- `Component`: Any Java component instances passed into one of the JsFunction methods will be replaced with the client-side version of the component.

:::info
Both synchronous and asynchronous function calling will wait to call a method until the `Element` has been added to the DOM before executing a function, but `callJsFunction()` will not wait for any `component` arguments to attach, which can result in failure. Conversely, invoking `callJsFunctionAsync()` may never complete if a component argument is never attached.
:::

In the demo below, an event is added to an HTML `Button`. This event is then fired programmatically by calling the `callJsFunctionAsync()` method. The resulting [`PendingResult`](#) is then used to create another message box once the asynchronous function has been completed.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
height='175px'
/>

## Executing JavaScript

In addition to executing JavaScript from the application level, it is also possible to execute JavaScript from the `Element` level as well. Performing this execution at the `Element` level allows the context of the HTML element to be included in the execution. This is a powerful tool that acts as a developer's conduit to interactive capabilities with client-side environments.

Similar to function execution, executing JavaScript can be done with synchronously or asynchronously with the following methods:

1. **`executeJs(String script)`**: This method takes `String`, which will be executed as JavaScript code in the client. This script is executed synchronously, meaning that the **executing thread is blocked** until the JS execution returns and results in a round trip. The results of the function are returned as an `Object`, which can be casted and used in Java.

2. **`executeJsAsync(String script)`**: As with the previous method, a passed `String` parameter will be executed as JavaScript code on the client. This method executes asynchronously and **does not block the executing thread**. It returns a [`PendingResult`](#), which allows for further interaction with the function and its payload.

:::tip
These methods have access to the `component` keyword, which gives the JavaScript code access to the client-side instance of the component executing the JavaScript.
:::
