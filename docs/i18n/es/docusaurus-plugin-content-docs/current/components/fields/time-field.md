---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
sidebar_class_name: updated-content
_i18n_hash: aa5cbd6fb54c91be419380eeaf26e65b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

<ParentLink parent="Field" />

`TimeField` es un componente de interfaz de usuario que permite a los usuarios ingresar o seleccionar tiempos en horas, minutos y, opcionalmente, segundos. Proporciona una manera intuitiva y eficiente de manejar información relacionada con el tiempo en diversas aplicaciones.

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Usos {#usages}

El `TimeField` es ideal para elegir y mostrar horas en tu aplicación. Aquí hay algunos ejemplos de cuándo usar el `TimeField`:

1. **Programación de Eventos**: Los campos de tiempo son esenciales en aplicaciones que implican establecer horarios para eventos, citas o reuniones.

2. **Seguimiento y Registro de Tiempo**: Las aplicaciones que registran tiempo, como las hojas de tiempo, necesitan campos de tiempo para entradas precisas.

3. **Recordatorios y Alarmas**: Usar un campo de tiempo simplifica el proceso de entrada para los usuarios que configuran recordatorios o alarmas en tu aplicación.

## Valor mínimo y máximo {#min-and-max-value}

Con los métodos `setMin()` y `setMax()`, puedes especificar un rango de tiempos aceptables.

- **Para `setMin()`**: Si el valor ingresado en el componente es anterior al tiempo mínimo especificado, el componente fallará en la validación de restricciones. Cuando se establecen tanto los valores mínimos como máximos, el valor mínimo debe ser un tiempo que sea igual o anterior al valor máximo.

- **Para `setMax()`**: Si el valor ingresado en el componente es posterior al tiempo máximo especificado, el componente fallará en la validación de restricciones. Cuando se establecen tanto los valores mínimos como máximos, el valor máximo debe ser un tiempo que sea igual o posterior al valor mínimo. 

## Manejo de valores y localización {#value-handling-and-localization}

Internamente, el componente `TimeField` representa su valor usando un objeto `LocalTime` del paquete `java.time`. Esto permite a los desarrolladores interactuar con valores de tiempo precisos independientemente de cómo se representen visualmente.

Mientras que el **componente del lado del cliente muestra la hora usando la configuración regional del navegador del usuario**, el formato analizado y almacenado siempre es estandarizado como `HH:mm:ss`.

Si configuras un valor de cadena en bruto, usa el método `setText()` con cuidado:

```java
timeField.setText("09:15:00"); // válido
```

:::warning
 Al usar el método `setText()`, se lanzará una `IllegalArgumentException` si el componente no puede analizar la entrada en el formato `HH:mm:ss`.
:::

:::info Interfaz del Selector 
La apariencia de la interfaz de entrada del selector de tiempo depende no solo de la configuración regional seleccionada, sino también del navegador y del sistema operativo que se esté utilizando. Esto asegura consistencia automática con la interfaz que los usuarios ya conocen.
:::

## Utilidades estáticas {#static-utilities}

La clase `TimeField` también proporciona los siguientes métodos de utilidad estática:

- `fromTime(String timeAsString)`: Convierte una cadena de tiempo en formato HH:mm:ss a un objeto LocalTime que luego puede ser utilizado con esta clase, o en otros lugares.

- `toTime(LocalTime time)`: Convierte un LocalTime a una cadena de tiempo en formato HH:mm:ss.

- `isValidTime(String timeAsString)`: Verifica si la cadena dada es un tiempo válido en HH:mm:ss. Esto devolverá un valor booleano verdadero si es así, falso en caso contrario.

## Mejores prácticas {#best-practices}

- **Proporcionar Ejemplos Claros de Formato de Hora**: Muestra claramente a los usuarios el formato de hora esperado cerca del `TimeField`. Usa ejemplos o marcadores de posición para ayudarles a ingresar la hora correctamente. Si es posible, muestra el formato de hora basado en la ubicación del usuario.

- **Accesibilidad**: Utiliza el componente `TimeField` con la accesibilidad en mente, asegurando que cumpla con los estándares de accesibilidad como proporcionar etiquetas adecuadas, suficiente contraste de color y compatibilidad con tecnologías asistenciales.

- **Opción de Reinicio**: Ofrece una forma para que los usuarios puedan borrar fácilmente el `TimeField` a un estado vacío o predeterminado.
