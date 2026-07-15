---
sidebar_position: 10
title: Events
description: >-
  Attach event listeners to webforJ components with lambdas, anonymous classes,
  or method references and reuse the event payload server-side.
slug: events
draft: false
_i18n_hash: e965d354159ccc38ad417700fc3686eb
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Los componentes, ya sean personalizados o parte del marco, admiten el manejo de eventos. Puedes agregar oyentes de eventos para capturar varios tipos de eventos, como interacciones del usuario, cambios en el estado u otros eventos personalizados. Estos oyentes de eventos pueden usarse para desencadenar acciones o comportamientos específicos en respuesta a los eventos.

En el siguiente ejemplo, se está agregando un evento utilizando cada uno de los tres métodos compatibles: expresiones lambda, clases anónimas y referencias a métodos.
## Agregando eventos {#adding-events}

Agregar un oyente de eventos es posible utilizando uno de los siguientes patrones donde:

- **`myComponent`** es el componente al que deseas adjuntar el oyente de eventos.

- **`addEventListener`** es reemplazado por el método específico del evento.

- **`EventListener`** es reemplazado por el tipo de evento al que se está escuchando.

```java
myComponent.addEventListener(e -> {
  //Ejecutado cuando se activa el evento
});

//O

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    //Ejecutado cuando se activa el evento
  }
});

//O

myComponent.addEventListener(this::eventMethod);
```

Se han agregado métodos adicionales de azúcar sintáctica, o alias, para permitir una adición alternativa de eventos utilizando el prefijo `on` seguido del evento, como:

```java
myComponent.onEvent(e -> {
  //Ejecutado cuando se activa el evento
});
```

## Removiendo un evento {#removing-an-event}

Al agregar un oyente de eventos, se devolverá un objeto `ListenerRegistration`. Este se puede usar, entre otras cosas, para eliminar el evento más tarde.

```java
//Agregando el evento
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
    //Ejecutado cuando se activa el evento
  });

//Removiendo el evento
listenerRegistration.remove();
```

## Usando la carga del evento {#using-event-payload}

Es importante notar que los eventos a menudo vienen con una carga útil, que contiene información adicional relacionada con el evento. Puedes utilizar de manera eficiente esta carga dentro del manejador de eventos para acceder a datos relevantes sin realizar viajes innecesarios entre el cliente y el servidor. Al hacerlo, puedes mejorar el rendimiento de tu aplicación.

El siguiente fragmento de código consulta el componente para obtener información que, para los propósitos de nuestra demostración, ya está incluida en la carga útil del evento, representando un código ineficiente:

```java
myComponent.addEventListener(e -> {
  // Acceder a datos del componente
  String componentText = e.getComponent().getText();

  //O si el componente es accesible dentro del ámbito de la función
  String componentText = myComponent.getText();

  // Usar componentText para realizar otras acciones.
});
```

En su lugar, al utilizar la carga del método, que para el ejemplo incluye el texto del componente, se evita un viaje:

```java
myComponent.addEventListener(e -> {
  // Acceder a datos de la carga útil del evento
  String componentText = e.getText();

  // Usar componentText para realizar otras acciones.
});
```

Este enfoque minimiza la necesidad de consultar al componente para obtener información, ya que los datos están disponibles en la carga útil del evento. Al seguir esta práctica de manejo eficiente de eventos, puedes mejorar el rendimiento y la capacidad de respuesta de tus componentes. Para más información, puedes consultar [Interacción Cliente/Servidor](../architecture/client-server).

### Ejemplo {#sample}

A continuación se muestra una demostración que muestra la adición de un <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> a un [`Button`](#). Este [`Button`](#) también utiliza información proveniente de la carga del evento para mostrar información en la pantalla.

<ComponentDemo
path='/webforj/buttonevent'
files={['src/main/java/com/webforj/samples/views/button/ButtonEventView.java']}
height='100px'
/>
