---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
_i18n_hash: 2bf90130b3a767840e2604045504ee91
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

El componente `Accordion` proporciona un conjunto apilado verticalmente de paneles colapsables. Cada panel tiene un encabezado clickeable que alterna la visibilidad de su contenido. Un `AccordionPanel` puede usarse como una sección de divulgación independiente o agruparse dentro de un `Accordion` para coordinar el comportamiento de expansión y colapso en múltiples paneles.

<!-- INTRO_END -->

:::tip Cuándo usar un acordeón
Los acordeones funcionan bien para preguntas frecuentes, páginas de configuración y flujos paso a paso donde revelar todo el contenido de una vez podría crear un diseño abrumador. Si las secciones son igualmente importantes y los usuarios se benefician de verlas simultáneamente, considera usar [pestañas](/docs/components/tabbedpane) en su lugar.
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` es el bloque de construcción central del sistema de acordeones. Pasa una cadena de texto como etiqueta al constructor para establecer el texto del encabezado, luego agrega componentes secundarios para poblar el cuerpo. Un panel funciona por sí solo sin ningún grupo de `Accordion` circundante, lo que lo convierte en un útil widget de divulgación ligero cuando solo necesitas una sección colapsable. El constructor sin argumentos también está disponible cuando prefieres configurar el panel completamente después de la construcción.

```java
// Solo etiqueta - agrega contenido del cuerpo por separado
AccordionPanel panel = new AccordionPanel("Título de Sección");
panel.add(new Paragraph("El contenido del cuerpo va aquí."));

