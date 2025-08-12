---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: 3384cb35d5087561cc9be2c11b95c7e1
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

El componente `InfiniteScroll` en webforJ carga automáticamente más contenido a medida que los usuarios se desplazan hacia abajo, eliminando la necesidad de paginación. Esto crea una experiencia fluida para listas, feeds y vistas con muchos datos al cargar contenido solo cuando es necesario.

Cuando los usuarios llegan al final del contenido desplazable, `InfiniteScroll` desencadena un evento para cargar más datos. Mientras se cargan nuevos contenidos, muestra un [`Spinner`](../components/spinner) con texto personalizable para indicar que más elementos están en camino.

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

## Gestión de estado {#state-management}

El componente `InfiniteScroll` emite eventos y mantiene un estado interno para ayudar a gestionar cómo y cuándo se carga el contenido.

Para obtener más datos cuando el usuario se desplaza, utiliza el método `onScroll()` o `addScrollListener()` para registrar un oyente. Dentro del oyente, normalmente cargas contenido adicional y llamas a `update()` para refrescar el estado de `InfiniteScroll`.

```java
infiniteScroll.onScroll(event -> {
    infiniteScroll.add(new Paragraph("Elemento cargado"));
    infiniteScroll.update();
});
```

Una vez que todo el contenido ha sido cargado, marca el desplazamiento como completado para prevenir más activaciones. Después de establecer completado, recuerda llamar a `update()` para aplicar el nuevo estado:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Esto desactiva el comportamiento de desplazamiento infinito.

:::tip Restablecer la bandera de carga
Puedes restablecer esta bandera utilizando `setCompleted(false)` si más tarde permites al usuario cargar más contenido (por ejemplo, después de una actualización).
:::


## Personalización del indicador de carga {#loading-indicator-customization}

Por defecto, `InfiniteScroll` muestra un indicador de carga integrado: un pequeño [`Spinner`](../components/spinner) animado junto con un texto de “Cargando datos”. Puedes cambiar el texto mostrado pasando un mensaje personalizado al constructor de `InfiniteScroll` o utilizando `setText()`.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Cargando más registros...");
infiniteScroll.setText("Cargando más elementos...");
```

De manera similar, puedes personalizar el [`Icon`](../components/icon) que se muestra durante la carga utilizando `setIcon()`.

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### Personalización completa {#full-customization}

Si deseas reemplazar completamente tanto el [`Spinner`](../components/spinner) como el texto con tu propio marcado,
puedes agregar contenido directamente en el slot de contenido especial utilizando `addToContent()`.

Cuando populas el slot de contenido, reemplazas completamente el diseño de carga predeterminado.

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## Estilización {#styling}

<TableBuilder name="InfiniteScroll" />
