---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and invoke JavaScript.
slug: element
_i18n_hash: 2af99ca4f1e5c8c2f7c31b3d7f647b41
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Los desarrolladores de webforJ tienen la opción de elegir no solo de la rica biblioteca de componentes proporcionados, sino también de integrar componentes de otros lugares. Para facilitar esto, se puede utilizar el componente `Element` para simplificar la integración de cualquier cosa, desde elementos HTML simples hasta componentes web personalizados más complejos.

:::important
El componente `Element` no se puede extender y no es el componente base para todos los componentes dentro de webforJ. Para leer más sobre la jerarquía de componentes de webforJ, lee [este artículo](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
/>

## Agregar eventos {#adding-events}

Para utilizar los eventos que pueden venir con tu elemento, puedes usar los métodos `addEventListener` del componente `Element`. Agregar un evento requiere al menos el tipo/nombre del evento que el componente espera y un listener que se añadirá al evento.

También hay opciones adicionales para personalizar aún más los eventos utilizando las configuraciones de opciones de eventos.

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputEventView.java',
  'src/main/frontend/css/element/elementInputEvent.css',
]}
height='240px'
/>

## Interacción de componentes {#component-interaction}

El componente `Element` actúa como un contenedor para otros componentes. Proporciona una manera de organizar y recuperar información para los componentes secundarios, y ofrece un conjunto claro de funciones para agregar o eliminar estos componentes secundarios según sea necesario.

### Agregar componentes secundarios {#adding-child-components}

El componente `Element` admite la composición de componentes secundarios. Los desarrolladores pueden organizar y gestionar estructuras de UI complejas agregando componentes como hijos al `Element`. Existen tres métodos para establecer contenido dentro de un `Element`:

1. **`add(Component... components)`**: Este método permite agregar uno o varios componentes a un `String` opcional que designa un slot específico cuando se usa con un Web Component. Omitir el slot añadirá el componente entre las etiquetas HTML.

2. **`setText(String text)`**: Este método se comporta de manera similar al método `setHtml()`, pero inyecta texto literal en el `Element`.

  ```java
  // Se muestra como los caracteres literales "<b>Status: ready</b>"
  element.setText("<b>Status: ready</b>");
  ```

  :::note Usando la etiqueta `<html>`
  Las versiones anteriores de webforJ trataban un valor envuelto en `<html>` y pasado a `setText()` como HTML. Este comportamiento está en desuso y se eliminará en webforJ 27.00.

  La primera vez que un valor envuelto en `<html>` llega a `setText()`, se registra una advertencia que nombra el componente y el sitio de llamada, para que la llamada pueda moverse a `setHtml()`.

  Para adoptar el valor predeterminado de webforJ 27.00 anticipadamente, establece `webforj.legacyHtmlInText` en `false`. En una aplicación Spring, el mismo valor se establece a través de `webforj.legacy-html-in-text`.

  ```java
  // webforj.legacyHtmlInText = true (predeterminado)
  element.setText("<html><b>Status: ready</b></html>"); // se muestra en negrita

  // webforj.legacyHtmlInText = false
  element.setText("<html><b>Status: ready</b></html>"); // muestra los caracteres <b>Status: ready</b>
  ```
  :::

