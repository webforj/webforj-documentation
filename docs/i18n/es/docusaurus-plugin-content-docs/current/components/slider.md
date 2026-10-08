---
title: Slider
sidebar_position: 101
description: >-
  Let users pick a numeric value with the Slider component, with configurable
  range, step, tick marks, labels, and orientation.
_i18n_hash: 88cace5ce1650eaaf33dfc4535125dc0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

El componente `Slider` ofrece a los usuarios una forma de seleccionar un valor numérico arrastrando una perilla a lo largo de una pista entre un límite mínimo y máximo. Se pueden configurar intervalos de pasos, marcas de tics y etiquetas para guiar la selección.

<!-- INTRO_END -->

Un nuevo `Slider` abarca un rango de 0 a 100 con un valor inicial de 50, por lo que funciona sin ninguna configuración. Propiedades como la orientación, marcas de tics, etiquetas y descripciones emergentes cubren casos más específicos, como el control de volumen a continuación.

<ComponentDemo
path='/webforj/slider'
files={['src/main/java/com/webforj/samples/views/slider/SliderView.java']}
height='100px'
/>

## Valor del `Slider` {#slider-value}

El valor del `Slider` representa la posición actual de la perilla en el control deslizante y se define como un entero dentro del rango del `Slider`. Este valor se actualiza dinámicamente a medida que el usuario interactúa con el control deslizante, lo que lo convierte en una propiedad esencial para rastrear la entrada del usuario.

:::tip Valor por defecto
Por defecto, el `Slider` comienza con un valor de 50, asumiendo el rango predeterminado de 0 a 100.
:::

### Estableciendo y obteniendo el valor {#setting-and-getting-the-value}

Puedes establecer el valor del `Slider` durante la inicialización o actualizarlo más tarde usando el método `setValue()`. Para recuperar el valor actual, utiliza el método `getValue()`.

```java
Slider slider = new Slider();
slider.setValue(25); // Establece el slider en 25

Integer value = slider.getValue();
System.out.println("Valor actual del Slider: " + value);
```

## Valores mínimo y máximo {#minimum-and-maximum-values}

Los valores mínimo y máximo definen el rango permitido del `Slider`, determinando los límites dentro de los cuales puede moverse la perilla del `Slider`. Por defecto, el rango se establece de 0 a 100, pero puedes personalizar estos valores para adaptarlos a tus necesidades.

Los intervalos en el `Slider` tienen un paso predeterminado de 1, lo que significa que el número de intervalos se determina por el rango. Por ejemplo:
- Un `Slider` con un rango de 0 a 10 tendrá 10 intervalos.
- Un `Slider` con un rango de 0 a 100 tendrá 100 intervalos.

Estos intervalos están distribuidos de manera uniforme a lo largo de la pista del control deslizante, con su espaciamiento dependiendo de las dimensiones del `Slider`.

A continuación se muestra un ejemplo de cómo crear un `Slider` con un rango personalizado:

<ComponentDemo
path='/webforj/donationslider'
files={['src/main/java/com/webforj/samples/views/slider/DonationSliderView.java']}
height='200px'
/>

## Configuración de tics {#tick-configuration}

El componente `Slider` ofrece una configuración flexible de tics, permitiéndote personalizar cómo se muestran las marcas de tics y cómo la perilla del deslizante interactúa con ellas. Esto incluye ajustar el espaciado de los tics mayores y menores, mostrar/ocultar marcas de tics y habilitar el ajuste a las marcas de tics para una entrada precisa del usuario.

### Espaciado de tics mayores y menores {#major-and-minor-tick-spacing}

Puedes definir el espaciado para las marcas de tics mayores y menores, lo que determina con qué frecuencia aparecen en la pista del `Slider`:

- Los tics mayores son más grandes y a menudo etiquetados para representar valores clave.
- Los tics menores son más pequeños y aparecen entre los tics mayores para ofrecer intervalos más finos.

Establece el espaciado de los tics utilizando los siguientes métodos `setMajorTickSpacing()` y `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Tics mayores cada 10 unidades
slider.setMinorTickSpacing(2);  // Tics menores cada 2 unidades
```

### Mostrar u ocultar tics {#show-or-hide-ticks}

Puedes alternar la visibilidad de las marcas de tics utilizando el método `setTicksVisible()`. Por defecto, las marcas de tics están ocultas.

```java
slider.setTicksVisible(true); // Mostrar marcas de tics
slider.setTicksVisible(false); // Ocultar marcas de tics
```

### Ajuste {#snapping}

