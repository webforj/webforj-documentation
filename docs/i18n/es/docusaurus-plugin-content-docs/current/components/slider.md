---
title: Slider
sidebar_position: 101
_i18n_hash: 045c80d3d54048157d805ee64213f210
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

El componente `Slider` en webforJ proporciona un control interactivo que permite a los usuarios seleccionar un valor dentro de un rango específico moviendo un botón. Esta característica es particularmente útil para aplicaciones que requieren entradas precisas o intuitivas, como seleccionar volúmenes, porcentajes u otros valores ajustables.

## Básicos {#basics}

El `Slider` está diseñado para funcionar directamente, sin necesidad de configuración adicional para funcionar de manera efectiva. Por defecto, abarca un rango de 0 a 100 con un valor inicial de 50, lo que lo hace ideal para una rápida integración en cualquier aplicación. Para casos de uso más específicos, el `Slider` se puede personalizar con propiedades como orientación, marcas de referencia, etiquetas y descripciones emergentes.

Aquí hay un ejemplo de un `Slider` que permite a los usuarios ajustar los niveles de volumen dentro de un rango predefinido:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## Valor del `Slider` {#slider-value}

El valor del `Slider` representa la posición actual del botón en el deslizador y se define como un entero dentro del rango del `Slider`. Este valor se actualiza dinámicamente a medida que el usuario interactúa con el deslizador, convirtiéndolo en una propiedad esencial para rastrear la entrada del usuario.

:::tip Valor por defecto
Por defecto, el `Slider` comienza con un valor de 50, asumiendo el rango por defecto de 0 a 100.
:::

### Establecer y obtener el valor {#setting-and-getting-the-value}

Puedes establecer el valor del `Slider` durante la inicialización o actualizarlo más tarde usando el método `setValue()`. Para recuperar el valor actual, usa el método `getValue()`.

```java
Slider slider = new Slider();  
slider.setValue(25); // Establece el deslizador en 25

Integer value = slider.getValue();  
System.out.println("Valor actual del Slider: " + value);
```

## Valores mínimos y máximos {#minimum-and-maximum-values}

Los valores mínimo y máximo definen el rango permitido del `Slider`, determinando los límites dentro de los cuales el botón del `Slider` puede moverse. Por defecto, el rango está configurado de 0 a 100, pero puedes personalizar estos valores según tus necesidades.

Los intervalos en el `Slider` tienen un paso por defecto de 1, lo que significa que el número de intervalos está determinado por el rango. Por ejemplo:
- Un Slider con un rango de 0 a 10 tendrá 10 intervalos.
- Un Slider con un rango de 0 a 100 tendrá 100 intervalos.

Estos intervalos están distribuídos uniformemente a lo largo de la pista del deslizador, con su espaciado dependiendo de las dimensiones del `Slider`.

