---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: afeb43fb31ce58db2860ceddd8e8527c
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

El componente `InfiniteScroll` en webforJ carga automáticamente más contenido a medida que los usuarios se desplazan hacia abajo, eliminando la necesidad de paginación. Esto crea una experiencia fluida para listas, feeds y vistas con muchos datos al cargar contenido solo cuando es necesario.

Cuando los usuarios alcanzan el final del contenido desplazable, `InfiniteScroll` desencadena un evento para cargar más datos. Mientras se carga contenido nuevo, muestra un [`Spinner`](../components/spinner) con texto personalizable para indicar que más elementos están en camino.

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

## Gestión del estado {#state-management}

El componente `InfiniteScroll` emite eventos y mantiene un estado interno para ayudar a gestionar cómo y cuándo se carga el contenido.

Para obtener más datos cuando el usuario se desplaza, usa el método `onScroll()` o `addScrollListener()` para registrar un listener. Dentro del listener, normalmente cargas contenido adicional y llamas a `update()` para refrescar el estado de `InfiniteScroll`.

```java
infiniteScroll.onScroll(event -> {
    infiniteScroll.add(new Paragraph("Elemento cargado"));
    infiniteScroll.update();
});
```

Una vez que todo el contenido ha sido cargado, marca el desplazamiento como completado para prevenir más desencadenamientos. Después de establecer como completado, recuerda llamar a `update()` para aplicar el nuevo estado:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Esto deshabilita el comportamiento de desplazamiento infinito.

:::tip Restablecer la bandera de carga
Puedes restablecer esta bandera usando `setCompleted(false)` si más tarde permites que el usuario cargue más contenido (por ejemplo, después de una actualización).
:::

## Personalización del indicador de carga {#loading-indicator-customization}

Por defecto, `InfiniteScroll` muestra un indicador de carga incorporado: un pequeño [`Spinner`](../components/spinner) animado junto con un texto de “Cargando datos”. Puedes cambiar el texto mostrado pasando un mensaje personalizado al constructor de `InfiniteScroll` o usando `setText()`.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Cargando más registros...");
infiniteScroll.setText("Cargando más elementos...");
```

Del mismo modo, puedes personalizar el [`Icon`](../components/icon) que se muestra durante la carga usando `setIcon()`.

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### Personalización completa {#full-customization}

Si deseas reemplazar completamente tanto el [`Spinner`](../components/spinner) como el texto con tu propio marcado, puedes añadir contenido directamente en la ranura de contenido especial usando `addToContent()`.

Cuando poblas la ranura de contenido, reemplaza completamente el diseño de carga predeterminado.

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## Estilo {#styling}

<TableBuilder name="InfiniteScroll" />
