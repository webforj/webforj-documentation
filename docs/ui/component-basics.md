---
sidebar_position: 1
title: Component Basics
slug: basics
draft: false
---

Components are fundamental building blocks that can be added to a window, providing user interface functionality and custom behavior. In the DWCj, the `Component` class serves as the foundation for all components within the engine.

## Lifecycle Management

Understanding the component lifecycle is essential for creating, managing, and utilizing components effectively. The following three lifecycle states have methods to manipulate their behavior. These methods should not explicitly be called by the user.

### Create and Destroy

All classes that extend the `Component` class are responsible for implementing the functionality to be executed when the `Component` is created, and when it is destroyed. This is done by overriding the `onCreate()` and `onDestroy()` methods, respectively.

#### Create

`onCreate()` is a method called when the component is created. Creating components involves setting up their initial state and functionality. This is where you define what the component should do when it's first created. Whether it's initializing variables, setting up event listeners, or performing any other setup, the `onCreate()` method is your entry point for customizing component behavior. 

:::tip
The `onCreate()` method is where the component and any constituents should be added to the window.
:::

#### Destroy

Destroying components is an essential part of managing resources and ensuring proper cleanup. Destroying a component is necessary when it's no longer needed or when you want to release resources associated with it. It allows a developer to perform cleanup tasks, such as stopping timers, releasing memory, or detaching event listeners. It also allows the `destroy()` method to be called on any constituent components.

:::tip
The `onDestroy()` method is responsible for calling the `destroy()` method on any constituent components. Otherwise, these components will still exist in the DOM, but will not be reachable via the API.
:::

### Asynchronous Attachment

While the `onAttach()` method is a protected method intended for use internally, the `whenAttached()` method returns a `PendingResult`, which allows for additional specified behavior to execute asynchronously once the component is attached in the DOM. 

:::tip
Unlike the previous three methods, `whenAttached()` is meant to be explicitly called by the user.
:::

```java
public class Demo extends App {
  @Override
  public void run() throws DwcjException {
    Frame window = new Frame();

    // onCreate() method is called
    Button button = new Button(); 

    /* Explicit call to whenAttached() which will display a 
    message box when the button is attached to the Frame.*/
    button.whenAttached().thenAccept( e -> msgbox("I'm attached!")); 
  
    // onAttach() method is called, which triggers the whenAttached PendingResult to resolve.
    window.add(button); 
  }
}
```

### Observers

Observers play a vital role in keeping track of component lifecycle events. Observers can be added and removed using the `addLifecycleObserver()` and `removeLifecycleObserver()` methods, and  receive notifications about events such as creation and destruction of components.

By adding observers, you can take action when a component is created, or destroyed. This is particularly useful for implementing custom logic or handling specific scenarios based on component events.

```java
Button button = new Button();
button.addLifecycleObserver((button, lifecycleEvent) -> {
  if (lifecycleEvent == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
    //implemented logic to execute when the Button is destroyed
  }
});
```

## Component Properties

### ID

Component IDs serve as unique identifiers for components, allowing you to interact with them and manage their state effectively.

#### Server-Side Component ID

Every component created from the `Component` class is assigned a server-side identifier automatically. Server-side IDs are essential for internal tracking and identification of components within the framework. You can retrieve the server-side component ID using the `getComponentId()` method.

#### Client-Side Component ID

While server-side component IDs are automatically generated, client-side component IDs need to be implemented by the user. The client-side ID represents the component's counterpart on the client side of your application.

:::important
This ID is **not** the ID attribute of the element in the DOM.
:::

<!-- Need demo from Hyyan on getting the client side ID with JS -->

### User Data

The `Component` class allows you to include additional information within the component using the `setUserData()` method. This information is accessible only on the server side of the component via the `getUserData()` method, and is not sent to the client. 

This is quite useful when there is information that should be included with a component, and when that information should be accessible without making a trip to the client to retrieve it.


<!-- 
- Need an idea for a demo fro the lifecycle observer
- Need demo from Hyyan on getting the client side ID with JS
 -->