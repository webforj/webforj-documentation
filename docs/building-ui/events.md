---
sidebar_position: 10
title: Events
slug: events
draft: false
---

<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Components, whether custom or part of the framework, support event handling. You can add event listeners to capture various types of events, such as user interactions, changes in state, or other custom events. These event listeners can be used to trigger specific actions or behaviors in response to the events.

In the example below, an event is being added using each of the three supported methods: lambda expressions, anonymous classes, and method references.
## Adding events

Adding an event listener is possible use one of the following patterns where:

- **`myComponent`** is the component to which you want to attach the event listener.

- **`addEventListener`** is replaced with the event-specific method.

- **`EventListener`** is replaced with the type of event being listened for.

```java
myComponent.addEventListener(e -> {
  //Executed when the event fires
});

//OR

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    //Executed when the event fires
  }
});

//OR

myComponent.addEventListener(this::eventMethod);
```

Additional syntactic sugar methods, or aliases, have been added to allow for alternative addition of events by using the `on` prefix followed by the event, such as:

```java
myComponent.onEvent(e -> {
  //Executed when the event fires
});
```

## Removing an event

When adding an event listener, a `ListenerRegistration` object will be returned. This can be used, among other things, to remove the event later on.

```java
//Adding the event
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
        //Executed when the event fires
    });

//Removing the event
listenerRegistration.remove();
```

## Using event payload

It's important to note that events often come with a payload, which contains additional information related to the event. You can efficiently utilize this payload within the event handler to access relevant data without making unnecessary round trips between the client and server. By doing so, you can improve the performance of your application.

The following code snippet queries the component to get information that, for our demonstration's purposes, is already included in the event payload, representing inefficient code:

```java
myComponent.addEventListener(e -> {
  // Access data from component
  String componentText = e.getComponent().getText();

  //OR if the component is accessible within the scope of the function
  String componentText = myComponent.getText();

  // Use the componentText to perform other actions.
});
```

Instead, utilizing the payload of the method, which for the sake of the example includes the text of the component, a roundtrip is avoided:

```java
myComponent.addEventListener(e -> {
  // Access data from the event payload
  String componentText = e.getText();
  
  // Use the componentText to perform other actions.
});
```

This approach minimizes the need to query the component for information, as the data is readily available in the event payload. By following this efficient event handling practice, you can enhance the performance and responsiveness of your components. For more information, you can refer to the documentation on [architecture](../architecture/architecture.md).

### Sample

Below is a demonstration showing the addition of a <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> to a [`Button`](#). This  [`Button`](#) also uses information coming with the event's payload to display information on the screen.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/button/Event.txt'
height='100px'
/>

<!-- <EventTable base events={['drawerOpen', 'drawerClose']} /> -->