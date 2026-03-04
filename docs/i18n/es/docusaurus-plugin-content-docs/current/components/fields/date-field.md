---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
_i18n_hash: bf6829e0fafbd0c69a49a5563e8a298b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

El componente `DateField` permite a los usuarios ingresar o seleccionar una fecha por año, mes y día. Maneja la validación automáticamente, por lo que las fechas formateadas incorrectamente se detectan antes de que se envíe el formulario.

<!-- INTRO_END -->

## Usando `DateField` {#using-datefield}

<ParentLink parent="Field" />

`DateField` extiende la clase `Field` compartida, que proporciona características comunes en todos los componentes de campo. El siguiente ejemplo crea DateFields de salida y retorno que permanecen sincronizados, con restricciones mínimas y máximas para limitar el rango seleccionable.

<ComponentDemo 
path='/webforj/datefield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java'
/>

## Valor del campo (`LocalDate`) {#field-value-localdate}

El componente `DateField` almacena su valor internamente como un objeto `LocalDate`, que representa una fecha sin información de hora o zona horaria. Esto permite un manejo preciso de entradas basadas en calendario a través de diferentes sistemas.

:::info Valor mostrado VS valor analizado 
Mientras que el **valor mostrado** se adapta a la configuración regional del navegador del usuario, asegurando un formato familiar regionalmente (por ejemplo, `MM/DD/YYYY` en los Estados Unidos o `DD.MM.YYYY` en Europa), el **valor analizado** siempre se basa en el formato fijo de `yyyy-MM-dd`.
:::

### Obtener y establecer el valor de `LocalDate` {#getting-and-setting-the-localdate-value}

Para recuperar el valor actual, utiliza el método `getValue()`:

```java
LocalDate value = dateField.getValue();
```

Para establecer el valor programáticamente, usa el método `setValue()`:

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### Usando `setText()` {#using-settext}

También puedes asignar un valor usando una cadena sin procesar, pero debe seguir el formato exacto `yyyy-MM-dd`:

```java
dateField.setText("2024-04-27"); // válido

dateField.setText("04/27/2024"); // no válido
```

:::warning
 Al usar el método `setText()`, se lanzará una `IllegalArgumentException` si el componente no puede analizar la entrada en el formato `yyyy-MM-dd`.
:::

## Usos {#usages}

El `DateField` es ideal para elegir y mostrar fechas en tu aplicación. Aquí hay algunos ejemplos de cuándo usar el `DateField`:

1. **Programación de Eventos y Calendarios**: Los campos de fecha son esenciales en aplicaciones que involucran la programación de eventos, la reserva de citas o el seguimiento de fechas importantes.

2. **Entradas de Formularios**: Simplifican el proceso de selección de fechas para un usuario que completa un formulario que requiere una fecha, como un cumpleaños.

3. **Sistemas de Reservas y Reservaciones**: Las aplicaciones que implican sistemas de reservas a menudo requieren que los usuarios ingresen fechas específicas. Un campo de fecha agiliza el proceso y asegura una selección de fecha precisa.

4. **Gestión de Tareas y Plazos**: Los campos de fecha son valiosos en aplicaciones que implican gestión de tareas o establecimiento de plazos. Los usuarios pueden especificar fácilmente fechas de vencimiento, fechas de inicio u otra información sensible al tiempo.

## Valor mínimo y máximo {#min-and-max-value}

### El valor mínimo {#the-min-value}
El método `setMin()` define la fecha más temprana que un usuario puede ingresar en el componente. Si la entrada es anterior a la mínima especificada, fallará la validación de restricciones. Cuando se usa junto con `setMax()`, la mínima debe ser una fecha que sea la misma o anterior a la máxima.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Mínimo permitido: 1 de enero de 2023
```

### El valor máximo {#the-max-value}
El método `setMax()` define la última fecha que acepta el componente. Si la fecha ingresada es posterior a la máxima especificada, la entrada es no válida. Cuando se definen ambos valores, la máxima debe ser una fecha que sea la misma o posterior a la mínima.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Máximo permitido: 31 de diciembre de 2023
```

## Utilidades estáticas {#static-utilities}

La clase `DateField` también proporciona los siguientes métodos de utilidad estáticos:

- `fromDate(String dateAsString)`: Convierte una cadena de fecha en formato `yyyy-MM-dd` a un objeto `LocalDate`, que puede ser utilizado con este campo, o en otros lugares.

- `toDate(LocalDate date)`: Convierte un objeto `LocalDate` a una cadena de fecha en formato `yyyy-MM-dd`.

- `isValidDate(String dateAsString)`: Verifica si la cadena dada es una fecha válida `yyyy-MM-dd`.

## Mejores prácticas {#best-practices}

Para asegurar una experiencia óptima para el usuario al usar el componente `DateField`, considera las siguientes mejores prácticas:

- **Accesibilidad**: Utiliza etiquetas apropiadas para asegurar que los usuarios con tecnologías de asistencia puedan navegar y usar fácilmente los campos de fecha en tu aplicación.

- **Autocompletar la Fecha Actual**: Si es apropiado para el caso de uso de tu aplicación, autocompleta el campo de fecha con la fecha actual.
