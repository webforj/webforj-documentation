---
sidebar_position: 1
title: Accordion
description: >-
  Group collapsible panels with the Accordion and AccordionPanel components to
  toggle visibility and coordinate expand or collapse behavior.
_i18n_hash: b11e2d2ef8854f757454635c984da1d6
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

El componente `Accordion` proporciona un conjunto apilado verticalmente de paneles colapsables. Cada panel tiene un encabezado clickeable que alterna la visibilidad de su contenido principal. Un `AccordionPanel` puede ser utilizado como una sección de divulgación independiente, o agrupado dentro de un `Accordion` para coordinar el comportamiento de expansión y colapso entre múltiples paneles.

<!-- INTRO_END -->

:::tip Cuándo usar un acordeón
Los acordeones funcionan bien para preguntas frecuentes, páginas de configuración y flujos paso a paso donde revelar todo el contenido a la vez crearía un diseño abrumador. Si las secciones son igualmente importantes y los usuarios se benefician al verlas simultáneamente, considere [pestañas](/docs/components/tabbedpane) en su lugar.
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` es el bloque de construcción fundamental del sistema de acordeones. Pase una cadena de etiqueta al constructor para establecer el texto del encabezado, luego agregue componentes secundarios para llenar el cuerpo. Un panel funciona por sí mismo sin ningún grupo `Accordion` circundante, lo que lo convierte en un útil widget de divulgación liviano cuando solo necesita una única sección colapsable. También está disponible un constructor sin argumentos cuando prefiere configurar el panel completamente después de la construcción.

```java
// Solo etiqueta - agregar contenido del cuerpo por separado
AccordionPanel panel = new AccordionPanel("Título de Sección");
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

Controle el estado abierto/cerrado programáticamente en cualquier momento. `isOpened()` es útil cuando necesita leer el estado actual antes de decidir qué hacer. Por ejemplo, podría alternar un panel al estado opuesto o mostrar u ocultar condicionalmente otras partes de la UI.

```java
// Expandir el panel
panel.open();

// Colapsar el panel
panel.close();

// Devuelve verdadero si está actualmente expandido
boolean isOpen = panel.isOpened();
```

Use `setLabel()` para actualizar el texto del encabezado después de la construcción. `setText()` es un alias para la misma operación, por lo que la etiqueta puede mantenerse sincronizada con datos dinámicos:

```java
panel.setLabel("Etiqueta Actualizada");
```

## Grupos de acordeones {#accordion-groups}

Agrupar múltiples instancias de `AccordionPanel` dentro de un `Accordion` crea un grupo coordinado. Por defecto, el grupo utiliza **modo único**: abrir un panel colapsa automáticamente todos los demás, manteniendo solo una sección visible a la vez. Este ajuste predeterminado es intencional, mantiene al usuario enfocado en un solo contenido y evita que la página se vuelva visualmente abrumadora cuando los paneles tienen contenido sustancial en el cuerpo.

Los paneles se construyen de forma independiente y se pasan al `Accordion`, por lo que puede configurar cada uno antes de agruparlos. También pueden existir múltiples instancias separadas de `Accordion` en la misma página; cada grupo gestiona su propio estado de forma independiente, por lo que expandir un panel en un grupo no afecta a otro.

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

Con el modo múltiple activado, todos los paneles en el grupo pueden ser expandidos o colapsados a la vez usando los métodos de agrupación:

```java
// Expandir cada panel en el grupo
accordion.openAll();

// Colapsar cada panel en el grupo
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

Los paneles individuales se pueden deshabilitar para evitar la interacción del usuario mientras siguen siendo visibles. Esto es útil durante estados de carga o cuando ciertas secciones no están disponibles condicionalmente, mostrando la estructura del panel sin permitir la interacción prematura. Un panel deshabilitado que ya estaba abierto permanece expandido, pero su encabezado ya no se puede hacer clic para colapsarlo. Deshabilitar el grupo `Accordion` aplica el estado deshabilitado a todos los paneles contenidos a la vez, por lo que no necesita recorrer los paneles individualmente.

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

## Personalizando paneles {#customizing-panels}

Más allá de las etiquetas y el comportamiento básico de abrir/cerrar, cada `AccordionPanel` admite una personalización más rica de tanto su contenido de encabezado como el ícono de expansión/colapso.

### Encabezado personalizado {#custom-header}

El encabezado de un panel renderiza su etiqueta como texto simple por defecto. Use `addToHeader()` para reemplazar ese texto con cualquier componente o combinación de componentes, facilitando incluir íconos, insignias, indicadores de estado u otro marcado enriquecido junto con la etiqueta del panel. Esto es particularmente útil en tableros o paneles de configuración donde cada encabezado de sección necesita transmitir contexto adicional de un vistazo, como un conteo de elementos, una insignia de advertencia o un estado de finalización, sin requerir que el usuario expanda el panel primero.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Encabezado Personalizado con Icono"));
panel.addToHeader(headerContent);
```

:::info Reemplazo de etiqueta
El contenido agregado a través de `addToHeader()` reemplaza completamente el texto de la etiqueta predeterminada. `setLabel()` y `setText()` continúan funcionando junto a `addToHeader()`, pero dado que el espacio de encabezado tiene precedencia visual, el texto de la etiqueta no se mostrará a menos que lo incluya explícitamente en su contenido asignado.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### Ícono personalizado {#custom-icon}

El indicador de expansión/colapso predeterminado es un chevron que es visible tanto en los estados abierto como cerrado. `setIcon()` lo reemplaza con cualquier componente de [`Icon`](/docs/components/icon), útil para iconografía de marca o cuando una metáfora visual diferente se ajusta mejor al contenido. Pasar `null` restaura el chevron predeterminado. `getIcon()` devuelve el ícono actualmente establecido, o `null` si se está utilizando el chevron predeterminado.

```java
// Reemplazar el chevron predeterminado con un ícono de más
panel.setIcon(FeatherIcon.PLUS.create());

// Restaurar el chevron predeterminado
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

Los acordeones pueden estar anidados dentro de otros paneles de acordeón, lo que es útil para representar contenido jerárquico como configuraciones categorizadas o navegación multinivel. Agregue un `Accordion` interno a un `AccordionPanel` externo como cualquier otro componente secundario. Mantenga la anidación superficial. Uno o dos niveles suelen ser suficientes. Las jerarquías más profundas tienden a ser más difíciles de navegar y a menudo indican que la estructura del contenido necesita ser repensada.

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

`AccordionPanel` dispara eventos en cada etapa del ciclo de vida del cambio. Los tres tipos de eventos cubren diferentes momentos, así que elija según cuándo necesita que su lógica se ejecute:

| Evento | Ocurre |
|--------|--------|
| `AccordionPanelToggleEvent` | Antes de que cambie el estado |
| `AccordionPanelOpenEvent` | Después de que el panel se haya abierto completamente |
| `AccordionPanelCloseEvent` | Después de que el panel se haya cerrado completamente |

```java
panel.onToggle(e -> {
    // Ocurre antes de que el panel cambie de estado.
    // e.isOpened() refleja el estado al que se está cambiando, no el estado actual.
    String direction = e.isOpened() ? "abriendo" : "cerrando";
});

panel.onOpen(e -> {
    // Ocurre después de que el panel esté completamente abierto — bueno para cargar contenido de forma perezosa.
});

panel.onClose(e -> {
    // Ocurre después de que el panel esté completamente cerrado — bueno para limpieza o actualizaciones de resumen.
});
```

## Estilizando {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
