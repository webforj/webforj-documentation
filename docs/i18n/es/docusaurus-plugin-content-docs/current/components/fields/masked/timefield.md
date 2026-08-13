---
title: MaskedTimeField
sidebar_position: 20
description: >-
  Capture time input with the MaskedTimeField, applying 12 or 24-hour masks,
  format indicators, locale-aware parsing, and validation.
_i18n_hash: 2631f01d383c134ba92d8ad03f5a57d3
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

El `MaskedTimeField` es un campo de texto que permite a los usuarios ingresar horas como **números** y formatea automáticamente la entrada según una máscara definida cuando el campo pierde el foco. La máscara especifica el formato de hora esperado, guiando tanto la entrada como la visualización. El componente admite análisis flexible, validación, localización y restauración de valores para un manejo consistente del tiempo.

<!-- INTRO_END -->

## Básicos {#basics}

:::tip ¿Buscando entrada de fecha?
El `MaskedTimeField` está diseñado para entrada de **solo tiempo**. Si buscas un componente para manejar **fechas** con un formato similar basado en máscaras, echa un vistazo al [`MaskedDateField`](./datefield.md).
:::

El `MaskedTimeField` puede ser instanciado con o sin parámetros. Puedes definir un valor inicial, una etiqueta, un marcador de posición y un oyente de eventos para cambios de valor.

<ComponentDemo
path='/webforj/maskedtimefield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java']}
height='120px'
/>

## Reglas de máscara {#mask-rules}

El `MaskedTimeField` utiliza indicadores de formato para definir cómo se analiza y muestra el tiempo. Cada indicador de formato comienza con un `%` seguido de una letra que representa un componente de tiempo.

:::tip Aplicar máscaras programáticamente
Para formatear o analizar horas con la misma sintaxis de máscara fuera de un campo, utiliza la clase de utilidad [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

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
| `d`         | Decimal (formato predeterminado)  |

Estos permiten un formateo de tiempo flexible y amigable con la localidad.

## Localización del formato de tiempo {#time-format-localization}

El `MaskedTimeField` admite localización configurando la localidad adecuada. Esto garantiza que la entrada y salida de tiempo coincidan con las convenciones regionales.

```java
field.setLocale(Locale.GERMANY);
```

Esto afecta cómo se muestran los indicadores AM/PM, cómo se manejan los separadores y cómo se analizan los valores.

## Lógica de análisis {#parsing-logic}

El `MaskedTimeField` analiza la entrada del usuario según la máscara de tiempo definida. Acepta entradas numéricas completas y abreviadas con o sin delimitadores, permitiendo una entrada flexible mientras asegura horas válidas. El comportamiento de análisis depende del orden de formato definido por la máscara (por ejemplo, `%Hz:%mz` para hora/minuto). Este formato determina cómo se interpretan las secuencias numéricas.

### Ejemplos de escenarios de análisis {#example-parsing-scenarios}

| Entrada | Máscara        | Interpretado Como |
|---------|----------------|--------------------|
| `900`   | `%Hz:%mz`      | `09:00`            |
| `1345`  | `%Hz:%mz`      | `13:45`            |
| `0230`  | `%hz:%mz %p`   | `02:30 AM`         |
| `1830`  | `%hz:%mz %p`   | `06:30 PM`         |

## Estableciendo restricciones mínimas/máximas {#setting-minmax-constraints}

Puedes restringir el rango de tiempo permitido en un `MaskedTimeField` usando los métodos `setMin()` y `setMax()`:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Ambos métodos aceptan valores del tipo [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Las entradas fuera del rango definido se consideran inválidas.

## Restaurando el valor {#restoring-the-value}

El `MaskedTimeField` incluye una función de restauración que restablece el valor del campo a un estado predefinido u original. Esto puede ser útil para deshacer cambios o regresar a una hora predeterminada.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Formas de restaurar el valor {#ways-to-restore-the-value}

- **Programáticamente**, llamando a `restoreValue()`
- **A través del teclado**, presionando <kbd>ESC</kbd> (esta es la tecla de restauración predeterminada a menos que sea reemplazada por un oyente de eventos)

<ComponentDemo
path='/webforj/maskedtimefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java']}
height='120px'
/>

## Patrones de validación {#validation-patterns}

Puedes aplicar reglas de validación del lado del cliente usando expresiones regulares con el método `setPattern()`:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Este patrón asegura que solo los valores que coincidan con el formato `HH:mm` (dos dígitos, dos puntos, dos dígitos) se consideren válidos.

:::tip Formato de expresión regular
El patrón debe seguir la sintaxis de RegExp de JavaScript tal como se documenta [aquí](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Notas sobre el manejo de entrada
El campo intenta analizar y formatear entradas de tiempo numéricas según la máscara actual. Sin embargo, los usuarios aún pueden ingresar manualmente valores que no coincidan con el formato esperado. Si la entrada es sintácticamente válida pero semánticamente incorrecta o no se puede analizar (por ejemplo, `99:99`), puede pasar las comprobaciones de patrón pero fallar en la validación lógica.
Siempre debes validar el valor de entrada en la lógica de tu aplicación, incluso si se establece un patrón de expresión regular, para garantizar que el tiempo esté tanto correctamente formateado como tenga sentido.
:::

## Selector de tiempo {#time-picker}

El `MaskedTimeField` incluye un selector de tiempo incorporado que permite a los usuarios seleccionar una hora visualmente, en lugar de escribirla. Esto mejora la usabilidad para los usuarios menos técnicos o cuando se requiere una entrada precisa.

<ComponentDemo
path='/webforj/maskedtimefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java']}
height='450px'
/>

