---
sidebar_position: 16
title: MaskDecorator
sidebar_class_name: new-content
_i18n_hash: 30ecd8eeaa79a3e5f963e319373d1378
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/MaskDecorator" top='true'/>

`MaskDecorator` es una clase de utilidad estática para aplicar máscaras a cadenas, números, fechas y horas fuera de un campo de entrada. Utiliza la misma sintaxis de máscara que los [componentes de campo enmascarados](/docs/components/fields/masked/overview) de webforJ, lo que hace que sea sencillo formatear y analizar valores de manera consistente, ya sea en una etiqueta de visualización, un renderizador de [`Tabla`](/docs/components/table/overview) o cualquier otra ubicación en tu aplicación.

Usa `MaskDecorator` cuando necesites formatear valores programáticamente para su visualización en lugar de para la entrada interactiva, como en los renderizadores de celdas de tabla, etiquetas de solo lectura, informes exportados o cualquier contexto donde un campo de formulario no sea apropiado. Para un formato interactivo mientras el usuario escribe, utiliza un componente de campo enmascarado en su lugar.

## Enmascarando cadenas {#masking-strings}

Usa `forString()` para aplicar una máscara de caracteres a un valor de cadena simple:

```java
String result = MaskDecorator.forString("abc123", "AAA-000");
// → "ABC-123"
```

La máscara controla qué caracteres son aceptados en cada posición.

### Caracteres de máscara {#string-mask-characters}

| Carácter | Descripción |
|----------|-------------|
| `X`      | Cualquier carácter imprimible |
| `a`      | Cualquier carácter alfabético |
| `A`      | Cualquier carácter alfabético; las letras minúsculas se convierten a mayúsculas |
| `0`      | Cualquier dígito (0–9) |
| `z`      | Cualquier dígito o letra |
| `Z`      | Cualquier dígito o letra; las letras minúsculas se convierten a mayúsculas |

Cualquier otro carácter en la máscara se trata como un literal y se inserta tal cual en la salida. Los caracteres inválidos en la entrada se ignoran silenciosamente, las entradas cortas se completan con espacios y las entradas largas se truncarán para ajustarse a la máscara.

### Ejemplos {#string-examples}

```java
MaskDecorator.forString("1234567890", "(000) 000-0000");  // → "(123) 456-7890"
MaskDecorator.forString("a1b2c3",     "A0A 0A0");         // → "A1B 2C3"
MaskDecorator.forString("1234",       "ZZZZ-0000");        // → "1234-    " (rellenado)
```

## Enmascarando números {#masking-numbers}

Usa `forNumber()` para formatear un valor numérico utilizando una máscara numérica:

```java
String result = MaskDecorator.forNumber(1234567.89, "#,###,##0.00");
// → "1,234,567.89"
```

### Caracteres de máscara {#number-mask-characters}

| Carácter | Descripción |
|----------|-------------|
| `0`      | Siempre reemplazado por un dígito (0–9) |
| `#`      | Suprime ceros a la izquierda. Reemplazado por el carácter de relleno a la izquierda del punto decimal. Para dígitos finales a la derecha, reemplazado por un espacio o cero. De lo contrario, reemplazado por un dígito |
| `,`      | Utilizado como separador de agrupación. Reemplazado por el carácter de relleno si no se han colocado dígitos; de lo contrario, mostrado como una coma |
| `-`      | Muestra `-` si el número es negativo; reemplazado por el carácter de relleno si es positivo |
| `+`      | Muestra `+` si es positivo, o `-` si es negativo |
| `$`      | Siempre resulta en un signo de dólar |
| `(`      | Inserta `(` si el número es negativo; reemplazado por el carácter de relleno si es positivo |
| `)`      | Inserta `)` si el número es negativo; reemplazado por el carácter de relleno si es positivo |
| `CR`     | Inserta `CR` para números negativos; dos espacios para números positivos |
| `DR`     | Inserta `CR` para números negativos; `DR` para números positivos |
| `*`      | Siempre inserta un asterisco |
| `.`      | Marca el punto decimal. Reemplazado por el carácter de relleno si no aparecen dígitos en la salida. Después del decimal, el carácter de relleno se convierte en un espacio |
| `B`      | Siempre se convierte en un espacio; cualquier otro carácter literal se copia tal cual |

Los caracteres `-`, `+`, `$` y `(` pueden flotar: la primera ocurrencia se mueve a la última posición donde un `#` o `,` fue reemplazado por el carácter de relleno.

:::info Comportamiento de redondeo
`forNumber()` redondea los valores para que coincidan con la precisión decimal en la máscara. Por ejemplo, `MaskDecorator.forNumber(12.34567, "###0.00")` produce `"  12.35"`.
:::

### Ejemplos {#number-examples}

```java
MaskDecorator.forNumber(1234.5,    "###,##0.00");  // → "  1,234.50"
MaskDecorator.forNumber(-9876.0,   "###,##0.00-"); // → "  9,876.00-"
MaskDecorator.forNumber(42.0,      "$###,##0.00"); // → "     $42.00"
MaskDecorator.forNumber(0.5,       "#0.000");      // → " 0.500"
```

## Enmascarando fechas {#masking-dates}

Usa `forDate()` para formatear un valor `LocalDate` con una máscara de fecha:

