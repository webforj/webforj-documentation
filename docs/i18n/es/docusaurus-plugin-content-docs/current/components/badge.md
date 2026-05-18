---
title: Badge
sidebar_position: 8
_i18n_hash: 1f599f2c8a833e09f2d945ed0ead5447
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

Un `Badge` es una etiqueta compacta y visualmente distinta utilizada para transmitir estado, conteos o breves piezas de información contextual. Ya sea que necesites marcar el conteo de notificaciones, señalar un elemento como "Nuevo" o llamar la atención sobre una advertencia, los badges te ofrecen una forma ligera de presentar esa información directamente en la interfaz de usuario.

<!-- INTRO_END -->

:::tip Uso de un `Badge`
Los badges funcionan bien para conteos de notificaciones, etiquetas de estado y metadatos breves como etiquetas de versión o estados de lanzamiento. Mantén el texto del badge en una o dos palabras para que la etiqueta se lea de un vistazo.
:::

## Creando un badge {#creating-a-badge}

El `Badge` más simple toma una cadena de texto. También puedes pasar un `BadgeTheme` directamente en el constructor para establecer el estilo visual de inmediato. El constructor sin argumentos está disponible cuando necesitas construir un badge dinámicamente y configurarlo después de la creación.

```java
Badge badge = new Badge("Nuevo");

// Con un tema
Badge primary = new Badge("Destacado", BadgeTheme.SUCCESS);

// Construido dinámicamente
Badge status = new Badge();
status.setLabel("Pendiente");
status.setTheme(BadgeTheme.WARNING);
```

## Etiqueta {#label}

Puedes establecer o actualizar el contenido de texto de un badge en cualquier momento con `setLabel()`. El método `setText()` es un alias para la misma operación; utiliza el que se lea más naturalmente en el contexto. Ambos tienen getters correspondientes, `getLabel()` y `getText()`, si necesitas leer el valor actual de nuevo.

```java
Badge badge = new Badge();
badge.setLabel("Actualizado");

// Equivalente
badge.setText("Actualizado");

// Leer el valor
String current = badge.getLabel();
```

## Iconos {#icons}

A veces, un enfoque más visual es útil al transmitir información con un `Badge`. Los badges admiten contenido de icono alojado. Pasa un icono junto con el texto utilizando el constructor `Badge(String, Component...)`, o pasa un icono solo para crear un badge solo de icono. Cuando se combina con texto, el icono se renderiza a la izquierda de la etiqueta.

Los badges solo de icono funcionan especialmente bien para indicadores de estado compactos en diseños densos donde una palabra corta se sentiría desordenada. Combinar un icono con texto es un buen punto intermedio cuando el icono solo podría ser ambiguo. Un símbolo de estado es ampliamente entendido por sí solo, pero agregar una etiqueta de texto corta elimina la conjetura para los usuarios primerizos. Puedes pasar múltiples componentes al constructor si necesitas componer un prefijo más rico, aunque en la práctica un solo icono es el patrón más común.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons'
files={['src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java']}
height='345px'
/>
<!-- vale on -->

```java
// Icono con texto
Badge done = new Badge("Hecho", FeatherIcon.CHECK_CIRCLE.create());
done.setTheme(BadgeTheme.SUCCESS);

// Solo icono
Badge check = new Badge(FeatherIcon.CHECK.create());
check.setTheme(BadgeTheme.SUCCESS);
```

## Uso en otros componentes {#usage-in-other-components}

### Botones {#buttons}

Adjunta un `Badge` a un `Button` usando `setBadge()`. El badge aparece en la esquina superior derecha del botón, superponiéndose al borde del botón. Este es un patrón común para conteos de notificaciones en acciones de la barra de herramientas o botones de icono. Dado que el badge es un componente independiente, es completamente independiente del propio tema y tamaño del botón. Puedes combinar un botón primario con un badge de peligro, o un botón espectral con un badge de éxito, y cada lado de la combinación se estiliza sin conflictos. Actualizar el conteo más tarde es tan simple como llamar a `badge.setLabel()` con un nuevo valor; el botón no necesita ser tocado.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons'
files={['src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java']}
height='290px'
/>
<!-- vale on -->

### Panel con pestañas {#tabbed-pane}

Agrega un `Badge` como sufijo en un `Tab` usando `setSuffixComponent()`. Este es un ajuste natural para conteos al estilo de bandeja de entrada o indicadores de estado en cada pestaña. Es el tipo de patrón que ves en clientes de correo electrónico o gestores de tareas donde es importante señalar la actividad en cada sección de un vistazo. El badge se sitúa en el borde posterior de la etiqueta de la pestaña, después de cualquier contenido de prefijo, y permanece visible sin importar qué pestaña esté activa en ese momento. Esta persistencia es intencional: ocultar el badge en pestañas inactivas dificultaría saber qué secciones requieren atención sin cambiar a cada una.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane'
files={['src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java']}
height='360px'
/>
<!-- vale on -->

## Estilización {#styling}

Los badges admiten varias dimensiones de estilización: colores de tema para transmitir significado, una escala de extensión para controlar el tamaño, y propiedades CSS para personalización detallada.

### Temas {#themes}

Como con muchos componentes en webforJ, el `Badge` viene en catorce temas: siete variantes llenas y siete delineadas.

Los temas llenos utilizan un fondo sólido y calculan automáticamente un color de texto que cumple con los requisitos de contraste. Las variantes delineadas, en cambio, utilizan un fondo teñido con un borde coloreado, lo que las convierte en una opción más sutil cuando deseas que el badge complemente el contenido circundante en lugar de dominarlo.

Aplica un tema utilizando `setTheme()` o a través del constructor.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes'
files={['src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java']}
height='260px'
/>
<!-- vale on -->

### Color personalizable {#custom-color}

Si los temas incorporados no coinciden con tu paleta, establece un color de semilla personalizado utilizando la propiedad CSS `--dwc-badge-seed`. A partir de este único valor, el badge deriva automáticamente los colores de fondo, texto y borde, por lo que cada combinación se mantiene legible sin que tengas que especificar cada uno individualmente. Esto significa que puedes marcar un badge de cualquier color en tu sistema de diseño con confianza. Los valores de Tono, Saturación y Luminosidad (HSL) son particularmente convenientes aquí; cambiar solo el tono es suficiente para producir una familia de colores completamente diferente mientras se mantiene el contraste.

```java
Badge badge = new Badge("Personalizado");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### Tamaño {#sizing}

Utiliza `setExpanse()` para controlar el tamaño del badge. Hay nueve tamaños disponibles, que van desde `XXXSMALL` hasta `XXXLARGE`, y el predeterminado es `SMALL`.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes'
files={['src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java']}
height='300px'
/>
<!-- vale on -->

### Partes y variables CSS {#parts-and-css-variables}

<TableBuilder name="Badge" />
