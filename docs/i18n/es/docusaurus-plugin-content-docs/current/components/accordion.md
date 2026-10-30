---
sidebar_position: 1
title: Accordion
description: >-
  Group collapsible panels with the Accordion and AccordionPanel components to
  toggle visibility and coordinate expand or collapse behavior.
_i18n_hash: 6537c2d24bfc99dd866f27277ed4f197
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

El componente `Accordion` proporciona un conjunto apilado verticalmente de paneles colapsables. Cada panel tiene un encabezado clicable que alterna la visibilidad de su contenido. Un `AccordionPanel` puede usarse como una sección de divulgación independiente, o agrupada dentro de un `Accordion` para coordinar el comportamiento de expansión y colapso entre múltiples paneles.

<!-- INTRO_END -->

:::tip Cuándo usar un acordeón
Los acordeones funcionan bien para preguntas frecuentes, páginas de configuraciones y flujos paso a paso donde revelar todo el contenido a la vez podría crear un diseño abrumador. Si las secciones son igualmente importantes y los usuarios se benefician de verlas simultáneamente, considera [pestañas](/docs/components/tabbedpane) en su lugar.
:::

## `AccordionPanel` <DocChip chip='name' label="dwc-accordion-panel" /> {#accordion-panel}

`AccordionPanel` es el bloque de construcción principal del sistema de acordeones. Pasa una cadena de etiquetas al constructor para establecer el texto del encabezado, luego agrega componentes secundarios para poblar el cuerpo. Un panel funciona por sí solo sin ningún grupo `Accordion` que lo rodee, lo que lo convierte en un útil widget de divulgación ligero cuando solo necesitas una sola sección colapsable. También está disponible el constructor sin argumentos cuando prefieres configurar el panel completamente después de la construcción.

```java
// Solo etiqueta - agrega contenido del cuerpo por separado
AccordionPanel panel = new AccordionPanel("Título de la Sección");
panel.add(new Paragraph("El contenido del cuerpo va aquí."));

// Etiqueta y contenido del cuerpo pasados directamente en el constructor
AccordionPanel panel = new AccordionPanel("Título", new Paragraph("Contenido del cuerpo."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java']}
height='550px'
/>
<!-- vale on -->

### Apertura y cierre {#opening-and-closing}

Controla el estado abierto/cerrado programáticamente en cualquier momento. `isOpened()` es útil cuando necesitas leer el estado actual antes de decidir qué hacer. Por ejemplo, podrías alternar un panel al estado opuesto o mostrar u ocultar condicionalmente otras partes de la interfaz de usuario.

```java
// Expande el panel
panel.open();

// Colapsa el panel
panel.close();

// Devuelve true si está actualmente expandido
boolean isOpen = panel.isOpened();
```

Usa `setLabel()` para actualizar el texto del encabezado después de la construcción. `setText()` es un alias para la misma operación, por lo que la etiqueta puede mantenerse sincronizada con datos dinámicos:

```java
panel.setLabel("Etiqueta Actualizada");
```

## Grupos de acordeones {#accordion-groups}

Envuelto múltiples instancias de `AccordionPanel` dentro de un `Accordion` crea un grupo coordinado. Por defecto, el grupo usa **modo único**: abrir un panel colapsa automáticamente todos los demás, manteniendo solo una sección visible a la vez. Este comportamiento por defecto es intencional, mantiene al usuario enfocado en un solo contenido y evita que la página se vuelva visualmente abrumadora cuando los paneles tienen contenido considerable.

Los paneles se construyen de forma independiente y se pasan al `Accordion`, por lo que puedes configurar cada uno antes de agruparlos. Múltiples instancias de `Accordion` separadas también pueden existir en la misma página; cada grupo gestiona su propio estado de forma independiente, por lo que expandir un panel en un grupo no afecta a otro.

```java
AccordionPanel panel1 = new AccordionPanel("¿Qué es webforJ?");
AccordionPanel panel2 = new AccordionPanel("¿Cómo funcionan los paneles agrupados?");
AccordionPanel panel3 = new AccordionPanel("¿Puedo tener múltiples grupos?");

Accordion accordion = new Accordion(panel1, panel2, panel3);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiongroup'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionGroupView.java']}
height='400px'
/>
<!-- vale on -->

### Modo múltiple {#multiple-mode}

El modo múltiple permite que cualquier número de paneles permanezca expandido simultáneamente. Esto es útil cuando los usuarios necesitan comparar el contenido de varias secciones a la vez, o cuando cada panel es lo suficientemente corto como para que expandir varios a la vez no cree un diseño desordenado.

```java
accordion.setMultiple(true);
```

Con el modo múltiple activo, todos los paneles en el grupo pueden expandirse o colapsarse a la vez usando los métodos masivos:

