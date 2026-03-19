---
title: Badge
sidebar_position: 8
sidebar_class_name: new-content
_i18n_hash: 112f61dea5c6c0d434267a25ccc61b9e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

Una `Badge` es una etiqueta compacta y visualmente distinta utilizada para transmitir estado, conteos o pequeños fragmentos de información contextual. Ya sea que necesite marcar un conteo de notificaciones, señalar un artículo como "Nuevo" o llamar la atención sobre una advertencia, las insignias le ofrecen una forma ligera de mostrar esa información directamente en la interfaz de usuario.

<!-- INTRO_END -->

:::tip Uso de una `Badge`
Las insignias funcionan bien para conteos de notificaciones, etiquetas de estado y metadatos cortos, como etiquetas de versión o estados de lanzamiento. Mantenga el texto de la insignia en una o dos palabras para que la etiqueta se lea de un vistazo.
:::

## Creación de una insignia {#creating-a-badge}

La `Badge` más simple toma una cadena de texto. También puede pasar un `BadgeTheme` directamente en el constructor para establecer el estilo visual de inmediato. El constructor sin argumentos está disponible cuando necesita construir una insignia dinámicamente y configurarla después de la creación.

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

Puede establecer o actualizar el contenido de texto de una insignia en cualquier momento con `setLabel()`. El método `setText()` es un alias para la misma operación; use el que se lea de manera más natural en el contexto. Ambos tienen getters correspondientes, `getLabel()` y `getText()`, si necesita leer el valor actual.

```java
Badge badge = new Badge();
badge.setLabel("Actualizado");

// Equivalente
badge.setText("Actualizado");

// Leer el valor
String current = badge.getLabel();
```

## Iconos {#icons}

A veces, un enfoque más visual es útil al transmitir información con una `Badge`. Las insignias admiten contenido de icono desplazado. Pase un icono junto con el texto utilizando el constructor `Badge(String, Component...)`, o pase un icono solo para crear una insignia solo con icono. Cuando se combina con texto, el icono se renderiza a la izquierda de la etiqueta.

Las insignias solo de icono funcionan especialmente bien como indicadores de estado compactos en diseños densos donde una palabra corta puede sentirse desordenada. Combinar un icono con texto es un buen punto intermedio cuando el icono por sí solo podría ser ambiguo. Un símbolo de estado es ampliamente entendido por sí mismo, pero agregar una breve etiqueta de texto elimina conjeturas para los usuarios primerizos. Puede pasar múltiples componentes al constructor si necesita componer un prefijo más rico, aunque en la práctica un solo icono es el patrón más común.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java'
height='320px'
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

Adjunte una `Badge` a un `Button` usando `setBadge()`. La insignia aparece en la esquina superior derecha del botón, superponiéndose al borde del botón. Este es un patrón común para conteos de notificaciones en acciones de barra de herramientas o botones de icono. Dado que la insignia es un componente independiente, es completamente independiente del propio tema y tamaño del botón. Puede emparejar un botón primario con una insignia de peligro, o un botón fantasma con una insignia de éxito, y cada lado de la combinación se estiliza sin conflictos. Actualizar el conteo más tarde es tan simple como llamar a `badge.setLabel()` con un nuevo valor; no es necesario tocar el botón.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java'
height='250px'
/>
<!-- vale on -->

### Panel con pestañas {#tabbed-pane}

Agregue una `Badge` como sufijo en una `Tab` usando `setSuffixComponent()`. Esto es una adaptación natural para conteos estilo bandeja de entrada o indicadores de estado en cada pestaña. Es el tipo de patrón que se ve en clientes de correo electrónico o gestores de tareas donde es importante señalar la actividad en cada sección de un vistazo. La insignia se sitúa en el borde trasero de la etiqueta de la pestaña, después de cualquier contenido prefijo, y permanece visible independientemente de cuál pestaña esté activa. Esta persistencia es intencional: ocultar la insignia en pestañas inactivas dificultaría saber qué secciones necesitan atención sin cambiar a cada una.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java'
height='325px'
/>
<!-- vale on -->

## Estilización {#styling}

Las insignias admiten varias dimensiones de estilo: colores de tema para transmitir significado, una escala de expansión para controlar el tamaño y propiedades CSS para una personalización más detallada.

### Temas {#themes}

Al igual que muchos componentes en webforJ, la `Badge` viene en catorce temas: siete variantes llenas y siete esbozadas.

Los temas llenos utilizan un fondo sólido y calculan automáticamente un color de texto que cumple con los requisitos de contraste. Las variantes esbozadas, en cambio, utilizan un fondo tintado con un borde de color, lo que las convierte en una opción más sutil cuando desea que la insignia complemente el contenido circundante en lugar de dominarlo.

Aplique un tema utilizando `setTheme()` o a través del constructor.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java'
height='260px'
/>
<!-- vale on -->

### Color personalizado {#custom-color}

Si los temas incorporados no coinciden con su paleta, establezca un color base personalizado utilizando la propiedad CSS `--dwc-badge-seed`. A partir de este único valor, la insignia deriva automáticamente los colores de fondo, texto y borde, por lo que cada combinación se mantiene legible sin usted especificar cada uno individualmente. Esto significa que puede diseñar una insignia de cualquier color en su sistema de diseño con confianza. Los valores de Tono, Saturación y Luminosidad (HSL) son particularmente convenientes aquí; cambiar solo el tono es suficiente para producir una familia de colores completamente diferente mientras se mantiene el contraste intacto.

```java
Badge badge = new Badge("Personalizada");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### Tamaño {#sizing}

Utilice `setExpanse()` para controlar el tamaño de la insignia. Hay nueve tamaños disponibles, que van desde `XXXSMALL` hasta `XXXLARGE`, y el predeterminado es `SMALL`.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java'
height='260px'
/>
<!-- vale on -->

### Partes y variables CSS {#parts-and-css-variables}

<TableBuilder name="Badge" />
