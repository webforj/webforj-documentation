---
sidebar_position: 4
title: Event Options
_i18n_hash: 8bf57e40eec8e571f3d62266e388f114
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` es una herramienta versátil de webforJ diseñada para encapsular y gestionar los ajustes de configuración para los eventos de `Element` dentro de las aplicaciones webforJ. Como un contenedor de varias opciones, permite a los desarrolladores dictar con precisión cómo se deben procesar los eventos asociados con los elementos.

## Datos del evento {#event-data}

Los datos del evento son una característica clave de `ElementEventOptions`, lo que permite a los desarrolladores adjuntar información específica a las opciones del evento. Esta funcionalidad facilita el envío de datos personalizados del cliente al servidor cuando se activa un evento. Esta capacidad es fundamental para transmitir contexto adicional o parámetros asociados con el evento, y permite que la información sea accesible y utilizada sin necesidad de realizar viajes adicionales al cliente.

Por ejemplo, considere un escenario en el que tiene un evento de clic en un botón y desea pasar el nombre de usuario del usuario actual junto con el evento. En lugar de consultar el nombre de usuario de un usuario desde el cliente cada vez, envíe esta información junto con el evento como datos.

:::tip
Para más información, consulte las páginas de [eventos](../../building-ui/events) y [Interacción Cliente/Servidor](../../architecture/client-server).
:::

Para agregar datos a las opciones de evento, puede usar el método `addData()`.

<!-- ### Ejemplo -->

## Ejecución de JavaScript {#executing-javascript}

La clase `ElementEventOptions` permite a los desarrolladores especificar código JavaScript que se evaluará en el lado del cliente antes de que se active el evento asociado. Esta característica permite a los clientes preparar los datos del evento o desencadenar eventos adicionales según sea necesario. Esto es útil en muchos casos, por ejemplo, cuando se desea validar datos de formularios en el lado del cliente antes de enviarlos a través de un evento de envío de formulario.

### Uso {#usage}
Para establecer el código del evento, use el método `setCode()`.

## Filtrando eventos {#filtering-events}

`ElementEventOptions` incluye una característica para establecer una expresión de filtro que se evaluará en el cliente antes de que se active el evento. Esta expresión de filtro permite al cliente determinar si el evento debe continuar o ser detenido según ciertas condiciones. Considere un campo de entrada donde desea activar un evento solo si el texto ingresado cumple con criterios específicos, como una longitud mínima.

### Uso {#usage-1}
Para establecer el filtro del evento, use el método `setFilter()`.

## Debouncing y throttling {#debouncing-and-throttling}

### Propósito {#purpose}
`ElementEventOptions` proporciona mecanismos para debouncing y throttling de eventos. Estas características son útiles para controlar la frecuencia de los oyentes de eventos, asegurando que se activen solo bajo ciertas condiciones.

### Uso {#usage-2}
- Para establecer debounce, use el método `setDebounce`.
- Para establecer throttle, use el método `setThrottle`.

### Ejemplo {#example}
En escenarios donde desea manejar entradas rápidas del usuario, como campos de entrada de búsqueda, puede usar debounce para retrasar la ejecución hasta que el usuario haya terminado de escribir.

## Fusionando opciones de eventos {#merging-event-options}

La clase `ElementEventOptions` admite la fusión con otras instancias, lo que permite a los desarrolladores agregar varias opciones. Esta característica es útil al combinar configuraciones de diferentes fuentes.

## Anotaciones {#annotations}

### Propósito {#purpose-1}
Para mayor comodidad, `ElementEventOptions` se puede configurar utilizando anotaciones. Estas anotaciones proporcionan una forma más concisa y expresiva de establecer opciones de eventos.

### Ejemplo {#example-1}
Considere la siguiente anotación de ejemplo:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
      debounce = @DebounceSettings(value = 200))
```
