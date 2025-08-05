---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: 0d5052fd2f20b391e0eaadbf7c771e5e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

<ParentLink parent="Field" />

Puedes usar el componente `NumberField` para aceptar entradas numéricas de un usuario. Asegura que solo se ingresen valores numéricos válidos y proporciona una interfaz conveniente para ingresar números.

<ComponentDemo 
path='/webforj/numberfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java'
/>

## Valor del campo {#field-value}

El componente `NumberField` almacena su valor como un `Double`, permitiendo un manejo preciso tanto de enteros como de números decimales.

### Obtener el valor actual {#getting-the-current-value}

Puedes recuperar el valor numérico ingresado por el usuario utilizando:

```java
Double currentValue = numberField.getValue();
```

### Establecer un nuevo valor {#setting-a-new-value}

Para establecer el campo programáticamente:

```java
numberField.setValue(42.5);
```

Si no se ha ingresado ningún valor y no se ha establecido un valor predeterminado, `getValue()` devolverá `null`.

:::tip
Aunque el campo está diseñado para aceptar solo entradas numéricas válidas, ten en cuenta que el valor subyacente puede ser nulo. Siempre verifica si es nulo antes de usar el resultado.
:::

## Usos {#usages}

El `NumberField` es mejor utilizado en escenarios donde capturar, mostrar o manipular datos numéricos es esencial para tu aplicación. Aquí hay algunos ejemplos de cuándo usar el `NumberField`:

1. **Formularios de entrada numérica**: Al diseñar formularios que requieren entradas numéricas, usar un `NumberField` simplifica el proceso de entrada para los usuarios. Esto es particularmente útil para aplicaciones que recopilan datos del usuario o requieren valores numéricos.

2. **Análisis de datos y cálculos**: Un `NumberField` es especialmente valioso en aplicaciones que involucran análisis de datos, cálculos u operaciones matemáticas. Permiten a los usuarios ingresar o manipular valores numéricos con precisión.

3. **Aplicaciones financieras y de presupuestación**: Las aplicaciones que involucran cálculos financieros, presupuestación o seguimiento de gastos a menudo requieren entradas numéricas precisas. Un `NumberField` asegura la entrada precisa de cifras financieras.

4. **Medición y conversión de unidades**: En aplicaciones que manejan mediciones o conversiones de unidades, el `NumberField` es ideal para ingresar valores numéricos con unidades como longitud, peso o volumen.

## Valor mínimo y máximo {#min-and-max-value}

Con el método `setMin()`, puedes especificar el valor mínimo aceptable en el campo numérico. Si un usuario ingresa un valor inferior a este umbral, el componente fallará en la validación de restricciones y proporcionará la retroalimentación adecuada.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Mínimo permitido: 0.0
```

Por separado, el método `setMax()` te permite definir el valor máximo aceptable. Si un usuario ingresa un valor superior a este límite, la entrada será rechazada. Cuando se establecen valores mínimos y máximos, el máximo debe ser mayor o igual al mínimo.

```java
numberField.setMax(100.0); // Máximo permitido: 100.0
```

En esta configuración, ingresar un valor como -5 o 150 sería inválido, mientras que se aceptan valores entre 0 y 100.

## Granularidad {#granularity}

Puedes usar el método `setStep()` para especificar la granularidad a la que debe adherirse el valor al usar las teclas de flecha para modificar el valor. Esto incrementará o decrementará el valor del componente en un cierto paso cada vez. Esto no se aplica cuando un usuario ingresa un valor directamente, sino solo cuando interactúa con el `NumberField` utilizando las teclas de flecha.

## Texto de marcador de posición {#placeholder-text}

Puedes establecer un texto de marcador de posición para el `NumberField` utilizando el método `setPlaceholder()`. El texto de marcador de posición se muestra cuando el campo está vacío, ayudando a invitar al usuario a ingresar una entrada apropiada en el `NumberField`.

:::tip Proporcione un contexto claro para la precisión
Si la entrada numérica se relaciona con una unidad de medida específica o tiene un contexto particular, proporciona etiquetas claras o información adicional para guiar a los usuarios y asegurar una entrada precisa.
:::

## Mejores prácticas {#best-practices}

Para asegurar una integración fluida y una experiencia óptima para el usuario, considera las siguientes mejores prácticas al usar el `NumberField`:

- **Accesibilidad**: Utiliza el componente `NumberField` con la accesibilidad en mente, cumpliendo con los estándares de accesibilidad, como etiquetas adecuadas, soporte para navegación por teclado y compatibilidad con tecnologías asistivas. Asegúrate de que los usuarios con discapacidades puedan interactuar de manera efectiva con el `NumberField`.

- **Utiliza botones de incremento/decremento**: Si es apropiado para tu aplicación, considera utilizar botones de incremento y decremento con el `NumberField`. Esto permite a los usuarios ajustar el valor numérico por un incremento o decremento específico con un solo clic.