```java
LocalDate date = LocalDate.of(2025, 7, 4);
String result = MaskDecorator.forDate(date, "%Mz/%Dz/%Yl");
// → "07/04/2025"
```

Usa `parseDate()` para analizar una cadena de fecha enmascarada de nuevo a un `LocalDate`:

```java
LocalDate date = MaskDecorator.parseDate("07/04/2025", "%Mz/%Dz/%Yl");
// → LocalDate.of(2025, 7, 4)
```

Hay una sobrecarga consciente de la región disponible al analizar cadenas que contienen referencias de números de semana:

```java
LocalDate date = MaskDecorator.parseDate("07/04/2025", "%Mz/%Dz/%Yl", Locale.US);
```

### Indicadores de formato de fecha {#date-format-indicators}

| Formato | Descripción |
|---------|-------------|
| `%Y`    | Año         |
| `%M`    | Mes         |
| `%D`    | Día         |

### Modificadores {#date-modifiers}

Un modificador opcional sigue inmediatamente al indicador de formato:

| Modificador | Descripción                |
|-------------|----------------------------|
| `z`         | Relleno con ceros          |
| `s`         | Representación en texto corto |
| `l`         | Representación en texto largo  |
| `p`         | Número empaquetado         |
| `d`         | Decimal (formato predeterminado) |

### Ejemplos {#date-examples}

```java
LocalDate d = LocalDate.of(2025, 3, 5);

MaskDecorator.forDate(d, "%Mz/%Dz/%Yl");  // → "03/05/2025"
MaskDecorator.forDate(d, "%Dz.%Mz.%Yz");  // → "05.03.25"
MaskDecorator.forDate(d, "%Dl, %Ml %Dz");  // → "Wednesday, March 05"
MaskDecorator.forDate(d, "%Yl-%Mz-%Dz");  // → "2025-03-05"
```

## Enmascarando horas {#masking-times}

Usa `forTime()` para formatear un valor `LocalTime` con una máscara de hora:

```java
LocalTime time = LocalTime.of(14, 30, 0);
String result = MaskDecorator.forTime(time, "%Hz:%mz");
// → "14:30"
```

Usa `parseTime()` para analizar una cadena de hora enmascarada de nuevo a un `LocalTime`:

```java
LocalTime time = MaskDecorator.parseTime("14:30", "%Hz:%mz");
// → LocalTime.of(14, 30)
```

Hay una sobrecarga consciente de la región disponible al analizar cadenas que contienen valores AM/PM localizados:

```java
LocalTime time = MaskDecorator.parseTime("02:30 pm", "%hz:%mz %p", Locale.US);
```

### Indicadores de formato de hora {#time-format-indicators}

| Formato | Descripción            |
|---------|------------------------|
| `%H`    | Hora (reloj de 24 horas) |
| `%h`    | Hora (reloj de 12 horas) |
| `%m`    | Minuto                 |
| `%s`    | Segundo                 |
| `%p`    | am/pm                  |

### Modificadores {#time-modifiers}

Las máscaras de hora utilizan los mismos modificadores que las máscaras de fecha. Consulta [Modificadores de fecha](#date-modifiers).

### Ejemplos {#time-examples}

```java
LocalTime t = LocalTime.of(9, 5, 30);

MaskDecorator.forTime(t, "%Hz:%mz:%sz");  // → "09:05:30"
MaskDecorator.forTime(t, "%hz:%mz %p");   // → "09:05 am"
MaskDecorator.forTime(t, "%Hz%mz");       // → "0905"
```

## Enmascarando fecha y hora {#masking-datetime}

Usa `forDateTime()` para formatear un valor `LocalDateTime` utilizando una máscara combinada de fecha y hora:

```java
LocalDateTime dt = LocalDateTime.of(2025, 7, 4, 14, 30, 0);
String result = MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz");
// → "07/04/2025 14:30"
```

### Indicadores de formato {#datetime-format-indicators}

`forDateTime()` admite todos los indicadores de formato de fecha y hora en cualquier combinación. Consulta [Indicadores de formato de fecha](#date-format-indicators) y [Indicadores de formato de hora](#time-format-indicators) para la lista completa.

### Modificadores {#datetime-modifiers}

Todos los modificadores descritos en [Modificadores de fecha](#date-modifiers) se aplican tanto a la parte de fecha como a la de hora de una máscara combinada.

### Ejemplos {#datetime-examples}

```java
LocalDateTime dt = LocalDateTime.of(2025, 7, 4, 14, 30, 0);

MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz");      // → "07/04/2025 14:30"
MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz:%sz");  // → "07/04/2025 14:30:00"
MaskDecorator.forDateTime(dt, "%Dz.%Mz.%Yz %hz:%mz %p");  // → "04.07.25 02:30 pm"
```

## Manejo de resultados nulos {#handling-null-results}

:::warning
Todos los métodos `for*()` y `parse*()` devuelven `null` si la entrada es inválida o no se puede analizar. Siempre verifica que el resultado no sea nulo antes de usarlo en la lógica de tu aplicación.
:::

```java
String formatted = MaskDecorator.forDate(date, "%Mz/%Dz/%Yl");
if (formatted != null) {
  label.setText(formatted);
}
```
