---
title: Slider
sidebar_position: 101
_i18n_hash: 16e62c6e021597448e33a04ebfd6f201
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

El componente `Slider` ofrece a los usuarios una forma de seleccionar un valor numérico arrastrando un control deslizante a lo largo de una pista entre un límite mínimo y uno máximo. Los intervalos de paso, las marcas de graduación y las etiquetas se pueden configurar para guiar la selección.

<!-- INTRO_END -->

## Básicos {#basics}

El `Slider` está diseñado para funcionar de inmediato, sin necesidad de configuración adicional para operar de manera efectiva. Por defecto, abarca un rango de 0 a 100 con un valor inicial de 50, lo que lo hace ideal para una integración rápida en cualquier aplicación. Para casos de uso más específicos, el `Slider` se puede personalizar con propiedades como orientación, marcas de graduación, etiquetas y tooltips.

Aquí hay un ejemplo de un `Slider` que permite a los usuarios ajustar los niveles de volumen dentro de un rango predefinido:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## Valor del `Slider` {#slider-value}

El valor del `Slider` representa la posición actual del control en el deslizador y se define como un entero dentro del rango del `Slider`. Este valor se actualiza dinámicamente a medida que el usuario interactúa con el deslizador, siendo una propiedad esencial para rastrear la entrada del usuario.

:::tip Valor por defecto
Por defecto, el `Slider` comienza con un valor de 50, asumiendo el rango predeterminado de 0 a 100.
:::

### Establecer y obtener el valor {#setting-and-getting-the-value}

Puedes establecer el valor del `Slider` durante la inicialización o actualizarlo más tarde utilizando el método `setValue()`. Para recuperar el valor actual, utiliza el método `getValue()`.

```java
Slider slider = new Slider();  
slider.setValue(25); // Establece el deslizador en 25

Integer value = slider.getValue();  
System.out.println("Valor actual del deslizador: " + value);
```

## Valores mínimo y máximo {#minimum-and-maximum-values}

Los valores mínimo y máximo definen el rango allowable del `Slider`, determinando los límites dentro de los cuales puede moverse el control del `Slider`. Por defecto, el rango se establece de 0 a 100, pero puedes personalizar estos valores según tus necesidades.

Los intervalos en el `Slider` tienen un paso predeterminado de 1, lo que significa que el número de intervalos se determina por el rango. Por ejemplo:
- Un deslizador con un rango de 0 a 10 tendrá 10 intervalos.
- Un deslizador con un rango de 0 a 100 tendrá 100 intervalos.

Estos intervalos están distribuidos uniformemente a lo largo de la pista del deslizador, y su espacio depende de las dimensiones del `Slider`.

A continuación se muestra un ejemplo de crear un `Slider` con un rango personalizado:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Configuración de marcas de graduación {#tick-configuration}

El componente `Slider` ofrece una configuración flexible de marcas de graduación, permitiéndote personalizar cómo se muestran las marcas y cómo interactúa el control del deslizador con ellas. Esto incluye ajustar el espaciado de las marcas de graduación mayores y menores, mostrar/ocultar marcas de graduación y habilitar el ajuste a las marcas de graduación para una entrada de usuario precisa.

### Espaciado de marcas de graduación mayores y menores {#major-and-minor-tick-spacing}

Puedes definir el espaciado para las marcas de graduación mayores y menores, lo que determina con qué frecuencia aparecen en la pista del `Slider`:

- Las marcas de graduación mayores son más grandes y, a menudo, están etiquetadas para representar valores clave.
- Las marcas de graduación menores son más pequeñas y aparecen entre las marcas de graduación mayores para ofrecer intervalos más finos.

Establece el espaciado de las marcas de graduación utilizando los métodos `setMajorTickSpacing()` y `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Marcas mayores cada 10 unidades
slider.setMinorTickSpacing(2);  // Marcas menores cada 2 unidades
```

### Mostrar u ocultar marcas de graduación {#show-or-hide-ticks}

Puedes alternar la visibilidad de las marcas de graduación utilizando el método `setTicksVisible()`. Por defecto, las marcas de graduación están ocultas.

```java
slider.setTicksVisible(true); // Mostrar marcas de graduación
slider.setTicksVisible(false); // Ocultar marcas de graduación
```

### Ajuste {#snapping}

Para garantizar que el control del `Slider` se alinee con la marca de graduación más cercana durante la interacción del usuario, habilita el ajuste utilizando el método `setSnapToTicks()`:

```java
slider.setSnapToTicks(true); // Habilitar ajuste
```

Aquí hay un ejemplo de un `Slider` totalmente configurado que muestra la configuración de las marcas de graduación mayores y menores junto con la capacidad de ajuste para ajustes precisos:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Orientación e inversión {#orientation-and-inversion}

El componente `Slider` admite dos orientaciones: horizontal (predeterminada) y vertical. Puedes cambiar la orientación según el diseño de tu interfaz de usuario y los requisitos de la aplicación.

Además de la orientación, el `Slider` también puede invertirse. Por defecto:

- Un `Slider` horizontal va de mínimo (izquierda) a máximo (derecha).
- Un `Slider` vertical va de mínimo (abajo) a máximo (arriba).

Cuando se invierte, esta dirección se invierte. Usa el método `setInverted(true)` para habilitar la inversión.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## Etiquetas {#labels}

El componente `Slider` admite etiquetas en las marcas de graduación para ayudar a los usuarios a interpretar los valores más fácilmente. Puedes usar etiquetas numéricas predeterminadas o proporcionar etiquetas personalizadas, y puedes alternar su visibilidad según sea necesario.

### Etiquetas predeterminadas {#default-labels}

Por defecto, el deslizador puede mostrar etiquetas numéricas en las marcas de graduación mayores. Estos valores se determinan mediante la configuración de `setMajorTickSpacing()`. Para habilitar etiquetas predeterminadas, utiliza:

```java
slider.setLabelsVisible(true);
```

### Etiquetas personalizadas {#custom-labels}

Puedes reemplazar las etiquetas numéricas predeterminadas con texto personalizado mediante el método `setLabels()`. Esto es útil cuando deseas mostrar valores más significativos (por ejemplo, temperatura, moneda o categorías).

```java
Map<Integer, String> customLabels = Map.of(
  0, "Frío",
  30, "Frío",
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
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## Tooltips {#tooltips}

Los tooltips mejoran la usabilidad al mostrar el valor del `Slider` directamente encima o debajo del control, ayudando a los usuarios a realizar ajustes más precisos. Puedes configurar el comportamiento, la visibilidad y el formato del tooltip según tus necesidades.

Para habilitar tooltips, utiliza el método `setTooltipVisible()`. Por defecto, los tooltips están deshabilitados:

```java
slider.setTooltipVisible(true); // Habilitar tooltips
slider.setTooltipVisible(false); // Deshabilitar tooltips
```

Los tooltips también se pueden configurar para que aparezcan solo cuando el usuario interactúa con el `Slider`. Usa el método `setTooltipVisibleOnSlideOnly()` para habilitar este comportamiento. Esto es especialmente útil para reducir el desorden visual mientras sigues proporcionando comentarios útiles durante la interacción.

Aquí hay un ejemplo de un `Slider` totalmente configurado con tooltips:


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


## Estilo {#styling}

### Temas {#themes}

El `Slider` viene con 6 temas integrados para una estilización rápida sin el uso de CSS. La tematización se admite mediante el uso de una clase enum integrada.
A continuación se muestran deslizadores con cada uno de los temas admitidos aplicados:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
