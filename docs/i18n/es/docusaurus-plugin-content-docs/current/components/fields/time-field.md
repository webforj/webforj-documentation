---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 9b4c187f1c86207e13b38812f0eb7e6c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` es un componente de interfaz de usuario que permite a los usuarios ingresar o seleccionar horarios con precisión de horas y minutos. Si se proporcionan segundos, el componente los descartará.

<!-- INTRO_END -->

## Usando el `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` extiende la clase compartida `Field`, que proporciona características comunes a todos los componentes de campo. El siguiente ejemplo crea un `TimeField` de recordatorio inicializado a la hora actual.

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## Usos {#usages}

El `TimeField` es ideal para elegir y mostrar horarios en tu aplicación. Aquí hay algunos ejemplos de cuándo usar el `TimeField`:

1. **Programación de Eventos**: Los campos de hora son esenciales en aplicaciones que involucran la fijación de horarios para eventos, citas o reuniones.

2. **Seguimiento y Registro de Tiempos**: Las aplicaciones que registran tiempo, como las hojas de horas, necesitan campos de hora para entradas precisas.

3. **Recordatorios y Alarmas**: Usar un campo de hora simplifica el proceso de entrada para los usuarios que establecen recordatorios o alarmas en tu aplicación.

## Valor mínimo y máximo {#min-and-max-value}

Con los métodos `setMin()` y `setMax()`, puedes especificar un rango de tiempos aceptables.

- **Para `setMin()`**: Si el valor ingresado en el componente es anterior al tiempo mínimo especificado, el componente no pasará la validación de restricción. Cuando se establecen tanto los valores mínimo como máximo, el valor mínimo debe ser un tiempo que sea igual o anterior al valor máximo.

- **Para `setMax()`**: Si el valor ingresado en el componente es posterior al tiempo máximo especificado, el componente no pasará la validación de restricción. Cuando se establecen tanto los valores mínimo como máximo, el valor máximo debe ser un tiempo que sea igual o posterior al valor mínimo.

## Manejo de valores y localización {#value-handling-and-localization}

Internamente, el componente `TimeField` representa su valor utilizando un objeto `LocalTime` del paquete `java.time`. Esto permite a los desarrolladores interactuar con valores de hora precisos independientemente de cómo se rendericen visualmente.

El navegador determina cómo el selector muestra la hora para la localidad del usuario. El valor de texto del campo utiliza el formato de 24 horas `HH:mm`, y su valor `LocalTime` se trunca a minutos.

Si se establece un valor de cadena en bruto, usa el método `setText()` con cuidado:

```java
timeField.setText("09:15");    // válido
timeField.setText("09:15:30"); // también válido; se desprecian los segundos, quedando 09:15
```

:::warning
Al usar `setText()`, se lanzará una `IllegalArgumentException` si la entrada no se puede analizar como una hora válida. Se aceptan entradas tanto `HH:mm` como `HH:mm:ss`, pero se descartan los segundos.
:::


:::info UI del selector
La apariencia de la UI del selector de hora depende de la localidad seleccionada, el navegador y el sistema operativo. Esto crea una consistencia automática con la interfaz con la que los usuarios ya están familiarizados.
:::

## Utilidades estáticas {#static-utilities}

La clase `TimeField` también proporciona los siguientes métodos de utilidad estáticos:

- `fromTime(String timeAsString)`: Analiza una cadena de tiempo, con o sin segundos, en un `LocalTime` truncado a minutos.

- `toTime(LocalTime time)`: Convierte un `LocalTime` a una cadena en formato `HH:mm`, desechando los segundos.

- `isValidTime(String timeAsString)`: Verifica si una cadena de tiempo es válida, incluyendo entradas `HH:mm` y `HH:mm:ss`. Retorna `true` si es válida y `false` en caso contrario.

## Mejores prácticas {#best-practices}

- **Proporcionar Ejemplos Claros del Formato de Hora**: Muestra claramente a los usuarios el formato de hora esperado cerca del `TimeField`. Utiliza ejemplos o marcadores de posición para ayudarles a ingresar la hora correctamente. Si es posible, muestra el formato de hora basado en la ubicación del usuario.

- **Accesibilidad**: Usa el componente `TimeField` con la accesibilidad en mente, cumpliendo con los estándares de accesibilidad como etiquetas adecuadas, suficiente contraste de color y compatibilidad con tecnologías asistivas.

- **Opción de Reinicio**: Proporciona una manera para que los usuarios puedan limpiar fácilmente el `TimeField` a un estado vacío o por defecto.
