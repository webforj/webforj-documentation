---
title: Drawer
sidebar_position: 35
_i18n_hash: d0c9ff09e591673c99918aa6875af28a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

El componente `Drawer` en webforJ crea un panel deslizante que aparece desde el borde de la pantalla, revelando contenido adicional sin abandonar la vista actual. Se utiliza comúnmente para navegación lateral, menús de filtro, configuraciones de usuario o notificaciones compactas que necesitan aparecer temporalmente sin interrumpir la interfaz principal.

<!-- INTRO_END -->

## Apilamiento {#stacking}

Los drawers se apilan automáticamente cuando se abren múltiples, lo que los convierte en una opción flexible para interfaces con espacio limitado.

El ejemplo a continuación muestra este comportamiento dentro del componente [`AppLayout`](../components/app-layout). El drawer de navegación que se activa mediante el menú de hamburguesas está incorporado en [`AppLayout`](../components/app-layout), mientras que el popup de bienvenida en la parte inferior utiliza una instancia de `Drawer` independiente. Ambos coexisten y se apilan de forma independiente, demostrando cómo los Drawers pueden integrarse dentro de componentes de diseño o usarse como elementos independientes.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofocus

El componente `Drawer` admite autofocus, que establece automáticamente el enfoque en el primer elemento enfocables cuando se abre el `Drawer`. Esto mejora la usabilidad al llamar la atención directamente al primer elemento accionable.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Ejemplo -->

## Etiqueta {#label}

El método `setLabel()` puede proporcionar una descripción significativa del contenido dentro de un `Drawer`. Cuando se establece una etiqueta, tecnologías asistivas como los lectores de pantalla pueden anunciarla, ayudando a los usuarios a entender el propósito del `Drawer` sin ver su contenido visual.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Administrador de Tareas");
```

:::tip Etiquetas Descriptivas
Utiliza etiquetas concisas y descriptivas que reflejen el propósito del `Drawer`. Evita términos genéricos como "Menú" o "Panel" cuando se puede usar un nombre más específico.
:::

## Tamaño

Para controlar el tamaño de un `Drawer`, establece un valor para la propiedad CSS personalizada `--dwc-drawer-size`. Esto establece el ancho del `Drawer` para colocación izquierda/derecha o la altura para colocación superior/inferior.

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

Para evitar que el `Drawer` crezca demasiado, utiliza `--dwc-drawer-max-size` junto con él:

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

## Ubicación {#placement}

El método `setPlacement()` controla dónde aparece el `Drawer` en la ventana de visualización.

Opciones de ubicación disponibles:

<!-- vale off -->
- **SUPERIOR**: Posiciona el drawer en el borde superior de la ventana de visualización.
- **SUPERIOR_CENTRO**: Alinea el drawer horizontalmente centrado en la parte superior de la ventana de visualización.
- **INFERIOR**: Coloca el drawer en la parte inferior de la ventana de visualización.
- **INFERIOR_CENTRO**: Centra horizontalmente el drawer en la parte inferior de la ventana de visualización.
- **IZQUIERDA**: Posiciona el drawer a lo largo del borde izquierdo de la ventana de visualización.
- **DERECHA**: Posiciona el drawer a lo largo del borde derecho de la ventana de visualización.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Manejo de eventos

El componente `Drawer` emite eventos de ciclo de vida que se pueden usar para activar la lógica de la aplicación en respuesta a cambios en su estado abierto o cerrado. 

Eventos soportados:

- `DrawerOpenEvent`: Se activa cuando el drawer está completamente abierto.
- `DrawerCloseEvent`: Se activa cuando el drawer está completamente cerrado.

Puedes adjuntar oyentes a estos eventos para ejecutar lógica cuando el estado del `Drawer` cambia.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Manejar evento de drawer abierto
});

drawer.addCloseListener(e -> {
  // Manejar evento de drawer cerrado
});
```

## Ejemplo: Selector de contactos

El componente `Drawer` expone contenido adicional sin interrumpir la vista actual. Este ejemplo coloca un drawer en la parte inferior central, conteniendo una lista de contactos desplazable.

Cada contacto exhibe un avatar, nombre, ubicación y botón de acción para un acceso rápido a detalles o comunicación. Este enfoque funciona bien para construir herramientas compactas como selectores de contactos, paneles de configuración o notificaciones.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Ejemplo: Administrador de tareas

Este ejemplo utiliza un `Drawer` como un administrador de tareas. Puedes agregar tareas, marcarlas y limpiar las completadas. El pie de `Drawer` incluye controles de formulario para interactuar con la lista de tareas, y el botón “Agregar Tarea” [`Button`](../components/button) se desactiva si se alcanzan 50 tareas.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
height='600px'
/>

## Estilo {#styling}

<TableBuilder name="Drawer" />
