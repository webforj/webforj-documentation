---
sidebar_position: 4
title: Event Options
_i18n_hash: d780e41b809f0e3df55f65a1c71983a0
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` es una herramienta versátil de webforJ diseñada para encapsular y gestionar configuraciones de eventos de `Element` dentro de aplicaciones webforJ. Como un contenedor de varias opciones, permite a los desarrolladores determinar con precisión cómo deben ser procesados los eventos asociados a los elementos.

## Datos del evento {#event-data}

Los datos del evento son una característica clave de `ElementEventOptions`, permitiendo a los desarrolladores adjuntar información específica a las opciones de eventos. Esta funcionalidad facilita el paso de datos personalizados del cliente al servidor cuando se activa un evento. Esta capacidad es fundamental para transmitir contexto adicional o parámetros asociados al evento, y permite que la información sea accesible y utilizable sin necesidad de hacer viajes adicionales al cliente.

Por ejemplo, considera un escenario donde tienes un evento de clic en un botón, y deseas pasar el nombre de usuario del usuario actual junto con el evento. En lugar de consultar el nombre de usuario de un cliente cada vez, envía esta información junto con el evento como datos.

:::tip
Para más información, consulta las páginas de [eventos](../../building-ui/events) y [Interacción Cliente/Servidor](../../architecture/client-server).
:::

Para añadir datos a las opciones del evento, puedes usar el método `addData()`.

## Ejecutando JavaScript {#executing-javascript}

La clase `ElementEventOptions` permite a los desarrolladores especificar código JavaScript que se evaluará del lado del cliente antes de que se active el evento asociado. Esta característica permite a los clientes preparar datos del evento o activar eventos adicionales según sea necesario. Esto es útil en muchos casos, por ejemplo, cuando se desea validar datos de formularios en el lado del cliente antes de enviarlos a través de un evento de envío de formulario.

### Uso {#usage}
Para establecer el código del evento, utiliza el método `setCode()`.

## Filtrando eventos {#filtering-events}

`ElementEventOptions` incluye una característica para establecer una expresión de filtro que se evaluará en el cliente antes de que se dispare el evento. Esta expresión de filtro permite al cliente determinar si el evento debe continuar o ser detenido según ciertas condiciones. Considera un campo de entrada donde deseas activar un evento solo si el texto ingresado cumple con criterios específicos, como una longitud mínima.

### Uso {#usage-1}
Para establecer el filtro del evento, utiliza el método `setFilter()`.

## Debounce y throttling {#debouncing-and-throttling}

### Propósito {#purpose}
`ElementEventOptions` proporciona mecanismos para debouncing y throttling de eventos. Estas características son útiles para controlar la frecuencia de los oyentes de eventos, asegurando que se activen solo bajo ciertas condiciones.

### Uso {#usage-2}
- Para establecer debounce, utiliza el método `setDebounce`.
- Para establecer throttle, utiliza el método `setThrottle`.

### Ejemplo {#example}
En situaciones donde deseas manejar la entrada rápida del usuario, como en campos de búsqueda, puedes usar debounce para retrasar la ejecución hasta que el usuario haya terminado de escribir.

## Fusionando opciones de evento {#merging-event-options}

La clase `ElementEventOptions` admite la fusión con otras instancias, permitiendo a los desarrolladores agregar varias opciones. Esta característica es útil al combinar configuraciones de diferentes fuentes.

## Anotaciones {#annotations}

### Propósito {#purpose-1}
Para conveniencia, `ElementEventOptions` se puede configurar utilizando anotaciones. Estas anotaciones proporcionan una forma más concisa y expresiva de establecer opciones de eventos.

### Ejemplo {#example-1}
Considera el siguiente ejemplo de anotación:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
      debounce = @DebounceSettings(value = 200))
```
