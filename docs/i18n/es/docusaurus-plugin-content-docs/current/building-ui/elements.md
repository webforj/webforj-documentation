---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and call JavaScript functions.
slug: element
_i18n_hash: dff3b1c4df821aad3c4c7a4c66cfff65
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Los desarrolladores de webforJ tienen la opción de elegir no solo entre la rica biblioteca de componentes proporcionada, sino también de integrar componentes de otros lugares. Para facilitar esto, se puede utilizar el componente `Element` para simplificar la integración de cualquier cosa, desde elementos HTML simples hasta componentes web personalizados más complejos.

:::important
El componente `Element` no se puede extender y no es el componente base para todos los componentes dentro de webforJ. Para leer más sobre la jerarquía de componentes de webforJ, lee [este artículo](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementmeter'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementMeterView.java'
]}
height='250px'
/>

## Agregar eventos {#adding-events}

Para utilizar eventos que puedan venir con tu elemento, puedes usar los métodos `addEventListener` del componente `Element`. Agregar un evento requiere al menos el tipo/nombre del evento que el componente espera y un listener que se añadirá al evento.

También hay opciones adicionales para personalizar aún más los eventos utilizando las configuraciones de Opciones de Evento.

<ComponentDemo
path='/webforj/elementtaginput'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementTagInputView.java',
  'src/main/frontend/css/element/elementtaginput.css',
]}
height='250px'
/>

## Interacción de componentes {#component-interaction}

El componente `Element` actúa como un contenedor para otros componentes. Proporciona una forma de organizar y recuperar información para componentes secundarios, y ofrece un conjunto claro de funciones para agregar o eliminar estos componentes secundarios según sea necesario.

### Agregar componentes secundarios {#adding-child-components}

El componente `Element` admite la composición de componentes secundarios. Los desarrolladores pueden organizar y gestionar estructuras de interfaz de usuario complejas al agregar componentes como hijos al `Element`. Existen tres métodos para establecer contenido dentro de un `Element`:

1. **`add(Component... components)`**: Este método permite agregar uno o varios componentes a un `String` opcional que designa un slot específico al usarse con un componente web. Omitir el slot agregará el componente entre las etiquetas HTML.

2. **`setHtml(String html)`**: Este método toma el `String` pasado al método e inyecta como HTML dentro del componente. Dependiendo del `Element`, esto puede renderizarse de diferentes maneras.

3. **`setText(String text)`**: Este método se comporta de manera similar al método `setHtml()`, pero inyecta texto literal en el `Element`.

<ComponentDemo
path='/webforj/elementfigure'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementFigureView.java',
  'src/main/frontend/css/element/elementfigure.css',
]}
height='250px'
/>

:::warning Reemplazando contenido
Llamar a `setHtml()` o `setText()` reemplazará el contenido actualmente contenido entre las etiquetas de apertura y cierre del elemento.
:::

### Eliminar componentes {#removing-components}

Además de agregar componentes a un `Element`, se implementan los siguientes métodos para eliminar varios componentes secundarios:

1. **`remove(Component... components)`**: Este método toma uno o más componentes y los elimina como componentes secundarios.

2. **`removeAll()`**: Este método elimina todos los componentes secundarios del `Element`.

### Acceder a componentes {#accessing-components}

Para acceder a los diversos componentes secundarios presentes dentro de un `Element`, o información sobre estos componentes, están disponibles los siguientes métodos:

1. **`getComponents()`**: Este método devuelve una lista de Java `List` de todos los hijos del `Element`.

2. **`getComponents(String id)`**: Este método es similar al método anterior, pero toma la ID del componente del lado del servidor y la devuelve cuando se encuentra.

3. **`getComponentCount()`**: Devuelve el número de componentes secundarios presentes dentro del `Element`.

## Llamar a funciones de JavaScript {#calling-javascript-functions}

El componente `Element` proporciona dos métodos API que permiten llamar a funciones de JavaScript en elementos HTML.

1. **`callJsFunction(String functionName, Object... arguments)`**: Este método toma un nombre de función como cadena, y opcionalmente toma uno o más objetos como parámetros de la función. Este método se ejecuta de forma sincrónica, lo que significa que el **hilo en ejecución está bloqueado** hasta que el método JS retorna, y resulta en un viaje redondo. Los resultados de la función se devuelven como un `Object`, que se puede convertir y usar en Java.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Al igual que el método anterior, se puede pasar un nombre de función y argumentos opcionales para la función. Este método se ejecuta de forma asíncrona y **no bloquea el hilo en ejecución**. Devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite más interacción con la función y su carga útil.

### Pasar parámetros {#passing-parameters}

Los argumentos que se pasan a estos métodos que se utilizan en la ejecución de funciones JS se serializan como un array JSON. Hay dos tipos de argumentos notables que se manejan de la siguiente manera:
- `this`: Usar la palabra clave `this` le dará al método una referencia a la versión del cliente del componente que invoca.
- `Component`: Cualquier instancia de componente de Java pasada a uno de los métodos JsFunction será reemplazada por la versión del cliente del componente.

:::warning Esperando argumentos del componente
Tanto la llamada a funciones sincrónicas como asíncronas esperarán hasta que el `Element` haya sido agregado al DOM antes de ejecutar una función, pero `callJsFunction()` no esperará que se adjunten los argumentos `component`, lo que puede resultar en un fallo. Por el contrario, invocar `callJsFunctionAsync()` puede nunca completarse si un argumento de componente nunca se adjunta.
:::

En la demostración a continuación, seleccionar **Buscar foco** llama al método nativo `focus()` en la entrada de búsqueda con `callJsFunctionAsync()`. El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> resultante se utiliza para confirmar la llamada con un toast una vez que la función asíncrona se completa.

<ComponentDemo
path='/webforj/elementsearch'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementSearchView.java',
  'src/main/frontend/css/element/elementsearch.css',
]}
height='250px'
/>

## Ejecutar JavaScript {#executing-javascript}

Más allá de llamar a funciones nombradas, un `Element` puede ejecutar scripts en crudo restringidos a ese elemento con `executeJs`, `executeJsAsync`, y `executeJsVoidAsync`. Consulta [Ejecutar JavaScript](./execute-javascript.md) para estos métodos, su comportamiento sincrónico y asíncrono, y cómo los valores devueltos se convierten en tipos de Java.
