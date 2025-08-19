---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
sidebar_class_name: updated-content
_i18n_hash: dd6fe3e8a737f5b016f92629d9767dbb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

<ParentLink parent="Field" />

El componente `DateTimeField` está diseñado para permitir a los usuarios ingresar tanto una fecha como una hora. Esto incluye especificar el año, mes y día, junto con la hora en horas y minutos. Proporciona a los usuarios la opción de validar su entrada para precisión o utilizar una interfaz de selector de fecha y hora dedicada para simplificar el proceso de selección.

<ComponentDemo 
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## Usos {#usages}

El `DateTimeField` se utiliza mejor en escenarios donde capturar o mostrar tanto la fecha **como** la hora es esencial para tu aplicación. Aquí hay algunos ejemplos de cuándo usar el `DateTimeField`:

1. **Programación de Eventos y Calendarios**: Permite a los usuarios programar eventos, reservar citas y gestionar sus calendarios de manera eficiente al ofrecerles un único componente que les permite elegir la fecha y la hora.
<!-- vale off -->
2. **Check-in y Check-out**: Facilita la selección de los tiempos de check-in y check-out por parte del usuario cuando el periodo puede abarcar varios días.
<!-- vale on -->
3. **Registro de Datos y Tiempos**: Utiliza `DateTimeFields` para aplicaciones que implican registrar la fecha y la hora en que ocurren los eventos o cuando un usuario envía datos.

4. **Gestión de Tareas y Plazos**: Los `DateTimeFields` son valiosos en aplicaciones que implican la gestión de tareas o el establecimiento de plazos donde tanto la fecha como la hora son relevantes para una programación precisa.

## Valor del campo (`LocalDateTime`) {#field-value-localdatetime}

Internamente, el componente `DateTimeField` representa su valor utilizando un objeto `LocalDateTime` del paquete `java.time`. Esto proporciona un control preciso sobre ambos componentes, la fecha y la hora de la entrada.

Mientras que el **valor del lado del cliente** se renderiza según la configuración regional del navegador del usuario (por ejemplo, formatos de fecha y hora que coinciden con las convenciones locales), el valor **analizado** sigue una estructura estricta y predecible: **`yyyy-MM-ddTHH:mm:ss`**.

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

Si prefieres establecer el valor mediante una cadena de texto sin procesar, debe seguir el formato exacto de `yyyy-MM-ddTHH:mm:ss`.

```java
dateTimeField.setText("2024-04-27T14:30:00"); // válido

dateTimeField.setText("24-04-27T14:30:00"); // inválido
```

:::warning
 Al usar el método `setText()`, se lanzará una `IllegalArgumentException` si el componente no puede analizar la entrada en el formato `yyyy-MM-ddTHH:mm:ss`.
:::

## Utilidades estáticas {#static-utilities}

La clase DateTimeField también proporciona los siguientes métodos de utilidad estáticos:

- `fromDateTime(String dateTimeAsString)`: Convierte una cadena de fecha y hora en formato `yyyy-MM-ddTHH:mm:ss` a un objeto LocalDateTime que luego se puede utilizar con esta clase, o en otro lugar.

- `toDateTime(LocalDateTime dateTime)`: Convierte un objeto LocalDateTime a una cadena de fecha y hora en formato `yyyy-MM-ddTHH:mm:ss`.

- `isValidDateTime(String dateTimeAsString)`: Verifica si la cadena dada es una fecha y hora válida en formato `yyyy-MM-ddTHH:mm:ss`. Esto devolverá un valor booleano verdadero si es así, falso en caso contrario.

## Valor mínimo y máximo {#min-and-max-value}

### El valor mínimo {#the-min-value}

Si el valor ingresado en el componente es anterior a la marca de tiempo mínima especificada, el componente fallará en la validación de restricciones. Cuando ambos valores mínimo y máximo están establecidos, el valor mínimo debe ser una marca de tiempo que sea la misma o anterior al valor máximo.

```java
// Establecer marca de tiempo mínima permitida: 1 de enero de 2023 a las 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### El valor máximo {#the-max-value}

Si el valor ingresado en el componente es posterior a la marca de tiempo máxima especificada, el componente fallará en la validación de restricciones. Cuando ambos valores mínimo y máximo están establecidos, el valor máximo debe ser una marca de tiempo que sea la misma o posterior al valor mínimo.

```java
// Establecer marca de tiempo máxima permitida: 31 de diciembre de 2023 a las 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Mejores prácticas {#best-practices}

Para garantizar una experiencia óptima para el usuario al utilizar el componente `DateTimeField`, considera las siguientes mejores prácticas:

- **Visualización de Fechas Localizadas**: Localizar el formato de fecha e incorporar preferencias regionales asegura que las fechas se presenten en un formato familiar para el usuario.

- **Incluir Zonas Horarias**: Si tu aplicación maneja información sensible al tiempo en diferentes zonas horarias, considera incorporar la selección de zona horaria junto con el campo de fecha para asegurar una representación precisa de la fecha y hora.

- **Accesibilidad**: Utiliza el `DateTimeField` con la accesibilidad en mente. Asegúrate de que cumpla con los estándares de accesibilidad, como proporcionar etiquetas adecuadas y ser compatible con tecnologías de asistencia.

- **Poblar Automáticamente la Fecha Actual**: Considera proporcionar una opción para auto-poblar la fecha y hora actuales como un valor predeterminado en el campo de fecha y hora, si es apropiado para el caso de uso de tu aplicación.
