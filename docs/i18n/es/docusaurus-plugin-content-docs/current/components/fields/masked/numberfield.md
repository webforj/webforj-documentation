---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: 00d399f2bcfb22c884608aa8a0975573
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

El `MaskedNumberField` es un campo de texto diseñado para la entrada numérica estructurada. Asegura que los números estén formateados de manera consistente según una máscara definida, lo que lo hace especialmente útil para formularios financieros, campos de precios o cualquier entrada donde la precisión y legibilidad sean importantes.

Este componente admite el formateo numérico, la localización de caracteres decimales/agrupadores y restricciones de valor opcionales como mínimos o máximos.

## Básicos {#basics}

El `MaskedNumberField` se puede instanciar con o sin parámetros. Admite establecer un valor inicial, una etiqueta, un marcador de posición y un escuchador de eventos para reaccionar a los cambios de valor.

Esta demostración presenta un **Calculador de Propinas** que utiliza `MaskedNumberField` para una entrada numérica intuitiva. Un campo está configurado para aceptar un importe de factura formateado, mientras que el otro captura un porcentaje de propina de número entero. Ambos campos aplican máscaras numéricas para garantizar un formato consistente y predecible.

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## Reglas de máscara {#mask-rules}

El `MaskedNumberField` utiliza una cadena de máscara para controlar cómo se formatea y muestra la entrada numérica. 
Cada carácter en la máscara define un comportamiento de formato específico, permitiendo un control preciso sobre cómo aparecen los números.

### Caracteres de máscara {#mask-characters}

| Carácter | Descripción |
|-----------|-------------|
| `0`       | Siempre reemplazado por un dígito (0–9). |
| `#`       | Suprime ceros a la izquierda. Reemplazado por el carácter de relleno a la izquierda del punto decimal. Para dígitos finales, reemplazado por un espacio o cero. De lo contrario, reemplazado por un dígito. |
| `,`       | Utilizado como un separador de agrupación (por ejemplo, miles). Reemplazado por el carácter de relleno si no hay dígitos que lo precedan. De lo contrario, se muestra como una coma. |
| `-`       | Muestra un signo menos (`-`) si el número es negativo. Reemplazado por el carácter de relleno si es positivo. |
| `+`       | Muestra `+` para números positivos o `-` para números negativos. |
| `$`       | Siempre resulta en un signo de dólar. |
| `(`       | Inserta un paréntesis izquierdo `(` para valores negativos. Reemplazado por el carácter de relleno si es positivo. |
| `)`       | Inserta un paréntesis derecho `)` para valores negativos. Reemplazado por el carácter de relleno si es positivo. |
| `CR`      | Muestra `CR` para números negativos. Muestra dos espacios si el número es positivo. |
| `DR`      | Muestra `CR` para números negativos. Muestra `DR` para números positivos. |
| `*`       | Inserta un asterisco `*`. |
| `.`       | Marca el punto decimal. Si no aparece ningún dígito en la salida, se reemplaza por el carácter de relleno. Después del decimal, los caracteres de relleno se tratan como espacios. |
| `B`       | Siempre se convierte en un espacio. Cualquier otro carácter literal se muestra tal cual. |

Algunos de los caracteres anteriores pueden aparecer más de una vez en la máscara para el formateo. Estos incluyen `-`, `+`, `$`, y `(`. Si alguno de estos caracteres está presente en la máscara, el primero que se encuentra se moverá a la última posición donde un `#` o `,` fue reemplazado por el carácter de relleno. Si no existe tal posición, el carácter doble se deja donde está.

:::info Sin redondeo automático
Una máscara dentro de un campo **NO** redondea. Por ejemplo, al colocar un valor como `12.34567` en un campo que está enmascarado con `###0.00`, obtendrás `12.34`.
:::

## Separadores de agrupación y decimales {#group-and-decimal-separators}

El `MaskedNumberField` admite la personalización de los caracteres de **agrupación** y **decimal**, facilitando la adaptación del formateo numérico a diferentes convenciones locales o comerciales.

