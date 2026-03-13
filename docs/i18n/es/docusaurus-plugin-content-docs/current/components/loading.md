---
title: Loading
sidebar_position: 65
_i18n_hash: 45fa6bcfc4a2fd5995a06dc98b6f91bf
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

El componente `Loading` muestra una superposición sobre un componente o área específica, señalando que una operación está en progreso y bloqueando temporalmente la interacción. Funciona bien para tareas como la carga de datos, cálculos o procesos en segundo plano. Para procesos globales en la aplicación, el componente [`BusyIndicator`](../components/busyindicator) cubre toda la interfaz en su lugar.

<!-- INTRO_END -->

## Basics {#basics}

La forma más sencilla de crear un componente `Loading` es inicializándolo sin configuraciones adicionales. Por defecto, esto muestra un spinner básico sobre el contenido de su padre. Sin embargo, también puedes proporcionar un mensaje para mayor contexto.

Aquí hay un ejemplo de cómo crear un componente `Loading` con un mensaje:

<ComponentDemo 
path='/webforj/loadingdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java'
cssURL='/css/loadingstyles/loadingdemo.css'
height = '300px'
/>

## Scoping {#scoping}

El componente `Loading` en webforJ puede limitarse a un contenedor padre específico, como un `Div`, asegurando que solo bloquee la interacción del usuario dentro de ese elemento. Por defecto, el componente `Loading` es relativo a su padre, lo que significa que superpone el componente padre en lugar de toda la aplicación.

Para limitar el componente `Loading` a su padre, simplemente agrega el componente `Loading` al contenedor padre. Por ejemplo, si lo agregas a un `Div`, la superposición de carga se aplica solo a ese `Div`:

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading solo bloqueará la interacción dentro del parentDiv
```

## Backdrop {#backdrop}

El componente `Loading` en webforJ te permite mostrar un fondo para bloquear la interacción del usuario mientras un proceso está en curso. Por defecto, el componente habilita el fondo, pero tienes la opción de desactivarlo si es necesario.

Para el componente `Loading`, el fondo es visible por defecto. Puedes habilitar o desactivarlo utilizando el método `setBackdropVisible()`:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Desactiva el fondo
loading.open();
```
:::info Fondo Desactivado
Incluso cuando apagas el fondo, el componente `Loading` sigue bloqueando la interacción del usuario para asegurar que el proceso subyacente se complete sin interrupciones. El fondo simplemente controla la superposición visual, no el comportamiento de bloqueo de interacción.
:::

## `Spinner` {#spinner}

El componente `Loading` en webforJ incluye un `Spinner` que indica visualmente que hay una operación en segundo plano en curso. Puedes personalizar este spinner con varias opciones, incluyendo su tamaño, velocidad, dirección, tema y visibilidad.

Aquí hay un ejemplo de cómo puedes personalizar el spinner dentro de un componente `Loading`:

<ComponentDemo 
path='/webforj/loadingspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java'
cssURL='/css/loadingstyles/loadingspinnerdemo.css'
height = '300px'
/>

## Use cases {#use-cases}
- **Recuperación de Datos**  
   Al recuperar datos de un servidor o API, el componente `Loading` superpone una sección específica de la interfaz de usuario, como una tarjeta o un formulario, para informar a los usuarios que el sistema está trabajando en segundo plano. Esto es ideal cuando deseas mostrar progreso en solo una parte de la pantalla sin bloquear toda la interfaz.

- **Carga de Contenido en Tarjetas/Secciones**  
   El componente `Loading` se puede limitar a áreas específicas de una página, como tarjetas o contenedores individuales. Esto es útil cuando deseas indicar que una sección particular de la interfaz de usuario aún se está cargando mientras permites que los usuarios interactúen con otras partes de la página.

- **Envíos de Formularios Complejos**  
   Para envíos de formularios más largos donde la validación o el procesamiento llevan tiempo, el componente `Loading` proporciona retroalimentación visual a los usuarios, asegurándoles que su entrada se está procesando activamente.

## Styling {#styling}

<TableBuilder name="Loading" />
