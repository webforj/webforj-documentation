---
title: Badge
sidebar_position: 8
sidebar_class_name: new-content
_i18n_hash: 83dfb4c5ec1d554fc78e7e860128fb46
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

Una `Badge` es una etiqueta compacta y visualmente distinta utilizada para transmitir estado, conteos o breves fragmentos de información contextual. Ya sea que necesites flaggear un conteo de notificaciones, marcar un elemento como "Nuevo" o llamar la atención sobre una advertencia, las insignias te brindan una manera liviana de mostrar esa información directamente en la interfaz de usuario.

<!-- INTRO_END -->

:::tip Usando una `Badge`
Las insignias funcionan bien para conteos de notificaciones, etiquetas de estado y metadatos breves como etiquetas de versión o estados de lanzamiento. Mantén el texto de la insignia en una o dos palabras para que la etiqueta sea legible de un vistazo.
:::

## Creando una insignia {#creating-a-badge}

La `Badge` más simple toma una cadena de texto. También puedes pasar un `BadgeTheme` directamente en el constructor para establecer el estilo visual de inmediato. El constructor sin argumentos está disponible cuando necesitas construir una insignia dinámicamente y configurarla después de la creación.

```java
Badge badge = new Badge("Nuevo");

// Con un tema
Badge primary = new Badge("Destacado", BadgeTheme.SUCCESS);

// Construida dinámicamente
Badge status = new Badge();
status.setLabel("Pendiente");
status.setTheme(BadgeTheme.WARNING);
```

## Etiqueta {#label}

Puedes establecer o actualizar el contenido de texto de una insignia en cualquier momento con `setLabel()`. El método `setText()` es un alias para la misma operación; usa el que se lea de manera más natural en el contexto. Ambos tienen getters correspondientes, `getLabel()` y `getText()`, si necesitas leer el valor actual.

```java
Badge badge = new Badge();
badge.setLabel("Actualizado");

// Equivalente
badge.setText("Actualizado");

// Leer el valor
String current = badge.getLabel();
```

## Iconos {#icons}

A veces, un enfoque más visual es útil al transmitir información con una `Badge`. Las insignias admiten contenido de icono en ranura. Pasa un icono junto con texto utilizando el constructor `Badge(String, Component...)`, o pasa un icono solo para crear una insignia solo con icono. Cuando se combinan con texto, el icono se representa a la izquierda de la etiqueta.

Las insignias solo con icono funcionan especialmente bien para indicadores de estado compactos en diseños densos donde una palabra corta se sentiría desordenada. Combinar un icono con texto es un buen punto intermedio cuando el icono solo podría ser ambiguo. Un símbolo de estado es ampliamente comprendido por sí solo, pero añadir una etiqueta de texto corta elimina la incertidumbre para los usuarios primerizos. Puedes pasar múltiples componentes al constructor si necesitas componer un prefijo más rico, aunque en la práctica un solo icono es el patrón más común.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java'
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

Adjunta una `Badge` a un `Button` usando `setBadge()`. La insignia aparece en la esquina superior derecha del botón, superponiéndose al borde del botón. Este es un patrón común para conteos de notificaciones en acciones de la barra de herramientas o botones de icono. Dado que la insignia es un componente independiente, es totalmente independiente del tema y tamaño del botón. Puedes emparejar un botón primario con una insignia de peligro, o un botón fantasma con una insignia de éxito, y cada lado de la combinación se estiliza sin conflictos. Actualizar el conteo más tarde es tan simple como llamar a `badge.setLabel()` con un nuevo valor; no es necesario tocar el botón.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java'
height='290px'
/>
<!-- vale on -->

### Panel de pestañas {#tabbed-pane}

Agrega una `Badge` como sufijo en una `Tab` usando `setSuffixComponent()`. Este es un ajuste natural para conteos de estilo bandeja de entrada o indicadores de estado en cada pestaña. Es el tipo de patrón que se ve en clientes de correo electrónico o administradores de tareas donde es importante señalar la actividad en cada sección de un vistazo. La insignia se sitúa en el borde posterior de la etiqueta de la pestaña, después de cualquier contenido prefijo, y permanece visible sin importar qué pestaña esté actualmente activa. Esta persistencia es intencional: ocultar la insignia en pestañas inactivas haría más difícil saber qué secciones necesitan atención sin cambiar a cada una.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java'
height='360px'
/>
<!-- vale on -->

## Estilizando {#styling}

Las insignias admiten varias dimensiones de estilo: colores de tema para transmitir significado, una escala de expansión para controlar el tamaño y propiedades CSS para una personalización detallada.

### Temas {#themes}

Como con muchos componentes en webforJ, la `Badge` viene en catorce temas: siete variantes rellenas y siete variantes delineadas.

Los temas rellenos utilizan un fondo sólido y calculan automáticamente un color de texto que cumple con los requisitos de contraste. Las variantes delineadas, en cambio, utilizan un fondo teñido con un borde de color, lo que las convierte en una opción más sutil cuando deseas que la insignia complemente el contenido circundante en lugar de dominarlo.

Aplica un tema usando `setTheme()` o a través del constructor.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java'
height='260px'
/>
<!-- vale on -->

### Color personalizado {#custom-color}

Si los temas integrados no coinciden con tu paleta, establece un color de semilla personalizado usando la propiedad CSS `--dwc-badge-seed`. A partir de este único valor, la insignia deriva automáticamente los colores de fondo, texto y borde, por lo que cada combinación se mantiene legible sin que tengas que especificar cada uno individualmente. Esto significa que puedes marcar una insignia con cualquier color en tu sistema de diseño con confianza. Los valores de Matiz, Saturación y Luminosidad (HSL) son particularmente convenientes aquí; cambiar solo la tonalidad es suficiente para producir una familia de colores completamente diferente mientras se mantiene el contraste intacto.

```java
Badge badge = new Badge("Personalizado");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### Tamaño {#sizing}

Usa `setExpanse()` para controlar el tamaño de la insignia. Nueve tamaños están disponibles, que van desde `XXXSMALL` hasta `XXXLARGE`, y el predeterminado es `SMALL`.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java'
height='300px'
/>
<!-- vale on -->

### Partes y variables CSS {#parts-and-css-variables}

<TableBuilder name="Badge" />