A continuación se muestra un ejemplo de la creación de un `Slider` con un rango personalizado:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Configuración de marcas de referencia {#tick-configuration}

El componente `Slider` ofrece una configuración de marcas de referencia flexible, lo que permite personalizar cómo se muestran las marcas de referencia y cómo interactúa el botón del deslizador con ellas. Esto incluye ajustar el espaciado de las marcas de referencia principales y menores, mostrar/ocultar marcas de referencia y habilitar el ajuste a las marcas de referencia para una entrada precisa del usuario.

### Espaciado de marcas de referencia principales y menores {#major-and-minor-tick-spacing}

Puedes definir el espaciado para las marcas de referencia principales y menores, lo que determina con qué frecuencia aparecen en la pista del `Slider`:

- Las marcas de referencia principales son más grandes y a menudo se etiquetan para representar valores clave.
- Las marcas de referencia menores son más pequeñas y aparecen entre las marcas de referencia principales para ofrecer intervalos más finos.

Establece el espaciado de las marcas usando los siguientes métodos `setMajorTickSpacing()` y `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Marcas principales cada 10 unidades
slider.setMinorTickSpacing(2);  // Marcas menores cada 2 unidades
```

### Mostrar u ocultar marcas de referencia {#show-or-hide-ticks}

Puedes alternar la visibilidad de las marcas de referencia usando el método `setTicksVisible()`. Por defecto, las marcas de referencia están ocultas.

```java
slider.setTicksVisible(true); // Mostrar marcas de referencia
slider.setTicksVisible(false); // Ocultar marcas de referencia
```

### Ajuste {#snapping}

Para garantizar que el botón del `Slider` se alinee con la marca de referencia más cercana durante la interacción del usuario, habilita el ajuste usando el método `setSnapToTicks()`:

```java
slider.setSnapToTicks(true); // Habilitar ajuste
```

Aquí hay un ejemplo de un `Slider` completamente configurado que muestra la configuración de marcas principales y menores junto con la capacidad de ajuste para ajustes precisos:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Orientación e inversión {#orientation-and-inversion}

El componente `Slider` admite dos orientaciones: horizontal (por defecto) y vertical. Puedes cambiar la orientación para adaptarse a tu diseño de interfaz de usuario y requisitos de la aplicación.

Además de la orientación, el `Slider` también se puede invertir. Por defecto:

- Un `Slider` horizontal va de mínimo (izquierda) a máximo (derecha).
- Un `Slider` vertical va de mínimo (abajo) a máximo (arriba).

Cuando se invierte, esta dirección se invierte. Usa el método `setInverted(true)` para habilitar la inversión.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## Etiquetas {#labels}

El componente `Slider` admite etiquetas en las marcas de referencia para ayudar a los usuarios a interpretar los valores más fácilmente. Puedes usar etiquetas numéricas por defecto o proporcionar etiquetas personalizadas, y puedes alternar su visibilidad según sea necesario.

### Etiquetas por defecto {#default-labels}

Por defecto, el deslizador puede mostrar etiquetas numéricas en las marcas de referencia principales. Estos valores están determinados por la configuración de `setMajorTickSpacing()`. Para habilitar etiquetas por defecto, usa:

```java
slider.setLabelsVisible(true);
```

### Etiquetas personalizadas {#custom-labels}

Puedes reemplazar las etiquetas numéricas por defecto con texto personalizado usando el método `setLabels()`. Esto es útil cuando deseas mostrar valores más significativos (por ejemplo, temperatura, moneda o categorías).

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

### Alternar visibilidad de etiquetas {#toggling-label-visibility}

Ya sea que estés usando etiquetas por defecto o personalizadas, puedes controlar su visibilidad con `setLabelsVisible(true)` o ocultarlas con `setLabelsVisible(false)`.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## Descripciones emergentes {#tooltips}

Las descripciones emergentes mejoran la usabilidad al mostrar el valor del `Slider` directamente encima o debajo del botón, ayudando a los usuarios a hacer ajustes más precisos. Puedes configurar el comportamiento, la visibilidad y el formato de la descripción emergente según tus necesidades.

Para habilitar las descripciones emergentes, usa el método `setTooltipVisible()`. Por defecto, las descripciones emergentes están deshabilitadas:

```java
slider.setTooltipVisible(true); // Habilitar descripciones emergentes
slider.setTooltipVisible(false); // Deshabilitar descripciones emergentes
```

Las descripciones emergentes también se pueden configurar para aparecer solo cuando el usuario interactúa con el `Slider`. Usa el método `setTooltipVisibleOnSlideOnly()` para habilitar este comportamiento. Esto es especialmente útil para reducir el desorden visual mientras se proporciona retroalimentación útil durante la interacción.

Aquí hay un ejemplo de un `Slider` completamente configurado con descripciones emergentes:

### Personalización de descripciones emergentes {#tooltip-customization}

Por defecto, el `Slider` muestra una descripción emergente con su valor actual. Si deseas personalizar este texto, usa el método `setTooltipText()`. Esto es útil cuando deseas que la descripción emergente muestre texto estático o descriptivo en lugar del valor en vivo.

También puedes usar una expresión de JavaScript para formatear la descripción emergente dinámicamente. Si tu expresión incluye la palabra clave `return`, se usa tal cual. Si no, se envuelve automáticamente con `return` y `;` para formar una función válida. Por ejemplo:

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

El `Slider` viene con 6 temas integrados para rápido estilo sin el uso de CSS. El tema es compatible mediante el uso de una clase enum incorporada.
A continuación se muestran deslizadores con cada uno de los temas admitidos aplicados:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
