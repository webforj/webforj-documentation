---
sidebar_position: 5
title: Elements
slug: element
_i18n_hash: 820bed6c059dad74a523673f245f3b2a
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Los desarrolladores de webforJ tienen la opción de elegir no solo de la rica biblioteca de componentes proporcionada, sino también de integrar componentes de otros lugares. Para facilitar esto, se puede utilizar el componente `Element` para simplificar la integración de cualquier cosa, desde elementos HTML simples, hasta componentes web personalizados más complejos.

:::important
El componente `Element` no se puede extender y no es el componente base para todos los componentes dentro de webforJ. Para leer más sobre la jerarquía de componentes de webforJ, lee [este artículo](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
/>

## Agregando eventos {#adding-events}

Para utilizar eventos que pueden venir con tu elemento, puedes usar los métodos `addEventListener` del componente `Element`. Agregar un evento requiere al menos el tipo/nombre del evento que el componente espera y un listener que se agregará al evento.

También hay opciones adicionales para personalizar aún más los eventos utilizando las configuraciones de Opciones de Evento.

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputEventView.java',
  'src/main/resources/static/css/element/elementInputEvent.css',
]}
height='240px'
/>

## Interacción de componentes {#component-interaction}

El componente `Element` actúa como un contenedor para otros componentes. Proporciona una forma de organizar y recuperar información para los componentes hijos y ofrece un conjunto claro de funciones para agregar o eliminar estos componentes hijos según sea necesario.

### Agregando componentes hijos {#adding-child-components}

El componente `Element` soporta la composición de componentes hijos. Los desarrolladores pueden organizar y gestionar estructuras de UI complejas agregando componentes como hijos al `Element`. Existen tres métodos para establecer contenido dentro de un `Element`:

1. **`add(Component... components)`**: Este método permite agregar uno o múltiples componentes a un `String` opcional que designa una ranura específica cuando se usa con un Componente Web. Omitir la ranura agregará el componente entre las etiquetas HTML.

2. **`setHtml(String html)`**: Este método toma el `String` pasado al método y lo inyecta como HTML dentro del componente. Dependiendo del `Element`, esto puede ser renderizado de diferentes maneras.

3. **`setText(String text)`**: Este método se comporta de manera similar al método `setHtml()`, pero inyecta texto literal en el `Element`.

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
Llamar a `setHtml()` o `setText()` reemplazará el contenido actualmente contenido entre las etiquetas de apertura y cierre del elemento.
:::

### Eliminando componentes {#removing-components}

Además de agregar componentes a un `Element`, se implementan los siguientes métodos para la eliminación de varios componentes hijos:

1. **`remove(Component... components)`**: Este método toma uno o más componentes y los elimina como componentes hijos.

2. **`removeAll()`**: Este método elimina todos los componentes hijos del `Element`.

### Accediendo a componentes {#accessing-components}

Para acceder a los diversos componentes hijos presentes dentro de un `Element`, o información sobre estos componentes, los siguientes métodos están disponibles:

1. **`getComponents()`**: Este método devuelve una `List` de Java de todos los hijos del `Element`.

2. **`getComponents(String id)`**: Este método es similar al método anterior, pero toma la ID del lado del servidor de un componente específico y lo devuelve cuando se encuentra.

3. **`getComponentCount()`**: Devuelve el número de componentes hijos presentes dentro del `Element`.

## Llamando funciones de JavaScript {#calling-javascript-functions}

El componente `Element` proporciona dos métodos de API que permiten llamar a funciones de JavaScript en elementos HTML.

1. **`callJsFunction(String functionName, Object... arguments)`**: Este método toma el nombre de una función como un string y opcionalmente toma uno o más Objetos como parámetros para la función. Este método se ejecuta de forma sincrónica, lo que significa que el **hilo que se ejecuta está bloqueado** hasta que el método JS devuelve, resultando en un viaje de ida y vuelta. Los resultados de la función se devuelven como un `Object`, que puede ser convertido y utilizado en Java.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Al igual que el método anterior, se puede pasar un nombre de función y argumentos opcionales para la función. Este método se ejecuta de forma asincrónica y **no bloquea el hilo que se ejecuta**. Devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite una mayor interacción con la función y su carga útil.

### Pasando parámetros {#passing-parameters}

Los argumentos que se pasan a estos métodos y que se utilizan en la ejecución de funciones JS se serializan como un array JSON. Existen dos tipos de argumentos notables que se manejan de la siguiente manera:
- `this`: Usar la palabra clave `this` dará al método una referencia a la versión del lado del cliente del componente invocador.
- `Component`: Cualquier instancia de componentes de Java pasadas a uno de los métodos JsFunction serán reemplazadas por la versión del lado del cliente del componente.

:::info
Tanto la llamada sincrónica como la asincrónica a funciones esperará hasta que el `Element` haya sido agregado al DOM antes de ejecutar una función, pero `callJsFunction()` no esperará a que se adjunten los argumentos `component`, lo que puede resultar en un fallo. Por el contrario, invocar `callJsFunctionAsync()` puede nunca completarse si un argumento de componente nunca se adjunta.
:::

En la demostración a continuación, se agrega un evento a un `Button` HTML. Este evento se dispara posteriormente de manera programática al llamar al método `callJsFunctionAsync()`. El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> resultante se utiliza para crear otro cuadro de mensaje una vez que la función asincrónica ha sido completada.

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='240px'
/>

## Ejecutando JavaScript {#executing-javascript}

Además de ejecutar JavaScript desde el nivel de aplicación, es posible ejecutar JavaScript desde el nivel de `Element` también. Realizar esta ejecución en el nivel de `Element` permite incluir el contexto del elemento HTML en la ejecución. Esta es una herramienta poderosa que actúa como un conducto para las capacidades interactivas con entornos del lado del cliente.

Al igual que la ejecución de funciones, la ejecución de JavaScript se puede hacer de forma sincrónica o asincrónica con los siguientes métodos:

1. **`executeJs(String script)`**: Este método toma un `String`, que se ejecutará como código JavaScript en el cliente. Este script se ejecuta de forma sincrónica, lo que significa que el **hilo que se ejecuta está bloqueado** hasta que la ejecución de JS devuelve, resultando en un viaje de ida y vuelta. Los resultados de la función se devuelven como un `Object`, que puede ser convertido y utilizado en Java.

2. **`executeJsAsync(String script)`**: Al igual que el método anterior, un parámetro `String` pasado se ejecutará como código JavaScript en el cliente. Este método se ejecuta de forma asincrónica y **no bloquea el hilo que se ejecuta**. Devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite una mayor interacción con la función y su carga útil.

:::tip
Estos métodos tienen acceso a la palabra clave `component`, que le da al código JavaScript acceso a la instancia del lado del cliente del componente que ejecuta el JavaScript.
:::
