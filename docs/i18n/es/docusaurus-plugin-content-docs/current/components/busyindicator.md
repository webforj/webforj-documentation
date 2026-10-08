---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: 663fb0d605695631bad3753aadf178e5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

El `BusyIndicator` es una superposición de pantalla completa que indica un proceso en curso y bloquea la interacción del usuario hasta que finaliza. Cubre toda la interfaz durante operaciones como inicialización o sincronización de datos. Mientras que el componente [`Loading`](../components/loading) se centra en áreas específicas dentro de la interfaz, el `BusyIndicator` se aplica de manera global.

El `BusyIndicator` se muestra como un indicador de carga sin necesidad de configuración. Puedes añadir un mensaje, cambiar el tema del indicador o ajustar la configuración de visibilidad cuando un proceso necesita más contexto.

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Fondos {#backdrops}

El componente `BusyIndicator` en webforJ te permite mostrar un fondo que bloquea la interacción del usuario mientras un proceso está en curso. Por defecto, el componente habilita el fondo, pero tienes la opción de desactivarlo si es necesario.

El `BusyIndicator` muestra un fondo por defecto. Puedes controlar la visibilidad del fondo utilizando el método `setBackdropVisible()`, como se muestra a continuación:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Desactiva el fondo
busyIndicator.open();
```
:::info Desactivando el fondo
Incluso cuando desactivas el fondo, el componente `BusyIndicator` sigue bloqueando la interacción del usuario para asegurar que el proceso subyacente se complete sin interrupciones. El fondo simplemente controla la superposición visual, no el comportamiento de bloqueo de interacción.
:::

## `Spinner` {#spinner}

El componente `BusyIndicator` en webforJ incluye un `Spinner` que indica visualmente que una operación en segundo plano está en progreso. Puedes personalizar este spinner con varias opciones, incluyendo su tamaño, velocidad, dirección, tema y visibilidad.

Aquí hay un ejemplo de cómo puedes personalizar el spinner dentro de un componente `BusyIndicator`:

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Casos de uso {#use-cases}
- **Procesamiento en Página Completa**
   El `BusyIndicator` es adecuado para operaciones más grandes, de página completa, como cuando un usuario inicia una tarea que afecta toda la página, como subir un archivo o procesar datos en múltiples secciones. Puede informar a los usuarios que toda la aplicación está en funcionamiento, evitando interacciones hasta que el proceso se complete.

- **Operaciones Críticas del Sistema**
   Al realizar tareas críticas para el sistema, como sincronizar datos, aplicar actualizaciones en todo el sistema o procesar información sensible, el `BusyIndicator` proporciona una retroalimentación visual clara de que una operación importante está en curso, permitiendo al usuario esperar hasta que se complete.

- **Cargas de Datos Asíncronas**
   En escenarios donde se involucra procesamiento de datos asíncrono, como al llamar a múltiples API o esperar cálculos complejos, el componente `BusyIndicator` indica activamente que el sistema está ocupado, instando a los usuarios a esperar antes de realizar acciones adicionales.

## Estilo {#styling}

<TableBuilder name="BusyIndicator" />
