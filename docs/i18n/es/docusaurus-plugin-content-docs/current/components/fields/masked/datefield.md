---
title: MaskedDateField
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 93973075b9f8f9bcc3eddf18e8b01017
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

El `MaskedDateField` es un control de entrada de texto diseñado para la entrada estructurada de fechas. Permite a los usuarios introducir fechas como **números** y formatea automáticamente la entrada basada en una máscara definida cuando el campo pierde el foco. La máscara es una cadena que especifica el formato de fecha esperado, guiando tanto la entrada como la visualización.

Este componente admite análisis flexible, validación, localización y restauración de valores. Es especialmente útil en formularios como registros, reservas y programación, donde se requieren formatos de fecha consistentes y específicos de la región.

:::tip ¿Buscando entrada de tiempo?
El `MaskedDateField` está enfocado únicamente en valores de **fecha**. Si necesitas un componente similar para ingresar y formatear **hora**, consulta el [`MaskedTimeField`](./timefield) en su lugar.
:::

## Basics {#basics}

El `MaskedDateField` se puede instanciar con o sin parámetros. Puedes definir un valor inicial, una etiqueta, un marcador de posición y un oyente de eventos para cambios de valor.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Reglas de máscara {#mask-rules}

El `MaskedDateField` admite múltiples formatos de fecha utilizados en todo el mundo, que varían según el orden de día, mes y año. Los patrones comunes incluyen:

- **Día/Mes/Año** (utilizado en la mayor parte de Europa)
- **Mes/Día/Año** (utilizado en los Estados Unidos)
- **Año/Mes/Día** (utilizado en China, Japón y Corea; también el estándar ISO: `YYYY-MM-DD`)

Dentro de estos formatos, las variaciones locales incluyen la elección del separador (por ejemplo, `-`, `/` o `.`), si los años son de dos o cuatro dígitos, y si los meses o días de un solo dígito están completados con ceros a la izquierda.

Para manejar esta diversidad, el `MaskedDateField` utiliza indicadores de formato, cada uno comenzando con `%`, seguido por una letra que representa una parte específica de la fecha. Estos indicadores definen cómo se analiza la entrada y cómo se muestra la fecha.

### Indicadores de formato de fecha {#date-format-indicators}

| Formato | Descripción |
| ------- | ----------- |
| `%Y`   | Año        |
| `%M`   | Mes       |
| `%D`   | Día         |

### Modificadores {#modifiers}

Los modificadores permiten un mayor control sobre cómo se formatean los componentes de la fecha:

| Modificador | Descripción               |
| ----------- | ------------------------- |
| `z`         | Rellenar con ceros       |
| `s`         | Representación de texto corto |
| `l`         | Representación de texto larga  |
| `p`         | Número comprimido        |
| `d`         | Decimal (formato por defecto)  |

Estos se pueden combinar para construir una amplia variedad de máscaras de fecha.

## Localización del formato de fecha {#date-format-localization}

El `MaskedDateField` se adapta a los formatos de fecha regionales configurando la localidad apropiada. Esto asegura que las fechas se muestren y analicen de una manera que coincida con las expectativas del usuario.

| Región        | Formato     | Ejemplo      |
| ------------- | ---------- | ------------ |
| Estados Unidos | MM/DD/YYYY | `07/04/2023` |
| Europa        | DD/MM/YYYY | `04/07/2023` |
| Estándar ISO  | YYYY-MM-DD | `2023-07-04` |

Para aplicar la localización, utiliza el método `setLocale()`. Acepta un [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) y ajusta automáticamente tanto el formato como el análisis:

```java
dateField.setLocale(Locale.FRANCE);
```

## Lógica de análisis {#parsing-logic}

El `MaskedDateField` analiza la entrada del usuario basada en la máscara de fecha definida. Acepta tanto entradas numéricas completas como abreviadas con o sin delimitadores, permitiendo una entrada flexible mientras asegura fechas válidas.
El comportamiento de análisis depende del orden de formato definido por la máscara (por ejemplo, `%Mz/%Dz/%Yz` para mes/día/año). Este formato determina cómo se interpretan las secuencias numéricas.

Por ejemplo, suponiendo que hoy es `15 de septiembre de 2012`, así es como se interpretarían varias entradas:

### Escenarios de análisis de ejemplo {#example-parsing-scenarios}

| Entrada                                | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| -------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>         | Un solo dígito se interpreta siempre como un número de día dentro del mes actual, por lo que esto sería el 1 de septiembre de 2012.                                                                                 | Igual que YMD                                                                         | Igual que YMD                                                                                                                  |
| <div align="center">`12`</div>        | Dos dígitos se interpretan siempre como un número de día dentro del mes actual, por lo que esto sería el 12 de septiembre de 2012.                                                                                   | Igual que YMD                                                                         | Igual que YMD                                                                                                                  |
| <div align="center">`112`</div>       | Tres dígitos se interpretan como un número de mes de 1 dígito seguido de un número de día de 2 dígitos, por lo que esto sería el 12 de enero de 2012.                                                                        | Igual que YMD                                                                         | Tres dígitos se interpretan como un número de día de 1 dígito seguido de un número de mes de 2 dígitos, por lo que esto sería el 1 de diciembre de 2012. |
| <div align="center">`1004`</div>      | Cuatro dígitos se interpretan como MMDD, por lo que esto sería el 4 de octubre de 2012.                                                                                                                             | Igual que YMD                                                                         | Cuatro dígitos se interpretan como DDMM, por lo que esto sería el 10 de abril de 2012.                                                         |
| <div align="center">`020304`</div>    | Seis dígitos se interpretan como YYMMDD, por lo que esto sería el 4 de marzo de 2002.                                                                                                                              | Seis dígitos se interpretan como MMDDYY, por lo que esto sería el 3 de febrero de 2004.            | Seis dígitos se interpretan como DDMMYY, por lo que esto sería el 2 de marzo de 2004.                                                         |
| <div align="center">`8 digits`</div>  | Ocho dígitos se interpretan como YYYYMMDD. Por ejemplo, `20040612` es el 12 de junio de 2004.                                                                                                                | Ocho dígitos se interpretan como MMDDYYYY. Por ejemplo, `06122004` es el 12 de junio de 2004. | Ocho dígitos se interpretan como DDMMYYYY. Por ejemplo, `06122004` es el 6 de diciembre de 2004.                                        |
| <div align="center">`12/6`</div>      | Dos números separados por cualquier delimitador válido se interpretan como MM/DD, por lo que esto sería el 6 de diciembre de 2012. <br />Nota: Todos los caracteres excepto letras y dígitos se consideran delimitadores válidos. | Igual que YMD                                                                         | Dos números separados por cualquier delimitador se interpretan como DD/MM, por lo que esto sería el 12 de junio de 2012.                               |
| <div align="center">`3/4/5`</div>     | 5 de abril de 2012                                                                                                                                                                                      | 4 de marzo de 2005                                                                       | 3 de abril de 2005                                                                                                                 |


## Análisis de fechas textuales <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Por defecto, el `MaskedDateField` solo acepta entradas numéricas para fechas. Sin embargo, puedes habilitar el **análisis de fechas textuales** para permitir que los usuarios ingresen nombres de meses y días en su entrada. Esta función es particularmente útil para crear una entrada de fecha más natural.

Para habilitar el análisis textual, utiliza el método `setTextualDateParsing()`:

```java
dateField.setTextualDateParsing(true);
```

### Sustitución del nombre del mes {#month-name-substitution}

Cuando el análisis textual está habilitado, puedes usar modificadores especiales en tu máscara para aceptar nombres de meses en lugar de valores numéricos:

- **`%Ms`** - Acepta nombres de meses cortos (Ene, Feb, Mar, etc.)
- **`%Ml`** - Acepta nombres de meses longos (Enero, Febrero, Marzo, etc.)

Los nombres de los meses pueden aparecer en cualquier posición dentro de la máscara, y el campo aún aceptará entradas numéricas como respaldo.

#### Ejemplos

| Máscara | Entrada | Resultado |
| ------- | ------- | --------- |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | **Válido** - Se analiza como 1 de septiembre de 2025 |
| `%Ml/%Dz/%Yz` | `Septiembre/01/25` | **Válido** - Se analiza como 1 de septiembre de 2025 |
| `%Dz/%Ml/%Yz` | `01/Septiembre/25` | **Válido** - Se analiza como 1 de septiembre de 2025 |
| `%Mz/%Dz/%Yz` | `09/01/25` | **Válido** - La opción numérica de respaldo sigue funcionando |

:::info
Todos los 12 meses están soportados en ambas formas corta (Ene, Feb, Mar, Abr, May, Jun, Jul, Ago, Sep, Oct, Nov, Dic) y larga (Enero, Febrero, etc.).
:::
### Decoración del nombre del día {#day-name-decoration}

Los nombres de los días de la semana pueden incluirse en la entrada para una mejor legibilidad, pero son **decorativos solamente** y se eliminan durante el análisis. No afectan el valor real de la fecha.

- **`%Ds`** - Acepta nombres cortos de días (Lun, Mar, Mié, etc.)
- **`%Dl`** - Acepta nombres largos de días (Lunes, Martes, Miércoles, etc.)

:::warning Los Nombres de los Días Requieren un Día Numérico
Al usar nombres de días de la semana (`%Ds` o `%Dl`), tu máscara **también debe incluir** `%Dz` o `%Dd` para especificar el número real del día. Sin un componente numérico de día, la entrada será inválida.
:::

#### Ejemplos

| Máscara | Entrada | Resultado |
| ------- | ------- | --------- |
| `%Ds %Mz/%Dz/%Yz` | `Lun 09/01/25` | **Válido** - El nombre del día es decorativo |
| `%Dl %Mz/%Dz/%Yz` | `Lunes 09/01/25` | **Válido** - El nombre del día es decorativo |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Mar` | **Válido** - El nombre del día al final |
| `%Dl/%Mz/%Yz` | `Lunes/09/25` | **Inválido** - Falta `%Dz` |
| `%Mz/%Dl/%Yz` | `09/Lunes/25` | **Inválido** - Falta `%Dz` |

Todos los 7 días de la semana están soportados en ambas formas corta (Lun, Mar, Mié, Jue, Vie, Sáb, Dom) y larga (Lunes, Martes, etc.).

### Reglas de análisis adicionales {#additional-parsing-rules}

El análisis de fechas textuales incluye varias funciones útiles:

- **Sin distinción de mayúsculas:** Entradas como `LUNES 09/01/25`, `lunes 09/01/25` o `Lunes 09/01/25` funcionan de la misma manera.
- **Consciente del idioma:** Los nombres de meses y días deben coincidir con la localidad configurada del campo. Por ejemplo, con una localidad francesa, usa `septembre` y no `September`. Los nombres en inglés no serán reconocidos a menos que la localidad esté configurada en inglés.
  - Localidad francesa: `septembre/01/25` es reconocido como septiembre
  - Localidad alemana: `Montag 09/01/25` es reconocido con lunes como el nombre del día

## Estableciendo restricciones mínimas/máximas {#setting-minmax-constraints}

Puedes restringir el rango de fechas permitidas en un `MaskedDateField` utilizando los métodos `setMin()` y `setMax()`:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Ambos métodos aceptan valores del tipo [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). La entrada fuera del rango definido se considerará inválida.

## Restaurando el valor {#restoring-the-value}

El `MaskedDateField` incluye una función de restauración que reinicia el valor del campo a un estado predefinido u original. Esto es útil para revertir las entradas del usuario o restaurar a una fecha por defecto.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Maneras de restaurar el valor {#ways-to-restore-the-value}

- **Programáticamente**, llamando a `restoreValue()`
- **A través del teclado**, presionando <kbd>ESC</kbd> (esta es la tecla de restauración predeterminada a menos que se anule con un oyente de eventos)

Puedes establecer el valor a restaurar con `setRestoreValue()`, pasando una instancia de [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html).

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Patrones de validación {#validation-patterns}

Puedes aplicar reglas de validación del lado del cliente usando expresiones regulares con el método `setPattern()`:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Este patrón asegura que solo los valores que coincidan con el formato `MM/DD/YYYY` (dos dígitos, barra, dos dígitos, barra, cuatro dígitos) se consideren válidos.

:::tip Formato de Expresión Regular
El patrón debe seguir la sintaxis de RegExp de JavaScript como se documenta [aquí](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Notas sobre el Manejo de Entradas
El campo intenta analizar y formatear entradas de fecha numéricas basándose en la máscara actual. Sin embargo, los usuarios aún pueden ingresar manualmente valores que no coincidan con el formato esperado. Si la entrada es sintácticamente válida pero semánticamente incorrecta o no analizables (por ejemplo, `99/99/9999`), puede pasar las comprobaciones de patrones pero fallar en la validación lógica.
Siempre debes validar el valor de entrada en la lógica de tu aplicación, incluso si se establece un patrón de expresión regular, para asegurar que la fecha esté correctamente formateada y tenga sentido.
::::

## Selector de fecha {#date-picker}

El `MaskedDateField` incluye un selector de calendario incorporado que permite a los usuarios seleccionar una fecha visualmente, en lugar de escribirla. Esto mejora la usabilidad para usuarios menos técnicos o cuando se requiere entrada precisa.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Accediendo al selector {#accessing-the-picker}

Puedes acceder al selector de fecha utilizando `getPicker()`:

```java
DatePicker picker = dateField.getPicker();
```

### Mostrar/ocultar el ícono del selector {#showhide-the-picker-icon}

Usa `setIconVisible()` para mostrar u ocultar el ícono del calendario junto al campo:

```java
picker.setIconVisible(true); // muestra el ícono
```

### Comportamiento de apertura automática {#auto-open-behavior}

Puedes configurar el selector para que se abra automáticamente cuando el usuario interactúe con el campo (por ejemplo, haga clic, presione Enter o flechas):

```java
picker.setAutoOpen(true);
```

:::tip Forzar Selección a Través del Selector
Para asegurar que los usuarios solo puedan seleccionar una fecha usando el selector de calendario (y no ingresando manualmente una), combina las siguientes dos configuraciones:

```java
dateField.getPicker().setAutoOpen(true); // Abre el selector en la interacción del usuario
dateField.setAllowCustomValue(false);    // Deshabilita la entrada de texto manual
```

Esta configuración garantiza que toda la entrada de fechas provenga a través de la interfaz del selector, lo cual es útil cuando deseas un control estricto sobre el formato y eliminar problemas de análisis de entradas escritas.
:::

### Abrir manualmente el calendario {#manually-open-the-calendar}

Para abrir el calendario programáticamente:

```java
picker.open();
```

O usa el alias:

```java
picker.show(); // igual que open()
```

### Mostrar semanas en el calendario {#show-weeks-in-the-calendar}

El selector puede mostrar opcionalmente los números de semana en la vista del calendario:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

El `MaskedDateFieldSpinner` extiende [`MaskedDateField`](#basics) al agregar controles de rueda que permiten a los usuarios incrementar o decrementar la fecha utilizando las flechas o botones de la interfaz. Proporciona un estilo de interacción más guiado, especialmente útil en aplicaciones de estilo de escritorio.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Características clave {#key-features}

- **Incremento de Fecha Interactiva:**  
  Usa las flechas o botones de giro para incrementar o decrementar el valor de la fecha.

- **Unidad de Paso Personalizable:**  
  Elige qué parte de la fecha modificar usando `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Las opciones incluyen `DAY`, `WEEK`, `MONTH` y `YEAR`.

- **Fronteras Mínimas/Máximas:**  
  Hereda soporte para fechas mínimas y máximas permitidas usando `setMin()` y `setMax()`.

- **Salida Formateada:**  
  Completamente compatible con máscaras y configuraciones de localización de `MaskedDateField`.

### Ejemplo: Configuración de paso semanal {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Esto hace que cada paso de giro avance o retroceda la fecha en una semana.

## Estilización {#styling}

<TableBuilder name="MaskedDateField" />
