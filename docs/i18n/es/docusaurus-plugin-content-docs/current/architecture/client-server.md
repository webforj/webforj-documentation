---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: a69f444ce2e5a9dea37304d466f4e6ac
---
La siguiente sección discute varias cualidades de rendimiento y mejores prácticas para webforJ, así como detalles de implementación para el marco.

Cuando se crea una aplicación en webforJ, el cliente y el servidor trabajan juntos para manipular datos entre el cliente y el servidor, lo que se puede desglosar en las siguientes categorías amplias:

## 1. Servidor a cliente {#1-server-to-client}

Los métodos de webforJ, como `setText()`, se incluyen en esta categoría. La aplicación de webforJ que se ejecuta en el servidor envía datos al cliente sin esperar una respuesta. webforJ optimiza automáticamente los lotes de operaciones en esta categoría para mejorar el rendimiento.

## 2. Cliente a servidor {#2-client-to-server}

Esta categoría cubre el tráfico de eventos, como el método `Button.onClick()`. En su mayoría, el cliente envía eventos al servidor sin esperar ninguna respuesta. El objeto de evento típicamente contiene parámetros adicionales relacionados con el evento, como el código hash. Debido a que esta información se entrega al servidor como parte del acto de entregar el evento, está inmediatamente disponible para el programa tan pronto como se recibe el evento.

## 3. Servidor a cliente a servidor (viaje de ida y vuelta) {#3-server-to-client-to-server-round-trip}

Los viajes de ida y vuelta se realizan cuando la aplicación consulta al cliente para obtener información dinámica que no se puede almacenar en caché en el servidor. Métodos como `Label.getText()` y `Checkbox.isChecked()` caen en esta categoría. Cuando una aplicación de webforJ ejecuta una línea como `String title = myLabel.getText()`, se detiene por completo mientras el servidor envía esa solicitud al cliente y luego espera que el cliente envíe la respuesta de vuelta.

Si la aplicación envía varios mensajes al cliente que no requieren una respuesta (categoría 1), seguidos de un único mensaje que requiere un viaje de ida y vuelta (categoría 3), la aplicación debe esperar a que el cliente procese todos los mensajes pendientes y luego responda al mensaje final que requiere una respuesta. En algunos casos, esto puede agregar un retraso. Si ese viaje de ida y vuelta no se hubiera introducido, el cliente habría podido continuar trabajando procesando esos mensajes acumulados mientras la aplicación que se ejecuta en el servidor avanzaba a nuevos trabajos.

## Mejorar rendimiento {#improve-performance}

Es posible mejorar significativamente la capacidad de respuesta evitando los viajes de ida y vuelta de la tercera categoría tanto como sea posible. Por ejemplo, cambiar la funcionalidad onSelect del ComboBox de esto:

```java
private void comboBoxSelect(ListSelectEvent ev){
  ComboBox component = (ComboBox) ev.getComponent();

  // Va al cliente
  int selected = component.getSelectedIndex();
}
```

a lo siguiente:

```java
private void comboBoxSelect(ListSelectEvent ev){
  // Obtiene el valor del evento
  int selected = ev.getSelectedIndex();
}
```

En el primer fragmento, `ComboBox.getSelectedIndex()` que se realiza en el componente fuerza un viaje de ida y vuelta de regreso al cliente, introduciendo un retraso. En la segunda versión, usar el método `ListSelectEvent.getSelectedIndex()` del evento recupera el valor que se entregó al servidor como parte del evento original.

## Caché {#caching}

webforJ optimiza aún más el rendimiento utilizando caché. En general, existen dos tipos de datos en este contexto: los datos que el usuario puede cambiar directamente y los datos que no pueden ser cambiados por el usuario. En el primer caso, al recuperar la información con la que los usuarios interactuarán directamente, es necesario consultar al servidor por esta información.

Sin embargo, la información que no puede ser cambiada por el usuario se puede almacenar en caché para evitar pérdidas de rendimiento adicionales. Esto asegura que no se necesite realizar un viaje de ida y vuelta innecesariamente, proporcionando una experiencia de usuario más eficiente. webforJ optimiza aplicaciones de esta manera para asegurar un rendimiento óptimo.

## Tiempo de carga {#loading-time}

Cuando el usuario lanza una aplicación de webforJ, se carga solo un pequeño fragmento (alrededor de 2.5 kB gzip) de JavaScript para iniciar la sesión. Después de eso, descarga dinámicamente mensajes individuales o fragmentos de JavaScript, bajo demanda, a medida que la aplicación utiliza la funcionalidad correspondiente. Por ejemplo, el servidor solo envía al cliente el JavaScript necesario para construir un `Button` de webforJ una vez, cuando la aplicación crea su primer componente `Button`. Esto resulta en mejoras medibles en el tiempo de carga inicial, lo que resulta en una mejor experiencia de usuario.
