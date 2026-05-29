---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: ca6e544259fc218b59cebd14d34e4530
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` es un componente de interfaz de usuario que permite a los usuarios ingresar o seleccionar horas, minutos y opcionalmente segundos. Proporciona una forma intuitiva y eficiente de manejar información relacionada con el tiempo en diversas aplicaciones.

<!-- INTRO_END -->

## Usando el `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` extiende la clase `Field` compartida, que proporciona características comunes a todos los componentes de campo. El siguiente ejemplo crea un `TimeField` de recordatorio inicializado a la hora actual.

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## Usos {#usages}

El `TimeField` es ideal para elegir y mostrar horas en tu aplicación. Aquí hay algunos ejemplos de cuándo usar el `TimeField`:

1. **Programación de Eventos**: Los campos de tiempo son esenciales en aplicaciones que implican establecer horas para eventos, citas o reuniones.

2. **Seguimiento y Registro de Tiempo**: Las aplicaciones que registran tiempo, como hojas de tiempo, necesitan campos de tiempo para entradas precisas.

3. **Recordatorios y Alarmas**: Usar un campo de tiempo simplifica el proceso de entrada para los usuarios que establecen recordatorios o alarmas en tu aplicación.

## Valor mínimo y máximo {#min-and-max-value}

Con los métodos `setMin()` y `setMax()`, puedes especificar un rango de tiempos aceptables.

- **Para `setMin()`**: Si el valor ingresado en el componente es anterior al tiempo mínimo especificado, el componente fallará la validación de restricciones. Cuando se establecen tanto los valores mínimo como máximo, el valor mínimo debe ser un tiempo que sea el mismo o anterior al valor máximo.

- **Para `setMax()`**: Si el valor ingresado en el componente es posterior al tiempo máximo especificado, el componente fallará la validación de restricciones. Cuando se establecen tanto los valores mínimo como máximo, el valor máximo debe ser un tiempo que sea el mismo o posterior al valor mínimo. 

## Manejo de valor y localización {#value-handling-and-localization}

Internamente, el componente `TimeField` representa su valor utilizando un objeto `LocalTime` del paquete `java.time`. Esto permite a los desarrolladores interactuar con valores de tiempo precisos independientemente de cómo se representen visualmente.

Mientras que el **componente del lado del cliente muestra la hora utilizando la configuración regional del navegador del usuario**, el formato analizado y almacenado siempre está estandarizado como `HH:mm:ss`.

Si se establece un valor de cadena en crudo, usa el método `setText()` con cuidado:

```java
timeField.setText("09:15:00"); // válido
```

:::warning
 Al usar el método `setText()`, se lanzará una `IllegalArgumentException` si el componente no puede analizar la entrada en el formato `HH:mm:ss`.
:::


:::info Interfaz de selección 
La apariencia de la interfaz del selector de tiempo depende no solo de la configuración regional seleccionada, sino también del navegador y del sistema operativo que se esté utilizando. Esto asegura una consistencia automática con la interfaz a la que los usuarios ya están familiarizados.
:::

## Utilidades estáticas {#static-utilities}

La clase `TimeField` también proporciona los siguientes métodos de utilidad estáticos:

- `fromTime(String timeAsString)`: Convierte una cadena de tiempo en formato HH:mm:ss a un objeto LocalTime que se puede utilizar con esta clase, o en otros lugares.

- `toTime(LocalTime time)`: Convierte un LocalTime a una cadena de tiempo en formato HH:mm:ss.

- `isValidTime(String timeAsString)`: Verifica si la cadena dada es un tiempo válido en formato HH:mm:ss. Esto devolverá un valor booleano verdadero si es así, falso de lo contrario.

## Mejores prácticas {#best-practices}

- **Proporcionar Ejemplos Claros del Formato de Hora**: Muestra claramente a los usuarios el formato de hora esperado cerca del `TimeField`. Usa ejemplos o marcadores de posición para ayudarles a ingresar la hora correctamente. Si es posible, muestra el formato de hora basado en la ubicación del usuario.

- **Accesibilidad**: Utiliza el componente `TimeField` teniendo en cuenta la accesibilidad, asegurándote de que cumpla con los estándares de accesibilidad, como proporcionar etiquetas adecuadas, suficiente contraste de color y compatibilidad con tecnologías de asistencia.

- **Opción de Reinicio**: Proporciona una forma para que los usuarios puedan limpiar fácilmente el `TimeField` a un estado vacío o predeterminado.
