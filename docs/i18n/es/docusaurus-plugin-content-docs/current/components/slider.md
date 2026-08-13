---
title: Slider
sidebar_position: 101
description: >-
  Let users pick a numeric value with the Slider component, with configurable
  range, step, tick marks, labels, and orientation.
_i18n_hash: 06f08c2c7500c5fb8d50a1dcfd8488da
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

El componente `Slider` proporciona a los usuarios una forma de seleccionar un valor numérico arrastrando una perilla a lo largo de una pista entre un límite mínimo y máximo. Se pueden configurar intervalos de pasos, marcas de verificación y etiquetas para guiar la selección.

<!-- INTRO_END -->

## Basics {#basics}

El `Slider` está diseñado para funcionar directamente sin necesidad de una configuración adicional para que funcione de manera efectiva. Por defecto, abarca un rango de 0 a 100 con un valor inicial de 50, lo que lo convierte en ideal para la integración rápida en cualquier aplicación. Para casos de uso más específicos, el `Slider` se puede personalizar con propiedades como orientación, marcas de verificación, etiquetas y tooltips.

Aquí hay un ejemplo de un `Slider` que permite a los usuarios ajustar los niveles de volumen dentro de un rango predefinido:

<ComponentDemo
path='/webforj/slider'
files={['src/main/java/com/webforj/samples/views/slider/SliderView.java']}
height='100px'
/>

## `Slider` value {#slider-value}

El valor del `Slider` representa la posición actual de la perilla en el slider y se define como un entero dentro del rango del `Slider`. Este valor se actualiza dinámicamente a medida que el usuario interactúa con el slider, convirtiéndose en una propiedad esencial para rastrear la entrada del usuario.

:::tip Valor por defecto
Por defecto, el `Slider` comienza con un valor de 50, asumiendo el rango por defecto de 0 a 100.
:::

### Establecer y obtener el valor {#setting-and-getting-the-value}

Puedes establecer el valor del `Slider` durante la inicialización o actualizarlo más tarde usando el método `setValue()`. Para recuperar el valor actual, utiliza el método `getValue()`.

```java
Slider slider = new Slider();
slider.setValue(25); // Establece el slider en 25

Integer value = slider.getValue();
System.out.println("Valor actual del Slider: " + value);
```

## Valores mínimo y máximo {#minimum-and-maximum-values}

Los valores mínimo y máximo definen el rango permitido del `Slider`, determinando los límites dentro de los cuales la perilla del `Slider` puede moverse. Por defecto, el rango está configurado de 0 a 100, pero puedes personalizar estos valores para adaptarlos a tus necesidades.

Los intervalos en el `Slider` tienen un paso por defecto de 1, lo que significa que el número de intervalos está determinado por el rango. Por ejemplo:
- Un `Slider` con un rango de 0 a 10 tendrá 10 intervalos.
- Un `Slider` con un rango de 0 a 100 tendrá 100 intervalos.

Estos intervalos están distribuidos uniformemente a lo largo de la pista del slider, con su espaciado dependiendo de las dimensiones del `Slider`.

A continuación se muestra un ejemplo de la creación de un `Slider` con un rango personalizado:

<ComponentDemo
path='/webforj/donationslider'
files={['src/main/java/com/webforj/samples/views/slider/DonationSliderView.java']}
height='200px'
/>

## Configuración de marcas de verificación {#tick-configuration}

El componente `Slider` ofrece una configuración flexible de marcas de verificación, lo que te permite personalizar cómo se muestran las marcas de verificación y cómo la perilla del slider interactúa con ellas. Esto incluye ajustar el espaciado entre marcas de verificación mayores y menores, mostrar/ocultar marcas de verificación y habilitar el ajuste a las marcas de verificación para una entrada precisa del usuario.

### Espaciado de marcas de verificación mayores y menores {#major-and-minor-tick-spacing}

Puedes definir el espaciado para las marcas de verificación mayores y menores, lo que determina con qué frecuencia aparecen en la pista del `Slider`:

- Las marcas de verificación mayores son más grandes y a menudo están etiquetadas para representar valores clave.
- Las marcas de verificación menores son más pequeñas y aparecen entre las marcas de verificación mayores para ofrecer intervalos más finos.

Establece el espaciado de las marcas de verificación utilizando los siguientes métodos `setMajorTickSpacing()` y `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Marcas mayores cada 10 unidades
slider.setMinorTickSpacing(2);  // Marcas menores cada 2 unidades
```

### Mostrar u ocultar marcas de verificación {#show-or-hide-ticks}

Puedes alternar la visibilidad de las marcas de verificación utilizando el método `setTicksVisible()`. Por defecto, las marcas de verificación están ocultas.

```java
slider.setTicksVisible(true); // Mostrar marcas de verificación
slider.setTicksVisible(false); // Ocultar marcas de verificación
```

### Ajuste {#snapping}

