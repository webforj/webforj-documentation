---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: bba6de4e793a65cc887af236d206bb46
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

El `MaskedNumberField` es un campo de texto diseñado para formatear entradas numéricas de manera coherente, basado en una máscara definida. Es útil para formularios financieros, campos de precios o cualquier entrada donde la precisión y la legibilidad son importantes.

Este componente se puede instanciar con o sin parámetros. Soporta formateo de números, localización de caracteres decimales/de agrupación y restricciones de valor opcionales como mínimos o máximos. También permite establecer un valor inicial, una etiqueta, un marcador de posición y un oyente de eventos para reaccionar a los cambios de valor.

<!-- INTRO_END -->

El ejemplo a continuación muestra un **Calculador de Propinas** que utiliza `MaskedNumberField` para entrada numérica intuitiva. Un campo está configurado para aceptar un monto de factura formateado, mientras que el otro captura un porcentaje de propina en números enteros.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Reglas de máscara {#mask-rules}

El `MaskedNumberField` utiliza una cadena de máscara para controlar cómo se formatea y muestra la entrada numérica. Cada carácter en la máscara define un comportamiento de formateo específico, lo que permite un control preciso sobre cómo aparecen los números.

:::tip Aplicación de máscaras de manera programática
Para formatear números con la misma sintaxis de máscara fuera de un campo, por ejemplo, al renderizar datos en una [`Tabla`](/docs/components/table/overview), utiliza la clase utilitaria [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Caracteres de máscara {#mask-characters}

| Carácter | Descripción |
|----------|-------------|
| `0`      | Siempre reemplazado por un dígito (0–9). |
| `#`      | Suprime ceros a la izquierda. Reemplazado por el carácter de relleno a la izquierda del punto decimal. Para los dígitos finales, se reemplaza por un espacio o cero. De lo contrario, se reemplaza por un dígito. |
| `,`      | Usado como un separador de agrupación (por ejemplo, miles). Reemplazado por el carácter de relleno si no hay dígitos que lo precedan. De lo contrario, aparece como una coma. |
| `-`      | Muestra un signo menos (`-`) si el número es negativo. Reemplazado por el carácter de relleno si es positivo. |
| `+`      | Muestra `+` para números positivos o `-` para números negativos. |
| `$`      | Siempre resulta en un signo de dólar. |
| `(`      | Inserta un paréntesis izquierdo `(` para valores negativos. Reemplazado por el carácter de relleno si es positivo. |
| `)`      | Inserta un paréntesis derecho `)` para valores negativos. Reemplazado por el carácter de relleno si es positivo. |
| `CR`     | Muestra `CR` para números negativos. Muestra dos espacios si el número es positivo. |
| `DR`     | Muestra `CR` para números negativos. Muestra `DR` para números positivos. |
| `*`      | Inserta un asterisco `*`. |
| `.`      | Marca el punto decimal. Si no aparecen dígitos en la salida, se reemplaza por el carácter de relleno. Después del decimal, los caracteres de relleno se tratan como espacios. |
| `B`      | Siempre se convierte en un espacio. Cualquier otro carácter literal se muestra tal cual. |

Algunos de los caracteres anteriores pueden aparecer más de una vez en la máscara para formateo. Estos incluyen `-`, `+`, `$`, y `(`. Si alguno de estos caracteres está presente en la máscara, el primero encontrado se moverá a la última posición donde un `#` o `,` fue reemplazado por el carácter de relleno. Si no existe tal posición, el carácter duplicado se deja donde está.

:::info Sin redondeo automático
Una máscara dentro de un campo **NO** redondea. Por ejemplo, al colocar un valor como `12.34567` en un campo que está enmascarado con `###0.00`, obtendrás `12.34`.
:::

## Separadores de grupo y decimal {#group-and-decimal-separators}

El `MaskedNumberField` soporta la personalización de los caracteres de **agrupación** y **decimal**, lo que facilita la adaptación del formateo numérico a diferentes localidades o convenciones comerciales.

- El **separador de grupo** se utiliza para separar visualmente los miles (por ejemplo, `1,000,000`).
- El **separador decimal** indica la parte fraccionaria de un número (por ejemplo, `123.45`).

Esto es útil en aplicaciones internacionales donde diferentes regiones utilizan diferentes caracteres (por ejemplo, `.` vs `,`).

```java
field.setGroupCharacter(".");   // por ejemplo, 1.000.000
field.setDecimalCharacter(","); // por ejemplo, 123,45
```

:::tip Comportamiento predeterminado
Por defecto, `MaskedNumberField` aplica los separadores de grupo y decimal basados en la localidad actual de la aplicación. Puedes sobrescribirlos en cualquier momento utilizando los setters proporcionados.
:::

## Negable {#negateable}

El `MaskedNumberField` soporta una opción para controlar si se permiten números negativos.

Por defecto, se permiten valores negativos como `-123.45`. Para evitar esto, usa `setNegateable(false)` para restringir la entrada a solo valores positivos.

Esto es útil en escenarios comerciales donde valores como cantidades, totales o porcentajes deben ser siempre no negativos.

```java
field.setNegateable(false);
```

Cuando `negatable` se establece como `false`, el campo bloquea cualquier intento de ingresar un signo menos o de introducir valores negativos de otra manera.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Valores mínimo y máximo {#min-and-max-values}

El `MaskedNumberField` permite establecer límites numéricos utilizando `setMin()` y `setMax()`. Estas restricciones ayudan a garantizar que la entrada del usuario se mantenga dentro de un rango válido y esperado.

- **Valor Mínimo**
  Utiliza `setMin()` para definir el número más bajo aceptable:

  ```java
  field.setMin(10.0); // Valor mínimo: 10
  ```

  Si el usuario ingresa un número por debajo de este umbral, se considerará inválido.

- **Valor Máximo**
  Utiliza `setMax()` para definir el número más alto aceptable:

  ```java
  field.setMax(100.0); // Valor máximo: 100
  ```

  Los valores que superen este límite se marcarán como inválidos.

## Restauración del valor {#restoring-the-value}

El `MaskedNumberField` soporta una característica de restauración que restablece el valor del campo a un estado predefinido. Esto puede ser útil cuando los usuarios necesitan deshacer cambios, revertir ediciones accidentales o regresar a un valor predeterminado conocido.

Para habilitar este comportamiento, define el valor objetivo utilizando `setRestoreValue()`. Cuando sea necesario, el campo se puede restablecer programáticamente utilizando `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Formas de restaurar el valor {#ways-to-restore-the-value}

- **Programáticamente** utilizando `restoreValue()`
- **A través del teclado**, presionando <kbd>ESC</kbd> (esta es la tecla de restauración predeterminada a menos que se sobrescriba)

El valor de restauración debe establecerse explícitamente. Si no está definido, la característica no revertirá el campo.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

El `MaskedNumberFieldSpinner` extiende el `MaskedNumberField` al agregar controles de spinner que permiten a los usuarios aumentar o disminuir el valor usando botones de paso o teclas de flecha. Esto es ideal para entradas como cantidades, ajustes de precios, controles de calificación o cualquier escenario donde los usuarios realicen cambios incrementales.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Características clave {#key-features}

- **Incrementos de paso**
  Usa `setStep()` para definir cuánto debe cambiar el valor con cada giro:

  ```java
  spinner.setStep(5.0); // Cada giro suma o resta 5
  ```

- **Controles interactivos**
  Los usuarios pueden hacer clic en los botones del spinner o usar la entrada del teclado para ajustar el valor.

- **Todas las características de MaskedNumberField**
  Soporta completamente máscaras, formateo, caracteres de agrupación/decimal, restricciones mínimas/máximas y lógica de restauración.

## Estilo {#styling}

<TableBuilder name="MaskedNumberField" />
