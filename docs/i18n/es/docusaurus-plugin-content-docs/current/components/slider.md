---
title: Slider
sidebar_position: 101
_i18n_hash: 56140362edd92adde8d6114a8e6652c9
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

El componente `Slider` ofrece a los usuarios una forma de seleccionar un valor numérico arrastrando una perilla a lo largo de una pista entre un límite mínimo y máximo. Los intervalos de paso, marcas de tachado y etiquetas se pueden configurar para guiar la selección.

<!-- INTRO_END -->

## Básicos {#basics}

El `Slider` está diseñado para funcionar directamente, sin necesidad de una configuración adicional para funcionar eficazmente. Por defecto, abarca un rango de 0 a 100 con un valor inicial de 50, lo que lo hace ideal para una rápida integración en cualquier aplicación. Para casos de uso más específicos, el `Slider` se puede personalizar con propiedades como orientación, marcas de tachado, etiquetas y descripciones emergentes.

A continuación, se muestra un ejemplo de un `Slider` que permite a los usuarios ajustar los niveles de volumen dentro de un rango predefinido:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height='100px'
/>

## Valor del `Slider` {#slider-value}

El valor del `Slider` representa la posición actual de la perilla sobre el control deslizante y se define como un entero dentro del rango del `Slider`. Este valor se actualiza dinámicamente a medida que el usuario interactúa con el deslizador, convirtiéndolo en una propiedad esencial para rastrear la entrada del usuario.

:::tip Valor predeterminado
Por defecto, el `Slider` comienza con un valor de 50, asumiendo el rango predeterminado de 0 a 100.
:::

### Establecer y obtener el valor {#setting-and-getting-the-value}

Puedes establecer el valor del `Slider` durante la inicialización o actualizarlo más tarde utilizando el método `setValue()`. Para recuperar el valor actual, usa el método `getValue()`.

```java
Slider slider = new Slider();  
slider.setValue(25); // Establece el slider en 25

Integer value = slider.getValue();  
System.out.println("Valor Actual del Slider: " + value);
```

## Valores mínimo y máximo {#minimum-and-maximum-values}

Los valores mínimo y máximo definen el rango permitido del `Slider`, determinando los límites dentro de los cuales puede moverse la perilla del `Slider`. Por defecto, el rango se establece de 0 a 100, pero puedes personalizar estos valores según tus necesidades.

Los intervalos en el `Slider` tienen un paso predeterminado de 1, lo que significa que el número de intervalos está determinado por el rango. Por ejemplo:
- Un Slider con un rango de 0 a 10 tendrá 10 intervalos.
- Un Slider con un rango de 0 a 100 tendrá 100 intervalos.

Estos intervalos se distribuyen uniformemente a lo largo de la pista del control deslizante, y su espaciado depende de las dimensiones del `Slider`.

A continuación, se muestra un ejemplo de creación de un `Slider` con un rango personalizado:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height='200px'
/>

## Configuración de marcas de tachado {#tick-configuration}

El componente `Slider` ofrece una configuración de marcas de tachado flexible, lo que te permite personalizar cómo se muestran las marcas de tachado y cómo interactúa la perilla del deslizador con ellas. Esto incluye ajustar el espaciado de marcas de tachado mayores y menores, mostrar/ocultar marcas de tachado y habilitar el ajuste a las marcas de tachado para una entrada de usuario precisa.

### Espaciado de marcas de tachado mayores y menores {#major-and-minor-tick-spacing}

Puedes definir el espaciado para las marcas de tachado mayores y menores, lo que determina con qué frecuencia aparecen en la pista del `Slider`:

- Las marcas de tachado mayores son más grandes y a menudo etiquetadas para representar valores clave.
- Las marcas de tachado menores son más pequeñas y aparecen entre las marcas de tachado mayores para ofrecer intervalos más finos.

Establece el espaciado de las marcas de tachado utilizando los siguientes métodos `setMajorTickSpacing()` y `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Marcas de tachado mayores cada 10 unidades
slider.setMinorTickSpacing(2);  // Marcas de tachado menores cada 2 unidades
```

### Mostrar u ocultar marcas de tachado {#show-or-hide-ticks}