Para asegurar que la perilla del `Slider` se alinee con la marca de tics más cercana durante la interacción del usuario, habilita el ajuste utilizando el método `setSnapToTicks()`:

```java
slider.setSnapToTicks(true); // Habilitar ajuste
```

Aquí hay un ejemplo de un `Slider` completamente configurado que muestra la configuración de tics mayores y menores junto con la capacidad de ajuste para ajustes precisos:

<ComponentDemo
path='/webforj/slidertickspacing'
files={['src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java']}
height='350px'
/>

## Orientación e inversión {#orientation-and-inversion}

El componente `Slider` admite dos orientaciones: horizontal (predeterminada) y vertical. Puedes cambiar la orientación para adaptarla a tu diseño de UI y requerimientos de la aplicación.

Además de la orientación, el `Slider` también se puede invertir. Por defecto:

- Un `Slider` horizontal va de mínimo (izquierda) a máximo (derecha).
- Un `Slider` vertical va de mínimo (abajo) a máximo (arriba).

Cuando está invertido, esta dirección se invierte. Utiliza el método `setInverted(true)` para habilitar la inversión.

<ComponentDemo
path='/webforj/sliderorientation'
files={['src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java']}
height='440px'
/>

## Etiquetas {#labels}

El componente `Slider` admite etiquetas en las marcas de tics para ayudar a los usuarios a interpretar los valores con más facilidad. Puedes usar etiquetas numéricas predeterminadas o proporcionar etiquetas personalizadas, y puedes alternar su visibilidad según sea necesario.

### Etiquetas predeterminadas {#default-labels}

Por defecto, el slider puede mostrar etiquetas numéricas en las marcas de tics mayores. Estos valores son determinados por la configuración `setMajorTickSpacing()`. Para habilitar etiquetas predeterminadas, utiliza:

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

### Alternar visibilidad de etiquetas {#toggling-label-visibility}

Ya sea que estés usando etiquetas predeterminadas o personalizadas, puedes controlar su visibilidad con `setLabelsVisible(true)` o ocultarlas con `setLabelsVisible(false)`.

<ComponentDemo
path='/webforj/sliderlabels'
files={['src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java']}
height='150px'
/>

## Descripciones emergentes {#tooltips}

Las descripciones emergentes mejoran la usabilidad al mostrar el valor del `Slider` directamente arriba o debajo de la perilla, ayudando a los usuarios a realizar ajustes más precisos. Puedes configurar el comportamiento, la visibilidad y el formato de la descripción emergente según tus necesidades.

Para habilitar descripciones emergentes, utiliza el método `setTooltipVisible()`. Por defecto, las descripciones emergentes están deshabilitadas:

```java
slider.setTooltipVisible(true); // Habilitar descripciones emergentes
slider.setTooltipVisible(false); // Deshabilitar descripciones emergentes
```

Las descripciones emergentes también se pueden configurar para que aparezcan solo cuando el usuario interactúa con el `Slider`. Utiliza el método `setTooltipVisibleOnSlideOnly()` para habilitar este comportamiento. Esto es especialmente útil para reducir el desorden visual mientras aún se proporciona retroalimentación útil durante la interacción.

Aquí hay un ejemplo de un `Slider` completamente configurado con descripciones emergentes:


### Personalización de descripciones emergentes {#tooltip-customization}

Por defecto, el `Slider` muestra una descripción emergente con su valor actual. Si deseas personalizar este texto, utiliza el método `setTooltipText()`. Esto es útil cuando deseas que la descripción emergente muestre texto estático o descriptivo en lugar del valor en tiempo real.

También puedes usar una expresión de JavaScript para formatear la descripción emergente dinámicamente. Si tu expresión incluye la palabra clave `return`, se utiliza tal como está. Si no, se envuelve automáticamente con `return` y `;` para formar una función válida. Por ejemplo:

```java
// Muestra el valor seguido de un signo de dólar
slider.setTooltipText("return x + '$'");
```

O simplemente:

```java
// Interpretado como: return x + ' unidades';
slider.setTooltipText("x + ' unidades'");
```


## Estilo {#styling}

### Temas {#themes}

El `Slider` viene con 6 temas integrados para un estilo rápido sin la necesidad de CSS. El tema se admite mediante el uso de una clase enum incorporada.
A continuación se muestran deslizadores con cada uno de los temas admitidos aplicados:

<ComponentDemo
path='/webforj/sliderthemes'
files={['src/main/java/com/webforj/samples/views/slider/SliderThemesView.java']}
height='460px'
/>

<TableBuilder name="Slider" />
