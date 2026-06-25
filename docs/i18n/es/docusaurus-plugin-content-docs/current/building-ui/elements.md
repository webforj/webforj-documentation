---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and invoke JavaScript.
slug: element
_i18n_hash: d941e314cdd63d19471e80936ef5d6bc
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Los desarrolladores de webforJ tienen la opción de elegir no solo entre la rica biblioteca de componentes proporcionada, sino también integrar componentes de otros lugares. Para facilitar esto, se puede utilizar el componente `Element` para simplificar la integración de cualquier cosa, desde simples elementos HTML hasta componentes web personalizados más complejos.

:::important
El componente `Element` no se puede extender y no es el componente base para todos los componentes dentro de webforJ. Para leer más sobre la jerarquía de componentes de webforJ, lea [este artículo](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
/>

## Agregar eventos {#adding-events}

Para utilizar eventos que pueden venir con su elemento, puede usar los métodos `addEventListener` del componente `Element`. Agregar un evento requiere al menos el tipo/nombre del evento que el componente espera y un oyente que se agregará al evento.

También hay opciones adicionales para personalizar aún más los eventos utilizando las configuraciones de Opciones de Eventos.

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputEventView.java',
  'src/main/resources/static/css/element/elementInputEvent.css',
]}
height='240px'
/>

## Interacción entre componentes {#component-interaction}

El componente `Element` actúa como un contenedor para otros componentes. Proporciona una forma de organizar y recuperar información para componentes secundarios y ofrece un conjunto claro de funciones para agregar o eliminar estos componentes secundarios según sea necesario.

### Agregar componentes secundarios {#adding-child-components}

El componente `Element` admite la composición de componentes secundarios. Los desarrolladores pueden organizar y gestionar estructuras de interfaz de usuario complejas agregando componentes como hijos al `Element`. Existen tres métodos para establecer contenido dentro de un `Element`:

1. **`add(Component... components)`**: Este método permite agregar uno o varios componentes a un `String` opcional que designa un slot específico cuando se utiliza con un Componente Web. Omitir el slot agregará el componente entre las etiquetas HTML.

2. **`setText(String text)`**: Este método se comporta de manera similar al método `setHtml()`, pero inyecta texto literal en el `Element`.

  ```java
  // Se muestra como los caracteres literales "<b>Estado: listo</b>"
  element.setText("<b>Estado: listo</b>");
  ```

  :::note Usando la etiqueta `<html>`
  Las versiones anteriores de webforJ trataban un valor envuelto en `<html>` y pasado a `setText()` como HTML. Este comportamiento está obsoleto y se eliminará en webforJ 27.00.

  La primera vez que un valor envuelto en `<html>` llega a `setText()`, se registra una advertencia que nombra el componente y el sitio de llamada, para que la llamada pueda ser movida a `setHtml()`.

  Para adoptar el valor predeterminado de webforJ 27.00 con anticipación, establezca `webforj.legacyHtmlInText` en `false`. En una aplicación Spring, el mismo valor se establece a través de `webforj.legacy-html-in-text`.

  ```java
  // webforj.legacyHtmlInText = true (predeterminado)
  element.setText("<html><b>Estado: listo</b></html>"); // renderiza en negrita

  // webforj.legacyHtmlInText = false
  element.setText("<html><b>Estado: listo</b></html>"); // muestra los caracteres <b>Estado: listo</b>
  ```
  :::

