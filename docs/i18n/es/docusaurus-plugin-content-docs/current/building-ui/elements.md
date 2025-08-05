---
sidebar_position: 3
title: Elements
slug: element
_i18n_hash: d77ff55b483b72de9ee1d36473d7751d
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Los desarrolladores de webforJ tienen la opción de elegir no solo de la rica biblioteca de componentes proporcionados, sino también de integrar componentes de otros lugares. Para facilitar esto, se puede usar el componente `Element` para simplificar la integración de cualquier cosa, desde elementos HTML simples hasta componentes web personalizados más complejos.

:::important
El componente `Element` no puede ser extendido y no es el componente base para todos los componentes dentro de webforJ. Para leer más sobre la jerarquía de componentes de webforJ, lee [este artículo](../architecture/controls-components.md).
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## Agregando eventos {#adding-events}

Para utilizar eventos que pueden venir con tu elemento, puedes usar los métodos `addEventListener` del componente `Element`. Agregar un evento requiere al menos el tipo/nombre del evento que el componente espera y un listener que se agregará al evento.

También hay opciones adicionales para personalizar más los eventos utilizando las configuraciones de Opciones de Eventos.

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## Interacción del componente {#component-interaction}

El componente `Element` actúa como un contenedor para otros componentes. Proporciona una forma de organizar y recuperar información para los componentes secundarios y ofrece un conjunto claro de funciones para agregar o quitar estos componentes secundarios según sea necesario.

### Agregando componentes secundarios {#adding-child-components}

El componente `Element` admite la composición de componentes secundarios. Los desarrolladores pueden organizar y gestionar estructuras de UI complejas agregando componentes como hijos al `Element`. Existen tres métodos para establecer contenido dentro de un `Element`:

1. **`add(Component... components)`**: Este método permitirá que se agregue uno o varios componentes a un `String` opcional que designa un slot específico cuando se usa con un Componente Web. Omitir el slot agregará el componente entre las etiquetas HTML.

2. **`setHtml(String html)`**: Este método toma el `String` pasado al método e inyecta como HTML dentro del componente. Dependiendo del `Element`, esto puede renderizarse de diferentes maneras.

3. **`setText(String text)`**: Este método se comporta de manera similar al método `setHtml()`, pero inyecta texto literal en el `Element`.

<ComponentDemo 
path='/webforj/elementinputtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputTextView.java'
cssURL='/css/element/elementInput.css'
height='175px'
/>

:::tip
Llamar a `setHtml()` o `setText()` reemplazará el contenido actualmente contenido entre las etiquetas de apertura y cierre del elemento.
:::

### Eliminando componentes {#removing-components}

Además de agregar componentes a un `Element`, se implementan los siguientes métodos para la eliminación de varios componentes secundarios:

1. **`remove(Component... components)`**: Este método toma uno o más componentes y los eliminará como componentes secundarios.

2. **`removeAll()`**: Este método eliminará todos los componentes secundarios del `Element`.

### Accediendo a componentes {#accessing-components}

Para acceder a los diversos componentes secundarios presentes dentro de un `Element`, o información relacionada con estos componentes, están disponibles los siguientes métodos:

1. **`getComponents()`**: Este método devuelve una `List` de Java de todos los hijos del `Element`.

2. **`getComponents(String id)`**: Este método es similar al anterior, pero tomará el ID del lado del servidor de un componente específico y lo devolverá cuando se encuentre.

3. **`getComponentCount()`**: Devuelve el número de componentes secundarios presentes dentro del `Element`.

## Llamando funciones de JavaScript {#calling-javascript-functions}

El componente `Element` proporciona dos métodos de API que permiten llamar a funciones de JavaScript en elementos HTML.

1. **`callJsFunction(String functionName, Object... arguments)`**: Este método toma un nombre de función como cadena y opcionalmente toma uno o más Objetos como parámetros para la función. Este método se ejecuta de manera sincrónica, lo que significa que el **hilo en ejecución está bloqueado** hasta que el método de JS devuelve, resultando en un viaje de ida y vuelta. Los resultados de la función se devuelven como un `Object`, que puede ser convertido y utilizado en Java.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Al igual que el método anterior, se puede pasar un nombre de función y argumentos opcionales para la función. Este método se ejecuta de manera asíncrona y **no bloquea el hilo en ejecución**. Devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite una interacción adicional con la función y su carga útil.

### Pasando parámetros {#passing-parameters}

Los argumentos que se pasan a estos métodos que se utilizan en la ejecución de funciones de JS se serializan como un arreglo JSON. Hay dos tipos de argumentos notorios que se manejan de la siguiente manera:
- `this`: Usar la palabra clave `this` dará al método una referencia a la versión del lado del cliente del componente que invoca.
- `Component`: Cualquier instancia de componente de Java pasada a uno de los métodos de JsFunction será reemplazada por la versión del lado del cliente del componente.

:::info
Tanto la llamada de función sincrónica como asincrónica esperarán para llamar a un método hasta que el `Element` haya sido agregado al DOM antes de ejecutar una función, pero `callJsFunction()` no esperará a que se adjunten argumentos `component`, lo que puede resultar en fallos. Por otro lado, invocar `callJsFunctionAsync()` puede que nunca se complete si un argumento de componente nunca se adjunta.
:::

En el demo a continuación, se agrega un evento a un `Button` HTML. Este evento se activa a programa llamando al método `callJsFunctionAsync()`. El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> resultante se utiliza para crear otra caja de mensaje una vez que la función asíncrona se ha completado.

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## Ejecutando JavaScript {#executing-javascript}

Además de ejecutar JavaScript desde el nivel de la aplicación, también es posible ejecutar JavaScript desde el nivel de `Element`. Realizar esta ejecución a nivel de `Element` permite incluir el contexto del elemento HTML en la ejecución. Esta es una herramienta poderosa que actúa como un conducto para que los desarrolladores accedan a capacidades interactivas con entornos del lado del cliente.

Similar a la ejecución de funciones, ejecutar JavaScript se puede hacer de manera sincrónica o asincrónica con los siguientes métodos:

1. **`executeJs(String script)`**: Este método toma un `String`, que será ejecutado como código JavaScript en el cliente. Este script se ejecuta de manera sincrónica, lo que significa que el **hilo en ejecución está bloqueado** hasta que la ejecución de JS devuelve y resulta en un viaje de ida y vuelta. Los resultados de la función se devuelven como un `Object`, que puede ser convertido y utilizado en Java.

2. **`executeJsAsync(String script)`**: Al igual que el método anterior, un parámetro `String` pasado se ejecutará como código JavaScript en el cliente. Este método se ejecuta de manera asíncrona y **no bloquea el hilo en ejecución**. Devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite una interacción adicional con la función y su carga útil.

:::tip
Estos métodos tienen acceso a la palabra clave `component`, que da al código JavaScript acceso a la instancia del lado del cliente del componente que ejecuta el JavaScript.
:::
