---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: 626c7e147632731dfdc761116a8abdc9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

El `MaskedNumberField` es un campo de texto diseñado para la entrada numérica estructurada. Asegura que los números estén formateados de manera consistente en función de una máscara definida, lo que lo hace especialmente útil para formularios financieros, campos de precios o cualquier entrada donde la precisión y legibilidad son importantes.

Este componente admite el formateo numérico, la localización de caracteres decimales/grupales y restricciones opcionales en los valores, como mínimos o máximos.

## Basics {#basics}

El `MaskedNumberField` puede ser instanciado con o sin parámetros. Admite establecer un valor inicial, una etiqueta, un marcador de posición y un listener de eventos para reaccionar a los cambios de valor.

Esta demostración muestra un **Calculador de Propinas** que utiliza `MaskedNumberField` para una entrada numérica intuitiva. Un campo está configurado para aceptar un monto de factura formateado, mientras que el otro captura un porcentaje de propina en número entero. Ambos campos aplican máscaras numéricas para asegurar un formateo consistente y predecible.

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## Mask rules {#mask-rules}

El `MaskedNumberField` usa una cadena de máscara para controlar cómo se formatea y muestra la entrada numérica. 
Cada carácter en la máscara define un comportamiento de formateo específico, permitiendo un control preciso sobre cómo aparecen los números.

### Mask characters {#mask-characters}

| Character | Description |
|-----------|-------------|
| `0`       | Siempre reemplazado por un dígito (0–9). |
| `#`       | Suprime los ceros iniciales. Reemplazado por el carácter de llenado a la izquierda del punto decimal. Para los dígitos finales, reemplazado por un espacio o cero. De lo contrario, reemplazado por un dígito. |
| `,`       | Usado como separador de agrupación (por ejemplo, miles). Reemplazado por el carácter de llenado si no hay dígitos que lo precedan. De lo contrario, se muestra como una coma. |
| `-`       | Muestra un signo negativo (`-`) si el número es negativo. Reemplazado por el carácter de llenado si es positivo. |
| `+`       | Muestra `+` para números positivos o `-` para números negativos. |
| `$`       | Siempre resulta en un signo de dólar. |
| `(`       | Inserta un paréntesis izquierdo `(` para valores negativos. Reemplazado por el carácter de llenado si es positivo. |
| `)`       | Inserta un paréntesis derecho `)` para valores negativos. Reemplazado por el carácter de llenado si es positivo. |
| `CR`      | Muestra `CR` para números negativos. Muestra dos espacios si el número es positivo. |
| `DR`      | Muestra `CR` para números negativos. Muestra `DR` para números positivos. |
| `*`       | Inserta un asterisco `*`. |
| `.`       | Marca el punto decimal. Si no aparecen dígitos en la salida, se reemplaza por el carácter de llenado. Después del decimal, los caracteres de llenado se tratan como espacios. |
| `B`       | Siempre se convierte en un espacio. Cualquier otro carácter literal se muestra tal como está. |

Algunos de los caracteres anteriores pueden aparecer más de una vez en la máscara para formateo. Estos incluyen `-`, `+`, `$` y
`(`. Si alguno de estos caracteres está presente en la máscara, el primero encontrado se moverá
a la última posición donde un `#` o `,` fue reemplazado por el carácter de llenado. Si no existe tal posición,
el carácter doble queda donde está.

:::info No Automatic Rounding
Una máscara dentro de un campo **NO** redondea. Por ejemplo, al colocar un valor como `12.34567`
en un campo que está enmascarado con `###0.00`, obtendrás `12.34`.
:::

## Group and decimal separators {#group-and-decimal-separators}

El `MaskedNumberField` admite la personalización de caracteres de **agrupación** y **decimal**, facilitando la adaptación del formateo de números a diferentes convenciones locales o comerciales.

- El **separador de grupo** se utiliza para separar visualmente los miles (por ejemplo, `1,000,000`).
- El **separador decimal** indica la parte fraccionaria de un número (por ejemplo, `123.45`).

Esto es útil en aplicaciones internacionales donde diferentes regiones utilizan diferentes caracteres (por ejemplo, `.` frente a `,`).

```java
field.setGroupCharacter(".");   // e.g. 1.000.000
field.setDecimalCharacter(","); // e.g. 123,45
```

:::tip Comportamiento Predeterminado
Por defecto, `MaskedNumberField` aplica separadores de grupo y decimal basados en la configuración regional actual de la aplicación. Puedes sobreescribirlos en cualquier momento utilizando los setters proporcionados.
:::

## Negateable {#negateable}

El `MaskedNumberField` admite una opción para controlar si se permiten números negativos.

Por defecto, se permiten valores negativos como `-123.45`. Para evitar esto, usa `setNegateable(false)` para restringir la entrada a valores positivos solamente.

Esto es útil en escenarios comerciales donde valores como cantidades, totales o porcentajes deben ser siempre no negativos.

```java
field.setNegateable(false);
```

Cuando `negatable` se establece en `false`, el campo bloquea cualquier intento de ingresar un signo negativo o ingresar valores negativos de otra manera.

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## Min and max values {#min-and-max-values}

El `MaskedNumberField` admite la configuración de límites numéricos utilizando `setMin()` y `setMax()`. 
Estas restricciones ayudan a asegurar que la entrada del usuario se mantenga dentro de un rango válido y esperado.

- **Valor Mínimo**  
  Usa `setMin()` para definir el número aceptable más bajo:

  ```java
  field.setMin(10.0); // Valor mínimo: 10
  ```

  Si el usuario introduce un número por debajo de este umbral, se considerará inválido.

- **Valor Máximo**  
  Usa `setMax()` para definir el número aceptable más alto:

  ```java
  field.setMax(100.0); // Valor máximo: 100
  ```

  Los valores por encima de este límite serán marcados como inválidos.

## Restoring the value {#restoring-the-value}

El `MaskedNumberField` admite una función de restauración que restablece el valor del campo a un estado predefinido. 
Esto puede ser útil cuando los usuarios necesitan deshacer cambios, revertir ediciones accidentales o volver a un valor predeterminado conocido.

Para habilitar este comportamiento, define el valor objetivo utilizando `setRestoreValue()`. 
Cuando sea necesario, el campo puede ser restablecido programáticamente utilizando `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Ways to restore the value {#ways-to-restore-the-value}

- **Programáticamente** usando `restoreValue()`
- **A través del teclado**, presionando <kbd>ESC</kbd> (esta es la tecla de restauración predeterminada a menos que se anule)

El valor de restauración debe ser establecido explícitamente. Si no se define, la función no revertirá el campo.

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

El `MaskedNumberFieldSpinner` extiende [`MaskedNumberField`](#basics) agregando controles de spinner que permiten a los usuarios aumentar o disminuir el valor utilizando botones de paso o teclas de flecha. 
Esto es ideal para entradas como cantidades, ajustes de precios, controles de calificación o cualquier escenario en el que los usuarios realicen cambios incrementales.

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### Key features {#key-features}

- **Incrementos de Paso**  
  Usa `setStep()` para definir cuánto debe cambiar el valor con cada giro:

  ```java
  spinner.setStep(5.0); // Cada giro suma o resta 5
  ```

- **Controles Interactivos**  
  Los usuarios pueden hacer clic en los botones del spinner o usar la entrada del teclado para ajustar el valor.

- **Todas las Características de MaskedNumberField**  
  Admite completamente máscaras, formateo, caracteres de agrupación/decimal, restricciones de min/max y lógica de restauración.

## Styling {#styling}

<TableBuilder name="MaskedNumberField" />
