---
title: Slider
sidebar_position: 101
_i18n_hash: 47e9254faad15097b580eb4099968fbc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

El componente `Slider` en webforJ proporciona un control interactivo que permite a los usuarios seleccionar un valor dentro de un rango específico al mover una perilla. Esta característica es particularmente útil para aplicaciones que requieren entradas precisas o intuitivas, como seleccionar volúmenes, porcentajes u otros valores ajustables.

## Básicos {#basics}

El `Slider` está diseñado para funcionar inmediatamente, sin necesidad de configuración adicional para funcionar de manera efectiva. Por defecto, abarca un rango de 0 a 100 con un valor inicial de 50, lo que lo hace ideal para una integración rápida en cualquier aplicación. Para casos de uso más específicos, el `Slider` se puede personalizar con propiedades como orientación, marcas de ticks, etiquetas y tooltips.

Aquí hay un ejemplo de un `Slider` que permite a los usuarios ajustar los niveles de volumen dentro de un rango predefinido:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## Valor del `Slider` {#slider-value}

El valor del `Slider` representa la posición actual de la perilla en el slider y se define como un entero dentro del rango del `Slider`. Este valor se actualiza dinámicamente a medida que el usuario interactúa con el slider, lo que lo convierte en una propiedad esencial para rastrear la entrada del usuario.

:::tip Valor predeterminado
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

Los valores mínimo y máximo definen el rango permitido del `Slider`, determinando los límites dentro de los cuales puede moverse la perilla del `Slider`. Por defecto, el rango se establece de 0 a 100, pero puedes personalizar estos valores según tus necesidades.

Los intervalos en el `Slider` tienen un paso predeterminado de 1, lo que significa que el número de intervalos se determina por el rango. Por ejemplo:
- Un Slider con un rango de 0 a 10 tendrá 10 intervalos.
- Un Slider con un rango de 0 a 100 tendrá 100 intervalos.

Estos intervalos se distribuyen uniformemente a lo largo de la pista del slider, y su espaciado depende de las dimensiones del `Slider`.

A continuación, se muestra un ejemplo de creación de un `Slider` con un rango personalizado:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Configuración de ticks {#tick-configuration}

El componente `Slider` ofrece una configuración de ticks flexible, lo que permite personalizar cómo se muestran las marcas de ticks y cómo interactúa la perilla del slider con ellas. Esto incluye ajustar el espaciado de ticks mayores y menores, mostrar/ocultar marcas de ticks y habilitar la alineación con marcas de ticks para una entrada precisa del usuario.

### Espaciado de ticks mayores y menores {#major-and-minor-tick-spacing}

Puedes definir el espaciado para las marcas de ticks mayores y menores, que determina con qué frecuencia aparecen en la pista del `Slider`:

- Los ticks mayores son más grandes y a menudo están etiquetados para representar valores clave.
- Los ticks menores son más pequeños y aparecen entre los ticks mayores para ofrecer intervalos más finos.

Establece el espaciado de ticks utilizando los métodos `setMajorTickSpacing()` y `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Ticks mayores cada 10 unidades
slider.setMinorTickSpacing(2);  // Ticks menores cada 2 unidades
```

### Mostrar u ocultar ticks {#show-or-hide-ticks}

Puedes alternar la visibilidad de las marcas de ticks utilizando el método `setTicksVisible()`. Por defecto, las marcas de ticks están ocultas.

```java
slider.setTicksVisible(true); // Mostrar marcas de ticks
slider.setTicksVisible(false); // Ocultar marcas de ticks
```

### Alineación {#snapping}

Para asegurar que la perilla del `Slider` se alinee con la marca de tick más cercana durante la interacción del usuario, habilita la alineación utilizando el método `setSnapToTicks()`:

```java
slider.setSnapToTicks(true); // Habilitar la alineación
```

Aquí hay un ejemplo de un `Slider` completamente configurado que muestra configuraciones de ticks mayores y menores junto con la capacidad de alineación para ajustes precisos:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Orientación e inversión {#orientation-and-inversion}

El componente `Slider` admite dos orientaciones: horizontal (predeterminada) y vertical. Puedes cambiar la orientación para adaptarla a la disposición de tu interfaz de usuario y a los requisitos de la aplicación.

Además de la orientación, el `Slider` también se puede invertir. Por defecto:

- Un `Slider` horizontal va de mínimo (izquierda) a máximo (derecha).
- Un `Slider` vertical va de mínimo (inferior) a máximo (superior).

Cuando se invierte, esta dirección se invierte. Usa el método `setInverted(true)` para habilitar la inversión.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## Etiquetas {#labels}

El componente `Slider` admite etiquetas en las marcas de ticks para ayudar a los usuarios a interpretar los valores más fácilmente. Puedes usar etiquetas numéricas predeterminadas o proporcionar etiquetas personalizadas, y puedes alternar su visibilidad según sea necesario.

### Etiquetas predeterminadas {#default-labels}

Por defecto, el slider puede mostrar etiquetas numéricas en las marcas de ticks mayores. Estos valores se determinan mediante la configuración de `setMajorTickSpacing()`. Para habilitar etiquetas predeterminadas, usa:

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
height = '150px'
/>

## Tooltips {#tooltips}

Los tooltips mejoran la usabilidad al mostrar el valor del `Slider` directamente encima o debajo de la perilla, ayudando a los usuarios a hacer ajustes más precisos. Puedes configurar el comportamiento, la visibilidad y el formato del tooltip según tus necesidades.

Para habilitar los tooltips, utiliza el método `setTooltipVisible()`. Por defecto, los tooltips están deshabilitados:

```java
slider.setTooltipVisible(true); // Habilitar tooltips
slider.setTooltipVisible(false); // Deshabilitar tooltips
```

Los tooltips también se pueden configurar para que aparezcan solo cuando el usuario interactúa con el `Slider`. Usa el método `setTooltipVisibleOnSlideOnly()` para habilitar este comportamiento. Esto es especialmente útil para reducir el desorden visual mientras se sigue proporcionando retroalimentación útil durante la interacción.

Aquí hay un ejemplo de un `Slider` completamente configurado con tooltips:


### Personalización del tooltip {#tooltip-customization}

Por defecto, el `Slider` muestra un tooltip con su valor actual. Si deseas personalizar este texto, utiliza el método `setTooltipText()`. Esto es útil cuando quieres que el tooltip muestre texto estático o descriptivo en lugar del valor en vivo.

También puedes usar una expresión de JavaScript para formatear el tooltip dinámicamente. Si tu expresión incluye la palabra clave `return`, se utiliza tal como está. Si no, se envuelve automáticamente con `return` y `;` para formar una función válida. Por ejemplo:

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

El `Slider` viene con 6 temas integrados para una estilización rápida sin el uso de CSS. El uso de temas está soportado por una clase enum incorporada.
A continuación se muestran sliders con cada uno de los temas soportados aplicados:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
