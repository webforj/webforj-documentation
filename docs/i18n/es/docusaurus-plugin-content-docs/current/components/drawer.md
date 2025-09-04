---
title: Drawer
sidebar_position: 35
sidebar_class_name: updated-content
_i18n_hash: a19d1b8c8e0b74cecee529e86649d449
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

El componente `Drawer` en webforJ crea un panel deslizante que aparece desde el borde de la pantalla, revelando contenido adicional sin dejar la vista actual. Se utiliza comúnmente para navegación lateral, menús de filtros, configuraciones de usuario o notificaciones compactas que necesitan aparecer temporalmente sin interrumpir la interfaz principal.

Los `Drawers` se apilan automáticamente cuando se abren múltiples, lo que los convierte en una opción flexible para interfaces con espacio limitado.

El ejemplo a continuación muestra este comportamiento dentro del componente [`AppLayout`](../components/app-layout). El cajón de navegación activado por el menú hamburguesa está integrado en [`AppLayout`](../components/app-layout), mientras que el popup de bienvenida en la parte inferior utiliza una instancia de `Drawer` independiente. Ambos coexisten y se apilan de manera independiente, demostrando cómo los `Drawers` pueden integrarse dentro de componentes de diseño o usarse como elementos independientes.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofocus

El componente `Drawer` admite autofocus, que establece automáticamente el foco en el primer elemento enfocable cuando se abre el `Drawer`. Esto mejora la usabilidad al llevar la atención directamente al primer elemento accionable.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Ejemplo -->

## Label {#label}

El método `setLabel()` puede proporcionar una descripción significativa del contenido dentro de un `Drawer`. Cuando se establece una etiqueta, las tecnologías asistivas como los lectores de pantalla pueden anunciarla, ayudando a los usuarios a entender el propósito del `Drawer` sin ver su contenido visual.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Administrador de Tareas");
```

:::tip Etiquetas Descriptivas
Utiliza etiquetas concisas y descriptivas que reflejen el propósito del `Drawer`. Evita términos genéricos como "Menú" o "Panel" cuando se puede usar un nombre más específico.
:::

## Tamaño

Para controlar el tamaño de un `Drawer`, establece un valor para la propiedad CSS personalizada `--dwc-drawer-size`. Esto establece el ancho del `Drawer` para colocaciones izquierda/derecha o la altura para colocaciones superior/inferior.

Puedes definir el valor usando cualquier unidad CSS válida como un porcentaje, píxeles o vw/vh, utilizando Java o CSS:

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

Para evitar que el `Drawer` crezca demasiado, utiliza `--dwc-drawer-max-size` junto a él:

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

## Colocación

El método `setPlacement()` controla dónde aparece el `Drawer` en la ventana de visualización.

Opciones de colocación disponibles:

<!-- vale off -->
- **TOP**: Posiciona el cajón en el borde superior de la ventana de visualización.
- **TOP_CENTER**: Alinea el cajón horizontalmente centrado en la parte superior de la ventana de visualización.
- **BOTTOM**: Coloca el cajón en la parte inferior de la ventana de visualización.
- **BOTTOM_CENTER**: Centra horizontalmente el cajón en la parte inferior de la ventana de visualización.
- **LEFT**: Posiciona el cajón a lo largo del borde izquierdo de la ventana de visualización.
- **RIGHT**: Posiciona el cajón a lo largo del borde derecho de la ventana de visualización.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Manejo de eventos

El componente `Drawer` emite eventos de ciclo de vida que se pueden utilizar para activar la lógica de la aplicación en respuesta a cambios en su estado abierto o cerrado.

Eventos soportados:

- `DrawerOpenEvent`: Se dispara cuando el cajón está completamente abierto.
- `DrawerCloseEvent`: Se dispara cuando el cajón está completamente cerrado.

Puedes adjuntar oyentes a estos eventos para ejecutar lógica cuando el estado del `Drawer` cambia.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Manejar el evento de cajón abierto
});

drawer.addCloseListener(e -> {
  // Manejar el evento de cajón cerrado
});
```

## Ejemplo: Selector de contactos

El componente `Drawer` expone contenido adicional sin interrumpir la vista actual. Este ejemplo coloca un cajón en el centro inferior, conteniendo una lista de contactos desplazable.

Cada contacto muestra un avatar, nombre, ubicación y botón de acción para acceso rápido a detalles o comunicación. Este enfoque funciona bien para construir herramientas compactas como selectores de contactos, paneles de configuración o notificaciones.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Ejemplo: Administrador de tareas

Este ejemplo utiliza un `Drawer` como un administrador de tareas. Puedes agregar tareas, marcarlas como completadas y eliminar las completadas. El pie del `Drawer` incluye controles de formulario para interactuar con la lista de tareas, y el botón "Agregar Tarea" [`Button`](../components/button) se desactiva si se alcanzan 50 tareas.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
height='600px'
/>

## Estilo {#styling}

<TableBuilder name="Drawer" />
