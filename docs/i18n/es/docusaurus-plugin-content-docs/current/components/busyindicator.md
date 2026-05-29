---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: 456b6118cd6219f530c5292611ba46e0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

El `BusyIndicator` es una superposición de pantalla completa que señala un proceso en curso y bloquea la interacción del usuario hasta que se completa. Cubre toda la interfaz durante operaciones como la inicialización o la sincronización de datos. Mientras que el componente [`Loading`](../components/loading) se centra en áreas específicas dentro de la interfaz, el `BusyIndicator` se aplica de forma global.

<!-- INTRO_END -->

## Básicos {#basics}

El `BusyIndicator` en webforJ se muestra como un simple spinner, lo que lo hace fácil de usar sin configuración. Sin embargo, puedes personalizarlo añadiendo un mensaje, ajustando el tema del spinner o modificando la configuración de visibilidad. Esto te permite proporcionar más contexto o estilo mientras mantienes una solución funcional lista para usar.

En este ejemplo, el `BusyIndicator` impide cualquier acción del usuario en toda la interfaz hasta que finalice la operación.

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Fondos {#backdrops}

El componente `BusyIndicator` en webforJ te permite mostrar un fondo que bloquea la interacción del usuario mientras un proceso está en curso. Por defecto, el componente habilita el fondo, pero tienes la opción de desactivarlo si es necesario.

El `BusyIndicator` muestra un fondo por defecto. Puedes controlar la visibilidad del fondo usando el método `setBackdropVisible()`, como se muestra a continuación:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Desactiva el fondo
busyIndicator.open();
```
:::info Desactivar Fondo
Incluso cuando desactivas el fondo, el componente `BusyIndicator` continúa bloqueando la interacción del usuario para asegurar que el proceso subyacente se complete sin interrupciones. El fondo simplemente controla la superposición visual, no el comportamiento de bloqueo de interacción.
:::

## `Spinner` {#spinner}

El componente `BusyIndicator` en webforJ incluye un `Spinner` que indica visualmente que una operación en segundo plano está en progreso. Puedes personalizar este spinner con varias opciones, incluyendo su tamaño, velocidad, dirección, tema y visibilidad.

Aquí tienes un ejemplo de cómo puedes personalizar el spinner dentro de un componente `BusyIndicator`:

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Casos de uso {#use-cases}
- **Procesamiento a Nivel de Página**  
   El `BusyIndicator` es muy adecuado para operaciones más grandes, a nivel de página, como cuando un usuario inicia una tarea que afecta a toda la página, como cargar un archivo o procesar datos en múltiples secciones. Puede informar a los usuarios que toda la aplicación está trabajando, impidiendo más interacción hasta que el proceso esté completo.

- **Operaciones Críticas del Sistema**  
   Al realizar tareas críticas del sistema, como sincronizar datos, aplicar actualizaciones a nivel de sistema o procesar información sensible, el `BusyIndicator` proporciona una clara retroalimentación visual de que una operación importante está en curso, permitiendo que el usuario espere hasta que se complete.

- **Cargas de Datos Asincrónicas**  
   En escenarios donde hay procesamiento de datos de manera asincrónica, como al llamar a múltiples APIs o esperar cálculos complejos, el componente `BusyIndicator` indica activamente que el sistema está ocupado, lo que provoca que los usuarios esperen antes de realizar acciones adicionales.

## Estilización {#styling}

<TableBuilder name="BusyIndicator" />
