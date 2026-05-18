---
title: Loading
sidebar_position: 65
_i18n_hash: c81b8d0ced3e4097693a186a05f18dbf
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

El componente `Loading` muestra una superposición en un componente o área específica, señalando que una operación está en progreso y bloqueando temporalmente la interacción. Funciona bien para tareas como la carga de datos, cálculos o procesos en segundo plano. Para procesos globales en toda la aplicación, el componente [`BusyIndicator`](../components/busyindicator) cubre toda la interfaz.

<!-- INTRO_END -->

## Básicos {#basics}

La forma más sencilla de crear un componente `Loading` es inicializándolo sin configuraciones adicionales. Por defecto, esto muestra un spinner básico sobre su contenido padre. Sin embargo, también puedes proporcionar un mensaje para más contexto.

Aquí hay un ejemplo de crear un componente `Loading` con un mensaje:

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com.webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/resources/static/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Alcance {#scoping}

El componente `Loading` en webforJ puede limitar su alcance a un contenedor padre específico, como un `Div`, asegurando que solo bloquee la interacción del usuario dentro de ese elemento. Por defecto, el componente `Loading` es relativo a su padre, lo que significa que se superpone al componente padre en lugar de a toda la aplicación.

Para limitar el componente `Loading` a su padre, simplemente agrega el componente `Loading` al contenedor padre. Por ejemplo, si lo agregas a un `Div`, la superposición de carga se aplica solo a ese `Div`:

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading solo bloqueará la interacción dentro de parentDiv
```

## Fondo {#backdrop}

El componente `Loading` en webforJ te permite mostrar un fondo para bloquear la interacción del usuario mientras un proceso está en curso. Por defecto, el componente activa el fondo, pero tienes la opción de desactivarlo si es necesario.

Para el componente `Loading`, el fondo es visible por defecto. Puedes habilitarlo explícitamente o apagarlo utilizando el método `setBackdropVisible()`:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Desactiva el fondo
loading.open();
```
:::info Fondo apagado
Incluso cuando apagas el fondo, el componente `Loading` sigue bloqueando la interacción del usuario para garantizar que el proceso subyacente se complete sin interrupciones. El fondo simplemente controla la superposición visual, no el comportamiento de bloqueo de interacción.
:::

## `Spinner` {#spinner}

El componente `Loading` en webforJ incluye un `Spinner` que indica visualmente que una operación en segundo plano está en progreso. Puedes personalizar este spinner con varias opciones, incluyendo su tamaño, velocidad, dirección, tema y visibilidad.

Aquí hay un ejemplo de cómo puedes personalizar el spinner dentro de un componente `Loading`:

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com.webforj/samples/views/loading/LoadingSpinnerDemoView.java',
  'src/main/resources/static/css/loadingstyles/loadingspinnerdemo.css',
]}
height='300px'
/>

## Casos de uso {#use-cases}
- **Recuperación de datos**  
   Al recuperar datos de un servidor o API, el componente `Loading` superpone una sección específica de la interfaz de usuario, como una tarjeta o formulario, para informar a los usuarios que el sistema está trabajando en segundo plano. Esto es ideal cuando deseas mostrar progreso en solo una parte de la pantalla sin bloquear toda la interfaz.

- **Carga de contenido en tarjetas/secciones**  
   El componente `Loading` puede limitarse a áreas específicas de una página, como tarjetas individuales o contenedores. Esto es útil cuando deseas indicar que una sección particular de la interfaz de usuario aún se está cargando mientras permites que los usuarios interactúen con otras partes de la página.

- **Envíos de formularios complejos**  
   Para envíos de formularios más largos donde la validación o el procesamiento toma tiempo, el componente `Loading` proporciona retroalimentación visual a los usuarios, asegurándoles que su entrada se está procesando activamente.

## Estilo {#styling}

<TableBuilder name="Loading" />
