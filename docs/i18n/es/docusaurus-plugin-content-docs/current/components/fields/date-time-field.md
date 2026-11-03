---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: 9ccb17716ffd5fa2d32781fc603d5042
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

El componente `DateTimeField` permite a los usuarios ingresar tanto una fecha como una hora en un solo campo, abarcando año, mes, día, horas y minutos. Valida la entrada por precisión y puede presentar un selector de fecha y hora para facilitar la selección.

<!-- INTRO_END -->

## Usando `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` extiende la clase compartida `Field`, que proporciona características comunes a todos los componentes de campo. El siguiente ejemplo crea un `DateTimeField` etiquetado para seleccionar una fecha y hora de salida.

<ComponentDemo
path='/webforj/datetimefield'
files={['src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java']}
/>

## Usos {#usages}

El `DateTimeField` es mejor utilizado en escenarios donde capturar o mostrar tanto la fecha **como** la hora es esencial para tu aplicación. Aquí hay algunos ejemplos de cuándo usar el `DateTimeField`:

1. **Programación de Eventos y Calendarios**: Permite a los usuarios programar eventos, reservar citas y gestionar sus calendarios de manera eficiente al darles un solo componente que les permite elegir la fecha y la hora.
<!-- vale off -->
2. **Check-in y check-out**: Facilita la selección de los tiempos de check-in y check-out cuando el período puede abarcar varios días.
<!-- vale on -->
3. **Registro de Datos y Timestamps**: Utiliza `DateTimeFields` para aplicaciones que implican registrar la fecha y la hora en que ocurren eventos o cuando un usuario envía datos.

4. **Gestión de Tareas y Plazos**: Los `DateTimeFields` son valiosos en aplicaciones que involucran la gestión de tareas o el establecimiento de plazos donde tanto la fecha como la hora son relevantes para una programación precisa.

## Valor del campo (`LocalDateTime`) {#field-value-localdatetime}

Internamente, el componente `DateTimeField` representa su valor utilizando un objeto `LocalDateTime` del paquete `java.time`. Los valores se truncan a minutos, por lo que los segundos y los segundos fraccionarios se descartan.

El navegador muestra la fecha y la hora de acuerdo con la configuración regional del usuario, mientras que el valor de texto del campo utiliza el formato `yyyy-MM-ddTHH:mm`. `LocalDateTime` no incluye una zona horaria, y no se acepta entrada con un sufijo de zona horaria como `Z` o `+02:00`.

### Obtener y establecer el valor {#getting-and-setting-the-value}

Para obtener el valor actual, utiliza el método `getValue()`:

```java
LocalDateTime value = dateTimeField.getValue();
```

Para establecer programáticamente el valor, utiliza el método `setValue()`:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30));
```

### Usando `setText()` {#using-settext}

Para establecer el valor a partir de una cadena sin procesar, utiliza `setText()` con una fecha y hora local, como `yyyy-MM-ddTHH:mm`. La entrada también puede incluir segundos o segundos fraccionarios, los cuales se descartan.

```java
dateTimeField.setText("2024-04-27T14:30");       // válido
dateTimeField.setText("2024-04-27T14:30:45");    // válido; los segundos se descartan

dateTimeField.setText("24-04-27T14:30");        // inválido; usa un año de cuatro dígitos
dateTimeField.setText("2024-04-27T14:30Z");      // inválido; no se aceptan sufijos de zona horaria
```

:::warning
Al usar `setText()`, se lanza una `IllegalArgumentException` si la entrada no puede ser analizada como una fecha-hora local válida. Se aceptan entradas con precisión de minuto y entradas con segundos, pero no se aceptan sufijos de zona horaria.
:::

## Utilidades estáticas {#static-utilities}

La clase DateTimeField también proporciona los siguientes métodos utilitarios estáticos:

- `fromDateTime(String dateTimeAsString)`: analiza una cadena de fecha-hora local, con o sin segundos, en un `LocalDateTime` truncado a minutos.

- `toDateTime(LocalDateTime dateTime)`: convierte un `LocalDateTime` a una cadena en el formato `yyyy-MM-ddTHH:mm`. Se descartan los segundos y los segundos fraccionarios.

- `isValidDateTime(String dateTimeAsString)`: verifica si una cadena es una fecha-hora local válida. Se aceptan entradas con segundos o segundos fraccionarios. Devuelve `false` para entradas inválidas o sufijos de zona horaria.

## Valor mínimo y máximo {#min-and-max-value}

### El valor mínimo {#the-min-value}

Si el valor ingresado en el componente es anterior a la marca de tiempo mínima especificada, el componente fallará en la validación de restricciones. Cuando se establecen tanto los valores mínimo como máximo, el valor mínimo debe ser una marca de tiempo que sea la misma o anterior que el valor máximo.

```java
// Establecer la marca de tiempo mínima permitida: 1 de enero de 2023 a las 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### El valor máximo {#the-max-value}

Si el valor ingresado en el componente es posterior a la marca de tiempo máxima especificada, el componente fallará en la validación de restricciones. Cuando se establecen tanto los valores mínimo como máximo, el valor máximo debe ser una marca de tiempo que sea la misma o posterior que el valor mínimo.

```java
// Establecer la marca de tiempo máxima permitida: 31 de diciembre de 2023 a las 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Mejores prácticas {#best-practices}

Para asegurar una experiencia de usuario óptima al usar el componente `DateTimeField`, considera las siguientes mejores prácticas:

- **Visualización de Fecha Localizada**: Localizar el formato de la fecha e incorporar preferencias regionales asegura que las fechas se presenten en un formato familiar para el usuario.

- **Manejar Zonas Horarias por Separado**: si tu aplicación funciona a través de zonas horarias, proporciona un selector de zona horaria separado y maneja las conversiones en tu aplicación. El `DateTimeField` almacena solo la fecha y hora locales.

- **Accesibilidad**: Utiliza el `DateTimeField` teniendo en cuenta la accesibilidad. Asegúrate de que cumpla con los estándares de accesibilidad, como proporcionar etiquetas adecuadas y ser compatible con tecnologías asistivas.

- **Autocompletar Fecha Actual**: considera proporcionar una opción para autocompletar la fecha y hora actuales como valor predeterminado en el campo de fecha y hora, si es apropiado para el caso de uso de tu aplicación.
