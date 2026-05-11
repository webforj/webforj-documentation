---
sidebar_position: 5
title: Elements
slug: element
_i18n_hash: 067ff9e31676f6991dab011252151043
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Los desarrolladores de webforJ tienen la opción de elegir no solo entre la rica biblioteca de componentes proporcionados, sino también integrar componentes de otros lugares. Para facilitar esto, se puede utilizar el componente `Element` para simplificar la integración de cualquier cosa, desde elementos HTML simples hasta componentes web personalizados más complejos.

:::important
El componente `Element` no se puede extender y no es el componente base para todos los componentes dentro de webforJ. Para leer más sobre la jerarquía de componentes de webforJ, lea [este artículo](../architecture/controls-components.md).
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## Agregando eventos {#adding-events}

Para utilizar eventos que pueden venir con su elemento, puede usar los métodos `addEventListener` del componente `Element`. Agregar un evento requiere al menos el tipo/nombre del evento que el componente espera y un oyente que se añadirá al evento.

También hay opciones adicionales para personalizar aún más los eventos mediante la configuración de Opciones de Eventos.

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## Interacción de componentes {#component-interaction}

El componente `Element` actúa como un contenedor para otros componentes. Proporciona una manera de organizar y recuperar información de los componentes secundarios y ofrece un conjunto claro de funciones para agregar o eliminar estos componentes secundarios según sea necesario.

### Agregando componentes secundarios {#adding-child-components}

El componente `Element` admite la composición de componentes secundarios. Los desarrolladores pueden organizar y gestionar estructuras de UI complejas al agregar componentes como hijos al `Element`. Existen tres métodos para establecer contenido dentro de un `Element`:

1. **`add(Component... components)`**: Este método permite agregar uno o varios componentes a una `String` opcional que designa un slot específico cuando se utiliza con un Componente Web. Omitir el slot agregará el componente entre las etiquetas HTML.

2. **`setHtml(String html)`**: Este método toma la `String` pasada al método e inyecta como HTML dentro del componente. Dependiendo del `Element`, esto puede renderizarse de diferentes maneras.

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

1. **`remove(Component... components)`**: Este método toma uno o más componentes y los elimina como componentes secundarios.

2. **`removeAll()`**: Este método elimina todos los componentes secundarios del `Element`.

### Accediendo a componentes {#accessing-components}

Para acceder a los diversos componentes secundarios presentes dentro de un `Element`, o información sobre esos componentes, están disponibles los siguientes métodos:

1. **`getComponents()`**: Este método devuelve una `List` de Java de todos los hijos del `Element`.

2. **`getComponents(String id)`**: Este método es similar al método anterior, pero toma el ID del lado del servidor de un componente específico y lo devuelve cuando se encuentra.

3. **`getComponentCount()`**: Devuelve el número de componentes secundarios presentes dentro del `Element`.

## Llamando funciones de JavaScript {#calling-javascript-functions}

El componente `Element` proporciona dos métodos API que permiten que se llamen funciones de JavaScript en elementos HTML.

1. **`callJsFunction(String functionName, Object... arguments)`**: Este método toma un nombre de función como una cadena, y opcionalmente acepta uno o más objetos como parámetros para la función. Este método se ejecuta de manera sincrónica, lo que significa que el **hilo ejecutor está bloqueado** hasta que el método JS devuelve, y resulta en un viaje redondo. Los resultados de la función se devuelven como un `Object`, que se puede convertir y utilizar en Java.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Al igual que el método anterior, se puede pasar un nombre de función y argumentos opcionales para la función. Este método se ejecuta de manera asíncrona y **no bloquea el hilo ejecutor**. Devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite una interacción adicional con la función y su carga.

### Pasando parámetros {#passing-parameters}

Los argumentos que se pasan a estos métodos y que se utilizan en la ejecución de funciones JS se serializan como un arreglo JSON. Hay dos tipos de argumentos notables que se manejan de la siguiente manera:
- `this`: Usar la palabra clave `this` le dará al método una referencia a la versión del lado del cliente del componente que invoca.
- `Component`: Cualquier instancia de componente de Java pasada a uno de los métodos JsFunction será reemplazada por la versión del lado del cliente del componente.

:::info
Tanto la llamada a funciones sincrónicas como asíncronas esperarán hasta que el `Element` haya sido agregado al DOM antes de ejecutar una función, pero `callJsFunction()` no esperará a que se adjunten los argumentos `component`, lo cual puede resultar en un fallo. Por el contrario, invocar `callJsFunctionAsync()` puede no completarse nunca si un argumento de componente nunca se adjunta.
:::

En la demostración a continuación, se agrega un evento a un `Button` HTML. Este evento se dispara programáticamente al llamar al método `callJsFunctionAsync()`. El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> resultante se utiliza luego para crear otro cuadro de mensaje una vez que se ha completado la función asíncrona.

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## Ejecutando JavaScript {#executing-javascript}

Además de ejecutar JavaScript desde el nivel de la aplicación, también es posible ejecutar JavaScript desde el nivel del `Element`. Realizar esta ejecución a nivel de `Element` permite que el contexto del elemento HTML se incluya en la ejecución. Esta es una herramienta poderosa que actúa como un conducto para que los desarrolladores interactúen con capacidades interactivas en entornos del lado del cliente.

Similar a la ejecución de funciones, la ejecución de JavaScript se puede realizar de forma sincrónica o asíncrona con los siguientes métodos:

1. **`executeJs(String script)`**: Este método toma una `String`, que se ejecutará como código JavaScript en el cliente. Este script se ejecuta de manera sincrónica, lo que significa que el **hilo ejecutor está bloqueado** hasta que la ejecución de JS devuelve, y resulta en un viaje redondo. Los resultados de la función se devuelven como un `Object`, que se puede convertir y utilizar en Java.

2. **`executeJsAsync(String script)`**: Al igual que el método anterior, se pasará un parámetro `String` que se ejecutará como código JavaScript en el cliente. Este método se ejecuta de manera asíncrona y **no bloquea el hilo ejecutor**. Devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite interacciones adicionales con la función y su carga.

:::tip
Estos métodos tienen acceso a la palabra clave `component`, que le da al código JavaScript acceso a la instancia del lado del cliente del componente que está ejecutando el JavaScript.
:::
