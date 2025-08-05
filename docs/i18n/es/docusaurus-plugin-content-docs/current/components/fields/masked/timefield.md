---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: 3d719856c08ce148bcd2999878d8c161
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

El `MaskedTimeField` es un control de entrada de texto diseñado para la entrada de tiempo precisa y estructurada. Permite a los usuarios ingresar horas como **números** y formatea automáticamente la entrada según una máscara definida cuando el campo pierde el foco. La máscara es una cadena que especifica el formato de tiempo esperado, guiando tanto la entrada como la visualización.

Este componente admite análisis flexible, validación, localización, y restauración de valores. Es especialmente útil en formularios sensibles al tiempo, como horarios, hojas de tiempo y reservas.

:::tip ¿Buscas una entrada de fecha?
El `MaskedTimeField` está construido para entrada de **sólo tiempo**. Si buscas un componente para manejar **fechas** con un formato similar basado en máscara, echa un vistazo al [`MaskedDateField`](./datefield.md).
:::

## Básicos {#basics}

El `MaskedTimeField` puede ser instanciado con o sin parámetros. Puedes definir un valor inicial, una etiqueta, un marcador de posición, y un oyente de eventos para los cambios de valor.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Reglas de máscara {#mask-rules}

El `MaskedTimeField` utiliza indicadores de formato para definir cómo se analiza y muestra el tiempo. Cada indicador de formato comienza con un `%` seguido de una letra que representa un componente del tiempo.

### Indicadores de formato de tiempo {#time-format-indicators}

| Formato | Descripción          |
|---------|----------------------|
| `%H`    | Hora (24 horas)      |
| `%h`    | Hora (12 horas)      |
| `%m`    | Minuto               |
| `%s`    | Segundo              |
| `%p`    | AM/PM                |

### Modificadores {#modifiers}

Los modificadores refinan la visualización de los componentes de tiempo:

| Modificador | Descripción                  |
|-------------|------------------------------|
| `z`         | Relleno con ceros            |
| `s`         | Representación de texto corto |
| `l`         | Representación de texto largo  |
| `p`         | Número empaquetado           |
| `d`         | Decimal (formato predeterminado)  |

Estos permiten un formateo de tiempo flexible y amigable con la localidad.

## Localización del formato de tiempo {#time-format-localization}

El `MaskedTimeField` admite la localización configurando la localidad apropiada. Esto asegura que la entrada y salida de tiempo coincidan con las convenciones regionales.

```java
field.setLocale(Locale.GERMANY);
```

Esto afecta cómo se muestran los indicadores AM/PM, cómo se manejan los separadores, y cómo se analizan los valores.

## Lógica de análisis {#parsing-logic}

El `MaskedTimeField` analiza la entrada del usuario basándose en la máscara de tiempo definida. Acepta entradas numéricas completas y abreviadas con o sin delimitadores, permitiendo una entrada flexible mientras asegura tiempos válidos. El comportamiento de análisis depende del orden del formato definido por la máscara (por ejemplo, `%Hz:%mz` para hora/minuto). Este formato determina cómo se interpretan las secuencias numéricas.

### Ejemplos de escenarios de análisis {#example-parsing-scenarios}

| Entrada | Máscara       | Interpretado Como |
|---------|---------------|-------------------|
| `900`   | `%Hz:%mz`     | `09:00`           |
| `1345`  | `%Hz:%mz`     | `13:45`           |
| `0230`  | `%hz:%mz %p`  | `02:30 AM`        |
| `1830`  | `%hz:%mz %p`  | `06:30 PM`        |

## Establecer restricciones min/max {#setting-minmax-constraints}

Puedes restringir el rango de tiempo permitido en un `MaskedTimeField` utilizando los métodos `setMin()` y `setMax()`:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Ambos métodos aceptan valores de tipo [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Las entradas fuera del rango definido se consideran inválidas.

## Restaurando el valor {#restoring-the-value}

El `MaskedTimeField` incluye una función de restauración que restablece el valor del campo a un estado predefinido u original. Esto puede ser útil para deshacer cambios o volver a un tiempo predeterminado.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Formas de restaurar el valor {#ways-to-restore-the-value}

- **Programáticamente**, llamando a `restoreValue()`
- **A través del teclado**, presionando <kbd>ESC</kbd> (esta es la clave de restauración predeterminada a menos que se anule mediante un oyente de eventos)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Patrones de validación {#validation-patterns}

Puedes aplicar reglas de validación del lado del cliente utilizando expresiones regulares con el método `setPattern()`:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Este patrón asegura que solo se consideren válidos los valores que coincidan con el formato `HH:mm` (dos dígitos, dos puntos, dos dígitos).

:::tip Formato de Expresiones Regulares
El patrón debe seguir la sintaxis de RegExp de JavaScript según se documenta [aquí](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Notas sobre el Manejo de Entradas
El campo intenta analizar y formatear entradas de tiempo numéricas basadas en la máscara actual. Sin embargo, los usuarios pueden seguir ingresando manualmente valores que no coincidan con el formato esperado. Si la entrada es sintácticamente válida pero semánticamente incorrecta o no se puede analizar (por ejemplo, `99:99`), podría pasar las verificaciones de patrón pero fallar en la validación lógica.
Siempre debes validar el valor de entrada en la lógica de tu aplicación, incluso si se establece un patrón de expresión regular, para asegurarte de que el tiempo esté correctamente formateado y tenga sentido.
:::

## Selector de tiempo {#time-picker}

El `MaskedTimeField` incluye un selector de tiempo incorporado que permite a los usuarios seleccionar un tiempo de manera visual, en lugar de escribirlo. Esto mejora la usabilidad para usuarios menos técnicos o cuando se requiere una entrada precisa.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Accediendo al selector {#accessing-the-picker}

Puedes acceder al selector de tiempo usando `getPicker()`:

```java
TimePicker picker = field.getPicker();
```

### Mostrar/ocultar el ícono del selector {#showhide-the-picker-icon}

Usa `setIconVisible()` para mostrar u ocultar el ícono del reloj junto al campo:

```java
picker.setIconVisible(true); // muestra el icono
```

### Comportamiento de apertura automática {#auto-open-behavior}

Puedes configurar el selector para que se abra automáticamente cuando el usuario interactúa con el campo (por ejemplo, haciendo clic, presionando Enter o teclas de flecha):

```java
picker.setAutoOpen(true);
```

:::tip Forzar Selección a Través del Selector
Para asegurar que los usuarios solo puedan seleccionar un tiempo usando el selector (y no escribir uno manualmente), combina las siguientes dos configuraciones:

```java
field.getPicker().setAutoOpen(true); // Abre el selector en la interacción del usuario
field.setAllowCustomValue(false);    // Desactiva la entrada de texto manual
```

Esta configuración garantiza que toda la entrada de tiempo provenga a través de la interfaz del selector, lo que es útil cuando deseas un control estricto del formato y eliminar problemas de análisis de entradas escritas.
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

### Establecer el paso del selector {#setting-the-picker-step}

Puedes definir el intervalo entre los tiempos seleccionables en el selector usando `setStep()`. Esto te permite controlar cuán granular son las opciones de tiempo—ideal para escenarios como la programación en bloques de 15 minutos.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Restricción de Paso
El paso debe dividir uniformemente una hora o un día completo. De lo contrario, se lanzará una excepción.
:::

Esto asegura que la lista desplegable contenga valores predecibles y uniformemente espaciados como `09:00`, `09:15`, `09:30`, etc.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

El `MaskedTimeFieldSpinner` extiende [`MaskedTimeField`](#basics) añadiendo controles de spinner que permiten a los usuarios incrementar o decrementar el tiempo usando teclas de flecha o botones de la interfaz. Proporciona un estilo de interacción más guiado, especialmente útil en aplicaciones de escritorio.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Características clave {#key-features}

- **Incremento Interactivo de Tiempo:**  
  Usa teclas de flecha o botones de giro para incrementar o decrementar el valor de tiempo.

- **Unidad de Giro Personalizable:**  
  Elige qué parte del tiempo modificar usando `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Las opciones incluyen `HOUR`, `MINUTE`, `SECOND`, y `MILLISECOND`.

- **Límites Min/Max:**  
  Hereda el soporte para tiempos mínimos y máximos permitidos usando `setMin()` y `setMax()`.

- **Salida Formateada:**  
  Totalmente compatible con las máscaras y configuraciones de localización de `MaskedTimeField`.

### Ejemplo: Configurar el paso por hora {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Estilización {#styling}

<TableBuilder name="MaskedTimeField" />
