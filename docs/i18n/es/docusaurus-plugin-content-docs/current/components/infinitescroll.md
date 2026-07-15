---
title: InfiniteScroll
sidebar_position: 60
description: >-
  Load more content as users scroll with the InfiniteScroll component, emitting
  scroll events and showing a customizable loading spinner.
_i18n_hash: f021168e8d6187e38da9107bd2f3ad65
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

El componente `InfiniteScroll` en webforJ carga automáticamente más contenido a medida que los usuarios se desplazan hacia abajo, eliminando la necesidad de paginación. Esto crea una experiencia fluida para listas, feeds y vistas con grandes cantidades de datos al cargar contenido solo cuando es necesario.

Cuando los usuarios alcanzan el final del contenido desplazable, `InfiniteScroll` activa un evento para cargar más datos. Mientras se carga nuevo contenido, muestra un [`Spinner`](../components/spinner) con texto personalizable para indicar que más elementos están en camino.

<!-- INTRO_END -->

## Gestión del estado {#state-management}

El componente `InfiniteScroll` emite eventos y mantiene un estado interno para ayudar a gestionar cómo y cuándo se carga el contenido.

<ComponentDemo
path='/webforj/infinitescroll'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java',
  'src/main/frontend/css/infinitescroll/infinitescroll.css',
]}
/>

Para obtener más datos cuando el usuario se desplaza, utiliza el método `onScroll()` o `addScrollListener()` para registrar un listener. Dentro del listener, normalmente cargas contenido adicional y llamas a `update()` para actualizar el estado de `InfiniteScroll`.

```java
infiniteScroll.onScroll(event -> {
  infiniteScroll.add(new Paragraph("Elemento cargado"));
  infiniteScroll.update();
});
```

Una vez que todo el contenido ha sido cargado, marca el desplazamiento como completado para evitar más activaciones. Después de establecer completado, recuerda llamar a `update()` para aplicar el nuevo estado:

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

De manera similar, puedes personalizar el [`Icono`](../components/icon) que se muestra durante la carga utilizando `setIcon()`.

<ComponentDemo
path='/webforj/infinitescrollloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java',
  'src/main/frontend/css/infinitescroll/infinitescroll.css',
]}
/>

### Personalización completa {#full-customization}

Si deseas reemplazar completamente tanto el [`Spinner`](../components/spinner) como el texto con tu propio marcado, puedes agregar contenido directamente en el espacio de contenido especial usando `addToContent()`.

Cuando rellenes el espacio de contenido, reemplazará por completo el diseño de carga predeterminado.

<ComponentDemo
path='/webforj/infinitescrollcustomloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java',
  'src/main/frontend/css/infinitescroll/infinitescrollcustom.css',
]}
/>

## Estilización {#styling}

<TableBuilder name="InfiniteScroll" />
