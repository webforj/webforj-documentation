---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: e50a52f19876f98eec1bd825ca82cd6a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

El `MaskedTimeField` es un control de entrada de texto diseñado para la entrada de tiempo precisa y estructurada. Permite a los usuarios introducir tiempos como **números** y formatea automáticamente la entrada según una máscara definida cuando el campo pierde el foco. La máscara es una cadena que especifica el formato de tiempo esperado, guiando tanto la entrada como la visualización.

Este componente admite análisis flexible, validación, localización y restauración de valores. Es especialmente útil en formularios sensibles al tiempo como horarios, hojas de tiempo y reservas.

:::tip ¿Buscas una entrada de fecha?
El `MaskedTimeField` está diseñado para la entrada de **solo tiempo**. Si buscas un componente para manejar **fechas** con un formato basado en máscaras similar, echa un vistazo al [`MaskedDateField`](./datefield.md).
:::

## Basics {#basics}

El `MaskedTimeField` puede ser instanciado con o sin parámetros. Puedes definir un valor inicial, una etiqueta, un marcador de posición y un oyente de eventos para cambios de valor.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Regulas de máscara {#mask-rules}

El `MaskedTimeField` utiliza indicadores de formato para definir cómo se analiza y se muestra el tiempo. Cada indicador de formato comienza con un `%` seguido de una letra que representa un componente de tiempo.

### Indicadores de formato de tiempo {#time-format-indicators}

| Formato | Descripción         |
|---------|---------------------|
| `%H`    | Hora (24 horas)     |
| `%h`    | Hora (12 horas)     |
| `%m`    | Minuto              |
| `%s`    | Segundo             |
| `%p`    | AM/PM               |

### Modificadores {#modifiers}

Los modificadores refinan la visualización de los componentes de tiempo:

| Modificador | Descripción               |
|-------------|---------------------------|
| `z`         | Rellenar con ceros        |
| `s`         | Representación de texto corta |
| `l`         | Representación de texto larga  |
| `p`         | Número empaquetado        |
| `d`         | Decimal (formato predeterminado) |

Estos permiten una flexibilidad y formato de tiempo adecuado a la localidad.

## Localización del formato de tiempo {#time-format-localization}

El `MaskedTimeField` admite la localización estableciendo la localidad apropiada. Esto garantiza que la entrada y salida de tiempo coincidan con las convenciones regionales.

```java
field.setLocale(Locale.GERMANY);
```

Esto afecta cómo se muestran los indicadores AM/PM, cómo se manejan los separadores y cómo se analizan los valores.

## Lógica de análisis {#parsing-logic}

El `MaskedTimeField` analiza la entrada del usuario según la máscara de tiempo definida. Acepta tanto entradas numéricas completas como abreviadas con o sin delimitadores, lo que permite una entrada flexible mientras asegura tiempos válidos. El comportamiento de análisis depende del orden de formato definido por la máscara (por ejemplo, `%Hz:%mz` para hora/minuto). Este formato determina cómo se interpretan las secuencias numéricas.

### Ejemplos de escenarios de análisis {#example-parsing-scenarios}

| Entrada | Máscara       | Interpretado Como |
|---------|---------------|--------------------|
| `900`   | `%Hz:%mz`     | `09:00`            |
| `1345`  | `%Hz:%mz`     | `13:45`            |
| `0230`  | `%hz:%mz %p`  | `02:30 AM`         |
| `1830`  | `%hz:%mz %p`  | `06:30 PM`         |

## Estableciendo restricciones de min/max {#setting-minmax-constraints}

Puedes restringir el rango de tiempo permitido en un `MaskedTimeField` utilizando los métodos `setMin()` y `setMax()`:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Ambos métodos aceptan valores del tipo [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Las entradas fuera del rango definido se consideran inválidas.

## Restaurando el valor {#restoring-the-value}

El `MaskedTimeField` incluye una función de restauración que restablece el valor del campo a un estado predefinido u original. Esto puede ser útil para deshacer cambios o volver a un tiempo predeterminado.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Maneras de restaurar el valor {#ways-to-restore-the-value}

- **Programáticamente**, llamando a `restoreValue()`
- **Mediante teclado**, presionando <kbd>ESC</kbd> (esta es la tecla de restauración predeterminada a menos que sea anulada por un oyente de eventos)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Patrones de validación {#validation-patterns}

Puedes aplicar reglas de validación del lado del cliente usando expresiones regulares con el método `setPattern()`:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Este patrón asegura que solo se consideren válidos los valores que coincidan con el formato `HH:mm` (dos dígitos, dos puntos, dos dígitos).

:::tip Formato de expresión regular
El patrón debe seguir la sintaxis de RegExp de JavaScript como se documenta [aquí](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Notas sobre el manejo de entrada
El campo intenta analizar y formatear las entradas numéricas de tiempo basándose en la máscara actual. Sin embargo, los usuarios aún pueden introducir manualmente valores que no coincidan con el formato esperado. Si la entrada es sintácticamente válida pero semánticamente incorrecta o inanalizable (por ejemplo, `99:99`), puede pasar las comprobaciones de patrón pero fallar la validación lógica.
Siempre debes validar el valor de entrada en la lógica de tu aplicación, incluso si se establece un patrón de expresión regular, para garantizar que el tiempo esté correctamente formateado y sea significativo.
:::

## Selector de tiempo {#time-picker}

El `MaskedTimeField` incluye un selector de tiempo integrado que permite a los usuarios seleccionar una hora visualmente, en lugar de escribirla. Esto mejora la usabilidad para usuarios menos técnicos o cuando se requiere una entrada precisa.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Accediendo al selector {#accessing-the-picker}

Puedes acceder al selector de tiempo utilizando `getPicker()`:

```java
TimePicker picker = field.getPicker();
```

### Mostrar/ocultar el icono del selector {#showhide-the-picker-icon}

Utiliza `setIconVisible()` para mostrar u ocultar el icono del reloj junto al campo:

```java
picker.setIconVisible(true); // muestra el icono
```

### Comportamiento de apertura automática {#auto-open-behavior}

Puedes configurar el selector para que se abra automáticamente cuando el usuario interactúa con el campo (por ejemplo, hace clic, presiona Enter o las teclas de flecha):

```java
picker.setAutoOpen(true);
```

:::tip Impedir selección a través del selector
Para asegurarte de que los usuarios solo puedan seleccionar una hora utilizando el selector (y no teclear una manualmente), combina las siguientes dos configuraciones:

```java
field.getPicker().setAutoOpen(true); // Abre el selector en la interacción del usuario
field.setAllowCustomValue(false);    // Desactiva la entrada de texto manual
```

Esta configuración garantiza que toda la entrada de tiempo provenga a través de la interfaz del selector, lo cual es útil cuando quieres un control estricto del formato y eliminar problemas de análisis de la entrada tecleada.
:::

### Abrir manualmente el selector {#manually-open-the-picker}

Para abrir el selector de tiempo programáticamente:

```java
picker.open();
```

O usa el alias:

```java
picker.show(); // igual que open()
```

### Estableciendo el paso del selector {#setting-the-picker-step}

Puedes definir el intervalo entre los tiempos seleccionables en el selector utilizando `setStep()`. Esto te permite controlar cuán granular son las opciones de tiempo—ideal para escenarios como programar en bloques de 15 minutos.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Restricción de paso
El paso debe dividir uniformemente una hora o un día completo. De lo contrario, se lanzará una excepción.
:::

Esto garantiza que la lista desplegable contenga valores predecibles y espaciados uniformemente como `09:00`, `09:15`, `09:30`, etc.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

El `MaskedTimeFieldSpinner` amplía el [`MaskedTimeField`](#basics) al agregar controles de spinner que permiten a los usuarios incrementar o decrementar el tiempo utilizando teclas de flecha o botones de la interfaz. Proporciona un estilo de interacción más guiado, especialmente útil en aplicaciones de escritorio.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Características clave {#key-features}

- **Incremento interactivo de tiempo:**  
  Usa teclas de flecha o botones giratorios para incrementar o decrementar el valor de tiempo.

- **Unidad de giro personalizable:**  
  Elige qué parte del tiempo modificar utilizando `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Las opciones incluyen `HOUR`, `MINUTE`, `SECOND` y `MILLISECOND`.

- **Límites mínimo/máximo:**  
  Hereda soporte para los tiempos mínimos y máximos permitidos utilizando `setMin()` y `setMax()`.

- **Salida formateada:**  
  Totalmente compatible con máscaras y configuraciones de localización del `MaskedTimeField`.

### Ejemplo: Configurar el incremento por hora {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Estilizando {#styling}

<TableBuilder name="MaskedTimeField" />
