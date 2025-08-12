---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: 2fcf0727f1bcfd60a2800bad252733ba
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

El componente `NumberField` almacena su valor como un `Double`, permitiendo el manejo preciso tanto de enteros como de números decimales.

### Obtener el valor actual {#getting-the-current-value}

Puedes recuperar el valor numérico ingresado por el usuario usando:

```java
Double currentValue = numberField.getValue();
```

### Establecer un nuevo valor {#setting-a-new-value}

Para establecer el campo programáticamente:

```java
numberField.setValue(42.5);
```

Si no se ha ingresado ningún valor y no se ha configurado un valor predeterminado, `getValue()` devolverá `null`.

:::tip
Si bien el campo está diseñado para aceptar solo entradas numéricas válidas, ten en cuenta que el valor subyacente puede ser nulo. Siempre verifica si es nulo antes de usar el resultado.
:::

## Usos {#usages}

El `NumberField` es mejor utilizado en escenarios donde capturar, mostrar o manipular datos numéricos es esencial para tu aplicación. Aquí hay algunos ejemplos de cuándo usar el `NumberField`:

1. **Formularios de Entrada Numérica**: Al diseñar formularios que requieren entradas numéricas, usar un `NumberField` simplifica el proceso de entrada para los usuarios. Esto es particularmente útil para aplicaciones que recopilan datos del usuario o requieren valores numéricos.

2. **Análisis de Datos y Cálculos**: Un `NumberField` es particularmente valioso en aplicaciones que implican análisis de datos, cálculos u operaciones matemáticas. Permiten a los usuarios ingresar o manipular valores numéricos con precisión.

3. **Aplicaciones Financieras y de Presupuesto**: Las aplicaciones que implican cálculos financieros, elaboración de presupuestos o seguimiento de gastos a menudo requieren entradas numéricas precisas. Un `NumberField` asegura una entrada precisa de cifras financieras.

4. **Medición y Conversión de Unidades**: En aplicaciones que manejan mediciones o conversiones de unidades, el `NumberField` es ideal para ingresar valores numéricos con unidades como longitud, peso o volumen.

## Valor mínimo y máximo {#min-and-max-value}

Con el método `setMin()`, puedes especificar el valor mínimo aceptable en el campo numérico. Si un usuario ingresa un valor inferior a este umbral, el componente fallará en la validación de restricciones y proporcionará la retroalimentación adecuada.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Mínimo permitido: 0.0
```

Por separado, el método `setMax()` te permite definir el valor máximo aceptable. Si un usuario ingresa un valor superior a este límite, la entrada será rechazada. Cuando se establecen tanto valores mínimo como máximo, el máximo debe ser mayor o igual al mínimo.

```java
numberField.setMax(100.0); // Máximo permitido: 100.0
```

En esta configuración, ingresar un valor como -5 o 150 sería inválido, mientras que los valores entre 0 y 100 son aceptados.

## Granularidad {#granularity}

Puedes utilizar el método `setStep()` para especificar la granularidad a la que el valor debe adherirse al usar las teclas de flecha para modificar el valor. Esto incrementará o decrementará el valor del componente en un cierto paso cada vez. Esto no se aplica cuando un usuario ingresa un valor directamente, sino solo cuando interactúa con el `NumberField` utilizando las teclas de flecha.

## Texto de marcador de posición {#placeholder-text}

Puedes establecer un texto de marcador de posición para el `NumberField` utilizando el método `setPlaceholder()`. El texto de marcador de posición se muestra cuando el campo está vacío, ayudando a indicar al usuario que ingrese la entrada adecuada en el `NumberField`.

:::tip Proporciona un contexto claro para la precisión
Si la entrada numérica se relaciona con una unidad de medida específica o tiene un contexto particular, proporciona una etiqueta clara o información adicional para guiar a los usuarios y garantizar una entrada precisa.
:::

## Mejores prácticas {#best-practices}

Para garantizar una integración fluida y una experiencia óptima para el usuario, considera las siguientes mejores prácticas al usar el `NumberField`:

- **Accesibilidad**: Utiliza el componente `NumberField` teniendo en cuenta la accesibilidad, cumpliendo con los estándares de accesibilidad como el etiquetado adecuado, soporte para navegación por teclado y compatibilidad con tecnologías de asistencia. Asegúrate de que los usuarios con discapacidades puedan interactuar con el `NumberField` de manera efectiva.

- **Utiliza Botones de Incremento/Decremento**: Si es apropiado para tu aplicación, considera utilizar botones de incremento y decremento con el `NumberField`. Esto permite a los usuarios ajustar el valor numérico en un incremento o decremento específico con un solo clic.