```java
// Expande todos los paneles en el grupo
accordion.openAll();

// Colapsa todos los paneles en el grupo
accordion.closeAll();
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java']}
height='575px'
/>
<!-- vale on -->

:::info Restricción de modo único
`openAll()` solo está disponible cuando el modo múltiple está habilitado. Llamarlo en modo único no tiene efecto. `closeAll()` funciona en ambos modos.
:::

<!-- vale off -->
## Estado deshabilitado {#disabled-state}
<!-- vale on -->

Los paneles individuales pueden ser deshabilitados para prevenir interacciones del usuario mientras siguen siendo visibles. Esto es útil durante los estados de carga o cuando ciertas secciones están condicionalmente no disponibles, mostrando la estructura del panel sin permitir interacciones prematuras. Un panel deshabilitado que ya está abierto permanece expandido, pero su encabezado ya no se puede hacer clic para colapsarlo. Deshabilitar el grupo `Accordion` aplica el estado deshabilitado a todos los paneles contenidos a la vez, por lo que no necesitas iterar por los paneles individualmente.

```java
// Deshabilitar un solo panel
panel.setEnabled(false);

// Deshabilitar todos los paneles en el grupo
accordion.setEnabled(false);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiondisabled'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionDisabledView.java']}
height='650px'
/>
<!-- vale on -->

## Personalización de paneles {#customizing-panels}

Más allá de etiquetas y el comportamiento básico de apertura/cierre, cada `AccordionPanel` admite una rica personalización tanto de su contenido de encabezado como del ícono de expandir/colapsar.

### Encabezado personalizado {#custom-header}

El encabezado de un panel renderiza su etiqueta como texto plano por defecto. Usa `addToHeader()` para reemplazar ese texto con cualquier componente o combinación de componentes, facilitando incluir íconos, insignias, indicadores de estado u otro marcado rico junto a la etiqueta del panel. Esto es particularmente útil en tableros o paneles de configuración donde cada encabezado de sección necesita transmitir contexto adicional de un vistazo, como un recuento de elementos, una insignia de advertencia o un estado de finalización, sin requerir que el usuario expanda el panel primero.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Encabezado Personalizado con Ícono"));
panel.addToHeader(headerContent);
```

:::info Reemplazo de etiqueta
El contenido agregado a través de `addToHeader()` reemplaza completamente el texto de la etiqueta por defecto. `setLabel()` y `setText()` siguen funcionando junto con `addToHeader()`, pero dado que la ranura de encabezado tiene precedencia visual, el texto de la etiqueta no se mostrará a menos que lo incluyas explícitamente en tu contenido ranurado.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### Ícono personalizado {#custom-icon}

El indicador de expandir/colapsar por defecto es un chevron que es visible tanto en los estados abierto como cerrado. `setIcon()` lo reemplaza con cualquier componente [`Icon`](/docs/components/icon), útil para iconografía de marca o cuando una metáfora visual diferente se ajusta mejor al contenido. Pasar `null` restaura el chevron por defecto. `getIcon()` devuelve el ícono actualmente configurado, o `null` si se está usando el chevron por defecto.

```java
// Reemplaza el chevron por defecto con un ícono de más
panel.setIcon(FeatherIcon.PLUS.create());

// Restaura el chevron por defecto
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java']}
height='200px'
/>
<!-- vale on -->

## Acordeones anidados {#nested-accordions}

Los acordeones se pueden anidar dentro de otros paneles de acordeón, lo que es útil para representar contenido jerárquico como configuraciones categorizadas o navegación de múltiples niveles. Agrega un `Accordion` interno a un `AccordionPanel` externo como cualquier otro componente secundario. Mantén la anidación superficial. Uno o dos niveles suele ser suficiente. Jerarquías más profundas tienden a ser más difíciles de navegar y a menudo señalan que la estructura del contenido necesita ser repensada.

```java
AccordionPanel innerA = new AccordionPanel("Panel Interno A");
AccordionPanel innerB = new AccordionPanel("Panel Interno B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Panel Externo");
outer.add(innerAccordion);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionnested'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java']}
height='550px'
/>
<!-- vale on -->

## Eventos {#events}

`AccordionPanel` dispara eventos en cada etapa del ciclo de vida del alternador. Los tres tipos de eventos cubren diferentes momentos, así que elige según cuándo necesites que tu lógica se ejecute:

| Evento | Se dispara |
|-------|-------|
| `AccordionPanelToggleEvent` | Antes de que cambie el estado |
| `AccordionPanelOpenEvent` | Después de que el panel se haya abierto completamente |
| `AccordionPanelCloseEvent` | Después de que el panel se haya cerrado completamente |

```java
panel.onToggle(e -> {
    // Se dispara antes de que el panel cambie de estado.
    // e.isOpened() refleja el estado al que se está transitando, no el estado actual.
    String direction = e.isOpened() ? "abriendo" : "cerrando";
});

panel.onOpen(e -> {
    // Se dispara después de que el panel está completamente abierto — bueno para cargar contenido de forma perezosa.
});

panel.onClose(e -> {
    // Se dispara después de que el panel está completamente cerrado — bueno para limpieza o actualizaciones de resumen.
});
```

## Estilo {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