3. **`setHtml(String html)`**: Este método toma el `String` pasado al método e inyecta como HTML dentro del componente. Dependiendo del `Element`, esto puede ser renderizado de diferentes maneras.

  :::danger Secuencias de comandos entre sitios (XSS)
  Como precaución contra [ataques de secuencias de comandos entre sitios (XSS)](/docs/security/application-security/common-threats#cross-site-scripting-xss), use `setHtml()` solo con contenido que controle directamente.
  :::

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
Llamar a `setHtml()` o `setText()` reemplazará el contenido que actualmente se encuentra entre las etiquetas de apertura y cierre del elemento.
:::

### Eliminar componentes {#removing-components}

Además de agregar componentes a un `Element`, se implementan los siguientes métodos para eliminar varios componentes secundarios:

1. **`remove(Component... components)`**: Este método toma uno o más componentes y los elimina como componentes secundarios.

2. **`removeAll()`**: Este método elimina todos los componentes secundarios del `Element`.

### Acceder a los componentes {#accessing-components}

Para acceder a los diversos componentes secundarios presentes dentro de un `Element`, o información sobre estos componentes, están disponibles los siguientes métodos:

1. **`getComponents()`**: Este método devuelve una `List` de Java de todos los hijos del `Element`.

2. **`getComponents(String id)`**: Este método es similar al método anterior, pero toma el ID del lado del servidor de un componente específico y lo devuelve cuando se encuentra.

3. **`getComponentCount()`**: Devuelve el número de componentes secundarios presentes dentro del `Element`.

## Llamando funciones de JavaScript {#calling-javascript-functions}

El componente `Element` proporciona dos métodos de API que permiten llamar a funciones de JavaScript en elementos HTML.

1. **`callJsFunction(String functionName, Object... arguments)`**: Este método toma un nombre de función como una cadena y opcionalmente toma uno o más objetos como parámetros para la función. Este método se ejecuta de forma sincrónica, lo que significa que **el hilo de ejecución se bloquea** hasta que el método JS retorna, y resulta en un viaje de ida y vuelta. Los resultados de la función se devuelven como un `Object`, que puede ser convertido y utilizado en Java.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Al igual que el método anterior, se puede pasar un nombre de función y argumentos opcionales para la función. Este método se ejecuta de forma asíncrona y **no bloquea el hilo de ejecución**. Devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite una interacción adicional con la función y su carga.

### Pasando parámetros {#passing-parameters}

Los argumentos que se pasan a estos métodos que se utilizan en la ejecución de funciones JS se serializan como un array JSON. Hay dos tipos de argumentos notables que se manejan de la siguiente manera:
- `this`: Usar la palabra clave `this` dará al método una referencia a la versión del componente del lado del cliente.
- `Component`: Las instancias de componentes de Java pasadas a uno de los métodos JsFunction serán reemplazadas por la versión del lado del cliente del componente.

:::info
Tanto la llamada a funciones sincrónicas como asincrónicas esperarán hasta que el `Element` haya sido agregado al DOM antes de ejecutar una función, pero `callJsFunction()` no esperará a que se adjunten los argumentos `component`, lo que puede resultar en un fallo. Por el contrario, invocar `callJsFunctionAsync()` puede no completarse nunca si un argumento de componente nunca se adjunta.
:::

En la demostración a continuación, se agrega un evento a un HTML `Button`. Este evento se activa programáticamente al llamar al método `callJsFunctionAsync()`. El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> resultante se utiliza para crear otro cuadro de mensaje una vez que se ha completado la función asíncrona.

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='240px'
/>

## Ejecutando JavaScript {#executing-javascript}

Además de ejecutar JavaScript desde el nivel de la aplicación, también es posible ejecutar JavaScript desde el nivel del `Element`. Realizar esta ejecución en el nivel del `Element` permite incluir el contexto del elemento HTML en la ejecución. Esta es una herramienta poderosa que actúa como un conducto para que los desarrolladores tengan capacidades interactivas con entornos del lado del cliente.

Al igual que la ejecución de funciones, ejecutar JavaScript puede hacerse de forma sincrónica o asincrónica con los siguientes métodos:

1. **`executeJs(String script)`**: Este método toma un `String`, que se ejecutará como código JavaScript en el cliente. Este script se ejecuta de manera sincrónica, lo que significa que **el hilo de ejecución se bloquea** hasta que la ejecución de JS retorna, y resulta en un viaje de ida y vuelta. Los resultados de la función se devuelven como un `Object`, que puede ser convertido y utilizado en Java.

2. **`executeJsAsync(String script)`**: Al igual que el método anterior, un parámetro `String` pasado se ejecutará como código JavaScript en el cliente. Este método se ejecuta de manera asíncrona y **no bloquea el hilo de ejecución**. Devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite una interacción adicional con la función y su carga.

:::tip
Estos métodos tienen acceso a la palabra clave `component`, que proporciona al código JavaScript acceso a la instancia del lado del cliente del componente que ejecuta el JavaScript.
:::