3. **`setHtml(String html)`**: Este método recibe el `String` pasado al método e inyecta como HTML dentro del componente. Dependiendo del `Element`, esto puede representarse de diferentes maneras.

  :::danger Cross-site Scripting (XSS)
  Como precaución contra [ataques de cross-site scripting (XSS)](/docs/security/application-security/common-threats#cross-site-scripting-xss), usa `setHtml()` solo con contenido que controlas directamente.
  :::

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
Llamar a `setHtml()` o `setText()` reemplazará el contenido actualmente contenido entre las etiquetas de apertura y cierre del elemento.
:::

### Eliminar componentes {#removing-components}

Además de agregar componentes a un `Element`, se implementan los siguientes métodos para la eliminación de varios componentes secundarios:

1. **`remove(Component... components)`**: Este método toma uno o más componentes y los elimina como componentes secundarios.

2. **`removeAll()`**: Este método elimina todos los componentes secundarios del `Element`.

### Acceder a componentes {#accessing-components}

Para acceder a los diversos componentes secundarios presentes dentro de un `Element`, o información sobre estos componentes, están disponibles los siguientes métodos:

1. **`getComponents()`**: Este método devuelve una `List` de Java de todos los hijos del `Element`.

2. **`getComponents(String id)`**: Este método es similar al anterior, pero toma el ID del lado del servidor de un componente específico y lo devuelve cuando se encuentra.

3. **`getComponentCount()`**: Devuelve el número de componentes secundarios presentes dentro del `Element`.

## Llamando a funciones JavaScript {#calling-javascript-functions}

El componente `Element` proporciona dos métodos API que permiten llamar a funciones JavaScript en elementos HTML.

1. **`callJsFunction(String functionName, Object... arguments)`**: Este método toma el nombre de una función como un string y opcionalmente toma uno o más objetos como parámetros para la función. Este método se ejecuta de manera síncrona, lo que significa que el **hilo que ejecuta se bloquea** hasta que el método JS devuelve, resultando en un viaje redondo. Los resultados de la función se devuelven como un `Object`, que puede ser convertido y utilizado en Java.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Al igual que el método anterior, se puede pasar un nombre de función y argumentos opcionales para la función. Este método se ejecuta de forma asíncrona y **no bloquea el hilo que ejecuta**. Devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite una interacción adicional con la función y su carga útil.

### Pasando parámetros {#passing-parameters}

Los argumentos que se pasan a estos métodos que se utilizan en la ejecución de funciones JS se serializan como un arreglo JSON. Hay dos tipos de argumentos notables que se manejan de la siguiente manera:
- `this`: Usar la palabra clave `this` dará al método una referencia a la versión del componente del lado del cliente que invoca.
- `Component`: Cualquier instancia de componente Java pasada a uno de los métodos JsFunction se reemplazará con la versión del lado del cliente del componente.

:::info
Tanto la llamada síncrona como la asíncrona de funciones esperará hasta que el `Element` se haya añadido al DOM antes de ejecutar una función, pero `callJsFunction()` no esperará a que se adjunten los argumentos `component`, lo que puede resultar en un fallo. Por el contrario, invocar `callJsFunctionAsync()` puede nunca completarse si un argumento de componente nunca se adjunta.
:::

En la demostración a continuación, se agrega un evento a un `Button` HTML. Este evento se activa a nivel de programación llamando al método `callJsFunctionAsync()`. El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> resultante se utiliza luego para crear otro cuadro de mensaje una vez que se ha completado la función asíncrona.

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
height='240px'
/>

## Ejecutando JavaScript {#executing-javascript}

Además de ejecutar JavaScript desde el nivel de la aplicación, también es posible ejecutar JavaScript desde el nivel del `Element`. Realizar esta ejecución a nivel de `Element` permite incluir el contexto del elemento HTML en la ejecución. Esta es una herramienta poderosa que actúa como un conducto para que los desarrolladores interactúen con capacidades de entornos del lado del cliente.

Al igual que la ejecución de funciones, la ejecución de JavaScript se puede realizar de manera sincrónica o asincrónica con los siguientes métodos:

1. **`executeJs(String script)`**: Este método toma un `String`, que se ejecutará como código JavaScript en el cliente. Este script se ejecuta de manera síncrona, lo que significa que el **hilo que ejecuta se bloquea** hasta que la ejecución de JS devuelve, resultando en un viaje redondo. Los resultados de la función se devuelven como un `Object`, que puede ser convertido y utilizado en Java.

2. **`executeJsAsync(String script)`**: Al igual que el método anterior, un parámetro `String` pasado se ejecutará como código JavaScript en el cliente. Este método se ejecuta de forma asíncrona y **no bloquea el hilo que ejecuta**. Devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite una interacción adicional con la función y su carga útil.

:::tip
Estos métodos tienen acceso a la palabra clave `component`, que da al código JavaScript acceso a la instancia del componente del lado del cliente que está ejecutando el JavaScript.
:::
