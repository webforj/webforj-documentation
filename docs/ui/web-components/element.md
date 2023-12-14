---
sidebar_position: 1
title: Element Class
slug: element
draft: false
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';


In DWCj, developers have the option of choosing not only from the rich library of components provided, but also integrating components from elsewhere. To facilitate this, the `Element` class can be used to simplify the integration of anything from simple HTML elements, to more complex custom web components. 

:::important
The `Element` class cannot be extended, and is not the base class for all components within the DWCj. To read more about the DWCj's class hierarchy, read [this article](../../architecture/controls-components.md).
:::

## Creating a Web Element

The `Element` component has three constructors provided to quickly and efficiently create web elements in your DWCj program.

1. `Element()` : This parameterless constructor will create a `Div` element that can be added to your application. 

2. `Element(String node)`: This constructor takes a single `String` argument. This argument should be the name of the HTML node you want to create, which will then create that desired element that can be added to your application.

3. `Element(String node, String html)`: Using this constructor allows you to specify the desired node name, as well as include HTML content to be included in the element. 

The below example will create an HTML `<details>` tag:

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples/DetailsConstructor' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/demos/webcomponents/DetailsConstructor.java'
height='175px'
/>

## Adding Events

In order to utilize events that may come with your element, you can use the `Element` component's `addEventListener` methods.

Adding an event requires at least the type/name of the event the component expects, and a listener to be added to the event. 

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples/DetailsEvent' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/demos/webcomponents/DetailsEvent.java'
height='175px'
/>

There are also additional options to further customize events by using the [Event Options](#) configurations.

## Slot Manipulation

The `Element` class supports the composition of child components within specified slots. Developers can organize and manage complex UI structures by adding components as children to the `Element`.

### Adding to a Slot

Three methods exist to set content within the an `Element`:

1. **`add()`**: This method will allow one or multiple components to be added to an optional `String` which designates a specified slot. Omitting the slot will add the component between the HTML tags.

2. **`setHtml()`**: This method take the `String` passed to the method and inject it as HTML within the component. Depending on the `Element`, this may be rendered in different ways.

3. **`setText()`**: This method behaves similarly to the `setHtml()` method, but injects literal text into the `Element`.

In the demo below, two `detail` HTML elements are created using the `Element` component. The first has its contents set using the `setText()` and `add()` methods, while the second has HTML injected into it directly using the `setHtml()` method.

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples/DetailsSlots' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/demos/webcomponents/DetailsSlots.java'
height='175px'
/>

:::tip
Calling `setHtml()` or `setText()` will replace content currently contained between the element's opening and closing tags.
:::

### Slot Interaction

Various methods which allow for interaction with slots and their various contained `Component` objects exist. 

1. **`findComponentSlot()`**: This method is used to search for a specific component across all slots in a component system. It returns the name of the slot where the component is located. If the component is not found in any slot, an empty string is returned.

2. **`getComponentsInSlot()`**: This method retrieves the list of components assigned to a given slot in a component system. Optionally, pass a specific class type to filter the results of the method.

3. **`getFirstComponentInSlot()`**: This method is designed to fetch the first component assigned to the slot. Optionally pass a specific class type to filter the results of this method.

## Calling JavaScript Functions

The `Element` class provides two API methods which allow for JavaScript functions to be called on HTML elements: 

1. **`callJsFunction()`**: This method takes a function name as a string, and optionally takes one or more Objects as parameters for the function. This method is executed synchronously, meaning that the executing thread is blocked until the JS method returns. The results of the function are returned to this 

2. **`callJsFunctionAsync()`**: As with the previous method, a function name and optional arguments for the function can be passed. This method executes asynchronously and does not block the executing thread. It returns a [`PendingResult`](#), which allows for further interaction with the function and its payload.

In the demo below, an event is added to an HTML `Button`. This event is then fired programmatically by calling the `callJsFunctionAsync()` method. The resulting `PendingResult` is then used to create another message box once the asynchronous function has been completed.

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples/ButtonFunction' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/demos/webcomponents/ButtonFunction.java'
height='175px'
/>

## Executing JavaScript

In addition to executing JavaScript from the application level, it is also possible to execute JavaScript from the Element level as well. Performing this execution at the element level allows the context of the HTML element to be included. 

JavaScript can be executed synchronously or asynchronously, with the synchronous version returning an object, and the asynchronous version returning a [`PendingResult`](#). 

Any JavaScript code executed at the Element level will have access to two special argument types: `this` and `component`. `this` is replaced with the current instance of the client element, while `component` is replaced with its corresponding client component instance, provided it is attached to the DOM.


