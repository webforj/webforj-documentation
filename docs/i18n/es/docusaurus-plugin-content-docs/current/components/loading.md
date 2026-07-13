---
title: Loading
sidebar_position: 65
description: >-
  Overlay a parent container with the Loading component to block interaction
  during async tasks, with backdrop and spinner customization.
_i18n_hash: e17c9249d41752ed1f4b98d18028371a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

El componente `Loading` muestra una superposición sobre un componente o área específica, señalando que una operación está en curso y bloqueando temporalmente la interacción. Funciona bien para tareas como carga de datos, cálculos o procesos en segundo plano. Para procesos globales en la aplicación, el componente [`BusyIndicator`](../components/busyindicator) cubre toda la interfaz.

<!-- INTRO_END -->

## Basics {#basics}

La forma más simple de crear un componente `Loading` es inicializándolo sin ninguna configuración adicional. Por defecto, esto muestra un spinner básico sobre su contenido padre. Sin embargo, también puedes proporcionar un mensaje para dar más contexto.

Aquí hay un ejemplo de cómo crear un componente `Loading` con un mensaje:

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Scoping {#scoping}

El componente `Loading` en webforJ puede limitarse a un contenedor padre específico, como un `Div`, asegurando que solo bloquee la interacción del usuario dentro de ese elemento. Por defecto, el componente `Loading` es relativo a su padre, lo que significa que se superpone al componente padre en lugar de a toda la aplicación.

Para limitar el componente `Loading` a su padre, simplemente añade el componente `Loading` al contenedor padre. Por ejemplo, si lo añades a un `Div`, la superposición de carga se aplica solo a ese `Div`:

```java
Div parentDiv = new Div();
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading solo bloqueará la interacción dentro de parentDiv
```

## Backdrop {#backdrop}

El componente `Loading` en webforJ te permite mostrar un fondo para bloquear la interacción del usuario mientras un proceso está en curso. Por defecto, el componente habilita el fondo, pero tienes la opción de desactivarlo si es necesario.

Para el componente `Loading`, el fondo es visible por defecto. Puedes habilitarlo o desactivarlo explícitamente utilizando el método `setBackdropVisible()`:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Desactiva el fondo
loading.open();
```
:::info Fondo Desactivado
Incluso cuando desactivas el fondo, el componente `Loading` continúa bloqueando la interacción del usuario para garantizar que el proceso subyacente se complete sin interrupciones. El fondo simplemente controla la superposición visual, no el comportamiento de bloqueo de interacción.
:::

## `Spinner` {#spinner}

El componente `Loading` en webforJ incluye un `Spinner` que indica visualmente que una operación en segundo plano está en progreso. Puedes personalizar este spinner con varias opciones, incluyendo su tamaño, velocidad, dirección, tema y visibilidad.

Aquí hay un ejemplo de cómo puedes personalizar el spinner dentro de un componente `Loading`:

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingspinnerdemo.css',
]}
height='300px'
/>

## Use cases {#use-cases}
- **Recuperación de Datos**
   Al recuperar datos de un servidor o API, el componente `Loading` superpone una sección específica de la UI, como una tarjeta o formulario, para informar a los usuarios que el sistema está trabajando en segundo plano. Esto es ideal cuando deseas mostrar el progreso en solo una parte de la pantalla sin bloquear toda la interfaz.

- **Carga de Contenido en Tarjetas/Secciones**
   El componente `Loading` puede limitarse a áreas específicas de una página, como tarjetas individuales o contenedores. Esto es útil cuando deseas indicar que una sección particular de la UI aún se está cargando mientras permites que los usuarios interactúen con otras partes de la página.

- **Envíos de Formularios Complejos**
   Para envíos de formularios más largos donde la validación o el procesamiento lleva tiempo, el componente `Loading` proporciona retroalimentación visual a los usuarios, asegurándoles que su entrada se está procesando activamente.

## Styling {#styling}

<TableBuilder name="Loading" />
