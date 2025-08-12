---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: f76242de3a742ad3a930e1581f688592
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

El `MaskedDateField` es un control de entrada de texto diseñado para la entrada estructurada de fechas. Permite a los usuarios ingresar fechas como **números** y formatea automáticamente la entrada según una máscara definida cuando el campo pierde el foco. La máscara es una cadena que especifica el formato de fecha esperado, guiando tanto la entrada como la visualización.

Este componente soporta análisis flexible, validación, localización y restauración de valores. Es especialmente útil en formularios como registros, reservas y programación, donde se requieren formatos de fecha consistentes y específicos de la región.

:::tip ¿Buscas entrada de tiempo?
El `MaskedDateField` se centra exclusivamente en valores de **fecha**. Si necesitas un componente similar para ingresar y formatear **tiempo**, consulta el [`MaskedTimeField`](./timefield) en su lugar.
:::

## Basics {#basics}

El `MaskedDateField` se puede instanciar con o sin parámetros. Puedes definir un valor inicial, una etiqueta, un marcador de posición y un oyente de eventos para los cambios de valor.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Mask rules {#mask-rules}

El `MaskedDateField` soporta múltiples formatos de fecha utilizados en todo el mundo, que varían por el orden de día, mes y año. Los patrones comunes incluyen:

- **Día/Mes/Año** (utilizado en la mayor parte de Europa)
- **Mes/Día/Año** (utilizado en los Estados Unidos)
- **Año/Mes/Día** (utilizado en China, Japón y Corea; también el estándar ISO: `YYYY-MM-DD`)

Dentro de estos formatos, las variaciones locales incluyen la elección del separador (por ejemplo, `-`, `/` o `.`), si los años son de dos o cuatro dígitos, y si los meses o días de un solo dígito tienen ceros delante.

Para manejar esta diversidad, el `MaskedDateField` utiliza indicadores de formato, cada uno comenzando con `%`, seguido de una letra que representa una parte específica de la fecha. Estos indicadores definen cómo se analiza la entrada y cómo se muestra la fecha.

### Date format indicators {#date-format-indicators}

| Formato | Descripción |
| ------- | ----------- |
| `%Y`   | Año        |
| `%M`   | Mes       |
| `%D`   | Día         |

### Modifiers {#modifiers}

Los modificadores permiten un mayor control sobre cómo se formatean los componentes de la fecha:

| Modificador | Descripción               |
| ----------- | ------------------------- |
| `z`         | Relleno de ceros         |
| `s`         | Representación de texto corto |
| `l`         | Representación de texto largo  |
| `p`         | Número empaquetado            |
| `d`         | Decimal (formato predeterminado)  |

Estos pueden combinarse para construir una amplia variedad de máscaras de fecha.

## Date format localization {#date-format-localization}

El `MaskedDateField` se adapta a los formatos de fecha regionales estableciendo la configuración de localización apropiada. Esto asegura que las fechas se muestren y analicen de una manera que coincida con las expectativas del usuario.

| Región        | Formato     | Ejemplo      |
| ------------- | ---------- | ------------ |
| Estados Unidos | MM/DD/YYYY | `07/04/2023` |
| Europa        | DD/MM/YYYY | `04/07/2023` |
| Estándar ISO  | YYYY-MM-DD | `2023-07-04` |

Para aplicar la localización, usa el método `setLocale()`. Acepta un [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) y ajusta automáticamente tanto el formato como el análisis:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsing logic {#parsing-logic}

El `MaskedDateField` analiza la entrada del usuario en función de la máscara de fecha definida. Acepta tanto entradas numéricas completas como abbreviadas con o sin delimitadores, permitiendo una entrada flexible mientras asegura fechas válidas. 
El comportamiento de análisis depende del orden del formato definido por la máscara (por ejemplo, `%Mz/%Dz/%Yz` para mes/día/año). Este formato determina cómo se interpretan las secuencias numéricas.

Por ejemplo, asumiendo que hoy es `15 de septiembre de 2012`, así es como se interpretarían varias entradas:

### Example parsing scenarios {#example-parsing-scenarios}

| Entrada                                | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| -------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>         | Un solo dígito siempre se interpreta como un número de día dentro del mes actual, por lo que esto sería el 1 de septiembre de 2012.                                                                                 | Igual que YMD                                                                         | Igual que YMD                                                                                                                  |
| <div align="center">`12`</div>        | Dos dígitos siempre se interpretan como un número de día dentro del mes actual, por lo que esto sería el 12 de septiembre de 2012.                                                                                   | Igual que YMD                                                                         | Igual que YMD                                                                                                                  |
| <div align="center">`112`</div>       | Tres dígitos se interpretan como un número de mes de 1 dígito seguido de un número de día de 2 dígitos, por lo que esto sería el 12 de enero de 2012.                                                                        | Igual que YMD                                                                         | Los tres dígitos se interpretan como un número de día de 1 dígito seguido de un número de mes de dos dígitos, por lo que esto sería el 1 de diciembre de 2012. |
| <div align="center">`1004`</div>      | Cuatro dígitos se interpretan como MMDD, por lo que esto sería el 4 de octubre de 2012.                                                                                                                             | Igual que YMD                                                                         | Cuatro dígitos se interpretan como DDMM, por lo que esto sería el 10 de abril de 2012.                                                         |
| <div align="center">`020304`</div>    | Seis dígitos se interpretan como YYMMDD, por lo que esto sería el 4 de marzo de 2002.                                                                                                                              | Seis dígitos se interpretan como MMDDYY, por lo que esto sería el 3 de febrero de 2004.            | Seis dígitos se interpretan como DDMMYY, por lo que esto sería el 2 de marzo de 2004.                                                         |
| <div align="center">`8 digits`</div>  | Ocho dígitos se interpretan como YYYYMMDD. Por ejemplo, `20040612` es el 12 de junio de 2004.                                                                                                                | Ocho dígitos se interpretan como MMDDYYYY. Por ejemplo, `06122004` es el 12 de junio de 2004. | Ocho dígitos se interpretan como DDMMYYYY. Por ejemplo, `06122004` es el 6 de diciembre de 2004.                                        |
| <div align="center">`12/6`</div>      | Dos números separados por cualquier delimitador válido se interpretan como MM/DD, por lo que esto sería el 6 de diciembre de 2012. <br />Nota: Todos los caracteres excepto letras y dígitos son considerados delimitadores válidos. | Igual que YMD                                                                         | Dos números separados por cualquier delimitador se interpretan como DD/MM, por lo que esto sería el 12 de junio de 2012.                               |
| <div align="center">`3/4/5`</div>     | 5 de abril de 2012                                                                                                                                                                                      | 4 de marzo de 2005                                                                       | 3 de abril de 2005                                                                                                                 |

## Setting min/max constraints {#setting-minmax-constraints}

Puedes restringir el rango de fechas permitidas en un `MaskedDateField` utilizando los métodos `setMin()` y `setMax()`:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Ambos métodos aceptan valores del tipo [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). La entrada fuera del rango definido se considerará no válida.

## Restoring the value {#restoring-the-value}

El `MaskedDateField` incluye una función de restauración que restablece el valor del campo a un estado predefinido u original. Esto es útil para revertir la entrada del usuario o restablecer a una fecha predeterminada.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Ways to restore the value {#ways-to-restore-the-value}

- **Programáticamente**, llamando a `restoreValue()`
- **A través del teclado**, presionando <kbd>ESC</kbd> (esta es la tecla de restauración predeterminada a menos que sea reemplazada por un oyente de eventos)

Puedes establecer el valor a restaurar con `setRestoreValue()`, pasando una instancia de [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html).

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validation patterns {#validation-patterns}

Puedes aplicar reglas de validación del lado del cliente utilizando expresiones regulares con el método `setPattern()`:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Este patrón asegura que solo se consideren válidos los valores que coincidan con el formato `MM/DD/YYYY` (dos dígitos, barra, dos dígitos, barra, cuatro dígitos).

:::tip Formato de expresión regular
El patrón debe seguir la sintaxis RegExp de JavaScript como se documenta [aquí](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Notas sobre el manejo de entradas
El campo intenta analizar y formatear entradas de fecha numéricas en función de la máscara actual. Sin embargo, los usuarios aún pueden ingresar manualmente valores que no coincidan con el formato esperado. Si la entrada es sintácticamente válida pero semánticamente incorrecta o inanalizable (por ejemplo, `99/99/9999`), puede pasar las verificaciones de patrón pero fallar en la validación lógica.
Siempre debes validar el valor de entrada en la lógica de tu aplicación, incluso si se establece un patrón de expresión regular, para asegurar que la fecha esté correctamente formateada y sea significativa.
::::

## Date picker {#date-picker}

El `MaskedDateField` incluye un selector de calendario incorporado que permite a los usuarios seleccionar una fecha de forma visual, en lugar de escribirla. Esto mejora la usabilidad para usuarios menos técnicos o cuando se requiere una entrada precisa.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Accessing the picker {#accessing-the-picker}

Puedes acceder al selector de fechas usando `getPicker()`:

```java
DatePicker picker = dateField.getPicker();
```

### Show/hide the picker icon {#showhide-the-picker-icon}

Usa `setIconVisible()` para mostrar u ocultar el ícono del calendario junto al campo:

```java
picker.setIconVisible(true); // muestra el ícono
```

### Auto-open behavior {#auto-open-behavior}

Puedes configurar el selector para que se abra automáticamente cuando el usuario interactúa con el campo (por ejemplo, hace clic, presiona Enter o teclas de flecha):

```java
picker.setAutoOpen(true);
```

:::tip Habilitar selección a través del selector
Para asegurarte de que los usuarios solo puedan seleccionar una fecha utilizando el calendario y no ingresarla manualmente, combina las siguientes dos configuraciones:

```java
dateField.getPicker().setAutoOpen(true); // Abre el selector en la interacción del usuario
dateField.setAllowCustomValue(false);    // Desactiva la entrada de texto manual
```

Esta configuración garantiza que toda la entrada de fecha provenga a través de la interfaz del selector, lo cual es útil cuando deseas un control estricto sobre el formato y eliminar problemas de análisis de entradas escritas.
:::

### Manually open the calendar {#manually-open-the-calendar}

Para abrir el calendario programáticamente:

```java
picker.open();
```

O usa el alias:

```java
picker.show(); // igual que open()
```

### Show weeks in the calendar {#show-weeks-in-the-calendar}

El selector puede mostrar opcionalmente números de semanas en la vista del calendario:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

El `MaskedDateFieldSpinner` extiende el [`MaskedDateField`](#basics) agregando controles de spinner que permiten a los usuarios incrementar o decrementar la fecha utilizando teclas de flecha o botones de la interfaz. Proporciona un estilo de interacción más guiado, especialmente útil en aplicaciones de estilo de escritorio.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Key features {#key-features}

- **Interacción de paso de fecha interactivo:**  
  Usa las teclas de flecha o los botones de giro para incrementar o decrementar el valor de la fecha.

- **Unidad de paso personalizable:**  
  Elige qué parte de la fecha modificar usando `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Las opciones incluyen `DAY`, `WEEK`, `MONTH`, y `YEAR`.

- **Límites min/max:**  
  Hereda el soporte para fechas mínimas y máximas permitidas usando `setMin()` y `setMax()`.

- **Salida formateada:**  
  Totalmente compatible con las máscaras y configuraciones de localización del `MaskedDateField`.

### Example: Configure weekly stepping {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Esto hace que cada paso de giro avance o retroceda la fecha en una semana.

## Styling {#styling}

<TableBuilder name="MaskedDateField" />
