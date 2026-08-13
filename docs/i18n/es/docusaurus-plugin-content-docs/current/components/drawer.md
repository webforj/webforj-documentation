---
title: Drawer
sidebar_position: 35
description: >-
  Slide panels in from any screen edge with the Drawer component, ideal for side
  navigation, filter menus, settings, and stackable popovers.
_i18n_hash: c04ddb6b8576656535eb1fa09ea587e3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

El componente `Drawer` en webforJ crea un panel deslizante que aparece desde el borde de la pantalla, revelando contenido adicional sin abandonar la vista actual. Se utiliza comúnmente para navegación lateral, menús de filtros, configuraciones de usuario o notificaciones compactas que necesitan aparecer temporalmente sin interrumpir la interfaz principal.

<!-- INTRO_END -->

## Apilamiento {#stacking}

Los drawers se apilan automáticamente cuando se abren varios, lo que los convierte en una opción flexible para interfaces con espacio limitado.

El ejemplo a continuación muestra este comportamiento dentro del componente [`AppLayout`](../components/app-layout). El drawer de navegación activado por el menú de hamburguesa está integrado en [`AppLayout`](../components/app-layout), mientras que el popup de bienvenida en la parte inferior utiliza una instancia de `Drawer` independiente. Ambos coexisten y se apilan independientemente, demostrando cómo los drawers pueden integrarse dentro de componentes de diseño o utilizarse como elementos independientes.

<ComponentDemo
path='/webforj/drawerwelcome'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java',
  'src/main/frontend/css/drawer/drawerWelcome.css',
]}
/>

## Autofocus {#autofocus}

El componente `Drawer` admite el autofocus, que establece automáticamente el enfoque en el primer elemento enfocable cuando se abre el `Drawer`. Esto mejora la usabilidad al atraer la atención directamente al primer elemento accionable.

<ComponentDemo
path='/webforj/drawerautofocus'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java']}
height='600px'
/>

<!-- Ejemplo -->

## Etiqueta {#label}

El método `setLabel()` puede proporcionar una descripción significativa del contenido dentro de un `Drawer`. Cuando se establece una etiqueta, las tecnologías de asistencia como los lectores de pantalla pueden anunciarla, ayudando a los usuarios a comprender el propósito del `Drawer` sin ver su contenido visual.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Gestor de Tareas");
```

:::tip Etiquetas Descriptivas
Utilice etiquetas concisas y descriptivas que reflejen el propósito del `Drawer`. Evite términos genéricos como “Menú” o “Panel” cuando se puede usar un nombre más específico.
:::

## Tamaño {#size}

Para controlar el tamaño de un `Drawer`, establezca un valor para la propiedad CSS personalizada `--dwc-drawer-size`. Esto establece el ancho del `Drawer` para la colocación izquierda/derecha o la altura para la colocación superior/inferior.

Puede definir el valor utilizando cualquier unidad CSS válida, como un porcentaje, píxeles o vw/vh, utilizando Java o CSS:

```java
// Java
drawer.setStyle("--dwc-drawer-size", "40%");
```

```css
/* CSS */
dwc-drawer {
  --dwc-drawer-size: 40%;
}
```

Para evitar que el `Drawer` crezca demasiado, utilice `--dwc-drawer-max-size` junto con él:

```java
// Java
drawer.setStyle("--dwc-drawer-size", "40%");
drawer.setStyle("--dwc-drawer-max-size", "800px");
```

```css
/* CSS */
dwc-drawer {
  --dwc-drawer-size: 40%;
  --dwc-drawer-max-size: 800px;
}
```

## Colocación {#placement}

El método `setPlacement()` controla dónde aparece el `Drawer` en la ventana gráfica.

Opciones de colocación disponibles:

<!-- vale off -->
- **SUPERIOR**: Posiciona el drawer en el borde superior de la ventana gráfica.
- **SUPERIOR_CENTRO**: Alinea el drawer horizontalmente centrado en la parte superior de la ventana gráfica.
- **INFERIOR**: Coloca el drawer en la parte inferior de la ventana gráfica.
- **INFERIOR_CENTRO**: Centra horizontalmente el drawer en la parte inferior de la ventana gráfica.
- **IZQUIERDO**: Posiciona el drawer a lo largo del borde izquierdo de la ventana gráfica.
- **DERECHO**: Posiciona el drawer a lo largo del borde derecho de la ventana gráfica.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## Manejo de eventos {#event-handling}

El componente `Drawer` emite eventos de ciclo de vida que pueden utilizarse para activar la lógica de la aplicación en respuesta a cambios en su estado abierto o cerrado.

Eventos admitidos:

- `DrawerOpenEvent`: Se dispara cuando el drawer está completamente abierto.
- `DrawerCloseEvent`: Se dispara cuando el drawer está completamente cerrado.

Puede adjuntar oyentes a estos eventos para ejecutar lógica cuando cambia el estado del `Drawer`.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Manejar el evento de drawer abierto
});

drawer.addCloseListener(e -> {
  // Manejar el evento de drawer cerrado
});
```

## Ejemplo: Selector de contactos {#example-contact-picker}

El componente `Drawer` expone contenido adicional sin interrumpir la vista actual. Este ejemplo coloca un drawer en el centro inferior, que contiene una lista de contactos desplazable.

Cada contacto muestra un avatar, nombre, ubicación y un botón de acción para acceder rápidamente a detalles o comunicación. Este enfoque funciona bien para construir herramientas compactas como selectores de contactos, paneles de configuración o notificaciones.

<ComponentDemo
path='/webforj/drawercontact'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java',
  'src/main/resources/css/drawer/drawerContact.css',
]}
height='600px'
/>

## Ejemplo: Gestor de tareas {#example-task-manager}

Este ejemplo utiliza un `Drawer` como gestor de tareas. Puede agregar tareas, marcarlas y borrar las completadas. El pie del `Drawer` incluye controles de formulario para interactuar con la lista de tareas, y el botón “Agregar Tarea” [`Button`](../components/button) se desactiva si se alcanzan 50 tareas.

<ComponentDemo
path='/webforj/drawertask'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java',
  'src/main/frontend/css/drawer/drawer-task-view.css',
]}
height='600px'
/>

## Estilo {#styling}

<TableBuilder name="Drawer" />