Para asegurar que la perilla del `Slider` se alinee con la marca de verificación más cercana durante la interacción del usuario, habilita el ajuste utilizando el método `setSnapToTicks()`:

```java
slider.setSnapToTicks(true); // Habilitar ajuste
```

Aquí hay un ejemplo de un `Slider` completamente configurado que muestra la configuración de marcas mayores y menores junto con la capacidad de ajuste para ajustes precisos:

<ComponentDemo
path='/webforj/slidertickspacing'
files={['src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java']}
height='350px'
/>

## Orientación e inversión {#orientation-and-inversion}

El componente `Slider` admite dos orientaciones: horizontal (por defecto) y vertical. Puedes cambiar la orientación para adaptarla a tu diseño de UI y requisitos de la aplicación.

Además de la orientación, el `Slider` también puede ser invertido. Por defecto:

- Un `Slider` horizontal va de mínimo (izquierda) a máximo (derecha).
- Un `Slider` vertical va de mínimo (abajo) a máximo (arriba).

Cuando se invierte, esta dirección se revierte. Usa el método `setInverted(true)` para habilitar la inversión.

<ComponentDemo
path='/webforj/sliderorientation'
files={['src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java']}
height='440px'
/>

## Etiquetas {#labels}

El componente `Slider` admite etiquetas en las marcas de verificación para ayudar a los usuarios a interpretar los valores más fácilmente. Puedes usar etiquetas numéricas predeterminadas o proporcionar etiquetas personalizadas, y puedes alternar su visibilidad según sea necesario.

### Etiquetas predeterminadas {#default-labels}

Por defecto, el slider puede mostrar etiquetas numéricas en las marcas de verificación mayores. Estos valores son determinados por la configuración `setMajorTickSpacing()`. Para habilitar las etiquetas predeterminadas, usa:

```java
slider.setLabelsVisible(true);
```

### Etiquetas personalizadas {#custom-labels}

Puedes reemplazar las etiquetas numéricas predeterminadas con texto personalizado utilizando el método `setLabels()`. Esto es útil cuando deseas mostrar valores más significativos (por ejemplo, temperatura, moneda o categorías).

```java
Map<Integer, String> customLabels = Map.of(
  0, "Frío",
  30, "Fresco",
  50, "Moderado",
  80, "Cálido",
  100, "Caliente"
);

slider.setLabels(customLabels);
slider.setLabelsVisible(true);
```

### Alternar la visibilidad de las etiquetas {#toggling-label-visibility}

Ya sea que estés utilizando etiquetas predeterminadas o personalizadas, puedes controlar su visibilidad con `setLabelsVisible(true)` o ocultarlas con `setLabelsVisible(false)`.

<ComponentDemo
path='/webforj/sliderlabels'
files={['src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java']}
height='150px'
/>

## Tooltips {#tooltips}

Los tooltips mejoran la usabilidad al mostrar el valor del `Slider` directamente encima o debajo de la perilla, ayudando a los usuarios a hacer ajustes más precisos. Puedes configurar el comportamiento, la visibilidad y el formato del tooltip según tus necesidades.

Para habilitar los tooltips, utiliza el método `setTooltipVisible()`. Por defecto, los tooltips están deshabilitados:

```java
slider.setTooltipVisible(true); // Habilitar tooltips
slider.setTooltipVisible(false); // Deshabilitar tooltips
```

Los tooltips también se pueden configurar para aparecer solo cuando el usuario interactúa con el `Slider`. Usa el método `setTooltipVisibleOnSlideOnly()` para habilitar este comportamiento. Esto es especialmente útil para reducir el desorden visual mientras se proporciona retroalimentación útil durante la interacción.

Aquí hay un ejemplo de un `Slider` completamente configurado con tooltips:

### Personalización del tooltip {#tooltip-customization}

Por defecto, el `Slider` muestra un tooltip con su valor actual. Si deseas personalizar este texto, utiliza el método `setTooltipText()`. Esto es útil cuando deseas que el tooltip muestre texto estático o descriptivo en lugar del valor en vivo.

También puedes usar una expresión de JavaScript para formatear el tooltip dinámicamente. Si tu expresión incluye la palabra clave `return`, se usa tal cual. Si no, se envuelve automáticamente con `return` y `;` para formar una función válida. Por ejemplo:

```java
// Muestra el valor seguido de un signo de dólar
slider.setTooltipText("return x + '$'");
```

O simplemente:

```java
// Interpretado como: return x + ' unidades';
slider.setTooltipText("x + ' unidades'");
```

## Estilización {#styling}

### Temas {#themes}

El `Slider` viene con 6 temas incorporados para una estilización rápida sin el uso de CSS. La tematización es compatible mediante el uso de una clase enum incorporada.
A continuación se muestran sliders con cada uno de los temas admitidos aplicados:

<ComponentDemo
path='/webforj/sliderthemes'
files={['src/main/java/com/webforj/samples/views/slider/SliderThemesView.java']}
height='460px'
/>

<TableBuilder name="Slider" />
