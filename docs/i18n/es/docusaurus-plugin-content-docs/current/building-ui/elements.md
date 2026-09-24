---
sidebar_position: 5
title: Elementos
sidebar_class_name: updated-content
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and call JavaScript functions.
slug: element
_i18n_hash: 988b2a49584036eee3b0475215a707ae
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Los desarrolladores de webforJ tienen la opción de elegir no solo de la rica biblioteca de componentes proporcionados, sino también integrar componentes de otros lugares. Para facilitar esto, se puede utilizar el componente `Element` para simplificar la integración de cualquier cosa, desde elementos HTML simples hasta componentes web personalizados más complejos.

:::important
El componente `Element` no se puede extender y no es el componente base para todos los componentes dentro de webforJ. Para leer más sobre la jerarquía de componentes de webforJ, lee [este artículo](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementmeter'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementMeterView.java',
  'src/main/resources/static/css/element/elementMeter.css',
]}
height='240px'
/>

## Agregando eventos {#adding-events}

Para utilizar eventos que pueden venir con tu elemento, puedes usar los métodos `addEventListener` del componente `Element`. Agregar un evento requiere al menos el tipo/nombre del evento que el componente espera y un listener que se agregará al evento.

También hay opciones adicionales para personalizar aún más los eventos mediante las configuraciones de Opciones de Evento.

<ComponentDemo
path='/webforj/elementtaginput'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementTagInputView.java',
  'src/main/resources/static/css/element/elementTagInput.css',
]}
height='240px'
/>

## Interacción de componentes {#component-interaction}

El componente `Element` actúa como un contenedor para otros componentes. Proporciona una forma de organizar y recuperar información para los componentes secundarios, y ofrece un conjunto claro de funciones para agregar o eliminar estos componentes secundarios según sea necesario.

### Agregando componentes secundarios {#adding-child-components}

El componente `Element` admite la composición de componentes secundarios. Los desarrolladores pueden organizar y gestionar estructuras de interfaz de usuario complejas agregando componentes como hijos al `Element`. Existen tres métodos para establecer contenido dentro de un `Element`:

1. **`add(Component... components)`**: Este método permite agregar uno o varios componentes a un `String` opcional que designa un slot específico cuando se utiliza con un Web Component. Omitir el slot agregará el componente entre las etiquetas HTML.

2. **`setHtml(String html)`**: Este método toma el `String` pasado al método e inyecta como HTML dentro del componente. Dependiendo del `Element`, esto puede ser representado de diferentes maneras.

3. **`setText(String text)`**: Este método se comporta de manera similar al método `setHtml()`, pero inyecta texto literal en el `Element`.

<ComponentDemo
path='/webforj/elementfigure'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementFigureView.java',
  'src/main/resources/static/css/element/elementFigure.css',
]}
height='240px'
/>

:::warning Reemplazando contenido
Llamar a `setHtml()` o `setText()` reemplazará el contenido actualmente contenido entre las etiquetas de apertura y cierre del elemento.
:::

### Eliminando componentes {#removing-components}

Además de agregar componentes a un `Element`, se implementan los siguientes métodos para la eliminación de varios componentes secundarios:

1. **`remove(Component... components)`**: Este método toma uno o más componentes y los elimina como componentes secundarios.

2. **`removeAll()`**: Este método elimina todos los componentes secundarios del `Element`.

### Accediendo a componentes {#accessing-components}

Para acceder a los diversos componentes secundarios presentes dentro de un `Element`, o información sobre estos componentes, están disponibles los siguientes métodos:

1. **`getComponents()`**: Este método devuelve una `List` de Java de todos los hijos del `Element`.

2. **`getComponents(String id)`**: Este método es similar al método anterior, pero toma el ID del servidor de un componente específico y lo devuelve cuando se encuentra.

3. **`getComponentCount()`**: Devuelve el número de componentes secundarios presentes dentro del `Element`.

## Llamando funciones de JavaScript {#calling-javascript-functions}

El componente `Element` proporciona dos métodos de API que permiten llamar funciones de JavaScript en elementos HTML.

1. **`callJsFunction(String functionName, Object... arguments)`**: Este método toma un nombre de función como cadena y, opcionalmente, toma uno o más objetos como parámetros para la función. Este método se ejecuta de manera sincrónica, lo que significa que el **hilo en ejecución está bloqueado** hasta que el método de JS devuelve, y resulta en un viaje de ida y vuelta. Los resultados de la función se devuelven como un `Object`, que puede ser convertido y utilizado en Java.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Al igual que el método anterior, se puede pasar un nombre de función y argumentos opcionales para la función. Este método se ejecuta de manera asíncrona y **no bloquea el hilo en ejecución**. Devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite una mayor interacción con la función y su carga útil.

### Pasando parámetros {#passing-parameters}

Los argumentos que se pasan a estos métodos y que se utilizan en la ejecución de funciones de JS se serializan como un array JSON. Hay dos tipos de argumentos notables que se gestionan de la siguiente manera:
- `this`: Usar la palabra clave `this` le dará al método una referencia a la versión del componente invocante del lado del cliente.
- `Component`: Cualquier instancia de componente Java pasada a uno de los métodos JsFunction será reemplazada por la versión del lado del cliente del componente.

:::warning Esperando argumentos de componente
Tanto la llamada a funciones sincrónicas como asíncronas esperarán hasta que el `Element` se haya agregado al DOM antes de ejecutar una función, pero `callJsFunction()` no esperará ningún argumento de `component` para adjuntar, lo que puede resultar en un fallo. Por el contrario, invocar `callJsFunctionAsync()` puede nunca completarse si un argumento de componente nunca se adjunta.
:::

En la demostración a continuación, seleccionar **Buscar enfoque** llama al método nativo `focus()` en la entrada de búsqueda con `callJsFunctionAsync()`. El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> resultante se utiliza para confirmar la llamada con un toast una vez que la función asíncrona completa.

<ComponentDemo
path='/webforj/elementsearch'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementSearchView.java',
  'src/main/resources/static/css/element/elementSearch.css',
]}
height='240px'
/>

## Ejecutando JavaScript {#executing-javascript}

Más allá de llamar funciones nombradas, un `Element` puede ejecutar scripts en bruto ligados a ese elemento con `executeJs`, `executeJsAsync`, y `executeJsVoidAsync`. Consulta [Ejecutar JavaScript](./execute-javascript.md) para estos métodos, su comportamiento sincrónico y asíncrono, y cómo los valores devueltos se convierten en tipos de Java.
