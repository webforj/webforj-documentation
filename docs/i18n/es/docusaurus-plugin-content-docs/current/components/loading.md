---
title: Loading
sidebar_position: 65
_i18n_hash: fd3e1e31d1a614494358f9d67a9d3cd8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

El componente `Loading` en webforJ muestra una superposición que indica el procesamiento de una operación, impidiendo temporalmente la interacción del usuario hasta que la tarea esté completa. Esta característica mejora la experiencia del usuario, especialmente en situaciones donde tareas como la carga de datos, cálculos o procesos en segundo plano pueden tardar un tiempo. Para procesos globales en toda la aplicación, considere usar el componente [`BusyIndicator`](../components/busyindicator), que bloquea la interacción en toda la interfaz.

## Básicos {#basics}

La forma más simple de crear un componente `Loading` es inicializándolo sin configuraciones adicionales. Por defecto, esto muestra un spinner básico sobre su contenido padre. Sin embargo, también puede proporcionar un mensaje para más contexto.

Aquí hay un ejemplo de creación de un componente `Loading` con un mensaje:

<ComponentDemo 
path='/webforj/loadingdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java'
cssURL='/css/loadingstyles/loadingdemo.css'
height = '300px'
/>

## Alcance {#scoping}

El componente `Loading` en webforJ puede limitarse a un contenedor padre específico, como un `Div`, asegurando que solo bloquea la interacción del usuario dentro de ese elemento. Por defecto, el componente `Loading` es relativo a su padre, lo que significa que se superpone al componente padre en lugar de a toda la aplicación.

Para limitar el componente `Loading` a su padre, simplemente agregue el componente `Loading` al contenedor padre. Por ejemplo, si lo agrega a un `Div`, la superposición de carga se aplica solo a ese `Div`:

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading solo bloqueará la interacción dentro del parentDiv
```

## Fondo {#backdrop}

El componente `Loading` en webforJ le permite mostrar un fondo para bloquear la interacción del usuario mientras un proceso está en curso. Por defecto, el componente habilita el fondo, pero tiene la opción de desactivarlo si es necesario.

Para el componente `Loading`, el fondo es visible por defecto. Puede habilitarlo o desactivarlo explícitamente utilizando el método `setBackdropVisible()`:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Desactiva el fondo
loading.open();
```
:::info Fondo Apagado
Incluso cuando desactiva el fondo, el componente `Loading` continúa bloqueando la interacción del usuario para garantizar que el proceso subyacente se complete sin interrupciones. El fondo simplemente controla la superposición visual, no el comportamiento de bloqueo de interacción.
:::

## `Spinner` {#spinner}

El componente `Loading` en webforJ incluye un `Spinner` que indica visualmente que una operación en segundo plano está en progreso. Puede personalizar este spinner con varias opciones, incluido su tamaño, velocidad, dirección, tema y visibilidad.

Aquí hay un ejemplo de cómo puede personalizar el spinner dentro de un componente `Loading`:

<ComponentDemo 
path='/webforj/loadingspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java'
cssURL='/css/loadingstyles/loadingspinnerdemo.css'
height = '300px'
/>

## Casos de uso {#use-cases}
- **Obtención de Datos**  
   Al recuperar datos de un servidor o API, el componente `Loading` superpone una sección específica de la interfaz de usuario, como una tarjeta o formulario, para informar a los usuarios que el sistema está trabajando en segundo plano. Esto es ideal cuando quiere mostrar progreso en solo una parte de la pantalla sin bloquear toda la interfaz.

- **Carga de Contenido en Tarjetas/Secciones**  
   El componente `Loading` puede limitarse a áreas específicas de una página, como tarjetas individuales o contenedores. Esto es útil cuando desea indicar que una sección particular de la interfaz de usuario aún se está cargando mientras permite a los usuarios interactuar con otras partes de la página.

- **Envíos de Formularios Complejos**  
   Para envíos de formularios más largos donde la validación o el procesamiento toma tiempo, el componente `Loading` proporciona retroalimentación visual a los usuarios, asegurándoles que su entrada se está procesando activamente.

## Estilo {#styling}

<TableBuilder name="Loading" />
