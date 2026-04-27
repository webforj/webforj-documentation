---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: bfaa13bee2b1c6dd1c88c8af989a6532
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

El `MaskedTimeField` es un campo de texto que permite a los usuarios ingresar horas como **números** y formatea automáticamente la entrada según una máscara definida cuando el campo pierde el foco. La máscara especifica el formato de hora esperado, guiando tanto la entrada como la visualización. El componente admite un análisis flexible, validación, localización y restauración de valores para un manejo consistente del tiempo.

<!-- INTRO_END -->

## Basics {#basics}

:::tip ¿Buscas entrada de fecha?
El `MaskedTimeField` está diseñado para entradas de **solo tiempo**. Si buscas un componente para manejar **fechas** con un formato basado en máscaras similar, echa un vistazo al [`MaskedDateField`](./datefield.md).
:::

El `MaskedTimeField` se puede instanciar con o sin parámetros. Puedes definir un valor inicial, una etiqueta, un marcador de posición y un oyente de eventos para cambios de valor.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Reglas de máscara {#mask-rules}

El `MaskedTimeField` utiliza indicadores de formato para definir cómo se analiza y muestra la hora. Cada indicador de formato comienza con un `%` seguido de una letra que representa un componente de tiempo.

:::tip Aplicando máscaras programáticamente
Para formatear o analizar horas con la misma sintaxis de máscara fuera de un campo, utiliza la clase de utilidad [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Indicadores de formato de hora {#time-format-indicators}

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
| `s`         | Representación de texto corta  |
| `l`         | Representación de texto largo   |
| `p`         | Número empaquetado        |
| `d`         | Decimal (formato por defecto)  |

Estos permiten un formato de tiempo flexible y amigable con la localidad.

## Localización del formato de hora {#time-format-localization}

El `MaskedTimeField` admite la localización estableciendo la localidad apropiada. Esto asegura que la entrada y salida de tiempo coincidan con las convenciones regionales.

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

## Estableciendo restricciones min/max {#setting-minmax-constraints}

Puedes restringir el rango de tiempo permitido en un `MaskedTimeField` usando los métodos `setMin()` y `setMax()`:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Ambos métodos aceptan valores del tipo [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Las entradas fuera del rango definido se consideran inválidas.

## Restaurando el valor {#restoring-the-value}

El `MaskedTimeField` incluye una función de restauración que restablece el valor del campo a un estado predefinido u original. Esto puede ser útil para deshacer cambios o volver a un tiempo por defecto.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Maneras de restaurar el valor {#ways-to-restore-the-value}

- **Programáticamente**, llamando a `restoreValue()`
- **A través del teclado**, presionando <kbd>ESC</kbd> (esta es la tecla de restauración por defecto a menos que se sobrescriba por un oyente de eventos)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Patrones de validación {#validation-patterns}

Puedes aplicar reglas de validación del lado del cliente usando expresiones regulares con el método `setPattern()`:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Este patrón asegura que solo los valores que coinciden con el formato `HH:mm` (dos dígitos, dos puntos, dos dígitos) se consideren válidos.

:::tip Formato de Expresión Regular
El patrón debe seguir la sintaxis de RegExp de JavaScript como se documenta [aquí](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Notas sobre el Manejo de Entrada
El campo intenta analizar y formatear las entradas de tiempo numéricas según la máscara actual. Sin embargo, los usuarios aún pueden ingresar manualmente valores que no coinciden con el formato esperado. Si la entrada es sintácticamente válida pero semánticamente incorrecta o inanalizable (por ejemplo, `99:99`), puede pasar las verificaciones de patrón pero fallar en la validación lógica. 
Siempre debes validar el valor de entrada en la lógica de tu aplicación, incluso si se establece un patrón de expresión regular, para asegurar que el tiempo esté correctamente formateado y tenga sentido.
:::

## Selector de tiempo {#time-picker}

El `MaskedTimeField` incluye un selector de tiempo incorporado que permite a los usuarios seleccionar un tiempo visualmente, en lugar de escribirlo. Esto mejora la usabilidad para usuarios menos técnicos o cuando se requiere una entrada precisa.

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
picker.setIconVisible(true); // muestra el ícono
```

### Comportamiento de auto-apertura {#auto-open-behavior}

Puedes configurar el selector para que se abra automáticamente cuando el usuario interactúa con el campo (por ejemplo, hace clic, presiona Enter o las teclas de flecha):

```java
picker.setAutoOpen(true);
```

:::tip Forzar Selección a través del Selector
Para asegurar que los usuarios solo puedan seleccionar un tiempo usando el selector (y no ingresarlo manualmente), combina las siguientes dos configuraciones:

```java
field.getPicker().setAutoOpen(true); // Abre el selector en la interacción del usuario
field.setAllowCustomValue(false);    // Desactiva la entrada de texto manual
```

Esta configuración garantiza que toda la entrada de tiempo provenga de la interfaz del selector, lo que es útil cuando deseas un control estrictamente de formato y eliminar problemas de análisis de entradas escritas.
:::

### Abrir el selector manualmente {#manually-open-the-picker}

Para abrir el selector de tiempo programáticamente:

```java
picker.open();
```

O utiliza el alias:

```java
picker.show(); // igual que open()
```

### Estableciendo el paso del selector {#setting-the-picker-step}

Puedes definir el intervalo entre los tiempos seleccionables en el selector utilizando `setStep()`. Esto te permite controlar cuán granulares son las opciones de tiempo, ideal para escenarios como programación en bloques de 15 minutos.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Restricción de Paso
El paso debe dividirse uniformemente en una hora o un día completo. De lo contrario, se lanzará una excepción.
:::

Esto asegura que la lista desplegable contenga valores predecibles y espaciados uniformemente como `09:00`, `09:15`, `09:30`, etc.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

El `MaskedTimeFieldSpinner` amplía el [`MaskedTimeField`](#basics) al agregar controles de spinner que permiten a los usuarios incrementar o decrementar el tiempo usando teclas de flecha o botones de la interfaz. Proporciona un estilo de interacción más guiado, especialmente útil en aplicaciones de estilo de escritorio.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Características clave {#key-features}

- **Incremento de Tiempo Interactivo:**  
  Usa las teclas de flecha o botones de giro para incrementar o decrementar el valor de tiempo.

- **Unidad de Giro Personalizable:**  
  Elige qué parte del tiempo modificar usando `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Las opciones incluyen `HOUR`, `MINUTE`, `SECOND`, y `MILLISECOND`.

- **Límites Min/Max:**  
  Hereda el soporte para tiempos mínimos y máximos permitidos usando `setMin()` y `setMax()`.

- **Salida Formateada:**  
  Totalmente compatible con máscaras y configuraciones de localización del `MaskedTimeField`.

### Ejemplo: Configurar el incremento por hora {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Estilo {#styling}

<TableBuilder name="MaskedTimeField" />
