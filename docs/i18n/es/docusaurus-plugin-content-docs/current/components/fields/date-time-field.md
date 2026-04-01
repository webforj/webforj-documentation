---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: e90e93f7db172a33b2ce205bfd6a6b3c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

El componente `DateTimeField` permite a los usuarios ingresar tanto una fecha como una hora en un solo campo, abarcando año, mes, día, horas y minutos. Valida la entrada para asegurar precisión y puede presentar un selector de fecha y hora para facilitar la selección.

<!-- INTRO_END -->

## Usando `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` extiende la clase `Field` compartida, que proporciona características comunes a todos los componentes de campo. El siguiente ejemplo crea un `DateTimeField` etiquetado para seleccionar una fecha y hora de salida.

<ComponentDemo 
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## Usos {#usages}

El `DateTimeField` es mejor utilizado en escenarios donde capturar o mostrar tanto la fecha **como** la hora es esencial para tu aplicación. Aquí hay algunos ejemplos de cuándo usar el `DateTimeField`:

1. **Programación de eventos y calendarios**: Permite a los usuarios programar eventos de manera eficiente, reservar citas y gestionar sus calendarios al darles un único componente que les permite elegir la fecha y la hora.
<!-- vale off -->
2. **Check-in y check-out**: Facilita la selección de horarios de check-in y check-out cuando el periodo puede abarcar varios días.
<!-- vale on -->
3. **Registro de datos y marcas de tiempo**: Utiliza `DateTimeFields` para aplicaciones que implican registrar la fecha y la hora de cuándo ocurren eventos o cuándo un usuario envía datos.

4. **Gestión de tareas y plazos**: Los `DateTimeFields` son valiosos en aplicaciones que implican gestión de tareas o establecimiento de plazos donde tanto la fecha como la hora son relevantes para una programación precisa.

## Valor del campo (`LocalDateTime`) {#field-value-localdatetime}

Internamente, el componente `DateTimeField` representa su valor utilizando un objeto `LocalDateTime` del paquete `java.time`. Esto proporciona un control preciso sobre los componentes de fecha y hora de la entrada.

Mientras que el valor **del lado del cliente** se renderiza en función de la configuración regional del navegador del usuario (por ejemplo, formatos de fecha y hora que coinciden con las convenciones locales), el valor **analizado** sigue una estructura estricta y predecible: **`yyyy-MM-ddTHH:mm:ss`**.

### Obtener y establecer el valor {#getting-and-setting-the-value}

Para recuperar el valor actual, utiliza el método `getValue()`:

```java
LocalDateTime value = dateTimeField.getValue();
```

Para establecer el valor programáticamente, utiliza el método `setValue()`:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### Usando `setText()` {#using-settext}

Si prefieres establecer el valor a través de una cadena cruda, debe seguir el formato exacto de `yyyy-MM-ddTHH:mm:ss`.

```java
dateTimeField.setText("2024-04-27T14:30:00"); // válido

dateTimeField.setText("24-04-27T14:30:00"); // inválido
```

:::warning
 Al usar el método `setText()`, se lanzará una `IllegalArgumentException` si el componente no puede analizar la entrada en el formato `yyyy-MM-ddTHH:mm:ss`.
:::

## Utilidades estáticas {#static-utilities}

La clase DateTimeField también proporciona los siguientes métodos de utilidad estática:

- `fromDateTime(String dateTimeAsString)`: Convierte una cadena de fecha y hora en formato `yyyy-MM-ddTHH:mm:ss` a un objeto LocalDateTime que luego puede ser utilizado con esta clase, o en otros lugares.

- `toDateTime(LocalDateTime dateTime)`: Convierte un objeto LocalDateTime a una cadena de fecha y hora en formato `yyyy-MM-ddTHH:mm:ss`.

- `isValidDateTime(String dateTimeAsString)`: Comprueba si la cadena dada es una fecha y hora válida en formato `yyyy-MM-ddTHH:mm:ss`. Esto devolverá un valor booleano verdadero si es así, falso de lo contrario.

## Valor mínimo y máximo {#min-and-max-value}

### El valor mínimo {#the-min-value}

Si el valor ingresado en el componente es anterior a la marca de tiempo mínima especificada, el componente fallará en la validación de restricciones. Cuando se establecen tanto los valores mínimo como máximo, el valor mínimo debe ser una marca de tiempo que sea igual o anterior al valor máximo.

```java
// Establecer marca de tiempo mínima permitida: 1 de enero de 2023 a las 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### El valor máximo {#the-max-value}

Si el valor ingresado en el componente es posterior a la marca de tiempo máxima especificada, el componente fallará en la validación de restricciones. Cuando se establecen tanto los valores mínimo como máximo, el valor máximo debe ser una marca de tiempo que sea igual o posterior al valor mínimo.

```java
// Establecer marca de tiempo máxima permitida: 31 de diciembre de 2023 a las 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Mejores prácticas {#best-practices}

Para asegurar una experiencia óptima del usuario al usar el componente `DateTimeField`, considera las siguientes mejores prácticas:

- **Visualización de fecha localizada**: Localizar el formato de fecha e incorporar preferencias regionales asegura que las fechas se presenten en un formato familiar para el usuario.

- **Incluir zonas horarias**: Si tu aplicación maneja información sensible al tiempo a través de diferentes zonas horarias, considera incorporar la selección de zona horaria junto al campo de fecha para asegurar una representación precisa de la fecha y la hora.

- **Accesibilidad**: Utiliza el `DateTimeField` teniendo en cuenta la accesibilidad. Asegúrate de que cumpla con los estándares de accesibilidad, como proporcionar etiquetas adecuadas y ser compatible con tecnologías asistivas.

- **Autocompletar la fecha actual**: Considera proporcionar una opción para autocompletar la fecha y hora actuales como valor predeterminado en el campo de fecha y hora, si es apropiado para el caso de uso de tu aplicación.