- El **separador de agrupación** se utiliza para separar visualmente los miles (por ejemplo, `1,000,000`).
- El **separador decimal** indica la parte fraccionaria de un número (por ejemplo, `123.45`).

Esto es útil en aplicaciones internacionales donde diferentes regiones utilizan diferentes caracteres (por ejemplo, `.` vs `,`).

```java
field.setGroupCharacter(".");   // por ejemplo, 1.000.000
field.setDecimalCharacter(","); // por ejemplo, 123,45
```

:::tip Comportamiento predeterminado
Por defecto, `MaskedNumberField` aplica separadores de agrupación y decimal según la configuración regional actual de la aplicación. Puedes anularlos en cualquier momento utilizando los métodos de establecimiento proporcionados.
:::

## Negable {#negateable}

El `MaskedNumberField` admite una opción para controlar si se permiten números negativos.

Por defecto, se permiten valores negativos como `-123.45`. Para evitar esto, usa `setNegateable(false)` para restringir la entrada solo a valores positivos.

Esto es útil en escenarios empresariales donde valores como cantidades, totales o porcentajes deben ser siempre no negativos.

```java
field.setNegateable(false);
```

Cuando `negatable` se establece en `false`, el campo bloquea cualquier intento de ingresar un signo menos o de ingresar valores negativos de cualquier otra manera.

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## Valores mínimo y máximo {#min-and-max-values}

El `MaskedNumberField` admite establecer límites numéricos utilizando `setMin()` y `setMax()`. 
Estas restricciones ayudan a garantizar que la entrada del usuario se mantenga dentro de un rango válido y esperado.

- **Valor mínimo**  
  Usa `setMin()` para definir el número más bajo aceptable:

  ```java
  field.setMin(10.0); // Valor mínimo: 10
  ```

  Si el usuario ingresa un número por debajo de este umbral, se considerará inválido.

- **Valor máximo**  
  Usa `setMax()` para definir el número más alto aceptable:

  ```java
  field.setMax(100.0); // Valor máximo: 100
  ```

  Los valores por encima de este límite se marcarán como inválidos.

## Restaurar el valor {#restoring-the-value}

El `MaskedNumberField` admite una función de restauración que restablece el valor del campo a un estado predefinido. 
Esto puede ser útil cuando los usuarios necesitan deshacer cambios, revertir ediciones accidentales o regresar a un valor predeterminado conocido.

Para habilitar este comportamiento, define el valor objetivo usando `setRestoreValue()`. 
Cuando sea necesario, el campo se puede restablecer programáticamente utilizando `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Formas de restaurar el valor {#ways-to-restore-the-value}

- **Programáticamente** usando `restoreValue()`
- **A través del teclado**, presionando <kbd>ESC</kbd> (esta es la tecla de restauración predeterminada a menos que se anule)

El valor de restauración debe establecerse explícitamente. Si no se define, la función no revertirá el campo.

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

El `MaskedNumberFieldSpinner` amplía [`MaskedNumberField`](#basics) al agregar controles de spinner que permiten a los usuarios aumentar o disminuir el valor utilizando botones de paso o teclas de flecha. 
Esto es ideal para entradas como cantidades, ajustes de precios, controles de calificación o cualquier escenario donde los usuarios hagan cambios incrementales.

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### Características clave {#key-features}

- **Incrementos de paso**  
  Usa `setStep()` para definir cuánto debería cambiar el valor con cada giro:

  ```java
  spinner.setStep(5.0); // Cada giro suma o resta 5
  ```

- **Controles interactivos**  
  Los usuarios pueden hacer clic en los botones de spinner o usar la entrada del teclado para ajustar el valor.

- **Todas las características de MaskedNumberField**  
  Admite completamente máscaras, formateo, caracteres de agrupación/decimales, restricciones de min/máx y lógica de restauración.

## Estilizando {#styling}

<TableBuilder name="MaskedNumberField" />