// Etiqueta y contenido del cuerpo pasados directamente en el constructor
AccordionPanel panel = new AccordionPanel("Título", new Paragraph("Contenido del cuerpo."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java'
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

Envuelve múltiples instancias de `AccordionPanel` dentro de un `Accordion` para crear un grupo coordinado. Por defecto, el grupo utiliza **modo único**: abrir un panel colapsa automáticamente todos los demás, manteniendo solo una sección visible a la vez. Este comportamiento predeterminado es intencional, ya que mantiene al usuario concentrado en una pieza de contenido y evita que la página se vuelva visualmente abrumadora cuando los paneles tienen contenido corporal sustancial.

Los paneles se construyen de forma independiente y se pasan al `Accordion`, por lo que puedes configurar cada uno antes de agruparlos. También pueden existir múltiples instancias separadas de `Accordion` en la misma página; cada grupo gestiona su propio estado de forma independiente, por lo que expandir un panel en un grupo no tiene efecto en otro.

```java
AccordionPanel panel1 = new AccordionPanel("¿Qué es webforJ?");
AccordionPanel panel2 = new AccordionPanel("¿Cómo funcionan los paneles agrupados?");
AccordionPanel panel3 = new AccordionPanel("¿Puedo tener múltiples grupos?");

Accordion accordion = new Accordion(panel1, panel2, panel3);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiongroup'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionGroupView.java'
height='400px'
/>
<!-- vale on -->

### Modo múltiple {#multiple-mode}

El modo múltiple permite que cualquier número de paneles permanezca expandido simultáneamente. Esto es útil cuando los usuarios necesitan comparar el contenido de varias secciones a la vez, o cuando cada panel es lo suficientemente corto como para que expandir varios a la vez no cree un diseño desordenado.

```java
accordion.setMultiple(true);
```

Con el modo múltiple activo, todos los paneles en el grupo se pueden expandir o colapsar a la vez utilizando los métodos masivos:

```java
// Expande cada panel en el grupo
accordion.openAll();

// Colapsa cada panel en el grupo
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java'
height='575px'
/>
<!-- vale on -->

:::info Restricción de modo único
`openAll()` solo está disponible cuando el modo múltiple está habilitado. Llamarlo en modo único no tiene efecto. `closeAll()` funciona en ambos modos.
:::

<!-- vale off -->
## Estado deshabilitado {#disabled-state}
<!-- vale on -->

Los paneles individuales pueden ser deshabilitados para prevenir la interacción del usuario mientras siguen visibles. Esto es útil durante estados de carga o cuando ciertas secciones están condicionalmente no disponibles, mostrando la estructura del panel sin permitir interacciones prematuras. Un panel deshabilitado que ya estaba abierto permanece expandido, pero su encabezado ya no se puede hacer clic para colapsarlo. Deshabilitar el grupo de `Accordion` aplica el estado deshabilitado a todos los paneles contenidos a la vez, por lo que no necesitas recorrer los paneles individualmente.

```java
// Deshabilita un panel individual
panel.setEnabled(false);

// Deshabilita todos los paneles en el grupo
accordion.setEnabled(false);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiondisabled'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionDisabledView.java'
height='650px'
/>
<!-- vale on -->

## Personalizando paneles {#customizing-panels}

Más allá de las etiquetas y el comportamiento básico de apertura/cierre, cada `AccordionPanel` admite una personalización más rica tanto de su contenido de encabezado como del ícono de expansión/colapso.

### Encabezado personalizado {#custom-header}

El encabezado de un panel muestra su etiqueta como texto plano por defecto. Usa `addToHeader()` para reemplazar ese texto con cualquier componente o combinación de componentes, facilitando la inclusión de íconos, insignias, indicadores de estado u otro marcado rico junto con la etiqueta del panel. Esto es particularmente útil en paneles de navegación o de configuración donde cada encabezado de sección necesita transmitir contexto adicional de un vistazo, como un conteo de elementos, una insignia de advertencia o un estado de finalización, sin requerir que el usuario expanda el panel primero.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Encabezado Personalizado con Ícono"));
panel.addToHeader(headerContent);
```

:::info Reemplazo de etiqueta
El contenido agregado a través de `addToHeader()` reemplaza completamente el texto de etiqueta predeterminado. `setLabel()` y `setText()` continúan funcionando junto con `addToHeader()`, pero dado que la ranura del encabezado tiene prioridad visual, el texto de la etiqueta no se mostrará a menos que lo incluyas explícitamente en tu contenido con ranura.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### Ícono personalizado {#custom-icon}

El indicador de expansión/colapso por defecto es un chevron que es visible tanto en los estados abierto como cerrado. `setIcon()` lo reemplaza con cualquier componente de [`Icon`](/docs/components/icon), útil para iconografía de marca o cuando una metáfora visual diferente se ajusta mejor al contenido. Pasar `null` restaura el chevron predeterminado. `getIcon()` devuelve el ícono actualmente configurado, o `null` si se está utilizando el chevron predeterminado.

```java
// Reemplaza el chevron predeterminado con un ícono de suma
panel.setIcon(FeatherIcon.PLUS.create());

// Restaura el chevron predeterminado
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java'
height='200px'
/>
<!-- vale on -->

## Acordeones anidados {#nested-accordions}

Los acordeones se pueden anidar dentro de otros paneles de acordeón, lo cual es útil para representar contenido jerárquico como configuraciones categorizadas o navegación de múltiples niveles. Agrega un `Accordion` interno a un `AccordionPanel` externo como cualquier otro componente secundario. Mantén la anidación poco profunda. Uno o dos niveles suelen ser suficientes. Jerarquías más profundas tienden a ser más difíciles de navegar y a menudo indican que la estructura del contenido necesita repensarse.

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
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java'
height='550px'
/>
<!-- vale on -->

## Eventos {#events}

`AccordionPanel` emite eventos en cada etapa del ciclo de vida del alternador. Los tres tipos de eventos cubren diferentes momentos, así que elige según cuándo tu lógica necesita ejecutarse:

| Evento | Se produce |
|-------|-------|
| `AccordionPanelToggleEvent` | Antes de que el estado cambie |
| `AccordionPanelOpenEvent` | Después de que el panel se haya abierto completamente |
| `AccordionPanelCloseEvent` | Después de que el panel se haya cerrado completamente |

```java
panel.onToggle(e -> {
    // Se produce antes de que el panel cambie de estado.
    // e.isOpened() refleja el estado al que se está trasladando, no el estado actual.
    String direction = e.isOpened() ? "abriendo" : "cerrando";
});

panel.onOpen(e -> {
    // Se produce después de que el panel está completamente abierto — bueno para cargamento perezoso de contenido.
});

panel.onClose(e -> {
    // Se produce después de que el panel está completamente cerrado — bueno para limpieza o actualizaciones de resumen.
});
```

## Estilo {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
