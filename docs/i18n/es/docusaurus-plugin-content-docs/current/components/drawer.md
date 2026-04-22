---
title: Drawer
sidebar_position: 35
_i18n_hash: 51577f27568214c5d39e43b7e6ce42d0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

El componente `Drawer` en webforJ crea un panel deslizante que aparece desde el borde de la pantalla, revelando contenido adicional sin abandonar la vista actual. Se utiliza comúnmente para navegación lateral, menús de filtrado, configuraciones de usuario o notificaciones compactas que deben aparecer temporalmente sin interrumpir la interfaz principal.

<!-- INTRO_END -->

## Apilamiento {#stacking}

Los drawers se apilan automáticamente cuando se abren varios, lo que los convierte en una opción flexible para interfaces con espacio limitado.

El ejemplo a continuación muestra este comportamiento dentro del componente [`AppLayout`](../components/app-layout). El drawer de navegación activado por el menú de hamburguesa está integrado en [`AppLayout`](../components/app-layout), mientras que el popup de bienvenida en la parte inferior utiliza una instancia de `Drawer` independiente. Ambos coexisten y se apilan de manera independiente, demostrando cómo los Drawers pueden integrarse dentro de componentes de diseño o utilizarse como elementos independientes.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofocus

El componente `Drawer` admite autofocus, que establece automáticamente el foco en el primer elemento enfocável cuando se abre el `Drawer`. Esto mejora la usabilidad al dirigir la atención directamente al primer elemento accionable.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Ejemplo -->

## Etiqueta {#label}

El método `setLabel()` puede proporcionar una descripción significativa del contenido dentro de un `Drawer`. Cuando se establece una etiqueta, las tecnologías asistivas como los lectores de pantalla pueden anunciarla, ayudando a los usuarios a entender el propósito del `Drawer` sin ver su contenido visual.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Gestor de Tareas");
```

:::tip Etiquetas Descriptivas
Utiliza etiquetas concisas y descriptivas que reflejen el propósito del `Drawer`. Evita términos genéricos como “Menú” o “Panel” cuando se puede usar un nombre más específico.
:::

## Tamaño

Para controlar el tamaño de un `Drawer`, establece un valor para la propiedad personalizada CSS `--dwc-drawer-size`. Esto establece el ancho del `Drawer` para colocación izquierda/derecha o la altura para colocación superior/inferior.

Puedes definir el valor utilizando cualquier unidad CSS válida, como un porcentaje, píxeles o vw/vh, usando Java o CSS:

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

## Colocación {#placement}

El método `setPlacement()` controla dónde aparece el `Drawer` en la ventana de visualización.

Opciones de colocación disponibles:

<!-- vale off -->
- **SUPERIOR**: Posiciona el drawer en el borde superior de la ventana de visualización.
- **CENTRO_SUPERIOR**: Alinea el drawer horizontalmente centrado en la parte superior de la ventana de visualización.
- **INFERIOR**: Coloca el drawer en la parte inferior de la ventana de visualización.
- **CENTRO_INFERIOR**: Centra horizontalmente el drawer en la parte inferior de la ventana de visualización.
- **IZQUIERDA**: Posiciona el drawer a lo largo del borde izquierdo de la ventana de visualización.
- **DERECHA**: Posiciona el drawer a lo largo del borde derecho de la ventana de visualización.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Manejo de eventos

El componente `Drawer` emite eventos del ciclo de vida que pueden usarse para activar la lógica de la aplicación en respuesta a cambios en su estado abierto o cerrado.

Eventos soportados:

- `DrawerOpenEvent`: Se dispara cuando el drawer está completamente abierto.
- `DrawerCloseEvent`: Se dispara cuando el drawer está completamente cerrado.

Puedes adjuntar oyentes a estos eventos para ejecutar lógica cuando el estado del `Drawer` cambie.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Manejar el evento de apertura del drawer
});

drawer.addCloseListener(e -> {
  // Manejar el evento de cierre del drawer
});
```

## Ejemplo: Selector de contactos

El componente `Drawer` expone contenido adicional sin interrumpir la vista actual. Este ejemplo coloca un drawer en el centro inferior, conteniendo una lista de contactos desplazable.

Cada contacto muestra un avatar, nombre, ubicación y botón de acción para acceder rápidamente a detalles o comunicación. Este enfoque funciona bien para construir herramientas compactas como selectores de contactos, paneles de configuración o notificaciones.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Ejemplo: Gestor de tareas

Este ejemplo utiliza un `Drawer` como gestor de tareas. Puedes añadir tareas, marcarlas como completadas y limpiar las finalizadas. El pie del `Drawer` incluye controles de formulario para interactuar con la lista de tareas, y el botón “Añadir Tarea” [`Button`](../components/button) se desactiva si se alcanzan 50 tareas.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
cssURL='/css/drawer/drawer-task-view.css'
height='600px'
/>

## Estilo {#styling}

<TableBuilder name="Drawer" />