Puedes alternar la visibilidad de las marcas de tachado utilizando el método `setTicksVisible()`. Por defecto, las marcas de tachado están ocultas.

```java
slider.setTicksVisible(true); // Mostrar marcas de tachado
slider.setTicksVisible(false); // Ocultar marcas de tachado
```

### Ajuste {#snapping}

Para garantizar que la perilla del `Slider` se alinee con la marca de tachado más cercana durante la interacción del usuario, habilita el ajuste utilizando el método `setSnapToTicks()`:

```java
slider.setSnapToTicks(true); // Habilitar ajuste
```

A continuación, se muestra un ejemplo de un `Slider` completamente configurado que muestra configuraciones de marcas de tachado mayores y menores junto con la capacidad de ajuste para ajustes precisos:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height='350px'
/>

## Orientación e inversión {#orientation-and-inversion}

El componente `Slider` admite dos orientaciones: horizontal (predeterminado) y vertical. Puedes cambiar la orientación para adaptarla a tu diseño de interfaz de usuario y requisitos de la aplicación.

Además de la orientación, el `Slider` también se puede invertir. Por defecto:

- Un `Slider` horizontal va de mínimo (izquierda) a máximo (derecha).
- Un `Slider` vertical va de mínimo (abajo) a máximo (arriba).

Cuando se invierte, esta dirección se invierte. Usa el método `setInverted(true)` para habilitar la inversión.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height='420px'
/>

## Etiquetas {#labels}

El componente `Slider` admite etiquetas en las marcas de tachado para ayudar a los usuarios a interpretar los valores más fácilmente. Puedes utilizar etiquetas numéricas predeterminadas o proporcionar etiquetas personalizadas, y puedes alternar su visibilidad según sea necesario.

### Etiquetas predeterminadas {#default-labels}

Por defecto, el deslizador puede mostrar etiquetas numéricas en las marcas de tachado mayores. Estos valores son determinados por la configuración de `setMajorTickSpacing()`. Para habilitar etiquetas predeterminadas, usa:

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

Ya sea que estés utilizando etiquetas predeterminadas o personalizadas, puedes controlar su visibilidad con `setLabelsVisible(true)` o ocultarlas con `setLabelsVisible(false)`.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height='150px'
/>

## Descripciones emergentes {#tooltips}

Las descripciones emergentes mejoran la usabilidad al mostrar el valor del `Slider` directamente encima o debajo de la perilla, ayudando a los usuarios a hacer ajustes más precisos. Puedes configurar el comportamiento, la visibilidad y el formato de la descripción emergente según tus necesidades.

Para habilitar descripciones emergentes, utiliza el método `setTooltipVisible()`. Por defecto, las descripciones emergentes están deshabilitadas:

```java
slider.setTooltipVisible(true); // Habilitar descripciones emergentes
slider.setTooltipVisible(false); // Deshabilitar descripciones emergentes
```

Las descripciones emergentes también se pueden configurar para que aparezcan solo cuando el usuario interactúa con el `Slider`. Usa el método `setTooltipVisibleOnSlideOnly()` para habilitar este comportamiento. Esto es especialmente útil para reducir el desorden visual mientras se proporciona retroalimentación útil durante la interacción.

A continuación, se muestra un ejemplo de un `Slider` completamente configurado con descripciones emergentes:


### Personalización de descripciones emergentes {#tooltip-customization}

Por defecto, el `Slider` muestra una descripción emergente con su valor actual. Si deseas personalizar este texto, utiliza el método `setTooltipText()`. Esto es útil cuando deseas que la descripción emergente muestre texto estático o descriptivo en lugar del valor en vivo.

También puedes utilizar una expresión de JavaScript para formatear la descripción emergente dinámicamente. Si tu expresión incluye la palabra clave `return`, se usa tal cual. Si no, se envuelve automáticamente con `return` y `;` para formar una función válida. Por ejemplo:

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

El `Slider` viene con 6 temas integrados para una rápida estilización sin el uso de CSS. La tematización es compatible mediante el uso de una clase enum incorporada. 
A continuación se muestran deslizadores con cada uno de los Temas admitidos aplicados:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height='460px'
/>

<TableBuilder name="Slider" />
