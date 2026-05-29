---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: aa5037e2faa2968328081b1811dcabb0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

El componente `NumberField` acepta entradas numéricas y rechaza valores inválidos automáticamente. Soporta límites mínimo y máximo, intervalos de paso y texto de marcador de posición.

<!-- INTRO_END -->

## Usando `NumberField` {#using-numberfield}

<ParentLink parent="Field" />

`NumberField` extiende la clase `Field` compartida, que proporciona características comunes en todos los componentes de campo. El siguiente ejemplo crea un `NumberField` con una etiqueta y un texto de marcador de posición.

<ComponentDemo
path='/webforj/numberfield'
files={['src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java']}
/>

## Valor del campo {#field-value}

El componente `NumberField` almacena su valor como un `Double`, permitiendo el manejo preciso de números enteros y decimales.

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
Si bien el campo está diseñado para aceptar solo entradas numéricas válidas, ten en cuenta que el valor subyacente puede ser nulo. Siempre verifica si es nulo antes de usar el resultado.
:::

## Usos {#usages}

El `NumberField` se utiliza mejor en escenarios donde capturar, mostrar o manipular datos numéricos es esencial para tu aplicación. Aquí hay algunos ejemplos de cuándo usar el `NumberField`:

1. **Formularios de Entrada Numérica**: Al diseñar formularios que requieren entradas numéricas, utilizar un `NumberField` simplifica el proceso de entrada para los usuarios. Esto es particularmente útil para aplicaciones que recopilan datos de usuarios o requieren valores numéricos.

2. **Análisis de Datos y Cálculos**: Un `NumberField` es particularmente valioso en aplicaciones que involucren análisis de datos, cálculos u operaciones matemáticas. Permiten a los usuarios ingresar o manipular valores numéricos con precisión.

3. **Aplicaciones Financieras y de Presupuesto**: Las aplicaciones que implican cálculos financieros, presupuestos o seguimiento de gastos a menudo requieren entradas numéricas precisas. Un `NumberField` asegura una entrada precisa de cifras financieras.

4. **Medición y Conversión de Unidades**: En aplicaciones que tratan con mediciones o conversiones de unidades, el `NumberField` es ideal para ingresar valores numéricos con unidades como longitud, peso o volumen.

## Valor mínimo y máximo {#min-and-max-value}

Con el método `setMin()`, puedes especificar el valor mínimo aceptable en el campo numérico. Si un usuario ingresa un valor inferior a este umbral, el componente fallará la validación de restricción y proporcionará retroalimentación apropiada.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Mínimo permitido: 0.0
```

Por separado, el método `setMax()` te permite definir el valor máximo aceptable. Si un usuario ingresa un valor superior a este límite, la entrada será rechazada. Cuando se establecen tanto el valor mínimo como el máximo, el máximo debe ser mayor o igual al mínimo.

```java
numberField.setMax(100.0); // Máximo permitido: 100.0
```

En esta configuración, ingresar un valor como -5 o 150 sería inválido, mientras que los valores entre 0 y 100 son aceptados.

## Granularidad {#granularity}

Puedes utilizar el método `setStep()` para especificar la granularidad a la que debe adherirse el valor al usar las teclas de flecha para modificarlo. Esto incrementará o disminuirá el valor del componente por un determinado paso cada vez. Esto no se aplica cuando un usuario ingresa un valor directamente, sino solo al interactuar con el `NumberField` utilizando las teclas de flecha.

## Texto de marcador de posición {#placeholder-text}

Puedes establecer texto de marcador de posición para el `NumberField` utilizando el método `setPlaceholder()`. El texto de marcador de posición se muestra cuando el campo está vacío, ayudando a orientar al usuario para que ingrese la entrada apropiada en el `NumberField`.

:::tip Proporciona contexto claro para precisión
Si la entrada numérica está relacionada con una unidad de medida específica o tiene un contexto particular, proporciona etiquetado claro o información adicional para guiar a los usuarios y asegurar entradas precisas.
:::

## Mejores prácticas {#best-practices}

Para asegurar una integración fluida y una experiencia óptima para el usuario, considera las siguientes mejores prácticas al usar el `NumberField`:

- **Accesibilidad**: Utiliza el componente `NumberField` con la accesibilidad en mente, cumpliendo con los estándares de accesibilidad como etiquetado adecuado, soporte para navegación por teclado y compatibilidad con tecnologías asistivas. Asegúrate de que los usuarios con discapacidades puedan interactuar con el `NumberField` de manera efectiva.

- **Utiliza Botones de Incremento/Decremento**: Si es apropiado para tu aplicación, considera utilizar botones de incremento y decremento con el `NumberField`. Esto permite a los usuarios ajustar el valor numérico por un incremento o decremento específico con un solo clic.
