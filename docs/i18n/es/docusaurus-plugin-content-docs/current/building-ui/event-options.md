---
sidebar_position: 7
title: Event Options
_i18n_hash: 64cfa37f974517956ccb3fd75618df50
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` es una herramienta versátil de webforJ diseñada para encapsular y gestionar configuraciones para eventos de `Element` dentro de aplicaciones webforJ. Como un contenedor de varias opciones, permite a los desarrolladores dictar con precisión cómo deben ser procesados los eventos asociados con los elementos.

## Datos del evento {#event-data}

Los datos del evento son una característica clave de `ElementEventOptions`, permitiendo a los desarrolladores adjuntar información específica a las opciones del evento. Esta funcionalidad facilita el paso de datos personalizados del cliente al servidor cuando se desencadena un evento. Esta capacidad es fundamental para transmitir contexto adicional o parámetros asociados con el evento, y permite que la información sea accesible y utilizada sin necesidad de realizar viajes adicionales al cliente.

Por ejemplo, considera un escenario donde tienes un evento de clic en un botón, y deseas pasar el nombre de usuario actual junto con el evento. En lugar de consultar el nombre de usuario de un cliente cada vez, envía esta información junto con el evento como datos.

:::tip
Para más información, consulta las páginas de [eventos](/docs/building-ui/events) y [Interacción Cliente/Servidor](/docs/architecture/client-server).
:::

Para agregar datos a las opciones del evento, puedes usar el método `addData()`.

<!-- ### Ejemplo -->

## Ejecución de JavaScript {#executing-javascript}

La clase `ElementEventOptions` permite a los desarrolladores especificar código JavaScript que debe evaluarse en el lado del cliente antes de que se dispare el evento asociado. Esta característica permite a los clientes preparar datos de evento o desencadenar eventos adicionales según sea necesario. Esto es útil en muchos casos, por ejemplo, cuando se quiere validar los datos de un formulario en el lado del cliente antes de enviarlo a través de un evento de envío de formulario.

### Uso {#usage}
Para establecer el código del evento, usa el método `setCode()`.

## Filtrado de eventos {#filtering-events}

`ElementEventOptions` incluye una característica para establecer una expresión de filtro que se evaluará en el cliente antes de que se dispare el evento. Esta expresión de filtro permite al cliente determinar si el evento debe continuar o ser detenido según ciertas condiciones. Considera un campo de entrada donde deseas desencadenar un evento solo si el texto ingresado cumple con criterios específicos, como una longitud mínima.

### Uso {#usage-1}
Para establecer el filtro del evento, usa el método `setFilter()`.

## Debounce y throttle {#debouncing-and-throttling}

### Propósito {#purpose}
`ElementEventOptions` proporciona mecanismos para debouncing y throttling de eventos. Estas características son útiles para controlar la frecuencia de los oyentes de eventos, asegurando que se activen solo bajo ciertas condiciones.

### Uso {#usage-2}
- Para establecer el debounce, usa el método `setDebounce`.
- Para establecer el throttle, usa el método `setThrottle`.

### Ejemplo {#example}
En escenarios donde deseas manejar entradas rápidas de usuario, como campos de búsqueda, puedes usar debounce para retrasar la ejecución hasta que el usuario haya terminado de escribir.

## Unión de opciones de eventos {#merging-event-options}

La clase `ElementEventOptions` admite la unión con otras instancias, permitiendo a los desarrolladores agregar varias opciones. Esta característica es útil al combinar configuraciones de diferentes fuentes.

## Anotaciones {#annotations}

### Propósito {#purpose-1}
Para mayor comodidad, `ElementEventOptions` se puede configurar usando anotaciones. Estas anotaciones proporcionan una forma más concisa y expresiva de establecer opciones de eventos.

### Ejemplo {#example-1}
Considera el siguiente ejemplo de anotación:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
debounce = @DebounceSettings(value = 200))
```
