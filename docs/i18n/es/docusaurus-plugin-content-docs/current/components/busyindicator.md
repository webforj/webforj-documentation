---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: 30ca15f8b8170f6d7da6a786ddafea7f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

El `BusyIndicator` es una superposición de pantalla completa que señala un proceso en curso y bloquea la interacción del usuario hasta que se complete. Cubre toda la interfaz durante operaciones como inicialización o sincronización de datos. Mientras que el componente [`Loading`](../components/loading) se centra en áreas específicas dentro de la interfaz, el `BusyIndicator` se aplica globalmente.

<!-- INTRO_END -->

## Básicos {#basics}

El `BusyIndicator` en webforJ se muestra como un simple spinner, lo que lo hace fácil de usar sin configuración. Sin embargo, puedes personalizarlo añadiendo un mensaje, ajustando el tema del spinner o modificando las configuraciones de visibilidad. Esto te permite proporcionar más contexto o estilo mientras mantienes una solución funcional lista para usar.

En este ejemplo, el `BusyIndicator` previene cualquier acción del usuario en toda la interfaz hasta que la operación se complete.

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Fondos {#backdrops}

El componente `BusyIndicator` en webforJ te permite mostrar un fondo para bloquear la interacción del usuario mientras un proceso está en curso. Por defecto, el componente habilita el fondo, pero tienes la opción de desactivarlo si es necesario.

El `BusyIndicator` muestra un fondo por defecto. Puedes controlar la visibilidad del fondo usando el método `setBackdropVisible()`, como se muestra a continuación:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Desactiva el fondo
busyIndicator.open();
```
:::info Desactivando el Fondo
Incluso cuando apagas el fondo, el componente `BusyIndicator` continúa bloqueando la interacción del usuario para asegurar que el proceso subyacente se complete sin interrupciones. El fondo simplemente controla la superposición visual, no el comportamiento de bloqueo de interacción.
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
- **Procesamiento en toda la página**
   El `BusyIndicator` es adecuado para operaciones más grandes que abarcan toda la página, como cuando un usuario inicia una tarea que afecta a toda la página, como cargar un archivo o procesar datos en múltiples secciones. Puede informar a los usuarios que toda la aplicación está trabajando, evitando interacciones adicionales hasta que el proceso se complete.

- **Operaciones críticas del sistema**
   Al realizar tareas críticas para el sistema, como sincronización de datos, aplicación de actualizaciones a nivel de sistema o procesamiento de información sensible, el `BusyIndicator` proporciona una retroalimentación visual clara de que una operación importante está en curso, permitiendo al usuario esperar hasta que se complete.

- **Carga de datos asíncrona**
   En escenarios donde se involucren procesamiento de datos asíncronos, como llamar a múltiples APIs o esperar cálculos complejos, el componente `BusyIndicator` indica activamente que el sistema está ocupado, instando a los usuarios a esperar antes de realizar acciones adicionales.

## Estilo {#styling}

<TableBuilder name="BusyIndicator" />
