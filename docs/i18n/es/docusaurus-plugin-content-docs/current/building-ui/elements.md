---
sidebar_position: 5
title: Elements
slug: element
_i18n_hash: 2ea3ba8ae8756dcea1ee5d0eb9fb0cf9
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Los desarrolladores de webforJ tienen la opción de elegir no solo de la rica biblioteca de componentes proporcionada, sino también de integrar componentes de otros lugares. Para facilitar esto, el componente `Element` se puede usar para simplificar la integración de cualquier cosa, desde elementos HTML simples hasta componentes web personalizados más complejos.

:::important
El componente `Element` no puede ser extendido y no es el componente base para todos los componentes dentro de webforJ. Para leer más sobre la jerarquía de componentes de webforJ, lee [este artículo](../architecture/controls-components.md).
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## Agregar eventos {#adding-events}

Para utilizar eventos que pueden venir con tu elemento, puedes usar los métodos `addEventListener` del componente `Element`. Agregar un evento requiere al menos el tipo/nombre del evento que el componente espera y un oyente que se agregará al evento.

También hay opciones adicionales para personalizar aún más los eventos mediante las configuraciones de Opciones de Evento.

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## Interacción de componentes {#component-interaction}

El componente `Element` actúa como un contenedor para otros componentes. Ofrece una manera de organizar y recuperar información para los componentes secundarios y proporciona un conjunto claro de funciones para agregar o eliminar estos componentes secundarios según sea necesario.

### Agregar componentes secundarios {#adding-child-components}

El componente `Element` admite la composición de componentes secundarios. Los desarrolladores pueden organizar y gestionar estructuras de UI complejas agregando componentes como hijos al `Element`. Existen tres métodos para establecer contenido dentro de un `Element`:

1. **`add(Component... components)`**: Este método permitirá que uno o varios componentes se añadan a un `String` opcional que designa una ranura específica cuando se utiliza con un Componente Web. Omitir la ranura agregará el componente entre las etiquetas HTML.

2. **`setHtml(String html)`**: Este método toma el `String` pasado al método e inyecta como HTML dentro del componente. Dependiendo del `Element`, esto puede ser renderizado de diferentes maneras.

3. **`setText(String text)`**: Este método se comporta de manera similar al método `setHtml()`, pero inyecta texto literal en el `Element`.

<ComponentDemo 
path='/webforj/elementinputtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputTextView.java'
cssURL='/css/element/elementInput.css'
height='175px'
/>

:::tip
Llamar a `setHtml()` o `setText()` reemplazará el contenido que actualmente se encuentra entre las etiquetas de apertura y cierre del elemento.
:::

### Eliminar componentes {#removing-components}

Además de agregar componentes a un `Element`, se implementan los siguientes métodos para eliminar varios componentes secundarios:

1. **`remove(Component... components)`**: Este método toma uno o más componentes y los eliminará como componentes secundarios.

2. **`removeAll()`**: Este método eliminará todos los componentes secundarios del `Element`.

### Acceder a componentes {#accessing-components}

Para acceder a los varios componentes secundarios presentes dentro de un `Element`, o información sobre estos componentes, están disponibles los siguientes métodos:

1. **`getComponents()`**: Este método devuelve una `List` de Java de todos los hijos del `Element`.

2. **`getComponents(String id)`**: Este método es similar al método anterior, pero tomará la ID del lado del servidor de un componente específico y la devolverá si se encuentra.

3. **`getComponentCount()`**: Devuelve el número de componentes secundarios presentes dentro del `Element`.

## Llamar funciones de JavaScript {#calling-javascript-functions}

El componente `Element` proporciona dos métodos de API que permiten llamar funciones de JavaScript en elementos HTML.

1. **`callJsFunction(String functionName, Object... arguments)`**: Este método toma un nombre de función como cadena y opcionalmente toma uno o más Objetos como parámetros para la función. Este método se ejecuta de manera sincrónica, lo que significa que el **hilo de ejecución está bloqueado** hasta que el método JS devuelve, y resulta en un viaje de ida y vuelta. Los resultados de la función se devuelven como un `Object`, que puede ser convertido y utilizado en Java.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Al igual que con el método anterior, se puede pasar un nombre de función y argumentos opcionales para la función. Este método se ejecuta de manera asíncrona y **no bloquea el hilo de ejecución**. Devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite una interacción adicional con la función y su carga.

### Pasar parámetros {#passing-parameters}

Los argumentos que se pasan a estos métodos que se utilizan en la ejecución de funciones JS se serializan como un array JSON. Hay dos tipos de argumentos notables que se manejan de las siguientes maneras:
- `this`: Usar la palabra clave `this` dará al método una referencia a la versión del lado del cliente del componente invocador.
- `Component`: Cualquier instancia de componente Java pasada a uno de los métodos JsFunction será reemplazada por la versión del lado del cliente del componente.

:::info
Tanto la llamada de función sincrónica como asíncrona esperará para llamar a un método hasta que el `Element` haya sido añadido al DOM antes de ejecutar una función, pero `callJsFunction()` no esperará a que se adjunten los argumentos `component`, lo que puede resultar en un fallo. Por el contrario, invocar `callJsFunctionAsync()` puede nunca completarse si un argumento de componente nunca se adjunta.
:::

En la demostración a continuación, se agrega un evento a un `Button` HTML. Este evento se dispara luego de manera programática llamando al método `callJsFunctionAsync()`. El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> resultante se utiliza luego para crear otro cuadro de mensaje una vez que la función asíncrona ha sido completada.

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## Ejecutar JavaScript {#executing-javascript}

Además de ejecutar JavaScript desde el nivel de aplicación, también es posible ejecutar JavaScript desde el nivel de `Element`. Realizar esta ejecución en el nivel de `Element` permite que el contexto del elemento HTML esté incluido en la ejecución. Esta es una herramienta poderosa que actúa como un conducto para que los desarrolladores tengan capacidades interactivas con entornos del lado del cliente.

Al igual que la ejecución de funciones, la ejecución de JavaScript se puede hacer de forma sincrónica o asíncrona con los siguientes métodos:

1. **`executeJs(String script)`**: Este método toma un `String`, que se ejecutará como código JavaScript en el cliente. Este script se ejecuta de manera sincrónica, lo que significa que el **hilo de ejecución está bloqueado** hasta que la ejecución de JS retorna y resulta en un viaje de ida y vuelta. Los resultados de la función se devuelven como un `Object`, que puede ser convertido y utilizado en Java.

2. **`executeJsAsync(String script)`**: Al igual que con el método anterior, un parámetro `String` pasado se ejecutará como código JavaScript en el cliente. Este método se ejecuta de manera asíncrona y **no bloquea el hilo de ejecución**. Devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite una interacción adicional con la función y su carga.

:::tip
Estos métodos tienen acceso a la palabra clave `component`, que da al código JavaScript acceso a la instancia del lado del cliente del componente que está ejecutando el JavaScript.
:::
