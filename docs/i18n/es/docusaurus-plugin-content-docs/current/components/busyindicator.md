---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: e8d5c8ba0e26f0cc8fb98a640069347f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

El `BusyIndicator` es una superposición de pantalla completa que señala un proceso en curso y bloquea la interacción del usuario hasta que se complete. Cubre toda la interfaz durante operaciones como inicialización o sincronización de datos. Mientras que el componente [`Loading`](../components/loading) se centra en áreas específicas dentro de la interfaz, el `BusyIndicator` se aplica de manera global.

<!-- INTRO_END -->

## Básicos {#basics}

El `BusyIndicator` en webforJ se muestra como un simple spinner, lo que lo hace fácil de usar sin configuración. Sin embargo, puedes personalizarlo añadiendo un mensaje, ajustando el tema del spinner o modificando las configuraciones de visibilidad. Esto te permite proporcionar más contexto o estilo mientras mantienes una solución funcional lista para usar.

En este ejemplo, el `BusyIndicator` impide cualquier acción del usuario en toda la interfaz hasta que se complete la operación.

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Fondos {#backdrops}

El componente `BusyIndicator` en webforJ te permite mostrar un fondo para bloquear la interacción del usuario mientras un proceso está en curso. Por defecto, el componente habilita el fondo, pero tienes la opción de desactivarlo si es necesario.

El `BusyIndicator` muestra un fondo por defecto. Puedes controlar la visibilidad del fondo utilizando el método `setBackdropVisible()`, como se muestra a continuación:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Desactiva el fondo
busyIndicator.open();
```
:::info Apagar el Fondo
Incluso cuando desactivas el fondo, el componente `BusyIndicator` continúa bloqueando la interacción del usuario para asegurar que el proceso subyacente se complete sin interrupciones. El fondo simplemente controla la superposición visual, no el comportamiento de bloqueo de interacción.
:::

## `Spinner` {#spinner}

El componente `BusyIndicator` en webforJ incluye un `Spinner` que indica visualmente que se está llevando a cabo una operación en segundo plano. Puedes personalizar este spinner con varias opciones, incluyendo su tamaño, velocidad, dirección, tema y visibilidad.

Aquí tienes un ejemplo de cómo puedes personalizar el spinner dentro de un componente `BusyIndicator`:

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Casos de uso {#use-cases}
- **Procesamiento en Toda la Página**  
   El `BusyIndicator` es ideal para operaciones más grandes y que abarcan toda la página, como cuando un usuario inicia una tarea que afecta toda la página, como subir un archivo o procesar datos en múltiples secciones. Puede informar a los usuarios que toda la aplicación está trabajando, impidiendo una interacción adicional hasta que el proceso esté completo.

- **Operaciones Críticas del Sistema**  
   Al realizar tareas críticas para el sistema, como sincronización de datos, aplicación de actualizaciones a nivel de sistema o procesamiento de información sensible, el `BusyIndicator` proporciona una retroalimentación visual clara de que una operación importante está en curso, permitiendo al usuario esperar hasta que se complete.

- **Cargas de Datos Asincrónicas**  
   En escenarios donde se involucra el procesamiento de datos asincrónicos, como al llamar a múltiples API o esperar cálculos complejos, el componente `BusyIndicator` indica activamente que el sistema está ocupado, instando a los usuarios a esperar antes de realizar acciones adicionales.

## Estilo {#styling}

<TableBuilder name="BusyIndicator" />
