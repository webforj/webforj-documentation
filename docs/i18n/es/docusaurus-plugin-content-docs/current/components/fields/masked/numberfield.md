---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: f247d28107239e0db4a24823c8a1ef60
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

El `MaskedNumberField` es un campo de texto diseñado para formatear entradas numéricas de manera consistente, basado en una máscara definida. Es útil para formularios financieros, campos de precios o cualquier entrada donde la precisión y legibilidad sean importantes.

Este componente se puede instanciar con o sin parámetros. Soporta formateo de números, localización de caracteres decimales/agrupadores y restricciones de valor opcionales, como mínimos o máximos. También permite establecer un valor inicial, una etiqueta, un marcador de posición y un listener de eventos para reaccionar a los cambios de valor.

<!-- INTRO_END -->

El siguiente ejemplo muestra un **Calculador de Propinas** que utiliza `MaskedNumberField` para una entrada numérica intuitiva. Un campo está configurado para aceptar un monto de factura formateado, mientras que el otro captura un porcentaje de propina en números enteros.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Reglas de máscara {#mask-rules}

El `MaskedNumberField` utiliza una cadena de máscara para controlar cómo se formatea y muestra la entrada numérica. Cada carácter en la máscara define un comportamiento de formateo específico, permitiendo un control preciso sobre cómo aparecen los números.

:::tip Aplicación de máscaras programáticamente
Para formatear números con la misma sintaxis de máscara fuera de un campo, por ejemplo, al renderizar datos en una [`Tabla`](/docs/components/table/overview), utiliza la clase utilitaria [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Caracteres de máscara {#mask-characters}

| Carácter  | Descripción |
|-----------|-------------|
| `0`       | Siempre es reemplazado por un dígito (0–9). |
| `#`       | Suprime ceros a la izquierda. Reemplazado por el carácter de relleno a la izquierda del punto decimal. Para dígitos finales, se reemplaza por un espacio o un cero. De lo contrario, se reemplaza por un dígito. |
| `,`       | Usado como separador de agrupación (e.g. miles). Reemplazado por el carácter de relleno si no hay dígitos delante. De lo contrario, se muestra como una coma. |
| `-`       | Muestra un signo menos (`-`) si el número es negativo. Reemplazado por el carácter de relleno si es positivo. |
| `+`       | Muestra `+` para números positivos o `-` para negativos. |
| `$`       | Siempre resulta en un signo de dólar. |
| `(`       | Inserta un paréntesis izquierdo `(` para valores negativos. Reemplazado por el carácter de relleno si es positivo. |
| `)`       | Inserta un paréntesis derecho `)` para valores negativos. Reemplazado por el carácter de relleno si es positivo. |
| `CR`      | Muestra `CR` para números negativos. Muestra dos espacios si el número es positivo. |
| `DR`      | Muestra `CR` para números negativos. Muestra `DR` para números positivos. |
| `*`       | Inserta un asterisco `*`. |
| `.`       | Marca el punto decimal. Si no aparecen dígitos en la salida, se reemplaza por el carácter de relleno. Después del decimal, los caracteres de relleno se tratan como espacios. |
| `B`       | Siempre se convierte en un espacio. Cualquier otro carácter literal se muestra tal cual. |

Algunos de los caracteres anteriores pueden aparecer más de una vez en la máscara para el formateo. Estos incluyen `-`, `+`, `$`, y `(`. Si alguno de estos caracteres está presente en la máscara, el primero que se encuentre se moverá a la última posición donde un `#` o `,` fue reemplazado por el carácter de relleno. Si no existe tal posición, el carácter se deja donde estaba.

:::info Sin Redondeo Automático
Una máscara dentro de un campo **NO** redondea. Por ejemplo, al colocar un valor como `12.34567` en un campo que está enmascarado con `###0.00`, obtendrás `12.34`.
:::

## Separadores de agrupación y decimales {#group-and-decimal-separators}

El `MaskedNumberField` soporta la personalización de los caracteres de **agrupación** y **decimal**, facilitando la adaptación del formateo numérico a diferentes locales o convenciones comerciales.

- El **separador de grupos** se utiliza para separar visualmente los miles (e.g. `1,000,000`).
- El **separador decimal** indica la parte fraccionaria de un número (e.g. `123.45`).

Esto es útil en aplicaciones internacionales donde diferentes regiones utilizan diferentes caracteres (e.g. `.` vs `,`).

```java
field.setGroupCharacter(".");   // e.g. 1.000.000
field.setDecimalCharacter(","); // e.g. 123,45
```

:::tip Comportamiento Predeterminado
Por defecto, `MaskedNumberField` aplica separadores de agrupación y decimales en función de la configuración regional actual de la aplicación. Puedes sobrescribirlos en cualquier momento utilizando los setters proporcionados.
:::

## Negable {#negateable}

El `MaskedNumberField` soporta una opción para controlar si se permiten números negativos.

Por defecto, se permiten valores negativos como `-123.45`. Para evitar esto, utiliza `setNegateable(false)` para restringir la entrada solo a valores positivos.

Esto es útil en escenarios comerciales donde valores como cantidades, totales o porcentajes deben ser siempre no negativos.

```java
field.setNegateable(false);
```

Cuando `negatable` se establece en `false`, el campo bloquea cualquier intento de ingresar un signo menos o de otro modo ingresar valores negativos.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Valores mínimos y máximos {#min-and-max-values}

El `MaskedNumberField` soporta la configuración de límites numéricos mediante `setMin()` y `setMax()`. Estas restricciones ayudan a garantizar que la entrada del usuario se mantenga dentro de un rango válido y esperado.

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

  Los valores por encima de este límite serán señalados como inválidos.

## Restaurar el valor {#restoring-the-value}

El `MaskedNumberField` soporta una función de restauración que restablece el valor del campo a un estado predefinido. Esto puede ser útil cuando los usuarios necesitan deshacer cambios, revertir ediciones accidentales o regresar a un valor predeterminado conocido.

Para restaurar un valor específico, defínelo utilizando `setRestoreValue()`. Cuando sea necesario, el campo puede ser restablecido programáticamente usando `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Formas de restaurar el valor {#ways-to-restore-the-value}

- **Programáticamente** usando `restoreValue()`
- **A través del teclado**, presionando <kbd>ESC</kbd> (esta es la tecla de restauración predeterminada a menos que se sobrescriba)

Si no se establece ningún valor de restauración, tanto `restoreValue()` como <kbd>ESC</kbd> establecen el valor del campo en `null`.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

El `MaskedNumberFieldSpinner` extiende el `MaskedNumberField` al agregar controles de spinner que permiten a los usuarios aumentar o disminuir el valor utilizando botones de paso o teclas de flecha. Esto es ideal para entradas como cantidades, ajustes de precios, controles de calificación o cualquier escenario donde los usuarios realicen cambios incrementales.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Características Clave {#key-features}

- **Incrementos de Paso**
  Utiliza `setStep()` para definir cuánto debería cambiar el valor con cada giro:

  ```java
  spinner.setStep(5.0); // Cada giro suma o resta 5
  ```

- **Controles Interactivos**
  Los usuarios pueden hacer clic en los botones del spinner o usar la entrada del teclado para ajustar el valor.

- **Todas las Características del MaskedNumberField**
  Soporta completamente máscaras, formateo, caracteres de agrupación/decimal, restricciones mínimas/máximas y lógica de restauración.

## Estilización {#styling}

<TableBuilder name="MaskedNumberField" />
