---
title: Card
sidebar_position: 17
sidebar_class_name: new-content
description: >-
  Group related content and actions with the Card component, including slotted
  regions. orientation, elevation, dividers, and click handling.
_i18n_hash: 08b0239bc5bbeb0b14f3b03dda7b8b17
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-card" />
<DocChip chip='since' label='26.02' />
<JavadocLink type="card" location="com/webforj/component/card/Card" top='true'/>

El componente `Card` proporciona una superficie para agrupar contenido y acciones relacionadas en un solo elemento. Soporta regiones con slots para una figura, un encabezado, un cuerpo y un pie, junto con configuraciones de orientación, elevación, separador y densidad que controlan cómo se presenta la tarjeta.

<!-- INTRO_END -->

## Creando una `Card` {#creating-a-card}

Crea una `Card` pasando contenido a su constructor, lo que coloca ese contenido en el cuerpo de la tarjeta. El cuerpo también puede ser llenado después de la creación con `add()` o `addToBody()`, que hacen lo mismo.

```java
Card card = new Card(new Paragraph("Las ventas aumentaron en todas las regiones."));

//Equivalente
Card card = new Card();
card.addToBody(new Paragraph("Las ventas aumentaron en todas las regiones."));
```

Una `Card` vacía renderiza su marco y nada más.

## Regiones de la tarjeta {#card-regions}

Cada región, aparte del cuerpo, se llena a través de su propio slot, y una región cuyo slot no tiene contenido no se renderiza. Una `Card` sin un pie se cierra después del cuerpo, y una `Card` con solo un cuerpo es un bloque de contenido enmarcado.

- `addToFigure()` sostiene la ilustración de la Card, como una imagen, un video o un gráfico. Su posición depende de la orientación de la Card.
- `addToIcon()` establece el visual principal en la fila del encabezado y acepta cualquier componente, incluyendo un `Icon` o un `Avatar`.
- `addToTitle()` establece el encabezado en la fila del encabezado.
- `addToCaption()` añade una línea secundaria bajo el título, útil para una fecha, un autor o un estado.
- `addToHeaderActions()` llena el final de la fila del encabezado, generalmente con un `Button` o un menú.
- `addToFooter()` cierra la `Card`, generalmente con acciones o metadatos.

```java
Card card = new Card(new Paragraph("Las ventas aumentaron en todas las regiones."));
card.addToFigure(new Img("cover.png", "Portada del informe"))
    .addToIcon(TablerIcon.create("chart-bar"))
    .addToTitle(new H3("Informe mensual"))
    .addToCaption(new Paragraph("Julio 2026"))
    .addToHeaderActions(new Button("Compartir"))
    .addToFooter(new Button("Leer más"));
```

:::info Título y nombre accesible
Una `Card` se anuncia como una región, y el título se convierte en su nombre accesible. Usa un elemento de encabezado como `H3` allí para que los usuarios de lectores de pantalla puedan encontrar la `Card` a través de la estructura de encabezados de la página.
:::

<ComponentDemo
path='/webforj/cardregions'
files={[
  'src/main/java/com/webforj/samples/views/card/CardRegionsView.java',
  'src/main/frontend/css/card/cardRegions.css',
]}
height='700px'
/>

## Orientación {#orientation}

La orientación controla dónde se sitúa la figura en relación con las otras regiones, y se establece con `setOrientation()`.

Las tarjetas son verticales por defecto, por lo que apilan la figura encima del encabezado, cuerpo y pie. Esto se adapta a las tarjetas organizadas en una cuadrícula, donde cada una ocupa una columna estrecha. Pasar `Card.Orientation.HORIZONTAL` a `setOrientation()` hace que la tarjeta sea horizontal, colocando la figura al lado de esas regiones.

```java
card.setOrientation(Card.Orientation.HORIZONTAL);
```

<ComponentDemo
path='/webforj/cardorientation'
files={['src/main/java/com/webforj/samples/views/card/CardOrientationView.java']}
height='500px'
/>

Debido a que la configuración mueve la figura y nada más, una `Card` sin figura se ve igual en ambas orientaciones.

## Elevación y borde {#elevation-and-border}

Dos configuraciones determinan cuán lejos se separa la `Card` de la página detrás de ella. `setShadow()` aplica un valor de la escala de sombras, que va desde `NONE` hasta `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, y `XLARGE` hasta `XXLARGE`. `setBorderless()` controla si la `Card` dibuja su borde. Los valores predeterminados son `Shadow.XSMALL` con el borde dibujado.

Las configuraciones son independientes, por lo que cualquier sombra puede ser combinada con o sin el borde.

<ComponentDemo
path='/webforj/cardappearance'
files={[
  'src/main/java/com/webforj/samples/views/card/CardAppearanceView.java']}
height='300px'
/>

## Separadores y expansión {#dividers-and-expanse}

Mientras que las configuraciones de elevación y borde controlan cómo se sienta la `Card` contra la página, los separadores y la expansión controlan las regiones legibles dentro de la tarjeta misma.

`setDivided(true)` dibuja un separador después del encabezado y antes del pie, lo que ayuda cuando las regiones contienen contenido denso. Los separadores están apagados por defecto. Un separador para una región que no tiene contenido no se dibuja, por lo que una tarjeta dividida sin pie muestra un separador, bajo el encabezado. Los separadores tienen más peso en tarjetas planas, donde no hay un marco presente para hacer ese trabajo.

`setExpanse()` controla la densidad, impulsando el relleno, los huecos entre regiones y el tamaño del título y la leyenda. `Card` utiliza el enum compartido `Expanse`, que ofrece `NONE`, `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, y `XLARGE`, siendo `MEDIUM` el valor predeterminado. Expansiones más pequeñas son adecuadas para mosaicos de tablero y barras laterales, donde varias tarjetas comparten la pantalla.

El siguiente ejemplo muestra dos componentes `Card` con separadores. Una `Card` está usando `Expanse.LARGE`, mientras que la otra está usando `Expanse.SMALL`:

<ComponentDemo
path='/webforj/carddensity'
files={[
  'src/main/java/com/webforj/samples/views/card/CardDensityView.java',
  'src/main/frontend/css/card/cardDensity.css',
]}
height='400px'
/>

## Eventos de clic {#click-events}

El componente `Card` implementa `HasElementClickListener`, por lo que un listener registrado con `onClick()` o `addClickListener()` recibe un `ElementClickEvent`. Esto hace que toda la superficie sea un solo objetivo.

```java
card.onClick(event -> Router.getCurrent().navigate(new Location("/reports/july")));
```

:::warning Clics desde dentro de la `Card`
Los clics en componentes dentro de la `Card` también alcanzan la `Card`, por lo que una `Card` con su propio listener se activa cuando el usuario presiona un `Button` en las acciones del encabezado o el pie. Añade un listener a la `Card` cuando la `Card` tiene una acción clara, y reserva los botones dentro de ella para acciones que la `Card` misma no realiza.
:::

## Estilizando {#styling}

<TableBuilder name="Card" />
