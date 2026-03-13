---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 994cad91e2870d59f3c0eec7c2b47141
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` es un componente de interfaz de usuario que permite a los usuarios ingresar o seleccionar horas, minutos y, opcionalmente, segundos. Proporciona una forma intuitiva y eficiente de manejar información relacionada con el tiempo en diversas aplicaciones.

<!-- INTRO_END -->

## Usando el `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` extiende la clase compartida `Field`, que proporciona características comunes a todos los componentes de campo. El siguiente ejemplo crea un `TimeField` de recordatorio inicializado a la hora actual.

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Usos {#usages}

El `TimeField` es ideal para elegir y mostrar horas en tu aplicación. Aquí hay algunos ejemplos de cuándo usar el `TimeField`:

1. **Programación de Eventos**: Los campos de tiempo son esenciales en aplicaciones que implican establecer horas para eventos, citas o reuniones.

2. **Seguimiento y Registro de Tiempo**: Las aplicaciones que rastrean el tiempo, como las hojas de tiempo, necesitan campos de tiempo para entradas precisas.

3. **Recordatorios y Alarmas**: Usar un campo de tiempo simplifica el proceso de entrada para los usuarios que establecen recordatorios o alarmas en tu aplicación.

## Valor mínimo y máximo {#min-and-max-value}

Con los métodos `setMin()` y `setMax()`, puedes especificar un rango de horas aceptables.

- **Para `setMin()`**: Si el valor ingresado en el componente es anterior a la hora mínima especificada, el componente fallará la validación de restricciones. Cuando se establecen los valores mínimo y máximo, el valor mínimo debe ser una hora que sea igual o anterior al valor máximo.

- **Para `setMax()`**: Si el valor ingresado en el componente es posterior a la hora máxima especificada, el componente fallará la validación de restricciones. Cuando se establecen los valores mínimo y máximo, el valor máximo debe ser una hora que sea igual o posterior al valor mínimo.

## Manejo de valores y localización {#value-handling-and-localization}

Internamente, el componente `TimeField` representa su valor utilizando un objeto `LocalTime` del paquete `java.time`. Esto permite a los desarrolladores interactuar con valores de tiempo precisos sin importar cómo se visualizan.

Mientras que el **componente del lado del cliente muestra la hora utilizando la configuración regional del navegador del usuario**, el formato analizado y almacenado siempre está estandarizado como `HH:mm:ss`.

Si configuras un valor de cadena sin procesar, utiliza el método `setText()` con cuidado:

```java
timeField.setText("09:15:00"); // válido
```

:::warning
 Al usar el método `setText()`, se lanzará una `IllegalArgumentException` si el componente no puede analizar la entrada en el formato `HH:mm:ss`.
:::


:::info Interfaz gráfica del selector 
La apariencia de la interfaz de entrada del selector de hora depende no solo de la configuración regional seleccionada, sino también del navegador y el sistema operativo que se esté utilizando. Esto asegura una consistencia automática con la interfaz que los usuarios ya conocen.
:::

## Utilidades estáticas {#static-utilities}

La clase `TimeField` también proporciona los siguientes métodos de utilidad estática:

- `fromTime(String timeAsString)`: Convierte una cadena de tiempo en formato HH:mm:ss a un objeto LocalTime que se puede utilizar con esta clase, o en otro lugar.

- `toTime(LocalTime time)`: Convierte un LocalTime a una cadena de tiempo en formato HH:mm:ss.

- `isValidTime(String timeAsString)`: Verifica si la cadena dada es una hora válida en formato HH:mm:ss. Esto devolverá un valor booleano verdadero si es así, falso de lo contrario.

## Mejores prácticas {#best-practices}

- **Proporcionar Ejemplos Claros de Formato de Hora**: Muestra claramente a los usuarios el formato de hora esperado cerca del `TimeField`. Usa ejemplos o marcadores de posición para ayudarlos a ingresar la hora correctamente. Si es posible, muestra el formato de hora basado en la ubicación del usuario.

- **Accesibilidad**: Utiliza el componente `TimeField` teniendo en cuenta la accesibilidad, asegurando que cumpla con los estándares de accesibilidad, como proporcionar etiquetas adecuadas, un contraste de color suficiente y compatibilidad con tecnologías de asistencia.

- **Opción de Restablecer**: Proporciona una forma para que los usuarios puedan borrar fácilmente el `TimeField` a un estado vacío o predeterminado.