### Accediendo al selector {#accessing-the-picker}

Puedes acceder al selector de tiempo usando `getPicker()`:

```java
TimePicker picker = field.getPicker();
```

### Mostrar/ocultar el ícono del selector {#showhide-the-picker-icon}

Usa `setIconVisible()` para mostrar u ocultar el ícono de reloj junto al campo:

```java
picker.setIconVisible(true); // muestra el icono
```

### Comportamiento de apertura automática {#auto-open-behavior}

Puedes configurar el selector para que se abra automáticamente cuando el usuario interactúa con el campo (por ejemplo, hace clic, presiona Enter o las teclas de flecha):

```java
picker.setAutoOpen(true);
```

:::tip Hacer cumplir la selección a través del selector
Para garantizar que los usuarios solo puedan seleccionar una hora utilizando el selector (y no escribirla manualmente), combina las siguientes dos configuraciones:

```java
field.getPicker().setAutoOpen(true); // Abre el selector en la interacción del usuario
field.setAllowCustomValue(false);    // Desactiva la entrada de texto manual
```

Esta configuración garantiza que toda entrada de tiempo provenga de la interfaz del selector, lo que es útil cuando deseas un control estricto del formato y eliminar problemas de análisis de las entradas escritas.
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

### Configuración del paso del selector {#setting-the-picker-step}

Puedes definir el intervalo entre los tiempos seleccionables en el selector usando `setStep()`. Esto te permite controlar cuán granular son las opciones de tiempo, ideal para escenarios como la programación en bloques de 15 minutos.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Restricción de paso
El paso debe dividir uniformemente una hora o un día completo. De lo contrario, se lanzará una excepción.
:::

Esto garantiza que la lista desplegable contenga valores predecibles y espaciados uniformemente como `09:00`, `09:15`, `09:30`, etc.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

El `MaskedTimeFieldSpinner` extiende [`MaskedTimeField`](#basics) al agregar controles de spinner que permiten a los usuarios incrementar o decrementar la hora utilizando las teclas de flecha o botones de la interfaz. Proporciona un estilo de interacción más guiado, especialmente útil en aplicaciones de estilo de escritorio.

<ComponentDemo
path='/webforj/maskedtimefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java']}
height='450px'
/>

### Características clave {#key-features}

- **Incremento de tiempo interactivo:**
  Usa teclas de flecha o botones de giro para incrementar o decrementar el valor del tiempo.

- **Unidad de giro personalizable:**
  Elige qué parte del tiempo modificar utilizando `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Las opciones incluyen `HOUR`, `MINUTE`, `SECOND`, y `MILLISECOND`.

- **Límites de mín./máx.:**
  Hereda soporte para tiempos permitidos mínimamente y máximamente usando `setMin()` y `setMax()`.

- **Salida formateada:**
  Totalmente compatible con máscaras y configuraciones de localización de `MaskedTimeField`.

### Ejemplo: Configurar incremento por hora {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Estilos {#styling}

<TableBuilder name="MaskedTimeField" />
